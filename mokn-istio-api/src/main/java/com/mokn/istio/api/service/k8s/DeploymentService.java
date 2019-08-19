package com.mokn.istio.api.service.k8s;

import com.mokn.istio.api.model.domain.DeploymentDomain;
import com.mokn.istio.api.model.k8s.deployment.Deployment;
import com.mokn.istio.api.model.k8s.deployment.DeploymentList;

import java.util.List;

public interface DeploymentService {
    public Deployment getDeployment(String namespaceName,String name);
    public DeploymentDomain getDeploymentDomain(String namespaceName,String name);
    public DeploymentList listDeployment();
    public DeploymentList listDeployment(String namespace);
    public List<DeploymentDomain> listDeploymentDomain();
    public List<DeploymentDomain> listDeploymentDomain(String namespace);
    public boolean deleteDeployment(String namesapce,String name);

}
