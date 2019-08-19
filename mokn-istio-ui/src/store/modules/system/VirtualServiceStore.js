
import {virtualServiceList} from '@/service/api';
//状态
const  state={
  virtualservices:[],//
  //总记录数
  total:0,
  //当前页
  pageNo:1,
  //每页显示记录数
  pageSize:10,
  //表格加载状态
  listLoading:false,
  filters:{
    namespace:'default'
  }
}
const getters={
  virtualservices:state=>state.virtualservices,
  total:state=>state.total,
  pageNo:state=>state.pageNo,
  pageSize:state=>state.pageSize,
  listLoading:state=>state.listLoading,
  filters: state=>state.filters
};
const actions={

  getVirtualServices({commit,state}) {
    let param={
      namespace:state.filters.namespace
    };
    commit('listLoading',true);
    virtualServiceList(param).then((res)=>{
      commit('loadList',res.data);
      commit('listLoading',false);
    });

  },
  //表格翻页
  currentChange({commit,dispatch},pageNo){
    commit('currentChange',pageNo);
    dispatch('getVirtualServices');
  },
  //表格设置每页条数
  sizeChange({commit,dispatch},pageSize){
    commit('sizeChange',pageSize);
    dispatch('getVirtualServices');
  },
  //重置过虑器
  resetFilters({commit,dispatch},filters){
    commit('resetFilters',filters);
    dispatch('getVirtualServices');

  },
  //表格载入状态
  listLoading({commit},isShow){
    commit('listLoading',isShow);
  },
  loadList({commit},param){
    commit('loadList',param);
  }
};
const mutations={
  loadList(state,param){
    state.total=param.total;
    state.virtualservices=param.rows;
  },
  //表格载入状态变更
  listLoading(state,isShow){
    state.listLoading=isShow;
  },
  //表格选中行数据状态变更
  selectsChange(state,selects){
    state.selects=selects;
  },
  //表格翻页状态变更
  currentChange(state,pageNo){
    state.pageNo=pageNo;
  },
  //表格每页显示条数状态变更
  sizeChange(state,pageSize){
    state.pageSize=pageSize;
  },
  //查询条件过虑器状态充更
  resetFilters(state,filters){
    state.filters=filters;
    state.versions=[];
  }

};
//导出
export default{
  //启用命名空间
  namespaced:true,
  state,
  getters,
  actions,
  mutations
};
