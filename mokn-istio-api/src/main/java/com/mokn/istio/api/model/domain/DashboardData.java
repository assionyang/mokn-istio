package com.mokn.istio.api.model.domain;

public class DashboardData {
    public Integer getNamespaceQty() {
        return namespaceQty;
    }

    public void setNamespaceQty(Integer namespaceQty) {
        this.namespaceQty = namespaceQty;
    }

    public Integer getDeploymentQty() {
        return deploymentQty;
    }

    public void setDeploymentQty(Integer deploymentQty) {
        this.deploymentQty = deploymentQty;
    }

    public Integer getPodQty() {
        return podQty;
    }

    public void setPodQty(Integer podQty) {
        this.podQty = podQty;
    }

    public Integer getServiceQty() {
        return serviceQty;
    }

    public void setServiceQty(Integer serviceQty) {
        this.serviceQty = serviceQty;
    }

    public Integer getConfigMapQty() {
        return configMapQty;
    }

    public void setConfigMapQty(Integer configMapQty) {
        this.configMapQty = configMapQty;
    }

    public Integer getVeritualServiceQty() {
        return veritualServiceQty;
    }

    public void setVeritualServiceQty(Integer veritualServiceQty) {
        this.veritualServiceQty = veritualServiceQty;
    }

    public Integer getGatewayQty() {
        return gatewayQty;
    }

    public void setGatewayQty(Integer gatewayQty) {
        this.gatewayQty = gatewayQty;
    }

    private Integer namespaceQty;
    private Integer deploymentQty;
    private Integer podQty;
    private Integer serviceQty;
    private Integer configMapQty;
    private Integer veritualServiceQty;
    private Integer gatewayQty;
    private Integer secretQty;

    public Integer getSecretQty() {
        return secretQty;
    }

    public void setSecretQty(Integer secretQty) {
        this.secretQty = secretQty;
    }
}
