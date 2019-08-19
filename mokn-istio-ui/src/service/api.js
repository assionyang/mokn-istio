import axios from 'axios';

const root = process.env.API_ROOT;

//axios默认配置
axios.defaults.baseURL = root;
axios.defaults.timeout = 100000;
// axios.defaults.withCredentials=true;

// http request 拦截器
axios.interceptors.request.use(
    config => {
        let token=sessionStorage.getItem('token');
        if(token!=null && token!=''){
          config.headers.Authorization = token;
        }
        return config;
    },
    err => {
        return Promise.reject(err);
    }
);
axios.interceptors.response.use(
    response => {
        // console.log(JSON.stringify(response.data));
        if(response.data.code=="400"){
            window.location='/#/login';
        }else{
            return response;
        }

    },
    error => {
        if (error.response) {

            switch (error.response.status) {
                case 401:
                    this.$router.push({path:'/login'});
            }
        }
        return Promise.reject(error.response.data)
    }
);


//登录
export const requestLogin = params => { return axios.post(`/user/login`, params).then(res => res.data);};
//User LIST
export const userList = params => { return axios.get(`/user/list`, { params: params }); };
//User 创建
export const userCreate = params => { return axios.post(`/user/create`, params).then(res => res.data);};
//User 删除
export const userDelete = params => { return axios.post(`/user/delete`, params).then(res => res.data);};
//User 修改密码
export const userUpdatePassword = params => { return axios.post(`/user/update/password`, params).then(res => res.data);};
//Dashboard
export const dashboardData = params => { return axios.get(`/dashboard/data`, { params: params }); };
//所有命名空间
export const namespaceList = params => { return axios.get(`/namespaces/all`, { params: params }); };
//Deployment LIST
export const deploymentList = params => { return axios.get(`/deployment/list`, { params: params }); };
//Deployment 删除
export const deploymentDelete = params => { return axios.post(`/deployment/delete`, params).then(res => res.data);};
//Pod LIST
export const podList = params => { return axios.get(`/pod/list`, { params: params }); };
//Pod 删除
export const podDelete = params => { return axios.post(`/pod/delete`, params).then(res => res.data);};
//Service LIST
export const serviceList = params => { return axios.get(`/service/list`, { params: params }); };
//Service 删除
export const serviceDelete = params => { return axios.post(`/service/delete`, params).then(res => res.data);};
//ConfigMap LIST
export const configmapList = params => { return axios.get(`/configmap/list`, { params: params }); };
//ConfigMap 删除
export const configmapDelete = params => { return axios.post(`/configmap/delete`, params).then(res => res.data);};
//VirtualService LIST
export const virtualServiceList = params => { return axios.get(`/virtualservice/list`, { params: params }); };
//VirtualService 删除
export const virtualServiceDelete = params => { return axios.post(`/virtualservice/delete`, params).then(res => res.data);};
//VirtualService 创建
export const virtualServiceCreate = params => { return axios.post(`/virtualservice/create`, params).then(res => res.data);};
//VirtualService 故障注入设置
export const virtualServiceFailSet = params => { return axios.post(`/virtualservice/fail/set`, params).then(res => res.data);};
//Gateway LIST
export const gatewayList = params => { return axios.get(`/gateway/list`, { params: params }); };
//Gateway LIST All
export const gatewayAll = params => { return axios.get(`/gateway/all`, { params: params }); };
//Gateway 删除
export const gatewayDelete = params => { return axios.post(`/gateway/delete`, params).then(res => res.data);};
//Gateway 创建
export const gatewayCreate = params => { return axios.post(`/gateway/create`, params).then(res => res.data);};
//DestinationRule LIST
export const destinationruleList = params => { return axios.get(`/destinationrule/list`, { params: params }); };
//DestinationRule 删除
export const destinationruleDelete = params => { return axios.post(`/destinationrule/delete`, params).then(res => res.data);};
//DestinationRule 创建
export const destinationruleCreate = params => { return axios.post(`/destinationrule/create`, params).then(res => res.data);};
//DestinationRule 设置熔断
export const destinationruleSetFuse = params => { return axios.post(`/destinationrule/fuse/set`, params).then(res => res.data);};
//istio 设置 查询
export const settingLoad = params => { return axios.get(`/istio/setting/load`, { params: params }); };
//istio 设置 入口网关类型设置
export const settingSet = params => { return axios.post(`/istio/setting/set`, params).then(res => res.data);};
//配置管理 ConfigMap LIST
export const k8sconfList = params => { return axios.get(`/conf/list`, { params: params }); };
//配置管理 ConfigMap 创建
export const k8sconfCreate = params => { return axios.post(`/conf/create`, params).then(res => res.data);};
//配置管理 ConfigMap 修改
export const k8sconfUpdate = params => { return axios.post(`/conf/update`, params).then(res => res.data);};
//配置管理 ConfigMap 回滚
export const k8sconfRollback = params => { return axios.post(`/conf/rollback`, params).then(res => res.data);};


//灰度发布列表
export const versionList = params => { return axios.get(`/istio/route/version/list`, { params: params }); };
//创建灰度发布
export const versionCreate = params => { return axios.post(`/istio/route/version/create`, params).then(res => res.data);};
//灰度命令
export const versionAction = params => { return axios.post(`/istio/route/version/action`, params).then(res => res.data);};








