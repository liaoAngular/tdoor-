package com.liao.tdoor.service;

import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import com.liao.tdoor.dao.PostEvaluateDao;
import com.liao.tdoor.dao.PostingDao;
import com.liao.tdoor.dao.UserDao;
import com.liao.tdoor.model.*;
import com.liao.tdoor.responseMsg.PostDynamicDetail;
import com.liao.tdoor.responseMsg.RespCode;
import com.liao.tdoor.responseMsg.RespEntity;
import com.liao.tdoor.util.HtmlContentUtil;
import com.liao.tdoor.util.PhantomTools;
import com.liao.tdoor.util.ShotsPicUtil;
import com.liao.tdoor.util.TestWsdlConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 动态服务层
 * @author 廖某某
 * @date 2019/02/19
 */
@Service
public class PostService {

    @Autowired
    PostingDao postingDao;

    @Autowired
    PostEvaluateDao postEvaluateDao;

    @Autowired
    UserDao  userDao;

    private Posting posting=new Posting();

    private RespEntity respEntity=new RespEntity();

    private PostDynamicDetail detail=new PostDynamicDetail();

    private User user=new User();

    private HtmlContentUtil contentUtil=new HtmlContentUtil();

    private TestWsdlConnection wsdlConnection=new TestWsdlConnection();

    private ShotsPicUtil shots;
    /**
     * 发布动态
     * @param user_id
     * @param post_title
     * @param post_content
     * @param category_id
     * @param post_date
     * @return
     */
    public RespEntity PostDynamic(String user_id,
                                  String post_title,
                                  String post_content,
                                  int category_id,
                                  Date post_date) {
        int status;
        status=wsdlConnection.TestConnection(post_content);
        if(status==200){
            /*System.setProperty("java.awt.headless", "false");*/
            //获取链接标题
            String webTitle=contentUtil.GetHtmlTitle(post_content);
            //获取缩略图路径
            String time=System.currentTimeMillis()+"";
            String imgUrl="C:\\work\\T-door\\webIMG\\post\\"+time+".png";
            //生成缩略图
            PhantomTools tools=new PhantomTools(1,"800px*600px");
            byte[] s= new byte[0];
            try {
                s = tools.getByteImg(post_content);
                FileOutputStream out=new FileOutputStream(imgUrl);
                out.write(s);
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(user_id!=null){
                System.out.println("插入数据到数据库");
                postingDao.SavePostInfo(user_id,post_title,post_content,category_id,post_date,imgUrl,webTitle);
                userDao.SponsorUserDao(user_id);
                respEntity=new RespEntity(RespCode.SUCCESS);
            }else {
                respEntity=new RespEntity(RespCode.WARN);
            }
        }else {
            respEntity=new RespEntity(RespCode.LINK_FAILED);
        }
        return respEntity;
    }
    /**
     * 根据user_id查询发布者基础信息到首页
     * 发送用户动态到首页
     * @return
     */
    public List<PostDynamicDetail> SendPostData(int value){
        List<PostDynamicDetail> dataList= new ArrayList<PostDynamicDetail>();
        List<Posting> postList=new ArrayList<Posting>();
        if (value==1){
            postList=postingDao.SendPosting();
        }else {
            postList=postingDao.SendHotPosting();
        }
        for(Posting p:postList){
            user=userDao.QueryInfoById(p.getUser_id());
            detail= new PostDynamicDetail(user.getId(),
                    user.getNickname(),user.getHeadUrl(),p);
            dataList.add(detail);
        }
        return dataList;
    }
    /**
     * 用户评论动态
     * @param post_id
     * @param user_id
     * @param content
     * @return
     */
    public RespEntity PostEvaluate(String post_id,String user_id,String content){
        if(user_id!=null && post_id!=null && content!=null){
            postingDao.PostByEvaluate(post_id);
            postEvaluateDao.PostEvaluateArea(post_id,user_id,content,new Date());
            respEntity=new RespEntity(RespCode.SUCCESS);
        }else {
            respEntity=new RespEntity(RespCode.WARN);
        }
        return respEntity;
    }

    /**
     * 根据动态ID查询动态详细信息
     * @param post_id
     * @return
     */
    public PostDynamicDetail PostDetail(String post_id){
        posting=postingDao.QueryByPostId(post_id);
        user=userDao.QueryInfoById(posting.getUser_id());
        PostDynamicDetail postDynamicDetail=new PostDynamicDetail(user.getId(),user.getNickname(),user.getHeadUrl(),posting);
        return postDynamicDetail;
    }
    /**
     * 根据动态ID查询所有评论
     * 包括用户基础信息
     * @param post_id
     * @return
     */
    public List<PostDynamicDetail> PostEvaluateList(String post_id){
        List<PostDynamicDetail> dataList=new ArrayList<PostDynamicDetail>();
        List<PostEvaluate> evaluates=new ArrayList<PostEvaluate>();
        evaluates=postEvaluateDao.SendPostEvaluateList(post_id);
        for(PostEvaluate data:evaluates){
            user=userDao.QueryInfoById(data.getUser_id());
            detail=new PostDynamicDetail(user.getId(),
                    user.getNickname(),user.getHeadUrl(),data);
            dataList.add(detail);
        }
        return dataList;
    }

    /**
     * 加入收藏夹
     * @param user_id
     * @param post_id
     * @return
     */
    public RespEntity CollectPost(String user_id,String post_id){
        PostCollect collect=new PostCollect();
        collect=postingDao.IsCollect(user_id,post_id);
        if(user_id!=null && post_id!=null){
            if(collect==null){
                //插入收藏记录
                postingDao.PostCollect(user_id,post_id);
                postingDao.PostByLike(post_id);
            }else {
                //删除收藏记录
                postingDao.CollectCancel(user_id,post_id);
                postingDao.LikeByCancel(post_id);
            }
            respEntity=new RespEntity(RespCode.SUCCESS);
        }else {
            respEntity=new RespEntity(RespCode.WARN);
        }
        return respEntity;
    }

    /**
     * 我的收藏夹
     * @param user_id
     * @return
     */
    public List<Posting> SendMyFavorites(String user_id){
        List<Posting> list=new ArrayList<Posting>();
        List<PostCollect> collects=new ArrayList<PostCollect>();
        if(user_id!=null){
            collects=postingDao.MyPostFavorites(user_id);
            for(PostCollect collect:collects){
                posting=postingDao.QueryByPostId(collect.getPost_id());
                list.add(posting);
            }
        }
        return list;
    }

    /**
     * 动态点赞（取消）操作
     * @param user_id
     * @param post_id
     * @return
     */
    public RespEntity PostGreat(String user_id,String post_id){
        PostGreat great=new PostGreat();
        great=postingDao.IsGreat(user_id,post_id);
        if(user_id!=null && post_id!=null){
            if(great==null){
                postingDao.PostByLike(post_id);
                //插入记录
                postingDao.GreatPosting(user_id,post_id);
                respEntity=new RespEntity(RespCode.SUCCESS);
            }else {
                postingDao.LikeByCancel(post_id);
                //删除记录
                postingDao.GreatByCancel(user_id,post_id);
                respEntity=new RespEntity(RespCode.SUCCESS);
            }
        }else {
            respEntity =new RespEntity(RespCode.WARN);
        }
        return respEntity;
    }

    /**
     * 提交评论数据
     * @param user_id
     * @param post_id
     * @param content
     * @return
     */
    public RespEntity CommentPost(String user_id,String post_id,String content){
        if(post_id==null || content==null){
            respEntity=new RespEntity(RespCode.WARN);
        }else {
            postingDao.PostByEvaluate(post_id);
            postingDao.AddPostEvaluate(content,user_id,post_id,new Date());
            respEntity=new RespEntity(RespCode.SUCCESS);
        }
        return respEntity;
    }
    /**
     * 用户查看动态，动态活跃数+1
     * @param post_id
     * @return
     */
    public RespEntity ClickPost(String post_id){
        postingDao.PostByClick(post_id);
        respEntity=new RespEntity(RespCode.SUCCESS);
        return respEntity;
    }

    /**
     * 我的动态
     * @param user_id
     * @return
     */
    public List<Posting> MyPosting(String user_id){
        List<Posting> postings=new ArrayList<Posting>();
        postings=postingDao.QueryPostByUserId(user_id);
        return postings;
    }

    /**
     * 查询是否已经被收藏
     * @param user_id
     * @param post_id
     * @return
     */
    public RespEntity CheckIsCollectPost(String user_id,String post_id){
        PostCollect collect=new PostCollect();
        collect=postingDao.IsCollect(user_id,post_id);
        if (collect==null){
            return new RespEntity(RespCode.WARN);
        }else {
            return new RespEntity(RespCode.SUCCESS);
        }
    }
    /**
     * 删除收藏的动态
     * @param user_id
     * @param post_id
     * @return
     */
    public RespEntity DeleteMyCollect(String user_id,String post_id){
        postingDao.CollectCancel(user_id,post_id);
        return new RespEntity(RespCode.SUCCESS);
    }
    /**
     * 删除发布的动态
     * @param user_id
     * @param post_id
     * @return
     */
    public RespEntity DeleteMyPosting(String user_id,String post_id){
        postingDao.DeletePostDao(user_id,post_id);
        return new RespEntity(RespCode.SUCCESS);
    }
}
