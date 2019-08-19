package com.mokn.istio.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mokn.istio.api.common.JwtUtil;
import com.mokn.istio.api.common.LoginPassport;
import com.mokn.istio.api.common.NumberKeyUtils;
import com.mokn.istio.api.model.db.K8sConf;
import com.mokn.istio.api.model.db.K8sConfRecord;
import com.mokn.istio.api.model.domain.*;
import com.mokn.istio.api.model.em.ConfRecordStatusEnum;
import com.mokn.istio.api.service.db.K8sConfService;
import com.mokn.istio.api.service.k8s.ConfigMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(value = "/conf")
public class K8sConfController {

    @Autowired
    private K8sConfService confService;
    @Autowired
    private ConfigMapService configMapService;
    @Autowired
    private JwtUtil jwtUtil;

    @LoginPassport
    @GetMapping(value = "/list")
    public JsonResult<K8sConf> list(@RequestParam(value = "pageNo",required = false) Integer pageNo,
                                    @RequestParam(value = "pageSize",required = false) Integer pageSize,
                                    @RequestParam(value = "namespace",required = false) String namespace,
                                    @RequestParam(value = "name",required = false) String name,
                                    @RequestHeader(value = "Authorization",required = false) String auth){
        JsonResult<K8sConf> result=new JsonResult<>();

        K8sConf condition=new K8sConf();
        if(namespace!=null && !namespace.equals("")){
            condition.setNamespace(namespace);
        }
        if(name!=null && !name.equals("")){
            condition.setNamespace(name);
        }

        PageHelper.startPage(pageNo,pageSize);
        PageInfo<K8sConf> pageInfo=new PageInfo<>(confService.listConf(condition));

        List<K8sConf> k8sConfs=pageInfo.getList();
        for(int i=0;i<k8sConfs.size();i++){
            for(int j=0;j<k8sConfs.get(i).getItems().size();j++){
                if(auth==null || auth.equals("") || (!jwtUtil.getValueFromToken(auth,"role").equals("admin") && !jwtUtil.getValueFromToken(auth,"role").equals("release"))){
                    k8sConfs.get(i).getItems().get(j).setValue("******");
                    k8sConfs.get(i).getCurrencyRecord().setConfData("没有权限查看");
                    for(int h=0;h<k8sConfs.get(i).getRecords().size();h++){
                        k8sConfs.get(i).getRecords().get(h).setConfData("没有权限查看");
                    }
                }
            }
        }
        return result.success(k8sConfs,pageInfo.getTotal());
    }

    @LoginPassport(valid = true,role = "admin|release")
    @PostMapping(value = "/create")
    public JsonResult create(@RequestBody ConfCreateRequest request){
        JsonResult result=new JsonResult();
        if(request.getName()==null || request.getName().equals("")){
            return result.fail("名称不能为空");
        }
        if(request.getNamespace()==null || request.getNamespace().equals("")){
            return result.fail("命名空间不能为空");
        }
        if(request.getItems()==null || request.getItems().size()==0){
            return result.fail("数据不能为空");
        }

        for(KeyValueDomain kv:request.getItems()){
            int i=0;
            for(KeyValueDomain kv2:request.getItems()){
                if(kv.getKey().equals(kv2.getKey())){
                    i++;
                }
            }
            if(i>1){
                return result.fail("配置键重复");
            }
        }


        K8sConf condition=new K8sConf();
        condition.setNamespace(request.getNamespace());
        condition.setName(request.getName());
        List<K8sConf> k8sConfs=confService.listConf(condition);
        if(k8sConfs.size()>0){
            return result.fail("配置已存在");
        }

        if(configMapService.getDomain(request.getNamespace(),request.getName())!=null){
            return result.fail("配置已存在");
        }

        String no= NumberKeyUtils.timeNumber();
        K8sConf conf=new K8sConf();
        conf.setConfigType("configmap");
        conf.setCreatedAt(new Date());
        conf.setNamespace(request.getNamespace());
        conf.setName(request.getName());
        conf.setCurrencyNo(no);
        conf.setCurrencyType("create");
        K8sConfRecord record=new K8sConfRecord();
        record.setCreatedAt(new Date());
        record.setNamespace(request.getNamespace());
        record.setName(request.getName());
        record.setRecordNo(no);
        record.setRecordStatus(ConfRecordStatusEnum.RUN.getValue());
        record.setSaveType("create");
        Map<String,String> confDataMap=new HashMap<>();
        for(KeyValueDomain kv:request.getItems()){
            confDataMap.put(kv.getKey(),kv.getValue());
        }
        record.setConfData(JSONObject.toJSONString(confDataMap));
        List<K8sConfRecord> records=new ArrayList<>();
        records.add(record);
        conf.setRecords(records);
        if(confService.createConf(conf)) {
            return result.success("创建成功");
        }else{
            return result.fail("创建失败");
        }
    }

    @LoginPassport(valid = true,role = "admin|release")
    @PostMapping(value = "/update")
    public JsonResult update(@RequestBody ConfUpdateRequest request){
        JsonResult result=new JsonResult();
        if(request.getSysno()==null){
            return result.fail("请选择修改项");
        }
        if(request.getItems()==null || request.getItems().size()==0){
            return result.fail("配置项不能为空");
        }
        K8sConf conf=confService.getConf(request.getSysno());
        if(conf==null){
            return result.fail("修改项不存在");
        }

        for(KeyValueDomain kv:request.getItems()){
            int i=0;
            for(KeyValueDomain kv2:request.getItems()){
                if(kv.getKey().equals(kv2.getKey())){
                    i++;
                }
            }
            if(i>1){
                return result.fail("配置键重复");
            }
        }

        String no= NumberKeyUtils.timeNumber();
        K8sConfRecord record=new K8sConfRecord();
        record.setConfSysno(request.getSysno());
        record.setCreatedAt(new Date());
        record.setNamespace(conf.getNamespace());
        record.setName(conf.getName());
        record.setRecordNo(no);
        record.setRecordStatus(ConfRecordStatusEnum.RUN.getValue());
        record.setSaveType("update");
        Map<String,String> confDataMap=new HashMap<>();
        for(KeyValueDomain kv:request.getItems()){
            confDataMap.put(kv.getKey(),kv.getValue());
        }
        record.setConfData(JSONObject.toJSONString(confDataMap));
        confService.createRecord(record);
        conf.setCurrencyType("update");
        conf.setCurrencyNo(no);
        confService.updateConf(conf);
        return result.success("修改成功");
    }

    @LoginPassport(valid = true,role = "admin|release")
    @PostMapping(value = "/rollback")
    public JsonResult rollback(@RequestBody ConfRollbackRequest request){
        JsonResult result=new JsonResult();
        if(request.getSysno()==null){
            return result.fail("请选择回滚配置");
        }
        if(request.getRecordSysno()==null){
            return result.fail("请选择回滚项");
        }

        K8sConf conf=confService.getConf(request.getSysno());
        if(conf==null){
            return result.fail("回滚配置不存在");
        }
        K8sConfRecord confRecord=confService.getRecord(request.getRecordSysno());
        if(conf==null){
            return result.fail("回滚项不存在");
        }

        String no= NumberKeyUtils.timeNumber();
        K8sConfRecord record=new K8sConfRecord();
        record.setConfSysno(request.getSysno());
        record.setCreatedAt(new Date());
        record.setNamespace(conf.getNamespace());
        record.setName(conf.getName());
        record.setRecordNo(no);
        record.setRecordStatus(ConfRecordStatusEnum.RUN.getValue());
        record.setSaveType("rollback");
        Map<String,String> confDataMap=new HashMap<>();
        record.setConfData(confRecord.getConfData());
        confService.createRecord(record);
        conf.setCurrencyType("rollback");
        conf.setCurrencyNo(no);
        confService.updateConf(conf);
        return result.success("回滚成功");
    }
}
