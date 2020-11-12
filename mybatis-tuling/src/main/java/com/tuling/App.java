package com.tuling;

import com.tuling.entity.User;
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
                UserMapper mapper = session.getMapper(UserMapper.class);
                System.out.println(mapper.getClass());
                List<User> users = mapper.selectAllUser();
                System.out.println("第一次获取该用户"+users);

//                System.out.println("更新用户");
//                user.setUserName("李四");
//                mapper.updateUser(user);//在这里更新用户后  会清除一级缓存
//                session.clearCache();//清除一级缓存
//
//                User user1 = mapper.selectById(1L);
//                System.out.println("第二次获取该用户"+user1);
//                System.out.println("两次获取的结果"+(users==user1));
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
