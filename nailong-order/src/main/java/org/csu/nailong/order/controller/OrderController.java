package org.csu.nailong.order.controller;

import org.csu.nailong.order.common.CommonResponse;
import org.csu.nailong.order.dao.AddressDao;
import org.csu.nailong.order.entity.Address;
import org.csu.nailong.order.entity.Cart;
import org.csu.nailong.order.entity.CartItem;
import org.csu.nailong.order.entity.Item;
import org.csu.nailong.order.entity.Order;
import org.csu.nailong.order.entity.OrderItem;
import org.csu.nailong.order.entity.User;
import org.csu.nailong.order.entity.dto.ItemSubmitObject;
import org.csu.nailong.order.entity.dto.OrderStatusChangeRequest1;
import org.csu.nailong.order.entity.dto.OrderStatusChangeRequest2;
import org.csu.nailong.order.entity.vo.CartOrder;
import org.csu.nailong.order.service.CartService;
import org.csu.nailong.order.service.ItemService;
import org.csu.nailong.order.service.OrderService;
import org.csu.nailong.order.service.RemoteInventoryService;
import org.csu.nailong.order.service.RemoteUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final AddressDao addressDao;

    @Autowired
    private RemoteUserService remoteUserService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private RemoteInventoryService remoteInventoryService;

    @Autowired
    private CartService cartService;

    @Autowired
    public OrderController(AddressDao addressDao) {
        this.addressDao = addressDao;
    }

    @GetMapping("/orderForm")
    public CommonResponse<List<Address>> orderForm(@RequestParam("userId") int userId) {
        // TODO: 原项目通过模板渲染页面，微服务化后仅提供 JSON 数据，由网关 + 前端项目负责页面展示
        List<Address> addressList = addressDao.getAllAddressById(userId);
        return CommonResponse.createForSuccess(addressList);
    }

    @GetMapping("/singleOrder")
    public CommonResponse<List<Address>> orderForm1(@RequestParam("userId") int userId) {
        return orderForm(userId);
    }

    @GetMapping("/CartCount")
    public CommonResponse<CartOrder> cartCount(@RequestParam("userId") int userId) {
        // TODO: 原项目通过模板渲染页面，微服务化后仅提供 JSON 数据，由网关 + 前端项目负责页面展示
        return buildCartOrderResponse(userId);
    }

    @GetMapping("/cartOrder")
    public CommonResponse<CartOrder> cartOrder(@RequestParam("userId") int userId) {
        return buildCartOrderResponse(userId);
    }

    private CommonResponse<CartOrder> buildCartOrderResponse(int userId) {
        Cart cart = cartService.getCart(userId);
        List<Address> addressList = addressDao.getAllAddressById(userId);
        CartOrder cartOrder = new CartOrder();
        cartOrder.setAddressList(addressList);
        cartOrder.setCartItemList(cart.getItemList());
        return CommonResponse.createForSuccess(cartOrder);
    }

    @PostMapping("/CartHandler")
    public CommonResponse<List<Order>> cartHandler(@RequestParam("userId") int userId,
                                                   @RequestParam("address") String addressID,
                                                   @RequestBody Cart cart) {
        // TODO: 原项目依赖 HttpSession 清空购物车，这里仅返回新订单信息，清理逻辑交由前端或调用方处理
        User user = remoteUserService.getUser(userId);
        if (user == null) {
            return CommonResponse.createForError("用户不存在");
        }
        List<CartItem> cartItems = cart.getItemList() == null ? new ArrayList<>() : cart.getItemList();
        List<Order> currentOrderList = orderService.addNewOrder1(user, addressID, cartItems);
        return CommonResponse.createForSuccess(currentOrderList);
    }

    @PostMapping("/CartSubmit")
    public CommonResponse<List<Order>> cartHandler1(@RequestParam("userId") int userId,
                                                    @RequestParam("address") String addressID) {
        Cart cart = cartService.getCart(userId);
        List<CartItem> cartItems = cart.getItemList();
        for (CartItem cartItem : cartItems) {
            cartService.removeItemFromCart(userId, cartItem.getItemID());
        }

        User user = remoteUserService.getUser(userId);
        if (user == null) {
            return CommonResponse.createForError("用户不存在");
        }

        List<Order> currentOrderList = orderService.addNewOrder1(user, addressID, cartItems);
        return CommonResponse.createForSuccess(currentOrderList);
    }

    @PostMapping("/ItemHandler")
    public CommonResponse<Order> itemHandler(@RequestBody ItemSubmitObject itemSubmitObject) {
        return processSingleItemOrder(itemSubmitObject);
    }

    @PostMapping("/ItemSubmit")
    public CommonResponse<Order> itemHandler1(@RequestBody ItemSubmitObject itemSubmitObject) {
        return processSingleItemOrder(itemSubmitObject);
    }

    private CommonResponse<Order> processSingleItemOrder(ItemSubmitObject itemSubmitObject) {
        List<Item> items = new ArrayList<>();
        items.add(itemSubmitObject.getItem());

        User user = remoteUserService.getUser(itemSubmitObject.getUserId());
        if (user == null) {
            return CommonResponse.createForError("用户不存在");
        }

        Order order = orderService.addNewOrder3(user, itemSubmitObject.getAddressID(), items);
        if (order == null) {
            return CommonResponse.createForError("库存不足");
        }
        return CommonResponse.createForSuccess(order);
    }

    @PostMapping("/addresses")
    public CommonResponse<Void> addAddress(@RequestBody Address newAddress) {
        if (newAddress.getIsDefault() != 0) {
            addressDao.updateDefaultAddress(newAddress.getUserId());
        }
        addressDao.addAddress(newAddress);
        return CommonResponse.createForSuccess();
    }

    @DeleteMapping("/addresses/{addressId}")
    public CommonResponse<Void> delete(@PathVariable int addressId) {
        addressDao.deleteAddress(addressId);
        return CommonResponse.createForSuccess();
    }

    @GetMapping("/orders/{userId}")
    public CommonResponse<List<OrderItem>> myOrder(@PathVariable("userId") int userId) {
        User user = remoteUserService.getUser(userId);
        if (user == null) {
            return CommonResponse.createForError("用户不存在");
        }

        List<Order> orderList = orderService.getOrderListByClient(userId, 0);
        List<OrderItem> orderItems = orderService.getOrderItems(userId, orderList, 0);
        return CommonResponse.createForSuccess(orderItems);
    }

    @PutMapping("/orderStatus")
    public CommonResponse<Void> statusChangeTo1(@RequestBody OrderStatusChangeRequest1 request) {
        for (String orderId : request.getCurrentOrderList()) {
            Order order = orderService.getOrderByOrderId(orderId);
            orderService.updateOrder(order, request.getNextStatus());
        }
        return CommonResponse.createForSuccess();
    }

    @PutMapping("/status")
    public CommonResponse<Void> statusChange(@RequestBody OrderStatusChangeRequest2 request) {
        Order order = orderService.getOrderByOrderId(request.getOrderId());
        if ("10".equals(request.getNextStatus())) {
            Item item = itemService.getItemByItemId(order.getItem_id());
            int remain = remoteInventoryService.getItemCount(item.getId());
            item.setRemainingNumb(remain + order.getAmount());
            order.setIs_occupy(0);
            remoteInventoryService.updateItem(item);
        }
        orderService.updateOrder(order, request.getNextStatus());
        return CommonResponse.createForSuccess();
    }

    @GetMapping("/newOrders/{userId}")
    public CommonResponse<List<OrderItem>> updateMyOrder(@PathVariable("userId") int userId) {
        List<Order> orderList = orderService.getOrderListByClient(userId, 0);
        List<OrderItem> orderItems = orderService.getOrderItems(userId, orderList, 0);
        return CommonResponse.createForSuccess(orderItems);
    }

    @GetMapping("/address/{orderId}")
    public CommonResponse<Address> getAddress(@PathVariable("orderId") String orderId) {
        int addressId = orderService.getOrderByOrderId(orderId).getAddress_id();
        Address address = addressDao.getAddressById(addressId);
        return CommonResponse.createForSuccess(address);
    }

    @GetMapping("/{orderId}")
    public CommonResponse<Order> getOrder(@PathVariable("orderId") String orderId) {
        Order order = orderService.getOrderByOrderId(orderId);
        return CommonResponse.createForSuccess(order);
    }
}
