package com.mokn.istio.api.service.k8s;

import com.mokn.istio.api.model.domain.PodDomain;
import com.mokn.istio.api.model.k8s.pod.Pod;
import com.mokn.istio.api.model.k8s.pod.PodList;

import java.util.List;

public interface PodService {
    public Pod get(String namespaceName, String name);
    public PodDomain getDomain(String namespaceName, String name);
    public PodList list();
    public PodList list(String namespace);
    public List<PodDomain> listDomain();
    public List<PodDomain> listDomain(String namespace);
    public boolean delete(String namesapce,String name);
}
