<#assign s=JspTaglibs["/struts-tags"]>

<div class="cont">
<h2>
    <div class="cal">
    	<#if changeStatusFlag=="N"><span class="t_s01" style="font-size:14px;margin: 0px 10px 0px 0px;"><b>申請退款期間無法修改帳戶資料</b></span></#if>
    	
    	<#if buAccountVO?exists>
			<#else>
				<input type="button" id="modify" value="修改帳戶資料" style="width:110px;" <#if changeStatusFlag=="N">disabled</#if> >
    	</#if>
    	
    </div>
    <img src="<@s.url value="/" />html/img/iconcr.gif" hspace="2" vspace="12" align="absmiddle" />帳戶資訊
</h2>

<div class="grtba">        
<table width="100%" border="0" cellpadding="0" cellspacing="1" class="tb02">
    <tr>
        <th height="35">帳戶編號</th>
        <td>${pfpCustomerInfo.customerInfoId!}</td>
    </tr>
    <tr>
        <th height="35">帳戶名稱</th>
        <td>${pfpCustomerInfo.customerInfoTitle!}</td>
    </tr>
    <tr>
        <th height="35">帳戶狀態</th>
        <td>
        <#if pfpCustomerInfo.status=="0">
			關閉       (${pfpCustomerInfo.updateDate?string("yyyy-MM-dd HH:mm:ss")!})
		<#elseif pfpCustomerInfo.status=="1">
			開啟       (${pfpCustomerInfo.activateDate?string("yyyy-MM-dd HH:mm:ss")!})
		<#elseif pfpCustomerInfo.status=="2">
			停權       (${pfpCustomerInfo.updateDate?string("yyyy-MM-dd HH:mm:ss")!})
		</#if>
		
        </td>
    </tr>
    <tr>
      	<th height="35">帳戶類別</th>
      	<td>
		<#if pfpCustomerInfo.category=="1">
			個人戶
		<#elseif pfpCustomerInfo.category=="2">
			公司戶
		</#if>
		</td>
    </tr>
    <#if pfpCustomerInfo.category=="2">
    <tr>
        <th height="35">公司名稱</th>
        <td>${pfpCustomerInfo.companyTitle!}</td>
    </tr>
    <tr>
        <th height="35">統一編號</th>
        <td>${pfpCustomerInfo.registration!}</td>
    </tr>
    </#if>
    <tr>
        <th height="35">產業類別</th>
        <td>${pfpCustomerInfo.industry!}</td>	
    </tr>
    <tr>
        <th height="35">廣告連結網址</th>
        <td><a href="http://${pfpCustomerInfo.urlAddress!}" target=_blank>http://${pfpCustomerInfo.urlAddress!}</a></td>	
    </tr>
</table>
</div>

<h2><img src="<@s.url value="/" />html/img/iconcr.gif" hspace="2" vspace="12" align="absmiddle" />帳戶付費方式</h2>

<div class="grtba">
<table width="100%" border="0" cellpadding="0" cellspacing="1" class="tb02">
    <tr>
        <th height="35">結帳方式</th>
        <td>
        	<#if pay_type == payType[0].payType>
        		${payType[0].payName!}
        	<#elseif pay_type == payType[1].payType>
        		${payType[1].payName!}
        	</#if>
        	
        </td>        
    </tr>
    <tr>
        <th height="35">帳戶餘額<br /><span class="t_s01">(帳戶餘額結算時間至前一天為止)</span></th>
        <td>
            <span style="float:left">NT$ ${pfpCustomerInfo.remain?string('#,###')!}</span>
            <div class="gbtn"><#if pay_type == payType[0].payType><a href="accountRemain.html">帳戶儲值</a></#if></div>                    
        </td>
    </tr>
</table>
</div>

