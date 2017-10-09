document.write("<script src='"+contextPath+"/js/common/verifyIdCard.js'></script>");
$(function(){
	$.extend($.fn.validatebox.defaults.rules, {
		equals : {
			validator : function(value,param) {
				return value == $(param[0]).val();
			}
			,message : '两次输入不一致.'
		}
		,checkIdCard: {
			validator : function(value,param) {
				return idCardHelper.check18IdCard(value) || idCardHelper.check15IdCard(value);
			}
			,message : '请输入正确的15或18位公民身份证号.'
		}
		,blankSpace : {
			validator : function(value,param) {
				return !($.trim(value).length == 0);
			}
			,message : '您输入的内容全部为空格.'
		}
		,length:{
			validator : function(value,param) {
				var len = $.trim(value).length;
				var noTrimLen = value.length;
				return (len>=param[0]&&len<=param[1])&&(noTrimLen>=param[0]&&noTrimLen<=param[1]);
			}
			,message:$.fn.validatebox.defaults.rules.length.message
		}
		,number : {
			validator : function(value,param) {
				var regExp=/^\d+?$/;
				return regExp.test (value);
			}
			,message : '请输入整数数字.'
		}
		,numberFloat : {
			validator : function(value,param) {
				var regExp=/^\d+(\.\d+)?$/;
				return regExp.test (value);
			}
			,message : '请输入数字.'
		}
		,dateLessThan : {
			validator : function(value,param) {
				var compareValue = $(param[0]).textbox('getValue');
				compareValue = compareValue.replace(/-/g,"/");
				var compareDate = new Date(Date.parse(compareValue));
				value = value.replace(/-/g,"/");
				var date = new Date(Date.parse(value));
				if(date > compareDate) {
					return false;
				}
				return true;
			}
			,message : '起始时间要小于结束时间.'
		}
		,dateGreaterThan : {
			validator : function(value,param) {
				var compareValue = $(param[0]).textbox('getValue');
				compareValue = compareValue.replace(/-/g,"/");
				var compareDate = new Date(Date.parse(compareValue));
				value = value.replace(/-/g,"/");
				var date = new Date(Date.parse(value));
				if(date < compareDate) {
					return false;
				}
				return true;
			}
			,message : '结束时间要大于起始时间.'
		},
		fileType: {
		        validator: function(value, param){
		        	var extStart=value.lastIndexOf("."); 
		        	var ext=value.substring(extStart+1,value.length).toUpperCase(); 
		        	var rflag = true;
		        	if(param.length>0){
		        		rflag = false;
		        		for(var i=0;i<param[0].length;i++){
		        			if(ext==param[0][i].toUpperCase()){
		        				rflag = true;
		        				break;
		        			}
		        		}
		        	}
		           return rflag;
		       },
		       message: '允许的文件类型: {0}.'
		    }
	});
});