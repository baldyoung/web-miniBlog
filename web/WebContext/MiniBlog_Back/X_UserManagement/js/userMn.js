




var currentUserId=undefined;
$(document).ready(function(){

    testFun();
});

//加载用户数据表格
function  loadUserTable(t){
    var dataTable = $('#userTable');
    if ($.fn.dataTable.isDataTable(dataTable)) {
        dataTable.DataTable().destroy();
    }
    for(var i=0;i<t.length;i++){
        t[i].xxx='***';
        if(t[i].grade>=7) t[i].gradeName='管理员';
        else t[i].gradeName='普通'
    }

    dataTable.DataTable({
        'searching':true, //去掉搜索框
        'bLengthChange': true, //去掉每页显示多少条数据方法
        "serverSide": false, //关闭分页操作，默认就是关闭
        "autoWidth" : false, //
        "bSort": false, //打开排序功能
        //"order": [[ 0, "desc" ]], //默认排序的指标
        'data':t, //表格的数据源
        'columns':[{
            data:'account'
        },{
            data:'userState'
        },{
            data:'sex'
        },{
            data:'mailBox'
        },{
            data:'xxx'
        },{
            data:'registerTime'
        }],
        "columnDefs": [{
            "render" : function(data, type, row) {
                var a = "";
                //a += "<button type='button' class='btn btn-primary' onclick='showEditStudent(\""+0+"\")' data-toggle='modal' data-target='#myModal5' title='编辑用户' data-toggle='dropdown' style='margin-right:15px; margin-bottom: -1px;'><i class='fa fa-pencil-square-o'></i>&nbsp;编辑</button>"
                //a += "<button type='button' class='btn btn-primary' style='margin-top:3px;' onclick='loadOrderForm(\""+row.id+"\")'  data-toggle='dropdown' data-target=\"#OrderFormWindow\" style='margin-right:15px; margin-bottom: -1px;'><i class='fa fa-building-o'></i>&nbsp;查看</button>"
                //a += "<button type='button' class='btn btn-primary' onclick='courseList(\""+row.id+"\")' data-toggle='modal' data-target='#courseListDialog' title='课程列表' data-toggle='dropdown' style='margin-right:15px; margin-bottom: -1px;'><i class='fa fa-list'></i>&nbsp;课程列表</button>"
                a+="<button class=\"btn btn-primary \" style='margin-top:3px;' type=\"button\" data-toggle=\"modal\" data-target=\"#OrderFormWindow\" onclick=\"deleteUser('"+row.id+"')\"><i class=\"fa fa-building-o\"></i>删除"
                a+="</button>"
                a+="<button class=\"btn btn-primary \" style='margin-top:3px; margin-left:5px; ' type=\"button\" data-toggle=\"modal\" data-target=\"#myModal\" onclick=\"openUserInfWin('"+row.id+"','"+row.name+"','"+row.account+"', '"+row.password+"', '"+row.grade+"' )\"><i class=\"fa fa-building-o\"></i>修改"
                a+="</button>"
				a+="<button class=\"btn btn-primary \" style='margin-top:3px; margin-left:5px; ' type=\"button\" onclick='alert(\"待开发\")'><i class=\"fa fa-building-o\"></i>个人首页"
                a+="</button>"
                return a;
            },
            "targets" :6
        }]
        //$('#OrderFormData_wrapper').css('padding-bottom', '0px');

    })
}

//加载用户的详细详细
function loadUserInf(t){
    currentUserId=t.id;
    $('#nameText').val(t.name);
    $('#accountText').val(t.account);
    $('#passwordText').val(t.password);
    $('#gradeText').val(t.grade);
}
//重置用户详细窗口
function resetUserInf(){
    $('#nameText').val('');
    $('#accountText').val('');
    $('#passwordText').val('');
    $('#gradeText').val(3);
}
//加载指定用户的信息
function openUserInfWin(tid, tname, taccount, tpwd, tgrade){
    var temp = {
        id:tid,
        name:tname,
        account:taccount,
        password:tpwd,
        grade:(tgrade<'7'?'3':'7')
    }
    $('#myModal_title').text('用户信息-修改')
    loadUserInf(temp);
}
//准备添加新用户
function readForAddUser(){
    currentUserId=undefined;
    $('#myModal_title').text('用户信息-新增')
    resetUserInf();
}
//保存用户信息
function saveUserInf(){
    var temp = {
        name: $('#nameText').val(),
        account: $('#accountText').val(),
        password: $('#passwordText').val(),
        grade: $('#gradeText').val()
    }
    if(currentUserId!=undefined)
        temp.id=currentUserId;
    var tip = check(temp);
    if(true!=tip){//格式不正确
        showTip(tip);
    }else{
        postUserInf(temp)
    }
}

//检查指定的json对象的格式是否正确
function check(t){
    if(undefined==t || undefined==t.name || undefined==t.account || undefined==t.password || undefined==t.grade) return undefined;
    if(t.name.length>=50 ) return '用户名称需要小于50个字符';
    if(t.account.length>=20) return '登录名需要小于20个字符';
    if(t.password.length>=20) return '登录密码需要小于20个字符';
    if(t.name=='' || t.account=='' || t.password=='') return '信息不能留空';
    return true;
}
//提示信息
function showTip(t){
    toastr.options = {
        "closeButton": true,
        "debug": false,
        "progressBar": true,
        "positionClass": "toast-top-center",
        "onclick": null,
        "showDuration": "400",
        "hideDuration": "1000",
        "timeOut": "7000",
        "extendedTimeOut": "1000",
        "showEasing": "swing",
        "hideEasing": "linear",
        "showMethod": "fadeIn",
        "hideMethod": "fadeOut"
    }
    toastr.warning(''+t);

}


/*
[{
            data:'account'
        },{
            data:'userState'
        },{
            data:'sex'
        },{
            data:'mailBox'
        },{
            data:'xxx'
        },{
            data:'registerTime'
        }],
*/
function testFun(){
    var temp = [{
        id:'10010',
        account:'肖某',
        password:'123456789',
        userState:'正常',
		sex:'男',
		mailBox:'123456@qq.com',
		xxx:'***',
		registerTime:'2019-08-13'
    },{
        id:'10010',
        account:'肖某某',
        password:'123456789',
        userState:'已封禁',
		sex:'男',
		mailBox:'123456@qq.com',
		xxx:'***',
		registerTime:'2019-08-13'
    }]
    loadUserTable(temp);
}