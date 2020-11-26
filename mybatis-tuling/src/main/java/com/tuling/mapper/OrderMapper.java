package com.tuling.mapper;

import com.tuling.OrderQueryReq;
import com.tuling.entity.Order;

import java.util.List;

/***
 * @Author 徐庶   QQ:1092002729
 * @Slogan 致敬大师，致敬未来的你
 */
public interface OrderMapper {

    List<Order> selectAll();

    List<Order> selectByPage(OrderQueryReq queryReq);
}
