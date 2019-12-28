<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="GB18030">
  <head>
    <meta charset="GB18030">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

	<%@ include file="/WEB-INF/include/base_css.jsp" %> 
		
	<style>
	.tree li {
        list-style-type: none;
		cursor:pointer;
	}
	</style>
  </head>

  <body>

    <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
      <div class="container-fluid">
        <div class="navbar-header">
           <div><a class="navbar-brand" style="font-size:32px;" href="#">众筹平台 - 许可维护</a></div>
        </div>
		<%@include file="/WEB-INF/include/manager_header.jsp" %>
		
      </div>
    </nav>

    <div class="container-fluid">
      <div class="row">
        <div class="col-sm-3 col-md-2 sidebar">
		<%@include file="/WEB-INF/include/manager_menu.jsp" %>
		
        </div>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">

			<div class="panel panel-default">
              <div class="panel-heading"><i class="glyphicon glyphicon-th-list"></i> 权限菜单列表 <div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i class="glyphicon glyphicon-question-sign"></i></div></div>
			  <div class="panel-body">
                  <ul id="treeDemo" class="ztree"></ul>
			  </div>
			</div>
        </div>
      </div>
    </div>
    <!-- 添加菜单节点的模态框 -->
<div class="modal fade" id="saveMenuModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="exampleModalLabel">添加菜单</h4>
      </div>
      <div class="modal-body">
        <form>
          <div class="form-group">
            <label for="recipient-name" class="control-label">菜单名称：</label>
            <input type="text" name="name" class="form-control" id="recipient-name">
            <input type="hidden" name="pid">
          </div>
          <div class="form-group">
            <label for="message-text" class="control-label">菜单样式：</label>
             <input type="text" name="icon" class="form-control" id="recipient-icon">
          </div>
        </form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        <button type="button" id="saveMenuBtn" class="btn btn-primary">Submit</button>
      </div>
    </div>
  </div>
</div>
<!-- 修改节点菜单 -->
<div class="modal fade" id="updateMenuModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="exampleModalLabel">修改菜单</h4>
      </div>
      <div class="modal-body">
        <form>
          <div class="form-group">
            <label for="recipient-name" class="control-label">菜单名称：</label>
            <input type="text" name="name" class="form-control" id="recipient-name">
            <input type="hidden" name="id">
          </div>
          <div class="form-group">
            <label for="message-text" class="control-label">菜单样式：</label>
             <input type="text" name="icon" class="form-control" id="recipient-icon">
          </div>
        </form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        <button type="button" id="updateSaveMenuBtn" class="btn btn-primary">Submit</button>
      </div>
    </div>
  </div>
</div>

	<%@ include file="/WEB-INF/include/base_js.jsp" %>
    
        <script type="text/javascript">
      	$(".tree a:contains('菜单维护')").css("color", "red");
        $(".tree a:contains('菜单维护')").parents("ul").show();
        $(".tree a:contains('菜单维护')").parents("li").toggleClass("tree-closed");
        
            $(function () {
			    $(".list-group-item").click(function(){
				    if ( $(this).find("ul") ) {
						$(this).toggleClass("tree-closed");
						if ( $(this).hasClass("tree-closed") ) {
							$("ul", this).hide("fast");
						} else {
							$("ul", this).show("fast");
						}
					}
				});    
            });	
			//ztree的使用
			//1.引入jQuery.js文件  ztree的style样式  js文件
			//2.在页面中准备ztree的容器 【ul】
			//3.在js代码中准备ztree的setting配置
			
			function addDiyDom(treeId, treeNode) {
				$("#"+treeNode.tId+"_ico").removeClass();/* .addClass(treeNode.icon); */
				$("#"+treeNode.tId+"_span").before("<span class='"+treeNode.icon+"'></span>");
			};
			function addHoverDom(treeId, treeNode) {
				var aObj = $("#" + treeNode.tId + "_a"); // tId = permissionTree_1, ==> $("#permissionTree_1_a")
				aObj.attr("href", "javascript:;");
				if (treeNode.editNameFlag || $("#btnGroup"+treeNode.tId).length>0) return;
				var s = '<span menuid="'+treeNode.id+'" id="btnGroup'+treeNode.tId+'">';
				if ( treeNode.level == 0 ) {
					s += '<a onclick="addMenu('+treeNode.id+')" class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" href="javascript:void(0);" >&nbsp;&nbsp;<i class="fa fa-fw fa-plus rbg "></i></a>';
				} else if ( treeNode.level == 1 ) {
					s += '<a onclick="updateMenu('+treeNode.id+')" class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;"  href="javascript:void(0);" title="修改权限信息">&nbsp;&nbsp;<i class="fa fa-fw fa-edit rbg "></i></a>';
					if (treeNode.childrenMenus.length == 0) {
						s += '<a class="btn btn-info dropdown-toggle btn-xs deleteMenuBtn" style="margin-left:10px;padding-top:0px;" href="javascript:void(0);" >&nbsp;&nbsp;<i class="fa fa-fw fa-times rbg "></i></a>';
					}
					s += '<a onclick="addMenu('+treeNode.id+')" class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" href="javascript:void(0);" >&nbsp;&nbsp;<i class="fa fa-fw fa-plus rbg "></i></a>';
				} else if ( treeNode.level == 2 ) {
					s += '<a onclick="updateMenu('+treeNode.id+')" class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;"  href="javascript:void(0);" title="修改权限信息">&nbsp;&nbsp;<i class="fa fa-fw fa-edit rbg "></i></a>';
					s += '<a class="btn btn-info dropdown-toggle btn-xs deleteMenuBtn" style="margin-left:10px;padding-top:0px;" href="javascript:void(0);">&nbsp;&nbsp;<i class="fa fa-fw fa-times rbg "></i></a>';
				}

				s += '</span>';
				aObj.after(s);

			};
			//修改菜单节点
			function updateMenu(id){
				//1.根据菜单id查询菜单信息
				$.post("${PATH}/menu/getMenu", {"id":id}, function(menu){
					//2.将菜单信息回显到模态框中
					$("#updateMenuModal input[name='name']").val(menu.name);
					$("#updateMenuModal input[name='icon']").val(menu.icon);
					$("#updateMenuModal input[name='id']").val(menu.id);
					//3.弹出模态框
					$("#updateMenuModal").modal("toggle");
				});
				
			}
			//给修改模态框的修改按钮绑定单击事件
			$("#updateMenuModal #updateSaveMenuBtn").click(function(){
				
				$.post("${PATH}/menu/updateMenu", $("#updateMenuModal form").serialize(), function(result){
					if ("ok" == result) {
						$("#updateMenuModal").modal("toggle");
						initMenusTree();
					}
				});
				
			});
			
			//添加节点弹出模态框
			function addMenu(pid){
				//获取点击菜单按钮的父节点id作为pid
				$("#saveMenuModal input[name='pid']").val(pid);
				
				$("#saveMenuModal").modal("toggle");
			}
			//提交添加菜单的方法
			$("#saveMenuModal #saveMenuBtn").click(function(){
				//提交数据给服务器
				$.post("${PATH}/menu/saveMenu", $("#saveMenuModal form").serialize(), function(result){
					if ("ok" == result){
						
						$("#saveMenuModal").modal("toggle");
						
						initMenusTree();
					}
				});
				//关闭模态框
				//刷新ztree树
			});
			
			//给节点生成的删除按钮绑定单击事件，用于删除菜单
			$("#treeDemo").delegate(".deleteMenuBtn", "click", function(){
				var menuid = $(this).parent().attr("menuid");
				$.post("${PATH}/menu/deleteMenu", {id:menuid},function(result){
					if ("ok" == result) {
						initMenusTree();
					}
				});
			});
			
			
			function removeHoverDom(treeId, treeNode) {
				$("#btnGroup"+treeNode.tId).remove();
			};
			
			var setting = {
				view: {
					addHoverDom: addHoverDom,
					removeHoverDom: removeHoverDom,
					addDiyDom: addDiyDom
					
				},
				data: {
					key: {
						children: "childrenMenus",
						url:""
					},
					simpleData:{
						enable:true,
						pIdKey:"pid"
					}
				}
			};
			
			function initMenusTree(){
				$.ajax({
					type:"GET",
					url:"${PATH}/menu/listMenus",
					success:function(menus){
						menus.push({
							id:0,
							name:"系统权限菜单",
							icon:"glyphicon glyphicon-th"
						});
						//4.调用ztree的init方法将数据源解析显示到容器中
						var $treeNode = $.fn.zTree.init($("#treeDemo"),setting,menus);
						$treeNode.expandAll(true);
					}
				});
			}
			initMenusTree();
			
            
            
        </script>
  </body>
</html>
