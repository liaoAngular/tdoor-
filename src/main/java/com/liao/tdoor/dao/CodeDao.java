package com.liao.tdoor.dao;

import com.liao.tdoor.model.VerificationCode;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface CodeDao {
    /**
     * 校验验证码
     * @param email
     * @return
     */
    @Select("select * from verification_code where email=#{email}")
    public VerificationCode checkCode(String email);

    /**
     * 保存验证码和用户注册邮箱到数据库
     * @param email
     * @param code
     * @param time
     */
    @Insert("insert into verification_code(register_id,email,code,time) values (#{register_id},#{email},#{code},#{time})")
    @SelectKey(keyProperty = "register_id", resultType = String.class, before = true, statement = "select replace(uuid(), '-', '') as id from dual")
    public void saveCode(String email, String code, Date time);

    /**
     * 注册完成后删除记录
     * @param email
     */
    @Delete("delete from verification_code where email=#{email}")
    public void deleteCode(String email);

    /**
     * 更新数据库中的验证码
     * @param email
     * @param code
     */
    @Update("update verification_code set code=#{code} where email=#{email}")
    public void changeCode(String email,String code);
}
