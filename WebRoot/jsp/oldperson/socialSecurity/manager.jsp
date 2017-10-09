<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/taglib/c.tld" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<c:import url="/jsp/common/include.jsp"></c:import>
<head>
   <script>
		function reset() {
			$('#verifyForm').form('reset');
		}
   </script>
</head>
  
  <body class="easyui-layout">
	<table id="dg" title="老年人社保发放管理"  data-options="
				rownumbers:true,
				fit : true,
				singleSelect:false,
				autoRowHeight:false,
				autoRowWeight:true,
				pagination:true,
				pageSize:10,
				url:'<c:url value="/oldperson/social/security/list.do" />',
				toolbar:'#tb'">
		<thead>
			<tr>
				<th field="name" align="center" width="10%">姓名</th>
				<th field="sex" align="center" width="5%" formatter="esite.convertCode2Name" parentId="xb">性别</th>
				<th field="idCard" align="center" width="15%">身份证号</th>
				<th field="socialNumber" align="center" width="15%">社保编号</th>
				<th field="age" align="center" width="5%" >年龄</th>
				<th field="area" align="center" width="20%" >老年人所属地区</th>
				<th field="type" align="center" width="8%" formatter="esite.convertCode2Name" parentId="lnrlb" >人员状态</th>
				<th field="sendDate" formatter="esite.formatDate" pattern="yyyy-MM-dd" align="center" width="10%" >发放时间</th>
				<th field="sendStatus" formatter="esite.convertCode2Name" parentId="sendStatus" align="center" width="10%" >发放状态</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto">
		<div>
			<!-- <a href="#" title="查看历史导入记录以及历史导入的文件" class="easyui-linkbutton" iconCls="icon-application_double" plain="true" onclick="alert('未实现')">历史导入</a> -->
			<a href="<c:url value="/jsp/oldperson/socialSecurity/mb.rar"/>" title="社保模板下载" class="easyui-linkbutton" iconCls="icon-rar" plain="true">模板下载</a>
			<a href="#" title="老年人社保发放情况导入" class="easyui-linkbutton" iconCls="icon-xls" plain="true" onclick="dataImport()">导入(社保来源)</a>
		</div>
	</div>
	<script type="text/javascript">
	$(function(){
		$('#dg').datagrid({
			//双击事件
			onDblClickRow: function(rowIndex,rowData){
				//var url = '<c:url value="/oldperson/"/>' + rowData.id + '.do';
				//top.openDialog({onClose:function(){$('#dg').datagrid();},width:'40%',height:'80%',title:'老年人档案信息',url:url});
			}
		});
	});
	
	function dataImport() {
		top.openDialog({onClose:function(){$('#dg').datagrid();},width:'50%',height:'50%',title:'社保数据导入',url:'<c:url value="/oldperson/social/security/importPage.do"/>'});
	}
	
	</script>

  </body>
</html>
