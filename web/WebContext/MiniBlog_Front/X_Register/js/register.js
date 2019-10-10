

//状态栏的控制核心
var RegisterBarOption = {
	title : undefined,
	content : undefined,
	state : undefined,
	target : '#resultStatusBar',
	target_title : '#resultStatusBar_title',
	target_content : '#resultStatusBar_content',
	showRegisterResult : function (t, c, s){
		this.resetState(s);
		this.title = t;
		this.content = c;
		$(this.target).show();
		$(this.target_title).text(this.title);
		$(this.target_content).text(this.content);
	},
	resetState : function(t){
		var temp = 'jq-icon-'+this.state;
		if(this.state != undefined && $(this.target).hasClass(temp) )
			$(this.target).removeClass(temp);
		temp = 'jq-icon-'+t;
		$(this.target).addClass(temp);
		this.state = t;
	},
	closeResultStatusBar : function(){
		$(this.target).hide();
	}
};

var checkDataFormat = {
	checkNewUserData : function(t){
		
	}
};
				

$(function(){
	
	init();
	
	
});

function init(){
	
}



//注册请求
function register(){
	if($('#newAccount').val() == '' || $('#newMailbox').val() == '' || $('#newPassword').val() == ''){
		RegisterBarOption.showRegisterResult('信息不能留空', '请补全信息', 'warning');
		return ;
	}
	if($('#newPassword').val() != $('#newPassword2').val()){
		RegisterBarOption.showRegisterResult('两次密码不同', '请再次输入密码', 'warning');
		return ;
	}
	
	$('#btn_register').attr('disabled', 'true');
    var userData = {
        account:$('#newAccount').val(),
        password:$('#newPassword').val(),
        mailbox:$('#newMailbox').val()
    };
    $.ajax({
        url: "registerNewUser",
        type: 'POST',
        cache: false,
        dataType:'json',
        contentType: "application/json;charset=utf-8",
        data: JSON.stringify(userData),
        success: function (data) {
            console.log(data);
			if(data.result == 'true'){
				RegisterBarOption.showRegisterResult('注册成功', '请记住你的登录名：'+$('#newAccount').val(), 'success');
			} else if(data.result == 'false'){
				RegisterBarOption.showRegisterResult('注册失败', data.inf, 'warning');
				$('#btn_register').attr('disabled', 'false');
				$('#btn_login').removeAttr('disabled');
			} else{
				RegisterBarOption.showRegisterResult('注册失败', '数据获取异常，请联系系统管理员', 'danger');
			}
        },
		error : function(){
			RegisterBarOption.showRegisterResult('注册失败', '服务器连接异常，请联系系统管理员', 'danger');
		}
    });


}


	
	









