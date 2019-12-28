<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
	
<div class="tree">
	<ul style="padding-left: 0px;" class="list-group">
		<!-- 判断菜单是否有子菜单 -->
		<c:forEach items="${parentMenus}" var="parent">
			<c:choose>
				<c:when test="${empty parent.childrenMenus}">
					<li class="list-group-item tree-closed">
						<a href="${PATH}/${parent.url }"><i class="${parent.icon }"></i> ${parent.name}</a>
					</li>
				</c:when>
				<c:otherwise>
					<li class="list-group-item tree-closed"><span><i
							class="glyphicon glyphicon glyphicon-tasks"></i> ${parent.name}<span
							class="badge" style="float: right">${parent.childrenMenus.size()}</span></span>
						<ul style="margin-top: 10px; display: none;">
							<c:forEach items="${parent.childrenMenus}" var="children">
								<li style="height: 30px;">
									<a href="${PATH}/${children.url }"><i class="${children.icon }"></i> ${children.name}</a>
								</li>
							</c:forEach>
						</ul></li>
				</c:otherwise>
			</c:choose>
		</c:forEach>
	</ul>
</div>
