



function login(){
	
	$('#btn_login').attr('disabled', 'true');
    var userData = {
        account:$('#tAccount').val(),
        password:$('#tPassword').val()
    };
	console.log('登录信息：'+userData);
	location.href = '../X_RealTimeMonitoring/realTimeMonitoring.html';
	return ;
	
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
				RegisterBarOption.showRegisterResult('登录成功', '3s后跳转页面', 'success');
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