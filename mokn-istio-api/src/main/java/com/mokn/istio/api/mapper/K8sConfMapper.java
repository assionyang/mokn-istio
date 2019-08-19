package com.mokn.istio.api.mapper;

import com.mokn.istio.api.model.db.K8sConf;
import com.mokn.istio.api.model.db.K8sConfRecord;

import java.util.List;

public interface K8sConfMapper {
    Long insertConf(K8sConf conf);
    Long insertConfRecord(List<K8sConfRecord> records);
    Long updateConf(K8sConf conf);
    Long updateConfRecord(K8sConfRecord record);
    K8sConf selectConfBySysNo(Long sysno);
    K8sConfRecord selectConfRecordBySysno(Long sysno);
    List<K8sConfRecord> selectConfRecordByConfSysno(Long confSysno);
    K8sConfRecord selectConfRecordByRecordNo(String recordNo);
    List<K8sConf> selectConf(K8sConf condition);
    List<K8sConfRecord> selectConfRecord(K8sConfRecord condition);

}
