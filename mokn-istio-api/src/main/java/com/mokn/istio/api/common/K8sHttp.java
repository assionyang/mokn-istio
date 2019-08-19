package com.mokn.istio.api.common;

import com.alibaba.fastjson.JSONObject;
import com.mokn.istio.api.service.cloud.HuaweiCloudService;
import jdk.nashorn.internal.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class K8sHttp {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private HuaweiCloudService huaweiCloudService;
    @Value("${k8s.api.url}")
    private String K8S_API_URL;

    private HttpHeaders getHeaders(){
        HttpHeaders headers=new HttpHeaders();
        headers.add("Authorization","Bearer "+huaweiCloudService.getToken());
        return headers;
    }

    /**
     * GET 请求
     * @param api
     * @return
     */
    public String get(String api){
        ResponseEntity<String> responseEntity=restTemplate.exchange(
                K8S_API_URL+api,
                HttpMethod.GET,
                new HttpEntity<String>(getHeaders()),
                String.class
        );
        return responseEntity.getBody();
    }

    /**
     * GET 请求带参数
     * @param api
     * @param params
     * @return
     */
    public String get(String api, Map<String,Object> params){
        ResponseEntity<String> responseEntity=restTemplate.exchange(
                K8S_API_URL+api,
                HttpMethod.GET,
                new HttpEntity<String>(getHeaders()),
                String.class,
                params
        );
        return responseEntity.getBody();
    }

    /**
     * POST 请求
     * @param api
     * @param requestBody
     * @return
     */
    public String post(String api,String requestBody){
        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.setBearerAuth(huaweiCloudService.getToken());
        HttpEntity<String> request=new HttpEntity<String>(requestBody,headers);
        ResponseEntity<String> responseEntity=restTemplate.postForEntity(K8S_API_URL+api,request,String.class);
        return responseEntity.getBody();
    }

    /**
     * PUT 请求
     * @param api
     * @param requestBody
     * @return
     */
    public String put(String api,String requestBody){
        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.setBearerAuth(huaweiCloudService.getToken());
        HttpEntity<String> request=new HttpEntity<String>(requestBody,headers);
        ResponseEntity<String> responseEntity=restTemplate.exchange(
          K8S_API_URL+api,
          HttpMethod.PUT,
          request, String.class
        );
        return responseEntity.getBody();
    }

    /**
     * DELETE 请求
     * @param api
     * @return
     */
    public String delete(String api){
        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.setBearerAuth(huaweiCloudService.getToken());
        HttpEntity<String> request=new HttpEntity<String>(null,headers);
        ResponseEntity<String> responseEntity=restTemplate.exchange(
                K8S_API_URL+api,
                HttpMethod.DELETE,
                request, String.class
        );
        return responseEntity.getBody();
    }

    /**
     * PATCH 请求
     * @param api
     * @param body
     * @return
     */
    public String patch(String api,String body){
        HttpHeaders headers=new HttpHeaders();
        headers.add("Content-Type","application/strategic-merge-patch+json");
        headers.setBearerAuth(huaweiCloudService.getToken());
        HttpEntity<String> request=new HttpEntity<String>(body,headers);
        ResponseEntity<String> responseEntity=restTemplate.exchange(
                K8S_API_URL+api,
                HttpMethod.PATCH,
                request, String.class
        );
        return responseEntity.getBody();
    }
}
