import Vue from 'vue';
import {userInfo} from '@/service/api'
import config from '@/../config';
//状态
const state={
    user: {},
    userCard: {},
    member: [],
    registerUrl: [],
}

//getters
const getters={
    user:state=>state.user,
    userCard:state=>state.userCard,
    member:state=>state.member,
    registerUrl:state=> config.url.registerUrl+'/register?code='+state.registerUrl
}

//actions
const actions={
	setUserInfo({commit},name){
        userInfo().then(res=>{
            const {code,message,row} = res.data;
            if(code === '200'){
                console.log('33');
                commit('setUserInfo',{
                    user: row,
                    userCard: row.userCard,
                    member: row.bankAccounts,
                    registerUrl: row.userCard.inviteCode
                })
            }
        })
	},
}

//mutations
const mutations={
	setUserInfo(state,userInfo){
		Object.assign(state,userInfo);
	},
	
}

export default{
	namespaced:true,
	state,
	getters,
	actions,
	mutations
}