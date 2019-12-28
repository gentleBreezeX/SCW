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
          <div><a class="navbar-brand" style="font-size:32px;" href="#">众筹平台 - 用户维护</a></div>
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
<form class="form-inline" role="form" method="post" action="${PATH}/admin/index.html" style="float:left;">
  <div class="form-group has-feedback">
    <div class="input-group">
      <div class="input-group-addon">查询条件</div>
      <input class="form-control has-success" name="condition" value="${param.condition}" placeholder="请输入查询条件">
    </div>
  </div>
  <button type="submit" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询</button>
</form>
<button id="deleteManyAdminBtn" type="button" class="btn btn-danger" style="float:right;margin-left:10px;"><i class=" glyphicon glyphicon-remove"></i> 删除</button>
<button type="button" class="btn btn-primary" style="float:right;" onclick="window.location.href='${PATH}/add.html'"><i class="glyphicon glyphicon-plus"></i> 新增</button>
<br>
 <hr style="clear:both;">
          <div class="table-responsive">
            <table class="table  table-bordered">
              <thead>
                <tr >
                  <th width="30">#</th>
				  <th width="30"><input type="checkbox"></th>
                  <th>账号</th>
                  <th>名称</th>
                  <th>邮箱地址</th>
                  <th width="100">操作</th>
                </tr>
              </thead>
              <tbody>
              <c:forEach items="${pageInfo.list}" var="admin" varStatus="vs">
              	<tr>
                  <td>${vs.count}</td>
				  <td><input id="${admin.id}" type="checkbox"></td>
                  <td>${admin.loginacct }</td>
                  <td>${admin.username }</td>
                  <td>${admin.email }</td>
                  <td>
				      <button adminId="${admin.id }" type="button" class="btn btn-success btn-xs"><i class=" glyphicon glyphicon-check"></i></button>
				      <button adminId="${admin.id }" type="button" class="btn btn-primary btn-xs"><i class=" glyphicon glyphicon-pencil"></i></button>
					  <button adminId="${admin.id }" type="button" class="btn btn-danger btn-xs deleteAdminBtn"><i class=" glyphicon glyphicon-remove"></i></button>
				  </td>
                </tr>
              </c:forEach> 
              </tbody>
			  <tfoot>
			     <tr >
				     <td colspan="6" align="center">
						<ul class="pagination">
							<c:choose>
								<c:when test="${pageInfo.hasPreviousPage}">
									<li><a href="${PATH}/admin/index.html?pageNum=${pageInfo.pageNum-1}&condition=${param.condition}">上一页</a></li>
								</c:when>
								<c:otherwise>
									<li class="disabled"><a href="javascript:void(0);">上一页</a></li>
								</c:otherwise>
							</c:choose>
							<c:forEach items="${pageInfo.navigatepageNums}" var="index">
								<c:choose>
									<c:when test="${pageInfo.pageNum == index}">
										<li class="active"><a href="${PATH}/admin/index.html?pageNum=${index}&condition=${param.condition}">${index}<span class="sr-only">(current)</span></a></li>
									</c:when>
									<c:otherwise>
										<li><a href="${PATH}/admin/index.html?pageNum=${index}&condition=${param.condition}">${index}</a></li>
									</c:otherwise>
								</c:choose>
								
							</c:forEach>							
							<c:choose>
								<c:when test="${pageInfo.hasNextPage}">
									<li><a href="${PATH}/admin/index.html?pageNum=${pageInfo.pageNum+1}&condition=${param.condition}">下一页</a></li>
								</c:when>
								<c:otherwise>
									<li class="disabled"><a href="javascript:void(0);">下一页</a></li>
								</c:otherwise>
							</c:choose>
							
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


	<%@ include file="/WEB-INF/include/base_js.jsp" %>
	
        <script type="text/javascript">
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
            	var adminId = $(this).attr("adminId");
                window.location.href = "${PATH}/admin/assignRole.html?id=" + adminId;
            });
            $("tbody .btn-primary").click(function(){
            	var adminId = $(this).attr("adminId");
                window.location.href = "${PATH}/admin/edit.html?id=" + adminId;
            });
            
            $(".deleteAdminBtn").click(function () {
				var adminId = $(this).attr("adminId");
				var name = $(this).parents("tr").find("td:eq(3)").text();
				layer.confirm("你确定要删除"+ name +"吗？", {title:"删除提示", icon:3}, function(index){
					layer.close(index);
					layer.msg('删除成功',{time:1000},function(){
						window.location = "${PATH}/admin/deleteAdmin?id=" + adminId;	
					});
					
				});
			});
            //全实现批量删除的选、全不选   
            $("table thead :checkbox").click(function(){
            	var flag = $("table thead :checkbox").prop("checked");
            	$("table tbody :checkbox").prop("checked", flag);
            });
            
           	$("table tbody :checkbox").click(function(){
            	var totalCount = $("table tbody :checkbox").length;
            	var checkedCount = $("table tbody :checkbox:checked").length;
            	$("table thead :checkbox").prop("checked", totalCount == checkedCount);
            });
           	
           	$("#deleteManyAdminBtn").click(function(){
           		var $checkedInp = $("table tbody :checkbox:checked");
           		var ids = new Array();
           		$.each($checkedInp, function(){
           			ids.push(this.id);
           		});
           		var idsStr = ids.join(",");
           		layer.confirm("你确定要删除选中的信息吗？", {title:"删除提示", icon:4},function(index){
           			layer.close(index);
           			layer.msg("删除成功", {time:1000, icon:5});
           			setTimeout(function(){           				
		           		window.location = "${PATH}/admin/deleteAdmins?ids=" + idsStr;
           			},1000);
           		});
           		
           	});
            
        </script>
        <!-- 实现菜单高亮和开闭和问题 -->
        <script type="text/javascript" src="${PATH}/static/include/admin.js"></script>
  </body>
</html>
