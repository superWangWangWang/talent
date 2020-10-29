package com.jiantai.talent.mapper;

import com.jiantai.talent.entity.DataEntity;
import com.jiantai.talent.entity.Resume;
import com.jiantai.talent.entity.StaffInfo;
import com.jiantai.talent.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {
    /**
     * 根据账号查询用户
     * @param account
     * @return
     */
    @Select("SELECT u.*,s.resume_state,s.photo,s.nick_name,s.name,s.sex,s.sex,s.native_place,s.national,s.height,s.married,s.health,s.phone_number,s.intention,s.address,s.skills_and_hobbies,s.education,s.work_experience,s.expected_salary,s.resume,s.watched FROM `user` AS u LEFT JOIN `staff_info` AS s ON u.`id` = s.`uid` WHERE u.`account` = #{account}")
    User findUser(String account);

    /**
     * 注册，插入账号密码
     * @param user
     */
    @Insert("INSERT INTO `talent`.`user` (`account`,`pwd`) VALUES (#{user.account},#{user.pwd})")
    void register(@Param("user") User user);

    /**
     * 注册的时候添加昵
     * @param user
     */
    @Insert("INSERT INTO `talent`.`staff_info` (`uid`,`nick_name`) VALUES(#{user.id},#{user.nickName})")
    void addUserNickName(@Param("user") User user);

    /**
     * 应聘者修改其简历是否公开
     * @param user
     */
    @Update("update `staff_info` set `resume_state` = #{user.resumeState} where uid = #{user.id}")
    void updateResumeState(@Param("user") User user);

    /**
     * 获取所有的教育程度
     * @return
     */
    @Select("SELECT * FROM `education`")
    List<DataEntity> getEducation();

    /**
     * 获取所有的工作年限
     * @return
     */
    @Select("SELECT * FROM `experience`")
    List<DataEntity> getExperience();

    /**
     * 获取所有的薪资区间
     * @return
     */
    @Select("SELECT * FROM `salary`")
    List<DataEntity> getSalary();

    /**
     * 保存新建的简历
     * @param resume
     */
    @Insert("INSERT INTO `talent`.`resume` (`uid`,`name`,`sex`,`age`,`education`,`intention`,`experience`,`salary`,`phone_number`,`work_experience`,`education_experience`,`skill`,`introduction`) VALUES(#{resume.uid},#{resume.name},#{resume.sex},#{resume.age},#{resume.education},#{resume.intention},#{resume.experience},#{resume.salary},#{resume.phoneNumber},#{resume.workExperience},#{resume.educationExperience},#{resume.skill},#{resume.introduction})")
    void addResume(@Param("resume") Resume resume);

    /**
     * 根据u_id查询其未删除的简历
     * @param uid
     * @return
     */
    @Select("SELECT * FROM `resume` WHERE `uid` = #{uid} AND `delete` = 0")
    List<Resume> findResume(Integer uid);

    /**
     * 根据简历id和用户id删除简历
     * @param id
     * @param uid
     */
    @Update("UPDATE `resume` SET `delete` = 1 WHERE `id` = #{id} AND `uid` = #{uid}")
    void deleteResume(String id,String uid);

    /**
     * 编辑简历
     * @param resume
     */
    @Update("UPDATE `talent`.`resume` SET `name` = #{resume.name},`sex` = #{resume.sex},`age` = #{resume.age},`education` = #{resume.education},`intention` = #{resume.intention},`experience` = #{resume.experience},`salary` = #{resume.salary},`phone_number` = #{resume.phoneNumber},`work_experience` = #{resume.workExperience},`education_experience` = #{resume.educationExperience},`skill` = #{resume.skill},`introduction` = #{resume.introduction} WHERE `id` = #{resume.id} AND `uid` = #{resume.uid}")
    void editResume(@Param("resume")Resume resume);

    /**
     * 更新用户的头像--应聘者
     * @param user
     */
    @Update("UPDATE staff_info SET `photo` = #{user.photo} WHERE uid = #{user.id}")
    void addStaffPhoto(@Param("user") User user);
    //====================================================================================

    /**
     * 根据uid查询员工信息
     * @param uid
     * @return
     */
    @Select("SELECT * FROM `talent`.`staff_info` WHERE `staff_info`.`uid` = #{uid}")
    StaffInfo getStaffInfoByUid(Integer uid);

    /**
     * 根据id和uid更新全表
     * @param staffInfo
     */
    @Update("UPDATE `talent`.`staff_info` SET `resume_state` = #{staffInfo.resumeState},`photo` = #{staffInfo.photo},`nick_name` = #{staffInfo.nickName},`name` = #{staffInfo.name},`sex` = #{staffInfo.sex},`age` = #{staffInfo.age},`native_place` = #{staffInfo.nativePlace},`national` = #{staffInfo.national},`height` = #{staffInfo.height},`married` = #{staffInfo.married},`health` = #{staffInfo.health},`phone_number` = #{staffInfo.phoneNumber},`intention` = #{staffInfo.intention},`address` = #{staffInfo.address},`skills_and_hobbies` = #{staffInfo.skillsAndHobbies},`education` = #{staffInfo.education},`work_experience` = #{staffInfo.workExperience},`expected_salary` = #{staffInfo.expectedSalary},`resume` = #{staffInfo.resume},`watched` = #{staffInfo.watched} WHERE `id` = #{staffInfo.id} AND `uid` = #{staffInfo.uid}")
    void updateStaffInfo(@Param("staffInfo") StaffInfo staffInfo);
}
