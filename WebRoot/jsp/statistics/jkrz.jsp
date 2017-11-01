<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/taglib/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/taglib/fmt.tld" prefix="fmt"%>	
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE html>
<html>

<head>
<c:import url="/jsp/common/include.jsp"></c:import>
<script src="<c:url value="/js/echarts/build/dist/echarts.js"/>"></script>

<script type="text/javascript">
	// 路径配置
	require.config({
		paths : {
			echarts : '<c:url value="/js/echarts/build/dist"/>'
		}
	});
	require([ 'echarts', 'echarts/theme/macarons', 'echarts/chart/bar' // 使用柱状图就加载bar模块，按需加载
	], function(ec, theme) {
		// 基于准备好的dom，初始化echarts图表
		var myChart = ec.init(document.getElementById('main'), theme);

		option = {
		 	title : {
		        text: '${statisticsName}',
		        subtext: '',
		        x:'center'
		    },
		    toolbox: {
		        show : true,
		        feature : {
		            saveAsImage : {show: true}
		        }
		    },
			tooltip : {
				trigger : 'axis',
				axisPointer : { // 坐标轴指示器，坐标轴触发有效
					type : 'line' // 默认为直线，可选为：'line' | 'shadow'
				}
			},
			legend : {
				orient : 'horizontal',//horizontal vertical
				x : 'center',
				y : 'bottom',
				data : [ '已完成', '未完成']
			},
			calculable : false,
			xAxis : [ {
				type : 'category',
				data : [${title}]

			} ],
			yAxis : [ {
				type : 'value'
			} ],
			series : [
					{
						name : '已完成',
						type : 'bar',
						stack : '本地',
						data : [${bdYrz}]
					},
					{
						name : '未完成',
						type : 'bar',
						stack : '本地',
						data : [${bdWrz}]
					}]
		};

		// 为echarts对象加载数据
		myChart.setOption(option);
	});
</script>
</head>
<body>
	<div id="tb" style="padding:2px 5px;">
		<form name="statForm" id="statForm">
			<input prompt="选择周期" id="cycle" name="cycleId" class="easyui-combogrid" width="200px"/>
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="stat()">统计</a>
		</form>
	</div>
	<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
	<div id="main" style="height:500px"></div>
</body>
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
	
});
function stat() {
	//var url = '<c:url value="/statistics/jkrz.do" />';
	//$('#statForm').form('submit',{
	//	url : url
	//});
	statForm.submit();
}
</script>
</html>
