<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/view" prefix="view" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title><spring:message code="system.config.name" /></title>
</head>
<body>


<div class="box1 center" whiteBg="true" width="450">
	<div class="msg_icon4"><span style="color:red">401 禁止访问</span></div>
	<div class="padding_left50 padding_right15 padding_top20 minHeight_100 font_14" >
		您没有访问此功能的权限,如果您需要此功能请联系管理员开通。
		<br/>
		${errorMessage }
	</div>
</div>

</body>
</html>
