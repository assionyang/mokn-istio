<!--模板-->
<template>
    <section>
        <!--查询过虑器组件-->
        <Filters></Filters>
        <!--表格组件-->
        <Table></Table>
    </section>
</template>
<!--JS-->
<script>
    //引入自定义组件
    import Filters from '@/views/system/faultinjection/components/Filters';
    import Table from '@/views/system/faultinjection/components/Table';

    export default{
        name:'FaultInjection',
        //加载组件
        components:{
            Filters,
            Table
        }
    }
</script>