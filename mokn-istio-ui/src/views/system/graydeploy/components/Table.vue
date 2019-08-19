<template>
	<section>
	    <!--表格部分-->
		<el-col :span="24">
    		<el-table :data="versions" v-loading="listLoading" @selection-change="selectsChange" style="width: 100%;"  size="mini" header-row-style="background:#F2F6FC">
    		<template>
                <el-table-column prop="" width="130" label="发布类型" align="center">
					<template slot-scope="scope">
						<span v-if="scope.row.versionType==1"><el-tag size="small"><i class="el-icon-success"/> 直接发布</el-tag></span>
                        <span v-if="scope.row.versionType==2"><el-tag type="warning" size="small"><i class="el-icon-share"/> 流量权重</el-tag></span>
                        <span v-if="scope.row.versionType==3"><el-tag type="success" size="small"><i class="el-icon-upload"/> 金丝雀</el-tag></span>	
					</template>
				</el-table-column>
                <el-table-column prop="" width="120" label="状态" align="center">
					<template slot-scope="scope">
						<span v-if="scope.row.versionStatus==1"><el-tag size="small"><i class="el-icon-info"/> 创建</el-tag></span>
                        <span v-if="scope.row.versionStatus==2"><el-tag type="warning" size="small"><i class="el-icon-warning"/> 部署中</el-tag></span>
                        <span v-if="scope.row.versionStatus==3"><el-tag type="primary" size="small"><i class="el-icon-success"/> 部署成功</el-tag></span>
                        <span v-if="scope.row.versionStatus==4"><el-tag type="danger" size="small"><i class="el-icon-error"/> 部署失败</el-tag></span>
                        <span v-if="scope.row.versionStatus==5"><el-tag type="warning" size="small"><i class="el-icon-warning"/> 上线中</el-tag></span>
                        <span v-if="scope.row.versionStatus==6"><el-tag type="success" size="small"><i class="el-icon-success"/> 上线成功</el-tag></span>	
                        <span v-if="scope.row.versionStatus==7"><el-tag type="danger" size="small"><i class="el-icon-error"/> 上线失败</el-tag></span>	
                        <span v-if="scope.row.versionStatus==8"><el-tag type="warning" size="small"><i class="el-icon-warning"/> 回滚中</el-tag></span>	
                        <span v-if="scope.row.versionStatus==9"><el-tag type="danger" size="small"><i class="el-icon-success"/> 回滚成功</el-tag></span>
                        <span v-if="scope.row.versionStatus==10"><el-tag type="danger" size="small"><i class="el-icon-error"/> 回滚失败</el-tag></span>		
                        <span v-if="scope.row.versionStatus==11"><el-tag type="info" size="small"><i class="el-icon-success"/> 取消</el-tag></span>		
					</template>
				</el-table-column>
				<el-table-column prop="versionNo" label="发布编号" width="180"></el-table-column>	
                	<el-table-column prop="versionTitle" label="标题">
				    <template slot-scope="scope">
                       <el-button type="text" @click="detail(scope.row)">{{scope.row.versionTitle}}</el-button>
				    </template>	
				</el-table-column>
            <el-table-column prop="" label="应用(新版本绿色)">
            <template slot-scope="scope">
            		         <span v-for="o in scope.row.items" :key="o">
                           <el-tag type="info" size="small">{{o.namespace}}/{{o.nameHost}}</el-tag>
                           <el-tag type="success" size="small">{{o.subsetNew}}</el-tag>
                           <el-tag type="warning" size="small">{{o.subsetOld}}</el-tag>
                           <br/>
            		         </span>
            </template>
            </el-table-column>
    
                <el-table-column label="时间" align="center" width="60">
					<template slot-scope="scope">
						
						<el-tooltip placement="top">
  					        <div slot="content">
								创建：{{scope.row.createdAt}}<br/>
                        		部署：{{scope.row.deployAt}}<br/>
                        		上线：{{scope.row.releaseAt}}<br/>
                        		回滚：{{scope.row.rollbackAt}}
							</div>
 					        <el-button size="mini" icon="el-icon-warning"></el-button>
                        </el-tooltip>
					</template>
				</el-table-column>
                <el-table-column prop="operName" label="操作人" width="100"></el-table-column>		
                <el-table-column label="创建时间"  width="180">
                   <template slot-scope="scope">
                      <i class="el-icon-time"></i>
                      <span style="margin-left: 10px">{{ scope.row.createdAt|time }}</span>
                   </template>
                  </el-table-column>
				<el-table-column label="操作" align="center" width="220">
            		<template slot-scope="scope">
                        
                        <span v-if="scope.row.versionStatus==1">
                            <el-button type="primary" size="mini" @click="action(scope.row,'DEPLOY_ING')">部署</el-button>
                            <el-button type="info" size="mini" @click="action(scope.row,'CANCEL')">取消</el-button>
                        </span>
                        <span v-if="scope.row.versionStatus==3">
                            <el-button type="success" size="mini" @click="action(scope.row,'RELEASE_ING')">上线</el-button>
                            <el-button type="danger" size="mini" @click="action(scope.row,'ROLLBACK_ING')">回滚</el-button>
                        </span>
                        <span v-if="scope.row.versionStatus==6">

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
        <el-dialog  :title="formTitle" :visible.sync="formVisible" size="small">
           <div v-for="o in version.items" :key="o">
                <el-card class="box-card">
                  <div slot="header" class="clearfix">
                    <span v-if="o.versionStatus==1"><el-tag size="small"><i class="el-icon-info"/> 创建</el-tag> ({{o.createdAt}})</span>
                    <span v-if="o.versionStatus==2"><el-tag type="warning" size="small"><i class="el-icon-warning"/> 部署中</el-tag> ({{o.deployAt}})</span>
                    <span v-if="o.versionStatus==3"><el-tag type="success" size="small"><i class="el-icon-success"/> 部署成功</el-tag> ({{o.deployAt}})</span>
                    <span v-if="o.versionStatus==4"><el-tag type="danger" size="small"><i class="el-icon-error"/> 部署失败</el-tag> ({{o.deployAt}})</span>
                    <span v-if="o.versionStatus==5"><el-tag type="warning" size="small"><i class="el-icon-warning"/> 上线中</el-tag> ({{o.releaseAt}})</span>
                    <span v-if="o.versionStatus==6"><el-tag type="success" size="small"><i class="el-icon-success"/> 上线成功</el-tag> ({{o.releaseAt}})</span>	
                    <span v-if="o.versionStatus==7"><el-tag type="danger" size="small"><i class="el-icon-error"/> 上线失败</el-tag> ({{o.releaseAt}})</span>	
                    <span v-if="o.versionStatus==8"><el-tag type="warning" size="small"><i class="el-icon-warning"/> 回滚中</el-tag> ({{o.rollbackAt}})</span>	
                    <span v-if="o.versionStatus==9"><el-tag type="success" size="small"><i class="el-icon-success"/> 回滚成功</el-tag> ({{o.rollbackAt}})</span>	
                    <span v-if="o.versionStatus==10"><el-tag type="danger" size="small"><i class="el-icon-error"/> 回滚失败</el-tag> ({{o.rollbackAt}})</span>		
                    <span v-if="o.versionStatus==11"><el-tag type="info" size="small"><i class="el-icon-success"/> 取消</el-tag></span>		
                  </div>
                  <div class="text item">
                    <span><b>应用：</b>{{o.namespace}}/{{o.nameHost}}<br/></span>
                    <span><b>新版本Subset：</b>{{o.subsetNew}}<br/></span>
                    <span><b>旧版本Subset：</b>{{o.subsetOld}}<br/></span>
                    <span v-if="version.versionType==2"><b>流量权重：</b>{{o.weight}}<br/></span>
                    <span v-if="version.versionType==3"><b>HTTP Key：</b>{{o.httpKey}} <b>HTTP Value：</b>{{o.httpValue}}<br/></span>
                  </div>
                  

                </el-card><br/>
            </div>
        </el-dialog>
	</section>
</template>

<script>
//引用数据操作API
import store from '@/store'
//引用vuex的导入语法糖功能
import {createNamespacedHelpers} from 'vuex';
import {versionAction} from '@/service/api';
const {mapGetters,mapActions}=createNamespacedHelpers('system/version');
	export default{
		name:'VersionTable',
		data(){
			return{
                formTitle:'',
                formVisible:false,
                version:{}
			}
		},
		//计算属性
		computed:{
			//导入store的getters属性
			...mapGetters(['versions','listLoading','total','pageSize'])
		},
		//当前方法
		methods:{
			//导入actions方法
            ...mapActions(['getVersions','selectsChange','currentChange','sizeChange']),
            action(row,action){
                this.$confirm('确认操作吗?', '提示', {
                                 confirmButtonText: '确定',
                                 cancelButtonText: '取消',
                                 type: 'warning'
                                }).then(() => {
                                    this.actionSubmit(row.sysno,action);
                                });
            },
            actionSubmit(sysno,action){
                var param={
                    sysno:sysno,
                    versionStatus:action
                }
                versionAction(param).then((data)=>{
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
                                    this.getVersions();
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
			this.getVersions();

		}
	}
</script>
