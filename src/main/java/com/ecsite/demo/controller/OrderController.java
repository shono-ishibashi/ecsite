package com.ecsite.demo.controller;

import com.ecsite.demo.domain.Order;
import com.ecsite.demo.form.OrderConfirmForm;
import com.ecsite.demo.mybatisParam.OrderConfirmParam;
import com.ecsite.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private HttpSession session;

    @ModelAttribute
    private OrderConfirmForm setUpOrderConfirmForm() {
        return new OrderConfirmForm();
    }

    @RequestMapping("/to-order-confirm")
    public String toOrderConfirm(Model model) {

        Map<Integer,String> deliveryTimeMap = new TreeMap<>();
        deliveryTimeMap.put(10,"10時");
        deliveryTimeMap.put(11,"11時");
        deliveryTimeMap.put(12,"12時");
        deliveryTimeMap.put(13,"13時");
        deliveryTimeMap.put(14,"14時");
        deliveryTimeMap.put(15,"15時");
        deliveryTimeMap.put(16,"16時");
        deliveryTimeMap.put(17,"17時");
        deliveryTimeMap.put(18,"18時");

        model.addAttribute("deliveryTimeMap",deliveryTimeMap);


        Order order = orderService.showCart();
        model.addAttribute("cart",order);

        return "order_confirm";
    }

    @RequestMapping("/order-confirm")
    public String orderConfirm(
            @Validated OrderConfirmForm form
            , BindingResult result
            , Model model
            , Integer orderId
            ) {

        if(result.hasErrors()){
            return toOrderConfirm(model);
        }

        //現在から３時間後
        LocalDateTime deliveryTimeLimit = LocalDateTime.now();
        deliveryTimeLimit = deliveryTimeLimit.plusHours(3);

        //注文日時
        LocalDateTime inputDeliveryTime = LocalDateTime.ofInstant(form.getDeliveryTime().toInstant(), ZoneId.systemDefault()).with(LocalTime.of(form.getDeliveryTimeHour(),0));


        //注文時刻が注文時点の３時間後よりも前なら、エラー
        if (deliveryTimeLimit.isAfter(inputDeliveryTime)) {
            model.addAttribute("deliveryTimeError",true);
            model.addAttribute("orderConfirmForm",form);
            return toOrderConfirm(model);
        }



        //-----データ更新-----

        OrderConfirmParam param = new OrderConfirmParam();

        param.setId(orderId);
        param.setName(form.getName());
        param.setEmail(form.getEmail());
        param.setZipcode(form.getZipcode());
        param.setAddress(form.getAddress());
        param.setTelephone(form.getTelephone());
        param.setDeliveryTime(Timestamp.valueOf(inputDeliveryTime));
        param.setUserId((Integer) session.getAttribute("userId"));

        if(form.getPaymentMethod() == 1){
            param.setStatus(1);
        }else {
            param.setStatus(2);
        }

        param.setOrderDate(new Date());

        param.setPaymentMethod(form.getPaymentMethod());

        orderService.updateForOrderConfirm(param);

        return "order_finished";
    }


    @RequestMapping("/history")
    public String orderHistory(Model model){

        List<Order> orderList = orderService.getOrderList();

        model.addAttribute("orderList",orderList);

        return "order_history";
    }


    public static LocalDate date2LocalDate(final Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
