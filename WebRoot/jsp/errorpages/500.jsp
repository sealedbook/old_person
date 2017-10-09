<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/view" prefix="view" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<title><spring:message code="system.config.name" /></title>

</head>
<body>


<div class="box1" whiteBg="true" Width="850">
	<div class="msg_icon2"><span></span></div>
	<div class="padding_left50 padding_right15 padding_top20 minHeight_100 font_10" >
		<h3 style="color:red"><c:out value="${exception.message}"/></h3>
        <span style="cursor:hand" onclick="$('#errorMsg').toggle()">查看详细</span>
        <div id="errorMsg" style="display:none">
        	 <p>
           		 <c:out value="${exceptionStack}"/>
       		 </p>
        </div>       
	</div>
</div>

</body>
</html>
