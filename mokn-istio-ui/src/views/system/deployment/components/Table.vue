<template>
	<section>
	    <!--表格部分-->
		<el-col :span="24">
    		<el-table :data="deployments" v-loading="listLoading"  style="width: 100%;" size="mini" header-row-style="background:#F2F6FC">
    		<template>
				<el-table-column prop="name" label="名称">
				    <template slot-scope="scope">
                       <b>{{scope.row.name}}</b>
				    </template>	
				</el-table-column>	
				<el-table-column label="状态" align="center">
					<template slot-scope="scope">
						<span v-if="scope.row.availableReplicas==scope.row.replicas && scope.row.availableReplicas>0"><el-tag type="success" size="small"><i class="el-icon-success"/> 运行中</el-tag></span>
						<span v-if="scope.row.availableReplicas!=scope.row.replicas && scope.row.availableReplicas>0"><el-tag type="primary" size="small"><i class="el-icon-warning"/> 可用</el-tag></span>
						<span v-if="scope.row.availableReplicas==0"><el-tag type="danger" size="small"><i class="el-icon-error"/> 不可用</el-tag></span>
					</template>
				</el-table-column>
				<el-table-column label="副本" align="center">
					<template slot-scope="scope">
						<span v-if="scope.row.availableReplicas==scope.row.replicas && scope.row.availableReplicas>0">
							<el-col :span="20">
								<el-progress :percentage="100" status="success" stroke-width="5" ></el-progress>
							</el-col>
							<el-col :span="4">
								<span style="color:#67C23A">{{scope.row.availableReplicas}}/{{scope.row.replicas}}</span>
							</el-col>
						</span>
						<span v-if="scope.row.availableReplicas!=scope.row.replicas && scope.row.availableReplicas>0">
							<el-col :span="20">
								<el-progress :percentage="50" stroke-width="5"></el-progress>
							</el-col>
							<el-col :span="4">
								<span style="color:#409EFF">{{scope.row.availableReplicas}}/{{scope.row.replicas}}</span>
							</el-col>
						</span>
						<span v-if="scope.row.availableReplicas==0">
							<el-col :span="20">
								<el-progress :percentage="100" status="exception" stroke-width="5"></el-progress>
							</el-col>
							<el-col :span="4">
								<span style="color:#F56C6C">{{scope.row.availableReplicas}}/{{scope.row.replicas}}</span>
							</el-col>
						</span>
					</template>
				</el-table-column>
				<el-table-column prop="namespace" label="命名空间"></el-table-column>
				
				<el-table-column label="app/version" align="center">
					<template slot-scope="scope">
						<el-tag type="info" size="small">{{scope.row.app}}</el-tag> / <el-tag type="success" size="small">{{scope.row.version}}</el-tag>
					</template>
				</el-table-column>
				<el-table-column label="创建时间"  width="180">
                   <template slot-scope="scope">
                      <i class="el-icon-time"></i>
                      <span style="margin-left: 10px">{{ scope.row.creationTimestamp|time }}</span>
                   </template>
                </el-table-column>
				<el-table-column label="操作" align="center" width="100">
            		<template slot-scope="scope">
                        <el-button type="danger" size="mini" @click="del(scope.row)">删除</el-button>
                    </template>
          		</el-table-column>
    		</template>
			</el-table>
		</el-col>
	</section>
</template>

<script>
//引用数据操作API
import store from '@/store'
//引用vuex的导入语法糖功能
import {createNamespacedHelpers} from 'vuex';
import {deploymentDelete} from '@/service/api';
const {mapGetters,mapActions}=createNamespacedHelpers('system/deployment');
	export default{
		name:'DeploymentTable',
		data(){
			return{
                
			}
		},
		//计算属性
		computed:{
			//导入store的getters属性
			...mapGetters(['deployments','listLoading'])
		},
		//当前方法
		methods:{
			//导入actions方法
			...mapActions(['getDeployments','selectsChange','currentChange','sizeChange']),
			del(row){
                this.$confirm('确认删除吗?', '提示', {
                                 confirmButtonText: '确定',
                                 cancelButtonText: '取消',
                                 type: 'warning'
                                }).then(() => {
                                    this.delSubmit(row);
                                });
            },
            delSubmit(row){
                var param={
					namespace:row.namespace,
					name:row.name,
                    app:row.app
                }
                deploymentDelete(param).then((data)=>{
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
                                    this.getDeployments();
								}
							});
            },
		},
		//Vue勾子函数，重新渲染执行
		mounted(){
            this.getDeployments();
            

		}
	}
</script>
