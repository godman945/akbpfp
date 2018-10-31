﻿var currentPage = 1;
var pageCount = 1;
var totalCount = 0;

var defaultTemplate = 
	    '<!-- 篩選第一層 -->'+
		'<div class="txt-inlineblock level1">'+
       '<div class="select-box">'+
          '<select class="selector" onchange="filterDisplayRule($(this).val(),$(this).parent().parent().parent());">'+
             '<option value="catalog_prod_seq">ID</option>'+
			 '<option value="ec_name">商品名稱</option>'+
             '<option value="ec_price">原價</option>'+
             '<option value="ec_discount_price">特價</option>'+
             '<option value="ec_stock_status">供應情況</option>'+
             '<option value="ec_use_status">使用狀況</option>'+
             '<option value="ec_category">類別</option>'+
          '</select>'+
       '</div>'+
    '</div>'+
    '<!-- 篩選第二層 -->'+
    '<div class="txt-inlineblock level2">'+
       '<div class="select-box" data-level="1">'+
          '<select>'+
             '<option value="like">包含</option>'+
             '<option value="notlike">不包含</option>'+
          '</select>'+
       '</div>'+
       '<div class="select-box" data-level="2">'+
          '<select>'+
             '<option value="gt">大於</option>'+
             '<option value="lt">小於</option>'+
             '<option value="eq">等於</option>'+
             '<option value="neq">不等於</option>'+
          '</select>'+
       '</div>'+
       '<div class="select-box" data-level="3">'+
          '<select>'+
             '<option value="eq">屬於</option>'+
             '<option value="neq">不屬於</option>'+
          '</select>'+
       '</div>'+
    '</div>'+
    '<!-- 篩選第三層 -->'+
    '<div class="txt-inlineblock level3">'+
       '<div class="input-text" data-level="1">'+
          '<input type="text" name="" maxlength="6" value="">'+
          '<div class="msg-error" style="display:none">請填寫篩選條件</div>'+
       '</div>'+
       '<div class="input-number" data-level="2">'+
          'NT<input type="text" name="" maxlength="6" value="">元'+
          '<div class="msg-error" style="display:none">請填寫數字</div>'+
       '</div>'+
       '<div class="select-box" data-level="3">'+
          '<select>'+
             '<option value="0">無庫存</option>'+
             '<option value="1">有庫存</option>'+
             '<option value="2">預購</option>'+
             '<option value="3">停售</option>'+
          '</select>'+
       '</div>'+
       '<div class="select-box" data-level="4">'+
          '<select>'+
             '<option value="0">全新</option>'+
             '<option value="1">二手</option>'+
             '<option value="2">福利品</option>'+
          '</select>'+
       '</div>'+
       '<div class="input-text" data-level="5">'+
       		'<input class="tags" type="text" name="" maxlength="6" value="">'+
       		'<label for="tags"></label>'+
       		'<div class="msg-error" style="display:none">請填寫篩選條件</div>'+
       '</div>'+
    '</div>'+
    '<!--刪除篩選 -->'+
    '<div class="icon-kill" onclick="deleteFilterCondition(event,$(this))"></div>';

var availableTags = [];

$(document).ready(function(){
	
	//分類選單
	initCategoryVal();
	jQuery.browser={};(function(){jQuery.browser.msie=false; jQuery.browser.version=0;if(navigator.userAgent.match(/MSIE ([0-9]+)./)){ jQuery.browser.msie=true;jQuery.browser.version=RegExp.$1;}})();
	var tagsArray = document.getElementsByClassName("tags");
	$.each(tagsArray, function(index, obj){
		
		console.log('obj old')
    	console.log(obj)
		
		$(obj).autocomplete({
		    source: availableTags,   //資料來源                
	        minLength: 0    //輸入最少字數 
		})
		    
		// 自動完成focus ; Click文字盒就顯示全部List
	    $(obj).focus(function () {
	    	if (this.value == "") {
	    		$(this).autocomplete("search");
	        }
	     });
	});
	
	
	
//	$( function() {
////	    var availableTags = [
////	      "ActionScript",
////	      "a成功",
////	      "AppleScript",
////	      "貝西",
////	      "Asp",
////	      "a貝寶",
////	      "BASIC"
////	    ];
//	    $( "#tagss" ).autocomplete({
//	      source: availableTags,   //資料來源                
//          minLength: 0    //輸入最少字數 
//	    })
//	    
////		 // 自動完成focus ; Click文字盒就顯示全部List
//        $('#tagss').focus(function () {
////        	alert('22');
//            if (this.value == "") {
//                $(this).autocomplete("search");
//            }
//        });
//	    
//} );
	
	
	
	
	
	
	
	
	
	
	
	//切換商品目錄
	$("#catalog").change(function() {
		location.href = "prodListCardStyleView.html?catalogSeq="+ $('#catalog option:selected').val() + "&currentPage=1&pageSizeSelected=10";
	 });
	
	window.onload=function(){
		 var slid = (function () {
             var activeID		= 0,
                 itemW			= $('.prodslider-item').width(),
                 slid_count		= $('.prodslider-item').length,
                 $slidItems		= $('.prodslider-items'),
                 $slidItem		= $('.prodslider-item'),
                 $arrowPrev		= $('.prodslider-navbtn.prev'),
                 $arrowNext		= $('.prodslider-navbtn.next'),
                 $itemArrow		= $('.prodslider-navbtn'),
                 slidTimeout,
                 timeOut			= 3000;

             init();

             function init() { 
                 $slidItems.css({'width':(itemW * slid_count)+'px'});
                 $itemArrow.css({'opacity':1.0});
                 navigateSlide();
             };
             
             function autoPlay(){
                 clearTimeout(slidTimeout);
                 slidTimeout=setTimeout(function(){
                     activeID++;
                     if(activeID <= slid_count-1){
                         navigateSlide();
                     }else{
                         activeID = 0;   
                         navigateSlide();
                     }
                 }, timeOut);
             };

             function navigateSlide() {
            	 console.log("activeID");
                 console.log(activeID);
                 var xTarget = ((activeID * itemW) * -1);
                 $slidItems.animate({marginLeft:xTarget},500,function(){
                     //autoPlay();
                 });
                 if (activeID == 0) {$arrowPrev.css('opacity',0.2)} else {$arrowPrev.css('opacity',1)}
                 if (activeID == slid_count-1) {$arrowNext.css('opacity',0.2);} else {$arrowNext.css('opacity',1);}
                 
             };			

             $itemArrow.on('click', function() {
                 clearTimeout(slidTimeout);
                 if($(this).hasClass('next')){
                     if(activeID<slid_count-1){
                         activeID++;
                         navigateSlide();
                     }
                 }else{
                     if(activeID>0){
                         activeID--;
                         navigateSlide();
                     }
                 };
             });

         })();
	}
	
	//初始頁碼
	initPage();
	
	//按上一頁
	$('.prev').click(function(e){
		currentPage =  parseInt(currentPage)-1;
		$('#currentPage').text(currentPage)
		$('#pageCount').text(pageCount)
		if (currentPage<=1){
			$('.prev').css('display', 'none');
		}
		//新增條件重新綁定監聽
        $(".filter-wrap select").unbind("click");
        $(".filter-wrap select").bind("click", handler);
        handler(e);
		queryProdGroupFilterListAjax();
	 });
	
	
	//按下一頁
	$('.next').click(function(e){
		currentPage = parseInt(currentPage)+1;
		$('#currentPage').text(currentPage)
		$('#pageCount').text(pageCount)
		if (currentPage>=pageCount){
			$('.next').css('display', 'none');
		}
		//新增條件重新綁定監聽
        $(".filter-wrap select").unbind("click");
        $(".filter-wrap select").bind("click", handler);
        handler(e);
		queryProdGroupFilterListAjax();
	 });
	
	
//	$('.prodslider-navbtn').on('click', function() {
////        clearTimeout(slidTimeout);
//        if($(this).hasClass('next')){
//        	
//            if(activeID<slid_count-1){
//                activeID++;
//                navigateSlide();
//            }
//        }else{
//            if(activeID>0){
//                activeID--;
//                navigateSlide();
//            }
//        };
//    });
	

    // 增加篩選條件
    $('.link-addfilter').click(function(){
    	$('.filter-wrap')[0].insertAdjacentHTML('beforeend', '<div class="filter-group" data-level1="1" data-level2="1" data-level3="1">'+defaultTemplate+'</div>');
    	
        //新增條件重新綁定監聽
        $(".filter-wrap select").unbind("change");
        $(".filter-wrap input").unbind("keyup");
        
        $(".filter-wrap select").bind("change", handler);
        $(".filter-wrap input").bind("keyup", handler);
        
    })
    
    
    
	
	if($("#catalogSeqData").val() !=""){
		console.log("#####################999999999");
		$("#catalog").val($("#catalogSeqData").val());
		
		//針對類別條件綁定jquery查詢套件
		var tagsArray = document.getElementsByClassName("tags");
		$(function() {
			$.each(tagsArray, function(index, obj){
				$(obj).unbind();
				$(obj).autocomplete({
					source: availableTags,
					minLength: 0,
					select: function(event, ui) {
						console.log(event);
						console.log("CCCCCCC");
					},
					search: function(event, ui) {
						console.log(event);
						console.log("BBBBBBBBBBb"); 
					},
				}).focus(function() {
					if (this.value == "") {
						$(this).autocomplete("search");
					}
				});
			});
		 });
		
	}
	
    //重bind事件
    $(".filter-wrap select").bind("change", handler);
    $(".filter-wrap input").bind("keyup", handler);
    
    //重bind事件
    $("#catalogGroupName").bind("keyup", handler);
    
    
    $('#addCatalogProdGroup').click(function(e){
    	addCatalogProdGroupAjax();
    })
    
    
    // 請填寫組合名稱
    $('#catalogGroupName').click(function(){
        //組合名稱重新綁定監聽
        $("#catalogGroupName").unbind("keyup");
        $("#catalogGroupName").bind("keyup", handler);
    })
    
});


//初始頁面按鈕
function initPage(){
	currentPage = $('#currentPage').text();
	pageCount = $('#pageCount').text();
	totalCount = $('#totalCount').text();

	
	$('.prev').css('display','');
	$('.next').css('display','');
	
	if (pageCount==1 ){
		$('.prev').css('display', 'none')
		$('.next').css('display', 'none')
	}else if(currentPage<pageCount){
		$('.next').css('display','');
	}
	
	if(currentPage==pageCount){
		$('.next').css('display', 'none')
	}
	
	if(currentPage==1){
		$('.prev').css('display', 'none')
	}
	
	$('#currentPage').text(currentPage)
	$('#pageCount').text(pageCount)
	
}


/**
* 撈分類資料
*/
function initCategoryVal(){
		$.ajax({
		    type: "post",
		    dataType: "json",
		    url: "queryCategoryGroupByVal.html",
		    data: {
	    	    "catalogSeq": $('#catalog option:selected').val()
		    },
		    timeout: 30000,
		    error: function(xhr){
		    	alert("系統繁忙，請稍後再試！");
		    },
		    success:function(response, status){
		    	
			}
		}).done(function (response) {
			$.each(response.categoryGroupByVal, function(index, val){
				availableTags.push(val);
			});
		});
}


//全域
var filterContentMap= [];

/**
* 寫入商品組合篩選條件Ajax
*/
function addCatalogProdGroupAjax(){
	
	if (checkGroupName() != true){
		return;
	}
  		
	if (checkColumnValue() != true){
		return;
	}
		
		handler('click');
		
		$.ajax({
		    type: "post",
		    dataType: "json",
		    url: "addCatalogProdGroup.html",
		    data: {
	    	    "catalogSeq": $('#catalog option:selected').val(),
	   	        "catalogGroupName": $('#catalogGroupName').val(),
	   	        "filterContentMap": JSON.stringify(filterContentMap)
		    },
		    timeout: 30000,
		    error: function(xhr){
		    	alert("系統繁忙，請稍後再試！");
		    },
		    success:function(response, status){
		    	
			}
		}).done(function (response) {
	    	if (response.status=="SUCCESS"){
	    		alert(response.msg)
	    		$(location).attr( 'href' ,"queryCatalogGroup.html?catalogSeq="+ $('#catalog option:selected').val());
	    	}else{
	    		alert(response.msg)
	    	}
		});
}

/**
* 檢查商品組合名稱
*/
function checkGroupName() {
	var checkFlag = true;
	
	if ($('#catalogGroupName').val() == ""){
		$('#groupNameMsgError').css("display", "");
		checkFlag = false;
	}else{
		$('#groupNameMsgError').css("display", "none");
	}
	
	return checkFlag;
};



/**
* 新增商品組合前檢查欄位
*/
function checkColumnValue() {
	var checkFlag = true;
	
 	//如果資料值為空，不撈db
 	$(".filter-group").each(function(index,obj) {
 		var fieldStr ="";
 		var conditionStr ="";
 		var filterValStr ="";
 		$(obj).find(".txt-inlineblock").each(function(index2,obj2) {
 			//篩選第一層
 			if(index2 == 0){
 				fieldStr = $(obj2).find("select")[0].value;
 			}
 			
 			//篩選第三層
 			if(index2 == 2){
 				//商品ID
 				if (fieldStr == "catalog_prod_seq"){
 					filterValStr = $($(obj2).children()[0]).children()[0].value;
 					if (filterValStr == ""){
 						$($($(obj2).children()[0]).children()[1]).css("display", "");
 						 checkFlag = false;
 					}else{
 						$($($(obj2).children()[0]).children()[1]).css("display", "none");
 					}
 				}
 				//商品名稱
 				if (fieldStr == "ec_name"){
 					filterValStr = $($(obj2).children()[0]).children()[0].value;
 					if (filterValStr == ""){
 						$($($(obj2).children()[0]).children()[1]).css("display", "");
 						checkFlag = false;
 					}else{
 						$($($(obj2).children()[0]).children()[1]).css("display", "none");
 					}
 				}
 				
 				//原價
 				if (fieldStr == "ec_price"){
 					filterValStr = $($(obj2).children()[1]).children()[0].value;
 					if ( (filterValStr == "") || (isNaN(filterValStr)) ){
 						$($($(obj2).children()[1]).children()[1]).css("display", "");
 						checkFlag = false;
 					}else{
 						$($($(obj2).children()[1]).children()[1]).css("display", "none");
 					}
 				}
 				
 				//特價
 				if (fieldStr == "ec_discount_price"){
 					filterValStr = $($(obj2).children()[1]).children()[0].value;
 					if (filterValStr == "" || (isNaN(filterValStr)) ){
 						$($($(obj2).children()[1]).children()[1]).css("display", "");
 						checkFlag = false;
 					}else{
 						$($($(obj2).children()[1]).children()[1]).css("display", "none");
 					}
 				}
 				
 				//供應情況
 				if (fieldStr == "ec_stock_status"){
 					filterValStr = $($(obj2).children()[2]).children()[0].value;
 				}
 				
 				//使用狀況
 				if (fieldStr == "ec_use_status"){
 					filterValStr = $($(obj2).children()[3]).children()[0].value;
 				}
 				
 				//類別
 				if (fieldStr == "ec_category"){
 					filterValStr = $($(obj2).children()[0]).children()[0].value;
 					if (filterValStr == ""){
 						$($($(obj2).children()[0]).children()[1]).css("display", "");
 						checkFlag = false;
 					}else{
 						$($($(obj2).children()[0]).children()[1]).css("display", "none");
 					}
 				}
 			}
 		});
 	});
 	
 	return checkFlag; 
};


/**
* loop畫面全部篩選條件塞入全域filterContentMap
*/
function handler(e) {
	
	if (checkColumnValue() != true){
		return;
	}
	
	if (checkGroupName() != true){
		return;
	}
	
	filterContentMap = [];
	var flag = true;
 	//全部群組資料
 	$(".filter-group").each(function(index,obj) {
 		var map = new Object();
 		var fieldStr ="";
 		var conditionStr ="";
 		var filterValStr ="";
 		$(obj).find(".txt-inlineblock").each(function(index2,obj2) {
 			//篩選第一層
 			if(index2 == 0){
 				fieldStr = $(obj2).find("select")[0].value;
 				//第1層值塞map
 				map["field"] = fieldStr;
 			}
// 			console.log("第1層");
// 			console.log(fieldStr);
 			
 			//篩選第二層
 			if(index2 == 1){
 				//商品ID
 				if (fieldStr == "catalog_prod_seq"){
 					conditionStr = $($(obj2).children()[0]).children()[0].value;
 				}
 				//商品名稱
 				if (fieldStr == "ec_name"){
 					conditionStr = $($(obj2).children()[0]).children()[0].value;
 				}
 				//原價
 				if (fieldStr == "ec_price"){
 					conditionStr = $($(obj2).children()[1]).children()[0].value;
 				}
 				//特價
 				if (fieldStr == "ec_discount_price"){
 					conditionStr = $($(obj2).children()[1]).children()[0].value;
 				}
 				//供應情況
 				if (fieldStr == "ec_stock_status"){
 					conditionStr = $($(obj2).children()[2]).children()[0].value;
 				}
 				
 				//使用狀況
 				if (fieldStr == "ec_use_status"){
 					conditionStr = $($(obj2).children()[2]).children()[0].value;
 				}
 				
 				//類別
 				if (fieldStr == "ec_category"){
 					conditionStr = $($(obj2).children()[2]).children()[0].value;
 				}
 				//第2層值塞map
 				map["condition"] = conditionStr;
 			}
// 			console.log("第2層");
// 			console.log(conditionStr);
 			
 			
 			//篩選第三層
 			if(index2 == 2){
 				//商品ID
 				if (fieldStr == "catalog_prod_seq"){
 					filterValStr = $($(obj2).children()[0]).children()[0].value;
 				}
 				//商品名稱
 				if (fieldStr == "ec_name"){
 					filterValStr = $($(obj2).children()[0]).children()[0].value;
 				}
 				
 				//原價
 				if (fieldStr == "ec_price"){
 					filterValStr = $($(obj2).children()[1]).children()[0].value;
 				}
 				
 				//特價
 				if (fieldStr == "ec_discount_price"){
 					filterValStr = $($(obj2).children()[1]).children()[0].value;
 				}
 				
 				//供應情況
 				if (fieldStr == "ec_stock_status"){
 					filterValStr = $($(obj2).children()[2]).children()[0].value;
 				}
 				
 				//使用狀況
 				if (fieldStr == "ec_use_status"){
 					filterValStr = $($(obj2).children()[3]).children()[0].value;
 				}
 				
 				//類別
 				if (fieldStr == "ec_category"){
 					filterValStr = $($(obj2).children()[0]).children()[0].value;
 				}
// 				
 				//第3層值塞map
 				map["value"] = filterValStr;
 				
 				//如果第3層值為空，即不撈DB
 				if (filterValStr == ""){
 					flag = false;
 					return;
 				}
 			}
 		});
 		console.log("--map----");	
 		//篩選條件map
		filterContentMap.push(map);
	   	console.log(filterContentMap);
 	});
 	
 	
 	if (flag == true){	
	 	if(e.type == 'keyup'){
	 		$(this).doTimeout("findProdName", 1000, function() {
	 			queryProdGroupFilterListAjax();
			});
	 	}else{
	 		queryProdGroupFilterListAjax();
	 	}
 	}
 	
};


/**
* 依據商品組合篩選條件撈出符合的商品list
*/
function queryProdGroupFilterListAjax(){
	
	$.ajax({
	    type: "post",
	    dataType: "json",
	    url: "queryProdGroupFilterList.html",
	    data: {
    	    "catalogSeq": $('#catalog option:selected').val(),
    	    "filterContentMap": JSON.stringify(filterContentMap),
    	    "currentPage": currentPage,
    	    "pageSizeSelected": "4"
	    },
	    timeout: 30000,
	    error: function(xhr){
	    	alert("系統繁忙，請稍後再試！");
	    },
	    success:function(response, status){
	    	
		}
	}).done(function (response) {
//		alert(response)
    	
//    	//更新頁碼與清單資料
//    	$('.pagination-wrap').data('order', response.currentPage.toString());
//    	$('.pagination-wrap').data('quantity', response.pageCount.toString());
//    	$('#prodQuantityTxt').empty();
//    	$('#prodQuantityTxt').append(response.totalCount.toString());
        
    	if (response.status=="ERROR"){
//    		alert("ajax error")
    		$("#prodListDiv").empty();
    		alert(response.msg)
    	}else{
//    		alert("ajax ok")
//    		alert("OK")
    		$("#prodListDiv").empty();
    		var tempHtml = "";
	    	$.each(response.prodList, function(index, list){
	    		var ecImg ="";
	    		var ecName ="";
	    		var ecDiscountPrice ="";
	    		$.each(list, function(key, val){
	    			
	    			if(key == "ecImg"){
	    				ecImg = val;
	    			}
	    			if(key == "ecName"){
	    				ecName = val;
	    			}
	    			if(key == "ecDiscountPrice"){
	    				ecDiscountPrice = val;
	    			}
	    		});
	    			tempHtml += " 	<div class='prodcard-box txt-noselect'> ";
	    			tempHtml += "		<div class='prodcard-imgbox'>";
	    			tempHtml += "	    	<img src='http://showstg.pchome.com.tw/pfp/"+ecImg+"'> ";
	    			tempHtml += "	    </div> ";
	    			tempHtml += "	    <div class='prodcard-infobox'> ";
	    			tempHtml += "	    	<div class='group g1'> ";
	    			tempHtml += "	    		<div data-info-name='prodname'>"+ecName+"</div> ";
	    			tempHtml += "	    	</div> ";
	    			tempHtml += "	    	<div class='group g2'> ";
	    			tempHtml += "	    		<div data-info-name='promoprice'>特價<span>$</span><i>"+ecDiscountPrice+"</i></div> ";
	    			tempHtml += "	    	</div> ";
	    			tempHtml += "		</div> ";
	    			tempHtml += "	</div> ";
	    	
	    	});
	    	$('#prodListDiv').html(tempHtml);
	    	
    	}
//	    	//整理頁碼
	    	processPageAndTotalPage(response);
	    	//初始頁碼
	    	initPage();
	    });
	
}



//整理頁碼
function processPageAndTotalPage(response){
//	alert("頁碼")
	currentPage = response.currentPage;
	pageCount = response.pageCount;
	totalCount = response.totalCount;
	
	
	console.log(currentPage)
	console.log(pageCount)
	console.log(totalCount)
	
	$('#currentPage').text(response.currentPage);
	$('#pageCount').text(response.pageCount);
	$('#totalCount').text(response.totalCount);
}


//call ajax
function deleteFilterCondition(event,obj){
	obj.parent().remove();
	handler(event);
	
	
	console.log("del OK");
	
}



function filterDisplayRule(val, obj) {
	obj[0].innerHTML = '';
	obj[0].innerHTML = defaultTemplate;
	
	
	//新增條件重新綁定監聽
    $(".filter-wrap select").unbind("change");
    $(".filter-wrap input").unbind("keyup");
    
    $(".filter-wrap select").bind("change", handler);
    $(".filter-wrap input").bind("keyup", handler);
	
//	console.log(val);
//	console.log(obj[0]);
	$(obj).find('select').each(function(index, object){
		if(index == 0){
			var selectObj = object;
			$(selectObj).val(val);
		}
	});
	
	var level1, level2, level3;
	switch (val) {
	case 'catalog_prod_seq'://ID
		level1 = '1';
		level2 = '1';
		level3 = '1';
		break;
	case 'ec_name'://prodname
		level1 = '2';
		level2 = '1';
		level3 = '1';
		break;
	case 'ec_price'://listprice
		level1 = '3';
		level2 = '2';
		level3 = '2';
		break;
	case 'ec_discount_price'://promoprice
		level1 = '4';
		level2 = '2';
		level3 = '2';
		break;
	case 'ec_stock_status'://supplement
		level1 = '5';
		level2 = '3';
		level3 = '3';
		break;
	case 'ec_use_status'://neworused
		level1 = '6';
		level2 = '3';
		level3 = '4';
		break;
	case 'ec_category'://class
		level1 = '7';
		level2 = '3';
		level3 = '5';
		break;
	default:
		;
	}
	obj.attr('data-level1', level1);
	obj.attr('data-level2', level2);
	obj.attr('data-level3', level3);
//	console.log(obj.index(), val, level1, level2, level3);
	
	
	
	//針對類別條件綁定jquery查詢套件
	var tagsArray = document.getElementsByClassName("tags");
	$(function() {
		$.each(tagsArray, function(index, obj){
			$(obj).unbind();
			$(obj).autocomplete({
				source: availableTags,
				minLength: 0,
				select: function(event, ui) {
					console.log(event);
					console.log("CCCCCCC");
				},
				search: function(event, ui) {
					console.log(event);
					console.log("BBBBBBBBBBb"); 
				},
			}).focus(function() {
				if (this.value == "") {
					$(this).autocomplete("search");
				}
			});
		});
	 });
}




