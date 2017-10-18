/**
 * jquery.easypage.js v0.0.1
 * http://git.oschina.net/easypage/easypage
 *
 * Copyright (C) 2016 ahhzd@vip.qq.com
 */
;(function($,window,document,undefined){
	var defaults_ = null;
	/***声明一个HuzdPage对象***/
	var HuzdPage = function(ele,options){
		this.element = ele;
		this.defaults = {
			prefix:'',
			pageSize:10,
			queryBtn:'',
			url:'',
			beforeLoad:function(){},
			loadComplete:function(){},
			fillTable:function(){},
			param:function(){}
		};
		this.opts = $.extend({},this.defaults,options);
		defaults_ = this.opts;
	};
	/***add Method to the Object***/
	HuzdPage.prototype = {
		init:function(obj){
			var pageSizeHtml = "";
			if($.isArray(defaults_.pageSize)){
				pageSizeHtml += "<select id='pageSize_"+defaults_.prefix+"' class='pageSize_select_'>";
				$.each(defaults_.pageSize,function(i,n){
					pageSizeHtml += ("<option value='"+n+"'>"+n+"</option>");
				});
				pageSizeHtml += "</select>";
			}else if(parseInt(defaults_.pageSize)==defaults_.pageSize){
				pageSizeHtml = "<select id='pageSize_"+defaults_.prefix+"' class='pageSize_select_'><option value='"+defaults_.pageSize+"'>"+defaults_.pageSize+"</option></select>";
			}
			$pageInfo = $("<div class='page_info_'>第<i id='currentPage_"+defaults_.prefix+"'>1</i>页，总<i id='totalPages_"+defaults_.prefix+"'>1</i>页，总<i id='totalRecords_"+defaults_.prefix+"'>0</i>条，<i >"+pageSizeHtml+"</i>条/页。</div>");
			$pageItemsContainer = $("<ul class='pageItemsContainer_'></ul>");
			$preLink = $("<li><a href='javascript:void(0)' id='preBtn_"+defaults_.prefix+"' class='pagePre_'> &lt; </a></li>");
			$nextLink = $("<li><a href='javascript:void(0)' id='nextBtn_"+defaults_.prefix+"' class='pageNext_'> &gt; </a></li>");
			$pageContainer = $("<div id='page_"+defaults_.prefix+"' class='page_container_'></div>");
			
			$pageInfo.appendTo($pageContainer);//将分页数据信息插入分页DIV中
			$preLink.appendTo($pageItemsContainer);
			$nextLink.appendTo($pageItemsContainer);
			$pageItemsContainer.appendTo($pageContainer);
			$pageContainer.appendTo(this.element.parent());
			
			$preLink.bind("click",function(){
				HuzdPage.prototype.prePage.apply(obj,arguments);
			});
			$nextLink.bind("click",function(){
				HuzdPage.prototype.nextPage.apply(obj,arguments);
			});
			if(defaults_.queryBtn){
				$("#"+defaults_.queryBtn).bind("click",function(){
					HuzdPage.prototype.loadData.apply(obj,arguments);
				});
			}
			HuzdPage.prototype.loadData.apply(this,arguments);
			return this;
		},prePage:function(){
			var currentPage = parseInt($("#currentPage_"+defaults_.prefix).html());
			currentPage = ((currentPage-1)>0)?currentPage-1:1;
			$("#currentPage_"+defaults_.prefix).html(currentPage);
			HuzdPage.prototype.loadData.apply(this,arguments);
		},nextPage:function(){
			var currentPage = parseInt($("#currentPage_"+defaults_.prefix).html());
			var totalPages   = parseInt($("#totalPages_"+defaults_.prefix).html());
			currentPage = ((currentPage+1)<=totalPages)?currentPage+1:totalPages;
			$("#currentPage_"+defaults_.prefix).html(currentPage);
			HuzdPage.prototype.loadData.apply(this,arguments);
		},loadData:function(){
			var _otherParam_ = defaults_.param();	
			var _pageInfo_ =  HuzdPage.prototype.loadPageInfo.apply(this,arguments);
			$.extend(_otherParam_,_pageInfo_);
			var prefix_ = defaults_.prefix;
			var call_back_ = defaults_.fillTable;
			var obj = this;
			$.ajax({url:defaults_.url,type:"POST",data:_otherParam_,success:function(msg){
				if(msg&&msg.page){
					$("#pageSize_"+prefix_+" option[value='"+msg.page.pageSize+"']").attr("selected","selected");;
					$("#currentPage_"+prefix_).html(msg.page.currentPage);
					$("#totalRecords_"+prefix_).html(msg.page.totalRecords);
					$("#totalPages_"+prefix_).html(msg.page.totalPages);
					$(".pageItems_").remove();
					var pageNum = 5;
					var startPage = 1;
					var endPage = 1;
					var page = msg.page;
					if(page.currentPage<=parseInt(pageNum/2)+1){
						startPage = 1;
						endPage	  = (page.totalPages>pageNum)?pageNum:page.totalPages;
					}
					if(page.currentPage>parseInt(pageNum/2)+1){
						startPage = page.currentPage - parseInt(pageNum/2);
						endPage   = page.currentPage + parseInt(pageNum/2);
						if(endPage>page.totalPages)endPage = page.totalPages;
					}
					var innerPage = "";
					while(startPage<=endPage){
						innerPage += ('<li class="'+(startPage == page.currentPage?'current':'')+'"><a href="javascript:;" class="pageItems_">'+startPage+'</a></li>');
						startPage++;
					}
					$(".pagePre_"+prefix_).parent().after(innerPage);
					$(".pageItems_").bind("click",function(){
						$("#currentPage_"+prefix_).html(parseInt($(this).html()));
						HuzdPage.prototype.loadData.apply(obj,arguments);
					});
					$(".pageSize_select_").bind("change",function(){
						HuzdPage.prototype.loadData.apply(obj,arguments);
					});
				}
				call_back_(msg);
			},dataType:"json",beforeSend:defaults_.beforeLoad,complete:defaults_.loadComplete});
		},loadPageInfo:function(){
			var pageParam_ = {'page.pageSize':$("#pageSize_"+defaults_.prefix).val(),'page.currentPage':$("#currentPage_"+defaults_.prefix).html(),
			        'page.totalRecords':$("#totalRecords_"+defaults_.prefix).html(),'page.totalPages':$("#totalPages_"+defaults_.prefix).html()};
			return pageParam_;
		}
	};
	//分页插件对外暴露接口
	$.fn.page = function(options){
		var huzdpage = new HuzdPage(this,options);
		huzdpage.init(huzdpage);
		return this;
	};
	//分页插件对外暴露接口
	$.fn.refresh = function(){
		HuzdPage.prototype.loadData.apply(defaults_,arguments);
	};
})(jQuery,window,document);