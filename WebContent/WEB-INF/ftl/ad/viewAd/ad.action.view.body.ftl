<#assign s=JspTaglibs["/struts-tags"]>
<#assign t=JspTaglibs["http://tiles.apache.org/tags-tiles"]>

<link type="text/css" rel="stylesheet" href="<@s.url value="/html/css/ad/adActionView.css"/>"/> 

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
		<div class="map"><span>»</span>檢視廣告</div>
		
		<div class="cala01">
            <input id="IT_dateRange" readonly="true"/> 
            <img src="<@s.url value="/" />html/img/icon_cal.gif" border="0" align="absmiddle" id="dateRangeSelect"/>
        </div>
        
        <div style="clear:both;height:10px"></div>
        
        <@t.insertAttribute name="dateRangeSelect" />            
	</div>
	
	<div class="grtba borderbox" >
		<div>
			<h4 style="float:left">廣告列表 </h4>
            <#if addFlag?exists && addFlag=="y">
			    &nbsp;<img class="addAdImg" src="<@s.url value="/" />html/img/btnadd.gif">

			    <#-- 廣告刊登選擇介面 start -->
				<div class="menu-option-addad" style="display: none;">
					<div class="content-wrapper">
						<div class="close-menu">
							<span class="icon-nv"><img src="<@s.url value="/" />html/img/icon_close.png"></span>
						</div>
				
						<div class="menu-groups">
							<div class="addlist-item adAdd">
								<div class="menu-item">
									<span class="icon-nv"><img src="<@s.url value="/" />html/img/icon_addnew.png"></span>
									<span class="menu-item-label _ngcontent-cry-99">
										一般廣告刊登
									</span>
								</div>
								<div class="descrip-item">
									多類型廣告刊登樣式
								</div>
							</div>
				
							<div class="addlist-item fastURLAdAdd">
								<div class="menu-item">
									<span class="icon-nv"><img src="<@s.url value="/" />html/img/icon_addnew.png"></span>
									<span class="menu-item-label _ngcontent-cry-99">
										快速網址刊登
									</span>
								</div>
								<div class="descrip-item">
									限PChome 24h購物、商店街、露天
								</div>
							</div>
					    </div>
				    </div>
				</div>
				<#-- 廣告刊登選擇介面 end-->
            </#if>
		</div>
		
        <div style="clear:both;background:#e4e3e0">				
		    <table cellspacing="3" cellpadding="0" border="0" class="srchtb">
		        <tbody>
		            <tr>
		                <td>
		                    <select id="searchType" name="searchType">
		                    	<option value="">全部</option>
		                    	<#list searchAdType as adType>
		                    		<option value="${adType.type!}">${adType.typeName!}</option>
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