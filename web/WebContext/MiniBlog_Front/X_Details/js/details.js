
var LikeFlagRecordId = '';
function getArgFromURL(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");//构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);//匹配目标参数
    if (r != null) return unescape(r[2]);
    return null;//返回参数值
}

function initArticleDetailsPage() {
    $.ajax({
        url: "getArticleDetails",
        type: 'POST',
        cache: false,
        //async: false, //设置同步
        dataType: 'json',
        contentType: "application/x-www-form-urlencoded;charset=utf-8",
        data: {articleId: getArgFromURL("articleId")},
        success: function (data) {
            if (data.result == 'success') {
                result = data.data;
                if (result.isLike == 'yes') {
                    LikeFlagRecordId = result.recordId;
                }
                showContent(result);
                showCommentCells(result.commentList);
            } else {
                TooltipOption.showPrimaryInf(data.inf);
            }
        },
        error: function () {
            TooltipOption.showWarningInf('服务器连接失败');
        }
    });
}

$(function () {
    init();

});
function init() {
    $('#commentEditArea').hide();
    //初始化分页条的数据
    PagingModule.CurrentIndexPage = 1; //默认页面是第一页
    PagingModule.MaxPagingNum = 5; //一个页面最多展示5条数据
    PagingModule.TotalNumOfPageBtn = 5; //被显示的分页按钮数量，不能大于数据分页后的页数
    PagingModule.TotalMaxPagingNum = 10; //数据分页后的页数
    //在指定的组件下创建分页条
    initCreateModuleBar('pagingOptionArea');
    initLoadPageModule();
    initArticleDetailsPage();
}

// 获取当前帖子的评论集
function requestCommentList() {

    $.ajax({
        url: "getCommentListOfArticle",
        type: 'POST',
        cache: false,
        //async: false, //设置同步
        dataType: 'json',
        contentType: "application/x-www-form-urlencoded;charset=utf-8",
        data: {articleId: getArgFromURL("articleId")},
        success: function (data) {
            if (data.result == 'success') {
                result = data.data;
                // showContent(result);
                showCommentCells(result);
            } else {
                TooltipOption.showPrimaryInf(data.inf);
            }
        },
        error: function () {
            TooltipOption.showWarningInf('服务器连接失败');
        }
    });
}

//创建内容对应的HTML
function createContentHTML(t) {
    var i, imgs = t.pictureList;
    var str = "<div class='item-box  layer-photos-demo1 layer-photos-demo'>"
    str += "<h3>" + t.title + "</h3>";
    str += "<h5>"+t.name+"&nbsp;&nbsp;&nbsp;发布于：<span>" + t.publishTime + "</span></h5>";
    str += "<p>" + t.content + "</p>";
    if (undefined != imgs) {
        for (i = 0; i < imgs.length; i++)
            str += "<img style='width:200px; height:130px; margin-right:5px; margin-bottom:5px; ' src='../../MiniBlog_CommonRes/res/img/" + imgs[i] + "' alt=''>";
    }
    str += "<div class='count layui-clear'>";
    str += "<span class='pull-left'>阅读 <em>" + t.readNum + "</em></span>";
    str += "<span class='pull-left' style='margin-left:20px; '>评论 <em>" + t.commentAmount + "</em></span>";
    str += "<span onclick='markLikeOfContent(" + t.id + ")' id='iDoNotHaveIdeaForYourName' class='pull-left" + (t.isLike == 'yes' ? " layblog-this" : "") + "' style='margin-left:20px; '><i class='layui-icon layui-icon-praise'></i><em id='noName' >" + t.likeAmount + "</em></span>";
    str += "<span class='pull-right' style='margin-left:20px; color:green; ' onclick='editComment()' >写评论 </span>";
    str += "</div>";
    str += "</div>";
    return str;
}

//将给定的内容进行加载并显示
function showContent(t) {
    var target = $('#contentArea');
    target.html(createContentHTML(t));
}


//打开或者关闭评论编辑区
function editComment() {
    var target = $('#commentEditArea');
    if (target.css('display') == 'none') {
        target.show();
    } else {
        target.hide();
    }
}

//获取评论编辑区的内容，并提交后台处理，即新增评论
function commitComment() {
    var target = $('#newComment');
    if ('' == target.val()) {
        TooltipOption.showPrimaryInf("评论内容不能为空");
        return;
    }
    console.log('新评论的内容为:' + target.val());
    //提交后台处理，留言成功后关闭评论编辑区，否则提示留言失败的原因
    $.ajax({
        url: "addCommentAboutArticle",
        type: 'POST',
        cache: false,
        async: false, //设置同步
        dataType: 'json',
        contentType: "application/x-www-form-urlencoded;charset=utf-8",
        data: {articleId: getArgFromURL("articleId"), comment : target.val()},
        success: function (data) {
            if (data.result == 'success') {
                result = data.data;
                requestCommentList();
                target.val('');
            } else {
                TooltipOption.showPrimaryInf(data.inf);
            }
        },
        error: function () {
            TooltipOption.showWarningInf('服务器连接失败');
        }
    });
    ////评论成功后就重新获取当前评论区的第一页留言数据并加载
    editComment();
}

//拼接给定的评论对应的展示单元的HTML
function createCommentCellHTML(t) {
    var str = "<div class='info-item' style='margin-bottom:5px;'>";
    str += "<img class='info-img' style='width:70px; height:70px; ' src='../../MiniBlog_CommonRes/res/img/" + (t.userPicture == undefined ? "fail.jpg" : t.userPicture) + "' alt=''>";
    str += "<div class='info-text'>";
    str += "<p class='title count'>";
    str += "<span class='name'>" + (t.name == undefined ? '游客' : t.name) + "</span>";
    str += "<span class='name' style='margin-left:35px; '>评论时间：" + t.recordTime + "</span>";
    str += "<span class='name' style='margin-left:63px; color:palevioletred;' onclick='deleteTheComment("+t.id+")'>" + (t.isOwner == 'yes' ? "删除" : "") + "</span>";
   // str += "<span id='commentParselike" + t.id + "' class='info-img' onclick='markLikeOfComment(\"" + t.id + "\")' ><i class='layui-icon layui-icon-praise'></i><span id='commentLike" + t.id + "' >" + t.likeAmount + "</span></span>";
    str += "</p>";
    str += "<p class='info-intr'>" + t.content + "</p>";
    str += "</div>";
    str += "</div>";
    return str;
}

//将给定的评论集加载到展示区域
function showCommentCells(t) {
    var i = 0;
    var target = $('#commentDisplayArea');
    target.html('');
    for (; i < t.length; i++)
        target.append(createCommentCellHTML(t[i]));
}

//点赞操作
function markLikeNO(msgId) {
    var target = $('#like' + msgId);
    //将要点赞的留言编号发送给后台，后台确认后响应前端
    //...
    if ($('#parselike' + msgId).attr('class') != 'info-img layblog-this')
        target.text(parseInt(target.text()) + 1);
    console.log('新的点赞数:' + target.text());

}

//获取并加载下一页展示区内容
function loadNextPageX() {
    //clearDisplayArea();
    loadNextPage();
}

function loadLastPageX() {
    loadLastPage();
}

function loadTargetPageX(t) {
    loadTargetPage($('#pageNumber' + t).text());
}


//---------- 评论点赞功能
function markLikeOfComment(t) {
    var target = $('#commentParselike' + t);
    console.log(target.attr('class'));
    if (target.attr('class') == 'info-img layblog-this') {//已点赞
        console.log('取消点赞');
        target.removeClass('layblog-this');
        $('#commentLike' + t).text(parseInt($('#commentLike' + t).text()) - 1);
    } else {//未点赞
        target.addClass('layblog-this');
        console.log('点赞');
        $('#commentLike' + t).text(parseInt($('#commentLike' + t).text()) + 1);
    }
}

//---------- 内容点赞功能
function markLikeOfContent(t) {
    var target = $('#iDoNotHaveIdeaForYourName');
    console.log(target.attr('class'));
    if (undefined == LikeFlagRecordId || null == LikeFlagRecordId) {
        LikeFlagRecordId = '';
    }
    $.ajax({
        url: "../X_Details/markLikeFlagOfArticle",
        type: 'GET',
        cache: false,
        async: false, //设置同步
        dataType:'json',
        contentType: "application/x-www-form-urlencoded;charset=utf-8",
        data: {articleId : getArgFromURL("articleId"), recordId : LikeFlagRecordId},
        success: function (data) {

            // TooltipOption.showPrimaryInf(data.inf);
            if(data.result == 'success'){
                LikeFlagRecordId = (undefined == data.data ? '' : data.data.id);
                // result =  data.data;
                if (target.attr('class') == 'pull-left layblog-this') {//已点赞
                    console.log('取消点赞');
                    target.removeClass('layblog-this');
                    $('#noName').text(parseInt($('#noName').text()) - 1);
                } else {//未点赞
                    target.addClass('layblog-this');
                    console.log('点赞');
                    $('#noName').text(parseInt($('#noName').text()) + 1);
                }
            } else{
                TooltipOption.showPrimaryInf(data.inf);
            }
        },
        error : function(){
            TooltipOption.showWarningInf('服务器连接失败');
        }
    });
}

function deleteTheComment(t) {
    $.ajax({
        url: "deleteCommentAboutArticle",
        type: 'POST',
        cache: false,
        // async: false, //设置同步
        dataType: 'json',
        contentType: "application/x-www-form-urlencoded;charset=utf-8",
        data: {commentId : t},
        success: function (data) {
            if (data.result == 'success') {
                result = data.data;
                requestCommentList();
            } else {
                TooltipOption.showPrimaryInf(data.inf);
            }
        },
        error: function () {
            TooltipOption.showWarningInf('服务器连接失败');
        }
    });
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
