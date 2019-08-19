<!--模板-->
<template>
    <section>
        <!--增改窗体组件-->
        <Form></Form>
        <!--表格组件-->
        <Table></Table>
    </section>
</template>
<!--JS-->
<script>
    //引入自定义组件
    import Form from '@/views/system/user/components/Form';
    import Table from '@/views/system/user/components/Table';

    export default{
        name:'User',
        //加载组件
        components:{
            Form,
            Table
        }
    }
</script>