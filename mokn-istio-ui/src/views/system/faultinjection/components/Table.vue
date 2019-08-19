<template>
	<section>
	    <!--表格部分-->
		<el-col :span="24">
    		<el-table :data="virtualservices" v-loading="listLoading" @selection-change="selectsChange" style="width: 100%;"  size="mini" header-row-style="background:#F2F6FC">
    		<template>
                <el-table-column prop="name" label="名称">
				    <template slot-scope="scope">
                       <b>{{scope.row.name}}</b>
				    </template>	
				</el-table-column>	
                <el-table-column prop="namespace" label="命名空间"></el-table-column>	
                <el-table-column prop="" label="Istio类型" align="center">
					<template slot-scope="scope">
						<span v-if="scope.row.istioVersionType=='VirtualService_Default'">
							<el-tag size="small"><i class="el-icon-success"/> 直接发布({{scope.row.istioVersion}})</el-tag>
						</span>
                        <span v-if="scope.row.istioVersionType=='VirtualService_Weight'">
							<el-tag type="warning" size="small"><i class="el-icon-share"/> 流量权重({{scope.row.istioVersion}})</el-tag>
						</span>
                        <span v-if="scope.row.istioVersionType=='VirtualService_Canary'">
							<el-tag type="success" size="small"><i class="el-icon-upload"/> 金丝雀({{scope.row.istioVersion}})</el-tag>
						</span>	
                        <span v-if="scope.row.istioVersionType=='VirtualService_Gateway'">
							<el-tag type="success" size="small"><i class="el-icon-share"/> 网关</el-tag>
						</span>	
						<span v-if="scope.row.istioVersionType==''">
							<el-tag type="info" size="small">无</el-tag>
						</span>	
					</template>
				</el-table-column>
                <el-table-column prop="" label="是否开启延迟" align="center">
					<template slot-scope="scope">
						<span v-if="scope.row.openFaultDelay==true"><el-tag size="small" type="success"><i class="el-icon-success"/></el-tag></span>
                        <span v-else><el-tag type="danger" size="small"><i class="el-icon-error"/></el-tag></span>	
					</template>
				</el-table-column>
                <el-table-column prop="" label="是否开启故障" align="center">
					<template slot-scope="scope">
						<span v-if="scope.row.openFaultAbort==true"><el-tag size="small" type="success"><i class="el-icon-success"/></el-tag></span>
                        <span v-else><el-tag type="danger" size="small"><i class="el-icon-error"/></el-tag></span>	
					</template>
				</el-table-column>

				<el-table-column label="操作" align="center" width="220">
            		<template slot-scope="scope">
                        <span v-if="scope.row.istioVersionType=='VirtualService_Default'">
							<el-button type="primary" size="mini" @click="formShow(scope.row)">注入设置</el-button>
						</span>
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
                <el-card class="box-card">
                   <div slot="header" class="clearfix">
                       注入延迟
                   </div>
                    <el-form-item label="是否开启" prop="openFaultDelay">
                          <el-switch 
                           v-model="form.openFaultDelay"
                           active-color="#13ce66"
                           active-text=""
                           inactive-text="">
                        </el-switch>
                </el-form-item> 
                <el-form-item label="faultDelayFixedDelay" prop="faultDelayFixedDelay">
                   <el-input type="text" v-model="form.faultDelayFixedDelay" :disabled="!form.openFaultDelay" placeholder="单位s m h，如3s代表3秒"></el-input>
                </el-form-item> 
                <el-form-item label="faultDelayPercent" prop="faultDelayPercent">
                   <el-input-number v-model="form.faultDelayPercent" :disabled="!form.openFaultDelay"></el-input-number>
                </el-form-item> 
                </el-card>
                <br/>

                 <el-card class="box-card">
                   <div slot="header" class="clearfix">
                       注入错误
                   </div>
                    <el-form-item label="是否开启" prop="openFaultAbort">
                          <el-switch 
                           v-model="form.openFaultAbort"
                           active-color="#13ce66"
                           active-text=""
                           inactive-text="">
                        </el-switch>
                </el-form-item> 
                <el-form-item label="faultAbortHttpStatus" prop="faultAbortHttpStatus">
                   <el-input-number v-model="form.faultAbortHttpStatus" :disabled="!form.openFaultAbort"></el-input-number>
                </el-form-item> 
                <el-form-item label="faultAbortPercent" prop="faultAbortPercent">
                   <el-input-number v-model="form.faultAbortPercent" :disabled="!form.openFaultAbort"></el-input-number>
                </el-form-item> 
                </el-card>
                
               
               
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
import {virtualServiceFailSet} from '@/service/api';
const {mapGetters,mapActions}=createNamespacedHelpers('system/virtualservice');
	export default{
		name:'FaultInjectionTable',
		data(){
			return{
                formTitle:'',
                formVisible:false,
                form:{
                    name:'',
                    namespace:'',
                    openFault:false,
                    openFaultDelay:false,
                    openFaultAbort:false,
                    faultDelayFixedDelay:"0s",
                    faultDelayPercent:0,
                    faultAbortHttpStatus:0,
                    faultAbortPercent:0
                }
			}
		},
		//计算属性
		computed:{
			//导入store的getters属性
			...mapGetters(['virtualservices','listLoading','total','pageSize'])
		},
		//当前方法
		methods:{
			//导入actions方法
            ...mapActions(['getVirtualServices','selectsChange','currentChange','sizeChange']),
            formShow(row){
                this.formTitle="服务："+row.name;
                this.formVisible=true;
                this.form.name=row.name;
                this.form.namespace=row.namespace;
                this.form.openFault=row.openFault;
                this.form.openFaultDelay=row.openFaultDelay;
                this.form.openFaultAbort=row.openFaultAbort;
                this.form.faultDelayFixedDelay=row.faultDelayFixedDelay;
                this.form.faultDelayPercent=row.faultDelayPercent;
                this.form.faultAbortHttpStatus=row.faultAbortHttpStatus;
                this.form.faultAbortPercent=row.faultAbortPercent;
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
                this.form.openFault=false;
                this.form.openFaultDelay=false;
                this.form.openFaultAbort=false;
                this.form.faultDelayFixedDelay="0s";
                this.form.faultDelayPercent=0;
                this.form.faultAbortHttpStatus=0;
                this.form.faultAbortPercent=0;
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
                    openFault:this.form.openFault,
                    openFaultDelay:this.form.openFaultDelay,
                    openFaultAbort:this.form.openFaultAbort,
                    faultDelayFixedDelay:this.form.faultDelayFixedDelay,
                    faultDelayPercent:this.form.faultDelayPercent,
                    faultAbortHttpStatus:this.form.faultAbortHttpStatus,
                    faultAbortPercent:this.form.faultAbortPercent
                }
                virtualServiceFailSet(param).then((data)=>{
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
                                    this.getVirtualServices();
								}
							});
            }
		},
		//Vue勾子函数，重新渲染执行
		mounted(){
			this.getVirtualServices();

		}
	}
</script>
