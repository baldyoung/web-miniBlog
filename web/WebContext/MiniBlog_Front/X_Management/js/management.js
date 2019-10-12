
window.onload=function(){

    var manager = {
        init:function(){

        },
        initEvent:function(){

        }
    }
    var addButton = '<button class="layui-btn" id="addButton">增加</button>';
    var bodys = document.getElementsByTagName("body")[0];
    bodys.innerHTML+= '<script type="text/html" id="barDemo">'  +
       ' <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detail">查看</' + 'a>' +
       ' <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</' + 'a>' +
        ' <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</' + 'a>' +
        '</' + 'script>';


    layui.use(['table','layer','laypage'], function(){
        var table = layui.table;
        var layer=layui.layer;
        var layerpage=layui.laypage;
        //第一个实例
        table.render({
            elem: '#forum'
            ,height: 312
            ,url: 'table' //数据接口
            ,page:{
                layout:['limit','count','prev','page','next','skip'],
                curr:1,//设置初始页
                limit:10,//一页显示条数
                limits:[5,10,15],//每页条数的选择
                groups:3,//显示连续的页码数
                first:'首页',//不显示首页
                last:'尾页'//不显示尾页
            } //开启分页
            ,cols: [[ //表头
                {field: 'id', title: 'ID', width:80, sort: true ,fixed:'left'}
                ,{field: 'content', title: '论坛内容',align:'center' ,width:300}
                ,{field: 'praise', title: '点赞数', width: 100}
                ,{field: 'comment', title: '评论数', width: 100}
                ,{field: 'comment', title: '创建时间', width: 100}
                ,{field: 'btn', title: addButton, width: 200,toolbar:'#barDemo',fixed:'right'}
            ]]
        });
        table.on('tool(forum)',function(obj){
            var data = obj.data;
            var layEvent = obj.event;

            if(layEvent='del'){
                layer.confirm('确定删除吗？',function(index){

                });

            }
        });

    });
}