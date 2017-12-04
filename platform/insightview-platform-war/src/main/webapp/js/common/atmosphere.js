$(function () {
	
    var socket = $.atmosphere;

    // We are now ready to cut the request
    var request = { url: getRootName() + '/atmosphere/subscribe/ann',
        contentType : "application/json",
        logLevel : 'debug',
        transport : 'websocket' ,
        fallbackTransport: 'long-polling'};


    request.onOpen = function(response) {
       
    };

    //<!-- For demonstration of how you can customize the fallbackTransport based on the browser -->
    request.onTransportFailure = function(errorMsg, request) {
        jQuery.atmosphere.info(errorMsg);
        if ( window.EventSource ) {
            request.fallbackTransport = "long-polling";
        }
    };

    request.onReconnect = function (request, response) {
        socket.info("Reconnecting");
    };

    request.onMessage = function (response) {
        var message = response.responseBody;
        try {
            var json = jQuery.parseJSON(message);
            //右下角弹出框
            fableApp.msgTips.msgTips(json);
        } catch (e) {
           // console.log('This doesn\'t look like a valid JSON: ', message.data);
            return;
        }
    };

    request.onClose = function(response) {
        logged = false;
    };

    request.onError = function(response) {
    	//console.log('Sorry, but there\'s some problem with your '
         //   + 'socket or the server is down');
    };

    var subSocket = socket.subscribe(request);

});
