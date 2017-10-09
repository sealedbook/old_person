<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fn.tld" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<c:import url="/jsp/common/include.jsp"></c:import>
<head>
   
</head>
  
  <body>
    <form action="<c:url value="/oldperson/social/security/import.do"/>" method="post" enctype="multipart/form-data">
    	<input class="easyui-filebox" buttonText="请选择文件" style="width:300px" name="file" id="file"/>
    	<br/>
    	<input class="easyui-button" type="submit" value="上传"/>
    </form>
    <c:choose>
    	<c:when test="${0 < fn:length(errorMessage)}">
    		<font color="red">本次导入部分失败,请对照错误修改后重新上传。错误原因如下：</font><br/>
    	</c:when>
    	<c:when test="${status == 'success'}">
    		<font color="green">导入成功</font>
    	</c:when>
    </c:choose>
    <c:forEach items="${errorMessage }" var="errorMsg">
    	<font color="red">${errorMsg }</font><br/>
    </c:forEach>
  </body>
</html>
