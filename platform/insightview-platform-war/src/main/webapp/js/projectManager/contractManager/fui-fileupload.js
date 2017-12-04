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
        imgWidth:16,
        imgHeight:16
    };

    f.fn.f_fileupload = function(options,param){
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
            hiddenInput = f("<input id='iconFileName' name='" + hiddenInputName + "' type='hidden' value='"+ f(self).attr("value") +"'/>"),
            hiddenInputId = hiddenInput.attr("id"),
            span = f("<span id='span_ImgPreview'  style='width:"+ config.imgWidth +"px;height:" +config.imgHeight +"px;'/>"),
            previewInput = f("<img id='imgPreview' src='"+ imgSrc +"'/>"),
            downloadButton = f("<a id='downloadFile' title='"+ config.title +"'/>"),
            uploadButton = f("<input id='uploadBtn' type='button' value='上传' disabled='disabled' style='width:50px;'/>");
        f(self).attr("name",hiddenInputName+"_file");
        if(config.whetherPreview === true){
            span.insertBefore(self);
            span.append(previewInput);
            hiddenInput.insertBefore(span);
        }
        else {
            hiddenInput.insertBefore(self);
        }
        downloadButton.insertAfter(self);
        if(f(self).nextAll("#uploadBtn").length == 0){
            uploadButton.insertAfter(downloadButton);
        }
        self.change(function(){
            var that = this;
            verifyFileFormat(that,config);
        });

        uploadButton.click(function(){
            uploadFile(fileElementId,config);

        });

    };

    function verifyFileFormat(getFile,config){
    	//debugger;
        var verifyImg = new Image(),
            opt = getFile.value.lastIndexOf(".")+ 1,
            disabledBool = true,
            showErrorMsg = true;
        if(config.filesize){
            var fileSize = getFile.files[0].size,
                size = fileSize/1024;
        }
        if(config.fileFormat){
            var fileType = getFile.value.substring(opt,getFile.value.length),
                fileTypeArr = eval('(' + config.fileFormat + ')'),
                errorMsg="上传的文件格式仅支持：" + fileTypeArr.toString();
            for(var i=0; i < fileTypeArr.length;i++){
                if(fileType == fileTypeArr[i]){
                    showErrorMsg = false;
                    disabledBool = false;
                    break;
                }
            }
        }else{
            showErrorMsg = false;
            disabledBool = false;
        }

        f("#uploadBtn").attr('disabled', disabledBool);
        console.log("test" + f("#uploadBtn").attr("disabled"));
        if(showErrorMsg === true){
            f.messager.alert("提示",errorMsg,"info");
        }else{

            if(size > config.filesize){
                f("#uploadBtn").attr('disabled', true);
                f.messager.alert("提示","上传文件最大不能超过" + config.filesize +"kb!","info");
            }else{
                if(document.all){
                    getFile.select();
                    verifyImg.src = document.selection.createRange().text;
                }
                else {
                    verifyImg.src = window.URL.createObjectURL(getFile.files[0]);

                }
                verifyImg.onload = function(){
                    if(verifyImg.width > config.imgWidth|| verifyImg.height > config.imgHeight){
                        f("#uploadBtn").attr('disabled', true);
                        f.messager.alert("提示","图片的长宽不能大于'"+ config.imgWidth +"*"+ config.imgHeight +"'像素","info");
                    }else{
                        disabledBool = false;
                        f("#imgPreview").attr("src", verifyImg.src);
                    }
                };
            }

        }

    }

    function uploadFile(id,config){
        var fileObject = new Object();
        fileObject.fileElementId = id;
        fileObject.uploadBtnId = "uploadBtn";
        fileObject.hiddenInputId = 'iconFileName';
        performFileUpload(fileObject,config);
    }

    function uploadCallBack(fileObject,config){
        f("#" + fileObject.hiddenInputId).val(fileObject.filePath);
        f("#downloadFile").html(fileObject.filePath);
        initDownloadLinkTag("downloadFile", fileObject.filePath,config);
        //添加回调
        var downloadPath = fileObject.filePath,
            selectFun = config.onFileUpload,
            fileName = f("#downloadFile").text();
        if(selectFun && (typeof  selectFun ==="function")){
            selectFun.call(this,downloadPath,fileName);
        }
    }

    function performFileUpload(uploadObject,config) {
        f.ajaxFileUpload({
            url : config.url,
            secureuri : false,
            fileElementId : uploadObject.fileElementId,
            dataType : 'json',
            success : function(data, status) {
                var disabledBool = false;
                if(config.repeatUpload && config.repeatUpload === false) {
                    disabledBool = true;
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
                uploadCallBack(fileObject,config);
            },
            error : function(data, status, e) {
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
        }

    }

    function construFileName(originalFileName) {
        var allFileName = originalFileName.split("//")[2],
            fileNamePrefix = allFileName.split(".")[0],
            fileNameSuffix = allFileName.split(".")[1],
            relFileName = fileNamePrefix.substring(0, fileNamePrefix.length - 24);
        return relFileName + "." + fileNameSuffix;
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