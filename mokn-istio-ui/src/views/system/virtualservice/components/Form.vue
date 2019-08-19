<template>
	<section>
        <!--直接发布页面-->
	    <el-dialog  :title="formTitle" :visible.sync="formVisible"  :before-close="formClose">
            <el-form :model="form" label-width="100px" ref="form" size="small">
                <el-form-item label="" prop="switchType">
                          <el-switch 
                           v-model="form.switchType"
                           active-color="#13ce66"
                           active-text="网关"
                           inactive-text="服务">
                        </el-switch>
                </el-form-item> 
                <el-card class="box-card" v-if="!form.switchType">
                   <div slot="header" class="clearfix">
                       服务
                   </div>
                <el-form-item id="service" label="应用服务">
                    <el-cascader
                        :props="namespacessProps"
                        :options="namespacess"
                        v-model="form.services">
                    </el-cascader>
                  </el-form-item>   
                <el-form-item label="版本Subset" prop="subset">
                   <el-input type="text" v-model="form.subset" placeholder=""></el-input>
                </el-form-item> 
                </el-card>
                <br/>
                <el-card class="box-card" v-if="form.switchType">
                   <div slot="header" class="clearfix">
                       网关
                   </div>
                 <el-form-item label="网关" >
                        <el-select v-model="form.gatewayName" placeholder="请选择">
                      <el-option
                        v-for="item in gatewayAlls"
                        :key="item.name"
                        :label="item.name"
                        :value="item.name">
                      </el-option>
                    </el-select>
                </el-form-item> 
                <el-form-item id="service" label="应用服务">
                    <el-cascader
                        :props="namespacessServiceProps"
                        :options="namespacessService"
                        v-model="form.services">
                    </el-cascader>
                </el-form-item>   
                <el-form-item label="域名" prop="host">
                   <el-input type="text" v-model="form.host" placeholder=""></el-input>
                </el-form-item> 
                <el-form-item label="端口" prop="routeNumber">
                   <el-input-number v-model="form.routeNumber" placeholder=""></el-input-number>
                </el-form-item> 
                </el-card>
                
  
            </el-form>
	        <div slot="footer" class="dialog-footer">
				<el-button type="info" @click="formClose" size="small">取消</el-button>
				<el-button type="primary" @click="formSubmit" :loading="formLoading" size="small">提交</el-button>
		    </div>
	    </el-dialog>
        <!--操作按钮组部分-->
        <el-col :span="24">
          <el-col :span="23">
            <el-button type="primary" @click="formShow" icon="el-icon-upload" size="small">创建</el-button>

          </el-col>
         
        </el-col>
   <el-col :span="24">
      &nbsp;
   </el-col>
	</section>
</template>

<script>

import util from '@/common/util';
import store from '@/store'
//引用vuex的导入语法糖功能
import {createNamespacedHelpers} from 'vuex';
import {namespaceList,gatewayAll,virtualServiceCreate} from '@/service/api';
const {mapGetters,mapActions}=createNamespacedHelpers('system/virtualservice');
	export default{
		name:'VirtualServiceForm',
		//默认数据state
		data(){
			return{
                namespacess:[],
                namespacessProps:{
                   label:'name',
                   value:'name',
                   children:'services'
                },
                namespacessService:[],
                namespacessServiceProps:{
                   label:'name',
                   value:'name',
                   children:'services'
                },
                gatewayAlls:[],
				formTitle:'',
                formVisible:false,
                formLoading:false,
				form:{
                    switchType:false,
                    namespace:'',
                    subset:'',
                    gatewayName:'',
                    host:'',
                    routeHost:'',
                    routeNumber:80,
                    services:[],
                    gateways:[]
                }
			}
		},
		//计算属性
		computed:{
			//导入store的getters属性
			...mapGetters(['selects']),
		},
		methods:{
			//导入actions方法
            ...mapActions(['getVirtualServices','listLoading']),
            getNamespacess() {
              let param={
              };
              namespaceList(param).then((res)=>{
                  this.namespacess=res.data;
                  this.namespacessService=res.data;
              });
            },
            getGatewayAll(){
              let param={
              };
              gatewayAll(param).then((res)=>{
                  this.gatewayAlls=res.data.rows;
                  console.log(JSON.stringify(this.gatewayAlls));
              });
            },
            switchChane(){

            },
            formShow(){
                this.formVisible=true;
                this.formTitle="创建";
            },
            formReset(){
                this.form.switchType=false;
                this.form.namespace='';
                this.form.subset='';
                this.form.gatewayName='';
                this.form.host='';
                this.form.routeHost='';
                this.form.routeNumber=80;
                this.form.services=[];
                this.form.gateways=[];
            },
            formClose(){
                this.formReset();
                this.formVisible=false;
            },
            formSubmit(){
                this.$refs.form.validate((valid)=>{
					if(valid){
                        if(!this.form.switchType){
                           if(this.form.services.length==0){
                             this.$message({
										message:'请选择应用服务',
										type:'error'
                           });
                           return;
                           }
                        }else{
                           if(this.form.gatewayName==''){
                             this.$message({
										message:'请选择网关',
										type:'error'
                           });
                           return;
                           }
                           if(this.form.services.length==0){
                             this.$message({
										message:'请选择应用服务',
										type:'error'
                           });
                           return;
                           }
                        }
                
                var createType='';
                var namespace='';
                var name='';
                var host='';
                var routeHost='';
                if(this.form.switchType){
                    createType="gateway";
                    namespace=this.form.services[0];
                    name=this.form.gatewayName;
                    host=this.form.host;
                    routeHost=this.form.services[1];
                }else{
                    createType="default";
                    namespace=this.form.services[0];
                    name=this.form.services[1];
                    host=this.form.services[1];
                    routeHost=this.form.services[1];
                }
                 var param={
                     createType:createType,
                     namespace:namespace,
                     name:name,
                     subset:this.form.subset,
                     host:host,
                     gatewayName:name,
                     routeHost:routeHost,
                     routeNumber:this.form.routeNumber
                 };
                virtualServiceCreate(param).then((data)=>{
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
                                    this.formReset();
                                    this.formVisible=false;
                                    this.getVirtualServices();
								}
							});
                    }
                });
            }
		},
		mounted(){
            this.getNamespacess();
            this.getGatewayAll();
		}
	}

</script>
<style>

</style>
