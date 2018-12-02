/**
 * Created by CHENRC2 on 2017-5-4.
 */
String.prototype.format = function(args) {
    var result = this;
    if (arguments.length > 0) {
        if (arguments.length == 1 && typeof (args) == "object") {
            for (var key in args) {
                if(args[key]!=undefined){
                    var reg = new RegExp("({" + key + "})", "g");
                    result = result.replace(reg, args[key]);
                }
            }
        }
        else {
            for (var i = 0; i < arguments.length; i++) {
                if (arguments[i] != undefined) {
                    var reg = new RegExp("({[" + i + "]})", "g");
                    result = result.replace(reg, arguments[i]);
                }
            }
        }
    }
    return result;
};
var GetQueryString=function(name) {
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if(r!=null)return unescape(r[2]); return null;
};
/**
 * 获取图片大小
 * @param obj
 * @returns {number} 单位KB
 */
var getPhotoSize=function(obj){
    var fileSize = 0;
    var isIE = /msie/i.test(navigator.userAgent) && !window.opera;
    if (isIE && !obj.files) {
        var filePath = obj.value;
        var fileSystem = new ActiveXObject("Scripting.FileSystemObject");
        var file = fileSystem.GetFile (filePath);
        fileSize = file.Size;
    }else {
        fileSize = obj.files[0].size;
    }
    fileSize=Math.round(fileSize/1024*100)/100; //单位为KB
    return fileSize;
};
/**
 * 关闭页面
 */
var closePage=function(){
    parent.layer.close(index);
}
/**
 * 读取cookie
 * @param name
 * @returns {null}
 */
var getCookie=function(name) {
    var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
    if(arr=document.cookie.match(reg))
        return unescape(arr[2]);
    else
        return null;
};
/**
 * jQueryMobile 弹出提示框
 * @param text：提示内容
 * @param callback:点击确定要执行的函数
 */
function confirmJQM(text, callback) {
    var popupDialogId = 'popupDialogC';
    $('<div data-role="popup" id="' + popupDialogId + '" data-confirmed="no" data-position-to="window" data-transition="pop" data-theme="a" data-dismissible="false" style="max-width:500px;">'+
        '<div role="main" class="ui-content" style="text-align: center;padding: 10px;">'+
        '<h3 class="ui-title" style="padding: 10px 20px;">' + text + '</h3>'+
        '<p></p>'+
        '<a data-role="button" data-theme="a" class="optionCancel" style="width: 70px;height:20px;line-height:20px;" data-mini="true" data-inline="true" >拒绝</a>'+
        '<a data-role="button" data-theme="a" class="optionConfirm" style="width: 70px;height:20px;line-height:20px;" data-transition="flow" data-inline="true" >同意</a>'+
        '</div>'+
        '</div>').appendTo($.mobile.pageContainer);
    var popupDialogObj = $('#' + popupDialogId);
    popupDialogObj.trigger('create');  //动态加载时 需要重新刷新下 也就是给popup赋上jqm对应的css

    //初始化popup
    popupDialogObj.popup({
        //关闭时 绑定的事件
        afterclose: function (event, ui) {
            popupDialogObj.find(".optionConfirm").first().off('click'); //关闭时 需要清除确定按钮上 绑定的事件
            $(event.target).remove();//删除 创建的 popup
        },

        //显示时 绑定的事件
        afteropen: function (event, ui) {
            popupDialogObj.find(".optionConfirm").first().on('click', function () {
                popupDialogObj.attr('data-confirmed', 'no');
                $('#popupDialogC').popup('close');
                if(callback && callback instanceof Function ){
                    callback(1);
                }
            });
            popupDialogObj.find(".optionCancel").first().on('click', function () {
                popupDialogObj.attr('data-confirmed', 'no');
                $('#popupDialogC').popup('close');
                if(callback && callback instanceof Function ){
                    callback(0);
                }
            });
        }
    });
    //打开
    popupDialogObj.popup('open');
}

/**
 * 提示信息
 * @param msg
 */
var showDefaultMsg=function(msg){
    $.mobile.loading( "show", {
        text: msg,
        textVisible: true,
        theme: 'b',
        textonly: true,
        html: ''
    });
    setTimeout(function(){
        $.mobile.loading( "hide");
    },2000);
};

var isOpen = false;
var timer = null;
var showMsg=function(msg,time){
    /*if(!time){time=3}*/
    if(time == 0){
        $("#tsDiv").popup('close');
        window.clearTimeout(timer);
        isOpen = false;
    }
    if(msg && time != 0){
        if(!time){
            time = 3;
        }

        if(isOpen == false){
            $("#tsMode").html(msg);
            $("#tsDiv").popup('open');
            isOpen = true;
        }
        $("#time").html(time);
        time--;
        timer = window.setTimeout(function(){showMsg(msg,time);},1000);
    }
}

function showloading(registerloading){
    if(registerloading=="true"){
        $.mobile.loading('show', {
            text: '操作中...',
            textVisible: true,
            theme: 'b',
            textonly: false,
            html: ""
        });
    }else{
        $.mobile.loading( "hide");
    }
}

function createSelect(data,optname,optvalue,defaultval){
	var options="";
	if(defaultval){
		options="<option value=''>"+defaultval+"</option>";
	}
	for(var i=0;i<data.length;i++){
		options+="<option value='"+data[i][optvalue]+"'>"+data[i][optname]+"</option>";
	}
	return options;
}