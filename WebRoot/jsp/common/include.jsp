<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/WEB-INF/taglib/c.tld" prefix="c" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<script>
var contextPath = '<%=path%>';
</script>
<link id="dlink" rel="stylesheet" type="text/css" href="<c:url value="/js/easyui/themes/gray/easyui.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/js/easyui/themes/icon.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/js/easyui/themes/table.css"/>" />

<script type="text/javascript" src="<c:url value="/js/jquery/jquery.min.1.11.1.js" />"></script>
<script type="text/javascript" src="<c:url value="/js/easyui/jquery.easyui.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/easyui/locale/easyui-lang-zh_CN.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/common/dataGridFormater.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/common/easyui-form-validator.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/common/easyui-default-value.js"/>"></script>
