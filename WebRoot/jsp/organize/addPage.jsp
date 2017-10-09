<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/taglib/c.tld" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<c:import url="/jsp/common/include.jsp"></c:import>
<script type="text/javascript">
$(function(){
	$('#areaCreateButton').click(function(){
		if(false == $('#areaForm').form('validate')) {
			return;
		}
		$.ajax({
			type:"POST",
			url:'<c:url value="/organize/add.do?parentId=${parentId}" />',
			dataType:'text',
			data:$('#areaForm').serialize(),
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
	    <form id="areaForm" method="post">
	    	<input type="hidden" name="parent.id" value="${parentId }"/>
	    	<table class="tableStyle">
				<tr>
					<td width="20%" class="right">区域名称</td>
					<td width="30%"><input data-options="required:true,validType:['blankSpace','minLength[2]','length[0,10]']" class="easyui-textbox" name="name" id="name" /></td>
				</tr>
				<tr>
					<td width="20%" class="right">区域类型</td>
					<td width="30%">
					<input class="easyui-combobox" name="type" id="type"
						data-options="
						required:true,
						editable:false,
						valueField:'dicCode',
						textField:'dicName',
						url:'<c:url value="/dictionary/area_type/sub.do"/>'
						">
					</td>
				</tr>
	    	</table>
	    </form>
	    <div style="text-align:center;padding:5px">
	    	<a href="javascript:void(0)" class="easyui-linkbutton" id="areaCreateButton">提 交</a>
	    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="top.closeDialog();">关 闭</a>
	    </div>
  </body>
</html>
