package com.mokn.istio.api.service.db;

import com.mokn.istio.api.mapper.IstioRouteVersionMapper;
import com.mokn.istio.api.model.db.IstioRouteVersion;
import com.mokn.istio.api.model.db.IstioRouteVersionItem;
import com.mokn.istio.api.model.em.VersionStatusEnum;
import com.mokn.istio.api.model.em.VersionTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class IstioRouteVersionServiceImpl implements IstioRouteVersionService {

    @Autowired
    private IstioRouteVersionMapper versionMapper;

    @Override
    @Transactional
    public boolean create(IstioRouteVersion version) {
        versionMapper.insertVersion(version);
        for(int i=0;i<version.getItems().size();i++){
            version.getItems().get(i).setVersionSysno(version.getSysno());
        }
        versionMapper.insertVersionItem(version.getItems());
        return Boolean.TRUE;
    }

    @Override
    @Transactional
    public boolean update(IstioRouteVersion version) {
        versionMapper.updateVersion(version);
        if(version.getItems()!=null && version.getItems().size()>0){
            for(IstioRouteVersionItem item:version.getItems()){
                versionMapper.updateVersionItem(item);
            }
        }
        return Boolean.TRUE;
    }

    @Override
    public IstioRouteVersion getVersion(Long sysno) {
        return versionMapper.selectVersionBySysno(sysno);
    }

    @Override
    public List<IstioRouteVersion> listVersion(IstioRouteVersion condition) {
        return versionMapper.selectVersion(condition);
    }

    @Override
    @Transactional
    public boolean action(Long sysno, VersionStatusEnum versionStatus) {
        IstioRouteVersion version=versionMapper.selectVersionBySysno(sysno);
        if(version==null){
            return Boolean.FALSE;
        }
        if(versionStatus==VersionStatusEnum.DEPLOY_ING){
            // 部署
            if(version.getVersionStatus()!=VersionStatusEnum.CREATED.getValue()){
                return Boolean.FALSE;
            }
            IstioRouteVersion updateVersion=new IstioRouteVersion();
            updateVersion.setSysno(version.getSysno());
            updateVersion.setVersionStatus(VersionStatusEnum.DEPLOY_ING.getValue());
            updateVersion.setDeployAt(new Date());
            versionMapper.updateVersion(updateVersion);
            for(IstioRouteVersionItem item:version.getItems()){
                IstioRouteVersionItem updateItem=new IstioRouteVersionItem();
                updateItem.setSysno(item.getSysno());
                updateItem.setVersionStatus(VersionStatusEnum.DEPLOY_ING.getValue());
                updateItem.setDeployAt(new Date());
                versionMapper.updateVersionItem(updateItem);
            }
            return Boolean.TRUE;
        }
        if(versionStatus==VersionStatusEnum.DEPLOY_SUCCESS){
            // 部署成功
            if(version.getVersionStatus()!=VersionStatusEnum.DEPLOY_ING.getValue()){
                return Boolean.FALSE;
            }
            IstioRouteVersion updateVersion=new IstioRouteVersion();
            updateVersion.setSysno(version.getSysno());
            updateVersion.setVersionStatus(VersionStatusEnum.DEPLOY_SUCCESS.getValue());
            updateVersion.setDeployAt(new Date());
            versionMapper.updateVersion(updateVersion);
            for(IstioRouteVersionItem item:version.getItems()){
                IstioRouteVersionItem updateItem=new IstioRouteVersionItem();
                updateItem.setSysno(item.getSysno());
                updateItem.setVersionStatus(VersionStatusEnum.DEPLOY_SUCCESS.getValue());
                updateItem.setDeployAt(new Date());
                versionMapper.updateVersionItem(updateItem);
            }
            return Boolean.TRUE;
        }
        if(versionStatus==VersionStatusEnum.RELEASE_ING){
            // 上线
            if(version.getVersionStatus()!=VersionStatusEnum.DEPLOY_SUCCESS.getValue()){
                return Boolean.FALSE;
            }
            IstioRouteVersion updateVersion=new IstioRouteVersion();
            updateVersion.setSysno(version.getSysno());
            updateVersion.setVersionStatus(VersionStatusEnum.RELEASE_ING.getValue());
            updateVersion.setReleaseAt(new Date());
            versionMapper.updateVersion(updateVersion);
            for(IstioRouteVersionItem item:version.getItems()){
                IstioRouteVersionItem updateItem=new IstioRouteVersionItem();
                updateItem.setSysno(item.getSysno());
                updateItem.setVersionStatus(VersionStatusEnum.RELEASE_ING.getValue());
                updateItem.setReleaseAt(new Date());
                versionMapper.updateVersionItem(updateItem);
            }
            return Boolean.TRUE;
        }
        if(versionStatus==VersionStatusEnum.RELEASE_SUCCESS){
            // 上线成功
            if(version.getVersionStatus()!=VersionStatusEnum.DEPLOY_ING.getValue()
            && version.getVersionStatus()!= VersionStatusEnum.RELEASE_ING.getValue()){
                return Boolean.FALSE;
            }
            IstioRouteVersion updateVersion=new IstioRouteVersion();
            updateVersion.setSysno(version.getSysno());
            updateVersion.setVersionStatus(VersionStatusEnum.RELEASE_SUCCESS.getValue());
            updateVersion.setDeployAt(new Date());
            versionMapper.updateVersion(updateVersion);
            for(IstioRouteVersionItem item:version.getItems()){
                IstioRouteVersionItem updateItem=new IstioRouteVersionItem();
                updateItem.setSysno(item.getSysno());
                updateItem.setVersionStatus(VersionStatusEnum.RELEASE_SUCCESS.getValue());
                updateItem.setDeployAt(new Date());
                versionMapper.updateVersionItem(updateItem);
            }
            return Boolean.TRUE;
        }
        if(versionStatus==VersionStatusEnum.ROLLBACK_ING){
            // 回滚
            if(version.getVersionStatus()!=VersionStatusEnum.RELEASE_SUCCESS.getValue()
            && version.getVersionStatus()!=VersionStatusEnum.DEPLOY_SUCCESS.getValue()){
                return Boolean.FALSE;
            }
            IstioRouteVersion updateVersion=new IstioRouteVersion();
            updateVersion.setSysno(version.getSysno());
            updateVersion.setVersionStatus(VersionStatusEnum.ROLLBACK_ING.getValue());
            updateVersion.setRollbackAt(new Date());
            versionMapper.updateVersion(updateVersion);
            for(IstioRouteVersionItem item:version.getItems()){
                IstioRouteVersionItem updateItem=new IstioRouteVersionItem();
                updateItem.setSysno(item.getSysno());
                updateItem.setVersionStatus(VersionStatusEnum.ROLLBACK_ING.getValue());
                updateItem.setRollbackAt(new Date());
                versionMapper.updateVersionItem(updateItem);
            }
            return Boolean.TRUE;
        }
        if(versionStatus==VersionStatusEnum.ROLLBACK_SUCCESS){
            // 回滚成功
            if(version.getVersionStatus()!=VersionStatusEnum.ROLLBACK_ING.getValue()){
                return Boolean.FALSE;
            }
            IstioRouteVersion updateVersion=new IstioRouteVersion();
            updateVersion.setSysno(version.getSysno());
            updateVersion.setVersionStatus(VersionStatusEnum.ROLLBACK_SUCCESS.getValue());
            updateVersion.setDeployAt(new Date());
            versionMapper.updateVersion(updateVersion);
            for(IstioRouteVersionItem item:version.getItems()){
                IstioRouteVersionItem updateItem=new IstioRouteVersionItem();
                updateItem.setSysno(item.getSysno());
                updateItem.setVersionStatus(VersionStatusEnum.ROLLBACK_SUCCESS.getValue());
                updateItem.setDeployAt(new Date());
                versionMapper.updateVersionItem(updateItem);
            }
            return Boolean.TRUE;
        }
        if(versionStatus==VersionStatusEnum.CANCEL){
            // 上线
            if(version.getVersionStatus()!=VersionStatusEnum.CREATED.getValue()){
                return Boolean.FALSE;
            }
            IstioRouteVersion updateVersion=new IstioRouteVersion();
            updateVersion.setSysno(version.getSysno());
            updateVersion.setVersionStatus(VersionStatusEnum.CANCEL.getValue());
            versionMapper.updateVersion(updateVersion);
            for(IstioRouteVersionItem item:version.getItems()){
                IstioRouteVersionItem updateItem=new IstioRouteVersionItem();
                updateItem.setSysno(item.getSysno());
                updateItem.setVersionStatus(VersionStatusEnum.CANCEL.getValue());
                versionMapper.updateVersionItem(updateItem);
            }
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public IstioRouteVersionMapper getVersionMapper() {
        return versionMapper;
    }

    public void setVersionMapper(IstioRouteVersionMapper versionMapper) {
        this.versionMapper = versionMapper;
    }
}
