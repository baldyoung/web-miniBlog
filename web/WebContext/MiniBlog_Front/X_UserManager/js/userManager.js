
window.onload=function(){
    layui.use('form', function(){
        var form = layui.form;

        //监听提交
        form.on('submit(formDemo)', function(data){
            layer.msg(JSON.stringify(data.field));
            return false;
        });
    });

    var data = {
        username:'哈哈哈',
        phone:15280429287,
        email:'789654147@qq.com',
        sex:'男',
        content:'第三方刚发给对方告诉对方房东告诉对方告诉对方法定公司法定更广泛的'
    }


    var addUserMessage = function(data){
        $(".username").val(data.username);
        $(".username").attr("readonly","readonly");
        $(".phone").val(data.phone);
        $(".email").val(data.email);
        if(data.sex=='男'){
            $(".man").attr("checked","checked");
            $(".female").removeAttr("checked");
        }else{
            $(".female").attr("checked","checked");
            $(".man").removeAttr("checked");
        }
        $(".content").text(data.content);
    }

    addUserMessage(data);


    var userManager = {
        init:function(){
            this.initEvent();
        },
       /* initdata:{
            phone:$(".phone").text(),
            email:$(".email").text(),
            sex:$('input:radio[name="sex"]:checked').val(),
            content:$('.content').text()
        },*/
        initEvent:function(){
            this.selectForum();
            this.modifyData();
        },
        modifyData:function(){
            $(".modify").on("click",function(){
                var datas = {};
                datas.username=$(".username").text();

            });
        },
        selectFroum:function(){
            $(".select").on("click",function(){
                $.get("WebContext/MiniBlog_Front/X_Forum/forum");
            })
        }
    }
    userManager.init();

    var initPage = function(){
        $.ajax({
            url:'/WebContext/MiniBlog_Front/X_UserManager/UserManager',
            type:'get',
            contentType:'text/json',
            success:function(data){
                addUserMessage(data);
            }
        });
    }

}
