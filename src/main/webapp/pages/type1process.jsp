<%@ include file="/pages/taglibs.jsp"%>

<head>
<title><fmt:message key="Type1" /></title>
<meta name="menu" content="AdminMenu" />
<link href="static/css/login.css" rel="stylesheet"></link>
</head>

<div class="login-page">
	<%-- <spring:bind path="fileUpload.*">
        <c:if test="${not empty status.errorMessages}">
        <div class="alert alert-danger alert-dismissable">
            <a href="#" data-dismiss="alert" class="close">&times;</a>
            <c:forEach var="error" items="${status.errorMessages}">
                <c:out value="${error}" escapeXml="false"/><br />
            </c:forEach>
        </div>
        </c:if>
    </spring:bind> --%>

	<form:form commandName="type1process" method="post"
		action="type1process">
		<form class="login-form">
			<div class="form">
				<div class="col-sm-3">
					<h2>
						<h3>Type1 Manual Entries</h3>
					</h2>
				</div>
				<%-- <spring:bind path="type1.name">
					<div
						class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
				</spring:bind>
				<form:input cssClass="form-control" path="name" id="name" />
				<form:errors path="name" cssClass="help-block" />
			</div> --%>
				<form:hidden path="ship.id" />
				<form:hidden path="ship.companyId" />
				<form:hidden path="ship.name" />
				<form:hidden path="ship.voiceToCellular" />
				<form:hidden path="ship.voiceToTerrestial" />
				<form:hidden path="ship.iridiumCitadelMonthlyFee" />
				<form:hidden path="ship.voiceRebate" />
				<form:hidden path="ship.dataRebate" />
				<table>
					<tr>
						<td colspan="2">Fixed Charges</td>
					</tr>
					<tr>
						<td>Monthly Charges</td>
						<td><form:input path="ship.monthlyFee" readonly="true" /></td>
					</tr>
					<tr>
						<td>Static IP Charges</td>
						<td><form:input path="ship.staticIpFee" readonly="true"/></td>
					</tr>
					<%-- <tr>
						<td>Data Rebate</td>
						<td><form:input path="dataRebate"/></td>
					</tr>
					<tr>
						<td>Voice Rebate</td>
						<td><form:input path="voiceRebate"/></td>
					</tr> --%>
					<tr>
						<td colspan="2">Variable Charges</td>
					</tr>
					<tr>
						<td>Entry Name</td>
						<td>Entry Units</td>
						<td>Cost Per Unit</td>
					</tr>
					<c:forEach items="${type1process.entries}" var="entry" varStatus="status">
						<tr>							
							<td><c:out value="${entry.entryName}"/></td>
							<c:set var="hide" value="${entry.entryUnits eq 1}"/>
							<td><div style="display:${hide ? 'none' : 'block'}"><form:hidden path="entries[${status.index}].id"/><form:hidden path="entries[${status.index}].accountType"/><form:hidden path="entries[${status.index}].entryName"/><form:input path="entries[${status.index}].entryUnits"/></div></td>
							<td><div><form:input path="entries[${status.index}].entryCostPerUnit"/></div></td>
							<%-- <form:input id="${status.index}" path="type1process.entries[${status.index}].entryValue"/> --%>
						</tr>
					</c:forEach>
					<%-- <c:forEach items="${entries}" var="entry" varStatus="status">
						<tr>
							<td>${entries[${status.index}].entryName}</td>
							<td><form:input path="entries[${status.index}].entryValue" /></td>
						</tr>
					</c:forEach> --%>
				</table>
				<div>
					<form:button>Submit</form:button>
				</div>
		</form>
	</form:form>
	<%-- <c:set var="scripts" scope="request">
<v:javascript formName="fileUpload" staticJavascript="false"/>
<script type="text/javascript" src="<c:url value="/scripts/validator.jsp"/>"></script>
</c:set> --%>