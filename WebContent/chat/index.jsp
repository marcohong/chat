<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String root = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
	<title>web chat</title>
	<link rel="stylesheet" href="<%=root %>/chat/css/chat_css.css"/>
	<link rel="stylesheet" href="<%=root %>/chat/css/index.css"/>
	<script type='text/javascript' src='<%=root%>/chat/js/title_msg.js'></script>
	<script type='text/javascript' src='<%=root%>/dwr/engine.js'></script>
	<script type='text/javascript' src='<%=root%>/dwr/interface/manager.js'></script>
</head>
<body onload="init();bindMsg();">
	<div id="myChat">
		<input type="text" id="me_uid" style="display: none"/>
		<input type="text" id="me_name" style="display: none"/>
		<div class="tabHost">
			<ul>
				<!-- <li class="tabHostClick">渣渣买家1号</li> -->
			</ul>
		</div>
		<div class="aside">
			<div class="userName">在线用户</div>
			<ul id="show_users">
			</ul>
		</div>
		<div id="chat_1" class="window">
			<div class="header">
				<div class="forName">暂无聊天对象</div>
			</div>
			<div class="content1">
				<div class="js_show" id="chat_show_1"></div>
				<div class="tool">
					<div class="js_emotion fl">表情</div>
					<div class="history fr"><input type="checkbox" id="group" name="group" onclick="groupChat()"/><label for="group">开启群聊</label></div>
				</div>
				<textarea class="js_saytext" id="chat_saytext_1" name="saytext"></textarea>
				<div class="btn">
					<div class="js_subBtn fr">发送</div>
					<div class="button1 fr">关闭</div>
					<div class="js_noticeTip">发送内容不能为空</div>
					<div class="clear"></div>
				</div>
			</div>
		</div>
	</div>
<script type="text/javascript" src="<%=root %>/chat/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=root %>/chat/js/jquery.qqFace.js"></script>
<script type="text/javascript" src="<%=root %>/chat/js/index.js"></script>
</body>
</html>


