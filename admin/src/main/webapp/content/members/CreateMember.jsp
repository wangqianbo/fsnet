<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<link rel=stylesheet type="text/css" href="css/osx.css" />
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/jquery.simplemodal.js"></script>
<script type="text/javascript" src="js/osx.js"></script>

<fieldset class="fieldsetAdmin">
	<legend class="legendAdmin">
		<bean:message key="members.create" />
	</legend>

	<html:form action="/CreateMember">
		<table id="CreateMember" class="fieldsetTableAdmin">
			<tr>
				<td><label for="name"> <bean:message key="members.name" />
				</label></td>
				<td><html:text property="name" styleId="name"
						errorStyleClass="error" />
					<div class="errorMessage">
						<html:errors property="name" />
					</div></td>
			</tr>

			<tr>
				<td><label for="firstName"> <bean:message
							key="members.firstName" />
				</label></td>
				<td><html:text property="firstName" styleId="firstName"
						errorStyleClass="error" />
					<div class="errorMessage">
						<html:errors property="firstName" />
					</div></td>
			</tr>

			<tr>
				<td><label for="email"> <bean:message
							key="members.email" />
				</label></td>
				<td><html:text property="email" styleId="email"
						errorStyleClass="error" />
					<div class="errorMessage">
						<html:errors property="email" />
					</div></td>
			</tr>

			<tr>
				<td><label for="parentId"> <bean:message
							key="members.group" />
				</label></td>
				<td colspan="3"><html:select property="parentId"
						styleClass="select" styleId="parentId">
						<html:option value="">
							<bean:message key="members.listGroups" />
						</html:option>
						<c:forEach var="socialGroup" items="${sessionScope.allGroups2}">
							<html:option value="${socialGroup.id}">${socialGroup.name}</html:option>
						</c:forEach>
					</html:select></td>
			</tr>

			<tr>
				<td><label for="typePassword1"> <bean:message
							key="members.generatePassword" />
				</label></td>
				<td><html:radio property="typePassword" styleId="typePassword1"
						value="generatePassword" onclick="definePasword()" /></td>
			</tr>

			<tr>
				<td><label for="typePassword2"> <bean:message
							key="members.definePassword" />
				</label></td>
				<td><html:radio property="typePassword" styleId="typePassword2"
						value="definePassword" onclick="definePasword()" /></td>
			</tr>
			<tr>
				<td><label for="password"> <bean:message
							key="members.password" />
				</label></td>
				<td><html:password property="password" styleId="password"
						errorStyleClass="error" />
					<div class="errorMessage">
						<html:errors property="password" />
					</div></td>
			</tr>

			<tr>
				<td><label for="passwordConfirmation"> <bean:message
							key="members.passwordConfirmation" />
				</label></td>
				<td><html:password property="passwordConfirmation"
						styleId="passwordConfirmation" errorStyleClass="error" />
					<div class="errorMessage">
						<html:errors property="passwordConfirmation" />
					</div></td>
			</tr>

			<%@ include file="SamePartForMember.jsp"%>
		</table>
	</html:form>
</fieldset>

<script type="text/javascript">
	function definePasword() {
		var generatePassword = document.getElementById('generatePassword');
		var password = document.getElementById('password');
		var passwordConfirmation = document
				.getElementById('passwordConfirmation');
		if (generatePassword.checked) {
			passwordConfirmation.disabled = true;
			password.disabled = true;
		} else {
			passwordConfirmation.disabled = false;
			password.disabled = false;
		}
	}
</script>
