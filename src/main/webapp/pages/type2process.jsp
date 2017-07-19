<%@ include file="/pages/taglibs.jsp"%>

<head>
<title><fmt:message key="Type2" /></title>
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

	<form:form commandName="type2process" method="post"
		action="type2process">
		<form class="login-form">
			<div class="form">
				<div class="col-sm-3">
					<h2>
						<h3>Type2 Manual Entries</h3>
					</h2>
				</div>
				<%-- <spring:bind path="type1.name">
					<div
						class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
				</spring:bind>
				<form:input cssClass="form-control" path="name" id="name" />
				<form:errors path="name" cssClass="help-block" />
			</div> --%>
				<%-- <form:hidden path="ship.id" />
				<form:hidden path="ship.companyId" />
				<form:hidden path="ship.name" />
				<form:hidden path="ship.voiceToCellular" />
				<form:hidden path="ship.voiceToTerrestial" />
				<form:hidden path="ship.imsi1" />
				<form:hidden path="ship.imsi2" /> --%>
				<c:forEach items="${type2process.shipModels}" var="bigentry"
					varStatus="status1">
					<table>
						<tr>
							<form:hidden path="shipModels[${status1.index}].ship.id" />
							<form:hidden path="shipModels[${status1.index}].ship.companyId" />
							<form:hidden path="shipModels[${status1.index}].ship.name" />
							<form:hidden path="shipModels[${status1.index}].ship.voiceToCellular" />
							<form:hidden path="shipModels[${status1.index}].ship.voiceToTerrestial" />
							<form:hidden path="shipModels[${status1.index}].ship.imsi1" />
							<form:hidden path="shipModels[${status1.index}].ship.imsi2" />
							<form:hidden path="shipModels[${status1.index}].ship.voiceRebate" />
							<form:hidden path="shipModels[${status1.index}].ship.dataRebate" />
							<td align="left"><h4><c:out value="${bigentry.ship.name}" /></h4></td>
							<td></td>
							<%-- <form:input id="${status.index}" path="type1process.entries[${status.index}].entryValue"/> --%>
						</tr>
						<tr>
							<td colspan="2">Fixed Charges</td>
						</tr>
						<tr>
							<td>Monthly Charges</td>
							<td><form:input path="shipModels[${status1.index}].ship.monthlyFee" readonly="true" /></td>
						</tr>
						<tr>
							<td>Static IP Charges</td>
							<td><form:input path="shipModels[${status1.index}].ship.staticIpFee" readonly="true" /></td>
						</tr>
						<%-- <tr>
							<td>Data Rebate</td>
							<td><form:input path="shipModels[${status1.index}].dataRebate" /></td>
						</tr>
						<tr>
							<td>Voice Rebate</td>
							<td><form:input path="shipModels[${status1.index}].voiceRebate" /></td>
						</tr> --%>
						<tr>
							<td colspan="2">Variable Charges</td>
						</tr>
						<c:forEach items="${bigentry.manualFields}" var="entry"
							varStatus="status">
							<tr>
								<td><c:out value="${entry.entryName}" /></td>
								<td><div>
										<form:hidden path="shipModels[${status1.index}].manualFields[${status.index}].id" />
										<form:hidden path="shipModels[${status1.index}].manualFields[${status.index}].accountType" />
										<form:hidden path="shipModels[${status1.index}].manualFields[${status.index}].entryName" />
										<form:input path="shipModels[${status1.index}].manualFields[${status.index}].entryCostPerUnit" />
									</div></td>
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
				</c:forEach>
				<div>
					<form:button>Submit</form:button>
				</div>
		</form>
	</form:form>
	<%-- <c:set var="scripts" scope="request">
<v:javascript formName="fileUpload" staticJavascript="false"/>
<script type="text/javascript" src="<c:url value="/scripts/validator.jsp"/>"></script>
</c:set> --%>