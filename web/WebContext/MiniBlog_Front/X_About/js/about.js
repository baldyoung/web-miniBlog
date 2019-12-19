

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

    }
}


function editActionCancel() {

}
function editActionSave() {

}
















