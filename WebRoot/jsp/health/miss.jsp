<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="/WEB-INF/taglib/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglib/fmt.tld" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

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
      function formatOldPerson(val, row, index) {
        if (null == val) {
          return '系统中随访人员被意外删除';
        }
        return row.oldPerson.name;
      }

      function formatCycleBegin(val, row, index) {
        if (null == val) {
          return '系统中周期被删除';
        }
        var msg = new Date(row.cycle.cycleBegin).format('yyyy-MM-dd') + "至" + new Date(row.cycle.cycleEnd).format('yyyy-MM-dd');
        return msg;
      }

      function formatCycleEnd(val, row, index) {
        if (null == val) {
          return '系统中周期被删除';
        }
        return val.cycleEnd;
      }

      function query() {
        var formSerializeArray = $('#verifyForm').serializeArray();
        var param = {};
        $.each(formSerializeArray, function (i, field) {
          param[this.name] = this.value;
        });
        $('#dg').datagrid('reload', param);
      }

      function reset() {
        $('#verifyForm').form('reset');
      }

      function showOperatorName(val, row, index) {
        if (null == val) {
          return '系统中操作员被意外删除';
        }
        return val.name;
      }

      function formatOldPersonArea(val, row, index) {
        if (null == row.oldPerson) {
          return '系统中随访人员被意外删除';
        }
        return row.oldPerson.area.name;
      }
    </script>
</head>

<body>
<table id="dg" title="失访记录" style="width:98%;height:750px;" data-options="
				rownumbers:true,
				fit : true,
				singleSelect:false,
				autoRowHeight:false,
				autoRowWeight:true,
				pagination:true,
				pageSize:10,
				url:'<c:url value="/miss/list.do" />',
				toolbar:'#tb'">
    <thead>
    <tr>
        <th field="oldPerson" formatter="formatOldPerson" align="center" width="12%">随访人员姓名</th>
        <th field="cycle" formatter="formatCycleBegin" align="center" width="12%">周期</th>
        <th field="missCause" align="center" width="12%">失访原因</th>
        <th field="missDate" formatter="formatDate" align="center" width="12%">失访登记时间</th>
    </tr>
    </thead>
</table>
<div id="tb" style="padding:5px;height:auto">
    <form name="verifyForm" id="verifyForm">

        <input prompt="选择周期" id="cycle" name="cycleId" class="easyui-combogrid" width="200px"/>

        <input id="oldPersonId" name="oldPersonId" prompt='随访人员' class="easyui-combogrid" data-options="
			 			panelWidth: 500,
								idField: 'id',
								textField: 'name',
								url: '<c:url value="/oldperson/list.do" />',
								method: 'get',
								columns: [[
									{field:'idCard',title:'身份证号',width:'50%',align:'center'},
									{field:'name',title:'姓名',width:'50%',align:'center'}
								]],
								pagination:true,
								pageSize:10,
								fitColumns: true,
								rownumbers:true,
								autoRowHeight:true
								,onChange:function(newValue,oldValue) {
									var select = $(this).combogrid('grid').datagrid('getSelected');
									if(select != null) return;
									$(this).combogrid('grid').datagrid('load',{
											idCard:newValue
										});
								}
								,onClickRow : function(index,row) {
						        	$('#oldPersonId').combogrid('setValue',row.id);
									$('#oldPersonId').combogrid('setText',row.name);
								}
			 " style="width:150px">
        <a class="easyui-linkbutton" iconCls="icon-search" onclick="query()">查询</a>
        <a class="easyui-linkbutton" iconCls="icon-reload" onclick="reset()">重置</a>
    </form>
</div>
<script type="text/javascript">

  $(function () {
    $('#dg').datagrid({
      //双击事件
      onDblClickRow: function (rowIndex, rowData) {
        if (null == rowData.oldPerson) {
          $.messager.alert("系统提示", "该信息已不存在.", "info");
          return;
        }
        var url = '<c:url value="/health/verify/"/>' + rowData.id + '/view.do?verifyState=' + $(
            "#verifyState").combobox('getValue') + '&operatorId=' + $('#operatorId').combogrid('getValue');
        top.openDialog({
          onClose: function () {
            $('#dg').datagrid();
          }, width: '60%', height: '100%', title: '随访人员指纹与脸部照片信息审核', url: url
        });
      }
    });
  });


</script>

</body>
</html>
