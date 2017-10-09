<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/taglib/c.tld" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title><spring:message code="system.config.name" /></title>
	<c:import url="/jsp/common/include.jsp"></c:import>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
	<link href="<c:url value="/js/easyui/themes/login/style.css"/>" rel="stylesheet" type="text/css" id="skin"/>


<!--居中显示start-->
<script type="text/javascript" src="main/libs/js/method/center-plugin.js"></script>
<!--居中显示end-->
<script type="text/javascript" src="<c:url value="/js/common/md5.js"/>"></script>
<script>
	if (top.location != self.location)top.location=self.location;
</script>
<style>
/*提示信息*/	
#cursorMessageDiv {
	position: absolute;
	z-index: 99999;
	border: solid 1px #cc9933;
	background: #ffffcc;
	padding: 2px;
	margin: 0px;
	display: none;
	line-height:150%;
}
/*提示信息*/
</style>
</head>
<body >
	<div class="login_main">
		<div class="login_top">
		</div>
		<div class="login_middle">
			<div class="login_middleleft"></div>
			<div class="login_middlecenter">
					<form id="loginForm" name="loginForm" class="login_form">
					<div class="login_user"><input type="text" value="" name="account" id="account"></div>
					<div class="login_pass"><input type="password" value="" name="password" id="password"></div>
					<div class="clear"></div>
					<div class="login_button">
						<div class="login_button_left"><input id="loginButton" type="button"/></div>
						<div class="login_button_right"><span id="loginMsg" style="display:block;color:red;height:22px;line-height:22px;"></span></div>
						<div class="clear"></div>
					</div>
					</form>
					<div class="login_info" style="display:none;"></div>
					<div class="login_info2" style="display:none;"></div>
			</div>
			<div class="login_middleright"></div>
			<div class="clear"></div>
		</div>
		<div class="login_bottom">
			<div class="login_copyright">&copyright 2014-2020 版权所有 技术支持：</div>
		</div>
	</div>
<script>
	$(function(){
		$(window).keydown(function(event){
			  switch(event.keyCode) {
			  	case 13 : $('#loginButton').click();break;
			  }
		});
		$('#loginButton').click(function(){
			var password = $('#password').val();
			password = MD5(password);
			$('#password').val(password);
			$.ajax({
				url : '<c:url value="/security/login.do"/>'
				,data : $('#loginForm').serialize()
				,type : 'POST'
				,dataType : 'text'
				,success : function(data, textStatus){
					var frameUrl = '<c:url value="/work/frame.do"/>';
					window.location.href = frameUrl;
				}
				,error : function(XMLHttpRequest, textStatus, errorThrown) {
					$("#loginMsg").text("用户名或密码错误！");
				}
			});
		});		 
	})
</script>
</body>
</html>

