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
 * @description: 验证一级缓存
 * @author: zhangbing
 * @create: 2020-10-30 10:08
 **/
public class FirstLevelCache {

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
                UserMapper mapper = session.getMapper(UserMapper.class);
                System.out.println(mapper.getClass());
                User user = mapper.selectById(1L);
                System.out.println("第一次获取该用户"+user);

//                System.out.println("更新用户");
//                user.setUserName("李四");
//                mapper.updateUser(user);//在这里更新用户后  会清除一级缓存
//                session.clearCache();//清除一级缓存

                User user1 = mapper.selectById(1L);
                System.out.println("第二次获取该用户"+user1);
                System.out.println("两次获取的结果"+(user==user1));
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
