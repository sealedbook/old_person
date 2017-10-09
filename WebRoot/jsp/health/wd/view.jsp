<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/taglib/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglib/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglib/code2name.tld" prefix="code2name"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<c:import url="/jsp/common/include.jsp"></c:import>
<head>
<title><spring:message code="system.config.name" /></title>
<script type="text/javascript">
function verifyOldPerson(url) {
	$.get(url,function(){
		top.closeDialog();
	});
}
</script>
</head>
  <body>
  	 	<div style="text-align:center;padding:5px">
  	 		<font style="font-size: 12px">【<code2name:dictionary code="${oldPersonWdVerifyInfo.verifyState }" parentId="shzt"/>】</font>
  	 		<c:choose>
  	 			<c:when test="${!empty(nextVerifyId)}">
  	 				<a href="<c:url value="/health/wd/verify/next/${oldPersonWdVerifyInfo.id}.do?verifyState=1"/>" class="easyui-linkbutton" iconCls="icon-tick" >通过,并显示下一个</a>
					<a href="<c:url value="/health/wd/verify/next/${oldPersonWdVerifyInfo.id}.do?verifyState=0"/>" class="easyui-linkbutton" iconCls="icon-joystick_delete"><font color="red">不通过,并显示下一个</font></a>
					<a href="<c:url value="/health/wd/verify/next/${oldPersonWdVerifyInfo.id}.do"/>" class="easyui-linkbutton" iconCls="icon-vcard" >下一个</a>
  	 			</c:when>
  	 			<c:otherwise>
  	 				<a onclick=verifyOldPerson("<c:url value="/health/wd/verify.do?verifyId=${oldPersonWdVerifyInfo.id }&verifyState=1"/>") class="easyui-linkbutton" iconCls="icon-tick" >通过</a>
  	 				<a data-options="disabled:true" class="easyui-linkbutton" iconCls="icon-tick" >通过,并显示下一个</a>
					<a data-options="disabled:true" class="easyui-linkbutton" iconCls="icon-joystick_delete"><font color="red">不通过,并显示下一个</font></a>
					<a onclick=verifyOldPerson("<c:url value="/health/wd/verify.do?verifyId=${oldPersonWdVerifyInfo.id }&verifyState=0"/>") class="easyui-linkbutton" iconCls="icon-joystick_delete"><font color="red">不通过</font></a>
					<a data-options="disabled:true" class="easyui-linkbutton" iconCls="icon-vcard" >下一个</a>
  	 			</c:otherwise>
  	 		</c:choose>
		</div>
 		<table align="center">
 			<tr>
 				<td>
 					<fieldset>
 						<legend>正面照片</legend>
 						<img alt="" style="cursor:pointer;" width="150" src="<c:url value="/oldperson/${oldPerson.id}/photo.do"/>">
 						<img style="cursor:pointer;" width="150" src="<c:url value="/health/wd/face/photo.do?id=${verifyPhotoCollection[0].id}"/>">
 					</fieldset>
 				</td>
 			</tr>
 			<tr>
 				<td colspan="2">
 					<fieldset>
 						<legend>侧面照片</legend>
 						<img style="cursor:pointer;" width="150" src="<c:url value="/health/wd/face/photo.do?id=${firstVerifyPhotoCollection[1].id}"/>">
 						<img style="cursor:pointer;" width="150" src="<c:url value="/health/wd/face/photo.do?id=${verifyPhotoCollection[1].id}"/>">
 						<img style="cursor:pointer;" width="150" src="<c:url value="/health/wd/face/photo.do?id=${firstVerifyPhotoCollection[2].id}"/>">
 						<img style="cursor:pointer;" width="150" src="<c:url value="/health/wd/face/photo.do?id=${verifyPhotoCollection[2].id}"/>">
 					</fieldset>
 				</td>
 			</tr>
 			<tr>
 				<td colspan="2">
 					<fieldset>
 						<legend>其他照片</legend>
 						<img style="cursor:pointer;" width="150" src="<c:url value="/health/wd/face/photo.do?id=${firstVerifyPhotoCollection[3].id}"/>">
 						<img style="cursor:pointer;" width="150" src="<c:url value="/health/wd/face/photo.do?id=${verifyPhotoCollection[3].id}"/>">
 						<img style="cursor:pointer;" width="150" src="<c:url value="/health/wd/face/photo.do?id=${firstVerifyPhotoCollection[4].id}"/>">
 						<img style="cursor:pointer;" width="150" src="<c:url value="/health/wd/face/photo.do?id=${verifyPhotoCollection[4].id}"/>">
 					</fieldset>
 				</td>
 			</tr>
 		</table>
 		<div style="text-align:center;padding:5px">
 			<c:choose>
  	 			<c:when test="${!empty(nextVerifyId)}">
  	 				<a href="<c:url value="/health/wd/verify/next/${oldPersonWdVerifyInfo.id}.do?verifyState=1"/>" class="easyui-linkbutton" iconCls="icon-tick" >通过,并显示下一个</a>
					<a href="<c:url value="/health/wd/verify/next/${oldPersonWdVerifyInfo.id}.do?verifyState=0"/>" class="easyui-linkbutton" iconCls="icon-joystick_delete"><font color="red">不通过,并显示下一个</font></a>
					<a href="<c:url value="/health/wd/verify/next/${oldPersonWdVerifyInfo.id}.do"/>" class="easyui-linkbutton" iconCls="icon-vcard" >下一个</a>
  	 			</c:when>
  	 			<c:otherwise>
  	 				<a onclick=verifyOldPerson("<c:url value="/health/wd/verify.do?verifyId=${oldPersonWdVerifyInfo.id }&verifyState=1"/>") class="easyui-linkbutton" iconCls="icon-tick" >通过</a>
  	 				<a data-options="disabled:true" class="easyui-linkbutton" iconCls="icon-tick" >通过,并显示下一个</a>
					<a data-options="disabled:true" class="easyui-linkbutton" iconCls="icon-joystick_delete"><font color="red">不通过,并显示下一个</font></a>
					<a onclick=verifyOldPerson("<c:url value="/health/wd/verify.do?verifyId=${oldPersonWdVerifyInfo.id }&verifyState=0"/>") class="easyui-linkbutton" iconCls="icon-joystick_delete"><font color="red">不通过</font></a>
					<a data-options="disabled:true" class="easyui-linkbutton" iconCls="icon-vcard" >下一个</a>
  	 			</c:otherwise>
  	 		</c:choose>
		</div>
  </body>
</html>
