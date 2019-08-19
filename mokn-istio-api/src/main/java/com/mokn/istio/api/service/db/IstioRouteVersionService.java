package com.mokn.istio.api.service.db;

import com.mokn.istio.api.model.db.IstioRouteVersion;
import com.mokn.istio.api.model.em.VersionStatusEnum;

import java.util.List;

public interface IstioRouteVersionService {
    boolean create(IstioRouteVersion version);
    boolean update(IstioRouteVersion version);
    IstioRouteVersion getVersion(Long sysno);
    List<IstioRouteVersion> listVersion(IstioRouteVersion condition);
    boolean action(Long sysno, VersionStatusEnum versionStatus);
}
