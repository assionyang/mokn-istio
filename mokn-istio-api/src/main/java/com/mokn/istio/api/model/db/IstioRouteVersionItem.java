package com.mokn.istio.api.model.db;

import java.util.Date;

public class IstioRouteVersionItem {
    public Long getSysno() {
        return sysno;
    }

    public void setSysno(Long sysno) {
        this.sysno = sysno;
    }

    public Long getVersionSysno() {
        return versionSysno;
    }

    public void setVersionSysno(Long versionSysno) {
        this.versionSysno = versionSysno;
    }

    public Integer getVersionType() {
        return versionType;
    }

    public void setVersionType(Integer versionType) {
        this.versionType = versionType;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getNameHost() {
        return nameHost;
    }

    public void setNameHost(String nameHost) {
        this.nameHost = nameHost;
    }

    public String getSubsetOld() {
        return subsetOld;
    }

    public void setSubsetOld(String subsetOld) {
        this.subsetOld = subsetOld;
    }

    public String getSubsetNew() {
        return subsetNew;
    }

    public void setSubsetNew(String subsetNew) {
        this.subsetNew = subsetNew;
    }

    public String getOldJson() {
        return oldJson;
    }

    public void setOldJson(String oldJson) {
        this.oldJson = oldJson;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getDeployAt() {
        return deployAt;
    }

    public void setDeployAt(Date deployAt) {
        this.deployAt = deployAt;
    }

    public Date getReleaseAt() {
        return releaseAt;
    }

    public void setReleaseAt(Date releaseAt) {
        this.releaseAt = releaseAt;
    }

    public Date getRollbackAt() {
        return rollbackAt;
    }

    public void setRollbackAt(Date rollbackAt) {
        this.rollbackAt = rollbackAt;
    }

    public Integer getVersionStatus() {
        return versionStatus;
    }

    public void setVersionStatus(Integer versionStatus) {
        this.versionStatus = versionStatus;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public IstioRouteVersion getIstioRouteVersion() {
        return istioRouteVersion;
    }

    public void setIstioRouteVersion(IstioRouteVersion istioRouteVersion) {
        this.istioRouteVersion = istioRouteVersion;
    }

    private Long sysno;
    private Long versionSysno;
    private Integer versionType;
    private String namespace;
    private String nameHost;
    private String subsetOld;
    private String subsetNew;
    private String oldJson;
    private Date createdAt;
    private Date deployAt;
    private Date releaseAt;
    private Date rollbackAt;
    private Integer versionStatus;
    private String memo;

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getHttpKey() {
        return httpKey;
    }

    public void setHttpKey(String httpKey) {
        this.httpKey = httpKey;
    }

    public String getHttpValue() {
        return httpValue;
    }

    public void setHttpValue(String httpValue) {
        this.httpValue = httpValue;
    }

    private Integer weight;
    private String httpKey;
    private String httpValue;
    private IstioRouteVersion istioRouteVersion;
}
