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
import org.springframework.web.bind.annotation.*;

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
    @ResponseBody
    public CommonResponse<List<Address>> orderForm1(@RequestParam("userId") int userId) {
        List<Address> addressList = addressDao.getAllAddressById(userId);
        // 将地址列表保存到session中
        return CommonResponse.createForSuccess(addressList);
    }

//    @GetMapping("/CartCount")
//    public CommonResponse<CartOrder> cartCount(@RequestParam("userId") int userId) {
//        // TODO: 原项目通过模板渲染页面，微服务化后仅提供 JSON 数据，由网关 + 前端项目负责页面展示
//        return buildCartOrderResponse(userId);
//    }

    // 进入购物车结算界面的修改（用于前后端分离）
    @GetMapping("/cartOrder")
    @ResponseBody
    public CommonResponse<CartOrder> CartCount1(@RequestParam("userId")int userId) {

        Cart cart = cartService.getCart(userId);
        List<Address> addressList = addressDao.getAllAddressById(userId);

        CartOrder cartOrder = new CartOrder();
        cartOrder.setAddressList(addressList);
        cartOrder.setCartItemList(cart.getItemList());

        return CommonResponse.createForSuccess(cartOrder);
    }

//    private CommonResponse<CartOrder> buildCartOrderResponse(int userId) {
//        Cart cart = cartService.getCart(userId);
//        List<Address> addressList = addressDao.getAllAddressById(userId);
//        CartOrder cartOrder = new CartOrder();
//        cartOrder.setAddressList(addressList);
//        cartOrder.setCartItemList(cart.getItemList());
//        return CommonResponse.createForSuccess(cartOrder);
//    }

    // 处理提交购物车订单，清楚购物车数据
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


    @PostMapping("/ItemSubmit")
    public CommonResponse<Order> itemHandler1(@RequestBody ItemSubmitObject itemSubmitObject) {
        //增加新订单
        List<Item> items = new ArrayList<>();
        items.add(itemSubmitObject.getItem());
        System.out.println(itemSubmitObject.getItem());
        System.out.println(itemSubmitObject.getAddressID());

        User user = remoteUserService.getUser(itemSubmitObject.getUserId());
        //这里要返回 currentOrder,便于前端回传修改状态
//        System.out.println("111111.....");
        Order order = orderService.addNewOrder3(user, itemSubmitObject.getAddressID(), items);
//        System.out.println("22222222.....");
        if(order == null){
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
//            Item item = itemService.getItemByItemId(order.getItem_id());
            Item item = remoteInventoryService.getItemById(order.getItem_id());
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
