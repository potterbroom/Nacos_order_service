package org.csu.nailong.order.entity;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    private int id;

    @NotBlank(message = "用户名不能为空")
    private String username;
    @NotBlank(message = "密码不能为空")
    private String password;

    private String email;
    private int age;
    private String responsibility;
    private int isOnline;
    private int is_frozen;
    private String frozen_reason;
    private int credit;
    private int merchantCredit;
    @Setter
    private String stars;

    private List<Item> cart = new ArrayList<>();
}
