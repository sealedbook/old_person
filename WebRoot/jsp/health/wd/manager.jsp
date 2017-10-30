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
	<table id="dg" title="老年人认证审核(外地)" style="width:98%;height:750px;" data-options="
				rownumbers:true,
				fit : true,
				singleSelect:false,
				autoRowHeight:false,
				autoRowWeight:true,
				pagination:true,
				pageSize:10,
				url:'<c:url value="/health/wd/verify/list.do" />',
				toolbar:'#tb'">
		<thead>
			<tr>
				<th field="ck" checkbox="true"></th>
				<th field="oldPerson" formatter="formatOldPerson" align="center" width="10%">老年人姓名</th>
				<th field="sex" align="center" width="5%" formatter="esite.convertCode2Name" parentId="xb">性别</th>
				<th field="idCard" align="center" width="13%">身份证号</th>
				<th field="socialNumber" align="center" width="13%">编号</th>
				<th field="birthday" align="center" width="10%" formatter="esite.formatDate" pattern="yyyy-MM-dd" >出生日期</th>
				<th field="age" align="center" width="5%" >年龄</th>
				<th field="oldPersonArea" formatter="formatOldPersonArea" align="center" width="20%">老年人所属地区</th>
				<th field="verifyState" formatter="esite.convertCode2Name" parentId="shzt" align="center" width="10%" >最终认证状态</th>
				<th field="insertDateTime" formatter="esite.formatDate"  pattern="yyyy-MM-dd HH:mm" align="center" width="10%" >同步时间</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto">
		<form name="verifyForm" id="verifyForm">
			<input id="oldPersonName" name="oldPersonName" prompt='老年人姓名' class="easyui-textbox" data-options="validType:['minLength[1]','length[0,10]']" style="width:150px">
			<input id="oldPersonIdCard" name="oldPersonIdCard" prompt='老年人身份证号' class="easyui-textbox" style="width:150px">  
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
				var url = '<c:url value="/health/wd/verify/"/>' + rowData.id + '/view.do';
				top.openDialog({onClose:function(){$('#dg').datagrid();},width:'60%',height:'100%',title:'老年人指纹与脸部照片信息审核',url:url});
			}
		});
	});
	</script>

  </body>
</html>
