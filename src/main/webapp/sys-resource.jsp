<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>资源管理</title>
    <link rel="stylesheet" type="text/css" href="${_theme_}css/style.css"/>
    <link rel="stylesheet" type="text/css" href="js/artDialog/skins/opera.css"/>
    <link rel="stylesheet" type="text/css" href="js/ztree/zTreeStyle/zTreeStyle.css"/>
    <link rel="stylesheet" type="text/css" href="js/easyPage/css/skins/jquery.easypage.css"/>
    <style type="text/css">span {
        display: inherit;
    }</style>
</head>

<body>
<div class="rightinfo">
    <div class="tools">
        <ul class="toolbar">
            <shiro:hasPermission name="resource:add">
                <li class="click btn_add" id="addBtn">添加</li>
            </shiro:hasPermission>
            <shiro:hasPermission name="resource:update">
                <li class="click btn_edit" id="updateBtn">修改</li>
            </shiro:hasPermission>
            <shiro:hasPermission name="resource:delete">
                <li class="click btn_delete" id="deleteBtn">删除</li>
            </shiro:hasPermission>
            <shiro:hasPermission name="resource:refreshCode">
                <li class="click btn_refresh" id="refreshCodeBtn">刷新编码</li>
            </shiro:hasPermission>
        </ul>
    </div>
    <div class="content">
        <div class="content_left">
            <div class="widget_box">
                <div class="widget_title"><span>资源树</span></div>
                <div class="widget_body">
                    <div class="ztree" id="resourceTree"></div>
                </div>
            </div>
        </div>
        <div class="content_right hide" id="resourceInfo">
            <div class="widget_box">
                <div class="widget_title"><span>资源信息</span></div>
                <div class="widget_body" style="padding-top:10px;">
                    <ul class="forminfo">
                        <li>
                            <label>资源编号：</label>
                            <input id="resourceId_" type="text" class="dfinput small" readonly="readonly"/>
                            <i></i>
                        </li>
                        <li>
                            <label>资源名称：</label>
                            <input id="resourceName_" type="text" readonly="readonly" class="dfinput"
                                   style="width:300px;"/>
                            <i></i>
                        </li>
                        <li>
                            <label>资源路径：</label>
                            <input id="resourcePath_" type="text" readonly="readonly" class="dfinput"
                                   style="width:300px;"/>
                            <i></i>
                        </li>
                        <li>
                            <label>资源类型：</label>
                            <input id="resourceType_" type="text" class="dfinput normal" readonly="readonly"/>
                            <i></i>
                        </li>
                        <li>
                            <label>资源图标：</label>
                            <img id="resourceIcon_" src=""/>
                            <i></i>
                        </li>
                        <li>
                            <label>显示顺序：</label>
                            <input id="resourceOrder_" type="text" class="dfinput normal" readonly="readonly"/>
                            <i></i>
                        </li>
                        <li>
                            <label>资源编码：</label>
                            <input id="resourceDisplayCode_" type="text" class="dfinput normal" readonly="readonly"/>
                            <i></i>
                        </li>
                        <li>
                            <label>状态：</label>
                            <input id="resourceStatus_" type="text" class="dfinput normal" readonly="readonly"/>
                            <i></i>
                        </li>
                        <li>
                            <label>资源描述：</label>
                            <input id="resourceDesc_" type="text" class="dfinput large" readonly="readonly"/>
                            <i></i>
                        </li>
                    </ul>
                </div>
            </div>
        </div>

        <!-- add Data form -->
        <div class="hide" id="addDataDiv">
            <form class="form-horizontal" id="addDataForm" name="addDataForm">
                <input type="hidden" name="resource.id" id="id" value=""/>
                <input type="hidden" name="resource.level" id="level" value="0"/>
                <input type="hidden" name="resource.parent.id" id="pId" value=""/>
                <input type="hidden" name="resource.displayCode" id="displayCode" value=""/>
                <input type="hidden" name="resource.parent.displayCode" id="parentDisplayCode" value=""/>
                <ul class="forminfo">
                    <li>
                        <label>资源类型</label>
                        <select id="type" name="resource.type" class="select1" style="width:100px;">
                            <option value="1">菜单</option>
                            <option value="2">权限</option>
                        </select>
                        <i>菜单用于展示，权限用来约束操作</i>
                    </li>
                    <li>
                        <label id="name_label">菜单名称</label>
                        <input type="text" value="" id="name" name="resource.name" class="dfinput"
                               style="width:200px;"/>
                        <i>中文名称</i>
                    </li>
                    <li>
                        <label id="path_label">菜单路径</label>
                        <input type="text" id="path" name="resource.path" class="dfinput" style="width:300px;"/>
                        <i>菜单为URL；权限为资源名称:动作</i>
                    </li>
                    <li>
                        <label>显示顺序</label>
                        <input type="text" id="order" name="resource.order" class="dfinput small"/>
                        <i>资源在列表中的展示先后顺序，升序排列</i>
                    </li>
                    <li id="li_icon">
                        <label>图标</label>
                        <input type="text" id="icon" name="resource.icon" class="dfinput small"/>
                        <i>菜单图标</i>
                    </li>
                    <li>
                        <label>状态</label>
                        <select id="status" name="resource.status" class="select1" style="width:100px;">
                            <option value="N">正常</option>
                            <option value="F">禁用</option>
                        </select>
                        <i>资源状态</i>
                    </li>
                    <li>
                        <label>资源描述</label>
                        <textarea rows="4" cols="5" style="width:300px;" id="desc" name="resource.desc"></textarea>
                        <i></i>
                    </li>
                </ul>
            </form>
        </div>
        <!-- add Data form -->

    </div>
</div>
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/ztree/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="js/artDialog/artDialog.min.js"></script>
<script type="text/javascript" src="js/artDialog/artDialog.plugins.min.js"></script>
<script type="text/javascript" src="js/validate/jquery.validate.min.js"></script>
<script type="text/javascript" src="js/validate/jquery.validate.message_cn.js"></script>
<script type="text/javascript" src="js/validate/jquery.validate.method.js"></script>
<script type="text/javascript" src="js/validate/resetFormHelper.js"></script>
<script type="text/javascript">
    var setting = {
        data: {simpleData: {enable: true, rootPId: null}},
        callback: {onClick: treeClickEvent},
        async: {enable: true, url: "resAction!list.huzd", autoParam: ["id=resId", "name=name", "level=level"]}
    };
    var resourceTree = null;
    $(function () {
        resourceTree = $.fn.zTree.init($("#resourceTree"), setting);

        $("#addBtn").click(function () {
            $(".help-inline").html("");
            var resAllNodes = resourceTree.getNodes();
            var treeNode = resourceTree.getNodes()[0];
            if (resAllNodes.length == 1 && (treeNode.id == 0 && treeNode.pId == null)) {//添加根节点
                art.dialog({
                    title: '系统提示', content: '你将添加根节点！', okValue: '确定', ok: function () {
                        showAddForm();
                    }, cancelValue: '取消'
                });
            } else {
                if (resourceTree.getSelectedNodes().length == 0) {
                    art.dialog({title: '系统提示', content: '请选择父资源！', time: 2000});
                    return false;
                } else if (resourceTree.getSelectedNodes().length > 1) {
                    art.dialog({title: '系统提示', content: '只能选择一个父节点！', time: 2000});
                    return false;
                } else {
                    var selectNode = resourceTree.getSelectedNodes()[0];
                    $("#pId").val(selectNode.id);
                    $("#parentDisplayCode").val(selectNode.code);
                    $("#level").val(parseInt(selectNode.level) + 1);
                    if (selectNode.level == 0) {
                        $("#icon").val("");
                        $("#li_icon").show();
                    } else {
                        $("#icon").val("#");
                        $("#li_icon").hide();
                    }
                    showAddForm();
                }
            }
        });
        //配置表单验证信息
        $("#addDataForm").validate({
            rules: {
                'resource.name': {required: true, minlength: 2},
                'resource.desc': {required: true, minlength: 4},
                'resource.type': {required: true, digits: true},
                'resource.path': {
                    required: true, shiroPermission: function () {
                        return ("2" == $("#type").val()) ? true : false;
                    }
                },
                'resource.order': {required: true, digits: true},
                'resource.icon': {
                    required: function () {
                        if ($("#type").val() == 1 && null != $("#icon").val())return true;
                        else if ($("#type").val() == 2 && null == $("#icon").val())return true;
                        else return false;
                    }
                }
            },
            errorElement: 'b', success: function (label) {
                label.remove();
            }
        });

        $("#updateBtn").click(function () {
            if (resourceTree.getSelectedNodes().length == 0) {
                art.dialog({title: '系统提示', content: '请选择资源！', time: 2000});
                return false;
            } else if (resourceTree.getSelectedNodes().length > 1) {
                art.dialog({title: '系统提示', content: '只能选择一个资源！', time: 2000});
                return false;
            } else {
                var treeNode = resourceTree.getSelectedNodes()[0];
                if (treeNode.id == 0 && treeNode.pId == null) {
                    art.dialog({title: '系统提示', content: '无资源可删除！'});
                } else {
                    if (treeNode.level == 1) {
                        $("#icon").val("");
                        $("#li_icon").show();
                    } else {
                        $("#icon").val("#");
                        $("#li_icon").hide();
                    }
                    $.ajax({
                        type: 'POST',
                        url: 'resAction!get.huzd',
                        data: {'resource.id': treeNode.id},
                        async: false,
                        dataType: 'json',
                        success: function (msg) {
                            if (true == msg.flag) {
                                $("#id").val(msg.resource.id);
                                $("#pId").val((msg && msg.resource && msg.resource.parent) ? msg.resource.parent.id : "");
                                $("#level").val(msg.resource.level);
                                $("#name").val(msg.resource.name);
                                $("#type option[value='" + msg.resource.type + "']").attr("selected", "selected");
                                $("#status option[value='" + msg.resource.status + "']").attr("selected", "selected");
                                $("#path").val(msg.resource.path);
                                $("#order").val(msg.resource.order);
                                $("#icon").val(msg.resource.icon);
                                $("#desc").val(msg.resource.desc);
                                $("#displayCode").val(msg.resource.displayCode);
                                $("#parentDisplayCode").val((msg && msg.resource && msg.resource.parent) ? msg.resource.parent.displayCode : "");
                            } else {
                                art.dialog({title: '系统提示', content: '查询出错请重试！', time: 2000});
                            }
                        }
                    });
                }
                art.dialog({
                    title: '信息修改', content: document.getElementById("addDataDiv"), lock: true,
                    okValue: '修改', ok: function () {
                        if ($("#addDataForm").valid()) {
                            $.ajax({
                                type: 'POST',
                                url: 'resAction!update.huzd',
                                data: $("#addDataForm").serialize(),
                                async: false,
                                dataType: 'json',
                                success: function (msg) {
                                    if (true == msg.flag) {
                                        $("#addDataForm")[0].reset();
                                        resetValidatorClass();
                                        var pId = resourceTree.getSelectedNodes()[0].pId;
                                        resourceTree.reAsyncChildNodes(resourceTree.getNodeByParam("id", pId, null), "refresh");
                                        art.dialog({title: '系统提示', content: '修改成功！', time: 2000});
                                    } else {
                                        art.dialog({title: '系统提示', content: '修改出错请重试！', time: 2000});
                                    }
                                }
                            });
                        } else {
                            return false;
                        }
                    }, cancelValue: '关闭', cancel: function () {
                        $("#addDataForm")[0].reset();
                        resetValidatorClass();
                    }
                });
            }
        });

        $("#deleteBtn").click(function () {
            art.confirm('你确定要删除？后果可能很严重...', function () {
                $.ajax({
                    type: 'POST',
                    url: 'resAction!delete.huzd',
                    data: {resId: resourceTree.getSelectedNodes()[0].id},
                    async: false,
                    dataType: 'json',
                    success: function (msg) {
                        if (true == msg.flag) {
                            var pId = resourceTree.getSelectedNodes()[0].pId;
                            resourceTree.reAsyncChildNodes(resourceTree.getNodeByParam("id", pId, null), "refresh");
                            art.dialog({title: '系统提示', content: '删除成功！', time: 2000});
                        } else {
                            art.dialog({title: '系统提示', content: '删除出错请重试！', time: 2000});
                        }
                    }
                });
            }, function () {
                return true;
            });
        });

        $("#refreshCodeBtn").click(function () {
            art.confirm('本次操作将重新刷新所有资源节点的系统编码，确认操作？', function () {
                var nodes = resourceTree.getNodesByParam("isRoot", true, null);
                $.ajax({
                    type: 'POST',
                    url: 'resAction!freshCode.huzd',
                    data: {resId: nodes[0].id},
                    dataType: 'json',
                    success: function (msg) {
                        if (true == msg.flag) {
                            art.dialog({title: '系统提示', content: '刷新成功，请重新打开本页面，查看最新组织机构编码！', time: 2000});
                        } else {
                            art.dialog({title: '系统提示', content: '刷新出错请重试！', time: 2000});
                        }
                    }
                });
                return true;
            }, function () {
                return true;
            });
        });

        $("#type").change(function () {
            var type = $("#type").find("option:selected").val();
            if ("1" == type) {
                $("#name_label").html("菜单名称");
                $("#path_label").html("菜单路径");
            } else {
                $("#name_label").html("权限名称");
                $("#path_label").html("权限标识");
            }
        });

    });
    //点击节点触发事件
    function treeClickEvent(event, treeId, treeNode) {
        $.ajax({
            type: 'POST',
            url: 'resAction!get.huzd',
            data: {'resource.id': treeNode.id},
            async: false,
            dataType: 'json',
            success: function (msg) {
                if (true == msg.flag) {
                    $("#resourceId_").val(msg.resource.id);
                    $("#resourceName_").val(msg.resource.name);
                    $("#resourcePath_").val(msg.resource.path);
                    $("#resourceType_").val(msg.resource.type == 1 ? "菜单" : "资源");
                    if (msg.resource.icon && "#" != msg.resource.icon) {
                        $("#resourceIcon_").attr("src", "${_theme_}" + msg.resource.icon);
                    } else {
                        $("#resourceIcon_").attr("src", "");
                    }
                    $("#resourceOrder_").val(msg.resource.order);
                    $("#resourceDesc_").val(msg.resource.desc);
                    $("#resourceDisplayCode_").val(msg.resource.displayCode);
                    $("#resourceStatus_").val(msg.resource.status == "N" ? "正常" : "禁用");
                    $("#resourceInfo").removeClass("hide");
                } else {
                    art.dialog({title: '系统提示', content: '查询出错请重试！', time: 2000});
                }
            }
        });
    }

    function showAddForm() {
        art.dialog({
            title: '资源添加', content: document.getElementById("addDataDiv"), lock: true,
            okValue: '添加', ok: function () {
                if ($("#addDataForm").valid()) {
                    $.ajax({
                        type: 'POST',
                        url: 'resAction!add.huzd',
                        data: $("#addDataForm").serialize(),
                        async: false,
                        dataType: 'json',
                        success: function (msg) {
                            if (true == msg.flag) {
                                $("#addDataForm")[0].reset();
                                resetValidatorClass();
                                var treeNode = resourceTree.getSelectedNodes()[0];
                                if (!treeNode.isParent) {
                                    treeNode.isParent = true;
                                    resourceTree.updateNode(treeNode);
                                }
                                resourceTree.reAsyncChildNodes(resourceTree.getSelectedNodes()[0], "refresh");
                                art.dialog({title: '系统提示', content: '添加成功！', time: 2000});

                            } else {
                                art.dialog({title: '系统提示', content: '添加出错请重试！', time: 2000});
                            }
                        }
                    });
                } else {
                    return false;
                }
            }, cancelValue: '关闭', cancel: function () {
                $("#addDataForm")[0].reset();
                resetValidatorClass();
            }
        });
    }
</script>
</body>

</html>
  