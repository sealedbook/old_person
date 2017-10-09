<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/taglib/c.tld" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<c:import url="/jsp/common/include.jsp"></c:import>
<head>
   
</head>
  
  <body>
	<table id="dg" title="通知通告管理" style="width:98%;height:750px;" data-options="
				rownumbers:true,
				fit : true,
				singleSelect:false,
				autoRowHeight:false,
				autoRowWeight:true,
				pagination:true,
				pageSize:10,
				url:'<c:url value="/notice/list.do" />',
				toolbar:'#tb'">
		<thead>
			<tr>
				<th field="ck" checkbox="true"></th>
				<th field="content" align="center" width="36%">通知内容</th>
				<th field="submitDateTime" formatter="esite.formatDate"  pattern="yyyy-MM-dd HH:mm" align="center" width="15%">通知时间</th>
				<th field="submitUserId" formatter="esite.convertUserCode2Name" align="center" width="15%" >通知发布人</th>
				<th field="receiveStatus" align="center" width="15%" >当前签收情况</th>
				<th field="submitIpAddress" align="center" width="15%" >操作地址</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto">
		<div>
			<a href="#" title="添加" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add();">添加通知</a>
			|
			<a href="#" title="查看详细信息" class="easyui-linkbutton" iconCls="icon-table_gear" plain="true" onclick="view()">详细信息</a>
		</div>
	</div>
	<script type="text/javascript">

	$(function(){
		$('#dg').datagrid({
			//双击事件
			onDblClickRow: function(rowIndex,rowData){
				var url = '<c:url value="/notice/view/"/>' + rowData.id + '.do';
				top.openDialog({onClose:function(){$('#dg').datagrid();},width:'50%',height:'90%',title:'通知通告详细信息',url:url});
			}
		});
	});

	function add(){
		top.openDialog({onClose:function(){$('#dg').datagrid();},width:'40%',height:'80%',title:'通知通告录入',url:'<c:url value="/notice/addPage.do"/>'});
	}
	
	function view() {
		var row  = $('#dg').datagrid("getSelections");
		if(row.length == 0 || row.length > 1){
			$.messager.alert("系统提示","请选择一个通知,或双击通知.","info");
		}
		var url = '<c:url value="/notice/view/"/>' + row[0].id + '.do';
		top.openDialog({onClose:function(){$('#dg').datagrid();},width:'50%',height:'90%',title:'通知通告详细信息',url:url});
	}

	</script>

  </body>
</html>
