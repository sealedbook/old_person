<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/taglib/c.tld" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>


<head>
	<c:import url="/jsp/common/include.jsp"></c:import>
</head>
  
  <body>
	<table id="dg" title="操作员管理" style="width:98%;height:750px;" data-options="
				fit:true,
				rownumbers:true,
				singleSelect:false,
				autoRowHeight:false,
				autoRowWeight:true,
				pagination:true,
				pageSize:10,
				url:'<c:url value="/operator/list.do" />',
				toolbar:'#tb'">
		<thead>
			<tr>
				<th field="ck" checkbox="true"></th>
				<th field="name" formatter="operatorStatus" align="center" width="10%">姓名</th>
				<th field="sex" align="center" width="5%" formatter="esite.convertSex" parentId="xb">性别</th>
				<th field="idCard" align="center" width="20%">身份证号</th>
				<th field="birthday" align="center" width="20%" formatter="esite.formatDate" pattern="yyyy-MM-dd" >出生日期</th>
				<th field="age" align="center" width="5%" >年龄</th>
				<th field="manageArea" formatter="esite.convertOrganizeCodeArray2Name" align="center" width="36%" >管理区域</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto">
		<div>
			<form name="operatorQueryForm" id="operatorQueryForm">
				<input id="name" name="name" prompt='操作员姓名' class="easyui-textbox" data-options="validType:['minLength[1]','length[0,10]']" style="width:150px">
				<input id="idCard" name="idCard" prompt='操作员身份证号' class="easyui-textbox" style="width:150px">  
				<input prompt='操作员性别' class="easyui-combobox" name="sex" id="sex"
					data-options="
					editable:false,
					valueField:'dicCode',
					textField:'dicName',
					url:'<c:url value="/dictionary/xb/sub.do"/>'
				">
				<input prompt='操作员状态' class="easyui-combobox" name="status" id="status"
					data-options="
					editable:false,
					valueField:'dicCode',
					textField:'dicName',
					url:'<c:url value="/dictionary/czyStatus/sub.do"/>'
				">
				<!-- 
				
				<input prompt='操作员管理地区' name="areaId" id="organizeTree"  class="easyui-combotree" data-options="
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
			<view:security url="/operator/undo.do">
				<a href="#" title="还原为正常状态" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="undo();">还原</a>
			</view:security>
			<a href="#" title="提升为管理员权限" class="easyui-linkbutton" iconCls="icon-man" plain="true" onclick="up();">升为管理</a>
			<a href="#" title="取消管理员权限" class="easyui-linkbutton" iconCls="icon-clear" plain="true" onclick="down();">取消管理</a>
			<!-- 
			<a href="#" title="批量为操作员划分管理区域" class="easyui-linkbutton" iconCls="icon-chart_pie_edit" plain="true" onclick="resetArea();">管理区域划分</a>
			 -->
		</div>
	</div>
	<script type="text/javascript">

	$(function(){
		$('#dg').datagrid({
			//双击事件
			onDblClickRow: function(rowIndex,rowData){
				var url = '<c:url value="/operator/page/"/>' + rowData.id + '/view.do';
				top.openDialog({onClose:function(){},width:'60%',height:'60%',title:'操作员详细信息',url:url});
			}
		});
	});

	function up() {
		var row  = $('#dg').datagrid("getSelections");
		if(row.length == 0 || row.length > 1){
			$.messager.alert("系统提示","请选择一个修改项","info");
		}else{
			var url = '<c:url value="/user/up/manager.do?idCard='+row[0].idCard+'"/>';
			$.get(url,function(){
				$.messager.alert("系统提示","已成为管理员.","info");
			});
		}
		
	}
	function down() {
		var row  = $('#dg').datagrid("getSelections");
		if(row.length == 0 || row.length > 1){
			$.messager.alert("系统提示","请选择一个修改项","info");
		}else{
			var url = '<c:url value="/user/down/manager.do?idCard='+row[0].idCard+'"/>';
			$.get(url,function(){
				$.messager.alert("系统提示","已取消管理员身份.","info");
			});
		}
	}
	
	function operatorStatus(val,row,index) {
		if(row.status.length <= 0) {
			return '<font color="green">' + val + '(正常状态)</font>';
		} else if(row.status == 'died') {
			return '<font color="red">' + val + '(已死亡)</font>';
		} else if(row.status == 'delete') {
			return '<span style=text-decoration:line-through;><font color="red">' + val + '(已删除)</font></span>';
		}
	}
	
	function add(){
		top.openDialog({onClose:function(){$('#dg').datagrid();},width:'50%',height:'50%',title:'操作员基本信息录入',url:'<c:url value="/operator/page/addPage.do"/>'});
	}
	
	function resetArea() {
		//获取选中行
		var row  = $('#dg').datagrid("getSelections");
		if(row.length <= 0){
			$.messager.alert("系统提示","请至少选择一个操作员","info");
		}else{
			var url = '<c:url value="/organize/select.do"/>';
			top.openDialog({onClose:function(){
				
			},closable:false,width:'50%',height:'50%',title:'管理区域划分',url:url});
		}
	}
	
	function update() {
		//获取选中行
		var row  = $('#dg').datagrid("getSelections");
		if(row.length == 0 || row.length > 1){
			$.messager.alert("系统提示","请选择一个修改项","info");
		}else{
			if(row[0].status != '') {
				$.messager.alert("系统提示","所选的信息当前状态不支持这个操作.","info");
				return;
			}
			var url = '<c:url value="/operator/page/'+row[0].id+'/updatePage.do"/>';
			top.openDialog({onClose:function(){$('#dg').datagrid();},width:'50%',height:'50%',title:'操作人基本信息修改',url:url});
		}
	}
	
	function undo() {
		var row  = $('#dg').datagrid("getSelections");
		if(row.length == 0 || row.length > 1){
			$.messager.alert("系统提示","请选择一项进行操作","info");
		}else{
			if(row[0].status == '') {
				$.messager.alert("系统提示","所选的信息当前状态不支持这个操作.","info");
				return;
			}
			var url = '<c:url value="/operator/undo.do"/>';
			$.post(url,{operatorId:row[0].id},function(){
				$('#dg').datagrid();
			});
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
			$.messager.confirm("系统提示","删除操作肯定会导致该操作员无法正常登陆该系统.您确认要删除该操作员吗?",function(r){
				if(r) {
					$.ajax({
						type:"POST",
						url:'<c:url value="/operator/delete.do" />',
						dataType:'text',
						data:{operatorId:row[0].id}
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
	function reset() {
		$('#operatorQueryForm').form('reset');
	}
	function query() {
		var queryParamsArray = $('#operatorQueryForm').serializeArray();
		var params = {};
		$.each(queryParamsArray,function(i, field) {
			if(this.value.length > 0) {
				params[this.name] = this.value;
			}
		});
		$('#dg').datagrid('reload',params);
	}
	</script>

  </body>
</html>
