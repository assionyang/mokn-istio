import Main from '@/views/layout/Main'
import Login from '@/views/Login'

// 不作为Main组件的子页面展示的页面
export const loginRouter = {
    path: '/login',
    name: '登录',
    component: resolve => { require(['@/views/Login'], resolve); },
    meta:{isLogin:false,isAuth:false},
};
export const page404Router = {
    path: '*',
    name: '404',
    component: resolve => { require(['@/views/404'], resolve); },
    meta:{isLogin:false,isAuth:false}
};

//作为Main组件的子页面展示的页面
export const appRouter=[
    {
    	path:'/',
    	name:'System',
    	component:Main,
    	children:[
        	{path:'/',redirect:{path:'/dashboard'}},
            {path:'/dashboard',name:'Dashboard',component:resolve=>{require(['@/views/system/dashboard/Container'],resolve);},meta:{isLogin:true,isAuth:true}},
            {path:'/graydeploy',name:'灰度发布',component:resolve=>{require(['@/views/system/graydeploy/Container'],resolve);},meta:{isLogin:true,isAuth:true}},
            {path:'/namespaces',name:'命名空间',component:resolve=>{require(['@/views/system/namespaces/Container'],resolve);},meta:{isLogin:true,isAuth:true}},
            {path:'/deployment',name:'Deployments',component:resolve=>{require(['@/views/system/deployment/Container'],resolve);},meta:{isLogin:true,isAuth:true}},
            {path:'/pod',name:'Pod',component:resolve=>{require(['@/views/system/pod/Container'],resolve);},meta:{isLogin:true,isAuth:true}},
            {path:'/service',name:'Service',component:resolve=>{require(['@/views/system/service/Container'],resolve);},meta:{isLogin:true,isAuth:true}},
            {path:'/configmap',name:'Pod',component:resolve=>{require(['@/views/system/configmap/Container'],resolve);},meta:{isLogin:true,isAuth:true}},
            {path:'/destinationrule',name:'DestinationRule',component:resolve=>{require(['@/views/system/destinationrule/Container'],resolve);},meta:{isLogin:true,isAuth:true}},
            {path:'/virtualservice',name:'VirtualService',component:resolve=>{require(['@/views/system/virtualservice/Container'],resolve);},meta:{isLogin:true,isAuth:true}},
            {path:'/gateway',name:'Gateway',component:resolve=>{require(['@/views/system/gateway/Container'],resolve);},meta:{isLogin:true,isAuth:true}},
            {path:'/routelimit',name:'RouteLimit',component:resolve=>{require(['@/views/system/routelimit/Container'],resolve);},meta:{isLogin:true,isAuth:true}},
            {path:'/faultinjection',name:'FaultInjection',component:resolve=>{require(['@/views/system/faultinjection/Container'],resolve);},meta:{isLogin:true,isAuth:true}},
            {path:'/flowtrack',name:'FlowTrack',component:resolve=>{require(['@/views/system/flowtrack/Container'],resolve);},meta:{isLogin:true,isAuth:true}},
            {path:'/istiosetting',name:'IstioSetting',component:resolve=>{require(['@/views/system/istiosetting/Container'],resolve);},meta:{isLogin:true,isAuth:true}},
            {path:'/user',name:'User',component:resolve=>{require(['@/views/system/user/Container'],resolve);},meta:{isLogin:true,isAuth:true}},
            {path:'/k8sconfigmap',name:'K8sConfigMap',component:resolve=>{require(['@/views/system/k8sconfigmap/Container'],resolve);},meta:{isLogin:true,isAuth:true}}
      ]
    }
];

//导出
export const routers=[
    loginRouter,
    ...appRouter,
    page404Router
];
