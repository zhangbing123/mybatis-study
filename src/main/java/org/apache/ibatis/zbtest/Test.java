package org.apache.ibatis.zbtest;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * mybatis源码分析入口
 */
public class Test {

    public static void main(String[] args) throws IOException {
        //mybatis配置文件路径
        String resource = "mybatis-config.xml";
        //使用Resources.getResourceAsStream()方法返回一个IO流
        InputStream inputStream = Resources.getResourceAsStream(resource);
        /**
         * 获取SqlSessionFactory对象
         * 这一步会把mybatis的配置文件和mapper的映射文件解析成Configuration配置对象
         * 这一步会维护一个本地的二级缓存，即：同一个命名空间的查询共享一个缓存
         */
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        /**
         * 开启一个SqlSession
         * 这一步会创建一个sql执行器并封装到SqlSession对象中
         * 在执行器中会维护一个本地一级缓存，同一个session享有一个本地缓存
         */
        SqlSession session = sqlSessionFactory.openSession();
        try {
            User user = session.selectOne("org.apache.ibatis.zbtest.User.selectByPrimary", 9L);
            System.out.println(user);
        } finally {
            session.close();
        }
    }
}
