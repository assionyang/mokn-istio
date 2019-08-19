package com.mokn.istio.api.service.k8s;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.mokn.istio.api.common.K8sHttp;
import com.mokn.istio.api.model.domain.DestinationRuleDomain;
import com.mokn.istio.api.model.domain.DestinationRuleSubsetDomain;
import com.mokn.istio.api.model.k8s.destinationrule.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DestinationRuleServiceImpl implements DestinationRuleService {

    private final static Logger logger= LoggerFactory.getLogger(DestinationRuleServiceImpl.class);

    public final static String DESTINATIONRULE_BY_NAMESAPCES_NAME="/apis/networking.istio.io/v1alpha3/namespaces/{namespace}/destinationrules/{name}";
    public final static String DESTINATIONRULE_BY_NAMESPACES="/apis/networking.istio.io/v1alpha3/namespaces/{namespace}/destinationrules";

    @Autowired
    private K8sHttp k8sHttp;

    @Override
    public DestinationRule get(String namespace, String name) {
        try{
            String result=k8sHttp.get(DESTINATIONRULE_BY_NAMESAPCES_NAME.replace("{namespace}",namespace).replace("{name}",name));
            return JSONObject.parseObject(result, DestinationRule.class);
        }catch (Exception err){
            logger.error(err.getMessage(),err);
            return null;
        }
    }

    @Override
    public DestinationRuleList list(String namespace) {
        try{
            String result=k8sHttp.get(DESTINATIONRULE_BY_NAMESPACES.replace("{namespace}",namespace));
            return JSONObject.parseObject(result, DestinationRuleList.class);
        }catch (Exception err){
            logger.error(err.getMessage(),err);
            return null;
        }
    }

    @Override
    public boolean post(String namespace,String name,String json){
        try{
            String result=k8sHttp.post(DESTINATIONRULE_BY_NAMESAPCES_NAME.replace("{namespace}",namespace).replace("{name}",name),json);
            JSONObject obj=JSONObject.parseObject(result);
            if(obj.containsKey("kind") && obj.getString("kind").equals("DestinationRule")){
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
            String result=k8sHttp.put(DESTINATIONRULE_BY_NAMESAPCES_NAME.replace("{namespace}",namespace).replace("{name}",name),json);
            JSONObject obj=JSONObject.parseObject(result);
            if(obj.containsKey("kind") && obj.getString("kind").equals("DestinationRule")){
                return Boolean.TRUE;
            }
        }catch (Exception err){
            logger.error(err.getMessage(),err);
        }
        return Boolean.FALSE;
    }

    @Override
    public boolean delete(String namespace, String name) {
        try{
            String result=k8sHttp.delete(DESTINATIONRULE_BY_NAMESAPCES_NAME.replace("{namespace}",namespace).replace("{name}",name));
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
    public boolean putTrafficPolicy(String namespace, String name, DestinationRuleDomain domain) {
        DestinationRule destinationRule=this.get(namespace,name);
        if(destinationRule==null){
            return Boolean.FALSE;
        }

        if(!domain.getOpenFuse()){
            destinationRule.getSpec().setTrafficPolicy(null);
            return this.put(namespace,name,JSONObject.toJSONString(destinationRule).trim());
        }else{
            DestinationRule_Spec_TrafficPolicy trafficPolicy=new DestinationRule_Spec_TrafficPolicy();

            DestinationRule_Spec_TrafficPolicy_ConnectionPoll connectionPoll=new DestinationRule_Spec_TrafficPolicy_ConnectionPoll();
            DestinationRule_Spec_TrafficPolicy_ConnectionPoll_Http http=new DestinationRule_Spec_TrafficPolicy_ConnectionPoll_Http();
            http.setHttp1MaxPendingRequests(domain.getHttp1MaxPendingRequests());
            http.setMaxRequestsPerConnection(domain.getMaxRequestsPerConnection());
            DestinationRule_Spec_TrafficPolicy_ConnectionPoll_Tcp tcp=new DestinationRule_Spec_TrafficPolicy_ConnectionPoll_Tcp();
            tcp.setMaxConnections(domain.getMaxConnections());
            connectionPoll.setHttp(http);
            connectionPoll.setTcp(tcp);
            trafficPolicy.setConnectionPoll(connectionPoll);

            DestinationRule_Spec_TrafficPolicy_OutlierDetection outlierDetection=new DestinationRule_Spec_TrafficPolicy_OutlierDetection();
            outlierDetection.setBaseEjectionTime(domain.getBaseEjectionTime());
            outlierDetection.setConsecutiveErrors(domain.getConsecutiveErrors());
            outlierDetection.setInterval(domain.getInterval());
            outlierDetection.setMaxEjectionPercent(domain.getMaxEjectionPercent());
            trafficPolicy.setOutlierDetection(outlierDetection);

            destinationRule.getSpec().setTrafficPolicy(trafficPolicy);

            return this.put(namespace,name,JSONObject.toJSONString(destinationRule));
        }
    }

    @Override
    public boolean putSubsets(String namespace, String name, List<DestinationRuleSubsetDomain> subsetDomains){
        DestinationRule destinationRule=this.get(namespace,name);
        if(destinationRule==null){
            return Boolean.FALSE;
        }

        if(subsetDomains==null || subsetDomains.size()==0){
            destinationRule.getSpec().setSubsets(null);
            return this.put(namespace,name,JSONObject.toJSONString(destinationRule));
        }else{
            List<DestinationRule_Spec_Subset> subsets=new ArrayList<>();
            for(DestinationRuleSubsetDomain subsetDomain:subsetDomains){
                DestinationRule_Spec_Subset subset=new DestinationRule_Spec_Subset();
                DestinationRule_Spec_Subset_Label label=new DestinationRule_Spec_Subset_Label();
                label.setVersion(subsetDomain.getVersion());
                subset.setName(subsetDomain.getName());
                subset.setLabels(label);
                subsets.add(subset);
            }
            destinationRule.getSpec().setSubsets(subsets);
            return this.put(namespace,name,JSONObject.toJSONString(destinationRule));
        }
    }

    @Override
    public DestinationRuleDomain getDomain(String namespace, String name) {
        DestinationRule destinationRule=this.get(namespace,name);
        if(destinationRule==null || destinationRule.getSpec()==null){
            return null;
        }

        DestinationRuleDomain domain=new DestinationRuleDomain();
        domain.setName(destinationRule.getMetadata().getName());
        domain.setNamespace(destinationRule.getMetadata().getNamespace());
        domain.setSelfLink(destinationRule.getMetadata().getSelfLink());
        domain.setGeneration(destinationRule.getMetadata().getGeneration());
        domain.setResourceVersion(destinationRule.getMetadata().getResourceVersion());
        domain.setUid(destinationRule.getMetadata().getUid());
        domain.setCreationTimestamp(destinationRule.getMetadata().getCreationTimestamp());
        if(destinationRule.getSpec()!=null){
            domain.setHost(destinationRule.getSpec().getHost());
            if(destinationRule.getSpec().getSubsets()!=null){
                List<DestinationRuleSubsetDomain> subsetDomains=new ArrayList<>();
                for(DestinationRule_Spec_Subset subset:destinationRule.getSpec().getSubsets()){
                    DestinationRuleSubsetDomain subsetDomain=new DestinationRuleSubsetDomain();
                    subsetDomain.setName(subset.getName());
                    if(subset.getLabels()!=null){
                        subsetDomain.setVersion(subset.getLabels().getVersion());
                        subsetDomains.add(subsetDomain);
                    }
                }
                domain.setSubsets(subsetDomains);
            }
            if(destinationRule.getSpec().getTrafficPolicy()!=null
                    && destinationRule.getSpec().getTrafficPolicy().getConnectionPoll()!=null
                    && destinationRule.getSpec().getTrafficPolicy().getOutlierDetection()!=null){
                domain.setOpenFuse(true);
                domain.setHttp1MaxPendingRequests(destinationRule.getSpec().getTrafficPolicy().getConnectionPoll().getHttp().getHttp1MaxPendingRequests());
                domain.setMaxRequestsPerConnection(destinationRule.getSpec().getTrafficPolicy().getConnectionPoll().getHttp().getMaxRequestsPerConnection());
                domain.setMaxConnections(destinationRule.getSpec().getTrafficPolicy().getConnectionPoll().getTcp().getMaxConnections());
                domain.setBaseEjectionTime(destinationRule.getSpec().getTrafficPolicy().getOutlierDetection().getBaseEjectionTime());
                domain.setConsecutiveErrors(destinationRule.getSpec().getTrafficPolicy().getOutlierDetection().getConsecutiveErrors());
                domain.setInterval(destinationRule.getSpec().getTrafficPolicy().getOutlierDetection().getInterval());
                domain.setMaxEjectionPercent(destinationRule.getSpec().getTrafficPolicy().getOutlierDetection().getMaxEjectionPercent());
            }else{
                domain.setOpenFuse(false);
            }
        }


        return domain;
    }

    @Override
    public List<DestinationRuleDomain> listDomain(String namespace) {
        DestinationRuleList destinationRuleList=this.list(namespace);
        if(destinationRuleList==null){
            return null;
        }

        List<DestinationRuleDomain> domains=new ArrayList<>();
        for(DestinationRuleList_Item destinationRule:destinationRuleList.getItems()){
            DestinationRuleDomain domain=new DestinationRuleDomain();
            domain.setName(destinationRule.getMetadata().getName());
            domain.setNamespace(destinationRule.getMetadata().getNamespace());
            domain.setSelfLink(destinationRule.getMetadata().getSelfLink());
            domain.setGeneration(destinationRule.getMetadata().getGeneration());
            domain.setResourceVersion(destinationRule.getMetadata().getResourceVersion());
            domain.setUid(destinationRule.getMetadata().getUid());
            domain.setCreationTimestamp(destinationRule.getMetadata().getCreationTimestamp());
            if(destinationRule.getSpec()!=null){
                domain.setHost(destinationRule.getSpec().getHost());
                if(destinationRule.getSpec().getSubsets()!=null){
                    List<DestinationRuleSubsetDomain> subsetDomains=new ArrayList<>();
                    for(DestinationRule_Spec_Subset subset:destinationRule.getSpec().getSubsets()){
                        DestinationRuleSubsetDomain subsetDomain=new DestinationRuleSubsetDomain();
                        subsetDomain.setName(subset.getName());
                        if(subset.getLabels()!=null){
                            subsetDomain.setVersion(subset.getLabels().getVersion());
                            subsetDomains.add(subsetDomain);
                        }
                    }
                    domain.setSubsets(subsetDomains);
                }
            }
            if(destinationRule.getSpec().getTrafficPolicy()!=null
                    && destinationRule.getSpec().getTrafficPolicy().getConnectionPoll()!=null
            && destinationRule.getSpec().getTrafficPolicy().getOutlierDetection()!=null){
                domain.setOpenFuse(true);
                domain.setHttp1MaxPendingRequests(destinationRule.getSpec().getTrafficPolicy().getConnectionPoll().getHttp().getHttp1MaxPendingRequests());
                domain.setMaxRequestsPerConnection(destinationRule.getSpec().getTrafficPolicy().getConnectionPoll().getHttp().getMaxRequestsPerConnection());
                domain.setMaxConnections(destinationRule.getSpec().getTrafficPolicy().getConnectionPoll().getTcp().getMaxConnections());
                domain.setBaseEjectionTime(destinationRule.getSpec().getTrafficPolicy().getOutlierDetection().getBaseEjectionTime());
                domain.setConsecutiveErrors(destinationRule.getSpec().getTrafficPolicy().getOutlierDetection().getConsecutiveErrors());
                domain.setInterval(destinationRule.getSpec().getTrafficPolicy().getOutlierDetection().getInterval());
                domain.setMaxEjectionPercent(destinationRule.getSpec().getTrafficPolicy().getOutlierDetection().getMaxEjectionPercent());
            }else{
                domain.setOpenFuse(false);
            }
            domains.add(domain);
        }

        return domains;
    }
}
