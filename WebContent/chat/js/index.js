var wid = 1;
var array = new Array();//存放聊天窗口数组
var msgs = new Array();//存放用户未读消息
var content_def_height = document.getElementById("chat_show_"+wid).scrollHeight;//获取显示框的初始高度

//监听显示框变化的高度
function scrollDiv() {
	document.getElementById("chat_show_"+wid).scrollTop = document.getElementById("chat_show_"+wid).scrollHeight - content_def_height;
}

//初始化函数
function init() {
	// 这个方法用来启动该页面的ReverseAjax功能
	dwr.engine.setActiveReverseAjax(true);
	// 设置在页面关闭时，通知服务端销毁会话
	dwr.engine.setNotifyServerOnPageUnload(true);
	// 设置错误时不弹出alert
	dwr.engine.setErrorHandler(function() {
		console.log("system error");
	});
	// 加载初始化用户信息
	manager.loadPage();
	//初始化设置默认聊天窗口
	changeChatWin(wid);
}

//显示用户列表
function getUsers(users) {
	$("#show_users").html("");
	for (var i = 0; i < users.length; i++) {
		if(users[i].id != $("#me_uid").val()) {
			$("#show_users").append("<li id=\"list_"+users[i].id+"\" " +
					"ondblclick=\"createChatWin('"+users[i].id+"','"+users[i].name+"')\">" + 
					users[i].name  + "<b class='chatNum' style='display:none'></b></li>");	
			if(countCacheMsg(users[i].id) > 0) {
				$("#list_"+users[i].id).find("b").html(countCacheMsg(users[i].id)).css("display","block");
			}
		}else {
			$("#show_users").append("<li id=\"list_"+users[i].id+"\">" + 
					"自己_" + users[i].name.substring(3)  + "<b class='chatNum' style='display:none'></b></li>");
		}
	}
}

//设置个人信息
function chatUser(obj) {
	$("#me_uid").val(obj.id);
	$("#me_name").val(obj.name);
}

//群聊设置
function groupChat() {
	var group = $("#group").is(":checked") == true ? 1 : 0;
	manager.setGroupChat(group);
	createChatWin("-1","群聊窗口");
}

//显示聊天内容
function content(message) {
	console.log(message);
	//titleMsg.show();//标题提示新消息
	//如果没有创建聊天窗口
	var _arr_id = message.msgType == 0 ? message.senderId : "-1";//获取聊天类型，私聊还是群聊
	if(!checkArray(_arr_id)) {//检测是否存在窗口数组中，没有则在线用户列表提示
		if(array.length == 0) {//默认第一个打开聊天窗口
			createChatWin(message.senderId, message.sender);
			appendHtml(message.senderId, message.sender, message.sendTime, message.content);
		}else {
			msgs.push(message);//保存未读消息
			if(message.senderId != $("#me_uid").val()) {	
				setTimeout(function() {
					$("#list_"+message.senderId).find("b").html(countCacheMsg(message.senderId)).css("display","block");
				}, 500);
			}
		}
	} else {//在当前窗口
		if(message.content != "") {
			if(!$("#chat_"+message.senderId).is(":visible")) {
				var visCount = parseInt($("#tab_"+message.senderId).find(".chatNum").html());
				$("#tab_"+message.senderId).find(".chatNum").html(visCount + 1).css("display","block");
			}
			appendHtml(_arr_id, message.sender, message.sendTime, message.content);
			scrollDiv();
		}
	} 
}

//统计缓存消息个数
function countCacheMsg(uid) {
	var j = 0;
	for (var i = 0; i < msgs.length; i++) {
		if(msgs[i].senderId == uid) {
			j++;
		}
	}
	return j;
}
//追加HTML
function appendHtml(uid, name, time, msg) {
	$("#chat_show_"+uid).append(
			"<div class='inner'>" + "<p class='p1'>" + name
					+ "&nbsp;&nbsp;&nbsp;&nbsp;" + time + "</p>"
					+ "<p class='p2'>" + replace_em(msg) + "</p>" + "</div>");
}

//创建聊天窗口
function createChatWin(uid,name) {
	$("#list_"+uid).css("background","");
	wid = uid;//改变全局变量
	if(!checkArray(uid) && $("#me_uid").val() != uid) {//如果存在则不创建
		array.push(uid);
		//动态添加聊天内容窗口和文字输入窗口
		$("#myChat").append("<div id=\"chat_"+wid+"\" class=\"window\">"
				+"<div class=\"header\">"
				+"<div class=\"forName\">"+name+"</div>"
				+"</div>"
				+"<div class=\"content1\">"
				+"<div class=\"js_show\" id=\"chat_show_"+wid+"\"></div>"
				+"<div class=\"tool\">"
				+"<div class=\"js_emotion fl\">表情</div>"
				+"<div class=\"history fr\">消息记录</div>"
				+"</div>"
				+"<textarea class=\"js_saytext\" id=\"chat_saytext_"+wid+"\" name=\"saytext\"></textarea>"
				+"<div class=\"btn\">"
				+"<div class=\"js_subBtn fr\">发送</div>"
				+"<div class=\"button1 fr\" onclick=\"colseChat('"+wid+"')\">关闭</div>"
				+"<div class=\"js_noticeTip\">发送内容不能为空</div>"
				+"<div class=\"clear\"></div>"
				+"</div>"
				+"</div>"
				+"</div>");
		//左侧添加tab选卡，设置相关事件
		$(".tabHost ul").append("<li onmouseover=\"turnOffWinShow('tab_"+wid+"')\" onmouseout=\"turnOffWinHide('tab_"+wid+"')\"  id=\"tab_"+wid+"\">" +
				"<div onclick=\"changeChatWin('"+wid+"')\">"+name+"</div><b class='turnoff' onclick=\"colseChat('"+wid+"')\" style='display:none;'></b>" +
						"<b class='chatNum' style='display:none'>0</b></li>");
	}
	settingChatWin(uid);//设置聊天相关属性
	changeChatWin(uid);//改变聊天窗口
}

//设置聊天窗口属性
function settingChatWin(uid) {
	wid = uid;
	$("#chat_saytext_"+wid).focus();//输入框获取焦点
	$("#chat_"+wid+" .js_emotion").qqFace({
		id : 'facebox',  assign:"chat_saytext_"+wid,  path:'chat/arclist/'	//表情存放的路径
	});
	
	$("#chat_"+wid+" .js_subBtn").click(function() {//发送按钮
		var strMsg = $("#chat_saytext_"+wid).val();
		if(strMsg != "") {
			if(strMsg.length > 490) {
				alert("输入字符超长，请分开发送");
			}else {	
				var message = new Message();
				var sendTime = getNowDate();
				message.content = strMsg;
				message.senderId = $("#me_uid").val();
				message.msgType = 0;
				if(wid == "-1") 
					message.msgType = 1;
				message.sendTime = sendTime;
				message.sender =$("#me_name").val(); 
				var sender = new Array();
				sender[0] = wid;
				manager.send(sender, message);
				appendHtml(wid, "你", getNowDate(), strMsg);
				$("#chat_saytext_"+wid).val("");
				$("#chat_saytext_"+wid).focus();
				scrollDiv();
			}
		}else {//如果输入内容为空，弹出提示
			$("#chat_"+wid).find(".js_noticeTip").fadeIn(600);
			setTimeout(function(){
				$("#chat_"+wid).find(".js_noticeTip").fadeOut(600);
			}, 1000);
			$("#chat_saytext_"+wid).focus();
		}
	});
	
	//textArea调用取消回车换行函数
    $("#chat_saytext_"+wid).keydown(function(event) {
		checkEnter(event);
	});
}

//切换聊天窗口
function changeChatWin(uid) {
	wid = uid;
	$("#tab_"+uid).find(".chatNum").html(0).css("display","none");
	$("#tab_"+uid).addClass('tabHostClick').siblings('.tabHost li').removeClass('tabHostClick');
	$("#chat_"+uid).show().siblings('.window').hide();
	if(msgs.length > 0) {
		handleCacheMsg(uid);
		$("#list_"+uid).find("b").html("").css("display","none");
	}
}

//处理未读消息显示
function handleCacheMsg(uid) {
	for (var i = 0; i < msgs.length; i++) {
		if(msgs[i].senderId == uid) {
			appendHtml(msgs[i].senderId, msgs[i].sender, msgs[i].sendTime, msgs[i].content);
			msgs.splice(i, 1);
			handleCacheMsg(uid);
		}
	}
}
//替换文字中表情符
function replace_em(str) {
	str = str.replace(/\</g,'&lt;');
	str = str.replace(/\>/g,'&gt;');
	str = str.replace(/\n/g,'<br/>');
	str = str.replace(/\[em_([0-9]*)\]/g,'<img src="chat/arclist/$1.gif" border="0" />');
	return str;
}

//回车事件star
document.onkeydown = function() {
	var iKeyCode = -1;
	if (arguments[0]) {
		iKeyCode = arguments[0].which;
 	} else {
        iKeyCode = event.keyCode;
    }
    if (iKeyCode == 13) {
       $("#chat_"+wid+" .js_subBtn").click();
       $("#chat_saytext_"+wid).val("");
    }
};

//消除textArea回车换行函数===========================
function checkEnter(e) {
	var et = e || window.event;
	var keycode = et.charCode || et.keyCode;
	if (keycode == 13) {
		if (window.event)
			window.event.returnValue = false;
		else
			e.preventDefault();// for firefox
	}
}

//判断用户是否包含在列表中
function checkArray(sid) {
	for(var i = 0; i< array.length; i++) {
		if(array[i] == sid)
			return true;
	}
	return false;
}

//检测定时器
function checkTimer(timer) {
	for(var i = 0; i< timers.length; i++) {
		if(timers[i] == timer)
			return true;
	}
	return false;
}
//关闭聊天窗口
function colseChat(wid) {
	if($(".window").length > 1) {	
		$("#chat_"+wid).remove();
		$("#tab_"+wid).remove();
		for(var i = 0; i< array.length; i++) {
			if(array[i] == wid) {
				array.splice(i, 1);
				manager.closeChat($("#me_uid").val(), wid);
				break;
			}
		}
		manager.setGroupChat(0);//设置关闭群聊
		$("#group").removeAttr("checked");
		if(array.length > 0) {
			wid = array[0];
			changeChatWin(array[0]);
		}else {
			changeChatWin(1);
		}
	}
}

//获取当前时间
function getNowDate() {
	var date = new Date();
	var dStr = date.getFullYear() + "/" + (date.getMonth() + 1) + "/" + date.getDate()
			+ " " + date.getHours() +":" + date.getMinutes() + ":" + date.getSeconds();
	return dStr;
}

//window浏览器窗口关闭事件关闭连接
$(window).bind('beforeunload',function() {
	manager.removeUser($("#me_uid").val(), "");
});
//window浏览器窗口关闭事件关闭连接
$(window).bind('onunload',function() {
	manager.removeUser($("#me_uid").val(), "");
});

//清除消息提示
function bindMsg() {
	 document.onclick = function(){  
         titleMsg.clear();   
     }; 
}

//标签关闭箭头显示
function turnOffWinShow(id){
	var sbtitle=document.getElementById(id).getElementsByTagName("b");
	sbtitle[0].style.display = "block";
}

//标签关闭箭头隐藏
function turnOffWinHide(id){
	var sbtitle=document.getElementById(id).getElementsByTagName("b");
	sbtitle[0].style.display = "none";
}