<#assign s=JspTaglibs["/struts-tags"]>
<#assign t=JspTaglibs["http://tiles.apache.org/tags-tiles"]>
<#assign today = .now>
<#assign startDate = ("2015-03-18 00:00:00")?date("yyyy-MM-dd HH:mm:ss")> 
<#assign endDate = ("2015-05-11 23:59:59")?date("yyyy-MM-dd HH:mm:ss")> 




<link type="text/css" rel="stylesheet" href="<@s.url value="/" />html/main/css/bootstrap.min.css" />
<link type="text/css" rel="stylesheet" href="<@s.url value="/" />html/css/style2016.css" />
<link type="text/css" rel="stylesheet" href="<@s.url value="/" />html/css/style_2020event.css" />

<#if (today?date >= startDate?date) && (today?date <= endDate?date)>
    <link type="text/css" rel="stylesheet" href="<@s.url value="/" />html/main/css/layout_motherday.css" />
<#else>
    <!--<link type="text/css" rel="stylesheet" href="<@s.url value="/" />html/main/css/layout.css" />-->
</#if>

<script src="https://www.youtube.com/iframe_api?v=3.0.0" id="YTAPI"></script>
<script>
    $(document).ready(function() {
        // Add smooth scrolling to all links in navbar + footer link
        $(".navbar a, footer a[href='#myPage']").on('click', function(event) {
            // Make sure this.hash has a value before overriding default behavior
            if (this.hash !== "") {
                // Prevent default anchor click behavior
                event.preventDefault();

                // Store hash
                var hash = this.hash;

                // Using jQuery's animate() method to add smooth page scroll
                // The optional number (900) specifies the number of milliseconds it takes to scroll to the specified area
                $('html, body').animate({
                    scrollTop: $(hash).offset().top
                }, 900, function() {

                    // Add hash (#) to URL when done scrolling (default click behavior)
                    window.location.hash = hash;
                });
            } // End if
        });

        $(window).scroll(function() {
            $(".slideanim").each(function() {
                var pos = $(this).offset().top;

                var winTop = $(window).scrollTop();
                if (pos < winTop + 600) {
                    $(this).addClass("slide");
                }
            });
            $(".photobg,#videolayer").css('opacity',1-($(window).scrollTop()/2000));
            $("#title").css('opacity',1-($(window).scrollTop()/600));
        });
        $(".photobg,#videolayer").css('opacity',1-($(window).scrollTop()/2000));
        $("#title").css('opacity',1-($(window).scrollTop()/600));
    })
</script>

<script>
    var myPlayer;
    jQuery(function () {
        myPlayer = jQuery("#video").YTPlayer();
        $("#video").YTPApplyFilters({brightness: 80});
    });
</script>

<nav class="navbar navbar-default navbar-fixed-top">

    <div class="container-fluid nopadding">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <div class="logo"><a href="http://show.pchome.com.tw/" target="http://show.pchome.com.tw"><img src="<@s.url value="/" />html/img/logo_pchome.png" border="0" /></a></div>
        </div>
        
        
        
        <div class="collapse navbar-collapse" id="myNavbar">
            <ul class="nav navbar-nav navbar-left">
                <li><a href="#step">廣告刊登</a></li>
                <li><a href="#impression">廣告版位</a></li>
                <li><a href="#fee">計費方式</a></li>
                <li><a href="#advantage">廣告優勢</a></li>
                <li><a href="http://show.pchome.com.tw/faq.html" target="_blank">新手上路</a></li>
                <li><a href="http://4c.pchome.com.tw/campaign/emba/" target="_blank">行銷觀點</a></li>
				<!-- <li><a href="http://faq.pchome.com.tw/service/show_contact.html" target="_blank">專人諮詢</a></li> -->
				<li><a href="#telService" target="_blank">專人諮詢</a></li>
				<li><a href="http://bit.ly/2MQkbQ2" target="_blank" class="evnt">最新優惠</a></li>
		        <div class="login">
		            <#if id_pchome?exists>
						<!-- <img src="<@s.url value="/" />html/main/img/icon_bb.gif" hspace="3" align="absmiddle"> -->
						<b><#if bu_id?exists>${bu_id}<#else>${id_pchome}</#if></b> 您好 ( <a href="<@s.url value="/" />logout.html">登出</a> )
					<#else>
						<a href="<@s.url value="/" />login.html">登入</a>
					</#if>
					<!-- ．<a href="${akbpfdServer}redirect.html" target="_blank">經銷商登入</a>
						 ．<a href="http://www.pchome.com.tw" target="">PChome</a> -->
					．<a href="http://www.pchome.com.tw" target="">PChome</a>
		        </div>
            </ul>
        </div>
    </div>
</nav>





<div class="videobg">
	<div id="videolayer" style="opacity: 0.092;"><div class="mbYTP_wrapper" id="wrapper_mbYTP_video" style="position: absolute; z-index: 0; min-width: 100%; min-height: 100%; left: 0px; top: 0px; overflow: hidden; opacity: 0;">
		<iframe id="mbYTP_video" class="playerBox" style="position: absolute; z-index: 0; width: 100%; height: 100%; top: 0px; left: 0px; overflow: hidden; opacity: 1;" frameborder="0" allowfullscreen="1" title="YouTube video player" width="640" height="360" src="https://www.youtube.com/embed/mdII7PQ3ItA?autoplay=1&modestbranding=1&controls=0&showinfo=0&rel=0&enablejsapi=1&version=3&playerapiid=mbYTP_video&origin=file%3A%2F%2Ffile-svr&allowfullscreen=true&wmode=transparent&iv_load_policy=3&html5=1&widgetid=1"></iframe>
	<div class="YTPOverlay" style="position: absolute; top: 0px; left: 0px; width: 100%; height: 100%;"></div></div></div>
	<!--<video id="video" autoplay="" loop="" class="hide-in-mobile"><source data-src="street1694.mp4" type="video/mp4" src="street1694.mp4"></video> -->
	<div class="photobg show-in-mobile" style="opacity: 0.092;"><img src="html/img/videobg.jpg"></div>
</div>

<div id="title" class="title text-center" style="opacity: -2.02667;">
	<!--
	<table>
		<tbody><tr><td>
		<img src="html/main/img/pchomelogo.png" alt="" class="titlogo">
		<h1>聯播網廣告</h1>
		<p>免費曝光，點擊計費；只要<span>$500</span>即可刊登，行銷推廣商品好伙伴！</p>
		<a class="buybtn" href="<@s.url value="/" />redirect.html">立即刊登廣告<span class="glyphicon glyphicon-menu-right"></span></a>
		<h4><span>免</span>開戶費<em>‧</em><span>免</span>設定費<em>‧</em><span>免</span>服務費</h4>
		</td></tr>
	</tbody></table>
	-->
	<table>
		<tbody><tr><td>

		<!-- 快速登入 start-->
		<div class="qklog_box">
		<div class="row">
			<div class="col-md-7 col-sm-12">
					
				<div class="tit-container">

					<img src="http://show.pchome.com.tw/html/main/img/pchomelogo.png" alt="" class="titlogo">
					<h1>聯播網廣告</h1>
					<h4><span>免</span>開戶費<em>‧</em><span>免</span>設定費<em>‧</em><span>免</span>服務費</h4>
					<a class="buybtn" href="/redirect.html">立即刊登廣告<span class="glyphicon glyphicon-menu-right"></span></a>
					
					<p>免費曝光，點擊計費；只要<span>$500</span>即可刊登，行銷推廣商品好伙伴！</p>
				</div>
			</div>
			<div class="col-md-5 col-sm-12">
				<h2 class="text">首次刊登</h2>
				<h3 class="text">我要申請在PChome聯播網刊登廣告</h3>
				<form name="idcheck" id="idcheck" action="/loginCheck.html" method="post">	            			
					<div class="loginbox">
						<div align="center"><font color="#FF0000" size="6"><strong></strong></font></div>
						<div class="qk_login">
							<div><input id="userNM" maxlength="50" name="userNM" type="text" value="" placeholder="姓名"></div>
							<div><input id="userTEL" maxlength="50" name="userTEL" type="text" value="" placeholder="電話"></div>
							<div><input id="userEM" maxlength="50" name="userEM" type="text" value="" placeholder="Email"></div>
						</div>							
						<a class="btn" id="formSubmit" type="button" onclick="sendMailAjax();">立即申請</a>				
					</div>
				</form>
			</div>
		</div>
		</div>
		<!-- 快速登入 end-->


		</td></tr>
	</tbody></table>
</div>

<!-- 專人諮詢固定按鈕 fixed icon -->
<div class="float-box transition-all">               
	<a href="#telService" target="_blank" class="consult-icon"><em data-icon="consult"></em><span>專人諮詢</span></a>
</div>

<!-- 專人諮詢固定按鈕 fixed icon end-->


<div class="contentbox">

	<!-- 刊登秘訣 -->
	<div id="step" class="container-fluid text-center steps">
		<div class="row text-center">
			<span class="subject">刊登秘訣
		</span></div>
		<div class="row stepbox">
			<div class="col-sm-4">
				<span class="glyphicon glyphicon-menu-right"></span>
				<div class="stepicon"><img src="html/main/img/stepicon1.png"></div>
				<h3>建立聯播網廣告</h3>
				<h5>可自由新增與編輯優質廣告內容。</h5>
				<span class="glyphicon glyphicon-menu-down"></span>
			</div>
			<div class="col-sm-4">
				<span class="glyphicon glyphicon-menu-right"></span>
				<div class="stepicon"><img src="html/main/img/stepicon2.png"></div>
				<h3>選擇廣告形式</h3>
				<h5>可選擇圖片、圖文、影音等廣告形式。</h5>
				<span class="glyphicon glyphicon-menu-down"></span>
			</div>
			<div class="col-sm-4">
				<div class="stepicon"><img src="html/main/img/stepicon3.png"></div>
				<h3>PChome大流量，客戶找上門</h3>
				<h5>大數據分析比對，聯播網廣告有效展示。</h5>
			</div>
		</div>
	</div>
	
	<!-- 全站曝光 -->
	<div id="impression" class="container-fluid text-center impression">
		<div class="row text-center">
			<span class="subject">全站曝光
		</span></div>
		<h3>PChome聯播網廣告深入各大網站</h3>

		<h5>PChome集團聯播網廣告<span class="glyphicon glyphicon-menu-right"></span>結合PChome集團資源吸引準客戶目光</h5>
		<div class="row row1">
			<div class="col-sm-12"><img src="html/main/img/imp-img1.png"></div>
			<div class="col-sm-12"><img src="html/main/img/imp-img2.png"></div>
		</div>
		<br>
		<br>

		<h5>PChome聯播網合作夥伴們<span class="glyphicon glyphicon-menu-right"></span>眾網齊發，吸引最大化消費者目光。</h5>     
		<div class="row row3" style="max-width:1100px;margin:auto">
			<div class="col-sm-12">
				<img src="html/main/img/L-managertoday.png">
				<img src="html/main/img/L-teepr.png">
				<img src="html/main/img/L-igamer.png">
				<img src="html/main/img/L-bnext.png">
				<img src="html/main/img/L-mamaclub.png">
				<img src="html/main/img/L-juksy.png">

				<img src="html/main/img/L-nownews.png">
				<img src="html/main/img/L-123kubo.png">
				<img src="html/main/img/L-dodocook.png">
				<img src="html/main/img/L-youthwant.png">
				<img src="html/main/img/L-roodo.png">
				<img src="html/main/img/L-eznewlife.png">
				
				<img src="html/main/img/L-5678news.png">
				<img src="html/main/img/L-PTT.png">
				<img src="html/main/img/L-weatherrisk.png">
				<img src="html/main/img/L-iguang.png">
				<img src="html/main/img/L-ck101.png">
				<img src="html/main/img/L-ithome.png">
				
				<img src="html/main/img/L-jkf.png">
				<img src="html/main/img/L-moneydj.png">
				<img src="html/main/img/L-cool3c.png">
				<img src="html/main/img/L-healthnews.png">
				<img src="html/main/img/L-ytower.png">
				<img src="html/main/img/L-plurk.png">
			</div>
			<span>More...
		</span></div>
	</div>
	
	<!-- 收費/付款 -->
	<div id="fee" class="container-fluid text-center payment">
		<div class="row text-center slideanim slide">
			<span class="subject">如何收費
		</span></div>

		<div class="row text-center paymentbox">
			<div class="col-sm-4 col-sx-12 slideanim slide">
				<div class="box">
					<img src="html/main/img/fee1icon.png">
					<h3>廣告曝光免費</h3>
					<p>聯播網廣告享有免費大量曝光，未被網友點擊時，不需支付任何廣告費用。</p>
				</div>
			</div>
			<div class="col-sm-4 col-sx-12 slideanim slide">
				<div class="box">
					<img src="html/main/img/fee2icon.png">
					<h3>廣告點擊收費</h3>
					<p>聯播網廣告是以廣告點擊計費！當網友點擊您的廣告，才需支付廣告費用。</p>
				</div>
			</div>
			<div class="col-sm-4 col-sx-12 slideanim slide">
				<div class="box">
					<img src="html/main/img/fee3icon.png">
					<h3>預算設定</h3>
					<p>你可自行設定每月廣告金額、每次點擊金額。有效控制預算，創造最大效益，完全不用擔心預算太少，無法買廣告作曝光。</p>
				</div>
			</div>
		</div>

		<div class="row text-center howto slideanim slide">
			<div class="col-sm-12">
				<div class="howtobox">
					<div class="row text-center">
						<span class="subject">如何付款
					</span></div>
					<br>
					<p>‧ 信用卡<span class="glyphicon glyphicon-menu-right"></span>接受VISA、Master或JCB信用卡，線上刷卡一次付清。</p>
					<p>‧ ATM<span class="glyphicon glyphicon-menu-right"></span>選擇ATM轉帳方式繳款，系統會產生一組您專屬的轉帳帳號，僅限當次訂購使用，您可抄下或列印，至任何一台ATM自動櫃員機輸入。</p>
				</div>
			</div>
		</div>

	</div>
	<!-- 廣告優勢 -->
	<div id="advantage" class="container-fluid advantage">
		<div class="row text-center">
			<span class="subject">廣告優勢
		</span></div>
		<div class="text-center">
			<h2>6大優勢!顧客自動找上門</h2>
		</div>
		<div class="row">
			<div class="col-sm-6 col-xs-12">
				<h3><em>1</em>找到對的人</h3>
				<p>電子商務首選<span class="glyphicon glyphicon-menu-right"></span>900萬名網友最愛來PChome買東西。</p>
				<p>高消費力<span class="glyphicon glyphicon-menu-right"></span>平均每7分鐘賣出一台NB、一台單眼相機。</p>
				<p>高含金量<span class="glyphicon glyphicon-menu-right"></span>2015年度創造170億營業額。</p>
			</div>
			<div class="col-sm-6 col-xs-12">
				<h3><em>2</em>大量曝光</h3>
				<p>免費大量曝光，廣告觸角延伸到更廣客層。</p>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-6 col-xs-12">
				<h3><em>3</em>絕佳位置</h3>
				<p>廣告置頂曝光，涵蓋PChome全網，通通看的到、找的到、買的到。</p>
			</div>
			<div class="col-sm-6 col-xs-12">
				<h3><em>4</em>超低廣告成本</h3>
				<p>實際點擊廣告進入網站才計費。</p>
				<p>輕鬆自訂花費及點擊成本。</p>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-6 col-xs-12">
				<h3><em>5</em>靈活管理</h3>
				<p>廣告文案不限修改次數，24小時隨時修改、隨時更新。</p>
			</div>
			<div class="col-sm-6 col-xs-12">
				<h3><em>6</em>成效控管</h3>
				<p>提供多樣成效報表分析，控管廣告投放效益。</p>
			</div>
		</div>
		<a class="buybtn" href="<@s.url value="/" />redirect.html">立即購買<span class="glyphicon glyphicon-menu-right"></span>聯播網廣告</a>
	</div>
	
	<!--專人諮詢-->
    <div id="telService" class="container-fluid text-center telservice">
        <div class="text-center">
            <h3><strong>我要諮詢</strong> 請專員盡速與我聯絡</h3>
            <div style="color:black;text-align:center;width:100%;">
			填寫以下表單留下你的聯絡資料<br>
			或直接來信至<a target="_blank" href="mailto:show@response.pchome.com.tw"><font color="blue"><u> show@response.pchome.com.tw</font></u></a><br>
			我們將有專人盡快與你聯繫！<br>
			<b>免費諮詢專線 02-27000898 分機6066<b>
            </div>
			
        </div>
        <div class="serviceframe">
            <iframe src="http://faq.pchome.com.tw/service/show_contact.html" allowtransparency="true" frameborder="0"></iframe>
        </div>
    </div>

	

	<footer class="container-fluid text-center">
		<a href="#myPage" title="To Top" class="gotop">
			<span class="glyphicon glyphicon-triangle-top"></span><br>TOP
		</a>
		<p><a href="http://www.pchome.com.tw/copyright.html" target="_blank" >著作權保護</a> | 網路家庭版權所有、轉載必究 ‧Copyright PChome Online | HiNet 主機代管．本站已依網站內容分級處理<br>PChome Online and PChome are trademarks of PChome Online Inc.</p>
	</footer>

</div>
<div id="video" class="player mb_YTPlayer" data-property="{videoURL:'https://www.youtube.com/watch?v=mdII7PQ3ItA',containment:'#videolayer', showControls:false, autoPlay:true, loop:true, mute:true, startAt:0, opacity:1, addRaster:true, quality:'default'}" style="display: none;">videobg</div>

