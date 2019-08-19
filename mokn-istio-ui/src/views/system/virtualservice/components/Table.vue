<template>
	<section>
	    <!--表格部分-->
		<el-col :span="24">
    		<el-table :data="virtualservices" v-loading="listLoading"  style="width: 100%;"  size="mini" header-row-style="background:#F2F6FC">
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
import {virtualServiceDelete} from '@/service/api';
const {mapGetters,mapActions}=createNamespacedHelpers('system/virtualservice');
	export default{
		name:'VirtualServiceTable',
		data(){
			return{
                
			}
		},
		//计算属性
		computed:{
			//导入store的getters属性
			...mapGetters(['virtualservices','listLoading'])
		},
		//当前方法
		methods:{
			//导入actions方法
			...mapActions(['getVirtualServices','selectsChange','currentChange','sizeChange']),
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
                virtualServiceDelete(param).then((data)=>{
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
                                    this.getVirtualServices();
								}
							});
            },
		},
		//Vue勾子函数，重新渲染执行
		mounted(){
            this.getVirtualServices();
            

		}
	}
</script>
