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
        		<img src="<@s.url value="/" />html/img/icon_cal.gif" border="0" align="absmiddle" id="dateRangeSelect"/>
        	</div>	
        	
			<div style="clear:both;height:10px"></div>
			
			<@t.insertAttribute name="dateRangeSelect" />
			
			<div class="adnav_box">
				<ul class="detailtab">
					<li><a href="#" class="active"><img src="<@s.url value="/" />html/img/detailtab_bg2.gif" width="7" height="10">關鍵字</a></li>
					<li><a href="adAdView.html?adGroupSeq=${adGroup.adGroupSeq!}">廣告明細</a></li>
				</ul>
			</div>
			
  		</div>
  
		<div class="grtba borderbox" >
			<div>
				<a href="adKeywordAdd.html?adGroupSeq=${adGroup.adGroupSeq!}" class="addbtn"><img src="<@s.url value="/" />html/img/btnadd2.gif" border="0"></a>				
			</div>
            <div style="clear:both;background:#e4e3e0">	
            			
                <table cellspacing="3" cellpadding="0" border="0" class="srchtb">
                    <tbody>
                        <tr>
                            <td>
                                <input type="text" id="keyword" name="keyword" style="width:400px;">
                            </td>
                            <td><a href="#" id="search" muse_scanned="true"><img border="0" src="<@s.url value="/" />html/img/srchbtn.gif"></a></td>
                        </tr>
                    </tbody>
                </table>
            </div>
            
            <div style="clear:both;height:50%"></div>
            			
            <div id="tableList" class="scrollwrap"></div> 
            
		</div>
</div>
 
<input type="hidden" id="adGroupSeq" name="adGroupSeq" value="${adGroup.adGroupSeq!}" />  
<input type="hidden" id="adExcludeKeywordSeq" name="adExcludeKeywordSeq" />
<input type="hidden" id="adExcludeKeywordStatus" name="adExcludeKeywordStatus" />


