


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

//登录请求
function login(){
	if($('#tAccount').val() == '' || $('#tPassword').val() == ''){
		RegisterBarOption.showRegisterResult('登录账户和密码不能为空', '请补全', 'warning');
		return;
	}
	$('#btn_login').attr('disabled', 'true');
    var userData = {
        account:$('#tAccount').val(),
        password:$('#tPassword').val()
    };
    $.ajax({
        url: "loginCheck",
        type: 'POST',
        cache: false,
        dataType:'json',
        contentType: "application/json;charset=utf-8",
        data: JSON.stringify(userData),
        success: function (data) {
            console.log(data);
			if(data.result == 'true'){
				RegisterBarOption.showRegisterResult('登录成功', '即将跳转页面', 'success');
				window.location.href = data.inf;
			} else if(data.result == 'false'){
				RegisterBarOption.showRegisterResult('登录失败', '账户或者密码错误', 'warning');
				$('#btn_login').attr('disabled', 'false'); //这样是无法解除按钮的disabled状态
				$('#btn_login').removeAttr('disabled');
			} else{
				RegisterBarOption.showRegisterResult('登录失败', '数据获取异常，请联系系统管理员', 'danger');
			}
        },
		error : function(){
			RegisterBarOption.showRegisterResult('登录失败', '服务器连接异常，请联系系统管理员', 'danger');
		}
    });


}











