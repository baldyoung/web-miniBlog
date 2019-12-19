

$(function(){
    $.ajax({
        url: "getAbout",
        type: 'GET',
        cache: false,
        //async: false, //设置同步
        dataType: 'json',
        contentType: "application/x-www-form-urlencoded;charset=utf-8",
        data: {},
        success: function (data) {
            if (data.result == 'success') {
                var result = data.data;
                AboutModule.showAbout(result.content);
                if (result.isOwner == 'yes') {
                    AboutModule.openOrCloseEditOption('open');
                }
            } else {
                // TooltipOption.showPrimaryInf(data.inf);
            }
        },
        error: function () {
            // TooltipOption.showWarningInf('服务器连接失败');
        }
    });
});
var AboutModule = {
    aboutContent : '',
    aboutContentBackup : '',
    showAbout : function(t) {
        AboutModule.aboutContent = t;
        $('#aboutContent').text(t);
    },
    openOrCloseEditOption : function (t) {
        if (t == 'open') {
            $('#btnEdit').show();
        }
    },
    openOrCloseEditArea : function (t) {
        if (t == 'open') {
            $('#aboutContent').hide();
            $('#btnEdit').hide();
            $('#aboutEditArea').show();
            $('#btnEditSave').show();
            $('#btnEditCancel').show();
            $('#aboutEditArea').show();
        } else {
            $('#aboutContent').show();
            $('#btnEdit').show();
            $('#aboutEditArea').hide();
            $('#btnEditSave').hide();
            $('#btnEditCancel').hide();
            $('#aboutEditArea').hide();
        }
    },
    editActionStart : function() {
        AboutModule.aboutContentBackup = AboutModule.aboutContent;
        AboutModule.openOrCloseEditArea('open');
        $('#aboutEditArea').val(AboutModule.aboutContent);
    },
    editActionCancel : function() {
        AboutModule.openOrCloseEditArea('close');
    },
    editActionSave : function() {
        var newContent = $('#aboutEditArea').val();
        $.ajax({
            url: "updateAbout",
            type: 'GET',
            cache: false,
            async: false, //设置同步
            dataType: 'json',
            contentType: "application/x-www-form-urlencoded;charset=utf-8",
            data: {content:newContent},
            success: function (data) {
                if (data.result == 'success') {
                    TooltipOption.showPrimaryInf("修改成功");
                    AboutModule.showAbout(newContent)
                    AboutModule.openOrCloseEditArea('close');
                } else {
                    TooltipOption.showPrimaryInf(data.inf);
                }
            },
            error: function () {
                TooltipOption.showWarningInf('服务器连接失败');
            }
        });
    }
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
















