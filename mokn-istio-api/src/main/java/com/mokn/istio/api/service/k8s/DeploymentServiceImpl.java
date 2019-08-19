package com.mokn.istio.api.service.k8s;

import com.alibaba.fastjson.JSONObject;
import com.mokn.istio.api.common.K8sHttp;
import com.mokn.istio.api.model.domain.DeploymentDomain;
import com.mokn.istio.api.model.k8s.deployment.Deployment;
import com.mokn.istio.api.model.k8s.deployment.DeploymentList;
import com.mokn.istio.api.model.k8s.deployment.DeploymentList_Item;
import net.sf.jsqlparser.expression.operators.relational.RegExpMatchOperatorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeploymentServiceImpl implements DeploymentService {

    private final static Logger logger= LoggerFactory.getLogger(DeploymentServiceImpl.class);

    public final static String DEPLOYMENT_BY_NAMESAPCES_NAME="/apis/apps/v1/namespaces/{namespace}/deployments/{name}";
    public final static String DEPLOYMENTS="/apis/apps/v1/deployments";
    public final static String DEPLOYMENTS_BY_NAMESPACES="/apis/apps/v1/namespaces/{namespace}/deployments";

    @Autowired
    private K8sHttp k8sHttp;


    @Override
    public Deployment getDeployment(String namespaceName, String name) {
        try{
            String result=k8sHttp.get(DEPLOYMENT_BY_NAMESAPCES_NAME.replace("{namespace}",namespaceName).replace("{name}",name));
            Deployment deployment= JSONObject.parseObject(result,Deployment.class);
            return deployment;
        }catch (Exception err){
            logger.error(err.getMessage(),err);
            return null;
        }
    }

    @Override
    public DeploymentList listDeployment() {
        try{
            String result=k8sHttp.get(DEPLOYMENTS);
            DeploymentList deploymentList=JSONObject.parseObject(result,DeploymentList.class);
            return deploymentList;
        }catch (Exception err){
            logger.error(err.getMessage(),err);
            return null;
        }
    }

    @Override
    public DeploymentList listDeployment(String namespace) {
        try{
            String result=k8sHttp.get(DEPLOYMENTS_BY_NAMESPACES.replace("{namespace}",namespace));
            DeploymentList deploymentList=JSONObject.parseObject(result,DeploymentList.class);
            return deploymentList;
        }catch (Exception err){
            logger.error(err.getMessage(),err);
            return null;
        }
    }

    @Override
    public List<DeploymentDomain> listDeploymentDomain() {
        DeploymentList deploymentList=this.listDeployment();
        if(deploymentList==null){
            return null;
        }
        List<DeploymentDomain> deploymentDomains=new ArrayList<>();
        for(DeploymentList_Item deployment:deploymentList.getItems()){
            String app=null;
            String version=null;
            if(deployment.getMetadata().getLabels()!=null){
                app=deployment.getMetadata().getLabels().getApp();
                version=deployment.getMetadata().getLabels().getVersion();
            }
            DeploymentDomain domain=new DeploymentDomain(deployment.getMetadata().getName(),
                    deployment.getMetadata().getNamespace(),
                    deployment.getMetadata().getUid(),
                    deployment.getMetadata().getResourceVersion(),
                    app,
                    version);
            domain.setReplicas(deployment.getStatus().getReplicas()==null?0:deployment.getStatus().getReplicas());
            domain.setAvailableReplicas(deployment.getStatus().getAvailableReplicas()==null?0:deployment.getStatus().getAvailableReplicas());
            deploymentDomains.add(domain);
        }
        return deploymentDomains;
    }

    @Override
    public List<DeploymentDomain> listDeploymentDomain(String namespace) {
        DeploymentList deploymentList=this.listDeployment(namespace);
        if(deploymentList==null){
            return null;
        }
        List<DeploymentDomain> deploymentDomains=new ArrayList<>();
        for(DeploymentList_Item deployment:deploymentList.getItems()){
            String app=null;
            String version=null;
            if(deployment.getMetadata().getLabels()!=null){
                app=deployment.getMetadata().getLabels().getApp();
                version=deployment.getMetadata().getLabels().getVersion();
            }
            DeploymentDomain domain=new DeploymentDomain(deployment.getMetadata().getName(),
                    deployment.getMetadata().getNamespace(),
                    deployment.getMetadata().getUid(),
                    deployment.getMetadata().getResourceVersion(),
                    app,
                    version);
            domain.setReplicas(deployment.getStatus().getReplicas()==null?0:deployment.getStatus().getReplicas());
            domain.setAvailableReplicas(deployment.getStatus().getAvailableReplicas()==null?0:deployment.getStatus().getAvailableReplicas());
            domain.setCreationTimestamp(deployment.getMetadata().getCreationTimestamp());
            deploymentDomains.add(domain);
        }
        return deploymentDomains;
    }

    @Override
    public boolean deleteDeployment(String namesapce, String name) {
        try{
            String result=k8sHttp.delete(DEPLOYMENT_BY_NAMESAPCES_NAME.replace("{namespace}",namesapce).replace("{name}",name));
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
    public DeploymentDomain getDeploymentDomain(String namespaceName, String name) {
        Deployment deployment=this.getDeployment(namespaceName,name);
        if(deployment==null || deployment.getMetadata()==null || deployment.getMetadata().getName()==null){
            return null;
        }
        String app=null;
        String version=null;
        if(deployment.getMetadata().getLabels()!=null){
            app=deployment.getMetadata().getLabels().getApp();
            version=deployment.getMetadata().getLabels().getVersion();
        }
        DeploymentDomain domain=new DeploymentDomain(deployment.getMetadata().getName(),
                deployment.getMetadata().getNamespace(),
                deployment.getMetadata().getUid(),
                deployment.getMetadata().getResourceVersion(),
                app,
                version);
        domain.setReplicas(deployment.getStatus().getReplicas()==null?0:deployment.getStatus().getReplicas());
        domain.setAvailableReplicas(deployment.getStatus().getAvailableReplicas()==null?0:deployment.getStatus().getAvailableReplicas());
        domain.setCreationTimestamp(deployment.getMetadata().getCreationTimestamp());
        return domain;
    }


}
