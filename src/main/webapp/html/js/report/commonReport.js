$(document).ready(function () {

	horisontal_scroll_listing($("#table-listing"));
	
});


/**
 * 調整查詢結果列表畫面(art提供)
 * @param listing_obj
 * @returns
 */
function horisontal_scroll_listing(listing_obj) {
	if ($("#totalPage").val() == 0) { // 查無資料則總頁數為0，查無資料不調整畫面
		return false;
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
				if (url.indexOf('reportKeyword.html') == -1) { // 非關鍵字成效報表，才做高度css處理
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

/**
 * 處理頁碼按鈕事件
 */
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