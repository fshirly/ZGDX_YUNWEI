/**
 * Created by Administrator on 2017/11/27 0027.
 */
define(["base","commonApp"],function (base,common) {
    var bodyHeight=$("body").height();
    var containerHeight = $(".container").height(bodyHeight-30);
    // $.ajax({
    //     url:$.base+"/monitor/alarmHistory/listAlarmHistoryNew",
    //     type:"post",
    //     contentType:"application/json",
    //     data:JSON.stringify({pageNo:1,pageSize:10,param:{alarmTitle:"",alarmType:"0",alarmLevel:'0',alarmOperateStatus:'0'}}),
    //     success:function (result) {
    //
    //     }
    // });
    var gridOption = {
        processing:true,
        serverSide:true,
        searching:false,
        ordering:false,
        lengthChange:false,
        paging:true,
        info:true,
        pagingType: "full_numbers",
        ajax:{
            url:$.base+"/monitor/alarmHistory/listAlarmHistoryNew",
            type:"post",
            contentType:"application/json",
            dataType:"json",
            data:function (d) {
                return JSON.stringify({pageNo:d.start/d.length+1,pageSize:d.length,param:{alarmTitle: $(".title").val(),alarmType:"0",alarmLevel:'0',alarmOperateStatus:'0'}})
            }
        },
        columns:[
            {"title":"序号","data": "id","sWidth":"10%"},
            {"title":"告警状态","data":"operateStatusName","sWidth":"10%"},
            {"title":"告警级别","data":"alarmLevelName","sWidth":"10%"},
            {"title":"首次发生时间","data":"timeBegin","sWidth":"15%"},
            {"title":"告警标题","data":"alarmTitle","sWidth":"15%"},
            {"title":"告警名称","data":"moName","sWidth":"15%"},
            {"title":"告警源IP","data":"sourceMOIPAddress","sWidth":"15%"},
            {"title":"告警类型","data":"alarmTypeName","sWidth":"10%"}
        ],
        columnDefs:[
            {
                "render":function(data,type,row,meta){
                    return meta.row
                },
                targets:0
            },
            {
                "render":function(data,type,row,meta){
                    var color = "";
                    switch (row.alarmLevelName){
                        case "紧急":
                            color = "#ce0000";
                            break;
                        case "严重":
                            color = "#e16602";
                            break;
                        case "一般":
                            color = "#ec9b00";
                            break;
                        case "提示":
                            color = "#81b601";
                            break;
                        case "未确定":
                            color = "#ccc";
                            break;
                    }
                    return  "<div style='width:100%;height:100%;background:"+color+" '>"+row.alarmLevelName+"</div>";
                },
                targets:2
            },
            {
                "render":function(data,type,row,meta){
                    if(row.startTime != null){
                        var time =  new Date(row.startTime);
                        var year =  time.getFullYear();
                        var month = time.getMonth()+1;
                        var date = time.getDate();
                        var hours = time.getHours();
                        var minute = time.getMinutes();
                        var second = time.getSeconds();
                        if(month<10){
                            month = "0"+month
                        }
                        if(date<10){
                            date = "0"+date
                        }
                        if(hours<10){
                            hours = "0"+hours
                        }

                        if(minute<10){
                            minute = "0"+minute
                        }
                        if(second<10){
                            second = "0"+second
                        }
                        return year+"-"+month+"-"+date+" "+hours+":"+minute+":"+second;
                    }

                },
                targets:3
            }
        ],
        drawCallback:function(setting){
            base.scroll({
                container:$(".container")
            })
        }
    };
    var setGrid = function(){
        grid = base.datatables({
            container:$("#alarmHistoryList"),
            option:gridOption
        })
    };
    //查询
    function search() {
        $("#searchBtn").off().on("click",function () {
            grid.reload()
        })
    }
    //重置
    function reset() {
        $("#resetBtn").off().on("click",function () {
            $(".title").val("")
        })
    }
    return {
        main:function () {
            setGrid();
            search();
            reset();
        }
    }
});
