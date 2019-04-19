<#assign s=JspTaglibs["/struts-tags"]>


<div class="cont">
<!-- 公告 start -->
<h2>
<div class="cal">帳戶名稱：${customer_info_title}</div>
<img src="<@s.url value="/html/img/"/>iconcr.gif" hspace="2" vspace="12" align="absmiddle" />公告訊息
</h2>



<div class="grtba">

<table cellspacing="3" cellpadding="0" border="0" style="float:left;margin-bottom:10px">
	<tr>
	    <td>
	        <select id="boardType">
	        <#list boardType as list>
	        	 <option value="${list.type}" >${list.chName}</option>
	        </#list>
	        </select>
	    </td>
	</tr>
</table>

<div id="boardTable"></div>

<!-- 公告 end -->
</div>
</div>