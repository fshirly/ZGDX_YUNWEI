define(["base","commonApp"],function(base,common){
    var bodyHeight=$("body").height();
    $(".container").height(bodyHeight-30);
    var application = {
        processing:true,
        serverSide:false,
        searching:false,
        ordering:false,
        lengthChange:false,
        paging:true,
        info:true,
        pagingType: "full_numbers",
        ajax:{
            url:$.base+"/store/appMonitor",
            type:"post",
            contentType:"application/json",
            data:function (d) {
                return JSON.stringify({pageNo:d.start/d.length+1,pageSize:d.length,param:{}})
            }
        },
        columns:[
            {"title":"告警状态","data":"status","sWidth":"20%"},
            {"title":"应用名称","data":"name","sWidth":"30%"},
            {"title":"应用地址","data":"address","sWidth":"50%"}
        ],
        columnDefs:[
        ],
        drawCallback:function(setting){
            base.scroll({
                container:$(".container")
            })
        }
    };
    var setGrid = function(){
        grid = base.datatables({
            container:$("#app"),
            option:application
        })
    };
    return {
        main:function(){
            setGrid()
        }
    }
})