package com.mokn.istio.api.mapper;

import com.mokn.istio.api.model.db.IstioRouteVersion;
import com.mokn.istio.api.model.db.IstioRouteVersionItem;

import java.util.List;

public interface IstioRouteVersionMapper {
    Long insertVersion(IstioRouteVersion version);
    Long insertVersionItem(List<IstioRouteVersionItem> list);
    IstioRouteVersion selectVersionBySysno(Long sysno);
    List<IstioRouteVersionItem> selectVersionItemByVersionSysno(Long sysno);
    Long updateVersion(IstioRouteVersion version);
    Long updateVersionItem(IstioRouteVersionItem versionItem);
    List<IstioRouteVersion> selectVersion(IstioRouteVersion condition);
}
