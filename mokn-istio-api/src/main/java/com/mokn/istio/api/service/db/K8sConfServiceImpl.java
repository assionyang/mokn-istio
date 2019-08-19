package com.mokn.istio.api.service.db;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.mokn.istio.api.mapper.K8sConfMapper;
import com.mokn.istio.api.model.db.K8sConf;
import com.mokn.istio.api.model.db.K8sConfRecord;
import com.mokn.istio.api.model.domain.KeyValueDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class K8sConfServiceImpl implements K8sConfService {

    @Autowired
    private K8sConfMapper confMapper;

    @Override
    @Transactional
    public boolean createConf(K8sConf conf) {
        confMapper.insertConf(conf);
        for(int i=0;i<conf.getRecords().size();i++){
            conf.getRecords().get(i).setConfSysno(conf.getSysno());
        }
        confMapper.insertConfRecord(conf.getRecords());

        return Boolean.TRUE;
    }

    @Override
    public boolean createRecord(K8sConfRecord record) {
        List<K8sConfRecord> records=new ArrayList<>();
        records.add(record);
        confMapper.insertConfRecord(records);
        return Boolean.TRUE;
    }

    @Override
    public boolean updateConf(K8sConf conf) {
        return confMapper.updateConf(conf)>0;
    }

    @Override
    public boolean updateRecord(K8sConfRecord record) {
        return confMapper.updateConfRecord(record)>0;
    }

    @Override
    public K8sConf getConf(Long sysno) {
        K8sConf k8sConf = confMapper.selectConfBySysNo(sysno);
        List<KeyValueDomain> keyValues=new ArrayList<>();
        Map<String,String> map= (Map) JSONObject.parseObject(k8sConf.getCurrencyRecord().getConfData());
        for(Map.Entry<String,String> entry:map.entrySet()){
            KeyValueDomain kv=new KeyValueDomain();
            kv.setKey(entry.getKey());
            kv.setValue(entry.getValue());
            keyValues.add(kv);
        }
        k8sConf.setItems(keyValues);
        return k8sConf;
    }

    @Override
    public K8sConfRecord getRecord(Long sysno) {
        return confMapper.selectConfRecordBySysno(sysno);
    }

    @Override
    public List<K8sConfRecord> listRecordByConfSysno(Long confSysno) {
        return confMapper.selectConfRecordByConfSysno(confSysno);
    }

    @Override
    public List<K8sConf> listConf(K8sConf condition) {
        List<K8sConf>  k8sConfs=confMapper.selectConf(condition);
        for(int i=0;i<k8sConfs.size();i++){
            List<KeyValueDomain> keyValues=new ArrayList<>();
            Map<String,String> map= (Map) JSONObject.parseObject(k8sConfs.get(i).getCurrencyRecord().getConfData());
            for(Map.Entry<String,String> entry:map.entrySet()){
                KeyValueDomain kv=new KeyValueDomain();
                kv.setKey(entry.getKey());
                kv.setValue(entry.getValue());
                keyValues.add(kv);
            }
            k8sConfs.get(i).setItems(keyValues);
        }
        return k8sConfs;
    }

    @Override
    public List<K8sConfRecord> listRrcord(K8sConfRecord condition) {
        List<K8sConfRecord> records = confMapper.selectConfRecord(condition);
        for(int i=0;i<records.size();i++){
            JSONObject jsonObject=JSONObject.parseObject(records.get(i).getConfData());
            Map<String,String> map=JSONObject.parseObject(records.get(i).getConfData(),new TypeReference<Map<String,String>>(){});
            records.get(i).setData(map);
        }
        return records;
    }
}
