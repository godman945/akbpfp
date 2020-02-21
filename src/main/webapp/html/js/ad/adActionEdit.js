var websiteCategoryObj = null;



$(document).ready(function(){
    
	initDate();

	function initDate() {
		$("#adActionStartDate").datepicker({
            dateFormat: "yy-mm-dd",
            yearRange:"-10:+10",
            minDate: "-6M",
            maxDate: 0
        });
		
		if ($('#adActionEndDate').length > 0) {
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
		}
		
		var obj = JSON.parse($("#txtArea").text());
		var str = null;
		Object.keys(obj).forEach(function(key){
			str = '<li class="listem_culm01" trakingCode="'+key+'">';
			str = str + '<div style="margin-right:5%;">';
			if(obj[key].trackingStatus == '1'){
				str = str + '<div style="float:right;">代碼:'+key+'<div style="margin-left:10px;display:inline-block;" onclick="checkTrackingCode(this,\''+key+'\')"><span class="rmcx_icon"></span></div></div>';
			}else{
				str = str + '<div style="float:right;">代碼:'+key+'<div style="margin-left:10px;display:inline-block;"><span class="rmcx_icon"></span></div></div>';
			}
			str = str + '<div>名稱:'+obj[key].trackingName+'</div>';
			$("#trackingCodeUl").append(str);
		});
		
		var obj = JSON.parse($("#txtArea2").text());
		var str = null;
		Object.keys(obj).forEach(function(key){
			str = '<option value="'+key+'">'+obj[key].convertName+'</option>';
			$("#convertCodeSelect").append(str);
		});
		
		var obj = JSON.parse($("#txtArea3").text());
		$("input[name='countryRadio']").each(function(){  
			if(this.value == $("#adCountry").val()){
				$(this).attr('checked','checked');
			}
			
			if("Taiwan" == $("#adCountry").val()){
				$("input[name='adEditCity']").each(function(){
					if($("#adCity").val().includes(this.value)){
						$(this).attr('checked','checked');
					}
				});
			}
		});
		
		if($("#adPvLimitAmount").val() !='' && $("#adPvLimitAmount").val() > 0){
			$("#adPvLimitAmountSelect").val($("#adPvLimitAmount").val());	
			$("#adPvLimitAmountSelect").removeAttr("disabled");
		}else{
			$("#adPvLimitAmountSelect").attr("disabled","disabled");
		}

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
			adActionDesc: {
				required : true,
				maxlength : 30
			},
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
			adActionDesc: {
				required : "請填寫廣告內容簡述.",
				maxlength : "廣告內容簡述輸入字數已達上限 30 字"
			},
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
			$("[id*=checkbox]").not("[name*=selAll]").each(function(){
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
			
			if($("#adSpecificPlayType2").prop("checked")){
				
				var checkSelect = false;
				$("[name=websiteAddCategory]").each(function(){
					checkSelect = true;
				});
				
				if(!checkSelect){
					alert("提醒您，指定投放網站類型至少要選擇一種類型");
					return false;
				}
			} 
			
			if($("#pvLimitSelect2").prop("checked")){
				
				var re = /^[0-9]*$/;
				var adPvLimitAmount = $("#adPvLimitAmountSelect").val();
				$("#adPvLimitAmount").val(adPvLimitAmount);
				if(!re.test(adPvLimitAmount)){
					alert("曝光頻率限制次數只能填寫數字");
					return false;
				}
				
				if(adPvLimitAmount == ""){
					alert("曝光頻率限制須至少一次");
					return false;
				}
				
				if(parseInt($("#adPvLimitAmount").val()) <= 0){
					alert("曝光頻率限制須至少一次");
					return false;
				}
			}
			
			var prodCodeInfoEdit = new Object();
			prodCodeInfoEdit["tracking_code"] = {};
			prodCodeInfoEdit["convert_code"] = {};
			
			if($('input[name=adactionTrackingCode]:checked').val() == '1' && $("#trackingCodeAdd").children().length == 0){
				$("#trackingCodrMsg").show();
				return false;
			}else{
				$("#trackingCodeAdd").children().each(function(index) {
					prodCodeInfoEdit.tracking_code[index] = this.id;
				});
			}
			
			if($('input[name=convertCodeRadio]:checked').val() == 'Y' && $("#convertCodeSelect option:selected").val() == "0"){
				$("#convertCodrMsg").show();
				return false;
			}else{
				if($("#convertCodeSelect option:selected").val() != "0"){
					prodCodeInfoEdit.convert_code[0] = $("#convertCodeSelect option:selected").val();
				}
				
			}
			
			if($('input[name=adactionTrackingCode]:checked').val() == '0'){
				prodCodeInfoEdit.tracking_code = {};
			}
			if($('input[name=convertCodeRadio]:checked').val() == 'N'){
				prodCodeInfoEdit.convert_code = {};
			}
			
			var myJSON = JSON.stringify(prodCodeInfoEdit);
			$("#prodCodeInfo").text(myJSON);
			
			// form submit
			$("#adActionStartAge").removeAttr("disabled");
			$("#adActionEndAge").removeAttr("disabled");
			$("#timeCode").val(timeString);
			$("#adType").removeAttr("disabled");
			$("#adDevice").removeAttr("disabled");
			
			
			
			$("#modifyForm").submit();
		}
		
	});

	$('#cancel').click(function(){
		$("#modifyForm")[0].reset();
		window.location.replace($("#backPage").val());
		//$(location).attr('href',$("#backPage").val());
	});
	
	$("#adActionStartAge,#adActionEndAge").change(function(){
		var startAge = $("#adActionStartAge").val();
		var endAge = $("#adActionEndAge").val();
		
		//檢查年齡區間是否有間隔5歲以上
		if((parseInt(endAge) - parseInt(startAge)) < 5){
			alert("提醒您，族群年齡區間需間隔5歲以上");
		}
	});
	
	$.ajax({
        url: "getPfbxWebsiteCategory.html",
        type: "POST",
        dataType: "json",
        success: function (response) {

        },
        error: function (response) {

        }
    }).done(function (response) {
        var categoryObj = JSON.parse(response);
        websiteCategoryObj = categoryObj;
        //console.log(categoryObj);
        appendCategory("website",categoryObj);
        appendAddSort("website",categoryObj);
        loadCheckCategory("website");
    });
	
	$("#adSpecificPlayType1").click(function(){
		$("[name=adActionSex]").removeAttr("disabled");
		$("[name=age]").removeAttr("disabled");
		if($("#age2").prop("checked")){
			$("#adActionStartAge").removeAttr("disabled");
			$("#adActionEndAge").removeAttr("disabled");
		}
		$("#websiteButton").attr("onclick","openWebsite('1')");
		$("#websiteButton").html("<em>瀏覽</em>")
		$("#websiteData").hide();
		$("#websiteAddDiv").hide();
	});
	
	$("#adSpecificPlayType2").click(function(){
		if($(this).prop("checked")){
			$("[name=adActionSex]").attr("disabled","disabled");
			$("[name=age]").attr("disabled","disabled");
			$("#adActionStartAge").attr("disabled","disabled");
			$("#adActionEndAge").attr("disabled","disabled");
			
			nothing = false;
			$("[name=websiteAddCategory]").each(function() {
				nothing = true;
			});
			
			if(nothing){
				$("#websiteAddDiv").show();
			}
		}
	});
	
	//初始化廣告活動設定商品廣告代碼
	initSaveProdCode();
	$("input[name='adactionTrackingCode']").change(function(){
		disableNotUseTrackingCode();
	});
	
	
	$("input[name='convertCodeRadio']").change(function(){
		if(this.value == 'N'){
			$("#convertCodeSelect").attr("disabled","disabled");
		}else{
			$("#convertCodeSelect")[0].removeAttribute('disabled');
		}
	});
	
});

function changeCountry(city){
	$("#adCountry").val(city);
}


function initSaveProdCode(){
	var prodCodeInfo = JSON.parse($("#prodCodeInfo").text());
	var flag = false;
	for (key in prodCodeInfo) {
		if(key == "convert_code"){
			for (x in prodCodeInfo[key]) {
				var convertCode = (prodCodeInfo[key])[x];
				if($("#convertCodeSelect option[value="+convertCode+"]")[0] != undefined){
					$("#convertCodeSelect option[value="+convertCode+"]").attr('selected', 'selected');
					document.getElementsByName("convertCodeRadio")[1].checked = true;
					flag = true;
				}
			}
		}else if(key == "tracking_code"){
			for (x in prodCodeInfo[key]) {
				var trackingCode = (prodCodeInfo[key])[x];
				$("#trackingCodeUl li").each(function(index) {
					if($(this).attr('trakingCode') != undefined && $(this).attr('trakingCode') != "" && $(this).attr('trakingCode') != 'undefined'){
						var code = $(this).attr('trakingCode');
						if(trackingCode == code){
							var name = this.children[0].children[1].innerHTML.replace("名稱:","");
							var addTrackingCodeStr = '<li class="lstbox" id="'+code+'" name="alex">'+
							'<div class="listxt">'+name+'</div>'+
							'<button onclick="deleteTrackingCode(\''+code+'\',this)" class="hidnbt cnrl_hb" type="button" style="height: 12px; width: 12px;" ><span class="accessible_elem"><em class="box">關閉</em></span><i aria-hidden="true" class="rmicpo sp_pfpimg ic_rmvxl" size="12" alt=""></i></button>'+
							'</li>';
							$("#trackingCodeAdd").append(addTrackingCodeStr);
							this.className = "listem_culm01 listem_culm02";
							document.getElementsByName("adactionTrackingCode")[1].checked = true;
							$("#trackingCodeAddDiv").show();
						}
					}
				});
			}
			if($("#trackingCodeAdd").children().length < 3 && $("#trackingCodeAdd").children().length > 0){
				$(".inxrltbox")[1].style= "overflow:auto;";
			}else if($("#trackingCodeAdd").children().length > 3){
				$(".inxrltbox")[1].style= "height:100px;overflow:auto;";
			}else if($("#trackingCodeAdd").children().length == 0){
				$($(".inxrltbox")[1]).hide();
			}
			$("#trackingCodeData").hide();
		}
	}
	
	if(!flag){
		document.getElementById("convertCodeSelect").disabled = true;
	}
	
}

//點擊使用再行銷下的瀏覽
function openTrackingCode(type) {
	if($("#trackingCodeButton").text() == '關閉'){
		$("#trackingCodeButton").html("<em>瀏覽</em>");
		$("#trackingCodeData").hide();
	}else{
		if($('input[name=adactionTrackingCode]:checked').val() == '1'){
			$("#trackingCodeButton").html("<em>關閉</em>");
			$("#trackingCodeData").show();
		}
	}
	if($("#trackingCodeAdd").children().length > 0){
		$("#trackingCodeAddDiv").show();
	}
}

function specificTrackingCodeSelectAll(){
	$("#trackingCodeAdd").empty();
	$("#trackingCodeUl li").each(function(index) {
		if($(this).attr('trakingCode') != undefined && $(this).attr('trakingCode') != "" && $(this).attr('trakingCode') != 'undefined'){
			var code = $(this).attr('trakingCode');
			var addTrackingCodeStr = '<li class="lstbox" id="'+code+'" name="alex">'+
			'<div class="listxt">'+code+'</div>'+
			'<button onclick="deleteTrackingCode(\''+code+'\',this)" class="hidnbt cnrl_hb" type="button" style="height: 12px; width: 12px;" ><span class="accessible_elem"><em class="box">關閉</em></span><i aria-hidden="true" class="rmicpo sp_pfpimg ic_rmvxl" size="12" alt=""></i></button>'+
			'</li>';
			$("#trackingCodeAdd").append(addTrackingCodeStr);
			this.className = "listem_culm01 listem_culm02";
		}
	});
	if($("#trackingCodeAdd").children().length > 0){
		$("#trackingCodeAddDiv").show();
	}
	
	if($("#trackingCodeAdd").children().length < 3 && $("#trackingCodeAdd").children().length > 0){
		$(".inxrltbox")[1].style= "overflow:auto;";
		$("#trackingCodeData").show();
	}else if($("#trackingCodeAdd").children().length > 3){
		$(".inxrltbox")[1].style= "height:100px;overflow:auto;";
		$("#trackingCodeData").show();
	}else if($("#trackingCodeAdd").children().length == 0){
		$($(".inxrltbox")[1]).hide();
	}
	
}

function specificTrackingCodeRemoveAll(){
	$("#trackingCodeAdd").empty();
	$("#trackingCodeUl li").each(function(index) {
		if($(this).attr('trakingCode') != undefined && $(this).attr('trakingCode') != "" && $(this).attr('trakingCode') != 'undefined'){
			this.className = "listem_culm01";
		}
	});
	$("#trackingCodeAddDiv").hide();
}

function checkTrackingCode(obj,code){
	var liType = obj.parentElement.parentElement.parentElement.className;
	var name = obj.parentElement.parentElement.parentElement.children[0].children[1].innerHTML.replace("名稱:","");
	
	if(liType == "listem_culm01"){
		obj.parentElement.parentElement.parentElement.className = "listem_culm01 listem_culm02";
		var addTrackingCodeStr = '<li class="lstbox" id="'+code+'" name="alex">'+
		'<div class="listxt">'+name+'</div>'+
		'<button onclick="deleteTrackingCode(\''+code+'\',this)" class="hidnbt cnrl_hb" type="button" style="height: 12px; width: 12px;" ><span class="accessible_elem"><em class="box">關閉</em></span><i aria-hidden="true" class="rmicpo sp_pfpimg ic_rmvxl" size="12" alt=""></i></button>'+
		'</li>';
		$("#trackingCodeAdd").append(addTrackingCodeStr);
		$("#trackingCodeAddDiv").show();
	}else{
		obj.parentElement.parentElement.parentElement.className = "listem_culm01";
		$("#trackingCodeAdd").children().each(function(index) {
			if(this.id == code){
				this.remove();
			}  
		});
		if($("#trackingCodeAdd").children().length == 0){
			$("#trackingCodeAddDiv").hide();
		}
	}
	
	if($("#trackingCodeAdd").children().length < 3 && $("#trackingCodeAdd").children().length > 0){
		$(".inxrltbox")[1].style= "overflow:auto;";
		$("#trackingCodeData").show();
	}else if($("#trackingCodeAdd").children().length > 3){
		$(".inxrltbox")[1].style= "height:100px;overflow:auto;";
		$("#trackingCodeData").show();
	}else if($("#trackingCodeAdd").children().length == 0){
		$($(".inxrltbox")[1]).hide();
	}
	
}

function disableNotUseTrackingCode(){
	if($('input[name=adactionTrackingCode]:checked').val() == '0'){
		$("#trackingCodeAddDiv").hide();
		$("#trackingCodeData").hide();
		$("#trackingCodeButton").html("<em>瀏覽</em>");
	}else{
		$("#trackingCodeAddDiv").show();
		$("#trackingCodeData").show();
		$("#trackingCodeButton").html("<em>關閉</em>");
		
		if($("#trackingCodeAdd").children().length < 3 && $("#trackingCodeAdd").children().length > 0){
			$(".inxrltbox")[1].style= "overflow:auto;";
			$("#trackingCodeData").show();
		}else if($("#trackingCodeAdd").children().length > 3){
			$(".inxrltbox")[1].style= "height:100px;overflow:auto;";
			$("#trackingCodeData").show();
		}else if($("#trackingCodeAdd").children().length == 0){
			$($(".inxrltbox")[1]).hide();
			
		}
	}
}

function deleteTrackingCode(code,boj){
	$("#trackingCodeUl li").each(function(index) {
		if($(this).attr('trakingCode') != undefined && $(this).attr('trakingCode') != "" && $(this).attr('trakingCode') != 'undefined'){
			if($(this).attr('trakingCode') == code){
				this.className = "listem_culm01";
			}
		}
	});
	boj.parentElement.remove();
//	if($("#trackingCodeAdd").children().length < 3){
//		$(".inxrltbox")[1].style= "overflow:auto;";
//	}else{
//		$(".inxrltbox")[1].style= "height:100px;overflow:auto;";
//	}
//	
//	if($("#trackingCodeAdd").children().length < 3 && $("#trackingCodeAdd").children().length > 0){
//		$(".inxrltbox")[1].style= "overflow:auto;";
//		$("#trackingCodeData").show();
//	}else if($("#trackingCodeAdd").children().length > 3){
//		$(".inxrltbox")[1].style= "height:100px;overflow:auto;";
//		$("#trackingCodeData").show();
//	}else if($("#trackingCodeAdd").children().length == 0){
//		$($(".inxrltbox")[1]).hide();
//		
//	}
//	$("#trackingCodeButton").html("<em>關閉</em>")
}

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
	$("#detailId").val("進階設定 -");
	$("#detailId").attr("onclick","closeDetail()");
	$("#selectDetail").show();
	$("#detailMsg").show();
}

//關閉進階設定
function closeDetail(){
	$("#detailId").val("進階設定 +");
	$("#detailId").attr("onclick","openDetail()");
	$("#selectDetail").hide();
	$("#detailMsg").hide();
}

//選擇全時段播放
function selAllTime(){
	$("#openTimeDetail").html('自訂播放時段');
	$("[id*=checkbox]").attr("checked","checked");
	$("[id*=selAll]").attr("checked","checked");
}

//選擇自訂時段播放
function selAnyTime(){
	$("#openTimeDetail").html('<a id="detailId" style="cursor: pointer;" onclick="selectTime()" >自訂播放時段</a>');
	$("[id*=checkbox]").removeAttr("checked");
	$("[id*=selAll]").removeAttr("checked");
	selectTime();
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

//無曝光頻率限制 
function selNoLimit(){
	$("#adPvLimitStyle").attr("disabled","disabled");
	$("#adPvLimitPeriod").attr("disabled","disabled");
	$("#adPvLimitAmount").attr("disabled","disabled");
	$("#adPvLimitAmount").val("0");
	$("#adPvLimitAmountSelect").attr("disabled","disabled");
	$("#adPvLimitAmountSelect").val("10");
	$("#adPvLimitAmount").val("0");
}

//曝光頻率限制 
function selAnyLimit(){
	$("#adPvLimitStyle").removeAttr("disabled");
	$("#adPvLimitPeriod").removeAttr("disabled");
	$("#adPvLimitAmount").removeAttr("disabled");
	$("#adPvLimitAmountSelect").removeAttr("disabled");
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

function saveBtn(){
	$('#selectTimeDiv').html($("#fancybox-content").html());
	parent.$.fancybox.close();
}

function closeBtn(){
	//$('#selectTimeDiv').html($("#fancybox-content").html());
	parent.$.fancybox.close();
}

function selCheckbox(number){
	if($("#fancybox-content [name=checkbox" + number + "]").prop("checked")){
		$("#fancybox-content [name=checkbox" + number + "]").attr("checked","checked");
	} else {
		$("#fancybox-content [name=checkbox" + number + "]").removeAttr("checked");
	}
}

function selAllCheckbox(number){
	if($("#fancybox-content [name=selAll" + number + "]").prop("checked")){
		$("#fancybox-content [name=selAll" + number + "]").attr("checked","checked");
		$("#fancybox-content [name*=checkbox" + number + "]").attr("checked","checked");
	} else {
		$("#fancybox-content [name=selAll" + number + "]").removeAttr("checked");
		$("#fancybox-content [name*=checkbox" + number + "]").removeAttr("checked");
	}
}

function selAllTimeCheckbox(number){
	if($("#fancybox-content [name=selAllTime" + number + "]").prop("checked")){
		$("#fancybox-content [name=selAllTime" + number + "]").attr("checked","checked");
		$("#fancybox-content [name*=checkbox][name$=" + number + "]").attr("checked","checked");
	} else {
		$("#fancybox-content [name=selAllTime" + number + "]").removeAttr("checked");
		$("#fancybox-content [name*=checkbox][name$=" + number + "]").removeAttr("checked");
	}
}

function openWebsite(type) {
	if($("#adSpecificPlayType2").prop("checked")){
		if(type == '1'){
			$("#websiteButton").attr("onclick","openWebsite('2')");
			$("#websiteButton").html("<em>關閉</em>")
			$("#websiteData").show();
		} else {
			$("#websiteButton").attr("onclick","openWebsite('1')");
			$("#websiteButton").html("<em>瀏覽</em>")
			$("#websiteData").hide();
		}	
	}
}

//增加下拉選單資料
function appendCategory(specificType,data) {
	for (var keys in data) {
		var tag = '<li class="listem_culm01" id="' + specificType + 'Li_' + data[keys][0].code.substring(0,parseInt(data[keys][0].level)*5)  + '_' + data[keys][0].level + '" ';
		
		if(data[keys][0].level != "1"){
			tag += ' style="display: none;" ';
		}
		
		tag += ' ' + specificType + 'code="' + data[keys][0].code + '" >';
		tag += '<div class="s_mrow" role="presentation">';
		
		if(data[keys][0].children == "Y"){
			tag += '<div class="s_inent level' + data[keys][0].level + '" onclick="openLi(\'' + specificType + '\',\'' + data[keys][0].code.substring(0,parseInt(data[keys][0].level)*5) + '\',\'' + data[keys][0].level + '\',\'open\')">';
			tag += '<div class="ic_trarw aw_right" ></div>';
		} else {
			tag += '<div class="s_inent level' + data[keys][0].level + '" >';
			tag += '<div class="" ></div>';
		}
		
		tag += '</div>';
		tag += '<div class="cntxt">' + data[keys][0].name + '</div>';
		if(data[keys][0].children == "Y"){
			tag += '<div class="rt_slct_fd" role="presentation" id="' + specificType + '_' + data[keys][0].id + '" onclick="checkCategory(\'' + specificType + '\',\'' + data[keys][0].code.substring(0,parseInt(data[keys][0].level)*5) + '\',\'' + data[keys][0].level + '\',\'pick\')" >';
			tag += '<div class="bt_slctall"><em class="box" data-intl-hash="c2d5cea683a1a8c723b69414447d3dd4" data-intl-translation="選擇全部" data-intl-trid="">選擇全部</em></div>';
			tag += '<div class="rmcx"><span class="rmcx_icon"></span></div>';
			tag += '</div>';
		} else {
			tag += '<div class="rmcx" id="' + specificType + '_' + data[keys][0].id + '" onclick="checkCategory(\'' + specificType + '\',\'' + data[keys][0].code.substring(0,parseInt(data[keys][0].level)*5) + '\',\'' + data[keys][0].level + '\',\'pick\')" ><span class="rmcx_icon"></span></div>';
		}
		tag += '</li>';
		$("#" + specificType + "Ui").append(tag);
	}
}

//點選箭頭動作
function openLi(specificType,code,level,type){
	var levelNumber = parseInt(level) +1;
	
	if(($("li[id=" + specificType + "Li_" + code + "_" + level + "]").attr("class")).indexOf("listem_culm02") < 0){
		if(type == "open"){
			$("li[id^=" + specificType + "Li_" + code + "][id$=_" + levelNumber + "]").show();
			$($("li[id=" + specificType + "Li_" + code + "_" + level + "]").children().children().children("[class='ic_trarw aw_right']")[0]).attr("class","ic_trarw aw_dwn");
			$($("li[id=" + specificType + "Li_" + code + "_" + level + "]").children().children("[class*='s_inent level']")[0]).attr("onclick","openLi('" + specificType + "','" + code + "','" + level + "','close')");
		} else {
			$("li[id^=" + specificType + "Li_" + code + "]").not("[id=" + specificType + "Li_" + code + "_" + level + "]").each(function() {
				$(this).hide();
				$(this).html($(this).html().replace(/close/g, "open"));
				$($(this).children().children().children("[class='ic_trarw aw_dwn']")[0]).attr("class","ic_trarw aw_right");
			});
			$($("li[id=" + specificType + "Li_" + code + "_" + level + "]").children().children().children("[class='ic_trarw aw_dwn']")[0]).attr("class","ic_trarw aw_right");
			$($("li[id=" + specificType + "Li_" + code + "_" + level + "]").children().children("[class*='s_inent level']")[0]).attr("onclick","openLi('" + specificType + "','" + code + "','" + level + "','open')");
		}
	}
}

//建立右方選定目錄
function appendAddSort(specificType,data){
	
	var level01Name = "";
	var level02Name = "";
	var level03Name = "";
	var level04Name = "";
	
	$("#" + specificType + "Add").append('<ul class="clasf_area" id="' + specificType + 'AddLu"></ul>');
	
	
	for (var keys in data) {
		
		if(data[keys][0].level == '1'){
			level01Name = data[keys][0].name;
		} else if(data[keys][0].level == '2'){
			level02Name = data[keys][0].name;
		} else if(data[keys][0].level == '3'){
			level03Name = data[keys][0].name;
		} else if(data[keys][0].level == '4'){
			level04Name = data[keys][0].name;
		}
		
		if(data[keys][0].children == "Y"){
			var tag = '<ul class="clasf_area" id="' + specificType + 'AddLu_' + data[keys][0].code.substring(0,parseInt(data[keys][0].level)*5)  + '_' + data[keys][0].level + '" style="display: none;" >';
			tag += '<li class="bol01 bol02" id="' + specificType + 'TitleLi_' + data[keys][0].code.substring(0,parseInt(data[keys][0].level)*5) + '" >';
			
			if(parseInt(data[keys][0].level) >= 1){
				tag += '<span>' + level01Name + '</span>';
			}
			
			if(parseInt(data[keys][0].level) >= 2){
				tag += '<span class="bularw bol03"> &gt; </span><span>' + level02Name + '</span>';
			}
			
			if(parseInt(data[keys][0].level) >= 3){
				tag += '<span class="bularw bol03"> &gt; </span><span>' + level03Name + '</span>';
			}
			
			if(parseInt(data[keys][0].level) >= 4){
				tag += '<span class="bularw bol03"> &gt; </span><span>' + level04Name + '</span>';
			}
			
			tag += '<button class="hidnbt cnrl_hb" type="button" style="height: 12px; width: 12px;" onclick="removeAddCategoryList(\'' + specificType + 'AddLu_' + data[keys][0].code.substring(0,parseInt(data[keys][0].level)*5)  + '_' + data[keys][0].level + '\')" >';
			tag += '<span class="accessible_elem"><em class="box">關閉</em></span><i aria-hidden="true" class="rmicpo sp_pfpimg ic_rmvxl" size="12" alt=""></i></button>';
			tag += '</li>';
			tag += '</lu>';
			
			$("#" + specificType + "Add").append(tag);
		}
		
	}
}

//點選類別方框動作
function checkCategory(specificType,code,level,type){
	var addCode = $("li[id=" + specificType + "Li_" + code + "_" + level + "]").attr(specificType + "code");
	
	if(type == "pick"){
		$("li[id=" + specificType + "Li_" + code + "_" + level + "]").attr("class","listem_culm01 listem_culm02");
		$("li[id=" + specificType + "Li_" + code + "_" + level + "]").html($("li[id=" + specificType + "Li_" + code + "_" + level + "]").html().replace(/pick/g, "unPick"));
		
		$("li[id^=" + specificType + "Li_" + code + "]").not("[id=" + specificType + "Li_" + code + "_" + level + "]").each(function() {
			$(this).hide();
			$(this).html($(this).html().replace(/close/g, "open"));
			$($(this).children().children().children("[class='ic_trarw aw_dwn']")[0]).attr("class","ic_trarw aw_right");
		});
		$($("li[id=" + specificType + "Li_" + code + "_" + level + "]").children().children().children("[class^='ic_trarw aw_']")[0]).attr("class","ic_trarw aw_right");
		if(($("li[id=" + specificType + "Li_" + code + "_" + level + "]").html()).indexOf("ic_trarw aw_") > 0){
			$($("li[id=" + specificType + "Li_" + code + "_" + level + "]").children().children("[class*='s_inent level']")[0]).attr("onclick","openLi('" + specificType + "','" + code + "','" + level + "','open')");
		}
		
		var categoryData = null;
		if(specificType == "website"){
			categoryData = websiteCategoryObj[addCode];
		}
		
		//var tag = '<li id="' + specificType + 'Add_' + categoryData[0].code + '" name="' + specificType + 'AddCategory" value="' + categoryData[0].id +  '" >';
		// += '<lu>';
		//tag += '<div>';
		var tag = '<li class="lstbox" id="' + specificType + 'Add_' + categoryData[0].code + '" >';
		tag += '<div class="listxt">' + categoryData[0].name + '</div>';
		tag += '<button class="hidnbt cnrl_hb" type="button" style="height: 12px; width: 12px;" onclick="removeAddCategory(\'' + specificType + '\',\'' + addCode + '\')" >';
		tag += '<span class="accessible_elem"><em class="box">關閉</em></span><i aria-hidden="true" class="rmicpo sp_pfpimg ic_rmvxl" size="12" alt=""></i></button>';
		tag += '<input type="hidden" name="' + specificType + 'AddCategory" value="' + categoryData[0].id +  '" />';
		tag += '</li>';
		//tag += '</div>';
		//tag += '</lu>';
		//tag += '</li>';
		
		if(level != "1"){
			var levelNumber = parseInt(level) -1;
			$("#" + specificType + "AddLu_" + code.substring(0,levelNumber*5) + "_" + levelNumber).show();
			$("#" + specificType + "AddLu_" + code.substring(0,levelNumber*5) + "_" + levelNumber).append(tag);
		} else {
			$("#" + specificType + "AddLu").show();
			$("#" + specificType + "AddLu").append(tag);
		}
		
		$("#" + specificType + "AddDiv").show();
		
		$('li[id^="' + specificType + 'TitleLi_' + code + '"] button').trigger('click');
	} else {
		$("li[id=" + specificType + "Li_" + code + "_" + level + "]").attr("class","listem_culm01");
		$("li[id=" + specificType + "Li_" + code + "_" + level + "]").html($("li[id=" + specificType + "Li_" + code + "_" + level + "]").html().replace(/unPick/g, "pick"));
		
		removeAddData(specificType,addCode);
	}
}

//移除已選擇的類別
function removeAddCategory(specificType,addCode){
	
	$("li[" + specificType + "code=" + addCode + "]").attr("class","listem_culm01");
	$("li[" + specificType + "code=" + addCode + "]").html($("li[" + specificType + "code=" + addCode + "]").html().replace(/unPick/g, "pick"));
	
	
	removeAddData(specificType,addCode);
}

//移除資料連動
function removeAddData(specificType,addCode){
	var parentId = $($("#" + specificType + "Add_" + addCode).parent()[0]).attr("id");
	$("#" + specificType + "Add_" + addCode).remove();
	var nothing = true;
	
	$("[id=" + parentId + "] li").not("[class='bol01 bol02']").each(function() {
		nothing = false;
	});
	
	if(nothing){
		$("[id=" + parentId + "]").hide();
	}
	
	nothing = true;
	$("[name=" + specificType + "AddCategory]").each(function() {
		nothing = false;
	});
	
	if(nothing){
		$("#" + specificType + "AddDiv").hide();
	}
}

//移除目錄下所選擇的全部類別
function removeAddCategoryList(id){
	$("[id=" + id + "] li").not("[class='bol01 bol02']").each(function() {
		$("[id=" + $(this).attr("id") + "] button").trigger('click');
	});
}

function loadCheckCategory(specificType){
	var checkId = $("#old" + specificType + "Category").val();
	
	var checkIdArray = checkId.split(",");
	for(var i=0;i<checkIdArray.length;i++){
		$("[id=" + specificType + "_" + checkIdArray[i] + "]").trigger('click');
	}
	
}

//選擇全部
function specificSelectAll(specificType){
	$("li[id^=" + specificType + "Li_][id$=_1]").each(function(){
		if($(this).attr("class") == "listem_culm01"){
			$($(this).children().children()[2]).trigger('click');
		}
	});
}

//取消全部
function specificRemoveAll(specificType){
	$("li[id^=" + specificType + "Li_]").each(function(){
		if($(this).attr("class") == "listem_culm01 listem_culm02"){
			$($(this).children().children()[2]).trigger('click');
		}
	});
}