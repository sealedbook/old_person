function Esite(){}
var esite = new Esite();

Esite.prototype.formatDate = function(val,row,index) {
	var field = this.htmlDom.attr('field');
	//如果是子对象里的值
	if(field.indexOf(".") > 0 )
	{
		var nameArray = field.split(".");
		var value = row[nameArray[0]];
		for(var i = 1; i < nameArray.length; i++){
			value = value[nameArray[i]];
		}
		val = value;
	}
	if(val == null) {
		return '';
	}
	
	var pattern = this.htmlDom ? this.htmlDom.attr('pattern') : this.pattern;
	return new Date(val).format(pattern);
};

Esite.prototype.convertUserCode2Name = function(val,row,index) {
	if(val == null) {
		return '';
	}
	var cacheSystemUser = $(window).data("systemUser_" + val);
	if(cacheSystemUser) {
		return cacheSystemUser;
	}
	var url = contextPath + '/user/find.do';
	var showName = '';
	$.ajax({
		async : false
		,url : url
		,data : {id:val}
		,dataType : 'json'
		,success : function(dictionary) {
			if(dictionary) {
				showName = dictionary.showName;
			} else {
				showName = '';
			}
		}
	});
	$(window).data("systemUser_" + val,showName);
	return showName;
};

Esite.prototype.convertOperatorCode2Name = function(val,row,index) {
	if(val == null) {
		return '';
	}
	var cacheOperator = $(window).data("operator_" + val);
	if(cacheOperator) {
		return cacheOperator;
	}
	var url = contextPath + '/operator/' + val + '.do';
	var showName = '';
	$.ajax({
		async : false
		,url : url
		,data : {id:val}
		,dataType : 'json'
		,success : function(operator) {
			if(operator) {
				showName = operator.name;
			} else {
				showName = '';
			}
		}
	});
	$(window).data("operator_" + val,showName);
	return showName;
};

Esite.prototype.convertOrganizeCodeArray2Name = function(val,row,index) {
	if(val == null) {
		return '';
	}
	var areaIdArray = val.replace('[','').replace(']','').split(',');
	var param = '';
	$.each(areaIdArray,function(){
		param += '&idArray='+this.trim();
	});
	var dictionaryUrl = contextPath + '/organize/array/query.do?1=1' + param;
	var showName = '';
	$.ajax({
		async : false
		,url : dictionaryUrl
		,dataType : 'json'
		,success : function(organizeArray) {
			if(organizeArray) {
				$.each(organizeArray,function(idx,obj) {
					if(obj != null) {
						showName += obj.name + ' ';
					}
				});
				showName = showName.trim().replace(/ /g,',');
			} else {
				showName = '';
			}
		}
	});
	return showName;
}

Esite.prototype.convertOrganizeCode2Name = function(val,row,index) {
	var field = this.htmlDom.attr('field');
	//如果是子对象里的值
	if(field.indexOf(".") > 0 ) {
		var nameArray = field.split(".");
		var value = row[nameArray[0]];
		for(var i = 1; i < nameArray.length; i++){
			value = value[nameArray[i]];
		}
		val = value;
	}
	if(val == null) {
		return '';
	}
	var fullName = this.htmlDom ? this.htmlDom.attr('fullName') : this.fullName;
	
	var cacheOrganize = $(window).data("organize_" + fullName + "_" + val);
	if(cacheOrganize) {
		return cacheOrganize;
	}
	var dictionaryUrl = contextPath + '/organize/query.do';
	var showName = '';
	$.ajax({
		async : false
		,url : dictionaryUrl
		,data : {id:val}
		,dataType : 'json'
		,success : function(organize) {
			if(organize) {
				if(fullName == 'true') {
					showName = organize.fullName;
				} else {
					showName = organize.name;
				}
			} else {
				showName = '';
			}
		}
	});
	$(window).data("organize_" + fullName + "_" + val,showName);
	return showName;
};

Esite.prototype.convertCode2Name = function(val,row,index) {
	//增加页面缓存功能
	var parentId = '';
	if(this.htmlDom) {
		parentId = this.htmlDom.attr('parentId');
	} else {
		parentId = this.parentId;
	}
	var key = "dictionary_" + parentId + "_" + val;
	key = key.replace('-','=');
	var cacheDictionary = $(document).data(key);
	if(cacheDictionary) {
		return cacheDictionary;
	}
	var dictionaryUrl = contextPath + '/dictionary/' + parentId + '/' + encodeURI(val) + '.do';
	var showName = '';
	$.ajax({
		async : false
		,url : dictionaryUrl
		,dataType : 'json'
		,success : function(dictionary) {
			if(dictionary) {
				showName = dictionary.dicName;
			} else {
				showName = '';
			}
		}
	});
	$(document).data(key,showName);
	return showName;
};

Esite.prototype.convertSex = function(val,row,index) {
	if(val == '1') {
		return '男';
	}
	return '女';
};

Esite.prototype.convertSflx = function(val,row,index) {
	if(val == 'sflx01') {
		return '从业人员';
	} else if(val == 'sflx02') {
		return '城基保';
	} else if(val == 'sflx03') {
		return '新农保';
	}
	return '';
};

Esite.prototype.convertLnrlb = function(val,row,index) {
	if(val == '1') {
		return '本地';
	} else if(val == '2') {
		return '外地';
	}
	return '';
};

Esite.prototype.convertFingerVerifyState = function(val,row,index) {
	if(val == '2') {
		return '终端认证成功';
	} else if(val == '3') {
		return '终端认证失败';
	} else if(val == '4') {
		return '终端无法确认';
	}
	return '';
};

Esite.prototype.convertShzt = function(val,row,index) {
	if(val == '-1') {
		return '未审核';
	} else if(val == '0') {
		return '未通过审核';
	} else if(val == '1') {
		return '审核通过';
	}
	return '';
};

Esite.prototype.convertJmzt = function(val,row,index) {
	if(val == 'none') {
		return '未建模';
	} else if(val == 'created') {
		return '已建模';
	} else if(val == 'contcreated') {
		return '无法建模';
	} else if(val == 'init') {
		return '未建模';
	}
	return '';
}

Date.prototype.format = function(pattern) {
	var o = {
		"M+" : this.getMonth() + 1,
		"d+" : this.getDate(),
		"HH+" : this.getHours(),
		"m+" : this.getMinutes(),
		"s+" : this.getSeconds(),
		"q+" : Math.floor((this.getMonth()+3)/3),
		"ms" : this.getMilliseconds()
	};
	
	if(/(y+)/.test(pattern)) {
		pattern = pattern.replace(RegExp.$1,(this.getFullYear()+"").substr(4 - RegExp.$1.length));
	}
	for(var k in o) {
		if(new RegExp("(" + k + ")").test(pattern)) {
			pattern = pattern.replace(RegExp.$1,(RegExp.$1.length==1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
		}
	}
	return pattern;
};