<template>
	<section>
        <!--直接发布页面-->
	    <el-dialog  :title="formTitle" :visible.sync="formVisible" :before-close="formClose">
            <el-input type="text" placeholder="版本标题" auto-complete="off" v-model="form.versionTitle" size="small"></el-input><br/><br/>
            <el-form :model="form" :inline="true" label-width="100px" :rules="formRules" ref="form" size="small">
            
            <div v-for="o in form.items" :key="o">
	    	    <el-card class="box-card">
                  <div slot="header" class="clearfix">
                    <span>服务{{o.key}}</span>
                    <el-button style="float: right; padding: 3px 0" type="text" @click="formItemsRemove(o.key)">X</el-button>
                  </div>
                  <el-form-item label="应用服务">
                    <el-cascader props=""
                        :props="cascaderProps"
                        :options="namespacess"
                        v-model="o.app"
                        @change="formItemsSelectApp(o.key,o.app)">
                    </el-cascader>
                  </el-form-item>
                  <el-form-item label="新版本Subset" prop="userTitle">
                    <el-input type="text" auto-complete="off" v-model="o.subset"></el-input>
                  </el-form-item> 
                  <el-form-item label="流量权重" prop="userTitle" v-if="form.versionType=='WEIGHT'">
                    <el-input-number v-model="o.weight" :min="0" :max="100" label="描述文字"></el-input-number>
                  </el-form-item>
                  <el-form-item label="HTTP Key" prop="userTitle" v-if="form.versionType=='CANARY'">
                    <el-input type="text" auto-complete="off" v-model="o.httpKey"></el-input>
                  </el-form-item>
                  <el-form-item label="HTTP Value" prop="userTitle" v-if="form.versionType=='CANARY'">
                    <el-input type="text" auto-complete="off" v-model="o.httpValue"></el-input>
                  </el-form-item>
                </el-card><br/>
            </div>
            </el-form>
	        <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="formItemsAdd" size="small">添加服务</el-button>
				<el-button type="info" @click="formCancel" size="small">取消</el-button>
				<el-button type="success" @click="formSubmit" :loading="formLoading" size="small">提交</el-button>
		    </div>
	    </el-dialog>
        <!--操作按钮组部分-->
        <el-col :span="24">
          <el-col :span="23">
            <el-button type="primary" @click="formShow('RELEASE')" icon="el-icon-success" size="small">创建直接发布</el-button>
            <el-button type="warning" @click="formShow('WEIGHT')" icon="el-icon-share" size="small">创建流量权重</el-button>
            <el-button type="success" @click="formShow('CANARY')" icon="el-icon-upload" size="small">创建金丝雀</el-button>

          </el-col>
          <el-col :span="1" style="text-align: right;">
            <el-button @click="getVersions" icon="el-icon-refresh" size="mini"></el-button>
          </el-col>
        </el-col>
        <!--默认提示信息部分-->
        <el-col :span="24">
          &nbsp;
        </el-col>
	</section>
</template>

<script>

import util from '@/common/util';
import store from '@/store'
import {} from '@/service/api';
//引用vuex的导入语法糖功能
import {createNamespacedHelpers} from 'vuex';
import {namespaceList,versionCreate} from '@/service/api';
const {mapGetters,mapActions}=createNamespacedHelpers('system/version');
	export default{
		name:'VersionForm',
		//默认数据state
		data(){
			return{
                cascaderProps:{
                   label:'name',
                   value:'name',
                   children:'virtualServices'
                },
                namespacess:[],
				formTitle:'',
                formVisible:false,
                formLoading:false,
                formSubIndex:0,
                formSubs:[],
				form:{
                    versionType:'',
                    versionTitle:'',
                    versionRemark:'',
                    items:[],
                    itemIndex:0
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
            ...mapActions(['getVersions','listLoading']),
            getNamespacess() {
              let param={
              };
              namespaceList(param).then((res)=>{
                  this.namespacess=res.data;
              });
            },
            formShow(val){
                this.formVisible=true;
                this.form.versionType=val;
                if(val=='RELEASE'){
                    this.formTitle="直接发布";
                }else if(val=='WEIGHT'){
                    this.formTitle="流量权重发布";
                }else if(val=='CANARY'){
                    this.formTitle="金丝雀发布";
                }else{
                    this.formTitle="出错";
                }
            },
            formReset(){
                this.form.versionTitle='';
                this.form.versionRemark='';
                this.form.items=[];
                this.form.itemIndex=0;
            },
            formClose(){
                return;
                  this.$confirm('确认取消?', '提示', {
                                 confirmButtonText: '确定',
                                 cancelButtonText: '取消',
                                 type: 'warning'
                                }).then(() => {
                                    this.formReset();
                                    this.formVisible=false;
                                }).catch(() => {
                                                
                                });
            },
            formCancel(){
                this.formReset();
                this.formVisible=false;            
            },
            formItemsAdd(){
                this.form.itemIndex++;
                var item={key:this.form.itemIndex,app:['',''],subset:'',weight:0,httpKey:'',httpValue:''};
                this.form.items.push(item);
            },
            formItemsRemove(key){
                this.form.items.map((item,index)=>{
                    if(item.key==key){
                        this.form.items.splice(index,1);
                    }
                })
            },
            formItemsSelectApp(key,app){
                this.data.items.map((item,index)=>{
                    if(item.key==key){
                        item.app=app;
                    }
                })
            },
            formSubmit(){
                  this.$confirm('确认提交?', '提示', {
                                 confirmButtonText: '确定',
                                 cancelButtonText: '取消',
                                 type: 'warning'
                                }).then(() => {
                                    this.submit();
                                });
   
            },
            submit(){
                this.formLoading=true;
                 var version={
                     versionType:this.form.versionType,
                     versionTitle:this.form.versionTitle,
                     versionRemark:this.form.versionRemark,
                     items:[]
                 };
                 this.form.items.map((item,index)=>{
                    var versionItem={
                        versionType:version.versionType,
                        namespace:item.app[0],
                        nameHost:item.app[1],
                        subsetNew:item.subset,
                        weight:item.weight,
                        httpKey:item.httpKey,
                        httpValue:item.httpValue
                    };
                    version.items.push(versionItem);
                });
                
                if(version.versionTitle==''){
                    this.$message({
						message:"请输入版本标题",
						type:'error'
                    });
                    this.formLoading=false;
                    return;
                }
                if(version.items==null || version.items.length==0){
                    this.$message({
						message:"至少添加一条服务",
						type:'error'
                    });
                    this.formLoading=false;
                    return;
                }
                var checkType='';
                version.items.map((item,index)=>{
                    if(item.namespace==''){
                        checkType='namespace';
                    }else if(item.nameHost==''){
                        checkType='nameHost';
                    }else if(item.subsetNew==''){
                        checkType='subsetNew';
                    }else if(item.httpKey==''){
                        checkType='httpKey';
                    }else if(item.httpValue==''){
                        checkType='httpValue';
                    }
                });
                if(checkType=='namespace'){
                    this.$message({
					message:"命名空间不能为空",
					type:'error'});
                    this.formLoading=false;
                    return;
                }
                if(checkType=='nameHost'){
                    this.$message({
					message:"服务应用不能为空",
					type:'error'});
                    this.formLoading=false;
                    return;
                }
                if(checkType=='subsetNew'){
                    this.$message({
					message:"Subset版本号不能为空",
					type:'error'});
                    this.formLoading=false;
                    return;
                }
                if(checkType=='httpKey' && version.versionType=='CANARY'){
                    this.$message({
					message:"Key不能为空",
					type:'error'});
                    this.formLoading=false;
                    return;
                }
                if(checkType=='httpValue' && version.versionType=='CANARY'){
                    this.$message({
					message:"Value不能为空",
					type:'error'});
                    this.formLoading=false;
                    return;
                }
                versionCreate(version).then((data)=>{
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
                                    this.getVersions();
								}
							});
            },
            showLog(){
                 console.log(JSON.stringify(this.formSubs));
            }
		},
		mounted(){
            this.getNamespacess();
		}
	}

</script>
<style>

</style>
