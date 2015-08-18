$(document).ready(function(){

	var LinkUrl = false;
	var ShowUrl = false;

	// validate field
	$("#modifyForm").validate({
		rules: {
			adTitle: {
				required: true,
				maxlength: 17
			},
			adContent: {
				required: true,
				maxlength: 38
			},
			adLinkURL: {
				required: true,
				maxlength: 1024
			},
			adShowURL: {
				required: true,
				maxlength: 30
			}
		},
		messages: {
			adTitle: {
				required: "請填寫廣告標題.",
				maxlength: "廣告標題最多輸入17字"
			},
			adContent: {
				required: "請填寫廣告內容.",
				maxlength: "廣告內容最多輸入38字"
			},
			adLinkURL: {
				required: "請填寫廣告連結網址.",
				maxlength: "廣告連結網址最多輸入1024字"
			},
			adShowURL: {
				required: "請填寫廣告顯示網址.",
				maxlength: "廣告顯示網址最多輸入30字"
			}
		}
		,errorPlacement: function (error, element) {
			error.appendTo(element.parent("td"));
		}
	});

	$('#adLinkURL').blur(function() {
		if($("#adLinkURL").val() != "") {
			var adLinkURL = $("#adLinkURL").val();
			$.ajax({
				type: "POST",
				url: "checkAdUrl.html",
				data: { url: adLinkURL}
			}).done(function( msg ) {
				if(msg == "false") {
					$('#chkLinkURL').text("請輸入正確的廣告連結網址");
					LinkUrl = false;
				} else {
					$('#chkLinkURL').text("");
					LinkUrl = true;
				}
			});
		} else {
			$('#chkLinkURL').text("");
			LinkUrl = false;
		}
	});

	$('#adShowURL').blur(function() {
		if($("#adShowURL").val() != "") {
			var adLinkURL = $("#adShowURL").val();
			$.ajax({
				type: "POST",
				url: "checkAdUrl.html",
				data: { url: adLinkURL}
			}).done(function( msg ) {
				if(msg == "false") {
					$('#chkShowURL').text("請輸入正確的廣告顯示網址");
					ShowUrl = false;
				} else {
					$('#chkShowURL').text("");
					ShowUrl = true;
				}
			});
		} else {
			$('#chkShowURL').text("");
			ShowUrl = false;
		}
	});

	$('#sameRealUrl').click(function() {
		if($('#sameRealUrl').attr("checked")) {
			var adLinkURL = $("#adLinkURL").val();
			// 去掉網址的 http://
			if(adLinkURL.indexOf("http://") == 0) {
				adLinkURL = adLinkURL.replace("http://", "");
				// 去掉連結網址/後的所有字
				if(adLinkURL.indexOf("/") > 0) {
					adLinkURL = adLinkURL.substring(0, adLinkURL.indexOf("/"));
				}
			} else if(adLinkURL.indexOf("https://") == 0) {
				adLinkURL = adLinkURL.replace("https://", "");
				// 去掉連結網址/後的所有字
				if(adLinkURL.indexOf("/") > 0) {
					adLinkURL = adLinkURL.substring(0, adLinkURL.indexOf("/"));
				}
				adLinkURL = "https://" + adLinkURL;
			}
			
			$('#adShowURL').val(adLinkURL);
			if(adLinkURL == "") {
				$("#previewURL").text($('#adShowURL').attr("placeholder"));
			} else {
				$("#previewURL").text($('#adShowURL').val());
			}
		} else {
			$('#adShowURL').val("");
			$("#previewURL").text($('#adShowURL').attr("placeholder"));
		}
		ShowUrl = LinkUrl;

		var length = $('#adShowURL').val().length;
		$("#spanAdShowURL").text("已輸入" + length + "字，剩" + (30 - length) + "字");
		setData();
	});
	
	$('#adTitle').keyup(function() {
		if($('#adTitle').val() != "")		$("#previewTitle").text($('#adTitle').val());
		else								$("#previewTitle").text($('#adTitle').attr("placeholder"));

		var length = $('#adTitle').val().length;
		$("#spanAdTitle").text("已輸入" + length + "字，剩" + (17 - length) + "字");
		setData();
	});
	
	$('#adContent').keyup(function() {
		if($('#adContent').val() != "")		$("#previewContent").text($('#adContent').val());
		else								$("#previewContent").text($('#adContent').attr("placeholder"));

		var length = $('#adContent').val().length;
		$("#spanAdContent").text("已輸入" + length + "字，剩" + (38 - length) + "字");
		setData();
	});

	
	$('#adLinkURL').keyup(function() {
		var length = $('#adLinkURL').val().length;
		$("#spanAdLinkURL").text("已輸入" + length + "字，剩" + (1024 - length) + "字");
		setData();
	});

	$('#adShowURL').keyup(function() {
		if($('#adShowURL').val() != "")		$("#previewURL").text($('#adShowURL').val());
		else								$("#previewURL").text($('#adShowURL').attr("placeholder"));

		var length = $('#adShowURL').val().length;
		$("#spanAdShowURL").text("已輸入" + length + "字，剩" + (30 - length) + "字");
		setData();
	});

	$('#save').click(function(){
		saveData();
	});

	$('#saveNew').click(function(){
		$('#saveAndNew').val("save+new");
		saveData();
	});

	$('#back').click(function(){
		$(location).attr('href', "adGroupEdit.html?backPage=adAdAdd&adGroupSeq=" + $("#adGroupSeq").val());
	});

	$('#cancel').click(function(){
		$("#modifyForm")[0].reset();
		$(location).attr("href","adGroupView.html?adActionSeq=" + $("#adActionSeq").val() + "&adGroupSeq=" + $("#adGroupSeq").val());
	});

	function saveData() {
		//取得驗證回傳值
		if($("#modifyForm").valid() == 1 && document.getElementsByName("keywords").length > 0){
			if(LinkUrl && ShowUrl) {
				setData();
				alert("all ok");
				// form submit
				$("#modifyForm").submit();
			}
		} else {
			if(document.getElementsByName("keywords").length == 0) {
				$('#chkadKeyword').text("請輸入關鍵字");
			}
		}
	}

	function setData() {
		document.getElementsByName("adDetailContent")[0].value = $('#adTitle').val();
		document.getElementsByName("adDetailContent")[1].value = $('#adContent').val();
		document.getElementsByName("adDetailContent")[2].value = $('#adLinkURL').val();
		document.getElementsByName("adDetailContent")[3].value = $('#adShowURL').val();
	}
});



//選擇廣告樣式，並分別導入廣告樣式頁面
function setAdStyle(adStyle) {
	location.href = "adAdAdd.html?adActionSeq="+ $("#adActionSeq").val() + "&adGroupSeq=" + $("#adGroupSeq").val() + "&adStyle=" + adStyle;
}

//新增關鍵字
function addkeyword() {
	var adKeyword = document.getElementById("adKeyword").value;
	if(adKeyword != "") {
		addKeywords(adKeyword);
	}
}

//新增排除關鍵字
function addExcludeWord() {
	var ExcludeKeyword = document.getElementById("adExcludeKeyword").value;
	var liLen = document.getElementById("adExcludeKeyword").getElementsByTagName('li').length; 
	if(liLen >= 500) {
		alert("最多可輸入500個排除關鍵字");
	} else 	if(ExcludeKeyword != "") {
		var ul = document.getElementById("ExcludeKeywordUL");
		if(WordExist_KeywordUL(ExcludeKeyword) || WordExist_ExcludeKeywordUL(ExcludeKeyword) || WordExist_existKW(ExcludeKeyword)) {
			if(WordExist_existKW(ExcludeKeyword)) {
				alert("您無法排除已建立的關鍵字 - " + ExcludeKeyword);
			} else if(WordExist_KeywordUL(ExcludeKeyword)) {
				alert("您不能同時建立又排除相同的關鍵字 - " + ExcludeKeyword);
			} else {
				alert("您不能同時排除相同的關鍵字 - " + ExcludeKeyword);
			}
		} else {
			var newLI = document.createElement("LI");
			newLI.setAttribute("id", "ekw_" + ExcludeKeyword);
			var addElement = "<input type=\"hidden\" id=\"excludeKeywords\" name=\"excludeKeywords\" value=\""+ExcludeKeyword+"\"><img src=\"./html/img/deleicon.gif\" onclick=\"javascript:removeWord('ekw_"+ExcludeKeyword+"', 'ExcludeKeywordUL','bExcludeKW');\">" + ExcludeKeyword;
			ul.appendChild(newLI);
			newLI.innerHTML = addElement;
			document.getElementById("adExcludeKeyword").value = "";
			updStatus("ExcludeKeywordUL", "bExcludeKW");
		}
	}
}

//檢查關鍵字是否存在於本次新增的關鍵字中
function WordExist_KeywordUL(word) {
	var exist = false;
	var ul_1 = document.getElementById("KeywordUL"),
	    li_1 = ul_1.getElementsByTagName('li'),
	    len_1 = li_1.length;
	while (len_1--) {
		if(word == getText(li_1[len_1])) {
			exist = true;
			break;
		}
	}

	return exist;
}

//檢查關鍵字是否存在於排除關鍵字中
function WordExist_ExcludeKeywordUL(word) {
	var exist = false;
	var ul_2 = document.getElementById("ExcludeKeywordUL"),
	    li_2 = ul_2.getElementsByTagName('li'),
	    len_2 = li_2.length;
	while (len_2--) {
		if(word == getText(li_2[len_2])) {
			exist = true;
			break;
		}
	}

	return exist;
}

//檢查關鍵字是否存在於已建立的分類關鍵字中
function WordExist_existKW(word) {
	var exist = false;
	var sel_3 = document.getElementById("existKW"),
		len_3 = sel_3.length;
	while (len_3--) {
//		// 檢查 ftl 吃字，兩個空白會變一個空白
//		alert("word = '" + word + "'\nsel_3.options[len_3].text = '" + sel_3.options[len_3].text + "'\nsame=" + (word == sel_3.options[len_3].text));
//		var bytes = [];
//		for (var i = 0; i < word.length; ++i){
//			bytes.push(word.charCodeAt(i));
//		}
//
//		var bytes2 = [];
//		for (var i = 0; i < sel_3.options[len_3].text.length; ++i){
//			bytes2.push(sel_3.options[len_3].text.charCodeAt(i));
//		}
//		alert("word = " + bytes + "\nsel_3.options[len_3].text = " + bytes2 + "\nthe same = " + (bytes == bytes2));
//		// 檢查吃字，兩個空白會變一個空白

		if(word == sel_3.options[len_3].text) {
			exist = true;
			break;
		}
	}

	return exist;
}

// 讀取<li></li>裡面的文字
function getText(node) {
	var s = '';
	node = node.firstChild;
	if ( node ) do {
		if ( node.nodeType === 3 ) {
			s += node.data;
		}
		if ( node.nodeType === 1 ) {
			s += getText(node);
		}
	} while ( node = node.nextSibling );
	return s;
}

// 呼叫系統建議關鍵字
function suggestKW() {
	var word = $("#adKeyword").val();
	if($("#divSuggestWord").css("display") == "none") {
		$("#divSuggestWord").show();
	}
	document.getElementById("suggestWordUL").innerHTML = '';

	if(word != "") {
		$.ajax({
			type: "post",
			url: "getSuggestKW.html",
			data: { q: word }
		}).done(function( msg ) {
			alert(msg);
			var ul = document.getElementById("suggestWordUL");
			var suggestKeyword = msg.split(",");
			if(suggestKeyword.length > 0) {

			    var li_2 = ul_2.getElementsByTagName('li'),
			    	len_2 = li_2.length;
				while (len_2--) {
					if(word == getText(li_2[len_2])) {
						exist = true;
						break;
					}
				}

				//if()
				// 全選
				var newAddAll = document.createElement("LI");
				newAddAll.setAttribute("class", "btn_seleall");
				var addAllElement = "<img src=\"./html/img/btn_seleall.gif\" style=\"border:0; cursor:pointer\" onclick=\"javascript:selAllSugKW();\">";
				newAddAll.innerHTML = addAllElement;
				ul.appendChild(newAddAll);

				for(var i = 0; i < suggestKeyword.length; i++) {
					if(suggestKeyword[i] == "" || WordExist_KeywordUL(suggestKeyword[i]) || WordExist_ExcludeKeywordUL(suggestKeyword[i]) || WordExist_existKW(suggestKeyword[i])) {
						continue;
					} else {
						insSuggestKW(ul, suggestKeyword[i]);
//						var newLI = document.createElement("LI");
//						newLI.setAttribute("id", "skw_" + suggestKeyword[i]);
//						var addElement = "<input type=\"checkbox\" id=\"suggestKeywords\" name=\"suggestKeywords\" value=\""+suggestKeyword[i]+"\" onclick=\"javascript:addSuggestKeyword('"+suggestKeyword[i]+"', this.checked);\">" + suggestKeyword[i];
//						ul.appendChild(newLI);
//						newLI.innerHTML = addElement;
					}
				}
			}
		});
	}
}

function insSuggestKW(ul, suggestKeyword) {
	var newLI = document.createElement("LI");
	newLI.setAttribute("id", "skw_" + suggestKeyword);
	var addElement = "<input type=\"checkbox\" id=\"suggestKeywords\" name=\"suggestKeywords\" value=\""+suggestKeyword+"\" onclick=\"javascript:addSuggestKeyword('"+suggestKeyword+"', this.checked);\">" + suggestKeyword;
	ul.appendChild(newLI);
	newLI.innerHTML = addElement;
}

// 點 checkbox 新增系統建議關鍵字
function addSuggestKeyword(sugKeyword, checked) {
	if(checked && sugKeyword != "") {
		addKeywords(sugKeyword);
		removeWord("skw_" + sugKeyword, "suggestWordUL", "");
	} else if(!checked) {
		removeWord(sugKeyword, "KeywordUL", "bKW");
	}
}

//建立關鍵字
function addKeywords(Keyword) {
	var ul = document.getElementById("KeywordUL");
	var liLen = document.getElementById("KeywordUL").getElementsByTagName('li').length; 
	if(liLen >= 500) {
		alert("最多可輸入500個關鍵字");
	} else if(WordExist_KeywordUL(Keyword) || WordExist_ExcludeKeywordUL(Keyword) || WordExist_existKW(Keyword)) {
		if(WordExist_KeywordUL(Keyword)) {
			alert("您不能同時建立相同的關鍵字 - " + Keyword);
		} else if(WordExist_ExcludeKeywordUL(Keyword)) {
			alert("您不能同時建立又排除相同的關鍵字 - " + Keyword);
		} else {
			alert("您已經建立過關鍵字 - " + Keyword);
		}
	} else {
		var newLI = document.createElement("LI");
		newLI.setAttribute("id", Keyword);
		var addElement = "<input type=\"hidden\" id=\"keywords\" name=\"keywords\" value=\""+Keyword+"\"><img id=\"btnRemoveKW\" src=\"./html/img/deleicon.gif\" onclick=\"javascript:removeWord('"+Keyword+"', 'KeywordUL', 'bKW');\">" + Keyword;
		ul.appendChild(newLI);
		newLI.innerHTML = addElement;
		updStatus("KeywordUL", "bKW");
		$('#chkadKeyword').text("");
	}
}

// 移除關鍵字
function removeWord(elementId, ulId, bTag) {
	var elem = document.getElementById(elementId);
	elem.parentNode.removeChild(elem);
	if(bTag != null && bTag != "") {
		insSuggestKW(document.getElementById("suggestWordUL"), elementId);
		updStatus(ulId, bTag);
	}
}

// 新增全部系統關鍵字
function selAllSugKW() {
	var ul_1 = document.getElementById("suggestWordUL"),
	li_1 = ul_1.getElementsByTagName('li'),
	len_1 = li_1.length;
	i = 1;
	do{
		addKeywords(getText(li_1[i]));
		document.getElementsByName("suggestKeywords")[i-1].checked = true;
		i++;
		
	} while (i < len_1);
}

// 更新關鍵字狀態
function updStatus(UL, bTagElem) {
	var liLen = document.getElementById(UL).getElementsByTagName('li').length; 
	var bTag = document.getElementById(bTagElem);
	bTag.innerHTML = "已新增 " +liLen+ " 個，還可輸入 <span class=\"t_s01\">"+(500-liLen)+"</span> 個";
}
