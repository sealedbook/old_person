<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/taglib/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fn.tld" prefix="fn" %>

<!DOCTYPE html>
<html>
  <head>
  	<script type="text/javascript">
  		function go(url) {
  			window.open(url, "rightFrame");
  		}
  	</script>
  </head>
  <body>
	<div class="easyui-panel" style="padding:1px;background-color: #dbeefd">
		<c:forEach items="${menus }" var="menus">
			<a href="#" class="easyui-menubutton" data-options="menu:'#${menus.id }'">${menus.name }</a>
			<div id="${menus.id }" style="width:150px;">
				<c:forEach items="${menus.subSystemMenu }" var="sub1Menus">
					<c:choose>
						<c:when test="${fn:length(sub1Menus.subSystemMenu) <= 0}">
							<div onclick="go('<c:url value="${sub1Menus.url }"/>');">${sub1Menus.name }</div>
						</c:when>
						<c:otherwise>
							<div>
								<span>${sub1Menus.name }</span>
								<div>
									<c:forEach items="${sub1Menus.subSystemMenu }" var="sub2Menus">
										<c:choose>
											<c:when test="${fn:length(sub2Menus.subSystemMenu) <= 0}">
												<div onclick="go('<c:url value="${sub2Menus.url }"/>')">${sub2Menus.name }</div>
											</c:when>
											<c:otherwise>
												<div>
													<span>${sub2Menus.name }</span>
													<div>
														<c:forEach items="${sub2Menus.subSystemMenu }" var="sub3Menus">
															<div onclick="go('<c:url value="${sub3Menus.url }"/>');">${sub3Menus.name }</div>
														</c:forEach>
													</div>
												</div>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</div>
							</div>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</div>
		</c:forEach>
	</div>
	
  </body>
</html>
