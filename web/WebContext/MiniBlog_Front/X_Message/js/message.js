

$(function(){
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
		str += "<span id='parselike"+t.id+"' class='info-img like' onclick='markLike(\""+t.id+"\")' ><i class='layui-icon layui-icon-praise'></i><span id='like"+t.id+"' >"+t.likeNumber+"</span></span>";
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

//点赞操作
function markLike(msgId){
	var target = $('#like'+msgId);
	//将要点赞的留言编号发送给后台，后台确认后响应前端
	//...
	if($('#parselike'+msgId).attr('class') != 'info-img like layblog-this')
		target.text(parseInt(target.text())+1);
	console.log('新的点赞数:'+target.text());
	
}
	








