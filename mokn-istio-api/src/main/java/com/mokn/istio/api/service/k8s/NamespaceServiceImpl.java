package com.mokn.istio.api.service.k8s;

import com.alibaba.fastjson.JSONObject;
import com.mokn.istio.api.common.K8sHttp;
import com.mokn.istio.api.model.domain.NamespaceDomain;
import com.mokn.istio.api.model.k8s.namespace.Namespace;
import com.mokn.istio.api.model.k8s.namespace.NamespaceList;
import com.mokn.istio.api.model.k8s.namespace.NamespaceList_Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NamespaceServiceImpl implements NamespaceService {

    private final static Logger logger= LoggerFactory.getLogger(NamespaceServiceImpl.class);

    public final static String NAMESPACES="/api/v1/namespaces"; // 获取所有命名空间
    public final static String NAMESPACE_BY_NAME="/api/v1/namespaces/{name}"; // 获取所有命名空间

    @Autowired
    private K8sHttp k8sHttp;


    @Override
    public NamespaceList listNamespace() {
        try{
            String result= k8sHttp.get(NAMESPACES);
            return JSONObject.parseObject(result,NamespaceList.class);
        }catch (Exception err){
            logger.error(err.getMessage(),err);
            return null;
        }
    }

    @Override
    public Namespace getNamespace(String namespaceName) {
        try{
            String result=k8sHttp.get(NAMESPACE_BY_NAME.replace("{name}",namespaceName));
            return JSONObject.parseObject(result,Namespace.class);
        }catch (Exception err){
            logger.error(err.getMessage(),err);
            return null;
        }
    }

    @Override
    public List<NamespaceDomain> listNamespaceDomain() {
        NamespaceList namespaceList=this.listNamespace();
        if(namespaceList==null){
            return null;
        }
        List<NamespaceDomain> domains=new ArrayList<>();
        for(NamespaceList_Item item:namespaceList.getItems()){
            NamespaceDomain domain=new NamespaceDomain(item.getMetadata().getName(),item.getMetadata().getUid());
            if(!domain.getName().equals("kube-system") && !domain.getName().equals("istio-system") && !domain.getName().equals("kube-public")){

            }
            domain.setCreationTimestamp(item.getMetadata().getCreationTimestamp());
            if(item.getMetadata().getLabels()!=null){
                domain.setIstioInjection(item.getMetadata().getLabels().getIstioInjection());
            }
            domains.add(domain);
        }
        return domains;
    }

    @Override
    public NamespaceDomain getNamespaceDomain(String namespaceName) {
        Namespace namespace=this.getNamespace(namespaceName);
        if(namespace==null || namespaceName.equals("kube-system") || namespaceName.equals("istio-system") || namespaceName.equals("kube-public")){
            return null;
        }
        NamespaceDomain domain=new NamespaceDomain(namespace.getMetadata().getName(),namespace.getMetadata().getUid());
        if(namespace.getMetadata().getLabels()!=null){
            domain.setIstioInjection(namespace.getMetadata().getLabels().getIstioInjection());
        }
        domain.setCreationTimestamp(namespace.getMetadata().getCreationTimestamp());
        return domain;
    }
}
