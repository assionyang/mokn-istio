<template>
	<section>
        <!--直接发布页面-->
	    <el-dialog  :title="formTitle" :visible.sync="formVisible" :before-close="formClose">
            
            <el-form :model="form" :inline="true" label-width="100px" ref="form" size="small">
            <el-form-item label="命名空间" >
                        <el-select v-model="form.namespace" placeholder="请选择">
                      <el-option
                        v-for="item in namespacess"
                        :key="item.uid"
                        :label="item.name"
                        :value="item.name">
                      </el-option>
                    </el-select>
          </el-form-item> 
           <el-form-item label="名称" >
                        <el-input type="text" placeholder="名称" auto-complete="off" v-model="form.name" size="small"></el-input><br/><br/>
          </el-form-item> 
            
            <div v-for="o in form.items" :key="o">
	    	    <el-card class="box-card">
                  <div slot="header" class="clearfix">
                    <span>配置项{{o.index}}</span>
                    <el-button style="float: right; padding: 3px 0" type="text" @click="formItemsRemove(o.key)">X</el-button>
                  </div>
                  <el-form-item label="配置键" prop="userTitle">
                    <el-input type="text" auto-complete="off" v-model="o.key"></el-input>
                  </el-form-item> 
                  <el-form-item label="配置值" prop="userTitle">
                     <el-input type="text" auto-complete="off" v-model="o.value"></el-input>
                  </el-form-item>
                </el-card>
            </div>
            </el-form>
	        <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="formItemsAdd" size="small">添加配置项</el-button>
				<el-button type="info" @click="formCancel" size="small">取消</el-button>
				<el-button type="success" @click="formSubmit" :loading="formLoading" size="small">提交</el-button>
		    </div>
	    </el-dialog>
        <!--操作按钮组部分-->
        <el-col :span="24">
          <el-col :span="23">
            <el-button type="primary" @click="formShow" icon="el-icon-success" size="small">创建配置</el-button>

          </el-col>
          <el-col :span="1" style="text-align: right;">
            <el-button @click="getConfigMaps" icon="el-icon-refresh" size="mini"></el-button>
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
import {namespaceList,k8sconfCreate} from '@/service/api';
const {mapGetters,mapActions}=createNamespacedHelpers('system/k8sconfigmap');
	export default{
		name:'K8sConfigMapForm',
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
                    itemIndex:0,
                    items:[]
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
            ...mapActions(['getConfigMaps','listLoading']),
            getNamespacess() {
              let param={
              };
              namespaceList(param).then((res)=>{
                  this.namespacess=res.data;
              });
            },
            formShow(){
                this.formVisible=true;
                this.formTitle="创建配置";
            },
            formReset(){
                this.form.namespace='';
                this.form.name='';
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
                var item={index:this.form.itemIndex,key:'',value:''};
                this.form.items.push(item);
            },
            formItemsRemove(key){
                this.form.items.map((item,index)=>{
                    if(item.key==key){
                        this.form.items.splice(index,1);
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
                 var conf={
                     namespace:this.form.namespace,
                     name:this.form.name,
                     items:[]
                 };
                 this.form.items.map((item,index)=>{
                    var keyValue={
                        key:item.key,
                        value:item.value
                    };
                    conf.items.push(keyValue);
                });
                
                k8sconfCreate(conf).then((data)=>{
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
                                    this.getConfigMaps();
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
