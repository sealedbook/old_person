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
$(function(){
	$('#dg').datagrid({});
	
	$('#submitVerify').click(function(){
		$.ajax({
			type:"POST",
			url:'<c:url value="/health/verify.do" />',
			dataType:'text',
			data:$('#healthVerifyForm').serialize(),
			error:function(xmlHttpRequest){
				$.messager.alert('系统提示',xmlHttpRequest.responseText,'error');
			},
			success:function(data){
				$.messager.alert('系统提示','已审核完毕.','info');
				top.closeDialog();
			}
		});
	});
});
</script>
</head>
  <body>
  	 	<div style="text-align:center;padding:5px">
  	 		<font style="font-size: 12px">【备注：${healthFingerprintRemark }】</font>
  	 		<font style="font-size: 12px">【<code2name:dictionary code="${healthInfo.verifyState }" parentId="shzt"/>】</font>
  	 		<c:choose>
  	 			<c:when test="${!empty(nextHealthId)}">
  	 				<a href="<c:url value="/health/verify/next/${healthInfo.id}.do?healthFingerprintId=${healthFingerprintId }&updateVerifyState=1&verifyState=${verifyState}&oldPersonId=${oldPersonId }&operatorId=${operatorId }"/>" class="easyui-linkbutton" iconCls="icon-tick" >通过,并显示下一个</a>
					<a href="<c:url value="/health/verify/next/${healthInfo.id}.do?healthFingerprintId=${healthFingerprintId }&updateVerifyState=0&verifyState=${verifyState}&oldPersonId=${oldPersonId }&operatorId=${operatorId }"/>" class="easyui-linkbutton" iconCls="icon-joystick_delete" ><font color="red">不通过,并显示下一个</font></a>
					<a href="<c:url value="/health/verify/next/${healthInfo.id}.do?verifyState=${verifyState}&oldPersonId=${oldPersonId }&operatorId=${operatorId }"/>" class="easyui-linkbutton" iconCls="icon-vcard" >下一个</a>
  	 			</c:when>
  	 			<c:otherwise>
  	 				<a onclick=verifyOldPerson("<c:url value="/health/verify.do?healthId=${healthInfo.id }&healthFingerprintId=${healthFingerprintId }&verifyState=1"/>") class="easyui-linkbutton" iconCls="icon-tick" >通过</a>
  	 				<a data-options="disabled:true" class="easyui-linkbutton" iconCls="icon-tick" >通过,并显示下一个</a>
					<a data-options="disabled:true" class="easyui-linkbutton" iconCls="icon-joystick_delete"><font color="red">不通过,并显示下一个</font></a>
					<a onclick=verifyOldPerson("<c:url value="/health/verify.do?healthId=${healthInfo.id }&healthFingerprintId=${healthFingerprintId }&verifyState=0"/>") class="easyui-linkbutton" iconCls="icon-joystick_delete"><font color="red">不通过</font></a>
					<a data-options="disabled:true" class="easyui-linkbutton" iconCls="icon-vcard" >下一个</a>
  	 			</c:otherwise>
  	 		</c:choose>
		</div>
 		<table align="center">
 			<tr>
 				<td>
 					<fieldset>
 						<legend>指纹与正面照</legend>
 						<img style="cursor:pointer;" width="150" src="<c:url value="/oldperson/fingerprint/photo.do?oldPersonId=${healthInfo.oldPerson.id}"/>">
 						<img style="cursor:pointer;" width="150" src="<c:url value="/health/fingerprin/photo.do?id=${healthFingerprintId}"/>">
 						<img alt="" style="cursor:pointer;" width="150" src="<c:url value="/oldperson/${healthInfo.oldPerson.id}/photo.do"/>">
 						<img style="cursor:pointer;" width="150" src="<c:url value="/health/face/photo.do?id=${healthPhotoCollection[0].id}"/>">
 					</fieldset>
 				</td>
 			</tr>
 			<tr>
 				<td>
	 				<fieldset>
						<legend>侧面照片</legend>
						<img style="cursor:pointer;" width="150" src="<c:url value="/health/face/photo.do?id=${firstHealthPhotoCollection[1].id}"/>">
						<img style="cursor:pointer;" width="150" src="<c:url value="/health/face/photo.do?id=${healthPhotoCollection[1].id}"/>">
						<img style="cursor:pointer;" width="150" src="<c:url value="/health/face/photo.do?id=${firstHealthPhotoCollection[2].id}"/>">
						<img style="cursor:pointer;" width="150" src="<c:url value="/health/face/photo.do?id=${healthPhotoCollection[2].id}"/>">
					</fieldset>
 				</td>
 			</tr>
 			<tr>
 				<td>
 					<fieldset>
 						<legend>其他照片-${oldPersonName }-${oldPersonIdCard }</legend>
 						<img style="cursor:pointer;" width="150" src="<c:url value="/health/face/photo.do?id=${firstHealthPhotoCollection[3].id}"/>">
 						<img style="cursor:pointer;" width="150" src="<c:url value="/health/face/photo.do?id=${healthPhotoCollection[3].id}"/>">
 						<img style="cursor:pointer;" width="150" src="<c:url value="/health/face/photo.do?id=${firstHealthPhotoCollection[4].id}"/>">
 						<img style="cursor:pointer;" width="150" src="<c:url value="/health/face/photo.do?id=${healthPhotoCollection[4].id}"/>">
 					</fieldset>
 				</td>
 			</tr>
 		</table>
 		<div style="text-align:center;padding:5px">
 			<c:choose>
  	 			<c:when test="${!empty(nextHealthId)}">
  	 				<a href="<c:url value="/health/verify/next/${healthInfo.id}.do?healthFingerprintId=${healthFingerprintId }&updateVerifyState=1&verifyState=${verifyState}&oldPersonId=${oldPersonId }&operatorId=${operatorId }"/>" class="easyui-linkbutton" iconCls="icon-tick" >通过,并显示下一个</a>
					<a href="<c:url value="/health/verify/next/${healthInfo.id}.do?healthFingerprintId=${healthFingerprintId }&updateVerifyState=0&verifyState=${verifyState}&oldPersonId=${oldPersonId }&operatorId=${operatorId }"/>" class="easyui-linkbutton" iconCls="icon-joystick_delete" ><font color="red">不通过,并显示下一个</font></a>
					<a href="<c:url value="/health/verify/next/${healthInfo.id}.do?verifyState=${verifyState}&oldPersonId=${oldPersonId }&operatorId=${operatorId }"/>" class="easyui-linkbutton" iconCls="icon-vcard" >下一个</a>
  	 			</c:when>
  	 			<c:otherwise>
  	 				<a onclick=verifyOldPerson("<c:url value="/health/verify.do?healthId=${healthInfo.id }&healthFingerprintId=${healthFingerprintId }&verifyState=1"/>") class="easyui-linkbutton" iconCls="icon-tick" >通过</a>
  	 				<a data-options="disabled:true" class="easyui-linkbutton" iconCls="icon-tick" >通过,并显示下一个</a>
					<a data-options="disabled:true" class="easyui-linkbutton" iconCls="icon-joystick_delete"><font color="red">不通过,并显示下一个</font></a>
					<a onclick=verifyOldPerson("<c:url value="/health/verify.do?healthId=${healthInfo.id }&healthFingerprintId=${healthFingerprintId }&verifyState=0"/>") class="easyui-linkbutton" iconCls="icon-joystick_delete"><font color="red">不通过</font></a>
					<a data-options="disabled:true" class="easyui-linkbutton" iconCls="icon-vcard" >下一个</a>
  	 			</c:otherwise>
  	 		</c:choose>
		</div>
 		<!-- 
 		<c:forEach items="${healthPhotoCollection }" var="healthPhoto" varStatus="idx">
 			<div style="position: relative; height: 250px;float: left;margin-left: 2px">
				<img height="250" alt="" src="<c:url value="/health/face/photo.do?id=${healthPhoto.id}"/>">
				<span style="position: absolute; top:100%; left: 0;">老年人${healthPhoto.photoPositionName }照片</span>
			</div>
	   	</c:forEach>
 		 -->
  </body>
</html>
