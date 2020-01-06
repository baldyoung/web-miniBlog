

$(function(){
	MessageModule.init();
});

var MessageModule  = {
	targetId : '#messageDisplayArea',
	currentPageNumber : 0,
	init : function() {
		$(MessageModule.targetId).html('');
		MessageModule.currentPageNumber = 0;
		MessageModule.requestAndShowMessage();
	},
	requestAndShowMessage : function() {
	    if (-1 == MessageModule.currentPageNumber) {
	        return ;
        }
		$.ajax({
			url: "messageList",
			type: 'GET',
			cache: false,
			dataType:'json',
			async: false, //设置同步
			contentType: "application/x-www-form-urlencoded;charset=utf-8",
			data: {pageNumber : MessageModule.currentPageNumber},
			success: function (data) {
				if(data.result == 'success'){
					data = data.data;
					if (data.noMore == 'no') {
						MessageModule.currentPageNumber += 1;
					} else {
					    MessageModule.currentPageNumber = -1;
                    }
					MessageModule.showMessageList(data.list);
				} else{
					TooltipOption.showPrimaryInf(data.inf);
				}
			},
			error : function(){
				//TooltipOption.showWarningInf('服务器连接失败');
			}
		});
	},
	createDisplayCellHTML : function(t) {
	    if (isEmpty(t.content)) {
	        t.content = '什么也没写呢...'
        }
		var str = "<div id='messageDiv"  + t.id +"' class='info-item' style='margin-bottom:5px;'>";
		str += "<img class='info-img' style='width:70px; height:70px; ' src='../../MiniBlog_CommonRes/res/img/" + (t.pictureName == undefined ? "fail.jpg" : t.pictureName) + "' alt=''>";
		str += "<div class='info-text'>";
		str += "<p class='title count'>";
		str += "<span class='name'>" + (t.name == undefined ? '游客' : t.name) + "</span>";
		str += "<span class='name' style='margin-left:35px; '>评论时间：" + t.recordTime + "</span>";
		str += "<span class='name' style='margin-left:63px; color:palevioletred; cursor:pointer; ' onclick='MessageModule.deleteMessage("+t.id+")'>" + (t.isOwner == 'yes' ? "删除" : "") + "</span>";
		// str += "<span id='commentParselike" + t.id + "' class='info-img' onclick='markLikeOfComment(\"" + t.id + "\")' ><i class='layui-icon layui-icon-praise'></i><span id='commentLike" + t.id + "' >" + t.likeAmount + "</span></span>";
		str += "</p>";
		str += "<textarea id='commentArea" + t.id + "' class='info-intr' readonly='true' style='resize:none; background:#f2f2f2; width:100%; margin-top:-33px; border:0px solid;'>" + t.content + "</textarea>";
		str += "</div>";
		str += "</div>";
		return str;
	},
	showMessageList : function(t, isAppend) {
		if (undefined == t) {
			console.log('MesssageModule.showMessageList error : t is undefined');
			return;
		}
		var target = $(MessageModule.targetId);
		var i, item;
		if (isAppend == 'false') {
			target.html('');
		}
		for (i=0; i<t.length; i++) {
			item = t[i];
			target.append(MessageModule.createDisplayCellHTML(item))
			var textarea=document.getElementById('commentArea' + t[i].id);
			textarea.style.height = textarea.scrollHeight + 5 + 'px';
		}
	},
	deleteDisplayCell : function(t) {
		$('#messageDiv'+t).remove();
	},
	deleteMessage : function(t) {
		$.ajax({
			url: "deleteMessage",
			type: 'GET',
			cache: false,
			dataType:'json',
			async: false, //设置同步
			contentType: "application/x-www-form-urlencoded;charset=utf-8",
			data: {recordId : t},
			success: function (data) {
				if(data.result == 'success'){
					MessageModule.deleteDisplayCell(t);
				} else{
					TooltipOption.showPrimaryInf(data.inf);
				}
			},
			error : function(){
				//TooltipOption.showWarningInf('服务器连接失败');
			}
		});
	},
	addMessage : function () {
		var target = $('#LAY-msg-content');
		var newMsg = {
			content:target.val()
		}
		console.log('新留言内容:'+newMsg.content);
		var i;
		for (i=0; i < newMsg.content.length; i++) {
			if (newMsg.content[i] != ' ')
				break;
		}
		if (newMsg.content.length == 0 || i == newMsg.content.length) {
			return ;
		}
		$.ajax({
			url: "addMessage",
			type: 'POST',
			cache: false,
			dataType:'json',
			async: false, //设置同步
			contentType: "application/x-www-form-urlencoded;charset=utf-8",
			data: newMsg,
			success: function (data) {
				if(data.result == 'success'){
					// MessageModule.showMessageList();
					target.val('');
					MessageModule.init();
				} else{
					TooltipOption.showPrimaryInf(data.inf);
				}
			},
			error : function(){
				//TooltipOption.showWarningInf('服务器连接失败');
			}
		});
	}
}




function isEmpty(t) {
    if (undefined == t || null == t) {
        return true;
    }
    var i;
    for (i=0; i<t.length; i++) {
        if (t[i] != ' ')
            break;
    }
    return i == t.length;
}











function test(){
	var temp =[{
		id:101,
		userName:'肖人人',
		userPicture:'../A_CommonRes/res/img/info-img.png',
		content:'测试数据...测试数据...',
		likeNumber:33
	},{
		id:102,
		userName:'肖姐姐',
		userPicture:'../A_CommonRes/res/img/info-img.png',
		content:'测试数据...测试数据...',
		likeNumber:36
	}];
	showDisplayCells(temp);
	
}

function init(){
	//初始化分页条的数据
	PagingModule.CurrentIndexPage = 1; //默认页面是第一页
	PagingModule.MaxPagingNum = 5; //一个页面最多展示5条数据
	PagingModule.TotalNumOfPageBtn = 5; //被显示的分页按钮数量，不能大于数据分页后的页数
	PagingModule.TotalMaxPagingNum = 10; //数据分页后的页数
	//在指定的组件下创建分页条
	initCreateModuleBar('pagingOptionArea');
	initLoadPageModule();
	
}

/*DisplayCell所对应的对象应该有如下属性
{
	id:被展示单元的编号,
	userName:留言的用户名称,
	userPicture:留言用户的头像,
	content:留言内容,
	likeNumber:已有的点赞数量
}


*/

//将给定的展示对象集合追加到展示区域
function showDisplayCells(t){
	var i=0;
	var target = $('#DisplayArea');
	for(;i<t.length;i++)
		target.append(createDisplayCellHTML(t[i]));
}
//拼接给定展示单元的HTML
function createDisplayCellHTML(t){

}

//添加留言
function addNewMessage(){
	var target = $('#LAY-msg-content');
	var newMsg = {
		content:target.val()
	}
	console.log('新留言内容:'+newMsg.content);
	//发送给后台，后台返回当前用户的头像以及名称，同时还有新留言的编号
	//...
	//提交成功后就重新获取当前留言区的第一页留言数据并加载
	target.val('');
}

//---------- 评论点赞功能
function markLikeOfComment(t){
	var target = $('#commentParselike'+t);
	console.log(target.attr('class'));
	if(target.attr('class') == 'info-img layblog-this'){//已点赞
		console.log('取消点赞');
		//target.attr('class', 'info-img');
		target.removeClass('layblog-this');
		//console.log('#'+target.attr('class'));
		$('#commentLike'+t).text(parseInt($('#commentLike'+t).text())-1);
	}else{//未点赞
		target.addClass('layblog-this');
		console.log('点赞');
		$('#commentLike'+t).text(parseInt($('#commentLike'+t).text())+1);
	}	
}
	


//获取并加载下一页展示区内容
function loadNextPageX(){
	//clearDisplayArea();
	loadNextPage();
}

function loadLastPageX(){
	loadLastPage();
}

function loadTargetPageX(t){
	loadTargetPage($('#pageNumber'+t).text());
}





