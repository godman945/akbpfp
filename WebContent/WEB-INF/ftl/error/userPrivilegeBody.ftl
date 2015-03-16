<#assign s=JspTaglibs["/struts-tags"]>
<#assign t=JspTaglibs["http://tiles.apache.org/tags-tiles"]>


<center style="font-size:16px">
提醒您，您的關鍵字廣告帳戶僅有帳單管理、報表管理權限!<br>
您可以聯絡帳戶總管理者開啟管理權限，若您仍需前往查看關鍵字帳戶請按是，按否即可回到原畫面。<br>
<input type="button" value="是" onclick="javascript:window.location='summary.html'" />
<input type="button" value="否" onclick="javascript:window.location='${ref!}'"/>
</center>