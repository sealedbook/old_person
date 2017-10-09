<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/taglib/c.tld" prefix="c"%>
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
	require([ 'echarts', 'echarts/theme/macarons', 'echarts/chart/radar'
	], function(ec, theme) {
		// 基于准备好的dom，初始化echarts图表
		var myChart = ec.init(document.getElementById('main'), theme);

		option = {
			    title : {
			        text: '老年人本次认证 vs 本期平均值',
			        subtext: ''
			    },
			    tooltip : {
			        trigger: 'axis'
			    },
			    legend: {
			        orient : 'vertical',
			        x : 'right',
			        y : 'bottom',
			        data:['本次','本期平均']
			    },
			    polar : [
			       {
			           indicator : [
			               { text: '心率', max: 100},
			               { text: '呼吸率', max: 20},
			               { text: '血氧', max: 100},
			               { text: '脉率', max: 100},
			               { text: '收缩压', max: 139},
			               { text: '舒张压', max: 89}
			            ]
			        }
			    ],
			    calculable : true,
			    series : [
			        {
			            name: '本次 vs 本期平均',
			            type: 'radar',
			            data : [
			                {
			                    value : [${thisResult}],
			                    name : '本次'
			                },
			                 {
			                    value : [${avgHealthResult}],
			                    name : '本期平均'
			                }
			            ]
			        }
			    ]
			};

		// 为echarts对象加载数据
		myChart.setOption(option);
	});
</script>
</head>
<body>
	<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
	<div id="main" style="height:400px"></div>
</body>
</html>
