<template>
	<section>
	    <!--表格部分-->
		<el-col :span="24">
    		<el-table :data="namespaces" style="width: 100%;" size="mini" header-row-style="background:#F2F6FC">
                <template>
                    <el-table-column prop="name" label="名称">
						<template slot-scope="scope">
                           <b>{{scope.row.name}}</b>
				        </template>	
					</el-table-column>
					<el-table-column label="Istio注入" align="center">
					<template slot-scope="scope">
						<span v-if="scope.row.istioInjection=='enabled'"><el-tag type="success" size="small"><i class="el-icon-success"/> 是</el-tag></span>
						<span v-else><el-tag type="danger" size="small"><i class="el-icon-error"/> 否</el-tag></span>
					</template>
				    </el-table-column>
                    <el-table-column prop="" label="服务">
            		       <template slot-scope="scope">
            		         <span v-for="o in scope.row.virtualServices" :key="o">
                               &nbsp;<el-tag type="info" size="small">{{o.name}}</el-tag> 
            		         </span>
            		       </template>
                    </el-table-column>
					<el-table-column label="创建时间"  width="180">
                   <template slot-scope="scope">
                      <i class="el-icon-time"></i>
                      <span style="margin-left: 10px">{{ scope.row.creationTimestamp|time }}</span>
                   </template>
                  </el-table-column>
                </template>	
			</el-table>
		</el-col>
	</section>
</template>

<script>
import {namespaceList} from '@/service/api';
	export default{
		name:'NamespacesTable',
		data(){
			return{
                namespaces:[]
			}
		},
		//计算属性
		computed:{
		},
		//当前方法
		methods:{
           getNamespaces(){
              let param={
              };
              namespaceList(param).then((res)=>{
                  this.namespaces=res.data;
              });
           }
		},
		//Vue勾子函数，重新渲染执行
		mounted(){
			this.getNamespaces();

		}
	}
</script>
