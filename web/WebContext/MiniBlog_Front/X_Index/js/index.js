

$(function(){
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
		likeNumber:'33'
	},{
		id:'102',
		title:'测试表数据二',
		publishTime:'2019-10-5 16:33',
		content:'这只是一组测试数据\n这只是一组测试数据这只是一组测试数据。',
		images:['../A_CommonRes/res/img/item.png', '../A_CommonRes/res/img/item.png'],
		commentNumber:'66',
		likeNumber:'3'
	}];
	showDisplayCell(temp);
	
}
/*DisplayCell所对应的对象应该有如下属性
{
	id:被展示单元的编号,
	title:被展示单元的标题,
	publishTime:被发布的时间,
	content:详细内容,
	images:[图片1,图片2, ...],
	commentNumber:已有的评论数量,
	likeNumber:已有的点赞数量
}


*/
//将给定的展示对象集合追加到展示区域
function showDisplayCell(t){
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
		str += "<h3><a href='details'>"+t.title+"</a></h3>";
		str += "<h5>发布于："+t.publishTime+"<span></span></h5>";
		str += "<p>"+t.content+"</p>";
		for(i=0;i<imgs.length;i++)
			str += "<img style='margin-right:5px; margin-bottom:5px; ' src='"+imgs[i]+"' alt=''>";
		str += "</div>";
		str += "<div class='comment count'>";
		str += "<a href='comment'>评论"+t.commentNumber+"</a>";
		str += "<a style='cursor:pointer; ' class='like'>点赞"+t.likeNumber+"</a>";
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
function loadNextPage(){
	clearDisplayArea();
	
}












