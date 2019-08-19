package com.mokn.istio.api.jobs.configmap;

import com.alibaba.fastjson.JSONObject;
import com.mokn.istio.api.model.db.K8sConf;
import com.mokn.istio.api.model.db.K8sConfRecord;
import com.mokn.istio.api.model.em.ConfRecordStatusEnum;
import com.mokn.istio.api.model.k8s.configmap.ConfigMap;
import com.mokn.istio.api.model.k8s.configmap.ConfigMap_Metadata;
import com.mokn.istio.api.service.db.K8sConfService;
import com.mokn.istio.api.service.k8s.ConfigMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ApplyJob {

    @Autowired
    private K8sConfService confService;
    @Autowired
    private ConfigMapService configMapService;

    @Scheduled(cron = "0/1 * * * * ?")
    public void run() {
        K8sConfRecord condition=new K8sConfRecord();
        condition.setRecordStatus(ConfRecordStatusEnum.RUN.getValue());
        List<K8sConfRecord> records=confService.listRrcord(condition);

        for(K8sConfRecord record:records){
            if(record.getSaveType().equals("create")){
                ConfigMap configMap=new ConfigMap();
                configMap.setApiVersion("v1");
                configMap.setKind("ConfigMap");
                ConfigMap_Metadata metadata=new ConfigMap_Metadata();
                metadata.setNamespace(record.getNamespace());
                metadata.setName(record.getName());
                configMap.setMetadata(metadata);
                configMap.setData(record.getData());

                if(configMapService.post(record.getNamespace(), JSONObject.toJSONString(configMap))){
                    record.setRecordStatus(ConfRecordStatusEnum.RUN_SUCCESS.getValue());
                }else{
                    record.setRecordStatus(ConfRecordStatusEnum.RUN_FAIL.getValue());
                }
                confService.updateRecord(record);
            }else{
                // 更新&回滚
                ConfigMap configMap=configMapService.get(record.getNamespace(),record.getName());

                record.setRecordStatus(ConfRecordStatusEnum.RUN_FAIL.getValue());
                if(configMap!=null){
                    configMap.setData(record.getData());
                    if(configMapService.put(record.getNamespace(),record.getName(),JSONObject.toJSONString(configMap))){
                        record.setRecordStatus(ConfRecordStatusEnum.RUN_SUCCESS.getValue());
                    }
                }
                confService.updateRecord(record);
            }
        }
    }
}
