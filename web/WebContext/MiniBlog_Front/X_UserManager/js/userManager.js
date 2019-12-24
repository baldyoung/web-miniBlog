





$(function(){
    UserInfoModule.requestAndLoadUserInfo();
    UserInfoModule.updateAction('close');
    $('#userPictureArea').attr('onclick', '');
});

var UserInfoModule = {
    currentUserInfo : undefined,
    backupUserInfo : undefined,
    loadUserInfo : function(t) {
        $('#userNameArea').val(t.userName);
        $('#userMailboxArea').val(t.userMailbox);
        $('#userAccountArea').val(t.userAccount);
        $('#userPasswordArea').val(t.userPassword);
    },
    changeUserPicture : function(t) {
        previewImage(document.getElementById('userPictureArea'), t);
    },
    packageNewUserInfo : function() {
        var isChanged = false;
        var newUserInfo = new FormData();
        var userName = $('#userNameArea').val();
        var userMailbox = $('#userMailboxArea').val();
        var userAccount = $('#userAccountArea').val();
        var userPassword = $('#userPasswordArea').val();
        var currentInfo = UserInfoModule.currentUserInfo;
        if (userName != currentInfo.userName) {
            newUserInfo.append('userName', userName);
            UserInfoModule.backupUserInfo.userName = userName;
            isChanged = true;
        }
        if (userMailbox != currentInfo.userMailbox) {
            newUserInfo.append('userMailbox', userMailbox);
            UserInfoModule.backupUserInfo.userMailbox = userMailbox;
            isChanged = true;
        }
        if (userAccount != currentInfo.userAccount) {
            newUserInfo.append('userAccount', userAccount);
            UserInfoModule.backupUserInfo.userAccount = userAccount;
            isChanged = true;
        }
        if (userPassword != currentInfo.userPassword) {
            newUserInfo.append('userPassword', userPassword);
            UserInfoModule.backupUserInfo.userPassword = userPassword;
            isChanged = true;
        }
        var fileObj = $('#userPictureFile')[0].files[0];
        if (undefined != fileObj) {
            newUserInfo.append('userPicture', fileObj);
            isChanged = true;
        }
        return (isChanged == true ? newUserInfo : undefined);
    },
    updateUserInfo : function() {
        var newUserInfo = UserInfoModule.packageNewUserInfo();
        if (undefined == newUserInfo) {
            return;
        }
        $.ajax({
            type: 'POST',
            url: 'updateUserInfo',
            data: newUserInfo,
            async: false, //设置同步
            processData: false, // 告诉jquery要传输data对象
            contentType: false,   // 告诉jquery不需要增加请求头对于contentType的设置
            success: function (data) {
                if(data.result == 'success'){
                    UserInfoModule.currentUserInfo = UserInfoModule.backupUserInfo;
                    TooltipOption.showPrimaryInf('修改成功');
                    UserInfoModule.updateAction('close');
                } else{
                    TooltipOption.showWarningInf('修改失败:'+data.inf);
                    UserInfoModule.updateAction('close');
                }
            },
            error : function(){
                TooltipOption.showWarningInf('服务器连接失败');
            }
        });
    },
    requestAndLoadUserInfo : function() {
        $.ajax({
            url: "userInfo",
            type: 'GET',
            cache: false,
            dataType:'json',
            async: false, //设置同步
            contentType: "application/x-www-form-urlencoded;charset=utf-8",
            data: {},
            success: function (data) {
                if(data.result == 'success'){
                    data = data.data;
                    $('#userPictureArea').attr('src', '../../MiniBlog_CommonRes/res/img/'+data.userPicture);
                    UserInfoModule.currentUserInfo = data;
                    UserInfoModule.backupUserInfo = data;
                    UserInfoModule.loadUserInfo(data);
                } else{
                }
            },
            error : function(){
                TooltipOption.showWarningInf('服务器连接失败');
            }
        });
    },
    updateAction : function(t) {
        if (t == 'open') {
            $('#btnUpdate').hide();
            $('#btnCancel').show();
            $('#btnSubmit').show();
            $('#tipA').show();
            $('#userPictureArea').attr('onclick', 'userPictureFile.click()');
            UserInfoModule.setUserInfoReadOnly(false);
        } else {
            $('#btnUpdate').show();
            $('#btnSubmit').hide();
            $('#btnCancel').hide();
            $('#tipA').hide();
            $('#userPictureArea').attr('onclick', '');
            UserInfoModule.setUserInfoReadOnly(true);
            UserInfoModule.loadUserInfo(UserInfoModule.currentUserInfo);
        }
    },
    setUserInfoReadOnly : function(t) {
        if (t == true) {
            $('.layui-input').attr('readonly', 'true');
        } else {
            $('.layui-input').removeAttr('readonly');
        }
    }
}
function previewImage(obj, file){
    var reader = new FileReader();
    reader.onload = function(evt){//这个调用应该是异步进行的
        obj.src = evt.target.result; //似乎是读取目标文件的二进制数据，然后存入指定img的内存区域，应该是吧...
    };
    reader.readAsDataURL(file.files[0]);
}
//提示框控制模块-Y
var TooltipOption = {
    targetId : '#tooltipArea',
    btnYesId : '#btnTipYes',
    btnNoId : '#btnTipNo',
    colorOfWarning : '#ff8585',
    colorOfPrimary : '#93d0dd',
    run : undefined,
    showPrimaryInf : function(d){
        $('#tipTitleDiv').css('background', this.colorOfPrimary);
        $('#tipContent').text(d);
        this.show();
        $(this.btnNoId).hide();
    },
    showWarningInf : function(d){
        $('#tipTitleDiv').css('background', this.colorOfWarning);
        $('#tipContent').text(d);
        this.show();
        $(this.btnNoId).hide();
    },
    runIfOk : function(t, r){
        this.run = r;
        $('#tipTitleDiv').css('background', this.colorOfPrimary);
        $('#tipContent').text(t);
        $(this.btnNoId).show();
        $(this.targetId).show();
    },
    ok : function(){
        if(undefined!=this.run) this.run();
        this.run = undefined;
        this.hide();
    },
    cancel : function(){
        this.run = undefined;
        this.hide();
    },
    show : function(){
        $(this.btnNoId).show();
        $(this.targetId).show();
    },
    hide : function(){
        $(this.targetId).hide();
    }
}













window.onload=function(){
    return;
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
