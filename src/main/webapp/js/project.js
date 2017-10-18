;(function($,window,document,undefined){
	/****
	 * @author huzd@si-tech.com.cn
	 */
	
	loadProjects(false);
	
	$("#projectShowName").live("click",function(){
		loadProjects(true);
	});
	
	function loadProjects(flag){
		$.post("projectAction!loadUserProjects.huzd",null,function(msg){
			if(!msg.flag&& (flag || !$("#currentProjetShow").html())){
				if(msg.projects&&msg.projects.length>1){
					var innerHtml = "";
					$.each(msg.projects,function(i,n){
						innerHtml += ("<tr><td><input type='radio' name='dataIds' value='"+n.id+"'/></td><td>"+n.name+"</td><td>"+n.master.chinesename+"</td><td>"+n.type.name+"</td><td>"+n.finishDate+"</td></tr>");
					});
					$("#projectsTable_>tbody").html(innerHtml);
					art.dialog({title:'当前工程选择：',content:document.getElementById('projectlists_'),lock:true,cancel:function(){return false;},ok:function(){
						if($("input[type='radio']:checked").length==1){
							$.post("projectAction!ensureMainProject.huzd",{'project.id':$("input[type='radio']:checked").val()},function(msg){
								fillProjectShow(msg.project);
								window.location.reload();
							},"json");
						}else{
							art.dialog({title:'系统提示',content:'项目必选！',time:2000});
							return false;
						}
					}});
				}else{
					if(msg.projects&&msg.projects.length==1){
						fillProjectShow(msg.projects[0]);
					}
				}
			}else{
				if(msg.flag&&msg.projects.length==1){
					fillProjectShow(msg.projects[0]);
				}
			}
		},"json");
	}
	
	function fillProjectShow(project,flag){
		if(project&&project.name){
			$currentProjetShow = $("#currentProjetShow");
			if($currentProjetShow.html())$currentProjetShow.remove();
			$currentProjetShow = $("<li id=\"currentProjetShow\"><a>当前项目：</a><a href=\"javascript:;\" id=\"projectShowName\">"+project.name+"</a></li>");
			$("#onlineUserFeather").before($currentProjetShow);
		}
	}
	
})(jQuery,window,document);