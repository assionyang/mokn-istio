<!--模板-->
<template>
    <section>
        <!--查询过虑器组件-->
        <Filters></Filters>
        <!--增改窗体组件-->
        <Form></Form>
        <!--表格组件-->
        <Table></Table>
    </section>
</template>
<!--JS-->
<script>
    //引入自定义组件
    import Form from '@/views/system/k8sconfigmap/components/Form';
    import Filters from '@/views/system/k8sconfigmap/components/Filters';
    import Table from '@/views/system/k8sconfigmap/components/Table';

    export default{
        name:'K8sConfigMap',
        //加载组件
        components:{
            Filters,
            Form,
            Table
        }
    }
</script>