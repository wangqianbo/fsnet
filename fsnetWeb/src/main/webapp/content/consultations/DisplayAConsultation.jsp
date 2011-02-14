<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>

<c:if test="${consultation eq null }"><p><bean:message key="consultation.unavailable" /></p></c:if>

<c:if test="${consultation ne null }">
<h3>${consultation.title }</h3>
<table>
	<tr>
		<td class="consultationPerticipant"><bean:message key="consultation.voter" /></td>
		<c:forEach var="choice" items="${consultation.choices }">
			<td class="consultationResultChoice">${choice.intituled }</td>
		</c:forEach>
		<td class="consultationComment"><bean:message key="consultation.comment" /></td>
	</tr>
	<c:forEach var="vote" items="${consultation.consultationVotes }">
		<tr>
		<td class="consultationPerticipant"><ili:getSocialEntityInfos socialEntity="${vote.voter }" /></td>
		<c:forEach var="choice" items="${consultation.choices }">
			<c:set var="isVoted" value="false"/>
			<c:forEach var="choiceVote" items="${vote.choices }">
				<c:if test="${choice.id eq choiceVote.choice.id }"><c:set var="isVoted" value="true"/></c:if>
			</c:forEach>
			<td <c:if test="${isVoted}">class="consultationIsVoted"</c:if>
			<c:if test="${not isVoted}">class="consultationIsNotVoted"</c:if> /></td>
		</c:forEach>
		<td class="consultationComment">${vote.comment}</td>
		</tr>
	</c:forEach>
	<tr>
		<td></td>
		<ili:consultationResults consultation="${consultation}" number="number" percent="percent" maximum="max">
			<td class="${max?'consultationResultMax':'consultationResult' }">${number}<br />${percent }%</td>
		</ili:consultationResults>
	<tr>
	<html:form action="/VoteConsultation" method="post">
		<tr>
			<td></td>
			<c:forEach var="choice" items="${consultation.choices }">
				<td class="consultationFormChoices"><html:multibox property="voteChoice" value="${choice.id}" /></td>
			</c:forEach>
			<td><html:text property="voteComment"/></td>
			<html:hidden property="id" value="${consultation.id }"/>
			<td><html:submit styleClass="button"><bean:message key="consultation.vote" /></html:submit></td>
		</tr>
	</html:form>
</table>
</c:if>