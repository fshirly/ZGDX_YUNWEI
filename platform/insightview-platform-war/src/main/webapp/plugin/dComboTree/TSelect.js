//���̷���·�������������Ϊ��·����ʱ���뽫_bp="";
var _bp = location.pathname.substring(0,location.pathname.indexOf("/",1));
_bp="";

//���������������洢���ֵ��ֶ������洢ID���ֶ������������ͣ����мӵĲ�������ʾ�������Ŀ�ȣ�������html�����еĿ������ʾ��
function addTree(nameField,valueField,treeType,params,textLen){
	if(!textLen)textLen = "100%";
	_ssjTSel.nameFields[_ssjTSel.nameFields.length] = nameField;
	if(!params)params="";
	_ssjTSel.createTextField(nameField,valueField,textLen);//alert()
	if(!_ssjTSel.types[treeType])
		$(_ssjTSel.pre+nameField).value="û�д����͵�������������ϵ����Ա��";
	var tmpUrl = _ssjTSel.types[treeType]+(_ssjTSel.types[treeType].indexOf("?")>=0?"&":"?")+params;
	if(_ssjTSel.urls.indexOf(tmpUrl)<0){
		_ssjTSel.urls[_ssjTSel.urls.length]=tmpUrl;
		_ssjTSel.createIFrame(tmpUrl);
	}
	$(_ssjTSel.pre+nameField).frame = eval("document.all."+_ssjTSel.pre+"frame_"+_ssjTSel.urls.indexOf(tmpUrl));
}
//��������ģ��
function TSelect(){
	this.urls = [];
	this.nameFields = [];
	this.types = {};
	this.pre = "ssj_";
	this.curTxt = null;
	
	this.types["org"] = "tree.htm";
}
//��ʾ��
TSelect.prototype.showTree = function(){
	var tmpTxt = this.curTxt;
	this.hideTree();
	if(tmpTxt == event.srcElement.parentNode.parentNode.cells[0].firstChild)return;
	this.curTxt = event.srcElement.parentNode.parentNode.cells[0].firstChild;
	var div = fnGetPreEle(this.curTxt,"DIV");//s.s
	var pos = getoffset(div);
	this.curTxt.frame.style.top = pos[0] + div.clientHeight + 4;
	this.curTxt.frame.style.left = pos[1] + 1.5;
	this.curTxt.frame.style.width = (div.clientWidth>220?div.clientWidth+1:220);
	try{this.curTxt.frame.contentWindow.setSelectedNode($(this.curTxt.valueName).value);}catch(e){;}
	this.curTxt.frame.style.visibility="visible";
}
//������
TSelect.prototype.hideTree = function(){
	if(this.curTxt==null || this.curTxt.frame==null || this.curTxt.frame.style.visibility!="visible")return;
	try{this.curTxt.value = this.curTxt.frame.contentWindow.getSelText();}catch(e){;}
	var objs = $2(this.curTxt.name.substring(_ssjTSel.pre.length));
	if(objs.length>1)eval("_"+this.curTxt.name.substring(_ssjTSel.pre.length)+".parentNode.removeChild(_"+this.curTxt.name.substring(_ssjTSel.pre.length)+")");
	try{objs[0].value = this.curTxt.frame.contentWindow.getSelText();}catch(e){;}
	if(this.curTxt.name!=this.curTxt.valueName){
		var objs = $2(this.curTxt.valueName);
		if(objs.length>1)eval("_"+this.curTxt.valueName+".parentNode.removeChild(_"+this.curTxt.valueName+")");
		try{objs[0].value = this.curTxt.frame.contentWindow.getSelValue();}catch(e){;}
	}
	this.curTxt.frame.style.visibility="hidden";
	this.curTxt = null;
}
//����������ģ���Ķ���
TSelect.prototype.createTextField = function(nameField,valueField,textLen){
	document.write("<div style='BORDER: 2px inset;HEIGHT: 23px;width:"+textLen+";'>");
	document.write("<table cellSpacing=0 cellPadding=0><tr><td width='"+textLen+"'><input type='text' valueName='"+valueField+"' name='"+_ssjTSel.pre+nameField+"' id='"+_ssjTSel.pre+nameField+"' readonly class='noborderinput'/></td>");
	document.write("<td><img name='"+_ssjTSel.pre+"img' src='"+_bp+"ssjImg/icn_003.gif' onMouseDown='this.src=\""+_bp+"ssjImg/icn_003_b.gif\"' onMouseUp='this.src=\""+_bp+"ssjImg/icn_003.gif\"' style='align:absmiddle;' onclick='_ssjTSel.showTree();'></td></tr></table>");
	if($2(nameField).length==0)
		document.write("<input type='hidden' name='"+nameField+"' id='_"+nameField+"'/>");
	else
		$(_ssjTSel.pre+nameField).value=$2(nameField)[0].value;
	if($2(valueField).length==0 && valueField!=nameField)
		document.write("<input type='hidden' name='"+valueField+"' id='_"+valueField+"'/>");
	else if($2(valueField).length>0)
		_ssjTSel.setNameField(nameField);
	document.write("</div>");
}
//��ҳ���а�IDת�������ơ�
TSelect.prototype.setNameField = function(nameField){
	if(nameField){
		try{
			if($(_ssjTSel.pre+nameField).frame && $(_ssjTSel.pre+nameField).frame.contentWindow.isLoad()){
				$(_ssjTSel.pre+nameField).value=$(_ssjTSel.pre+nameField).frame.contentWindow.getItemTextById($2($(_ssjTSel.pre+nameField).valueName)[0].value);
				$2(nameField)[0].value = $(_ssjTSel.pre+nameField).value;
			}else{
				setTimeout("_ssjTSel.setNameField('"+nameField+"')",100);
			}
		}catch(e){
			setTimeout("_ssjTSel.setNameField('"+nameField+"')",100);
		}
	}else{
		for(var i=0;i<_ssjTSel.nameFields.length;i++){
			nameField = _ssjTSel.nameFields[i];
			try{
				if($(_ssjTSel.pre+nameField).frame && $(_ssjTSel.pre+nameField).frame.contentWindow.isLoad()){
					$(_ssjTSel.pre+nameField).value=$(_ssjTSel.pre+nameField).frame.contentWindow.getItemTextById($2($(_ssjTSel.pre+nameField).valueName)[0].value);
					$2(nameField)[0].value = $(_ssjTSel.pre+nameField).value;
				}else{
					setTimeout("_ssjTSel.setNameField('"+nameField+"')",100);
				}
			}catch(e){
				setTimeout("_ssjTSel.setNameField('"+nameField+"')",100);
			}
		}
	}
}
//����IFRAME
TSelect.prototype.createIFrame = function(url){
	document.write("<iframe src='"+_bp+url+"' frameborder='0' id='"+_ssjTSel.pre+"frame_"+_ssjTSel.urls.indexOf(url)+"' name='"+_ssjTSel.pre+"frame_"+_ssjTSel.urls.indexOf(url)+"' style='position:absolute; top:0;border:1px solid; left:0; width:100; height:250; visibility:hidden; z-index:500;margin-left:0;margin-top:0;'></iframe>");
}
//Ϊҳ�������ʽ��
document.write("<style type='text/css'>");
document.write(".noborderinput{BORDER: white 1px dashed;width:100%;HEIGHT: 17px;}");
document.write("</style>");
//����������
var _ssjTSel = new TSelect();
//��ҳ���е�������ط�ʱ������
document.onclick = function(){
	if(event.srcElement.name!=(_ssjTSel.pre+"img")){
		_ssjTSel.hideTree();
	}
}
//----------------------------------��������---------------------------------------
Array.prototype.indexOf = function(item){for(var i=0;i<this.length;i++){if(this[i]==item)return i;}return -1;}
function $(id){return document.getElementById(id)}
function $2(name){return document.getElementsByName(name);}
function fnGetPreEle(oEl,tagname){
	try{while( null != oEl && oEl.tagName!=tagname )oEl = oEl.parentElement;
	return oEl; }catch(e){return null;} 
}
function getoffset(e){
 	var t=e.offsetTop; 	var l=e.offsetLeft;
 	while(e=e.offsetParent){t+=e.offsetTop;l+=e.offsetLeft;}
 	var rec = [];rec[0]  = t;rec[1] = l;
 	return rec
}