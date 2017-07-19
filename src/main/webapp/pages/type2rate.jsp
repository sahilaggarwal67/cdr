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

	<form:form commandName="type2rateModels" method="post"
		action="type2rate">
		<form class="login-form">
			<div class="form">
				<div class="col-sm-3">
					<h2>
						<h3>Type2 Missing Rates</h3>
					</h2>
				</div>
				<%-- <spring:bind path="type1.name">
					<div
						class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
				</spring:bind>
				<form:input cssClass="form-control" path="name" id="name" />
				<form:errors path="name" cssClass="help-block" />
			</div> --%>
				<table>
					<c:forEach items="${type2rateModels.rateList}" var="rate"
						varStatus="status">
						<!-- <th>
						<td>Description</td>
						<td>Rate</td>
						<td>Multiplied By</td>
						</th> -->
						<tr>
							<td><c:out value="${rate.rateEntryName}" /></td>
							<td><div><form:hidden path="rateList[${status.index}].rateEntryName"/>
									<form:select path="rateList[${status.index}].rateType"
										id="rateType">
										<form:options items="${rate.priceTypeList}" itemValue="type"
											itemLabel="priceType" />
									</form:select>
								</div></td>
							 <td><div>
									<form:input path="rateList[${status.index}].multiplier" />
								</div></td>					
						</tr>
					</c:forEach>					
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