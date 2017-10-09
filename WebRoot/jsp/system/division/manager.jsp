<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/taglib/c.tld" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

<head>
<c:import url="/jsp/common/include.jsp"></c:import>
</head>
  
  <body>
  	<div id="divisionManagerViewDiv" class="easyui-layout" data-options="fit:true">
		<div data-options="region:'west',split:true" title="行政区划列表" style="width:210px;">
			<ul class="easyui-tree" id="divisionTree"></ul>
			<script>
			$(function(){
			    $('#divisionTree').tree({
			        url:'<c:url value="/organize/simple/async/load.do"/>'
		        	,onBeforeExpand:function(node){
	    				$(this).tree('options').queryParams.parentId = node.id;
	    			}
	    			,onSelect:function(node) {
	    				var url = '<c:url value="/division/detail.do?areaId="/>' + node.id;
	    				$('#rightDivisionFrame').attr('src',url);
	    			}
	    			,onContextMenu:function(e, node) {
	    				e.preventDefault();
	    				$(this).tree('select', node.target);
	    				var menu = $('#treeMenu');
	    				if(node.status == 'del') {
	    					menu = $('#treeMenuRestore');
	    				} else if(node.type == 'c' || node.type == 'sq') {
	    					menu = $('#treeMenuNoAdd');
	    				}
	    				$(menu).menu('show',{
	    					 left: e.pageX,
	    					 top: e.pageY
	    				});
	    			}
	    			,formatter:function(node) {
	    				var type = '未分类';
	    				if(node.type == 's') {
	    					type = '省市';
	    				}
	    				if(node.type == 'z') {
	    					type = '地区-镇';
	    				}
	    				if(node.type == 'fl') {
	    					type = '分类';
	    				}
	    				if(node.type == 'c') {
	    					type = '地区-村';
	    				}
	    				if(node.type == 'sq') {
	    					type = '地区-社区';
	    				}
	    				if(node.status == 'del') {
	    					return '<span style="text-decoration:line-through;">' + node.text + '(已删除)' + '</span>';
	    				}
	    				return node.text + '(' + type + ')' + '</span>';
	    			}
			       ,method:'post'
			       ,lines:true
			    });
			});
			function editNode() {
				var selectedNode = $("#divisionTree").tree('getSelected');
				var url = '<c:url value="/organize/editPage.do?id="/>' + selectedNode.id;
				top.openDialog({onClose:function(){
					if($("#divisionTree").tree('isLeaf',selectedNode.target)) {
						var parentNode= $("#divisionTree").tree('getParent',selectedNode.target);
						$("#divisionTree").tree('reload',parentNode.target);
					}
					$("#divisionTree").tree('reload',selectedNode.target);
				},width:'30%',height:'30%',title:'区域修改',url:url});
			}
			function append() {
				var selectedNode = $("#divisionTree").tree('getSelected');
				var url = '<c:url value="/organize/addPage.do?parentId="/>' + selectedNode.id;
				top.openDialog({onClose:function(){
					if($("#divisionTree").tree('isLeaf',selectedNode.target)) {
						var parentNode= $("#divisionTree").tree('getParent',selectedNode.target);
						$("#divisionTree").tree('reload',parentNode.target);
					}
					$("#divisionTree").tree('reload',selectedNode.target);
				},width:'30%',height:'30%',title:'添加新的区域',url:url});
			}
			function deleteNode() {
				var selectedNode = $("#divisionTree").tree('getSelected');
				 $.messager.confirm('系统提示', '是否确定要删除该区域?', function(r){
					 if (r){
						var url = '<c:url value="/organize/remove.do"/>';
						$.post(url,{id:selectedNode.id},function(data){
							if(data.length <= 0) {
								var parentNode= $("#divisionTree").tree('getParent',selectedNode.target);
								$("#divisionTree").tree('reload',parentNode.target);
								$("#divisionTree").tree('reload',selectedNode.target);
							} else {
								$.messager.alert('系统提示',data,'error');
							}
						});
					 }
				});
			}
			function refresh() {
				var selectedNode = $("#divisionTree").tree('getSelected');
				$("#divisionTree").tree('reload',selectedNode.target);
			}
			function restore() {
				var selectedNode = $("#divisionTree").tree('getSelected');
				var url = '<c:url value="/organize/restore.do"/>';
				$.post(url,{id:selectedNode.id},function(){
					var parentNode= $("#divisionTree").tree('getParent',selectedNode.target);
					$("#divisionTree").tree('reload',parentNode.target);
					$("#divisionTree").tree('reload',selectedNode.target);
				});
			}
			</script>
		</div>
		
		<div id="treeMenuRestore" class="easyui-menu" style="width:200px;">
			<div onclick="restore()" data-options="iconCls:'icon-undo'">还原</div>
			<div class="menu-sep"></div>
			<div onclick="refresh()" data-options="iconCls:'icon-reload'">刷新</div>
		</div>
		<div id="treeMenu" class="easyui-menu" style="width:200px;">
			<div onclick="append()" data-options="iconCls:'icon-add'">增加下级地区</div>
			<div onclick="deleteNode()" data-options="iconCls:'icon-remove'">删除地区</div>
			<div onclick="editNode()" data-options="iconCls:'icon-edit'">修改地区</div>
			<div class="menu-sep"></div>
			<div onclick="refresh()" data-options="iconCls:'icon-reload'">刷新</div>
		</div>
		<div id="treeMenuNoAdd" class="easyui-menu" style="width:200px;">
			<div onclick="deleteNode()" data-options="iconCls:'icon-remove'">删除地区</div>
			<div onclick="editNode()" data-options="iconCls:'icon-edit'">修改地区</div>
			<div class="menu-sep"></div>
			<div onclick="refresh()" data-options="iconCls:'icon-reload'">刷新</div>
		</div>
		<div data-options="region:'center'">
			<iframe src="<c:url value="/division/detail.do"/>" name="rightDivisionFrame" id="rightDivisionFrame" height="99%" width="100%" frameborder="0"></iframe>
		</div>
	</div>
  </body>
</html>
