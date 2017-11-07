	var iframeArrayData = window.parent.document.getElementsByTagName("iframe");
	var iframeArray =  [];
	for(var i = 0; i< iframeArrayData.length; i++){
		if(iframeArrayData[i].id == "pchome8044_ad_frame1"){
			iframeArray.push(iframeArrayData[i]);
		}
	}
	
	var scrollTop = window.parent.parent.document.body.scrollTop || window.parent.parent.document.documentElement.scrollTop;
	var timeVideo;
	var iframeInfoMap = new Object();
	
	/*廣告行為資訊*/
	document.addEventListener('DOMContentLoaded', function () {
		for(var i = 0; i< iframeArray.length; i++){
			iframeArray[i].onload = function(){
				var video = this.contentDocument.body.querySelector(".home-banner");
				var adType = (video == null) ? "MEDIA" : "VIDEO";
				var adInfo = null;
				
				if(video != null){
					var playbtn = video.parentElement.querySelector('#video-playbtn');
					var pausebtn = video.parentElement.querySelector('#video-pausebtn');
					var replaybtn = video.parentElement.querySelector('#video-replaybtn');
					var videoSoundoff = video.parentElement.parentElement.parentElement.querySelector('#video-soundoff');
					var videoSoundOn = video.parentElement.parentElement.parentElement.querySelector('#video-soundon');
					var videoCountdown = video.parentElement.parentElement.parentElement.querySelector('#video-countdown');
					var videoIconbox = video.parentElement.parentElement.parentElement.querySelector('.video-iconbox');
					var videoBtn = video.parentElement.querySelector('.video-btn');
					
					
					playbtn.addEventListener('click', function() {
						PlayHandler(video);
					});
					
					replaybtn.addEventListener('click', function() {
						ReplayHandler(video);
					});
					
					video.addEventListener('click', function() {
						PauseHandler(video);
					});
					
					pausebtn.addEventListener('click', function() {
						PlayHandler(video);
					});
					
					videoSoundoff.addEventListener('click', function() {
						var videoSoundoffObj = this.parentElement.querySelector('#video-soundoff');
						var videoSoundOnObj = this.parentElement.querySelector('#video-soundon');
						VideoSound(false,videoSoundoffObj,videoSoundOnObj,video);
					});
					videoSoundOn.addEventListener('click', function() {
						var videoSoundoffObj = this.parentElement.querySelector('#video-soundoff');
						var videoSoundOnObj = this.parentElement.querySelector('#video-soundon');
						VideoSound(true,videoSoundoffObj,videoSoundOnObj,video);
					});
					
					var css = document.createElement("style");
					css.type = "text/css";
					
					var adratio = this.height / this.width;
					var adw = 250;
					var	adh = adw * adratio;
					
					var barh = 30;
					if(this.width == 970 && this.height == 250){
						barh = 7.7304;
						videoCountdown.setAttribute("style", "margin-top:-10px;font-size:6px;");
						videoIconbox.setAttribute("style", "margin-top:-10px;");
						videoBtn.setAttribute("style", "margin-top:-10px;");
						videoBtn.setAttribute("style", "width:35px;margin-left:-35px");
					}else if(this.width == 950 && this.height == 390) {
						barh = 7.9;
						videoCountdown.setAttribute("style", "margin-top:-10px;font-size:6px;");
						videoIconbox.setAttribute("style", "margin-top:-10px;");
						videoBtn.setAttribute("style", "width:35px;margin-left:-35px");
					}
					var vdow;
					var vdoh = adh - barh;
					var ratio = 0.5625;
					var xcenter=false;
					var ycenter=true;
					var xpos;
					var ypos;
					var imgWidth;
					var imgHeight;
					
					
					if (vdoh/adw < ratio){
						vdow = vdoh / ratio;
						xpos=(!xcenter)?0:(adw-vdow)/2;
						ypos=0;
						vdow+="px";
						
						if(this.width == 970 && this.height == 250){
							console.log('vdoh:'+vdoh);
							console.log('ratio:'+ratio);
							console.log('vdow:'+vdoh / ratio);
							vdow = "100.77px";
							vdoh = 34.42;
							imgWidth = adw - 100.77;
							imgHeight = adh;
						}else if(this.width == 950 && this.height == 390){
							console.log('vdoh:'+vdoh);
							console.log('ratio:'+ratio);
							console.log('vdow:'+vdoh / ratio);
							imgWidth = adw - 168;
							imgHeight = adh;
							vdow = "168.25px";
							vdoh = 72;
						}else{
							imgWidth = 250;
							imgHeight = adh;
						}
						
					}else{
						vdow=100;
						xpos=0
						ypos=(!ycenter)?0:(adh-adw*0.5625-30)/2;
						vdow+="%";
					}
//					imgWidth = adw - vdow;
					
					css.innerHTML = ".adw{width:"+adw+"px}.adh{height:"+adh+"px}.vdow{width:"+vdow+"}.xpos{left:"+xpos+"px}.ypos{top:"+ypos+"px}";
					this.contentDocument.childNodes[0].getElementsByTagName("head")[0].appendChild(css);
					
					var adbg = this.contentDocument.childNodes[0].querySelector('.adbg');
					
					console.log('imgWidth:'+imgWidth);
					
	
					
					adbg.setAttribute("style", "background-size:"+imgWidth+"px "+imgHeight+"px");
					/*
					if(this.height == 102.63){
						adbg.setAttribute("style", "background-size:188.8px 64.42px");
					}else	if(this.height == 64.43){
						adbg.setAttribute("style", "background-size:121px 102.63px");
					}
					*/
					
					this.width = 250;
					this.height = adh;
					
					/*
					if(this.width == 640){
						this.width = adw;
						this.height = 390 * 0.2631;
					}else if(this.width == 391){
						this.width = adw;
						this.height = 250 * 0.2577;
					}else{
					
					}
					*/
				}
			};
		}
		
		/*重新播放*/
		function ReplayHandler(video){
			if(video == null || video == undefined){
				return false;
			}
			
			var index = getIframeIndex(video);
			timeVideo = setInterval('alex('+index+')',"300");
			iframeInfoMap["iframe"+index] = {timmer:timeVideo};
			
			var playbtn = video.parentElement.querySelector('#video-playbtn');
			var pausebtn = video.parentElement.querySelector('#video-pausebtn');
			var replaybtn = video.parentElement.querySelector('#video-replaybtn');
			var adlinkbtn1 = video.parentElement.parentElement.parentElement.parentElement.querySelector('#ad-linkbtn');
			var adlinkbtn2 = video.parentElement.querySelector('#video-linkbtn');
			
			playbtn.style.display='none';
			pausebtn.style.display='none';
			replaybtn.style.display='none';
			adlinkbtn1.style.opacity=0;
			adlinkbtn2.style.display='none';
			video.play();
		}
		
		/*點擊開始播放*/
		function PlayHandler(video){
			if(video == null || video == undefined){
				return false;
			}
			var playbtn = video.parentElement.querySelector('#video-playbtn');
			var pausebtn = video.parentElement.querySelector('#video-pausebtn');
			var replaybtn = video.parentElement.querySelector('#video-replaybtn');
			var adlinkbtn1 = video.parentElement.parentElement.parentElement.parentElement.querySelector('#ad-linkbtn');
			var adlinkbtn2 = video.parentElement.querySelector('#video-linkbtn');
			
			playbtn.style.display='none';
			pausebtn.style.display='none';
			replaybtn.style.display='none';
			adlinkbtn1.style.opacity=0;
			adlinkbtn2.style.display='none';
			video.play();
			var index = getIframeIndex(video);
			timeVideo = setInterval('alex('+index+')',"300");
			iframeInfoMap["iframe"+index] = {timmer:timeVideo};
		}
		
		/*點擊廣告暫停播放*/
		function PauseHandler(video){
			if(video == null || video == undefined){
				return false;
			}
			var index = getIframeIndex(video);
			clearInterval(iframeInfoMap["iframe"+index].timmer);
			var playbtn = video.parentElement.querySelector('#video-playbtn');
			var pausebtn = video.parentElement.querySelector('#video-pausebtn');
			var adcountdown = video.parentElement.parentElement.parentElement.querySelector('#video-countdown');
			var adlinkbtn1 = video.parentElement.parentElement.parentElement.parentElement.querySelector('#ad-linkbtn');
			var adlinkbtn2 = video.parentElement.querySelector('#video-linkbtn');
			pausebtn.style.display='block';
			adcountdown.style.display='block';
			adlinkbtn1.style.opacity=1;
			adlinkbtn2.style.display='block';
			video.pause();
		}
		
		/*點擊聲音，true為關閉聲音*/
		function VideoSound(flag,videoSoundoffObj,videoSoundOnObj,video){
			if(flag){
				video.muted = true;
				videoSoundOnObj.style.display='none';
				videoSoundoffObj.style.display='block';
			}else {
				video.muted = false;
				video.volume = 0.1;
				videoSoundOnObj.style.display='block';
				videoSoundoffObj.style.display='none';
				var index = getIframeIndex(video);
			}
		}
		
	}, false);
	
	
	/*影片時間格式*/
	function formatSecond(secs) {          
        var min = Math.floor(secs / 60);
        var sec = parseInt(secs-(min * 60));
        if (sec < 10) {
        	sec= '0' + sec;
        }
        return min+' : '+sec;
    }

	function alex(index){
		var video = iframeArray[index].contentDocument.body.querySelector(".home-banner");
		if(video == null || video == undefined){
			clearInterval(timeVideo);
			return false;
		}
		
		var replaybtn = video.parentElement.querySelector('#video-replaybtn');
		var pausebtn = video.parentElement.querySelector('#video-pausebtn');
		var adcountdown = video.parentElement.parentElement.parentElement.querySelector('#video-countdown');
		var ttime = Math.ceil(video.duration - video.currentTime);
		var percent = Math.ceil((video.currentTime / video.duration) * 100);
		var adlinkbtn1 = video.parentElement.parentElement.parentElement.parentElement.querySelector('#ad-linkbtn');
		var adlinkbtn2 = video.parentElement.querySelector('#video-linkbtn');
		
		if(!video.paused){
			adcountdown.style.display='block';
			adcountdown.innerHTML= "影片倒數 "+ formatSecond(ttime);
		}else{
			if(ttime == 0){
				clearInterval(iframeInfoMap["iframe"+index].timmer);
				adcountdown.style.display='none';
				replaybtn.style.display='block';
				pausebtn.style.display='none';
				adcountdown.innerHTML= "";
				adlinkbtn1.style.opacity=1;
				adlinkbtn2.style.display='block';
				video.load();
			}
		}
	}
	
	/*取得iframe index*/
	function getIframeIndex(video){
		for(var i = 0; i< iframeArray.length; i++){
			if(iframeArray[i].contentDocument.body.querySelector(".home-banner") == video){
				return i;
			}
		}
	}