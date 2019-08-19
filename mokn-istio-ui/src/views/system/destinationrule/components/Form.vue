<template>
	<section>
        <!--直接发布页面-->
	    <el-dialog  :title="formTitle" :visible.sync="formVisible"  :before-close="formClose">
            <el-form :model="form" label-width="100px" :rules="formRules" ref="form" size="small">
                <el-form-item label="应用服务">
                    <el-cascader props=""
                        :props="cascaderProps"
                        :options="namespacess"
                        v-model="form.app"
                        @change="formItemsSelectApp(o.key,o.app)">
                    </el-cascader>
                  </el-form-item>
          <el-form-item label="版本Subset" prop="version">
                   <el-input type="text" auto-complete="off" v-model="form.version"></el-input>
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
import {namespaceList,destinationruleCreate} from '@/service/api';
const {mapGetters,mapActions}=createNamespacedHelpers('system/destinationrule');
	export default{
		name:'DestinationRuleForm',
		//默认数据state
		data(){
			return{
                namespacess:[],
                cascaderProps:{
                   label:'name',
                   value:'name',
                   children:'services'
                },
				formTitle:'',
                formVisible:false,
                formLoading:false,
				form:{
                    namespace:'',
                    name:'',
                    app:[]
                },
                formRules:{
                    version:[{required:true,message:'请输入版本号Subset',trigger:'blur'}]
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
            ...mapActions(['getDestinationRules','listLoading']),
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
                this.form.versionTitle='';
                this.form.app=[],
                this.form.version='';
            },
            formClose(){
                this.formReset();
                this.formVisible=false;
            },
            formSubmit(){
                this.$refs.form.validate((valid)=>{
					if(valid){
                 if(this.form.app.length==0){
                   this.$message({
										message:'请选择应用服务',
										type:'error'
                  });
                  return;
                 }
                 var param={
                     namespace:this.form.app[0],
                     name:this.form.app[1],
                     version:this.form.version
                 };
                destinationruleCreate(param).then((data)=>{
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
                                    this.getDestinationRules();
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
