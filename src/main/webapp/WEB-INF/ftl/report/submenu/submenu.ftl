<#assign s=JspTaglibs["/struts-tags"]>
<#assign t=JspTaglibs["http://tiles.apache.org/tags-tiles"]>
<@s.set var="rUri" value="%{#request['javax.servlet.forward.request_uri']}" />


  <@s.if test="%{#rUri.indexOf('/reportExcerpt.') != -1}">
        <div class="submn"><a href="<@s.url value="/reportExcerpt.html" />" style="text-decoration:none"><b>總廣告成效</b></a></div>
  </@s.if>
  <@s.else>
        <div class="submn"><a href="<@s.url value="/reportExcerpt.html" />">總廣告成效</a></div>
  </@s.else>
  
  <@s.if test="%{#rUri.indexOf('/reportAdDaily.') != -1}">
        <div class="submn"><a href="<@s.url value="/reportAdDaily.html" />" style="text-decoration:none"><b>每日花費成效</b></a></div>
  </@s.if>
  <@s.else>
        <div class="submn"><a href="<@s.url value="/reportAdDaily.html" />">每日花費成效</a></div>
  </@s.else>
  
 <@s.if test="%{#rUri.indexOf('/reportCampagin.') != -1}">
        <div class="submn"><a href="<@s.url value="/reportCampagin.html" />" style="text-decoration:none"><b>廣告成效</b></a></div>
  </@s.if>
  <@s.else>
        <div class="submn"><a href="<@s.url value="/reportCampagin.html" />">廣告成效</a></div>
  </@s.else>
  
  <@s.if test="%{#rUri.indexOf('/reportAdGroup.') != -1}">
        <div class="submn"><a href="<@s.url value="/reportAdGroup.html" />" style="text-decoration:none"><b>分類成效</b></a></div>
  </@s.if>
  <@s.else>
        <div class="submn"><a href="<@s.url value="/reportAdGroup.html" />">分類成效</a></div>
  </@s.else>
      
   <@s.if test="%{#rUri.indexOf('/reportAdvertise.') != -1}">
        <div class="submn"><a href="<@s.url value="/reportAdvertise.html" />" style="text-decoration:none"><b>廣告明細成效</b></a></div>
  </@s.if>
  <@s.else>
        <div class="submn"><a href="<@s.url value="/reportAdvertise.html" />">廣告明細成效</a></div>
  </@s.else>

  <!-- (20190813移除此報表)
  <@s.if test="%{#rUri.indexOf('/reportKeyword.') != -1}">
        <div class="submn"><a href="<@s.url value="/reportKeyword.html" />" style="text-decoration:none"><b>關鍵字成效</b></a></div>
  </@s.if>
  <@s.else>
        <div class="submn"><a href="<@s.url value="/reportKeyword.html" />">關鍵字成效</a></div>
  </@s.else>
  -->
  
  <@s.if test="%{#rUri.indexOf('/reportAdOs.') != -1}">
        <div class="submn"><a href="<@s.url value="/reportAdOs.html" />" style="text-decoration:none"><b>行動廣告成效</b></a></div>
  </@s.if>
  <@s.else>
        <div class="submn"><a href="<@s.url value="/reportAdOs.html" />">行動廣告成效</a></div>
  </@s.else>
 
  <@s.if test="%{#rUri.indexOf('/reportAdTime.') != -1}">
        <div class="submn"><a href="<@s.url value="/reportAdTime.html" />" style="text-decoration:none"><b>廣告播放時段</b></a></div>
  </@s.if>
  <@s.else>
        <div class="submn"><a href="<@s.url value="/reportAdTime.html" />">廣告播放時段</a></div>
  </@s.else>
  
  <@s.if test="%{#rUri.indexOf('/reportAdAgesex.') != -1}">
        <div class="submn"><a href="<@s.url value="/reportAdAgesex.html" />" style="text-decoration:none"><b>廣告族群</b></a></div>
  </@s.if>
  <@s.else>
        <div class="submn"><a href="<@s.url value="/reportAdAgesex.html" />">廣告族群</a></div>
  </@s.else>
  
  <@s.if test="%{#rUri.indexOf('/reportAdWebsite.') != -1}">
        <div class="submn"><a href="<@s.url value="/reportAdWebsite.html" />" style="text-decoration:none"><b>網站類型</b></a></div>
  </@s.if>
  <@s.else>
        <div class="submn"><a href="<@s.url value="/reportAdWebsite.html" />">網站類型</a></div>
  </@s.else>
  <@s.if test="%{#rUri.indexOf('/reportVideoPerformance.') != -1}">
        <div class="submn"><a href="<@s.url value="/reportVideoPerformance.html" />" style="text-decoration:none"><b>影音廣告</b></a></div>
  </@s.if>
  <@s.else>
        <div class="submn"><a href="<@s.url value="/reportVideoPerformance.html" />">影音廣告</a></div>
  </@s.else>