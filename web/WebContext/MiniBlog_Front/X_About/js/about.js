
window.onload = function() {

    return ;
    //测试数据
    var data = {
        content: '<p>诸葛亮于汉灵帝光和四年（181年）出生在琅琊郡阳都县的一个官吏之家，诸葛氏是琅琊的望族，先祖诸葛丰曾在西汉元帝时做过司隶校尉，诸葛亮的父亲诸葛珪在东汉末年做过泰山郡丞； [2]  诸葛亮3岁时母亲章氏病逝，诸葛亮8岁时丧父，与弟弟诸葛均一起跟随由袁术任命为豫章太守的叔父诸葛玄到豫章（今江西南昌）赴任，东汉朝廷派朱皓取代了诸葛玄职务，诸葛玄就去投奔荆州刘表。</p><p>建安二年（197年），诸葛玄去世，诸葛亮就在隆中（位于今湖北襄阳）隐居，平日喜欢吟诵《梁甫吟》，又常以管仲、乐毅自比，时人对他都是不屑一顾，只有好友徐庶、崔州平等人相信他的才干。</p>',
        read_num: 5000,
        praise_num: 2000
    }
    //添加事件
    var about = {
        init:function(){
            this.initEvent();
        },

        initEvent:function(){
            $(".praise").on("click",function(){
                $(".praise").toggleClass("praise-red");
                if($(".praise").hasClass("praise-red")){
                    var num = $(".praise-num").text();
                    $(".praise-num").text(parseInt(num)+1);
                }else{
                    var num = $(".praise-num").text();
                    $(".praise-num").text(parseInt(num)-1);
                }
            });
        }
    }

    about.init();
    var addDataTohtml = function (data) {
        $(".content").html(data.content);
        $(".read-num").text(data.read_num);
        $(".praise-num").text(data.praise_num) ;
    }
    addDataTohtml(data);

    var findAbout = function () {
        $.ajax({
            url: '/WebContext/MiniBlog_Front/X_About/findAbout',
            type: 'get',
            dataType: 'json',
            contentType: 'text/json',
            success:function(data){
                if(data==null){

                }else{
                    addDataTohtml(data);
                }
            }
        });
    }
}

function getArgFromURL(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");//构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);//匹配目标参数
    if (r != null) return unescape(r[2]);
    return null;//返回参数值
}

$(function(){
    $.ajax({
        url: "getIntro",
        type: 'POST',
        cache: false,
        //async: false, //设置同步
        dataType: 'json',
        contentType: "application/x-www-form-urlencoded;charset=utf-8",
        data: {id: getArgFromURL("id")},
        success: function (data) {
            if (data.result == 'success') {
                var result = data.data;
                $('#introContent').html(result.content);
            } else {
                // TooltipOption.showPrimaryInf(data.inf);
            }
        },
        error: function () {
            // TooltipOption.showWarningInf('服务器连接失败');
        }
    });
});