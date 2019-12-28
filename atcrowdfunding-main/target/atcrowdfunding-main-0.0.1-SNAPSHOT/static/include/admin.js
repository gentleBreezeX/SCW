/**
 * 
 */
  	//设置当前页面的"用户维护"高亮显示
  	$(".tree a:contains('用户维护')").css("color", "red");
    $(".tree a:contains('用户维护')").parents("ul").show();
    $(".tree a:contains('用户维护')").parents("li").toggleClass("tree-closed")