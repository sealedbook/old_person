<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/taglib/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglib/code2name.tld" prefix="code2name"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<c:import url="/jsp/common/include.jsp"></c:import>
<script type="text/javascript">
$(function(){
	$('#userCreateButton').click(function(){
		if(false == $('#userForm').form('validate')) {
			return false;
		}
		$.ajax({
			type:"POST",
			url:'<c:url value="/operator/update.do" />',
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

</script>
<head>
   
</head>
  <body>
	    <form id="userForm" method="post">
			<input type="hidden" name="id" value="${operator.id }"/>
	    	<table class="tableStyle">
				<tr>
					<td width="20%" class="right">操作员姓名</td>
					<td width="30%"><input value="${operator.name }" class="easyui-textbox" name="name" id="name" data-options="required:true,validType:['length[0,10]']"/></td>
					<td width="20%" class="right">身份证号</td>
					<td width="30%">
						${operator.idCard }
						<input type="hidden" value="${operator.idCard }" name="idCard" id="idCard" />
					</td>
				</tr>
				<tr>
					<td width="20%" class="right">所属民族</td>
					<td>
						<input value="${operator.nationality }" class="easyui-combobox" name="nationality" id=nationality
							data-options="
							required:true,
							editable:false,
							valueField:'dicCode',
							textField:'dicName',
							url:'<c:url value="/dictionary/nationality/sub.do"/>'
						">
					</td>
					<td width="20%" class="right">电话号码</td>
					<td>
						<input class="easyui-textbox" name="phoneNumber" id="phoneNumber" value="${operator.phoneNumber }" data-options="validType:['blankSpace']"/>
					</td>
				</tr>
				<tr>
					<td width="20%" class="right">管理地区</td>
					<td colspan="3">
						<input name="manageAreaArray" id="areaIdArray" class="easyui-combotree" multiple data-options="
			    			onBeforeExpand:function(node){
			    				$(this).tree('options').url = '<c:url value="/organize/async/load.do"/>';
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
			    			,url:'<c:url value="/organize/tree/multiple/node.do"/>'
			    			,queryParams:{idArray:'${areaIdArray }'}
			    			,method:'post'
			    			,onlyLeafCheck:true
			    			,lines:true
			    			,checkbox:true
			    			,required:true
			    			,width:'90%'
		    			"/>
		    		</td>
				</tr>
				<tr>
					<td width="20%" class="right">家庭住址</td>
					<td colspan="3">
						<input class="easyui-textbox" data-options="validType:['blankSpace'],width:'90%'" value="${operator.homeAddress }" name="homeAddress" id="homeAddress"/>
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
