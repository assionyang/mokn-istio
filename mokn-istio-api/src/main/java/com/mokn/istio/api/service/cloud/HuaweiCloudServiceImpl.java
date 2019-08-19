package com.mokn.istio.api.service.cloud;

import com.alibaba.fastjson.JSONObject;
import com.mokn.istio.api.model.huaweicloud.token.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;

@Service
public class HuaweiCloudServiceImpl implements HuaweiCloudService {

    private static String TOKEN;
    private static Date TOKEN_TIME;

    @Value("${huaweicloud.token.url}")
    private String TOKEN_URL;

    @Value("${huaweicloud.token.project}")
    private String TOKEN_PROJECT;

    @Value("${huaweicloud.token.username}")
    private String TOKEN_USERNAME;

    @Value("${huaweicloud.token.password}")
    private String TOKEN_PASSWORD;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public String getToken() {

        // 如果本地存在有效TOKEN直接返回
        if(TOKEN!= null && !TOKEN.equals("") && TOKEN_TIME!=null && (new Date().getTime()-TOKEN_TIME.getTime()<28800000)){
            return TOKEN;
        }

        // 创建Token
        Domain domain=new Domain(TOKEN_USERNAME);
        User user=new User(TOKEN_USERNAME,TOKEN_PASSWORD,domain);
        Password password=new Password(user);
        Identity identity=new Identity("password",password);
        Project project=new Project(TOKEN_PROJECT);
        Scope scope=new Scope(project);
        Auth auth=new Auth(identity,scope);
        Body body=new Body(auth);

        MultiValueMap<String,Object> headers=new LinkedMultiValueMap<>();
        headers.add("Accept","application/json");
        headers.add("Content-Type","application/json");
        HttpEntity request=new HttpEntity(JSONObject.toJSONString(body),headers);
        ResponseEntity<String> result=restTemplate.postForEntity(TOKEN_URL,request,String.class);
        List<String> tokens=result.getHeaders().get("X-Subject-Token");
        if(tokens==null || tokens.size()==0){
            return null;
        }

        TOKEN=tokens.get(0).trim();
        TOKEN_TIME=new Date();
        return TOKEN;
    }
}
