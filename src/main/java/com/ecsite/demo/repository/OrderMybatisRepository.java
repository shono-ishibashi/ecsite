package com.ecsite.demo.repository;

import com.ecsite.demo.domain.Order;
import com.ecsite.demo.domain.OrderIdUpdateParam;
import com.ecsite.demo.domain.OrderItem;
import com.ecsite.demo.domain.OrderTopping;
import com.ecsite.demo.mybatisParam.OrderConfirmParam;
import com.ecsite.demo.mybatisParam.OrderSearchParam;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderMybatisRepository {

    private final SqlSessionTemplate sqlSessionTemplate;

    private final String STATEMENT = "com.ecsite.demo.mapper.OrderMapper";

    public List<Order> findByStatusAndUserId(OrderSearchParam param){
        return sqlSessionTemplate.selectList(STATEMENT + ".findByStatusAndUserId",param);
    }

    public Integer insertOrder(Order order){
        return sqlSessionTemplate.selectOne(STATEMENT + ".insertOrder" , order);
    }

    public Integer insertOrderItem(OrderItem orderItem){
        return sqlSessionTemplate.selectOne(STATEMENT + ".insertOrderItem",orderItem);
    }

    public void insertOrderTopping(OrderTopping orderTopping){
        sqlSessionTemplate.insert(STATEMENT + ".insertOrderTopping", orderTopping);
    }

    public void updateOrderTotalPrice(Order order){
        sqlSessionTemplate.update(STATEMENT + ".updateOrderTotalPrice", order);
    }

    public void deleteItemFromCart(Integer orderItemId){
        sqlSessionTemplate.delete(STATEMENT + ".deleteItemFromCart",orderItemId);
    }

    public void updateForOrderConfirm(OrderConfirmParam param){
        sqlSessionTemplate.update(STATEMENT + ".updateForOrderConfirm",param);
    }

    public List<Order> findOrderHistory(Integer userId) {
        return sqlSessionTemplate.selectList(STATEMENT + ".findOrderHistory" , userId);
    }

    public void updateOrderId(OrderIdUpdateParam updateParam){
        sqlSessionTemplate.update(STATEMENT + ".updateOrderId",updateParam);
    }



    
}
