<template>
	<section>
		<el-row :gutter="20" style="padding:10px;">
	    <el-col :span="6">
	    <el-card style="height:115px;">
  	    	<div>
   	    	 <span>K8s Namespace</span>
 	    	 </div>
         <br/>
  	     <div>
            <span style="font-family:Arial;font-size:30px;color:rgb(97, 160, 168);">{{data.namespaceQty}}</span><br/><br/>
         </div>
        </el-card><br/>
        <el-card style="height:115px;">
  	    	<div>
   	    	 <span>K8s Deployment</span>
 	    	 </div>
         <br/>
  	     <div>
            <span style="font-family:Arial;font-size:30px;color:rgb(97, 160, 168);">{{data.deploymentQty}}</span><br/><br/>
         </div>
        </el-card>
	    </el-col>

         <el-col :span="6">
        <el-card style="height:115px;">
  	    	<div>
   	    	 <span>K8s Pod</span>
 	    	 </div>
         <br/>
  	     <div>
            <span style="font-family:Arial;font-size:30px;color:rgb(97, 160, 168);">{{data.podQty}}</span><br/><br/>
         </div>
        </el-card><br/>
        <el-card style="height:115px;">
  	    	<div>
   	    	 <span>K8s Service</span>
 	    	 </div>
         <br/>
  	     <div>
            <span style="font-family:Arial;font-size:30px;color:rgb(97, 160, 168);">{{data.serviceQty}}</span><br/><br/>
         </div>
        </el-card>
        </el-col>

        <el-col :span="6">
        <el-card style="height:115px;">
  	    	<div>
   	    	 <span>K8s ConfigMap</span>
 	    	 </div>
         <br/>
  	     <div>
            <span style="font-family:Arial;font-size:30px;color:rgb(97, 160, 168);">{{data.configMapQty}}</span><br/><br/>
         </div>
        </el-card><br/>
        <el-card style="height:115px;">
  	    	<div>
   	    	 <span>K8s Secret</span>
 	    	 </div>
         <br/>
  	     <div>
            <span style="font-family:Arial;font-size:30px;color:rgb(97, 160, 168);">{{data.secretQty}}</span><br/><br/>
         </div>
        </el-card>
        </el-col>

        <el-col :span="6">
        <el-card style="height:115px;">
  	    	<div>
   	    	 <span>Istio VirtualService</span>
 	    	 </div>
         <br/>
  	     <div>
            <span style="font-family:Arial;font-size:30px;color:rgb(97, 160, 168);">{{data.veritualServiceQty}}</span><br/><br/>
         </div>
        </el-card><br/>
        <el-card style="height:115px;">
  	    	<div>
   	    	 <span>Istio Gateway</span>
 	    	 </div>
         <br/>
  	     <div>
            <span style="font-family:Arial;font-size:30px;color:rgb(97, 160, 168);">{{data.gatewayQty}}</span><br/><br/>
         </div>
        </el-card>
        </el-col>
	</el-row>

	</section>
</template>

<script>
import {dashboardData} from '@/service/api';

export default{
  data(){
      dashboardData(null).then((res)=>{
                  this.data=res.data.row;
      });
      return{
          data:{}
      }
  },
  methods:{
  },
  mounted(){
  }
}
</script>