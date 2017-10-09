<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/taglib/c.tld" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<!-- <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"> -->
<html>
<head>
	<c:import url="/jsp/common/include.jsp"></c:import>
	<script type="text/javascript" src="<c:url value="/js/common/application-global.js"/>"></script>
	<link rel="stylesheet" type="text/css" href="<c:url value="/system/layout/skin/style.css"/>" />
	<link rel="stylesheet" type="text/css" href="<c:url value="/system/layout/skin/position.css"/>" />
	<link rel="stylesheet" type="text/css" href="<c:url value="/system/layout/skin/reset.css"/>" />
	<title><spring:message code="system.config.name" /></title>
	<script>
		$(function(){
			//$('#frameViewDiv').layout('resize',{height:document.documentElement.clientHeight});
		});
		function modifyPassword() {
			var url = '<c:url value="/security/user/modifyPasswordPage.do"/>';
			openDialog({onClose:function(){
				//<c:url value="/security/logout.do"/>
			},width:'40%',height:'50%',title:'修改密码',url:url});
		}
	</script>
</head>

<body>
	<div id="frameViewDiv" class="easyui-layout" data-options="fit:true">
		<div data-options="region:'north'" style="height:96px;">
			<div id="bs_bannercenter">
				<div id="bs_bannerleft">
					<div class="top_function">
						<div class="float_left  padding_right10 white">
								<div class="bs_nav">
									<div class="float_right padding_top2 padding_right5">
										<a href="#" class="easyui-linkbutton" style="color:white" iconCls="icon-user" plain="true" >${customer.user.showName }</a>
										<a href="#" class="easyui-linkbutton" style="color:white" iconCls="icon-monitor" plain="true" >${customer.ip }</a>
										<a href="#" onclick="modifyPassword()" class="easyui-linkbutton" style="color:white" iconCls="icon-key" plain="true" >修改密码</a>
										<a href="<c:url value="/security/logout.do"/>" class="easyui-linkbutton" style="color:white" iconCls="icon-no" plain="true" >退出系统</a>
										<div class="clear"></div>
									</div>
									<div class="clear"></div>
								</div>
						</div>
					</div>
				</div>
			</div>
			
			<div>
				<c:import url="/work/frame/menus.do"></c:import>
			</div>
		</div>
		<!-- 
		<div id="west" data-options="region:'west',split:false" title="<spring:message code="system.config.frame.left.title" />" style="width:210px;overflow-y:hidden">
			<script>
				var leftIframe = $("<iframe frameborder='0'></iframe>").appendTo($('#west'));
				$(leftIframe).attr("id", "leftFrame");
				$(leftIframe).attr("name", "leftFrame");
				$(leftIframe).attr("height", "100%");
				$(leftIframe).attr("width", "100%");
				$(leftIframe).attr("src","<c:url value="/work/frame/leftPage.do"/>");
			</script>
		</div>
		 -->
		<div id="center" data-options="region:'center'" style="overflow-y:hidden">
			<script>
				var leftIframe = $("<iframe frameborder='0'></iframe>").appendTo($('#center'));
				$(leftIframe).attr("id", "rightFrame");
				$(leftIframe).attr("name", "rightFrame");
				$(leftIframe).attr("height", "100%");
				$(leftIframe).attr("width", "100%");
				$(leftIframe).attr("src","<c:url value="/health/verify/manager.do"/>");
			</script>
		</div>
	</div>
  </body>
</html>
