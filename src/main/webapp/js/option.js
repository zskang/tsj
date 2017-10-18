var categoryIds = "";
;(function($,window,document,undefined) {
	if($(".asynOptions").length>0){
		$(".asynOptions").each(function(i,n){
			categoryIds += $(this).attr("data");
			if(i<$(".asynOptions").length-1)categoryIds+=",";
		});
		getSelectOption();	
	}

})(jQuery,window,document);

/**获取选项码值**/
function getSelectOption(){
    $.ajax({
        url: 'optionAction!selectoption.huzd',
        async: false,//改为同步方式
        data: {dataIds:categoryIds},
        dataType:'json',
        success: function (data) {
        	fillOption(data);
        }
    });
}

/**选项填充函数**/
function fillOption(data){
	var categorys = categoryIds.split(",");
	$.each(categorys,function(i,n){
			var innerHtml = "";
			$.each(data[i],function(j,m){
				innerHtml +=("<option value='"+m.id+"'>"+m.name+"</option>");
		});
		$("select[data='"+n+"']").append(innerHtml);
	});
}

/**根据类型获取选项码值**/
var bycategory = "";
function loadOption(category,type){
	bycategory = category;
	if(type == 'N'){
		var result;
	    $.ajax({
	        url: 'optionAction!selectoption.huzd',
	        async: false,//改为同步方式
	        data: {dataIds:category},
	        success: function (data) {
	            var innerHtml = "";
	        	var categorys = bycategory.split(",");
	        	$.each(categorys,function(i,n){
	        		$.each(data[i],function(j,m){
	        			innerHtml +=("<option value='"+m.id+"'>"+m.name+"</option>");
	        		});
	        	});
	        	result = innerHtml;
	        }
	    });
	    return result;
	}else{
		$.post('optionAction!selectoption.huzd',{dataIds:category},byCategoryfillOption,"json");
	}
}

/**选项填充函数**/
function byCategoryfillOption(data){
	var categorys = bycategory.split(",");
	$.each(categorys,function(i,n){
		var innerHtml = "";
		$.each(data[i],function(j,m){
			innerHtml +=("<option value='"+m.id+"'>"+m.name+"</option>");
		});
		$("select[data='"+n+"']").append(innerHtml);
	});
}