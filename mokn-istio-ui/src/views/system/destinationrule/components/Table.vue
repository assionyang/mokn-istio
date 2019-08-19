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
				<el-table-column label="创建时间"  width="180">
                   <template slot-scope="scope">
                      <i class="el-icon-time"></i>
                      <span style="margin-left: 10px">{{ scope.row.creationTimestamp|time }}</span>
                   </template>
                </el-table-column>
				<el-table-column label="操作" align="center" width="220">
            		<template slot-scope="scope">
                        <el-button type="danger" size="mini" @click="del(scope.row)">删除</el-button>
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
	</section>
</template>

<script>
//引用数据操作API
import store from '@/store'
//引用vuex的导入语法糖功能
import {createNamespacedHelpers} from 'vuex';
import {destinationruleDelete} from '@/service/api';
const {mapGetters,mapActions}=createNamespacedHelpers('system/destinationrule');
	export default{
		name:'DestinationRuleTable',
		data(){
			return{
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
            del(row){
                this.$confirm('确认删除吗?', '提示', {
                                 confirmButtonText: '确定',
                                 cancelButtonText: '取消',
                                 type: 'warning'
                                }).then(() => {
                                    this.submit(row);
                                });
            },
            submit(row){
                var param={
                    namespace:row.namespace,
                    name:row.name
                }
                destinationruleDelete(param).then((data)=>{
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
                                    this.getDestinationRules();
								}
							});
            }
		},
		//Vue勾子函数，重新渲染执行
		mounted(){
			this.getDestinationRules();

		}
	}
</script>
