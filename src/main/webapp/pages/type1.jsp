<%@ include file="/pages/taglibs.jsp"%>

<head>
<title><fmt:message key="Type1" /></title>
<meta name="menu" content="AdminMenu" />
<link href="static/css/login.css" rel="stylesheet"></link>
<script type="text/javascript">
	window.onload = pdfDownload;
	function pdfDownload() {
		var filePath = document.getElementById("filePath").value;
		if (filePath) {
			window.location.href = "${ctx}/type1Report?filePath=" + filePath;
		}
	}
</script>
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

	<form:form commandName="type1" method="post" action="type1"
		enctype="multipart/form-data">
		<form class="login-form" enctype="multipart/form-data">
			<form:hidden id="filePath" path="filePath" />
			<div class="form">
				<div class="col-sm-3">
					<h2>
						<h3>Type1 Upload</h3>
					</h2>
				</div>
				<%-- <spring:bind path="type1.name">
					<div
						class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
				</spring:bind>
				<form:input cssClass="form-control" path="name" id="name" />
				<form:errors path="name" cssClass="help-block" />
			</div> --%>
				<spring:bind path="type1.file">
					<div
						class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
				</spring:bind>
				<input type="file" name="file" id="file" />
				<form:errors path="file" cssClass="help-block" />
				<div>
					<form:button>Upload</form:button>
				</div>
		</form>
	</form:form>
	<%-- <c:set var="scripts" scope="request">
<v:javascript formName="fileUpload" staticJavascript="false"/>
<script type="text/javascript" src="<c:url value="/scripts/validator.jsp"/>"></script>
</c:set> --%>