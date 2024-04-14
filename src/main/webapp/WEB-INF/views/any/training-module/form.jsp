<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="developer.training-module.form.label.code" path="code"/>
	<acme:input-textbox code="developer.training-module.form.label.details" path="details"/>
	<acme:input-select path="difficultyLevel" code="developer.training-module.form.label.difficultyLevel" choices="${difficultyLevels}"/>
	<acme:input-url code="developer.training-module.form.label.link" path="link"/>
	<acme:input-integer code="developer.training-module.form.label.estimatedTotalTime" path="estimatedTotalTime"/>
	<acme:input-select code="developer.training-module.form.label.project" path="project" choices="${projects}"/>

</acme:form>