
var resultInfOption = {
    id : '#resultInf',
    showInf : function(t){
        $(this.id).css('color', 'black');
        $(this.id).text(t);
    },
    showWarningInf : function(t){
        $(this.id).css('color', '#ffc361');
        $(this.id).text(t);
    },
    showDangerInf : function(t){
        $(this.id).css('color', 'red');
        $(this.id).text(t);
    },
    showOkInf : function(t){
        $(this.id).css('color', 'green');
        $(this.id).text(t);
    }
}


function login(){
	
	$('#btn_login').attr('disabled', 'true');
    var userData = {
        account:$('#tAccount').val(),
        password:$('#tPassword').val()
    };
	console.log('登录信息：'+userData);
	//location.href = '../X_RealTimeMonitoring/realTimeMonitoring.html';

	
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
				resultInfOption.showOkInf('登录成功，正在跳转页面...');
				window.location.href = data.inf;
			} else if(data.result == 'false'){
				resultInfOption.showWarningInf('账户或者密码错误');
				$('#btn_login').attr('disabled', 'false'); //这样是无法解除按钮的disabled状态
				$('#btn_login').removeAttr('disabled');
			} else{
			    resultInfOption.showDangerInf('获取数据异常')
			}
        },
		error : function(){
			resultInfOption.showDangerInf('服务器连接异常，请联系系统管理员');
		}
    });
	
	
}