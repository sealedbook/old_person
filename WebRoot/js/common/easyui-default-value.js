var formInputWidth = 150;

if($.fn.validatebox) {
	$.fn.validatebox.defaults.tipPosition='left';
	$.fn.validatebox.defaults.deltaX = 0;
}
if($.fn.combobox) {
	$.fn.combobox.defaults.panelHeight = 'auto';
	$.fn.combobox.defaults.panelMaxHeight = '200';
	$.fn.combobox.defaults.width = formInputWidth;
}
if($.fn.combogrid) {
	$.fn.combogrid.defaults.pageList = [5,10,15,20];
	$.fn.combogrid.defaults.width = formInputWidth;
	$.fn.combogrid.defaults.striped = true;
	$.fn.combogrid.defaults.onHidePanel = function() {
		var select = $(this).combogrid('grid').datagrid('getSelected');
		if(null == select) {
			$(this).combogrid('setValue',null);
			$(this).combogrid('setText',null);
			$(this).combogrid('clear');
		}
	}
	//处理当加载完成后,相关信息被删除的情况
	$.fn.combogrid.defaults.onLoadSuccess = function(data) {
		var value = $(this).combogrid('getValue');
		var text = $(this).combogrid('getText');
		//value.length==32 修正手动输入时,由于value==text引发的自动clear
		if(value == text && value.length == 32) {
			$(this).combogrid('clear');
		}
	}
}
if($.fn.datagrid) {
	$.fn.datagrid.defaults.pageList = [5,10,15,20,25,30,50];
	$.fn.datagrid.defaults.pageSize = 30;
	$.fn.datagrid.defaults.sortable = false;
	$.fn.datagrid.defaults.striped = true;
}
if($.fn.textbox) {
	$.fn.textbox.defaults.width = formInputWidth;
	$.fn.textbox.defaults.onChange = function(newValue,oldValue) {
		if(newValue == oldValue) return;
		newValue = newValue.trim();
		if(newValue.length <= 0) {
			$(this).textbox('reset');
		} else {
			$(this).textbox('setValue',newValue);
			$(this).textbox('setText',newValue);
		}
	}
}
if($.fn.datebox) {
	$.fn.datebox.defaults.width = formInputWidth;
	var buttons = $.extend([], $.fn.datebox.defaults.buttons);
	buttons.splice(1, 0, {
		text: '清空',
		handler: function(target){
			$(target).datebox('reset');
			$(target).datebox('hidePanel');
		}
	});
	$.fn.datebox.defaults.buttons = buttons;
}
if($.fn.datetimebox) {
	$.fn.datetimebox.defaults.width = formInputWidth;
	var buttons = $.extend([], $.fn.datetimebox.defaults.buttons);
	buttons.splice(1, 0, {
		text: '清空',
		handler: function(target){
			$(target).datebox('reset');
			$(target).datebox('hidePanel');
		}
	});
	$.fn.datetimebox.defaults.buttons = buttons;
}

if($.fn.combotree) {
	$.fn.combotree.defaults.width = formInputWidth;
}
