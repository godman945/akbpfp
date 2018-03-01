<#assign s=JspTaglibs["/struts-tags"]>
<#assign t=JspTaglibs["http://tiles.apache.org/tags-tiles"]>

<div class="queryResult" id="tableList">
	<div>
		<span class="apply-element-txt">已選擇: <b class="checkboxCount-up">0</b> 筆商品物件刊登</span>
	
		<span class="pages">
		    <img style="vertical-align:middle" id="fpage" src="<@s.url value="/html/img/"/>page_first.gif" />
			<img style="vertical-align:middle" id="ppage" src="<@s.url value="/html/img/"/>page_pre.gif" />
			${page}/${totalPage}
			<img style="vertical-align:middle" id="npage" src="<@s.url value="/html/img/"/>page_next.gif" />
			<img style="vertical-align:middle" id="epage" src="<@s.url value="/html/img/"/>page_end.gif" />
			
			&nbsp&nbsp
			
			顯示 
			<select id="pageSizeSelect" style="vertical-align:middle" > 
			    <#list pageSizeList as ps>
			        <option>${ps}</option>
			    </#list>
			</select> 
			行
		</span>			
	</div>

	<div style="clear:both;height:50%"></div>

	<table id="tableView" class="tableManager" width="100%" border="0" cellpadding="0" cellspacing="1" role="grid">
		<thead>
			<tr role="row">
				<th width="50" data-column="0" class="" scope="col" role="columnheader" aria-disabled="true" unselectable="on" aria-sort="none" aria-label="全選: No sort applied, sorting is disabled" style="user-select: none;">
					<div class="tablesorter-header-inner">
						<a href="#" onclick="checkAll()">全選</a>
					</div>
				</th>
				<th>廣告明細預覽</th>
				<th style="width: 36%; user-select: none;" aria-label="連結網址"><div>連結網址</div></th>
				<th style="width: 9%; user-select: none;" aria-label="原價"><div>原價</div></th>
				<th style="width: 9%; user-select: none;" aria-label="促銷價"><div>促銷價</div></th>
			</tr>
		</thead>
		
		<tbody aria-live="polite" aria-relevant="all">
			<tr role="row">
				<td>
					<input type="checkbox" id="chkN_0" name="chkN" disabled="">
				</td>
		        <td height="35" class="td02">
					<div class="ad-mod" id="">
					 	<#-- 預覽廣告模式 start-->
					 	<div class="mod_edit">
					 		<input class="mod-button btn_edit" type="button" id="" value="修 改">
					        <#-- iframe廣告 start-->
							<div style="min-width: 400px;width:337px; height:85px; border:0px rgb(205,205,205) solid; padding:15px 5px 15px 5px; font-family:微軟正黑體,Arial; position:relative; ">
								<#--LOGO-->
								<div id="logooff" style="position:absolute;top:0; left:0;width:20px; height:18px; line-height:18px; background:rgb(175,175,175); cursor:pointer;" onmouseover="doOver()"><img src="https://kdpic.pchome.com.tw/img/public/adlogo_off.png" width="20" height="18" border="0"></div>
								<#--LOGO-->
								<div id="logoshow" style="display:none;position:absolute;top:0; left:0; height:18px; line-height:18px; background:rgb(175,175,175); cursor:pointer;" onmouseout="doOut()"><a href="https://show.pchome.com.tw" style="text-decoration:none" target="_new"><span style="font-size:12px;color:#FFF;text-shadow:-1px -1px rgb(152,152,152); padding-left:52px; background:url(https://kdpic.pchome.com.tw/img/public/adlogo_on.png) no-repeat;">提供的廣告</span></a></div>
								<#--廣告內容-->
								<#-- real url -->
								<a target="_blank" href="http://kaza.pchome.com.tw/event/rainbow/index.html" style="text-decoration:none">
									<div style="width:315px; height:85px; float:left;text-align:left; margin-left:12px;"><#-- img src --><img src="http://show.pchome.com.tw/img/ad_201310300003.jpg?time=20180201123133" style=" width:85px; height:85px; float:left; margin-right:5px; border:0">
										<div>
											<h3 style=" font-size:17px; font-weight:600; line-height:20px; margin: 0; padding: 0; color:rgb(0,69,178);word-break:break-all;display:inline-block;word-wrap: break-word;width:225px;"><#-- title -->沒有朋友人生是黑白的？快來KAZA</h3>
									    	<p style=" font-size:12px; color:rgb(102,102,102); line-height:15px;margin: 0; padding: 0;word-break:break-all;display:inline-block;word-wrap: break-word;width:225px;"><#-- desc -->揪朋友來KAZA 有機會獲得頂級草本保養品</p>
									    	<span style=" font-size:12px; color:rgb(0,107,182); line-height:15px;margin: 0; padding: 0;word-break:break-all;display:inline-block;word-wrap: break-word;width:225px;"><#-- display url -->kaza.pchome.com.tw</span>
										</div>
									</div>
								</a>
							</div>
					    	<#-- iframe廣告 end-->
						</div>
				    	<#-- 預覽廣告模式 end-->
				
						<#-- 修改廣告模式 start-->
						<div class="mod_ok ad-mod-hide">
					    	<input class="mod-button btn_ok" type="button" id="" value="確 認">
							<#-- iframe廣告 start-->
							<div style="width:85%; border:0px rgb(205,205,205) solid; padding:15px 5px 15px 5px; font-family:微軟正黑體,Arial; position:relative; ">
								<#--LOGO-->
								<div id="logooff" style="position:absolute;top:0; left:0;width:20px; height:18px; line-height:18px; background:rgb(175,175,175); cursor:pointer;" onmouseover="doOver()"><img src="https://kdpic.pchome.com.tw/img/public/adlogo_off.png" width="20" height="18" border="0"></div>
								<#--LOGO-->
								<div id="logoshow" style="display:none;position:absolute;top:0; left:0; height:18px; line-height:18px; background:rgb(175,175,175); cursor:pointer;" onmouseout="doOut()"><a href="https://show.pchome.com.tw" style="text-decoration:none" target="_new"><span style="font-size:12px;color:#FFF;text-shadow:-1px -1px rgb(152,152,152); padding-left:52px; background:url(https://kdpic.pchome.com.tw/img/public/adlogo_on.png) no-repeat;">提供的廣告</span></a></div>
								<#--廣告內容-->
								<#-- real url -->
								<div style="float:left;text-align:left; margin-left:12px;"><#-- img src --><img src="http://show.pchome.com.tw/img/ad_201310300003.jpg?time=20180201123133" style=" width:85px; height:85px; float:left; margin-right:5px; border:0">
									<div style="float: left;width: 72%;">
					                	<input type="text" class="inputPlaceholderTmg" id="adTitle" name="adTitle" style="width:96%;margin: 1px 0; padding: 3px;" placeholder="沒有朋友人生是黑白的？快來KAZA" maxlength="17">
					                	<span style="float:right" id="spanAdTitle">已輸入0字，剩17字</span>
								    	<textarea style="width:96%;margin: 1px 0;padding: 3px;" class="inputPlaceholderTmgTextarea" id="adContent" name="adContent" maxlength="36" onkeypress="if(event.keyCode==13) return false;" placeholder="揪朋友來KAZA 有機會獲得頂級草本保養品"></textarea>
								    	<span style="float:right" id="spanAdContent">已輸入0字，剩36字</span>
								    	<input type="text" class="inputPlaceholderTmg" data-value="spanAdLinkURL" id="adLinkURL" name="adLinkURL" style="width: 96%;margin: 1px 0;padding: 3px;" value="kaza.pchome.com.tw/event/rainbow/index.html" placeholder="show.pchome.com.tw">
								    	<span style="float:right" id="spanAdShowURL">已輸入0字，剩30字</span>
									</div>
								</div>
							</div>
					    	<#-- iframe廣告 end-->
					    </div>
				     	<#-- 修改廣告模式 end-->
					</div>
		        </td>
		        
		        <td class="td02">http://www.pazzo.com.tw/detail/P402017001303</td>
		        <td class="td03">NT.780</td>

				<td class="td03 ad-mod">						
					<#-- preview-mod -->
					<div class="mod_edit">
					    <input class="mod-button ps btn_edit" type="button" id="" value="修 改">
						<div class="price_wd">NT.349</div>
					</div>
					<#-- modify-mod -->
					<div class="mod_ok ad-mod-hide">
					    <input class="mod-button ps btn_ok" type="button" id="" value="確 認"> 
						<p class="price_wd">NT.<input type="text" name="" style="width:80px;margin: 1px 0; padding: 3px;text-align: center" placeholder="349" maxlength="8"></p>
	                </div>
				</td>				
			</tr>
			
		</tbody>
	</table>
	
	<#--
	<input type="hidden" id="adAdSeq" name="adAdSeq">
	<input type="hidden" id="status" name="status">
	-->

	<input type="hidden" id="formPage" name="page" value="${page}">	
	<input type="hidden" id="fpageSize" name="pageSize" value="${pageSize}">
	<input type="hidden" id="ftotalPage" name="totalPage" value="${totalPage}">
    
</div>