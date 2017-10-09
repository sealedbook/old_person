<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/taglib/c.tld" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<c:import url="/jsp/common/include.jsp"></c:import>
<head>
   <script>
   function formatOldPerson(val,row,index) {
	   if(null == val) {
		   return '系统中老年人被意外删除';
	   }
	   return val.name;
   }
   
   function query() {
	   var formSerializeArray = $('#verifyForm').serializeArray();
	   var param = {};
	   $.each(formSerializeArray,function(i, field){
		   param[this.name] = this.value;
	   });
	   $('#dg').datagrid('reload',param);
   }
   function reset() {
	   $('#verifyForm').form('reset');
   }

   function showOperatorName(val,row,index) {
	   if(null == val) {
		   return '系统中操作员被意外删除';
	   }
	   return val.name;
   }
   function formatOldPersonArea(val,row,index) {
	   if(null == row.oldPerson) {
		   return '系统中老年人被意外删除';
	   }
	   return row.oldPerson.area.name;
   }
   </script>
</head>
  
  <body>
	<table id="dg" title="认证审核" style="width:98%;height:750px;" data-options="
				rownumbers:true,
				fit : true,
				singleSelect:false,
				autoRowHeight:false,
				autoRowWeight:true,
				pagination:true,
				pageSize:10,
				url:'<c:url value="/health/verify/list.do" />',
				toolbar:'#tb'">
		<thead>
			<tr>
				<th field="oldPerson" formatter="formatOldPerson" align="center" width="12%">老年人姓名</th>
				<th field="oldPersonArea" formatter="formatOldPersonArea" align="center" width="22%">老年人所属地区</th>
				<th field="beginDateTime" formatter="esite.formatDate"  pattern="yyyy-MM-dd HH:mm" align="center" width="12%">开始认证时间</th>
				<th field="useTime"  align="center" width="8%" >用时</th>
				<th field="operator" formatter="showOperatorName" align="center" width="12%" >操作员姓名</th>
				<th field="fingerVerifyState" formatter="esite.convertFingerVerifyState" parentId="fingerVerifyState" align="center" width="10%" >终端指纹识别</th>
				<th field="verifyState" formatter="esite.convertShzt" parentId="shzt" align="center" width="10%" >最终认证状态</th>
				<th field="insertDateTime" formatter="esite.formatDate"  pattern="yyyy-MM-dd HH:mm" align="center" width="10%" >同步时间</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto">
		<form name="verifyForm" id="verifyForm">
			<!-- 
				<input id="operatorName" name="operatorName" prompt='操作员姓名' class="easyui-textbox" data-options="validType:['minLength[1]','length[0,10]']" style="width:150px"> 
			 -->
			 <input id="operatorId" name="operatorId" prompt='操作员' class="easyui-combogrid" data-options="
								panelWidth: 500,
								idField: 'id',
								textField: 'name',
								url: '<c:url value="/operator/list.do" />',
								method: 'get',
								columns: [[
									{field:'idCard',title:'身份证号',width:'50%',align:'center'},
									{field:'name',title:'姓名',width:'50%',align:'center'}
								]],
								pagination:true,
								pageSize:10,
								fitColumns: true,
								rownumbers:true,
								autoRowHeight:true
								,onChange:function(newValue,oldValue) {
									var select = $(this).combogrid('grid').datagrid('getSelected');
									if(select != null) return;
									$(this).combogrid('grid').datagrid('load',{
											name:newValue
										});
								}
								,onClickRow : function(index,row) {
						        	$('#operatorId').combogrid('setValue',row.id);
									$('#operatorId').combogrid('setText',row.name);
									return;
								}
			 " style="width:150px">
			<!-- 
			<input id="oldPersonName" name="oldPersonName" prompt='老年人姓名' class="easyui-textbox" data-options="validType:['minLength[1]','length[0,10]']" style="width:150px"> 
			<input id="oldPersonIdCard" name="oldPersonIdCard" prompt='老年人身份证号' class="easyui-textbox" style="width:150px"> 
			 -->
			 <input id="oldPersonId" name="oldPersonId" prompt='老年人' class="easyui-combogrid" data-options="
			 			panelWidth: 500,
								idField: 'id',
								textField: 'name',
								url: '<c:url value="/oldperson/list.do" />',
								method: 'get',
								columns: [[
									{field:'idCard',title:'身份证号',width:'50%',align:'center'},
									{field:'name',title:'姓名',width:'50%',align:'center'}
								]],
								pagination:true,
								pageSize:10,
								fitColumns: true,
								rownumbers:true,
								autoRowHeight:true
								,onChange:function(newValue,oldValue) {
									var select = $(this).combogrid('grid').datagrid('getSelected');
									if(select != null) return;
									$(this).combogrid('grid').datagrid('load',{
											idCard:newValue
										});
								}
								,onClickRow : function(index,row) {
						        	$('#oldPersonId').combogrid('setValue',row.id);
									$('#oldPersonId').combogrid('setText',row.name);
								}
			 " style="width:150px"> 
			<!-- <input id="healthBeginDate" prompt='开始认证时间' class="easyui-datebox" data-options="editable:false" style="width:150px">  -->
			<!-- <input id="healthEndDate" prompt='完成认证时间' data-options="editable:false" class="easyui-datebox" style="width:150px"> -->
			<input class="easyui-combobox" value="-1" name="verifyState" id="verifyState"
						data-options="
						editable:false,
						valueField:'dicCode',
						textField:'dicName',
						url:'<c:url value="/dictionary/shzt/sub.do"/>'
					">
			<a class="easyui-linkbutton" iconCls="icon-search" onclick="query()">查询</a>
			<a class="easyui-linkbutton" iconCls="icon-reload" onclick="reset()">重置</a>
		</form>
	</div>
	<script type="text/javascript">

	$(function(){
		$('#dg').datagrid({
			//双击事件
			onDblClickRow: function(rowIndex,rowData){
				if(null == rowData.oldPerson) {
					$.messager.alert("系统提示","该信息已不存在.","info");
					return;
				}
				var url = '<c:url value="/health/verify/"/>' + rowData.id + '/view.do?verifyState='+$("#verifyState").combobox('getValue') + '&operatorId=' + $('#operatorId').combogrid('getValue');
				top.openDialog({onClose:function(){$('#dg').datagrid();},width:'60%',height:'100%',title:'老年人指纹与脸部照片信息审核',url:url});
			}
		});
	});


	</script>

  </body>
</html>
