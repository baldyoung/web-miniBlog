
//后台需要提供的对象所应有的属性，及其简介
/*
帖子所对应的对象应该有如下属性
{
	id:被展示单元的编号,
	title:被展示单元的标题,
	publishTime:被发布的时间,
	content:详细内容,
	images:[图片1,图片2, ...],
	commentNumber:已有的评论数量,
	likeNumber:已有的点赞数量,
	isLike:当前用户是否已点赞('yes'/'no')
}
初始化分页数据应该有如下属性
{
	TotalNumOfPaging : undefined, //被分页的数据的总数，这里应该与已有评论数量等同
}

*/




$(function(){
	init();
	test();
});

//前端测试函数
function test(){
	var temp = [{
		id:'101',
		title:'测试表数据一',
		publishTime:'2019-10-5 17:33',
		content:'这只是一组测试数据\n这只是一组测试数据这只是一组测试数据。',
		images:['../A_CommonRes/res/img/item.png', '../A_CommonRes/res/img/item.png', '../A_CommonRes/res/img/item.png', '../A_CommonRes/res/img/item.png'],
		commentNumber:'99',
		likeNumber:'33',
		isLike : 'yes'
	},{
		id:'102',
		title:'测试表数据二',
		publishTime:'2019-10-5 16:33',
		content:'这只是一组测试数据\n这只是一组测试数据这只是一组测试数据。',
		images:['../A_CommonRes/res/img/item.png', '../A_CommonRes/res/img/item.png'],
		commentNumber:'66',
		likeNumber:'3',
		isLike : 'no'
	}];
	showDisplayCells(temp);
	//初始化分页条的数据
	PagingModule.CurrentIndexPage = 1; //默认页面是第一页
	PagingModule.MaxPagingNum = 5; //一个页面最多展示5条数据
	PagingModule.TotalNumOfPageBtn = 5; //被显示的分页按钮数量，不能大于数据分页后的页数
	PagingModule.TotalMaxPagingNum = 10; //数据分页后的页数
	//在指定的组件下创建分页条
	initCreateModuleBar('pagingOptionArea');
	initLoadPageModule();
	
}
//初始化调用
function init(){
	
	
}
//将给定的展示对象集合追加到展示区域
function showDisplayCells(t){
	var i=0;
	var target = $('#DisplayArea');
	for(;i<t.length;i++)
		target.append(createDisplayCellHTML(t[i]));
}
//拼接给定展示单元的HTML
function createDisplayCellHTML(t){
	var i, imgs = t.images;
	var str = "<div class='item'>";
		str += "<div class='item-box  layer-photos-demo1 layer-photos-demo'>";
		str += "<h3><a href='../X_Details/details.html?id="+t.id+"' >"+t.title+"</a></h3>";
		str += "<h5>发布于："+t.publishTime+"<span></span></h5>";
		str += "<p>"+t.content+"</p>";
		for(i=0;i<imgs.length;i++)
			str += "<img style='margin-right:5px; margin-bottom:5px; ' src='"+imgs[i]+"' alt=''>";
		str += "</div>";
		str += "<div class='comment count'>";
		str += "<a href='../X_Details/details.html?id="+t.id+"'>评论"+t.commentNumber+"</a>";
		str += "<a id='cellLikeFlag"+t.id+"' onclick='markLike("+t.id+")' style='cursor:pointer; ' class='"+(t.isLike == 'yes'?"layblog-this":"")+"'><span id='cellLikeFlagA"+t.id+"'>"+(t.isLike == 'yes'?'已赞':'点赞')+"</span><span id='cellLikeFlagB"+t.id+"'>123</span></a>";
		str += "</div>";
		str += "</div>";
	return str;
}
//清空展示区域
function clearDisplayArea(){
	var target = $('#DisplayArea');
	target.html('');
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

//---------- 点赞功能
function markLike(t){
	var target = $('#cellLikeFlag'+t);
	console.log(target.attr('class'));
	if(target.attr('class') == 'layblog-this'){//已点赞
		console.log('取消点赞');
		target.removeClass('layblog-this');
		$('#cellLikeFlagA'+t).text('点赞');
		$('#cellLikeFlagB'+t).text(parseInt($('#cellLikeFlagB'+t).text())-1);
	}else{//未点赞
		target.addClass('layblog-this');
		console.log('点赞');
		$('#cellLikeFlagA'+t).text('已赞');
		$('#cellLikeFlagB'+t).text(parseInt($('#cellLikeFlagB'+t).text())+1);
	}
	
}










