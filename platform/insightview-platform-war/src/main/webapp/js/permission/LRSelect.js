   
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
           //�ж�Դ�Ƿ����     
           var sourceIsRemovable = (sourceIsLeft && LIsRemovable) || (!sourceIsLeft && RIsRemovable);   
           //����Է�û�����ֵ     
           if (!LRSelect.checkValInSelect(selVal, target)) {   
               //�������û��optgroup, �Է�ֱ�Ӽ��Լ��ͺ���.       
               if (!haveGroup) {   
                   //���Դ�ǿ��ƶ���   
                   if (sourceIsRemovable) {   
                       $("#" + target).prepend($(this));   
                   } else {   
                       $("#" + target).prepend($(this).clone());   
                   }   
               }   
               //���������optgroup       
               else {   
                   //����Է�������ͬ��optgroup       
                   if (targetHaveSameGroup) {   
                       //���Դ�ǿ��ƶ���     
                       if (sourceIsRemovable) {   
                           $("#" + target).find("optgroup[label='" + selfOptgroupLab + "']").prepend($(this));   
                       } else {   
                           $("#" + target).find("optgroup[label='" + selfOptgroupLab + "']").prepend($(this).clone());   
                       }   
                   } else {   
                       $("#" + target).prepend($(this).parent().clone());   
                       $("#" + target + " optgroup[label='" + selfOptgroupLab + "'] option[value!='" + selVal + "']").remove();   
                       //���Դ�ǿ��ƶ���     
                       if (sourceIsRemovable) {   
                           $(this).remove();   
                       }   
                   }   
                   //���Դ�ǿ��ƶ���     
                   if (sourceIsRemovable) {   
                       //������˶Է�֮��, �����optgroupΪ��, ��ɾ���Լ���optgroup     
                       if ($("#" + source).find("optgroup[label='" + selfOptgroupLab + "'] option").length == 0) {   
                           $("#" + source).find("optgroup[label='" + selfOptgroupLab + "']").remove();   
                       }   
                   }   
               }   
           }   
            //����Է������ֵ�ˣ� ���Լ����ƶ�������£� ɾ����ֵ     
            else {   
                //�������û��optgroup      
                if (!haveGroup) {   
                    //���Դ�ǿ��ƶ���     
                    if (sourceIsRemovable) {   
                        $(this).remove();   
                    }   
                } else {   
                    //���Դ�ǿ��ƶ���     
                    if (sourceIsRemovable) {   
                        $(this).remove();   
                        //������˶Է�֮��, �����optgroupΪ��, ��ɾ���Լ���optgroup     
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
    //�ж�һ��ֵ,��select���Ƿ��Ѵ���           
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
//�������: UpDownSelOption     
//��    ��:    
//����˵��: ����ָ����select��option�������ƶ�.     
//�������: ����ͼƬ��id, ��Ҫ�ƶ�option��select��id   
//����ʾ��: $(function(){ $.fn.UpDownSelOption("imgUp", "imgDown", "selRight"); });     
//�������:    
//��������: 2011-08-22   
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
//�������: LSelect_RVSelect       
//��    ��:    
//����˵��: ������select������select��option�Ļ����ƶ�.     
//�������: 1-6:��select,��select ,>| ,> ,< , |< ������DOMԪ�ص�Id     
//          7:checkbox��name     
//          8:select��option�Ƿ��ƶ� (�޴˲�����Ϊ���ƶ�---true)     
//          9:li�Ƿ���ƶ�           (�޴˲�����Ϊ���ƶ�---true)     
//����ʾ��:$(function (){ $.fn.LSelect_RVSelect("selLeft", "ulVirtualSel", "img_L_AllTo_R", "img_L_To_R", "img_R_To_L", "img_R_AllTo_L","chkItem");}     
//�������:     
//��������: 2011-08-31   
(function ($) {   
    jQuery.fn.LSelect_RVSelect = function (selLeft, ulVirtualSel, img_L_AllTo_R, img_L_To_R, img_R_To_L, img_R_AllTo_L, chkName, selOpIsRemovable, liIsRemovable) {   
        $("#" + img_L_AllTo_R + ",#" + img_L_To_R + ",#" + img_R_To_L + ",#" + img_R_AllTo_L).css("cursor", "pointer");   
        if (typeof (selOpIsRemovable) == "undefined") {   
            selOpIsRemovable = true;   
        }   
        if (typeof (liIsRemovable) == "undefined") {   
            liIsRemovable = true;   
        }   
        //��ߵ�select��option˫��     
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
        //�ұߵ�li����, ������ʽ   
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
        //�ж�ѡ��ֵ�Ƿ���ul��     
        function isExtInUL(selVal, target) {   
            var result = false;   
            $("#" + target + " :checkbox").each(function () {   
                if ($(this).val() == selVal) {   
                    result = true;   
                }   
            });   
            return result;   
        }   
        //�ж�ѡ��ֵ�Ƿ���select��     
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