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
	<acme:input-textbox code="manager.project.form.label.code" path="code"/>
	<acme:input-textbox code="manager.project.form.label.title" path="title"/>
	<acme:input-textbox code="manager.project.form.label.published" path="published"/>
	<acme:input-textbox code="manager.project.form.label.abstractDescription" path="abstractDescription"/>
	<acme:input-textbox code="manager.project.form.label.fatalErrorPresent" path="fatalErrorPresent"/>
	<acme:input-textbox code="manager.project.form.label.estimatedCostInHours" path="estimatedCostInHours"/>
	<acme:input-textbox code="manager.project.form.label.link" path="link"/>
</acme:form>
