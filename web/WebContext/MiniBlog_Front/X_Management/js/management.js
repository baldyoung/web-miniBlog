
window.onload=function(){
    layui.use(['table','layer'], function(){
        var table = layui.table;

        //第一个实例
        table.render({
            elem: '#demo'
            ,height: 312
            ,url: '/WebContext/MiniBlog_Front/X_Management/table' //数据接口
            ,page:true /*{
                layout:['limit','count','prev','page','next','skip'],
                limit:10,//一页显示条数
                limits:[5,10,15],//每页条数的选择
                groups:3,//显示连续的页码数
                first:'首页',//不显示首页
                last:'尾页'//不显示尾页
            } //开启分页*/
            ,cols: [[ //表头
                {field: 'chk', type:'checkbox', width:80, fixed: 'left' }
                ,{field: 'id', title: 'ID', width:80, sort: true }
                ,{field: 'content', title: '论坛内容', width:300}
                ,{field: 'praise', title: '点赞数', width: 100}
                ,{field: 'comment', title: '评论数', width: 100}

            ]]
        });

    });
}