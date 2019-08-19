<template>
	<section>
        <!--直接发布页面-->
	    <el-dialog  :title="formTitle" :visible.sync="formVisible"  :before-close="formClose">
            <el-form :model="form" label-width="100px" :rules="formRules" ref="form" size="small">
                <el-form-item label="命名空间" >
                        <el-select v-model="form.namespace" placeholder="Select">
                      <el-option
                        v-for="item in namespacess"
                        :key="item.uid"
                        :label="item.name"
                        :value="item.name">
                      </el-option>
                    </el-select>
          </el-form-item> 
           <el-form-item label="名称" prop="version">
                   <el-input type="text" auto-complete="off" v-model="form.name"></el-input>
                </el-form-item> 
          <el-form-item label="域名" prop="version">
                   <el-input type="text" auto-complete="off" v-model="form.host"></el-input>
                </el-form-item> 
             <el-form-item label="端口" prop="version">
                   <el-input type="text" auto-complete="off" v-model="form.port"></el-input>
                </el-form-item> 
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
import {namespaceList,gatewayCreate} from '@/service/api';
const {mapGetters,mapActions}=createNamespacedHelpers('system/gateway');
	export default{
		name:'GatewayForm',
		//默认数据state
		data(){
			return{
                namespacess:[],
				formTitle:'',
                formVisible:false,
                formLoading:false,
				form:{
                    namespace:'',
                    name:'',
                    host:'',
                    port:80
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
            ...mapActions(['getGateways','listLoading']),
            getNamespacess() {
              let param={
              };
              namespaceList(param).then((res)=>{
                  this.namespacess=res.data;
              });
            },
            formShow(){
                this.formVisible=true;
                this.formTitle="添加用户";
            },
            formReset(){
                    this.form.namespace='';
                    this.form.name='';
                    this.form.host='';
                    this.form.port=80;
            },
            formClose(){
                this.formReset();
                this.formVisible=false;
            },
            formSubmit(){
                this.$refs.form.validate((valid)=>{
					if(valid){
                 if(this.form.namespace==''){
                   this.$message({
										message:'请选择命名空间',
										type:'error'
                  });
                  return;
                 }
                 var param={
                     namespace:this.form.namespace,
                     name:this.form.name,
                     host:this.form.host,
                     port:this.form.port
                 };
                gatewayCreate(param).then((data)=>{
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
                                    this.getGateways();
								}
							});
                    }
                });
            }
		},
		mounted(){
           this.getNamespacess();
		}
	}

</script>
<style>

</style>
