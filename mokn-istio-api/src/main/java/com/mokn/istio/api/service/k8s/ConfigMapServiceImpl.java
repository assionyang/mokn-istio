package com.mokn.istio.api.service.k8s;

import com.alibaba.fastjson.JSONObject;
import com.mokn.istio.api.common.K8sHttp;
import com.mokn.istio.api.model.domain.ConfigMapDomain;
import com.mokn.istio.api.model.k8s.configmap.ConfigMap;
import com.mokn.istio.api.model.k8s.configmap.ConfigMapList;
import com.mokn.istio.api.model.k8s.configmap.ConfigMapList_Item;
import com.mokn.istio.api.model.k8s.pod.Pod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ConfigMapServiceImpl implements ConfigMapService {

    private final static Logger logger= LoggerFactory.getLogger(ConfigMapServiceImpl.class);

    public final static String CONFIGMAP_BY_NAMESAPCES_NAME="/api/v1/namespaces/{namespace}/configmaps/{name}";
    public final static String CONFIGMAP_BY_NAMESPACES="/api/v1/namespaces/{namespace}/configmaps";

    @Autowired
    private K8sHttp k8sHttp;

    @Override
    public ConfigMap get(String namespaceName, String name) {
        try{
            String result=k8sHttp.get(CONFIGMAP_BY_NAMESAPCES_NAME.replace("{namespace}",namespaceName).replace("{name}",name));
            return JSONObject.parseObject(result, ConfigMap.class);
        }catch (Exception err){
            logger.error(err.getMessage(),err);
            return null;
        }
    }

    @Override
    public ConfigMapDomain getDomain(String namespaceName, String name) {
        ConfigMap configMap=this.get(namespaceName,name);
        if(configMap==null || !configMap.getKind().equals("ConfigMap")){
            return null;
        }
        ConfigMapDomain domain=new ConfigMapDomain();
        domain.setName(configMap.getMetadata().getName());
        domain.setNamespace(configMap.getMetadata().getNamespace());
        domain.setResourceVersion(configMap.getMetadata().getResourceVersion());
        domain.setUid(configMap.getMetadata().getUid());
        domain.setCreatedAt(configMap.getMetadata().getCreationTimestamp());
        domain.setEnable(configMap.getMetadata().getEnable());
        domain.setData(configMap.getData());
        return domain;
    }

    @Override
    public ConfigMapList list(String namespace) {
        try{
            String result=k8sHttp.get(CONFIGMAP_BY_NAMESPACES.replace("{namespace}",namespace));
            return JSONObject.parseObject(result, ConfigMapList.class);
        }catch (Exception err){
            logger.error(err.getMessage(),err);
            return null;
        }
    }

    @Override
    public List<ConfigMapDomain> listDomain(String namespace) {
        ConfigMapList configMapList=this.list(namespace);
        if(configMapList==null || !configMapList.getKind().equals("ConfigMapList")){
            return null;
        }
        List<ConfigMapDomain> domains=new ArrayList<>();
        for(ConfigMapList_Item configMap:configMapList.getItems()){
            ConfigMapDomain domain=new ConfigMapDomain();
            domain.setName(configMap.getMetadata().getName());
            domain.setNamespace(configMap.getMetadata().getNamespace());
            domain.setResourceVersion(configMap.getMetadata().getResourceVersion());
            domain.setUid(configMap.getMetadata().getUid());
            domain.setCreatedAt(configMap.getMetadata().getCreationTimestamp());
            domain.setEnable(configMap.getMetadata().getEnable());
            domain.setData(configMap.getData());
            domains.add(domain);
        }

        return domains;
    }

    @Override
    public boolean delete(String namesapce, String name) {
        try{
            String api=CONFIGMAP_BY_NAMESAPCES_NAME.replace("{namespace}",namesapce).replace("{name}",name);
            String result=k8sHttp.delete(api);
            return Boolean.TRUE;
        }catch (Exception err){
            logger.error(err.getMessage(),err);
        }
        return Boolean.FALSE;
    }

    @Override
    public boolean post(String namespace, String json) {
        try{
            String result=k8sHttp.post(CONFIGMAP_BY_NAMESPACES.replace("{namespace}",namespace),json);
            JSONObject obj=JSONObject.parseObject(result);
            if(obj.containsKey("kind") && obj.getString("kind").equals("ConfigMap")){
                return Boolean.TRUE;
            }
        }catch (Exception err){
            logger.error(err.getMessage(),err);
        }
        return Boolean.FALSE;
    }

    @Override
    public boolean put(String namespace, String name, String json) {
        try{
            String result=k8sHttp.put(CONFIGMAP_BY_NAMESAPCES_NAME.replace("{namespace}",namespace).replace("{name}",name),json);
            JSONObject obj=JSONObject.parseObject(result);
            if(obj.containsKey("kind") && obj.getString("kind").equals("ConfigMap")){
                return Boolean.TRUE;
            }
        }catch (Exception err){
            logger.error(err.getMessage(),err);
        }
        return Boolean.FALSE;
    }
}
