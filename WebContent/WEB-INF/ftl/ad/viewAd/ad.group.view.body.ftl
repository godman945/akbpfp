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
		<img src="<@s.url value="/" />html/img/iconcr.gif" hspace="2" vspace="12" align="absmiddle" />檢視分類
	</h2>
	
	<div class="adnav">
		<div class="map">
			<span>»</span><a href="adActionView.html">檢視廣告</a>
			<span>»</span>廣告：${adAction.adActionName!}
		</div>
		
		<div class="cala01">
            <input id="IT_dateRange" readonly="true"/> 
            <img src="<@s.url value="/" />html/img/icon_cal.gif" border="0" align="absmiddle" id="dateRangeSelect"/>
        </div>
        
        <div style="clear:both;height:10px"></div>
        
        <@t.insertAttribute name="dateRangeSelect" />            
	</div>
	
	<div class="grtba borderbox" >
		<div>
			<h4 style="float:left">分類列表 </h4>
			<a href="adGroupAdd.html?adActionSeq=${adAction.adActionSeq!}"  class="addbtn"><img src="<@s.url value="/" />html/img/btnadd3.gif"></a>				
		</div>
		
        <div style="clear:both;background:#e4e3e0">				
		    <table cellspacing="3" cellpadding="0" border="0" class="srchtb">
		        <tbody>
		            <tr>
		                <td>
		                    <select id="searchType" name="searchType">
		                        <#list searchAdType as adType>
		                    		<option value="${adType.type!}">${adType.chName!}</option>
		                        </#list>
		                    </select>
		                    <input type="text" id="keyword" name="keyword" style="width:400px;">
		                </td>
		                <td><a href="#" id="search" muse_scanned="true"><img border="0" src="<@s.url value="/" />html/img/srchbtn.gif"></a></td>
		            </tr>
		        </tbody>
		    </table>
		</div>
		
		<div style="clear:both;height:50%"></div>
            
        <div id="tableList"></div>
		
	</div>
</div>	
 
<input type="hidden" id="adActionSeq" name="adActionSeq" value="${adAction.adActionSeq!}" />
<input type="hidden" id="groupMaxPrice" name="groupMaxPrice" value="${groupMaxPrice!}" />
<input type="hidden" id="adType" name="adType" value="${adAction.adType!}" />
