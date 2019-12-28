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

	<!-- 静态包含css样式 -->
	<%@ include file="/WEB-INF/include/base_css.jsp" %>
	
	<style>
	.tree li {
        list-style-type: none;
		cursor:pointer;
	}
	.tree-closed {
	    height : 40px;
	}
	.tree-expanded {
	    height : auto;
	}
	</style>
  </head>

  <body>

    <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
      <div class="container-fluid">
        <div class="navbar-header">
          <div><a class="navbar-brand" style="font-size:32px;" href="#">众筹平台 - 控制面板</a></div>
        </div> 
        <!-- 静态包含头部菜单信息 -->	
        <%@include file="/WEB-INF/include/manager_header.jsp" %>
		
      </div>
    </nav>
    
    <div class="container-fluid">
      <div class="row">
        <div class="col-sm-3 col-md-2 sidebar">
        <!-- 静态包含左侧菜单信息 -->
        <%@include file="/WEB-INF/include/manager_menu.jsp" %>

        </div>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
          <h1 class="page-header">控制面板</h1>

          <div class="row placeholders">
            <div class="col-xs-6 col-sm-3 placeholder">
              <img data-src="holder.js/200x200/auto/sky" class="img-responsive" alt="Generic placeholder thumbnail">
              <h4>Label</h4>
              <span class="text-muted">Something else</span>
            </div>
            <div class="col-xs-6 col-sm-3 placeholder">
              <img data-src="holder.js/200x200/auto/vine" class="img-responsive" alt="Generic placeholder thumbnail">
              <h4>Label</h4>
              <span class="text-muted">Something else</span>
            </div>
            <div class="col-xs-6 col-sm-3 placeholder">
              <img data-src="holder.js/200x200/auto/sky" class="img-responsive" alt="Generic placeholder thumbnail">
              <h4>Label</h4>
              <span class="text-muted">Something else</span>
            </div>
            <div class="col-xs-6 col-sm-3 placeholder">
              <img data-src="holder.js/200x200/auto/vine" class="img-responsive" alt="Generic placeholder thumbnail">
              <h4>Label</h4>
              <span class="text-muted">Something else</span>
            </div>
          </div>
        </div>
      </div>
    </div>

		    
     <!-- 静态包含js -->
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
			    
			    $("#logoutBtn").click(function(){
			    	var url = this.href;
			    	//确认注销提示
			    	layer.confirm("你真的要注销吗？", {icon:2, title:"退出系统"}, function(index){
			    		layer.close(index);
			    		layer.msg("注销中~~~",{icon:16, time:1000});
			    		//1秒后执行传入的函数
			    		setTimeout(function(){
			    			window.location = url;
			    		}, 1000);
			    	})
			    	
			    	return false;//取消a标签的默认行为
			    });
			    
			  	//设置当前页面的"控制面板"高亮显示
	          	$(".tree a:contains('控制面板')").css("color", "red");
			    
            });
          
        </script>
  </body>
</html>
