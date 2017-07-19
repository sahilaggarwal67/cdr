<%@ include file="/pages/taglibs.jsp"%>
<html>
<head>
<link href="static/css/login.css" rel="stylesheet"></link>
</head>
<body>
	<form:form commandName="login" action="login" method="post">
		<div class="login-page">

			<div class="form">
				<form class="login-form">
					<spring:bind path="username">
						<div
							class="${(not empty status.errorMessage) ? ' has-error ' : ''}">
					</spring:bind>
					<form:input type="text" placeholder="username" path="username" />
					<form:errors path="username" />
					</div>
			<form:button>Login</form:button>
		</div>
		</div>
	</form:form>
</body>
</html>