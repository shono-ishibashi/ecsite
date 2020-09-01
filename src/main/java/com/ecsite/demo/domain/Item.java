package com.ecsite.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    private Integer id;
    private String name;
    private String description;
    private Integer priceM;
    private Integer priceL;
    private String imagePath;
    private Boolean deleted;
    private List<Topping> topping;
}
