package com.ecsite.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private Integer id;
    private Integer userId;
    private Integer status;
    private Integer totalPrice;
    private Date orderDate;
    private String destinationName;
    private String destinationEmail;
    private String destinationZipcode;
    private String destinationAddress;
    private String destinationTel;
    private Timestamp deliveryTime;
    private Date DateFormatDeliveryTime;
    private Integer paymentMethod;
    private User user;
    private List<OrderItem> orderItemList;
}

