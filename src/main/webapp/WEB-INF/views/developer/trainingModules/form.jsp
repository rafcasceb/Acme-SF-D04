<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="developer.trainingModule.form.label.code" path="code"/>
	<acme:input-textbox code="developer.trainingModule.form.label.details" path="details"/>
	<acme:input-select code="developer.trainingModule.form.label.difficultyLevel" path="difficultyLevel" choices="${difficultyLevel}"/>
	<acme:input-moment code="developer.trainingModule.form.label.creationMoment" path="creationMoment"/>
	<acme:input-moment code="developer.trainingModule.form.label.updateMoment" path="updateMoment"/>
	<acme:input-url code="developer.trainingModule.form.label.link" path="link"/>
	<acme:input-integer code="developer.trainingModule.form.label.estimatedTotalTime" path="estimatedTotalTime"/>
	<acme:input-select code="developer.trainingModule.form.label.project" path="project" choices="${project}"/>
	
	<jstl:choose>
		<jstl:when test="${_command == 'show' && published == true }">
			<acme:button code="developer.trainingModule.form.button.trainingSessions" action="/developer/trainingSession/list?masterId=${id}"/>
		</jstl:when>
		<jstl:when test=""${acme:anyOf(_command, 'show|update|delete|publish') && published == false }">
			<acme:button code="developer.trainingModule.form.button.trainingSessions" action="/developer/trainingSession/list?masterId=${id}"/>
			<acme:submit code="developer.trainingModule.form.button.update" action="/developer/trainingSession/update"/>
			<acme:submit code="developer.trainingModule.form.button.delete" action="/developer/trainingSession/delete"/>
			<acme:submit code="developer.trainingModule.form.button.publish" action="/developer/trainingSession/publish"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
		<acme:submit code="developer.trainingModule.form.button.publish" action="/developer/trainingSession/create"/>
		</jstl:when>
	</jstl:choose>
</acme:form>