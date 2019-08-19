import Vue from 'vue'
import Router from 'vue-router'
import {routers} from './router'

Vue.use(Router);
const RouterConfig={
	routes:routers
}
export const router=new Router(RouterConfig);

//路由过滤，验证登录和访问权限
router.beforeEach((to,from,next)=>{
	if(to.path==='/404'){
		next();
		return;
	}
	let token=sessionStorage.getItem('token');
	next();
	if(!token && to.meta.isLogin && token!==""){
		next({path:'/login'});
	}else{
		next();
	}
})

