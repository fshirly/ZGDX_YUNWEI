/**
 * Created by zhaoyp on 2014/8/21.
 */
(function(){
    var _default_config ={
        url: f.contextPath + "/commonFileUpload/uploadFile",
        downloadLinkUrl: f.contextPath + "/commonFileUpload/CosDownload?fileDir=",
        title : "下载文件",
        fileUploadButtonName : "上传",
        defaultImgSrc :"${pageContext.request.contextPath}/fui/themes/default/icons/dtree/page.png",
        imgWidth : 16,
        imgHeight : 16,
        imgPreviewWidth :16,
        imgPreviewHeight :16,
        upLoadBtnId : "uploadBtn",
        downloadFile :"downloadFile",
        delButtonId: "delButtonBtn",
        delButtonName: "删除"
    };

    f.fn.f_fileupload = function(options,param){
        getFileServerPath();
        
        if(options.inputFileId==undefined){
            options.inputFileId = 'iconFileName';
        }
        if(typeof options === "object"){
            _create.call(this,options);
        }
        else if(typeof options === "string"){
            var method = _method[options];
            if (method) {
                method.call(this,param);
            }
        }
    };

    var _method ={
        getValue: function() {

        }
    };

    var _create = function(options){
        var config = f.extend(true,{},_default_config,options || {}),
            self = this,
            fileElementId = f(self).attr("id"),
            hiddenInputName = f(self).attr("name"),
            imgSrc = file_Server_Path + f(self).attr("value"),
            hiddenInput = f("<input id='"+config.inputFileId+"'  name='" + hiddenInputName + "' type='hidden' value='"+ f(self).attr("value") +"'/>"),
            hiddenInputId = hiddenInput.attr("id"),
            span = f("<span id='span_ImgPreview'  style='width:"+ config.imgWidth +"px;height:" +config.imgHeight +"px;'></span>"),
            previewInput = f("<img id='imgPreview' src/>"),
            downloadButton = f("<a id='"+ config.downloadFile +"' title='"+ config.title +"' style='width: 160px;overflow: hidden;display: block;height: 15px;line-height:15px;float: right;margin-right: 10%;'></a>"),
            uploadButton = f("<input id='"+ config.upLoadBtnId +"' type='button' value='" + config.fileUploadButtonName + "' disabled='disabled' style='width:45px;'/>"),
            delButton = f("<input type='button' disabled='disabled' value='" + config.delButtonName + "' id='" + config.delButtonId + "' style='width:45px;'>");
            //progressDiv = f("<div id='progressDiv' style='display:none'><div style='margin-top:35px;margin-left:70px'>正在上传...请耐心等待<div id='p' class='easyui-progressbar' data-options='value:0' style='width:300px;'></div></div></div>");
            progressBar = f("<div id='p2' style='display:none;position: relative;z-index: 9999;top:-65px;left:295px;width:350px;height:100px;background-color:#EBEBEB;'><div style='margin-left:25px;'>正在上传...请耐心等待</div><div id='p1' class='easyui-progressbar' data-options='value:0' style='width:300px;margin-left:25px;' ></div></div>");
        f(self).attr("name",hiddenInputName+"_file").css("max-width",220);
        if(config.whetherPreview === true){
            span.insertBefore(self);
            span.append(previewInput);
            hiddenInput.insertBefore(span);
        }
        else {
            hiddenInput.insertBefore(self);
        }
        if(config.bigFile == true) {
        	progressBar.insertAfter(self);
        }
        downloadButton.insertAfter(self);
        if(f(self).nextAll("#"+config.upLoadBtnId).length == 0){
            uploadButton.insertAfter(downloadButton);
        }
        if (config.del === true) {
            delButton.insertAfter(uploadButton);
            var fileFref;
            if(f(self).attr("value")){
                fileFref = f(self).attr("value");
            }else{
                fileFref = f("#"+fileElementId+"_path").val();
            }
            if(f("#"+config.inputFileId).val()!=""||fileFref!=''){
            	f("#" + config.delButtonId).removeAttr('disabled');
            }
        };
        if(config.showFile == false){
            downloadButton.css("display","none");
        }
        if(f(self).attr("value")!="" && f(self).attr("value") != undefined){
            f("#imgPreview").attr("src",imgSrc);
            if(config.viewImgSrcId && config.viewImgSrcId!=""){
                f("#"+config.viewImgSrcId).attr("src",imgSrc);
            }
        }
        if(f("#"+config.inputFileId).attr("value") == "undefined"){
            f("#"+config.inputFileId).attr("value","");
        }
        
        self.change(function(){
            var that = this;
            verifyFileFormat(that,config);
        });

        uploadButton.click(function(){
        	var fileName = f("#"+config.inputFileId).val();
        	if(fileName!=""){
        		delFile(fileName);
        	}
            uploadFile(fileElementId,config);
            f("#"+config.upLoadBtnId).attr("disabled","disabled");
        });
        delButton.click(function () {
            //删除文件
        	var fileName = f("#"+config.inputFileId).val();
        	if(fileName!=""){
        		delFile(f("#"+config.inputFileId).val(),config.downloadFile);
        	}
        	downloadButton.html("");
            f("#"+fileElementId).val("");
            f("#"+config.inputFileId).val("");
            f("#imgPreview").attr("src","");
            f("#" + config.delButtonId).attr("disabled", "disabled");
            f("#"+config.upLoadBtnId).attr("disabled","disabled");
        });
        
        if(config.filePreview === true){
            var fileFref;
            if(f(self).attr("value")){
                fileFref = f(self).attr("value");
            }else{
                fileFref = f("#"+fileElementId+"_path").val();
            }
            initDownloadLinkTag(config.downloadFile,fileFref,config);
        }
    };

    function verifyFileFormat(getFile,config){
        var verifyImg = new Image(),
            opt = getFile.value.lastIndexOf(".")+ 1,
            disabledBool = true,
            showErrorMsg = true,
            errorMsg = "",
            UploadFileNameValue = getFile.value.split("\\"),
            UploadFileNameLen = UploadFileNameValue.length - 1,
            UploadFileName = UploadFileNameValue[UploadFileNameLen],
            dotCount = UploadFileName.split(".").length - 1 ,
            size;

        if(config.filesize){

            var fileSize = 0;

            if (navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion .split(";")[1].replace(/[ ]/g,"")=="MSIE8.0") {
                var objFSO = new ActiveXObject("Scripting.FileSystemObject");
                var filePath = getFile.value;

                var objFile = objFSO.getFile(filePath);
                fileSize = objFile.size;
                size= fileSize / 1024;

            }else{
                fileSize = getFile.files[0].size;
                    size = fileSize/1024;
            }

        }


        if(config.fileFormat) {
            var fileType = getFile.value.substring(opt, getFile.value.length),
                fileTypeArr = eval('(' + config.fileFormat + ')');
            errorMsg = "上传的文件格式仅支持：" + fileTypeArr.toString();
            for (var i = 0; i < fileTypeArr.length; i++) {
                if (fileType == fileTypeArr[i]) {
                    showErrorMsg = false;
                    disabledBool = false;
                    break;
                }
            }
        }else if(config.forbidFormat){
            var fileTypeName = getFile.value.substring(opt, getFile.value.length),
                fileTypeNameArr = eval('(' + config.forbidFormat + ')');
            errorMsg = "上传的文件不能包含以下格式：" + fileTypeNameArr.toString();
            for (var j = 0; j < fileTypeNameArr.length; j++) {
                if (fileTypeName == fileTypeNameArr[j]) {
                    showErrorMsg = true;
                    disabledBool = true;
                    break;
                }else{
                	showErrorMsg = false;
                    disabledBool = false;	
                }
            }
        }else{
            showErrorMsg = false;
            disabledBool = false;
        }
        if(disabledBool===true){
            f("#"+config.upLoadBtnId).attr('disabled','disabled');
        }else{
            f("#"+config.upLoadBtnId).removeAttr('disabled');
            f("#"+config.delButtonId).removeAttr('disabled');
        }
        if(showErrorMsg === true){
            f.messager.alert("提示",errorMsg,"error");
        }else{
            var unit = config.unit ==="M"?"M":"kb";
            var filesize = unit === "M"?config.filesize*1024:config.filesize;
            if(size > filesize){
                f("#"+config.upLoadBtnId).attr('disabled', 'disabled');
                f.messager.alert("提示","上传文件最大不能超过" + config.filesize + unit +"!","info");
            }else{
                verifyImg.onload = function(){
                    if(config.imgWidth && config.imgHeight){
                        if((typeof config.imgWidth==="String" && typeof config.imgHeight==="String")&&(config.imgWidth ==="default" && config.imgHeight ==="default")){
                            disabledBool = false;

//                            if(config.viewImgSrcId && config.viewImgSrcId!=""){
//                                f("#"+config.viewImgSrcId).attr("src",verifyImg.src);
//                            }
                        }else if((verifyImg.width > config.imgWidth) || (verifyImg.height > config.imgHeight)){
                            f("#"+config.upLoadBtnId).attr('disabled', 'disabled');
                            f.messager.alert("提示","图片的长宽不能大于'"+ config.imgWidth +"*"+ config.imgHeight +"'像素","info");
                        }
                        f("#imgPreview").attr("src", verifyImg.src).css({
                            width: config.imgPreviewWidth+"px",
                            height: config.imgPreviewHeight+"px"
                        });
                        if(config.viewImgSrcId && config.viewImgSrcId!=""){
                            f("#"+config.viewImgSrcId).attr("src",verifyImg.src);
                        }
                    }
                };
                if(document.all){
                    getFile.select();
                    verifyImg.src = document.selection.createRange().text;
                }
                else {
                    verifyImg.src = window.URL.createObjectURL(getFile.files[0]);

                }

            }

        }

    }

    function uploadFile(id,config){
        var fileObject = new Object();
        fileObject.fileElementId = id;
        fileObject.uploadBtnId = config.upLoadBtnId;
        fileObject.hiddenInputId = config.inputFileId;
        if(config.bigFile == true) {
        	$("#p2").css('display','block');
        	var fileName = f("#"+id).val();
        	var fsize;
            if(config.filesize){
                if (navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion .split(";")[1].replace(/[ ]/g,"")=="MSIE8.0") {
                    var objFSO = new ActiveXObject("Scripting.FileSystemObject");
                    var filePath = $('#' + id).get(0).value;
                    var objFile = objFSO.getFile(filePath);
                    fsize = objFile.size;
                }else{
                    fsize =$('#' + id).get(0).files[0].size;
                }
            }
            var i = 0;
        	var timer = setInterval(function() {
        		var ajax_getFileSize = {
            			url : getRootName() + "/commonFileUpload/getFileSize",
            			type : 'post',
            			data : {"fileDir":fileName},
            			dateType : 'json',
            			error : function(){
            				f.messager.alert('错误', 'ajax_error', 'error');
            			},
            			success : function(data){
            				if(data == '') {
            					data = 0;
            				}
            				var val = (data/fsize)*100;
            				if(val < 10) {
            					val = i;
            					i++;
            				}
            				$('#p1').progressbar('setValue', parseInt(val));
            			}
            	};
            	ajax_(ajax_getFileSize);
        	}, 1000);
        }
        performFileUpload(fileObject,config,timer);
    }

  
    
    function uploadCallBack(fileObject,config){
        f("#" + fileObject.hiddenInputId).val(fileObject.filePath);
        f("#"+config.downloadFile).html(fileObject.filePath);
        initDownloadLinkTag(config.downloadFile, fileObject.filePath,config);
        //添加回调
        var downloadPath = fileObject.filePath,
            selectFun = config.onFileUpload,
            fileName = f("#"+config.downloadFile).text();
        if(selectFun && (typeof  selectFun ==="function")){
            selectFun.call(this,downloadPath,fileName, fileObject.fileSize);
        }
    }

    function performFileUpload(uploadObject,config,timer) {
        f.ajaxFileUpload({
            url : config.url,
            secureuri : false,
            fileElementId : uploadObject.fileElementId,
            dataType : 'json',
            success : function(data, status) {
                var disabledBool = true;
                if(config.repeatUpload && config.repeatUpload === true) {
                    disabledBool = false;
                }
                f("#" + uploadObject.uploadBtnId).attr('disabled', disabledBool);
                var dataJson = data.msg;
                dataJson = eval("(" + dataJson + ")");
                var fileName = dataJson.fileName;
                var fileDir = dataJson.fileDir;
                var filePathCommon = "//" + fileDir + "//" + fileName;
                var fileObject = new Object();
                fileObject.filePath = filePathCommon;
                fileObject.hiddenInputId = uploadObject.hiddenInputId;
                fileObject.fileSize = dataJson.fileSize;
                uploadCallBack(fileObject,config);
                $('#' + uploadObject.fileElementId).change(function(){
                    verifyFileFormat($('#' + uploadObject.fileElementId).get(0),config);
                });
                if(config.bigFile == true) {
                	$("#p2").css("display","none");
                	clearInterval(timer);
                }
            },
            error : function(data, status, e) {
                $('#' + uploadObject.fileElementId).change(function(){
                    verifyFileFormat($('#' + uploadObject.fileElementId).get(0),config);
                });
                if(config.bigFile == true) {
                	$("#p2").css("display","none");
                	clearInterval(timer);
                }
                f.messager.alert("提示","文件上传失败！","error");
                return false;
            }
        });
    }

    function initDownloadLinkTag(tagId, fileHref, config) {
        if('' == fileHref || null == fileHref){
            f("#" + tagId).attr("href", '#');
            f("#" + tagId).text('');
        }else{
            var relHref = config.downloadLinkUrl + fileHref,
                relFileName = construFileName(fileHref);
            relHref = relHref.replace(/['\/\/']/g, "\\");
            f("#" + tagId).attr("href", relHref);
            f("#" + tagId).text(relFileName);
            f("#" + tagId).attr("title","点击下载："+relFileName);
        }

    }

    function construFileName(originalFileName) {
        var allFileName = originalFileName.split("//")[2],
            allFileNameSplit = allFileName.split("."),
            allFileNameLen = allFileNameSplit.length-1,
            fileNamePrefix = "",
            fileNameSuffix = allFileNameSplit[allFileNameLen];
        for(var i=0;i<allFileNameLen;i++){
            if(allFileNameSplit[i].indexOf("_")!=-1&&allFileNameSplit[i].length>24){
                fileNamePrefix+= allFileNameSplit[i].substring(0, allFileNameSplit[i].length - 24)+".";
            }else{
                fileNamePrefix+=allFileNameSplit[i]+".";
            }
        }
        var relFileName = fileNamePrefix;
        return relFileName + fileNameSuffix;
    }

    function delFile(fileHref,tagId){
    	var relHref = fileHref;
    	var ajax_delFile = {
    			url : getRootName() + "/commonFileUpload/delFile",
    			type : 'post',
    			data : {"fileDir":relHref},
    			dateType : 'json',
    			error : function(){
    				f.messager.alert('错误', 'ajax_error', 'error');
    			},
    			success : function(data){
					if(tagId!=''){
						$("#"+tagId).text('删除成功,及时保存');
					}
    			}
    	};
    	ajax_(ajax_delFile);
    }
    
    f.extend({
        createUploadIframe: function(id, uri){
            var frameId = 'jUploadFrame' + id;
            var iframeHtml = '<iframe id="' + frameId + '" name="' + frameId + '" style="position:absolute; top:-9999px; left:-9999px"';
            if(window.ActiveXObject){
                if(typeof uri== 'boolean'){
                    iframeHtml += ' src="' + 'javascript:false' + '"';
                }
                else if(typeof uri== 'string'){
                    iframeHtml += ' src="' + uri + '"';
                }
            }
            iframeHtml += ' />';
            f(iframeHtml).appendTo(document.body);
            return f('#' + frameId).get(0);
        },

        //create form
        createUploadForm: function(id, fileElementId, data){
            var formId = 'jUploadForm' + id;
            var fileId = 'jUploadFile' + id;
            var form = f('<form  action="" method="POST" name="' + formId + '" id="' + formId + '" enctype="multipart/form-data"></form>');
            if(data){
                for(var i in data){
                    f('<input type="hidden" name="' + i + '" value="' + data[i] + '" />').appendTo(form);
                }
            }
            var oldElement = f('#' + fileElementId);
            var newElement = f(oldElement).clone();
            f(oldElement).attr('id', fileId);
            f(oldElement).before(newElement);
            f(oldElement).appendTo(form);
            //set attributes
            f(form).css('position', 'absolute');
            f(form).css('top', '-1200px');
            f(form).css('left', '-1200px');
            f(form).appendTo('body');
            return form;
        },
        ajaxFileUpload: function(s) {
            s = f.extend({}, f.ajaxSettings, s);
            var id = new Date().getTime();
            var form = f.createUploadForm(id, s.fileElementId, (typeof(s.data)=='undefined'?false:s.data));
            var io = f.createUploadIframe(id, s.secureuri);
            var frameId = 'jUploadFrame' + id;
            var formId = 'jUploadForm' + id;
            // Watch for a new set of requests
            if ( s.global && ! f.active++ ){
                f.event.trigger( "ajaxStart" );
            }
            var requestDone = false;
            // Create the request object
            var xml = {};
            if ( s.global )
                f.event.trigger("ajaxSend", [xml, s]);
            // Wait for a response to come back
            var uploadCallback = function(isTimeout){
                var io = document.getElementById(frameId);
                try{
                    if(io.contentWindow){
                        xml.responseText = io.contentWindow.document.body?io.contentWindow.document.body.innerHTML:null;
                        xml.responseXML = io.contentWindow.document.XMLDocument?io.contentWindow.document.XMLDocument:io.contentWindow.document;
                    }else if(io.contentDocument){
                        xml.responseText = io.contentDocument.document.body?io.contentDocument.document.body.innerHTML:null;
                        xml.responseXML = io.contentDocument.document.XMLDocument?io.contentDocument.document.XMLDocument:io.contentDocument.document;
                    }
                }catch(e){
                    f.handleError(s, xml, null, e);
                }
                if ( xml || isTimeout == "timeout"){
                    requestDone = true;
                    var status;
                    try {
                        status = isTimeout != "timeout" ? "success" : "error";
                        // Make sure that the request was successful or notmodified
                        if ( status != "error" ){
                            // process the data (runs the xml through httpData regardless of callback)
                            var data = f.uploadHttpData( xml, s.dataType );
                            // If a local callback was specified, fire it and pass it the data
                            if ( s.success )
                                s.success( data, status );
                            // Fire the global callback
                            if( s.global )
                                f.event.trigger( "ajaxSuccess", [xml, s] );
                        } else
                            f.handleError(s, xml, status);
                    } catch(e){
                        status = "error";
                        f.handleError(s, xml, status, e);
                    }
                    // The request was completed
                    if( s.global )
                        f.event.trigger( "ajaxComplete", [xml, s] );
                    // Handle the global AJAX counter
                    if ( s.global && ! --f.active )
                        f.event.trigger( "ajaxStop" );
                    // Process result
                    if ( s.complete )
                        s.complete(xml, status);
                    f(io).unbind();
                    setTimeout(function(){
                        try{
                            f(io).remove();
                            f(form).remove();
                        } catch(e){
                            f.handleError(s, xml, null, e);
                        }

                    }, 100);
                    xml = null;
                }
            };
            // Timeout checker
            if ( s.timeout > 0 )
            {
                setTimeout(function(){
                    // Check to see if the request is still happening
                    if( !requestDone ) uploadCallback( "timeout" );
                }, s.timeout);
            }
            try{
                var form = f('#' + formId);
                f(form).attr('action', s.url);
                f(form).attr('method', 'POST');
                f(form).attr('target', frameId);
                if(form.encoding)                {
                    f(form).attr('encoding', 'multipart/form-data');
                }else{
                    f(form).attr('enctype', 'multipart/form-data');
                }
                f(form).submit();
            } catch(e){
                f.handleError(s, xml, null, e);
            }
            f('#' + frameId).load(uploadCallback	);
            return {abort: function () {}};
        },

        uploadHttpData: function( r, type ) {
            var data = !type;
            data = type == "xml" || data ? r.responseXML : r.responseText;
            // If the type is "script", eval it in global context
            if ( type == "script" )
                f.globalEval( data );
            // Get the JavaScript object, if JSON is used.
            if ( type == "json" )
            //eval("data = \" "+data+" \"; ");
                data = eval("(" + data + ")");
            // evaluate scripts within html
            if ( type == "html" )
                f("<div>").html(data).evalScripts();

            return data;
        },

        handleError: function( s, xhr, status, e ) {
            // If a local callback was specified, fire it
            if ( s.error ) {
                s.error.call( s.context || s, xhr, status, e );
            }
            // Fire the global callback
            if ( s.global ) {
                (s.context ? f(s.context) : f.event).trigger( "ajaxError", [xhr, s, e] );
            }
        }
    });


})();