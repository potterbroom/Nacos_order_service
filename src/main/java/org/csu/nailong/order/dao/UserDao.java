package org.csu.nailong.order.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.csu.nailong.order.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserDao {
    List<User> getAllUsers();

    User getUser(@Param("id") int id);

    int addUser(User user);

    User findByUsername(@Param("username") String username);

    User getUserByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    User getUserByUsername(@Param("username") String username);

    void updateUser(@Param("id") int id,
            @Param("username") String username,
            @Param("password") String password,
            @Param("email") String email,
            @Param("age") int age);

    boolean deleteUser(@Param("id") int id);

    void updateUserPassword(@Param("id") int id, @Param("password") String password);

    int getUserIdByUsername(@Param("username") String username);

    List<User> getAllUsersWithDetails();
}
