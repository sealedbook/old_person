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
	$(function(){
		$('#dg').datagrid({
			fitColumns:true
			,fixed:true
		});
	});
	function showOperatorName(val,row,index) {
		return val.name;
	}
</script>
</head>
  <body>
 	<fieldset>
 		<legend>老年人基本信息</legend>
 		<table class="tableStyle">
	   		<tr>
	   			<td width="15%" class="right">老人姓名</td>
	   			<td>${oldPerson.name}</td>
	   			<td colspan="2" rowspan="6" align="center">
	   				<img alt="xxx" style="cursor:pointer;" width="150" src="<c:url value="/oldperson/${oldPerson.id}/photo.do"/>">
	   			</td>
	   		</tr>
	   		<tr>
	   			<td class="right">老人性别</td>
	   			<td><code2name:dictionary code="${oldPerson.sex}" parentId="xb"/></td>
	   		</tr>
	   		<tr>
	   			<td class="right">身份证号</td>
	   			<td>${oldPerson.idCard}</td>
	   		</tr>
	   		<tr>
	   			<td class="right">出生日期</td>
	   			<td><fmt:formatDate value="${oldPerson.birthday}" pattern="yyyy-MM-dd"/></td>
	   		</tr>
	   		<tr>
	   			<td class="right">老人年龄</td>
	   			<td>${oldPerson.age}</td>
	   		</tr>
	   		<tr>
	   			<td class="right">所属地区</td>
	   			<td>
	   				${oldPerson.area.name}
	   			</td>
	   		</tr>
	   		<tr>
	   			<td class="right">电话号码</td>
	   			<td>${oldPerson.phoneNumber}</td>
	   			<td class="right">所属民族</td>
	   			<td><code2name:dictionary code="${oldPerson.nationality}" parentId="nationality"/></td>
	   		</tr>
	   		<tr>
	   			<td width="15%" class="right">家庭住址</td>
	   			<td colspan="3">${oldPerson.homeAddress}</td>
	   		</tr>
	   		<tr>
				<td width="15%" class="right">人员状态</td>
				<td><code2name:dictionary code="${oldPerson.type}" parentId="lnrlb"/></td>
	   			<td width="15%" class="right">退休日期</td>
	   			<td><fmt:formatDate value="${oldPerson.txrq}" pattern="yyyy-MM-dd"/></td>
	   		</tr>
	   		<tr>
	   			<td width="15%" class="right">工作单位</td>
	   			<td colspan="3">${oldPerson.workUnit}</td>
	   		</tr>
	   	</table>
 	</fieldset>
   	<fieldset>
   		<legend>老年人认证信息</legend>
   		<table class="tableStyle">
   			<font style="color:red;font-size:12px">无认证信息</font>
   		</table>
   	</fieldset>
  </body>
</html>
