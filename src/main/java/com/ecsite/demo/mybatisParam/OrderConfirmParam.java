package com.ecsite.demo.mybatisParam;

import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

@Data
public class OrderConfirmParam {
    private Integer id;
    private Integer userId;
    private Date orderDate;
    private String name;
    private String email;
    private String zipcode;
    private String address;
    private String telephone;
    private Timestamp deliveryTime;
    private Integer paymentMethod;
    private Integer status;
}
