package com.mokn.istio.api.model.db;

import com.alibaba.fastjson.JSONObject;
import com.mokn.istio.api.model.domain.KeyValueDomain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class K8sConf {
    private Long sysno;
    private String configType;
    private String namespace;
    private String name;
    private String currencyNo;
    private String currencyType;
    private Date createdAt;
    private String memo;
    private K8sConfRecord currencyRecord;

    public List<KeyValueDomain> getItems() {
        return items;
    }

    public void setItems(List<KeyValueDomain> items) {
        this.items = items;
    }

    private List<KeyValueDomain> items;

    public K8sConfRecord getCurrencyRecord() {
        return currencyRecord;
    }

    public void setCurrencyRecord(K8sConfRecord currencyRecord) {
        this.currencyRecord = currencyRecord;
    }

    private List<K8sConfRecord> records;

    public List<K8sConfRecord> getRecords() {
        return records;
    }

    public void setRecords(List<K8sConfRecord> records) {
        this.records = records;
    }

    public Long getSysno() {
        return sysno;
    }

    public void setSysno(Long sysno) {
        this.sysno = sysno;
    }

    public String getConfigType() {
        return configType;
    }

    public void setConfigType(String configType) {
        this.configType = configType;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrencyNo() {
        return currencyNo;
    }

    public void setCurrencyNo(String currencyNo) {
        this.currencyNo = currencyNo;
    }

    public String getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
