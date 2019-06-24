package com.liao.tdoor.util;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liao.tdoor.model.Timestamp;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.net.MalformedURLException;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 生成token的工具类
 */
public class tokenUtil {
    /**
     * 签名秘钥(唯一秘钥，可以用密码做为秘钥)
     */
    public static final String SECRET="admin";

    private static Map<String,Object> MAP=new HashMap<String, Object>();

    /**
     * 生成token
     * @param username
     * @return
     */
    public static String createJwtToken(String username){
        String issuer="tdoor";
        String subject="liao";
        long ttlMillis=36000000;//10个小时后过期
        return createJwtToken(username,issuer,subject,ttlMillis);
    }

    /**
     * 生成token
     * @param username 用户名
     * @param issuer 改JWT的签发者，是否使用可以选
     * @param subject 改JWT所面向的用户，是否使用可选
     * @param ttlMillis 签发时间（有效时间，过期会报错）
     * @return token string
     */
    public static String createJwtToken(String username,String issuer,String subject,long ttlMillis){
        //签名算法，将token进行签名
        SignatureAlgorithm signatureAlgorithm=SignatureAlgorithm.HS256;
        //生成签发时间
        long nowMills=System.currentTimeMillis();
        Date now=new Date(nowMills);
        //通过秘钥签名JWT
        //创建token
        JwtBuilder builder=Jwts.builder().setId(username)
                                .setIssuedAt(now)
                                .signWith(signatureAlgorithm,getKey());
        //添加过期时间
        if(ttlMillis>=0){
            long expMillis=nowMills+ttlMillis;
            Date exp=new Date(expMillis);
            builder.setExpiration(exp);
        }
        //列入计算时间
        String token = builder.compact();
        String sign=token.split("\\.")[2];
        MAP=DateUtils.SetTimestamp(sign,username);
        System.out.println("1sign="+token);
        return token;
    }
    //验证和读取JWT的示例方法
    public static Claims parseJWT(String jwt){
        Claims claims=Jwts.parser()
                        .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET))
                        .parseClaimsJws(jwt).getBody();
        return claims;
    }

    public static String Vaild(String token) throws JsonProcessingException {
        String sign=null;
        try{
            //获得签名
            sign=token.split("\\.")[2];
            System.out.println("签名为："+sign);
            //获得载荷
            Map<String,Object> jwtClaims=Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET))
                    .parseClaimsJws(token).getBody();
            ObjectMapper mapper=new ObjectMapper();
            String payload=mapper.writeValueAsString(jwtClaims);
            //重新生成签名
            JwtBuilder builder=Jwts.builder()
                    .setPayload(payload)
                    .signWith(SignatureAlgorithm.HS256,getKey());
            //新签名
            String newsSign=builder.compact().split("\\.")[2];
            //新签名和旧签名对比
            if(!sign.equals(newsSign)){
                return null;
            }
        }catch (ExpiredJwtException e){
            System.out.println("token过期");
            String newToken=RefreshToken(sign);
            return newToken;
        }catch (SignatureException | MalformedJwtException e){
            return null;
        }
        System.out.println("token验证正确");
        return token;
    }
    //刷新令牌
    public static String RefreshToken(String token) throws JsonProcessingException{
        //获取token的刷新时间
        String sign=token.split("\\.")[2];
        String refreshTime="60000000";
        Timestamp timestamp =(Timestamp) MAP.get(sign);

        System.out.println("123qwe:"+timestamp.getUser_email());
        //
        String new_token = null;
        if(String.valueOf(System.currentTimeMillis()).compareTo(refreshTime)<0){
            System.out.println("重新生成token");
            new_token=createJwtToken("liao180@vip.qq.com");
        }else {
            System.out.println("自动刷新时间已经过期");
        }
        return new_token;
    }
    //生成秘钥
    private static Key getKey(){
        //HS256加密
        SignatureAlgorithm signatureAlgorithm=SignatureAlgorithm.HS256;
        //秘钥
        byte[] apiKeySecretByte=DatatypeConverter.parseBase64Binary(SECRET);
        Key signKey=new SecretKeySpec(apiKeySecretByte,signatureAlgorithm.getJcaName());
        return signKey;
    }
}
