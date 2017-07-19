<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><tiles:getAsString name="title" /></title>
<link href="static/css/login.css" rel="stylesheet"></link>
</head>

<body>
	<%--<div> <header id="header">
		<tiles:insertAttribute name="header" />
	</header> </div>--%>

	 <div style="float:left;padding:10px;width:15%;"><section id="sidemenu">
		<tiles:insertAttribute name="menu" />
	</section></div>

	<div style="float:left;padding:10px;width:80%;border-left:1px solid pink;"> 
	<section id="site-content">
		<tiles:insertAttribute name="body" />
	</section></div>

	<%-- <footer id="footer">
		<tiles:insertAttribute name="footer" />
	</footer> --%>
</body>
</html>