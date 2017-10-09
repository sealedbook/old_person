<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/taglib/c.tld" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<c:import url="/jsp/common/include.jsp"></c:import>
	<title><spring:message code="system.config.name" /></title>
	<script>
	function cencle() {
		top.closeDialog();
	}
	function ok() {
		alert($('#organizeTree').tree('getChecked').length);
	}
	</script>
</head>

<body style="height: 200px">
	<a href="#" title="划分所选的区域" class="easyui-linkbutton" iconCls="icon-ok" plain="true" onclick="ok();">划分</a>
	<a href="#" title="取消" class="easyui-linkbutton" iconCls="icon-no" plain="true" onclick="cencle();">取消</a>
	<div class="easyui-panel" style="padding:5px">
		<ul id="organizeTree"></ul>
	</div>
	<script>
		$(function(){
			$('#organizeTree').tree({
				onBeforeExpand:function(node){
    				$(this).tree('options').queryParams.parentId = node.id;
    			}
    			,onSelect:function(node) {
    				if(!$(this).tree('isLeaf',node.target)) {
    					$(this).tree('expand',node.target);
    				} else {
    					$(this).tree('getNode',node.target).checked == true?$(this).tree('uncheck',node.target) : $(this).tree('check',node.target);
    					
    				}
    			}
    			,onBeforeCheck : function(node, checked) {
    				if(node.status == 'del') {
						return false;
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
    			,checkbox:true
    			,onlyLeafCheck:true
    			,lines:true
			});
			
		});
	</script>
</body>
</html>
