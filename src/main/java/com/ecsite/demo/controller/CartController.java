package com.ecsite.demo.controller;

import com.ecsite.demo.domain.Order;
import com.ecsite.demo.domain.OrderItem;
import com.ecsite.demo.domain.OrderTopping;
import com.ecsite.demo.form.OrderItemForm;
import com.ecsite.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

import java.util.*;

import static java.util.Objects.isNull;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private HttpSession session;

    @Autowired
    private OrderService orderService;




    /**
     * カートの中身を表示するメソッド
     *
     * @return
     */
    @RequestMapping("/show")
    public String showCart(Model model) {

        Order cart = orderService.showCart();
        model.addAttribute("cart", cart);

        return "cart_list";
    }

    /**
     * カートにitemを追加するメソッド
     *
     * @return
     */
    @RequestMapping("/add")
    public String addCart(OrderItemForm orderItemForm) {

        Integer userId = (Integer) session.getAttribute("userId");

        //loginせず、初めてカートに追加した時、仮のuserIdを発行
        if (isNull(userId)) {
            userId = UUID.randomUUID().hashCode();
            session.setAttribute("userId", userId);
        }

        OrderItem orderItem = new OrderItem();
        orderItem.setItemId(orderItemForm.getItemId());
        orderItem.setQuantity(orderItemForm.getQuantity());
        orderItem.setSize(orderItemForm.getSize());
        List<OrderTopping> orderToppingList = new ArrayList<OrderTopping>();
        orderItem.setOrderToppingList(orderToppingList);

        if (!isNull(orderItemForm.getOrderToppingIdList())) {
            orderItemForm.getOrderToppingIdList().forEach(toppingId -> {
                OrderTopping orderTopping = new OrderTopping();
                orderTopping.setToppingId(toppingId);
                orderItem.getOrderToppingList().add(orderTopping);
            });
        }
        orderService.addCart(orderItem);

        return "forward:/list";
    }




    /**
     * カートからitemを削除するメソット
     *
     * @return
     */
    @RequestMapping("/remove")
    public String removeCart(Integer id) {

        orderService.deleteItemFromCart(id);

        return "forward:/cart/show";
    }
}
