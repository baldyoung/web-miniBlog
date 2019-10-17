
/*
一个用于分页模块中的下标组件


*/




//
var PagingModule = {
	TotalNumOfPaging : undefined, //被分页的数据的总数
	TotalMaxPagingNum : undefined, //分页后的最大页数
	TotalNumOfPageBtn : undefined, //分页模块中页面按钮的数量
	FirstIndexOfCell : undefined, //当前展示页的第一条数据的编号
	LastIndexOfCell : undefined, //当前展示页的最后一条数据的编号
	CurrentIndexPage : undefined, //当前展示页的序号
	MaxPagingNum : undefined, //展示页最大展示数据的数量
	run : undefined //页面下标变更后会调用这个函数
};



//初始化加载分页模块，向后台获取分页数据并在前端进行初始化操作
function initLoadPageModule(){
	//PagingModule.CurrentIndexPage = 1;
	//PagingModule.MaxPagingNum = 5;
	//PagingModule.TotalNumOfPageBtn = 5;
	//PagingModule.TotalMaxPagingNum = 10;
	loadTargetPage(PagingModule.CurrentIndexPage);
}
//在指定id的组件内创建一个分页条 --- 也可以直接直接在html中写好来，该调用应该在initLoadPageModule()调用之后
function initCreateModuleBar(t){
	var target = $('#'+t);
	var str = "<button id='lastPageBtn' style='background:#c3c9cf; cursor:not-allowed; margin-right:2px; ' class='layui-btn layui-btn-normal' onclick='loadLastPageX()' >上一页</button>";
		str += "<span id='pageNumberArea' >";
		for(var i=1;i<=PagingModule.TotalNumOfPageBtn;i++){
			str += "<button id='pageNumber"+i+"' class='layui-btn layui-btn-normal' style='margin-left:1px; margin-right:1px; "+(i==PagingModule.CurrentIndexPage?"background:#ffc361;":"")+" ' onclick='loadTargetPageX("+i+")' >"+i+"</button>";
		}
		str += "</span>";
		str += "<button id='nextPageBtn' class='layui-btn layui-btn-normal' style='margin-left:2px; ' onclick='loadNextPageX()' >下一页</button>";
	target.html(str);
}
//获取并加载上一页数据
function loadLastPage(){
	if(PagingModule.CurrentIndexPage > 1){
		PagingModule.CurrentIndexPage --;
		if(PagingModule.CurrentIndexPage < $('#pageNumber1').text()){
			var i=1;
			for(i=1;i<=PagingModule.TotalNumOfPageBtn;i++){
				var temp = $('#pageNumber'+i);
				temp.text(parseInt(temp.text())-1);
			}
		}
		loadTargetPage(PagingModule.CurrentIndexPage);
	}			
}
//获取并加载下一页数据
function loadNextPage(){
	if(PagingModule.CurrentIndexPage < PagingModule.TotalMaxPagingNum){
		PagingModule.CurrentIndexPage ++;
		if(PagingModule.CurrentIndexPage > $('#pageNumber'+PagingModule.TotalNumOfPageBtn).text()){
			var i=1;
			for(i=1;i<=PagingModule.TotalNumOfPageBtn;i++){
				var temp = $('#pageNumber'+i);
				temp.text(parseInt(temp.text())+1);
			}
		}
		loadTargetPage(PagingModule.CurrentIndexPage);
	}	
}
//获取并加载指定序号页的数据
function loadTargetPage(t){
	PagingModule.CurrentIndexPage = t;
	var temp = {
		CurrentIndexPage : PagingModule.CurrentIndexPage,
		MaxPagingNum : PagingModule.MaxPagingNum
	};
	//将请求页的序号与数量发送给后台，后台进行处理及响应，后台返回最新数据包括（被分页的数据的总条数、...）
	//...（这里进行ajax请求调用）
	var f = (PagingModule.CurrentIndexPage-1)*PagingModule.MaxPagingNum;
	if(undefined!=PagingModule.run)
		PagingModule.run(f, PagingModule.MaxPagingNum);
	else 
		console.log('PagingModule -> warning : PagingModule.run is undefined.');
	//获取成功后由前端进行处理，并改变指定页所对应按钮的状态
	//下面的调用应该在成功获取到分页数据后进行调用，否则不建议执行
	
	changePageBtnState(temp.CurrentIndexPage);	
	if(PagingModule.CurrentIndexPage == 1){
		$('#lastPageBtn').css('cursor', 'not-allowed');
		$('#lastPageBtn').css('background', '#c3c9cf');
	}else if(PagingModule.CurrentIndexPage == PagingModule.TotalMaxPagingNum){
		$('#nextPageBtn').css('cursor', 'not-allowed');
		$('#nextPageBtn').css('background', '#c3c9cf');
	}else{
		$('#nextPageBtn').css('cursor', 'pointer');
		$('#nextPageBtn').css('background', '#1E9FFF');
		$('#lastPageBtn').css('cursor', 'pointer');
		$('#lastPageBtn').css('background', '#1E9FFF');
	}
	
	console.log('请求的新页的数据:'+ temp);
}
//改变指定页面对应按钮的状态，t是页面的序号
function changePageBtnState(t){
	var i=1, ac=true;
	for(i=1;i<=PagingModule.TotalNumOfPageBtn;i++){
		var temp = $('#pageNumber'+i);
		if(temp.text() == t){
			temp.css('background', '#ffc361');
			ac = false;
		}else{
			temp.css('background', '#1E9FFF');
		}
	}
			
}




// 初始化调用
//initLoadPageModule();


















