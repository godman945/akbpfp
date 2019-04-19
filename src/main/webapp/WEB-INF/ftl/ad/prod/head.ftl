<#assign s=JspTaglibs["/struts-tags"]>
<#assign t=JspTaglibs["http://tiles.apache.org/tags-tiles"]>
<@s.set var="uri" value="%{#request['javax.servlet.forward.request_uri']}" />
		<h1 class="adtitle" style="">廣告：${adActionName!} &gt; 分類：${adGroupName!}</h1>
		<h2>
			<div class="cal">帳戶名稱：${customer_info_title!}</div>
			<img vspace="12" hspace="2" align="absmiddle" src="html/img/iconcr.gif">
				<#if uri?index_of('adAdAdd.html') != -1>
					新增廣告
				<#elseif uri?index_of('adAdEditProd.html') != -1>
					修改廣告	
				</#if>
				
			</img>
		</h2>
			<#if uri?index_of('adAdAdd.html') != -1>
				<div class="steps" style="background:none;">輸入廣告基本設定 &gt; 建立分類及出價  &gt; <b>製作廣告及關鍵字設定</b>  &gt; 廣告完成 </div>
			</#if>
		<ul class="admenuul">
				<li class="m04"><a href="" onclick="return(chkLeave())">商品廣告</a></li>
	    </ul>
