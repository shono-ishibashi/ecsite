package com.ecsite.demo.service;

import com.ecsite.demo.domain.Order;
import com.ecsite.demo.domain.OrderItem;
import com.ecsite.demo.mybatisParam.OrderConfirmParam;
import com.ecsite.demo.mybatisParam.OrderSearchParam;
import com.ecsite.demo.repository.OrderMybatisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
public class OrderService {

    @Autowired
    private OrderMybatisRepository orderMybatisRepository;

    @Autowired
    private HttpSession session;

    public Order showCart() {

        Integer userId = (Integer) session.getAttribute("userId");

        OrderSearchParam param = new OrderSearchParam();
        param.setUserId(userId);
        param.setStatus(0);

        List<Order> order = orderMybatisRepository.findByStatusAndUserId(param);
        if (order.isEmpty()) {
            return null;
        } else {
            Order cart = calculateTotalPrice(order.get(0));

            return cart;
        }
    }

    public void addCart(OrderItem orderItem) {

        //status=0 (cart) が存在しなかったら、新しくcartを作成
        OrderSearchParam param = new OrderSearchParam();

        Integer userId = (Integer) session.getAttribute("userId");

        param.setStatus(0);
        param.setUserId(userId);


        List<Order> cartList = orderMybatisRepository.findByStatusAndUserId(param);

        //cart存在していないため、新規作成
        if (cartList.isEmpty()) {
            Order order = new Order();
            order.setUserId(userId);
            order.setStatus(0);

            orderMybatisRepository.insertOrder(order);
        }
        Order cart = orderMybatisRepository.findByStatusAndUserId(param).get(0);
        //フォームで入力されたorderItemのデータをinsert
        orderItem.setOrderId(cart.getId());

        Integer orderItemId = orderMybatisRepository.insertOrderItem(orderItem);
        if (!orderItem.getOrderToppingList().isEmpty()) {
            orderItem.getOrderToppingList().forEach(orderTopping -> {
                orderTopping.setOrderItemId(orderItemId);
                orderMybatisRepository.insertOrderTopping(orderTopping);
            });
        }

        cart = calculateTotalPrice(orderMybatisRepository.findByStatusAndUserId(param).get(0));

        orderMybatisRepository.updateOrderTotalPrice(cart);
    }

    public Order calculateTotalPrice(Order cart) {


        cart.getOrderItemList().forEach(orderItem -> {
            int totalPrice = 0;

            if (orderItem.getSize() == 'M') {
                if (nonNull(orderItem.getOrderToppingList().get(0).getId())) {
                    totalPrice += orderItem.getOrderToppingList().stream().mapToInt(orderTopping -> orderTopping.getTopping().getPriceM()).sum();
                }
                totalPrice += orderItem.getItem().getPriceM();
            } else {
                if (nonNull(orderItem.getOrderToppingList().get(0).getId())) {
                    totalPrice += orderItem.getOrderToppingList().stream().mapToInt(orderTopping -> orderTopping.getTopping().getPriceL()).sum();
                }
                totalPrice += orderItem.getItem().getPriceL();
            }

            orderItem.setTotalPrice(totalPrice * orderItem.getQuantity());
        });

        Integer orderTotalPrice = cart.getOrderItemList().stream().mapToInt(OrderItem::getTotalPrice).sum();

        cart.setTotalPrice(orderTotalPrice);

        return cart;
    }

    public void deleteItemFromCart(Integer orderItemId){
        orderMybatisRepository.deleteItemFromCart(orderItemId);
    }

    public void updateForOrderConfirm(OrderConfirmParam param){
        orderMybatisRepository.updateForOrderConfirm(param);
    }

    public List<Order> getOrderList() {
        Integer userId = (Integer) session.getAttribute("userId");
        return orderMybatisRepository.findOrderHistory(userId);
    }
}
