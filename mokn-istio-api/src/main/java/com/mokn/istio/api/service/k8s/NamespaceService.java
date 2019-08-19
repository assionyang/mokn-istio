package com.mokn.istio.api.service.k8s;

import com.mokn.istio.api.model.domain.NamespaceDomain;
import com.mokn.istio.api.model.k8s.namespace.Namespace;
import com.mokn.istio.api.model.k8s.namespace.NamespaceList;

import java.util.List;

public interface NamespaceService {
    public NamespaceList listNamespace();
    public Namespace getNamespace(String namespaceName);
    public List<NamespaceDomain> listNamespaceDomain();
    public NamespaceDomain getNamespaceDomain(String namespaceName);
}
