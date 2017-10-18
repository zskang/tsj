/**
 * container:存放报表的table的选择符,
 * queryBtn：查询报表的按钮id,
 * reportId:报表的编号,
 * callback_:报表加载完成后调用的用户自定义回调函数,
 * getQueryParam_:查询条件收集函数,
 * fillTable_：表格填充函数
 * ***/
var totalColumnNo = 0;
function loadReportInfo(container,queryBtn,reportId,callback_,getQueryParam_,fillTable_){
	if(reportId){
		$.post("reportInfoAction!view.huzd",{'reportInfo.id':reportId},function(msg){
			$("#tableContainer").html(msg.tableHtml);
			$("#remark").html(msg.reportInfo.remark);
			totalColumnNo = msg.totalColumnNumber;
			$("#conditions_").css("display","block");
			$("#dynmicCondition").html(msg.reportInfo.condition);
			$("#dynmicCondition div[class*='ui-row']").hide();
			$("#dynmicCondition div[class*='ui-row']").first().show();
			//$("#dynmicCondition div[class*='ui-row']").last().show();
			
			$(container).page({
	             prefix:'',
	             url:'reportInfoAction!loadDate.huzd?reportInfo.id='+reportId,
	             pageSize:[10,5,20,30],
	             queryBtn:queryBtn,
	             param:getQueryParam_,
	             beforeLoad:function(){
	            	art.dialog({title:'系统提示',content:'数据加载中...',time:1000});
	             },loadComplete:function(){
	            	 //d__.close();
	             },
	             fillTable:fillTable_
	   		});
			//$(container +">thead th").attr("width",(100/totalColumnNo)+"%");
			callback_();	
		},"json");
	}

}