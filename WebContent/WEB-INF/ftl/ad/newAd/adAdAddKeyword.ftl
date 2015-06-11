<#assign s=JspTaglibs["/struts-tags"]>

		<!-- adKeyword start -->
		<script language="JavaScript" src="<@s.url value="/" />html/js/ad/adKeywordAction.js" ></script>
		<!-- adKeyword end -->
		<!-- IE 沒有 placeholder 效果，用此 code 模擬 placeholder(Jack指導版) --> 
		<!--[if IE]>
		<script language="JavaScript" src="<@s.url value="/" />html/js/ad/simuPlaceholderKeyword.js" ></script>
		<![endif]-->
		<!-- IE 沒有 placeholder 效果，用此 code 模擬 placeholder(Jack指導版) --> 
		<div style="clear:both;height:10px"></div>
		<div class="grtba">
			<h4>建立關鍵字</h4>
			<table width="100%" cellspacing="1" cellpadding="0" border="0" class="tb02">
				<tbody>
					<tr>
						<th>
							<a name="errAdKeyword"></a><span class="t_s02">* </span>輸入關鍵字<br>
							<span class="t_s01">系統比對到您設定的關鍵字，您的廣告就有機會曝光</span>
						</th>
						<td>
							<input type="text" class="inputPlaceholderKeyword" data-value="請輸入關鍵字" id="adKeyword" name="adKeyword" maxlength="50" placeholder="請輸入關鍵字">
							<input type="button" id="addAdKeyword" name="addAdKeyword" value="確認">
							<input type="button" id="sugkw" name="sugkw" value="系統建議關鍵字">
							&nbsp;&nbsp;<span id="chkAdKeyword" name="chkAdKeyword" style="color:red"></span><br>
							<span class="t_s03">輸入後 按enter 或 確認鍵 來新增關鍵字；每個關鍵字限50個字，最多可設定500個關鍵字！</span><br>
							<a href="javascript:setBatch()">大量新增關鍵字</a><br>
							<!-- 批次新增關鍵字START -->
							<div id="divBatchWord" style="${divBatchWord!}">
								<textarea id="batchkeywords" name="batchkeywords" rows="10" cols="50">${batchkeywords!}</textarea>
								<input type="button" id="btnAddBatch" name="btnAddBatch" value="批次加入"><BR>
								<span class="t_s03">大量新增關鍵字請以小寫逗號(,)或換行，來區隔每組關鍵字。</span><br>
							</div>
							<!-- 批次新增關鍵字end -->
							<!-- 建議START -->
							<div id="divSuggestWord" class="sugestword" style="display:none;">
								<ul id="suggestWordUL">
								</ul>
							</div>
							<!-- 建議end -->
							<!-- 關鍵字START -->
							<div id="divKeyword" class="setword" style="display:block;">
								<h5>本次新增的關鍵字</h5><b id="bKW">已新增 0 個，還可輸入 <span class="t_s01">500</span> 個</b>
								<ul id="KeywordUL">
								<#if likw?exists>
								    <#list likw as kwvo>
								    <li><input type="hidden" class="classKW" id="${kwvo.kw_id!}" name="${kwvo.kw_name!}" value="${kwvo.kw_value!}"><img src="./html/img/deleicon.gif">${kwvo.kw_value!}</li>
								    </#list>
								</#if>
								</ul>
							</div>
							<!-- 關鍵字end -->
						</td>
					</tr>
				</tbody>
			</table>
			<div style="clear:both;height:10px"></div>
			<h4>設定排除關鍵字</h4>
			<table width="100%" cellspacing="1" cellpadding="0" border="0" class="tb02">
				<tbody>
					<tr>
						<th>
							輸入排除關鍵字<br>
							<span class="t_s01">系統比對到這些排除的關鍵字時，不會播出您的廣告</span>
						</th>
						<td>
							<input type="text" class="inputPlaceholderKeyword" data-value="請輸入排除關鍵字" id="adExcludeKeyword" name="adExcludeKeyword" maxlength="50" placeholder="請輸入排除關鍵字">
							<input type="button" id="addAdExcludeKeyword" name="addAdExcludeKeyword" value="確認">
							<span id="chkAdExcludeKeyword" name="chkAdExcludeKeyword" style="color:red"></span>
							<br>
							<span class="t_s03">輸入後 按enter 或 確認鍵 來新增要排除的關鍵字；每個要排除的關鍵字限50個字，最多可設定500個要排除的關鍵字！</span>
							<!--關鍵字START-->
							<div class="setword" style="display:block;">
								<b id="bExcludeKW">已新增 0 個，還可輸入 <span class="t_s01">500</span> 個</b>
								<ul id="ExcludeKeywordUL">
								</ul>
							</div>
							<!--關鍵字end-->
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div style="clear:both;height:10px"></div>
		<div class="grtba">
			<h4>已建立的分類關鍵字</h4>
			<table width="100%" cellspacing="1" cellpadding="0" border="0" class="tb02">
				<tbody>
					<tr>
						<th>已建立的分類關鍵字</th>
						<td>
							<select id="existKW" name="existKW" size="10" style="width:200px">
							<#if pfpAdKeywords?exists>
							    <#list pfpAdKeywords as PfpAdKeyword>
							    <option value="${PfpAdKeyword.adKeywordSeq!}">${PfpAdKeyword.adKeyword!}</option>
							    </#list>
							</#if>
							</select>
						</td>
						<th>已建立的分類排除關鍵字</th>
						<td>
							<select id="existExcludeKW" name="existExcludeKW" size="10" style="width:200px">
							<#if pfpAdExcludeKeywords?exists>
							    <#list pfpAdExcludeKeywords as PfpAdExcludeKeyword>
							    <option value="${PfpAdExcludeKeyword.adExcludeKeywordSeq!}">${PfpAdExcludeKeyword.adExcludeKeyword!}</option>
							    </#list>
							</#if>
							</select>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
