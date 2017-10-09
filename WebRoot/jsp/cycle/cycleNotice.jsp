<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/taglib/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglib/fmt.tld" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<c:import url="/jsp/common/include.jsp"></c:import>
<script type="text/javascript">
$(function(){
	$('.areaSelector').hide();
	$('#userCreateButton').click(function(){
		$.ajax({
			type:"POST",
			url:'<c:url value="/cycle/${cycle.id }/notice.do" />',
			dataType:'text',
			data:$('#userForm').serialize(),
			error:function(xmlHttpRequest){
				$.messager.alert('系统提示',xmlHttpRequest.responseText,'error');
			},
			success:function(data){
				$.messager.alert('系统提示','保存成功.','info');
				top.closeDialog();
			}
		});
	});
	
	$('input[name="noticeType"]').change(function(){
		if($('input[name="noticeType"]:checked').val() == 'noticeTypeArea') {
			$('.areaSelector').show();
		} else {
			$('.areaSelector').hide();
		}
	});
});

</script>
<head>
   
</head>
  <body>
	    <form id="userForm" method="post">
	    	<table class="tableStyle">
	    		<tr>
					<td width="20%" class="right">周期设置</td>
					<td width="30%">
						<fmt:formatDate value="${cycle.cycleBegin }" pattern="yyyy-MM-dd"/> 
						至 
						<fmt:formatDate value="${cycle.cycleEnd }" pattern="yyyy-MM-dd"/> 
					</td>
				</tr>
				<tr>
					<td width="20%" class="right">发送方式</td>
					<td width="30%">
						<input value="noticeTypeAll" id="noticeTypeAll" name="noticeType" type="radio" checked="checked"/>全部(操作员)<br/>
						<input value="noticeTypeArea" id="noticeTypeArea" name="noticeType" type="radio" />按管辖(管辖内所有操作员)
					</td>
				</tr>
				<tr class="areaSelector">
					<td width="20%" class="right">选择管辖</td>
					<td>
						<input style="width: 70%" name="noticeAreaCollection" id="noticeAreaCollection" class="easyui-combotree" multiple data-options="
			    			onBeforeExpand:function(node){
			    				$(this).tree('options').queryParams.parentId = node.id;
			    			}
			    			,onSelect:function(node) {
			    				if(!$(this).tree('isLeaf',node.target)) {
			    					$(this).tree('expand',node.target);
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
			    			,onlyLeafCheck:true
			    			,lines:true
		    			"/>
		    		</td>
				</tr>
				<tr>
					<td width="20%" class="right">通知内容</td>
					<td width="30%">
						<textarea cols="40" rows="15" name="content"></textarea>
					</td>
				</tr>
	    	</table>
	    </form>
	    <div style="text-align:center;padding:5px">
	    	<a href="javascript:void(0)" class="easyui-linkbutton" id="userCreateButton">提 交</a>
	    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="top.closeDialog();">关 闭</a>
	    </div>
  </body>
</html>
