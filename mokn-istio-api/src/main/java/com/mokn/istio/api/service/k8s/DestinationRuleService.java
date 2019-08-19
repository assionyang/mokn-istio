package com.mokn.istio.api.service.k8s;

import com.mokn.istio.api.model.domain.DestinationRuleDomain;
import com.mokn.istio.api.model.domain.DestinationRuleSubsetDomain;
import com.mokn.istio.api.model.k8s.destinationrule.DestinationRule;
import com.mokn.istio.api.model.k8s.destinationrule.DestinationRuleList;
import com.mokn.istio.api.model.k8s.destinationrule.DestinationRule_Spec_TrafficPolicy;

import java.util.List;

public interface DestinationRuleService {
    public DestinationRule get(String namespace,String name);
    public DestinationRuleList list(String namespace);
    public boolean post(String namespace,String name,String json);
    public boolean put(String namesapce,String name,String json);
    public boolean delete(String namespace,String name);
    public boolean putTrafficPolicy(String namespace, String name, DestinationRuleDomain destinationRuleDomain);
    public boolean putSubsets(String namespace, String name, List<DestinationRuleSubsetDomain> subsets);
    public DestinationRuleDomain getDomain(String namespace,String name);
    public List<DestinationRuleDomain> listDomain(String namespace);
}
