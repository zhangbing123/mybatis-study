package com.tuling;

import com.tuling.entity.Order;
import com.tuling.entity.User;
import com.tuling.mapper.OrderMapper;
import com.tuling.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

/***
 * @Author 徐庶   QQ:1092002729
 * @Slogan 致敬大师，致敬未来的你
 */
public class App {
    public static void main(String[] args) {
        String resource = "mybatis-config.xml";
        Reader reader;
        try {
            //将XML配置文件构建为Configuration配置类
            reader = Resources.getResourceAsReader(resource);
            // 通过加载配置文件流构建一个SqlSessionFactory  DefaultSqlSessionFactory
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            // 数据源 执行器  DefaultSqlSession
            SqlSession session = sqlSessionFactory.openSession();
            try {
                // 执行查询 底层执行jdbc
//                User user = (User)session.selectOne("com.tuling.mapper.UserMapper.selectById", 1);

                /**
                 * mybatis的一级缓存是session级别的
                 */
                OrderMapper mapper = session.getMapper(OrderMapper.class);
                OrderQueryReq queryReq = new OrderQueryReq("测试", 1, 10);
                List<Order> orders = mapper.selectByPage(queryReq);
                System.out.println(orders);
                session.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                session.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
