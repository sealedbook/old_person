<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/taglib/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglib/fmt.tld" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<c:import url="/jsp/common/include.jsp"></c:import>
<head>
   <script>
   $(function(){
		$('#cycle').combogrid({
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
   function formatOldPerson(val,row,index) {
	   if(null == val) {
		   return '系统中随访人员被意外删除';
	   }
	   return val.name;
   }
   function query() {
	   var formSerializeArray = $('#verifyForm').serializeArray();
	   var param = {};
	   $.each(formSerializeArray,function(i, field){
		   param[this.name] = this.value;
	   });
	   $('#dg').datagrid('reload',param);
   }
   function reset() {
	   $('#verifyForm').form('reset');
   }
   function convertNullToEmpty(val,row,index) {
	   if(val <= 0) {
		   return '-';
	   }
	   return val;
   }
   </script>
</head>
  
  <body>
	<table id="dg" title="认证报告-审核通过的认证" style="width:98%;height:750px;" data-options="
				rownumbers:true,
				fit : true,
				singleSelect:false,
				autoRowHeight:false,
				autoRowWeight:true,
				pagination:true,
				pageSize:10,
				url:'<c:url value="/health/verify/list.do" />',
				queryParams : {verifyState:'1'},
				toolbar:'#tb'">
		<thead>
			<tr>
				<th field="ck" checkbox="true"></th>
				<th field="oldPerson" formatter="formatOldPerson" align="center" width="10%">随访人员</th>
				<th field="beginDateTime" formatter="esite.formatDate"  pattern="yyyy-MM-dd HH:mm" align="center" width="17%">开始认证时间</th>
				<th field="endDateTime" formatter="esite.formatDate"  pattern="yyyy-MM-dd HH:mm" align="center" width="17%">完成认证时间</th>
				<th field="heartRate" formatter="convertNullToEmpty" align="center" width="7%" >心率</th>
				<th field="respiratoryRate" formatter="convertNullToEmpty" align="center" width="7%" >呼吸率</th>
				<th field="bloodOxygen" formatter="convertNullToEmpty" align="center" width="7%" >血氧</th>
				<th field="pulseRate" formatter="convertNullToEmpty" align="center" width="7%" >脉率</th>
				<th field="systolicPressure" formatter="convertNullToEmpty" align="center" width="7%" >收缩压</th>
				<th field="diastolicPressure" formatter="convertNullToEmpty" align="center" width="7%" >舒张压</th>
				<th field="verifyState" formatter="esite.convertCode2Name" parentId="shzt" align="center" width="10%" >审核状态</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto">
		<form name="verifyForm" id="verifyForm">
			<input type="hidden" name="verifyState" value="1"/>
			<input prompt="选择周期" id="cycle" name="cycleId" class="easyui-combogrid" width="200px"/>
			<input id="oldPersonName" name="oldPersonName" prompt='随访人员姓名' class="easyui-textbox" data-options="validType:['minLength[1]','length[0,10]']" style="width:150px">
			<input id="oldPersonIdCard" name="oldPersonIdCard" prompt='随访人员身份证号' class="easyui-textbox" style="width:150px">
			<a class="easyui-linkbutton" iconCls="icon-search" onclick="query()">查询</a>
			<a class="easyui-linkbutton" iconCls="icon-reload" onclick="reset()">重置</a>
		</form>
		<a href="#" title="下载随访人员报告(Word文档)" class="easyui-linkbutton" iconCls="icon-word" plain="true" onclick="downReportWord();">认证报告-个人(Word)</a>
		<a href="#" title="批量下载随访人员报告(Word文档)" class="easyui-linkbutton" iconCls="icon-word" plain="true" onclick="downReportCollectionWord();">认证报告-批量(Word)</a>
		<!-- <a href="#" title="下载随访人员报告(PDF文档)" class="easyui-linkbutton" iconCls="icon-acrobat" plain="true" onclick="$.messager.alert('系统提示','该功能还没实现.');">认证报告(PDF)</a> -->
		<a href="#" title="下载随访人员报告(Excel文档)" class="easyui-linkbutton" iconCls="icon-xls" plain="true" onclick="exportExcel()">身份认证统计表-批量(Excel)</a>
		<a href="#" title="在线预览认证报告" class="easyui-linkbutton" iconCls="icon-office" plain="true" onclick="onlineView()">认证报告</a>
		|
		<a href="#" title="查看认证结果" class="easyui-linkbutton" iconCls="icon-page_paste" plain="true" onclick="healthResult()">结果比对</a>
	</div>
	<script type="text/javascript">

	$(function(){
		$('#dg').datagrid({
			//双击事件
			onDblClickRow: function(rowIndex,rowData){
				//var url = '<c:url value="/health/result/"/>' + rowData.id + '/view.do';
				var url = '<c:url value="/health/report/"/>' + rowData.id + '/view.do';
				top.openDialog({width:'50%',height:'80%',title:'随访人员【'+ rowData.oldPerson.name + '】的认证报告',url:url});
			}
		});
	});
	
	function onlineView() {
		var row  = $('#dg').datagrid("getSelections");
		if(row.length == 0 || row.length > 1){
			$.messager.alert("系统提示","请选择一项","info");
		}else{
			if(null == row[0].oldPerson) {
				$.messager.alert("系统提示","该信息已不存在.","info");
				return;
			}
			var healthId = row[0].id;
			var url = '<c:url value="/health/report/"/>' + healthId + '/view.do';
			top.openDialog({width:'50%',height:'100%',title:'随访人员【'+ row[0].oldPerson.name + '】的认证报告',url:url});
		}
	}
	
	function healthResult() {
		var row  = $('#dg').datagrid("getSelections");
		if(row.length == 0 || row.length > 1){
			$.messager.alert("系统提示","请选择一项","info");
		}else{
			if(null == row[0].oldPerson) {
				$.messager.alert("系统提示","该信息已不存在.","info");
				return;
			}
			var healthId = row[0].id;
			var url = '<c:url value="/health/result/"/>' + healthId + '/view.do';
			top.openDialog({width:'50%',height:'80%',title:'随访人员【'+ row[0].oldPerson.name + '】的认证结果比对',url:url});
		}
	}
	function downReportWord() {
		var row  = $('#dg').datagrid("getSelections");
		if(row.length == 0 || row.length > 1){
			$.messager.alert("系统提示","请选择一项","info");
		}else{
			if(null == row[0].oldPerson) {
				$.messager.alert("系统提示","该信息已不存在.","info");
				return;
			}
			var healthId = row[0].id;
			var url = '<c:url value="/health/report/"/>' + healthId + '/download.do';
			window.location.href = url;
		}
	}
	
	function downReportCollectionWord() {
		var cycleId = $('#cycle').combogrid('getValue');
		if(!cycleId || cycleId.length <=0) {
			$.messager.alert("系统提示","请选择一个周期.","info");
			return;
		}
		var url = '<c:url value="/health/report/export/word.do?cycleId="/>' + $('#cycle').combogrid('getValue');
		window.location.href = url;
	}
	
	function exportExcel() {
		var cycleId = $('#cycle').combogrid('getValue');
		if(!cycleId || cycleId.length <=0) {
			$.messager.alert("系统提示","请选择一个周期.","info");
			return;
		}
		var url = '<c:url value="/health/report/export/excel.do?cycleId="/>' + $('#cycle').combogrid('getValue');
		window.location.href = url;
	}

	</script>

  </body>
</html>
