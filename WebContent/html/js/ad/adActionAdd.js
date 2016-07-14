$(document).ready(function(){
	var firstAdType = $("#adType").val();
	var firstAdDevice = $("#adDeviceSelect").val();
	var firstObj;
	if(firstAdType == "0"){
		firstObj = JSON.parse($("#adAllDevice").text());
    } else if(firstAdType == "1"){
    	firstObj = JSON.parse($("#adSearchDevice").text());
    } else if(firstAdType == "2"){
    	firstObj = JSON.parse($("#adChannelDevice").text());
    }
	
	$.each(firstObj, function(key, value) { 
		if(firstAdDevice == key){
			$("#adDevice").append('<option value='+ key+' selected >' + value + '</option>');
		} else {
			$("#adDevice").append('<option value='+ key+'>' + value + '</option>');
		}
	});
	
	$("#adType").change(function(){
		var adType = $("#adType").val();
		var Obj;
		if(adType == "0"){
			Obj = JSON.parse($("#adAllDevice").text());
			$("#detailTitle").show();
	    } else if(adType == "1"){
	    	Obj = JSON.parse($("#adSearchDevice").text());
	    	
	    	$("#detailTitle").hide();
	    	$("#detailId").html("進階設定+");
	    	$("#detailId").attr("onclick","openDetail()");
	    	$("#selectDetail").hide();
	    	$("#openTimeDetail").html('自訂播放時段');
	    	$("[id*=checkbox]").attr("checked","checked");
	    	$("#selTime1").attr("checked","checked");
	    	$("#selTime2").removeAttr("checked");
	    	$("#sex1").attr("checked","checked");
	    	$("#sex2").removeAttr("checked");
	    	$("#sex3").removeAttr("checked");
	    	$("#age1").attr("checked","checked");
	    	$("#age2").removeAttr("checked");
	    	$("#adActionStartAge").attr("disabled","disabled");
	    	$("#adActionEndAge").attr("disabled","disabled");
	    	$("#adActionStartAge").val("0");
	    	$("#adActionEndAge").val("99");
	    	
	    } else if(adType == "2"){
	    	Obj = JSON.parse($("#adChannelDevice").text());
	    	$("#detailTitle").show();
	    }
		
		$("#adDevice option").remove();

		$.each(Obj, function(key, value) { 
			$("#adDevice").append('<option value='+ key+'>' + value + '</option>');
		});
	});
	
	initDate();

	
	function initDate() {

		/*if ($('#adActionStartDate').length > 0) {
//			$('#adActionStartDate').datepicker( "option", "minDate", new Date(Date.parse($('#adActionStartDate').val().replace(/-/g,"/"))) );
            if($('#adActionStartDate').val() != "") {
                $('#adActionStartDate').datepicker( "option", "minDate", new Date(Date.parse($('#adActionStartDate').val().replace(/-/g,"/"))) );
            } else {
                $("#adActionStartDate").datepicker({
                    dateFormat: "yy-mm-dd",
                    yearRange:"-10:+10",
                    minDate: "-6M",
                    maxDate: 0
                });
            }

		}*/

		$("#adActionStartDate").datepicker({
            dateFormat: "yy-mm-dd",
            yearRange:"-10:+10",
            minDate: "-6M",
            maxDate: 0
        });
		
		$("#adActionStartDate").datepicker({
            dateFormat: "yy-mm-dd",
            yearRange:"-10:+10",
            minDate: "-6M",
            maxDate: 0
		});
		
		/*if ($('#adActionEndDate').length > 0) {
            if($('#adActionEndDate').val() != "") {
            	if($('#adActionStartDate').val() != ""){
            		if(new Date(Date.parse($('#adActionStartDate').val().replace(/-/g,"/"))) < new Date()){
            			$('#adActionEndDate').datepicker( "option", "minDate", new Date() );	
            		} else {
            			$('#adActionStartDate').datepicker( "option", "minDate", new Date(Date.parse($('#adActionEndDate').val().replace(/-/g,"/"))) );
            		}
            	}
                $('#adActionEndDate').datepicker( "option", "minDate", new Date(Date.parse($('#adActionEndDate').val().replace(/-/g,"/"))) );
            } else {
                $("#adActionEndDate").datepicker({
                    dateFormat: "yy-mm-dd",
                    yearRange:"-10:+10",
                    minDate: "-6M",
                    maxDate: 0
                });
            }
		}*/
		
		
		/*if ($('#adActionEndDate').length > 0) {
            	if($('#adActionStartDate').val() != ""){
            		if(new Date(Date.parse($('#adActionStartDate').val().replace(/-/g,"/"))) < new Date()){
            			$("#adActionStartDate").datepicker({
            	            dateFormat: "yy-mm-dd",
            	            yearRange:"-10:+10",
            	            minDate: "-6M",
            	            maxDate: 0
            	        });
            		} else {
            			$('#adActionEndDate').datepicker( "option", "minDate", new Date(Date.parse($('#adActionStartDate').val().replace(/-/g,"/"))) );
            		}
            	}else {
            		$('#adActionEndDate').datepicker( "option", "minDate", new Date() );
            	}
                //$('#adActionEndDate').datepicker( "option", "minDate", new Date(Date.parse($('#adActionEndDate').val().replace(/-/g,"/"))) );
		}*/
		
	}

	// validate field
	$("#modifyForm").validate({
		rules: {
			adActionName: {
				required : true,
				maxlength : 20,
				remote: {
					url: "chkAdActionName.html",     //後台處理程序
					type: "post",               //數據發送方式
					data: {                     //要傳遞的數據，默認已傳遞應用此規則的表單項
						adActionSeq: function() {
							return $("#adActionSeq").val();
						},
						adActionName: function() {
							return $("#adActionName").val();
						}
					}
				}
			},
			/*adActionDesc: {
				required : true,
				maxlength : 30
			},*/
			adActionStartDate: "required",
			adActionEndDate: {
				required: {
					depends: function() {
						return $('input[name=selAdActionEndDate]:checked').val() == 'Y';
	                }
				}
			},
			adActionMax: {
				required: true,
				digits: true,
				min: 100,
				max: 999999
			}
		},
		messages: {
			adActionName: {
				required: "請填寫廣告名稱.",
				maxlength : "廣告名稱輸入字數已達上限 20 字",
				remote: "廣告名稱重複，請重新輸入"
			},
			/*adActionDesc: {
				required : "請填寫廣告內容簡述.",
				maxlength : "廣告內容簡述輸入字數已達上限 30 字"
			},*/
			adActionStartDate: {
				required:  "請選擇廣告開始日期."
			},
			adActionEndDate: {
				required:  "請選擇廣告結束日期.",
				greaterThan: "廣告結束日期不得小於廣告開始日期"
			},
			adActionMax: {
				required:  "請填寫每日預算金額.",
				digits: "每日預算只能填寫數字.",
				min: "為讓您的廣告有足夠曝光量，每日預算至少為NT$100",
				max: "每日預算最多為NT$999,999"
			}
		}
	});
	
	$("#selAdActionEndDate").click(function(){
		if($('input[name=selAdActionEndDate]:checked').val() == "N") {
			$("#adActionEndDate").val("");
			$("#chkEndDate").text("");
		}
	});
	
	$('#save').click(function(){
		
		//取得驗證回傳值
		if($("#modifyForm").valid() == 1){
			var startAge = $("#adActionStartAge").val();
			var endAge = $("#adActionEndAge").val();
			
			//檢查年齡區間是否有間隔5歲以上
			if((parseInt(endAge) - parseInt(startAge)) < 5){
				alert("提醒您，族群年齡區間需間隔5歲以上");
				return false;
			}
			
			var timeString = "";
			$("[id*=checkbox]").each(function(){
				if($(this).prop("checked")){
					timeString = timeString + "1";
				} else {
					timeString = timeString + "0";
				}
			});
			
			//檢查自訂播放時段是否至少勾選一個
			if(timeString.indexOf("1") < 0){
				alert("提醒您，自訂播放時段至少要選擇一個時間");
				return false;
			}
			// form submit
			$("#adActionStartAge").removeAttr("disabled");
			$("#adActionEndAge").removeAttr("disabled");
			$("#timeCode").val(timeString);
			$("#modifyForm").submit();
		}
		
	});

	$('#cancel').click(function(){
		$("#modifyForm")[0].reset();
		window.location.replace($("#backPage").val());
		//$(location).attr('href',$("#backPage").val());
	});
});

function opennots(id) {
	$("#shownotes"+id).css("visibility", "visible");
}

function closenots(id) {
	$("#shownotes"+id).css("visibility", "hidden");
}

function cleanEndDate() {
	$("#adActionEndDate").val("");
}

//開啟進階設定
function openDetail(){
	$("#detailId").html("進階設定-");
	$("#detailId").attr("onclick","closeDetail()");
	$("#selectDetail").show();
}

//關閉進階設定
function closeDetail(){
	$("#detailId").html("進階設定+");
	$("#detailId").attr("onclick","openDetail()");
	$("#selectDetail").hide();
}

//選擇全時段播放
function selAllTime(){
	$("#openTimeDetail").html('自訂播放時段');
	$("[id*=checkbox]").attr("checked","checked");
}

//選擇自訂時段播放
function selAnyTime(){
	$("#openTimeDetail").html('<a id="detailId" style="cursor: pointer;" onclick="selectTime()" >自訂播放時段</a>');
	$("[id*=checkbox]").removeAttr("checked");
}

//選擇全部年齡
function selAllAge(){
	$("#adActionStartAge").attr("disabled","disabled");
	$("#adActionEndAge").attr("disabled","disabled");
	$("#adActionStartAge").val("0");
	$("#adActionEndAge").val("99");
}

//選擇自訂年齡
function selAnyAge(){
	$("#adActionStartAge").removeAttr("disabled");
	$("#adActionEndAge").removeAttr("disabled");
}

function selectTime(){
	 $.fancybox(
	    		$('#selectTimeDiv').html(),
	    		{
	    			'modal'             : true,
	    			'autoDimensions'	: false,
	    			'width'         	: 'auto',
	    			'height'        	: 'auto',
	    			'autoSize'			: true,
	    			'autoHeight'		: true,
	    			'autoScale'			: true,
	    			'transitionIn'		: 'none',
	    			'transitionOut'		: 'none',
	    			'padding'			: 0,
	    			'overlayOpacity'    : .75,
	    			'overlayColor'      : '#fff',
	    			'scrolling'			: 'no'
	    		}
	    );
}

function closeBtn(){
	//$('#selectTimeDiv').html($("#fancybox-content").html());
	parent.$.fancybox.close();
}

function selCheckbox(number){
	$("#checkbox" + number).click();
	if($("#checkbox" + number).prop("checked")){
		$("#checkbox" + number).attr("checked","checked");
	} else {
		$("#checkbox" + number).removeAttr("checked");
	}
}