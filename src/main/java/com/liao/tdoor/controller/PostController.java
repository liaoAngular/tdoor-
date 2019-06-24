package com.liao.tdoor.controller;
import com.liao.tdoor.annotation.CurrentUser;
import com.liao.tdoor.annotation.PassToken;
import com.liao.tdoor.annotation.UserLoginToken;
import com.liao.tdoor.model.Posting;
import com.liao.tdoor.model.User;
import com.liao.tdoor.responseMsg.PostDynamicDetail;
import com.liao.tdoor.responseMsg.RespEntity;
import com.liao.tdoor.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 动态控制层
 * @author 廖某某
 * @date 2019/02/19
 */
@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    PostService postService;

    private RespEntity respEntity=new RespEntity();

    @UserLoginToken
    @RequestMapping("postDynamic")
    public RespEntity SaveDynamic(@CurrentUser User user, @RequestBody Map<String,Object> map){
        String title=(String)map.get("introduce"); //内容介绍
        String url=(String)map.get("urlAddress"); //跳转链接
        int category=(Integer) map.get("topics"); //所属主题

        respEntity=postService.PostDynamic(user.getId(),title,url,category,new Date());
        return respEntity;
    }

    @PassToken
    @RequestMapping("sendIndexList")
    public List<PostDynamicDetail> SendIndexList(@RequestBody Map<String,Object> map){
        int value= (int) map.get("value");
        List<PostDynamicDetail> dataList=new ArrayList<PostDynamicDetail>();
        dataList=postService.SendPostData(value);
        return dataList;
    }

    @PassToken
    @RequestMapping("sendPostEvaluate")
    public List<PostDynamicDetail> SendDynamicEvaluate(@RequestBody Map<String,Object> map){
        List<PostDynamicDetail> dataList=new ArrayList<PostDynamicDetail>();
        String post_id=(String)map.get("post_id");
        dataList=postService.PostEvaluateList(post_id);
        return dataList;
    }
    @PassToken
    @RequestMapping("sendPostDetail")
    public PostDynamicDetail SendPostDetail(@RequestBody Map<String,Object> map){
        String post_id=(String)map.get("post_id");
        PostDynamicDetail postDynamicDetail=postService.PostDetail(post_id);
        return postDynamicDetail;
    }
    @UserLoginToken
    @RequestMapping("commentPost")
    public RespEntity GetPostEvaluate(@CurrentUser User user,@RequestBody Map<String,Object> map){
        String content=(String)map.get("content");
        String post_id=(String)map.get("post_id");
        respEntity=postService.CommentPost(user.getId(),post_id,content);
        return respEntity;
    }
    @PassToken
    @RequestMapping("clickPost")
    public RespEntity AddPostClickNumber(@RequestBody Map<String,Object> map){
        String post_id= (String) map.get("dataId");
        respEntity=postService.ClickPost(post_id);
        return respEntity;
    }
    @PassToken
    @RequestMapping("checkMyPost")
    public List<Posting> SendUserPost(@RequestBody Map<String,Object> map){
        List<Posting> postings=new ArrayList<Posting>();
        String user_id= (String) map.get("user_id");
        postings=postService.MyPosting(user_id);
        return postings;
    }
    @UserLoginToken
    @RequestMapping("sendFavorites")
    public List<Posting> SendMyPostFavorites(@CurrentUser User user){
        List<Posting> postings=new ArrayList<Posting>();
        postings=postService.SendMyFavorites(user.getId());
        return postings;
    }

    @UserLoginToken
    @RequestMapping("collectPost")
    public RespEntity AddPostToFavorites(@CurrentUser User user,@RequestBody Map<String,Object> map){
        String post_id= (String) map.get("dataId");
        respEntity=postService.CollectPost(user.getId(),post_id);
        return respEntity;
    }
    @UserLoginToken
    @RequestMapping("isCollect")
    public RespEntity CheckCollect(@CurrentUser User user,@RequestBody Map<String,Object> map){
        String post_id= (String) map.get("dataId");
        return postService.CheckIsCollectPost(user.getId(),post_id);
    }
    @UserLoginToken
    @RequestMapping("deleteCollect")
    public RespEntity DeleteCollect(@CurrentUser User user,@RequestBody Map<String,Object> map){
        String post_id= (String) map.get("dataId");
        return postService.DeleteMyCollect(user.getId(),post_id);
    }
    @UserLoginToken
    @RequestMapping("deletePosting")
    public RespEntity DeletePostController(@CurrentUser User user,@RequestBody Map<String,Object> map){
        String post_id= (String) map.get("dataId");
        return postService.DeleteMyPosting(user.getId(),post_id);
    }
}
