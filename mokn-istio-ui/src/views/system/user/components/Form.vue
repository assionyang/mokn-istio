<template>
	<section>
        <!--直接发布页面-->
	    <el-dialog  :title="formTitle" :visible.sync="formVisible">
            <el-form :model="form" label-width="100px" :rules="formRules" ref="form" size="small">
                <el-form-item label="用户名" prop="username">
                   <el-input type="text" auto-complete="off" v-model="form.username"></el-input>
                </el-form-item> 
                <el-form-item label="密码" prop="password">
                   <el-input type="password" auto-complete="off" v-model="form.password"></el-input>
                </el-form-item> 
                <el-form-item label="姓名" prop="name">
                   <el-input type="text" auto-complete="off" v-model="form.name"></el-input>
                </el-form-item> 
                <el-form-item label="角色" prop="role" >
                        <el-select v-model="form.role" placeholder="Select">
                      <el-option
                        v-for="item in roles"
                        :key="item.key"
                        :label="item.label"
                        :value="item.value">
                      </el-option>
                    </el-select>
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
            <el-button type="primary" @click="formShow" icon="el-icon-upload" size="small">添加用户</el-button>

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
import {userCreate} from '@/service/api';
//引用vuex的导入语法糖功能
import {createNamespacedHelpers} from 'vuex';
import {namespaceList,versionCreate} from '@/service/api';
const {mapGetters,mapActions}=createNamespacedHelpers('system/users');
	export default{
		name:'UsersForm',
		//默认数据state
		data(){
			return{
                namespacess:[],
				formTitle:'',
                formVisible:false,
                formLoading:false,
				form:{
                    username:'',
                    password:'',
                    name:'',
                    role:''
                },
                roles:[
                    {key:'1',label:'管理员',value:'admin'},
                    {key:'2',label:'发布员',value:'release'},
                    {key:'3',label:'审计员',value:'audit'}
                ],
                formRules:{
                    username:[{required:true,message:'请输入用户名',trigger:'blur'}],
                    password:[{required:true,message:'请输入密码',trigger:'blur'}],
                    name:[{required:true,message:'请输入姓名',trigger:'blur'}],
                    role:[{required:true,message:'请选择角色',trigger:'blur'}]
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
            ...mapActions(['getUsers','listLoading']),
            formShow(){
                this.formVisible=true;
                this.formTitle="添加用户";
            },
            formReset(){
                this.form.versionTitle='';
                this.form.username='';
                this.form.password='';
                this.form.name='';
                this.form.role='';
            },
            formClose(){
                this.formReset();
                this.formVisible=false;
            },
            formSubmit(){
                this.$refs.form.validate((valid)=>{
					if(valid){
                       
                 var user={
                     username:this.form.username,
                     password:this.form.password,
                     name:this.form.name,
                     role:this.form.role
                 };
                userCreate(user).then((data)=>{
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
                                    this.getUsers();
								}
							});
                    }
                });
            }
		},
		mounted(){

		}
	}

</script>
<style>

</style>
