<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/taglib/c.tld" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

<head>
<c:import url="/jsp/common/include.jsp"></c:import>
<script>

$(function(){
	$('#operator').datagrid({});
	$('#oldPerson').datagrid({});
});
function formaterArea(val,row,index) {
	return val.name;
}
</script>
</head>

<body>
	<div data-options="fit:true" class="easyui-tabs">
		<div title="操作员${operatorNumber }人" >
			<table id="operator"  data-options="
						fit:true,
						rownumbers:true,
						singleSelect:false,
						autoRowHeight:false,
						autoRowWeight:true,
						pagination:true,
						pageSize:10,
						queryParams:{
							areaId:'${area.id }'
						},
						url:'<c:url value="/operator/list.do" />'">
				<thead>
					<tr>
						<th field="name" align="center" width="20%">姓名</th>
						<th field="sex" align="center" width="20%" formatter="esite.convertCode2Name" parentId="xb">性别</th>
						<th field="idCard" align="center" width="20%">身份证号</th>
						<th field="birthday" align="center" width="20%" formatter="esite.formatDate" pattern="yyyy-MM-dd" >出生日期</th>
						<th field="age" align="center" width="5%" >年龄</th>
					</tr>
				</thead>
			</table>
		</div>
		<div title="老年人${oldPersonNumber }人" >
				<table id="oldPerson"  data-options="
							fit:true,
							rownumbers:true,
							singleSelect:false,
							autoRowHeight:false,
							autoRowWeight:true,
							pagination:true,
							pageSize:10,
							queryParams:{
								areaId:'${area.id }'
							},
							url:'<c:url value="/oldperson/list.do" />'">
					<thead>
						<tr>
							<th field="name" align="center" width="10%">姓名</th>
							<th field="sex" align="center" width="5%" formatter="esite.convertCode2Name" parentId="xb">性别</th>
							<th field="idCard" align="center" width="15%">身份证号</th>
							<th field="socialNumber" align="center" width="15%">编号</th>
							<th field="birthday" align="center" width="12%" formatter="esite.formatDate" pattern="yyyy-MM-dd" >出生日期</th>
							<th field="age" align="center" width="5%" >年龄</th>
							<th field="type" align="center" width="10%" formatter="esite.convertCode2Name" parentId="lnrlb" >人员状态</th>
						</tr>
					</thead>
				</table>
		</div>
		<!-- <div title="统计信息" data-options="iconCls:'icon-sum',closable:true">暂无实现</div> -->
	</div>
</body>
</html>
