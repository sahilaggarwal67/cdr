<%@ include file="/pages/taglibs.jsp"%>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><tiles:getAsString name="title" /></title>
<link href="<c:url value='/static/css/bootstrap.css' />"
	rel="stylesheet"></link>
<link href="<c:url value='/static/css/login.css' />" rel="stylesheet"></link>
</head>

<body>
	<section id="site-content">
		<tiles:insertAttribute name="body" />
	</section>

</body>
</html>