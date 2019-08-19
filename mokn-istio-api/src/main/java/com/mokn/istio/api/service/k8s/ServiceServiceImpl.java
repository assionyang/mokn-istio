package com.mokn.istio.api.service.k8s;

import com.alibaba.fastjson.JSONObject;
import com.mokn.istio.api.common.K8sHttp;
import com.mokn.istio.api.model.domain.DeploymentDomain;
import com.mokn.istio.api.model.domain.ServiceDomain;
import com.mokn.istio.api.model.k8s.deployment.Deployment;
import com.mokn.istio.api.model.k8s.deployment.DeploymentList;
import com.mokn.istio.api.model.k8s.deployment.DeploymentList_Item;
import com.mokn.istio.api.model.k8s.service.Service;
import com.mokn.istio.api.model.k8s.service.ServiceList;
import com.mokn.istio.api.model.k8s.service.ServiceList_Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

@org.springframework.stereotype.Service
public class ServiceServiceImpl implements ServiceService {

    private final static Logger logger= LoggerFactory.getLogger(DeploymentServiceImpl.class);

    public final static String SERVICE_BY_NAMESAPCES_NAME="/api/v1/namespaces/{namespace}/services/{name}";
    public final static String SERVICES="/api/v1/services";
    public final static String SERVICES_BY_NAMESPACES="/api/v1/namespaces/{namespace}/services";

    @Autowired
    private K8sHttp k8sHttp;

    @Override
    public ServiceList listService() {
        try{
            String result=k8sHttp.get(SERVICES);
            ServiceList serviceList= JSONObject.parseObject(result,ServiceList.class);
            return serviceList;
        }catch (Exception err){
            logger.error(err.getMessage(),err);
            return null;
        }
    }

    @Override
    public ServiceList listService(String namespace) {
        try{
            String result=k8sHttp.get(SERVICES_BY_NAMESPACES.replace("{namespace}",namespace));
            ServiceList serviceList= JSONObject.parseObject(result,ServiceList.class);
            return serviceList;
        }catch (Exception err){
            logger.error(err.getMessage(),err);
            return null;
        }
    }

    @Override
    public Service getService(String namespace, String name) {
        try{
            String result=k8sHttp.get(SERVICE_BY_NAMESAPCES_NAME.replace("{namespace}",namespace).replace("{name}",name));
            Service service= JSONObject.parseObject(result,Service.class);
            return service;
        }catch (Exception err){
            logger.error(err.getMessage(),err);
            return null;
        }
    }

    @Override
    public boolean deleteService(String namespace, String name) {
        try{
            String result=k8sHttp.delete(SERVICE_BY_NAMESAPCES_NAME.replace("{namespace}",namespace).replace("{name}",name));
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
    public List<ServiceDomain> listServiceDomain() {
        ServiceList serviceList=this.listService();
        if(serviceList==null){
            return null;
        }
        List<ServiceDomain> serviceDomains=new ArrayList<>();
        for(ServiceList_Item service:serviceList.getItems()){
            String app=null;
            if(service.getMetadata().getLabels()!=null){
                app=service.getMetadata().getLabels().getApp();
            }
            ServiceDomain domain=new ServiceDomain(service.getMetadata().getName(),
                    service.getMetadata().getNamespace(),
                    service.getMetadata().getUid(),
                    service.getMetadata().getResourceVersion(),
                    service.getMetadata().getCreationTimestamp(),
                    app);
            domain.setCreationTimestamp(service.getMetadata().getCreationTimestamp());
            serviceDomains.add(domain);
        }
        return serviceDomains;
    }

    @Override
    public List<ServiceDomain> listServiceDomain(String namespace) {
        ServiceList serviceList=this.listService(namespace);
        if(serviceList==null){
            return null;
        }
        List<ServiceDomain> serviceDomains=new ArrayList<>();
        for(ServiceList_Item service:serviceList.getItems()){
            String app=null;
            if(service.getMetadata().getLabels()!=null){
                app=service.getMetadata().getLabels().getApp();
            }
            ServiceDomain domain=new ServiceDomain(service.getMetadata().getName(),
                    service.getMetadata().getNamespace(),
                    service.getMetadata().getUid(),
                    service.getMetadata().getResourceVersion(),
                    service.getMetadata().getCreationTimestamp(),
                    app);
            domain.setCreationTimestamp(service.getMetadata().getCreationTimestamp());
            serviceDomains.add(domain);
        }
        return serviceDomains;
    }

    @Override
    public  ServiceDomain getServiceDomain(String namespace,String name){
        Service service=this.getService(namespace,name);
        if(service==null){
            return null;
        }
        String app=null;
        if(service.getMetadata().getLabels()!=null){
            app=service.getMetadata().getLabels().getApp();
        }
        ServiceDomain domain=new ServiceDomain(service.getMetadata().getName(),
                service.getMetadata().getNamespace(),
                service.getMetadata().getUid(),
                service.getMetadata().getResourceVersion(),
                service.getMetadata().getCreationTimestamp(),
                app);
        domain.setCreationTimestamp(service.getMetadata().getCreationTimestamp());
        return domain;
    }

    @Override
    public boolean patch(String namespace,String name,String body){
        try{
            String result=k8sHttp.patch(SERVICE_BY_NAMESAPCES_NAME.replace("{namespace}",namespace).replace("{name}",name),body);
            return true;
        }catch (Exception err){
            logger.error(err.getMessage(),err);
        }
        return false;
    }

}
