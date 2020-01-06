<body class="page">


<div id="wrap">

<!-- Animations When Scrolling -->
<script>
		window.addEventListener('scroll', function(e) {
		if( $(window).scrollTop() <= 50) {
				$('.wow').removeClass('animated');
				$('.wow').removeAttr('style');
				new WOW().init();
		}
});
</script>

<!-- clouds Animations  -->
<script>      
    $(function(){
        if($(window).width() >= 769) {
            $('.cloud').each(function() {
            offset = $(this).offset();
            pos = $(this).position();
            toleft = $(window).width() + $(this).width() - (offset.left - pos.left);
            speed = (toleft - offset.left)*(30 - 3 * $(this).data('speed'));
            $(this).animate({left:toleft}, speed);
            });
            function cloud_loop() {
            $('.cloud').each(function() {
                if (!$(this).is(':animated')) {
                $(this).css({left:0});
                offset = $(this).offset();
                pos = $(this).position();
                $(this).css({left:$(this).width()*-1 - offset.left});
                toleft = $(window).width() + $(this).width() - (offset.left - pos.left);
                speed = (toleft - offset.left)*(30 - 3 * $(this).data('speed'));
                $(this).animate({left:toleft}, speed);
                }
            });
            }
            setInterval(cloud_loop, 1000);
        }
        });           
</script>

	<div class="main_visual">
       
			<div class=""><img src="html/banner/img/logo_PCad.png"></div>
            <div class="bg only_pc">
                    <img src="html/banner/img/bg1_1.png" class="bg1_4 cloud" data-speed="2">
                    <img src="html/banner/img/bg1_2.png" class="bg1_2 cloud" data-speed="5">
                    <img src="html/banner/img/bg1_3.png" class="bg1_3 cloud" data-speed="1">
                    <img src="html/banner/img/bg1_4.png" class="bg1_4 cloud" data-speed="3">                                        
           </div>

		<div class="inner">
            <div id="topfree" data-animation="animated tada" class="animated tada" style="opacity: 1;"><img src="html/banner/img/top_free.png"></div>

			<div class="sp">
                <figure class="top_sp"><img src="html/banner/img/main_sp_01.png" alt="鼠年錢滾錢 儲值送廣告金!"></figure>
                <div class="asahi"><img src="html/banner/img/asahi_l.png" alt=""></div>
			</div>
			<div class="pc">
				<figure class="subtit animated bounceIn"><img src="html/banner/img/tit_sub.png" alt="鼠年錢滾錢 儲值送廣告金!"></figure>
				<figure><img src="html/banner/img/main_visual.png" alt="鼠年錢滾錢 儲值送廣告金!"></figure>
			</div>
			<div class="obj">
				<div class="obj02"><img src="html/banner/img/obj02.png" width="397" alt=""></div>
				<div class="obj03"><img src="html/banner/img/obj03.png" width="351" alt=""></div>
				<div class="asahi"><img src="html/banner/img/asahi_l.png" alt=""></div>
			</div>
		</div>
	</div>

	<main id="main">
		<section class="navi_menu bg_darkwht">
			<div class="container">
				<div class="blk_body">
					<ul>
					    <li class="hvr-sink"><a href="#startup">商店街開通說明</a></li>
						<li class="hvr-sink"><a href="#promotion">儲值送廣告金</a></li>						
						<li class="hvr-sink"><a href="#link" target="_blank">立即參加</a></li>
					</ul>
				</div>
			</div>
		</section>


	<section class="promotion_blk bg_yellow">
      <div class="sec02_bg">
				<div  id="promotion" class="blk_top titleStyle01">
								<p class="sub">
										新的一年你準備好搶先打開知名度，<br class="res-sp">旗開得勝了嗎？</p>
								<p class="taC txt">	高CP值的廣告方式，消費者點擊才付費<br>讓PChome聯播網廣告助你在茫茫網路海中，<br class="res-sp">得到被看見的機會！<br></p>
				</div>

				<div class="blk_top titleStyle01 container">
					<h2 class=""><span class="mouse"><img src="html/banner/img/icon_mouse_white.png"></span>儲值送廣告金</h2>
					<p class="taC subRed">鼠年錢滾錢 儲值送廣告金!</p>
				</div>

				<div class="blk_body">
					<div class="container event">
						<div class="left">
					
							<div class="cols">
								<figure class="icon"><img src="html/banner/img/icon_time.png" width="60" alt=""></figure>
								<p class="cap">活動時間</p>
								<p class="taC">2020/01/06-2020/02/15</p>
							</div>
							<div class="cols">
								<figure class="icon"><img src="html/banner/img/icon_mouse.png" width="60" alt=""></figure>
								<p class="cap">參加資格</p>
								<p class="taC">PChome聯播網廣告用戶</p>

							</div>
							<div class="cols">
								<figure class="icon"><img src="html/banner/img//icon_gold.png" width="60" alt=""></figure>
								<p class="cap">活動內容</p>
                                <p>在活動期間內，<em class="txt_red">累計儲值總金額</em>達門檻，就贈送相對應廣告金！<br>金額將以贈送<em class="txt_red">最高金額方式</em>做計算，如：
                                    <span style="color:#a48334">儲值總金額10,000元，為5,000元乘以2，共贈送2,000元廣告金。</span></p>
							</div>
							<div class="cols">
								<figure class="icon"><img src="html/banner/img/icon_money.png" width="60" alt=""></figure>
								<p class="cap">注意事項</p>
								<ul class="list">
									<li>廣告金贈送時間為活動結束後10個工作天內，贈送之廣告金使用期限至2020/07/31。</li>
									<li>非預付客戶不得參加此活動。</li>
								   	<li>詢問活動相關事宜，請洽客服： (02)2700-0898分機#6066</li>
							    	<li>主辦單位保有隨時修改及終止本活動之權利，如有任何變更內容或詳細注意事項將公布於本網頁，恕不另行通知。</li>
					             </ul>
							</div>

						</div>
						<div class="right">
							<div class="cols wow fadeInLeft" data-wow-delay="-0.1s">
								<figure><img src="html/banner/img/deposit01.png" alt=""></figure>
							</div>
							<div class="cols wow fadeInRight" data-wow-delay="-0.1s"">
								<figure><img src="html/banner/img/deposit02.png" alt=""></figure>
							</div>
							<div class="cols wow fadeInDown" data-wow-delay="-0.1s"">
								<figure><img src="html/banner/img/deposit03.png" alt=""></figure>
							</div>
							<div class="cols wow fadeInUp" data-wow-delay="-0.1s"">
								<figure><img src="html/banner/img/deposit04.png" alt=""></figure>
							</div>
							<div class="cols wow fadeInUp" data-wow-delay="-0.1s"">
								<figure><img src="html/banner/img/deposit05.png" alt=""></figure>
								<p class="cap">※活動期間內，以儲值<em class="txt_red">累計總金額</em>贈送金額。</p>								
							</div>

						</div>
					</div>
					<div id="link" class="container">
						<div class="btnbox">
                            <div class="btn_style01">
                                <a href="http://bit.ly/2ZqsxDp " class="butn drop-shadow2" target="_blank">PChome商店街用戶<br>前往系統後台</a>
                                <p class="taC cap"">※商店街首次開通請見下方<a href="#startup">開通步驟</a></p>
                            </div>                          
                            <div class="btn_style01 colorpink"><a href="http://bit.ly/2Qldsig" class="butn drop-shadow2" target="_blank">立即參加</a></div>  
                           
						</div>
					</div>
				</div>
			</div>
		</section>		

		<section class="item_detail bg_blue">
			<div class="container">
				<div  id="startup"  class="blk_top titleStyle02 container">
					<h2><span class="mouse"><img src="html/banner/img/icon_mouse_gy.png"></span>商店街首次開通說明</h2>
					<p class="taC subYel">PChome商店街用戶<br class="res-sp">開通3步驟！</p>
				</div>
				<div class="blk_body">
					<p class="mod_tit"><img src="html/banner/img/anm01.png" alt="前往PChome商店街後台" class="wow fadeInDown" data-wow-delay="0.1s"></p>						
					<p class="taC titleStyle03">登入<span class="hilite">商店街會員</span><br class="res-sp"><em>→</em>進入<span class="hilite">我的店鋪</span><br class="res-sp"><em>→</em>點選<span class="hilite">廣告管理</span><br class="res-sp"><em>→</em>點選<span class="hilite">PChome聯播網廣告</span></p>							
					<div class="item_img wow slideInLeft">
						<figur class="drop-shadow2"><img src="html/banner/img/step1.gif" alt=""></figur>					
					</div>

					<p class="mod_tit"><img src="html/banner/img/anm02.png" alt="點選頁面下方按鈕刊登廣告”" class="wow slideInUp" data-wow-delay="0.1s"></p>						
					<div class="item_img wow  slideInRight">
						<figur class="drop-shadow2"><img src="html/banner/img/step2.gif" alt=""></figur>					
					</div>					

					<p class="mod_tit"><img src="html/banner/img/anm03.png" alt="PChome聯播網廣告頁面點擊刊登廣告" class="wow slideInUp"  data-wow-delay="0.1s"></p>						
					<div class="item_img wow slideInLeft">
						<figur class="drop-shadow2"><img src="html/banner/img/step3.gif" alt=""></figur>						
                    </div>
                    <div class="btnbox">
                         <div class="btn_style01 colorpink"><a href="http://bit.ly/2Q0JQrz" class="butn drop-shadow2" target="_blank">PChome商店街用戶<br>立即參加</a></div>                  
                    </div>
                   

				</div>
			</div>
		</section>


	</main>

		<footer id="footer">
		<div class="pagetop"><a href="#wrap"><span>TOP</span></a></div>
		<div class="copy"><small><a href="http://www.pchome.com.tw/copyright.html" target="_blank">著作權保護</a> | 網路家庭版權所有、轉載必究 ‧Copyright PChome Online | HiNet 主機代管．本站已依網站內容分級處理<br>PChome Online and PChome are trademarks of PChome Online Inc.</div>
	</footer>

</div>





</body>