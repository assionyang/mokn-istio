<template>
	<section>
	    <!--表格部分-->
		<el-col :span="24">
    		<el-table :data="destinationrules" v-loading="listLoading" @selection-change="selectsChange" style="width: 100%;"  size="mini" header-row-style="background:#F2F6FC">
    		<template>
                <el-table-column prop="name" label="名称">
				    <template slot-scope="scope">
                       <b>{{scope.row.name}}</b>
				    </template>	
				</el-table-column>	
                <el-table-column prop="namespace" label="命名空间"></el-table-column>	
                <el-table-column label="Subsets" align="center">
					<template slot-scope="scope">
						<el-tooltip placement="top">
  					        <div slot="content">
								<span v-for="o in scope.row.subsets" :key="o">
							           {{o}}<br/>
						        </span>
							</div>
 					        <el-button size="mini" icon="el-icon-warning"></el-button>
                        </el-tooltip>
					</template>
				</el-table-column>
                <el-table-column prop="" label="是否开启熔断" align="center">
					<template slot-scope="scope">
						<span v-if="scope.row.openFuse==true"><el-tag size="small" type="success"><i class="el-icon-success"/></el-tag></span>
                        <span v-else><el-tag type="danger" size="small"><i class="el-icon-error"/></el-tag></span>	
					</template>
				</el-table-column>

				<el-table-column label="操作" align="center" width="220">
            		<template slot-scope="scope">
                        <el-button type="primary" size="mini" @click="formShow(scope.row)">设置限流熔断</el-button>
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
        <!--详情dialog-->
        <el-dialog  :title="formTitle" :visible.sync="formVisible" :before-close="formClose">
            <el-form :model="form" label-width="200px" :rules="formRules" ref="form" size="small">
                <el-form-item label="是否开启" prop="openFuse">
                   <el-switch 
                    v-model="form.openFuse"
                    active-color="#13ce66"
                    active-text=""
                    inactive-text=""
                    @change="istioIngressGatewayTypeSet()">
                   </el-switch>
                </el-form-item> 
                <el-form-item label="http1MaxPendingRequests" prop="http1MaxPendingRequests">
                   <el-input-number v-model="form.http1MaxPendingRequests" :disabled="!form.openFuse"></el-input-number>
                </el-form-item> 
                <el-form-item label="maxRequestsPerConnection" prop="passmaxRequestsPerConnectionword">
                   <el-input-number v-model="form.maxRequestsPerConnection" :disabled="!form.openFuse"></el-input-number>
                </el-form-item> 
                <el-form-item label="maxConnections" prop="maxConnections">
                   <el-input-number v-model="form.maxConnections" :disabled="!form.openFuse"></el-input-number>
                </el-form-item> 
                <el-form-item label="baseEjectionTime" prop="baseEjectionTime">
                   <el-input type="text" v-model="form.baseEjectionTime" :disabled="!form.openFuse" placeholder="单位s m h，如3s代表3秒"></el-input>
                </el-form-item> 
                <el-form-item label="consecutiveErrors" prop="consecutiveErrors">
                   <el-input-number v-model="form.consecutiveErrors" :disabled="!form.openFuse"></el-input-number>
                </el-form-item> 
                <el-form-item label="interval" prop="interval">
                   <el-input type="text" v-model="form.interval" :disabled="!form.openFuse" placeholder="单位s m h，如3s代表3秒"></el-input>
                </el-form-item> 
                <el-form-item label="maxEjectionPercent" prop="maxEjectionPercent">
                   <el-input-number v-model="form.maxEjectionPercent" :disabled="!form.openFuse"></el-input-number>
                </el-form-item> 
               
            </el-form>
	        <div slot="footer" class="dialog-footer">
				<el-button type="info" @click="formClose" size="small">取消</el-button>
				<el-button type="primary" @click="fromSubmit" :loading="formLoading" size="small">提交</el-button>
		    </div>
	    </el-dialog>
	</section>
</template>

<script>
//引用数据操作API
import store from '@/store'
//引用vuex的导入语法糖功能
import {createNamespacedHelpers} from 'vuex';
import {destinationruleSetFuse} from '@/service/api';
const {mapGetters,mapActions}=createNamespacedHelpers('system/destinationrule');
	export default{
		name:'RouteLimitTable',
		data(){
			return{
                formTitle:'',
                formVisible:false,
                form:{
                    name:'',
                    namespace:'',
                    openFuse:false,
                    http1MaxPendingRequests:0,
                    maxRequestsPerConnection:0,
                    maxConnections:0,
                    baseEjectionTime:"0",
                    consecutiveErrors:0,
                    interval:"0",
                    maxEjectionPercent:0
                }
			}
		},
		//计算属性
		computed:{
			//导入store的getters属性
			...mapGetters(['destinationrules','listLoading','total','pageSize'])
		},
		//当前方法
		methods:{
			//导入actions方法
            ...mapActions(['getDestinationRules','selectsChange','currentChange','sizeChange']),
            formShow(row){
                this.formTitle="服务："+row.name;
                this.formVisible=true;
                this.form.name=row.name;
                this.form.namespace=row.namespace;
                this.form.openFuse=row.openFuse;
                this.form.http1MaxPendingRequests=row.http1MaxPendingRequests;
                this.form.maxRequestsPerConnection=row.maxRequestsPerConnection;
                this.form.maxConnections=row.maxConnections;
                this.form.baseEjectionTime=row.baseEjectionTime;
                this.form.consecutiveErrors=row.consecutiveErrors;
                this.form.interval=row.interval;
                this.form.maxEjectionPercent=row.maxEjectionPercent;
            },
            formClose(){
                this.formReset();
                this.formVisible=false;
            },
            formReset(){
                this.formTitle="";
                this.formVisible=false;
                this.form.name="";
                this.form.namespace="";
                this.form.openFuse=null;
                this.form.http1MaxPendingRequests=null;
                this.form.maxRequestsPerConnection=null;
                this.form.maxConnections=null;
                this.form.baseEjectionTime=null;
                this.form.consecutiveErrors=null;
                this.form.interval=null;
                this.form.maxEjectionPercent=null;
            },
            fromSubmit(){
                this.$confirm('确认提交吗?', '提示', {
                                 confirmButtonText: '确定',
                                 cancelButtonText: '取消',
                                 type: 'warning'
                                }).then(() => {
                                    this.submit();
                                });
            },
            submit(){
                var param={
                    name:this.form.name,
                    namespace:this.form.namespace,
                    openFuse:this.form.openFuse,
                    http1MaxPendingRequests:this.form.http1MaxPendingRequests,
                    maxRequestsPerConnection:this.form.maxRequestsPerConnection,
                    maxConnections:this.form.maxConnections,
                    baseEjectionTime:this.form.baseEjectionTime,
                    consecutiveErrors:this.form.consecutiveErrors,
                    interval:this.form.interval,
                    maxEjectionPercent:this.form.maxEjectionPercent,
                }
                destinationruleSetFuse(param).then((data)=>{
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
                                    this.formClose();
                                    this.getDestinationRules();
								}
							});
            },
			// 详情
			detail(row){
                this.formVisible=true;
                if(row.versionType==1){
                    this.formTitle="直接发布";
                }else if(row.versionType==2){
                    this.formTitle="流量权重";
                }else if(row.versionType==3){
                    this.formTitle="金丝雀";
                }
                this.version=row;
      		},
		},
		//Vue勾子函数，重新渲染执行
		mounted(){
			this.getDestinationRules();

		}
	}
</script>
