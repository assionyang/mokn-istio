package com.mokn.istio.api.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mokn.istio.api.common.LoginPassport;
import com.mokn.istio.api.common.NumberKeyUtils;
import com.mokn.istio.api.model.db.IstioRouteVersion;
import com.mokn.istio.api.model.db.IstioRouteVersionItem;
import com.mokn.istio.api.model.domain.*;
import com.mokn.istio.api.model.em.VersionStatusEnum;
import com.mokn.istio.api.service.db.IstioRouteVersionService;
import com.mokn.istio.api.service.k8s.VirtualServiceService;
import com.mokn.istio.api.service.k8s.DeploymentService;
import com.mokn.istio.api.service.k8s.NamespaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/istio/route/version")
public class IstioRouteVersionController {

    @Autowired
    private IstioRouteVersionService versionService;
    @Autowired
    private NamespaceService namespaceService;
    @Autowired
    private DeploymentService deploymentService;
    @Autowired
    private VirtualServiceService virtualServiceService;

    @LoginPassport
    @GetMapping(value = "/list")
    public JsonResult<IstioRouteVersion> list(@RequestParam(value = "pageNo",required = false) Integer pageNo,
                                              @RequestParam(value = "pageSize",required = false) Integer pageSize,
                                              @RequestParam(value = "versionStatus",required = false) Integer versionStatus){
        JsonResult<IstioRouteVersion> result=new JsonResult<>();
        IstioRouteVersion condition=new IstioRouteVersion();
        if(versionStatus!=null){
            condition.setVersionStatus(versionStatus);
        }
        PageHelper.startPage(pageNo,pageSize);
        PageInfo<IstioRouteVersion> pageInfo=new PageInfo<>(versionService.listVersion(condition));
        return result.success(pageInfo.getList(),pageInfo.getTotal());
    }

    @LoginPassport(valid = true,role = "admin|release")
    @PostMapping(value = "/create")
    public JsonResult<String> create(@RequestBody RouteVersionDomain version){
        JsonResult result=new JsonResult();

        if(version==null){
            return result.fail("不能为空");
        }
        if(version.getItems()==null || version.getItems().size()==0){
            return result.fail("至少配置一条");
        }

        // 创建
        IstioRouteVersion routeVersion=new IstioRouteVersion();
        routeVersion.setVersionType(version.getVersionType().getValue());
        routeVersion.setVersionNo(NumberKeyUtils.timeNumber());
        routeVersion.setVersionTitle(version.getVersionTitle());
        routeVersion.setVersionRemark(version.getVersionRemark());
        routeVersion.setCreatedAt(new Date());
        routeVersion.setMemo("");
        routeVersion.setOperName("");
        routeVersion.setVersionStatus(VersionStatusEnum.CREATED.getValue());
        List<IstioRouteVersionItem> routeVersionItems=new ArrayList<>();
        for(RouteVersionItemDomain item:version.getItems()){
            if(namespaceService.getNamespaceDomain(item.getNamespace())==null){
                return result.fail(item.getNamespace()+"：命名空间不存在");
            }
            if(deploymentService.getDeploymentDomain(item.getNamespace(),item.getNameHost()+"-"+item.getSubsetNew())==null){
                return result.fail(item.getNamespace()+"/"+item.getNameHost()+"-"+item.getSubsetNew()+"：实例不存在");
            }
            VirtualServiceDomain virtualServiceDomain=virtualServiceService.getVirtualServiceDomain(item.getNamespace(),item.getNameHost());
            if(virtualServiceDomain==null){
                return result.fail(item.getNamespace()+"/"+item.getNameHost()+"：路由服务不存在");
            }
            if(virtualServiceDomain.getVersionType()==null || !virtualServiceDomain.getVersionType().equals("VirtualService_Default")){
                return result.fail(item.getNamespace()+"/"+item.getNameHost()+"：旧版本类型不是默认版本");
            }
            if(virtualServiceDomain.getVersion()==null || virtualServiceDomain.getVersion().equals("")){
                return result.fail(item.getNamespace()+"/"+item.getNameHost()+"：旧版本号找不到");
            }
            if(virtualServiceDomain.getVersion().equals(item.getSubsetNew())){
                return result.fail(item.getNamespace()+"/"+item.getNameHost()+"：旧版本号不能和新版本号相同");
            }
            IstioRouteVersionItem routeVersionItem=new IstioRouteVersionItem();
            routeVersionItem.setVersionType(routeVersion.getVersionType());
            routeVersionItem.setVersionStatus(routeVersion.getVersionStatus());
            routeVersionItem.setCreatedAt(routeVersion.getCreatedAt());
            routeVersionItem.setNamespace(item.getNamespace());
            routeVersionItem.setNameHost(item.getNameHost());
            routeVersionItem.setSubsetOld(virtualServiceDomain.getVersion());
            routeVersionItem.setSubsetNew(item.getSubsetNew());
            routeVersionItem.setWeight(item.getWeight());
            routeVersionItem.setHttpKey(item.getHttpKey());
            routeVersionItem.setHttpValue(item.getHttpValue());
            routeVersionItems.add(routeVersionItem);
        }
        routeVersion.setItems(routeVersionItems);

        if(versionService.create(routeVersion)){
            return result.success("创建成功",routeVersion.getVersionNo());
        }

        return result.fail("创建失败");
    }

    @LoginPassport(valid = true,role = "admin|release")
    @PostMapping(value = "/action")
    public JsonResult action(@RequestBody RouteVersionActionRequest version){
        JsonResult result=new JsonResult();
        if(versionService.action(version.getSysno(),version.getVersionStatus())){
            return result.success("成功");
        }else{
            return result.fail("失败");
        }
    }
}
