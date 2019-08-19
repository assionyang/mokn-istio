package com.mokn.istio.api.service.k8s;

import com.alibaba.fastjson.JSONObject;
import com.mokn.istio.api.common.K8sHttp;
import com.mokn.istio.api.model.domain.PodDomain;
import com.mokn.istio.api.model.k8s.pod.Pod;
import com.mokn.istio.api.model.k8s.pod.PodList;
import com.mokn.istio.api.model.k8s.pod.PodList_Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PodServiceImpl implements PodService {

    private final static Logger logger= LoggerFactory.getLogger(PodServiceImpl.class);

    public final static String POD_BY_NAMESAPCES_NAME="/api/v1/namespaces/{namespace}/pods/{name}";
    public final static String PODS="/api/v1/namespaces/{namespace}/pods";
    public final static String PODS_BY_NAMESPACES="/api/v1/namespaces/{namespace}/pods";

    @Autowired
    private K8sHttp k8sHttp;

    @Override
    public Pod get(String namespaceName, String name) {
        try{
            String result=k8sHttp.get(POD_BY_NAMESAPCES_NAME.replace("{namespaces}",namespaceName).replace("{name}",name));
            return JSONObject.parseObject(result, Pod.class);
        }catch (Exception err){
            logger.error(err.getMessage(),err);
            return null;
        }
    }
    @Override
    public PodList list() {
        try{
            String result=k8sHttp.get(PODS);
            return JSONObject.parseObject(result, PodList.class);
        }catch (Exception err){
            logger.error(err.getMessage(),err);
            return null;
        }
    }
    @Override
    public PodList list(String namespace) {
        try{
            String result=k8sHttp.get(PODS_BY_NAMESPACES.replace("{namespace}",namespace));
            return JSONObject.parseObject(result, PodList.class);
        }catch (Exception err){
            logger.error(err.getMessage(),err);
            return null;
        }
    }

    @Override
    public PodDomain getDomain(String namespaceName, String name) {
        Pod pod=this.get(namespaceName,name);
        if(pod==null){
            return null;
        }


        PodDomain domain=new PodDomain();
        if(pod.getMetadata().getLabels()!=null){
            domain.setApp(pod.getMetadata().getLabels().getApp());
            domain.setVersion(pod.getMetadata().getLabels().getVersion());
        }
        if(pod.getStatus()!=null){
            domain.setPhase(pod.getStatus().getPhase());
        }
        domain.setName(pod.getMetadata().getName());
        domain.setGenerateName(pod.getMetadata().getGenerateName());
        domain.setNamespace(pod.getMetadata().getNamespace());
        domain.setResourceVersion(pod.getMetadata().getResourceVersion());
        domain.setCreationTimestamp(pod.getMetadata().getCreationTimestamp());
        return domain;
    }

    @Override
    public List<PodDomain> listDomain() {
        List<PodDomain> domains=new ArrayList<>();
        PodList podList=this.list();
        if(podList==null){
            return null;
        }

        for(PodList_Item pod:podList.getItems()){
            PodDomain domain=new PodDomain();
            if(pod.getMetadata().getLabels()!=null){
                domain.setApp(pod.getMetadata().getLabels().getApp());
                domain.setVersion(pod.getMetadata().getLabels().getVersion());
            }
            if(pod.getStatus()!=null){
                domain.setPhase(pod.getStatus().getPhase());
            }
            domain.setName(pod.getMetadata().getName());
            domain.setGenerateName(pod.getMetadata().getGenerateName());
            domain.setNamespace(pod.getMetadata().getNamespace());
            domain.setResourceVersion(pod.getMetadata().getResourceVersion());
            domain.setCreationTimestamp(pod.getMetadata().getCreationTimestamp());
            domains.add(domain);
        }

        return domains;
    }

    @Override
    public List<PodDomain> listDomain(String namespace) {
        List<PodDomain> domains=new ArrayList<>();
        PodList podList=this.list(namespace);
        if(podList==null){
            return null;
        }

        for(PodList_Item pod:podList.getItems()){
            PodDomain domain=new PodDomain();
            if(pod.getMetadata().getLabels()!=null){
                domain.setApp(pod.getMetadata().getLabels().getApp());
                domain.setVersion(pod.getMetadata().getLabels().getVersion());
            }
            if(pod.getStatus()!=null){
                domain.setPhase(pod.getStatus().getPhase());
            }
            domain.setName(pod.getMetadata().getName());
            domain.setGenerateName(pod.getMetadata().getGenerateName());
            domain.setNamespace(pod.getMetadata().getNamespace());
            domain.setResourceVersion(pod.getMetadata().getResourceVersion());
            domain.setCreationTimestamp(pod.getMetadata().getCreationTimestamp());
            domains.add(domain);
        }

        return domains;
    }

    @Override
    public boolean delete(String namesapce, String name) {
        try{
            String result=k8sHttp.delete(POD_BY_NAMESAPCES_NAME.replace("{namespace}",namesapce).replace("{name}",name));
            return Boolean.TRUE;
        }catch (Exception err){
            logger.error(err.getMessage(),err);
        }
        return Boolean.FALSE;
    }
}
