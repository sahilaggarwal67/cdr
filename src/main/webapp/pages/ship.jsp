<%@ include file="/pages/taglibs.jsp"%>

<head>
<!-- <link href="static/css/login.css" rel="stylesheet"></link> -->
<script src="static/js/jquery-3.1.1.min.js"></script>
<script type="text/javascript">

function changeListener() {
	changeAccountType();
}
	function changeCompany() {
		var selectedCompany = document.getElementById("selectedCompany").value;
		console.log(selectedCompany);
		window.location.href = "${ctx}/changeCompany?companyId="
				+ selectedCompany;
	}
	function changeAccountType() {
		var selectedAccountType = document.getElementById("accountType").value;
		console.log(selectedAccountType);
		if(selectedAccountType==1){
		/* $('#price1Div').hide();
		$('#price2Div').hide(); */
		$('#mappingNameDiv').hide();
		$('#feeAndStaticChargeDiv').hide();		
		$('#imsi2Div').hide();
		$('#imsi1Div').hide();
		$('#monthlyFeeDiv').show();
		$('#staticIpFeeDiv').show();
		$('#voiceToTerrestialDiv').show();
		$('#voiceToCellularDiv').show();
		$('#iridiumMonthlyFeeDiv').show();
		$('#voiceRebateDiv').show();
		$('#dataRebateDiv').show();
	}
		if(selectedAccountType==2){
			/* $('#voiceToTerrestialDiv').hide();
			$('#voiceToCellularDiv').hide(); */
			$('#iridiumMonthlyFeeDiv').hide();
			$('#mappingNameDiv').hide();
			$('#feeAndStaticChargeDiv').hide();	
			$('#monthlyFeeDiv').show();
			$('#staticIpFeeDiv').show();
			/* $('#price1Div').show();
			$('#price2Div').show(); */
			$('#imsi1Div').show();
			$('#imsi2Div').show();
			$('#voiceRebateDiv').show();
			$('#dataRebateDiv').show();				
		}
		if(selectedAccountType==3 || selectedAccountType==4){
			$('#voiceToTerrestialDiv').hide();
			$('#voiceToCellularDiv').hide();
			$('#iridiumMonthlyFeeDiv').hide();				
			$('#monthlyFeeDiv').hide();
			$('#staticIpFeeDiv').hide();
			$('#voiceRebateDiv').hide();
			$('#dataRebateDiv').hide();
			/* $('#price1Div').hide();
			$('#price2Div').hide(); */
			$('#imsi1Div').hide();
			$('#imsi2Div').hide();
			$('#mappingNameDiv').show();
			$('#feeAndStaticChargeDiv').show();
				
		}		
		/* window.location.href = "${ctx}/changeCompany?companyId="
				+ selectedCompany; */
	}
	function isNumberKey(evt)
    {
       var charCode = (evt.which) ? evt.which : evt.keyCode;
       if (charCode != 46 && charCode > 31 
         && (charCode < 48 || charCode > 57))
          return false;

       return true;
    }
	window.onload = changeListener;
</script>
</head>

<div class="login-page">
	<div class="form">

		<form:form commandName="shipModel" method="post" action="ship">
			<form class="login-form">
				<div>
					<spring:bind path="shipModel.selectedCompany">
						<div>
							<label><B>Companies</B></label>
					</spring:bind>
					<form:select path="selectedCompany" id="selectedCompany"
						onchange="changeCompany()">
						<form:options items="${shipModel.companies}" itemValue="id"
							itemLabel="name" />
					</form:select>
				</div>
				<div>
					<h4 align="left">Add a Ship</h4>
				</div>
				<form:hidden path="ship.id" />
				<form:hidden path="ship.companyId" />
				<spring:bind path="shipModel.ship.accountType">
					<div>
				</spring:bind>
				<table>
					<tr>
						<td width="50%" valign="top"><label align="left"><h5 >Account
									Type</h5></label></td>
						<td valign="top"><div><form:select path="ship.accountType" id="accountType"
								onchange="changeAccountType()">
								<form:options items="${shipModel.accountTypes}"
									itemValue="accountCode" itemLabel="accountType" />
							</form:select></div></td>
					</tr>
				</table>
				<form:errors path="ship.accountType" />
	</div>
	<spring:bind path="shipModel.ship.name">
		<div>
	</spring:bind>
	<table>
		<tr>
			<td width="50%"><label align="left"><h5>Name</h5></label></td>
			<td align="center"><form:input path="ship.name" id="name"
					required="required" /></td>
		</tr>
	</table>
</div>
<form:errors path="ship.name" />
</div>
<spring:bind path="shipModel.ship.monthlyFee">
	<div id="monthlyFeeDiv">
</spring:bind>
<table id="monthlyFeeTable">
	<tr>
		<td width="50%"><label align="left"><h5>Monthly Fee</h5></label></td>
		<td><form:input path="ship.monthlyFee" id="monthlyFee"
				required="required" onkeypress="return isNumberKey(event)" /></td>
	</tr>
</table>
<form:errors path="ship.monthlyFee" />
</div>
<spring:bind path="shipModel.ship.staticIpFee">
	<div id="staticIpFeeDiv">
</spring:bind>
<table id="staticIpFeeTable">
	<tr>
		<td width="50%"><label align="left"><h5>Static IP
					Fee</h5></label></td>
		<td><form:input path="ship.staticIpFee" id="staticIpFee"
				required="required" onkeypress="return isNumberKey(event)" /></td>
	</tr>
</table>
<form:errors path="ship.staticIpFee" />
</div>
<spring:bind path="shipModel.ship.voiceToTerrestial">
	<div id="voiceToTerrestialDiv">
</spring:bind>
<table id="voiceToTerrestialTable">
	<tr>
		<td width="50%"><label align="left"><h5>Terrestial
					Call Rate</h5></label></td>
		<td><form:input path="ship.voiceToTerrestial"
				id="voiceToTerrestialFee" onkeypress="return isNumberKey(event)" /></td>
	</tr>
</table>
<form:errors path="ship.voiceToTerrestial" />
</div>
<spring:bind path="shipModel.ship.voiceToCellular">
	<div id="voiceToCellularDiv">
</spring:bind>
<table id="voiceToCellularTable">
	<tr>
		<td width="50%"><label align="left"><h5>Cellular
					Call Rate</h5></label></td>
		<td><form:input path="ship.voiceToCellular"
				id="voiceToCellularFee" onkeypress="return isNumberKey(event)" /></td>
	</tr>
</table>
<form:errors path="ship.voiceToCellular" />
</div>
<%--<spring:bind path="shipModel.ship.price1">
	 <div id="price1Div">
</spring:bind>
<table id="price1Table">
	<tr>
		<td width="50%"><label align="left"><h5>Price 1</h5></label></td>
		<td><form:input path="ship.price1" id="price1"
				onkeypress="return isNumberKey(event)" /></td>
	</tr>
</table>
<form:errors path="ship.price2" />
</div>
<spring:bind path="shipModel.ship.price2">
	<div id="price2Div">
</spring:bind>
<table id="price1Table">
	<tr>
		<td width="50%"><label align="left"><h5>Price 2</h5></label></td>
		<td><form:input path="ship.price2" id="price2"
				onkeypress="return isNumberKey(event)" /></td>
	</tr>
</table>
<form:errors path="ship.price2" />
</div> --%>
<spring:bind path="shipModel.ship.imsi1">
	<div id="imsi1Div">
</spring:bind>
<table id="imsi1Table">
	<tr>
		<td width="50%"><label align="left"><h5>IMSI1</h5></label></td>
		<td><form:input path="ship.imsi1"
				id="imsi1" /></td>
	</tr>
</table>
<form:errors path="ship.imsi1" />
</div>
<spring:bind path="shipModel.ship.imsi2">
	<div id="imsi2Div">
</spring:bind>
<table id="imsi2Table">
	<tr>
		<td width="50%"><label align="left"><h5>IMSI2</h5></label></td>
		<td><form:input path="ship.imsi2"
				id="imsi2" /></td>
	</tr>
</table>
<form:errors path="ship.imsi2" />
</div>
<spring:bind path="shipModel.ship.iridiumCitadelMonthlyFee">
	<div id="iridiumMonthlyFeeDiv">
</spring:bind>
<table id="iridiumMonthlyFeeTable">
	<tr>
		<td width="50%"><label align="left"><h5>Iridium Citadel Monthly Fee</h5></label></td>
		<td><form:input path="ship.iridiumCitadelMonthlyFee"
				id="iridiumMonthlyFee" onkeypress="return isNumberKey(event)"/></td>
	</tr>
</table>
<form:errors path="ship.iridiumCitadelMonthlyFee" />
</div>
<spring:bind path="shipModel.ship.feeAndStaticCharge">
	<div id="feeAndStaticChargeDiv">
</spring:bind>
<table id="feeAndStaticChargeTable">
	<tr>
		<td width="50%"><label align="left"><h5>Fee And Static charge</h5></label></td>
		<td><form:input path="ship.feeAndStaticCharge"
				id="feeAndStaticCharge" onkeypress="return isNumberKey(event)"/></td>
	</tr>
</table>
<form:errors path="ship.feeAndStaticCharge" />
</div>
<spring:bind path="shipModel.ship.mappingName">
	<div id="mappingNameDiv">
</spring:bind>
<table id="mappingNameTable">
	<tr>
		<td width="50%"><label align="left"><h5>Mapping Name</h5></label></td>
		<td><form:input path="ship.mappingName"
				id="mappingName"/></td>
	</tr>
</table>
<form:errors path="ship.mappingName" />
</div>
<spring:bind path="shipModel.ship.dataRebate">
	<div id="dataRebateDiv">
</spring:bind>
<table id="dataRebateTable">
	<tr>
		<td width="50%"><label align="left"><h5>Data Rebate</h5></label></td>
		<td><form:input path="ship.dataRebate"
				id="dataRebate" onkeypress="return isNumberKey(event)"/></td>
	</tr>
</table>
<form:errors path="ship.dataRebate" />
</div>
<spring:bind path="shipModel.ship.voiceRebate">
	<div id="voiceRebateDiv">
</spring:bind>
<table id="voiceRebateTable">
	<tr>
		<td width="50%"><label align="left"><h5>Voice Rebate</h5></label></td>
		<td><form:input path="ship.voiceRebate"
				id="voiceRebate" onkeypress="return isNumberKey(event)"/></td>
	</tr>
</table>
<form:errors path="ship.voiceRebate" />
</div>
<c:if test="${shipModel.ship.id eq 0}">
	<form:button>Add</form:button>
</c:if>
<c:if test="${shipModel.ship.id ne 0}">
	<form:button>Update</form:button>
</c:if>
</form>

</form:form>
<h4 align="left">Ship List</h4>
<c:if test="${!empty shipModel.shipList}">
	<table style="align: left">
		<tr>
			<th align="left" width="20%">Account Type</th>		
			<th align="left" width="30%">Name</th>
			<th align="left" width="20%">Monthly Fee</th>
			<th align="left" width="20%">IP Fee</th>			
			<th align="left" width="10%">Edit</th>
		</tr>
		<c:forEach items="${shipModel.shipList}" var="ship">
			<tr>
				<td>${ship.shipType}</td>
				<td>${ship.name}</td>
				<td>${ship.monthlyFee}</td>
				<td>${ship.staticIpFee}</td>				
				<td><a href="<c:url value="/editShip?id=${ship.id}" />">Edit</a></td>
			</tr>
		</c:forEach>
	</table>
</c:if>
<c:if test="${empty shipModel.shipList}">
	<h5 align="left">No Ship found</h5>
</c:if>