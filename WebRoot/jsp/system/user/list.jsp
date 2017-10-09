<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/taglib/c.tld" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<c:import url="/jsp/common/include.jsp"></c:import>
<script type="text/javascript">

function showOrganize(val,row,index) {
	return val.orgFullName;
}

</script>
<head>
   
</head>
  
  <body>
	<table id="dg" title="用户信息" style="width:98%;height:750px;" data-options="
				fit:true,
				rownumbers:true,
				singleSelect:false,
				autoRowHeight:false,
				pagination:true,
				pageSize:10,
				url:'<c:url value="/user/list.do" />',
				toolbar:'#tb'">
		<thead>
			<tr>
				<th field="ck" checkbox="true"></th>
				<th field="account" align="center" width="20%">登录账号</th>
				<th field="showName" align="center" width="20%">姓名</th>
				<th field="sex" align="center" width="10%" formatter="esite.convertCode2Name" parentId="xb">性别</th>
				<th field="idCard" align="center" width="20%">身份证号</th>
				<!-- <th field="organize" formatter="showOrganize" align="center" width="47%">所在单位</th> -->
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto">
		<div>
			<a href="#" title="添加" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add();">添加</a>
			<a href="#" title="提升为管理员权限" class="easyui-linkbutton" iconCls="icon-man" plain="true" onclick="up();">升为管理</a>
			<a href="#" title="取消管理员权限" class="easyui-linkbutton" iconCls="icon-clear" plain="true" onclick="down();">取消管理</a>
			<!-- <a href="#" title="修改" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="update();">修改</a> 
			<a href="#" title="删除" class="easyui-linkbutton" iconCls="icon-no" plain="true" onclick="del();">删除</a>-->
		</div>
	</div>
	<script type="text/javascript">

	$(function(){
		$('#dg').datagrid({
			//双击事件
			onDblClickRow: function(rowIndex,rowData){
				//top.openDialog({onClose:function(){},width:'60%',height:'80%',title:'用户详细信息',url:'<c:url value="/user/view.do?id='+rowData.id+'"/>'});
			}
			,onSelect:function(index,row) {
				if(row.id == 'f70c1dd3719011e4b112dc0ea1dd9a8b') {
					$(this).datagrid('unselectRow',index);
					$(this).datagrid('uncheckRow',index);
				}
			}
		});
	});
	//查询
	function query(){
		var arr = $('#userForm').serializeArray();
		var append = "";
		$.each(arr,function(d){
			if(this.value.length > 0){
				append += this.name + '=' + escape(encodeURI(this.value)) + '&'
			}
		});
		$('#dg').datagrid('load','<c:url value="/user/list.do?'+append+'" />');
	}
	function add(){
		top.openDialog({onClose:function(){$('#dg').datagrid();},width:'50%',height:'50%',title:'用户添加',url:'<c:url value="/user/addPage.do"/>'});
	}
	function up() {
		var row  = $('#dg').datagrid("getSelections");
		if(row.length == 0 || row.length > 1){
			$.messager.alert("系统提示","请选择一个修改项","info");
		}else{
			var url = '<c:url value="/user/up/manager.do?id='+row[0].id+'"/>';
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
			var url = '<c:url value="/user/down/manager.do?id='+row[0].id+'"/>';
			$.get(url,function(){
				$.messager.alert("系统提示","已取消管理员身份.","info");
			});
		}
	}
	
	function update(){
		//获取选中行
		var row  = $('#dg').datagrid("getSelections");
		if(row.length == 0 || row.length > 1){
			$.messager.alert("系统提示","请选择一个修改项","info");
		}else{
			top.openDialog({onClose:function(){$('#dg').datagrid();},width:'60%',height:'50%',title:'用户修改',url:'<c:url value="/user/updatePage.do?id='+row[0].id+'"/>'});
		}
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
					$.post('<c:url value="/user/delete.do?'+ids+'" />',function(){
						//删除成功后，重新加载datagrid
						$.messager.alert("系统提示","删除成功","info");
						 $('#dg').datagrid();
					},'json');
				}
			});
		}
	}
	
	function clearButton(){
		$('#userForm').form('clear');
	}
	</script>

  </body>
</html>
