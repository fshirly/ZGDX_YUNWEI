		function doChange(obj){
			obj.style.backgroundImage="url(/"+_productName+"/style/images/images1000/list_hover.gif)";
			obj.style.backgroundRepeat="no-repeat";			
		}
		
		function changeBack(obj){
			obj.style.backgroundImage="url(/"+_productName+"/style/images/images1000/list_bg.gif)";
			obj.style.backgroundRepeat="no-repeat";
		}
		
		
		function doChangeAgain(obj){
			obj.style.backgroundImage="url("+_productName+"/style/images/images1000/listbg_select.gif)";
			obj.style.backgroundRepeat="no-repeat";			
		}
		
		function changeBackAgain(obj){
			obj.style.backgroundImage="";
		
		}
		
		
		var imageBefore="";
		function doChangeForTips(obj){
			imageBefore= obj.style.backgroundImage;
			obj.style.backgroundImage="url("+_productName+"/style/images/images1000/title_hover.gif)";
			obj.style.backgroundRepeat="no-repeat";			
		}
		
		function changeBackForTips(obj){
			obj.style.backgroundImage=imageBefore;
			obj.style.backgroundRepeat="no-repeat";		
		
		}
		
		
		function doShowNow(obj){
			
			var areaShow=document.getElementById('equipment');
			if(areaShow.style.display=="none" ||areaShow.style.display=="" ){
					areaShow.style.display="block";
					obj.style.backgroundImage="url("+_productName+"/style/images/images1000/title_select.gif)";
					document.getElementById('lines').innerHTML="";
					document.getElementById('lines').innerHTML="<img src='"+_productName+"/style/images/images1000/line_grey.gif'  />"
					imageBefore = "url("+_productName+"/style/images/images1000/title_select.gif)";
			}else if(areaShow.style.display=="block"){
					areaShow.style.display="none";
					obj.style.backgroundImage="url("+_productName+"/style/images/images1000/title_legend.gif)";
					document.getElementById('lines').innerHTML="";
					document.getElementById('lines').innerHTML="<img src='"+_productName+"/style/images/images1000/line.gif'  />"
					imageBefore = "url("+_productName+"/style/images/images1000/title_legend.gif)";
			}
			
			
		}
		
		function doShow(){
			var temp=document.getElementById('hdUl');	
			var now =  temp.style.display;
			if(now == "block" ){
				temp.style.display ="none";	
			}else if(now=="none" || now==""){
				temp.style.display ="block";	
			}
		}
		
		
		function changeBg(obj){
			//alert("aa");
			obj.style.backgroundImage="url(..\images\images1000\page_select.gif)";
			obj.style.backgroundRepeat="no-repeat";
		}
		
		/*~~获取当前时间~~*/
		function getCurDate()
		{ 
			  var d = new Date();
			   var years = d.getYear();
			   var month = add_zero(d.getMonth()+1);
			   var days = add_zero(d.getDate());
			   var hours = add_zero(d.getHours()); 
			   var minutes = add_zero(d.getMinutes());
			   var seconds=add_zero(d.getSeconds());
			   var ndate =" &nbsp;"+ years+"."+month+"."+days ;
			   //alert(ndate);
			   dateArea.innerHTML= ndate;
			}
		   function add_zero(temp){ if(temp<10) return "0"+temp; else return temp;}
		   
		   setInterval("getCurDate()",100);
		   
		   
		   
		 /*提示框 js*/
		var rDrag = {
		 o:null,
		 init:function(o){
		  o.onmousedown = this.start;
		 },
		 start:function(e){
		  var o;
		  e = rDrag.fixEvent(e);
					   e.preventDefault && e.preventDefault();
					   rDrag.o = o = this;
		  o.x = e.clientX - rDrag.o.offsetLeft;
						o.y = e.clientY - rDrag.o.offsetTop;
		  document.onmousemove = rDrag.move;
		  document.onmouseup = rDrag.end;
		 },
		 move:function(e){
		  e = rDrag.fixEvent(e);
		  var oLeft,oTop;
		  //alert(e.clientX +e.clientY );
		  oLeft = e.clientX - rDrag.o.x;
		  oTop = e.clientY - rDrag.o.y;
		  rDrag.o.style.left = oLeft + 'px';
		  rDrag.o.style.top = oTop + 'px';
		 },
		 end:function(e){
		  e = rDrag.fixEvent(e);
		  rDrag.o = document.onmousemove = document.onmouseup = null;
		 },
			fixEvent: function(e){
				if (!e) {
					e = window.event;
					e.target = e.srcElement;
					e.layerX = e.offsetX;
					e.layerY = e.offsetY;
				}
				return e;
			}
		}
		window.onload = function(){
				var obj;
				if(document.getElementById('draggable')==null){
					 obj=document.getElementById('draggable2');
				}else{
					 obj=document.getElementById('draggable');
				}
				if(obj!=null && obj !=undefined){
				
		 			rDrag.init(obj);
				}
		}
		
	  
		   
		   
		   
		 var  colorBefore="";
		function trChange(obj){
			colorBefore  = obj.style.backgroundColor;
			obj.style.backgroundColor="#ACE8FF";
		}
		
		function trChangeBack(obj)
		{
			obj.style.backgroundColor=colorBefore;
		}
		
		
		function doPromote(obj){
			var draggable=document.getElementById(obj);	
			var now =  draggable.style.display;
			if(now == "block" ){
					draggable.style.display ="none";	
			}else if(now=="none" || now==""){
					draggable.style.display ="block";	
			}
		}
		
		
		function doCancel(obj){
				document.getElementById(obj).style.display="none";
		}
	  
	  
	  