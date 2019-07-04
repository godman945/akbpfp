$(document).ready(function () {

	horisontal_scroll_listing($("#table-listing"));
	
});

// 調整查詢結果列表畫面(art提供)
function horisontal_scroll_listing(listing_obj) {
	if ($("#totalPage").val() == 0) { // 查無資料則總頁數為0，查無資料不調整畫面
		$("div.prodtable-box.txt-noselect").addClass("noData");
	}
	
	// get table object
	table_obj = $('.table', listing_obj);
	//get count fixed collumns params
	count_fixed_collumns = table_obj.attr('data-fixed-columns');
	if (count_fixed_collumns > 0) {
		// get wrapper object
		wrapper_obj           = $('.table-scrollable', listing_obj);
		wrapper_left_margin   = 0;
		table_collumns_width  = new Array();
		table_collumns_margin = new Array();
		
		// calculate wrapper margin and fixed column width
		$('th', table_obj).each(function(index) {
			if (index < count_fixed_collumns) {
				wrapper_left_margin        += $(this).outerWidth();
				table_collumns_width[index] = $(this).outerWidth();
			}
		})
		
		// calcualte margin for each column
		$.each(table_collumns_width, function(key, value) {
			if (key == 0) {
				table_collumns_margin[key] = wrapper_left_margin;
			} else {
				next_margin = 0;
				$.each(table_collumns_width, function(key_next, value_next) {
					if (key_next < key) {
						next_margin += value_next;
					}
				});
				table_collumns_margin[key] = wrapper_left_margin - next_margin;
			}
		});
		
		// set wrapper margin
		if (wrapper_left_margin > 0) {
			wrapper_obj.css('cssText', 'margin-left:' + wrapper_left_margin + 'px !important; width: auto');
		}
		
		// set position for fixed columns
		$('tr', table_obj).each(function() {
			// get current row height
			current_row_height = $(this).outerHeight();
			$('th,td', $(this)).each(function(index) {
				// set row height for all cells
				var url = location.pathname;
				if (url.indexOf('reportKeyword.html') == -1 
						&& $("#totalPage").val() > 0) { // 非關鍵字成效報表且有資料，才做高度css處理
					$(this).css('height', current_row_height);
				}
				// set position
				if (index < count_fixed_collumns) {
					$(this).css({
						'position' : 'absolute',
						'margin-left' : '-' + table_collumns_margin[index] + 'px', 
						'width' : table_collumns_width[index]
					})
					$(this).addClass('table-fixed-cell');
				}
			})
		})  
	}
}

// 處理頁碼按鈕事件
function processPageNumber() {
	var pageNo = $("#page").val(); // 目前第幾頁
	var pageCount = $("#totalPage").val(); // 共幾頁(總頁數)
	
	var currentlyStartPage; // 目前開始頁碼
	var currentlyEndPage; // 目前結束頁碼
	var top10Pages; // 上10頁開始頁碼
	if (parseInt(pageNo) % 10 == 0) { // 目前選擇頁數為10 20 30等10位數的時候處理
		currentlyStartPage = parseInt(pageNo % 10) * 10 + (pageNo - 10) + 1;
		currentlyEndPage = parseInt(pageNo % 10) * 10 + (pageNo - 10) + 10;
		top10Pages = parseInt(parseInt(pageNo / 10) * 10 - 19);
	} else {
		currentlyStartPage = parseInt(pageNo / 10) * 10 + 1;
		currentlyEndPage = parseInt(pageNo / 10) * 10 + 10;
		top10Pages = parseInt(parseInt(pageNo / 10) * 10 - 9);
	}
	
	var tempHtml = "";
	if (pageCount <= 10) { // 總頁數10頁(含)以下
		tempHtml += "<span class=\"pagination-buttongroup\">";
		for (var i = 1; i <= pageCount; i++) {
			if (i == pageNo) { // 目前頁數不帶換頁事件
				tempHtml += "<a data-num=\"" + i + "\" class=\"pagination-button active\" href=\"javascript:void(0);\"></a>";
			} else {
				tempHtml += "<a data-num=\"" + i + "\" class=\"pagination-button\" href=\"javascript:processChangePage('" + i + "');\"></a>";
			}
		}
		tempHtml += "</span>";
	} else {
		tempHtml += "<li class=\"txt-cell txt-left\">";
		tempHtml += "  <a data-num=\"1\" class=\"pagination-button left\" href=\"javascript:processChangePage('1');\"></a>";
		tempHtml += "</li>";
		tempHtml += "<li class=\"txt-cell\">";
		
		if (pageNo > 10) { // 當前頁超過第10頁才顯示上10頁按鈕
		tempHtml += "  <a class=\"pagination-button prev\" href=\"javascript:processChangePage('" + top10Pages + "');\" title=\"上10頁\"></a>";
		}
		
		tempHtml += "  <span class=\"pagination-buttongroup\">";

		for (var i = currentlyStartPage; i <= currentlyEndPage; i++) {
			if (i > pageCount) { // 超過總頁數時離開迴圈
				break;
			} else if (i == pageNo) { // 目前頁數不帶換頁事件
				tempHtml += "  <a data-num=\" " + i + " \" class=\"pagination-button active\" href=\"javascript:void(0);\"></a>";
			}else{
				tempHtml += "  <a data-num=\" " + i + " \" class=\"pagination-button\" href=\"javascript:processChangePage('" + i + "');\"></a>";
			}
		}
		tempHtml += "  </span>";
 
		if (pageNo <= Math.ceil(pageCount / 10) * 10 - 10) { // 總頁數/10  後無條件進位 *10 再-10為當前頁小於此數字才能出現下10頁按鈕
			tempHtml += "  <a class=\"pagination-button next\" href=\"javascript:processChangePage('" + parseInt(Math.ceil(pageNo / 10) * 10 + 1) + "');\" title=\"下10頁\"></a>";
		}
		
		tempHtml += "</li>";
		tempHtml += "<li class=\"txt-cell txt-right\">";
		tempHtml += "  <a data-num=\"" + pageCount + "\" class=\"pagination-button right\" href=\"javascript:processChangePage('" + pageCount + "');\"></a>";
		tempHtml += "</li>";
	}
	$(".pagination-box").html(tempHtml);
}

//樣板尺寸
var tproObject = new Object();
tproObject["data"] = {};

//行銷結尾圖
var salesEndIframewp = new Object();
salesEndIframewp["data"] = {};

var tempPRODObj; // 暫存目前選擇的商品廣告資料obj
/**
 * 目前廣告明細成效、總廣告成效 > 總廣告成效-明細用到
 * @param adStyle
 * @param Obj
 * @returns
 */
function preView(adStyle, Obj) {
	var tempObj = $(Obj).siblings(".hiddenVal"); // 取得同一層隱藏欄位值區塊
	var tempWidth = $(tempObj).find('#width').val();
	var tempHeight = $(tempObj).find('#height').val();
	
	var tempHtmlViewArr = new Array();
	var templateSizeOptionStr = '';
	var showLeftRightBtn = false; // 顯示左右按鈕，商品廣告同尺寸不同樣板切換
	
	if (adStyle == 'IMG') { // 圖像廣告
		// 組中間圖片畫面
		tempHtmlViewArr.push('<div class="predivbox"><iframe src="http://showstg.pchome.com.tw/pfp/' + $(tempObj).find('#img').val() + '" width="' + tempWidth + '" height="' + tempHeight + '" allowtransparency="true" frameborder="0" scrolling="no"></iframe></div>');
	} else if (adStyle == 'VIDEO') { // 影音廣告
		// 組中間圖片畫面
		tempHtmlViewArr.push('<div class="predivbox"><iframe scrolling="no" frameborder="0" marginwidth="0" marginheight="0" vspace="0" hspace="0" id="pchome8044_ad_frame1" width="' + tempWidth + '" height="' + tempHeight
				+ '" allowtransparency="true" allowfullscreen="true" src="http://showstg.pchome.com.tw/pfp/adVideoModel.html?adPreviewVideoURL=' + $(tempObj).find('#adPreviewVideoURL').val()
				+ '&adPreviewVideoBgImg=' + $(tempObj).find('#adPreviewVideoBgImg').val()
				+ '&realUrl=' + $(tempObj).find('#realUrl').val() + '&resize=true\"></iframe></div>');
	} else if (adStyle == 'PROD') { // 商品廣告
		showLeftRightBtn = true;
		var templateTpro = JSON.parse($(tempObj).find('#productTemplateStr').html());
		templateSizeOptionStr = processPRODSizeOptionHtml(templateTpro); // 選擇廣告尺寸
		tempPRODObj = Obj; // 切換尺寸用
		
		// 組中間圖片畫面
		var tproArr = templateTpro[tempWidth + "_" + tempHeight].split(',');
		for (var index = 0; index < tproArr.length; index++) {
			if(tproArr[index]){
				tempHtmlViewArr.push(processPRODIframeHtml(tempObj, tproArr[index]));
			}
		}
	} else if (adStyle == 'TMG') { // 圖文廣告
		templateSizeOptionStr = processTMGSizeOptionHtml($(tempObj).find('#adSeq').val()); // 選擇廣告尺寸
		
		// 組中間圖片畫面
		tempHtmlViewArr.push('<div class="predivbox"><span style="display:inline-block; background-color:white;"><iframe width="' + tempWidth + '" height="' + tempHeight + '" src="http://showstg.pchome.com.tw/pfp/adModel.html?adNo=' + $(tempObj).find('#adSeq').val() + '&tproNo=' + $(tempObj).find('#tproNo').val() + '" marginwidth="0" marginheight="0" scrolling="no" frameborder="0" align="ceneter" class="akb_iframe"></iframe></span></div>');
	}
	
	commonFancyBox(tempHtmlViewArr, showLeftRightBtn, tempWidth, tempHeight, templateSizeOptionStr);
}

/**
 * 處理PROD商品廣告尺寸下拉選單
 * @param templateTpro
 * @returns
 */
function processPRODSizeOptionHtml(templateTpro) {
	var tempHtml = '<div id="previewselector" class="previewselector">選擇廣告尺寸<select onchange="processPRODSelectSize(this);">';

	var index = 0;
	for (var key in templateTpro) {
		var tproSizeArray =  key.split('_');
		var width = tproSizeArray[0];
		var height = tproSizeArray[1];
		if (key == '300_250') {
			tempHtml += '<option value="tpro_' + key + '" selected>' + width + ' x ' + height + '</option>';
		} else {
			tempHtml += '<option value="tpro_' + key + '">' + width + ' x ' + height + '</option>';
		}
		
		var sizeTpros = templateTpro[key].substring(0, templateTpro[key].length -1);
		// 建立樣板物件內容
		tproObject.data['tpro_' + key] = sizeTpros;
		// 建立行銷結尾圖物件內容
		index = index + 1;
		salesEndIframewp.data['index_' + index] = key;
	}
	tempHtml += '</select></div>';
	
	return tempHtml;
}

/**
 * 處理TMG圖文廣告尺寸下拉選單
 * @returns
 */
function processTMGSizeOptionHtml(adSeq) {
	var tempHtml = '';
	var href = location.href;
	if (href.indexOf('show.pchome') > -1 || href.indexOf('show1.pchome') > -1 || href.indexOf('show2.pchome') > -1) {
		tempHtml = '<div id="previewselector" class="previewselector">選擇廣告尺寸'
		  + '<select onchange="processTMGSelectSize(\'' + adSeq + '\', this);">'
		  + '<option value="c_x05_tp_tpro_0083">120 x 600</option>'
		  + '<option value="c_x05_tp_tpro_0055">140 x 300</option>'
		  + '<option value="c_x05_tp_tpro_0021">160 x 240</option>'
		  + '<option value="c_x05_tp_tpro_0084">160 x 600</option>'
		  + '<option value="c_x05_tp_tpro_0058">180 x 150</option>'
		  + '<option value="c_x05_tp_tpro_0019">250 x 80</option>'
		  + '<option value="c_x05_tp_tpro_0011">300 x 100</option>'
		  + '<option value="c_x05_tp_tpro_0001" selected>300 x 250</option>'
		  + '<option value="c_x05_tp_tpro_0079">300 x 600</option>'
		  + '<option value="c_x05_tp_tpro_0051">320 x 480</option>'
		  + '<option value="c_x05_tp_tpro_0052">336 x 280</option>'
		  + '<option value="c_x05_tp_tpro_0077">640 x 390</option>'
		  + '<option value="c_x05_tp_tpro_0018">728 x 90</option>'
		  + '<option value="c_x05_tp_tpro_0081">950 x 390</option>'
		  + '<option value="c_x05_tp_tpro_0053">970 x 250</option></select>';
	} else {
		tempHtml = '<div id="previewselector" class="previewselector">選擇廣告尺寸'
		  + '<select onchange="processTMGSelectSize(\'' + adSeq + '\', this);">'
		  + '<option value="c_x05_tp_tpro_0036">120 x 600</option>'
		  + '<option value="c_x05_tp_tpro_0065">140 x 300</option>'
		  + '<option value="c_x05_tp_tpro_0024">160 x 240</option>'
		  + '<option value="c_x05_tp_tpro_0039">160 x 600</option>'
		  + '<option value="c_x05_tp_tpro_0068">180 x 150</option>'
		  + '<option value="c_x05_tp_tpro_0021">250 x 80</option>'
		  + '<option value="c_x05_tp_tpro_0013">300 x 100</option>'
		  + '<option value="c_x05_tp_tpro_0001" selected>300 x 250</option>'
		  + '<option value="c_x05_tp_tpro_0095">300 x 600</option>'
		  + '<option value="c_x05_tp_tpro_0053">320 x 480</option>'
		  + '<option value="c_x05_tp_tpro_0054">336 x 280</option>'
		  + '<option value="c_x05_tp_tpro_0097">640 x 390</option>'
		  + '<option value="c_x05_tp_tpro_0019">728 x 90</option>'
		  + '<option value="c_x05_tp_tpro_0096">950 x 390</option>'
		  + '<option value="c_x05_tp_tpro_0055">970 x 250</option></select>';
	}
	return tempHtml;
}

/**
 * 組商品廣告iframeHtml
 * @param tempObj
 * @param tpro
 * @returns
 */
function processPRODIframeHtml(tempObj, tpro) {
	var tempHtml = '<div class="predivbox"><iframe width="' + $(tempObj).find('#width').val() + '" height="' + $(tempObj).find('#height').val() + '" class="akb_iframe" scrolling="no" frameborder="0" marginwidth="0" marginheight="0" vspace="0" hspace="0" id="pchome8044_ad_frame1" allowtransparency="true" allowfullscreen="true"'
			+ 'src="http://showstg.pchome.com.tw/pfp/adProdModel.html' + '?catalogGroupId=' + $(tempObj).find('#catalogGroupId').val()
			+ '&disTxtType=' + $(tempObj).find('#disTxtType').val()
			+ '&disBgColor=' + $(tempObj).find('#disBgColor').val()
			+ '&disFontColor=' + $(tempObj).find('#disFontColor').val()
			+ '&btnTxt=' + $(tempObj).find('#btnTxt').val()
			+ '&btnFontColor=' + $(tempObj).find('#btnFontColor').val()
			+ '&btnBgColor=' + $(tempObj).find('#btnBgColor').val()
			+ '&logoType=' + $(tempObj).find('#logoType').val()
			+ '&logoText=' + $(tempObj).find('#logoText').val()
			+ '&logoBgColor=' + $(tempObj).find('#logoBgColor').val()
			+ '&logoFontColor=' + $(tempObj).find('#logoFontColor').val()
			+ '&prodLogoType=' + $(tempObj).find('#prodLogoType').val()
			+ '&adbgType=' + $(tempObj).find('#adbgType').val()
			+ '&imgProportiona=' + $(tempObj).find('#imgProportiona').val()
			+ '&userLogoPath=' + $(tempObj).find('#userLogoPath').val()
			+ '&previewTpro=' + tpro // 不同樣板
			+ '&saleImg=' + $(tempObj).find('#saleImg').val()
			+ '&saleEndImg=' + $(tempObj).find('#saleEndImg').val()
			+ '&posterType=' + $(tempObj).find('#posterType').val()
			+ '&realUrl=' + $(tempObj).find('#realUrlEncode').val() + '"></iframe></div>';
	return tempHtml;
}

/**
 * 共用fancybox
 * @param tempHtmlViewArr
 * @param showLeftRightBtn
 * @param tempWidth
 * @param tempHeight
 * @param templateSizeOptionStr
 * @returns
 */
function commonFancyBox(tempHtmlViewArr, showLeftRightBtn, tempWidth, tempHeight, templateSizeOptionStr) {
	$.fancybox(
			tempHtmlViewArr
			, {
				'type': 'html',
//				'index': id,
				'fitToView': false,
				'autoScale': false,
				'autoDimensions': false,
				'modal': false,
				'hideOnOverlayClick': false,
				'showCloseButton': true,
				'width': 970,
				'height': 600,
				'autoSize': false,
				'autoHeight': false,
				'transitionIn': 'none',
				'transitionOut': 'none',
				'speedIn': 100,
				'speedOut': 100,
				'padding': 0,
				'overlayOpacity': .70,
				'overlayColor': '#000',
				'scrolling': 'no',
				'showNavArrows': showLeftRightBtn,
				'titleShow': true,
				'titlePosition': 'outside',
				'titleFormat': function (title, currentArray, currentIndex, currentOpts) {
					return '<div class="predivtitle">廣告尺寸：' + tempWidth + ' x ' + tempHeight + '</div>';
				},
				onStart: function () { // 將在嘗試加載內容之前調用
					$('#fancybox-outer').css('backgroundColor', '#000');
					if (templateSizeOptionStr) { // 組尺寸下拉選單
						$('#fancybox-outer').prepend(templateSizeOptionStr);
					}
				},
				onCleanup: function () { // 將在關閉前調用
					$('#fancybox-outer').css('backgroundColor', '#fff');
					$('#previewselector').remove();
				},
				onClosed: function () { // FancyBox關閉後會被調用
					$("#preDiv>div").empty();
				}
			}
		);
}

/**
 * TMG 圖文廣告 預覽畫面切換尺寸
 * @param adSeq
 * @param obj
 */
function processTMGSelectSize(adSeq, obj) {
	var selectName = $(obj).find("option:selected").text();
	var selectNameArr = selectName.split(" x ");
	var tempWidth = selectNameArr[0];
	var tempHeight = selectNameArr[1];
	$('.akb_iframe').attr("src", "http://showstg.pchome.com.tw/pfp/adModel.html?adNo=" + adSeq + "&tproNo=" + obj.value + "").attr("height", tempHeight).attr("width", tempWidth);
	$('.predivtitle').html('廣告尺寸：' + selectName);
}

/**
 * PROD商品廣告 預覽畫面切換尺寸
 * @param obj
 */
function processPRODSelectSize(obj) {
	var selectName = $(obj).find("option:selected").text();
	var selectNameArr = selectName.split(" x ");
	var tempWidth = selectNameArr[0];
	var tempHeight = selectNameArr[1];
	$(tempPRODObj).siblings(".hiddenVal").find('#width').val(tempWidth);
	$(tempPRODObj).siblings(".hiddenVal").find('#height').val(tempHeight);
	preView('PROD', tempPRODObj);
}

// 呼叫商品成效
function previewProdAdDetail(customerInfoId, adSeq, from) {
	var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();
	window.location = "reportProdPerformance.html?pfpCustomerInfoId=" + customerInfoId + "&adSeq=" + adSeq + "&startDate=" + startDate + "&endDate=" + endDate + "&breadcrumbsType=" + from;
}