var projectId = '${sessionScope.project.id}';
;(function($,window,document,undefined){
	/****
	 * @author huzd@si-tech.com.cn
	 * WebSocket 客户端
	 */
   var webSocket;
	//判断当前浏览器是否支持WebSocket
   if(window.WebSocket){
	   webSocket = new WebSocket(_wspath_+"systemWS");
   }else{
	   webSocket = new SockJS(_basePath_+ "sockjs/systemWS");  
   }
   
   webSocket.onerror = function(event){};
   webSocket.onopen = function(event){ webSocket.send('hello');return false;};
   webSocket.onmessage = function(event) {
		var obj = jQuery.parseJSON(event.data);
		if(obj.onlineNumber)$("#onlineUserNumbers").html(obj.onlineNumber);
		if(obj.message){
			try{
				iziToast.show({
					class : 'toast',
					title : obj.title,
					message : "<a href='javascript:void(0)' onclick=\"openActivehtml(\'"+obj.moduleId+"\',\'"+projectId+"\',\'normal\')\">"+obj.message+"</a>",
					layout : 2,
					titleColor : 'red',
					position : 'bottomRight',
					close : true,
					pauseOnHover : true,
					backgroundColor : 'white',
					progressBarColor : '#0085d0',
					timeout : 100000000
				});
//				alert(obj.title);
//				art.dialog.notice({
//					title: obj.title,
//				    width: 420,// 必须指定一个像素宽度值或者百分比，否则浏览器窗口改变可能导致artDialog收缩
//				    content: "<a href='javascript:void(0)' onclick=\"openActivehtml(\'"+obj.moduleId+"\',\'"+projectId+"\',\'normal\')\">"+obj.message+"</a>",
//				    icon: 'face-sad',
//				    time: 10000
//				});
				
			}catch(e){
				art.dialog({title:obj.title,content:obj.message});
			}

		}
    };
})(jQuery,window,document);

function openActivehtml(moduleId,projectId,searchType){
//	var basePath = '${basePath}';
//	alert(basePath);
//	art.dialog.list['Notice'].close();
	var toast = document.querySelector('.toast');  
	iziToast.hide({
	    transitionOut: 'fadeOutUp'
	}, toast);
	window.parent.addTab_(181,"待办事项","app-mytask.jsp?moduleId="+moduleId+"&projectId="+projectId+"&searchType="+searchType);
}

artDialog.notice = function (options) {
	var opt = options || {},
		api, aConfig, hide, wrap, top,
		duration = 800;
		
	var config = {
		id: 'Notice',
		left: '100%',
		top: '100%',
		fixed: true,
		drag: false,
		resize: false,
		follow: null,
		lock: false,
		init: function(here){
			api = this;
			aConfig = api.config;
			wrap = api.DOM.wrap;
			top = parseInt(wrap[0].style.top);
			hide = top + wrap[0].offsetHeight;
			
			wrap.css('top', hide + 'px')
				.animate({top: top + 'px'}, duration, function () {
					opt.init && opt.init.call(api, here);
				});
		},
		close: function(here){
			wrap.animate({top: hide + 'px'}, duration, function () {
				opt.close && opt.close.call(this, here);
				aConfig.close = $.noop;
				api.close();
			});
			
			return false;
		}
	};	
	
	for (var i in opt) {
		if (config[i] === undefined) config[i] = opt[i];
	};
	
	return artDialog(config);
};