<template>
  <el-form :model="loginForm" :rules="loginRules" ref="loginForm" label-position="left" label-width="0px" class="login-container">
    <h3 class="title text-center">Istio运维平台</h3>
    <el-alert style="margin-bottom:10px;"
    title="欢迎使用 Istio运维平台"
    type="success">
  </el-alert>
     <el-form-item prop="loginuser">
      <el-input type="text" v-model="loginForm.loginuser" auto-complete="off" placeholder="帐号"
                @keyup.enter.native="loginIn" :autofocus="true" prefix-icon="el-icon-edit"></el-input>
    </el-form-item>
    <el-form-item prop="loginpwd">
      <el-input type="password" v-model="loginForm.loginpwd" auto-complete="off" placeholder="密码"
                @keyup.enter.native="loginIn" prefix-icon="el-icon-star-on"></el-input>
    </el-form-item>
    <el-form-item style="width:100%;margin-top:10px;">
      <el-button type="primary" style="width:100%;" @click.native.prevent="loginIn" :loading="logining" icon="el-icon-upload">登 录</el-button>
    </el-form-item>
  </el-form>
</template>
<script>
  import {requestLogin,getvalidateCode} from '../service/api';
  export default {
    name: 'Login',
    data() {
      return {
        logining: false,
        valiodateLoading:false,
        validateShow:false,
        codePic:'',
        loginForm: {
          loginuser: '',
          loginpwd: '',
          validateCode:''
        },
        openState: false,
        loginRules: {
          loginuser: [
            {required: true, message: '请输入登录帐号', trigger: 'blur'},
          ],
          loginpwd: [
            {required: true, message: '请输入登录密码', trigger: 'blur'},
          ]
        },
      };
    },
    methods: {
      resetForm() {
        this.$refs.loginForm.resetFields();
      },
      picValiateCode(){
        if(this.loginForm.loginuser==null ||this.loginForm.loginuser==''){
          this.$message({
                  message: "请先输入登录账号",
                  type: 'error'
                });
          return false;
        }
        let param={"userName":this.loginForm.loginuser}
        this.valiodateLoading=true;
        getvalidateCode(param).then(res=>{
          let {message, code, row} = res;
          if(code=="200"){
            this.validateShow=true;
            this.codePic='data:image/png;base64,'+row;
          }else{
            this.$message({
                  message: message,
                  type: 'error'
                });
          }
        })
      },
      menuchilren(data, pid) {
        var result = [], temp;
        for (var i in data) {

          if (data[i].parent == pid) {
            data[i] = data[i];
            result.push(data[i]);
            temp = this.menuchilren(data, data[i].id);

            if (temp.length > 0) {
              data[i].children = temp;
            } else {
              data[i].children = [];
            }
          }
        }
        return result;
      },
      loginIn(ev) {
        console.log(this.loginForm)
        this.$refs.loginForm.validate((valid) => {
          if (valid) {
            this.logining = true;
            var loginParams = {username: this.loginForm.loginuser, password: this.loginForm.loginpwd};
            requestLogin(loginParams).then(data => {
              this.logining = false;
              let {message, code, row} = data;
              if (code != 200) {
                this.$message({
                  message: message,
                  type: 'error'
                });
              } else {
                sessionStorage.setItem('token', row.token);
                sessionStorage.setItem('user', JSON.stringify(data.row));
                sessionStorage.setItem('name',data.row.name);
                
                this.$router.push({path: '/'});
              }
            });
          } else {
            return false;
          }
        });
      }
    }
  }
</script>

<style>
  .login-container {
    /*box-shadow: 0 0px 8px 0 rgba(0, 0, 0, 0.06), 0 1px 0px 0 rgba(0, 0, 0, 0.02);*/
    -webkit-border-radius: 5px;
    border-radius: 5px;
    -moz-border-radius: 5px;
    background-clip: padding-box;
    margin: 0 auto;
    max-width: 350px;
    width: 100%;
    padding: 35px 35px 15px 35px;
    background: #fff;
    border: 1px solid #eaeaea;
    box-shadow: 0 0 25px #cac6c6;
    box-sizing: border-box;
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%,-50%); 
  }
  .title {
      margin: 0px auto 40px auto;
      text-align: center;
      color: #505458;
    }
    .remember {
      margin: 0px 0px 35px 0px;
    }
  .text-center {
    text-align: center;
  }
  
</style>
