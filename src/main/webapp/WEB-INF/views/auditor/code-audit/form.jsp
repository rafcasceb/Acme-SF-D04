<%--
- form.jsp
-
- Copyright (C) 2012-2024 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="auditor.codeaudit.label.code"
		path="code" />
	<acme:input-textbox code="auditor.codeaudit.label.execution"
		path="execution" />
	<acme:input-textbox code="auditor.codeaudit.label.type"
		path="type" />
	<acme:input-double code="auditor.codeaudit.label.correctiveActions"
		path="correctiveActions" />
	<acme:input-textbox code="auditor.codeaudit.label.link"
		path="link" />

	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete')}">
			<acme:submit code="auditor.codeaudit.button.update"
				action="/employer/works-for/update" />
			<acme:submit code="auditor.codeaudit.button.delete"
				action="/employer/works-for/delete" />
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="auditor.codeaudit.button.create"
				action="/employer/works-for/create" />
		</jstl:when>
	</jstl:choose>
</acme:form>
