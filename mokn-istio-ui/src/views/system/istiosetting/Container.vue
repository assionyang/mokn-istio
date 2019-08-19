<!--模板-->
<template>
    <section>
        <el-card class="box-card">
         <div slot="header" class="clearfix">
           <span>istio-ingressgateway 网关类型设置</span>
              
         </div>
            <el-switch 
                style="display: block"
                v-model="istioIngressGatewayTypeSwitch"
                active-color="#13ce66"
                inactive-color="#409EFF"
                active-text="节点端口"
                inactive-text="负载均衡"
                @change="istioIngressGatewayTypeSet()">
              </el-switch>
        </el-card>



    </section>
</template>
<!--JS-->
<script>
import {settingLoad,settingSet} from '@/service/api';
	export default{
		name:'IstioSetting',
		data(){
			return{
                istioIngressGatewayTypeSwitch:false
			}
		},
		//计算属性
		computed:{
		},
		//当前方法
		methods:{
           loadSetting(){
              let param={
              };
              settingLoad(param).then((res)=>{
                  if(res.data.row.ingreeGatewaySpecType=='NodePort'){
                      this.istioIngressGatewayTypeSwitch=true;
                  }else{
                      this.istioIngressGatewayTypeSwitch=false;
                  }
              });
           },
           istioIngressGatewayTypeSet(){
               var type='';
               if(this.istioIngressGatewayTypeSwitch==true){
                   type='NodePort';
               }else{
                   type='LoadBalancer';
               }
               console.log(type);
               var param={
                   settingType:'istio-ingressgateway-type',
                   ingreeGatewaySpecType:type
               }
               this.setSubmit(param);
           },
           setSubmit(param){
                settingSet(param).then((data)=>{
								this.formLoading=false;
								let {code,message}=data;
								if(code!=200){
									this.$message({
										message:message,
										type:'error'
									});
								}else{
									this.$message({
										message:message,
										type:'success'
                                    });
                                    this.loadSetting();
								}
							});
            },
		},
		//Vue勾子函数，重新渲染执行
		mounted(){
			this.loadSetting();
		}
	}
</script>