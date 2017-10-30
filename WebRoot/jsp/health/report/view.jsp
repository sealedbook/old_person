<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/taglib/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglib/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglib/code2name.tld" prefix="code2name"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<c:import url="/jsp/common/include.jsp"></c:import>
<head>
<title><spring:message code="system.config.name" /></title>
<script type="text/javascript">
	$(function(){
		$('#dg').datagrid({
			fitColumns:true
			,fixed:true
		});
	});
	function showOperatorName(val,row,index) {
		return val.name;
	}
</script>
</head>
  <body>
 	<fieldset>
 		<legend>老年人基本信息</legend>
 		<table class="tableStyle">
	   		<tr>
	   			<td width="15%" class="right">老人姓名</td>
	   			<td>${oldPerson.name}</td>
	   			<td colspan="2" rowspan="6" align="center">
	   				<img alt="xxx" style="cursor:pointer;" width="150" src="<c:url value="/oldperson/${oldPerson.id}/photo.do"/>">
	   			</td>
	   		</tr>
	   		<tr>
	   			<td class="right">老人性别</td>
	   			<td><code2name:dictionary code="${oldPerson.sex}" parentId="xb"/></td>
	   		</tr>
	   		<tr>
	   			<td class="right">身份证号</td>
	   			<td>${oldPerson.idCard}</td>
	   		</tr>
	   		<tr>
	   			<td class="right">出生日期</td>
	   			<td><fmt:formatDate value="${oldPerson.birthday}" pattern="yyyy-MM-dd"/></td>
	   		</tr>
	   		<tr>
	   			<td class="right">老人年龄</td>
	   			<td>${oldPerson.age}</td>
	   		</tr>
	   		<tr>
	   			<td class="right">所属地区</td>
	   			<td>
	   				${oldPerson.area.name}
	   			</td>
	   		</tr>
	   		<tr>
	   			<td class="right">电话号码</td>
	   			<td>${oldPerson.phoneNumber}</td>
	   			<td class="right">所属民族</td>
	   			<td><code2name:dictionary code="${oldPerson.nationality}" parentId="nationality"/></td>
	   		</tr>
	   		<tr>
	   			<td width="15%" class="right">家庭住址</td>
	   			<td colspan="3">${oldPerson.homeAddress}</td>
	   		</tr>
	   		<tr>
				<td width="15%" class="right">人员状态</td>
				<td><code2name:dictionary code="${oldPerson.type}" parentId="lnrlb"/></td>
	   			<td width="15%" class="right">退休日期</td>
	   			<td><fmt:formatDate value="${oldPerson.txrq}" pattern="yyyy-MM-dd"/></td>
	   		</tr>
	   		<tr>
	   			<td width="15%" class="right">工作单位</td>
	   			<td colspan="3">${oldPerson.workUnit}</td>
	   		</tr>
	   	</table>
 	</fieldset>
   	<fieldset>
   		<legend>老年人认证信息</legend>
   		<table class="tableStyle">
   			<tr>
	   			<td width="15%" class="right">认证时间</td>
	   			<td width="35%">
	   				<fmt:formatDate value="${health.beginDateTime}" pattern="yyyy-MM-dd"/>
	   				至
	   				<fmt:formatDate value="${health.endDateTime}" pattern="yyyy-MM-dd"/>
	   			</td>
	   			<td width="15%" class="right">认证人</td>
	   			<td>${operator.name }</td>
	   		</tr>
	   		<tr>
	   			<td colspan="4">
	   				<table id="dg" >
						<thead>
							<tr>
								<th data-options="field:'itemid',width:10,align:'center'">类别</th>
								<th data-options="field:'productid',width:10,align:'center'">检测值</th>
								<th data-options="field:'listprice',width:10,align:'center'">正常范围</th>
								<th data-options="field:'attr1',width:10,align:'center'">检测结果</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>心率（次/分）</td>
								<td>${healthResult.heartRateToString }</td>
								<td>60-100</td>
								<td>${healthResult.heartRateResult }</td>
							</tr>
							<tr>
								<td>呼吸率</td>
								<td>${healthResult.respiratoryRateToString }</td>
								<td>12-20</td>
								<td>${healthResult.respiratoryRateResult }</td>
							</tr>
							<tr>
								<td>血氧（%）</td>
								<td>${healthResult.bloodOxygenToString }</td>
								<td>90-100</td>
								<td>${healthResult.bloodOxygenResult }</td>
							</tr>
							<tr>
								<td>脉率（次/分）</td>
								<td>${healthResult.pulseRateToString }</td>
								<td>60-100</td>
								<td>${healthResult.pulseRateResult }</td>
							</tr>
							<tr>
								<td>收缩压（mmhg）</td>
								<td>${healthResult.systolicPressureToString }</td>
								<td>90-139</td>
								<td>${healthResult.systolicPressureResult }</td>
							</tr>
							<tr>
								<td>舒张压（mmhg）</td>
								<td>${healthResult.diastolicPressureToString }</td>
								<td>60-89</td>
								<td>${healthResult.diastolicPressureResult }</td>
							</tr>
						</tbody>
					</table>
				</td>
	   		</tr>
   		</table>
   	</fieldset>
   	<fieldset>
   		<legend>检测结果分析</legend>
   		<table  class="tableStyle">
   			<tr>
   				<td width="15%" class="right">心率结果分析</td>
   				<td>${xljgfx }</td>
   			</tr>
   			<tr>
   				<td width="15%" class="right">呼吸率结果分析</td>
   				<td>${hxljgfx }</td>
   			</tr>
   			<tr>
   				<td width="15%" class="right">血氧结果分析</td>
   				<td>${xyjgfx }</td>
   			</tr>
   			<tr>
   				<td width="15%" class="right">脉率结果分析</td>
   				<td>${mljgfx }</td>
   			</tr>
   			<tr>
   				<td width="15%" class="right">收缩压结果分析</td>
   				<td>${ssyjgfx }</td>
   			</tr>
   			<tr>
   				<td width="15%" class="right">舒张压结果分析</td>
   				<td>${szyjgfx }</td>
   			</tr>
   		</table>
   	</fieldset>
   	<fieldset>
   		<legend>心电图</legend>
   		<img src="<c:url value="/health/report/ecg.do?healthResultId=${healthResult.id }"/>"/>
   	</fieldset>
   	<fieldset>
   		<legend>健康小贴士</legend>
   		<table  class="tableStyle">
   			<tr>
   				<td width="15%" class="right">心率小贴士</td>
   				<td>${xljgtips }</td>
   			</tr>
   			<tr>
   				<td width="15%" class="right">呼吸率小贴士</td>
   				<td>${hxljgtips }</td>
   			</tr>
   			<tr>
   				<td width="15%" class="right">血氧小贴士</td>
   				<td>${xyjgtips }</td>
   			</tr>
   			<tr>
   				<td width="15%" class="right">收缩压小贴士</td>
   				<td>${ssyjgtips }</td>
   			</tr>
   			<tr>
   				<td width="15%" class="right">舒张压小贴士</td>
   				<td>${szyjgtips }</td>
   			</tr>
   		</table>
   	</fieldset>
  </body>
</html>
