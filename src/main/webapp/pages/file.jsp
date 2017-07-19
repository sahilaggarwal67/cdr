<%@ include file="/pages/taglibs.jsp"%>

<head>
<!-- <link href="static/css/login.css" rel="stylesheet"></link> -->
<script src="static/js/jquery-3.1.1.min.js"></script>
<script type="text/javascript">
	function changeYear() {
		var selectedYear = document.getElementById("selectedYear").value;
		var selectedMonth = document.getElementById("selectedMonth").value;
		console.log(selectedYear + ":" + selectedMonth);
		window.location.href = "${ctx}/listFiles?year=" + selectedYear
				+ "&month=" + selectedMonth;
	}
</script>
</head>

<div class="login-page">
	<div class="form">

		<form:form commandName="file" method="post" action="files">
			<form class="login-form">
				<div>
					<h4 align="left">Uploaded Files</h4>
				</div>
				<div>
					<spring:bind path="file.year">
						<label><B>Year</B></label>
					</spring:bind>
					<form:select path="year" id="selectedYear" onchange="changeYear()">
						<form:options items="${file.years}" />
					</form:select>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<spring:bind path="file.month">
						<label><B>Month</B></label>
					</spring:bind>
					<form:select path="month" id="selectedMonth"
						onchange="changeYear()">
						<form:options items="${file.months}" />
					</form:select>
				</div>
			</form>
		</form:form>
		<c:if test="${!empty file.filesUploaded}">
			<table style="align: left">
				<tr>
					<th align="left" width="30%">File Name</th>
					<th align="left" width="20%">Type</th>
					<!-- <th align="left" width="20%">Uploaded By</th> -->
					<th align="left" width="20%">Uploaded Time</th>
					<th align="left" width="10%">Actual File</th>
					<th align="left" width="10%">PDF File</th>
				</tr>
				<c:forEach items="${file.filesUploaded}" var="fileUploaded">
					<tr>
						<td>${fileUploaded.fileName}</td>
						<td>${fileUploaded.type}</td>
						<%-- <td>${fileUploaded.uploadedBy}</td> --%>
						<td>${fileUploaded.uploadedTime}</td>
						<td><a
							href="<c:url value="/actualFile?reportId=${fileUploaded.reportId}&type=${fileUploaded.type}" />">Actual
								File</a></td>
						<td><a
							href="<c:url value="/pdfFile?reportId=${fileUploaded.reportId}&type=${fileUploaded.type}" />">PDF</a></td>
					</tr>
				</c:forEach>
			</table>
		</c:if>
		<c:if test="${empty file.filesUploaded}">
			<h5 align="left">No File found</h5>
		</c:if>