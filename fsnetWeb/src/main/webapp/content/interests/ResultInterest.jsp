<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<logic:present name="interestSearchPaginator" scope="request">
	<table class="inLineTable fieldsetTableAppli">
		<tr>
			<td><c:choose>
					<c:when
						test="${not empty requestScope.interestSearchPaginator.resultList}">
						<div class="cloud">
							<c:forEach var="interest"
								items="${requestScope.interestSearchPaginator.resultList}">
								<div>
									<html:link action="/InterestInformations">
										<html:param name="infoInterestId" value="${interest.id}" />
	                		${interest.name}
	                	</html:link>
								</div>
							</c:forEach>
						</div>
					</c:when>
					<c:otherwise>
						<bean:message key="interests.search.empty" />
					</c:otherwise>
				</c:choose></td>
		</tr>
	</table>
	<c:set var="paginatorInstance"
		value="${requestScope.interestSearchPaginator}" scope="request" />
	<c:set var="paginatorAction" value="/SearchInterest" scope="request" />
	<c:set var="paginatorTile" value="search" scope="request" />
	<c:import url="/content/pagination/Pagination.jsp" />
</logic:present>

