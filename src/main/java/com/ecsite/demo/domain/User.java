package com.ecsite.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User{
    private Integer id;
    private String name;
    private String email;
    private String password;
    private String zipcode;
    private String address;
    private String telephone;
}
