/**
 * 
 */
  	//设置当前页面的"角色维护"高亮显示
  	$(".tree a:contains('角色维护')").css("color", "red");
    $(".tree a:contains('角色维护')").parents("ul").show();
    $(".tree a:contains('角色维护')").parents("li").toggleClass("tree-closed")