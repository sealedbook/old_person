<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/taglib/c.tld" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<c:import url="/jsp/common/include.jsp"></c:import>
  </head>
  
  <body>
  	<table  style="font-size: 12px;">
  		<tr><td>当前用户：<span style="color:#F27F13;">${customer.user.showName }</span></td></tr>
  		<tr><td>管理辖区：<span style="color:#F27F13;"></span></td></tr>
  		<tr><td>本次登录时间：<span style="color:#F27F13;">${loginTime }</span></td></tr>
  		<tr><td>登录IP：<span style="color:#F27F13;">${customer.ip }</span></td></tr>
  	</table>
  </body>
</html>
