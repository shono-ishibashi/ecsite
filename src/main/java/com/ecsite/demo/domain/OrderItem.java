package com.ecsite.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {
    private Integer id;
    private Integer itemId;
    private Integer orderId;
    private Integer quantity;
    private Character size;
    private Item item;
    private Integer totalPrice;
    private List<OrderTopping> orderToppingList;
}
