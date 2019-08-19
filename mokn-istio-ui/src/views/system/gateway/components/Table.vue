<template>
	<section>
	    <!--表格部分-->
		<el-col :span="24">
    		<el-table :data="gateways" v-loading="listLoading"  style="width: 100%;"  size="mini" header-row-style="background:#F2F6FC">
    		<template>
				<el-table-column prop="name" label="名称">
				    <template slot-scope="scope">
                       <b>{{scope.row.name}}</b>
				    </template>	
				</el-table-column>	
				<el-table-column prop="namespace" label="命名空间"></el-table-column>
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
import {gatewayDelete} from '@/service/api';
const {mapGetters,mapActions}=createNamespacedHelpers('system/gateway');
	export default{
		name:'GatewayTable',
		data(){
			return{
                
			}
		},
		//计算属性
		computed:{
			//导入store的getters属性
			...mapGetters(['gateways','listLoading'])
		},
		//当前方法
		methods:{
			//导入actions方法
			...mapActions(['getGateways','selectsChange','currentChange','sizeChange']),
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
					name:row.name
                }
                gatewayDelete(param).then((data)=>{
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
                                    this.getGateways();
								}
							});
            },
		},
		//Vue勾子函数，重新渲染执行
		mounted(){
            this.getGateways();
            

		}
	}
</script>
