<template>
	<section>
	    <!--表格部分-->
		<el-col :span="24">
    		<el-table :data="configmaps" v-loading="listLoading" @selection-change="selectsChange" style="width: 100%;"  size="mini" header-row-style="background:#F2F6FC">
    		<template>
                <el-table-column prop="name" label="名称">
				    <template slot-scope="scope">
                       <b>{{scope.row.name}}</b>
				    </template>	
				</el-table-column>	
                <el-table-column prop="namespace" label="命名空间"></el-table-column>	
                <el-table-column prop="currencyNo" label="当前使用编号"></el-table-column>		
                <el-table-column prop="" width="130" label="当前配置类型" align="center">
					<template slot-scope="scope">
						<span v-if="scope.row.currencyType=='create'"><el-tag size="small"><i class="el-icon-circle-plus"/> 创建</el-tag></span>
                        <span v-if="scope.row.currencyType=='update'"><el-tag type="warning" size="small"><i class="el-icon-success"/> 更新</el-tag></span>
                        <span v-if="scope.row.currencyType=='rollback'"><el-tag type="danger" size="small"><i class="el-icon-warning"/> 回滚</el-tag></span>	
					</template>
				</el-table-column>
                <el-table-column label="数据" align="center">
					<template slot-scope="scope">
						
						<el-tooltip placement="top">
  					        <div slot="content">
								  {{scope.row.currencyRecord.confData}}
							</div>
 					        <el-button size="mini" icon="el-icon-warning"></el-button>
                        </el-tooltip>
					</template>
				</el-table-column>
                <el-table-column label="创建时间"  width="180">
                   <template slot-scope="scope">
                      <i class="el-icon-time"></i>
                      <span style="margin-left: 10px">{{ scope.row.createdAt|time }}</span>
                   </template>
                </el-table-column>
               
                
     
				<el-table-column label="操作" align="center" width="220">
            		<template slot-scope="scope">
                        <el-button type="primary" size="mini" @click="formUpdateShow(scope.row)">更新</el-button>
                        <el-button type="danger" size="mini" @click="formRollbackShow(scope.row)">回滚</el-button>
                    </template>
          		</el-table-column>
    		</template>
			</el-table>
		</el-col>
		<!--分页部分-->
		<el-col :span="24" class="toolbar">
			<el-pagination size="mini"  background layout="total,sizes,prev, pager, next,jumper" @size-change="sizeChange"  @current-change="currentChange" :page-sizes="[10,20,50,100]" :page-size="pageSize" :total="total" style="float:right;">
			</el-pagination>
		</el-col>
        <!--修改页面-->
	    <el-dialog  :title="formUpdateTitle" :visible.sync="formUpdateVisible" :before-close="formUpdateClose">
            
             <el-form :model="formUpdate" :inline="true" label-width="100px" ref="formUpdate" size="small">
    
              <el-form-item label="配置" >
                        <el-input type="text" placeholder="名称" auto-complete="off" v-model="formUpdate.title" size="small" disabled="true"></el-input><br/><br/>
              </el-form-item> 
              <div v-for="o in formUpdate.items" :key="o">
	    	    <el-card class="box-card">
                  <div slot="header" class="clearfix">
                    <span>配置项</span>
                    <el-button style="float: right; padding: 3px 0" type="text" @click="formUpdateItemsRemove(o.key)">X</el-button>
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
                <el-button type="primary" @click="formUpdateItemsAdd" size="small">添加配置项</el-button>
				<el-button type="info" @click="formUpdateClose" size="small">取消</el-button>
				<el-button type="success" @click="formUpdateSet" :loading="formUpdateLoading" size="small">提交</el-button>
		    </div>
	    </el-dialog>
         <!--回滚页面-->
	    <el-dialog  :title="formRollbackTitle" :visible.sync="formRollbackVisible" :before-close="formRollbackClose">
            
             <el-form :model="formRollback" :inline="true" label-width="100px" ref="formRollback" size="small">
    
              <el-form-item label="配置" >
                        <el-input type="text" placeholder="名称" auto-complete="off" v-model="formRollback.title" size="small" disabled="true"></el-input><br/><br/>
              </el-form-item> 
              <div v-for="o in formRollback.records" :key="o">
	    	    <el-card class="box-card">
                  <div slot="header" class="clearfix">
                    <span>{{o.recordNo}} ({{o.createdAt|time}}) 

                    </span>
                        <span v-if="o.recordStatus==1"><el-tag size="small"><i class="el-icon-circle-plus"/> 待执行</el-tag></span>
                        <span v-if="o.recordStatus==2"><el-tag type="success" size="small"><i class="el-icon-success"/> 执行成功</el-tag></span>
                        <span v-if="o.recordStatus==3"><el-tag type="danger" size="small"><i class="el-icon-warning"/> 执行失败</el-tag></span>	
                        <span v-if="o.recordStatus==4"><el-tag size="small"><i class="el-icon-plus"/> 取消</el-tag></span>	
                    <el-button type="primary" style="float: right; padding: 5px 5px 5px 5px"  @click="formRollbackSet(o.sysno)">回滚</el-button>
                  </div>
                  {{o.confData}}
                </el-card>
              </div>
           
            </el-form>
	        <div slot="footer" class="dialog-footer">
				<el-button type="info" @click="formRollbackClose" size="small">取消</el-button>
				<el-button type="success" @click="formRollbackSet" :loading="formRollbackLoading" size="small">提交</el-button>
		    </div>
	    </el-dialog>
	</section>
</template>

<script>
//引用数据操作API
import store from '@/store'
//引用vuex的导入语法糖功能
import {createNamespacedHelpers} from 'vuex';
import {namespaceList,k8sconfUpdate,k8sconfRollback} from '@/service/api';
const {mapGetters,mapActions}=createNamespacedHelpers('system/k8sconfigmap');
	export default{
		name:'K8sConfigMapTable',
		data(){
			return{
                formUpdateTitle:'',
                formUpdateVisible:false,
                formUpdateLoding:false,
                formUpdate:{
                    sysno:0,
                    title:'',
                    itemIndex:0,
                    items:[]
                },
                formRollbackTitle:'',
                formRollbackVisible:false,
                formRollbackLoding:false,
                formRollback:{
                    sysno:0,
                    recordSysno:0,
                    title:'',
                    records:[]
                }
			}
		},
		//计算属性
		computed:{
			//导入store的getters属性
			...mapGetters(['configmaps','listLoading','total','pageSize'])
		},
		//当前方法
		methods:{
			//导入actions方法
            ...mapActions(['getConfigMaps','selectsChange','currentChange','sizeChange']),
            formRollbackShow(row){
                this.formRollbackTitle="回滚配置";
                this.formRollbackVisible=true;
                this.formRollback.title=row.namespace+"/"+row.name;
                this.formRollback.sysno=row.sysno;
                this.formRollback.records=row.records;
            },
            formRollbackReset(){
                this.formRollbackTitle='';
                this.formRollbackVisible=false;
                this.formRollbackLoding=false;
                this.formRollback.sysno=0;
                this.formRollback.recordSysno=0;
                this.formRollback.title='';
                this.formRollback.records=[];
            },
            formRollbackClose(){
                this.formRollbackReset();
            },
            formRollbackSet(recordSysno){
                  this.$confirm('确认提交?', '提示', {
                                 confirmButtonText: '确定',
                                 cancelButtonText: '取消',
                                 type: 'warning'
                                }).then(() => {
                                    this.fromRollbackSubmit(recordSysno);
                                });
   
            },
            fromRollbackSubmit(recordSysno){
                this.formRollbackLoading=true;
                 var conf={
                     sysno:this.formRollback.sysno,
                     recordSysno:recordSysno
                 };
                
                k8sconfRollback(conf).then((data)=>{
								this.formRollbackLoading=false;
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
                                    this.formRollbackReset();
                                    this.getConfigMaps();
								}
							});
            },
            formUpdateReset(){
                this.formUpdateTitle='';
                this.formUpdateVisible=false;
                this.formUpdateLoding=false;
                this.formUpdate.sysno=0;
                this.formUpdate.title='';
                this.formUpdate.itemIndex=0;
                this.formUpdate.items=[];
            },
            formUpdateShow(row){
                this.formUpdateTitle="修改配置";
                this.formUpdateVisible=true;
                this.formUpdateTitle=row.namespace+"/"+row.name;
                this.formUpdate.sysno=row.sysno;
                this.formUpdate.items=row.items;
            },
            formUpdateClose(){
                this.formUpdateReset();
            },
            formUpdateItemsAdd(){
                this.formUpdate.itemIndex++;
                var item={index:this.formUpdate.itemIndex,key:'',value:''};
                this.formUpdate.items.push(item);
            },
            formUpdateItemsRemove(key){
                this.formUpdate.items.map((item,index)=>{
                    if(item.key==key){
                        this.formUpdate.items.splice(index,1);
                    }
                })
            },
            formUpdateSet(){
                  this.$confirm('确认提交?', '提示', {
                                 confirmButtonText: '确定',
                                 cancelButtonText: '取消',
                                 type: 'warning'
                                }).then(() => {
                                    this.fromUpdateSubmit();
                                });
   
            },
            fromUpdateSubmit(){
                this.formUpdateLoading=true;
                 var conf={
                     sysno:this.formUpdate.sysno,
                     items:[]
                 };
                 this.formUpdate.items.map((item,index)=>{
                    var keyValue={
                        key:item.key,
                        value:item.value
                    };
                    conf.items.push(keyValue);
                });
                
                k8sconfUpdate(conf).then((data)=>{
								this.formUpdateLoading=false;
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
                                    this.formUpdateReset();
                                    this.getConfigMaps();
								}
							});
            },
		},
		//Vue勾子函数，重新渲染执行
		mounted(){
			this.getConfigMaps();

		}
	}
</script>
