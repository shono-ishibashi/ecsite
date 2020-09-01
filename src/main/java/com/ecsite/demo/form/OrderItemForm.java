package com.ecsite.demo.form;

import lombok.Data;

import java.util.List;

@Data
public class OrderItemForm {
    private Integer itemId;
    private Character size;
    private List<Integer> orderToppingIdList;
    private Integer quantity;
}
