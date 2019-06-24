package com.liao.tdoor.service;

import com.liao.tdoor.dao.CodeDao;
import com.liao.tdoor.dao.PostingDao;
import com.liao.tdoor.dao.UserDao;
import com.liao.tdoor.model.*;
import com.liao.tdoor.responseMsg.PersonalEntity;
import com.liao.tdoor.responseMsg.RespCode;
import com.liao.tdoor.responseMsg.RespEntity;
import com.liao.tdoor.util.DateUtils;
import com.liao.tdoor.util.RandomTools;
import com.liao.tdoor.util.SendEmailUtils;
import com.liao.tdoor.util.tokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 用户服务层
 *
 * @author 廖某某
 * @date 2019/02/17
 */
@Service
public class UserService {
    @Autowired
    UserDao userDao;
    @Autowired
    CodeDao codeDao;
    @Autowired
    SendEmailUtils sendEmailUtils;
    @Autowired
    PostingDao pDao;

    private RespEntity respEntity = new RespEntity();

    private User user = new User();

    private VerificationCode verificationCode = new VerificationCode();

    private PersonalEntity infoEntity = new PersonalEntity();

    /**
     * 发送验证码
     *
     * @param email
     * @return
     */
    public RespEntity sendCode(String email) {
        try {
            String code = RandomTools.randomCode();//产生随机的验证码
            User user = new User();
            user = userDao.isExistUser(email);
            if (user == null) {
                System.out.println("邮箱：" + email + "--验证码为:" + code);
                //检验数据库注册表中是否有注册信息
                verificationCode = codeDao.checkCode(email);
                //如果已经提交过注册，但未完成注册
                if (verificationCode != null) {
                    codeDao.changeCode(email, code);
                } else {
                    //新建-保存验证码信息到数据库
                    codeDao.saveCode(email, code, new Date());
                }
                //发送邮件开始 发送验证码
                sendEmailUtils.sendRegisterCode(email, code);

                respEntity = new RespEntity(RespCode.REGISTER_SEND);
            } else {
                respEntity = new RespEntity(RespCode.REGISTER_NOTS);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return respEntity;
    }

    /**
     * 注册信息提交
     *
     * @param email
     * @param nickName
     * @param password
     * @param registerCode
     * @return
     */
    public RespEntity RegisterInfo(String email, String nickName, String password, String registerCode) {
        verificationCode = codeDao.checkCode(email);
        if (verificationCode != null) {
            if (registerCode.equals(verificationCode.getCode())) {
                //时间校验--暂略
                User user = new User(email, password, nickName);
                userDao.addUser(user);
                //删除验证码信息
                codeDao.deleteCode(email);
                respEntity = new RespEntity(RespCode.REGISTER_SUCCESS);
            } else {
                respEntity = new RespEntity(RespCode.CODE_EXPIRED);
            }
        } else {
            respEntity = new RespEntity(RespCode.REGISTER_FAILED);
        }
        return respEntity;
    }

    /**
     * 登录验证
     *
     * @param email
     * @param password
     * @return
     */
    public RespEntity Login(String email, String password) {
        user = userDao.login(email, password);
        UserSign sign = new UserSign();
        sign = userDao.SignRecord(user.getId());
        String signTime="2015-04-20 11:22:45";
        SimpleDateFormat sdfd =new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
        Date signDate= null;
        int code = 0;
        if (sign == null) {
            //没有查询到签到记录
            code = -1;
            try {
                signDate = sdfd.parse(signTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            boolean flag = DateUtils.isToday(sign.getUpdate_time());
            signDate=sign.getUpdate_time();
            //检查当天是否签到
            if (flag) {
                //今天已经签到
                code = 1;
            } else {
                //今天未签到
                code = -1;
            }
        }
        String token = "";
        if (user != null) {
            token = tokenUtil.createJwtToken(email);
            respEntity = new RespEntity(RespCode.LOGIN_SUCCESS, token, user, code, signDate);
        } else {
            respEntity = new RespEntity(RespCode.LOGIN_FAILED);
        }
        return respEntity;
    }

    /**
     * 根据旧密码更改密码
     *
     * @param usedPassword
     * @return
     */
    public RespEntity ChangePassword(String email, String usedPassword, String newPassword) {
        user = userDao.login(email, usedPassword);
        if (user == null) {
            respEntity = new RespEntity(RespCode.PASSWORD_FAILED);
        } else {
            userDao.ChangePassword(email, newPassword);
            respEntity = new RespEntity(RespCode.SUCCESS);
        }
        return respEntity;
    }

    /**
     * 根据token返回用户信息到页面
     *
     * @param user
     * @return
     */
    public RespEntity SendUserInfo(User user) throws Exception {
        UserSign sign = new UserSign();
        sign = userDao.SignRecord(user.getId());
        int code = 0;

        respEntity = new RespEntity(code, user);
        return respEntity;
    }

    /**
     * 签到
     *
     * @param user_id
     * @return
     */
    public RespEntity SignIn(String user_id) {
        UserSign sign = new UserSign();
        sign = userDao.SignRecord(user_id);
        if (sign == null) {
            userDao.FirstSign(user_id, new Date());
            respEntity = new RespEntity(RespCode.SUCCESS);
        } else {
            userDao.SignIn(user_id, new Date());
            respEntity = new RespEntity(RespCode.SUCCESS);
        }
        userDao.AddScoreDao(user_id);
        return respEntity;
    }

    /**
     * 查看个人中心
     *
     * @param user_id
     * @return
     */
    public User UserInfo(String user_id) {
        user = userDao.QueryInfoById(user_id);
        return user;
    }

    /**
     * 更改用户头像
     *
     * @param multipartFile 头像文件
     * @return
     */
    public RespEntity SettingUser(MultipartFile multipartFile, String user_id) {
        if (multipartFile != null) {
            //获取文件名
            String fileName = multipartFile.getOriginalFilename();
            //获取文件后缀名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            //重新生成文件名
            fileName = user_id + suffixName;
            //指定本地文件夹存储图片
            String filePath = "C:\\work\\T-door\\webIMG\\head\\";
            try {
                //将图片保存到文件夹中
                multipartFile.transferTo(new File(filePath + fileName));
                String headImgUrl = filePath + fileName;
                userDao.UpdateUserHeadImg(headImgUrl, user_id);
                return new RespEntity(RespCode.SUCCESS);

            } catch (Exception e) {
                e.printStackTrace();
                return new RespEntity(RespCode.WARN);
            }
        } else {
            //如果没有上传图片，就设置默认图片
            String headImgUrl = "C:\\work\\T-door\\webIMG\\head\\pic_default_secret.png";
            userDao.UpdateUserHeadImg(headImgUrl, user_id);
            return new RespEntity(RespCode.WARN);
        }
    }

    /**
     * 更改用户信息
     *
     * @param nickName
     * @param profiles
     * @param address
     * @param sex
     * @return
     */
    public RespEntity SettingUserInfo(String nickName, String profiles, int sex, String address, String user_id) {
        userDao.UpdateUserInfo(nickName, profiles, sex, address, user_id);
        return new RespEntity(RespCode.SUCCESS);
    }

    /**
     * 找回密码
     * @param email 用户邮箱
     * @param code  验证码
     * @param password 新密码
     * @return -1表示操作失败，0表示操作成功
     */
    public RespEntity FindPassword(String email, String code,String password) {
        verificationCode = codeDao.checkCode(email);
        if(verificationCode!=null && code.equals(verificationCode.getCode())){
            userDao.ChangePassword(email,password);
            codeDao.deleteCode(email);
            respEntity=new RespEntity(RespCode.SUCCESS);
        }else {
            respEntity=new RespEntity(RespCode.WARN);
        }
        return respEntity;
    }

    /**
     * 发送找回密码验证码
     *
     * @param email 邮箱账号
     * @return -1代表没有找到该账号，0代表操作成功
     */
    public RespEntity SendFindPasswordCode(String email) {
        User user = new User();
        user = userDao.isExistUser(email);
        if (user == null) {
            respEntity = new RespEntity(RespCode.WARN);
        } else {
            String code = RandomTools.randomCode();//产生随机的验证码
            //检验数据库注册表中是否有注册信息
            verificationCode = codeDao.checkCode(email);
            //如果已经提交过找回密码，但未完成找回
            if (verificationCode != null) {
                codeDao.changeCode(email, code);
            } else {
                //新建-保存验证码信息到数据库
                codeDao.saveCode(email, code, new Date());
            }
            //发送邮件开始 发送验证码
            sendEmailUtils.sendFindPsdCode(email, code, user.getNickname());
            respEntity = new RespEntity(RespCode.SUCCESS);
        }
        return respEntity;
    }

    /**
     * 支持原创作者10积分--服务层
     * @param post_id 动态id
     * @param user_id 用户id
     * @return
     */
    public RespEntity SponsorUserService(String post_id,String user_id){
        Posting posting=new Posting();
        pDao.QueryByPostId(post_id);
        user=userDao.QueryInfoById(user_id);
        if(user.getTcoin()>=10){
            userDao.SponsorUserDao(posting.getUser_id());
            userDao.ReduceScoreDao(user_id);
            respEntity=new RespEntity(RespCode.SUCCESS);
        }else {
            respEntity=new RespEntity(RespCode.NOT_ENOUGH);
        }
        return respEntity;
    }
}
