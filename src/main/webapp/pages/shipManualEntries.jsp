<%@ include file="/pages/taglibs.jsp"%>

<head>
<!-- <link href="static/css/login.css" rel="stylesheet"></link> -->
<script src="static/js/jquery-3.1.1.min.js"></script>
<script type="text/javascript">
	function changeCompany() {
		var selectedCompany = document.getElementById("selectedCompany").value;
		window.location.href = "${ctx}/changeShipManualCompany?companyId="
				+ selectedCompany;
	}
	function changeShip() {
		var selectedCompany = document.getElementById("selectedCompany").value;
		var selectedShip = document.getElementById("selectedShip").value;
		console.log(selectedCompany);
		window.location.href = "${ctx}/changeShipManualShip?companyId="
				+ selectedCompany+"&&shipId="+selectedShip;
	}
	function isNumberKey(evt)
    {
       var charCode = (evt.which) ? evt.which : evt.keyCode;
       if (charCode != 46 && charCode > 31 
         && (charCode < 48 || charCode > 57))
          return false;

       return true;
    }
</script>
</head>

<div class="login-page">
	<div class="form">

		<form:form commandName="shipManualEntriesModel" method="post"
			action="shipManualEntries">
			<form class="login-form">
				<div>
					<spring:bind path="shipManualEntriesModel.selectedCompany">
						<div>
						<table>
						<tr><td width="60%" align="right">
							<label><B>Companies</B></label></td>
					</spring:bind>
					<td width="40%" align="left">
					<form:select path="selectedCompany" id="selectedCompany"
						onchange="changeCompany()">
						<form:options items="${shipManualEntriesModel.companies}"
							itemValue="id" itemLabel="name" />
					</form:select>
					</td></tr>
					</table>
				</div>
				<div>
					<spring:bind path="shipManualEntriesModel.selectedShip">
						<div>
						<table>
						<tr><td width="60%" align="right">
							<label><B>Ships</B></label></td>
					</spring:bind>
					<td width="40%" align="left">
					<form:select path="selectedShip" id="selectedShip"
						onchange="changeShip()">
						<form:options items="${shipManualEntriesModel.ships}"
							itemValue="id" itemLabel="name" />
					</form:select>
					</td></tr></table>
				</div>
				<div>
					<h4 align="left">Add a Entry</h4>
				</div>
				<form:hidden path="shipManualEntry.id" />
				<form:hidden path="shipManualEntry.shipId" />
				<spring:bind path="shipManualEntriesModel.shipManualEntry.applicableDate">
					<div>
				</spring:bind>
				<table>
					<tr>
						<td width="33%"><label align="left"><h5>Date</h5></label></td>
						<td align="center"><form:input type="date" required="required"
								path="shipManualEntry.applicableDate" id="applicableDate" /></td>
					</tr>
				</table>
	</div>
	<form:errors path="applicableDate" />
	<spring:bind path="shipManualEntriesModel.shipManualEntry.entryName">
		<div>
	</spring:bind>
	<table>
		<tr>
			<td width="33%"><label align="left"><h5>Name</h5></label></td>
			<td align="center"><form:input path="shipManualEntry.entryName" required="required"
					id="name" /></td>
		</tr>
	</table>
</div>
<form:errors path="shipManualEntry.entryName" />
<spring:bind path="shipManualEntriesModel.shipManualEntry.entryValue">
	<div>
</spring:bind>
<table>
	<tr>
		<td width="33%"><label align="left"><h5>Value</h5></label></td>
		<td><form:input path="shipManualEntry.entryValue" id="entryValue"
				onkeypress="return isNumberKey(event)" required="required" /></td>
	</tr>
</table>
<form:errors path="shipManualEntry.entryValue" />
</div>
<c:if test="${shipManualEntriesModel.shipManualEntry.id eq 0}">
	<form:button>Add</form:button>
</c:if>
<c:if test="${shipManualEntriesModel.shipManualEntry.id ne 0}">
	<form:button>Update</form:button>
</c:if>
<h4 align="left">Ship Entry List</h4>
<c:if test="${!empty shipManualEntriesModel.ships}">
	<table style="align: left">
		<tr>
			<th align="left" width="30%">Applicable Date</th>
			<th align="left" width="30%">Entry Name</th>
			<th align="left" width="30%">Entry Value</th>
			<th align="left" width="10%">Edit</th>
		</tr>
		<c:forEach items="${shipManualEntriesModel.shipManualEntriesList}"
			var="shipManualEntry1">
			<tr>
				<td><fmt:formatDate type="date" value="${shipManualEntry1.applicableDate}" /></td>
				<td>${shipManualEntry1.entryName}</td>
				<td>${shipManualEntry1.entryValue}</td>
				<td><a
					href="<c:url value="/editShipManualEntries?id=${shipManualEntry1.id}" />">Edit</a></td>
			</tr>
		</c:forEach>
	</table>
</c:if>
<c:if test="${empty shipManualEntriesModel.shipManualEntriesList}">
	<h5 align="left">No Entry found</h5>
</c:if>
</form>

</form:form>
