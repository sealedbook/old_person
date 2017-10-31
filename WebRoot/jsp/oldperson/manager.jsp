<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/taglib/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglib/esite.tld" prefix="view" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<c:import url="/jsp/common/include.jsp"></c:import>
<head>
   <script>
		function formaterArea(val,row,index) {
			return val.name;
		}
		function reset() {
			$('#verifyForm').form('reset');
		}
		function query() {
			var queryParamsArray = $('#verifyForm').serializeArray();
			var params = {};
			$.each(queryParamsArray,function(i, field) {
				if(this.value.length > 0) {
					params[this.name] = this.value;
				}
			});
			$('#dg').datagrid('reload',params);
		}
		function oldPersonStatus(val,row,index) {
			if(row.status.length <= 0) {
				return '<font color="green">' + val + '(正常)</font>';
			} else if(row.status == 'died') {
				return '<font color="red">' + val + '(已死亡)</font>';
			} else if(row.status == 'delete') {
				return '<span style=text-decoration:line-through;><font color="red">' + val + '(已删除)</font></span>';
			}
		}
   </script>
</head>
  
  <body class="easyui-layout">
	<table id="dg" title="随访人员管理"  data-options="
				rownumbers:true,
				fit : true,
				singleSelect:false,
				autoRowHeight:false,
				autoRowWeight:true,
				pagination:true,
				pageSize:20,
				url:'<c:url value="/oldperson/list.do" />',
				toolbar:'#tb'">
		<thead>
			<tr>
				<th field="ck" checkbox="true"></th>
				<th field="name" formatter="oldPersonStatus" align="center" width="8%">姓名</th>
				<!-- <th field="sex" align="center" width="5%" formatter="esite.convertCode2Name" parentId="xb">性别</th> -->
				<th field="sex" align="center" width="5%" formatter="esite.convertSex">性别</th>
				<th field="idCard" align="center" width="15%">身份证号</th>
				<th field="baseQueueCode" align="center" width="15%">基线编号</th>
				<th field="birthday" align="center" width="12%" formatter="esite.formatDate" pattern="yyyy-MM-dd" >出生日期</th>
				<th field="age" align="center" width="5%" >年龄</th>
				<th field="area" formatter="formaterArea" align="center" width="20%" >随访人员所属地区</th>
				<th field="sflx" align="center" width="8%" formatter="esite.convertSflx" parentId="sflx">身份类型</th>
				<th field="modelingStatus" align="center" width="8%" formatter="esite.convertJmzt" parentId="sflx">建模状态</th>
				<!-- <th field="type" align="center" width="8%" formatter="esite.convertLnrlb" parentId="lnrlb" >人员状态</th> -->
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto">
		<div>
			<form name="verifyForm" id="verifyForm">
				<input id="name" name="name" prompt='随访人员姓名' class="easyui-textbox" data-options="validType:['minLength[1]','length[0,10]']" style="width:150px">
				<input id="idCard" name="idCard" prompt='随访人员身份证号' class="easyui-textbox" style="width:150px">
				<input id="ageRangeBegin" name="ageRangeBegin" prompt='年龄段-起始' data-options="validType:['number']" class="easyui-textbox" style="width:90px">
				<input id="ageRangeEnd" name="ageRangeEnd" prompt='年龄段-结束' data-options="validType:['number']" class="easyui-textbox" style="width:90px">
				<input prompt='随访人员性别' class="easyui-combobox" name="sex" id="sex"
					data-options="
					editable:false,
					valueField:'dicCode',
					textField:'dicName',
					url:'<c:url value="/dictionary/xb/sub.do"/>'
				">
				<!--
				<input prompt='人员状态' class="easyui-combobox" name="type" id="type"
					data-options="
					editable:false,
					valueField:'dicCode',
					textField:'dicName',
					url:'<c:url value="/dictionary/lnrlb/sub.do"/>'
				">
				-->
				<input prompt='随访人员状态' class="easyui-combobox" name="status" id="status"
					data-options="
					editable:false,
					valueField:'dicCode',
					textField:'dicName',
					url:'<c:url value="/dictionary/lnrStatus/sub.do"/>'
				">
				<input prompt='随访人员建模状态' class="easyui-combobox" name="modelingStatus" id="modelingStatus"
					data-options="
					editable:false,
					valueField:'dicCode',
					textField:'dicName',
					url:'<c:url value="/dictionary/lnrModelingStatus/sub.do"/>'
				">
				<!-- 
				<input prompt='随访人员年龄段' class="easyui-combobox" name="ageRange" id="ageRange"
					data-options="
					editable:false,
					valueField:'dicCode',
					textField:'dicName',
					url:'<c:url value="/dictionary/lnrAge/sub.do"/>'
				">
				 -->
				<!-- 
				<input prompt='随访人员所属地区' name="areaId" id="organizeTree"  class="easyui-combotree" data-options="
			    			onBeforeExpand:function(node){
			    				$(this).tree('options').queryParams.parentId = node.id;
			    			}
			    			,onSelect:function(node) {
			    				if(!$(this).tree('isLeaf',node.target)) {
			    					$(this).tree('expand',node.target);
									$('#organizeTree').combotree('setValue',node.id);
			    				}
			    				if(node.status == 'del') {
									$('#organizeTree').combotree('setValue',node.id);
			    				}
			    			}
			    			,formatter:function(node) {
			    				if(node.status == 'del') {
			    					return '<span style=text-decoration:line-through;>' + node.text + '(已删除,无法选择)' + '</span>';
			    				}
			    				return node.text;
			    			}
			    			,url:'<c:url value="/organize/async/load.do"/>'
			    			,method:'post'
			    			,lines:true
			    			,panelHeight:200
		    			"/>
				 -->
				<a class="easyui-linkbutton" iconCls="icon-search" onclick="query()">查询</a>
				<a class="easyui-linkbutton" iconCls="icon-reload" onclick="reset()">重置</a>
			</form>
		</div>
		<div>
			<a href="#" title="添加" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add();">添加</a>
			<a href="#" title="修改" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="update();">修改</a>
			<a href="#" title="删除" class="easyui-linkbutton" iconCls="icon-user_delete" plain="true" onclick="del();">删除</a>
			<a href="#" title="查看随访人员详细" class="easyui-linkbutton" iconCls="icon-user" plain="true" onclick="view();">查看</a>
			<a href="#" title="标记随访人员已经死亡" class="easyui-linkbutton" iconCls="icon-status_offline" plain="true" onclick="died();">死亡</a>
			<view:security url="/oldperson/number/undo.do">
				<a href="#" title="还原为正常状态" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="undo();">还原</a>
			</view:security>
			|
			<a href="<c:url value="/jsp/oldperson/mb.zip"/>" title="模板下载" class="easyui-linkbutton" iconCls="icon-rar" plain="true">模板下载</a>
			<a href="#" title="导入人员" class="easyui-linkbutton" iconCls="icon-xls" plain="true" onclick="importOldPerson()">导入人员</a>
			|
			<a href="#" title="已建模转换为未建模" class="easyui-linkbutton" iconCls="icon-wrench" plain="true" onclick="createdToNone()">已建模->未建模</a>
			<a href="#" title="未建模转换为无法建模" class="easyui-linkbutton" iconCls="icon-wrench_orange" plain="true" onclick="noneToContCreated()">未建模->无法建模</a>
			<view:security url="/oldperson/number/undo.do">
				<!-- <a href="#" title="查看随访人员的操作日志" class="easyui-linkbutton" iconCls="icon-application_xp_terminal" plain="true" onclick="operatorLog()">日志</a> -->
			</view:security>
		</div>
	</div>
	<script type="text/javascript">

	$(function(){
		$('#dg').datagrid({
			//双击事件
			onDblClickRow: function(rowIndex,rowData){
				var url = '<c:url value="/oldperson/"/>' + rowData.id + '.do';
				top.openDialog({onClose:function(){},width:'50%',height:'90%',title:'随访人员档案信息',url:url});
			}
		});
	});
	function operatorLog() {
		
	}
	function view() {
		var row  = $('#dg').datagrid("getSelections");
		if(row.length == 0 || row.length > 1){
			$.messager.alert("系统提示","请选择一项","info");
		}else{
			var url = '<c:url value="/oldperson/"/>' + row[0].id + '.do';
			top.openDialog({onClose:function(){$('#dg').datagrid();},width:'50%',height:'90%',title:'人员档案信息',url:url});
		}
	}
	
	function importOldPerson() {
		top.openDialog({onClose:function(){$('#dg').datagrid();},width:'50%',height:'50%',title:'数据导入',url:'<c:url value="/oldperson/importPage.do"/>'});
	}
	function add(){
		top.openDialog({onClose:function(){$('#dg').datagrid();},width:'40%',height:'80%',title:'人员基本信息录入',url:'<c:url value="/oldperson/addPage.do"/>'});
	}
	function undo() {
		//获取选中行
		var row  = $('#dg').datagrid("getSelections");
		if(row.length == 0 || row.length > 1){
			$.messager.alert("系统提示","请选择一个修改项","info");
		}else{
			if(row[0].status == '') {
				$.messager.alert("系统提示","所选的信息当前状态不支持这个操作.","info");
				return;
			}
			var url = '<c:url value="/oldperson/'+row[0].id+'/undo.do"/>';
			$.get(url,function(){
				$('#dg').datagrid();
			},'text');
		}
	}
	
	function createdToNone() {
		//获取选中行
		var row  = $('#dg').datagrid("getSelections");
		if(row.length == 0 || row.length > 1){
			$.messager.alert("系统提示","请选择一个修改项","info");
		}else{
			if(row[0].modelingStatus != 'created') {
				$.messager.alert("系统提示","所选的信息当前状态不支持这个操作.","info");
				return;
			}
			$.messager.confirm("系统提示","您确定将随访人员的已建模状态变更为未建模吗?",function(r){
				if(r) {
					$.ajax({
						type:"POST",
						url:'<c:url value="/oldperson/updateModelingStatus.do" />',
						dataType:'text',
						data:{oldPersonId:row[0].id, modelingStatus : 'none'}
						,beforeSend : function(XMLHttpRequest) {
							return $('#userForm').form('validate');
						}
						,error:function(xmlHttpRequest){
							$.messager.alert('系统提示',xmlHttpRequest.responseText,'error');
						},
						success:function(data){
							$.messager.alert('系统提示','变更成功.','info');
							$('#dg').datagrid();
						}
					});
				}
			});
		}
	}
	function noneToContCreated() {
		//获取选中行
		var row  = $('#dg').datagrid("getSelections");
		if(row.length == 0 || row.length > 1){
			$.messager.alert("系统提示","请选择一个修改项","info");
		}else{
			if(row[0].modelingStatus != 'none') {
				$.messager.alert("系统提示","所选的信息当前状态不支持这个操作.","info");
				return;
			}
			$.messager.confirm("系统提示","您确定将随访人员的未建模状态变更为无法建模吗?",function(r){
				if(r) {
					$.ajax({
						type:"POST",
						url:'<c:url value="/oldperson/updateModelingStatus.do" />',
						dataType:'text',
						data:{oldPersonId:row[0].id, modelingStatus : 'contcreated'}
						,beforeSend : function(XMLHttpRequest) {
							return $('#userForm').form('validate');
						}
						,error:function(xmlHttpRequest){
							$.messager.alert('系统提示',xmlHttpRequest.responseText,'error');
						},
						success:function(data){
							$.messager.alert('系统提示','变更成功.','info');
							$('#dg').datagrid();
						}
					});
				}
			});
		}
	}
	
	function update(){
		//获取选中行
		var row  = $('#dg').datagrid("getSelections");
		if(row.length == 0 || row.length > 1){
			$.messager.alert("系统提示","请选择一个修改项","info");
		}else{
			if(row[0].status != '') {
				$.messager.alert("系统提示","所选的信息当前状态不支持这个操作.","info");
				return;
			}
			var url = '<c:url value="/oldperson/'+row[0].id+'/updatePage.do"/>';
			top.openDialog({onClose:function(){$('#dg').datagrid();},width:'40%',height:'80%',title:'随访人员基本信息修改',url:url});
		}
	}
	function died() {
		var row  = $('#dg').datagrid("getSelections");
		if(row.length == 0 || row.length > 1){
			$.messager.alert("系统提示","请选择一项进行操作","info");
		}else{
			if(row[0].status != '') {
				$.messager.alert("系统提示","所选的信息当前状态不支持这个操作.","info");
				return;
			}
			$.messager.confirm("系统提示","您确定该随访人员已经死亡吗?",function(r){
				if(r) {
					$.ajax({
						type:"POST",
						url:'<c:url value="/oldperson/died.do" />',
						dataType:'text',
						data:{oldPersonId:row[0].id}
						,beforeSend : function(XMLHttpRequest) {
							return $('#userForm').form('validate');
						}
						,error:function(xmlHttpRequest){
							$.messager.alert('系统提示',xmlHttpRequest.responseText,'error');
						},
						success:function(data){
							$.messager.alert('系统提示','已经标记为死亡状态.','info');
							$('#dg').datagrid();
						}
					});
				}
			})
		}
	}
	function del() {
		var row  = $('#dg').datagrid("getSelections");
		if(row.length == 0 || row.length > 1){
			$.messager.alert("系统提示","请选择一项进行操作","info");
		}else{
			if(row[0].status != '') {
				$.messager.alert("系统提示","所选的信息当前状态不支持这个操作.","info");
				return;
			}
			$.messager.confirm("系统提示","确认要删除该随访人员吗?",function(r){
				if(r) {
					$.ajax({
						type:"POST",
						url:'<c:url value="/oldperson/delete.do" />',
						dataType:'text',
						data:{oldPersonId:row[0].id}
						,beforeSend : function(XMLHttpRequest) {
							return $('#userForm').form('validate');
						}
						,error:function(xmlHttpRequest){
							$.messager.alert('系统提示',xmlHttpRequest.responseText,'error');
						},
						success:function(data){
							$.messager.alert('系统提示','删除成功.','info');
							$('#dg').datagrid();
						}
					});
				}
			})
		}
	}
	</script>

  </body>
</html>
