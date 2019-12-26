


function requestAndShowIntro() {
	$.ajax({
		url: "getIntro",
		type: 'GET',
		cache: false,
		dataType:'json',
		contentType: "application/x-www-form-urlencoded;charset=utf-8",
		data: {},
		success: function (data) {
			if(data.result == 'success'){
				$('#introDisplayArea').text(data.data.content);
			} else{
				//TooltipOption.showPrimaryInf(data.inf);
			}
		},
		error : function(){
			//TooltipOption.showWarningInf('服务器连接失败');
		}
	});
}
function requestAndShowArticleList(fIndex, amount) {
	var temp = {
		firstIndex : ""+fIndex,
		maxAmount : ""+amount
	};
	$.ajax({
		url: "getArticleList",
		type: 'POST',
		cache: false,
		dataType:'json',
		contentType: "application/x-www-form-urlencoded;charset=utf-8",
		data: temp,
		success: function (data) {
			if(data.result == 'success'){
				showDisplayCells(data.data);
			} else{
				//TooltipOption.showPrimaryInf(data.inf);
			}
		},
		error : function(){
			//TooltipOption.showWarningInf('服务器连接失败');
		}
	});
}
function requestInitPagingData() {
	var result;
	$.ajax({
		url: "getInitPagingData",
		type: 'POST',
		cache: false,
		dataType:'json',
		async: false,
		contentType: "application/x-www-form-urlencoded;charset=utf-8",
		data: {},
		success: function (data) {
			if(data.result == 'success'){
				result = data.data;
			} else{
				//TooltipOption.showPrimaryInf(data.inf);
			}
		},
		error : function(){
			//TooltipOption.showWarningInf('服务器连接失败');
		}
	});
	return result;
}



$(function(){
	init();
});
//初始化调用
function init(){
	requestAndShowIntro();
	// requestAndShowArticleList();
	var initData = requestInitPagingData();
	//初始化分页条的数据
	PagingModule.run = requestAndShowArticleList;
	PagingModule.CurrentIndexPage = 1; //默认页面是第一页
	PagingModule.MaxPagingNum = initData.maxAmount; //一个页面最多展示5条数据
	PagingModule.TotalMaxPagingNum = Math.ceil(initData.totalAmount / initData.maxAmount); //数据分页后的页数
	PagingModule.TotalNumOfPageBtn = 6 > PagingModule.TotalMaxPagingNum ? PagingModule.TotalMaxPagingNum : 6; //被显示的分页按钮数量，不能大于数据分页后的页数
	//在指定的组件下创建分页条
	initCreateModuleBar('pagingOptionArea');
	initLoadPageModule();
}
//将给定的展示对象集合追加到展示区域
function showDisplayCells(t){
	var i=0;
	var target = $('#DisplayArea');
	target.html('');
	for(;i<t.length;i++) {
		target.append(createDisplayCellHTML(t[i]));
		var artId = t[i].articleId;
		articleLikeFlagMap[artId] = t[i].likeRecordId;
	}

}
//拼接给定展示单元的HTML
function createDisplayCellHTML(t){
	t.id = t.articleId;
	var i, imgs = t.pictureList;
	var str = "<div class='item'>";
	str += "<div class='item-box  layer-photos-demo1 layer-photos-demo'>";
	str += "<h3><a href='../X_Details/details.html?articleId="+t.id+"' >"+t.title+"</a><span style='float:right; color:#fe2727; font-size:13px; cursor:pointer; font-weight:bold; ' ></span></h3>";
	str += "";
	str += "<h5>"+t.name+"&nbsp;&nbsp;&nbsp;发布于："+t.publishTime+"<span></span></h5>";
	str += "<p>"+t.content+"</p>";
	if (undefined != imgs && undefined != imgs.length){
		for(i=0;i<imgs.length;i++) {
			str += "<img style='width:200px; height:130px; margin-right:5px; margin-bottom:5px; ' src='../../MiniBlog_CommonRes/res/img/"+imgs[i]+"' alt=''>";
		}
	}
	str += "</div>";
	str += "<div class='comment count'>";
	str += "<a href='../X_Details/details.html?articleId="+t.id+"'>评论"+t.commentAmount+"</a>";
	str += "<a id='cellLikeFlag"+t.id+"' onclick='markLike("+t.id+")' style='cursor:pointer; ' class='"+(t.isLike == 'yes'?"layblog-this":"")+"'><span id='cellLikeFlagA"+t.id+"'>"+(t.isLike == 'yes'?'已赞':'点赞')+"</span><span id='cellLikeFlagB"+t.id+"'>"+t.likeAmount+"</span></a>";
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
	markLikeOfContent(t);
	return;
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

var articleLikeFlagMap = {
};
function markLikeOfContent(t) {
	var LikeFlagRecordId = articleLikeFlagMap[t];
	var target = $('#cellLikeFlag'+t);
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
		data: {articleId : t, recordId : LikeFlagRecordId},
		success: function (data) {
			if(data.result == 'success'){
				LikeFlagRecordId = (undefined == data.data ? '' : data.data.id);
				articleLikeFlagMap[t] = LikeFlagRecordId;
				// result =  data.data;
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
			} else{
				TooltipOption.showPrimaryInf(data.inf);
			}
		},
		error : function(){
			TooltipOption.showWarningInf('服务器连接失败');
		}
	});
}







