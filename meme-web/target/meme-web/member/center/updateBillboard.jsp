<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<%@include file="/common/include/include_layui.jsp"%>

<link rel="stylesheet" type="text/css" href="<%=basePath%>static/js/webuploader/webuploader.css">
<script type="text/javascript" src="<%=basePath%>static/js/webuploader/webuploader.js"></script>

<link rel="stylesheet" type="text/css" href="<%=basePath%>static/css/upload.css">
</head>
<body>
<div data-role="page">
	<div data-role="header">
		<a href="javascript: history.go(-1)" class="ui-btn ui-corner-all ui-shadow ui-icon-home ui-btn-icon-left" data-ajax="false">返回</a>
		<h1>更新栏目公告</h1>
	</div>

	<div data-role="main" class="ui-content">
	    <form enctype="multipart/form-data" id="form">
			<input name="billboardid" type="hidden" value="${billboardid}">
			<label for="sign">公告内容：</label>
			<textarea rows="10" name="content" id="content"></textarea>
			<div id="linkCon">
			<label for="extlink">视频或有声链接地址：</label>
			<input type="text" name="extlink" id="extlink" value="">
			</div>
			<label for="checkUploadBtn">上传</label>
          	<input type="checkbox" id="checkUploadBtn" name="type" value="1">
          	<script type="text/javascript">
          	$('#checkUploadBtn').click(function(){
          		var check=$(this).is(':checked');
          		if(check){
          			$('#linkCon').empty();
          			/* var node='';
          			node+='<div class="ui-input-text ui-body-inherit ui-corner-all ui-shadow-inset ui-input-has-clear">';
          			node+='<input type="file" data-clear-btn="true" name="file" id="file" accept="video/*">';
          			node+='<a href="#" tabindex="-1" aria-hidden="true" class="ui-input-clear ui-btn ui-icon-delete ui-btn-icon-notext ui-corner-all ui-input-clear-hidden" title="Clear text">Clear text</a>';
          			node+='</div>';
          			$('#container').append(node); */
          			$('#uploader').show();
          			jQuery(function() {
          				var $ = jQuery,

          			    $wrap = $('#uploader'),

          			    // 图片容器
          			    $queue = $('<ul class="filelist"></ul>').appendTo( $wrap.find('.queueList') ),

          			    // 状态栏，包括进度和控制按钮
          			    $statusBar = $wrap.find('.statusBar'),

          			    // 文件总体选择信息。
          			    $info = $statusBar.find('.info'),

          			    // 上传按钮
          			    $upload = $wrap.find('.uploadBtn'),

          			    // 没选择文件之前的内容。
          			    $placeHolder = $wrap.find('.placeholder'),

          			    // 总体进度条
          			    $progress = $statusBar.find('.progress').hide(),

          			    // 添加的文件数量
          			    fileCount = 0,

          			    // 添加的文件总大小
          			    fileSize = 0,

          			    // 优化retina, 在retina下这个值是2
          			    ratio = window.devicePixelRatio || 1,

          			    // 缩略图大小
          			    thumbnailWidth = 110 * ratio,
          			    thumbnailHeight = 110 * ratio,

          			    // 可能有pedding, ready, uploading, confirm, done.
          			    state = 'pedding',

          			    // 所有文件的进度信息，key为file id
          			    percentages = {},

          			    supportTransition = (function(){
          			        var s = document.createElement('p').style,
          			            r = 'transition' in s ||
          			                  'WebkitTransition' in s ||
          			                  'MozTransition' in s ||
          			                  'msTransition' in s ||
          			                  'OTransition' in s;
          			        s = null;
          			        return r;
          			    })(),

          			    // WebUploader实例
          			    uploader;

          			if ( !WebUploader.Uploader.support() ) {
          			    alert( 'Web Uploader 不支持您的浏览器！如果你使用的是IE浏览器，请尝试升级 flash 播放器');
          			    throw new Error( 'WebUploader does not support the browser you are using.' );
          			}

          			uploader = WebUploader.create({
          			    pick: {
          			        id: '#filePicker',
          			        label: '点击选择文件'
          			    },
          			    dnd: '#uploader .queueList',
          			    paste: document.body,

          			    accept: {
          			        title: '音视频',
          			        extensions: '3gp,mkv,mp4,rmvb,avi,wmv,mkv,mpg,vob,mov,flv,swf,mp3,wma,ape,flac',
          			      	mimeTypes: 'audio/*,video/*'
          			    },
          			    formData: {
          			    	billboardid: '${billboardid}',
          			    },
          			    swf: GLOBAL_BASE_PATH + 'static/js/webuploader/Uploader.swf',

          			    disableGlobalDnd: true,
          			    duplicate: true,
          			    chunked: true,
          			    server: '<%=basePath%>member/center/doUpdateBillboardItem',
          			    fileNumLimit: 15,
          			    fileSizeLimit: 200000 * 1024 * 1024,    // 200 M
          			    fileSingleSizeLimit: 2000 * 1024 * 1024    // 50 M
          			});

          			// 添加“添加文件”的按钮，
          			uploader.addButton({
          			    id: '#filePicker2',
          			    label: '继续添加'
          			});

          			// 当有文件添加进来时执行，负责view的创建
          			function addFile( file ) {
          			    var $li = $( '<li id="' + file.id + '">' +
          			            '<p class="title">' + file.name + '</p>' +
          			            '<p class="imgWrap"></p>'+
          			            '<p class="progress"><span></span></p>' +
          			            '</li>' ),

          			        $btns = $('<div class="file-panel">' +
          			            '<span class="cancel">删除</span>' +
          			            '<span class="rotateRight">向右旋转</span>' +
          			            '<span class="rotateLeft">向左旋转</span></div>').appendTo( $li ),
          			        $prgress = $li.find('p.progress span'),
          			        $wrap = $li.find( 'p.imgWrap' ),
          			        $info = $('<p class="error"></p>'),

          			        showError = function( code ) {
          			            switch( code ) {
          			                case 'exceed_size':
          			                    text = '文件大小超出';
          			                    break;

          			                case 'interrupt':
          			                    text = '上传暂停';
          			                    break;

          			                default:
          			                    text = '上传失败，请重试';
          			                    break;
          			            }

          			            $info.text( text ).appendTo( $li );
          			        };

          			    if ( file.getStatus() === 'invalid' ) {
          			        showError( file.statusText );
          			    } else {
          			        $wrap.text( '预览中' );
          			        uploader.makeThumb( file, function( error, src ) {
          			            if ( error ) {
          			                $wrap.text( '不能预览' );
          			                return;
          			            }

          			            var img = $('<img src="'+src+'">');
          			            $wrap.empty().append( img );
          			        }, thumbnailWidth, thumbnailHeight );

          			        percentages[ file.id ] = [ file.size, 0 ];
          			        file.rotation = 0;
          			    }

          			    file.on('statuschange', function( cur, prev ) {
          			        if ( prev === 'progress' ) {
          			            $prgress.hide().width(0);
          			        } else if ( prev === 'queued' ) {
          			            $li.off( 'mouseenter mouseleave' );
          			            $btns.remove();
          			        }

          			        // 成功
          			        if ( cur === 'error' || cur === 'invalid' ) {
          			            console.log( file.statusText );
          			            showError( file.statusText );
          			            percentages[ file.id ][ 1 ] = 1;
          			        } else if ( cur === 'interrupt' ) {
          			            showError( 'interrupt' );
          			        } else if ( cur === 'queued' ) {
          			            percentages[ file.id ][ 1 ] = 0;
          			        } else if ( cur === 'progress' ) {
          			            $info.remove();
          			            $prgress.css('display', 'block');
          			        } else if ( cur === 'complete' ) {
          			            $li.append( '<span class="success"></span>' );
          			        }

          			        $li.removeClass( 'state-' + prev ).addClass( 'state-' + cur );
          			    });

          			    $li.on( 'mouseenter', function() {
          			        $btns.stop().animate({height: 30});
          			    });

          			    $li.on( 'mouseleave', function() {
          			        $btns.stop().animate({height: 0});
          			    });

          			    $btns.on( 'click', 'span', function() {
          			        var index = $(this).index(),
          			            deg;

          			        switch ( index ) {
          			            case 0:
          			                uploader.removeFile( file );
          			                return;

          			            case 1:
          			                file.rotation += 90;
          			                break;

          			            case 2:
          			                file.rotation -= 90;
          			                break;
          			        }

          			        if ( supportTransition ) {
          			            deg = 'rotate(' + file.rotation + 'deg)';
          			            $wrap.css({
          			                '-webkit-transform': deg,
          			                '-mos-transform': deg,
          			                '-o-transform': deg,
          			                'transform': deg
          			            });
          			        } else {
          			            $wrap.css( 'filter', 'progid:DXImageTransform.Microsoft.BasicImage(rotation='+ (~~((file.rotation/90)%4 + 4)%4) +')');
          			        }


          			    });

          			    $li.appendTo( $queue );
          			}

          			// 负责view的销毁
          			function removeFile( file ) {
          			    var $li = $('#'+file.id);

          			    delete percentages[ file.id ];
          			    updateTotalProgress();
          			    $li.off().find('.file-panel').off().end().remove();
          			}

          			function updateTotalProgress() {
          			    var loaded = 0,
          			        total = 0,
          			        spans = $progress.children(),
          			        percent;

          			    $.each( percentages, function( k, v ) {
          			        total += v[ 0 ];
          			        loaded += v[ 0 ] * v[ 1 ];
          			    } );

          			    percent = total ? loaded / total : 0;

          			    spans.eq( 0 ).text( Math.round( percent * 100 ) + '%' );
          			    spans.eq( 1 ).css( 'width', Math.round( percent * 100 ) + '%' );
          			    updateStatus();
          			}

          			function updateStatus() {
          			    var text = '', stats;

          			    if ( state === 'ready' ) {
          			        text = '选中' + fileCount + '个文件，共' +
          			                WebUploader.formatSize( fileSize ) + '。';
          			    } else if ( state === 'confirm' ) {
          			        stats = uploader.getStats();
          			        if ( stats.uploadFailNum ) {
          			            text = '已成功上传' + stats.successNum+ '个文件，'+
          			                stats.uploadFailNum + '个文件上传失败，<a class="retry" href="#">重新上传</a>失败文件或<a class="ignore" href="#">忽略</a>'
          			        }

          			    } else {
          			        stats = uploader.getStats();
          			        text = '共' + fileCount + '张（' +
          			                WebUploader.formatSize( fileSize )  +
          			                '），已上传' + stats.successNum + '个';

          			        if ( stats.uploadFailNum ) {
          			            text += '，失败' + stats.uploadFailNum + '个';
          			        }
          			    }

          			    $info.html( text );
          			}

          			function setState( val ) {
          			    var file, stats;

          			    if ( val === state ) {
          			        return;
          			    }

          			    $upload.removeClass( 'state-' + state );
          			    $upload.addClass( 'state-' + val );
          			    state = val;

          			    switch ( state ) {
          			        case 'pedding':
          			            $placeHolder.removeClass( 'element-invisible' );
          			            $queue.parent().removeClass('filled');
          			            $queue.hide();
          			            $statusBar.addClass( 'element-invisible' );
          			            uploader.refresh();
          			            break;

          			        case 'ready':
          			            $placeHolder.addClass( 'element-invisible' );
          			            $( '#filePicker2' ).removeClass( 'element-invisible');
          			            $queue.parent().addClass('filled');
          			            $queue.show();
          			            $statusBar.removeClass('element-invisible');
          			            uploader.refresh();
          			            break;

          			        case 'uploading':
          			            $( '#filePicker2' ).addClass( 'element-invisible' );
          			            $progress.show();
          			            $upload.text( '暂停上传' );
          			            break;

          			        case 'paused':
          			            $progress.show();
          			            $upload.text( '继续上传' );
          			            break;

          			        case 'confirm':
          			            $progress.hide();
          			            $upload.text( '开始上传' ).addClass( 'disabled' );

          			            stats = uploader.getStats();
          			            if ( stats.successNum && !stats.uploadFailNum ) {
          			                setState( 'finish' );
          			                return;
          			            }
          			            break;
          			        case 'finish':
          			            stats = uploader.getStats();
          			            if ( stats.successNum ) {
          			                
          			            } else {
          			                // 没有成功的图片，重设
          			                state = 'done';
          			                location.reload();
          			            }
          			            break;
          			    }

          			    updateStatus();
          			}

          			uploader.onUploadProgress = function( file, percentage ) {
          			    var $li = $('#'+file.id),
          			        $percent = $li.find('.progress span');

          			    $percent.css( 'width', percentage * 100 + '%' );
          			    percentages[ file.id ][ 1 ] = percentage;
          			    updateTotalProgress();
          			};

          			uploader.onFileQueued = function( file ) {
          			    fileCount++;
          			    fileSize += file.size;

          			    if ( fileCount === 1 ) {
          			        $placeHolder.addClass( 'element-invisible' );
          			        $statusBar.show();
          			    }

          			    addFile( file );
          			    setState( 'ready' );
          			    updateTotalProgress();
          			};

          			uploader.onFileDequeued = function( file ) {
          			    fileCount--;
          			    fileSize -= file.size;

          			    if ( !fileCount ) {
          			        setState( 'pedding' );
          			    }

          			    removeFile( file );
          			    updateTotalProgress();

          			};

          			uploader.on( 'all', function( type ) {
          			    var stats;
          			    switch( type ) {
          			        case 'uploadFinished':
          			            setState( 'confirm' );
          			            break;

          			        case 'startUpload':
          			            setState( 'uploading' );
          			            break;

          			        case 'stopUpload':
          			            setState( 'paused' );
          			            break;

          			    }
          			});

          			uploader.onError = function( code ) {
          			    alert( 'Eroor: ' + code );
          			};

          			$upload.on('click', function() {
          			    if ( $(this).hasClass( 'disabled' ) ) {
          			        return false;
          			    }

          			    if ( state === 'ready' ) {
          			        uploader.upload();
          			    } else if ( state === 'paused' ) {
          			        uploader.upload();
          			    } else if ( state === 'uploading' ) {
          			        uploader.stop();
          			    }
          			});

          			$info.on( 'click', '.retry', function() {
          			    uploader.retry();
          			} );

          			$info.on( 'click', '.ignore', function() {
          			    alert( 'todo' );
          			} );

          			$upload.addClass( 'state-' + state );
          			updateTotalProgress();
          			});
          		}else{
          			$('#linkCon').empty();
          			//$('#container').empty();
          			$('#uploader').hide();
          			var node='<div id="linkCon">';
          			node+='<label for="extlink">视频或有声链接地址：</label>';
          			node+='<div class="ui-input-text ui-body-inherit ui-corner-all ui-shadow-inset"><input type="text" name="extlink" id="extlink" value=""></div>';
          			node+='</div>';
          			$(node).appendTo('#linkCon');
          		}
          	});
          	</script>
			<div id="container">
			
				<div id="uploader" data-role="none" style="display:none;">
				    <div class="queueList" data-role="none">
				        <div id="dndArea" class="placeholder" data-role="none">
				            <div id="filePicker" class="webuploader-container">
					            <div class="webuploader-pick" data-role="none">点击选择视频</div>
					            <div data-role="none" style="position: absolute; top: 0px; left: 66.6px; width: 168px; height: 44px; overflow: hidden; bottom: auto; right: auto;">
					            	<input data-role="none" type="file" name="file" class="webuploader-element-invisible" multiple="multiple" accept="video/*">
					            	<label data-role="none" style="opacity: 0; width: 100%; height: 100%; display: block; cursor: pointer; background: rgb(255, 255, 255);"></label>
					            </div>
				            </div>
				            <p data-role="none">单次可选10个视频</p>
				        </div>
				    	<ul class="filelist" data-role="none"></ul>
				    </div>
				    <div class="statusBar" style="display:none;" data-role="none">
				        <div class="progress" style="display: none;" data-role="none">
				            <span class="text" data-role="none">0%</span>
				            <span class="percentage" style="width: 0%;" data-role="none"></span>
				        </div><div class="info" data-role="none">共0张（0B），已上传0个</div>
				        <div class="btns" data-role="none">
				            <div id="filePicker2" class="webuploader-container" data-role="none">
					            <div class="webuploader-pick" data-role="none">继续添加</div>
					            <div style="position: absolute; top: 0px; left: 0px; width: 1px; height: 1px; overflow: hidden;" data-role="none">
					            	<input data-role="none" type="file" name="file" class="webuploader-element-invisible" multiple="multiple" accept="video/*">
					            	<label data-role="none" style="opacity: 0; width: 100%; height: 100%; display: block; cursor: pointer; background: rgb(255, 255, 255);"></label>
					            </div>
				            </div>
				            <div data-role="none" class="uploadBtn state-pedding">开始上传</div>
				        </div>
				    </div>
				</div>
			
			</div>
			
			<button type="button" id="updateBtn" class="">确认提交</button>
	    </form>
	</div>
</div>
</body>
</html>
<script type="text/javascript">
$(document).ready(function(){
	$('#updateBtn').on('tap',function(){
		var content=$('#content').val();
		/*if(!content){
			showDefaultMsg('公告内容不能为空');
			return ;
		}*/
		$.ajax({
            url: '<%=basePath%>member/center/doUpdateBillboard',
            type: 'POST',
			cache: false,
			contentType: false,
			processData: false,
			beforeSend: function(){
				showloading('true');
			},
            data: new FormData($('#form')[0]),
            success: function(res){
				var data=JSON.parse(res);
				if(data.state==1) {
					showDefaultMsg(data.message);
					window.location.href='http://mmgmmj.com/zybj';
				}else {
					showDefaultMsg(data.message);
				}
            },
            error: function(e){
                showDefaultMsg('操作异常，请稍后重试或联系管理员');
            }
        });
   });
});

</script>