package com.tuling.mapper;

import com.tuling.entity.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/***
 * @Author 徐庶   QQ:1092002729
 * @Slogan 致敬大师，致敬未来的你
 */
public interface UserMapper {

    User selectById(Long id);

    @Select(value = "select * from t_user")
    List<User> selectAllUser();

    /**
     * 更新用户
     * @param user
     */
    void updateUser(User user);
}
