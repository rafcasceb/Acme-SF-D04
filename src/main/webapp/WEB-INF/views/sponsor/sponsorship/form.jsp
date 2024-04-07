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
	<acme:input-textbox code="sponsor.sponsorship.form.label.code" path="code"/>
	<acme:input-textbox code="sponsor.sponsorship.form.label.project" path="project.title"/>
	<acme:input-textbox code="sponsor.sponsorship.form.label.moment" path="moment"/>
	<acme:input-textbox code="sponsor.sponsorship.form.label.startDate" path="startDate"/>
	<acme:input-textbox code="sponsor.sponsorship.form.label.endDate" path="endDate"/>
	<acme:input-textbox code="sponsor.sponsorship.form.label.type" path="type"/>
	<acme:input-textbox code="sponsor.sponsorship.form.label.amount" path="amount"/>
	<acme:input-textbox code="sponsor.sponsorship.form.label.link" path="link"/>
	<acme:input-textbox code="sponsor.sponsorship.form.label.email" path="email"/>
	<acme:input-textbox code="sponsor.sponsorship.form.label.published" path="published"/>
	
	<acme:submit test="${_command == 'create'}" code="authenticated.consumer.form.button.create" action="/authenticated/consumer/create"/>
	<acme:submit test="${_command == 'update'}" code="authenticated.consumer.form.button.update" action="/authenticated/consumer/update"/>
</acme:form>
