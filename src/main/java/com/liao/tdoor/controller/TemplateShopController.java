package com.liao.tdoor.controller;

import com.liao.tdoor.annotation.CurrentUser;
import com.liao.tdoor.annotation.PassToken;
import com.liao.tdoor.annotation.UserLoginToken;
import com.liao.tdoor.model.TemplateShop;
import com.liao.tdoor.model.User;
import com.liao.tdoor.responseMsg.MyOrders;
import com.liao.tdoor.responseMsg.PostDynamicDetail;
import com.liao.tdoor.responseMsg.RespEntity;
import com.liao.tdoor.responseMsg.TemplateShopDetail;
import com.liao.tdoor.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 商城
 * @author 廖某某
 * @date 2019/2/24 8:45
 **/
@RestController
@RequestMapping("/shop")
public class TemplateShopController {
    @Autowired
    TemplateService tService;

    private List<TemplateShopDetail> detail=new ArrayList<TemplateShopDetail>();
    private TemplateShopDetail shopDetail=new TemplateShopDetail();
    private RespEntity respEntity=new RespEntity();
    @PassToken
    @RequestMapping("sendShopData")
    public List<TemplateShop> SendTemplateData(){
        List<TemplateShop> list=new ArrayList<TemplateShop>();
        list=tService.SendTemplateList();
        return list;
    }
    @PassToken
    @RequestMapping("sendSelectedData")
    public List<TemplateShop> SendSelectedData(){
        List<TemplateShop> list=new ArrayList<TemplateShop>();
        list=tService.SendTemplateSelected();
        return list;
    }
    @PassToken
    @RequestMapping("sendDetail")
    public TemplateShopDetail SendTemplateDetail(@RequestBody Map<String,Object> map){
        String user_id=(String)map.get("user_id");
        String template_id=(String)map.get("template_id");
        shopDetail=tService.SendTemplateDetail(user_id,template_id);
        return shopDetail;
    }
    @UserLoginToken
    @RequestMapping("collectT")
    public RespEntity CollectTemplate(@CurrentUser User user, @RequestBody Map<String,Object> map){
        String user_id=user.getId();
        String template_id=(String)map.get("template_id");
        respEntity=tService.TemplateCollect(user_id,template_id);
        return respEntity;
    }
    @UserLoginToken
    @RequestMapping("purchaseT")
    public RespEntity PurchaseTemplate(@CurrentUser User user,@RequestBody Map<String,Object>map){
        String user_id=user.getId();
        String template_id=(String)map.get("template_id");
        int price=(int)map.get("template_price");
        respEntity=tService.TemplateBuy(user_id,template_id,price);
        return respEntity;
    }

    @UserLoginToken
    @RequestMapping("showOrders")
    public List<MyOrders> ShowMyOrders(@CurrentUser User user){
        List<MyOrders> list=new ArrayList<MyOrders>();
        list=tService.MyOrders(user.getId());
        return list;
    }
    @UserLoginToken
    @RequestMapping("evaluateT")
    public RespEntity EvaluateTemplate(@CurrentUser User user,@RequestBody Map<String,Object> map){
        String content=(String)map.get("content");
        String score=(String)map.get("score");
        String template_id=(String)map.get("template_id");
        respEntity=tService.TemplateEvaluate(user.getId(),template_id,content,Integer.parseInt(score));
        return respEntity;
    }
    @PassToken
    @RequestMapping("evaluateList")
    public List<PostDynamicDetail> TemplateEvaluate(@RequestBody Map<String,Object> map){
        String templateId= (String) map.get("template_id");
        List<PostDynamicDetail> evaluateList=tService.SendTemplateEvaluate(templateId);
        return evaluateList;
    }
    @UserLoginToken
    @RequestMapping("sendTFavorites")
    public List<TemplateShop> SendMyTemplateMyFavorites(@CurrentUser User user){
        List<TemplateShop> tList=new ArrayList<TemplateShop>();
        tList=tService.MyFavorites(user.getId());
        return  tList;
    }
    @UserLoginToken
    @RequestMapping("deleteTCollect")
    public RespEntity DeleteCollectController(@CurrentUser User user ,@RequestBody Map<String,Object> map){
        String template_id= (String) map.get("dataId");
        return tService.DeleteCollectService(user.getId(),template_id);
    }
}
