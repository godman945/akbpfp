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
				<li><a class="qa selected" href="<@s.url value="/" />faq.html#content"></a></li>	
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
				<li id="list_${vo.no!}"<#if vo.no == lid> class="active"</#if>><a href="javascript:listClk('ol_${vo.no!}', ${faqListVOs?size});">${vo.name!}</a></li>
				</#list>
			</#if>
		
			</ul>
			<div class="qacont">
				<div class="backlist"><a href-"#"="">返回</a></div>
				<h1><i class="q${sid}"></i>${faqSolutionVO.faqQuestion}</h1>
				<!--內容開始-->
				${faqSolutionVO.faqSolutionContent}
				<BR><BR><BR><BR><BR><BR><BR><BR><BR><BR><BR><BR><BR><BR><BR><BR>
				<div class="qatext">
					<h2>註冊會員</h2>
					<h3>一、如何購買關鍵字廣告？</h3>
                       <p>如果您已經是PChome的會員，已完成信箱認證，只需要登入，即可購買關鍵字廣告。</p>
						<b>(若非PChome會員，請先<a href="#">申請會員</a>!)</b><br>

                      <img title="" src="http://pic.pimg.tw/babyhome88/1379392697-3088989074_n.jpg" alt="" width="509" height="281" border="0">
                       <p>設定您的PChome廣告帳戶資料並儲值500 元以上，即可開通您的廣告刊登帳戶。登入頁面後，填寫您關鍵字帳戶基本資料-&gt;填寫完畢後，按「付款儲值」即可開始建立關鍵字廣告</p>
                      <img title="" src="http://pic.pimg.tw/babyhome88/1379397129-516338572_n.jpg?v=1379397130" alt="" border="0">
                      <p class="tred">若您要購買的關鍵字廣告無網址，建議您可先在0元免費刊登中申請網址，廣告需要網址才能申請付費關鍵字廣告，並且一次儲值最低不能低於500元。</p>
				</div>
				<!--內容結束-->
			</div>
		</div>
	</div>
</div>