/*create by huzd at 2015-01-06 23:29:00*/

/** @huzd collapse the left size div of the main page. **/
function collapseMainLeft(){
	$(".leftContainer").addClass("hide");
	$(".rightContainer").css("width","100%");
	$(".fullScreen").addClass("fullScreen_expan");
}

/** @huzd expand the left size div of the main page. **/
function expandMainLeft(){
	$(".leftContainer").removeClass("hide");
	$(".fullScreen").removeClass("fullScreen_expan");
	$(".rightContainer").css("width","87%");
	$(".fullScreen").show();
}

/**load the second navigation**/
function loadNavigate(id,parent,isTabs){
	expandMainLeft();
	$.post("resAction!loadNavigate.huzd",{resId:id},function(msg){
		if(msg.resources.length>0){
			var innerHtml = (!parent)?("<dd><div class=\"title\"><span><img width=\"20\" height=\"20\" src=\""+_theme_+msg.resources[0].parent.icon+"\"/></span><label>"+msg.resources[0].parent.name+"</label></div>"):"";
			innerHtml +=("<div class='menuContainer "+(!parent?"":"secondLevel_")+"'><ul class=\""+((!parent)?"menuson":"subMenu")+"\">");
			$.each(msg.resources,function(i,n){
				innerHtml +=("<li class=\""+(n.childNo>0?"menu_dir":"")+"\" hasChild='"+(n.childNo>0?true:false)+"'><cite></cite>");//innerHtml +=("<li class=\""+(i==0?"active":"")+"\"><cite></cite>");
				if(isTabs==false){
					innerHtml +=("<a resId='"+n.id+"' href='"+(n.childNo>0?"javascript:void(0)":n.path)+"' target=\"mainiframe\" title=\""+n.name+"\">"+n.name+"</a><i></i></li>");
				}else{
					innerHtml +=("<a "+(n.name.length>9?" class='needTip_' ":"")+" data='"+n.path+"' href='javascript:;' resId='"+n.id+"'  title=\""+n.name+"\">"+n.name+"</a><i></i></li>");
				}
			});
			innerHtml +=("</ul></div>");
			innerHtml +=((!parent)?"</dd>":"");
			if(!parent){
				$(".leftmenu").html(innerHtml);
			}else{
				parent.next("div[class*='menuContainer']").remove();
				parent.after(innerHtml);	
			}
			var height_ = $(window).height() - 90;
			$(".leftmenu .menuContainer:first").css("height",(height_-35)+"px");
		}else{
			if(!parent){
				var innerHtml = "<dd><div class=\"title\"><span><img width=\"20\" height=\"20\" src=\"./images/leftico01.png\"/></span>";
				innerHtml +=("<label>无授权功能</label></div><ul class=\"menuson\">");
				innerHtml +=("</ul></dd>");
				$(".leftmenu").html(innerHtml);		
			}
		}
		//FIX THE TIPSY PLUGIN BUG. BY HUZD 时间紧在这里做简单处理。后期修改该插件来修复此BUG.
		if($(".tipsy").length>-1)$(".tipsy").remove();
	},"json");
}

/**load the second navigation**/
function openFirstLevelPage(id,title,url,isTabs){
			$(".fullScreen").hide();
			if(id&&url){
				if(isTabs){
					addTab_(id,title,(url&&"#"!=url)?url:"404.html");
				}else{
					$(".placeul>li").first().siblings().remove();
					$(".placeul").append("<li><a href='javascript:void(0)'>"+title+"<a></li>");
					$("#mainiframe").attr("src",(url&&"#"!=url)?url:"404.html");
				}
			}
			//FIX THE TIPSY PLUGIN BUG. BY HUZD 时间紧在这里做简单处理。后期修改该插件来修复此BUG.
			if($(".tipsy").length>-1)$(".tipsy").remove();
			
}
/**
 *用于调整整个后台框架的高度； 
 **/
function fixTheWeb(){
	var height_ = $(window).height() - 90;
	$(".con").css("height",height_+"px");
	$(".leftContainer").css("height",height_+"px");
	$(".leftmenu .menuContainer:first").css("height",(height_-35)+"px");
	$(".rightContainer").css("height",height_+"px");
	$(".rightContainer>.body").css("height",$(window).height() - 120+"px");
	$("#mainiframe").css("height",height_ - 40+"px");
}

fixTheWeb();

$(window).resize(function(){
	fixTheWeb();
});

/** 组织机构两级级联 **/
//function loadCountry(elem,option){
//	$.ajax({type:'post',url:"orgAction!loadCountry.huzd",async:false,success:function(msg){
//		if(msg.organizations.length>0){
//			var innerHtml = ('all'==option)?"<option value=''>全部</option>":"";
//			 $.each(msg.organizations,function(i,n){
//				 innerHtml += ("<option value='"+n.id+"'>"+n.name+"</option>");
//			 });
//			 $("#"+elem).html(innerHtml);
//		}
//	},dataType:"json"});	
//}
//
//function loadGroup(countryId,elem,option){
//	if(null!=countryId&&""!=countryId){
//		$.ajax({type:'post',url:"orgAction!loadGroup.huzd",async:false,data:{orgId:countryId},success:function(msg){
//			if(msg.organizations.length>0){
//				var innerHtml = ('all'==option)?"<option value=''>全部</option>":"";
//				$.each(msg.organizations,function(i,n){
//					innerHtml += ("<option value='"+n.id+"'>"+n.name+"</option>");
//				});
//				$("#"+elem).html(innerHtml);
//			}
//		},dataType:"json"});			
//	}else{$("#"+elem).html("<option value=''>全部</option>");}
//}