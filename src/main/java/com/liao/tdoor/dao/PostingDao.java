package com.liao.tdoor.dao;

import com.liao.tdoor.model.PostCollect;
import com.liao.tdoor.model.PostGreat;
import com.liao.tdoor.model.Posting;
import com.liao.tdoor.responseMsg.RespEntity;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PostingDao {
    /**
     * 用户发布动态，保存到数据库
     * @param user_id
     * @param post_title
     * @param post_content
     * @param category_id
     * @param post_date
     */
    @Insert("insert into post(post_id,user_id,post_title,post_content,category_id,post_date,post_thumbnail,post_web_title) " +
            "values (#{post_id},#{user_id},#{post_title},#{post_content},#{category_id},#{post_date},#{post_thumbnail},#{post_web_title})")
    @SelectKey(keyProperty = "post_id", resultType = String.class, before = true, statement = "select replace(uuid(), '-', '') as id from dual")
    public void SavePostInfo(String user_id,
                             String post_title,
                             String post_content,
                             int category_id,
                             Date post_date,
                             String post_thumbnail,
                             String post_web_title);

    /**
     * 用户点击动态查看，观看次数加一
     * @param post_id
     */
    @Update("update post set post_click_number=post_click_number+1 where post_id=#{post_id}")
    public void PostByClick(String post_id);

    /**
     * 用户点击喜欢
     * @param post_id
     */
    @Update("update post set post_by_like=post_by_like+1 where post_id=#{post_id}")
    public void PostByLike(String post_id);

    /**
     * 用户取消喜欢
     * @param post_id
     */
    @Update("update post set post_by_like=post_by_like-1 where post_id=#{post_id}")
    public void LikeByCancel(String post_id);
    /**
     * 用户点赞,记录 信息
     * @param user_id
     * @param post_id
     */
    @Insert("insert into post_great(great_id,user_id,post_id) values(#{great_id},#{user_id},#{post_id})")
    public void GreatPosting(String user_id,String post_id);
    /**
     * 用户取消点赞，删除记录
     * @param user_id
     * @param post_id
     */
    @Delete("delete from post_great where user_id=#{user_id} and post_id=#{post_id}")
    public void GreatByCancel(String user_id,String post_id);

    /**
     * 用户评论，评论数据++
     * @param post_id
     */
    @Update("update post set post_by_evaluate = post_by_evaluate+1 where post_id=#{post_id}")
    public void PostByEvaluate(String post_id);

    /**
     * 发送首页动态
     * 根据时间
     */
    @Select("select * from post order by post_date DESC")
    public List<Posting> SendPosting();
    /**
     * 根据热度发送首页动态
     * @return
     */
    @Select("select * from post order by post_by_like DESC")
    public List<Posting> SendHotPosting();
    /**
     * 查询是否有点赞记录
     * @param user_id
     * @param post_id
     * @return
     */
    @Select("select * from post_great where user_id=#{user_id} and post_id=#{post_id}")
    public PostGreat IsGreat(String user_id,String post_id);

    /**
     * 用户收藏动态
     * @param user_id
     * @param post_id
     */
    @Insert("insert into post_collect(id,post_id,user_id) values(#{id},#{post_id},#{user_id})")
    @SelectKey(keyProperty = "id", resultType = String.class, before = true, statement = "select replace(uuid(), '-', '') as id from dual")
    public void PostCollect(String user_id,String post_id);
    /**
     * 用户取消收藏该动态
     * @param user_id
     * @param post_id
     */
    @Delete("delete from post_collect where user_id=#{user_id} and post_id=#{post_id}")
    public void CollectCancel(String user_id,String post_id);

    /**
     * 查询用户收藏夹
     * @param user_id
     * @return
     */
    @Select("select * from post_collect where user_id = #{user_id}")
    public List<PostCollect> MyPostFavorites(String user_id);
    /**
     * 查询该条动态是否被收藏
     * @param user_id
     * @param post_id
     * @return
     */
    @Select("select * from post_collect where user_id=#{user_id} and post_id=#{post_id}")
    public PostCollect IsCollect(String user_id,String post_id);
    /**
     * 根据动态ID查询数据
     * @param post_id
     * @return
     */
    @Select("select * from post where post_id=#{post_id}")
    public Posting QueryByPostId(String post_id);
    /**
     * 根据用户ID查询用户发布的动态
     * @param user_id
     * @return
     */
    @Select("select * from post where user_id=#{user_id} order by post_date DESC")
    public List<Posting> QueryPostByUserId(String user_id);

    /**
     * 保存评论数据
     * @param content
     * @param user_id
     * @param post_id
     * @param time
     * @return
     */
    @Insert("insert into post_evaluate(evaluate_id,user_id,post_id,evaluate_content,evaluate_time) " +
            "values (#{id},#{user_id},#{post_id},#{content},#{time})")
    @SelectKey(keyProperty = "id", resultType = String.class, before = true, statement = "select replace(uuid(), '-', '') as id from dual")
    public void AddPostEvaluate(String content,String user_id,String post_id,Date time);
    /**
     * 删除发布的动态
     * @param user_id
     * @param post_id
     */
    @Delete("delete from post where user_id=#{user_id} and post_id=#{post_id}")
    public void DeletePostDao(String user_id,String post_id);
}
