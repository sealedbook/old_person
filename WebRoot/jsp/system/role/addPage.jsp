<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/taglib/c.tld" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<c:import url="/jsp/common/include.jsp"></c:import>
<script type="text/javascript">
$(function(){
	$('#createRoleButton').click(function(){
		$.ajax({
			type:"POST",
			url:'<c:url value="/role/save.do" />',
			dataType:'text',
			data:$('#xnzzForm').serialize(),
			error:function(xmlHttpRequest){
				$.messager.alert('系统提示',xmlHttpRequest.responseText,'error');
			},
			success:function(data){
				$.messager.alert('系统提示',data,'info');
				top.closeDialog();
			}
		});
	});
});

</script>
<head>
   
</head>
  <body>
	    <form id="xnzzForm" method="post">
	    	<table class="tableStyle">
	    		<tr>
					<td width="20%" class="right">角色名称：</td>
					<td width="30%"><input class="easyui-textbox" name="name" id="name" /></td>
				</tr>
	    	</table>
	    </form>
	    <div style="text-align:center;padding:5px">
	    	<a href="javascript:void(0)" class="easyui-linkbutton" id="createRoleButton"> 提 交</a>
	    </div>
  </body>
</html>
