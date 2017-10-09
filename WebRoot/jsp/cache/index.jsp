<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/taglib/esite.tld" prefix="view" %>
<%@ taglib uri="/WEB-INF/taglib/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglib/fmt.tld" prefix="fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<c:import url="/jsp/common/include.jsp"></c:import>
	<script>
		$(function(){
			$('#flushMenu').click(function(){
				var url = '<c:url value="/cache/flush/menu.do"/>';
				$.post(url,{},function(){
					$.messager.alert('系统提示','刷新成功','info');
				});
			});
			$('#flushGlobalResource').click(function(){
				var url = '<c:url value="/cache/flush/global/resource.do"/>';
				$.post(url,{},function(){
					$.messager.alert('系统提示','刷新成功','info');
				});
			});
			$('#flushDictionary').click(function(){
				var url = '<c:url value="/cache/flush/dictionary.do"/>';
				$.post(url,{},function(){
					$.messager.alert('系统提示','刷新成功','info');
				});
			});
			
		});
		
	</script>
</head>
<body>
	<a id="flushMenu" class="easyui-linkbutton" >刷新菜单</a>
	<a id="flushGlobalResource" class="easyui-linkbutton" >刷新公共资源</a>
	<a id="flushDictionary" class="easyui-linkbutton" >刷新字典</a>
	<!-- <a href="<c:url value="/jpa/test.do"/>">测试</a> -->
	
</body>
</html>