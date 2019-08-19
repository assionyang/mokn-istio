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
    import Filters from '@/views/system/virtualservice/components/Filters';
    import Form from '@/views/system/virtualservice/components/Form';
    import Table from '@/views/system/virtualservice/components/Table';

    export default{
        name:'VirtualService',
        //加载组件
        components:{
            Filters,
            Form,
            Table
        }
    }
</script>