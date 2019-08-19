package com.mokn.istio.api.service.db;

import com.mokn.istio.api.model.db.K8sConf;
import com.mokn.istio.api.model.db.K8sConfRecord;

import java.util.List;

public interface K8sConfService {
    boolean createConf(K8sConf conf);
    boolean createRecord(K8sConfRecord record);
    boolean updateConf(K8sConf conf);
    boolean updateRecord(K8sConfRecord record);
    K8sConf getConf(Long sysno);
    K8sConfRecord getRecord(Long sysno);
    List<K8sConfRecord> listRecordByConfSysno(Long confSysno);
    List<K8sConf> listConf(K8sConf condition);
    List<K8sConfRecord> listRrcord(K8sConfRecord condition);
}
