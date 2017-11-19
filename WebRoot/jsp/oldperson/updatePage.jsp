<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/taglib/c.tld" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<c:import url="/jsp/common/include.jsp"></c:import>
<script type="text/javascript">
$(function(){
	$('#userCreateButton').click(function(){
		if(false == $('#userForm').form('validate')) {
			return;
		}
		$.ajax({
			type:"POST",
			url:'<c:url value="/oldperson/update.do" />',
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
});
function copyIdCard() {
	var idCard = $('#idCard').textbox('getValue');
	$('#socialNumber').textbox('setValue',idCard);
	$('#socialNumber').textbox('setText',idCard);
}
</script>
<head>
   
</head>
  <body>
	    <form id="userForm" method="post">
	    	<input type="hidden" name="id" value="${oldPerson.id }"/>
	    	<fieldset>
	    		<legend>基本信息</legend>
		    	<table class="tableStyle">
					<tr>
						<td width="20%" class="right">人员姓名</td>
						<td width="30%"><input data-options="required:true,validType:['minLength[2]','length[0,10]']" class="easyui-textbox" name="name" id="name" value="${oldPerson.name }" /></td>
						<td width="20%" class="right">身份证号</td>
						<td width="30%">
							<c:if test="${updateIdCardable }">
								<input name="idCard" id="idCard" data-options="required:true,validType:['blankSpace','checkIdCard[]']" class="easyui-textbox" value="${oldPerson.idCard }"/>
							</c:if>
							<c:if test="${!updateIdCardable }">
								<input type="hidden" name="idCard" id="idCard" value="${oldPerson.idCard }"/>
								<input data-options="disabled:true" class="easyui-textbox" value="${oldPerson.idCard }"/>
							</c:if>
						</td>
					</tr>
					<tr>
						<td width="20%" class="right">基线队列编号</td>
						<td width="30%"><input data-options="required:true,validType:['blankSpace','length[14,17]']" class="easyui-textbox" name="baseQueueCode" id="baseQueueCode" value="${oldPerson.baseQueueCode}" /></td>
						<td width="20%" class="right">基线调查时间</td>
						<td width="30%"><input data-options="required:true" class="easyui-datetimebox" name="baseQueueTime" id="baseQueueTime" value="${oldPerson.baseQueueTime}" /></td>
					</tr>
					<tr>
						<td width="20%" class="right">基线队列住址</td>
						<td colspan="3">
							<input class="easyui-textbox" data-options="validType:['blankSpace'],width:'95%',height:'60px'" multiline="true"  name="baseQueueAddress" id="baseQueueAddress" value="${oldPerson.baseQueueAddress}"/>
						</td>
					</tr>
					<tr>
						<td width="20%" class="right">所属民族</td>
						<td>
							<input value="${oldPerson.nationality }" class="easyui-combobox" name="nationality" id=nationality
								data-options="
								required:true,
								editable:false,
								valueField:'dicCode',
								textField:'dicName',
								url:'<c:url value="/dictionary/nationality/sub.do"/>'
							">
						</td>
						<td width="20%" class="right">电话号码</td>
						<td width="30%"><input class="easyui-textbox" data-options="validType:['blankSpace']" value="${oldPerson.phoneNumber }" name="phoneNumber" id="phoneNumber"/></td>
					</tr>
					<tr>
						<td width="20%" class="right">所属地区</td>
						<td width="30%" colspan="3">
							<input  value="${oldPerson.area.id}" style="width: 90%" name="area.id" id="organizeTree"  class="easyui-combotree" data-options="
				    			required:true
				    			,onBeforeExpand:function(node){
				    				$(this).tree('options').url = '<c:url value="/organize/async/load.do"/>';
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
				    			,url:'<c:url value="/organize/tree/node.do"/>'
				    			,queryParams:{id:'${oldPerson.area.id}'}
				    			,method:'post'
				    			,lines:true
				    			,panelHeight:160
			    			"/>
			    		</td>
					</tr>
					<tr>
						<td width="20%" class="right">家庭住址</td>
						<td colspan="3">
							<input class="easyui-textbox" data-options="validType:['blankSpace'],width:'90%'" value="${oldPerson.homeAddress }" name="homeAddress" id="homeAddress"/>
			    		</td>
					</tr>
		    	</table>
			</fieldset>

	    	<div style="text-align:center;padding:5px">
		    	<a href="javascript:void(0)" class="easyui-linkbutton" id="userCreateButton">提 交</a>
		    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="top.closeDialog();">关 闭</a>
		    </div>
	    </form>
  </body>
</html>
