package com.jiantai.talent.mapper;

import com.jiantai.talent.entity.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    //========================================= [user] 账户表 ==========================================
    //--------查
    /**
     * 根据账号查询用户
     * @param account
     * @return
     */
    @Select("SELECT * FROM `user` WHERE `account` = #{account}")
    User findUserByAccount(String account);

    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    @Select("SELECT * FROM `user` WHERE `id` = #{id}")
    User findUserById(Integer id);

    //----------更
    /**
     * 根据user对象更新user表
     * @param user
     */
    @Update("UPDATE `talent`.`user` SET `account` = #{user.account},`pwd` = #{user.pwd},`type` = #{user.type},`state` = #{user.state},`show` = #{user.show},`login_count` = #{user.loginCount} WHERE `id` = #{user.id}")
    void updateUser(@Param("user") User user);
    //-----------增
    /**
     * 注册，插入账号密码
     * @param user
     */
    @Insert("INSERT INTO `talent`.`user` (`account`,`pwd`) VALUES (#{user.account},#{user.pwd})")
    @Options(useGeneratedKeys = true, keyProperty = "user.id")
    void addUser(@Param("user") User user);

    //========================================= [staff_info] 员工信息表 ==========================================
    //---------查
    /**
     * 根据uid查询员工信息表
     * @param uid
     * @return
     */
    @Select("SELECT * FROM `staff_info` WHERE `uid` = #{uid}")
    StaffInfo findStaffInfoByUid(Integer uid);
    //---------增

    /**
     * 注册的时候顺带新增一份staff info表数据  (正常情况不要使用 )
     * @param user
     */
    @Insert("INSERT INTO `staff_info` (`uid`) VALUES (#{user.id})")
    void addStaffInfo(@Param("user") User user);

    //---------更
    /**
     * 根据uid更新staff_info表
     * @param staffInfo
     */
    @Update("UPDATE `talent`.`staff_info` SET `photo` = #{staffInfo.photo},`nick_name` = #{staffInfo.nickName},`name` = #{staffInfo.name},`sex` = #{staffInfo.sex},`age` = #{staffInfo.age},`native_place` = #{staffInfo.nativePlace},`national` = #{staffInfo.national},`height` = #{staffInfo.height},`married` = #{staffInfo.married},`health` = #{staffInfo.health},`phone_number` = #{staffInfo.phoneNumber},`intention` = #{staffInfo.intention},`address` = #{staffInfo.address},`skills_and_hobbies` = #{staffInfo.skillsAndHobbies},`education` = #{staffInfo.education},`expected_salary` = #{staffInfo.expectedSalary},`watched` = #{staffInfo.watched} WHERE `uid` = #{staffInfo.uid}")
    void updateStaffInfo(@Param("staffInfo") StaffInfo staffInfo);
    //========================================= [education] 教育程度表 ==========================================
    //-------------查
    /**
     * 获取所有的教育程度
     * @return
     */
    @Select("SELECT * FROM `education`")
    List<Education> getEducation();

    //========================================= [experience] 工作年限表 ==========================================
    //-------------查
    /**
     * 获取所有的工作年限
     * @return
     */
    @Select("SELECT * FROM `experience`")
    List<Experience> getExperience();
    //========================================= [salary] 薪水表 ==========================================
    //-------------查
    /**
     * 获取所有的薪资区间
     * @return
     */
    @Select("SELECT * FROM `salary`")
    List<Salary> getSalary();
    //========================================= [resume] 简历表 ==========================================
    //------------查
        /**
     * 根据u_id查询其未删除的简历
     * @param uid
     * @return
     */
    @Select("SELECT * FROM `resume` WHERE `uid` = #{uid} AND `delete` = 0")
    List<Resume> findResume(Integer uid);
    //-------------增
    /**
     * 保存新建的简历
     * @param resume
     */
    @Insert("INSERT INTO `talent`.`resume` (`uid`,`name`,`sex`,`age`,`education`,`intention`,`experience`,`salary`,`phone_number`,`work_experience`,`education_experience`,`skill`,`introduction`) VALUES(#{resume.uid},#{resume.name},#{resume.sex},#{resume.age},#{resume.education},#{resume.intention},#{resume.experience},#{resume.salary},#{resume.phoneNumber},#{resume.workExperience},#{resume.educationExperience},#{resume.skill},#{resume.introduction})")
    void addResume(@Param("resume") Resume resume);


    //-------------更
    /**
     * 根据简历id和用户id删除简历
     * @param resume
     */
    @Update("UPDATE `resume` SET `delete` = 1 WHERE `id` = #{resume.id} AND `uid` = #{resume.uid}")
    void deleteResume(@Param("resume") Resume resume);

    /**
     * 根据id和uid编辑简历
     * @param resume
     */
    @Update("UPDATE `talent`.`resume` SET `name` = #{resume.name},`sex` = #{resume.sex},`age` = #{resume.age},`education` = #{resume.education},`intention` = #{resume.intention},`experience` = #{resume.experience},`salary` = #{resume.salary},`phone_number` = #{resume.phoneNumber},`work_experience` = #{resume.workExperience},`education_experience` = #{resume.educationExperience},`skill` = #{resume.skill},`introduction` = #{resume.introduction} WHERE `id` = #{resume.id} AND `uid` = #{resume.uid}")
    void editResume(@Param("resume")Resume resume);
    //================================================== [boss_info] boss信息表 ================================
    //---------------------------查

    /**
     * 根据user id 查询boss相关信息
     * @param user
     * @return
     */
    @Select("SELECT * FROM `boss_info` WHERE `uid` = #{user.id}")
    BossInfo findBossInfoByUid(@Param("user") User user);
    //---------------------------更
    /**
     * 更新boss信息
     * @param bossInfo
     */
    @Update("UPDATE `talent`.`boss_info` SET `photo` = #{bossInfo.photo},`name` = #{bossInfo.name},`phone_number` = #{bossInfo.phoneNumber},`address` = #{bossInfo.address},`staff_count` = #{bossInfo.staffCount},`credit_code` = #{bossInfo.creditCode},`website` = #{bossInfo.website},`watched` = #{bossInfo.watched} WHERE `id` = #{bossInfo.id} AND uid = #{bossInfo.uid}")
    void updateBossInfo(@Param("bossInfo") BossInfo bossInfo);
    //================================================== [welfare] 福利表 ================================
    //---------------------------更

    /**
     * 获取所有的福利
     * @return
     */
    @Select("SELECT * FROM welfare")
    List<Welfare> getWelfare();
    //================================================== [recruit] 招聘表 ================================
    //---------------------------查

    /**
     * 查询所有的简历--公开的 show = 0, 1=隐藏，delete 0=正常，1=已删除,根据发布时间排序
     * @return
     */
    @Select("SELECT * FROM recruit WHERE `show` = 0 AND `delete` = 0 ORDER BY `update_time` DESC")
    List<Recruit> getRecruit();

    /**
     * 根据用户id 查询其下所有的发布的简历
     * @param user
     * @return
     */
    @Select("SELECT * FROM recruit WHERE `uid` = #{user.id}")
    List<Recruit> findRecruitByUid(@Param("user") User user);

    /**
     * 根据id查询招聘信息
     * @param recruit
     * @return
     */
    @Select("SELECT * FROM recruit WHERE `id` = #{recruit.id} AND `uid` = #{recruit.uid}")
    Recruit findRecruitById(@Param("recruit") Recruit recruit);
    //---------------------------增加

    /**
     * 新增发布的招聘
     * @param recruit
     */
    @Insert("INSERT INTO `talent`.`recruit` (`uid`,`title`,`job`,`salary`,`number`,`education`,`experience`,`address`,`welfare`,`work`,`contact`,`telephone`) VALUES  (#{recruit.uid},#{recruit.title},#{recruit.job},#{recruit.salary},#{recruit.number},#{recruit.education},#{recruit.experience},#{recruit.address},#{recruit.welfare},#{recruit.work},#{recruit.contact},#{recruit.telephone})")
    void addRecruit(@Param("recruit") Recruit recruit);
    //---------------------------更

    /**
     * 更新招聘表
     * @param recruit
     */
    @Update("UPDATE `talent`.`recruit` SET `uid` = #{recruit.uid},`show` = #{recruit.show},`title` = #{recruit.title},`job` = #{recruit.job},`salary` = #{recruit.salary},`number` = #{recruit.number},`education` = #{recruit.education},`experience` = #{recruit.experience},`address` = #{recruit.address},`welfare` = #{recruit.welfare},`work` = #{recruit.work},`contact` = #{recruit.contact},`telephone` = #{recruit.telephone},`watched` = #{recruit.watched} WHERE `id` = #{recruit.id} AND `uid` = #{recruit.uid}")
    void updateRecruit(@Param("recruit") Recruit recruit);

    //================================================== [notice] 公告表 ================================
    //---------------------------查
    @Select("SELECT * FROM `notice` WHERE `show` = 0 ORDER BY `update_time` DESC")
    List<Notice> getNotice();
}
