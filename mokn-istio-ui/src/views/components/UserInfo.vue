<template>
  <section>
    <!--用户信息下拉菜单-->
        <el-dropdown @command="dropdownCommand">
            <span class="el-dropdown-link" style="color:black;">操作<i class="el-icon-arrow-down el-icon--right"></i></span>
            <el-dropdown-menu slot="dropdown">
                <el-dropdown-item command="password" >修改密码</el-dropdown-item>
                <el-dropdown-item command="logout" >退出登录</el-dropdown-item>
            </el-dropdown-menu>
        </el-dropdown>
        <el-dialog  title="修改密码" :visible.sync="formVisible">
            <el-form :model="form" label-width="100px" :rules="formRules" ref="form">
                <el-form-item label="新密码" prop="password">
                   <el-input type="password" auto-complete="off" v-model="form.password"></el-input>
                </el-form-item> 
            </el-form>
	        <div slot="footer" class="dialog-footer">
				<el-button type="info" @click="formClose">取消</el-button>
				<el-button type="primary" @click="formSubmit">提交</el-button>
		    </div>
	    </el-dialog>
       
  </section>
</template>

<style>
  .el-dropdown-link {
    cursor: pointer;
    color: #8a8f8e;
  }
  .el-icon-arrow-down {
    font-size: 12px;
  }
</style>

<script>
import {userUpdatePassword} from '@/service/api';
	export default{
        data(){
        return{
            name:'操作',
            formVisible:false,
            form:{
              passowrd:''
            },
            formRules:{
                    password:[{required:true,message:'请输入密码',trigger:'blur'}]
            }
        }
        },
        mounted(){
        
        },
        methods:{           
            dropdownCommand(command){
                if(command=="logout"){
                    this.$confirm('确认退出吗？', '提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }).then(() => {
                        sessionStorage.removeItem('token');
                        this.$router.push({path:'/login'});
                    }).catch(() => {});
                }else if(command=="password"){
                  this.formVisible=true;
                  this.formReset();
                }
            },
            path(url){
                window.open(url);
            },
            formReset(){
                this.form.password='';
            },
            formClose(){
                this.formReset();
                this.formVisible=false;
            },
            formSubmit(){
                this.$refs.form.validate((valid)=>{
					      if(valid){
    
                 var user={
                     password:this.form.password
                 };
                userUpdatePassword(user).then((data)=>{
						
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
								}
							});
                    }
                });
            }
        }
	}
</script>