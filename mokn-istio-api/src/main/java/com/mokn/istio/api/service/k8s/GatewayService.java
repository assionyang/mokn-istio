package com.mokn.istio.api.service.k8s;

import com.mokn.istio.api.model.domain.GatewayDomain;
import com.mokn.istio.api.model.k8s.gateway.Gateway;
import com.mokn.istio.api.model.k8s.gateway.GatewayList;

import java.util.List;

public interface GatewayService {
    public Gateway get(String namespaceName, String name);
    public GatewayDomain getDomain(String namespaceName, String name);
    public GatewayList list(String namespace);
    public List<GatewayDomain> listDomain(String namespace);
    public boolean delete(String namesapce,String name);
    public boolean post(String namespace,String name,String json);
}
