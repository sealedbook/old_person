<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/taglib/c.tld" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<c:import url="/jsp/common/include.jsp"></c:import>
<script type="text/javascript">
$(function(){
	$('#userCreateButton').click(function(){
		if(false == $('#userForm').form('validate')) {
			return false;
		}
		$.ajax({
			type:"POST",
			url:'<c:url value="/cycle/change.do" />',
			dataType:'text',
			data:$('#userForm').serialize(),
			error:function(xmlHttpRequest){
				$.messager.alert('系统提示',xmlHttpRequest.responseText,'error');
			},
			success:function(data){
				$.messager.alert('系统提示','保存成功.','info');
				top.closeDialog();
			}
		});
	});

});

</script>
<head>
   
</head>
  <body>
	    <form id="userForm" method="post">
	    	<input type="hidden" name="id" value="${cycle.id }" />
	    	<input type="hidden" name="notice.id" value="${cycle.notice.id }" />
	    	<table class="tableStyle">
	    		<tr>
					<td width="10%" class="right">时间周期</td>
					<td colspan="3" width="35%">
						<input id="cycleBegin" value="${cycle.cycleBegin }" name="cycleBegin" class="easyui-datebox" 
						data-options="required:true,editable:false,validType:{
							dateLessThan:['#cycleEnd']
						}" />
						至
						<input id="cycleEnd" value="${cycle.cycleEnd }" name="cycleEnd" class="easyui-datebox" 
						data-options="required:true,editable:false,validType:{
							dateGreaterThan:['#cycleBegin']
						}" />
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
