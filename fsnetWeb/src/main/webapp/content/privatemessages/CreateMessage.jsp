<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>


<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script type="text/javascript" src="js/tiny_mce/tiny_mce.js"></script>
<script type="text/javascript" src="js/mceTextArea.js"></script>
<script type="text/javascript" src="js/dmsAutoComplete.js"></script>



<fieldset class="fieldsetCadre">
	<legend>
		<bean:message key="privatemessages.sendM" />
	</legend>
	<table class="inLineTable tableStyle">
		<html:form action="/CreatePrivateMessage">

			<tr>
				<td><label for="memberSearch"> <bean:message
							key="privatemessages.recipient" />
				</label></td>
				<td><c:choose>
						<c:when test="${! empty param.receiver}">
							<html:text property="messageTo" errorStyleClass="error"
								style="width: 95%" styleId="memberSearch"
								value="${param.receiver}" />
						</c:when>
						<c:otherwise>
							<html:text property="messageTo" errorStyleClass="error"
								style="width: 95%" styleId="memberSearch" />
						</c:otherwise>
					</c:choose> <logic:messagesPresent property="messageTo">
						<div class="errorMessage">
							<html:errors property="messageTo" />
						</div>
					</logic:messagesPresent>
					<div id="searchDiv" class="ajaxSearch"></div></td>
			</tr>

			<tr>
				<td><label for="messageSubject"> <bean:message
							key="privatemessages.subject" />
				</label></td>
				<td><html:text property="messageSubject"
						errorStyleClass="error" styleId="messageSubject" style="width: 95%" /> <logic:messagesPresent
						property="messageSubject">
						<div class="errorMessage">
							<html:errors property="messageSubject" />
						</div>
					</logic:messagesPresent></td>
			</tr>

			<tr>
				<td><label for="messageBody"> <bean:message
							key="privatemessages.body" />
				</label></td>
				<td><html:textarea property="messageBody" styleId="messageBody"
						styleClass="mceTextArea" style="width: 100%;">
					</html:textarea> <logic:messagesPresent property="messageBody">
						<div class="errorMessage">
							<html:errors property="messageBody" />
						</div>
					</logic:messagesPresent></td>
			</tr>

			<tr>
				<td colspan="2" class="alignRight"><html:submit
						styleClass="btn btn-inverse"
						onclick="this.disabled=true; this.value='Sending Message'; this.form.submit();">
						<bean:message key="privatemessages.send" />
					</html:submit></td>
			</tr>
		</html:form>
	</table>
</fieldset>

<script type="text/javascript">
	var AC = new dmsAutoComplete('memberSearch', 'searchDiv');
	AC.clearField = false;
	AC.ajaxTarget = 'AjaxSearchMember.do';
</script>