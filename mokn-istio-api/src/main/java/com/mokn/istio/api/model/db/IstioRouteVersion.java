package com.mokn.istio.api.model.db;

import java.util.Date;
import java.util.List;

public class IstioRouteVersion {
    public Long getSysno() {
        return sysno;
    }

    public void setSysno(Long sysno) {
        this.sysno = sysno;
    }

    public Integer getVersionType() {
        return versionType;
    }

    public void setVersionType(Integer versionType) {
        this.versionType = versionType;
    }

    public String getVersionTitle() {
        return versionTitle;
    }

    public void setVersionTitle(String versionTitle) {
        this.versionTitle = versionTitle;
    }

    public String getVersionRemark() {
        return versionRemark;
    }

    public void setVersionRemark(String versionRemark) {
        this.versionRemark = versionRemark;
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

    public String getOperName() {
        return operName;
    }

    public void setOperName(String operName) {
        this.operName = operName;
    }

    private Long sysno;
    private Integer versionType;

    public String getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(String versionNo) {
        this.versionNo = versionNo;
    }

    private String versionNo;
    private String versionTitle;
    private String versionRemark;
    private Date createdAt;
    private Date deployAt;
    private Date releaseAt;
    private Date rollbackAt;
    private Integer versionStatus;
    private String memo;
    private String operName;

    public List<IstioRouteVersionItem> getItems() {
        return items;
    }

    public void setItems(List<IstioRouteVersionItem> items) {
        this.items = items;
    }

    private List<IstioRouteVersionItem> items;
}
