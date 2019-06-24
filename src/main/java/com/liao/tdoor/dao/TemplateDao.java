package com.liao.tdoor.dao;

import com.liao.tdoor.model.*;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author 廖某某
 * @date 2019/2/20 9:55
 **/
@Repository
public interface TemplateDao {

    /**
     * 查询4条(推荐)商品数据
     * @return
     */
    @Select("select * from template_shop order by template_by_collect DESC limit 4")
    public List<TemplateShop> QueryTemplate();

    /**
     * 查询5条（精选）商品数据
     * @return
     */
    @Select("select * from template_shop order by purchase_number DESC limit 5")
    public List<TemplateShop> QueryTemplateSelected();
    /**
     * 用户收藏数+1
     * @param template_id
     */
    @Update("update template_shop set template_by_collect=template_by_collect+1 where template_id=#{template_id}")
    public void TemplateByCollect(String template_id);

    /**
     * 用户取消收藏-1
     * @param template_id
     */
    @Update("update template_shop set template_by_collect=template_by_collect-1 where template_id=#{template_id}")
    public void CollectByCancel(String template_id);
    /**
     * 插入用户收藏表
     * @param user_id
     * @param template_id
     */
    @Insert("insert into template_collect(id,user_id,template_id) values(#{id},#{user_id},#{template_id})")
    @SelectKey(keyProperty = "id", resultType = String.class, before = true, statement = "select replace(uuid(), '-', '') as id from dual")
    public void CollectTemplate(String user_id,String template_id);
    /**
     * 用户购买数+1
     * @param template_id
     */
    @Update("update template_shop set purchase_number=purchase_number+1 where template_id=#{template_id}")
    public void TemplateByBuy(String template_id);
    /**
     * 查询该商品是否被用户收藏
     * @param user_id
     * @param template_id
     * @return
     */
    @Select("select * from template_collect where user_id=#{user_id} and template_id=#{template_id}")
    public TemplateCollect collect(String user_id,String template_id);
    /**
     * 取消收藏，删除记录
     * @param user_id
     * @param template_id
     */
    @Delete("delete from template_collect where user_id=#{user_id} and template_id=#{template_id}")
    public void CancelCollect(String user_id,String template_id);
    /**
     * 订单生成
     * @param template_id
     * @param user_id
     * @param time
     * @param show_url
     */
    @Insert("insert into purchase_order(order_id,template_id,user_id,trade_time,show_url) " +
            "values(#{order_id},#{template_id},#{user_id},#{time},#{show_url})")
    @SelectKey(keyProperty = "order_id", resultType = String.class, before = true, statement = "select replace(uuid(), '-', '') as id from dual")
    public void TemplateOrder(String template_id, String user_id, Date time,String show_url);

    /**
     * 查询用户是否已经购买
     * @param user_id
     * @param template_id
     * @return
     */
    @Select("select * from purchase_order where user_id=#{user_id} and template_id=#{template_id}")
    public order isPurchase(String user_id,String template_id);

    /**
     * 评论数加一
     * @param template_id
     */
    @Update("update template_shop set evaluate_number=evaluate_number+1 where post_id=#{template_id}")
    public void TemplateByEvaluate(String template_id);

    /**
     * 用户评论模板
     * @param user_id
     * @param template_id
     * @param evaluate_content
     * @param score
     */
    @Insert("insert into template_evaluate(id,user_id,template_id,score,evaluate_content,evaluate_time) " +
            "values(#{id},#{user_id},#{template_id},#{score},#{evaluate_content},#{time})")
    @SelectKey(keyProperty = "id", resultType = String.class, before = true, statement = "select replace(uuid(), '-', '') as id from dual")
    public void EvaluateTemplate(String user_id,String template_id,String evaluate_content,int score,Date time);

    /**
     * 封装模板评论到模板详情页面
     * @param template_id
     * @return
     */
    @Select("select * from template_evaluate where template_id=#{template_id} order by evaluate_time DESC")
    public List<TemplateEvaluate> LoadTemplateEvaluate(String template_id);

    /**
     * 根据ID查询商品
     * @param template_id
     * @return
     */
    @Select("select * from template_shop where template_id=#{template_id}")
    public TemplateShop QueryCommodityById(String template_id);

    /**
     * 查询用户所有收藏
     * @param user_id
     * @return
     */
    @Select("select * from template_collect where user_id=#{user_id}")
    public List<TemplateCollect> QueryByUserId(String user_id);

    /**
     * 查询用户订单
     * @param user_id
     * @return
     */
    @Select("select * from purchase_order where user_id=#{user_id} order by trade_time DESC")
    public List<order> QueryOrderByUserId(String user_id);
    /**
     * 查找商品详情图片
     * @param template_id
     * @return
     */
    @Select("select * from template_detail_img where template_id=#{template_id}")
    public List<TemplateDetailImg> QueryDetailImg(String template_id);
}
