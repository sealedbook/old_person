<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/taglib/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglib/esite.tld" prefix="view" %>
<%@ taglib uri="/WEB-INF/taglib/fmt.tld" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

<head>
	<c:import url="/jsp/common/include.jsp"></c:import>
	<script src="<c:url value="/js/echarts/build/dist/echarts.js"/>"></script>
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
			//var url = '<c:url value="/statistics/qygztj.do" />';
			//$('#statForm').form('submit',{
			//	url : url
			//});
			statForm.submit();
		}
   </script>
</head>
  
  <body class="easyui-layout">
	<div id="tb" style="height:auto">
		<div>
			<form name="statForm" id="statForm" action="<c:url value="/statistics/qygztj.do" />" method="post">
				<input prompt="选择周期" id="cycle" name="cycleId" class="easyui-combogrid" width="200px"/>
				<input prompt="选择地区" id="areaId" name="areaId" value="${area.id }" class="easyui-combotree" data-options="
		   			onBeforeExpand:function(node){
		   				$(this).tree('options').queryParams.parentId = node.id;
		   			}
		   			,onSelect:function(node) {
		   				if(node.type == 'fl') {
		   					$(this).tree('expand',node.target);
							$('#areaId').combotree('setValue',node.id);
		   				}
		   				if(node.status == 'del') {
							$('#areaId').combotree('setValue',node.id);
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
		   			,width:'20%'
		  			"/>
				<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="stat()">统计</a>
			</form>
		</div>
	</div>
	
	<table style="width: 100%;height: 95%">
		<tr height="50%">
			<td width="33%">
				<div id="zdjcwcdtj" style="height:100%;width: 100%"></div>
			</td>
			<td width="33%">
				<div id="rzwcdtj" style="height:100%;width: 100%"></div>
			</td>
			<td width="33%">
				<div id="bdrzwcdtj" style="height:100%;width: 100%"></div>
			</td>
		</tr>
		<tr height="50%">
			<td width="33%">
				<div id="wdrzwcdtj" style="height:100%;width: 100%"></div>
			</td>
			<td width="33%">
				<div id="gkxxtj" style="height:100%;width: 100%"></div>
			</td>
			<td width="33%">
				<div id="yhzbtj" style="height:100%;width: 100%"></div>
			</td>
		</tr>
	</table>
	<script>
		// 路径配置
	require.config({
			paths : {
				echarts : '<c:url value="/js/echarts/build/dist"/>'
			}
		});
	require([ 'echarts', 'echarts/theme/macarons', 'echarts/chart/line','echarts/chart/pie'], function(ec, theme) {
		// 基于准备好的dom，初始化echarts图表
		var gkxxtjChar = ec.init(document.getElementById('gkxxtj'), theme);
		gkxxtjOption = {
		    title : {
		        text: '${area.name}-概括统计',
		        subtext: '',
		        x:'center'
		    },
		    tooltip : {
		        trigger: 'item',
		        formatter: "{a} <br/>{b} : {c} ({d}%)"
		    },
		    //legend: {
		    //    orient : 'vertical',
		    //    x : 'left',
		    //    data:['已认证','未检测','待审核']
		    //},
		    calculable : true,
		    series : [
		        {
		            name:'${area.name}',
		            type:'pie',
		            radius : '60%',
		            center: ['50%', '50%'],
		            data:[
		                {value:${gktj.yrz}, name:'已认证',itemStyle:{normal:{color:'#87cefa'}}},
		                {value:${gktj.total-gktj.dsh-gktj.yrz}, name:'未检测',itemStyle:{normal:{color:'#ff7f50'}}},
		                {value:${gktj.dsh}, name:'待审核',itemStyle:{normal:{color:'#ffd700'}}}
		            ]
		        }
		    ]
		};
		// 为echarts对象加载数据
		gkxxtjChar.setOption(gkxxtjOption);
		
	});
	
	require([ 'echarts', 'echarts/theme/macarons', 'echarts/chart/line','echarts/chart/pie'], function(ec, theme) {
		// 基于准备好的dom，初始化echarts图表
		var yhzbtjChar = ec.init(document.getElementById('yhzbtj'), theme);
		yhzbtjOption = {
		    title : {
		        text: '${area.name}-用户占比统计',
		        subtext: '',
		        x:'center'
		    },
		    tooltip : {
		        trigger: 'item',
		        formatter: "{a} <br/>{b} : {c} ({d}%)"
		    },
		    calculable : true,
		    series : [
		        {
		            name:'${area.name}',
		            type:'pie',
		            radius : '60%',
		            center: ['50%', '50%'],
		            data:[
		                {value:${yhzbtj.bd}, name:'本地人'},
		                {value:${yhzbtj.wd}, name:'外地人'}
		            ]
		        }
		    ]
		};
		// 为echarts对象加载数据
		yhzbtjChar.setOption(yhzbtjOption);
		
	});
	
	
	require([ 'echarts', 'echarts/theme/macarons', 'echarts/chart/line','echarts/chart/gauge'], function(ec, theme) {
		// 基于准备好的dom，初始化echarts图表
		var zdjcwcdtjChar = ec.init(document.getElementById('zdjcwcdtj'), theme);
		zdjcwcdtjOption = {
			title : {
		        text: '${area.name}-终端检测完成度(随访人员)',
		        subtext: '',
		        x:'center'
		    },
		    tooltip : {
		        formatter: "{a} <br/>{b} : {c}%"
		    },
		    series : [
		        {
		            name:'随访人员',
		            type:'gauge',
		            detail : {formatter:'${yjczb}%'},
		            data:[{value:${yjczb}, name: '已检测占比'}]
		        }
		    ]
		};
		// 为echarts对象加载数据
		zdjcwcdtjChar.setOption(zdjcwcdtjOption);
		
	});
	
	require([ 'echarts', 'echarts/theme/macarons', 'echarts/chart/line','echarts/chart/gauge'], function(ec, theme) {
		// 基于准备好的dom，初始化echarts图表
		var rzwcdtjChar = ec.init(document.getElementById('rzwcdtj'), theme);
		rzwcdtjOption = {
			title : {
		        text: '${area.name}-认证完成度(所有随访人员)',
		        subtext: '',
		        x:'center'
		    },
		    tooltip : {
		        formatter: "{a} <br/>{b} : {c}%"
		    },
		    series : [
		        {
		            name:'随访人员',
		            type:'gauge',
		            detail : {formatter:'${yrzzb}%'},
		            data:[{value: ${yrzzb}, name: '已认证占比'}]
		        }
		    ]
		};
		// 为echarts对象加载数据
		rzwcdtjChar.setOption(rzwcdtjOption);
		
	});
	
	
	require([ 'echarts', 'echarts/theme/macarons', 'echarts/chart/line','echarts/chart/gauge'], function(ec, theme) {
		// 基于准备好的dom，初始化echarts图表
		var rzwcdtjChar = ec.init(document.getElementById('bdrzwcdtj'), theme);
		rzwcdtjOption = {
			title : {
		        text: '${area.name}-认证完成度(随访人员)',
		        subtext: '',
		        x:'center'
		    },
		    tooltip : {
		        formatter: "{a} <br/>{b} : {c}%"
		    },
		    series : [
		        {
		            name:'随访人员',
		            type:'gauge',
		            detail : {formatter:'${bdyrzzb}%'},
		            data:[{value: ${bdyrzzb}, name: '已认证占比'}]
		        }
		    ]
		};
		// 为echarts对象加载数据
		rzwcdtjChar.setOption(rzwcdtjOption);
		
	});
	
	
	
	require([ 'echarts', 'echarts/theme/macarons', 'echarts/chart/line','echarts/chart/gauge'], function(ec, theme) {
		// 基于准备好的dom，初始化echarts图表
		var rzwcdtjChar = ec.init(document.getElementById('wdrzwcdtj'), theme);
		rzwcdtjOption = {
			title : {
		        text: '${area.name}-认证完成度(其他随访人员)',
		        subtext: '',
		        x:'center'
		    },
		    tooltip : {
		        formatter: "{a} <br/>{b} : {c}%"
		    },
		    series : [
		        {
		            name:'随访人员',
		            type:'gauge',
		            detail : {formatter:'${wdyrzzb}%'},
		            data:[{value: ${wdyrzzb}, name: '已认证占比'}]
		        }
		    ]
		};
		// 为echarts对象加载数据
		rzwcdtjChar.setOption(rzwcdtjOption);
		
	});
	</script>
  </body>
</html>
