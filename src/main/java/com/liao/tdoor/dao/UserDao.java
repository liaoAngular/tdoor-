package com.liao.tdoor.dao;

import com.liao.tdoor.model.User;
import com.liao.tdoor.model.UserSign;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface UserDao {
    /**
     * @desc 查询改邮箱是否被注册
     * @param email
     * @return
     */
    @Select("select * from user where email=#{email}")
    public User isExistUser(String email);
    /**
     * 用户注册
     * @param user
     */
    @Insert("insert into user(id,email,password,nickname) values (#{id},#{email},#{password},#{nickname})")
    @SelectKey(keyProperty = "id", resultType = String.class, before = true, statement = "select replace(uuid(), '-', '') as id from dual")
    public void addUser(User user);
    /**
     * 用户登录
     * @param email
     * @param password
     * @return
     */
    @Select("select * from user where email=#{email} and password=#{password}")
    public User login(String email,String password);

    /**
     * 更改密码
     * @param email
     * @param newPassword
     */
    @Update("update user set password=#{newPassword} where email=#{email}")
    public void ChangePassword(String email,String newPassword);

    /**
     * 通过ID查询用户信息
     * @param user_id
     * @return
     */
    @Select("select * from user where id=#{user_id}")
    public User QueryInfoById(String user_id);

    /**
     * 购买商品，金币减去
     * @param user_id
     * @param price
     */
    @Update("update user set tcoin=tcoin-#{price} where id=#{user_id}")
    public void TCoinReduce(String user_id,int price);
    /**
     * 查询用户 是否是第一次签到
     * @param user_id
     * @return
     */
    @Select("select * from user_sign where user_id=#{user_id}")
    public UserSign SignRecord(String user_id);
    /**
     * 用户第一次签到，直接插入数据
     * @param user_id
     * @param create_time
     */
    @Insert("insert into user_sign(sign_id,user_id,count," +
            "create_time,update_time) values (#{sign_id}," +
            "#{user_id},1,#{create_time},#{create_time})")
    @SelectKey(keyProperty = "sign_id", resultType = String.class, before = true, statement = "select replace(uuid(), '-', '') as id from dual")
    public void FirstSign(String user_id, Date create_time);
    /**
     * 查询用户签到记录
     * @param user_id
     */
    @Select("select * from user_sign where user_id=#{user_id}")
    public UserSign IsSign(String user_id);
    /**
     * 用户签到
     * @param user_id
     * @param sign_time
     */
    @Update("update user_sign set update_time=#{sign_time} where user_id=#{user_id}")
    public void SignIn(String user_id,Date sign_time);
    /**
     * 签到获取20积分
     * @param user_id
     */
    @Update("update user set tcoin=tcoin+20 where id=#{user_id}")
    public void AddScoreDao(String user_id);
    /**
     * 更改用户头像
     * @param headImgUrl
     */
    @Update("update user set headUrl=#{headImgUrl} where id=#{user_id}")
    public void UpdateUserHeadImg(String headImgUrl,String user_id);

    /**
     * 更新用户信息
     * @param nickName 昵称
     * @param profiles 简介
     * @param sex 性别
     * @param address 地址
     */
    @Update("update user set nickname=#{nickName},profiles=#{profiles},sex=#{sex},address=#{address} where id=#{user_id}")
    public void UpdateUserInfo(String nickName,String profiles,int sex,String address,String user_id);

    /**
     * 赞助原创作者10积分
     * @param author_id
     */
    @Update("update user set tcoin=tcoin+10 where id=#{author_id}")
    public void SponsorUserDao(String author_id);
    /**
     * 扣除积分
     * @param user_id
     */
    @Update("update user set tcoin=tcoin-10 where id=#{user_id}")
    public void ReduceScoreDao(String user_id);
}
