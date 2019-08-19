import Vue from 'vue';

//状态
const state={
	logoLongName:'Istio 运维平台',//宽菜单LOGO文本
	logoMiniName:'O',//窄菜单LOGO文本
	collapsed:false,//菜单折叠状态
    sysloading:false//系统加载状态
}

//getters
const getters={
	logoLongName:state=>state.logoLongName,
	logoMiniName:state=>state.logoMiniName,
	collapsed:state=>state.collapsed,
	sysloading:state=>state.sysloading
}

//actions
const actions={
	setLogoLongName({commit},name){
		commit('setLogoLongName',name);
	},
	setLogoMiniName({commit},name){
		commit('setLogoMiniName',name);
	},
	setCollapsed({commit},collapsed){
		commit('setCollapsed',collapsed);
	},
	setSysLoading({commit},sysloading){
		commit('setSysLoading',sysloading);
	}
}

//mutations
const mutations={
	setLogoLongName(state,name){
		state.logoLongName=name;
	},
	setLogoMiniName(state,name){
		state.logoMiniName=name;
	},
	setCollapsed(state,collapsed){
		state.collapsed=collapsed;
	},
	setSysLoading(state,sysloading){
		state.sysloading=sysloading;
	}
}

export default{
	namespaced:true,
	state,
	getters,
	actions,
	mutations
}