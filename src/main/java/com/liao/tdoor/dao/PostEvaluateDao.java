package com.liao.tdoor.dao;

import com.liao.tdoor.model.PostEvaluate;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PostEvaluateDao {
    /**
     * 评论区
     * @param post_id
     * @param user_id
     * @param content
     * @param time
     */
    @Insert("insert into post_evaluate(evaluate_id," +
            "user_id,post_id,evaluate_content," +
            "evaluate_time) " +
            "values (#{evaluate_id},#{post_id},#{user_id},#{content},#{time})")
    @SelectKey(keyProperty = "post_id", resultType = String.class, before = true, statement = "select replace(uuid(), '-', '') as id from dual")
    public void PostEvaluateArea(String post_id, String user_id, String content, Date time);

    /**
     * 发送某动态的所有评论
     * @param post_id
     * @return
     */
    @Select("select * from post_evaluate where post_id=#{post_id}")
    public List<PostEvaluate> SendPostEvaluateList(String post_id);
}
