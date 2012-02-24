<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>



<fieldset class="fieldsetAdmin">
	<legend class="legendAdmin">
		<bean:message key="members.createMultiple" />
	</legend>

	<html:form action="/CreateMultipleMember">
		<table class="fieldsetTableAdmin">
			<tr>
				<td colspan="2"><bean:message
						key="members.createMultipleIndications" /></td>
			</tr>
			<tr>
				<td colspan="2"><bean:message
						key="members.createMultipleFormat" /></td>
			</tr>
			<tr class="errorMessage">
				<td colspan="2"><html:errors property="multipleMember" /></td>
			</tr>
			<tr>
				<td colspan="2"><html:textarea property="multipleMember"
						styleId="multipleMember" errorStyleClass="error" cols="80"
						rows="6" /></td>
				<jsp:include page="/content/manageMembers/SamePartForMember.jsp" />
		</table>
	</html:form>
</fieldset>
