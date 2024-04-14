<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="developer.training-session.list.label.code" path="code" width="20%"/>
	<acme:list-column code="developer.training-session.list.label.start-date" path="startDate" width="20%"/>
	<acme:list-column code="developer.training-session.list.label.end-date" path="endDate" width="20%"/>	
	<acme:list-column code="developer.training-session.list.label.location" path="location" width="20%"/>	
	<acme:list-column code="developer.training-session.list.label.instructor" path="instructor" width="20%"/>	


</acme:list>

<jstl:if test="${_command == 'list'}">
	<acme:button code="developer.training-session.list.button.create-form" action="/developer/training-session/create?masterId=${masterId}"/>
</jstl:if>