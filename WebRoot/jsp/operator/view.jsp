<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/taglib/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglib/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglib/esite.tld" prefix="view"%>
<%@ taglib uri="/WEB-INF/taglib/code2name.tld" prefix="code2name"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<c:import url="/jsp/common/include.jsp"></c:import>
<head>
<title><spring:message code="system.config.name" /></title>
<script type="text/javascript">
	$(function(){
		$('#dg').datagrid({
		});
	});
	function showOperatorName(val,row,index) {
		return val.name;
	}
	$(function(){
		$('#uploadOperatorPhoto').click(function(){
			if(false == $('#uploadPhotoForm').form('validate')) {
				return false;
			}
			uploadPhotoForm.submit();
		});
	});

</script>
</head>
  <body>
 	<fieldset>
 		<legend>基本信息</legend>
 		<table class="tableStyle">
	   		<tr>
	   			<td width="15%" class="right">操作员</td>
	   			<td>${operator.name}</td>
	   			<td colspan="2" rowspan="5" align="center">
	   				<img alt="无照片" style="cursor:pointer;" width="150" src="<c:url value="/operator/${operator.id}/photo.do"/>">
	   				<view:security url="/operator/photo/upload.do">
	   					<form id="uploadPhotoForm" name="uploadPhotoForm" action="<c:url value="/operator/photo/upload.do"/>" method="post" enctype="multipart/form-data">
	   						<input type="hidden" name="id" value="${operator.id}"/>
	   						<input class="easyui-filebox" name="photo" buttonText="请选择" data-options="prompt:'选择照片',required:true" validType="fileType[['jpg']]"/>
	   						<a class="easyui-linkbutton" id="uploadOperatorPhoto">上传</a>
	   					</form>
	   				</view:security>
	   			</td>
	   		</tr>
	   		<tr>
	   			<td class="right">性别</td>
	   			<td><code2name:dictionary code="${operator.sex}" parentId="xb"/></td>
	   		</tr>
	   		<tr>
	   			<td class="right">身份证号</td>
	   			<td>${operator.idCard}</td>
	   		</tr>
	   		<tr>
	   			<td class="right">出生日期</td>
	   			<td><fmt:formatDate value="${operator.birthday}" pattern="yyyy-MM-dd"/></td>
	   		</tr>
	   		<tr>
	   			<td class="right">年龄</td>
	   			<td>${operator.age}</td>
	   		</tr>
	   		<tr>
	   			<td class="right">电话号码</td>
	   			<td>${operator.phoneNumber}</td>
	   			<td class="right">所属民族</td>
	   			<td><code2name:dictionary code="${operator.nationality}" parentId="nationality"/></td>
	   		</tr>
	   		<tr>
	   			<td width="15%" class="right">家庭住址</td>
	   			<td colspan="3">${operator.homeAddress}</td>
	   		</tr>
	   	</table>
 	</fieldset>
   	<fieldset>
   		<legend>其他信息</legend>
   		<table class="tableStyle">
   			<tr>
	   			<td class="right">管理地区</td>
	   			<td>${organizeName}
	   			</td>
	   		</tr>
   		</table>
   	</fieldset>
  </body>
</html>
