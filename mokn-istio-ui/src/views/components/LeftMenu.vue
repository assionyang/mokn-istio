<template>

  <el-menu :collapse="collapsed"
    router
    unique-opened
      :default-active="active"
      @open="handleOpen"
      @close="handleClose"
      @select="menuActive"
      background-color="#031529"
      style="border-right:0px;"
      text-color="#b7bcc2"
      active-text-color="#fff"
      >

      <el-menu-item index="/dashboard">
        <i class="el-icon-menu"></i>
        <span slot="title">Dashboard</span>
      </el-menu-item>

      <el-submenu index="0" key="0">
      <template slot="title">
          <i class="el-icon-tickets"></i>
          <span>K8s资源</span>
        </template>
        <el-menu-item index="/namespaces" key="namespaces">K8s Namespace</el-menu-item>
        <el-menu-item index="/deployment" key="deployment">K8s Deployment</el-menu-item>
        <el-menu-item index="/pod" key="pod">K8s Pod</el-menu-item>
        <el-menu-item index="/service" key="service">K8s Service</el-menu-item>
        <el-menu-item index="/configmap" key="configmap">K8s ConfigMap</el-menu-item>
      </el-submenu>
      <el-submenu index="10" key="10">
      <template slot="title">
          <i class="el-icon-document"></i>
          <span>Istio资源</span>
        </template>
        <el-menu-item index="/gateway" key="gateway">Istio Gateway</el-menu-item>
        <el-menu-item index="/destinationrule" key="destinationrule">Istio DestinationRule</el-menu-item>
        <el-menu-item index="/virtualservice" key="virtualservice">Istio VirtualService</el-menu-item>
      </el-submenu>
      <el-submenu index="11" key="11">
      <template slot="title">
          <i class="el-icon-edit-outline"></i>
          <span>配置管理</span>
        </template>
        <el-menu-item index="/k8sconfigmap" key="gateway">ConfigMap</el-menu-item>
      </el-submenu>

      <el-submenu index="1" key="1">
      <template slot="title">
          <i class="el-icon-share"></i>
          <span>流量治理</span>
        </template>
        <el-menu-item index="/graydeploy" key="graydeploy">灰度发布</el-menu-item>
        <el-menu-item index="/routelimit" key="routelimit">熔断限流</el-menu-item>
        <el-menu-item index="/faultinjection" key="faultinjection">故障注入</el-menu-item>
      </el-submenu>
      <el-submenu index="2" key="2">
      <template slot="title">
          <i class="el-icon-refresh"></i>
          <span>流量监控</span>
        </template>
        <el-menu-item index="/flowtrack" key="flowtrack">流量追踪</el-menu-item>
      </el-submenu>
	   <el-submenu index="3" key="3">
      <template slot="title">
          <i class="el-icon-upload"></i>
          <span>Istio设置</span>
        </template>
        <el-menu-item index="/istiosetting" key="istiosetting">参数配置</el-menu-item>
      </el-submenu>
    <el-submenu index="4">
      <template slot="title">
        <i class="el-icon-setting"></i>
        <span slot="title">系统管理</span>
      </template>
        <el-menu-item index="/user" key="user">用户</el-menu-item>
    </el-submenu>
  </el-menu>
</template>


<script>
import {getMenus} from '../../service/api';
  export default {
  	name:'LeftMenu',
  	data(){
  		return{
        menus:[],
        active:''
  		}
  	},
  	props:['collapsed'],
    methods: {
      handleOpen(key, keyPath) {
        console.log(key, keyPath);
      },
      handleClose(key, keyPath) {
        console.log(key, keyPath);
      },
      getMenus(){
        let user=JSON.parse(sessionStorage.getItem('user'));
        if(user){
          this.menus=user.menus;
        }
      },
      menuActive(index,indexPath){

        if(this.active == ''){
          this.active = this.$route.path;
        }else{
          this.active = index;
          // console.log(index,this.active)
        }
      }
    },
    mounted(){
      this.getMenus();
      this.menuActive();
    }
  }
</script>
<style>
  ul.el-menu > li > ul > li.is-active {
    background: rgb(62,144,254) !important;
  }

  ul.el-menu > li.is-active {
    background:rgb(62,144,254) !important;
  }
  ul.el-menu > li > ul > li {
    background: rgb(1,12,23) !important;
  }
  ul.el-menu > li > ul > li .el-submenu__title {
    background: rgb(1,12,23) !important;
  }
</style>
