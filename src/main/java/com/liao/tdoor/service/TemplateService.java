package com.liao.tdoor.service;

import com.liao.tdoor.dao.TemplateDao;
import com.liao.tdoor.dao.UserDao;
import com.liao.tdoor.model.*;
import com.liao.tdoor.responseMsg.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 廖某某
 * @date 2019/2/20 14:31
 * 模板商品服务层
 **/
@Service
public class TemplateService {
    @Autowired
    TemplateDao templateDao;
    @Autowired
    UserDao userDao;

    private List<PostDynamicDetail> detailList=new ArrayList<PostDynamicDetail>();
    private List<TemplateShopDetail> templateList=new ArrayList<TemplateShopDetail>();
    private TemplateShop shop=new TemplateShop();
    private User user=new User();
    private PostDynamicDetail detail=new PostDynamicDetail();
    private RespEntity respEntity=new RespEntity();
    private TemplateShopDetail shopDetail=new TemplateShopDetail();
    private TemplateCollect collects=new TemplateCollect();
    private order purchaseOrder=new order();
    private TemplateDetailImg detailImg=new TemplateDetailImg();
    /**
     * 发送推荐商品数据到模板商城首页
     * @return
     */
    public List<TemplateShop> SendTemplateList(){
        List<TemplateShop> list=new ArrayList<TemplateShop>();
        list=templateDao.QueryTemplate();
        return list;
    }

    /**
     *发送精选商品数据到模板商城首页
     * @return
     */
    public List<TemplateShop> SendTemplateSelected(){
        List<TemplateShop> list=new ArrayList<TemplateShop>();
        list=templateDao.QueryTemplateSelected();
        return list;
    }
    /**
     * 发送数据到商品详情
     * @param user_id
     * @param template_id
     * @return
     */
    public TemplateShopDetail SendTemplateDetail(String user_id,String template_id){
        int isCollect=1;
        if(user_id==null || user_id.equals("")){
            isCollect=0;
        }else {
            collects=templateDao.collect(user_id,template_id);
            if(collects==null){
                isCollect=0;
            }
        }
        int isBuy=1;
        if (user_id==null && user_id.equals("")){
            isBuy=0;
        }else {
            purchaseOrder=templateDao.isPurchase(user_id,template_id);
            if(purchaseOrder==null){
                isBuy=0;
            }
        }
        List<TemplateDetailImg> imgList=new ArrayList<TemplateDetailImg>();
        imgList=templateDao.QueryDetailImg(template_id);
        user=userDao.QueryInfoById(user_id);
        shop=templateDao.QueryCommodityById(template_id);
        shopDetail=new TemplateShopDetail(user,isCollect,isBuy,shop,imgList);
        return shopDetail;
    }
    /**
     * 根据商品id发送商品评价
     * @param template_id
     * @return
     */
    public List<PostDynamicDetail> SendTemplateEvaluate(String template_id){
        List<PostDynamicDetail> t_evaluate=new ArrayList<PostDynamicDetail>();
        List<TemplateEvaluate> evaluateList=new ArrayList<TemplateEvaluate>();
        PostDynamicDetail postDynamicDetail=new PostDynamicDetail();
        evaluateList=templateDao.LoadTemplateEvaluate(template_id);
        for(TemplateEvaluate evaluate:evaluateList){
            user=userDao.QueryInfoById(evaluate.getUser_id());
            postDynamicDetail=new PostDynamicDetail(user.getId(),user.getNickname(),user.getHeadUrl(),evaluate);
            t_evaluate.add(postDynamicDetail);
        }
        return t_evaluate;
    }
    /**
     * 评论商品模板
     * @param user_id
     * @param template_id
     * @param content
     * @param score
     * @return
     */
    public RespEntity TemplateEvaluate(String user_id,String template_id,String content,int score){
        if(user_id!=null || template_id!=null || content!=null || score!=0){
            templateDao.EvaluateTemplate(user_id,template_id,content,score,new Date());
            respEntity=new RespEntity(RespCode.SUCCESS);
        }else {
            respEntity=new RespEntity(RespCode.WARN);
        }
        return respEntity;
    }
    /**
     * 用户收藏-取消收藏-商品模板
     * @param user_id
     * @param template_id
     * @return
     */
    public RespEntity TemplateCollect(String user_id,String template_id){
        if(user_id!=null || template_id!=null){
            collects=templateDao.collect(user_id,template_id);
            //收藏
            if(collects==null){
                templateDao.CollectTemplate(user_id,template_id);
                templateDao.TemplateByCollect(template_id);
                respEntity=new RespEntity(RespCode.SUCCESS);
            }else {
                //取消收藏
                templateDao.CancelCollect(user_id,template_id);
                templateDao.CollectByCancel(template_id);
                respEntity=new RespEntity(RespCode.CANCEL);
            }
        }else {
            respEntity=new RespEntity(RespCode.WARN);
        }
        return respEntity;
    }

    /**
     * 删除已经收藏的模板
     * @param user_id
     * @param template_id
     * @return
     */
    public RespEntity DeleteCollectService(String user_id,String template_id){
        templateDao.CancelCollect(user_id,template_id);
        templateDao.CollectByCancel(template_id);
        return new RespEntity(RespCode.CANCEL);
    }
    /**
     * 用户购买商品
     * @param user_id
     * @param template_id
     * @param price
     * @return
     */
    public RespEntity TemplateBuy(String user_id,String template_id,int price){
        if(user_id!=null || template_id!=null){
            user=userDao.QueryInfoById(user_id);
            //计算余额是否足够购买
            if(user.getTcoin()<price){
                respEntity=new RespEntity(RespCode.NOT_ENOUGH);
            }else {
                purchaseOrder=templateDao.isPurchase(user_id,template_id);
                if(purchaseOrder==null){
                    templateDao.TemplateByBuy(template_id);
                    shop=templateDao.QueryCommodityById(template_id);
                    templateDao.TemplateOrder(template_id,user_id,new Date(),shop.getTemplate_dp_url());
                    userDao.TCoinReduce(user_id,price);
                    respEntity=new RespEntity(RespCode.SUCCESS);
                }else {
                    respEntity=new RespEntity(RespCode.BOUGHT);
                }
            }
        }
        return respEntity;
    }

    /**
     * 查询用户收藏夹
     * @param user_id
     * @return
     */
    public List<TemplateShop> MyFavorites(String user_id){
        List<TemplateShop> list=new ArrayList<TemplateShop>();
        List<TemplateCollect> collects=new ArrayList<TemplateCollect>();
        collects=templateDao.QueryByUserId(user_id);
        for(TemplateCollect collect:collects){
            shop=templateDao.QueryCommodityById(collect.getTemplate_id());
            list.add(shop);
        }
        return list;
    }
    /**
     * 我的订单
     * @param user_id
     * @return
     */
    public List<MyOrders> MyOrders(String user_id){
        List<MyOrders> list=new ArrayList<MyOrders>();
        List<order> orders=new ArrayList<order>();
        orders=templateDao.QueryOrderByUserId(user_id);
        MyOrders myOrders=new MyOrders();
        for(order os:orders){
            shop=templateDao.QueryCommodityById(os.getTemplate_id());
            user=userDao.QueryInfoById(shop.getUser_id());
            myOrders=new MyOrders(os.getTemplate_id(),user.getNickname(),shop.getTemplate_name(),shop.getTemplate_introduce(),shop.getTemplate_img_url(),shop.getTemplate_price(),os.getTrade_time(),shop.getTemplate_dp_url());
            list.add(myOrders);
        }
        return list;
    }
}
