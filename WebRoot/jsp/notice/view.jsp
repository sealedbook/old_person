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
	$('#dg').datagrid({});
});
function formatReceiveDateTime(val) {
	if(null == val) {
		return '<font color="red">未签收</font>';
	} else {
		return new Date(val).format('yyyy-MM-dd HH:mm');
	}
}
</script>
</head>
  <body>
 	<table class="tableStyle">
   		<tr>
   			<td class="right">通知时间</td>
   			<td><fmt:formatDate value="${noticeEntity.submitDateTime }" pattern="yyyy-MM-dd HH:mm" /></td>
   			<td class="right">提交人</td>
   			<td><code2name:user code="${noticeEntity.submitUserId }"/></td>
   		</tr>
   		<tr>
   			<td class="right">通知内容</td>
   			<td colspan="3"><textarea rows="" cols="60" readonly="readonly">${noticeEntity.content }</textarea></td>
   		</tr>
   	</table>
	<table id="dg" title="通知通告签收情况【${noticeEntity.receiveStatus }】"  data-options="
				rownumbers:true,
				singleSelect:false,
				autoRowHeight:false,
				autoRowWeight:true,
				pagination:true,
				pageSize:10,
				url:'<c:url value="/notice/${noticeEntity.id }/receive/list.do" />'">
		<thead>
			<tr>
				<th field="receiveUserId" formatter="esite.convertOperatorCode2Name"  align="center" width="50%">签收人</th>
				<th field="receiveDateTime" formatter="formatReceiveDateTime" align="center" width="25%">签收时间</th>
				<th field="receiveDeviceNumber" align="center" width="24%" >签收设备号</th>
			</tr>
		</thead>
	</table>
  </body>
</html>
