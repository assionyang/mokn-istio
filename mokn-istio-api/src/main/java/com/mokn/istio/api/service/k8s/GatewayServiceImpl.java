package com.mokn.istio.api.service.k8s;

import com.alibaba.fastjson.JSONObject;
import com.mokn.istio.api.common.K8sHttp;
import com.mokn.istio.api.model.domain.GatewayDomain;
import com.mokn.istio.api.model.k8s.gateway.Gateway;
import com.mokn.istio.api.model.k8s.gateway.GatewayList;
import com.mokn.istio.api.model.k8s.gateway.GatewayList_Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GatewayServiceImpl implements GatewayService {

    private final static Logger logger= LoggerFactory.getLogger(GatewayServiceImpl.class);

    public final static String GATEWAY_BY_NAMESAPCES_NAME="/apis/networking.istio.io/v1alpha3/namespaces/{namespace}/gateways/{name}";
    public final static String GATEWAY_BY_NAMESPACES="/apis/networking.istio.io/v1alpha3/namespaces/{namespace}/gateways";

    @Autowired
    private K8sHttp k8sHttp;

    @Override
    public Gateway get(String namespaceName, String name) {
        try{
            String result=k8sHttp.get(GATEWAY_BY_NAMESAPCES_NAME.replace("{namespace}",namespaceName).replace("{name}",name));
            return JSONObject.parseObject(result, Gateway.class);
        }catch (Exception err){
            logger.error(err.getMessage(),err);
            return null;
        }
    }

    @Override
    public GatewayList list(String namespace) {
        try{
            String result=k8sHttp.get(GATEWAY_BY_NAMESPACES.replace("{namespace}",namespace));
            return JSONObject.parseObject(result, GatewayList.class);
        }catch (Exception err){
            logger.error(err.getMessage(),err);
            return null;
        }
    }

    @Override
    public GatewayDomain getDomain(String namespaceName, String name) {
        Gateway gateway=this.get(namespaceName,name);
        if(gateway==null || gateway.getSpec()==null){
            return null;
        }

        GatewayDomain domain=new GatewayDomain();
        domain.setName(gateway.getMetadata().getName());
        domain.setNamespace(gateway.getMetadata().getNamespace());
        domain.setResourceVersion(gateway.getMetadata().getResourceVersion());
        domain.setUid(gateway.getMetadata().getUid());
        domain.setCreationTimestamp(gateway.getMetadata().getCreationTimestamp());
        return domain;
    }





    @Override
    public List<GatewayDomain> listDomain(String namespace) {
        GatewayList gatewayList=this.list(namespace);
        if(gatewayList==null){
            return null;
        }

        List<GatewayDomain> domains=new ArrayList<>();
        for(GatewayList_Item gateway:gatewayList.getItems()){
            GatewayDomain domain=new GatewayDomain();
            domain.setName(gateway.getMetadata().getName());
            domain.setNamespace(gateway.getMetadata().getNamespace());
            domain.setResourceVersion(gateway.getMetadata().getResourceVersion());
            domain.setUid(gateway.getMetadata().getUid());
            domain.setCreationTimestamp(gateway.getMetadata().getCreationTimestamp());
            domains.add(domain);
        }
        return domains;
    }

    @Override
    public boolean delete(String namesapce, String name) {
        try{
            String result=k8sHttp.delete(GATEWAY_BY_NAMESAPCES_NAME.replace("{namespace}",namesapce).replace("{name}",name));
            JSONObject obj=JSONObject.parseObject(result);
            if(obj.containsKey("status") && obj.getString("status").equals("Success")){
                return Boolean.TRUE;
            }
        }catch (Exception err){
            logger.error(err.getMessage(),err);
        }
        return Boolean.FALSE;
    }

    @Override
    public boolean post(String namespace,String name,String json){
        try{
            String result=k8sHttp.post(GATEWAY_BY_NAMESAPCES_NAME.replace("{namespace}",namespace).replace("{name}",name),json);
            JSONObject obj=JSONObject.parseObject(result);
            if(obj.containsKey("kind") && obj.getString("kind").equals("Gateway")){
                return Boolean.TRUE;
            }
        }catch (Exception err){
            logger.error(err.getMessage(),err);
        }
        return Boolean.FALSE;
    }
}
