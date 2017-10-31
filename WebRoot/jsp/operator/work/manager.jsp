<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/taglib/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglib/fmt.tld" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<c:import url="/jsp/common/include.jsp"></c:import>
	<script>
	function formatDate(val,row,index) {
		return new Date(val).format('yyyy-MM-dd');
	}
	function cycleNoticeState(val,row,index) {
		if(null == val) {
			return '<font color="red">还未发布</font>';
		} else {
			return '<font color="green">' + val.receiveStatus + '</font>';
		}
	}
	$(function(){
		$('#cycle').combogrid({
	        required:true,
	        editable:false,
	        method: 'get',
	        mode: 'remote',
	        idField:'id',
	        panelWidth : '300',
	        url:'<c:url value="/cycle/list.do" />',
	        columns: [[
				{field:'cycleBegin',formatter:formatDate,title:'起始周期',width:'25%',align:'center'},
				{field:'cycleEnd',formatter:formatDate,title:'结束周期',width:'25%',align:'center'},
				{field:'notice',formatter:cycleNoticeState,title:'发布通知情况',width:'40%',align:'center'}
	        ]]
	        ,onClickRow : function(index,row) {
	        	$('#cycle').combogrid('setValue',row.id);
	        	var textShow = new Date(row.cycleBegin).format('yyyy-MM-dd') + ' 至 ' + new Date(row.cycleEnd).format('yyyy-MM-dd');
				$('#cycle').combogrid('setText',textShow);
			}
	        ,onLoadSuccess : function(data) {
	        	var begDate = '<fmt:formatDate value="${cycle.cycleBegin}" pattern="yyyy-MM-dd"/>';
	        	var endDate = '<fmt:formatDate value="${cycle.cycleEnd}" pattern="yyyy-MM-dd"/>';
	        	if(begDate.length > 0 && endDate.length > 0) {
	        		$(this).combogrid('setValue','${cycle.id}');
		    		$(this).combogrid('setText',begDate + ' 至 ' + endDate);
	        	}
	        }
	    });
	});
	function formaterWork(val,row,index) {
		var personCount = row.old_person_count;
		var healthCount = row.health_total;
		if(personCount <= 0) {
			return '0%';
		}
		var num = healthCount/personCount*100;
		num = ("" + num).substring(0,5);
		return num + '%';
	}
	</script>
</head>
  
  <body>
	<table id="dg" title="操作员工作进度" data-options="
				fit:true,
				rownumbers:true,
				singleSelect:false,
				autoRowHeight:false,
				autoRowWeight:true,
				url:'<c:url value="/operator/work/list.do" />',
				queryParams : {
					cycleId : '${cycle.id }'
				},
				toolbar:'#tb'">
		<thead>
			<tr>
				<th field="old_person_count" align="center" width="10%">随访人员数</th>
				<th field="health_total" align="center" width="10%">认证数</th>
				<th field="work" formatter="formaterWork" align="center" width="10%">工作进度</th>
				<th field="NAME" align="center" width="10%">操作员</th>
				<th field="SEX" align="center" width="5%" formatter="esite.convertCode2Name" parentId="xb">性别</th>
				<th field="ID_CARD" align="center" width="15%">身份证号</th>
				<th field="BIRTHDAY" align="center" width="10%" formatter="esite.formatDate" pattern="yyyy-MM-dd" >出生日期</th>
				<th field="MANAGE_AREA" formatter="esite.convertOrganizeCodeArray2Name" align="center" width="29%" >管理区域</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto">
		<div>
			<form name="operatorQueryForm" id="operatorQueryForm">
				<input prompt="选择周期" id="cycle" name="cycleId" class="easyui-combogrid" width="200px"/>
				<!-- <input id="name" name="name" prompt='操作员姓名' class="easyui-textbox" data-options="validType:['minLength[1]','length[0,10]']" style="width:150px">  -->
				<a class="easyui-linkbutton" iconCls="icon-search" onclick="query()">查询</a>
				<a class="easyui-linkbutton" iconCls="icon-reload" onclick="reset()">重置</a>
			</form>
		</div>
		<div>
			<a href="#" title="导出统计结果" class="easyui-linkbutton" iconCls="icon-xls" plain="true" onclick="exportExcel()">导出统计结果</a>
		</div>
	</div>
	<script type="text/javascript">

	$(function(){
		$('#dg').datagrid({
		});
	});
	
	function exportExcel() {
		var cycleId = $('#cycle').combogrid('getValue');
		if(!cycleId || cycleId.length <=0) {
			$.messager.alert("系统提示","请选择一个周期.","info");
			return;
		}
		var url = '<c:url value="/operator/work/list/export/excel.do?cycleId="/>' + $('#cycle').combogrid('getValue');
		window.location.href = url;
	}
	
	function reset() {
		$('#operatorQueryForm').form('reset');
	}
	function query() {
		var queryParamsArray = $('#operatorQueryForm').serializeArray();
		var params = {};
		$.each(queryParamsArray,function(i, field) {
			if(this.value.length > 0) {
				params[this.name] = this.value;
			}
		});
		$('#dg').datagrid('reload',params);
	}
	</script>

  </body>
</html>
