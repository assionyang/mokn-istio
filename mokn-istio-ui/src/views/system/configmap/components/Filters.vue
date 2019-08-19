<template>
  <section>
   <!--查询过滤条件部分-->
    <el-col :span="24" class="toolbar" style="padding-top:10px;">
        <el-form :inline="true" :model="filters" ref="filtersForm" size="small">
          <el-form-item label="命名空间" >
                        <el-select v-model="filters.namespace" placeholder="Select">
                      <el-option
                        v-for="item in namespaces"
                        :key="item.uid"
                        :label="item.name"
                        :value="item.name">
                      </el-option>
                    </el-select>
          </el-form-item> 
          <el-form-item>
            <el-button type="primary" @click="getConfigMaps" icon="el-icon-search" >查询</el-button>
          </el-form-item>
          <el-form-item>
            <el-button @click="resetFilters({namespace:''})" icon="el-icon-zoom-in">清空</el-button>
          </el-form-item>
        </el-form>
        </el-col>
  </section>
</template>
<script>
  import store from '@/store'
  import { createNamespacedHelpers } from 'vuex';
  import {namespaceList} from '@/service/api';
  const {mapGetters, mapActions} = createNamespacedHelpers('system/configmap')
  export default{
    name: 'ConfigMapFilters',
    data () {
      return {
        namespaces:[]
      }
    },
    //计算属性
    computed: {
      //导入store getters
      ...mapGetters(['filters'])
    },
    //本地方法
    methods: {
      //导入store actions
      ...mapActions(['getConfigMaps', 'resetFilters']),
      getNamespaces() {
              let param={
              };
              namespaceList(param).then((res)=>{
                  this.namespaces=res.data;
              });
      },
      search () {
        this.getConfigMaps()
      }
    },
    //Vue勾子函数，重新渲染执行
    mounted () {
      this.getNamespaces();
    }
  }
</script>
<style>
  .me-datepicker, .me-select {
    width: 180px !important;
  }

  .el-form-item__label {
    font-size: 12px !important;
    padding: 0 5px 0 0;
  }
</style>
