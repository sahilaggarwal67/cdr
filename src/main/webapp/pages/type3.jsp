<%@ include file="/pages/taglibs.jsp"%>

<head>
<title><fmt:message key="Type3" /></title>
<meta name="menu" content="AdminMenu" />
<link href="static/css/login.css" rel="stylesheet"></link>
<script type="text/javascript">
	window.onload = zipDownload;
	function zipDownload() {
		var filePath = document.getElementById("filePath").value;
		if (filePath) {
			window.location.href = "${ctx}/type3Report?filePath=" + filePath;
		}
	}
</script>
 <% 
 	Integer format = (Integer) request.getSession().getAttribute("FORMAT"); 
 	String heading = (format==1?"Type3a Upload":"Type3 Upload");
 %>
</head>

<div class="login-page">
	<form:form commandName="type3" method="post" action="type3"
		enctype="multipart/form-data">
		<form class="login-form" enctype="multipart/form-data">
			<form:hidden id="filePath" path="filePath" />
			<div class="form">
				<div class="col-sm-3">
					<h2>
						<h3>
							<%= heading %>
						</h3>
					</h2>
				</div>
				<spring:bind path="type3.files">
					<div
						class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
				</spring:bind>
				<input type="file" name="files" id="file" multiple="multiple" />
				<form:errors path="files" cssClass="help-block" />
				<div>
					<form:button>Upload</form:button>
				</div>
		</form>
	</form:form>