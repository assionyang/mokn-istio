package com.mokn.istio.api.common;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Java Token工具类
 */
@Component
public class JwtUtil {
    private String secret="mokn-istio-7afjlahj13";

    public Long expiration=86400000L;

    /**
     * 创建token
     * @param claims claims
     * @return token
     */
    public String createdToken(Map<String,Object> claims){
        Date expiration=new Date(new Date().getTime()+this.expiration);
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256,this.secret)
                .compact();
    }

    /**
     * 创建token 带过期时间
     * @param claims claims
     * @param expirationTime 过期时间
     * @return token
     */
    public String createdToken(Map<String,Object> claims,Date expirationTime){
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(expirationTime)
                .signWith(SignatureAlgorithm.HS256,this.secret)
                .compact();
    }



    /**
     * 验证token
     * @param token token
     * @return 有效true,无效false
     */
    public Boolean validateToken(String token){
        return !this.isTokenExpired(token);
    }


    /**
     * 从Token中获取Claims
     * @param token token
     * @return Claims
     */
    public Claims getClaimsFromToken(String token){
        Claims claims;
        try{
            claims=Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        }catch (Exception err){
            claims=null;
        }
        return claims;
    }

    /**
     * 获取过Token过期时间
     * @param token token
     * @return 过期时间
     */
    public Date getExpirationDateFromToken(String token){
        Date expiration;
        try{
            final Claims claims=getClaimsFromToken(token);
            expiration=claims.getExpiration();
        }catch (Exception err){
            expiration=null;
        }
        return expiration;
    }

    /**
     * 从Token中获取自定义值
     * @param token token
     * @param key 值键
     * @return 值
     */
    public Object getValueFromToken(String token,String key){
        Object result;
        try{
            final Claims claims=getClaimsFromToken(token);
            result=claims.get(key);
        }catch (Exception err){
            result=null;
        }
        return result;
    }

    /**
     * 验证token是否过期
     * @param token token
     * @return 过期：true,未过期：false
     */
    private Boolean isTokenExpired(String token){
        final Date expiration=getExpirationDateFromToken(token);
        if(expiration==null){
            return true;
        }
        return expiration.before(new Date());
    }





















}
