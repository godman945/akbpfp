$(document).ready(function(){
	//新增關鍵字用參數
	var existKeywords1 = "";
	var existKeywords2 = "";
	var existExcludeKeywords1 = "";
	var existExcludeKeywords2 = "";

	// 關鍵字列表id 用
	var kwids = 0;
	var ekwids = 0;

	// 初始更新關鍵字數量
	updStatus("KeywordUL", "bKW");

	// 輸入關鍵字的動作
	$("#adKeyword").keypress(function(event){
		// 為了因應 FF 沒有 "event.keyCode" 的行為，改為 "event.which"
		// 另外，IE 沒有 "event.which"
		var keyID = (event.charCode) ? event.charCode : ((event.which) ? event.which : event.keyCode);
		if(keyID == 13)		addkeyword();
		else				$('#chkAdKeyword').text("");
	});

	//輸入排除關鍵字的動作
	$("#adExcludeKeyword").keypress(function(event){
		// 為了因應 FF 沒有 "event.keyCode" 的行為，改為 "event.which"
		// 另外，IE 沒有 "event.which"
		var keyID = (event.charCode) ? event.charCode : ((event.which) ? event.which : event.keyCode);
		if(keyID == 13)		addExcludeWord();
		else				$('#chkAdExcludeKeyword').text("");
	});
	
	$("#addAdKeyword").click(function(){
		addkeyword();
	});
	
	$("#addAdExcludeKeyword").click(function(){
		addExcludeWord();
	});

	$("#sugkw").click(function(){
		suggestKW();
	});
	
	$("#btnAddBatch").click(function(){
		batchKW();
	});

	

	// 當元件是網頁 load 完之後，網頁動作產生時，要用 on 的方式，才會有作用
	$(document).on("click", "#KeywordUL li img", function(){
		$(this).parent().remove();
		updStatus("KeywordUL", "bKW");
	}).on("click", "#ExcludeKeywordUL li img", function(){
		$(this).parent().remove();
		updStatus("ExcludeKeywordUL", "bExcludeKW");
	}).on("click", "#btn_seleall", function() {
		selAllSugKW();
	}).on("click", "#suggestWordUL li input[type=checkbox]", function() {
		addSuggestKeyword($(this), $(this).prop("checked"));
		// 如果有重複的關鍵字，提醒使用者
		alertExist();
	});

	function chkKeyword(str) {
		var kw = "";
		var regx2 = /^[\-\. &]*$/;
		var wordLocStart = -1;
		var lastWord = "";
		for(var i = 0; i < str.length; i++) {
			var word = (i == str.length - 1)?str.substring(i):str.substring(i, i + 1);
			// 英文、數字、中文及不為 " " , "." , "-" , "&" 四種符號的字元 
			if ( !regx2.test( word )){
				// 將字元串起來
				kw += word;
				// 設定起始位置
				if(wordLocStart == -1)		wordLocStart = i;
				// 設定最後一個字
				lastWord = word;
			} else {	// " " , "." , "-" , "&" 四種符號的字元
				// 字的初始位置後的四個符號才會寫入字串中
				if(i > 0 && wordLocStart > -1) {
					var prevWord = str.substring(i - 1, i);
					if(word != prevWord) {
						kw += word;
					}
				}
			}
		}
		// 回傳最後一個字以前的字串
		return kw.substring(0,kw.lastIndexOf(lastWord) + 1);
	}

	// 檢查字串是否為中文、英文、數字所組成
	function isAllowKW(str) {
		var regx = /^[a-zA-Z0-9\-\. &\u4e00-\u9fa5]*$/;
		if ( regx.test( str )){
			return true;
		} else {
			return false;
		}
	}

	// 新增關鍵字
	function addkeyword() {
		//var adKeyword = chkSpace($("#adKeyword").val());
		//adKeyword = chkDash(adKeyword);
		var adKeyword = chkKeyword($("#adKeyword").val());
		//alert("kwyword = '" + adKeyword + "'\nisAllowKW(adKeyword) = " + isAllowKW(adKeyword));
		if(adKeyword != "" && isAllowKW(adKeyword) && adKeyword != $("#adKeyword").data("value")) {
			addKeywords(adKeyword);
			$('#adKeyword').val("");
			$('#chkAdKeyword').css("color","red");
			$('#chkAdKeyword').text("");
			// 如果有重複的關鍵字，提醒使用者
			alertExist();
		} else if(!isAllowKW(adKeyword)){
			$('#adKeyword').val("");
			$('#chkAdKeyword').css("color","red");
			$('#chkAdKeyword').text("關鍵字僅限中文、英文與數字，請重新確認您的關鍵字");
		} else {
			$('#chkAdKeyword').css("color","red");
			$('#chkAdKeyword').text("請輸入關鍵字");
		}
	}

	//建立關鍵字
	function addKeywords(Keyword) {
		var showWord = getAllExistWord();
		if($("#KeywordUL li").length >= 500) {
			alert("最多可輸入500個關鍵字");
		} else if($.inArray(Keyword, showWord) >= 0) {
			if(WordExist_KeywordUL(Keyword)) {
				existKeywords1 += (existKeywords1 == "")?Keyword : "," + Keyword;
			} else if(WordExist_existKW(Keyword)) {
				existKeywords1 += (existKeywords1 == "")?Keyword : "," + Keyword;
			} else if(WordExist_ExcludeKeywordUL(Keyword)) {
				existKeywords2 += (existKeywords2 == "")?Keyword : "," + Keyword;
			} else if(WordExist_existExcludeKW(Keyword)) {
				existKeywords2 += (existKeywords2 == "")?Keyword : "," + Keyword;
			} else {
				existKeywords1 += (existKeywords1 == "")?Keyword : "," + Keyword;
			}
		} else {
			kwids++;
			$("#KeywordUL").append("<li><input type=\"hidden\" class=\"classKW\" id=\"keywords_"+kwids+"\" name=\"keywords\" value=\""+Keyword+"\"><img src=\"./html/img/deleicon.gif\">" + Keyword + "</li>");
			updStatus("KeywordUL", "bKW");
		}
	}

	//新增排除關鍵字
	function addExcludeWord() {
		//var ExcludeKeyword = chkSpace($("#adExcludeKeyword").val());
		//ExcludeKeyword = chkDash(ExcludeKeyword);
		var ExcludeKeyword = chkKeyword($("#adExcludeKeyword").val());
		if(ExcludeKeyword != "" && isAllowKW(ExcludeKeyword) && ExcludeKeyword != $("#adExcludeKeyword").data("value")) {
			addExcludeWords(ExcludeKeyword, "removeWord");
			$('#adExcludeKeyword').val("");
			$('#chkAdExcludeKeyword').css("color","red");
			$('#chkAdExcludeKeyword').text("");
			// 如果有重複的關鍵字，提醒使用者
			alertExist();
		} else if(!isAllowKW(ExcludeKeyword)){
			$('#adExcludeKeyword').val("");
			$('#chkAdExcludeKeyword').css("color","red");
			$('#chkAdExcludeKeyword').text("排除關鍵字僅限中文、英文與數字，請重新確認您的關鍵字");
		} else {
			$('#chkAdExcludeKeyword').css("color","red");
			$('#chkAdExcludeKeyword').text("請輸入排除關鍵字");
		}
	}

	//新增排除關鍵字
	function addExcludeWords(ExcludeKeyword, js) {
		if($("#ExcludeKeywordUL li").length >= 500) {
			alert("最多可輸入500個排除關鍵字");
		} else 	if(ExcludeKeyword != "") {
			var showWord = getAllExistWord();
			if($.inArray(ExcludeKeyword, showWord) >= 0) {
				if(WordExist_KeywordUL(ExcludeKeyword)) {
					existExcludeKeywords1 += (existExcludeKeywords1 == "")?ExcludeKeyword : "," + ExcludeKeyword;
				} else if(WordExist_existKW(ExcludeKeyword)) {
					existExcludeKeywords1 += (existExcludeKeywords1 == "")?ExcludeKeyword : "," + ExcludeKeyword;
				} else if(WordExist_ExcludeKeywordUL(ExcludeKeyword)) {
					existExcludeKeywords2 += (existExcludeKeywords2 == "")?ExcludeKeyword : "," + ExcludeKeyword;
				} else if(WordExist_existExcludeKW(ExcludeKeyword)) {
					existExcludeKeywords2 += (existExcludeKeywords2 == "")?ExcludeKeyword : "," + ExcludeKeyword;
				} else {
					existExcludeKeywords1 += (existExcludeKeywords1 == "")?ExcludeKeyword : "," + ExcludeKeyword;
				}
			} else {
				ekwids++;
				$("#ExcludeKeywordUL").append("<li><input type=\"hidden\" class=\"classEKW\" id=\"excludeKeywords_"+kwids+"\" name=\"excludeKeywords\" value=\""+ExcludeKeyword+"\"><img src=\"./html/img/deleicon.gif\">" + ExcludeKeyword + "</li>");
				updStatus("ExcludeKeywordUL", "bExcludeKW");
			}
		}
	}

	// 檢查關鍵字是否存在於本次新增的關鍵字中
	function WordExist_KeywordUL(word) {
		var exist = false;
		$('#KeywordUL li .classKW').each( function() { 
			if(word == $(this).val()) {
				exist = true;
				return false;
			}
		}); 
		return exist;
	}

	// 檢查關鍵字是否存在於本次新增的排除關鍵字中
	function WordExist_ExcludeKeywordUL(word) {
		var exist = false;
		$('#ExcludeKeywordUL li .classEKW').each( function() {
			if(word == $(this).val()) {
				exist = true;
				return false;
			}
		});
		return exist;
	}

	// 檢查關鍵字是否存在於已建立的分類關鍵字中
	function WordExist_existKW(word) {
		var exist = false;
		$('#existKW option').each(function(){
			if(word == $(this).text()) {
				exist = true;
				return false;
			}
		});

		return exist;
	}

	//檢查關鍵字是否存在於已建立的分類關鍵字中
	function WordExist_existExcludeKW(word) {
		var exist = false;
		$('#existExcludeKW option').each(function(){
			if(word == $(this).text()) {
				exist = true;
				return false;
			}
		});

		return exist;
	}

	// 呼叫系統建議關鍵字
	function suggestKW() {
		var word = $("#adKeyword").val();
		// 過濾輸入字串全部為空白的內容
		// word == "請輸入關鍵字" =>  這個部分是為了IE
		if(word == "" || word == "請輸入關鍵字" || word.replace(/\s/g, "") == "") {
			$("#divSuggestWord").hide();
			$('#chkAdKeyword').css("color","red");
			$('#chkAdKeyword').text("請輸入關鍵字");
//			if(word != "") {
//				$("#adKeyword").val("");
//			}
		} else {
			$.blockUI.defaults.applyPlatformOpacityRules = false;
			$.blockUI({
			    message: "<h1>查詢系統建議關鍵字中，請稍後...</h1>",
			    css: {
		            width: '500px',
		            height: '65px',
		            opacity: .9,
		            border:         '3px solid #aaa',
		            backgroundColor:'#fff',
		            textAlign:      'center',
		            cursor:         'wait',
		            '-webkit-border-radius': '10px',
		            '-moz-border-radius':    '10px'
		        },
		        fadeIn: 1000, 
		        timeout:   200, 
		        onBlock: function() { 
					// 清空舊的系統建議關鍵字
					$("#suggestWordUL").empty();
			
					if(word != "" && word.replace(/\s/g, "") != "") {
						$.ajax({
							type: "get",
							url: "getSuggestKW.html",
							data: { q: word }
						}).done(function( msg ) {
							
							alert(msg);
							
							var suggestKeyword = msg.split(",");
							var suggestKeywordsLen = 0;
							if(msg != "" && suggestKeyword.length > 0) {
								if(suggestKeyword[0] != "") {
									$("#suggestWordUL").append("<li id=\"btn_seleall\" class=\"btn_seleall\"><img src=\"./html/img/btn_seleall.gif\" style=\"border:0; cursor:pointer\"></li>");
									if($("#divSuggestWord").css("display") == "none") {
										$("#divSuggestWord").show();
									}
									for(var i = 0; i < suggestKeyword.length; i++) {
										if(suggestKeyword[i] == ""
										|| WordExist_KeywordUL(suggestKeyword[i])
										|| WordExist_ExcludeKeywordUL(suggestKeyword[i])
										|| WordExist_existKW(suggestKeyword[i])
										|| WordExist_existExcludeKW(suggestKeyword[i])) {
											continue;
										} else {
											insSuggestKW($("#suggestWordUL"), i, suggestKeyword[i]);
											suggestKeywordsLen++;
										}
									}
									
									if(suggestKeywordsLen == 0) {
										$("#suggestWordUL").empty();
										$("#divSuggestWord").hide();
										$('#chkAdKeyword').css("color","red");
										$('#chkAdKeyword').text("沒有新建議的關鍵字");
									}
								}
							} else {
								$("#divSuggestWord").hide();
								$('#chkAdKeyword').css("color","red");
								$('#chkAdKeyword').text("沒有建議的關鍵字");
							}
						});
					}
		        }
			});
			$('#chkAdKeyword').css("color","red");
			$('#chkAdKeyword').text("");
		}
	}

	// 新增一組關鍵字
	function insSuggestKW(ul, no, suggestKeyword) {
		ul.append("<li><input type=\"checkbox\" class=\"classChkbox\" id=\"suggestKeywords"+no+"\" name=\"suggestKeywords\" value=\""+suggestKeyword+"\">" + suggestKeyword + "</li>");
	}

	// 點 checkbox 新增系統建議關鍵字
	function addSuggestKeyword(sugKeyword, checked) {
		if(checked && sugKeyword != "") {
			addKeywords(sugKeyword.val());
			removeSuggestKW(sugKeyword, "suggestWordUL");
			// 如果有重複的關鍵字，提醒使用者
			alertExist();
		}
	}

	// 移除系統建議關鍵字
	function removeSuggestKW(elem, ulId, bTag) {
		elem.parent().remove();
	}

	// 新增全部系統關鍵字
	function selAllSugKW() {
		$.blockUI.defaults.applyPlatformOpacityRules = false;
		$.blockUI({
		    message: "<h1>新增系統建議關鍵字中，請稍後...</h1>",
		    css: {
	            width: '500px',
	            height: '65px',
	            opacity: .9,
	            border:         '3px solid #aaa',
	            backgroundColor:'#fff',
	            textAlign:      'center',
	            cursor:         'wait',
	            '-webkit-border-radius': '10px',
	            '-moz-border-radius':    '10px'
	        },
	        fadeIn: 1000, 
	        timeout:   200, 
	        onBlock: function() { 
				$('#suggestWordUL li .classChkbox').each( function() { 
					if($("#KeywordUL li").length >= 500) {
						alert("最多可輸入500個關鍵字");
						return false;
					} else {
						addSuggestKeyword($(this), true);
					}
				});

				// 如果有重複的關鍵字，提醒使用者
				alertExist();

				// 判斷建議系統關鍵字是否還有未選的選項，沒有才會把顯示框關掉
				if($("#suggestWordUL li .classChkbox").length <= 1) {
					$("#suggestWordUL").empty();
					if($("#divSuggestWord").css("display") == "block") {
						$("#divSuggestWord").hide();
					}
				}
	        } 
		});
	}

	// 批次新增關鍵字
	function batchKW() {
		$.blockUI.defaults.applyPlatformOpacityRules = false;
		$.blockUI({
		    message: "<h1>批次新增關鍵字處理中，請稍後...</h1>",
		    css: {
	            width: '500px',
	            height: '65px',
	            opacity: .9,
	            border:         '3px solid #aaa',
	            backgroundColor:'#fff',
	            textAlign:      'center',
	            cursor:         'wait',
	            '-webkit-border-radius': '10px',
	            '-moz-border-radius':    '10px'
	        },
	        fadeIn: 1000, 
	        timeout:   200, 
	        onBlock: function() {
	    		var showWord = getAllExistWord();
	    		var sWord_org = $("#batchkeywords").val();
	    		var sWord_tmp = sWord_org.replace(/\n/g, ",");
	    		for(var i = 0; i < 10;i++) {
	    			sWord_tmp = sWord_tmp.replace(/,,/g, ",");
	    			if(sWord_tmp.indexOf(",,") < 0) {
	    				break;
	    			}
	    		}
	    		var aWord = sWord_tmp.split(",");
	    		var tmp = "";
	    		for(var iiii = 0; iiii < aWord.length; iiii++) {
	    			tmp += aWord[iiii] + "\n";
	    		}
	    		if(aWord.length > 0 && sWord_tmp != ",") {
	    			var noAllow = "";
	    			for(var key in aWord){
	    				//var adKeyword = chkSpace(aWord[key]);
	    				//adKeyword = chkDash(adKeyword);
	    				var adKeyword = chkKeyword(aWord[key]);
	    				if(adKeyword != "" && adKeyword.length <= 50 && isAllowKW(adKeyword) ) {
		    				if($.inArray(adKeyword, showWord) < 0) {
		    					if($("#KeywordUL li").length >= 500) {
		    						alert("最多可輸入500個關鍵字");
		    						return;
		    					} else {
		    						addKeywordUL(adKeyword);
		    						showWord.push(adKeyword);
		    						//kwids++;
		    						//$("#KeywordUL").append("<li><input type=\"hidden\" class=\"classKW\" id=\"keywords_"+kwids+"\" name=\"keywords\" value=\""+adKeyword+"\"><img src=\"./html/img/deleicon.gif\">" + adKeyword + "</li>");
		    						//updStatus("KeywordUL", "bKW");
		    					}
		    				} else {
		    					if(WordExist_KeywordUL(adKeyword)) {
		    						existExcludeKeywords1 += (existExcludeKeywords1 == "")?adKeyword : "," + adKeyword;
		    					} else if(WordExist_existKW(adKeyword)) {
		    						existExcludeKeywords1 += (existExcludeKeywords1 == "")?adKeyword : "," + adKeyword;
		    					} else if(WordExist_ExcludeKeywordUL(adKeyword)) {
		    						existExcludeKeywords2 += (existExcludeKeywords2 == "")?adKeyword : "," + adKeyword;
		    					} else if(WordExist_existExcludeKW(adKeyword)) {
		    						existExcludeKeywords2 += (existExcludeKeywords2 == "")?adKeyword : "," + adKeyword;
		    					}
		    				}
	    				} else if(adKeyword.length > 50) {
    						alert("'" + adKeyword + "'\n以上關鍵字字數上限為50個字，請重新修改");
    						return;
	    				} else if(!isAllowKW(adKeyword) || adKeyword == "") {
	    					noAllow += (noAllow != "")?"," + noAllow: noAllow;
	    				}
		    			$("#batchkeywords").val($("#batchkeywords").val().substring(adKeyword.length + 1));
	    			}
	    			if(noAllow != "") {
						alert("'" + noAllow + "'\n關鍵字僅限中文、英文與數字，請重新確認您的關鍵字");
						return;
	    			}
	    			$("#batchkeywords").val("");
	    			$("#divBatchWord").hide();
	    			// 如果有重複的關鍵字，提醒使用者
    				alertExist();
	    		} else {
	    			alert("請輸入正確大量新增關鍵字格式，以逗號\",\"或是換行分隔關鍵字");
	    		}
	        } 
		});
	}

	// 讀取頁面上所有關鍵字、排除關鍵字
	function getAllExistWord() {
		var showWord = new Array();
		$('#KeywordUL li .classKW').each( function() {
			showWord.push($(this).val());
		}); 

		$('#ExcludeKeywordUL li .classEKW').each( function() {
			showWord.push($(this).val());
		});

		$('#existKW option').each(function(){
			showWord.push($(this).text());
		});

		$('#existExcludeKW option').each(function(){
			showWord.push($(this).text());
		});
		return showWord;
	}
	
	function addKeywordUL(Keyword) {
		kwids++;
		$("#KeywordUL").append("<li><input type=\"hidden\" class=\"classKW\" id=\"keywords_"+kwids+"\" name=\"keywords\" value=\""+Keyword+"\"><img src=\"./html/img/deleicon.gif\">" + Keyword + "</li>");
		updStatus("KeywordUL", "bKW");
	}

	// 顯示已重複新增的關鍵字
	function alertExist() {
		var msg = "";
		if(existKeywords1 != "") {
			msg += "分類中已建立此關鍵字，故未新增以下關鍵字：" + existKeywords1 + "\n";
		} else if(existExcludeKeywords1 != "") {
			msg += "分類中已建立此關鍵字，故未新增以下排除關鍵字：" + existExcludeKeywords1;
		}
		if(existKeywords2 != "") {
			msg += "分類中已建立此排除關鍵字，故未新增以下關鍵字：" + existKeywords2 + "\n";
		} else if(existExcludeKeywords2 != "") {
			msg += "分類中已建立此排除關鍵字，故未新增以下排除關鍵字：" + existExcludeKeywords2;
		}
		if(msg != "")		alert(msg);
		existKeywords1 = "";
		existKeywords2 = "";
		existExcludeKeywords1 = "";
		existExcludeKeywords2 = "";
	}

	// 更新關鍵字狀態
	function updStatus(UL, bTagElem) {
		var liLen = $("#" + UL + " li").length;
		$("#" + bTagElem).html("已新增 " +liLen+ " 個，還可輸入 <span class=\"t_s01\">"+(500-liLen)+"</span> 個");
	}
});

// 顯示批次新增關鍵字
function setBatch() {
	if($("#divBatchWord").css("display") == "none") {
		$("#divBatchWord").show();
	} else {
		$("#divBatchWord").hide();
	}
}
