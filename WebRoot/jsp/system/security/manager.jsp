<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/taglib/esite.tld" prefix="view" %>
<%@ taglib uri="/WEB-INF/taglib/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglib/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/taglib/fn.tld" prefix="fn" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<c:import url="/jsp/common/include.jsp"></c:import>
<script>
	$(function(){
		$('#selectRole').change(function(){
			var roleId=$('#selectRole').val();
			var url = '<c:url value="/function/"/>' +roleId+ '.do';
			$.post(url,{},function(data){
				$("input").removeAttr("checked");
				$.each(data, function(i, fun){
					var funObj=$('#'+fun.id);
					$(funObj).prop("checked",true);
				});
			},'json');
		});
		$('#saveButton').click(function(){
			var selectRole=$('#selectRole').val();
			if(selectRole == ""){
				$.messager.alert('系统提示','请选择角色.','warning');
				return ;
			}
			url='<c:url value="/function/save.do"/>';
			$.ajax({
				type:"POST",
				url:url,
				dataType:'text',
				data:$('#qxForm').serialize(),
				error:function(xmlHttpRequest){
					$.messager.alert('系统提示',xmlHttpRequest.responseText,'error');
				},
				success:function(data){
					$.messager.alert('系统提示','保存成功.','info');
				}
			});
		});
	});
</script>
<style>
table{
	width:100%;
	font-family:verdana,arial,sans-serif;
	font-size:13px;
	color:#333333;
	border-width:1px;
	border-color:#666666;
	border-collapse:collapse;
}
</style>
</head>
<body>
<form id="qxForm" method="post">
	<table border="1">
		<tr>
			<td align="center" width="15%">角色：</td>
			<td><view:select preOptions="|请选择角色..."  name="roleId" id="selectRole" valueItem="ID" table="SYS_SECURITY_ROLE" displayItem="ROLE_NAME"/></td>
		</tr>
	</table>
 <c:forEach items="${menu}" var="obj">
 <div style="width:100%; background-color:#CCCCCC;">${obj.name}</div>
 <c:if test="${fn:length(obj.subFunction)>0}">
 <table border="1">
 	<tr>
		<td  align="center" width="15%">${obj.name}</td>
		<td>
			<c:forEach items="${obj.subFunction}" var="function">				 			
				<input id="${function.id}" type="checkbox" name="functionId" value="${function.id}" /><label>${function.name}</label>  
			</c:forEach>
		</td>
	</tr>		
 </table>
 </c:if>
<table border="1">	
 <c:forEach items="${obj.subSystemMenu}" var="subM">
 <c:choose>
 	<c:when test="${fn:length(subM.subSystemMenu)==0 }">
 		<tr>
 			<td align="center" width="15%">${subM.name}</td>
 			<td>
			<c:forEach items="${subM.subFunction}" var="function">
				<input id="${function.id}" type="checkbox"  name="functionId" value="${function.id}" /><label>${function.name}</label>
			</c:forEach>
			</td>
 		</tr>
 	</c:when>
 	<c:otherwise>
 		<tr>
	      <td <c:if test="${fn:length(subM.subFunction)>0}">rowspan="2"</c:if> width="15%" align="center" >${subM.name}</td>
		  <td>
		  	  <table border="1">
		  		<c:forEach items="${subM.subSystemMenu}" var="subMu">
					<tr>
						<td align="center" width="15%">${subMu.name}</td>
						<td>
							<table border="1">
								<c:choose>
									<c:when test="${fn:length(subMu.subSystemMenu)<=0}">
										<c:forEach items="${subMu.subFunction}" var="function">
											<input id="${function.id}" type="checkbox"  name="functionId" value="${function.id}" /><label for="${function.id}">${function.name}</label>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<c:forEach items="${subMu.subSystemMenu}" var="menu3">
											<tr>
												<td align="center" width="15%">${menu3.name}</td>
												<td>
													<c:forEach items="${menu3.subFunction}" var="function">
														<input id="${function.id}" type="checkbox"  name="functionId" value="${function.id}" /><label for="${function.id}">${function.name}</label>
													</c:forEach>
												</td>
											</tr>			 			
										</c:forEach>
									</c:otherwise>
								</c:choose>
							</table>
						</td>
					</tr>					 	
				</c:forEach>
			  </table>
		  </td>
	  	</tr>
 	</c:otherwise>
 </c:choose>
 </c:forEach>
</table>
</c:forEach>	
<div align="center" style="padding:5px;">
	<a class="easyui-linkbutton" id="saveButton" >保  存</a>
</div>
</form>
</body>
</html>