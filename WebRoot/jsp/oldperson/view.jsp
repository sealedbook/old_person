<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="/WEB-INF/taglib/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglib/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/taglib/code2name.tld" prefix="code2name" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<c:import url="/jsp/common/include.jsp"></c:import>
<head>
    <title><spring:message code="system.config.name"/></title>
    <script type="text/javascript">
      $(function () {
        $('#dg').datagrid({
          //双击事件
          onDblClickRow: function (rowIndex, rowData) {
            var url = '<c:url value="/health/report/"/>' + rowData.id + '/view.do';
            top.openDialog({
              onClose: function () {
              }, width: '50%', height: '90%', title: '认证信息', url: url
            });
          }
        });
        $('#healthMiss').datagrid({
          //双击事件
          onDblClickRow: function (rowIndex, rowData) {
            var url = '<c:url value="/health/report/"/>' + rowData.id + '/view.do';
            top.openDialog({
              onClose: function () {
              }, width: '50%', height: '90%', title: '认证信息', url: url
            });
          }
        });
        $('#wddg').datagrid({});

      });

      function showOperatorName(val, row, index) {
        return val.name;
      }

      function cjrz(val, row, index) {
        return row.hasHealthResult == 'true' ? '是' : '否';
      }
    </script>
</head>
<body>
<table class="tableStyle">
    <tr>
        <td width="15%" class="right">老人姓名</td>
        <td>${oldPerson.name}</td>
        <td colspan="2" rowspan="6" align="center">
            <img alt="xxx" style="cursor:pointer;" width="150"
                 src="<c:url value="/oldperson/${oldPerson.id}/photo.do"/>">
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
        <td width="84%" colspan="3">
            <input class="easyui-textbox" readonly="true" data-options="validType:['blankSpace'],width:'95%',height:'120px'" multiline="true"  value="${oldPerson.homeAddress}"/>
        </td>
    </tr>
    <tr>
        <td width="15%" class="right">退休日期</td>
        <td><fmt:formatDate value="${oldPerson.txrq}" pattern="yyyy-MM-dd"/></td>
        <td class="right">建模状态</td>
        <td><code2name:dictionary code="${oldPerson.modelingStatus}" parentId="lnrModelingStatus"/></td>
    </tr>
    <tr>
        <td width="15%" class="right">工作单位</td>
        <td width="84%" colspan="3"><input class="easyui-textbox" readonly="true" multiline="true"  data-options="width:'95%',height:'120px'" value="${oldPerson.workUnit}"/></td>
    </tr>
</table>
<c:choose>
    <c:when test="${oldPerson.type == '1' }">
        <table id="dg" title="认证记录" data-options="
						rownumbers:true,
						singleSelect:false,
						pagination:true,
						pageSize:10,
						url:'<c:url value="/health/list.do" />'
						,queryParams:{
							oldPersonId:'${oldPerson.id }'
						}
						,panelHeight:'500'
						">
            <thead>
            <tr>
                <th field="beginDateTime" formatter="esite.formatDate" pattern="yyyy-MM-dd HH:mm" align="center"
                    width="20%">开始认证时间
                </th>
                <th field="useTime" align="center" width="20%">用时</th>
                <th field="photo" align="center">照片认证</th>
                <th field="fmt" align="center" formatter="cjrz">采集认证</th>
                <th field="operator" formatter="showOperatorName" align="center" width="10%">操作员</th>
                <th field="fingerVerifyState" formatter="esite.convertCode2Name" parentId="fingerVerifyState"
                    align="center" width="15%">指纹识别
                </th>
                <th field="verifyState" formatter="esite.convertCode2Name" parentId="shzt" align="center" width="15%">
                    最终认证状态
                </th>
                <th field="insertDateTime" formatter="esite.formatDate" pattern="yyyy-MM-dd HH:mm" align="center"
                    width="18%">同步时间
                </th>
            </tr>
            </thead>
        </table>

        <table id="healthMiss" title="失访记录" data-options="
						rownumbers:true,
						singleSelect:false,
						pagination:true,
						pageSize:10,
						url:'<c:url value="/health/miss/list.do" />'
						,queryParams:{
							oldPersonId:'${oldPerson.id }'
						}
						,panelHeight:'500'
						">
            <thead>
            <tr>
                <th field="missCause"align="center"
                    width="80%">失访原因
                </th>
                <th field="missDate" formatter="esite.formatDate" pattern="yyyy-MM-dd HH:mm" align="center" width="20%">失访时间</th>
            </tr>
            </thead>
        </table>
    </c:when>
    <c:otherwise>
        <table id="wddg" title="认证记录" data-options="
						rownumbers:true,
						singleSelect:false,
						pagination:true,
						pageSize:10,
						url:'<c:url value="/health/wd/list.do" />'
						,queryParams:{
							oldPersonId:'${oldPerson.id }'
						}
						,panelHeight:'500'
						">
            <thead>
            <tr>
                <th field="insertUserName" align="center" width="15%">提交人</th>
                <th field="insertIpAddress" align="center" width="30%">提交地址</th>
                <th field="verifyState" formatter="esite.convertCode2Name" parentId="shzt" align="center" width="30%">
                    最终认证状态
                </th>
                <th field="insertDateTime" formatter="esite.formatDate" pattern="yyyy-MM-dd HH:mm" align="center"
                    width="23%">同步时间
                </th>
            </tr>
            </thead>
        </table>
    </c:otherwise>
</c:choose>

</body>
</html>
