

$(function(){
	init();
	test();
	
});

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
	var str = "<div class='info-item'>";
		str += "<img class='info-img' src='"+t.userPicture+"' alt=''>";
		str += "<div class='info-text'>";
		str += "<p class='title count'>";
		str += "<span class='name'>"+t.userName+"</span>";
		str += "<span id='commentParselike"+t.id+"' class='info-img' onclick='markLikeOfComment(\""+t.id+"\")' ><i class='layui-icon layui-icon-praise'></i><span id='commentLike"+t.id+"' >"+t.likeNumber+"</span></span>";
		str += "</p>";
		str += "<p class='info-intr'>"+t.content+"</p>";
		str += "</div>";
		str += "</div>";
	return str;
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





