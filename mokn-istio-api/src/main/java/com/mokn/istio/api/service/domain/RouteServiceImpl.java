package com.mokn.istio.api.service.domain;

import com.mokn.istio.api.model.db.IstioRouteVersion;
import com.mokn.istio.api.service.db.IstioRouteVersionService;
import com.mokn.istio.api.service.k8s.VirtualServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RouteServiceImpl implements RouteService {

    @Autowired
    private VirtualServiceService virtualServiceService;
    @Autowired
    private IstioRouteVersionService istioRouteVersionService;

    /**
     * 创建
     * @param version
     * @return
     */
    public boolean create(IstioRouteVersion version){

        return Boolean.TRUE;
    }

    /**
     * 部署
     * @param version
     * @return
     */
    public boolean deploy(IstioRouteVersion version){

        return Boolean.TRUE;
    }

    /**
     * 发布
     * @param version
     * @return
     */
    public boolean release(IstioRouteVersion version){

        return Boolean.TRUE;
    }

    /**
     * 回滚
     * @param version
     * @return
     */
    public boolean rollback(IstioRouteVersion version){

        return Boolean.TRUE;
    }
}
