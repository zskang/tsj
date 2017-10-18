//全选/取消全选响应事件
$("#selectAll").toggle(function(){
	$("#selectAll").html("取消");
	$('input[name="dataIds"][disabled!="disabled"]').attr("checked","checked");
},function(){
	$("#selectAll").html("全选");
	$('input[name="dataIds"]').removeAttr("checked");
});