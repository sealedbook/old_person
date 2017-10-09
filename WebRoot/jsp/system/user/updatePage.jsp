<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/taglib/c.tld" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<c:import url="/jsp/common/include.jsp"></c:import>
<script type="text/javascript">
$(function(){
	$('#userUpdateButton').click(function(){
		if(check() == false){
			return false;
		}else{
			$.ajax({
				type:"POST",
				url:'<c:url value="/user/update.do" />',
				dataType:'text',
				data:$('#userForm').serialize(),
				error:function(xmlHttpRequest){
					$.messager.alert('系统提示',xmlHttpRequest.responseText,'error');
				},
				success:function(data){
					$.messager.alert('系统提示','保存成功.','info');
				}
			});
		}
	});
});
function check(){
	var userAccount=$('#userAccount').val();
	if(userAccount == ""){
		$.messager.alert('系统提示','请输入登陆账户','info');
		return false;
	}
	var password=$('#password').val();
	if(password == ""){
		$.messager.alert('系统提示','请输入密码.','info');
		return false;
	}
	var showName=$('#showName').val();
	if(showName == ""){
		$.messager.alert('系统提示','请输入用户名','info');
		return false;
	}
	var sex=$('input[name="sex"]').val();
	if(sex == ""){
		$.messager.alert('系统提示','请选择性别','info');
		return false;
	}
	var flag=$('input[name="flag"]').val();
	if(flag == ""){
		$.messager.alert('系统提示','请选择启用标记','info');
		return false;
	}
	var organizeId=$('input[name="organize.id"]').val();
	if(organizeId == ""){
		$.messager.alert('系统提示','请选择所属单位','info');
		return false;
	}
	return true;
}
</script>
<head>
   
</head>
  <body>
	    <form id="userForm" method="post">
	    	<input type="hidden" name="id" value="${user.id}">
	    	<table cellpadding="3" align="center">
				<tr>
					<td width="20%" align="right">登陆账户：</td>
					<td width="30%"><input class="easyui-textbox" name="userAccount" id="userAccount" value="${user.userAccount}"/></td>
					<td width="20%" align="right">用户名：</td>
					<td width="30%"><input class="easyui-textbox" name="showName" id="showName" value="${user.showName}" /></td>
				</tr>
				<tr>
					<td align="right">性别：</td>
	    			<td><input class="easyui-combobox"  name="sex" value="${user.sex}"
						data-options="
						valueField:'dicCode',
						textField:'dicName',
						url:'<c:url value="/dictionary/XB/sub.dictionary"/>'
						">
	    			</td>
					<td align="right">启用标记：</td>
					<td><input class="easyui-combobox" name="flag" id="flag" value="${user.flag}"
						data-options="
						valueField:'dicCode',
						textField:'dicName',
						url:'<c:url value="/dictionary/QYBJ/sub.dictionary"/>'
						">
					</td>
				</tr>
				<tr>
					<td align="right">所属单位：</td>
					<td colspan="3">
		    			<input name="organize.id" value="${user.organize.id}" id="organizeTree" class="easyui-combotree" data-options="
			    			onBeforeExpand:function(node){
			    				$(this).tree('options').url = '<c:url value="/organize/async/load.organize"/>';
			    				$(this).tree('options').queryParams.parentId = node.id;
			    			}
			    			,onSelect:function(node) {
			    				if(!$(this).tree('isLeaf',node.target)) {
			    					$('#organizeTree').combotree('clear');
			    				}
			    			}
			    			,url:'<c:url value="/organize/tree/node.organize"/>'
			    			,queryParams:{id:'${user.organize.id}'}
			    			,method:'post'
			    			,lines:true
		    			"/>
					</td>
				</tr>
	    	</table>
	    </form>
	    <div style="text-align:center;padding:5px">
	    	<a href="javascript:void(0)" class="easyui-linkbutton" id="userUpdateButton">提 交</a>
	    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="top.closeDialog();">关 闭</a>
	    </div>
  </body>
</html>
