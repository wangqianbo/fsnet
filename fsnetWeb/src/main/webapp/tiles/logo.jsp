<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>

<div id="logo2">
	<img src="images/FSNET.png"/>
</div>
<html:link action="/DisplayProfile" styleId="userPicture">
	<img src="GetPhoto.do?memberId=${sessionScope.userId}"/>
</html:link>
 