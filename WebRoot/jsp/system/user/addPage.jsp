<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/taglib/c.tld" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<c:import url="/jsp/common/include.jsp"></c:import>
<script type="text/javascript">
$(function(){
	$('#userCreateButton').click(function(){
		$.ajax({
			type:"POST",
			url:'<c:url value="/user/add.do" />',
			dataType:'text',
			data:$('#userForm').serialize()
			,beforeSend : function(XMLHttpRequest) {
				return $('#userForm').form('validate');
			}
			,error:function(xmlHttpRequest){
				$.messager.alert('系统提示',xmlHttpRequest.responseText,'error');
			},
			success:function(data){
				$.messager.alert('系统提示','保存成功.','info');
			}
		});
	});
});

</script>
<head>
   
</head>
  <body>
	    <form id="userForm" method="post">
	    	<table class="tableStyle">
				<tr>
					<td width="20%" class="right">登录账户：</td>
					<td width="30%"><input data-options="required:true,validType:['blankSpace','length[0,10]']" class="easyui-textbox" name="account" id="account" /></td>
					<td width="20%" class="right">登录密码：</td>
					<td width="30%"><input data-options="required:true,validType:['blankSpace','length[0,20]']" class="easyui-textbox" type="password" name="password" id="password" style="width: 155px;"/></td>
				</tr>
				<tr>
					<td class="right">用户姓名：</td>
					<td><input data-options="required:true,validType:['blankSpace','length[0,5]']" class="easyui-textbox" name="showName" id="showName" /></td>
					<td class="right">身份证号：</td>
					<td><input data-options="required:true,validType:['blankSpace','checkIdCard[]']" class="easyui-textbox" name="idCard" id="idCard" /></td>
				</tr>
				<tr>
					<td class="right" >所属角色：</td>
					<td colspan="3">
						<c:forEach items="${roleCollection }" var="role">
							<input type="checkbox" value="${role.id }" name="roleId"/>${role.name }
						</c:forEach>
					</td>
				</tr>
	    	</table>
	    </form>
	    <div style="text-align:center;padding:5px">
	    	<a href="javascript:void(0)" class="easyui-linkbutton" id="userCreateButton">提 交</a>
	    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="top.closeDialog();">关 闭</a>
	    </div>
  </body>
</html>
