<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta charset="UTF-8">
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
	table tbody tr:nth-child(odd){background:#F4F4F4;}
	table tbody td:nth-child(even){color:#C00;}
	</style>
  </head>

  <body>

    <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
      <div class="container-fluid">
        <div class="navbar-header">
          <div><a class="navbar-brand" style="font-size:32px;" href="#">众筹平台 - 角色维护</a></div>
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
			  <div class="panel-heading">
				<h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
			  </div>
			  <div class="panel-body">
<form class="form-inline" role="form" style="float:left;">
  <div class="form-group has-feedback">
    <div class="input-group">
      <div class="input-group-addon">查询条件</div>
      <input name="condition" class="form-control has-success" type="text" placeholder="请输入查询条件">
    </div>
  </div>
  <button id="queryRoleBtn" type="button" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询</button>
 
</form>
<button  type="button" class="btn btn-danger" style="float:right;margin-left:10px;"><i class=" glyphicon glyphicon-remove"></i> 删除</button>
<button id="saveRoleBtn" type="button" class="btn btn-primary" style="float:right;"><i class="glyphicon glyphicon-plus"></i> 新增</button>
<br>
 <hr style="clear:both;">
          <div class="table-responsive">
            <table class="table  table-bordered">
              <thead>
                <tr >
                  <th width="30">#</th>
				  <th width="30"><input type="checkbox"></th>
                  <th>名称</th>
                  <th width="100">操作</th>
                </tr>
              </thead>
              <tbody>
               	
              </tbody>
			  <tfoot>
			     <tr >
				     <td colspan="6" align="center">
						<ul class="pagination">
							
						</ul>
					 </td>
				 </tr>
			  </tfoot>
            </table>
          </div>
			  </div>
			</div>
        </div>
      </div>
    </div>
 <!-- 新增的模态框 -->
 <div class="modal fade" id="saveRoleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="exampleModalLabel">新增角色</h4>
      </div>
      <div class="modal-body">
        <form>
          <div class="form-group">
            <label for="recipient-name" class="control-label">角色名称:</label>
            <input type="hidden" name="pages" class="form-control" id="recipient-name">
            <input type="text" name="name" class="form-control" id="recipient-name">
          </div>
        </form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        <button id="saveSubmitBtn" type="button" class="btn btn-primary">submit</button>
      </div>
    </div>
  </div>
</div>
 <!-- 修改的模态框 -->
 <div class="modal fade" id="editRoleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="exampleModalLabel">更新角色</h4>
      </div>
      <div class="modal-body">
        <form>
          <div class="form-group">
            <label for="recipient-name" class="control-label">角色名称:</label>
            <input name="name" pageNum="pageNum" type="text" class="form-control" id="recipient-name">
            <input name="rid" type="hidden" class="form-control" id="recipient-name">
          </div>
        </form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        <button id="editSubmitBtn" type="button" class="btn btn-primary">submit</button>
      </div>
    </div>
  </div>
</div> 
 <!-- 分配角色权限的模态框 -->
 <div class="modal fade" id="assignPermissionToRoleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="exampleModalLabel">分配许可</h4>
      </div>
      <div class="modal-body">
      	<input type="hidden" name="roleId"/>
        <ul id="permissionTree" class="ztree"></ul>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        <button id="assignPermissionBtn" type="button" class="btn btn-primary">submit</button>
      </div>
    </div>
  </div>
</div> 


	<%@ include file="/WEB-INF/include/base_js.jsp" %>

        <script type="text/javascript">
        
        	//给分配权限的提交按钮绑定单击事件
        	$("#assignPermissionToRoleModal #assignPermissionBtn").click(function(){
        		//1.获取roleid
        		var roleId = $("#assignPermissionToRoleModal input[name='roleId']").val();
        		//2.获取选中的所有权限id集合
        		var treeObj = $.fn.zTree.getZTreeObj("permissionTree");
        		var checkedNodes = treeObj.getCheckedNodes(true);
        		//3.遍历选中的节点，拼接节点id参数
        		var pids = "";
        		$.each(checkedNodes,function(){
        			pids += this.id + ",";
        		});
        		pids = pids.substring(0,pids.length-1);
        		
        		//发送ajax请求，修改权限
        		$.ajax({
        			type:"POST",			
		        	url:"${PATH}/role/updatePermissions",
		        	data:{id:roleId, "pids":pids},
        			success:function(result){
        				if ("ok" == result){
        					//关闭模态框
        					$("#assignPermissionToRoleModal").modal("toggle");
        				}
        			}
        		});
        		
        	});
				
        	//修改role
   			$("table tbody").delegate(".editRoleBtn", "click", function(){
   				var roleId = $(this).attr("rid");
   				
   				$.post("${PATH}/role/getRole", {id:roleId}, function(role){
   					
   				 	$("#editRoleModal input[name='name']").val(role.name)
   					$("#editRoleModal input[name='rid']").val(role.id)
   					
   					$('#editRoleModal').modal("show");
   				});	
        	});
   			$("#editSubmitBtn").click(function(){
   				//获取该role的id值和name值
   				var roleId = $("#editRoleModal input[name='rid']").val()
   				var roleName = $("#editRoleModal input[name='name']").val()
   				
        		$.post("${PATH}/role/updateRole",{id:roleId,name:roleName},function(result){
        			if ("ok"==result){
        				
        				var pageNum = $(".active").prop("id");
        				
        				layer.msg("更新成功",{time:1000,icon:8});
        				setTimeout(function(){
        					initRolesTable(pageNum);
        					//隐藏模态框
        					$("#editRoleModal").modal("hide");
        				},1000);
        				
        			}	 
        		});
        		
        	});
        	
 
        	//新增role
        	$("#saveRoleBtn").click(function(){
        		$('#saveRoleModal').modal("toggle");
        	});
 
        	$("#saveSubmitBtn").click(function(){
        		var name = $('#saveRoleModal input[name="name"]').val();
        		$.post("${PATH}/role/saveRole",{"name":name},function(result){
        			if ("ok"==result){
        				var pages = $("#saveRoleModal input[name='pages']").val();
        				layer.msg("添加成功",{time:1000,icon:7});
        				setTimeout(function(){
        					initRolesTable(pages+1);
        					//隐藏模态框
        					$("#saveRoleModal").modal("hide");
        				},1000);
        				
        			}	
        		});
        		
        	});
        	
        
        	//删除role
        	function deleteRole(id){
        		
        		layer.confirm("你确定要删除吗？",{title:"删除提示",icon:5, btn:["确定","取消"]},function(index){
        			layer.close(index);
        			layer.msg("删除成功",{time:1000, icon:6});
        			
        			$.ajax({
            			type:"POST",
            			url:"${PATH}/role/deleteRole",
            			data:{"id":id},
            			dataType:"JSON",
            			success:function(result){
            				
            				if (result.status == 403){
            					$("tfoot .pagination").append("<li style='color:red;'>"+result.message+"</li>");
            					return;
            				}
            				
            				if ("ok"==result){
            					var condition = $("input[name=condition]").val();
        						var pageNum = $(".active").prop("id");
        						initRolesTable(pageNum,condition);
            				}
            			}
        			});
        			
	
        		},function(index){
        			layer.close(index);
					var condition = $("input[name=condition]").val();
					var pageNum = $(".active").prop("id");
					initRolesTable(pageNum,condition);
        		});
        	}
        	
        	//给角色分配权限的按钮的单击事件
        	function assignPermissionToRole(roleId){
        		//1.异步请求根据roleId查询角色已经拥有的权限id列表
        		$.post("${PATH}/role/getRolePermissionIds", {"id":roleId}, function(permissionIds){
        			//alert(JSON.stringify(permissionIds));
        			//2.查询所有的权限列表
        			$.get("${PATH}/permission/getPermissions", function(permissions){
        				$.each(permissions, function(){
        					if (permissionIds.indexOf(this.id) > -1){
        						this.checked = true;
        					}
        				});
        				
        				//console.log(permissions);
        				//3.将权限列表以带复选框的树状图形式显示在模态框中
        				//3.1.引入ztree相关的js和css文件
        				//3.2 页面中准备ul容器
        				//3.3 设置setting，调用ztree的init方法将permissions解析显示到ztree树中
        				function addDiyDom(treeId, treeNode) {
        					$("#"+treeNode.tId+"_ico").removeClass();
        					$("#"+treeNode.tId+"_span").before("<span class='"+treeNode.icon+"'></span>");
						};
        				var setting = {
       						view: {
       							addDiyDom: addDiyDom
       						},
       						check:{
       							enable:true
       						},
        					data:{
        						key:{
        							name:"title"
        						},
        						simpleData:{
        							enable:true,
        							pIdKey:"pid"
        						}
        					}
        						
        				};
        				var $ztreeNode = $.fn.zTree.init($("#permissionTree"), setting, permissions);
        				$ztreeNode.expandAll(true);
        				//设置隐藏域的roleid
        				$("#assignPermissionToRoleModal input[name='roleId']").val(roleId);
                		//4.显示模态框
                		$("#assignPermissionToRoleModal").modal("toggle");
        			});
            		
        		});

        	}
        
        	//提取显示列表的方法
        	function initTableBody(pageInfo){
        		var $tbody = $("table tbody");
        		$tbody.empty();
        		$.each(pageInfo.list, function(index){
        			
        			$('<tr></tr>').append('<td>'+(index+1)+'</td>')
	       				.append('<td><input type="checkbox"></td>')
	       				.append('<td>'+this.name+'</td>')
	       				.append('<td>\
	       					      <button type="button" onclick="assignPermissionToRole('+this.id+')" class="btn btn-success btn-xs"><i class=" glyphicon glyphicon-check"></i></button>\
	       					      <button type="button" rid="'+this.id+'" class="btn btn-primary btn-xs editRoleBtn"><i class=" glyphicon glyphicon-pencil"></i></button>\
	       						  <button onclick="deleteRole('+this.id+')" type="button" class="btn btn-danger btn-xs"><i class=" glyphicon glyphicon-remove"></i></button>\
	       					  	</td>')
     					.appendTo($tbody)
        			
        		});
        	}
        	//提取显示导航页的方法
        	function initTableFoot(pageInfo){
        		var $ul = $("table tfoot .pagination");
        		$ul.empty();
        		//有上一页
        		if (pageInfo.hasPreviousPage) {
        			$('<li><a class="navPage" pageNum="'+(pageInfo.pageNum-1)+'">上一页</a></li>').appendTo($ul);
        		}else{
        			$('<li class="disabled"><a href="javascript:void(0);">上一页</a></li>').appendTo($ul);
        		}
        		
        		//分页数
        		$.each(pageInfo.navigatepageNums, function(){
        			if (this==pageInfo.pageNum){
        				$('<li class="active" id="'+this+'"><a href="javascript:void(0);">'+this+' <span class="sr-only">(current)</span></a></li>').appendTo($ul);
        			}else{
        				$('<li><a class="navPage" pageNum="'+this+'">'+this+'</a></li>').appendTo($ul);
        			}
        		})
        		
        		//有下一页
        		if (pageInfo.hasNextPage) {
        			$('<li><a class="navPage" pageNum="'+(pageInfo.pageNum+1)+'">下一页</a></li>').appendTo($ul);
        		}else{
        			$('<li class="disabled"><a href="javascript:void(0);">下一页</a></li>').appendTo($ul);
        		}
        	}
        	
        	//提取异步查询分页信息并解析到页面的代码
        	function initRolesTable(pageNum, condition){
        		$.get("${PATH}/role/listRoles", {"pageNum":pageNum, "condition":condition}, function(pageInfo){
            		//添加总页数
        			$("#saveRoleModal input[name='pages']").val(pageInfo.pages);
  		
        			initTableBody(pageInfo);
        			
        			initTableFoot(pageInfo);
   
            	});
        	}
        	
        	initRolesTable();
        	//使用ajax需要动态委派
        	$("table tfoot").delegate(".navPage", "click", function(){
        	
        		var pageNum = $(this).attr("pageNum");
        		
        		var condition = $("input[name=condition]").val();
        		
        		initRolesTable(pageNum,condition);
        	});
        	
        	$("#queryRoleBtn").click(function(){
        		var condition = $("input[name=condition]").val();
        		
        		initRolesTable(1,condition);
        	});
        
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
            
            $("tbody .btn-success").click(function(){
                window.location.href = "assignPermission.html";
            });
        </script>
        <script type="text/javascript" src="${PATH}/static/include/role.js"></script>
  </body>
</html>
