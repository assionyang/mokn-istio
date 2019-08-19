<template>
  <section>
   <!--查询过滤条件部分-->
    <el-col :span="24" class="toolbar" style="padding-top:10px;">
        <el-form :inline="true" :model="filters" ref="filtersForm" size="small">
          <el-form-item label="发布状态" >
                        <el-select v-model="filters.versionStatus" placeholder="选择">
							<el-option label="全部" value=""></el-option>
                            <el-option label="创建" value="1"></el-option>
                            <el-option label="部署中" value="2"></el-option>
							<el-option label="部署成功" value="3"></el-option>
							<el-option label="部署失败" value="4"></el-option>
							<el-option label="上线中" value="5"></el-option>
							<el-option label="上线成功" value="6"></el-option>
							<el-option label="上线失败" value="7"></el-option>
                            <el-option label="回滚中" value="8"></el-option>
                            <el-option label="回滚成功" value="9"></el-option>
                            <el-option label="回滚失败" value="10"></el-option>
                            <el-option label="取消" value="11"></el-option>
                        </el-select>
          </el-form-item> 
          <el-form-item>
            <el-button type="primary" @click="getVersions" icon="el-icon-search" size="small">查询</el-button>
          </el-form-item>
          <el-form-item>
            <el-button @click="resetFilters({versionStatus:''})" icon="el-icon-zoom-in" size="small">清空</el-button>
          </el-form-item>
        </el-form>
        </el-col>
  </section>
</template>
<script>
  import store from '@/store'
  import { createNamespacedHelpers } from 'vuex';
  const {mapGetters, mapActions} = createNamespacedHelpers('system/version')
  export default{
    name: 'VersionFilters',
    data () {
      return {

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
      ...mapActions(['getVersions', 'resetFilters']),
      search () {
        this.getVersions()
      }
    },
    //Vue勾子函数，重新渲染执行
    mounted () {
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
