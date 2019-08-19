package com.mokn.istio.api.service.k8s;

import com.mokn.istio.api.model.domain.VirtualServiceDomain;
import com.mokn.istio.api.model.k8s.virtualservice.VirtualService;
import com.mokn.istio.api.model.k8s.virtualservice.VirtualServiceList;

import java.util.List;

public interface VirtualServiceService {
    public VirtualService getVirtualService(String namespaceName,String name);
    public VirtualServiceList listVirtualService(String namespaceName);
    public VirtualServiceDomain getVirtualServiceDomain(String namespaceName, String name);
    public List<VirtualServiceDomain> listVirtualServiceDomain(String namespaceName);
    public boolean applyVirtualService(String namespaceName,String name,String body);
    public boolean delete(String namespace,String name);
    public boolean put(String namesapce,String name,String json);
    public boolean post(String namespace,String name,String json);
    public boolean putFault(String namespace,String name,VirtualServiceDomain domain);
}
