<!--模板-->
<template>
    <section>
        <!--查询过虑器组件-->
        <Filters></Filters>
        <Form></Form>
        <!--表格组件-->
        <Table></Table>
    </section>
</template>
<!--JS-->
<script>
    //引入自定义组件
    import Filters from '@/views/system/destinationrule/components/Filters';
    import Form from '@/views/system/destinationrule/components/Form';
    import Table from '@/views/system/destinationrule/components/Table';

    export default{
        name:'DestinationRule',
        //加载组件
        components:{
            Filters,
            Form,
            Table
        }
    }
</script>