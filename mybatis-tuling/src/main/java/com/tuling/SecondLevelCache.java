package com.tuling;

import com.tuling.entity.User;
import com.tuling.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

/**
 * @description: 二级缓存
 * @author: zhangbing
 * @create: 2020-11-12 11:02
 **/
public class SecondLevelCache {

    public static void main(String[] args) {
        //二级缓存是SqlSessionFactory级别的
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();

        // 开启第一个SqlSession 会打印查询的sql语句
        SqlSession session1 = sqlSessionFactory.openSession();
        UserMapper mapper = session1.getMapper(UserMapper.class);
        System.out.println(mapper.getClass());
        User user = mapper.selectById(1L);
        System.out.println("第一次获取该用户" + user);
        session1.commit();
        session1.close();


        //开启第二个SqlSession 不会打印查询的算起来语句

        SqlSession session2 = sqlSessionFactory.openSession();
        UserMapper mapper2 = session2.getMapper(UserMapper.class);
        System.out.println(mapper2.getClass());
        User use2 = mapper2.selectById(1L);
        System.out.println("第二次次获取该用户" + use2);
        session2.commit();
        session2.close();


    }

    private static SqlSessionFactory getSqlSessionFactory() {
        Reader reader;//将XML配置文件构建为Configuration配置类
        try {
            reader = Resources.getResourceAsReader("mybatis-config.xml");
            // 通过加载配置文件流构建一个SqlSessionFactory  DefaultSqlSessionFactory
            return new SqlSessionFactoryBuilder().build(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
