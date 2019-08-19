package com.mokn.istio.api.service.k8s;

import com.alibaba.fastjson.JSONObject;
import com.mokn.istio.api.common.K8sHttp;
import com.mokn.istio.api.model.domain.VirtualServiceDomain;
import com.mokn.istio.api.model.k8s.virtualservice.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VirtualServiceServiceImpl implements VirtualServiceService {

    private final static Logger logger= LoggerFactory.getLogger(VirtualServiceServiceImpl.class);

    public final static String VIRTUALSERVICES_BY_NAMESPACE="/apis/networking.istio.io/v1alpha3/namespaces/{namespaces}/virtualservices"; // 获取指定命名空间下的VirtualService
    public final static String VIRTUALSERVICE_BY_NAMESPACE_NAME="/apis/networking.istio.io/v1alpha3/namespaces/{namespaces}/virtualservices/{name}"; // 获取指定命名空间下的指定名字的VirtualService
    @Autowired
    private K8sHttp k8sHttp;

    @Override
    public VirtualService getVirtualService(String namespaceName, String name) {
        try{
            String result=k8sHttp.get(VIRTUALSERVICE_BY_NAMESPACE_NAME.replace("{namespaces}",namespaceName).replace("{name}",name));
            return JSONObject.parseObject(result,VirtualService.class);
        }catch (Exception err){
            logger.error(err.getMessage(),err);
            return null;
        }
    }

    @Override
    public VirtualServiceList listVirtualService(String namespaceName) {
        try{
            String result=k8sHttp.get(VIRTUALSERVICES_BY_NAMESPACE.replace("{namespaces}",namespaceName));
            return JSONObject.parseObject(result,VirtualServiceList.class);
        }catch (Exception err){
            logger.error(err.getMessage(),err);
            return null;
        }
    }

    @Override
    public VirtualServiceDomain getVirtualServiceDomain(String namespaceName, String name) {
        VirtualService virtualService=this.getVirtualService(namespaceName,name);
        if(virtualService==null || virtualService.getSpec()==null){
            return null;
        }
        String versionType=null;
        String version=null;
        if(virtualService.getMetadata().getLabels()!=null && virtualService.getMetadata().getLabels().getIstioVersionType()!=null){
            versionType=virtualService.getMetadata().getLabels().getIstioVersionType();
        }
        if(virtualService.getMetadata().getLabels()!=null && virtualService.getMetadata().getLabels().getIstioVersion()!=null){
            version=virtualService.getMetadata().getLabels().getIstioVersion();
        }
        VirtualServiceDomain domain=new VirtualServiceDomain(virtualService.getMetadata().getName(),
                versionType,
                version,
                virtualService.getMetadata().getNamespace(),
                virtualService.getMetadata().getUid(),
                virtualService.getMetadata().getResourceVersion());
        domain.setCreationTimestamp(virtualService.getMetadata().getCreationTimestamp());
        if(virtualService.getSpec()!=null && virtualService.getSpec().getHttp()!=null && virtualService.getSpec().getHttp().size()>0 &&virtualService.getSpec().getHttp().get(0).getFault()!=null){
            domain.setOpenFault(true);
            if(virtualService.getSpec().getHttp().get(0).getFault().getDelay()!=null){
                domain.setOpenFaultDelay(true);
                domain.setFaultDelayFixedDelay(virtualService.getSpec().getHttp().get(0).getFault().getDelay().getFixedDelay());
                domain.setFaultDelayPercent(virtualService.getSpec().getHttp().get(0).getFault().getDelay().getPercent());
            }else{
                domain.setOpenFaultDelay(false);
            }
            if(virtualService.getSpec().getHttp().get(0).getFault().getAbort()!=null){
                domain.setOpenFaultAbort(true);
                domain.setFaultAbortHttpStatus(virtualService.getSpec().getHttp().get(0).getFault().getAbort().getHttpStatus());
                domain.setFaultAbortPercent(virtualService.getSpec().getHttp().get(0).getFault().getAbort().getPercent());
            }else{
                domain.setOpenFaultAbort(false);
            }

        }else{
            domain.setOpenFault(false);
            domain.setOpenFaultDelay(false);
            domain.setOpenFaultAbort(false);
        }
        if(virtualService.getMetadata().getLabels()!=null
                && virtualService.getMetadata().getLabels().getIstioVersionType()!=null
                && !virtualService.getMetadata().getLabels().getIstioVersionType().equals("")){
            domain.setIstioVersionType(virtualService.getMetadata().getLabels().getIstioVersionType());
            domain.setIstioVersion(virtualService.getMetadata().getLabels().getIstioVersion());
        }else{
            domain.setIstioVersionType("");
            domain.setIstioVersion("");
        }

        return domain;
    }

    @Override
    public List<VirtualServiceDomain> listVirtualServiceDomain(String namespaceName) {
        VirtualServiceList virtualServiceList=this.listVirtualService(namespaceName);
        if(virtualServiceList==null){
            return null;
        }
        List<VirtualServiceDomain> domains=new ArrayList<>();
        for(VirtualServiceList_Item item:virtualServiceList.getItems()){
            String versionType=null;
            String version=null;
            if(item.getMetadata().getLabels()!=null && item.getMetadata().getLabels().getIstioVersionType()!=null){
                versionType=item.getMetadata().getLabels().getIstioVersionType();
            }
            if(item.getMetadata().getLabels()!=null && item.getMetadata().getLabels().getIstioVersion()!=null){
                version=item.getMetadata().getLabels().getIstioVersion();
            }
            VirtualServiceDomain domain=new VirtualServiceDomain(item.getMetadata().getName(),
                    versionType,
                    version,
                    item.getMetadata().getNamespace(),
                    item.getMetadata().getUid(),
                    item.getMetadata().getResourceVersion());
            domain.setCreationTimestamp(item.getMetadata().getCreationTimestamp());
            if(item.getSpec()!=null && item.getSpec().getHttp()!=null && item.getSpec().getHttp().size()>0 && item.getSpec().getHttp().get(0).getFault()!=null){
                domain.setOpenFault(true);
                if(item.getSpec().getHttp().get(0).getFault().getDelay()!=null){
                    domain.setOpenFaultDelay(true);
                    domain.setFaultDelayFixedDelay(item.getSpec().getHttp().get(0).getFault().getDelay().getFixedDelay());
                    domain.setFaultDelayPercent(item.getSpec().getHttp().get(0).getFault().getDelay().getPercent());
                }else{
                    domain.setOpenFaultDelay(false);
                }
                if(item.getSpec().getHttp().get(0).getFault().getAbort()!=null){
                    domain.setOpenFaultAbort(true);
                    domain.setFaultAbortHttpStatus(item.getSpec().getHttp().get(0).getFault().getAbort().getHttpStatus());
                    domain.setFaultAbortPercent(item.getSpec().getHttp().get(0).getFault().getAbort().getPercent());
                }else{
                    domain.setOpenFaultAbort(false);
                }

            }else{
                domain.setOpenFault(false);
                domain.setOpenFaultDelay(false);
                domain.setOpenFaultAbort(false);
            }
            if(item.getMetadata().getLabels()!=null
                    && item.getMetadata().getLabels().getIstioVersionType()!=null
                    && !item.getMetadata().getLabels().getIstioVersionType().equals("")){
                domain.setIstioVersionType(item.getMetadata().getLabels().getIstioVersionType());
                domain.setIstioVersion(item.getMetadata().getLabels().getIstioVersion());
            }else{
                domain.setIstioVersionType("");
                domain.setIstioVersion("");
            }
            domains.add(domain);
        }
        return domains;
    }


    @Override
    public boolean applyVirtualService(String namespaceName, String name, String body) {
        try{
            String result=k8sHttp.put(VIRTUALSERVICE_BY_NAMESPACE_NAME.replace("{namespaces}",namespaceName).replace("{name}",name),body);
            JSONObject obj=JSONObject.parseObject(result);
            if(obj.containsKey("kind") && obj.getString("kind").equals("VirtualService")){
                return Boolean.TRUE;
            }
        }catch (Exception err){
            logger.error(err.getMessage(),err);
        }
        return Boolean.FALSE;
    }
    @Override
    public boolean delete(String namespace,String name){
        try{
            String result=k8sHttp.delete(VIRTUALSERVICE_BY_NAMESPACE_NAME.replace("{namespaces}",namespace).replace("{name}",name));
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
    public boolean put(String namespace,String name,String json){
        try{
            String result=k8sHttp.put(VIRTUALSERVICE_BY_NAMESPACE_NAME.replace("{namespaces}",namespace).replace("{name}",name),json);
            JSONObject obj=JSONObject.parseObject(result);
            if(obj.containsKey("kind") && obj.getString("kind").equals("VirtualService")){
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
            String result=k8sHttp.post(VIRTUALSERVICE_BY_NAMESPACE_NAME.replace("{namespaces}",namespace).replace("{name}",name),json);
            JSONObject obj=JSONObject.parseObject(result);
            if(obj.containsKey("kind") && obj.getString("kind").equals("VirtualService")){
                return Boolean.TRUE;
            }
        }catch (Exception err){
            logger.error(err.getMessage(),err);
        }
        return Boolean.FALSE;
    }
    @Override
    public boolean putFault(String namespace,String name,VirtualServiceDomain domain){
        VirtualService virtualService=this.getVirtualService(namespace,name);
        if(virtualService==null){
            return Boolean.FALSE;
        }
        if(virtualService.getMetadata().getLabels()==null
                || virtualService.getMetadata().getLabels().getIstioVersionType()==null
                || !virtualService.getMetadata().getLabels().getIstioVersionType().equals("VirtualService_Default")){
            return Boolean.FALSE;
        }
        if(domain.getOpenFault()){
            VirtualService_Spec_Http_Fault fault=new VirtualService_Spec_Http_Fault();
            if(domain.getOpenFaultDelay()){
                VirtualService_Spec_Http_Fault_Delay delay=new VirtualService_Spec_Http_Fault_Delay();
                delay.setFixedDelay(domain.getFaultDelayFixedDelay());
                delay.setPercent(domain.getFaultDelayPercent());
                fault.setDelay(delay);
            }else{
                fault.setDelay(null);
            }
            if(domain.getOpenFaultAbort()){
                VirtualService_Spec_Http_Fault_Abort abort=new VirtualService_Spec_Http_Fault_Abort();
                abort.setHttpStatus(domain.getFaultAbortHttpStatus());
                abort.setPercent(domain.getFaultAbortPercent());
                fault.setAbort(abort);
            }else{
                fault.setAbort(null);
            }
            virtualService.getSpec().getHttp().get(0).setFault(fault);
            if(this.put(namespace,name,JSONObject.toJSONString(virtualService))){
                return Boolean.TRUE;
            }
        }else{
            virtualService.getSpec().getHttp().get(0).setFault(null);
            if(this.put(namespace,name,JSONObject.toJSONString(virtualService))){
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }
}
