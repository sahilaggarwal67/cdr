var api_key="5ab95dbcb2f58ea95543421ac68837991ad74f54202dd3e9ac04777236eeda50";
var webinar_id="83c3eec8a2";

function getWebinarTime() {	

    //var URL="https://app.webinarjam.com/api/v2/ever/webinar?api_key="+api_key+"&webinar_id="+webinar_id;
	var URL="https://app.webinarjam.com/api/v2/ever/webinar?webinar_id=83c3eec8a2&api_key=5ab95dbcb2f58ea95543421ac68837991ad74f54202dd3e9ac04777236eeda50";
	console.log(URL);
	return $.ajax({
		
		type : 'POST',
		contentType : "application/json",
		url :URL,
		dataType : 'text',
		
		
		timeout : 1000000,
		success : function(data) {
			obj=JSON.parse(data.replace('undefined',''));								
		},
		error : function(e) {
			console.log("ERROR: ", e);
		},
		done : function(e) {
			console.log("DONE");
		}	
			
	});
}



function loadWebinarTime()
{
	getWebinarTime().done(function(data) {
	    if (data) {
	          var temp=JSON.parse(data.replace('undefined',''));
			  console.log(temp);	
			  var timezone='<p class="help-block" id="selectedTimezone">Time Zone : <b>'+temp.webinar.timezone+'</b></p>';
			  jQuery('#timezoneConverter').html(timezone);	
              jQuery('#timeSelect').html('');			  
	          var content='<li role="presentation" class="disabled"><a role="menuitem" href="javascript:void(0);"> <span class="circlie"> <i class="fa fa-clock-o"></i></span> <span class="text" data-value="">Select Time</span></a></li>';
              $.each(temp.webinar.schedules, function(key,value) { 
					console.log(value.date);
					content=content.concat('<li role="presentation"><a role="menuitem" href="javascript:void(0);"> <span class="circlie"><i class="fa fa-clock-o"></i></span><span class="text" data-value="2">'+value.date+'</span></a></li>');
	        	    
	        	}); 
			 jQuery('#timeSelect').append(content);
	    } else {
	         console.log("Error loading campaignList");
	    }
	   })
	     .fail(function(x) {
	    	 console.log("Error getting campaignList deffered");
	    });
}


