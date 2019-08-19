package com.mokn.istio.api.service.k8s;

import com.mokn.istio.api.model.domain.ServiceDomain;
import com.mokn.istio.api.model.k8s.service.Service;
import com.mokn.istio.api.model.k8s.service.ServiceList;

import java.util.List;

public interface ServiceService {
    ServiceList listService();
    ServiceList listService(String namespace);
    Service getService(String namespace,String name);
    List<ServiceDomain> listServiceDomain();
    List<ServiceDomain> listServiceDomain(String namespace);
    ServiceDomain getServiceDomain(String namespace,String name);
    boolean deleteService(String namespace,String name);
    boolean patch(String namespace,String name,String body);
}
