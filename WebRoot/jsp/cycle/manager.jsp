<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/taglib/c.tld" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<c:import url="/jsp/common/include.jsp"></c:import>
<head>
   
</head>
  
  <body>
	<table id="dg" title="已设置周期" style="width:98%;height:750px;" data-options="
				rownumbers:true,
				fit : true,
				singleSelect:false,
				autoRowHeight:false,
				autoRowWeight:true,
				pagination:true,
				pageSize:10,
				url:'<c:url value="/cycle/list.do" />',
				toolbar:'#tb'">
		<thead>
			<tr>
				<th field="ck" checkbox="true"></th>
				<th field="cycleBegin" formatter="esite.formatDate"  pattern="yyyy-MM-dd" align="center" width="15%">起始周期</th>
				<th field="cycleEnd" formatter="esite.formatDate"  pattern="yyyy-MM-dd" align="center" width="15%">结束周期</th>
				<th field="notice" formatter="cycleNoticeState" align="center" width="15%" >发布通知情况</th>
				<th field="submitUserId" formatter="esite.convertUserCode2Name" align="center" width="15%" >周期发布人</th>
				<th field="submitDateTime" formatter="esite.formatDate"  pattern="yyyy-MM-dd HH:mm:ss" align="center" width="15%">周期发布时间</th>
				<th field="submitIpAddress" align="center" width="15%">操作地址</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto">
		<div>
			<a href="#" title="添加(设置)一个新的周期" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add();">添加周期</a>
			<a href="#" title="将周期变更" class="easyui-linkbutton" iconCls="icon-map_edit" plain="true" onclick="change();">周期变更</a>
			<a href="#" title="将周期以通知通告的方式发送给操作员" class="easyui-linkbutton" iconCls="icon-feed" plain="true" onclick="notice();">发送通知</a>
			
		</div>
	</div>
	<script type="text/javascript">
	function cycleNoticeState(val,row,index) {
		if(null == val) {
			return '<font color="red">还未发布</font>';
		} else {
			return '<font color="green">' + val.receiveStatus + '</font>';
		}
	}
	$(function(){
		$('#dg').datagrid({
			//双击事件
			onDblClickRow: function(rowIndex,rowData){
				//var url = '<c:url value="/notice/view/"/>' + rowData.id + '.do';
				//top.openDialog({onClose:function(){$('#dg').datagrid();},width:'50%',height:'90%',title:'通知通告详细信息',url:url});
			}
		});
	});

	function add(){
		top.openDialog({onClose:function(){$('#dg').datagrid();},width:'40%',height:'50%',title:'设置新的周期',url:'<c:url value="/cycle/addPage.do"/>'});
	}
	function change() {
		var row  = $('#dg').datagrid("getSelections");
		if(row.length == 0 || row.length > 1){
			$.messager.alert("系统提示","请选择一个周期.","info");
		} else {
			var url = '<c:url value="/cycle/changePage.do?cycleId="/>' + row[0].id;
			top.openDialog({onClose:function(){$('#dg').datagrid();},width:'40%',height:'50%',title:'发布通知通告',url:url});
		}
	}
	function notice() {
		var row  = $('#dg').datagrid("getSelections");
		if(row.length == 0 || row.length > 1){
			$.messager.alert("系统提示","请选择一个周期.","info");
		} else if(null == row[0].notice) {
			var url = '<c:url value="/cycle/noticePage.do?cycleId="/>' + row[0].id;
			top.openDialog({onClose:function(){$('#dg').datagrid();},width:'40%',height:'90%',title:'发布通知通告',url:url});
		} else {
			$.messager.alert("系统提示","选中的周期已经发送过通知.系统不允许重复发送.","info");
		}
		
	}

	</script>

  </body>
</html>
