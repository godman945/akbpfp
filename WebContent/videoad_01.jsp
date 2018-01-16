<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<script type="text/javascript">
	var url=window.location.search.replace('?',"");
	console.log(url);
	
	var iframeArray = window.parent.parent.document.getElementsByTagName("iframe");
	var scrollTop = window.parent.parent.document.body.scrollTop || window.parent.parent.document.documentElement.scrollTop;
	
	var timeVideo;
	document.addEventListener('DOMContentLoaded', function () {
		window.parent.parent.document.addEventListener('scroll', function() {
			playController();
		});
		
		window.parent.parent.addEventListener('resize', function() {
			playController();
		});

		window.parent.parent.document.addEventListener('visibilitychange', function() {			
			if(document.hidden){
				console.log('我切換了');
				playVisibilitychangeController(false);
			}else{
				console.log('我回來了');
				playVisibilitychangeController(true);
			}
		},false);	
			
		function playController(){
			try{
				/*滾輪移動距離*/
				var scrollTop = window.parent.parent.document.body.scrollTop || window.parent.parent.document.documentElement.scrollTop;
				/*視窗可視大小*/
				var viewHeight = window.parent.parent.innerHeight;
				for(var i = 0; i< iframeArray.length; i++){	
					var video = iframeArray[i].contentDocument.body.children[0].contentWindow.document.querySelector(".home-banner");
					if(iframeArray[i].contentDocument.body.children[0] == undefined){
						continue;
					}
						
					if(video == null){
						continue;
					}

					video.style.width = iframeArray[i].width+'px';
					video.style.height = iframeArray[i].height+'px';

					/*iframe頂部距離最上方絕對位置*/
					var iframeOffSetTop = iframeArray[i].offsetTop;
					/*iframe底部距離最上方距離*/
					var iframeBottom = iframeArray[i].getBoundingClientRect().bottom;
					/*iframe頂部距離最上方距離*/
					var iframeTop = iframeArray[i].getBoundingClientRect().top;
					/*iframe高度一半*/
					var iframeHalf = iframeArray[i].height / 2; 
					/*控制高度可視範圍高度扣除iframe高度*/
					var controllerHeight = (viewHeight - iframeArray[i].height) + iframeHalf;			
					if(iframeOffSetTop >= scrollTop){
						if(iframeBottom > 0){
							if(iframeTop <= controllerHeight){
								if(video.paused){
									console.log("iframe:"+i+" 是否播放:"+true);
									video.play();
									timeVideo = setInterval('alex('+i+')',"1000");
								}
							}else{
								if(!video.paused){
									console.log("iframe:"+i+" 是否播放:"+false);
									video.pause();
									clearInterval(timeVideo);
								}
							}
						}
					}else{
						if(iframeBottom < iframeHalf){
							if(!video.paused){
								console.log("iframe:"+i+" 是否播放:"+false);
								video.pause();
								clearInterval(timeVideo);
							}
						}else{
							if(video.paused){
								console.log("iframe:"+i+" 是否播放:"+true);
								video.play();
								timeVideo = setInterval('alex('+i+')',"1000");
							}
						}
					}
				}
			}catch(err){
				console.log(err);
			}
		}
		
		/*切換頁簽或遮屏則停止*/
		function playVisibilitychangeController(changeFlag){
			for(var i = 0; i< iframeArray.length; i++){
				if(iframeArray[i].contentDocument == null){
					continue;
				}
				
				if(iframeArray[i].contentDocument.body.children[0] == undefined){
					continue;
				}
				
				var video = iframeArray[i].contentDocument.body.children[0].contentWindow.document.querySelector(".home-banner");	
				if(video == null){
					continue;
				}
				
				/*切換進行停止影片動作*/			
				if(!changeFlag){
					if(!video.paused){
						console.log("iframe:"+i+" 是否播放:"+false);
						video.pause();
						clearInterval(timeVideo);
						console.log('因為切換我停止影片了');
					}
				}else{
					playController();
				}
			}
		}
	}, false);
	
	
	function alex(index){
		var video = iframeArray[index].contentDocument.body.children[0].contentWindow.document.querySelector(".home-banner");
		if(!video.paused){
			console.log('iframe:'+index +'目前:'+video.currentTime+'秒');
		}else{
			console.log('我停止影片了');
			clearInterval(timeVideo);
		}
	}
</script>
</head>
<body>

<style>
	/*video::-webkit-media-controls{display:none;}*/
	body, html{margin:0;padding:0;}
	.home-banner{display:block;cursor:pointer;}
	.video-wrapper{position:relative;display:table;overflow:hidden;background-color:#000;}
	.video-playbtn{position:absolute;top:50%;left:50%;margin-left:-49px;margin-top:-35px;z-index:1002;width:78px;height:50px;padding:20px;cursor:pointer;}
	.video-soundbtn{position:absolute;bottom:0.3em;right:0.3em;z-index:1000;cursor:pointer;}
	.video-countdown{position:absolute;bottom:0.3em;right:2.0em;z-index:1000;display:block;height:22px;line-height:22px;color:#fff;text-align:right;letter-spacing:1px;font-size:12px;font-family:"微軟正黑體";text-shadow: 2px 2px #000;margin-right:14px;}
	.video-adlinkbtn{position:absolute;top:0;left:0;z-index:1001;width:100%;height:100%;background-color:rgba(0,0,0,.5);}
</style>


<div id="video-over">
<span class="video-wrapper">
	<svg id="video-playbtn" class="video-playbtn" style="display:block" x="0px" y="0px" width="78px" height="50px" viewBox="0 0 78 50">
		<path fill="#000" opacity="0.650" d="M8,0h62c4.418,0,8,3.582,8,8v34c0,4.418-3.582,8-8,8H8c-4.418,0-8-3.582-8-8V8C0,3.582,3.582,0,8,0z"/>
		<path fill="#fff" d="M51.5,24.5c0,0.997-0.529,1.867-1.316,2.34v0.001L30.573,38.604c-0.007,0.004-0.014,0.008-0.021,0.012l-0.01,0.007h0C30.142,38.861,29.676,39,29.179,39c-1.479,0-2.679-1.217-2.679-2.719l0,0V12.719l0,0c0-1.501,1.199-2.719,2.679-2.719c0.498,0,0.963,0.138,1.362,0.378l0,0l0.01,0.006c0.007,0.004,0.014,0.008,0.021,0.013l19.611,11.762v0C50.971,22.633,51.5,23.503,51.5,24.5z"/>
	</svg>
	<svg id="video-replaybtn" class="video-playbtn" style="display:none" x="0px" y="0px" width="78px" height="50px" viewBox="0 0 78 50">
		<path fill="#000" opacity="0.650" d="M8,0h62c4.418,0,8,3.582,8,8v34c0,4.418-3.582,8-8,8H8c-4.418,0-8-3.582-8-8V8C0,3.582,3.582,0,8,0z"/>
		<path id="video-replaybtn" fill="#fff" d="M53.025,24.439h-0.961h-1.936h-2.904h-5.865l4.551-4.547c-1.533-2.483-4.258-4.153-7.394-4.153c-4.81,0-8.709,3.896-8.709,8.7s3.899,8.7,8.709,8.7c3.396,0,6.304-1.961,7.742-4.792v0.203l0,0c0.207-0.329,0.465-0.601,0.783-0.823c0.107-0.08,0.213-0.159,0.332-0.224c0.117-0.061,0.23-0.11,0.355-0.154c0.297-0.111,0.609-0.191,0.949-0.191c0.324,0,0.623,0.076,0.908,0.184c0.182,0.061,0.344,0.144,0.508,0.238c0.012,0.006,0.023,0.009,0.035,0.016v0.007c0.852,0.506,1.451,1.395,1.451,2.456c0,0.514-0.17,0.971-0.404,1.39h0.002C48.709,35.928,43.998,39,38.516,39C30.499,39,24,32.508,24,24.5S30.499,10,38.516,10c4.72,0,8.871,2.285,11.523,5.77L54,11.813v12.627H53.025z"/>
	</svg>
	<svg id="video-soundoff" class="video-soundbtn" style="display:block" x="0px" y="0px" width="20px" height="20px" viewBox="0 0 20 20">
		<path fill="#000" opacity="0.500" d="M5,0h10c2.762,0,5,2.238,5,5v10c0,2.762-2.238,5-5,5H5c-2.761,0-5-2.238-5-5V5C0,2.238,2.239,0,5,0z"/>
		<path fill="#fff" d="M15.8,5.943L4.698,15.069c-0.072,0.06-0.158,0.095-0.251,0.095c-0.248,0-0.449-0.245-0.449-0.546c0-0.188,0.079-0.354,0.199-0.453l11.102-9.126c0.072-0.059,0.158-0.094,0.252-0.094c0.248,0,0.449,0.244,0.449,0.546C15.999,5.68,15.92,5.846,15.8,5.943z M5.374,8.363c0-0.602,0.401-1.089,0.896-1.09V7.271h2.977l3.042-3.023l0.002,0.004C12.445,4.097,12.641,4,12.856,4c0.468,0,0.848,0.437,0.89,0.991l-8.372,6.873V8.363z M13.755,10.909V12v1.091v1.818c0,0.603-0.402,1.091-0.898,1.091c-0.197,0-0.379-0.08-0.527-0.211l-0.001,0.002l-3.056-2.7H8.69l5.064-4.156V10.909z"/>
	</svg>
	<svg id="video-soundon" class="video-soundbtn" style="display:none" x="0px" y="0px" width="20px" height="20px" viewBox="0 0 20 20">
		<path fill="#000" opacity="0.650" d="M5,0h10c2.762,0,5,2.238,5,5v10c0,2.762-2.238,5-5,5H5c-2.761,0-5-2.238-5-5V5C0,2.238,2.239,0,5,0z"/>
		<path fill="#fff" d="M14.544,14.858l-0.813-0.949c0.703-1.026,1.127-2.326,1.127-3.742c0-1.417-0.424-2.717-1.127-3.743l0.813-0.948C15.452,6.747,16,8.382,16,10.167S15.452,13.587,14.544,14.858z M11.143,15.5c-0.188,0-0.361-0.073-0.502-0.193l-0.001,0.002l-2.917-2.476H4.857c-0.473,0-0.857-0.447-0.857-1V8.5c0-0.552,0.383-0.998,0.855-1V7.498h2.842L10.6,4.726l0.002,0.004C10.75,4.588,10.937,4.5,11.143,4.5C11.616,4.5,12,4.947,12,5.5v3v2.333v1v1V14.5C12,15.053,11.616,15.5,11.143,15.5z"/>
	</svg>
	<span id="video-countdown" class="video-countdown"></span>
	<a id="video-adlinkbtn" class="video-adlinkbtn" style="display:none" href="http://www.pchome.com.tw?videoad" target="_blank"></a>

  	<video width="215" height="98" class="home-banner" controls autoplay loop muted preload="metadata" poster="">
		<source src="http://vstreamdev.mypchome.com.tw/20170119001/20170119001-0240p-0300k-vp8.webm"></source>  
		<source src="http://vstreamdev.mypchome.com.tw/20170119001/20170119001-1000k_640x360.mp4" ></source>
		<source src=""></source>
	</video>

</span>
</div>


<script>
	/*影片屬性&&點擊*/
	var video=document.getElementsByTagName('video')[0];
	
	console.log(video);
	video.style.backgroundColor  = "aliceblue";
	
	
	
	video.removeAttribute('controls');
	video.removeAttribute('autoplay');
	video.removeAttribute('loop');
	video.onclick=function(){video[video.paused?'play':'pause']();};

	/*播放&&重新播放按鈕*/
	var pauseTime=0;
	var playbtn=document.getElementById('video-playbtn');
	var replaybtn=document.getElementById('video-replaybtn');
	playbtn.onclick=function(){
		video.play();
	};
	replaybtn.onclick=function(){
		video.play();
	};

	/*聲音&&靜音按鈕*/
	var sndonbtn=document.getElementById('video-soundon');
	var sndoffbtn=document.getElementById('video-soundoff');
	sndoffbtn.onclick=function(){
		video.muted=false;
		this.style.display='none';
		sndonbtn.style.display='block';

	};
	sndonbtn.onclick=function(){
		video.muted=true;
		this.style.display='none';
		sndoffbtn.style.display='block';
	};

	/*廣告連結按鈕*/
	var adlinkbtn=document.getElementById('video-adlinkbtn');

	/*廣告秒數倒數*/
	var adcountdown=document.getElementById('video-countdown');

	video.addEventListener('progress',ProgresHandler,false);
	video.addEventListener('durationchange',DurationchangeHandler,false);
	video.addEventListener('loadedmetadata',MetadataHandler,false);
	video.addEventListener('canplay',CanplayHandler,false);
	video.addEventListener('play',PlayHandler,false);
	video.addEventListener('pause',PauseHandler,false);
	video.addEventListener('timeupdate',TimeupdateHandler,false);
	video.addEventListener('ended',EndedHandler,false);

	//document.addEventListener("fullscreenchange",FsHandler);
	//document.addEventListener("webkitfullscreenchange",FsHandler);
	//document.addEventListener("mozfullscreenchange",FsHandler);
	//document.addEventListener("MSFullscreenChange",FsHandler);
	
	function ProgresHandler(){
		/*影片下載處理中*/
	}
	function DurationchangeHandler(){

	}
	function MetadataHandler(){
		/*讀取影片資訊*/
		var metadatas=[this.duration,this.videoWidth,this.videoHeight];
		console.log('總秒數: '+metadatas[0]+'\n寬度: '+metadatas[1]+'\n高度: '+metadatas[2]);
	}
	function CanplayHandler(){
		/*可開始播放*/
		/*send pv*/
	}
	function PlayHandler(){
		/*播放影片*/
		/*send click*/
		this.currentTime=pauseTime;
		playbtn.style.display='none';
		replaybtn.style.display='none';
		adlinkbtn.style.display='none';
		adcountdown.style.display='block';
	}
	function PauseHandler(){
		/*暫停影片*/
		/*send click*/
		pauseTime=this.currentTime;
		replaybtn.style.display='block';
		//this.load();
	}
	function EndedHandler(){
		/*影片結束*/
		/*send complete*/
		pauseTime=0;
		video.load();
		adlinkbtn.style.display='block';
		adcountdown.style.display='none';
		//exitFS();
	}
	function TimeupdateHandler(){
		/*cue點更新*/
		/*send second record*/
		console.log(this.currentTime);
		var ttime=Math.ceil(this.duration-this.currentTime);
		adcountdown.innerHTML=formatSecond(ttime);
	}
	function formatSecond(secs) {          
         var min=Math.floor(secs/60);
         var sec=parseInt(secs-(min*60));
         if (sec<10) {sec='0'+sec;}
         return min+':'+sec;
     }

	function FsHandler(){
		if(document.fullscreenElement||document.webkitFullscreenElement||document.mozFullScreenElement||document.msFullscreenElement){
		}else{
		}
	}
	function exitFS(){
		if(document.exitFullscreen){
			document.exitFullscreen();
		}else if(document.webkitExitFullscreen){
			document.webkitExitFullscreen();
		}else if(
			document.mozCancelFullScreen){
			document.mozCancelFullScreen();
		}else if(
			document.msExitFullscreen){
			document.msExitFullscreen();
		}
	}

</script>



<!--pchome logo ico start here-->
<style>
/* logo 位置隨 iframe width 置右上 or 靠左上 */
/* right:0px -->置右 */
/* left:0px  -->靠左 */

.p8044_ad_icon {
    text-indent: -10000px;
    position: absolute;
    top: 0px;
    right: 0px;
    z-index: auto;
    width: 20px;
    height: 18px;
    line-height: 18px;
    cursor: pointer;
    text-rendering: geometricPrecision;
}

.p8044_ad_icon {
    background: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABQAAAASCAYAAABb0P4QAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAShJREFUeNpi/P//PwM1ARMDlQELugAjIyOIYgZiQSzqfwLxVyD+B+Jg8x0LDotYMzMz5ZuamorRJV6/fn3h06dPCy0sLF5i1QmyBRlDAe/27dsj/uMA//79ewek1NH1gjCuMGSVk5NTQuLfBxryEClYQMGRQ0qksAoKCsoj8bOYmZl1b968WYskpkiSgdzc3HAX7tmz5ykokv7+/fsPSc0jomIZ5COQgRwcHHADZWVl/YGuY1ZWVs5AUreb2Ehh1dDQUAOy//zHDY4BMTOxkcJaWFioAE2L6OA7EE8EYk9gxPwl1susOjo6yBGyAIingMIMaMhrknMKyEAJCQnkJHMcaNBZSvIyKx8fH7ILb5KUmbHlFCB9ACkCpInVC8KMg774orqBAAEGAOSN3py5SBQXAAAAAElFTkSuQmCC) #84a7de 0 0 no-repeat;
}

.p8044_ad_icon:hover {
    text-indent: 0px;
    padding-left: 52px;
    width: 62px;
    font-size: 12px;
    color: #FFF;
    text-shadow: -1px -1px #84a7de
}

.p8044_ad_icon:hover {
    background: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADIAAAASCAYAAAAZk42HAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAuJJREFUeNrclmGEVFEUx9/MjmGZxLIsj6FMIpYlStnEZlM2rWFZyow+1ZdWkWiJlCilFJFW2dWHGCKtVqs0Wo2W5TEso2WIIZZhGYYYxnQOv8vxvGWnT2sOf3PPe/eec/7nnHvmxTqdjtcLksjn871BxKz3CW6F3v8VLAMrScGY4IjAFxQFXwVbXfofwc4CZ6cEJcGQoMy7Iv5GWU/z67Pnsp6xREZ5GJZrgqeCG+gnBfMQd6LnTgk+43SPoLkDImcEi4JxQUEwQfAvDVENOiNImTg1hjn0Y2ojboweNOsCbC0ZNXRe8CVEwkkLEn92SMJVPGsqviKoCTbwl+b5gKDCepU9Vfypr6atSMYEdFHQFnyHfR9G5zmsbTAj+CDYK3hiMrbeRWs9D+kLocRcZ10yz9+FzsyE74irSBUSSfrQI/AsmVHRCbFksqrEb5rzE9hTor+Njyme13lXJxHT2Fml6lrV9yRX22+Ndyr92PGpjHZPO6oiavgj/Zk2WRhnXTMkvIjzGshV1g8E+8nuN8Gw2a9VPE5yXgkaVN5Vdon70o9+VLCJnYxp5QuCc+6O+MaATzCOhN6VO+ZebG7TJq6igeA0vZvE6VtIrNEuFfzdNee2GBjrJo5JMwlVf4292yQhoPqZRCibHlOiSqlLtEAb3SMgn/K7UTxgbMxyWRsE20c11cZZE9iz0LkCRGv4mGOk19nXpEIq94GTVCJiYt1jeoSlyD4t9Q8zIi8JrkCuRRJSRm9CRokMss6aSzxsKu+ZypfZ63RnR+0fYD2C3XJURarbtM5DAhjC+KOIPW5QZIxe5sLrmV9mr7bQY8FP9A2Cyxg9bUZ6mV99/4lk6UR9oZMrHqpI07RMWLTkJyIueoCzgFZwc9/pLVpqkXbTAN9wHxoggLQPwRVacJB3y7T2JDbHSOisG9GxXC6nv4domVYX/wOHIVffTd9alf84G+ymj8ZYr3zGx70ekZ4h8k+AAQCoOcTt/fuWhQAAAABJRU5ErkJggg==) #84a7de 0 0 no-repeat;
}
</style>    
<a href="http://show.pchome.com.tw" target="_blank" style="text-decoration:none">
    <div class="p8044_ad_icon">提供的廣告</div>
</a>
<!--pchome logo ico end here-->

</body>
</html>