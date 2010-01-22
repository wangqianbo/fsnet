<%-- 
    Document   : Topic
    Created on : 19 janv. 2010 23:51
    Author     : Zhu rui <zrhurey@gmail.com>
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>   
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<h3>the topics</h3>
<table>
<c:forEach var="msg" items="${lesMsgs}">
    <tr><td><html:link action="" styleClass="button">
		<html:param name="msgId" value="${msg.id}" />
		${msg.dateMessage} ${msg.propMsg}</html:link></td></tr>
	<c:if test="${not empty contenu}">
		<tr><td>${contenu}</td></tr>
	</c:if>	
</c:forEach>
</table>
