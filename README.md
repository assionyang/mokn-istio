# Kubernetes&Istio运维平台

# 功能列表
* Kubernetes资源管理
* Istio资源管理
* 配置管理（支持多版本回滚）
* 流量治理
    * 灰度发布（支持回滚）
        * 直接发布
        * 流量权重发布
        * 金丝雀发布
    * 熔断&限流设置
    * 故障注入设置
* 流量监控（暂未实现）
* Istio基本配置
* 系统用户管理

# 相关技术栈
> Java <br/>
> SpringBoot <br/>
> MySQL <br/>
> Vue <br/>
> ElementUI <br/>
> Kubenetes API
> Istio Kubenetes CRD

# 安装及运行
> 
>
>


# 项目目录结构
* database - SQL脚本
* docker-k8s-istio-template - 项目发布模板
* mokn-istio-api - Java API目录
    * src - 代码文件
      * java
        * com.mokn.istio.api 包
          * common 工具类
          * service - 配置类
          * controller - 控制器
          * jobs - Job类
          * mapper - MyBatis Mapper接口
          * model - 实体&领域&枚举类
          * service - 服务层
    * resource - 配置文件
      * mapping - MyBatis XML
      * application.yaml - 主配置文件
      * application-dev.yaml - 开发环境配置，参考主配置文件自行定义
* mokn-istio-ui - Vue前端UI目录

# 演示

演示地址：<http://xxx>

![](http://istio.mokn.com/pic/5.png)

![](http://istio.mokn.com/pic/6.png)

![](http://istio.mokn.com/pic/1.png)

![](http://istio.mokn.com/pic/2.png)

![](http://istio.mokn.com/pic/3.png)

![](http://istio.mokn.com/pic/4.png)
