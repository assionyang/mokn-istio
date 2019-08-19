package com.mokn.istio.api.model.domain;

public class VirtualServiceDomain {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersionType() {
        return versionType;
    }

    public void setVersionType(String versionType) {
        this.versionType = versionType;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getResourceVersion() {
        return resourceVersion;
    }

    public void setResourceVersion(String resourceVersion) {
        this.resourceVersion = resourceVersion;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    private String name;
    private String versionType;
    private String version;
    private String namespace;
    private String uid;
    private String resourceVersion;
    private String creationTimestamp;
    private Boolean openFault;
    private Boolean openFaultAbort;
    private Boolean openFaultDelay;
    private Integer faultAbortHttpStatus;
    private Integer faultAbortPercent;
    private String faultDelayFixedDelay;
    private Integer faultDelayPercent;

    public String getIstioVersionType() {
        return istioVersionType;
    }

    public void setIstioVersionType(String istioVersionType) {
        this.istioVersionType = istioVersionType;
    }

    private String istioVersionType;
    private String istioVersion;

    public String getIstioVersion() {
        return istioVersion;
    }

    public void setIstioVersion(String istioVersion) {
        this.istioVersion = istioVersion;
    }

    public String getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(String creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public Boolean getOpenFault() {
        return openFault;
    }

    public void setOpenFault(Boolean openFault) {
        this.openFault = openFault;
    }

    public Boolean getOpenFaultAbort() {
        return openFaultAbort;
    }

    public void setOpenFaultAbort(Boolean openFaultAbort) {
        this.openFaultAbort = openFaultAbort;
    }

    public Boolean getOpenFaultDelay() {
        return openFaultDelay;
    }

    public void setOpenFaultDelay(Boolean openFaultDelay) {
        this.openFaultDelay = openFaultDelay;
    }

    public Integer getFaultAbortHttpStatus() {
        return faultAbortHttpStatus;
    }

    public void setFaultAbortHttpStatus(Integer faultAbortHttpStatus) {
        this.faultAbortHttpStatus = faultAbortHttpStatus;
    }

    public Integer getFaultAbortPercent() {
        return faultAbortPercent;
    }

    public void setFaultAbortPercent(Integer faultAbortPercent) {
        this.faultAbortPercent = faultAbortPercent;
    }

    public String getFaultDelayFixedDelay() {
        return faultDelayFixedDelay;
    }

    public void setFaultDelayFixedDelay(String faultDelayFixedDelay) {
        this.faultDelayFixedDelay = faultDelayFixedDelay;
    }

    public Integer getFaultDelayPercent() {
        return faultDelayPercent;
    }

    public void setFaultDelayPercent(Integer faultDelayPercent) {
        this.faultDelayPercent = faultDelayPercent;
    }

    public VirtualServiceDomain(){}
    public VirtualServiceDomain(String name,String versionType,String version,String namespace,String uid,String resourceVersion){
        this.name=name;
        this.versionType=versionType;
        this.version=version;
        this.namespace=namespace;
        this.uid=uid;
        this.resourceVersion=resourceVersion;
    }
}
