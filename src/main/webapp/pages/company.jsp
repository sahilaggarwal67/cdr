<%@ include file="/pages/taglibs.jsp"%>

<head>
<!-- <link href="static/css/login.css" rel="stylesheet"></link> -->
</head>

<div class="login-page">
	<form:form commandName="company" method="post" action="company">
		<form class="login-form">
			<div class="form">
				<div style="float: left">
					<h4>Add a Company</h4>
				</div>
				<form:hidden path="id" />
				<spring:bind path="company.name">
					<div>
				</spring:bind>
				<form:input path="name" id="name" required="required" />
				<form:errors path="name" />
			</div>
			<c:if test="${company.id eq 0}">
			<form:button>Add</form:button>
			</c:if>
			<c:if test="${company.id ne 0}">
			<form:button>Update</form:button>
			</c:if>
		</form>

	</form:form>
	<h4 align="left">Companies List</h4>
	<c:if test="${!empty listCompanies}">
		<table style="align: left">
			<tr>
				<th align="left" width="80%">Company Name</th>
				<th align="left" width="20%">Edit</th>
			</tr>
			<c:forEach items="${listCompanies}" var="company">
				<tr>
					<td>${company.name}</td>
					<td><a href="<c:url value="/editCompany?id=${company.id}" />">Edit</a></td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
	<c:if test="${empty listCompanies}">
		<h5 align="left">No Company found</h5>
	</c:if>