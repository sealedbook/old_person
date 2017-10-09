<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/taglib/c.tld" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

<head>
	<c:import url="/jsp/common/include.jsp"></c:import>
</head>
  
  <body>
	<table id="dg" title="角色管理" style="width:98%;height:750px;" data-options="
				fit:true,
				rownumbers:true,
				singleSelect:false,
				autoRowHeight:false,
				pagination:true,
				pageSize:10,
				url:'<c:url value="/role/listForPage.do" />',
				toolbar:'#tb'">
		<thead>
			<tr>
				<th field="ck" checkbox="true"></th>
				<th field="name" align="center" width="95%">角色名称</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto">
		<div>
			<a href="#" title="添加" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add();">添加</a>
			<a href="#" title="删除" class="easyui-linkbutton" iconCls="icon-no" plain="true" onclick="del();">删除</a>
		</div>
	</div>
	<script type="text/javascript">

	$(function(){
		$('#dg').datagrid({
			onSelect:function(index,row) {
				if(row.id == '402881e94ab3e7f7014ab3fdbc63000e' || row.id == '402881ee4a48d7dd014a48d907030001' || row.id=='E89E35331C234CAB86439C06AF0F32F9') {
					$(this).datagrid('unselectRow',index);
					$(this).datagrid('uncheckRow',index);
				}
			}
			,onCheck:function(index,row) {
				if(row.id == '402881e94ab3e7f7014ab3fdbc63000e' || row.id == '402881ee4a48d7dd014a48d907030001' || row.id=='E89E35331C234CAB86439C06AF0F32F9') {
					$(this).datagrid('unselectRow',index);
					$(this).datagrid('uncheckRow',index);
				}
			}
		});
	});
	function add(){
		top.openDialog({onClose:function(){$('#dg').datagrid();},width:'40%',height:'40%',title:'角色添加',url:'<c:url value="/role/addPage.do"/>'});
	}
	function del(){
		var row  = $('#dg').datagrid("getSelections");
		if(row.length == 0){
			$.messager.alert("系统提示","请选择删除项","info");
		}else{
			//ids
			var ids = "";
			
			for(var i = 0;i<row.length;i++){
				ids += "&ids="+row[i].id;
			}
			//提示是否删除
			$.messager.confirm('系统提示', '是否删除?', function(r){
				if(r){
					$.post('<c:url value="/role/delete.do?'+ids+'" />',function(){
						//删除成功后，重新加载datagrid
						$.messager.alert("系统提示","删除成功","info");
						 $('#dg').datagrid();
					},'text');
				}
			});
		}
	}
	</script>

  </body>
</html>
