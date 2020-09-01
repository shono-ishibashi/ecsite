package com.ecsite.demo.controller;

import com.ecsite.demo.domain.Item;
import com.ecsite.demo.domain.Topping;
import com.ecsite.demo.form.OrderItemForm;
import com.ecsite.demo.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

@Controller
@RequestMapping("/")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @ModelAttribute
    public OrderItemForm orderItemFormSetUp() {
        OrderItemForm orderItemForm = new OrderItemForm();
        orderItemForm.setSize('M');
        return orderItemForm;
    }

    @RequestMapping("/list")
    public String showList(Model model,String partOfName,Integer page) {

        if(isNull(page)){
            page = 1;
        }

        if(page < 1){
            page = 1;
        }

        List<Item> itemList ;

        //検索条件が空文字なら一覧を表示
        if (isNull(partOfName) || partOfName.isEmpty()) {
            itemList = itemService.findAll();

        }else {
            itemList = itemService.findByNameLike(partOfName);
        }

        Integer size = 6;

        Page<Item> itemPage  = itemService.showListPaging(page,size,itemList);

        List<String> itemNameList = new ArrayList<>();
        itemService.findAll().forEach(item -> {
            itemNameList.add(item.getName());
        });

        model.addAttribute("itemList", itemPage);
        model.addAttribute("itemNameList",itemNameList);
        model.addAttribute("searchName",partOfName);
        model.addAttribute("pageNumber",page);

        return "item_list_noodle";
    }

    @RequestMapping("/search")
    public String search(Model model, String partOfName) {

        //検索条件が空文字なら一覧を表示
        if (partOfName.isEmpty()) {
            return "forward:/list";
        }

        List<Item> itemAllList = itemService.findAll();
        List<String> itemNameList = new ArrayList<>();
        itemAllList.forEach(item -> {
            itemNameList.add(item.getName());
        });

        model.addAttribute("itemNameList",itemNameList);

        List<Item> itemList = itemService.findByNameLike(partOfName);

        model.addAttribute("itemList", itemList);
        model.addAttribute("partOfName", partOfName);

        return "item_list_noodle";
    }

    @RequestMapping("/detail")
    public String showDetail(Integer id, Model model) {

        Item item = itemService.load(id);
        List<Topping> toppingList = itemService.findAllTopping();

        model.addAttribute("item", item);
        model.addAttribute("toppingList", toppingList);

        return "item_detail";
    }

}
