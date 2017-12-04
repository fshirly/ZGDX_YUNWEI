   
(function ($) {   
jQuery.fn.LRSelect = function (selLeft, selRight, img_L_AllTo_R, img_L_To_R, img_R_To_L, img_R_AllTo_L, LIsRemovable, RIsRemovable) {   
    $("#" + img_L_AllTo_R + ",#" + img_L_To_R + ",#" + img_R_To_L + ",#" + img_R_AllTo_L).css("cursor", "pointer");   
   if (typeof (LIsRemovable) == "undefined") {   
       LIsRemovable = true;   
   }   
   if (typeof (RIsRemovable) == "undefined") {   
       RIsRemovable = true;   
   }   
   $("#" + selLeft).dblclick(function () {   
       LRSelect.movePart(selLeft, selRight, true);   
   });   
   $("#" + selRight).dblclick(function () {   
       LRSelect.movePart(selRight, selLeft, false);   
   });   
   $("#" + img_L_AllTo_R).click(function () {   
       LRSelect.moveAll(selLeft, selRight, true);   
   });   
   $("#" + img_L_To_R).click(function () {   
       LRSelect.movePart(selLeft, selRight, true);   
   });   
   $("#" + img_R_To_L).click(function () {   
       LRSelect.movePart(selRight, selLeft, false);   
   });   
   $("#" + img_R_AllTo_L).click(function () {   
       LRSelect.moveAll(selRight, selLeft, false);   
   });   
   LRSelect = {};   
   LRSelect.movePart = function (source, target, sourceIsLeft) {   
       var obj = $("#" + source + " :selected");   
       LRSelect.commonMove(obj, source, target, sourceIsLeft);   
   }   
   LRSelect.moveAll = function (source, target, sourceIsLeft) {   
       var obj = $("#" + source + " option");   
       LRSelect.commonMove(obj, source, target, sourceIsLeft);   
   }   
   LRSelect.commonMove = function (jqObj, source, target, sourceIsLeft) {   
       jqObj.reverseSelf().each(function () {   
           var selVal = $(this).val();   
           var selfOptgroupLab = $(this).parent().attr("label");   
 
           var haveGroup = false;   
           var targetHaveSameGroup = true;   
           if (typeof (selfOptgroupLab) != "undefined") {   
               haveGroup = true;   
               if ($("#" + target).find("optgroup[label='" + selfOptgroupLab + "']").length == 0) {   
                   targetHaveSameGroup = false;   
               }   
           }   
           //判断源是否可移     
           var sourceIsRemovable = (sourceIsLeft && LIsRemovable) || (!sourceIsLeft && RIsRemovable);   
           //如果对方没有这个值     
           if (!LRSelect.checkValInSelect(selVal, target)) {   
               //如果自身都没有optgroup, 对方直接加自己就好了.       
               if (!haveGroup) {   
                   //如果源是可移动的   
                   if (sourceIsRemovable) {   
                       $("#" + target).prepend($(this));   
                   } else {   
                       $("#" + target).prepend($(this).clone());   
                   }   
               }   
               //如果自身有optgroup       
               else {   
                   //如果对方已有相同的optgroup       
                   if (targetHaveSameGroup) {   
                       //如果源是可移动的     
                       if (sourceIsRemovable) {   
                           $("#" + target).find("optgroup[label='" + selfOptgroupLab + "']").prepend($(this));   
                       } else {   
                           $("#" + target).find("optgroup[label='" + selfOptgroupLab + "']").prepend($(this).clone());   
                       }   
                   } else {   
                       $("#" + target).prepend($(this).parent().clone());   
                       $("#" + target + " optgroup[label='" + selfOptgroupLab + "'] option[value!='" + selVal + "']").remove();   
                       //如果源是可移动的     
                       if (sourceIsRemovable) {   
                           $(this).remove();   
                       }   
                   }   
                   //如果源是可移动的     
                   if (sourceIsRemovable) {   
                       //如果给了对方之后, 自身的optgroup为空, 则删除自己的optgroup     
                       if ($("#" + source).find("optgroup[label='" + selfOptgroupLab + "'] option").length == 0) {   
                           $("#" + source).find("optgroup[label='" + selfOptgroupLab + "']").remove();   
                       }   
                   }   
               }   
           }   
            //如果对方有这个值了， 在自己能移动的情况下， 删除此值     
            else {   
                //如果自身都没有optgroup      
                if (!haveGroup) {   
                    //如果源是可移动的     
                    if (sourceIsRemovable) {   
                        $(this).remove();   
                    }   
                } else {   
                    //如果源是可移动的     
                    if (sourceIsRemovable) {   
                        $(this).remove();   
                        //如果给了对方之后, 自身的optgroup为空, 则删除自己的optgroup     
                        if ($("#" + source).find("optgroup[label='" + selfOptgroupLab + "'] option").length == 0) {   
                            $("#" + source).find("optgroup[label='" + selfOptgroupLab + "']").remove();   
                        }   
                    }   
                }   
            }   
        });   
        $("#" + source + " option").each(function () {   
            $(this).get(0).selected = false;   
        });   
        $("#" + target + " option").each(function () {   
            $(this).get(0).selected = false;   
        });   
    }   
    //判断一个值,在select中是否已存在           
    LRSelect.checkValInSelect = function (val, selectId) {   
        var isExist = false;   
        $("#" + selectId + " option").each(function () {   
            if (val == $(this).val()) {   
                isExist = true;   
            }   
        });   
        return isExist;   
    }   
}   
})(jQuery);   
(function ($) {   
    $.fn.reverseSelf = function () {   
        var arr = [];   
        for (var i = this.length - 1; i >= 0; i--) {   
            arr.push(this.get(i));   
        }   
        return $(arr);   
    }   
})(jQuery);    
//=====================================================================     
//插件名称: UpDownSelOption     
//作    者:    
//功能说明: 设置指定的select的option的上下移动.     
//输入参数: 上下图片的id, 需要移动option的select的id   
//调用示例: $(function(){ $.fn.UpDownSelOption("imgUp", "imgDown", "selRight"); });     
//输出参数:    
//创建日期: 2011-08-22   
(function ($) {   
jQuery.fn.UpDownSelOption = function (imgUp, imgDown, up_down_targetId) {   
    $("#" + imgUp + ",#" + imgDown).css("cursor", "pointer");   
    var $sel = $("#" + up_down_targetId);   
    $("#" + imgUp).click(function () {   
        if ($sel.find("option:selected").length == 0) {   
            return;   
        }   
        if ($sel.find("option:selected:first").prev().length == 0) {   
            return;   
        }   
        $sel.find("option:selected:first").prev().insertAfter($sel.find("option:selected:last"));   
    });   
    $("#" + imgDown).click(function () {   
        if ($sel.find("option:selected").length == 0) {   
            return;   
        }   
        if ($sel.find("option:selected:last").next().length == 0) {   
            return;   
        }   
        $sel.find("option:selected:last").next().insertBefore($sel.find("option:selected:first"));   
    });   
}   
})(jQuery);   
//=====================================================================     
//插件名称: LSelect_RVSelect       
//作    者:    
//功能说明: 设置左select右虚拟select的option的互相移动.     
//输入参数: 1-6:左select,右select ,>| ,> ,< , |< 这六个DOM元素的Id     
//          7:checkbox的name     
//          8:select中option是否移动 (无此参数则为可移动---true)     
//          9:li是否可移动           (无此参数则为可移动---true)     
//调用示例:$(function (){ $.fn.LSelect_RVSelect("selLeft", "ulVirtualSel", "img_L_AllTo_R", "img_L_To_R", "img_R_To_L", "img_R_AllTo_L","chkItem");}     
//输出参数:     
//创建日期: 2011-08-31   
(function ($) {   
    jQuery.fn.LSelect_RVSelect = function (selLeft, ulVirtualSel, img_L_AllTo_R, img_L_To_R, img_R_To_L, img_R_AllTo_L, chkName, selOpIsRemovable, liIsRemovable) {   
        $("#" + img_L_AllTo_R + ",#" + img_L_To_R + ",#" + img_R_To_L + ",#" + img_R_AllTo_L).css("cursor", "pointer");   
        if (typeof (selOpIsRemovable) == "undefined") {   
            selOpIsRemovable = true;   
        }   
        if (typeof (liIsRemovable) == "undefined") {   
            liIsRemovable = true;   
        }   
        //左边的select的option双击     
        $("#" + selLeft).live("dblclick",function () {   
            var obj = $("#" + selLeft + " :selected");   
            selToUL(obj);   
        });   
        //click '>|'     
        $("#" + img_L_AllTo_R).click(function () {   
            var obj = $("#" + selLeft + " option");   
            selToUL(obj);   
        });   
        //click '>'     
        $("#" + img_L_To_R).click(function () {   
            var obj = $("#" + selLeft + " :selected");   
            selToUL(obj);   
        });   
        //右边的li单击, 设置样式   
        $("#" + ulVirtualSel + " li").live("click", function() {   
            $(this).toggleClass("ulVirtualSel_Click");   
        });   
        $("#" + ulVirtualSel + " li").live("dblclick", function() {   
            var obj = $(this);   
            ulToSel(obj);   
        });   
  
        //click '<'     
        $("#" + img_R_To_L).click(function () {   
            var obj = $("#" + ulVirtualSel + " li[class='ulVirtualSel_Click']");   
            ulToSel(obj);   
        });   
        //click '|<'     
        $("#" + img_R_AllTo_L).click(function () {   
            var obj = $("#" + ulVirtualSel + " li");   
            ulToSel(obj);   
        });   
        //select to ul     
        function selToUL(jqObj) {   
            jqObj.reverseSelf().each(function () {   
                var selVal = $(this).val();   
                if (!isExtInUL(selVal, ulVirtualSel)) {   
                    var html = "<li><input name='" + chkName + "' type='checkbox' value='" + selVal + "'><span>" + $(this).text() + "</span></li>";   
                    $("#" + ulVirtualSel).prepend(html);   
                }   
                if (selOpIsRemovable) {   
                    $(this).remove();   
                }   
            });   
        }   
        //ul to select     
        function ulToSel(jqObj) {   
            jqObj.reverseSelf().find(":checkbox").each(function () {   
                var selVal = $(this).val();   
                if (!isExtInSel(selVal, selLeft)) {   
                    var html = "<option value='" + selVal + "'>" + $(this).parent().find("span").text() + "</option>";   
                    $("#" + selLeft).prepend(html);   
                }   
                if (liIsRemovable) {   
                    $(this).parent().remove();   
                }   
            });   
        }   
        //判断选中值是否在ul中     
        function isExtInUL(selVal, target) {   
            var result = false;   
            $("#" + target + " :checkbox").each(function () {   
                if ($(this).val() == selVal) {   
                    result = true;   
                }   
            });   
            return result;   
        }   
        //判断选中值是否在select中     
        function isExtInSel(selVal, target) {   
            var isExist = false;   
            $("#" + target + " option").each(function () {   
                if (selVal == $(this).val()) {   
                    isExist = true;   
                }   
            });   
            return isExist;   
        }   
    }   
})(jQuery); 