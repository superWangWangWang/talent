package com.jiantai.talent.mapper;

import com.jiantai.talent.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    /**
     * 根据账号查询用户
     * @param account
     * @return
     */
    @Select("SELECT u.*,s.resume_state,s.photo,s.nick_name,s.name,s.sex,s.birth,s.native_place,s.national,s.height,s.married,s.health,s.phone_number,s.intention,s.address,s.skills_and_hobbies,s.education,s.work_experience,s.expected_salary,s.resume,s.watched FROM `user` AS u INNER JOIN `staff_info` AS s ON u.`id` = s.`uid` WHERE u.`account` = #{account}")
    User findUser(String account);

    /**
     * 注册，插入账号密码
     * @param user
     */
    @Insert("INSERT INTO `talent`.`user` (`account`,`pwd`) VALUES (#{user.account},#{user.pwd})")
    void register(@Param("user") User user);

    @Insert("INSERT INTO `talent`.`staff_info` (`uid`,`nick_name`) VALUES(#{user.id},#{user.nickName})")
    void addUserNickName(@Param("user") User user);
}