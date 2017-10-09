<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/taglib/c.tld" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<c:import url="/jsp/common/include.jsp"></c:import>
<script type="text/javascript">
$(function(){
	$('#userCreateButton').click(function(){
		$.ajax({
			type:"POST",
			url:'<c:url value="/oldperson/add.do" />',
			dataType:'text',
			data:$('#userForm').serialize()
			,beforeSend : function(XMLHttpRequest) {
				return $('#userForm').form('validate');
			}
			,error:function(xmlHttpRequest){
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
	    	<fieldset>
	    		<legend>基本信息</legend>
	    		<table class="tableStyle">
					<tr>
						<td width="20%" class="right">老人姓名</td>
						<td width="30%"><input data-options="required:true,validType:['blankSpace','length[0,10]']" class="easyui-textbox" name="name" id="name" /></td>
						<td width="20%" class="right">身份证号</td>
						<td width="30%"><input data-options="required:true,validType:['blankSpace','checkIdCard[]']" class="easyui-textbox" name="idCard" id="idCard" /></td>
					</tr>
					<tr>
						<td width="20%" class="right">所属民族</td>
						<td>
							<input class="easyui-combobox" name="nationality" id=nationality
								data-options="
								required:true,
								editable:false,
								valueField:'dicCode',
								textField:'dicName',
								url:'<c:url value="/dictionary/nationality/sub.do"/>'
							">
						</td>
						<td width="20%" class="right">电话号码</td>
						<td width="30%"><input class="easyui-textbox" data-options="validType:['blankSpace']" name="phoneNumber" id="phoneNumber"/></td>
					</tr>
					<tr>
						<td width="20%" class="right">所属地区</td>
						<td colspan="3">
							<input name="area.id" id="organizeTree"  class="easyui-combotree" data-options="
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
				    			,panelHeight:160
				    			,required:true
				    			,width:'95%'
			    			"/>
			    		</td>
					</tr>
					<tr>
						<td width="20%" class="right">家庭住址</td>
						<td colspan="3">
							<input class="easyui-textbox" data-options="validType:['blankSpace'],width:'95%'" name="homeAddress" id="homeAddress"/>
			    		</td>
					</tr>
		    	</table>
	    	</fieldset>
	    	<fieldset>
	    		<legend>社保信息</legend>
	    		<table class="tableStyle">
	    			<tr>
		    			<td width="20%" class="right">社保编号</td>
						<td width="30%" colspan="3">
							<input class="easyui-textbox" data-options="validType:['blankSpace','length[0,18]']" name="socialNumber" id="socialNumber"/>
							<a href="#" onclick="copyIdCard()">同身份证号</a>
						</td>
	    			</tr>
	    			<tr>
		    			<td width="20%" class="right">缴纳社保</td>
						<td width="30%">
							<input id="jnsbrq" name="jnsbrq" class="easyui-datebox" 
								data-options="editable:false,validType:{
									
								}" />
						</td>
						<td width="20%" class="right">退休日期</td>
						<td width="30%">
							<input id="txrq" name="txrq" class="easyui-datebox" 
								data-options="editable:false,validType:{
									
								}" />
						</td>
	    			</tr>
	    			<tr>
		    			<td width="20%" class="right">领取社保</td>
						<td width="30%">
							<input id="lqsbrq" name="lqsbrq" class="easyui-datebox" 
								data-options="editable:false,validType:{
									
								}" />
						</td>
						<td width="20%" class="right" colspan="2"></td>
	    			</tr>
	    			<tr>
	    				<td width="20%" class="right">身份类型</td>
						<td width="30%" >
							<input class="easyui-combobox" value="sflx01" name="sflx" id="sflx"
									data-options="
									required:true,
									editable:false,
									valueField:'dicCode',
									textField:'dicName',
									url:'<c:url value="/dictionary/sflx/sub.do"/>'
									,onChange:function(newValue, oldValue) {
										if(newValue == 'sflx01') {
											$('#workUnitTr').show();
											$('#workUnit').textbox('enableValidation');
										} else {
											$('#workUnitTr').hide();
											$('#workUnit').textbox('disableValidation');
										}
									}
								">
						</td>
						<td width="20%" class="right">人员状态</td>
						<td width="30%">
							<input class="easyui-combobox" name="type" id="type"
								data-options="
								required:true,
								editable:false,
								valueField:'dicCode',
								textField:'dicName',
								url:'<c:url value="/dictionary/lnrlb/sub.do"/>'
							">
						</td>
	    			</tr>
	    			<tr id="workUnitTr">
	    				<td width="20%" class="right">工作单位</td>
	    				<td colspan="3"><input class="easyui-textbox" data-options="width:'95%',required:true,validType:['blankSpace','length[0,100]']" name="workUnit" id="workUnit"/></td>
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
