<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/taglib/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/taglib/fmt.tld" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<c:import url="/jsp/common/include.jsp"></c:import>
<head>
<script>
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
	
	$(function(){
		$('#dg').datagrid({
			//双击事件
			onDblClickRow: function(rowIndex,rowData){
				if(rowData.areaId.length <= 0) {
					return;
				}
				var url = '<c:url value="/statistics/rzbfb.do?cycleId=${cycle.id}&areaId="/>' + rowData.areaId;
				top.openDialog({onClose:function(){},width:'90%',height:'90%',title:rowData.areaName + '认证百分比',url:url});
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
	function stat() {
		//var url = '<c:url value="/statistics/rzbfb.do" />';
		//$('#statForm').form('submit',{
		//	url : url
		//});
		statForm.submit();
	}
</script>
</head>
<body>
	<table id="dg" style="width: 100%" class="easyui-datagrid"
		title="<fmt:formatDate value="${cycle.cycleBegin}" pattern="yyyy-MM-dd"/> 至  <fmt:formatDate value="${cycle.cycleEnd}" pattern="yyyy-MM-dd"/> 周期报告"
		data-options="fitColumns:true,fit:true,toolbar:'#tb'">
		<thead>
			<tr>
				<th data-options="field:'number',width:3"></th>
				<th data-options="field:'areaId',hidden:true"></th>
				<th data-options="field:'areaName',width:10">地区</th>
				<th data-options="field:'total',width:6">总人数</th>
				<th data-options="field:'name',width:8">本地人数</th>
				<th data-options="field:'price7',width:12">已完成人数(本)</th>
				<th data-options="field:'price',width:12">本地人占比</th>
				<th data-options="field:'price3',width:10">待认证人数</th>
				<th data-options="field:'price4',width:12">待认证占比</th>
				<th data-options="field:'price5',width:15">已完成人数(本+外)</th>
				<th data-options="field:'price1',width:10">外地人数</th>
				<th data-options="field:'price2',width:12">外地人占比</th>
				<th data-options="field:'price6',width:12">已完成占比</th>
				<th data-options="field:'price8',width:12">已完成人数(外)</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${rzbfb }" var="r" varStatus="idx">
				<tr>
					<td>${idx.index + 1}<!-- 序号 --></td>
					<td>${r.ROOT_AREA_ID }</td><!-- 地区编号-->
					<td><c:out value="${r.area_name }" default="儋州市"/><!-- 地区 --></td>
					<td>${r.total } 人<!-- 总人数 --></td>
					<td>${r.bd } 人<!-- 本地人数 --></td>
					<td>${r.bd_yrz } 人</td><!-- 本地完成人数 -->
					<td><c:out value="${r.bd_bfb }" default="0%" /><!-- 本地人占比 --></td>
					<td>${r.wrz } 人<!-- 待认证人数 --></td>
					<td><c:out value="${r.wrz_bfb }" default="0%" /><!-- 待认证占比 --></td>
					<td>${r.yrz } 人<!-- 已完成人数(本+外) --></td>
					<td>${r.wd } 人<!-- 外地人数 --></td>
					<td><c:out value="${r.wd_bfb }" default="0%" /><!-- 外地人占比 --></td>
					<td><c:out value="${r.yrz_bfb }" default="0%" /><!-- 已完成占比 --></td>
					<td>${r.wd_yrz } 人<!-- 已完成人数(外) --></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<c:if test="${empty(areaId) }">
		<div id="tb" style="padding:2px 5px;">
			<form name="statForm" id="statForm">
				<input prompt="选择周期" id="cycle" name="cycleId" class="easyui-combogrid" width="200px"/>
				<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="stat()">统计</a>
			</form>
			<div>
				<a href="#" title="导出认证百分比" class="easyui-linkbutton" iconCls="icon-xls" plain="true" onclick="exportExcel()">导出统计结果</a>
			</div>
		</div>
	</c:if>
	<c:if test="${!empty(areaId) }">
		<div id="tb" style="padding:2px 5px;">
			<div>
				<a href="#" title="导出认证百分比" class="easyui-linkbutton" iconCls="icon-xls" plain="true" onclick="subExportExcel()">导出统计结果</a>
			</div>
		</div>
	</c:if>
	<script>
	function subExportExcel() {
		var url = '<c:url value="/statistics/rzbfb/export/excel.do?cycleId=${cycleId}"/>';
		var arrayId = '${areaId }';
		url += '&areaId=' + arrayId;
		window.location.href = url;
	}
	
	function exportExcel() {
		var cycleId = $('#cycle').combogrid('getValue');
		if(!cycleId || cycleId.length <=0) {
			$.messager.alert("系统提示","请选择一个周期.","info");
			return;
		}
		var url = '<c:url value="/statistics/rzbfb/export/excel.do?cycleId="/>' + $('#cycle').combogrid('getValue');
		window.location.href = url;
	}
	</script>
</body>
</html>
