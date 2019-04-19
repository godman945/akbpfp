<#assign s=JspTaglibs["/struts-tags"]>
<#assign t=JspTaglibs["http://tiles.apache.org/tags-tiles"]>

	<script type="text/javascript" src="<@s.url value="/" />html/main/js/jquery.min.js"></script>
	<script type="text/javascript" src="<@s.url value="/" />html/main/js/base.js"></script>
	<script type="text/javascript" src="http://rmi.pchome.com.tw/rmiservice/pv.html?t=pfp:pv_pfp_qa"></script>

	<div id="header">
		<div class="nav" style="display: none; top: -95px;">
			<ul id="topNavi">
				<li><a class="newp" href="<@s.url value="/" />index.html#content"></a></li>
				<li><a class="show" href="<@s.url value="/" />show.html#content"></a></li>
				<li><a class="pay" href="<@s.url value="/" />pay.html#content"></a></li>
				<li><a class="advantage" href="<@s.url value="/" />advantage.html#content"></a></li>
				<li><a class="qa selected" href="<@s.url value="/" />faq.html"></a></li>	
			</ul>
		</div>
	</div>
	<div class="container clearfix">
		<div id="content">
			<a name="qatop" id="qatop"></a>
			<img src="<@s.url value="/" />html/main/img/cont05n.png" usemap="#Map" border="0" style="margin:50px 0 0 0;">
			<ul class="qamenu">
			<#if faqListVOs?exists>
				<#list faqListVOs as vo>
				<li id="list_${vo.fid!}"<#if vo.fid?number == lid> class="active"</#if>><a href="javascript:listClk('${vo.fid!}');">${vo.name!}</a></li>
				</#list>
			</#if>
		
			</ul>
			<div class="qacont" id="qList"<#if qid??> Style="display:none</#if>">
				<#if faqQuestionVOs?exists>
				<ol id="ol_${fid!}" class="qalist" Style="display:<#if fid?number == lid>inline<#else>none</#if>">
					<#list faqQuestionVOs as qvo>
					<li><i class="q${qvo.no}"></i><a href="javascript:showSolution('${qvo.faqFId}', '${qvo.faqQId}', 'q${qvo.no}', '${qvo.faqContent}')">${qvo.faqContent}</a></li>
					</#list>
				</ol>
				</#if>
			</div>
			<div class="qacont" id="qSolution" style="display:<#if qid??>inline<#else>none</#if>">
				<div class="backlist"><a href="javascript:backList('${fid!}');">返回</a></div>
				<h1 id="questionContext"><i id="questionImg" class="${qimg!}"></i>${faqSolutionVO.faqQuestion!}</h1>
				<!--內容開始-->
				<div class="qatext" id="solution">${faqSolutionVO.faqSolutionContent!}
				</div>
				<!--內容結束-->
			</div>
		</div>
	</div>
</div>