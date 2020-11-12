package com.tuling;

import com.tuling.entity.Order;
import com.tuling.entity.User;
import com.tuling.mapper.OrderMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

/**
 * @description: 测试懒加载
 * @author: zhangbing
 * @create: 2020-11-12 10:25
 **/
public class TestLazy {

    public static void main(String[] args) {
        Reader reader;
        try {
            //将XML配置文件构建为Configuration配置类
            reader = Resources.getResourceAsReader("mybatis-config.xml");

            // 通过加载配置文件流构建一个SqlSessionFactory  DefaultSqlSessionFactory
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);

            // 数据源 执行器  DefaultSqlSession
            SqlSession session = sqlSessionFactory.openSession();

            try {

                /**
                 * mybatis的一级缓存是session级别的
                 */
                OrderMapper mapper = session.getMapper(OrderMapper.class);
                List<Order> orders = mapper.selectAll();

                for (Order order : orders) {
                    User user = order.getUser();
                    System.out.println(user.getUserName());
                }


            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                session.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
