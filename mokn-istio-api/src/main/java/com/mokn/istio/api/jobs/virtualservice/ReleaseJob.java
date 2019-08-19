package com.mokn.istio.api.jobs.virtualservice;

import com.mokn.istio.api.model.db.IstioRouteVersion;
import com.mokn.istio.api.model.db.IstioRouteVersionItem;
import com.mokn.istio.api.model.domain.DestinationRuleSubsetDomain;
import com.mokn.istio.api.model.em.ResourceEnum;
import com.mokn.istio.api.model.em.VersionStatusEnum;
import com.mokn.istio.api.model.k8s.deployment.Deployment;
import com.mokn.istio.api.model.k8s.virtualservice.VirtualService;
import com.mokn.istio.api.service.db.IstioRouteVersionService;
import com.mokn.istio.api.service.db.ResourceTemplateService;
import com.mokn.istio.api.service.k8s.DestinationRuleService;
import com.mokn.istio.api.service.k8s.VirtualServiceService;
import com.mokn.istio.api.service.k8s.DeploymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ReleaseJob {
    @Autowired
    private IstioRouteVersionService versionService;
    @Autowired
    private ResourceTemplateService resourceTemplateService;
    @Autowired
    private DestinationRuleService destinationRuleService;
    @Autowired
    private VirtualServiceService virtualServiceService;
    @Autowired
    private DeploymentService deploymentService;


    @Scheduled(cron = "0/1 * * * * ?")
    public void run(){
        IstioRouteVersion condition=new IstioRouteVersion();
        condition.setVersionStatus(VersionStatusEnum.RELEASE_ING.getValue());
        List<IstioRouteVersion> versions=versionService.listVersion(condition);
        for(IstioRouteVersion version:versions){
            if(version.getVersionStatus()!=VersionStatusEnum.RELEASE_ING.getValue()){
                continue;
            }

            // 取模板
            ResourceEnum resourceType=ResourceEnum.VIRTUALSERVICE_DEFAULT;

            // 验证所有明细是否可执行
            boolean isRun=true;
            for(IstioRouteVersionItem item:version.getItems()){
                VirtualService virtualService=virtualServiceService.getVirtualService(item.getNamespace(),item.getNameHost());
                if(virtualService==null || virtualService.getMetadata()==null || virtualService.getMetadata().getLabels()==null
                        || virtualService.getMetadata().getLabels().getIstioVersion()==null || virtualService.getMetadata().getLabels().getIstioVersionType()==null
                        || virtualService.getMetadata().getLabels().getIstioVersionType().equals("") || virtualService.getMetadata().getLabels().getIstioVersion().equals("")){
                    isRun=false;
                    break;
                }
                // 判断新版本Deployment是否成功创建
                Deployment deployment=deploymentService.getDeployment(item.getNamespace(),item.getNameHost()+"-"+item.getSubsetNew());
                if(deployment==null || deployment.getMetadata()==null
                        || deployment.getMetadata().getLabels()==null
                        || deployment.getMetadata().getLabels().getVersion()==null
                        || !deployment.getMetadata().getLabels().getVersion().equals(item.getSubsetNew())){
                    isRun=false;
                    break;
                }
            }

            if(isRun){
                String template=resourceTemplateService.getByResource(resourceType.getValue()).getTemplate();
                for(IstioRouteVersionItem item:version.getItems()){
                    VirtualService virtualService=virtualServiceService.getVirtualService(item.getNamespace(),item.getNameHost());
                    String applyJson=template.replace("{{.HOST}}",item.getNameHost())
                            .replace("{{.RESOURCE_VERSION}}",virtualService.getMetadata().getResourceVersion())
                            .replace("{{.ISTIO_VERSION}}",item.getSubsetNew())
                            .replace("{{.ISTIO_VERSION_TYPE}}",resourceType.getValue())
                            .replace("{{.SUBSET}}",item.getSubsetNew());


                    // 更改DestinationRule
                    List<DestinationRuleSubsetDomain> subsetDomains=new ArrayList<>();
                    DestinationRuleSubsetDomain domainNew=new DestinationRuleSubsetDomain();
                    domainNew.setVersion(item.getSubsetNew());
                    domainNew.setName(item.getSubsetNew());
                    subsetDomains.add(domainNew);
                    if(destinationRuleService.putSubsets(item.getNamespace(),item.getNameHost(),subsetDomains)){
                        // 更改VirtualService
                        boolean runFlag=virtualServiceService.applyVirtualService(item.getNamespace(),item.getNameHost(),applyJson.trim());
                    }
                }
                versionService.action(version.getSysno(),VersionStatusEnum.RELEASE_SUCCESS);
            }

        }
    }
}
