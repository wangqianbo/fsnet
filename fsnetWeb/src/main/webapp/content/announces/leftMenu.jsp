<%@taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>
<h2>
	<bean:message key="announce.leftMenu.my" />
</h2>

<ul>
	<li><ili:interactionFilter user="${ socialEntity }"
			right="${ rightAddAnnounce }">
			<s:a action="/DisplayCreateAnnounce">
				<bean:message key="announce.leftMenu.create" />
			</s:a>
		</ili:interactionFilter></li>
</ul>