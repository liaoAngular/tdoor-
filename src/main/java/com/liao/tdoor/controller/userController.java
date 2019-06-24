package com.liao.tdoor.controller;

import com.liao.tdoor.annotation.CurrentUser;
import com.liao.tdoor.annotation.PassToken;
import com.liao.tdoor.annotation.UserLoginToken;
import com.liao.tdoor.model.User;
import com.liao.tdoor.responseMsg.PersonalEntity;
import com.liao.tdoor.responseMsg.RespCode;
import com.liao.tdoor.responseMsg.RespEntity;
import com.liao.tdoor.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Map;

/**
 * @author 廖某某
 * 用户控制层
 */
@RestController
public class userController {

    @Autowired
    UserService userService;
    private RespEntity respEntity=new RespEntity();
    private PersonalEntity pEntity=new PersonalEntity();

    @PassToken
    @RequestMapping("register")
    public RespEntity register(@RequestBody Map<String,Object> map){
        String e_mail=(String)map.get("email");
        String nickName=(String)map.get("nickName");
        String password=(String)map.get("password");
        String registerCode=(String)map.get("code");
        respEntity=userService.RegisterInfo(e_mail,nickName,password,registerCode);
        return respEntity;
    }
    @PassToken
    @RequestMapping("sendCode")
    public RespEntity sendPollCode(@RequestBody Map<String,Object> map){
        String email=(String)map.get("email");
        RespEntity respEntity=userService.sendCode(email);
        return respEntity;
    }
    @PassToken
    @RequestMapping("/login")
    public RespEntity testData(@RequestBody Map<String,Object> map){
        String email=(String)map.get("email");
        String password=(String)map.get("password");
        respEntity=userService.Login(email,password);
        return respEntity;
    }
    @UserLoginToken
    @RequestMapping("/getUserInfo")
    public RespEntity getMessage(@CurrentUser User user) throws Exception{
        respEntity=userService.SendUserInfo(user);
        return respEntity;
    }

    /**
     * 更改密码
     * @param user
     * @param map
     * @return
     */
    @UserLoginToken
    @RequestMapping("/changePassword")
    public RespEntity OperationPassword(@CurrentUser User user,@RequestBody Map<String,Object> map){
        String email=user.getEmail();
        String newPassword=(String)map.get("newPassword");
        String usedPassword=(String)map.get("usedPassword");
        respEntity=userService.ChangePassword(email,usedPassword,newPassword);
        return respEntity;
    }
    /**
     * 用户签到
     * @param user
     * @return
     */
    @UserLoginToken
    @RequestMapping("/signIn")
    public RespEntity SigningIn(@CurrentUser User user){
        respEntity=userService.SignIn(user.getId());
        return respEntity;
    }

    /**
     * 查看个人信息
     * @return
     */
    @PassToken
    @RequestMapping("/checkUser")
    public User SendPersonalInfo(@RequestBody Map<String,Object> map){
        String user_id=(String) map.get("user_id");
        User user1=new User();
        user1=userService.UserInfo(user_id);
        return user1;
    }

    @UserLoginToken
    @RequestMapping("/updateHeadImg")
    public RespEntity uploadHeadImg(@CurrentUser User user, @RequestParam("file") MultipartFile multipartFile){
        respEntity=userService.SettingUser(multipartFile,user.getId());
        return respEntity;
    }

    @UserLoginToken
    @RequestMapping("/updateUser")
    public RespEntity UpdateUserInfo(@CurrentUser User user,@RequestBody Map<String,Object> map){
        String nickName= (String)map.get("nickName");
        String profiles=(String)map.get("profiles");
        int sex=(Integer)map.get("sex");
        String address=(String)map.get("address");
        respEntity=userService.SettingUserInfo(nickName,profiles,sex,address,user.getId());
        return respEntity;
    }
    @PassToken
    @RequestMapping("findpwdCode")
    public RespEntity FindPsdSendCode(@RequestBody Map<String,Object> map){
        String email= (String) map.get("email");
        respEntity=userService.SendFindPasswordCode(email);
        return respEntity;
    }
    @PassToken
    @RequestMapping("findPassword")
    public RespEntity FindPsdOperation(@RequestBody Map<String,Object> map){
        String email= (String) map.get("email");
        String password= (String) map.get("password");
        String code= (String) map.get("code");
        respEntity=userService.FindPassword(email,code,password);
        return respEntity;
    }
    @UserLoginToken
    @RequestMapping("sponsorUser")
    public RespEntity SponsorUserController(@CurrentUser User user,@RequestBody Map<String,Object> map){
        String post_id= (String) map.get("dataId");
        respEntity=userService.SponsorUserService(post_id,user.getId());
        return respEntity;
    }
}
