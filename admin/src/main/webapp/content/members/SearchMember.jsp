<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>

<h3><bean:message key="members.search"/></h3>
<html:form action="SearchMember">
    <div id="SearchMember">
        <html:text property="searchText" />
        <html:submit styleClass="button"><bean:message key="members.searchButton"/></html:submit>
    </div>
</html:form>

<h3><bean:message key="members.listMembers"/></h3>

<c:choose>
<c:when test="${! empty membersResult}">
	<table  class="inLineTable">
        <c:forEach var="member" items="${membersResult}">
            <tr class="content">
                <td><html:link action="/DisplayMember">${member.name} ${member.firstName}
                		<html:param name="idMember" value="${member.id}"/>
                	</html:link>
                </td>
                  <td class="tableButton">
                    <html:link action="/Members.do" styleClass="button" onclick="confirmDelete('DeleteMember.do?entitySelected='+${member.id})">
                       <bean:message key="members.searchDelete"/>
                    </html:link>
                </td>
            </tr>
        </c:forEach>
    </table>
</c:when>
<c:otherwise>
	<bean:message key="members.noResult"/>
</c:otherwise>
</c:choose>