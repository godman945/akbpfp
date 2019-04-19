<#assign s=JspTaglibs["/struts-tags"]>
<#assign t=JspTaglibs["http://tiles.apache.org/tags-tiles"]>

<div class="cont">
	<#if board?exists>
		<#list enumBoardType as types>
			<#if board.boardType == types.type>
				<h2 class="adtitle"><a href="accountRemain.html" muse_scanned="true"><b>${types.chName!}：</b>${board.content!}</a>  ${board.startDate!}</h2>
			</#if>
		</#list>
	</#if>
	<h2>
		<div class="cal">帳戶名稱：${customer_info_title}</div>
		<img src="<@s.url value="/" />html/img/iconcr.gif" hspace="2" vspace="12" align="absmiddle" />檢視廣告
	</h2>
	
	<div class="adnav">
		<div class="map">
			<span>»</span><a href="adActionView.html">檢視廣告</a>
			<span>»</span><a href="adGroupView.html?adActionSeq=${adGroup.pfpAdAction.adActionSeq!}&groupMaxPrice=${groupMaxPrice!}">廣告：${adGroup.pfpAdAction.adActionName!}</a>
			<span>»</span>分類：${adGroup.adGroupName!}</a>
		</div>
		
		<div class="cala01">
			<input id="IT_dateRange" readonly="true"/> 
			<img src="<@s.url value="/html/img/"/>icon_cal.gif" border="0" align="absmiddle" id="dateRangeSelect"/>
		</div>	
		
		<div style="clear:both;height:10px"></div>

		<@t.insertAttribute name="dateRangeSelect" />
	
		<div class="adnav_box">
			<ul class="detailtab">
				<#if adType == "0" || adType == "1" >
				<li><a href="adKeywordView.html?adGroupSeq=${adGroup.adGroupSeq!}&groupMaxPrice=${groupMaxPrice!}">關鍵字</a></li>
				</#if>
				<li><a href="#" class="active"><img src="<@s.url value="/" />html/img/detailtab_bg2.gif" width="7" height="10">廣告明細</a></li>
			</ul>
		</div>		
	</div>
	
	<div class="grtba borderbox" >
		<div>
			<a href="adAdAdd.html?adGroupSeq=${adGroup.adGroupSeq!}&adOperatingRule=${adOperatingRule!}" class="addbtn"><img src="<@s.url value="/" />html/img/btnadd4.gif" border="0"></a>
		</div>
		
		<div style="clear:both">
			<table cellspacing="3" cellpadding="0" border="0" class="srchtb">
				<tbody>
					<tr>
						<td>
							<select id="searchType" name="searchType" style="width:150px;" >
								<#list searchAdType as adType>
		                        	<#if adType.type == adGroup.pfpAdAction.adType >
		                    		<option value="${adType.type!}">${adType.typeName!}</option>
		                    		</#if>
		                        </#list>
							</select>
							<input type="text" id="keyword" name="keyword" style="width:400px;">
						</td>
						<td><a href="#" id="search" muse_scanned="true"><img border="0" src="<@s.url value="/" />html/img/srchbtn.gif"></a></td>
					</tr>
				</tbody>
			</table>
		</div>
		
		<div style="clear:both;height:10px"></div>
			
		<div id="tableList" class="scrollwrap"></div>			

	</div>
</div> 
<input type="hidden" id="adGroupSeq" name="adGroupSeq" value="${adGroup.adGroupSeq!}" />
<input type="hidden" id="adType" name="adType" value="${adType!}" />
