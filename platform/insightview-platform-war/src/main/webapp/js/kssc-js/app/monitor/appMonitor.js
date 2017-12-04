define(["base","commonApp"],function(base,common){
    var bodyHeight=$("body").height();
    $(".container").height(bodyHeight-30)
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
            url:"json/app.json"
        },
        columns:[
            {"title":"可用/持续的时间","data":"usableTime","sWidth":"20%"},
            {"title":"告警状态","data":"status","sWidth":"20%"},
            {"title":"应用名称","data":"appName","sWidth":"30%"},
            {"title":"应用地址","data":"address","sWidth":"30%"}
        ],
        columnDefs:[
            {
                "render":function(data,type,row,meta){
                    switch(row.status){
                        case "3":return "紧急";break;
                        case "2":return "严重";break;
                        case "1":return "一般";break;
                        case "0":return "正常";break;
                    }
                },
                targets:1
            }
        ],
        drawCallback:function(setting){
            base.scroll({
                container:$(".container")
            })
        }
    }
    var setGrid = function(){
        grid = base.datatables({
            container:$("#app"),
            option:application
        })
    }
    return {
        main:function(){
            setGrid()
        }
    }
})