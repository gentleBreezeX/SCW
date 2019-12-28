<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="zh-CN">
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
            <div><a class="navbar-brand" style="font-size:32px;" href="user.html">众筹平台 - 用户维护</a></div>
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
				<ol class="breadcrumb">
				  <li><a href="#">首页</a></li>
				  <li><a href="#">数据列表</a></li>
				  <li class="active">分配角色</li>
				</ol>
			<div class="panel panel-default">
			  <div class="panel-body">
				<form role="form" class="form-inline">
				  <div class="form-group">
					<label for="exampleInputPassword1">未分配角色列表</label><br>
					<select id="unAssignRoleSelect" class="form-control" multiple size="10" style="width:100px;overflow-y:auto;">
						<c:forEach items="${requestScope.unAssignRoles}" var="role">
							<option value="${role.id}">${role.name}</option>
						</c:forEach>
					
                        
                    </select>
				  </div>
				  <div class="form-group">
                        <ul>
                            <li id="assignRoleToAdminBtn" class="btn btn-default glyphicon glyphicon-chevron-right"></li>
                            <br>
                            <li id="unAssignRoleFromAdminBtn" class="btn btn-default glyphicon glyphicon-chevron-left" style="margin-top:20px;"></li>
                        </ul>
				  </div>
				  <div class="form-group" style="margin-left:40px;">
					<label for="exampleInputPassword1">已分配角色列表</label><br>
					<select id="assignRoleSelect" class="form-control" multiple size="10" style="width:100px;overflow-y:auto;">
                       <c:forEach items="${requestScope.assignRoles}" var="role">
							<option value="${role.id}">${role.name}</option>
						</c:forEach>
                    </select>
				  </div>
				</form>
			  </div>
			</div>
        </div>
      </div>
    </div>
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	  <div class="modal-dialog">
		<div class="modal-content">
		  <div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
			<h4 class="modal-title" id="myModalLabel">帮助</h4>
		  </div>
		  <div class="modal-body">
			<div class="bs-callout bs-callout-info">
				<h4>测试标题1</h4>
				<p>测试内容1，测试内容1，测试内容1，测试内容1，测试内容1，测试内容1</p>
			  </div>
			<div class="bs-callout bs-callout-info">
				<h4>测试标题2</h4>
				<p>测试内容2，测试内容2，测试内容2，测试内容2，测试内容2，测试内容2</p>
			  </div>
		  </div>
		  <!--
		  <div class="modal-footer">
			<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
			<button type="button" class="btn btn-primary">Save changes</button>
		  </div>
		  -->
		</div>
	  </div>
	</div>
		<%@ include file="/WEB-INF/include/base_js.jsp" %>
		<!-- 用户维护高亮显示 -->
		<script type="text/javascript" src="${PATH}/static/include/admin.js"></script>
        <script type="text/javascript">
        
        	//分配角色给admin
        	$("#assignRoleToAdminBtn").click(function(){
        		var idsStr = "";
        		$("#unAssignRoleSelect option:selected").each(function(){
        			idsStr += "roleId=" + this.value + "&"; 
        		});
        		//idsStr = idsStr.substring(0,idsStr.length-1);
				var adminId = "${param.id}";
				idsStr += "adminId=" + adminId;
        		$.ajax({
        			type:"post",
        			url:"${PATH}/admin/assignRoleToAdmin",
        			data:idsStr,
        			success:function(result){
        				if ("ok" == result){
        					$("#unAssignRoleSelect option:selected").appendTo("#assignRoleSelect");
        				}
        			}
        		});
        	});
        	
        	//给admin取消分配角色
        	$("#unAssignRoleFromAdminBtn").click(function(){
        		var idsStr = "";
        		$("#assignRoleSelect option:selected").each(function(){
        			idsStr += "roleId=" + this.value + "&"; 
        		});
        		//idsStr = idsStr.substring(0,idsStr.length-1);
				var adminId = "${param.id}";
				idsStr += "adminId=" + adminId;
        		$.ajax({
        			type:"post",
        			url:"${PATH}/admin/unAssignRoleToAdmin",
        			data:idsStr,
        			success:function(result){
        				if ("ok" == result){
        					$("#assignRoleSelect option:selected").appendTo("#unAssignRoleSelect");
        				}
        			}
        		});
        		
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
        </script>
  </body>
</html>
