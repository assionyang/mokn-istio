import Vue from 'vue';
import Vuex from 'vuex';
import AppStore from './modules/AppStore';
import UserStore from './modules/system/UserStore';
import UserInfoStore from './modules/system/UserInfoStore';
import UsersStore from './modules/system/UsersStore';
import versionStore from './modules/system/versionStore';
import DeploymentStore from './modules/system/DeploymentStore';
import PodStore from './modules/system/PodStore';
import ConfigMapStore from './modules/system/ConfigMapStore';
import ServiceStore from './modules/system/ServiceStore';
import VirtualServiceStore from './modules/system/VirtualServiceStore';
import DestinationRuleStore from './modules/system/DestinationRuleStore';
import GatewayStore from './modules/system/GatewayStore';
import K8sConfigMapStore from './modules/system/K8sConfigMapStore';
import 'babel-polyfill';

Vue.use(Vuex);

const store=new Vuex.Store({
	strict: false, //开启严格模式，state的变更只能通过mutation提交，不然报异常
    modules:{
        'system/user':UserStore,
        'system/userInfo':UserInfoStore,
        'system/version':versionStore,
        'system/users':UsersStore,
        'system/deployment':DeploymentStore,
        'system/pod':PodStore,
        'system/service':ServiceStore,
        'system/configmap':ConfigMapStore,
        'system/virtualservice':VirtualServiceStore,
        'system/destinationrule':DestinationRuleStore,
        'system/gateway':GatewayStore,
        'system/k8sconfigmap':K8sConfigMapStore,
        'app':AppStore,
    }
});

export default store;
