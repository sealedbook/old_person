var openedDialogIdArray = new Array();
function openDialog(setting) {
	var divId = getGuid();
	if(!setting.id){
		setting.id = getGuid();
	}
	var frameId = setting.id;
	
	var opendiv = $("<div/>").appendTo(document.body);
	$(opendiv).attr("id",divId);
	$(opendiv).css("overflow-y","hidden");
	
	var openIframe = $("<iframe frameborder='0'></iframe>").appendTo(opendiv);
	$(openIframe).attr("id",frameId);
	$(openIframe).attr("height","100%");
	$(openIframe).attr("width","100%");
	$(openIframe).load(function() {
		var windowAvailHeight = window.document.body.clientHeight;
		var iframeHeight = $(this).contents().find("body").height() + 60;
		if(iframeHeight >= windowAvailHeight) {
			iframeHeight = $(opendiv).panel('options').height;
		}
		$(opendiv).dialog('resize',{
			//height:iframeHeight
			//,top:(windowAvailHeight-iframeHeight)/2
		});
	});
	
	$(opendiv).dialog({
		title : setting.title?setting.title:' '
		,cache : false
		,modal : true
		,draggable:false
		,closable : setting.closable
		,width : setting.width ? setting.width : '70%'
		,height : setting.height ? setting.height : '90%'
		//,height : '70%'
		,onBeforeOpen : function(node) {
			$(openIframe).attr("src",encodeURI(setting.url));
		}
		//,onBeforeClose : function() {
		//	top.$.messager.confirm('系统提示','确认关闭该页面?',function(r){
		//		if(r) {
		//			if(openedDialogIdArray.length >= 1) {
		//				var dialogId = openedDialogIdArray[openedDialogIdArray.length-1];
		//				alert(1);
		//				$('#' + dialogId).dialog('close');
		//			}
		//		}
		//	});
		//	return false;
		//}
		,onClose : function(){
			try {
				if(typeof(setting.onClose)=='function' && setting.onClose) {
					var openedWin;
					if(top.document.getElementById(frameId)) {
						openedWin = top.document.getElementById(frameId).contentWindow;
					} else {
						openedWin = document.getElementById(frameId).contentWindow;
					}
					setting.onClose(openedWin);
				}
				$("iframe[id='frameId']").empty();
				$("div[id='divId']").empty();
			} finally {
				var dialogId = openedDialogIdArray.pop();
				if($('#' + dialogId).html() == 'undefined') {
					openedDialogIdArray.push(dialogId);
					return;
				}
			}
		}
	});
	openedDialogIdArray.push(divId);
}

function closeDialog() {
	if(openedDialogIdArray.length >= 1) {
		var dialogId = openedDialogIdArray[openedDialogIdArray.length-1];
		$('#' + dialogId).dialog('close');
	}
}

function getParentOpenWindow() {
	if(openedDialogIdArray && openedDialogIdArray.length > 1) {
		var parentOpenDivId =  openedDialogIdArray[openedDialogIdArray.length-2];
		var parentOpenIframeId = $('#' + parentOpenDivId).children("iframe").attr("id");
		var openedWin;
		if(top.document.getElementById(parentOpenIframeId)) {
			openedWin = top.document.getElementById(parentOpenIframeId).contentWindow;
		} else {
			openedWin = document.getElementById(parentOpenIframeId).contentWindow;
		}
		return openedWin;
	}
	return null;
}

function getGuid() {
	var guid = "";
	for (var i = 1; i <= 32; i++){
		if(Math) {
			guid += Math.floor(Math.random()*16.0).toString(16);
		} else {
			guid += new Date().getMilliseconds();
		}
	}
	return guid;    
}


