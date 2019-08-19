<template>
	<section>
	    <!--表格部分-->
		<el-col :span="24">
    		<el-table :data="users" v-loading="listLoading" @selection-change="selectsChange" style="width: 100%;"  size="mini" header-row-style="background:#F2F6FC">
    		<template>
				<el-table-column prop="username" label="用户名"></el-table-column>	
                <el-table-column prop="name" label="姓名"></el-table-column>	
                <el-table-column prop="role" label="角色"></el-table-column>	
  
				<el-table-column label="创建时间"  width="220">
                   <template slot-scope="scope">
                      <i class="el-icon-time"></i>
                      <span style="margin-left: 10px">{{ scope.row.createdAt|time }}</span>
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
		<!--分页部分-->
		<el-col :span="24" class="toolbar">
			<el-pagination  background layout="total,sizes,prev, pager, next,jumper" @size-change="sizeChange"  @current-change="currentChange" :page-sizes="[10,20,50,100]" :page-size="pageSize" :total="total" style="float:right;">
			</el-pagination>
		</el-col>
	</section>
</template>

<script>
//引用数据操作API
import store from '@/store'
//引用vuex的导入语法糖功能
import {createNamespacedHelpers} from 'vuex';
import {userDelete} from '@/service/api';
const {mapGetters,mapActions}=createNamespacedHelpers('system/users');
	export default{
		name:'UsersTable',
		data(){
			return{
			}
		},
		//计算属性
		computed:{
			//导入store的getters属性
			...mapGetters(['users','listLoading','total','pageSize'])
		},
		//当前方法
		methods:{
			//导入actions方法
            ...mapActions(['getUsers','selectsChange','currentChange','sizeChange']),
            del(row){
                this.$confirm('确认删除吗?', '提示', {
                                 confirmButtonText: '确定',
                                 cancelButtonText: '取消',
                                 type: 'warning'
                                }).then(() => {
                                    this.delSubmiet(row.sysno);
                                });
            },
            delSubmiet(sysno,action){
                var param={
                    sysno:sysno,
                    versionStatus:action
                }
                userDelete(param).then((data)=>{
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
                                    this.getUsers();
								}
							});
            }
		},
		//Vue勾子函数，重新渲染执行
		mounted(){
			this.getUsers();

		}
	}
</script>
