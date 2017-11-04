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
//					this.height = this.height - 30;
					//console.log('>>>>>>>>>>>>>this.height:'+this.height);
					
					
					var adratio = this.height / this.width;
					var adw = 230;
					
					var adh = adw * adratio;
				//	console.log('>>>>>>>>>>>>>adh:'+adh);
					var barh = 30
					var vdow;
					var vdoh = adh - barh;
					var ratio = 0.5625;

					var xcenter=false;
					var ycenter=true;
					var xpos;
					var ypos;

					var adbg = "";
					var poster = "img/video/missdior.gif"
					
					if (vdoh/adw<ratio){
						vdow=vdoh/ratio;
						xpos=(!xcenter)?0:(adw-vdow)/2;
						ypos=0;
						vdow+="px";
					}else{
						vdow=100;
						xpos=0
						ypos=(!ycenter)?0:(adh-adw*0.5625-30)/2;
						vdow+="%";
					}
					
					css.innerHTML = ".adw{width:"+adw+"px}.adh{height:"+adh+"px}.vdow{width:"+vdow+"}.xpos{left:"+xpos+"px}.ypos{top:"+ypos+"px}";
					this.contentDocument.childNodes[0].getElementsByTagName("head")[0].appendChild(css);
					
					var adbg = this.contentDocument.childNodes[0].querySelector('.adbg');
					
					
					
//					adbg.setAttribute("style", "height:250px");
					
					console.log(adbg);
					
					console.log(adbg.style.background);
					
					
					this.width = 230;
					this.height = adh;
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
			pausebtn.style.display='block';
			adcountdown.style.display='block';
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