package org.csu.nailong.order.dao;

import org.csu.nailong.order.entity.Address;

import java.util.List;

public interface AddressDao {
    List<Address> getAllAddressById(int user_id);
    void addAddress(Address address);
    void deleteAddress(int id);
    void updateAddress(Address address);
    void updateDefaultAddress(int userId);
    Address getAddressById(int id);
}
