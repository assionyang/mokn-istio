package com.mokn.istio.api.service.k8s;

import com.mokn.istio.api.model.domain.ConfigMapDomain;
import com.mokn.istio.api.model.k8s.configmap.ConfigMap;
import com.mokn.istio.api.model.k8s.configmap.ConfigMapList;

import java.util.List;

public interface ConfigMapService {
    public ConfigMap get(String namespaceName, String name);
    public ConfigMapDomain getDomain(String namespaceName, String name);
    public ConfigMapList list(String namespace);
    public List<ConfigMapDomain> listDomain(String namespace);
    public boolean delete(String namesapce,String name);
    public boolean post(String namespace,String json);
    public boolean put(String namespace,String name,String json);
}
