<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>


<fieldset class="fieldsetCadre">
	<legend >
		<bean:message key="showProfile.title"
			arg0="${watchedProfile.firstName} ${watchedProfile.name}" />
	</legend>
	<table class="inLineTable tableStyle">
		<tr>
			<td>
				<table class="inLineTable tableStyle">
					<c:if
						test="${watchedProfile.email != null && not empty watchedProfile.email }">
						<tr>
							<th><bean:message key="updateProfile.email" /></th>
							<td><html:link href="mailto:${watchedProfile.email}">
                    ${watchedProfile.email}
                </html:link></td>
						</tr>
					</c:if>
					<c:if
						test="${watchedProfile.address.address != null && not empty watchedProfile.address.address }">
						<tr>
							<th><bean:message key="updateProfile.adress" /></th>
							<td>${watchedProfile.address.address}</td>
						</tr>
					</c:if>
					<c:if
						test="${watchedProfile.address.city != null &&  not empty watchedProfile.address.city}">
						<tr>
							<th><bean:message key="updateProfile.city" /></th>
							<td>${watchedProfile.address.city}</td>
						</tr>
					</c:if>
					<c:if test="${birthDay != null && not empty birthDay }">
						<tr>
							<th><bean:message key="updateProfile.dateOfBirth" /></th>
							<td>${birthDay}</td>
						</tr>
					</c:if>
					<c:if
						test="${watchedProfile.profession != null && not empty  watchedProfile.profession}">
						<tr>
							<th><bean:message key="updateProfile.job" /></th>
							<td>${watchedProfile.profession}</td>
						</tr>
					</c:if>
					<c:if
						test="${watchedProfile.sex != null && not empty  watchedProfile.sex}">
						<tr>
							<th><bean:message key="updateProfile.sexe" /></th>
							<td><bean:message
									key="updateProfile.sexe.${watchedProfile.sex}" /></td>
						</tr>
					</c:if>
					<c:if
						test="${watchedProfile.phone != null && not empty watchedProfile.phone}">
						<tr>
							<th><bean:message key="updateProfile.phone" /></th>
							<td>${watchedProfile.phone}</td>
						</tr>
					</c:if>
				</table>
			</td>
		</tr>
		<tr>
			<td>
			<img src="avatar/${watchedProfile.id}.png" style="float: right;"
				alt="Avatar" />
			</td>
		</tr>
	</table>
</fieldset>


<div class="placeButton">
	<c:if
		test="${watchedProfile.id != currentUser.id && !alreadyInContact}">
		<html:link action="/ContactDemand" styleClass="btn btn-inverse">
			<bean:message key="showProfile.ask" />
			<html:param name="entitySelected" value="${watchedProfile.id}" />
		</html:link>
	</c:if>

	<c:if test="${watchedProfile.id != currentUser.id}">
		<html:link action="/DisplayCreatePrivateMessage" styleClass="btn btn-inverse">
			<bean:message key="showProfile.send" />
			<html:param name="receiver" value="${watchedProfile.email}" />
		</html:link>
	</c:if>
	<c:if test="${watchedProfile.id != currentUser.id && isLogged}">
		<a class="btn btn-inverse"
			onclick="javascript:chatWith('${watchedProfile.name}_${watchedProfile.id}','habib2@master11.com')">
			<bean:message key="showProfile.chat" />
		</a>
	</c:if>
</div>

<div class="clear"></div>

<fieldset class="fieldsetCadre">
	<legend>
		<bean:message key="showInterest.title"
			arg0="${watchedProfile.firstName} ${watchedProfile.name}" />
	</legend>
	<table class="inLineTable tableStyle">
		<tr>
			<td><logic:empty name="watchedProfile" property="interests">
					<bean:message key="Profile.noInterests" />.
</logic:empty>

				<div class="cloud">
					<c:forEach var="interest"
						items="${requestScope.interestPaginator.resultList}">

						<span class="tag"> <html:link
								action="/InterestInformations">
								<html:param name="infoInterestId" value="${interest.id}" />
                ${interest.name}
            </html:link>
						</span>
					</c:forEach>
				</div>
				<div class="clear"></div> <c:set var="paginatorInstance"
					value="${requestScope.interestPaginator}" scope="request" /> <c:set
					var="paginatorAction" value="/DisplayProfile" scope="request" /> <c:set
					var="paginatorTile" value="profileInterests" scope="request" /> <c:import
					url="/content/pagination/Pagination.jsp" /></td>
		</tr>
	</table>
</fieldset>

<fieldset class="fieldsetCadre">
	<legend>
		<bean:message key="profile.showInteraction.title"
			arg0="${watchedProfile.firstName} ${watchedProfile.name}" />
	</legend>
	<c:choose>
		<c:when test="${empty requestScope.interactions}">

			<table class="inLineTable tableStyle">
				<tr>
					<td><bean:message key="Profile.noInteractions" />.</td>
				</tr>
			</table>
		</c:when>
		<c:otherwise>
			<div class="space"></div>
			<script type="text/javascript">
				$(document).ready(
						function pagination() {
							var nomTable = "tableinteractions";
							var idColonneATrier = 2;
							var sensDeTri = "desc";
							var aoColumns = [ {
								"bSortable" : false
							}, null, {
								"sType" : "date-euro"
							} ];
							miseEnPageTable(nomTable, idColonneATrier,
									sensDeTri, aoColumns, false, 5);
						});
			</script>

			<table id="tableinteractions"
				class="inLineTable tableStyle">
				<thead>
					<tr>
						<th></th>
						<th><bean:message key="tableheader.name" /></th>
						<th><bean:message key="interaction.lastModif" /></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="inter" items="${requestScope.interactions}">
						<tr>
							<td><c:import url="/FavoriteFragment.do">
									<c:param name="interactionId" value="${inter.id}" />
								</c:import></td>
							<td><c:choose>
									<c:when test="${inter.simpleClassName eq 'Announcement'}">
										<html:link action="/DisplayAnnounce">
											<html:param name="idAnnounce" value="${inter.id}" />
                                ${inter.title}
                            </html:link>
									</c:when>
									<c:when test="${inter.simpleClassName eq 'Meeting'}">
										<html:link action="/DisplayEvent">
											<html:param name="eventId" value="${inter.id}" />
                                ${inter.title}
                            </html:link>
									</c:when>
									<c:when test="${inter.simpleClassName eq 'Topic'}">
										<html:link action="/Topic">
											<html:param name="topicId" value="${inter.id}" />
                                ${inter.title}
                                 
                            </html:link>
                            (${fn:length(inter.messages)} messages)
                        </c:when>
									<c:when test="${inter.simpleClassName eq 'Hub'}">
										<html:link action="/DisplayHub">
											<html:param name="hubId" value="${inter.id}" />
                                ${inter.title}
                            </html:link>
                            (${fn:length(inter.topics)} topics)
                        </c:when>
									<c:when test="${inter.simpleClassName eq 'Community'}">
										<html:link action="/DisplayCommunity">
											<html:param name="communityId" value="${inter.id}" />
                                ${inter.title}
                            </html:link>
                            (${fn:length(inter.hubs)} hubs)
                        </c:when>
									<c:when test="${inter.simpleClassName eq 'Consultation'}">
										<html:link action="/DisplayAConsultation">
											<html:param name="id" value="${inter.id}" />
                                ${inter.title}
                            </html:link>
									</c:when>
								</c:choose></td>
							<td><bean:write name="inter" property="lastModified"
									format="dd/MM/yyyy" /></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:otherwise>
	</c:choose>
</fieldset>


<fieldset class="fieldsetCadre">
	<legend>
		<bean:message key="showProfile.contacts.title" />
	</legend>
	<table class="inLineTable tableStyle">
		<tr>
			<td><logic:empty name="watchedProfile" property="contacts">
					<c:choose>
						<c:when test="${edit}">
							<bean:message key="showProfile.IHaveNoContacts" />
						</c:when>
						<c:otherwise>
							<bean:message key="showProfile.noContacts" />
						</c:otherwise>
					</c:choose>
				</logic:empty> <logic:iterate
					collection="${requestScope.contactsPaginator.resultList}" id="user">
					<ili:getMiniature socialEntity="${user}" />
				</logic:iterate>
				<div class="clear"></div> <c:set var="paginatorInstance"
					value="${requestScope.contactsPaginator}" scope="request" /> <c:set
					var="paginatorAction" value="/DisplayProfile" scope="request" /> <c:set
					var="paginatorTile" value="profileContacts" scope="request" /> <c:import
					url="/content/pagination/Pagination.jsp" /></td>
		</tr>
	</table>
</fieldset>

<fieldset class="fieldsetCadre">
	<legend>
		<bean:message key="showProfile.groups.tree" />
	</legend>
	<table class="inLineTable tableStyle">
		<tr>
			<td>
				<ul>
					<c:choose>
						<c:when test="${requestScope.treeGroupProfile ne ''}">
							<li><html:link action="/DisplayInformationGroup">
								<html:param name="idGroup" value="${ socialGroup.id }" />
								${requestScope.treeGroupProfile}
								</html:link>
							</li>

						</c:when>
						<c:otherwise>
							<li><bean:message key="avatar.member.no.group" /></li>
						</c:otherwise>
					</c:choose>
				</ul>
			</td>
		</tr>
	</table>
</fieldset>


