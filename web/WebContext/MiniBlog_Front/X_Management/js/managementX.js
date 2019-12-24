

/*
ajax 的回调函数里this字段的指向，不是调用ajax的对象
*/

//文本编辑器
var WordEditor;


//帖子管理模块-Y
var ArticleOption = {
	targetId : '#DisplayArea',
	readyToDeleteId : undefined,
	init : function(){
		
	},
	loadData : function(fIndex, amount){// 请求分页后的用户帖子集，并进行加载显示
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
				console.log('ArticleOption.loadData()-ajax-receive-data:'+data.toString());
				if(data.result == 'success'){
					ArticleOption.showArticle(data.data);
				} else{
					TooltipOption.showPrimaryInf(data.inf);
				}
			},
			error : function(){
				TooltipOption.showWarningInf('服务器连接失败');
			}
		});
	},
	deleteArticleAction : function(t){ // 准备删除指定帖子
		ArticleOption.readyToDeleteId = t;
		TooltipOption.runIfOk('确认删除该帖子', ArticleOption.deleteArticle);
	},
	deleteArticle : function(){ // 发送删除指定帖子的请求
		if(undefined == ArticleOption.readyToDeleteId) return;
		var temp ={
			articleId : ArticleOption.readyToDeleteId
		};
		ArticleOption.readyToDeleteId = undefined;
		$.ajax({
			url: "deleteArticle",
			type: 'POST',
			cache: false,
			dataType:'json',
			contentType: "application/x-www-form-urlencoded;charset=utf-8",
			data: temp,
			success: function (data) {
				// console.log('ArticleOption.loadData()-ajax-receive-data:'+data.toString());
				if(data.result == 'success'){
					TooltipOption.showPrimaryInf('删除成功');
					location.reload();
				} else{
					TooltipOption.showPrimaryInf('删除失败');
				}
			},
			error : function(){
				TooltipOption.showWarningInf('服务器连接失败');
			}
		});
	},
	showArticle : function(t){ // 加载并显示给定的帖子集合
		var i=0;
		var target = $(this.targetId);
		target.html('');
		for(;i<t.length;i++)
		target.append(this.createDisplayCellHTML(t[i]));
	},
	resetDisplayArea : function(){
		$(id).html('');
	},
	createDisplayCellHTML : function(t){
		var i, imgs = t.pictureList;
		var str = "<div class='item'>";
		str += "<div class='item-box  layer-photos-demo1 layer-photos-demo'>";
		str += "<h3><a href='../X_Details/details.html?articleId="+t.id+"' >"+t.title+"</a><span style='float:right; color:#fe2727; font-size:13px; cursor:pointer; font-weight:bold; ' onclick=\"ArticleOption.deleteArticleAction("+t.id+")\">删除</span></h3>";
		str += "";
		str += "<h5>"+t.name+"&nbsp;&nbsp;&nbsp;发布于："+t.publishTime+"<span></span></h5>";
		str += "<p>"+t.content+"</p>";
		if (undefined != imgs && undefined != imgs.length)
			for(i=0;i<imgs.length;i++)
				str += "<img style='width:200px; height:130px; margin-right:5px; margin-bottom:5px; ' src='../../MiniBlog_CommonRes/res/img/"+imgs[i]+"' alt=''>";
		str += "</div>";
		str += "<div class='comment count'>";
		str += "<a href='../X_Details/details.html?articleId="+t.id+"'>评论"+t.commentAmount+"</a>";
		str += "<a id='cellLikeFlag"+t.id+"' onclick='markLike("+t.id+")' style='cursor:pointer; ' class='"+(t.isLike == 'yes'?"layblog-this":"")+"'><span id='cellLikeFlagA"+t.id+"'>"+(t.isLike == 'yes'?'已赞':'点赞')+"</span><span id='cellLikeFlagB"+t.id+"'>"+t.likeAmount+"</span></a>";
		str += "</div>";
		str += "</div>";
		return str;
	}
}
//编辑区控制模块-Y
var ArticleEditAreaOption = {
	targetId : '#articleEditArea',
	targetTitleId : '#articleTitle',
	btnPublishYesId : '#btnPublishYes',
	btnPublishNoId : '#btnPublishNo',	
	wordEditor : undefined,
	init : function(){
		this.wordEditor = new Simditor({
                textarea: $('#editor'),
				toolbar: ['bold', 'italic', 'underline', 'color', 'link'], //工具栏参数
				toolbarHidden:false //选择不隐藏工具栏
        });
	},
	publishArticle : function(){
		var form = ImgOption.packageData();
		form.append('content', this.wordEditor.getValue());
		form.append('title', $(this.targetTitleId).val());
		console.log('ArticleEditAreaOption.publishArticle()-ajax-send-data:title->'+$(this.targetTitleId).val()+';content->'+this.wordEditor.getValue());
		$.ajax({
			type: 'POST',
			url: 'addNewArticle',
			data: form,
			processData: false, // 告诉jquery要传输data对象
			contentType: false,   // 告诉jquery不需要增加请求头对于contentType的设置
			success: function (data) {
				console.log('BulletinBoardOption.updateData()-ajax-receive-data:'+data.toString());
				if(data.result == 'success'){
					TooltipOption.showPrimaryInf('发布成功');
					ArticleEditAreaOption.hide();
					ArticleEditAreaOption.reset();
					location.reload();
				} else{
					TooltipOption.showWarningInf('发布失败:'+data.inf);
				}
			},
			error : function(){
				TooltipOption.showWarningInf('服务器连接失败');
			}
		});
	},
	actionPublish : function(){
		$(this.btnPublishYesId).attr('disabled', 'true');
		$(this.btnPublishYesId).css('cursor', 'not-allowed');
		$(this.btnPublishYesId).css('background', 'gray');
		this.publishArticle();
	},
	actionCancel : function(){
		//this.reset();
		this.hide();
	},
	show : function(){
		$(this.btnPublishYesId).removeAttr('disabled');
		$(this.btnPublishYesId).css('cursor', 'pointer');
		$(this.btnPublishYesId).css('background', 'rgb(0, 150, 136)');
		$(this.targetId).show();
	},
	hide : function(){
		$(this.targetId).hide();
	},
	reset : function(){
		this.wordEditor.setValue('');
		$(this.targetTitleId).val('');
		ImgOption.reset();
	}
};
//公告栏控制模块-Y
var BulletinBoardOption = {
	targetId : '#bulletinBoardEditArea',
	bulletinBoardData : '',
	init : function(){
		this.loadData();
	},
	loadData : function(){ //获取论坛的公告信息
		$.ajax({
			url: "getIntro",
			type: 'GET',
			cache: false,
			dataType:'json',
			contentType: "application/x-www-form-urlencoded;charset=utf-8",
			data: {},
			success: function (data) {
				// console.log('BulletinBoardOption.loadData()-ajax-receive-data:'+data.toString());
				if(data.result == 'success'){
					$(BulletinBoardOption.targetId).val(data.data.content);
					BulletinBoardOption.bulletinBoardData = data.data.content;
				} else{
					TooltipOption.showPrimaryInf('获取公告信息失败');
				}
			},
			error : function(){
				TooltipOption.showWarningInf('服务器连接失败');
			}
		});
	},
	updateData : function(){ //更新论坛的公告信息
		this.refusedEdit();
		if(this.bulletinBoardData == $(this.targetId).val())//如果内容没有变更就不发送数据
			return;
		var temp = {content:$(this.targetId).val()};
		console.log('BulletinBoardOption.updateData()-ajax-send-data:'+temp.toString());
		$.ajax({
			url: "updateIntro",
			type: 'POST',
			cache: false,
			dataType:'json',
			contentType: "application/x-www-form-urlencoded;charset=utf-8",
			data: temp,
			success: function (data) {
				// console.log('BulletinBoardOption.updateData()-ajax-receive-data:'+data.toString());
				if(data.result == 'success'){
					$(BulletinBoardOption.targetId).val(temp.content);
					BulletinBoardOption.bulletinBoardData = temp.content;
				} else{
					TooltipOption.showPrimaryInf('更新公告信息失败');
					$(BulletinBoardOption.targetId).val(BulletinBoardOption.bulletinBoardData);
				}
			},
			error : function(){
				TooltipOption.showWarningInf('服务器连接失败');
				$(BulletinBoardOption.targetId).val(BulletinBoardOption.bulletinBoardData);
			}
		});
	},
	cancelUpdateData : function(){
		$(this.targetId).val(this.bulletinBoardData);
		this.refusedEdit();
	},
	allowEdit : function(){
		$(this.targetId).removeAttr('readonly');
		$(this.targetId).css('background', '#fff');
		$('#btn_saveBulletinBoard').show();
		$('#btn_cancellBulletinBoard').show();
	},
	refusedEdit : function(){
		$('#btn_saveBulletinBoard').hide();
		$('#btn_cancellBulletinBoard').hide();
		$(this.targetId).attr('readonly', 'true');
		$(this.targetId).css('background', '#dee0e1');
	}
}
//图片增减控制模块-Y
var ImgOption = {
	newImgIdArray : [],
	newImgNum : 0,
	readyToDeleteId : undefined,
	packageData : function(){
		var form = new FormData();
		form.append("NewImgNum", ''+this.newImgIdArray.length);
		for(var i=0;i<this.newImgIdArray.length;i++){
			var fileobj = $("#fileCellForImg"+this.newImgIdArray[i])[0].files[0];
			form.append("img"+i, fileobj);
		}
		return form;
	},
	previewImage : function(obj, file){
		var reader = new FileReader();
		reader.onload = function(evt){
			obj.src = evt.target.result;
		};
		reader.readAsDataURL(file.files[0]);
	},
	addNewImg : function(t){
		var str = "<input id='fileCellForImg"+(this.newImgNum+1)+"' type='file' style='display:none; ' accept='.jpg' onchange='ImgOption.addNewImg(this)' />";		
		str += "<img id='newImg"+this.newImgNum+"' onclick='ImgOption.deleteNewImg("+this.newImgNum+")' style='margin-right:5px; margin-bottom:5px; ' height='130' width='200' src='' alt='点击图片进行删除'>";
		$('#fileCellForImg'+this.newImgNum).before(str);
		this.previewImage(document.getElementById('newImg'+this.newImgNum), t);
		this.newImgIdArray[this.newImgIdArray.length]=this.newImgNum;
		this.newImgNum++;
	},
	selectNewImg : function(){
		// console.log('selectNewImg get msg, the next newImg id is '+this.newImgNum);
		$('#fileCellForImg'+this.newImgNum).trigger('click');
	},
	deleteNewImg : function(t){
		this.readyToDeleteId = t;
		TooltipOption.runIfOk('确定删除？', this.deleteAction);
		//var str = $('#newImg'+t).attr('src');
		//console.log(str);
	},
	deleteAction : function(){
		console.log('readyToDeleteId:'+ImgOption.readyToDeleteId);
		if(undefined == ImgOption.readyToDeleteId) return;
		var t = ImgOption.readyToDeleteId;
		ImgOption.readyToDeleteId = undefined;
		for(var i=0;i<ImgOption.newImgIdArray.length;i++){
			if(ImgOption.newImgIdArray[i] == t)
				ImgOption.newImgIdArray.splice(i, 1);
		}
		$('#fileCellForImg'+t).remove();
		$('#newImg'+t).remove();
	},
	reset : function(){
		for(var i=0;i<this.newImgIdArray.length;i++){
			$("#fileCellForImg"+this.newImgIdArray[i]).remove();
			$('#newImg'+this.newImgIdArray[i]).remove();
		}
		this.newImgIdArray = [];
	}
};
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
var ActionSelect = {
	logoutAction : function(){
		TooltipOption.runIfOk('确认登出吗？', ActionSelect.logout);
	},
	logout : function() {
		$.ajax({
			url: "../X_Login/logout",
			type: 'GET',
			cache: false,
			dataType:'json',
			contentType: "application/x-www-form-urlencoded;charset=utf-8",
			data: {},
			success: function (data) {
				//console.log('ArticleOption.loadData()-ajax-receive-data:'+data.toString());
				if(data.result == 'success'){
					location.reload();
					// ArticleOption.showArticle(data.data);
				} else{
					TooltipOption.showPrimaryInf(data.inf);
				}
			},
			error : function(){
				TooltipOption.showWarningInf('服务器连接失败');
			}
		});
	}
}


$(function(){
	init();
	//test();
	
});
function init(){
	//论坛公告初始化
	BulletinBoardOption.init();
	//初始文章编辑区
	ArticleEditAreaOption.init();
	WordEditor = ArticleEditAreaOption.wordEditor;
	ArticleEditAreaOption.hide();
	//初始化分页条
	var data = requestPagingData();

	PagingModule.CurrentIndexPage = 1; //默认页面是第一页
	PagingModule.MaxPagingNum = data.maxDisplayAmount; //一个页面最多展示5条数据
	PagingModule.TotalMaxPagingNum = Math.ceil(data.totalAmount / data.maxDisplayAmount); //数据分页后的页数， 这里用Math.ceil函数来向上取整
	PagingModule.TotalNumOfPageBtn = (5 > PagingModule.TotalMaxPagingNum ? PagingModule.TotalMaxPagingNum : 5); //被显示的分页按钮数量，不能大于数据分页后的页数
	//指定分页条下标变动后被调用的函数
	PagingModule.run = ArticleOption.loadData;
	//在指定的组件下创建分页条
	initCreateModuleBar('pagingOptionArea');
	initLoadPageModule();
	console.log(PagingModule);
}

/**
 * 获取分页的初始数据
 * 同步请求
 * @returns {*}
 */
function requestPagingData() {
	var result;
	$.ajax({
		url: "getPagingData",
		type: 'POST',
		cache: false,
		async: false, //设置同步
		dataType:'json',
		contentType: "application/x-www-form-urlencoded;charset=utf-8",
		data: {},
		success: function (data) {
			if(data.result == 'success'){
				result =  data.data;
			} else{
				TooltipOption.showPrimaryInf(data.inf);
			}
		},
		error : function(){
			TooltipOption.showWarningInf('服务器连接失败');
		}
	});
	return result;
}
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
	ArticleOption.showArticle(temp);
	
}
function test4(){
	alert('lll');
}
function test3(){
	TooltipOption.run = test4;
	TooltipOption.show();
}
/****************************** 分页条相关 *****************************************/
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
/******************************* 图片编辑区 ****************************************/




function test2(){
    var str = WordEditor.getValue();
	console.log(str);
	return;
    var form = new FormData();
    form.append("NewImgNum", ''+NewImgIdArray.length);
	for(var i=0;i<NewImgIdArray.length;i++){
		var fileobj = $("#fileCellForImg"+NewImgIdArray[i])[0].files[0];
		form.append("img"+i, fileobj);
	}
	$.ajax({
        type: 'POST',
        url: 'http://upload/',
        data: form,
        processData: false, // 告诉jquery要传输data对象
        contentType: false,   // 告诉jquery不需要增加请求头对于contentType的设置
        success: function (arg) {
              console.log(arg)
        }
    });
}
/*******************************  ****************************************/

//---------- 点赞功能
/**
 * 点赞和取消点赞的公用操作
 * 异步请求
 * @param t
 */
function markLike(t){
	var target = $('#cellLikeFlag'+t);
	$.ajax({
		url: "markLikeForArticle",
		type: 'POST',
		cache: false,
		//async: false, //设置同步
		dataType:'json',
		contentType: "application/x-www-form-urlencoded;charset=utf-8",
		data: {articleId : t},
		success: function (data) {
			if(data.result == 'success'){
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













