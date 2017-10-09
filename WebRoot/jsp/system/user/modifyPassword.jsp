<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/taglib/c.tld" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

<head>
<c:import url="/jsp/common/include.jsp"></c:import>
<script type="text/javascript" src="<c:url value="/js/common/md5.js"/>"></script>
<script type="text/javascript">
$(function(){
	$('#userUpdateButton').click(function(){
		if($('#userForm').form('validate') == false){
			return false;
		}else{
			var oldPswd = $('#oldPassword').textbox('getText');
			$('#oldPassword').textbox('setValue',MD5(oldPswd));
			$('#oldPassword').textbox('setText',MD5(oldPswd));
			
			var newPswd = $('#newPassword').textbox('getText');
			$('#newPassword').textbox('setValue',MD5(newPswd));
			$('#newPassword').textbox('setText',MD5(newPswd));
			$.ajax({
				type:"POST",
				url:'<c:url value="/security/user/modifyPassword.do" />',
				dataType:'text',
				data:$('#userForm').serialize(),
				error:function(xmlHttpRequest){
					$.messager.alert('系统提示',xmlHttpRequest.responseText,'error');
				},
				success:function(data){
					top.$.messager.alert('系统提示','修改成功.','info');
					top.closeDialog();
				}
			});
		}
	});
});

</script>
</head>
  <body>
	    <form id="userForm" method="post">
	    	<input type="hidden" name="id" value="${user.id}">
	    	<table class="tableStyle">
				<tr>
					<td width="20%" class="right">旧密码</td>
					<td width="30%"><input type="password" data-options="required:true,validType:['blankSpace','length[0,32]']" class="easyui-textbox" name="oldPassword" id="oldPassword"/></td>
				</tr>
				<tr>
					<td width="20%" class="right">新密码</td>
					<td width="30%"><input type="password" data-options="required:true,validType:['blankSpace','length[0,32]']" class="easyui-textbox" name="newPassword" id="newPassword"/></td>
				</tr>
				<tr>
					<td width="20%" class="right">新密码确认</td>
					<td width="30%"><input type="password" data-options="required:true,validType:['blankSpace','length[0,32]']" class="easyui-textbox" id="oldPassword"/></td>
				</tr>
	    	</table>
	    </form>
	    <div style="text-align:center;padding:5px">
	    	<a href="javascript:void(0)" class="easyui-linkbutton" id="userUpdateButton">提 交</a>
	    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="top.closeDialog();">关 闭</a>
	    </div>
  </body>
</html>
