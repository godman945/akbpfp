﻿/*!
 * jQuery Tools v1.2.5 - The missing UI library for the Web
 *
 * overlay/overlay.js
 * overlay/overlay.apple.js
 * tabs/tabs.js
 * toolbox/toolbox.history.js
 *
 * NO COPYRIGHTS OR LICENSES. DO WHAT YOU LIKE.
 *
 * http://flowplayer.org/tools/
 *
*/

//overlay
eval(function(p,a,c,k,e,r){e=function(c){return(c<a?'':e(parseInt(c/a)))+((c=c%a)>35?String.fromCharCode(c+29):c.toString(36))};if(!''.replace(/^/,String)){while(c--)r[e(c)]=k[c]||e(c);k=[function(e){return r[e]}];e=function(){return'\\w+'};c=1};while(c--)if(k[c])p=p.replace(new RegExp('\\b'+e(c)+'\\b','g'),k[c]);return p}('(4($){$.q=$.q||{13:\'@1s\'};$.q.5={14:4(a,b,c){z[a]=[b,c]},15:{8:A,B:l,L:l,16:\'1t\',s:\'17\',C:!$.18.1u||$.18.13>6,k:\'D\',t:M,E:A,19:l,1a:\'1v\',N:A,m:\'10%\'}};n h=[],z={};$.q.5.14(\'17\',4(a,b){n c=9.O(),w=$(F);3(!c.C){a.m+=w.1w();a.k+=w.1x()}a.1y=c.C?\'C\':\'1z\';9.P().1b(a).1A(c.1a,b)},4(a){9.P().1B(9.O().16,a)});4 G(c,d){n f=9,u=c.1C(f),w=$(F),o,5,r,j=$.q.Q&&(d.E||d.Q),p=v.1D().1E().1F(10);3(j){3(R j==\'S\'){j={1G:j}}j.B=j.L=M}n g=d.N||c.1H("1I");5=g?$(g):A||c;3(!5.T){1c"1J 1K U G: "+g;}3(c&&c.1L(5)==-1){c.x(4(e){f.t(e);7 e.1M()})}$.1d(f,{t:4(e){3(f.V()){7 f}n a=z[d.s];3(!a){1c"G: 1N U s : \\""+d.s+"\\"";}3(d.19){$.W(h,4(){9.8(e)})}e=e||$.1e();e.H="X";u.I(e);3(e.1f()){7 f}r=l;3(j){$(5).Q(j)}n b=d.m,k=d.k,Y=5.1O({1g:l}),Z=5.1P({1g:l});3(R b==\'S\'){b=b==\'D\'?v.J((w.K()-Z)/2,0):1h(b,10)/1i*w.K()}3(k==\'D\'){k=v.J((w.1j()-Y)/2,0)}a[0].1k(f,{m:b,k:k},4(){3(r){e.H="1l";u.I(e)}});3(j&&d.B){$.E.1Q().1R("x",f.8)}3(d.B){$(11).y("x."+p,4(e){3(!$(e.N).1S(5).T){f.8(e)}})}3(d.L){$(11).y("1m."+p,4(e){3(e.1T==1U){f.8(e)}})}$(F).y("1n."+p,4(){3(r){3(R d.m==\'S\'){b=d.m==\'D\'?v.J((w.K()-Z)/2,0):1h(b,10)/1i*w.K()}5.1b({k:v.J((w.1j()-Y)/2,0),m:b})}});7 f},8:4(e){3(!f.V()){7 f}e=e||$.1e();e.H="1o";u.I(e);3(e.1f()){7}r=M;z[d.s][1].1k(f,4(){e.H="1p";u.I(e)});$(11).12("x."+p).12("1m."+p);$(F).12("1n."+p);3(j){$.E.8()}7 f},P:4(){7 5},1V:4(){7 c},1W:4(){7 o},V:4(){7 r},O:4(){7 d}});$.W("X,1X,1l,1o,1p".1Y(","),4(i,b){3($.1q(d[b])){$(f).y(b,d[b])}f[b]=4(a){3(a){$(f).y(b,a)}7 f}});o=5.U(d.8||".8");3(!o.T&&!d.8){o=$(\'<a 1Z="8"></a>\');5.20(o)}o.x(4(e){f.8(e)});3(d.t){f.t()}}$.21.5=4(a){n b=9.1r("5");3(b){7 b}3($.1q(a)){a={X:a}}a=$.1d(l,{},$.q.5.15,a);9.W(4(){b=22 G($(9),a);h.23(b);$(9).1r("5",b)});7 a.24?b:9}})(25);',62,130,'|||if|function|overlay||return|close|this||||||||||maskConf|left|true|top|var|closers|uid|tools|opened|effect|load|fire|Math||click|bind|effects|null|closeOnClick|fixed|center|mask|window|Overlay|type|trigger|max|height|closeOnEsc|false|target|getConf|getOverlay|expose|typeof|string|length|find|isOpened|each|onBeforeLoad|oWidth|oHeight||document|unbind|version|addEffect|conf|closeSpeed|default|browser|oneInstance|speed|css|throw|extend|Event|isDefaultPrevented|margin|parseInt|100|width|call|onLoad|keydown|resize|onBeforeClose|onClose|isFunction|data|VERSION|fast|msie|normal|scrollTop|scrollLeft|position|absolute|fadeIn|fadeOut|add|random|toString|slice|color|attr|rel|Could|not|index|preventDefault|cannot|outerWidth|outerHeight|getMask|one|parents|keyCode|27|getTrigger|getClosers|onStart|split|class|prepend|fn|new|push|api|jQuery'.split('|'),0,{}));

(function(a){var b=a.tools.overlay,c=a(window);a.extend(b.conf,{start:{top:null,left:null},fadeInSpeed:"fast",zIndex:9999});function d(a){var b=a.offset();return{top:b.top+a.height()/2,left:b.left+a.width()/2}}var e=function(b,e){var f=this.getOverlay(),g=this.getConf(),h=this.getTrigger(),i=this,j=f.outerWidth({margin:!0}),k=f.data("img"),l=g.fixed?"fixed":"absolute";if(!k){var m=f.css("backgroundImage");if(!m)throw"background-image CSS property not set for overlay";m=m.slice(m.indexOf("(")+1,m.indexOf(")")).replace(/\"/g,""),f.css("backgroundImage","none"),k=a("<img src=\""+m+"\"/>"),k.css({border:0,display:"none"}).width(j),a("body").append(k),f.data("img",k)}var n=g.start.top||Math.round(c.height()/2),o=g.start.left||Math.round(c.width()/2);if(h){var p=d(h);n=p.top,o=p.left}g.fixed?(n-=c.scrollTop(),o-=c.scrollLeft()):(b.top+=c.scrollTop(),b.left+=c.scrollLeft()),k.css({position:"absolute",top:n,left:o,width:0,zIndex:g.zIndex}).show(),b.position=l,f.css(b),k.animate({top:f.css("top"),left:f.css("left"),width:j},g.speed,function(){f.css("zIndex",g.zIndex+1).fadeIn(g.fadeInSpeed,function(){i.isOpened()&&!a(this).index(f)?e.call():f.hide()})}).css("position",l)},f=function(b){var e=this.getOverlay().hide(),f=this.getConf(),g=this.getTrigger(),h=e.data("img"),i={top:f.start.top,left:f.start.left,width:0};g&&a.extend(i,d(g)),f.fixed&&h.css({position:"absolute"}).animate({top:"+="+c.scrollTop(),left:"+="+c.scrollLeft()},0),h.animate(i,f.closeSpeed,b)};b.addEffect("apple",e,f)})(jQuery);
(function(a){a.tools=a.tools||{version:"v1.2.5"},a.tools.tabs={conf:{tabs:"a",current:"current",onBeforeClick:null,onClick:null,effect:"default",initialIndex:0,event:"click",rotate:!1,history:!1},addEffect:function(a,c){b[a]=c}};var b={"default":function(a,b){this.getPanes().hide().eq(a).show(),b.call()},fade:function(a,b){var c=this.getConf(),d=c.fadeOutSpeed,e=this.getPanes();d?e.fadeOut(d):e.hide(),e.eq(a).fadeIn(c.fadeInSpeed,b)},slide:function(a,b){this.getPanes().slideUp(200),this.getPanes().eq(a).slideDown(400,b)},ajax:function(a,b){this.getPanes().eq(0).load(this.getTabs().eq(a).attr("href"),b)}},c;a.tools.tabs.addEffect("horizontal",function(b,d){c||(c=this.getPanes().eq(0).width()),this.getCurrentPane().animate({width:0},function(){a(this).hide()}),this.getPanes().eq(b).animate({width:c},function(){a(this).show(),d.call()})});function d(c,d,e){var f=this,g=c.add(this),h=c.find(e.tabs),i=d.jquery?d:c.children(d),j;h.length||(h=c.children()),i.length||(i=c.parent().find(d)),i.length||(i=a(d)),a.extend(this,{click:function(c,d){var i=h.eq(c);typeof c=="string"&&c.replace("#","")&&(i=h.filter("[href*="+c.replace("#","")+"]"),c=Math.max(h.index(i),0));if(e.rotate){var k=h.length-1;if(c<0)return f.click(k,d);if(c>k)return f.click(0,d)}if(!i.length){if(j>=0)return f;c=e.initialIndex,i=h.eq(c)}if(c===j)return f;d=d||a.Event(),d.type="onBeforeClick",g.trigger(d,[c]);if(!d.isDefaultPrevented()){b[e.effect].call(f,c,function(){d.type="onClick",g.trigger(d,[c])}),j=c,h.removeClass(e.current),i.addClass(e.current);return f}},getConf:function(){return e},getTabs:function(){return h},getPanes:function(){return i},getCurrentPane:function(){return i.eq(j)},getCurrentTab:function(){return h.eq(j)},getIndex:function(){return j},next:function(){return f.click(j+1)},prev:function(){return f.click(j-1)},destroy:function(){h.unbind(e.event).removeClass(e.current),i.find("a[href^=#]").unbind("click.T");return f}}),a.each("onBeforeClick,onClick".split(","),function(b,c){a.isFunction(e[c])&&a(f).bind(c,e[c]),f[c]=function(b){b&&a(f).bind(c,b);return f}}),e.history&&a.fn.history&&(a.tools.history.init(h),e.event="history"),h.each(function(b){a(this).bind(e.event,function(a){f.click(b,a);return a.preventDefault()})}),i.find("a[href^=#]").bind("click.T",function(b){f.click(a(this).attr("href"),b)}),location.hash&&e.tabs=="a"&&c.find("[href="+location.hash+"]").length?f.click(location.hash):(e.initialIndex===0||e.initialIndex>0)&&f.click(e.initialIndex)}a.fn.tabs=function(b,c){var e=this.data("tabs");e&&(e.destroy(),this.removeData("tabs")),a.isFunction(c)&&(c={onBeforeClick:c}),c=a.extend({},a.tools.tabs.conf,c),this.each(function(){e=new d(a(this),b,c),a(this).data("tabs",e)});return c.api?e:this}})(jQuery);
(function($){var c,iframe,links,inited;$.tools=$.tools||{version:'@VERSION'};$.tools.history={init:function(b){if(inited){return}if($.browser.msie&&$.browser.version<'8'){if(!iframe){iframe=$("<iframe/>").attr("src","javascript:false;").hide().get(0);$("body").prepend(iframe);setInterval(function(){var a=iframe.contentWindow.document,h=a.location.hash;if(c!==h){$(window).trigger("hash",h)}},100);setIframeLocation(location.hash||'#')}}else{setInterval(function(){var h=location.hash;if(h!==c){$(window).trigger("hash",h)}},100)}links=!links?b:links.add(b);b.click(function(e){var a=$(this).attr("href");if(typeof(a)==='undefined'){if(this.nodeName.toLowerCase()!=='a'){a=$(this).find('a').attr("href")}}if(iframe){setIframeLocation(a)}if(a.slice(0,1)!="#"){location.href="#"+a;return e.preventDefault()}});inited=true}};function setIframeLocation(h){if(h){var a=iframe.contentWindow.document;a.open().close();a.location.hash=h}}$(window).bind("hash",function(e,h){if(h){links.filter(function(){var a=$(this).attr("href");return a==h||a==h.replace("#","")}).trigger("history",[h])}else{links.eq(0).trigger("history",[h])}c=h});$.fn.history=function(a){$.tools.history.init(this);return this.bind("history",a)}})(jQuery);

/**
 * jGestures: a jQuery plugin for gesture events
 * Copyright 2010-2011 Neue Digitale / Razorfish GmbH
 * Copyright 2011-2012, Razorfish GmbH
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * @copyright Razorfish GmbH
 * @author martin.krause@razorfish.de
 * @version 0.90-shake
 * @requires jQuery JavaScript Library v1.4.2 - http://jquery.com/- Copyright 2010, John Resig- Dual licensed under the MIT or GPL Version 2 licenses. http://jquery.org/license
 */
;(function(c){c.jGestures={};c.jGestures.defaults={};c.jGestures.defaults.thresholdShake={requiredShakes:10,freezeShakes:100,frontback:{sensitivity:10},leftright:{sensitivity:10},updown:{sensitivity:10}};c.jGestures.defaults.thresholdPinchopen=0.05;c.jGestures.defaults.thresholdPinchmove=0.05;c.jGestures.defaults.thresholdPinch=0.05;c.jGestures.defaults.thresholdPinchclose=0.05;c.jGestures.defaults.thresholdRotatecw=5;c.jGestures.defaults.thresholdRotateccw=5;c.jGestures.defaults.thresholdMove=20;c.jGestures.defaults.thresholdSwipe=100;c.jGestures.data={};c.jGestures.data.capableDevicesInUserAgentString=["iPad","iPhone","iPod","Mobile Safari"];c.jGestures.data.hasGestures=(function(){var k;for(k=0;k<c.jGestures.data.capableDevicesInUserAgentString.length;k++){if(navigator.userAgent.indexOf(c.jGestures.data.capableDevicesInUserAgentString[k])!==-1){return true}}return false})();c.hasGestures=c.jGestures.data.hasGestures;c.jGestures.events={touchstart:"jGestures.touchstart",touchendStart:"jGestures.touchend;start",touchendProcessed:"jGestures.touchend;processed",gesturestart:"jGestures.gesturestart",gestureendStart:"jGestures.gestureend;start",gestureendProcessed:"jGestures.gestureend;processed"};jQuery.each({orientationchange_orientationchange01:"orientationchange",gestureend_pinchopen01:"pinchopen",gestureend_pinchclose01:"pinchclose",gestureend_rotatecw01:"rotatecw",gestureend_rotateccw01:"rotateccw",gesturechange_pinch01:"pinch",gesturechange_rotate01:"rotate",touchstart_swipe13:"swipemove",touchstart_swipe01:"swipeone",touchstart_swipe02:"swipetwo",touchstart_swipe03:"swipethree",touchstart_swipe04:"swipefour",touchstart_swipe05:"swipeup",touchstart_swipe06:"swiperightup",touchstart_swipe07:"swiperight",touchstart_swipe08:"swiperightdown",touchstart_swipe09:"swipedown",touchstart_swipe10:"swipeleftdown",touchstart_swipe11:"swipeleft",touchstart_swipe12:"swipeleftup",touchstart_tap01:"tapone",touchstart_tap02:"taptwo",touchstart_tap03:"tapthree",touchstart_tap04:"tapfour",devicemotion_shake01:"shake",devicemotion_shake02:"shakefrontback",devicemotion_shake03:"shakeleftright",devicemotion_shake04:"shakeupdown"},function(l,k){jQuery.event.special[k]={setup:function(){var r=l.split("_");var o=r[0];var m=r[1].slice(0,r[1].length-2);var p=jQuery(this);var q;var n;if(!p.data("ojQueryGestures")||!p.data("ojQueryGestures")[o]){q=p.data("ojQueryGestures")||{};n={};n[o]=true;c.extend(true,q,n);p.data("ojQueryGestures",q);if(c.hasGestures){switch(m){case"orientationchange":p.get(0).addEventListener("orientationchange",a,false);break;case"shake":case"shakefrontback":case"shakeleftright":case"shakeupdown":case"tilt":p.get(0).addEventListener("devicemotion",b,false);break;case"tap":case"swipe":case"swipeup":case"swiperightup":case"swiperight":case"swiperightdown":case"swipedown":case"swipeleftdown":case"swipeleft":p.get(0).addEventListener("touchstart",h,false);break;case"pinchopen":case"pinchclose":case"rotatecw":case"rotateccw":p.get(0).addEventListener("gesturestart",e,false);p.get(0).addEventListener("gestureend",i,false);break;case"pinch":case"rotate":p.get(0).addEventListener("gesturestart",e,false);p.get(0).addEventListener("gesturechange",f,false);break}}else{switch(m){case"tap":case"swipe":p.bind("mousedown",h);break;case"orientationchange":case"pinchopen":case"pinchclose":case"rotatecw":case"rotateccw":case"pinch":case"rotate":case"shake":case"tilt":break}}}return false},add:function(n){var m=jQuery(this);var o=m.data("ojQueryGestures");o[n.type]={originalType:n.type};return false},remove:function(n){var m=jQuery(this);var o=m.data("ojQueryGestures");o[n.type]=false;m.data("ojQueryGestures",o);return false},teardown:function(){var r=l.split("_");var o=r[0];var m=r[1].slice(0,r[1].length-2);var p=jQuery(this);var q;var n;if(!p.data("ojQueryGestures")||!p.data("ojQueryGestures")[o]){q=p.data("ojQueryGestures")||{};n={};n[o]=false;c.extend(true,q,n);p.data("ojQueryGestures",q);if(c.hasGestures){switch(m){case"orientationchange":p.get(0).removeEventListener("orientationchange",a,false);break;case"shake":case"shakefrontback":case"shakeleftright":case"shakeupdown":case"tilt":p.get(0).removeEventListener("devicemotion",b,false);break;case"tap":case"swipe":case"swipeup":case"swiperightup":case"swiperight":case"swiperightdown":case"swipedown":case"swipeleftdown":case"swipeleft":case"swipeleftup":p.get(0).removeEventListener("touchstart",h,false);p.get(0).removeEventListener("touchmove",g,false);p.get(0).removeEventListener("touchend",j,false);break;case"pinchopen":case"pinchclose":case"rotatecw":case"rotateccw":p.get(0).removeEventListener("gesturestart",e,false);p.get(0).removeEventListener("gestureend",i,false);break;case"pinch":case"rotate":p.get(0).removeEventListener("gesturestart",e,false);p.get(0).removeEventListener("gesturechange",f,false);break}}else{switch(m){case"tap":case"swipe":p.unbind("mousedown",h);p.unbind("mousemove",g);p.unbind("mouseup",j);break;case"orientationchange":case"pinchopen":case"pinchclose":case"rotatecw":case"rotateccw":case"pinch":case"rotate":case"shake":case"tilt":break}}}return false}}});function d(k){k.startMove=(k.startMove)?k.startMove:{startX:null,startY:null,timestamp:null};var l=new Date().getTime();var m;var n;if(k.touches){n=[{lastX:k.deltaX,lastY:k.deltaY,moved:null,startX:k.screenX-k.startMove.screenX,startY:k.screenY-k.startMove.screenY}];m={vector:k.vector||null,orientation:window.orientation||null,lastX:((n[0].lastX>0)?+1:((n[0].lastX<0)?-1:0)),lastY:((n[0].lastY>0)?+1:((n[0].lastY<0)?-1:0)),startX:((n[0].startX>0)?+1:((n[0].startX<0)?-1:0)),startY:((n[0].startY>0)?+1:((n[0].startY<0)?-1:0))};n[0].moved=Math.sqrt(Math.pow(Math.abs(n[0].startX),2)+Math.pow(Math.abs(n[0].startY),2))}return{type:k.type||null,originalEvent:k.event||null,delta:n||null,direction:m||{orientation:window.orientation||null,vector:k.vector||null},duration:(k.duration)?k.duration:(k.startMove.timestamp)?l-k.timestamp:null,rotation:k.rotation||null,scale:k.scale||null,description:k.description||[k.type,":",k.touches,":",((n[0].lastX!=0)?((n[0].lastX>0)?"right":"left"):"steady"),":",((n[0].lastY!=0)?((n[0].lastY>0)?"down":"up"):"steady")].join("")}}function a(l){var k=["landscape:clockwise:","portrait:default:","landscape:counterclockwise:","portrait:upsidedown:"];c(window).triggerHandler("orientationchange",{direction:{orientation:window.orientation},description:["orientationchange:",k[((window.orientation/90)+1)],window.orientation].join("")})}function b(r){var k;var t=jQuery(window);var o=t.data("ojQueryGestures");var m=c.jGestures.defaults.thresholdShake;var n=o.oDeviceMotionLastDevicePosition||{accelerationIncludingGravity:{x:0,y:0,z:0},shake:{eventCount:0,intervalsPassed:0,intervalsFreeze:0},shakeleftright:{eventCount:0,intervalsPassed:0,intervalsFreeze:0},shakefrontback:{eventCount:0,intervalsPassed:0,intervalsFreeze:0},shakeupdown:{eventCount:0,intervalsPassed:0,intervalsFreeze:0}};var q={accelerationIncludingGravity:{x:r.accelerationIncludingGravity.x,y:r.accelerationIncludingGravity.y,z:r.accelerationIncludingGravity.z},shake:{eventCount:n.shake.eventCount,intervalsPassed:n.shake.intervalsPassed,intervalsFreeze:n.shake.intervalsFreeze},shakeleftright:{eventCount:n.shakeleftright.eventCount,intervalsPassed:n.shakeleftright.intervalsPassed,intervalsFreeze:n.shakeleftright.intervalsFreeze},shakefrontback:{eventCount:n.shakefrontback.eventCount,intervalsPassed:n.shakefrontback.intervalsPassed,intervalsFreeze:n.shakefrontback.intervalsFreeze},shakeupdown:{eventCount:n.shakeupdown.eventCount,intervalsPassed:n.shakeupdown.intervalsPassed,intervalsFreeze:n.shakeupdown.intervalsFreeze}};var p;var s;var l;for(k in o){switch(k){case"shake":case"shakeleftright":case"shakefrontback":case"shakeupdown":p=[];s=[];p.push(k);if(++q[k].intervalsFreeze>m.freezeShakes&&q[k].intervalsFreeze<(2*m.freezeShakes)){break}q[k].intervalsFreeze=0;q[k].intervalsPassed++;if((k==="shake"||k==="shakeleftright")&&(q.accelerationIncludingGravity.x>m.leftright.sensitivity||q.accelerationIncludingGravity.x<(-1*m.leftright.sensitivity))){p.push("leftright");p.push("x-axis")}if((k==="shake"||k==="shakefrontback")&&(q.accelerationIncludingGravity.y>m.frontback.sensitivity||q.accelerationIncludingGravity.y<(-1*m.frontback.sensitivity))){p.push("frontback");p.push("y-axis")}if((k==="shake"||k==="shakeupdown")&&(q.accelerationIncludingGravity.z+9.81>m.updown.sensitivity||q.accelerationIncludingGravity.z+9.81<(-1*m.updown.sensitivity))){p.push("updown");p.push("z-axis")}if(p.length>1){if(++q[k].eventCount==m.requiredShakes&&(q[k].intervalsPassed)<m.freezeShakes){t.triggerHandler(k,d({type:k,description:p.join(":"),event:r,duration:q[k].intervalsPassed*5}));q[k].eventCount=0;q[k].intervalsPassed=0;q[k].intervalsFreeze=m.freezeShakes+1}else{if(q[k].eventCount==m.requiredShakes&&(q[k].intervalsPassed)>m.freezeShakes){q[k].eventCount=0;q[k].intervalsPassed=0}}}break}l={};l.oDeviceMotionLastDevicePosition=q;t.data("ojQueryGestures",c.extend(true,o,l))}}function h(l){var k=jQuery(l.currentTarget);k.triggerHandler(c.jGestures.events.touchstart,l);if(c.hasGestures){l.currentTarget.addEventListener("touchmove",g,false);l.currentTarget.addEventListener("touchend",j,false)}else{k.bind("mousemove",g);k.bind("mouseup",j)}var n=k.data("ojQueryGestures");var m=(l.touches)?l.touches[0]:l;var o={};o.oLastSwipemove={screenX:m.screenX,screenY:m.screenY,timestamp:new Date().getTime()};o.oStartTouch={screenX:m.screenX,screenY:m.screenY,timestamp:new Date().getTime()};k.data("ojQueryGestures",c.extend(true,n,o))}function g(t){var v=jQuery(t.currentTarget);var s=v.data("ojQueryGestures");var q=!!t.touches;var l=(q)?t.changedTouches[0].screenX:t.screenX;var k=(q)?t.changedTouches[0].screenY:t.screenY;var r=s.oLastSwipemove;var o=l-r.screenX;var n=k-r.screenY;var u;if(!!s.oLastSwipemove){u=d({type:"swipemove",touches:(q)?t.touches.length:"1",screenY:k,screenX:l,deltaY:n,deltaX:o,startMove:r,event:t,timestamp:r.timestamp});v.triggerHandler(u.type,u)}var m={};var p=(t.touches)?t.touches[0]:t;m.oLastSwipemove={screenX:p.screenX,screenY:p.screenY,timestamp:new Date().getTime()};v.data("ojQueryGestures",c.extend(true,s,m))}function j(r){var v=jQuery(r.currentTarget);var x=!!r.changedTouches;var u=(x)?r.changedTouches.length:"1";var p=(x)?r.changedTouches[0].screenX:r.screenX;var o=(x)?r.changedTouches[0].screenY:r.screenY;v.triggerHandler(c.jGestures.events.touchendStart,r);if(c.hasGestures){r.currentTarget.removeEventListener("touchmove",g,false);r.currentTarget.removeEventListener("touchend",j,false)}else{v.unbind("mousemove",g);v.unbind("mouseup",j)}var m=v.data("ojQueryGestures");var y=(Math.abs(m.oStartTouch.screenX-p)>c.jGestures.defaults.thresholdMove||Math.abs(m.oStartTouch.screenY-o)>c.jGestures.defaults.thresholdMove)?true:false;var B=(Math.abs(m.oStartTouch.screenX-p)>c.jGestures.defaults.thresholdSwipe||Math.abs(m.oStartTouch.screenY-o)>c.jGestures.defaults.thresholdSwipe)?true:false;var A;var t;var n;var l;var k;var q;var w=["zero","one","two","three","four"];var s;for(A in m){t=m.oStartTouch;n={};p=(x)?r.changedTouches[0].screenX:r.screenX;o=(x)?r.changedTouches[0].screenY:r.screenY;l=p-t.screenX;k=o-t.screenY;q=d({type:"swipe",touches:u,screenY:o,screenX:p,deltaY:k,deltaX:l,startMove:t,event:r,timestamp:t.timestamp});s=false;switch(A){case"swipeone":if(x===false&&u==1&&y===false){break}if(x===false||(u==1&&y===true&&B===true)){s=true;q.type=["swipe",w[u]].join("");v.triggerHandler(q.type,q)}break;case"swipetwo":if((x&&u==2&&y===true&&B===true)){s=true;q.type=["swipe",w[u]].join("");v.triggerHandler(q.type,q)}break;case"swipethree":if((x&&u==3&&y===true&&B===true)){s=true;q.type=["swipe",w[u]].join("");v.triggerHandler(q.type,q)}break;case"swipefour":if((x&&u==4&&y===true&&B===true)){s=true;q.type=["swipe",w[u]].join("");v.triggerHandler(q.type,q)}break;case"swipeup":case"swiperightup":case"swiperight":case"swiperightdown":case"swipedown":case"swipeleftdown":case"swipeleft":case"swipeleftup":if(x&&y===true&&B===true){s=true;q.type=["swipe",((q.delta[0].lastX!=0)?((q.delta[0].lastX>0)?"right":"left"):""),((q.delta[0].lastY!=0)?((q.delta[0].lastY>0)?"down":"up"):"")].join("");v.triggerHandler(q.type,q)}break;case"tapone":case"taptwo":case"tapthree":case"tapfour":if((y!==true&&s!==true)&&(w[u]==A.slice(3))){q.description=["tap",w[u]].join("");q.type=["tap",w[u]].join("");v.triggerHandler(q.type,q)}break}var z={};v.data("ojQueryGestures",c.extend(true,m,z));v.data("ojQueryGestures",c.extend(true,m,z))}v.triggerHandler(c.jGestures.events.touchendProcessed,r)}function e(l){var k=jQuery(l.currentTarget);k.triggerHandler(c.jGestures.events.gesturestart,l);var m=k.data("ojQueryGestures");var n={};n.oStartTouch={timestamp:new Date().getTime()};k.data("ojQueryGestures",c.extend(true,m,n))}function f(l){var k=jQuery(l.currentTarget);var p,m,r,o;var q=k.data("ojQueryGestures");var n;for(n in q){switch(n){case"pinch":p=l.scale;if(((p<1)&&(p%1)<(1-c.jGestures.defaults.thresholdPinchclose))||((p>1)&&(p%1)>(c.jGestures.defaults.thresholdPinchopen))){m=(p<1)?-1:+1;o=d({type:"pinch",scale:p,touches:null,startMove:q.oStartTouch,event:l,timestamp:q.oStartTouch.timestamp,vector:m,description:["pinch:",m,":",((p<1)?"close":"open")].join("")});k.triggerHandler(o.type,o)}break;case"rotate":p=l.rotation;if(((p<1)&&(-1*(p)>c.jGestures.defaults.thresholdRotateccw))||((p>1)&&(p>c.jGestures.defaults.thresholdRotatecw))){m=(p<1)?-1:+1;o=d({type:"rotate",rotation:p,touches:null,startMove:q.oStartTouch,event:l,timestamp:q.oStartTouch.timestamp,vector:m,description:["rotate:",m,":",((p<1)?"counterclockwise":"clockwise")].join("")});k.triggerHandler(o.type,o)}break}}}function i(l){var k=jQuery(l.currentTarget);k.triggerHandler(c.jGestures.events.gestureendStart,l);var n;var o=k.data("ojQueryGestures");var m;for(m in o){switch(m){case"pinchclose":n=l.scale;if((n<1)&&(n%1)<(1-c.jGestures.defaults.thresholdPinchclose)){k.triggerHandler("pinchclose",d({type:"pinchclose",scale:n,vector:-1,touches:null,startMove:o.oStartTouch,event:l,timestamp:o.oStartTouch.timestamp,description:"pinch:-1:close"}))}break;case"pinchopen":n=l.scale;if((n>1)&&(n%1)>(c.jGestures.defaults.thresholdPinchopen)){k.triggerHandler("pinchopen",d({type:"pinchopen",scale:n,vector:+1,touches:null,startMove:o.oStartTouch,event:l,timestamp:o.oStartTouch.timestamp,description:"pinch:+1:open"}))}break;case"rotatecw":n=l.rotation;if((n>1)&&(n>c.jGestures.defaults.thresholdRotatecw)){k.triggerHandler("rotatecw",d({type:"rotatecw",rotation:n,vector:+1,touches:null,startMove:o.oStartTouch,event:l,timestamp:o.oStartTouch.timestamp,description:"rotate:+1:clockwise"}))}break;case"rotateccw":n=l.rotation;if((n<1)&&(-1*(n)>c.jGestures.defaults.thresholdRotateccw)){k.triggerHandler("rotateccw",d({type:"rotateccw",rotation:n,vector:-1,touches:null,startMove:o.oStartTouch,event:l,timestamp:o.oStartTouch.timestamp,description:"rotate:-1:counterclockwise"}))}break}}k.triggerHandler(c.jGestures.events.gestureendProcessed,l)}})(jQuery);


/*!
 * jquery.event.drag - v 2.0.0
 * Copyright (c) 2010 Three Dub Media - http://threedubmedia.com
 * Open Source MIT License - http://threedubmedia.com/code/license
 */
;(function(f){f.fn.drag=function(b,a,d){var e=typeof b=="string"?b:"",k=f.isFunction(b)?b:f.isFunction(a)?a:null;if(e.indexOf("drag")!==0)e="drag"+e;d=(b==k?a:d)||{};return k?this.bind(e,d,k):this.trigger(e)};var i=f.event,h=i.special,c=h.drag={defaults:{which:1,distance:0,not:":input",handle:null,relative:false,drop:true,click:false},datakey:"dragdata",livekey:"livedrag",add:function(b){var a=f.data(this,c.datakey),d=b.data||{};a.related+=1;if(!a.live&&b.selector){a.live=true;i.add(this,"draginit."+ c.livekey,c.delegate)}f.each(c.defaults,function(e){if(d[e]!==undefined)a[e]=d[e]})},remove:function(){f.data(this,c.datakey).related-=1},setup:function(){if(!f.data(this,c.datakey)){var b=f.extend({related:0},c.defaults);f.data(this,c.datakey,b);i.add(this,"mousedown",c.init,b);this.attachEvent&&this.attachEvent("ondragstart",c.dontstart)}},teardown:function(){if(!f.data(this,c.datakey).related){f.removeData(this,c.datakey);i.remove(this,"mousedown",c.init);i.remove(this,"draginit",c.delegate);c.textselect(true); this.detachEvent&&this.detachEvent("ondragstart",c.dontstart)}},init:function(b){var a=b.data,d;if(!(a.which>0&&b.which!=a.which))if(!f(b.target).is(a.not))if(!(a.handle&&!f(b.target).closest(a.handle,b.currentTarget).length)){a.propagates=1;a.interactions=[c.interaction(this,a)];a.target=b.target;a.pageX=b.pageX;a.pageY=b.pageY;a.dragging=null;d=c.hijack(b,"draginit",a);if(a.propagates){if((d=c.flatten(d))&&d.length){a.interactions=[];f.each(d,function(){a.interactions.push(c.interaction(this,a))})}a.propagates= a.interactions.length;a.drop!==false&&h.drop&&h.drop.handler(b,a);c.textselect(false);i.add(document,"mousemove mouseup",c.handler,a);return false}}},interaction:function(b,a){return{drag:b,callback:new c.callback,droppable:[],offset:f(b)[a.relative?"position":"offset"]()||{top:0,left:0}}},handler:function(b){var a=b.data;switch(b.type){case !a.dragging&&"mousemove":if(Math.pow(b.pageX-a.pageX,2)+Math.pow(b.pageY-a.pageY,2)<Math.pow(a.distance,2))break;b.target=a.target;c.hijack(b,"dragstart",a); if(a.propagates)a.dragging=true;case "mousemove":if(a.dragging){c.hijack(b,"drag",a);if(a.propagates){a.drop!==false&&h.drop&&h.drop.handler(b,a);break}b.type="mouseup"}case "mouseup":i.remove(document,"mousemove mouseup",c.handler);if(a.dragging){a.drop!==false&&h.drop&&h.drop.handler(b,a);c.hijack(b,"dragend",a)}c.textselect(true);if(a.click===false&&a.dragging){jQuery.event.triggered=true;setTimeout(function(){jQuery.event.triggered=false},20);a.dragging=false}break}},delegate:function(b){var a= [],d,e=f.data(this,"events")||{};f.each(e.live||[],function(k,j){if(j.preType.indexOf("drag")===0)if(d=f(b.target).closest(j.selector,b.currentTarget)[0]){i.add(d,j.origType+"."+c.livekey,j.origHandler,j.data);f.inArray(d,a)<0&&a.push(d)}});if(!a.length)return false;return f(a).bind("dragend."+c.livekey,function(){i.remove(this,"."+c.livekey)})},hijack:function(b,a,d,e,k){if(d){var j={event:b.originalEvent,type:b.type},n=a.indexOf("drop")?"drag":"drop",l,o=e||0,g,m;e=!isNaN(e)?e:d.interactions.length; b.type=a;b.originalEvent=null;d.results=[];do if(g=d.interactions[o])if(!(a!=="dragend"&&g.cancelled)){m=c.properties(b,d,g);g.results=[];f(k||g[n]||d.droppable).each(function(q,p){l=(m.target=p)?i.handle.call(p,b,m):null;if(l===false){if(n=="drag"){g.cancelled=true;d.propagates-=1}if(a=="drop")g[n][q]=null}else if(a=="dropinit")g.droppable.push(c.element(l)||p);if(a=="dragstart")g.proxy=f(c.element(l)||g.drag)[0];g.results.push(l);delete b.result;if(a!=="dropinit")return l});d.results[o]=c.flatten(g.results); if(a=="dropinit")g.droppable=c.flatten(g.droppable);a=="dragstart"&&!g.cancelled&&m.update()}while(++o<e);b.type=j.type;b.originalEvent=j.event;return c.flatten(d.results)}},properties:function(b,a,d){var e=d.callback;e.drag=d.drag;e.proxy=d.proxy||d.drag;e.startX=a.pageX;e.startY=a.pageY;e.deltaX=b.pageX-a.pageX;e.deltaY=b.pageY-a.pageY;e.originalX=d.offset.left;e.originalY=d.offset.top;e.offsetX=b.pageX-(a.pageX-e.originalX);e.offsetY=b.pageY-(a.pageY-e.originalY);e.drop=c.flatten((d.drop||[]).slice()); e.available=c.flatten((d.droppable||[]).slice());return e},element:function(b){if(b&&(b.jquery||b.nodeType==1))return b},flatten:function(b){return f.map(b,function(a){return a&&a.jquery?f.makeArray(a):a&&a.length?c.flatten(a):a})},textselect:function(b){f(document)[b?"unbind":"bind"]("selectstart",c.dontstart).attr("unselectable",b?"off":"on").css("MozUserSelect",b?"":"none")},dontstart:function(){return false},callback:function(){}};c.callback.prototype={update:function(){h.drop&&this.available.length&& f.each(this.available,function(b){h.drop.locate(this,b)})}};h.draginit=h.dragstart=h.dragend=c})(jQuery);
/*!
 * jquery.event.drop - v 2.0.0
 * Copyright (c) 2010 Three Dub Media - http://threedubmedia.com
 * Open Source MIT License - http://threedubmedia.com/code/license
 */
;(function(f){f.fn.drop=function(c,a,d){var g=typeof c=="string"?c:"",e=f.isFunction(c)?c:f.isFunction(a)?a:null;if(g.indexOf("drop")!==0)g="drop"+g;d=(c==e?a:d)||{};return e?this.bind(g,d,e):this.trigger(g)};f.drop=function(c){c=c||{};b.multi=c.multi===true?Infinity:c.multi===false?1:!isNaN(c.multi)?c.multi:b.multi;b.delay=c.delay||b.delay;b.tolerance=f.isFunction(c.tolerance)?c.tolerance:c.tolerance===null?null:b.tolerance;b.mode=c.mode||b.mode||"intersect"};var l=f.event,i=l.special,b=f.event.special.drop= {multi:1,delay:20,mode:"overlap",targets:[],datakey:"dropdata",livekey:"livedrop",add:function(c){var a=f.data(this,b.datakey);a.related+=1;if(!a.live&&c.selector){a.live=true;l.add(this,"dropinit."+b.livekey,b.delegate)}},remove:function(){f.data(this,b.datakey).related-=1},setup:function(){if(!f.data(this,b.datakey)){f.data(this,b.datakey,{related:0,active:[],anyactive:0,winner:0,location:{}});b.targets.push(this)}},teardown:function(){if(!f.data(this,b.datakey).related){f.removeData(this,b.datakey); l.remove(this,"dropinit",b.delegate);var c=this;b.targets=f.grep(b.targets,function(a){return a!==c})}},handler:function(c,a){var d;if(a)switch(c.type){case "mousedown":d=f(b.targets);if(typeof a.drop=="string")d=d.filter(a.drop);d.each(function(){var g=f.data(this,b.datakey);g.active=[];g.anyactive=0;g.winner=0});a.droppable=d;b.delegates=[];i.drag.hijack(c,"dropinit",a);b.delegates=f.unique(i.drag.flatten(b.delegates));break;case "mousemove":b.event=c;b.timer||b.tolerate(a);break;case "mouseup":b.timer= clearTimeout(b.timer);if(a.propagates){i.drag.hijack(c,"drop",a);i.drag.hijack(c,"dropend",a);f.each(b.delegates||[],function(){l.remove(this,"."+b.livekey)})}break}},delegate:function(c){var a=[],d,g=f.data(this,"events")||{};f.each(g.live||[],function(e,h){if(h.preType.indexOf("drop")===0){d=f(c.currentTarget).find(h.selector);d.length&&d.each(function(){l.add(this,h.origType+"."+b.livekey,h.origHandler,h.data);f.inArray(this,a)<0&&a.push(this)})}});b.delegates.push(a);return a.length?f(a):false}, locate:function(c,a){var d=f.data(c,b.datakey),g=f(c),e=g.offset()||{},h=g.outerHeight();g=g.outerWidth();e={elem:c,width:g,height:h,top:e.top,left:e.left,right:e.left+g,bottom:e.top+h};if(d){d.location=e;d.index=a;d.elem=c}return e},contains:function(c,a){return(a[0]||a.left)>=c.left&&(a[0]||a.right)<=c.right&&(a[1]||a.top)>=c.top&&(a[1]||a.bottom)<=c.bottom},modes:{intersect:function(c,a,d){return this.contains(d,[c.pageX,c.pageY])?1E9:this.modes.overlap.apply(this,arguments)},overlap:function(c, a,d){return Math.max(0,Math.min(d.bottom,a.bottom)-Math.max(d.top,a.top))*Math.max(0,Math.min(d.right,a.right)-Math.max(d.left,a.left))},fit:function(c,a,d){return this.contains(d,a)?1:0},middle:function(c,a,d){return this.contains(d,[a.left+a.width*0.5,a.top+a.height*0.5])?1:0}},sort:function(c,a){return a.winner-c.winner||c.index-a.index},tolerate:function(c){var a,d,g,e,h,m,j=0,k,p=c.interactions.length,n=[b.event.pageX,b.event.pageY],o=b.tolerance||b.modes[b.mode];do if(k=c.interactions[j]){if(!k)return; k.drop=[];h=[];m=k.droppable.length;if(o)g=b.locate(k.proxy);a=0;do if(d=k.droppable[a]){e=f.data(d,b.datakey);if(d=e.location){e.winner=o?o.call(b,b.event,g,d):b.contains(d,n)?1:0;h.push(e)}}while(++a<m);h.sort(b.sort);a=0;do if(e=h[a])if(e.winner&&k.drop.length<b.multi){if(!e.active[j]&&!e.anyactive)if(i.drag.hijack(b.event,"dropstart",c,j,e.elem)[0]!==false){e.active[j]=1;e.anyactive+=1}else e.winner=0;e.winner&&k.drop.push(e.elem)}else if(e.active[j]&&e.anyactive==1){i.drag.hijack(b.event,"dropend", c,j,e.elem);e.active[j]=0;e.anyactive-=1}while(++a<m)}while(++j<p);if(b.last&&n[0]==b.last.pageX&&n[1]==b.last.pageY)delete b.timer;else b.timer=setTimeout(function(){b.tolerate(c)},b.delay);b.last=b.event}};i.dropinit=i.dropstart=i.dropend=b})(jQuery);

//jQuery Tools @VERSION / Expose - Dim the lights
eval(function(p,a,c,k,e,r){e=function(c){return(c<a?'':e(parseInt(c/a)))+((c=c%a)>35?String.fromCharCode(c+29):c.toString(36))};if(!''.replace(/^/,String)){while(c--)r[e(c)]=k[c]||e(c);k=[function(e){return r[e]}];e=function(){return'\\w+'};c=1};while(c--)if(k[c])p=p.replace(new RegExp('\\b'+e(c)+'\\b','g'),k[c]);return p}('(3($){$.F=$.F||{1d:\'@1e\'};7 g;g=$.F.M={N:{t:\'1f\',O:\'1g\',P:\'1h\',Q:u,R:u,m:1i,G:0.8,S:0,v:\'#1j\',x:y,H:u,T:U,V:U}};3 I(){2($.1k.1l){7 d=$(p).n(),w=$(9).n();5[9.1m||p.1n.W||p.X.W,d-w<1o?w:d]}5[$(9).z(),$(p).n()]}3 Y(){5[$(9).n(),$(9).1p()]}3 o(a){2(a){5 a.o($.4)}}7 h,q,j,l,r;$.4={J:3(b,c){2(j){5 k}2(1q b==\'1r\'){b={v:b}}b=b||l;l=b=$.Z($.Z({},g.N),b);h=$("#"+b.t);2(!h.s){h=$(\'<A/>\').10("11",b.t);h.1s(\'12\');$("X").1t(h)}7 d=h.13(\'A\');2(b.x&&!d.s){h.1u(\'12\');$(\'<A/>\').10("11",(b.t+\'1v\')).1w(h)}1x 2(!b.x&&d.s){d.1y()}7 f=I();h.6({B:\'K\',14:0,1z:0,z:f[0],n:f[1],15:\'1A\',G:b.S,m:b.m});2(b.v){h.6("1B",b.v)}2(o(b.1C)===y){5 k}2(b.R){$(p).C("16.4",3(e){2(e.1D==1E){$.4.L(e)}})}2(b.Q){h.C("17.4",3(e){$.4.L(e)})}$(9).C("18.4",3(){2(b.H){$.4.D()}});$(9).C("19.4",3(){2(b.x&&b.H){$.4.D()}});2(c&&c.s){r=c.1F(0).6("m");$.1G(c,3(){7 a=$(k);2(!/1a|K|1H/i.1I(a.6("B"))){a.6("B","1a")}});q=c.6({m:1J.1K(b.m+1,r==\'1L\'?0:r)})}h.6({15:\'1M\'}).1N(b.O,b.G,3(){$.4.D();o(b.T);j="1b"});j=u;5 k},L:3(){2(j){2(o(l.1O)===y){5 k}h.1P(l.P,3(){2(q){q.6({m:r})}j=y;o(l.V)});$(p).E("16.4");h.E("17.4");$(9).E("18.4");$(9).E("19.4");o(l.1Q)}5 k},D:3(){2(j){7 a=I();h.6({z:a[0],n:a[1]});7 b=h.13(\'A\');2(b.s){7 c=Y();b.6({z:a[0],n:c[0],14:c[1],B:\'K\'})}}},1R:3(){5 h},1S:3(a){5 a?j==\'1b\':j},1T:3(){5 l},1U:3(){5 q}};$.1c.4=3(a){$.4.J(a);5 k};$.1c.M=3(a){$.4.J(a,k);5 k}})(1V);',62,120,'||if|function|mask|return|css|var||window||||||||||loaded|this|config|zIndex|height|call|document|exposed|overlayIndex|length|maskId|true|color||innerLayer|false|width|div|position|bind|fit|unbind|tools|opacity|fitOnResizeOrScroll|viewport|load|absolute|close|expose|conf|loadSpeed|closeSpeed|closeOnClick|closeOnEsc|startOpacity|onLoad|null|onClose|clientWidth|body|innerViewport|extend|attr|id|withInner|find|top|display|keydown|click|resize|scroll|relative|full|fn|version|VERSION|exposeMask|slow|fast|9000|fff|browser|msie|innerWidth|documentElement|20|scrollTop|typeof|string|removeClass|append|addClass|Inner|appendTo|else|remove|left|none|backgroundColor|onBeforeLoad|keyCode|27|eq|each|fixed|test|Math|max|auto|block|fadeTo|onBeforeClose|fadeOut|onCloseAfter|getMask|isLoaded|getConf|getExposed|jQuery'.split('|'),0,{}));

/**
 * jQuery.ScrollTo - Easy element scrolling using jQuery.
 * Copyright (c) 2007-2009 Ariel Flesler - aflesler(at)gmail(dot)com | http://flesler.blogspot.com
 * Dual licensed under MIT and GPL.
 * Date: 5/25/2009
 * @author Ariel Flesler
 * @version 1.4.2
 *
 * http://flesler.blogspot.com/2007/10/jqueryscrollto.html
 */
;(function(d){var k=d.scrollTo=function(a,i,e){d(window).scrollTo(a,i,e)};k.defaults={axis:'xy',duration:parseFloat(d.fn.jquery)>=1.3?0:1};k.window=function(a){return d(window)._scrollable()};d.fn._scrollable=function(){return this.map(function(){var a=this,i=!a.nodeName||d.inArray(a.nodeName.toLowerCase(),['iframe','#document','html','body'])!=-1;if(!i)return a;var e=(a.contentWindow||a).document||a.ownerDocument||a;return d.browser.safari||e.compatMode=='BackCompat'?e.body:e.documentElement})};d.fn.scrollTo=function(n,j,b){if(typeof j=='object'){b=j;j=0}if(typeof b=='function')b={onAfter:b};if(n=='max')n=9e9;b=d.extend({},k.defaults,b);j=j||b.speed||b.duration;b.queue=b.queue&&b.axis.length>1;if(b.queue)j/=2;b.offset=p(b.offset);b.over=p(b.over);return this._scrollable().each(function(){var q=this,r=d(q),f=n,s,g={},u=r.is('html,body');switch(typeof f){case'number':case'string':if(/^([+-]=)?\d+(\.\d+)?(px|%)?$/.test(f)){f=p(f);break}f=d(f,this);case'object':if(f.is||f.style)s=(f=d(f)).offset()}d.each(b.axis.split(''),function(a,i){var e=i=='x'?'Left':'Top',h=e.toLowerCase(),c='scroll'+e,l=q[c],m=k.max(q,i);if(s){g[c]=s[h]+(u?0:l-r.offset()[h]);if(b.margin){g[c]-=parseInt(f.css('margin'+e))||0;g[c]-=parseInt(f.css('border'+e+'Width'))||0}g[c]+=b.offset[h]||0;if(b.over[h])g[c]+=f[i=='x'?'width':'height']()*b.over[h]}else{var o=f[h];g[c]=o.slice&&o.slice(-1)=='%'?parseFloat(o)/100*m:o}if(/^\d+$/.test(g[c]))g[c]=g[c]<=0?0:Math.min(g[c],m);if(!a&&b.queue){if(l!=g[c])t(b.onAfterFirst);delete g[c]}});t(b.onAfter);function t(a){r.animate(g,j,b.easing,a&&function(){a.call(this,n,b)})}}).end()};k.max=function(a,i){var e=i=='x'?'Width':'Height',h='scroll'+e;if(!d(a).is('html,body'))return a[h]-d(a)[e.toLowerCase()]();var c='client'+e,l=a.ownerDocument.documentElement,m=a.ownerDocument.body;return Math.max(l[h],m[h])-Math.min(l[c],m[c])};function p(a){return typeof a=='object'?a:{top:a,left:a}}})(jQuery);


/**
 * jQuery Cookie plugin
 *
 * Copyright (c) 2010 Klaus Hartl (stilbuero.de)
 * Dual licensed under the MIT and GPL licenses:
 * http://www.opensource.org/licenses/mit-license.php
 * http://www.gnu.org/licenses/gpl.html
 *
 */
jQuery.cookie = function (key, value, options) {
    // key and at least value given, set cookie...
    if (arguments.length > 1 && String(value) !== "[object Object]") {
        options = jQuery.extend({}, options);

        if (value === null || value === undefined) {
            options.expires = -1;
        }

        if (typeof options.expires === 'number') {
            var days = options.expires, t = options.expires = new Date();
            t.setDate(t.getDate() + days);
        }

        value = String(value);

        return (document.cookie = [
            encodeURIComponent(key), '=',
            options.raw ? value : encodeURIComponent(value),
            options.expires ? '; expires=' + options.expires.toUTCString() : '', // use expires attribute, max-age is not supported by IE
            options.path ? '; path=' + options.path : '',
            options.domain ? '; domain=' + options.domain : '',
            options.secure ? '; secure' : ''
        ].join(''));
    }

    // key and possibly options given, get cookie...
    options = value || {};
    var result, decode = options.raw ? function (s) { return s; } : decodeURIComponent;
    return (result = new RegExp('(?:^|; )' + encodeURIComponent(key) + '=([^;]*)').exec(document.cookie)) ? decode(result[1]) : null;
};


/*
 * jQuery history plugin
 *
 * The MIT License
 *
 * Copyright (c) 2006-2009 Taku Sano (Mikage Sawatari)
 * Copyright (c) 2010 Takayuki Miwa
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
(function($){var locationWrapper={put:function(hash,win){(win||window).location.hash=this.encoder(hash)},get:function(win){var hash=((win||window).location.hash).replace(/^#/,'');try{return $.browser.mozilla?hash:decodeURIComponent(hash)}catch(error){return hash}},encoder:encodeURIComponent};var iframeWrapper={id:"__jQuery_history",init:function(){var html='<iframe id="'+this.id+'" style="display:none" src="javascript:false;" />';$("body").prepend(html);return this},_document:function(){return $("#"+this.id)[0].contentWindow.document},put:function(hash){var doc=this._document();doc.open();doc.close();locationWrapper.put(hash,doc)},get:function(){return locationWrapper.get(this._document())}};function initObjects(options){options=$.extend({unescape:false},options||{});locationWrapper.encoder=encoder(options.unescape);function encoder(unescape_){if(unescape_===true){return function(hash){return hash}}if(typeof unescape_=="string"&&(unescape_=partialDecoder(unescape_.split("")))||typeof unescape_=="function"){return function(hash){return unescape_(encodeURIComponent(hash))}}return encodeURIComponent}function partialDecoder(chars){var re=new RegExp($.map(chars,encodeURIComponent).join("|"),"ig");return function(enc){return enc.replace(re,decodeURIComponent)}}}var implementations={};implementations.base={callback:undefined,type:undefined,check:function(){},load:function(hash){},init:function(callback,options){initObjects(options);self.callback=callback;self._options=options;self._init()},_init:function(){},_options:{}};implementations.timer={_appState:undefined,_init:function(){var current_hash=locationWrapper.get();self._appState=current_hash;self.callback(current_hash);setInterval(self.check,100)},check:function(){var current_hash=locationWrapper.get();if(current_hash!=self._appState){self._appState=current_hash;self.callback(current_hash)}},load:function(hash){if(hash!=self._appState){locationWrapper.put(hash);self._appState=hash;self.callback(hash)}}};implementations.iframeTimer={_appState:undefined,_init:function(){var current_hash=locationWrapper.get();self._appState=current_hash;iframeWrapper.init().put(current_hash);self.callback(current_hash);setInterval(self.check,100)},check:function(){var iframe_hash=iframeWrapper.get(),location_hash=locationWrapper.get();if(location_hash!=iframe_hash){if(location_hash==self._appState){self._appState=iframe_hash;locationWrapper.put(iframe_hash);self.callback(iframe_hash)}else{self._appState=location_hash;iframeWrapper.put(location_hash);self.callback(location_hash)}}},load:function(hash){if(hash!=self._appState){locationWrapper.put(hash);iframeWrapper.put(hash);self._appState=hash;self.callback(hash)}}};implementations.hashchangeEvent={_init:function(){self.callback(locationWrapper.get());$(window).bind('hashchange',self.check)},check:function(){self.callback(locationWrapper.get())},load:function(hash){locationWrapper.put(hash)}};var self=$.extend({},implementations.base);if($.browser.msie&&($.browser.version<8||document.documentMode<8)){self.type='iframeTimer'}else if("onhashchange"in window){self.type='hashchangeEvent'}else{self.type='timer'}$.extend(self,implementations[self.type]);$.history=self})(jQuery);


/* Copyright (c) 2010 Brandon Aaron (http://brandonaaron.net)
 * Licensed under the MIT License (LICENSE.txt).
 *
 * Thanks to: http://adomas.org/javascript-mouse-wheel/ for some pointers.
 * Thanks to: Mathias Bank(http://www.mathias-bank.de) for a scope bug fix.
 * Thanks to: Seamus Leahy for adding deltaX and deltaY
 *
 * Version: 3.0.4
 *
 * Requires: 1.2.2+
 */
//(function(c){var a=["DOMMouseScroll","mousewheel"];c.event.special.mousewheel={setup:function(){if(this.addEventListener){for(var d=a.length;d;){this.addEventListener(a[--d],b,false)}}else{this.onmousewheel=b}},teardown:function(){if(this.removeEventListener){for(var d=a.length;d;){this.removeEventListener(a[--d],b,false)}}else{this.onmousewheel=null}}};c.fn.extend({mousewheel:function(d){return d?this.bind("mousewheel",d):this.trigger("mousewheel")},unmousewheel:function(d){return this.unbind("mousewheel",d)}});function b(i){var g=i||window.event,f=[].slice.call(arguments,1),j=0,h=true,e=0,d=0;i=c.event.fix(g);i.type="mousewheel";if(i.wheelDelta){j=i.wheelDelta/120}if(i.detail){j=-i.detail/3}d=j;if(g.axis!==undefined&&g.axis===g.HORIZONTAL_AXIS){d=0;e=-1*j}if(g.wheelDeltaY!==undefined){d=g.wheelDeltaY/120}if(g.wheelDeltaX!==undefined){e=-1*g.wheelDeltaX/120}f.unshift(i,j,e,d);return c.event.handle.apply(this,f)}})(jQuery);
/*! Copyright (c) 2011 Brandon Aaron (http://brandonaaron.net)
* Licensed under the MIT License (LICENSE.txt).
*
* Thanks to: http://adomas.org/javascript-mouse-wheel/ for some pointers.
* Thanks to: Mathias Bank(http://www.mathias-bank.de) for a scope bug fix.
* Thanks to: Seamus Leahy for adding deltaX and deltaY
*
* Version: 3.0.6
*
* Requires: 1.2.2+
*/

(function($) {

var types = ['DOMMouseScroll', 'mousewheel'];

if ($.event.fixHooks) {
    for ( var i=types.length; i; ) {
        $.event.fixHooks[ types[--i] ] = $.event.mouseHooks;
    }
}

$.event.special.mousewheel = {
    setup: function() {
        if ( this.addEventListener ) {
            for ( var i=types.length; i; ) {
                this.addEventListener( types[--i], handler, false );
            }
        } else {
            this.onmousewheel = handler;
        }
    },

    teardown: function() {
        if ( this.removeEventListener ) {
            for ( var i=types.length; i; ) {
                this.removeEventListener( types[--i], handler, false );
            }
        } else {
            this.onmousewheel = null;
        }
    }
};

$.fn.extend({
    mousewheel: function(fn) {
        return fn ? this.bind("mousewheel", fn) : this.trigger("mousewheel");
    },

    unmousewheel: function(fn) {
        return this.unbind("mousewheel", fn);
    }
});


function handler(event) {
    var orgEvent = event || window.event, args = [].slice.call( arguments, 1 ), delta = 0, returnValue = true, deltaX = 0, deltaY = 0;
    event = $.event.fix(orgEvent);
    event.type = "mousewheel";

    // Old school scrollwheel delta
    if ( orgEvent.wheelDelta ) { delta = orgEvent.wheelDelta/120; }
    if ( orgEvent.detail ) { delta = -orgEvent.detail/3; }

    // New school multidimensional scroll (touchpads) deltas
    deltaY = delta;

    // Gecko
    if ( orgEvent.axis !== undefined && orgEvent.axis === orgEvent.HORIZONTAL_AXIS ) {
        deltaY = 0;
        deltaX = -1*delta;
    }

    // Webkit
    if ( orgEvent.wheelDeltaY !== undefined ) { deltaY = orgEvent.wheelDeltaY/120; }
    if ( orgEvent.wheelDeltaX !== undefined ) { deltaX = -1*orgEvent.wheelDeltaX/120; }

    // Add event and delta to the front of the arguments
    args.unshift(event, delta, deltaX, deltaY);

    return ($.event.dispatch || $.event.handle).apply(this, args);
}

})(jQuery);

/* easing */
// t: current time, b: begInnIng value, c: change In value, d: duration
eval(function(p,a,c,k,e,r){e=function(c){return(c<a?'':e(parseInt(c/a)))+((c=c%a)>35?String.fromCharCode(c+29):c.toString(36))};if(!''.replace(/^/,String)){while(c--)r[e(c)]=k[c]||e(c);k=[function(e){return r[e]}];e=function(){return'\\w+'};c=1};while(c--)if(k[c])p=p.replace(new RegExp('\\b'+e(c)+'\\b','g'),k[c]);return p}('h.i[\'1a\']=h.i[\'z\'];h.O(h.i,{y:\'D\',z:9(x,t,b,c,d){6 h.i[h.i.y](x,t,b,c,d)},17:9(x,t,b,c,d){6 c*(t/=d)*t+b},D:9(x,t,b,c,d){6-c*(t/=d)*(t-2)+b},13:9(x,t,b,c,d){e((t/=d/2)<1)6 c/2*t*t+b;6-c/2*((--t)*(t-2)-1)+b},X:9(x,t,b,c,d){6 c*(t/=d)*t*t+b},U:9(x,t,b,c,d){6 c*((t=t/d-1)*t*t+1)+b},R:9(x,t,b,c,d){e((t/=d/2)<1)6 c/2*t*t*t+b;6 c/2*((t-=2)*t*t+2)+b},N:9(x,t,b,c,d){6 c*(t/=d)*t*t*t+b},M:9(x,t,b,c,d){6-c*((t=t/d-1)*t*t*t-1)+b},L:9(x,t,b,c,d){e((t/=d/2)<1)6 c/2*t*t*t*t+b;6-c/2*((t-=2)*t*t*t-2)+b},K:9(x,t,b,c,d){6 c*(t/=d)*t*t*t*t+b},J:9(x,t,b,c,d){6 c*((t=t/d-1)*t*t*t*t+1)+b},I:9(x,t,b,c,d){e((t/=d/2)<1)6 c/2*t*t*t*t*t+b;6 c/2*((t-=2)*t*t*t*t+2)+b},G:9(x,t,b,c,d){6-c*8.C(t/d*(8.g/2))+c+b},15:9(x,t,b,c,d){6 c*8.n(t/d*(8.g/2))+b},12:9(x,t,b,c,d){6-c/2*(8.C(8.g*t/d)-1)+b},Z:9(x,t,b,c,d){6(t==0)?b:c*8.j(2,10*(t/d-1))+b},Y:9(x,t,b,c,d){6(t==d)?b+c:c*(-8.j(2,-10*t/d)+1)+b},W:9(x,t,b,c,d){e(t==0)6 b;e(t==d)6 b+c;e((t/=d/2)<1)6 c/2*8.j(2,10*(t-1))+b;6 c/2*(-8.j(2,-10*--t)+2)+b},V:9(x,t,b,c,d){6-c*(8.o(1-(t/=d)*t)-1)+b},S:9(x,t,b,c,d){6 c*8.o(1-(t=t/d-1)*t)+b},Q:9(x,t,b,c,d){e((t/=d/2)<1)6-c/2*(8.o(1-t*t)-1)+b;6 c/2*(8.o(1-(t-=2)*t)+1)+b},P:9(x,t,b,c,d){f s=1.l;f p=0;f a=c;e(t==0)6 b;e((t/=d)==1)6 b+c;e(!p)p=d*.3;e(a<8.w(c)){a=c;f s=p/4}m f s=p/(2*8.g)*8.r(c/a);6-(a*8.j(2,10*(t-=1))*8.n((t*d-s)*(2*8.g)/p))+b},H:9(x,t,b,c,d){f s=1.l;f p=0;f a=c;e(t==0)6 b;e((t/=d)==1)6 b+c;e(!p)p=d*.3;e(a<8.w(c)){a=c;f s=p/4}m f s=p/(2*8.g)*8.r(c/a);6 a*8.j(2,-10*t)*8.n((t*d-s)*(2*8.g)/p)+c+b},T:9(x,t,b,c,d){f s=1.l;f p=0;f a=c;e(t==0)6 b;e((t/=d/2)==2)6 b+c;e(!p)p=d*(.3*1.5);e(a<8.w(c)){a=c;f s=p/4}m f s=p/(2*8.g)*8.r(c/a);e(t<1)6-.5*(a*8.j(2,10*(t-=1))*8.n((t*d-s)*(2*8.g)/p))+b;6 a*8.j(2,-10*(t-=1))*8.n((t*d-s)*(2*8.g)/p)*.5+c+b},F:9(x,t,b,c,d,s){e(s==u)s=1.l;6 c*(t/=d)*t*((s+1)*t-s)+b},E:9(x,t,b,c,d,s){e(s==u)s=1.l;6 c*((t=t/d-1)*t*((s+1)*t+s)+1)+b},16:9(x,t,b,c,d,s){e(s==u)s=1.l;e((t/=d/2)<1)6 c/2*(t*t*(((s*=(1.B))+1)*t-s))+b;6 c/2*((t-=2)*t*(((s*=(1.B))+1)*t+s)+2)+b},A:9(x,t,b,c,d){6 c-h.i.v(x,d-t,0,c,d)+b},v:9(x,t,b,c,d){e((t/=d)<(1/2.k)){6 c*(7.q*t*t)+b}m e(t<(2/2.k)){6 c*(7.q*(t-=(1.5/2.k))*t+.k)+b}m e(t<(2.5/2.k)){6 c*(7.q*(t-=(2.14/2.k))*t+.11)+b}m{6 c*(7.q*(t-=(2.18/2.k))*t+.19)+b}},1b:9(x,t,b,c,d){e(t<d/2)6 h.i.A(x,t*2,0,c,d)*.5+b;6 h.i.v(x,t*2-d,0,c,d)*.5+c*.5+b}});',62,74,'||||||return||Math|function|||||if|var|PI|jQuery|easing|pow|75|70158|else|sin|sqrt||5625|asin|||undefined|easeOutBounce|abs||def|swing|easeInBounce|525|cos|easeOutQuad|easeOutBack|easeInBack|easeInSine|easeOutElastic|easeInOutQuint|easeOutQuint|easeInQuint|easeInOutQuart|easeOutQuart|easeInQuart|extend|easeInElastic|easeInOutCirc|easeInOutCubic|easeOutCirc|easeInOutElastic|easeOutCubic|easeInCirc|easeInOutExpo|easeInCubic|easeOutExpo|easeInExpo||9375|easeInOutSine|easeInOutQuad|25|easeOutSine|easeInOutBack|easeInQuad|625|984375|jswing|easeInOutBounce'.split('|'),0,{}))

/**
 *  Plugin which is applied on a list of img objects and calls
 *  the specified callback function, only when all of them are loaded (or errored).
 *  @author:  H. Yankov (hristo.yankov at gmail dot com)
 *  @version: 1.0.0 (Feb/22/2010)
 *	http://yankov.us
 */
eval(function(p,a,c,k,e,r){e=function(c){return c.toString(a)};if(!''.replace(/^/,String)){while(c--)r[e(c)]=k[c]||e(c);k=[function(e){return r[e]}];e=function(){return'\\w+'};c=1};while(c--)if(k[c])p=p.replace(new RegExp('\\b'+e(c)+'\\b','g'),k[c]);return p}('(4($){$.7.8=4(a){5 b=$(6);5 c=b.h();5 d=c;5 e=0;$.7.8.9={2:g,3:g};5 f=$.i({},$.7.8.9,a);b.j(4(){1($(6)[0].k){d--;1(f.3)f.3(e,c)}l{$(6).m(4(){e++;1(f.3)f.3(e,c);1(e>=d)1(f.2)f.2()});$(6).n(4(){e++;1(f.3)f.3(e,c);1(e>=d)1(f.2)f.2()})}});1(d<=0)1(f.2)f.2()}})(o);',25,25,'|if|loadingCompleteCallback|imageLoadedCallback|function|var|this|fn|batchImageLoad|defaults|||||||null|size|extend|each|complete|else|load|error|jQuery'.split('|'),0,{}))

/*
 * jQuery Tooltip plugin 1.3
 *
 * http://bassistance.de/jquery-plugins/jquery-plugin-tooltip/
 * http://docs.jquery.com/Plugins/Tooltip
 *
 * Copyright (c) 2006 - 2008 Jörn Zaefferer
 *
 * $Id: jquery.tooltip.js 5741 2008-06-21 15:22:16Z joern.zaefferer $
 *
 * Dual licensed under the MIT and GPL licenses:
 *   http://www.opensource.org/licenses/mit-license.php
 *   http://www.gnu.org/licenses/gpl.html
 */
eval(function(p,a,c,k,e,r){e=function(c){return(c<a?'':e(parseInt(c/a)))+((c=c%a)>35?String.fromCharCode(c+29):c.toString(36))};if(!''.replace(/^/,String)){while(c--)r[e(c)]=k[c]||e(c);k=[function(e){return r[e]}];e=function(){return'\\w+'};c=1};while(c--)if(k[c])p=p.replace(new RegExp('\\b'+e(c)+'\\b','g'),k[c]);return p}(';(7($){k f={},9,m,D,E=$.2s.2n&&/2m\\s(5\\.5|6\\.)/.2l(2k.2j),I=U;$.l={C:U,1o:{R:2i,t:U,1w:1y,11:"",A:15,z:15,S:"l"},2h:7(){$.l.C=!$.l.C}};$.N.1p({l:7(a){a=$.1p({},$.l.1o,a);1s(a);j 2.F(7(){$.1x(2,"l",a);2.T=f.3.o("1a");2.Z=2.m;$(2).2g("m");2.2f=""}).2e(1k).2c(r).2a(7(e){})},M:E?7(){j 2.F(7(){k b=$(2).o(\'W\');4(b.29(/^p\\(["\']?(.*\\.28)["\']?\\)$/i)){b=27.$1;$(2).o({\'W\':\'1E\',\'1C\':"25:24.23.1X(1W=1y, 1V=1U, 1f=\'"+b+"\')"}).F(7(){k a=$(2).o(\'1g\');4(a!=\'1T\'&&a!=\'1i\')$(2).o(\'1g\',\'1i\')})}})}:7(){j 2},1j:E?7(){j 2.F(7(){$(2).o({\'1C\':\'\',W:\'\'})})}:7(){j 2},1n:7(){j 2.F(7(){$(2)[$(2).G()?"n":"r"]()})},p:7(){j 2.1m(\'1S\')||2.1m(\'1f\')}});7 1s(a){4(f.3)j;f.3=$(\'<u S="\'+a.S+\'"><X></X><u 1q="g"></u><u 1q="p"></u></u>\').1P(K.g).r();4($.N.L)f.3.L();f.m=$(\'X\',f.3);f.g=$(\'u.g\',f.3);f.p=$(\'u.p\',f.3)}7 8(a){j $.1x(a,"l")}7 1v(a){4(8(2).R)D=1L(n,8(2).R);q n();I=!!8(2).I;$(K.g).1K(\'16\',w);w(a)}7 1k(){4($.l.C||2==9||(!2.Z&&!8(2).18))j;9=2;m=2.Z;4(8(2).18){f.m.r();k a=8(2).18.1G(2);4(a.26||a.1F){f.g.1D().14(a)}q{f.g.G(a)}f.g.n()}q 4(8(2).1B){k b=m.1H(8(2).1B);f.m.G(b.1I()).n();f.g.1D();1J(k i=0,12;(12=b[i]);i++){4(i>0)f.g.14("<1M/>");f.g.14(12)}f.g.1n()}q{f.m.G(m).n();f.g.r()}4(8(2).1w&&$(2).p())f.p.G($(2).p().1N(\'1O://\',\'\')).n();q f.p.r();f.3.Y(8(2).11);4(8(2).M)f.3.M();1v.1Q(2,1R)}7 n(){D=V;4((!E||!$.N.L)&&8(9).t){4(f.3.O(":1e"))f.3.Q().n().P(8(9).t,9.T);q f.3.O(\':1c\')?f.3.P(8(9).t,9.T):f.3.1Y(8(9).t)}q{f.3.n()}w()}7 w(a){4($.l.C)j;4(a&&a.1Z.21=="22"){j}4(!I&&f.3.O(":1c")){$(K.g).1b(\'16\',w)}4(9==V){$(K.g).1b(\'16\',w);j}f.3.17("B-13").17("B-1A");k b=f.3[0].1z;k c=f.3[0].1u;4(a){k d=$(9).2b();b=d.z+8(9).z;c=d.A+8(9).A;k e=\'1l\';4(8(9).2d){e=$(H).1h()-b;b=\'1l\'}f.3.o({z:b-f.3[0].10-5,13:e,A:c})}k v=B(),h=f.3[0];4(v.x+v.1t<h.1z+h.10){b-=h.10+20+8(9).z;f.3.o({z:b+\'1r\'}).Y("B-13")}4(v.y+v.1d<h.1u+h.19){c-=h.19+20+8(9).A;f.3.o({A:c+\'1r\'}).Y("B-1A")}}7 B(){j{x:$(H).2o(),y:$(H).2p(),1t:$(H).1h(),1d:$(H).2q()}}7 r(a){4($.l.C)j;4(D)2r(D);9=V;k b=8(2);7 J(){f.3.17(b.11).r().o("1a","")}4((!E||!$.N.L)&&b.t){4(f.3.O(\':1e\'))f.3.Q().P(b.t,0,J);q f.3.Q().2t(b.t,J)}q J();4(8(2).M)f.3.1j()}})(2u);',62,155,'||this|parent|if|||function|settings|current|||||||body|||return|var|tooltip|title|show|css|url|else|hide||fade|div||update|||left|top|viewport|blocked|tID|IE|each|html|window|track|complete|document|bgiframe|fixPNG|fn|is|fadeTo|stop|delay|id|tOpacity|false|null|backgroundImage|h3|addClass|tooltipText|offsetWidth|extraClass|part|right|append||mousemove|removeClass|bodyHandler|offsetHeight|opacity|unbind|visible|cy|animated|src|position|width|relative|unfixPNG|save|auto|attr|hideWhenEmpty|defaults|extend|class|px|createHelper|cx|offsetTop|handle|showURL|data|true|offsetLeft|bottom|showBody|filter|empty|none|jquery|call|split|shift|for|bind|setTimeout|br|replace|http|appendTo|apply|arguments|href|absolute|crop|sizingMethod|enabled|AlphaImageLoader|fadeIn|target||tagName|OPTION|Microsoft|DXImageTransform|progid|nodeType|RegExp|png|match|click|offset|mouseout|positionLeft|mouseover|alt|removeAttr|block|200|userAgent|navigator|test|MSIE|msie|scrollLeft|scrollTop|height|clearTimeout|browser|fadeOut|jQuery'.split('|'),0,{}))


/*
CSS Browser Selector v0.4.0 (Nov 02, 2010)
Rafael Lima (http://rafael.adm.br)
http://rafael.adm.br/css_browser_selector
License: http://creativecommons.org/licenses/by/2.5/
Contributors: http://rafael.adm.br/css_browser_selector#contributors
*/
function css_browser_selector(u){var ua=u.toLowerCase(),is=function(t){return ua.indexOf(t)>-1},g='gecko',w='webkit',s='safari',o='opera',m='mobile',h=document.documentElement,b=[(!(/opera|webtv/i.test(ua))&&/msie\s(\d)/.test(ua))?('ie ie'+RegExp.$1):is('firefox/2')?g+' ff2':is('firefox/3.5')?g+' ff3 ff3_5':is('firefox/3.6')?g+' ff3 ff3_6':is('firefox/3')?g+' ff3':is('gecko/')?g:is('opera')?o+(/version\/(\d+)/.test(ua)?' '+o+RegExp.$1:(/opera(\s|\/)(\d+)/.test(ua)?' '+o+RegExp.$2:'')):is('konqueror')?'konqueror':is('blackberry')?m+' blackberry':is('android')?m+' android':is('chrome')?w+' chrome':is('iron')?w+' iron':is('applewebkit/')?w+' '+s+(/version\/(\d+)/.test(ua)?' '+s+RegExp.$1:''):is('mozilla/')?g:'',is('j2me')?m+' j2me':is('iphone')?m+' iphone':is('ipod')?m+' ipod':is('ipad')?m+' ipad':is('mac')?'mac':is('darwin')?'mac':is('webtv')?'webtv':is('win')?'win'+(is('windows nt 6.0')?' vista':''):is('freebsd')?'freebsd':(is('x11')||is('linux'))?'linux':'','js']; c = b.join(' '); h.className += ' '+c; return c;}; css_browser_selector(navigator.userAgent);
var ua = $.browser;
if(ua.msie && ua.version < 9)  { document.documentElement.className += " ielt9" }
if(ua.msie && ua.version < 10)  { document.documentElement.className += " ielt10" }

// VERTICALLY ALIGN FUNCTION
$.fn.vAlign=function(){return this.each(function(i){var a=$(this).height();var b=$(this).parent().height();var c=Math.ceil((b-a)/2);$(this).css('padding-top',c-2);$(this).css('height',b-c-2)})};

/*
 * Fade Slider Toggle plugin
 *
 * Copyright(c) 2009, Cedric Dugas
 * http://www.position-relative.net
 *
 * A sliderToggle() with opacity
 * Licenced under the MIT Licence
 */
jQuery.fn.fadeSliderToggle=function(a){a=jQuery.extend({speed:300,easing:"swing",onComplete:false},a);caller=this;if($(caller).css("display")=="none"){$(caller).animate({opacity:1,height:'toggle'},a.speed,a.easing,a.onComplete)}else{$(caller).animate({opacity:0,height:'toggle'},a.speed,a.easing,a.onComplete)}};

if (!jQuery.event.special.frame) {

// jquery.events.frame.js
// 1.1 - lite
// Stephen Band
//
// Project home:
// webdev.stephband.info/events/frame/
//
// Source:
// http://github.com/stephband/jquery.event.frame

(function(d,h){function i(a,b){function e(){f.frameCount++;a.call(f)}var f=this,g;this.frameDuration=b||25;this.frameCount=-1;this.start=function(){e();g=setInterval(e,this.frameDuration)};this.stop=function(){clearInterval(g);g=null}}function j(){var a=d.event.special.frame.handler,b=d.Event("frame"),e=this.array,f=e.length;for(b.frameCount=this.frameCount;f--;)a.call(e[f],b)}var c;if(!d.event.special.frame)d.event.special.frame={setup:function(a){if(c)c.array.push(this);else{c=new i(j,a&&a.frameDuration);
c.array=[this];var b=setTimeout(function(){c.start();clearTimeout(b);b=null},0)}},teardown:function(){for(var a=c.array,b=a.length;b--;)if(a[b]===this){a.splice(b,1);break}if(a.length===0){c.stop();c=h}},handler:function(){d.event.handle.apply(this,arguments)}}})(jQuery);
}

// jquery.jparallax.js
// 1.0
// Stephen Band
//
// Project and documentation site:
// webdev.stephband.info/jparallax/
//
// Repository:
// github.com/stephband/jparallax
//
// Dependencies:
// jquery.event.frame

(function(l,t){function y(i){return this.lib[i]}function q(i){return typeof i==="boolean"?i:!!parseFloat(i)}function r(i,b){var k=[q(i.xparallax),q(i.yparallax)];this.ontarget=false;this.decay=i.decay;this.pointer=b||[0.5,0.5];this.update=function(e,a){if(this.ontarget)this.pointer=e;else if((!k[0]||u(e[0]-this.pointer[0])<a[0])&&(!k[1]||u(e[1]-this.pointer[1])<a[1])){this.ontarget=true;this.pointer=e}else{a=[];for(var g=2;g--;)if(k[g])a[g]=e[g]+this.decay*(this.pointer[g]-e[g]);this.pointer=a}}}
function z(i,b){var k=this,e=i instanceof l?i:l(i),a=[q(b.xparallax),q(b.yparallax)],g=0,d;this.pointer=[0,0];this.active=false;this.activeOutside=b&&b.activeOutside||false;this.update=function(h){var j=this.pos,c=this.size,f=[],m=2;if(g>0){if(g===2){g=0;if(d)h=d}for(;m--;)if(a[m]){f[m]=(h[m]-j[m])/c[m];f[m]=f[m]<0?0:f[m]>1?1:f[m]}this.active=true;this.pointer=f}else this.active=false};this.updateSize=function(){var h=e.width(),j=e.height();k.size=[h,j];k.threshold=[1/h,1/j]};this.updatePos=function(){var h=
e.offset()||{left:0,top:0},j=parseInt(e.css("borderLeftWidth"))+parseInt(e.css("paddingLeft")),c=parseInt(e.css("borderTopWidth"))+parseInt(e.css("paddingTop"));k.pos=[h.left+j,h.top+c]};l(window).bind("resize",k.updateSize).bind("resize",k.updatePos);e.bind("mouseenter",function(){g=1}).bind("mouseleave",function(h){g=2;d=[h.pageX,h.pageY]});this.updateSize();this.updatePos()}function A(i,b){var k=[],e=[],a=[],g=[];this.update=function(d){for(var h=[],j,c,f=2,m={};f--;)if(e[f]){h[f]=e[f]*d[f]+a[f];
if(k[f]){j=g[f];c=h[f]*-1}else{j=h[f]*100+"%";c=h[f]*this.size[f]*-1}if(f===0){m.left=j;m.marginLeft=c}else{m.top=j;m.marginTop=c}}i.css(m)};this.setParallax=function(d,h,j,c){d=[d||b.xparallax,h||b.yparallax];j=[j||b.xorigin,c||b.yorigin];for(c=2;c--;){k[c]=o.px.test(d[c]);if(typeof j[c]==="string")j[c]=o.percent.test(j[c])?parseFloat(j[c])/100:v[j[c]]||1;if(k[c]){e[c]=parseInt(d[c]);a[c]=j[c]*(this.size[c]-e[c]);g[c]=j[c]*100+"%"}else{e[c]=d[c]===true?1:o.percent.test(d[c])?parseFloat(d[c])/100:
d[c];a[c]=e[c]?j[c]*(1-e[c]):0}}};this.getPointer=function(){for(var d=i.offsetParent(),h=i.position(),j=[],c=[],f=2;f--;){j[f]=k[f]?0:h[f===0?"left":"top"]/(d[f===0?"outerWidth":"outerHeight"]()-this.size[f]);c[f]=(j[f]-a[f])/e[f]}return c};this.setSize=function(d,h){this.size=[d||i.outerWidth(),h||i.outerHeight()]};this.setSize(b.width,b.height);this.setParallax(b.xparallax,b.yparallax,b.xorigin,b.yorigin)}function s(i){var b=l(this),k=i.data,e=b.data(n),a=k.port,g=k.mouse,d=e.mouse;if(k.timeStamp!==
i.timeStamp){k.timeStamp=i.timeStamp;a.update(w);if(a.active||!g.ontarget)g.update(a.pointer,a.threshold)}if(d){d.update(e.freeze?e.freeze.pointer:a.pointer,a.threshold);if(d.ontarget){delete e.mouse;e.freeze&&b.unbind(p).addClass(k.freezeClass)}g=d}else g.ontarget&&!a.active&&b.unbind(p);e.layer.update(g.pointer)}var n="parallax",x={mouseport:"body",xparallax:true,yparallax:true,xorigin:0.5,yorigin:0.5,decay:0.66,frameDuration:30,freezeClass:"freeze"},v={left:0,top:0,middle:0.5,center:0.5,right:1,
bottom:1},o={px:/^\d+\s?px$/,percent:/^\d+\s?%$/},p="frame."+n,u=Math.abs,w=[0,0];y.lib=v;l.fn[n]=function(i){var b=l.extend({},l.fn[n].options,i),k=arguments,e=this;if(!(b.mouseport instanceof l))b.mouseport=l(b.mouseport);b.port=new z(b.mouseport,b);b.mouse=new r(b);b.mouseport.bind("mouseenter",function(){b.mouse.ontarget=false;e.each(function(){var a=l(this);a.data(n).freeze||a.bind(p,b,s)})});return e.bind("freeze",function(a){var g=l(this),d=
g.data(n),h=d.mouse||d.freeze||b.mouse,j=o.percent.exec(a.x)?parseFloat(a.x.replace(/%$/,""))/100:a.x||h.pointer[0],c=o.percent.exec(a.y)?parseFloat(a.y.replace(/%$/,""))/100:a.y||h.pointer[1];a=a.decay;d.freeze={pointer:[j,c]};d.mouse=new r(b,h.pointer);if(a!==t)d.mouse.decay=a;g.bind(p,b,s)}).bind("unfreeze",function(a){var g=l(this),d=g.data(n);a=a.decay;var h;if(d.freeze){h=d.mouse?d.mouse.pointer:d.freeze.pointer;d.mouse=new r(b);d.mouse.pointer=h;if(a!==t)d.mouse.decay=a;delete d.freeze;g.removeClass(x.freezeClass).bind(p,
b,s)}}).each(function(a){var g=l(this);a=k[a+1]?l.extend({},b,k[a+1]):b;var d=new A(g,a);g.data(n,{layer:d,mouse:new r(a,d.getPointer())})})};l.fn[n].options=x;l(document).ready(function(){l(document).mousemove(function(i){w=[i.pageX,i.pageY]})})})(jQuery);

/*!
 * Placeholder plugin
 * http://mal.co.nz/code/jquery-placeholder/
 * A full featured jQuery plugin that emulates the HTML5 placeholder attribute for web browsers that do not natively support it. If the browser does support it, the plugin does nothing.
 */

eval(function(p,a,c,k,e,r){e=function(c){return(c<a?'':e(parseInt(c/a)))+((c=c%a)>35?String.fromCharCode(c+29):c.toString(36))};if(!''.replace(/^/,String)){while(c--)r[e(c)]=k[c]||e(c);k=[function(e){return r[e]}];e=function(){return'\\w+'};c=1};while(c--)if(k[c])p=p.replace(new RegExp('\\b'+e(c)+'\\b','g'),k[c]);return p}('(5($){$.r({1:{f:{l:\'J\',7:\'1\',t:u,v:K},w:u,3:5(a){2(!$.1.w)6;a="[L] "+a;$.1.x?g.3(a):$.1.y?h.g.3(a):M(a)},x:"g"8 h&&"N"8 h.g,y:"g"8 h&&"3"8 h.g}});$.z.1=\'1\'8 O.P(\'A\');$.k.j=$.k.9;$.k.9=5(a){$.1.3(\'8 9\');2(4[0]){$.1.3(\'Q R S T\');e b=$(4[0]);2(a!=m){$.1.3(\'8 U\');e c=b.j();e d=$(4).j(a);2(b.n($.1.f.7)&&c==b.i(\'1\')){b.o($.1.f.7)}6 d}2(b.n($.1.f.7)&&b.j()==b.i(\'1\')){$.1.3(\'p V W X\\\'s a 1\');6\'\'}Y{$.1.3(\'p Z 9\');6 b.j()}}$.1.3(\'p m\');6 m};$(h).q(\'10.1\',5(){e a=$(\'A.\'+$.1.f.7);2(a.11>0)a.9(\'\').i(\'B\',\'C\')});$.k.1=5(c){c=$.r({},$.1.f,c);2(!c.t&&$.z.1)6 4;6 4.12(5(){e b=$(4);2(!b.D(\'[1]\'))6;2(b.D(\':13\'))6;2(c.v)b.i(\'B\',\'C\');b.q(\'E.1\',5(){e a=$(4);2(4.F==a.i(\'1\')&&a.n(c.7))a.9(\'\').o(c.7).G(c.l)});b.q(\'H.1\',5(){e a=$(4);a.o(c.l);2(4.F==\'\')a.9(a.i(\'1\')).G(c.7)});b.I(\'H\');b.14(\'15\').16(5(){b.I(\'E.1\')})})}})(17);',62,70,'|placeholder|if|log|this|function|return|activeClass|in|val|||||var|settings|console|window|attr|plVal|fn|focusClass|undefined|hasClass|removeClass|returning|bind|extend||overrideSupport|false|preventRefreshIssues|debug|hasFirebug|hasConsoleLog|support|input|autocomplete|off|is|focus|value|addClass|blur|triggerHandler|placeholderFocus|true|Placeholder|alert|firebug|document|createElement|have|found|an|element|setter|empty|because|it|else|original|beforeunload|length|each|password|parents|form|submit|jQuery'.split('|'),0,{}));

/*!
 * jQuery Cycle Plugin (with Transition Definitions)
 * Examples and documentation at: http://jquery.malsup.com/cycle/
 * Copyright (c) 2007-2010 M. Alsup
 * Version: 2.9993 (26-MAY-2011)
 * Dual licensed under the MIT and GPL licenses.
 * http://jquery.malsup.com/license.html
 * Requires: jQuery v1.3.2 or later
 */
;(function($){var ver='2.9992';if($.support==undefined){$.support={opacity:!($.browser.msie)}}function debug(s){$.fn.cycle.debug&&log(s)}function log(){window.console&&console.log&&console.log('[cycle] '+Array.prototype.join.call(arguments,' '))}$.expr[':'].paused=function(el){return el.cyclePause}
$.fn.cycle=function(options,arg2){var o={s:this.selector,c:this.context};if(this.length===0&&options!='stop'){if(!$.isReady&&o.s){log('DOM not ready, queuing slideshow');$(function(){$(o.s,o.c).cycle(options,arg2)});return this}log('terminating; zero elements found by selector'+($.isReady?'':' (DOM not ready)'));return this}return this.each(function(){var opts=handleArguments(this,options,arg2);if(opts===false)return;opts.updateActivePagerLink=opts.updateActivePagerLink||$.fn.cycle.updateActivePagerLink;if(this.cycleTimeout)clearTimeout(this.cycleTimeout);this.cycleTimeout=this.cyclePause=0;var $cont=$(this);var $slides=opts.slideExpr?$(opts.slideExpr,this):$cont.children();var els=$slides.get();var opts2=buildOptions($cont,$slides,els,opts,o);if(opts2===false)return;if(els.length<2){log('terminating; too few slides: '+els.length);return}var startTime=opts2.continuous?10:getTimeout(els[opts2.currSlide],els[opts2.nextSlide],opts2,!opts2.backwards);if(startTime){startTime+=(opts2.delay||0);if(startTime<10)startTime=10;debug('first timeout: '+startTime);this.cycleTimeout=setTimeout(function(){go(els,opts2,0,!opts.backwards)},startTime)}})};function triggerPause(cont,byHover,onPager){var opts=$(cont).data('cycle.opts');var paused=!!cont.cyclePause;if(paused&&opts.paused)opts.paused(cont,opts,byHover,onPager);else if(!paused&&opts.resumed)opts.resumed(cont,opts,byHover,onPager)}function handleArguments(cont,options,arg2){if(cont.cycleStop==undefined)cont.cycleStop=0;if(options===undefined||options===null)options={};if(options.constructor==String){switch(options){case'destroy':case'stop':var opts=$(cont).data('cycle.opts');if(!opts)return false;cont.cycleStop++;if(cont.cycleTimeout)clearTimeout(cont.cycleTimeout);cont.cycleTimeout=0;opts.elements&&$(opts.elements).stop();$(cont).removeData('cycle.opts');if(options=='destroy')destroy(opts);return false;case'toggle':cont.cyclePause=(cont.cyclePause===1)?0:1;checkInstantResume(cont.cyclePause,arg2,cont);triggerPause(cont);return false;case'pause':cont.cyclePause=1;triggerPause(cont);return false;case'resume':cont.cyclePause=0;checkInstantResume(false,arg2,cont);triggerPause(cont);return false;case'prev':case'next':var opts=$(cont).data('cycle.opts');if(!opts){log('options not found, "prev/next" ignored');return false}$.fn.cycle[options](opts);return false;default:options={fx:options}};return options}else if(options.constructor==Number){var num=options;options=$(cont).data('cycle.opts');if(!options){log('options not found, can not advance slide');return false}if(num<0||num>=options.elements.length){log('invalid slide index: '+num);return false}options.nextSlide=num;if(cont.cycleTimeout){clearTimeout(cont.cycleTimeout);cont.cycleTimeout=0}if(typeof arg2=='string')options.oneTimeFx=arg2;go(options.elements,options,1,num>=options.currSlide);return false}return options;function checkInstantResume(isPaused,arg2,cont){if(!isPaused&&arg2===true){var options=$(cont).data('cycle.opts');if(!options){log('options not found, can not resume');return false}if(cont.cycleTimeout){clearTimeout(cont.cycleTimeout);cont.cycleTimeout=0}go(options.elements,options,1,!options.backwards)}}};function removeFilter(el,opts){if(!$.support.opacity&&opts.cleartype&&el.style.filter){try{el.style.removeAttribute('filter')}catch(smother){}}};function destroy(opts){if(opts.next)$(opts.next).unbind(opts.prevNextEvent);if(opts.prev)$(opts.prev).unbind(opts.prevNextEvent);if(opts.pager||opts.pagerAnchorBuilder)$.each(opts.pagerAnchors||[],function(){this.unbind().remove()});opts.pagerAnchors=null;if(opts.destroy)opts.destroy(opts)};function buildOptions($cont,$slides,els,options,o){var opts=$.extend({},$.fn.cycle.defaults,options||{},$.metadata?$cont.metadata():$.meta?$cont.data():{});var meta=$.isFunction($cont.data)?$cont.data(opts.metaAttr):null;if(meta)opts=$.extend(opts,meta);if(opts.autostop)opts.countdown=opts.autostopCount||els.length;var cont=$cont[0];$cont.data('cycle.opts',opts);opts.$cont=$cont;opts.stopCount=cont.cycleStop;opts.elements=els;opts.before=opts.before?[opts.before]:[];opts.after=opts.after?[opts.after]:[];if(!$.support.opacity&&opts.cleartype)opts.after.push(function(){removeFilter(this,opts)});if(opts.continuous)opts.after.push(function(){go(els,opts,0,!opts.backwards)});saveOriginalOpts(opts);if(!$.support.opacity&&opts.cleartype&&!opts.cleartypeNoBg)clearTypeFix($slides);if($cont.css('position')=='static')$cont.css('position','relative');if(opts.width)$cont.width(opts.width);if(opts.height&&opts.height!='auto')$cont.height(opts.height);if(opts.startingSlide)opts.startingSlide=parseInt(opts.startingSlide,10);else if(opts.backwards)opts.startingSlide=els.length-1;if(opts.random){opts.randomMap=[];for(var i=0;i<els.length;i++)opts.randomMap.push(i);opts.randomMap.sort(function(a,b){return Math.random()-0.5});opts.randomIndex=1;opts.startingSlide=opts.randomMap[1]}else if(opts.startingSlide>=els.length)opts.startingSlide=0;opts.currSlide=opts.startingSlide||0;var first=opts.startingSlide;$slides.css({position:'absolute',top:0,left:0}).hide().each(function(i){var z;if(opts.backwards)z=first?i<=first?els.length+(i-first):first-i:els.length-i;else z=first?i>=first?els.length-(i-first):first-i:els.length-i;$(this).css('z-index',z)});$(els[first]).css('opacity',1).show();removeFilter(els[first],opts);if(opts.fit){if(!opts.aspect){if(opts.width)$slides.width(opts.width);if(opts.height&&opts.height!='auto')$slides.height(opts.height)}else{$slides.each(function(){var $slide=$(this);var ratio=(opts.aspect===true)?$slide.width()/$slide.height():opts.aspect;if(opts.width&&$slide.width()!=opts.width){$slide.width(opts.width);$slide.height(opts.width/ratio)}if(opts.height&&$slide.height()<opts.height){$slide.height(opts.height);$slide.width(opts.height*ratio)}})}}if(opts.center&&((!opts.fit)||opts.aspect)){$slides.each(function(){var $slide=$(this);$slide.css({"margin-left":opts.width?((opts.width-$slide.width())/2)+"px":0,"margin-top":opts.height?((opts.height-$slide.height())/2)+"px":0})})}if(opts.center&&!opts.fit&&!opts.slideResize){$slides.each(function(){var $slide=$(this);$slide.css({"margin-left":opts.width?((opts.width-$slide.width())/2)+"px":0,"margin-top":opts.height?((opts.height-$slide.height())/2)+"px":0})})}var reshape=opts.containerResize&&!$cont.innerHeight();if(reshape){var maxw=0,maxh=0;for(var j=0;j<els.length;j++){var $e=$(els[j]),e=$e[0],w=$e.outerWidth(),h=$e.outerHeight();if(!w)w=e.offsetWidth||e.width||$e.attr('width');if(!h)h=e.offsetHeight||e.height||$e.attr('height');maxw=w>maxw?w:maxw;maxh=h>maxh?h:maxh}if(maxw>0&&maxh>0)$cont.css({width:maxw+'px',height:maxh+'px'})}var pauseFlag=false;if(opts.pause)$cont.hover(function(){pauseFlag=true;this.cyclePause++;triggerPause(cont,true)},function(){pauseFlag&&this.cyclePause--;triggerPause(cont,true)});if(supportMultiTransitions(opts)===false)return false;var requeue=false;options.requeueAttempts=options.requeueAttempts||0;$slides.each(function(){var $el=$(this);this.cycleH=(opts.fit&&opts.height)?opts.height:($el.height()||this.offsetHeight||this.height||$el.attr('height')||0);this.cycleW=(opts.fit&&opts.width)?opts.width:($el.width()||this.offsetWidth||this.width||$el.attr('width')||0);if($el.is('img')){var loadingIE=($.browser.msie&&this.cycleW==28&&this.cycleH==30&&!this.complete);var loadingFF=($.browser.mozilla&&this.cycleW==34&&this.cycleH==19&&!this.complete);var loadingOp=($.browser.opera&&((this.cycleW==42&&this.cycleH==19)||(this.cycleW==37&&this.cycleH==17))&&!this.complete);var loadingOther=(this.cycleH==0&&this.cycleW==0&&!this.complete);if(loadingIE||loadingFF||loadingOp||loadingOther){if(o.s&&opts.requeueOnImageNotLoaded&&++options.requeueAttempts<100){log(options.requeueAttempts,' - img slide not loaded, requeuing slideshow: ',this.src,this.cycleW,this.cycleH);setTimeout(function(){$(o.s,o.c).cycle(options)},opts.requeueTimeout);requeue=true;return false}else{log('could not determine size of image: '+this.src,this.cycleW,this.cycleH)}}}return true});if(requeue)return false;opts.cssBefore=opts.cssBefore||{};opts.cssAfter=opts.cssAfter||{};opts.cssFirst=opts.cssFirst||{};opts.animIn=opts.animIn||{};opts.animOut=opts.animOut||{};$slides.not(':eq('+first+')').css(opts.cssBefore);$($slides[first]).css(opts.cssFirst);if(opts.timeout){opts.timeout=parseInt(opts.timeout,10);if(opts.speed.constructor==String)opts.speed=$.fx.speeds[opts.speed]||parseInt(opts.speed,10);if(!opts.sync)opts.speed=opts.speed/2;var buffer=opts.fx=='none'?0:opts.fx=='shuffle'?500:250;while((opts.timeout-opts.speed)<buffer)opts.timeout+=opts.speed}if(opts.easing)opts.easeIn=opts.easeOut=opts.easing;if(!opts.speedIn)opts.speedIn=opts.speed;if(!opts.speedOut)opts.speedOut=opts.speed;opts.slideCount=els.length;opts.currSlide=opts.lastSlide=first;if(opts.random){if(++opts.randomIndex==els.length)opts.randomIndex=0;opts.nextSlide=opts.randomMap[opts.randomIndex]}else if(opts.backwards)opts.nextSlide=opts.startingSlide==0?(els.length-1):opts.startingSlide-1;else opts.nextSlide=opts.startingSlide>=(els.length-1)?0:opts.startingSlide+1;if(!opts.multiFx){var init=$.fn.cycle.transitions[opts.fx];if($.isFunction(init))init($cont,$slides,opts);else if(opts.fx!='custom'&&!opts.multiFx){log('unknown transition: '+opts.fx,'; slideshow terminating');return false}}var e0=$slides[first];if(!opts.skipInitializationCallbacks){if(opts.before.length)opts.before[0].apply(e0,[e0,e0,opts,true]);if(opts.after.length)opts.after[0].apply(e0,[e0,e0,opts,true])}if(opts.next)$(opts.next).bind(opts.prevNextEvent,function(){return advance(opts,1)});if(opts.prev)$(opts.prev).bind(opts.prevNextEvent,function(){return advance(opts,0)});if(opts.pager||opts.pagerAnchorBuilder)buildPager(els,opts);exposeAddSlide(opts,els);return opts};function saveOriginalOpts(opts){opts.original={before:[],after:[]};opts.original.cssBefore=$.extend({},opts.cssBefore);opts.original.cssAfter=$.extend({},opts.cssAfter);opts.original.animIn=$.extend({},opts.animIn);opts.original.animOut=$.extend({},opts.animOut);$.each(opts.before,function(){opts.original.before.push(this)});$.each(opts.after,function(){opts.original.after.push(this)})};function supportMultiTransitions(opts){var i,tx,txs=$.fn.cycle.transitions;if(opts.fx.indexOf(',')>0){opts.multiFx=true;opts.fxs=opts.fx.replace(/\s*/g,'').split(',');for(i=0;i<opts.fxs.length;i++){var fx=opts.fxs[i];tx=txs[fx];if(!tx||!txs.hasOwnProperty(fx)||!$.isFunction(tx)){log('discarding unknown transition: ',fx);opts.fxs.splice(i,1);i--}}if(!opts.fxs.length){log('No valid transitions named; slideshow terminating.');return false}}else if(opts.fx=='all'){opts.multiFx=true;opts.fxs=[];for(p in txs){tx=txs[p];if(txs.hasOwnProperty(p)&&$.isFunction(tx))opts.fxs.push(p)}}if(opts.multiFx&&opts.randomizeEffects){var r1=Math.floor(Math.random()*20)+30;for(i=0;i<r1;i++){var r2=Math.floor(Math.random()*opts.fxs.length);opts.fxs.push(opts.fxs.splice(r2,1)[0])}debug('randomized fx sequence: ',opts.fxs)}return true};function exposeAddSlide(opts,els){opts.addSlide=function(newSlide,prepend){var $s=$(newSlide),s=$s[0];if(!opts.autostopCount)opts.countdown++;els[prepend?'unshift':'push'](s);if(opts.els)opts.els[prepend?'unshift':'push'](s);opts.slideCount=els.length;$s.css('position','absolute');$s[prepend?'prependTo':'appendTo'](opts.$cont);if(prepend){opts.currSlide++;opts.nextSlide++}if(!$.support.opacity&&opts.cleartype&&!opts.cleartypeNoBg)clearTypeFix($s);if(opts.fit&&opts.width)$s.width(opts.width);if(opts.fit&&opts.height&&opts.height!='auto')$s.height(opts.height);s.cycleH=(opts.fit&&opts.height)?opts.height:$s.height();s.cycleW=(opts.fit&&opts.width)?opts.width:$s.width();$s.css(opts.cssBefore);if(opts.pager||opts.pagerAnchorBuilder)$.fn.cycle.createPagerAnchor(els.length-1,s,$(opts.pager),els,opts);if($.isFunction(opts.onAddSlide))opts.onAddSlide($s);else $s.hide()}}$.fn.cycle.resetState=function(opts,fx){fx=fx||opts.fx;opts.before=[];opts.after=[];opts.cssBefore=$.extend({},opts.original.cssBefore);opts.cssAfter=$.extend({},opts.original.cssAfter);opts.animIn=$.extend({},opts.original.animIn);opts.animOut=$.extend({},opts.original.animOut);opts.fxFn=null;$.each(opts.original.before,function(){opts.before.push(this)});$.each(opts.original.after,function(){opts.after.push(this)});var init=$.fn.cycle.transitions[fx];if($.isFunction(init))init(opts.$cont,$(opts.elements),opts)};function go(els,opts,manual,fwd){if(manual&&opts.busy&&opts.manualTrump){debug('manualTrump in go(), stopping active transition');$(els).stop(true,true);opts.busy=0}if(opts.busy){debug('transition active, ignoring new tx request');return}var p=opts.$cont[0],curr=els[opts.currSlide],next=els[opts.nextSlide];if(p.cycleStop!=opts.stopCount||p.cycleTimeout===0&&!manual)return;if(!manual&&!p.cyclePause&&!opts.bounce&&((opts.autostop&&(--opts.countdown<=0))||(opts.nowrap&&!opts.random&&opts.nextSlide<opts.currSlide))){if(opts.end)opts.end(opts);return}var changed=false;if((manual||!p.cyclePause)&&(opts.nextSlide!=opts.currSlide)){changed=true;var fx=opts.fx;curr.cycleH=curr.cycleH||$(curr).height();curr.cycleW=curr.cycleW||$(curr).width();next.cycleH=next.cycleH||$(next).height();next.cycleW=next.cycleW||$(next).width();if(opts.multiFx){if(fwd&&(opts.lastFx==undefined||++opts.lastFx>=opts.fxs.length))opts.lastFx=0;else if(!fwd&&(opts.lastFx==undefined||--opts.lastFx<0))opts.lastFx=opts.fxs.length-1;fx=opts.fxs[opts.lastFx]}if(opts.oneTimeFx){fx=opts.oneTimeFx;opts.oneTimeFx=null}$.fn.cycle.resetState(opts,fx);if(opts.before.length)$.each(opts.before,function(i,o){if(p.cycleStop!=opts.stopCount)return;o.apply(next,[curr,next,opts,fwd])});var after=function(){opts.busy=0;$.each(opts.after,function(i,o){if(p.cycleStop!=opts.stopCount)return;o.apply(next,[curr,next,opts,fwd])})};debug('tx firing('+fx+'); currSlide: '+opts.currSlide+'; nextSlide: '+opts.nextSlide);opts.busy=1;if(opts.fxFn)opts.fxFn(curr,next,opts,after,fwd,manual&&opts.fastOnEvent);else if($.isFunction($.fn.cycle[opts.fx]))$.fn.cycle[opts.fx](curr,next,opts,after,fwd,manual&&opts.fastOnEvent);else $.fn.cycle.custom(curr,next,opts,after,fwd,manual&&opts.fastOnEvent)}if(changed||opts.nextSlide==opts.currSlide){opts.lastSlide=opts.currSlide;if(opts.random){opts.currSlide=opts.nextSlide;if(++opts.randomIndex==els.length)opts.randomIndex=0;opts.nextSlide=opts.randomMap[opts.randomIndex];if(opts.nextSlide==opts.currSlide)opts.nextSlide=(opts.currSlide==opts.slideCount-1)?0:opts.currSlide+1}else if(opts.backwards){var roll=(opts.nextSlide-1)<0;if(roll&&opts.bounce){opts.backwards=!opts.backwards;opts.nextSlide=1;opts.currSlide=0}else{opts.nextSlide=roll?(els.length-1):opts.nextSlide-1;opts.currSlide=roll?0:opts.nextSlide+1}}else{var roll=(opts.nextSlide+1)==els.length;if(roll&&opts.bounce){opts.backwards=!opts.backwards;opts.nextSlide=els.length-2;opts.currSlide=els.length-1}else{opts.nextSlide=roll?0:opts.nextSlide+1;opts.currSlide=roll?els.length-1:opts.nextSlide-1}}}if(changed&&opts.pager)opts.updateActivePagerLink(opts.pager,opts.currSlide,opts.activePagerClass);var ms=0;if(opts.timeout&&!opts.continuous)ms=getTimeout(els[opts.currSlide],els[opts.nextSlide],opts,fwd);else if(opts.continuous&&p.cyclePause)ms=10;if(ms>0)p.cycleTimeout=setTimeout(function(){go(els,opts,0,!opts.backwards)},ms)};$.fn.cycle.updateActivePagerLink=function(pager,currSlide,clsName){$(pager).each(function(){$(this).children().removeClass(clsName).eq(currSlide).addClass(clsName)})};function getTimeout(curr,next,opts,fwd){if(opts.timeoutFn){var t=opts.timeoutFn.call(curr,curr,next,opts,fwd);while(opts.fx!='none'&&(t-opts.speed)<250)t+=opts.speed;debug('calculated timeout: '+t+'; speed: '+opts.speed);if(t!==false)return t}return opts.timeout};$.fn.cycle.next=function(opts){advance(opts,1)};$.fn.cycle.prev=function(opts){advance(opts,0)};function advance(opts,moveForward){var val=moveForward?1:-1;var els=opts.elements;var p=opts.$cont[0],timeout=p.cycleTimeout;if(timeout){clearTimeout(timeout);p.cycleTimeout=0}if(opts.random&&val<0){opts.randomIndex--;if(--opts.randomIndex==-2)opts.randomIndex=els.length-2;else if(opts.randomIndex==-1)opts.randomIndex=els.length-1;opts.nextSlide=opts.randomMap[opts.randomIndex]}else if(opts.random){opts.nextSlide=opts.randomMap[opts.randomIndex]}else{opts.nextSlide=opts.currSlide+val;if(opts.nextSlide<0){if(opts.nowrap)return false;opts.nextSlide=els.length-1}else if(opts.nextSlide>=els.length){if(opts.nowrap)return false;opts.nextSlide=0}}var cb=opts.onPrevNextEvent||opts.prevNextClick;if($.isFunction(cb))cb(val>0,opts.nextSlide,els[opts.nextSlide]);go(els,opts,1,moveForward);return false};function buildPager(els,opts){var $p=$(opts.pager);$.each(els,function(i,o){$.fn.cycle.createPagerAnchor(i,o,$p,els,opts)});opts.updateActivePagerLink(opts.pager,opts.startingSlide,opts.activePagerClass)};$.fn.cycle.createPagerAnchor=function(i,el,$p,els,opts){var a;if($.isFunction(opts.pagerAnchorBuilder)){a=opts.pagerAnchorBuilder(i,el);debug('pagerAnchorBuilder('+i+', el) returned: '+a)}else a='<a href="#">'+(i+1)+'</a>';if(!a)return;var $a=$(a);if($a.parents('body').length===0){var arr=[];if($p.length>1){$p.each(function(){var $clone=$a.clone(true);$(this).append($clone);arr.push($clone[0])});$a=$(arr)}else{$a.appendTo($p)}}opts.pagerAnchors=opts.pagerAnchors||[];opts.pagerAnchors.push($a);$a.bind(opts.pagerEvent,function(e){e.preventDefault();opts.nextSlide=i;var p=opts.$cont[0],timeout=p.cycleTimeout;if(timeout){clearTimeout(timeout);p.cycleTimeout=0}var cb=opts.onPagerEvent||opts.pagerClick;if($.isFunction(cb))cb(opts.nextSlide,els[opts.nextSlide]);go(els,opts,1,opts.currSlide<i)});if(!/^click/.test(opts.pagerEvent)&&!opts.allowPagerClickBubble)$a.bind('click.cycle',function(){return false});var pauseFlag=false;if(opts.pauseOnPagerHover){$a.hover(function(){pauseFlag=true;opts.$cont[0].cyclePause++;triggerPause(cont,true,true)},function(){pauseFlag&&opts.$cont[0].cyclePause--;triggerPause(cont,true,true)})}};$.fn.cycle.hopsFromLast=function(opts,fwd){var hops,l=opts.lastSlide,c=opts.currSlide;if(fwd)hops=c>l?c-l:opts.slideCount-l;else hops=c<l?l-c:l+opts.slideCount-c;return hops};function clearTypeFix($slides){debug('applying clearType background-color hack');function hex(s){s=parseInt(s,10).toString(16);return s.length<2?'0'+s:s};function getBg(e){for(;e&&e.nodeName.toLowerCase()!='html';e=e.parentNode){var v=$.css(e,'background-color');if(v&&v.indexOf('rgb')>=0){var rgb=v.match(/\d+/g);return'#'+hex(rgb[0])+hex(rgb[1])+hex(rgb[2])}if(v&&v!='transparent')return v}return'#ffffff'};$slides.each(function(){$(this).css('background-color',getBg(this))})};$.fn.cycle.commonReset=function(curr,next,opts,w,h,rev){$(opts.elements).not(curr).hide();if(typeof opts.cssBefore.opacity=='undefined')opts.cssBefore.opacity=1;opts.cssBefore.display='block';if(opts.slideResize&&w!==false&&next.cycleW>0)opts.cssBefore.width=next.cycleW;if(opts.slideResize&&h!==false&&next.cycleH>0)opts.cssBefore.height=next.cycleH;opts.cssAfter=opts.cssAfter||{};opts.cssAfter.display='none';$(curr).css('zIndex',opts.slideCount+(rev===true?1:0));$(next).css('zIndex',opts.slideCount+(rev===true?0:1))};$.fn.cycle.custom=function(curr,next,opts,cb,fwd,speedOverride){var $l=$(curr),$n=$(next);var speedIn=opts.speedIn,speedOut=opts.speedOut,easeIn=opts.easeIn,easeOut=opts.easeOut;$n.css(opts.cssBefore);if(speedOverride){if(typeof speedOverride=='number')speedIn=speedOut=speedOverride;else speedIn=speedOut=1;easeIn=easeOut=null}var fn=function(){$n.animate(opts.animIn,speedIn,easeIn,function(){cb()})};$l.animate(opts.animOut,speedOut,easeOut,function(){$l.css(opts.cssAfter);if(!opts.sync)fn()});if(opts.sync)fn()};$.fn.cycle.transitions={fade:function($cont,$slides,opts){$slides.not(':eq('+opts.currSlide+')').css('opacity',0);opts.before.push(function(curr,next,opts){$.fn.cycle.commonReset(curr,next,opts);opts.cssBefore.opacity=0});opts.animIn={opacity:1};opts.animOut={opacity:0};opts.cssBefore={top:0,left:0}}};$.fn.cycle.ver=function(){return ver};$.fn.cycle.defaults={activePagerClass:'activeSlide',after:null,allowPagerClickBubble:false,animIn:null,animOut:null,aspect:false,autostop:0,autostopCount:0,backwards:false,before:null,center:null,cleartype:!$.support.opacity,cleartypeNoBg:false,containerResize:1,continuous:0,cssAfter:null,cssBefore:null,delay:0,easeIn:null,easeOut:null,easing:null,end:null,fastOnEvent:0,fit:0,fx:'fade',fxFn:null,height:'auto',manualTrump:true,metaAttr:'cycle',next:null,nowrap:0,onPagerEvent:null,onPrevNextEvent:null,pager:null,pagerAnchorBuilder:null,pagerEvent:'click.cycle',pause:0,pauseOnPagerHover:0,prev:null,prevNextEvent:'click.cycle',random:0,randomizeEffects:1,requeueOnImageNotLoaded:true,requeueTimeout:250,rev:0,shuffle:null,skipInitializationCallbacks:false,slideExpr:null,slideResize:1,speed:1000,speedIn:null,speedOut:null,startingSlide:0,sync:1,timeout:4000,timeoutFn:null,updateActivePagerLink:null,width:null}})(jQuery);(function($){$.fn.cycle.transitions.none=function($cont,$slides,opts){opts.fxFn=function(curr,next,opts,after){$(next).show();$(curr).hide();after()}};$.fn.cycle.transitions.fadeout=function($cont,$slides,opts){$slides.not(':eq('+opts.currSlide+')').css({display:'block','opacity':1});opts.before.push(function(curr,next,opts,w,h,rev){$(curr).css('zIndex',opts.slideCount+(!rev===true?1:0));$(next).css('zIndex',opts.slideCount+(!rev===true?0:1))});opts.animIn.opacity=1;opts.animOut.opacity=0;opts.cssBefore.opacity=1;opts.cssBefore.display='block';opts.cssAfter.zIndex=0};$.fn.cycle.transitions.scrollUp=function($cont,$slides,opts){$cont.css('overflow','hidden');opts.before.push($.fn.cycle.commonReset);var h=$cont.height();opts.cssBefore.top=h;opts.cssBefore.left=0;opts.cssFirst.top=0;opts.animIn.top=0;opts.animOut.top=-h};$.fn.cycle.transitions.scrollDown=function($cont,$slides,opts){$cont.css('overflow','hidden');opts.before.push($.fn.cycle.commonReset);var h=$cont.height();opts.cssFirst.top=0;opts.cssBefore.top=-h;opts.cssBefore.left=0;opts.animIn.top=0;opts.animOut.top=h};$.fn.cycle.transitions.scrollLeft=function($cont,$slides,opts){$cont.css('overflow','hidden');opts.before.push($.fn.cycle.commonReset);var w=$cont.width();opts.cssFirst.left=0;opts.cssBefore.left=w;opts.cssBefore.top=0;opts.animIn.left=0;opts.animOut.left=0-w};$.fn.cycle.transitions.scrollRight=function($cont,$slides,opts){$cont.css('overflow','hidden');opts.before.push($.fn.cycle.commonReset);var w=$cont.width();opts.cssFirst.left=0;opts.cssBefore.left=-w;opts.cssBefore.top=0;opts.animIn.left=0;opts.animOut.left=w};$.fn.cycle.transitions.scrollHorz=function($cont,$slides,opts){$cont.css('overflow','hidden').width();opts.before.push(function(curr,next,opts,fwd){if(opts.rev)fwd=!fwd;$.fn.cycle.commonReset(curr,next,opts);opts.cssBefore.left=fwd?(next.cycleW-1):(1-next.cycleW);opts.animOut.left=fwd?-curr.cycleW:curr.cycleW});opts.cssFirst.left=0;opts.cssBefore.top=0;opts.animIn.left=0;opts.animOut.top=0};$.fn.cycle.transitions.scrollVert=function($cont,$slides,opts){$cont.css('overflow','hidden');opts.before.push(function(curr,next,opts,fwd){if(opts.rev)fwd=!fwd;$.fn.cycle.commonReset(curr,next,opts);opts.cssBefore.top=fwd?(1-next.cycleH):(next.cycleH-1);opts.animOut.top=fwd?curr.cycleH:-curr.cycleH});opts.cssFirst.top=0;opts.cssBefore.left=0;opts.animIn.top=0;opts.animOut.left=0};$.fn.cycle.transitions.slideX=function($cont,$slides,opts){opts.before.push(function(curr,next,opts){$(opts.elements).not(curr).hide();$.fn.cycle.commonReset(curr,next,opts,false,true);opts.animIn.width=next.cycleW});opts.cssBefore.left=0;opts.cssBefore.top=0;opts.cssBefore.width=0;opts.animIn.width='show';opts.animOut.width=0};$.fn.cycle.transitions.slideY=function($cont,$slides,opts){opts.before.push(function(curr,next,opts){$(opts.elements).not(curr).hide();$.fn.cycle.commonReset(curr,next,opts,true,false);opts.animIn.height=next.cycleH});opts.cssBefore.left=0;opts.cssBefore.top=0;opts.cssBefore.height=0;opts.animIn.height='show';opts.animOut.height=0};$.fn.cycle.transitions.shuffle=function($cont,$slides,opts){var i,w=$cont.css('overflow','visible').width();$slides.css({left:0,top:0});opts.before.push(function(curr,next,opts){$.fn.cycle.commonReset(curr,next,opts,true,true,true)});if(!opts.speedAdjusted){opts.speed=opts.speed/2;opts.speedAdjusted=true}opts.random=0;opts.shuffle=opts.shuffle||{left:-w,top:15};opts.els=[];for(i=0;i<$slides.length;i++)opts.els.push($slides[i]);for(i=0;i<opts.currSlide;i++)opts.els.push(opts.els.shift());opts.fxFn=function(curr,next,opts,cb,fwd){if(opts.rev)fwd=!fwd;var $el=fwd?$(curr):$(next);$(next).css(opts.cssBefore);var count=opts.slideCount;$el.animate(opts.shuffle,opts.speedIn,opts.easeIn,function(){var hops=$.fn.cycle.hopsFromLast(opts,fwd);for(var k=0;k<hops;k++)fwd?opts.els.push(opts.els.shift()):opts.els.unshift(opts.els.pop());if(fwd){for(var i=0,len=opts.els.length;i<len;i++)$(opts.els[i]).css('z-index',len-i+count)}else{var z=$(curr).css('z-index');$el.css('z-index',parseInt(z,10)+1+count)}$el.animate({left:0,top:0},opts.speedOut,opts.easeOut,function(){$(fwd?this:curr).hide();if(cb)cb()})})};$.extend(opts.cssBefore,{display:'block',opacity:1,top:0,left:0})};$.fn.cycle.transitions.turnUp=function($cont,$slides,opts){opts.before.push(function(curr,next,opts){$.fn.cycle.commonReset(curr,next,opts,true,false);opts.cssBefore.top=next.cycleH;opts.animIn.height=next.cycleH;opts.animOut.width=next.cycleW});opts.cssFirst.top=0;opts.cssBefore.left=0;opts.cssBefore.height=0;opts.animIn.top=0;opts.animOut.height=0};$.fn.cycle.transitions.turnDown=function($cont,$slides,opts){opts.before.push(function(curr,next,opts){$.fn.cycle.commonReset(curr,next,opts,true,false);opts.animIn.height=next.cycleH;opts.animOut.top=curr.cycleH});opts.cssFirst.top=0;opts.cssBefore.left=0;opts.cssBefore.top=0;opts.cssBefore.height=0;opts.animOut.height=0};$.fn.cycle.transitions.turnLeft=function($cont,$slides,opts){opts.before.push(function(curr,next,opts){$.fn.cycle.commonReset(curr,next,opts,false,true);opts.cssBefore.left=next.cycleW;opts.animIn.width=next.cycleW});opts.cssBefore.top=0;opts.cssBefore.width=0;opts.animIn.left=0;opts.animOut.width=0};$.fn.cycle.transitions.turnRight=function($cont,$slides,opts){opts.before.push(function(curr,next,opts){$.fn.cycle.commonReset(curr,next,opts,false,true);opts.animIn.width=next.cycleW;opts.animOut.left=curr.cycleW});$.extend(opts.cssBefore,{top:0,left:0,width:0});opts.animIn.left=0;opts.animOut.width=0};$.fn.cycle.transitions.zoom=function($cont,$slides,opts){opts.before.push(function(curr,next,opts){$.fn.cycle.commonReset(curr,next,opts,false,false,true);opts.cssBefore.top=next.cycleH/2;opts.cssBefore.left=next.cycleW/2;$.extend(opts.animIn,{top:0,left:0,width:next.cycleW,height:next.cycleH});$.extend(opts.animOut,{width:0,height:0,top:curr.cycleH/2,left:curr.cycleW/2})});opts.cssFirst.top=0;opts.cssFirst.left=0;opts.cssBefore.width=0;opts.cssBefore.height=0};$.fn.cycle.transitions.fadeZoom=function($cont,$slides,opts){opts.before.push(function(curr,next,opts){$.fn.cycle.commonReset(curr,next,opts,false,false);opts.cssBefore.left=next.cycleW/2;opts.cssBefore.top=next.cycleH/2;$.extend(opts.animIn,{top:0,left:0,width:next.cycleW,height:next.cycleH})});opts.cssBefore.width=0;opts.cssBefore.height=0;opts.animOut.opacity=0};$.fn.cycle.transitions.blindX=function($cont,$slides,opts){var w=$cont.css('overflow','hidden').width();opts.before.push(function(curr,next,opts){$.fn.cycle.commonReset(curr,next,opts);opts.animIn.width=next.cycleW;opts.animOut.left=curr.cycleW});opts.cssBefore.left=w;opts.cssBefore.top=0;opts.animIn.left=0;opts.animOut.left=w};$.fn.cycle.transitions.blindY=function($cont,$slides,opts){var h=$cont.css('overflow','hidden').height();opts.before.push(function(curr,next,opts){$.fn.cycle.commonReset(curr,next,opts);opts.animIn.height=next.cycleH;opts.animOut.top=curr.cycleH});opts.cssBefore.top=h;opts.cssBefore.left=0;opts.animIn.top=0;opts.animOut.top=h};$.fn.cycle.transitions.blindZ=function($cont,$slides,opts){var h=$cont.css('overflow','hidden').height();var w=$cont.width();opts.before.push(function(curr,next,opts){$.fn.cycle.commonReset(curr,next,opts);opts.animIn.height=next.cycleH;opts.animOut.top=curr.cycleH});opts.cssBefore.top=h;opts.cssBefore.left=w;opts.animIn.top=0;opts.animIn.left=0;opts.animOut.top=h;opts.animOut.left=w};$.fn.cycle.transitions.growX=function($cont,$slides,opts){opts.before.push(function(curr,next,opts){$.fn.cycle.commonReset(curr,next,opts,false,true);opts.cssBefore.left=this.cycleW/2;opts.animIn.left=0;opts.animIn.width=this.cycleW;opts.animOut.left=0});opts.cssBefore.top=0;opts.cssBefore.width=0};$.fn.cycle.transitions.growY=function($cont,$slides,opts){opts.before.push(function(curr,next,opts){$.fn.cycle.commonReset(curr,next,opts,true,false);opts.cssBefore.top=this.cycleH/2;opts.animIn.top=0;opts.animIn.height=this.cycleH;opts.animOut.top=0});opts.cssBefore.height=0;opts.cssBefore.left=0};$.fn.cycle.transitions.curtainX=function($cont,$slides,opts){opts.before.push(function(curr,next,opts){$.fn.cycle.commonReset(curr,next,opts,false,true,true);opts.cssBefore.left=next.cycleW/2;opts.animIn.left=0;opts.animIn.width=this.cycleW;opts.animOut.left=curr.cycleW/2;opts.animOut.width=0});opts.cssBefore.top=0;opts.cssBefore.width=0};$.fn.cycle.transitions.curtainY=function($cont,$slides,opts){opts.before.push(function(curr,next,opts){$.fn.cycle.commonReset(curr,next,opts,true,false,true);opts.cssBefore.top=next.cycleH/2;opts.animIn.top=0;opts.animIn.height=next.cycleH;opts.animOut.top=curr.cycleH/2;opts.animOut.height=0});opts.cssBefore.height=0;opts.cssBefore.left=0};$.fn.cycle.transitions.cover=function($cont,$slides,opts){var d=opts.direction||'left';var w=$cont.css('overflow','hidden').width();var h=$cont.height();opts.before.push(function(curr,next,opts){$.fn.cycle.commonReset(curr,next,opts);if(d=='right')opts.cssBefore.left=-w;else if(d=='up')opts.cssBefore.top=h;else if(d=='down')opts.cssBefore.top=-h;else opts.cssBefore.left=w});opts.animIn.left=0;opts.animIn.top=0;opts.cssBefore.top=0;opts.cssBefore.left=0};$.fn.cycle.transitions.uncover=function($cont,$slides,opts){var d=opts.direction||'left';var w=$cont.css('overflow','hidden').width();var h=$cont.height();opts.before.push(function(curr,next,opts){$.fn.cycle.commonReset(curr,next,opts,true,true,true);if(d=='right')opts.animOut.left=w;else if(d=='up')opts.animOut.top=-h;else if(d=='down')opts.animOut.top=h;else opts.animOut.left=-w});opts.animIn.left=0;opts.animIn.top=0;opts.cssBefore.top=0;opts.cssBefore.left=0};$.fn.cycle.transitions.toss=function($cont,$slides,opts){var w=$cont.css('overflow','visible').width();var h=$cont.height();opts.before.push(function(curr,next,opts){$.fn.cycle.commonReset(curr,next,opts,true,true,true);if(!opts.animOut.left&&!opts.animOut.top)$.extend(opts.animOut,{left:w*2,top:-h/2,opacity:0});else opts.animOut.opacity=0});opts.cssBefore.left=0;opts.cssBefore.top=0;opts.animIn.left=0};$.fn.cycle.transitions.wipe=function($cont,$slides,opts){var w=$cont.css('overflow','hidden').width();var h=$cont.height();opts.cssBefore=opts.cssBefore||{};var clip;if(opts.clip){if(/l2r/.test(opts.clip))clip='rect(0px 0px '+h+'px 0px)';else if(/r2l/.test(opts.clip))clip='rect(0px '+w+'px '+h+'px '+w+'px)';else if(/t2b/.test(opts.clip))clip='rect(0px '+w+'px 0px 0px)';else if(/b2t/.test(opts.clip))clip='rect('+h+'px '+w+'px '+h+'px 0px)';else if(/zoom/.test(opts.clip)){var top=parseInt(h/2,10);var left=parseInt(w/2,10);clip='rect('+top+'px '+left+'px '+top+'px '+left+'px)'}}opts.cssBefore.clip=opts.cssBefore.clip||clip||'rect(0px 0px 0px 0px)';var d=opts.cssBefore.clip.match(/(\d+)/g);var t=parseInt(d[0],10),r=parseInt(d[1],10),b=parseInt(d[2],10),l=parseInt(d[3],10);opts.before.push(function(curr,next,opts){if(curr==next)return;var $curr=$(curr),$next=$(next);$.fn.cycle.commonReset(curr,next,opts,true,true,false);opts.cssAfter.display='block';var step=1,count=parseInt((opts.speedIn/13),10)-1;(function f(){var tt=t?t-parseInt(step*(t/count),10):0;var ll=l?l-parseInt(step*(l/count),10):0;var bb=b<h?b+parseInt(step*((h-b)/count||1),10):h;var rr=r<w?r+parseInt(step*((w-r)/count||1),10):w;$next.css({clip:'rect('+tt+'px '+rr+'px '+bb+'px '+ll+'px)'});(step++<=count)?setTimeout(f,13):$curr.css('display','none')})()});$.extend(opts.cssBefore,{display:'block',opacity:1,top:0,left:0});opts.animIn={left:0};opts.animOut={left:0}}})(jQuery);


/*!
 * (v) hrefID jQuery extention
 * returns a valid #hash string from link href attribute in Internet Explorer
 */
(function($){$.fn.extend({hrefId:function(){return $(this).attr('href').substr($(this).attr('href').indexOf('#'));}});})(jQuery);


// function range()
// http://kevin.vanzonneveld.net
// +   original by: Waldo Malqui Silva
eval(function(p,a,c,k,e,r){e=function(c){return c.toString(a)};if(!''.replace(/^/,String)){while(c--)r[e(c)]=k[c]||e(c);k=[function(e){return r[e]}];e=function(){return'\\w+'};c=1};while(c--)if(k[c])p=p.replace(new RegExp('\\b'+e(c)+'\\b','g'),k[c]);return p}('m n(a,b,c){4 d=[];4 e,2,5;4 f=c||1;4 g=8;6(!3(a)&&!3(b)){e=a;2=b}7 6(3(a)&&3(b)){g=9;e=a.h(0);2=b.h(0)}7{e=(3(a)?0:a);2=(3(b)?0:b)}5=((e>2)?8:9);6(5){i(e<=2){d.j(((g)?k.l(e):e));e+=f}}7{i(e>=2){d.j(((g)?k.l(e):e));e-=f}}o d}',25,25,'||endval|isNaN|var|plus|if|else|false|true||||||||charCodeAt|while|push|String|fromCharCode|function|range|return'.split('|'),0,{}))

/*
 * jQuery Color Animations
 * Copyright 2007 John Resig
 * Released under the MIT and GPL licenses.
 */
eval(function(p,a,c,k,e,r){e=function(c){return(c<a?'':e(parseInt(c/a)))+((c=c%a)>35?String.fromCharCode(c+29):c.toString(36))};if(!''.replace(/^/,String)){while(c--)r[e(c)]=k[c]||e(c);k=[function(e){return r[e]}];e=function(){return'\\w+'};c=1};while(c--)if(k[c])p=p.replace(new RegExp('\\b'+e(c)+'\\b','g'),k[c]);return p}('(l(d){d.M([\'A\',\'N\',\'O\',\'P\',\'Q\',\'R\',\'S\'],l(i,b){d.T.U[b]=l(a){7(a.V==0){a.8=B(a.C,b);a.m=q(a.m)}a.C.W[b]="r("+[g.t(g.u(5((a.v*(a.m[0]-a.8[0]))+a.8[0]),4),0),g.t(g.u(5((a.v*(a.m[1]-a.8[1]))+a.8[1]),4),0),g.t(g.u(5((a.v*(a.m[2]-a.8[2]))+a.8[2]),4),0)].X(",")+")"}});l q(a){w b;7(a&&a.Y==Z&&a.10==3)f a;7(b=/r\\(\\s*([0-9]{1,3})\\s*,\\s*([0-9]{1,3})\\s*,\\s*([0-9]{1,3})\\s*\\)/.n(a))f[5(b[1]),5(b[2]),5(b[3])];7(b=/r\\(\\s*([0-9]+(?:\\.[0-9]+)?)\\%\\s*,\\s*([0-9]+(?:\\.[0-9]+)?)\\%\\s*,\\s*([0-9]+(?:\\.[0-9]+)?)\\%\\s*\\)/.n(a))f[x(b[1])*2.y,x(b[2])*2.y,x(b[3])*2.y];7(b=/#([a-h-j-9]{2})([a-h-j-9]{2})([a-h-j-9]{2})/.n(a))f[5(b[1],16),5(b[2],16),5(b[3],16)];7(b=/#([a-h-j-9])([a-h-j-9])([a-h-j-9])/.n(a))f[5(b[1]+b[1],16),5(b[2]+b[2],16),5(b[3]+b[3],16)];f e[d.11(a).12()]}l B(a,b){w c;13{c=d.14(a,b);7(c!=\'\'&&c!=\'15\'||d.17(a,"18"))19;b="A"}1a(a=a.1b);f q(c)};w e={1c:[0,4,4],1d:[D,4,4],1e:[E,E,1f],1g:[0,0,0],1h:[0,0,4],1i:[F,G,G],1j:[0,4,4],1k:[0,0,k],1l:[0,k,k],1m:[z,z,z],1n:[0,1o,0],1p:[1q,1r,H],1s:[k,0,k],1t:[1u,H,1v],1w:[4,I,0],1x:[1y,1z,1A],1B:[k,0,0],1C:[1D,1E,1F],1G:[1H,0,o],1I:[4,0,4],1J:[4,1K,0],1L:[0,6,0],1M:[1N,0,1O],1P:[D,J,I],1Q:[1R,1S,J],1T:[K,4,4],1U:[L,1V,L],1W:[o,o,o],1X:[4,1Y,1Z],20:[4,4,K],21:[0,4,0],22:[4,0,4],23:[6,0,0],24:[0,0,6],25:[6,6,0],26:[4,F,0],27:[4,p,28],29:[6,0,6],2a:[6,0,6],2b:[4,0,0],2c:[p,p,p],2d:[4,4,4],2e:[4,4,0]}})(2f);',62,140,'||||255|parseInt|128|if|start|||||||return|Math|fA||F0|139|function|end|exec|211|192|getRGB|rgb||max|min|pos|var|parseFloat|55|169|backgroundColor|getColor|elem|240|245|165|42|107|140|230|224|144|each|borderBottomColor|borderLeftColor|borderRightColor|borderTopColor|color|outlineColor|fx|step|state|style|join|constructor|Array|length|trim|toLowerCase|do|curCSS|transparent||nodeName|body|break|while|parentNode|aqua|azure|beige|220|black|blue|brown|cyan|darkblue|darkcyan|darkgrey|darkgreen|100|darkkhaki|189|183|darkmagenta|darkolivegreen|85|47|darkorange|darkorchid|153|50|204|darkred|darksalmon|233|150|122|darkviolet|148|fuchsia|gold|215|green|indigo|75|130|khaki|lightblue|173|216|lightcyan|lightgreen|238|lightgrey|lightpink|182|193|lightyellow|lime|magenta|maroon|navy|olive|orange|pink|203|purple|violet|red|silver|white|yellow|jQuery'.split('|'),0,{}))

/*!
 * Raphael 1.5.2 - JavaScript Vector Library
 *
 * Copyright (c) 2010 Dmitry Baranovskiy (http://raphaeljs.com)
 * Licensed under the MIT (http://raphaeljs.com/license.html) license.
 * Note: 2.0 is still too new and buggy (ie9), and there are lack of anim performance
 */
eval(function(p,a,c,k,e,r){e=function(c){return(c<a?'':e(parseInt(c/a)))+((c=c%a)>35?String.fromCharCode(c+29):c.toString(36))};if(!''.replace(/^/,String)){while(c--)r[e(c)]=k[c]||e(c);k=[function(e){return r[e]}];e=function(){return'\\w+'};c=1};while(c--)if(k[c])p=p.replace(new RegExp('\\b'+e(c)+'\\b','g'),k[c]);return p}('(17(){17 R(){14(R.1q(2Q[0],2A)){13 a=2Q[0],6X=5V[24](R,a.3h(0,3+R.1q(a[0],2t))),1y=6X.3Y();1h(13 i=0,1k=a[19];i<1k;i++){13 j=a[i]||{};8m[1v](j.1n)&&1y[1F](6X[j.1n]().1X(j))}15 1y}15 5V[24](R,2Q)}R.5W="1.5.2";13 z=/[, ]+/,8m={3i:1,1Z:1,1o:1,3j:1,1R:1,2X:1},8n=/\\{(\\d+)\\}/g,1J="aa",1v="ab",1z=6Y,3N=ac,5X={6Z:70[1J][1v].1K(3N,"4p"),1q:3N.4p},5e=17(){11.3k={}},27,1r="1r",24="24",20="20",5Y="ad"1x 1z,E="",S=" ",1G=af,21="21",49="ag ah 71 72 8o 8p 73 8q 8r 8s ai aj ak al am"[21](S),5Z={71:"8q",72:"8r",73:"8s"},29="29",19="19",3Z=1G[1J].4O,1H=an,2H=1H.4q,3a=1H.3b,2o=1H.2o,1W=1H.1W,2u=1H.2u,2t="ao",2R="2R",2A="2A",2h="2h",3B="1l",8t=70[1J][2h],1p={},1F="1F",74=/^3l\\([\'"]?([^\\)]+?)[\'"]?\\)$/i,8u=/^\\s*((#[a-f\\d]{6})|(#[a-f\\d]{3})|76?\\(\\s*([\\d\\.]+%?\\s*,\\s*[\\d\\.]+%?\\s*,\\s*[\\d\\.]+(?:%?\\s*,\\s*[\\d\\.]+)?)%?\\s*\\)|8v?\\(\\s*([\\d\\.]+(?:2i|\\61|%)?\\s*,\\s*[\\d\\.]+%?\\s*,\\s*[\\d\\.]+(?:%?\\s*,\\s*[\\d\\.]+)?)%?\\s*\\)|8w?\\(\\s*([\\d\\.]+(?:2i|\\61|%)?\\s*,\\s*[\\d\\.]+%?\\s*,\\s*[\\d\\.]+(?:%?\\s*,\\s*[\\d\\.]+)?)%?\\s*\\))\\s*$/i,8x={"ap":1,"77":1,"-77":1},8y=/^(?:aq-)?ar\\(([^,]+),([^,]+),([^,]+),([^\\)]+)\\)/,2j=1H.2j,2K="2K",1E=as,3C=at,2k=" 8z:au.av",5f=1G[1J].aw,5g={2L:0,"1T-1Z":"0 0 8A 8A",4P:"3c",cx:0,cy:0,1l:"#8B","1l-1m":1,1A:\'az "8C"\',"1A-3O":\'"8C"\',"1A-40":"10","1A-1s":"62","1A-4a":78,2a:0,1c:0,4b:"5h://aA.79/",1m:1,1o:"8D,0",r:0,2Y:0,3m:0,3n:0,1U:"1 1",3D:"",1i:"#3E","1i-3P":"","1i-4c":"4Q","1i-5i":"4Q","1i-5j":0,"1i-1m":1,"1i-1a":1,4r:"aB","1R-63":"64",4R:"4p",3o:"0 0",1a:0,x:0,y:0},65={3Q:"3Q",2L:2t,"1T-1Z":"4S",cx:2t,cy:2t,1l:"66","1l-1m":2t,"1A-40":2t,1c:2t,1m:2t,1o:"1o",r:2t,2Y:"4S",3m:2t,3n:2t,1U:"4S",1i:"66","1i-1m":2t,"1i-1a":2t,3o:"4S",1a:2t,x:2t,y:2t},2v="8E",8F=/^(2b|1L|\\d+%?)$/,67=/\\s*,\\s*/,8G={aC:1,aD:1},8H=/,?([aE]),?/8I,8J=/([aF])[\\s,]*((-?\\d*\\.?\\d*(?:e[-+]?\\d+)?\\s*,?\\s*)+)/7a,8K=/(-?\\d*\\.?\\d*(?:e[-+]?\\d+)?)\\s*,?\\s*/7a,7b=/^r(?:\\(([^,]+?)\\s*,\\s*([^\\)]+?)\\))?/,8L=17(a,b){15 a.4s-b.4s};R.1n=(3N.aG||1z.aH.aI("5h://68.69.6a/aJ/aK/aL#aM","1.1")?"6b":"4t");14(R.1n=="4t"){13 d=1z.41("7c"),b;d.7d=\'<v:3p 8M="1"/>\';b=d.2S;b.1s.8N="3l(#3c#4t)";14(!(b&&7e b.8M=="3d")){15 R.1n=1g}d=1g}R.4u=!(R.5k=R.1n=="4t");5e[1J]=R[1J];27=5e[1J];R.8O=0;R.7f=0;R.7g={};R.1q=17(o,a){a=3Z.1K(a);14(a=="5l"){15!8x[1v](+o)}15(a=="1g"&&o===1g)||(a==7e o)||(a=="3d"&&o===70(o))||(a=="2A"&&6c.8P&&6c.8P(o))||8t.1K(o).1Y(8,-1).4O()==a};R.6d=17(a,b,c,d,e,f){14(e==1g){13 x=a-c,y=b-d;14(!x&&!y){15 0}15((x<0)*3q+1H.8Q(-y/-x)*3q/2u+4d)%4d}1j{15 R.6d(a,b,e,f)-R.6d(c,d,e,f)}};R.4T=17(a){15 a%4d*2u/3q};R.2i=17(a){15 a*3q/2u%4d};R.aN=17(a,b,c){c=R.1q(c,"5l")?c:10;14(R.1q(a,2A)){13 i=a.19;2B(i--)14(2o(a[i]-b)<=c){15 a[i]}}1j{a=+a;13 d=b%a;14(d<c){15 b-d}14(d>a-c){15 b-d+a}}15 b};17 5m(){13 s=[],i=0;1h(;i<32;i++){s[i]=(~~(1H.aO()*16))[2h](16)}s[12]=4;s[16]=((s[16]&3)|8)[2h](16);15"r-"+s[29]("")}R.aP=17(a){3N=a;1z=3N.6Y};13 A=17(c){14(R.5k){13 d=/^\\s+|\\s+$/g;13 f;6e{13 g=2c aQ("aR");g.aS("<3r>");g.aT();f=g.3r}6f(e){f=aU().6Y.3r}13 h=f.aV();A=3s(17(a){6e{f.1s.2M=1G(a)[2v](d,E);13 b=h.aW("aX");b=((b&3F)<<16)|(b&aY)|((b&aZ)>>>16);15"#"+("b0"+b[2h](16)).1Y(-6)}6f(e){15"1P"}})}1j{13 i=1z.41("i");i.4R="4U\\4V b1 b2";i.1s.42="1P";1z.3r[1r](i);A=3s(17(a){i.1s.2M=a;15 1z.8R.8S(i,E).8T("2M")})}15 A(c)},7h=17(){15"b3("+[11.h,11.s,11.b]+")"},8U=17(){15"4W("+[11.h,11.s,11.l]+")"},8V=17(){15 11.3G};R.7i=17(h,s,b,o){14(R.1q(h,"3d")&&"h"1x h&&"s"1x h&&"b"1x h){b=h.b;s=h.s;h=h.h;o=h.o}15 R.7j(h,s,b/2,o)};R.7j=17(h,s,l,o){14(R.1q(h,"3d")&&"h"1x h&&"s"1x h&&"l"1x h){l=h.l;s=h.s;h=h.h}14(h>1||s>1||l>1){h/=4d;s/=2N;l/=2N}13 a={},5n=["r","g","b"],1I,22,3H,r,g,b;14(!s){a={r:l,g:l,b:l}}1j{14(l<.5){1I=l*(1+s)}1j{1I=l+s-l*s}22=2*l-1I;1h(13 i=0;i<3;i++){3H=h+1/3*-(i-1);3H<0&&3H++;3H>1&&3H--;14(3H*6<1){a[5n[i]]=22+(1I-22)*6*3H}1j 14(3H*2<1){a[5n[i]]=1I}1j 14(3H*3<2){a[5n[i]]=22+(1I-22)*(2/3-3H)*6}1j{a[5n[i]]=22}}}a.r*=3F;a.g*=3F;a.b*=3F;a.3G="#"+(8W|a.b|(a.g<<8)|(a.r<<16)).2h(16).1Y(1);R.1q(o,"5l")&&(a.1m=o);a.2h=8V;15 a};R.b4=17(a,b,c){14(b==1g&&R.1q(a,"3d")&&"r"1x a&&"g"1x a&&"b"1x a){c=a.b;b=a.g;a=a.r}14(b==1g&&R.1q(a,2R)){13 d=R.3t(a);a=d.r;b=d.g;c=d.b}14(a>1||b>1||c>1){a/=3F;b/=3F;c/=3F}13 e=2H(a,b,c),3b=3a(a,b,c),3R,7k,8X=e;14(3b==e){15{h:0,s:0,b:e,2h:7h}}1j{13 f=(e-3b);7k=f/e;14(a==e){3R=(b-c)/f}1j 14(b==e){3R=2+((c-a)/f)}1j{3R=4+((a-b)/f)}3R/=6;3R<0&&3R++;3R>1&&3R--}15{h:3R,s:7k,b:8X,2h:7h}};R.b5=17(a,b,c){14(b==1g&&R.1q(a,"3d")&&"r"1x a&&"g"1x a&&"b"1x a){c=a.b;b=a.g;a=a.r}14(b==1g&&R.1q(a,2R)){13 d=R.3t(a);a=d.r;b=d.g;c=d.b}14(a>1||b>1||c>1){a/=3F;b/=3F;c/=3F}13 e=2H(a,b,c),3b=3a(a,b,c),h,s,l=(e+3b)/2,4W;14(3b==e){4W={h:0,s:0,l:l}}1j{13 f=e-3b;s=l<.5?f/(e+3b):f/(2-e-3b);14(a==e){h=(b-c)/f}1j 14(b==e){h=2+(c-a)/f}1j{h=4+(a-b)/f}h/=6;h<0&&h++;h>1&&h--;4W={h:h,s:s,l:l}}4W.2h=8U;15 4W};R.5o=17(){15 11.29(",")[2v](8H,"$1")};17 3s(f,b,c){17 4X(){13 a=6c[1J].1Y.1K(2Q,0),4e=a[29]("\\b6"),2w=4X.2w=4X.2w||{},4Y=4X.4Y=4X.4Y||[];14(2w[1v](4e)){15 c?c(2w[4e]):2w[4e]}4Y[19]>=4v&&2x 2w[4Y.4w()];4Y[1F](4e);2w[4e]=f[24](b,a);15 c?c(2w[4e]):2w[4e]}15 4X}R.3t=3s(17(a){14(!a||!!((a=1G(a)).b7("-")+1)){15{r:-1,g:-1,b:-1,3G:"1P",5p:1}}14(a=="1P"){15{r:-1,g:-1,b:-1,3G:"1P"}}!(8G[1v](a.4O().5q(0,2))||a.4f()=="#")&&(a=A(a));13 b,2T,3e,3f,1m,t,1D,23=a.43(8u);14(23){14(23[2]){3f=3C(23[2].5q(5),16);3e=3C(23[2].5q(3,5),16);2T=3C(23[2].5q(1,3),16)}14(23[3]){3f=3C((t=23[3].4f(3))+t,16);3e=3C((t=23[3].4f(2))+t,16);2T=3C((t=23[3].4f(1))+t,16)}14(23[4]){1D=23[4][21](67);2T=1E(1D[0]);1D[0].1Y(-1)=="%"&&(2T*=2.55);3e=1E(1D[1]);1D[1].1Y(-1)=="%"&&(3e*=2.55);3f=1E(1D[2]);1D[2].1Y(-1)=="%"&&(3f*=2.55);23[1].4O().1Y(0,4)=="76"&&(1m=1E(1D[3]));1D[3]&&1D[3].1Y(-1)=="%"&&(1m/=2N)}14(23[5]){1D=23[5][21](67);2T=1E(1D[0]);1D[0].1Y(-1)=="%"&&(2T*=2.55);3e=1E(1D[1]);1D[1].1Y(-1)=="%"&&(3e*=2.55);3f=1E(1D[2]);1D[2].1Y(-1)=="%"&&(3f*=2.55);(1D[0].1Y(-3)=="2i"||1D[0].1Y(-1)=="\\61")&&(2T/=4d);23[1].4O().1Y(0,4)=="8v"&&(1m=1E(1D[3]));1D[3]&&1D[3].1Y(-1)=="%"&&(1m/=2N);15 R.7i(2T,3e,3f,1m)}14(23[6]){1D=23[6][21](67);2T=1E(1D[0]);1D[0].1Y(-1)=="%"&&(2T*=2.55);3e=1E(1D[1]);1D[1].1Y(-1)=="%"&&(3e*=2.55);3f=1E(1D[2]);1D[2].1Y(-1)=="%"&&(3f*=2.55);(1D[0].1Y(-3)=="2i"||1D[0].1Y(-1)=="\\61")&&(2T/=4d);23[1].4O().1Y(0,4)=="8w"&&(1m=1E(1D[3]));1D[3]&&1D[3].1Y(-1)=="%"&&(1m/=2N);15 R.7j(2T,3e,3f,1m)}23={r:2T,g:3e,b:3f};23.3G="#"+(8W|3f|(3e<<8)|(2T<<16)).2h(16).1Y(1);R.1q(1m,"5l")&&(23.1m=1m);15 23}15{r:-1,g:-1,b:-1,3G:"1P",5p:1}},R);R.5r=17(a){13 b=11.5r.2p=11.5r.2p||{h:0,s:1,b:a||.75},23=11.7i(b.h,b.s,b.b);b.h+=.b8;14(b.h>1){b.h=0;b.s-=.2;b.s<=0&&(11.5r.2p={h:0,s:1,b:b.b})}15 23.3G};R.5r.b9=17(){2x 11.2p};R.6g=3s(17(e){14(!e){15 1g}13 f={a:7,c:6,h:1,l:2,m:2,q:4,s:4,t:2,v:1,z:0},3I=[];14(R.1q(e,2A)&&R.1q(e[0],2A)){3I=5s(e)}14(!3I[19]){1G(e)[2v](8J,17(a,b,c){13 d=[],3u=3Z.1K(b);c[2v](8K,17(a,b){b&&d[1F](+b)});14(3u=="m"&&d[19]>2){3I[1F]([b][20](d.3h(0,2)));3u="l";b=b=="m"?"l":"L"}2B(d[19]>=f[3u]){3I[1F]([b][20](d.3h(0,f[3u])));14(!f[3u]){1d}}})}3I[2h]=R.5o;15 3I});R.7l=17(a,b,c,d,e,f,g,h,t){13 i=1-t,x=1W(i,3)*a+1W(i,2)*3*t*c+i*3*t*t*e+1W(t,3)*g,y=1W(i,3)*b+1W(i,2)*3*t*d+i*3*t*t*f+1W(t,3)*h,2Z=a+2*t*(c-a)+t*t*(e-2*c+a),30=b+2*t*(d-b)+t*t*(f-2*d+b),6h=c+2*t*(e-c)+t*t*(g-2*e+c),4x=d+2*t*(f-d)+t*t*(h-2*f+d),ax=(1-t)*a+t*c,ay=(1-t)*b+t*d,cx=(1-t)*e+t*g,cy=(1-t)*f+t*h,3J=(90-1H.8Q((2Z-6h)/(30-4x))*3q/2u);(2Z>6h||30<4x)&&(3J+=3q);15{x:x,y:y,m:{x:2Z,y:30},n:{x:6h,y:4x},2p:{x:ax,y:ay},2C:{x:cx,y:cy},3J:3J}};13 B=3s(17(a){14(!a){15{x:0,y:0,1a:0,1c:0}}a=5t(a);13 x=0,y=0,X=[],Y=[],p;1h(13 i=0,1k=a[19];i<1k;i++){p=a[i];14(p[0]=="M"){x=p[1];y=p[2];X[1F](x);Y[1F](y)}1j{13 b=8Y(x,y,p[1],p[2],p[3],p[4],p[5],p[6]);X=X[20](b.3b.x,b.4q.x);Y=Y[20](b.3b.y,b.4q.y);x=p[5];y=p[6]}}13 c=3a[24](0,X),7m=3a[24](0,Y);15{x:c,y:7m,1a:2H[24](0,X)-c,1c:2H[24](0,Y)-7m}}),5s=17(a){13 b=[];14(!R.1q(a,2A)||!R.1q(a&&a[0],2A)){a=R.6g(a)}1h(13 i=0,1k=a[19];i<1k;i++){b[i]=[];1h(13 j=0,2d=a[i][19];j<2d;j++){b[i][j]=a[i][j]}}b[2h]=R.5o;15 b},5u=3s(17(a){14(!R.1q(a,2A)||!R.1q(a&&a[0],2A)){a=R.6g(a)}13 b=[],x=0,y=0,2Z=0,30=0,2p=0;14(a[0][0]=="M"){x=a[0][1];y=a[0][2];2Z=x;30=y;2p++;b[1F](["M",x,y])}1h(13 i=2p,1k=a[19];i<1k;i++){13 r=b[i]=[],1C=a[i];14(1C[0]!=3Z.1K(1C[0])){r[0]=3Z.1K(1C[0]);2U(r[0]){1f"a":r[1]=1C[1];r[2]=1C[2];r[3]=1C[3];r[4]=1C[4];r[5]=1C[5];r[6]=+(1C[6]-x).4y(3);r[7]=+(1C[7]-y).4y(3);1d;1f"v":r[1]=+(1C[1]-y).4y(3);1d;1f"m":2Z=1C[1];30=1C[2];3c:1h(13 j=1,2d=1C[19];j<2d;j++){r[j]=+(1C[j]-((j%2)?x:y)).4y(3)}}}1j{r=b[i]=[];14(1C[0]=="m"){2Z=1C[1]+x;30=1C[2]+y}1h(13 k=0,6i=1C[19];k<6i;k++){b[i][k]=1C[k]}}13 c=b[i][19];2U(b[i][0]){1f"z":x=2Z;y=30;1d;1f"h":x+=+b[i][c-1];1d;1f"v":y+=+b[i][c-1];1d;3c:x+=+b[i][c-2];y+=+b[i][c-1]}}b[2h]=R.5o;15 b},0,5s),4Z=3s(17(a){14(!R.1q(a,2A)||!R.1q(a&&a[0],2A)){a=R.6g(a)}13 b=[],x=0,y=0,2Z=0,30=0,2p=0;14(a[0][0]=="M"){x=+a[0][1];y=+a[0][2];2Z=x;30=y;2p++;b[0]=["M",x,y]}1h(13 i=2p,1k=a[19];i<1k;i++){13 r=b[i]=[],1C=a[i];14(1C[0]!=5f.1K(1C[0])){r[0]=5f.1K(1C[0]);2U(r[0]){1f"A":r[1]=1C[1];r[2]=1C[2];r[3]=1C[3];r[4]=1C[4];r[5]=1C[5];r[6]=+(1C[6]+x);r[7]=+(1C[7]+y);1d;1f"V":r[1]=+1C[1]+y;1d;1f"H":r[1]=+1C[1]+x;1d;1f"M":2Z=+1C[1]+x;30=+1C[2]+y;3c:1h(13 j=1,2d=1C[19];j<2d;j++){r[j]=+1C[j]+((j%2)?x:y)}}}1j{1h(13 k=0,6i=1C[19];k<6i;k++){b[i][k]=1C[k]}}2U(r[0]){1f"Z":x=2Z;y=30;1d;1f"H":x=r[1];1d;1f"V":y=r[1];1d;1f"M":2Z=b[i][b[i][19]-2];30=b[i][b[i][19]-1];3c:x=b[i][b[i][19]-2];y=b[i][b[i][19]-1]}}b[2h]=R.5o;15 b},1g,5s),5v=17(a,b,c,d){15[a,b,c,d,c,d]},7n=17(a,b,c,d,e,f){13 g=1/3,5w=2/3;15[g*a+5w*c,g*b+5w*d,g*e+5w*c,g*f+5w*d,e,f]},7o=17(b,c,d,e,f,g,j,l,m,n){13 o=2u*ba/3q,4T=2u/3q*(+f||0),1y=[],2q,2l=3s(17(x,y,a){13 X=x*1H.4z(a)-y*1H.44(a),Y=x*1H.44(a)+y*1H.4z(a);15{x:X,y:Y}});14(!n){2q=2l(b,c,-4T);b=2q.x;c=2q.y;2q=2l(l,m,-4T);l=2q.x;m=2q.y;13 p=1H.4z(2u/3q*f),44=1H.44(2u/3q*f),x=(b-l)/2,y=(c-m)/2;13 h=(x*x)/(d*d)+(y*y)/(e*e);14(h>1){h=1H.4g(h);d=h*d;e=h*e}13 q=d*d,6j=e*e,k=(g==j?-1:1)*1H.4g(2o((q*6j-q*y*y-6j*x*x)/(q*y*y+6j*x*x))),cx=k*d*y/e+(b+l)/2,cy=k*-e*x/d+(c+m)/2,2D=1H.8Z(((c-cy)/e).4y(9)),2m=1H.8Z(((m-cy)/e).4y(9));2D=b<cx?2u-2D:2D;2m=l<cx?2u-2m:2m;2D<0&&(2D=2u*2+2D);2m<0&&(2m=2u*2+2m);14(j&&2D>2m){2D=2D-2u*2}14(!j&&2m>2D){2m=2m-2u*2}}1j{2D=n[0];2m=n[1];cx=n[2];cy=n[3]}13 r=2m-2D;14(2o(r)>o){13 s=2m,91=l,92=m;2m=2D+o*(j&&2m>2D?1:-1);l=cx+d*1H.4z(2m);m=cy+e*1H.44(2m);1y=7o(l,m,d,e,f,0,j,91,92,[2m,s,cx,cy])}r=2m-2D;13 u=1H.4z(2D),93=1H.44(2D),c2=1H.4z(2m),94=1H.44(2m),t=1H.bb(r/4),7p=4/3*d*t,7q=4/3*e*t,7r=[b,c],4A=[b+7p*93,c-7q*u],7s=[l+7p*94,m-7q*c2],7t=[l,m];4A[0]=2*7r[0]-4A[0];4A[1]=2*7r[1]-4A[1];14(n){15[4A,7s,7t][20](1y)}1j{1y=[4A,7s,7t][20](1y)[29]()[21](",");13 v=[];1h(13 i=0,1k=1y[19];i<1k;i++){v[i]=i%2?2l(1y[i-1],1y[i],4T).y:2l(1y[i],1y[i+1],4T).x}15 v}},5x=17(a,b,c,d,e,f,g,h,t){13 i=1-t;15{x:1W(i,3)*a+1W(i,2)*3*t*c+i*3*t*t*e+1W(t,3)*g,y:1W(i,3)*b+1W(i,2)*3*t*d+i*3*t*t*f+1W(t,3)*h}},8Y=3s(17(d,e,f,g,h,i,j,k){13 a=(h-2*f+d)-(j-2*h+f),b=2*(f-d)-2*(h-f),c=d-f,22=(-b+1H.4g(b*b-4*a*c))/2/a,1I=(-b-1H.4g(b*b-4*a*c))/2/a,y=[e,k],x=[d,j],2n;2o(22)>"6k"&&(22=.5);2o(1I)>"6k"&&(1I=.5);14(22>0&&22<1){2n=5x(d,e,f,g,h,i,j,k,22);x[1F](2n.x);y[1F](2n.y)}14(1I>0&&1I<1){2n=5x(d,e,f,g,h,i,j,k,1I);x[1F](2n.x);y[1F](2n.y)}a=(i-2*g+e)-(k-2*i+g);b=2*(g-e)-2*(i-g);c=e-g;22=(-b+1H.4g(b*b-4*a*c))/2/a;1I=(-b-1H.4g(b*b-4*a*c))/2/a;2o(22)>"6k"&&(22=.5);2o(1I)>"6k"&&(1I=.5);14(22>0&&22<1){2n=5x(d,e,f,g,h,i,j,k,22);x[1F](2n.x);y[1F](2n.y)}14(1I>0&&1I<1){2n=5x(d,e,f,g,h,i,j,k,1I);x[1F](2n.x);y[1F](2n.y)}15{3b:{x:3a[24](0,x),y:3a[24](0,y)},4q:{x:2H[24](0,x),y:2H[24](0,y)}}}),5t=3s(17(e,f){13 p=4Z(e),2e=f&&4Z(f),18={x:0,y:0,bx:0,by:0,X:0,Y:0,4B:1g,4C:1g},3S={x:0,y:0,bx:0,by:0,X:0,Y:0,4B:1g,4C:1g},7u=17(a,d){13 b,4x;14(!a){15["C",d.x,d.y,d.x,d.y,d.x,d.y]}!(a[0]1x{T:1,Q:1})&&(d.4B=d.4C=1g);2U(a[0]){1f"M":d.X=a[1];d.Y=a[2];1d;1f"A":a=["C"][20](7o[24](0,[d.x,d.y][20](a.1Y(1))));1d;1f"S":b=d.x+(d.x-(d.bx||d.x));4x=d.y+(d.y-(d.by||d.y));a=["C",b,4x][20](a.1Y(1));1d;1f"T":d.4B=d.x+(d.x-(d.4B||d.x));d.4C=d.y+(d.y-(d.4C||d.y));a=["C"][20](7n(d.x,d.y,d.4B,d.4C,a[1],a[2]));1d;1f"Q":d.4B=a[1];d.4C=a[2];a=["C"][20](7n(d.x,d.y,a[1],a[2],a[3],a[4]));1d;1f"L":a=["C"][20](5v(d.x,d.y,a[1],a[2]));1d;1f"H":a=["C"][20](5v(d.x,d.y,a[1],d.y));1d;1f"V":a=["C"][20](5v(d.x,d.y,d.x,a[1]));1d;1f"Z":a=["C"][20](5v(d.x,d.y,d.X,d.Y));1d}15 a},7v=17(a,i){14(a[i][19]>7){a[i].4w();13 b=a[i];2B(b[19]){a.3h(i++,0,["C"][20](b.3h(0,6)))}a.3h(i,1);1k=2H(p[19],2e&&2e[19]||0)}},7w=17(a,b,c,d,i){14(a&&b&&a[i][0]=="M"&&b[i][0]!="M"){b.3h(i,0,["M",d.x,d.y]);c.bx=0;c.by=0;c.x=a[i][1];c.y=a[i][2];1k=2H(p[19],2e&&2e[19]||0)}};1h(13 i=0,1k=2H(p[19],2e&&2e[19]||0);i<1k;i++){p[i]=7u(p[i],18);7v(p,i);2e&&(2e[i]=7u(2e[i],3S));2e&&7v(2e,i);7w(p,2e,18,3S,i);7w(2e,p,3S,18,i);13 g=p[i],50=2e&&2e[i],5y=g[19],5z=2e&&50[19];18.x=g[5y-2];18.y=g[5y-1];18.bx=1E(g[5y-4])||18.x;18.by=1E(g[5y-3])||18.y;3S.bx=2e&&(1E(50[5z-4])||3S.x);3S.by=2e&&(1E(50[5z-3])||3S.y);3S.x=2e&&50[5z-2];3S.y=2e&&50[5z-1]}15 2e?[p,2e]:p},1g,5s),7x=3s(17(a){13 b=[];1h(13 i=0,1k=a[19];i<1k;i++){13 c={},6l=a[i].43(/^([^:]*):?([\\d\\.]*)/);c.2M=R.3t(6l[1]);14(c.2M.5p){15 1g}c.2M=c.2M.3G;6l[2]&&(c.3K=6l[2]+"%");b[1F](c)}1h(i=1,1k=b[19]-1;i<1k;i++){14(!b[i].3K){13 e=1E(b[i-1].3K||0),2C=0;1h(13 j=i+1;j<1k;j++){14(b[j].3K){2C=b[j].3K;1d}}14(!2C){2C=2N;j=1k}2C=1E(2C);13 d=(2C-e)/(j-i+1);1h(;i<j;i++){e+=d;b[i].3K=e+"%"}}}15 b}),7y=17(x,y,w,h){13 a;14(R.1q(x,2R)||R.1q(x,"3d")){a=R.1q(x,2R)?1z.6m(x):x;14(a.95){14(y==1g){15{1V:a,1a:a.1s.bc||a.6n,1c:a.1s.bd||a.6o}}1j{15{1V:a,1a:y,1c:w}}}}1j{15{1V:1,x:x,y:y,1a:w,1c:h}}},6p=17(a,b){13 c=11;1h(13 d 1x b){14(b[1v](d)&&!(d 1x a)){2U(7e b[d]){1f"17":(17(f){a[d]=a===c?f:17(){15 f[24](c,2Q)}})(b[d]);1d;1f"3d":a[d]=a[d]||{};6p.1K(11,a[d],b[d]);1d;3c:a[d]=b[d];1d}}}},4D=17(a,b){a==b.1w&&(b.1w=a.2I);a==b.2V&&(b.2V=a.2y);a.2y&&(a.2y.2I=a.2I);a.2I&&(a.2I.2y=a.2y)},7z=17(a,b){14(b.1w===a){15}4D(a,b);a.2y=1g;a.2I=b.1w;b.1w.2y=a;b.1w=a},7A=17(a,b){14(b.2V===a){15}4D(a,b);a.2y=b.2V;a.2I=1g;b.2V.2I=a;b.2V=a},7B=17(a,b,c){4D(a,c);b==c.1w&&(c.1w=a);b.2y&&(b.2y.2I=a);a.2y=b.2y;a.2I=b;b.2y=a},7C=17(a,b,c){4D(a,c);b==c.2V&&(c.2V=a);b.2I&&(b.2I.2y=a);a.2I=b.2I;b.2I=a;a.2y=b},1M=17(a){15 17(){7D 2c 7E("4U\\4V: be 7F bf 1L 96 \\bg"+a+"\\bh bi 1M 3d");}};R.5u=5u;14(R.4u){27.97="5h://68.69.6a/7G/4u";27.4E="5h://68.69.6a/bj/4E";2j=17(a){15+a+(~~a===a)*.5};13 $=17(a,b){14(b){1h(13 c 1x b){14(b[1v](c)){a[2K](c,1G(b[c]))}}}1j{a=1z.bk(27.97,a);a.1s.bl="76(0,0,0,0)";15 a}};R[2h]=17(){15"98 9a bm 6b.\\9b 7F 9c 4U\\4V "+11.5W};13 C=17(a,b){13 c=$("1o");b.1S&&b.1S[1r](c);13 p=2c 1Q(c,b);p.1n="1o";G(p,{1l:"1P",1i:"#3E",1o:a});15 p};13 D=17(o,e,f){13 g="5A",31=.5,2E=.5,s=o.1s;e=1G(e)[2v](7b,17(a,b,c){g="6q";14(b&&c){31=1E(b);2E=1E(c);13 d=((2E>.5)*2-1);1W(31-.5,2)+1W(2E-.5,2)>.25&&(2E=1H.4g(.25-1W(31-.5,2))*d+.5)&&2E!=.5&&(2E=2E.4y(5)-1e-5*d)}15 E});e=e[21](/\\s*\\-\\s*/);14(g=="5A"){13 h=e.4w();h=-1E(h);14(6r(h)){15 1g}13 j=[0,0,1H.4z(h*2u/3q),1H.44(h*2u/3q)],4q=1/(2H(2o(j[2]),2o(j[3]))||1);j[2]*=4q;j[3]*=4q;14(j[2]<0){j[0]=-j[2];j[2]=0}14(j[3]<0){j[1]=-j[3];j[3]=0}}13 k=7x(e);14(!k){15 1g}13 l=o.7H(3B);l=l.43(/^3l\\(#(.*)\\)$/);l&&f.4h.33(1z.6m(l[1]));13 m=$(g+"bn");m.2z=5m();$(m,g=="6q"?{31:31,2E:2E}:{bo:j[0],bp:j[1],4i:j[2],bq:j[3]});f.4h[1r](m);1h(13 i=0,1k=k[19];i<1k;i++){13 n=$("51");$(n,{3K:k[i].3K?k[i].3K:!i?"0%":"2N%","51-2M":k[i].2M||"#8B"});m[1r](n)}$(o,{1l:"3l(#"+m.2z+")",1m:1,"1l-1m":1});s.1l=E;s.1m=1;s.bs=1;15 1};13 F=17(o){13 a=o.3T();$(o.52,{bt:R.3L("3v({0},{1})",a.x,a.y)})};13 G=17(o,c){13 d={"":[0],"1P":[0],"-":[3,1],".":[1,1],"-.":[3,1,1,1],"-..":[3,1,1,1,1,1],". ":[1,3],"- ":[4,3],"--":[8,3],"- .":[4,3,1,3],"--.":[8,3,1,3],"--..":[8,3,1,3,1,3]},1b=o.1b,18=o.18,3w=o.2l(),7I=17(o,a){a=d[3Z.1K(a)];14(a){13 b=o.18["1i-1a"]||"1",4Q={2j:b,7J:b,4Q:0}[o.18["1i-4c"]||c["1i-4c"]]||0,7K=[];13 i=a[19];2B(i--){7K[i]=a[i]*b+((i%2)?1:-1)*4Q}$(1b,{"1i-3P":7K[29](",")})}};c[1v]("2Y")&&(3w=c.2Y);13 e=1G(3w)[21](z);14(!(e.19-1)){e=1g}1j{e[1]=+e[1];e[2]=+e[2]}1E(3w)&&o.2l(0,2F);1h(13 f 1x c){14(c[1v](f)){14(!5g[1v](f)){6s}13 g=c[f];18[f]=g;2U(f){1f"2L":o.2L(g);1d;1f"2Y":o.2l(g,2F);1d;1f"4b":1f"4R":1f"4r":13 h=1b.1N;14(3Z.1K(h.95)!="a"){13 j=$("a");h.3x(j,1b);j[1r](1b);h=j}14(f=="4r"&&g=="9d"){h.5B(o.1p.4E,"6t","2c")}1j{h.5B(o.1p.4E,f,g)}1d;1f"4P":1b.1s.4P=g;1d;1f"1T-1Z":13 k=1G(g)[21](z);14(k[19]==4){o.1T&&o.1T.1N.1N.33(o.1T.1N);13 l=$("9e"),4j=$("1Z");l.2z=5m();$(4j,{x:k[0],y:k[1],1a:k[2],1c:k[3]});l[1r](4j);o.1p.4h[1r](l);$(1b,{"1T-1o":"3l(#"+l.2z+")"});o.1T=4j}1j{o.1T&&o.1T.1N.1N.33(o.1T.1N);13 l=$("9e"),4j=$("1o");l.2z="r"+(R.8O++)[2h](36);$(4j,{d:k});l[1r](4j);o.1p.4h[1r](l);$(1b,{"1T-1o":"3l(#"+l.2z+")"});o.1T=4j}14(!g){13 m=1z.6m(1b.7H("1T-1o")[2v](/(^3l\\(#|\\)$)/g,E));m&&m.1N.33(m);$(1b,{"1T-1o":E});2x o.1T}1d;1f"1o":14(o.1n=="1o"){$(1b,{d:g?18.1o=4Z(g):"8D,0"})}1d;1f"1a":1b[2K](f,g);14(18.31){f="x";g=18.x}1j{1d}1f"x":14(18.31){g=-18.x-(18.1a||0)}1f"3m":14(f=="3m"&&o.1n=="1Z"){1d}1f"cx":e&&(f=="x"||f=="cx")&&(e[1]+=g-18[f]);1b[2K](f,g);o.52&&F(o);1d;1f"1c":1b[2K](f,g);14(18.2E){f="y";g=18.y}1j{1d}1f"y":14(18.2E){g=-18.y-(18.1c||0)}1f"3n":14(f=="3n"&&o.1n=="1Z"){1d}1f"cy":e&&(f=="y"||f=="cy")&&(e[2]+=g-18[f]);1b[2K](f,g);o.52&&F(o);1d;1f"r":14(o.1n=="1Z"){$(1b,{3m:g,3n:g})}1j{1b[2K](f,g)}1d;1f"3D":14(o.1n=="2X"){1b.5B(o.1p.4E,"4b",g)}1d;1f"1i-1a":1b.1s.bu=g;1b[2K](f,g);14(18["1i-3P"]){7I(o,18["1i-3P"])}1d;1f"1i-3P":7I(o,g);1d;1f"3o":13 n=1G(g)[21](z);n[0]=+n[0]||0;n[1]=+n[1]||0;14(e){e[1]+=n[0];e[2]+=n[1]}3v.1K(o,n[0],n[1]);1d;1f"1U":n=1G(g)[21](z);o.1U(+n[0]||1,+n[1]||+n[0]||1,6r(1E(n[2]))?1g:+n[2],6r(1E(n[3]))?1g:+n[3]);1d;1f 3B:13 p=1G(g).43(74);14(p){l=$("52");13 q=$("2X");l.2z=5m();$(l,{x:0,y:0,bv:"bw",1c:1,1a:1});$(q,{x:0,y:0});q.5B(o.1p.4E,"4b",p[1]);l[1r](q);13 r=1z.41("bz");r.1s.3U="3y:3M;2r:-53;1w-53";r.bA=17(){$(l,{1a:11.6n,1c:11.6o});$(q,{1a:11.6n,1c:11.6o});1z.3r.33(11);o.1p.6u()};1z.3r[1r](r);r.3D=p[1];o.1p.4h[1r](l);1b.1s.1l="3l(#"+l.2z+")";$(1b,{1l:"3l(#"+l.2z+")"});o.52=l;o.52&&F(o);1d}13 s=R.3t(g);14(!s.5p){2x c.2a;2x 18.2a;!R.1q(18.1m,"6v")&&R.1q(c.1m,"6v")&&$(1b,{1m:18.1m});!R.1q(18["1l-1m"],"6v")&&R.1q(c["1l-1m"],"6v")&&$(1b,{"1l-1m":18["1l-1m"]})}1j 14((({3i:1,3j:1})[1v](o.1n)||1G(g).4f()!="r")&&D(1b,g,o.1p)){18.2a=g;18.1l="1P";1d}s[1v]("1m")&&$(1b,{"1l-1m":s.1m>1?s.1m/2N:s.1m});1f"1i":s=R.3t(g);1b[2K](f,s.3G);f=="1i"&&s[1v]("1m")&&$(1b,{"1i-1m":s.1m>1?s.1m/2N:s.1m});1d;1f"2a":(({3i:1,3j:1})[1v](o.1n)||1G(g).4f()!="r")&&D(1b,g,o.1p);1d;1f"1m":14(18.2a&&!18[1v]("1i-1m")){$(1b,{"1i-1m":g>1?g/2N:g})}1f"1l-1m":14(18.2a){13 t=1z.6m(1b.7H(3B)[2v](/^3l\\(#|\\)$/g,E));14(t){13 u=t.4F("51");u[u[19]-1][2K]("51-1m",g)}1d}3c:f=="1A-40"&&(g=3C(g,10)+"1B");13 v=f[2v](/(\\-.)/g,17(w){15 5f.1K(w.5q(1))});1b.1s[v]=g;1b[2K](f,g);1d}}}9f(o,c);14(e){o.2l(e.29(S))}1j{1E(3w)&&o.2l(3w,2F)}};13 H=1.2,9f=17(b,c){14(b.1n!="1R"||!(c[1v]("1R")||c[1v]("1A")||c[1v]("1A-40")||c[1v]("x")||c[1v]("y"))){15}13 a=b.18,1b=b.1b,5C=1b.2S?3C(1z.8R.8S(1b.2S,E).8T("1A-40"),10):10;14(c[1v]("1R")){a.1R=c.1R;2B(1b.2S){1b.33(1b.2S)}13 d=1G(c.1R)[21]("\\n");1h(13 i=0,1k=d[19];i<1k;i++)14(d[i]){13 e=$("9g");i&&$(e,{dy:5C*H,x:a.x});e[1r](1z.9h(d[i]));1b[1r](e)}}1j{d=1b.4F("9g");1h(i=0,1k=d[19];i<1k;i++){i&&$(d[i],{dy:5C*H,x:a.x})}}$(1b,{y:a.y});13 f=b.3T(),6w=a.y-(f.y+f.1c/2);6w&&R.1q(6w,"5l")&&$(1b,{y:a.y+6w})},1Q=17(a,b){13 X=0,Y=0;11[0]=a;11.2z=R.7f++;11.1b=a;a.7L=11;11.1p=b;11.18=11.18||{};11.3V=[];11.1u={5D:0,5E:0,1O:{2i:0,cx:0,cy:0},34:1,35:1};!b.2V&&(b.2V=11);11.2I=b.1w;b.1w&&(b.1w.2y=11);b.1w=11;11.2y=1g};13 I=1Q[1J];1Q[1J].2l=17(a,b,c){14(11.1M){15 11}14(a==1g){14(11.1u.1O.cx){15[11.1u.1O.2i,11.1u.1O.cx,11.1u.1O.cy][29](S)}15 11.1u.1O.2i}13 d=11.3T();a=1G(a)[21](z);14(a[19]-1){b=1E(a[1]);c=1E(a[2])}a=1E(a[0]);14(b!=1g&&b!==3W){11.1u.1O.2i=a}1j{11.1u.1O.2i+=a}(c==1g)&&(b=1g);11.1u.1O.cx=b;11.1u.1O.cy=c;b=b==1g?d.x+d.1a/2:b;c=c==1g?d.y+d.1c/2:c;14(11.1u.1O.2i){11.3V[0]=R.3L("2l({0} {1} {2})",11.1u.1O.2i,b,c);11.1T&&$(11.1T,{5F:R.3L("2l({0} {1} {2})",-11.1u.1O.2i,b,c)})}1j{11.3V[0]=E;11.1T&&$(11.1T,{5F:E})}$(11.1b,{5F:11.3V[29](S)});15 11};1Q[1J].7M=17(){!11.1M&&(11.1b.1s.42="1P");15 11};1Q[1J].6t=17(){!11.1M&&(11.1b.1s.42="");15 11};1Q[1J].5G=17(){14(11.1M){15}4D(11,11.1p);11.1b.1N.33(11.1b);1h(13 i 1x 11){2x 11[i]}11.1M=2F};1Q[1J].3T=17(){14(11.1M){15 11}14(11.1n=="1o"){15 B(11.18.1o)}14(11.1b.1s.42=="1P"){11.6t();13 a=2F}13 b={};6e{b=11.1b.3T()}6f(e){}bB{b=b||{}}14(11.1n=="1R"){b={x:b.x,y:77,1a:0,1c:0};1h(13 i=0,1k=11.1b.bC();i<1k;i++){13 c=11.1b.bD(i);(c.y<b.y)&&(b.y=c.y);(c.y+c.1c-b.y>b.1c)&&(b.1c=c.y+c.1c-b.y);(c.x+c.1a-b.x>b.1a)&&(b.1a=c.x+c.1a-b.x)}}a&&11.7M();15 b};1Q[1J].1X=17(a,b){14(11.1M){15 11}14(a==1g){13 c={};1h(13 i 1x 11.18)14(11.18[1v](i)){c[i]=11.18[i]}11.1u.1O.2i&&(c.2Y=11.2l());(11.1u.34!=1||11.1u.35!=1)&&(c.1U=11.1U());c.2a&&c.1l=="1P"&&(c.1l=c.2a)&&2x c.2a;15 c}14(b==1g&&R.1q(a,2R)){14(a=="3o"){15 3v.1K(11)}14(a=="2Y"){15 11.2l()}14(a=="1U"){15 11.1U()}14(a==3B&&11.18.1l=="1P"&&11.18.2a){15 11.18.2a}15 11.18[a]}14(b==1g&&R.1q(a,2A)){13 d={};1h(13 j=0,2d=a.19;j<2d;j++){d[a[j]]=11.1X(a[j])}15 d}14(b!=1g){13 e={};e[a]=b}1j 14(a!=1g&&R.1q(a,"3d")){e=a}1h(13 f 1x 11.1p.3k)14(11.1p.3k[1v](f)&&e[1v](f)&&R.1q(11.1p.3k[f],"17")){13 g=11.1p.3k[f].24(11,[][20](e[f]));11.18[f]=e[f];1h(13 h 1x g)14(g[1v](h)){e[h]=g[h]}}G(11,e);15 11};1Q[1J].9i=17(){14(11.1M){15 11}11.1b.1N[1r](11.1b);13 a=11.1p;a.1w!=11&&7z(11,a);15 11};1Q[1J].9j=17(){14(11.1M){15 11}14(11.1b.1N.2S!=11.1b){11.1b.1N.3x(11.1b,11.1b.1N.2S);7A(11,11.1p);13 a=11.1p}15 11};1Q[1J].6x=17(a){14(11.1M){15 11}13 b=a.1b||a[a.19-1].1b;14(b.6y){b.1N.3x(11.1b,b.6y)}1j{b.1N[1r](11.1b)}7B(11,a,11.1p);15 11};1Q[1J].3x=17(a){14(11.1M){15 11}13 b=a.1b||a[0].1b;b.1N.3x(11.1b,b);7C(11,a,11.1p);15 11};1Q[1J].2L=17(a){13 t=11;14(+a!==0){13 b=$("45"),2L=$("bE");t.18.2L=a;b.2z=5m();$(2L,{bF:+a||1.5});b.1r(2L);t.1p.4h.1r(b);t.5H=b;$(t.1b,{45:"3l(#"+b.2z+")"})}1j{14(t.5H){t.5H.1N.33(t.5H);2x t.5H;2x t.18.2L}t.1b.bG("45")}};13 J=17(a,x,y,r){13 b=$("3i");a.1S&&a.1S[1r](b);13 c=2c 1Q(b,a);c.18={cx:x,cy:y,r:r,1l:"1P",1i:"#3E"};c.1n="3i";$(b,c.18);15 c},7N=17(a,x,y,w,h,r){13 b=$("1Z");a.1S&&a.1S[1r](b);13 c=2c 1Q(b,a);c.18={x:x,y:y,1a:w,1c:h,r:r||0,3m:r||0,3n:r||0,1l:"1P",1i:"#3E"};c.1n="1Z";$(b,c.18);15 c},7O=17(a,x,y,b,c){13 d=$("3j");a.1S&&a.1S[1r](d);13 e=2c 1Q(d,a);e.18={cx:x,cy:y,3m:b,3n:c,1l:"1P",1i:"#3E"};e.1n="3j";$(d,e.18);15 e},7P=17(a,b,x,y,w,h){13 c=$("2X");$(c,{x:x,y:y,1a:w,1c:h,bH:"1P"});c.5B(a.4E,"4b",b);a.1S&&a.1S[1r](c);13 d=2c 1Q(c,a);d.18={x:x,y:y,1a:w,1c:h,3D:b};d.1n="2X";15 d},7Q=17(a,x,y,b){13 c=$("1R");$(c,{x:x,y:y,"1R-63":"64"});a.1S&&a.1S[1r](c);13 d=2c 1Q(c,a);d.18={x:x,y:y,"1R-63":"64",1R:b,1A:5g.1A,1i:"1P",1l:"#3E"};d.1n="1R";G(d,d.18);15 d},6z=17(a,b){11.1a=a||11.1a;11.1c=b||11.1c;11.1S[2K]("1a",11.1a);11.1S[2K]("1c",11.1c);15 11},5V=17(){13 a=7y[24](0,2Q),1V=a&&a.1V,x=a.x,y=a.y,1a=a.1a,1c=a.1c;14(!1V){7D 2c 7E("6b 1V 9k 9l.");}13 b=$("4u");x=x||0;y=y||0;1a=1a||9m;1c=1c||9n;$(b,{9o:"5h://68.69.6a/7G/4u",5W:1.1,1a:1a,1c:1c});14(1V==1){b.1s.3U="3y:3M;2r:"+x+"1B;1w:"+y+"1B";1z.3r[1r](b)}1j{14(1V.2S){1V.3x(b,1V.2S)}1j{1V[1r](b)}}1V=2c 5e;1V.1a=1a;1V.1c=1c;1V.1S=b;6p.1K(1V,1V,R.7g);1V.7R();15 1V};27.7R=17(){13 c=11.1S;2B(c.2S){c.33(c.2S)}11.2V=11.1w=1g;(11.7S=$("7S"))[1r](1z.9h("bI bJ 4U\\4V"));c[1r](11.7S);c[1r](11.4h=$("4h"))};27.5G=17(){11.1S.1N&&11.1S.1N.33(11.1S);1h(13 i 1x 11){11[i]=1M(i)}}}14(R.5k){13 K={M:"m",L:"l",C:"c",Z:"x",m:"t",l:"r",c:"v",z:"x"},9p=/([7T]),?([^7T]*)/8I,9q=/ 8z:\\S+9r\\([^\\)]+\\)/g,9s=/-?[^,\\s-]+/g,37=4v+S+4v,4G=10,54={1o:1,1Z:1},9t=17(f){13 g=/[bK]/7a,6A=4Z;1G(f).43(g)&&(6A=5t);g=/[7T]/g;14(6A==4Z&&!1G(f).43(g)){13 h=1G(f)[2v](9p,17(b,c,d){13 e=[],9u=3Z.1K(c)=="m",h=K[c];d[2v](9s,17(a){14(9u&&e[19]==2){h+=e+K[c=="m"?"l":"L"];e=[]}e[1F](2j(a*4G))});15 h+e});15 h}13 k=6A(f),p,r;h=[];1h(13 i=0,1k=k[19];i<1k;i++){p=k[i];r=3Z.1K(k[i][0]);r=="z"&&(r="x");1h(13 j=1,2d=p[19];j<2d;j++){r+=2j(p[j]*4G)+(j!=2d-1?",":E)}h[1F](r)}15 h[29](S)};R[2h]=17(){15"98 9a bL\\bM bN 6b. bO bP 1L 4t.\\9b 7F 9c 4U\\4V "+11.5W};C=17(a,b){13 g=L("46");g.1s.3U="3y:3M;2r:0;1w:0;1a:"+b.1a+"1B;1c:"+b.1c+"1B";g.37=b.37;g.3g=b.3g;13 c=L("3p"),4k=c.1s;4k.1a=b.1a+"1B";4k.1c=b.1c+"1B";c.37=37;c.3g=b.3g;g[1r](c);13 p=2c 1Q(c,g,b),1X={1l:"1P",1i:"#3E"};a&&(1X.1o=a);p.1n="1o";p.1o=[];p.bQ=E;G(p,1X);b.1S[1r](g);15 p};G=17(o,b){o.18=o.18||{};13 c=o.1b,a=o.18,s=c.1s,2q,7U=(b.x!=a.x||b.y!=a.y||b.1a!=a.1a||b.1c!=a.1c||b.r!=a.r)&&o.1n=="1Z",1y=o;1h(13 d 1x b)14(b[1v](d)){a[d]=b[d]}14(7U){a.1o=7V(a.x,a.y,a.1a,a.1c,a.r);o.X=a.x;o.Y=a.y;o.W=a.1a;o.H=a.1c}b.4b&&(c.4b=b.4b);b.4R&&(c.4R=b.4R);b.4r&&(c.4r=b.4r);b.4P&&(s.4P=b.4P);"2L"1x b&&o.2L(b.2L);14(b.1o&&o.1n=="1o"||7U){c.1o=9t(a.1o)}14(b.2Y!=1g){o.2l(b.2Y,2F)}14(b.3o){2q=1G(b.3o)[21](z);3v.1K(o,2q[0],2q[1]);14(o.1u.1O.cx!=1g){o.1u.1O.cx+=+2q[0];o.1u.1O.cy+=+2q[1];o.4l(o.18,2q[0],2q[1])}}14(b.1U){2q=1G(b.1U)[21](z);o.1U(+2q[0]||1,+2q[1]||+2q[0]||1,+2q[2]||1g,+2q[3]||1g)}14("1T-1Z"1x b){13 e=1G(b["1T-1Z"])[21](z);14(e[19]==4){e[2]=+e[2]+(+e[0]);e[3]=+e[3]+(+e[1]);13 f=c.5I||1z.41("7c"),4H=f.1s,46=c.1N;4H.1T=R.3L("1Z({1}1B {2}1B {3}1B {0}1B)",e);14(!c.5I){4H.3y="3M";4H.1w=0;4H.2r=0;4H.1a=o.1p.1a+"1B";4H.1c=o.1p.1c+"1B";46.1N.3x(f,46);f[1r](46);c.5I=f}}14(!b["1T-1Z"]){c.5I&&(c.5I.1s.1T=E)}}14(o.1n=="2X"&&b.3D){c.3D=b.3D}14(o.1n=="2X"&&b.1m){c.6B=2k+".bR(1m="+(b.1m*2N)+")";s.45=(c.5J||E)+(c.6B||E)}b.1A&&(s.1A=b.1A);b["1A-3O"]&&(s.9v=\'"\'+b["1A-3O"][21](",")[0][2v](/^[\'"]+|[\'"]+$/g,E)+\'"\');b["1A-40"]&&(s.5C=b["1A-40"]);b["1A-4a"]&&(s.9w=b["1A-4a"]);b["1A-1s"]&&(s.9x=b["1A-1s"]);14(b.1m!=1g||b["1i-1a"]!=1g||b.1l!=1g||b.1i!=1g||b["1i-1a"]!=1g||b["1i-1m"]!=1g||b["1l-1m"]!=1g||b["1i-3P"]!=1g||b["1i-5j"]!=1g||b["1i-5i"]!=1g||b["1i-4c"]!=1g){c=o.3p||c;13 g=(c.4F(3B)&&c.4F(3B)[0]),7W=3W;!g&&(7W=g=L(3B));14("1l-1m"1x b||"1m"1x b){13 h=((+a["1l-1m"]+1||2)-1)*((+a.1m+1||2)-1)*((+R.3t(b.1l).o+1||2)-1);h=3a(2H(h,0),1);g.1m=h}b.1l&&(g.3z=2F);14(g.3z==1g||b.1l=="1P"){g.3z=3W}14(g.3z&&b.1l){13 i=b.1l.43(74);14(i){g.3D=i[1];g.1n="bS"}1j{g.2M=R.3t(b.1l).3G;g.3D=E;g.1n="bT";14(R.3t(b.1l).5p&&(1y.1n 1x{3i:1,3j:1}||1G(b.1l).4f()!="r")&&D(1y,b.1l)){a.1l="1P";a.2a=b.1l}}}7W&&c[1r](g);13 j=(c.4F("1i")&&c.4F("1i")[0]),7X=3W;!j&&(7X=j=L("1i"));14((b.1i&&b.1i!="1P")||b["1i-1a"]||b["1i-1m"]!=1g||b["1i-3P"]||b["1i-5j"]||b["1i-5i"]||b["1i-4c"]){j.3z=2F}(b.1i=="1P"||j.3z==1g||b.1i==0||b["1i-1a"]==0)&&(j.3z=3W);13 k=R.3t(b.1i);j.3z&&b.1i&&(j.2M=k.3G);h=((+a["1i-1m"]+1||2)-1)*((+a.1m+1||2)-1)*((+k.o+1||2)-1);13 l=(1E(b["1i-1a"])||1)*.75;h=3a(2H(h,0),1);b["1i-1a"]==1g&&(l=a["1i-1a"]);b["1i-1a"]&&(j.4a=l);l&&l<1&&(h*=l)&&(j.4a=1);j.1m=h;b["1i-5i"]&&(j.bU=b["1i-5i"]||"bV");j.5j=b["1i-5j"]||8;b["1i-4c"]&&(j.bW=b["1i-4c"]=="4Q"?"bX":b["1i-4c"]=="7J"?"7J":"2j");14(b["1i-3P"]){13 m={"-":"bY",".":"bZ","-.":"c0","-..":"c1",". ":"2n","- ":"c3","--":"c4","- .":"c5","--.":"c6","--..":"c7"};j.c8=m[1v](b["1i-3P"])?m[b["1i-3P"]]:E}7X&&c[1r](j)}14(1y.1n=="1R"){s=1y.1p.3A.1s;a.1A&&(s.1A=a.1A);a["1A-3O"]&&(s.9v=a["1A-3O"]);a["1A-40"]&&(s.5C=a["1A-40"]);a["1A-4a"]&&(s.9w=a["1A-4a"]);a["1A-1s"]&&(s.9x=a["1A-1s"]);1y.1b.2R&&(1y.1p.3A.7d=1G(1y.1b.2R)[2v](/</g,"&#60;")[2v](/&/g,"&#38;")[2v](/\\n/g,"<br>"));1y.W=a.w=1y.1p.3A.6n;1y.H=a.h=1y.1p.3A.6o;1y.X=a.x;1y.Y=a.y+2j(1y.H/2);2U(a["1R-63"]){1f"2p":1y.1b.1s["v-1R-7Y"]="2r";1y.7Z=2j(1y.W/2);1d;1f"2C":1y.1b.1s["v-1R-7Y"]="c9";1y.7Z=-2j(1y.W/2);1d;3c:1y.1b.1s["v-1R-7Y"]="ca";1d}}};D=17(o,d){o.18=o.18||{};13 e=o.18,1l,1n="5A",6C=".5 .5";o.18.2a=d;d=1G(d)[2v](7b,17(a,b,c){1n="6q";14(b&&c){b=1E(b);c=1E(c);1W(b-.5,2)+1W(c-.5,2)>.25&&(c=1H.4g(.25-1W(b-.5,2))*((c>.5)*2-1)+.5);6C=b+S+c}15 E});d=d[21](/\\s*\\-\\s*/);14(1n=="5A"){13 f=d.4w();f=-1E(f);14(6r(f)){15 1g}}13 g=7x(d);14(!g){15 1g}o=o.3p||o.1b;1l=o.4F(3B)[0]||L(3B);!1l.1N&&o.1r(1l);14(g[19]){1l.3z=2F;1l.96="1P";1l.2M=g[0].2M;1l.cb=g[g[19]-1].2M;13 h=[];1h(13 i=0,1k=g[19];i<1k;i++){g[i].3K&&h[1F](g[i].3K+S+g[i].2M)}1l.9y&&(1l.9y.56=h[19]?h[29]():"0% "+1l.2M);14(1n=="6q"){1l.1n="cc";1l.cd="2N%";1l.ce=6C;1l.cf=6C}1j{1l.1n="2a";1l.6d=(cg-f)%4d}}15 1};1Q=17(a,b,c){13 d=0,ch=0,ci=0,cj=1;11[0]=a;11.2z=R.7f++;11.1b=a;a.7L=11;11.X=0;11.Y=0;11.18={};11.2f=b;11.1p=c;11.1u={5D:0,5E:0,1O:{2i:0},34:1,35:1};!c.2V&&(c.2V=11);11.2I=c.1w;c.1w&&(c.1w.2y=11);c.1w=11;11.2y=1g};I=1Q[1J];I.2l=17(a,b,c){14(11.1M){15 11}14(a==1g){14(11.1u.1O.cx){15[11.1u.1O.2i,11.1u.1O.cx,11.1u.1O.cy][29](S)}15 11.1u.1O.2i}a=1G(a)[21](z);14(a[19]-1){b=1E(a[1]);c=1E(a[2])}a=1E(a[0]);14(b!=1g){11.1u.1O.2i=a}1j{11.1u.1O.2i+=a}c==1g&&(b=1g);11.1u.1O.cx=b;11.1u.1O.cy=c;11.4l(11.18,b,c);11.2f.1s.2Y=11.1u.1O.2i;15 11};I.4l=17(a,b,c){14(11.1M){15 11}13 d=11.2f.1s,2J=(11.3p&&11.3p.1s)||11.1b.1s;a=a||{};1h(13 i 1x a)14(a[1v](i)){11.18[i]=a[i]}b=b||11.1u.1O.cx;c=c||11.1u.1O.cy;13 e=11.18,x,y,w,h;2U(11.1n){1f"3i":x=e.cx-e.r;y=e.cy-e.r;w=h=e.r*2;1d;1f"3j":x=e.cx-e.3m;y=e.cy-e.3n;w=e.3m*2;h=e.3n*2;1d;1f"2X":x=+e.x;y=+e.y;w=e.1a||0;h=e.1c||0;1d;1f"1R":11.80.v=["m",2j(e.x),", ",2j(e.y-2),"l",2j(e.x)+1,", ",2j(e.y-2)][29](E);x=e.x-2j(11.W/2);y=e.y-11.H/2;w=11.W;h=11.H;1d;1f"1Z":1f"1o":14(!11.18.1o){x=0;y=0;w=11.1p.1a;h=11.1p.1c}1j{13 f=B(11.18.1o);x=f.x;y=f.y;w=f.1a;h=f.1c}1d;3c:x=0;y=0;w=11.1p.1a;h=11.1p.1c;1d}b=(b==1g)?x+w/2:b;c=(c==1g)?y+h/2:c;13 g=b-11.1p.1a/2,1w=c-11.1p.1c/2,t;d.2r!=(t=g+"1B")&&(d.2r=t);d.1w!=(t=1w+"1B")&&(d.1w=t);11.X=54[1v](11.1n)?-g:x;11.Y=54[1v](11.1n)?-1w:y;11.W=w;11.H=h;14(54[1v](11.1n)){2J.2r!=(t=-g*4G+"1B")&&(2J.2r=t);2J.1w!=(t=-1w*4G+"1B")&&(2J.1w=t)}1j 14(11.1n=="1R"){2J.2r!=(t=-g+"1B")&&(2J.2r=t);2J.1w!=(t=-1w+"1B")&&(2J.1w=t)}1j{d.1a!=(t=11.1p.1a+"1B")&&(d.1a=t);d.1c!=(t=11.1p.1c+"1B")&&(d.1c=t);2J.2r!=(t=x-g+"1B")&&(2J.2r=t);2J.1w!=(t=y-1w+"1B")&&(2J.1w=t);2J.1a!=(t=w+"1B")&&(2J.1a=t);2J.1c!=(t=h+"1B")&&(2J.1c=t)}};I.7M=17(){!11.1M&&(11.2f.1s.42="1P");15 11};I.6t=17(){!11.1M&&(11.2f.1s.42="9z");15 11};I.3T=17(){14(11.1M){15 11}14(54[1v](11.1n)){15 B(11.18.1o)}15{x:11.X+(11.7Z||0),y:11.Y,1a:11.W,1c:11.H}};I.5G=17(){14(11.1M){15}4D(11,11.1p);11.1b.1N.33(11.1b);11.2f.1N.33(11.2f);11.3p&&11.3p.1N.33(11.3p);1h(13 i 1x 11){2x 11[i]}11.1M=2F};I.1X=17(a,b){14(11.1M){15 11}14(a==1g){13 c={};1h(13 i 1x 11.18)14(11.18[1v](i)){c[i]=11.18[i]}11.1u.1O.2i&&(c.2Y=11.2l());(11.1u.34!=1||11.1u.35!=1)&&(c.1U=11.1U());c.2a&&c.1l=="1P"&&(c.1l=c.2a)&&2x c.2a;15 c}14(b==1g&&R.1q(a,"2R")){14(a=="3o"){15 3v.1K(11)}14(a=="2Y"){15 11.2l()}14(a=="1U"){15 11.1U()}14(a==3B&&11.18.1l=="1P"&&11.18.2a){15 11.18.2a}15 11.18[a]}14(11.18&&b==1g&&R.1q(a,2A)){13 d,1D={};1h(i=0,d=a[19];i<d;i++){1D[a[i]]=11.1X(a[i])}15 1D}13 e;14(b!=1g){e={};e[a]=b}b==1g&&R.1q(a,"3d")&&(e=a);14(e){1h(13 f 1x 11.1p.3k)14(11.1p.3k[1v](f)&&e[1v](f)&&R.1q(11.1p.3k[f],"17")){13 g=11.1p.3k[f].24(11,[][20](e[f]));11.18[f]=e[f];1h(13 h 1x g)14(g[1v](h)){e[h]=g[h]}}14(e.1R&&11.1n=="1R"){11.1b.2R=e.1R}G(11,e);14(e.2a&&(({3i:1,3j:1})[1v](11.1n)||1G(e.2a).4f()!="r")){D(11,e.2a)}(!54[1v](11.1n)||11.1u.1O.2i)&&11.4l(11.18)}15 11};I.9i=17(){!11.1M&&11.2f.1N[1r](11.2f);11.1p.1w!=11&&7z(11,11.1p);15 11};I.9j=17(){14(11.1M){15 11}14(11.2f.1N.2S!=11.2f){11.2f.1N.3x(11.2f,11.2f.1N.2S);7A(11,11.1p)}15 11};I.6x=17(a){14(11.1M){15 11}14(a.4I==T){a=a[a.19-1]}14(a.2f.6y){a.2f.1N.3x(11.2f,a.2f.6y)}1j{a.2f.1N[1r](11.2f)}7B(11,a,11.1p);15 11};I.3x=17(a){14(11.1M){15 11}14(a.4I==T){a=a[0]}a.2f.1N.3x(11.2f,a.2f);7C(11,a,11.1p);15 11};I.2L=17(a){13 s=11.1b.ck,f=s.45;f=f.8E(9q,E);14(+a!==0){11.18.2L=a;s.45=f+S+2k+".9r(cl="+(+a||1.5)+")";s.6D=R.3L("-{0}1B 0 0 -{0}1B",2j(+a||1.5))}1j{s.45=f;s.6D=0;2x 11.18.2L}};J=17(a,x,y,r){13 g=L("46"),o=L("9A"),4k=o.1s;g.1s.3U="3y:3M;2r:0;1w:0;1a:"+a.1a+"1B;1c:"+a.1c+"1B";g.37=37;g.3g=a.3g;g[1r](o);13 b=2c 1Q(o,g,a);b.1n="3i";G(b,{1i:"#3E",1l:"1P"});b.18.cx=x;b.18.cy=y;b.18.r=r;b.4l({x:x-r,y:y-r,1a:r*2,1c:r*2});a.1S[1r](g);15 b};17 7V(x,y,w,h,r){14(r){15 R.3L("M{0},{1}l{2},9B{3},{3},0,0,1,{3},{3}9C,{5}a{3},{3},0,0,1,{4},{3}l{6},9B{3},{3},0,0,1,{4},{4}9C,{7}a{3},{3},0,0,1,{3},{4}z",x+r,y,w-r*2,r,-r,h-r*2,r*2-w,r*2-h)}1j{15 R.3L("M{0},{1}l{2},0,0,{3},{4},cm",x,y,w,h,-w)}}7N=17(b,x,y,w,h,r){13 c=7V(x,y,w,h,r),1y=b.1o(c),a=1y.18;1y.X=a.x=x;1y.Y=a.y=y;1y.W=a.1a=w;1y.H=a.1c=h;a.r=r;a.1o=c;1y.1n="1Z";15 1y};7O=17(a,x,y,b,c){13 g=L("46"),o=L("9A"),4k=o.1s;g.1s.3U="3y:3M;2r:0;1w:0;1a:"+a.1a+"1B;1c:"+a.1c+"1B";g.37=37;g.3g=a.3g;g[1r](o);13 d=2c 1Q(o,g,a);d.1n="3j";G(d,{1i:"#3E"});d.18.cx=x;d.18.cy=y;d.18.3m=b;d.18.3n=c;d.4l({x:x-b,y:y-c,1a:b*2,1c:c*2});a.1S[1r](g);15 d};7P=17(a,b,x,y,w,h){13 g=L("46"),o=L("2X");g.1s.3U="3y:3M;2r:0;1w:0;1a:"+a.1a+"1B;1c:"+a.1c+"1B";g.37=37;g.3g=a.3g;o.3D=b;g[1r](o);13 c=2c 1Q(o,g,a);c.1n="2X";c.18.3D=b;c.18.x=x;c.18.y=y;c.18.w=w;c.18.h=h;c.4l({x:x,y:y,1a:w,1c:h});a.1S[1r](g);15 c};7Q=17(a,x,y,b){13 g=L("46"),2s=L("3p"),4k=2s.1s,1o=L("1o"),cn=1o.1s,o=L("80");g.1s.3U="3y:3M;2r:0;1w:0;1a:"+a.1a+"1B;1c:"+a.1c+"1B";g.37=37;g.3g=a.3g;1o.v=R.3L("m{0},{1}l{2},{1}",2j(x*10),2j(y*10),2j(x*10)+1);1o.co=2F;4k.1a=a.1a;4k.1c=a.1c;o.2R=1G(b);o.3z=2F;2s[1r](o);2s[1r](1o);g[1r](2s);13 c=2c 1Q(o,g,a);c.3p=2s;c.80=1o;c.1n="1R";c.18.1R=b;c.18.x=x;c.18.y=y;c.18.w=1;c.18.h=1;G(c,{1A:5g.1A,1i:"1P",1l:"#3E"});c.4l();a.1S[1r](g);15 c};6z=17(a,b){13 c=11.1S.1s;a==+a&&(a+="1B");b==+b&&(b+="1B");c.1a=a;c.1c=b;c.1T="1Z(0 "+a+" "+b+" 0)";15 11};13 L;1z.cp().cq(".57","8N:3l(#3c#4t)");6e{!1z.9D.57&&1z.9D.cr("57","9E:9F-9G-79:5k");L=17(a){15 1z.41(\'<57:\'+a+\' 9H="57">\')}}6f(e){L=17(a){15 1z.41(\'<\'+a+\' 9o="9E:9F-9G.79:5k" 9H="57">\')}}5V=17(){13 a=7y[24](0,2Q),1V=a.1V,1c=a.1c,s,1a=a.1a,x=a.x,y=a.y;14(!1V){7D 2c 7E("4t 1V 9k 9l.");}13 b=2c 5e,c=b.1S=1z.41("7c"),cs=c.1s;x=x||0;y=y||0;1a=1a||9m;1c=1c||9n;1a==+1a&&(1a+="1B");1c==+1c&&(1c+="1B");b.1a=4v;b.1c=4v;b.37=4G*4v+S+4G*4v;b.3g="0 0";b.3A=1z.41("3A");b.3A.1s.3U="3y:3M;2r:-53;1w:-53;9I:0;6D:0;9J-1c:1;42:81;";c[1r](b.3A);cs.3U=R.3L("1w:0;2r:0;1a:{0};1c:{1};42:81-9z;3y:ct;1T:1Z(0 {0} {1} 0);cu:cv",1a,1c);14(1V==1){1z.3r[1r](c);cs.2r=x+"1B";cs.1w=y+"1B";cs.3y="3M"}1j{14(1V.2S){1V.3x(c,1V.2S)}1j{1V[1r](c)}}6p.1K(b,b,R.7g);15 b};27.7R=17(){11.1S.7d=E;11.3A=1z.41("3A");11.3A.1s.3U="3y:3M;2r:-53;1w:-53;9I:0;6D:0;9J-1c:1;42:81;";11.1S[1r](11.3A);11.2V=11.1w=1g};27.5G=17(){11.1S.1N.33(11.1S);1h(13 i 1x 11){11[i]=1M(i)}15 2F}}13 M=82.cw.43(/cz\\/(.*?)\\s/);14((82.cA=="cB cC, cD.")&&(M&&M[1]<4||82.cE.1Y(0,2)=="cF")){27.6u=17(){13 a=11.1Z(-99,-99,11.1a+99,11.1c+99).1X({1i:"1P"});3N.4J(17(){a.5G()})}}1j{27.6u=17(){}}13 N=17(){11.cG=3W},9K=17(){15 11.58.4K()},59=17(){11.cH=2F},9L=17(){15 11.58.59()},9M=(17(){14(1z.9N){15 17(b,c,d,g){13 h=5Y&&5Z[c]?5Z[c]:c;13 f=17(e){14(5Y&&5Z[1v](c)){1h(13 i=0,1k=e.6E&&e.6E.19;i<1k;i++){14(e.6E[i].4r==b){13 a=e;e=e.6E[i];e.58=a;e.4K=9K;e.59=9L;1d}}}15 d.1K(g,e)};b.9N(h,f,3W);15 17(){b.cI(h,f,3W);15 2F}}}1j 14(1z.9O){15 17(a,b,c,d){13 f=17(e){e=e||3N.cJ;e.4K=e.4K||N;e.59=e.59||59;15 c.1K(d,e)};a.9O("3z"+b,f);13 g=17(){a.cK("3z"+b,f);15 2F};15 g}}})(),2W=[],6F=17(e){13 x=e.6G,y=e.6H,9P=1z.6I.6J||1z.3r.6J,5K=1z.6I.6K||1z.3r.6K,2G,j=2W.19;2B(j--){2G=2W[j];14(5Y){13 i=e.9Q.19,5L;2B(i--){5L=e.9Q[i];14(5L.9R==2G.2s.4m.2z){x=5L.6G;y=5L.6H;(e.58?e.58:e).4K();1d}}}1j{e.4K()}x+=5K;y+=9P;2G.6L&&2G.6L.1K(2G.83||2G.2s,x-2G.2s.4m.x,y-2G.2s.4m.y,x,y,e)}},6M=17(e){R.9S(6F).9T(6M);13 i=2W.19,2G;2B(i--){2G=2W[i];2G.2s.4m={};2G.2C&&2G.2C.1K(2G.9U||2G.9V||2G.83||2G.2s,e)}2W=[]};1h(13 i=49[19];i--;){(17(c){R[c]=1Q[1J][c]=17(a,b){14(R.1q(a,"17")){11.49=11.49||[];11.49.1F({3u:c,f:a,9W:9M(11.3p||11.1b||1z,c,a,b||11)})}15 11};R["9X"+c]=1Q[1J]["9X"+c]=17(a){13 b=11.49,l=b[19];2B(l--)14(b[l].3u==c&&b[l].f==a){b[l].9W();b.3h(l,1);!b.19&&2x 11.49;15 11}15 11}})(49[i])}I.cL=17(a,b,c,d){15 11.8p(a,c).8o(b,d||c)};I.cM=17(a,b){15 11.cN(a).cO(b)};I.2W=17(b,c,d,f,g,h){11.4m={};11.71(17(e){(e.58||e).4K();13 a=1z.6I.6J||1z.3r.6J,5K=1z.6I.6K||1z.3r.6K;11.4m.x=e.6G+5K;11.4m.y=e.6H+a;11.4m.2z=e.9R;c&&c.1K(g||f||11,e.6G+5K,e.6H+a,e);!2W.19&&R.72(6F).73(6M);2W.1F({2s:11,6L:b,2C:d,83:f,9V:g,9U:h})});15 11};I.cP=17(a,b,c){13 i=2W.19;2B(i--){2W[i].2s==11&&(2W[i].6L==a&&2W[i].2C==c)&&2W.3h(i++,1)}!2W.19&&R.9S(6F).9T(6M)};27.3i=17(x,y,r){15 J(11,x||0,y||0,r||0)};27.1Z=17(x,y,w,h,r){15 7N(11,x||0,y||0,w||0,h||0,r||0)};27.3j=17(x,y,a,b){15 7O(11,x||0,y||0,a||0,b||0)};27.1o=17(a){a&&!R.1q(a,2R)&&!R.1q(a[0],2A)&&(a+=E);15 C(R.3L[24](R,2Q),11)};27.2X=17(a,x,y,w,h){15 7P(11,a||"cQ:9d",x||0,y||0,w||0,h||0)};27.1R=17(x,y,a){15 7Q(11,x||0,y||0,1G(a))};27.3Y=17(a){2Q[19]>1&&(a=6c[1J].3h.1K(2Q,0,2Q[19]));15 2c T(a)};27.6z=6z;27.1w=27.2V=1g;27.7L=R;17 84(){15 11.x+S+11.y}I.cR=17(){14(11.1M){15 11}11.1u.34=1;11.1u.35=1;11.18.1U="1 1"};I.1U=17(x,y,b,c){14(11.1M){15 11}14(x==1g&&y==1g){15{x:11.1u.34,y:11.1u.35,2h:84}}y=y||x;!+y&&(y=x);13 d,dy,cS,cT,a=11.18;14(x!=0){13 e=11.3T(),6N=e.x+e.1a/2,6O=e.y+e.1c/2,4L=2o(x/11.1u.34),4M=2o(y/11.1u.35);b=(+b||b==0)?b:6N;c=(+c||c==0)?c:6O;13 f=11.1u.34>0,85=11.1u.35>0,47=~~(x/2o(x)),48=~~(y/2o(y)),86=4L*47,87=4M*48,s=11.1b.1s,5M=b+2o(6N-b)*86*(6N>b==f?1:-1),5N=c+2o(6O-c)*87*(6O>c==85?1:-1),88=(x*47>y*48?4M:4L);2U(11.1n){1f"1Z":1f"2X":13 g=a.1a*4L,6P=a.1c*4M;11.1X({1c:6P,r:a.r*88,1a:g,x:5M-g/2,y:5N-6P/2});1d;1f"3i":1f"3j":11.1X({3m:a.3m*4L,3n:a.3n*4M,r:a.r*88,cx:5M,cy:5N});1d;1f"1R":11.1X({x:5M,y:5N});1d;1f"1o":13 h=5u(a.1o),89=2F,31=f?86:4L,2E=85?87:4M;1h(13 i=0,1k=h[19];i<1k;i++){13 p=h[i],5O=5f.1K(p[0]);14(5O=="M"&&89){6s}1j{89=3W}14(5O=="A"){p[h[i][19]-2]*=31;p[h[i][19]-1]*=2E;p[1]*=4L;p[2]*=4M;p[5]=+(47+48?!!+p[5]:!+p[5])}1j 14(5O=="H"){1h(13 j=1,2d=p[19];j<2d;j++){p[j]*=31}}1j 14(5O=="V"){1h(j=1,2d=p[19];j<2d;j++){p[j]*=2E}}1j{1h(j=1,2d=p[19];j<2d;j++){p[j]*=(j%2)?31:2E}}}13 k=B(h);d=5M-k.x-k.1a/2;dy=5N-k.y-k.1c/2;h[0][1]+=d;h[0][2]+=dy;11.1X({1o:h});1d}14(11.1n 1x{1R:1,2X:1}&&(47!=1||48!=1)){14(11.3V){11.3V[2]="1U("[20](47,",",48,")");11.1b[2K]("5F",11.3V[29](S));d=(47==-1)?-a.x-(g||0):a.x;dy=(48==-1)?-a.y-(6P||0):a.y;11.1X({x:d,y:dy});a.31=47-1;a.2E=48-1}1j{11.1b.5J=2k+".cU(cV="[20](47,", cW=0, cX=0, cY=",48,", cZ=0, d0=0, d1=\'d3 d4\', d5=\'d6\')");s.45=(11.1b.5J||E)+(11.1b.6B||E)}}1j{14(11.3V){11.3V[2]=E;11.1b[2K]("5F",11.3V[29](S));a.31=0;a.2E=0}1j{11.1b.5J=E;s.45=(11.1b.5J||E)+(11.1b.6B||E)}}a.1U=[x,y,b,c][29](S);11.1u.34=x;11.1u.35=y}15 11};I.8a=17(){14(11.1M){15 1g}13 a=11.1X();2x a.1U;2x a.3o;15 11.1p[11.1n]().1X(a)};13 O={},5P=17(a,b,c,d,e,f,g,h,j){13 k=0,5Q=2N,3u=[a,b,c,d,e,f,g,h].29(),2w=O[3u],6Q,2n;!2w&&(O[3u]=2w={3I:[]});2w.8b&&8c(2w.8b);2w.8b=4J(17(){2x O[3u]},7G);14(j!=1g){13 l=5P(a,b,c,d,e,f,g,h);5Q=~~l*10}1h(13 i=0;i<5Q+1;i++){14(2w.3I[j]>i){2n=2w.3I[i*5Q]}1j{2n=R.7l(a,b,c,d,e,f,g,h,i/5Q);2w.3I[i]=2n}i&&(k+=1W(1W(6Q.x-2n.x,2)+1W(6Q.y-2n.y,2),.5));14(j!=1g&&k>=j){15 2n}6Q=2n}14(j==1g){15 k}},6R=17(d,e){15 17(a,b,c){a=5t(a);13 x,y,p,l,4N="",5R={},26,39=0;1h(13 i=0,1k=a.19;i<1k;i++){p=a[i];14(p[0]=="M"){x=+p[1];y=+p[2]}1j{l=5P(x,y,p[1],p[2],p[3],p[4],p[5],p[6]);14(39+l>b){14(e&&!5R.2p){26=5P(x,y,p[1],p[2],p[3],p[4],p[5],p[6],b-39);4N+=["C",26.2p.x,26.2p.y,26.m.x,26.m.y,26.x,26.y];14(c){15 4N}5R.2p=4N;4N=["M",26.x,26.y+"C",26.n.x,26.n.y,26.2C.x,26.2C.y,p[5],p[6]][29]();39+=l;x=+p[5];y=+p[6];6s}14(!d&&!e){26=5P(x,y,p[1],p[2],p[3],p[4],p[5],p[6],b-39);15{x:26.x,y:26.y,3J:26.3J}}}39+=l;x=+p[5];y=+p[6]}4N+=p}5R.2C=4N;26=d?39:e?5R:R.7l(x,y,p[1],p[2],p[3],p[4],p[5],p[6],1);26.3J&&(26={x:26.x,y:26.y,3J:26.3J});15 26}};13 P=6R(1),5a=6R(),6S=6R(0,1);I.6T=17(){14(11.1n!="1o"){15}14(11.1b.6T){15 11.1b.6T()}15 P(11.18.1o)};I.5a=17(a){14(11.1n!="1o"){15}15 5a(11.18.1o,a)};I.d7=17(b,c){14(11.1n!="1o"){15}14(2o(11.6T()-c)<"1e-6"){15 6S(11.18.1o,b).2C}13 a=6S(11.18.1o,c,1);15 b?6S(a,b).2C:a};R.9Y={5A:17(n){15 n},"<":17(n){15 1W(n,3)},">":17(n){15 1W(n-1,3)+1},"<>":17(n){n=n*2;14(n<1){15 1W(n,3)/2}n-=2;15(1W(n,3)+2)/2},d8:17(n){13 s=1.9Z;15 n*n*((s+1)*n-s)},d9:17(n){n=n-1;13 s=1.9Z;15 n*n*((s+1)*n+s)+1},da:17(n){14(n==0||n==1){15 n}13 p=.3,s=p/4;15 1W(2,-10*n)*1H.44((n-s)*(2*2u)/p)+1},db:17(n){13 s=7.dc,p=2.75,l;14(n<(1/p)){l=s*n*n}1j{14(n<(2/p)){n-=(1.5/p);l=s*n*n+.75}1j{14(n<(2.5/p)){n-=(2.25/p);l=s*n*n+.dd}1j{n-=(2.de/p);l=s*n*n+.df}}}15 l}};13 Q=[],8d=17(){13 a=+2c a0;1h(13 l=0;l<Q[19];l++){13 e=Q[l];14(e.51||e.2s.1M){6s}13 b=a-e.2p,2k=e.2k,5S=e.5S,2b=e.2b,1t=e.1t,1L=e.1L,t=e.t,2O=e.2s,3Y={},2g;14(b<2k){13 c=5S(b/2k);1h(13 d 1x 2b)14(2b[1v](d)){2U(65[d]){1f"3Q":2g=c*2k*1t[d];1L.5b&&(2g=1L.39-2g);13 f=5a(1L[d],2g);2O.3v(1t.34-1t.x||0,1t.35-1t.y||0);1t.x=f.x;1t.y=f.y;2O.3v(f.x-1t.34,f.y-1t.35);1L.3w&&2O.2l(1t.r+f.3J,f.x,f.y);1d;1f 2t:2g=+2b[d]+c*2k*1t[d];1d;1f"66":2g="23("+[6U(2j(2b[d].r+c*2k*1t[d].r)),6U(2j(2b[d].g+c*2k*1t[d].g)),6U(2j(2b[d].b+c*2k*1t[d].b))][29](",")+")";1d;1f"1o":2g=[];1h(13 i=0,1k=2b[d][19];i<1k;i++){2g[i]=[2b[d][i][0]];1h(13 j=1,2d=2b[d][i][19];j<2d;j++){2g[i][j]=+2b[d][i][j]+c*2k*1t[d][i][j]}2g[i]=2g[i][29](S)}2g=2g[29](S);1d;1f"4S":2U(d){1f"3o":13 x=c*2k*1t[d][0]-t.x,y=c*2k*1t[d][1]-t.y;t.x+=x;t.y+=y;2g=x+S+y;1d;1f"2Y":2g=+2b[d][0]+c*2k*1t[d][0];2b[d][1]&&(2g+=","+2b[d][1]+","+2b[d][2]);1d;1f"1U":2g=[+2b[d][0]+c*2k*1t[d][0],+2b[d][1]+c*2k*1t[d][1],(2 1x 1L[d]?1L[d][2]:E),(3 1x 1L[d]?1L[d][3]:E)][29](S);1d;1f"1T-1Z":2g=[];i=4;2B(i--){2g[i]=+2b[d][i]+c*2k*1t[d][i]}1d}1d;3c:13 g=[].20(2b[d]);2g=[];i=2O.1p.3k[d].19;2B(i--){2g[i]=+g[i]+c*2k*1t[d][i]}1d}3Y[d]=2g}2O.1X(3Y);2O.8e&&2O.8e.1K(2O)}1j{14(1L.3Q){f=5a(1L.3Q,1L.39*!1L.5b);2O.3v(1t.34-(1t.x||0)+f.x-1t.34,1t.35-(1t.y||0)+f.y-1t.35);1L.3w&&2O.2l(1t.r+f.3J,f.x,f.y)}(t.x||t.y)&&2O.3v(-t.x,-t.y);1L.1U&&(1L.1U+=E);2O.1X(1L);Q.3h(l--,1)}}R.4u&&2O&&2O.1p&&2O.1p.6u();Q[19]&&4J(8d)},a1=17(a,b,c,d,e){13 f=c-d;b.4n.1F(4J(17(){R.1q(e,"17")&&e.1K(b);b.5c(a,f,a.5S)},d))},6U=17(a){15 2H(3a(a,3F),0)},3v=17(x,y){14(x==1g){15{x:11.1u.5D,y:11.1u.5E,2h:84}}11.1u.5D+=+x;11.1u.5E+=+y;2U(11.1n){1f"3i":1f"3j":11.1X({cx:+x+11.18.cx,cy:+y+11.18.cy});1d;1f"1Z":1f"2X":1f"1R":11.1X({x:+x+11.18.x,y:+y+11.18.y});1d;1f"1o":13 a=5u(11.18.1o);a[0][1]+=+x;a[0][2]+=+y;11.1X({1o:a});1d}15 11};I.a2=17(a,b,c,d,e){1h(13 i=0,1k=Q.19;i<1k;i++){14(Q[i].2s.2z==a.2z){b.2p=Q[i].2p}}15 11.5c(b,c,d,e)};I.dg=3Q();I.dh=3Q(1);17 3Q(f){15 17(a,b,c,d){13 e={5b:f};R.1q(c,"17")?(d=c):(e.3w=c);a&&a.4I==1Q&&(a=a.18.1o);a&&(e.3Q=a);15 11.5c(e,b,d)}}17 a3(t,c,d,e,f,g){13 h=3*c,bx=3*(e-c)-h,ax=1-h-bx,cy=3*d,by=3*(f-d)-cy,ay=1-cy-by;17 8f(t){15((ax*t+bx)*t+h)*t}17 a4(x,a){13 t=a5(x,a);15((ay*t+by)*t+cy)*t}17 a5(x,a){13 b,22,1I,4i,d2,i;1h(1I=x,i=0;i<8;i++){4i=8f(1I)-x;14(2o(4i)<a){15 1I}d2=(3*ax*1I+2*bx)*1I+h;14(2o(d2)<1e-6){1d}1I=1I-4i/d2}b=0;22=1;1I=x;14(1I<b){15 b}14(1I>22){15 22}2B(b<22){4i=8f(1I);14(2o(4i-x)<a){15 1I}14(x>4i){b=1I}1j{22=1I}1I=(22-b)/2+b}15 1I}15 a4(t,1/(di*g))}I.dj=17(f){11.8e=f||0;15 11};I.5c=17(a,b,c,d){13 e=11;e.4n=e.4n||[];14(R.1q(c,"17")||!c){d=c||1g}14(e.1M){d&&d.1K(e);15 e}13 f={},1L={},8g=3W,1t={};1h(13 g 1x a)14(a[1v](g)){14(65[1v](g)||e.1p.3k[1v](g)){8g=2F;f[g]=e.1X(g);(f[g]==1g)&&(f[g]=5g[g]);1L[g]=a[g];2U(65[g]){1f"3Q":13 h=P(a[g]);13 k=5a(a[g],h*!!a.5b);13 l=e.3T();1t[g]=h/b;1t.5D=l.x;1t.5E=l.y;1t.34=k.x;1t.35=k.y;1L.3w=a.3w;1L.5b=a.5b;1L.39=h;a.3w&&(1t.r=1E(e.2l())||0);1d;1f 2t:1t[g]=(1L[g]-f[g])/b;1d;1f"66":f[g]=R.3t(f[g]);13 m=R.3t(1L[g]);1t[g]={r:(m.r-f[g].r)/b,g:(m.g-f[g].g)/b,b:(m.b-f[g].b)/b};1d;1f"1o":13 n=5t(f[g],1L[g]);f[g]=n[0];13 o=n[1];1t[g]=[];1h(13 i=0,1k=f[g][19];i<1k;i++){1t[g][i]=[0];1h(13 j=1,2d=f[g][i][19];j<2d;j++){1t[g][i][j]=(o[i][j]-f[g][i][j])/b}}1d;1f"4S":13 p=1G(a[g])[21](z),5d=1G(f[g])[21](z);2U(g){1f"3o":f[g]=[0,0];1t[g]=[p[0]/b,p[1]/b];1d;1f"2Y":f[g]=(5d[1]==p[1]&&5d[2]==p[2])?5d:[0,p[1],p[2]];1t[g]=[(p[0]-f[g][0])/b,0,0];1d;1f"1U":a[g]=p;f[g]=1G(f[g])[21](z);1t[g]=[(p[0]-f[g][0])/b,(p[1]-f[g][1])/b,0,0];1d;1f"1T-1Z":f[g]=1G(f[g])[21](z);1t[g]=[];i=4;2B(i--){1t[g][i]=(p[i]-f[g][i])/b}1d}1L[g]=p;1d;3c:p=[].20(a[g]);5d=[].20(f[g]);1t[g]=[];i=e.1p.3k[g][19];2B(i--){1t[g][i]=((p[i]||0)-(5d[i]||0))/b}1d}}}14(!8g){13 q=[],6V;1h(13 r 1x a)14(a[1v](r)&&8F.a6(r)){g={56:a[r]};r=="2b"&&(r=0);r=="1L"&&(r=2N);g.4s=3C(r,10);q.1F(g)}q.dk(8L);14(q[0].4s){q.dl({4s:0,56:e.18})}1h(i=0,1k=q[19];i<1k;i++){a1(q[i].56,e,b/2N*q[i].4s,b/2N*(q[i-1]&&q[i-1].4s||0),q[i-1]&&q[i-1].56.a7)}6V=q[q[19]-1].56.a7;14(6V){e.4n.1F(4J(17(){6V.1K(e)},b))}}1j{13 s=R.9Y[c];14(!s){s=1G(c).43(8y);14(s&&s[19]==5){13 u=s;s=17(t){15 a3(t,+u[1],+u[2],+u[3],+u[4],b)}}1j{s=17(t){15 t}}}Q.1F({2p:a.2p||+2c a0,2k:b,5S:s,2b:f,1t:1t,1L:1L,2s:e,t:{x:0,y:0}});R.1q(d,"17")&&(e.8h=4J(17(){d.1K(e)},b));Q[19]==1&&4J(8d)}15 11};I.51=17(){1h(13 i=0;i<Q.19;i++){Q[i].2s.2z==11.2z&&Q.3h(i--,1)}1h(i=0,1k=11.4n&&11.4n.19;i<1k;i++){8c(11.4n[i])}11.4n=[];8c(11.8h);2x 11.8h;15 11};I.3v=17(x,y){15 11.1X({3o:x+" "+y})};I[2h]=17(){15"4U\\4V\\dm 3d"};R.ae=Q;13 T=17(a){11.28=[];11[19]=0;11.1n="3Y";14(a){1h(13 i=0,1k=a[19];i<1k;i++){14(a[i]&&(a[i].4I==1Q||a[i].4I==T)){11[11.28[19]]=11.28[11.28[19]]=a[i];11[19]++}}}};T[1J][1F]=17(){13 a,39;1h(13 i=0,1k=2Q[19];i<1k;i++){a=2Q[i];14(a&&(a.4I==1Q||a.4I==T)){39=11.28[19];11[39]=11.28[39]=a;11[19]++}}15 11};T[1J].a8=17(){2x 11[11[19]--];15 11.28.a8()};1h(13 U 1x I)14(I[1v](U)){T[1J][U]=(17(a){15 17(){1h(13 i=0,1k=11.28[19];i<1k;i++){11.28[i][a][24](11.28[i],2Q)}15 11}})(U)}T[1J].1X=17(a,b){14(a&&R.1q(a,2A)&&R.1q(a[0],"3d")){1h(13 j=0,2d=a[19];j<2d;j++){11.28[j].1X(a[j])}}1j{1h(13 i=0,1k=11.28[19];i<1k;i++){11.28[i].1X(a,b)}}15 11};T[1J].5c=17(a,b,c,d){(R.1q(c,"17")||!c)&&(d=c||1g);13 e=11.28[19],i=e,8i,3Y=11,5T;d&&(5T=17(){!--e&&d.1K(3Y)});c=R.1q(c,2R)?c:5T;8i=11.28[--i].5c(a,b,c,5T);2B(i--){11.28[i]&&!11.28[i].1M&&11.28[i].a2(8i,a,b,c,5T)}15 11};T[1J].6x=17(a){13 i=11.28[19];2B(i--){11.28[i].6x(a)}15 11};T[1J].3T=17(){13 x=[],y=[],w=[],h=[];1h(13 i=11.28[19];i--;){13 a=11.28[i].3T();x[1F](a.x);y[1F](a.y);w[1F](a.x+a.1a);h[1F](a.y+a.1c)}x=3a[24](0,x);y=3a[24](0,y);15{x:x,y:y,1a:2H[24](0,w)-x,1c:2H[24](0,h)-y}};T[1J].8a=17(s){s=2c T;1h(13 i=0,1k=11.28[19];i<1k;i++){s[1F](11.28[i].8a())}15 s};R.dn=17(b){14(!b.2P){15 b}11.3X=11.3X||{};13 c={w:b.w,2P:{},4o:{}},3O=b.2P["1A-3O"];1h(13 d 1x b.2P)14(b.2P[1v](d)){c.2P[d]=b.2P[d]}14(11.3X[3O]){11.3X[3O][1F](c)}1j{11.3X[3O]=[c]}14(!b.4u){c.2P["8j-8k-8l"]=3C(b.2P["8j-8k-8l"],10);1h(13 e 1x b.4o)14(b.4o[1v](e)){13 f=b.4o[e];c.4o[e]={w:f.w,k:{},d:f.d&&"M"+f.d[2v](/[do]/g,17(a){15{l:"L",c:"C",x:"z",t:"m",r:"l",v:"c"}[a]||"M"})+"z"};14(f.k){1h(13 k 1x f.k)14(f[1v](k)){c.4o[e].k[k]=f.k[k]}}}}15 b};27.a9=17(a,b,c,d){d=d||"62";c=c||"62";b=+b||{62:78,dp:dq,dr:ds,dt:du}[b]||78;14(!R.3X){15}13 e=R.3X[a];14(!e){13 f=2c dv("(^|\\\\s)"+a[2v](/[^\\w\\d\\s+!~.:1u-]/g,E)+"(\\\\s|$)","i");1h(13 g 1x R.3X)14(R.3X[1v](g)){14(f.a6(g)){e=R.3X[g];1d}}}13 h;14(e){1h(13 i=0,1k=e[19];i<1k;i++){h=e[i];14(h.2P["1A-4a"]==b&&(h.2P["1A-1s"]==c||!h.2P["1A-1s"])&&h.2P["1A-dw"]==d){1d}}}15 h};27.dx=17(x,y,a,b,c,d,e){d=d||"64";e=2H(3a(e||0,1),-1);13 f=11.3Y(),5U=1G(a)[21](E),4w=0,1o=E,1U;R.1q(b,a)&&(b=11.a9(b));14(b){1U=(c||16)/b.2P["8j-8k-8l"];13 g=b.2P.dz.21(z),1w=+g[0],1c=+g[1]+(d=="dA"?g[3]-g[1]+(+b.2P.dB):(g[3]-g[1])/2);1h(13 i=0,1k=5U[19];i<1k;i++){13 h=i&&b.4o[5U[i-1]]||{},6W=b.4o[5U[i]];4w+=i?(h.w||b.w)+(h.k&&h.k[5U[i]]||0)+(b.w*e):0;6W&&6W.d&&f[1F](11.1o(6W.d).1X({1l:"#3E",1i:"1P",3o:[4w,0]}))}f.1U(1U,1U,1w,1c).3v(x-1w,y-1c)}15 f};R.3L=17(b,c){13 d=R.1q(c,2A)?[0][20](c):2Q;b&&R.1q(b,2R)&&d[19]-1&&(b=b[2v](8n,17(a,i){15 d[++i]==1g?E:d[i]}));15 b||E};R.dC=17(){5X.6Z?(3N.4p=5X.1q):2x 4p;15 R};R.2s=I;R.dD=T[1J];5X.6Z?(3N.4p=R):(4p=R)})();',62,846,'|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||this||var|if|return||function|attrs|length|width|node|height|break||case|null|for|stroke|else|ii|fill|opacity|type|path|paper|is|appendChild|style|diff|_|has|top|in|res|doc|font|px|pa|values|toFloat|push|Str|math|t2|proto|call|to|removed|parentNode|rt|none|Element|text|canvas|clip|scale|container|pow|attr|slice|rect|concat|split|t1|rgb|apply||point|paperproto|items|join|gradient|from|new|jj|p2|Group|now|toString|deg|round|ms|rotate|f2|dot|abs|start|xy|left|el|nu|PI|rp|cache|delete|next|id|array|while|end|f1|fy|true|dragi|mmax|prev|os|setAttribute|blur|color|100|that|face|arguments|string|firstChild|red|switch|bottom|drag|image|rotation|mx|my|fx||removeChild|sx|sy||coordsize||len|mmin|min|default|object|green|blue|coordorigin|splice|circle|ellipse|customAttributes|url|rx|ry|translation|shape|180|body|cacher|getRGB|name|translate|rot|insertBefore|position|on|span|fillString|toInt|src|000|255|hex|t3|data|alpha|offset|format|absolute|win|family|dasharray|along|hue|attrs2|getBBox|cssText|transformations|false|fonts|set|lowerCase|size|createElement|display|match|sin|filter|group|dirx|diry|events|weight|href|linecap|360|args|charAt|sqrt|defs|x2|rc|ol|setBox|_drag|timeouts|glyphs|Raphael|max|target|key|VML|svg|1e3|shift|ny|toFixed|cos|m2|qx|qy|tear|xlink|getElementsByTagName|zoom|dstyle|constructor|setTimeout|preventDefault|kx|ky|sp|toLowerCase|cursor|butt|title|csv|rad|Rapha|xebl|hsl|newf|count|pathToAbsolute|seg2|stop|pattern|9999em|pathlike||value|rvml|originalEvent|stopPropagation|getPointAtLength|back|animate|from2|Paper|upperCase|availableAttrs|http|linejoin|miterlimit|vml|finite|createUUID|channels|_path2string|error|substring|getColor|pathClone|path2curve|pathToRelative|l2c|_23|findDotAtSegment|seglen|seg2len|linear|setAttributeNS|fontSize|tx|ty|transform|remove|_blur|clipRect|filterMatrix|scrollX|touch|ncx|ncy|P0|getPointAtSegmentLength|precision|subpaths|easing|collector|letters|create|version|oldRaphael|supportsTouch|touchMap||xb0|normal|anchor|middle|availableAnimAttrs|colour|commaSpaces|www|w3|org|SVG|Array|angle|try|catch|parsePathString|nx|kk|ry2|1e12|par|getElementById|offsetWidth|offsetHeight|plugins|radial|isNaN|continue|show|safari|undefined|dif|insertAfter|nextSibling|setSize|command|filterOpacity|fxfy|margin|targetTouches|dragMove|clientX|clientY|documentElement|scrollTop|scrollLeft|move|dragUp|rcx|rcy|newh|old|getLengthFactory|getSubpathsAtLength|getTotalLength|upto255|lastcall|curr|cnv|document|was|Object|mousedown|mousemove|mouseup|ISURL||rgba|Infinity|400|com|ig|radial_gradient|div|innerHTML|typeof|_oid|fn|hsbtoString|hsb2rgb|hsl2rgb|saturation|findDotsAtSegment|ymin|q2c|a2c|hx|hy|m1|m3|m4|processPath|fixArc|fixM|parseDots|getContainer|tofront|toback|insertafter|insertbefore|throw|Error|are|2000|getAttribute|addDashes|square|dashes|raphael|hide|theRect|theEllipse|theImage|theText|clear|desc|clmz|newpath|rectPath|newfill|newstroke|align|bbx|textpath|inline|navigator|move_scope|x_y|posy|dkx|dky|fr|skip|clone|timer|clearTimeout|animation|_run|sampleCurveX|animateable|_ac|item|units|per|em|elements|formatrg|mouseout|mouseover|touchstart|touchmove|touchend|objectToString|colourRegExp|hsba|hsla|isnan|bezierrg|progid|1e9|fff|Arial|M0|replace|animKeyFrames|hsrg|p2s|gi|pathCommand|pathValues|sortByKey|adj|behavior|_id|isArray|atan|defaultView|getComputedStyle|getPropertyValue|hsltoString|rgbtoString|16777216|brightness|curveDim|asin||x2old|y2old|s1|s2|tagName|method|svgns|Your||browser|nYou|running|blank|clipPath|tuneText|tspan|createTextNode|toFront|toBack|not|found|512|342|xmlns|bites|blurregexp|Blur|val|path2vml|isMove|fontFamily|fontWeight|fontStyle|colors|block|oval|0a|l0|namespaces|urn|schemas|microsoft|class|padding|line|preventTouch|stopTouch|addEvent|addEventListener|attachEvent|scrollY|touches|identifier|unmousemove|unmouseup|end_scope|start_scope|unbind|un|easing_formulas|70158|Date|keyframesRun|animateWith|CubicBezierAtTime|solve|solveCurveX|test|callback|pop|getFont|prototype|hasOwnProperty|window|createTouch||String|click|dblclick|orientationchange|touchcancel|gesturestart|gesturechange|gestureend|Math|number|NaN|cubic|bezier|parseFloat|parseInt|DXImageTransform|Microsoft|toUpperCase|||10px|raphaeljs|_blank|hs|rg|achlmqrstvxz|achlmqstvz|SVGAngle|implementation|hasFeature|TR|SVG11|feature|BasicStructure|snapTo|random|setWindow|ActiveXObject|htmlfile|write|close|createPopup|createTextRange|queryCommandValue|ForeColor|65280|16711680|000000|Colour|Picker|hsb|rgb2hsb|rgb2hsl|u25ba|indexOf|075|reset|120|tan|pixelWidth|pixelHeight|you|calling|u201c|u201d|of|1999|createElementNS|webkitTapHighlightColor|supports|Gradient|x1|y1|y2||fillOpacity|patternTransform|strokeWidth|patternUnits|userSpaceOnUse|||img|onload|finally|getNumberOfChars|getExtentOfChar|feGaussianBlur|stdDeviation|removeAttribute|preserveAspectRatio|Created|with|ahqstv|doesn|u2019t|support|Falling|down|Path|Alpha|tile|solid|joinstyle|miter|endcap|flat|shortdash|shortdot|shortdashdot|shortdashdotdot||dash|longdash|dashdot|longdashdot|longdashdotdot|dashstyle|right|center|color2|gradientradial|focus|focussize|focusposition|270|RotX|RotY|Scale|runtimeStyle|pixelradius|0z|ps|textpathok|createStyleSheet|addRule|add||relative|overflow|hidden|userAgent|||Version|vendor|Apple|Computer|Inc|platform|iP|returnValue|cancelBubble|removeEventListener|event|detachEvent|hover|unhover|unmouseover|unmouseout|undrag|about|resetScale|dcx|dcy|Matrix|M11|M12|M21|M22|Dx|Dy|sizingmethod||auto|expand|filtertype|bilinear|getSubpath|backIn|backOut|elastic|bounce|5625|9375|625|984375|animateAlong|animateAlongBack|200|onAnimation|sort|unshift|u2019s|registerFont|mlcxtrv|bold|700|lighter|300|bolder|800|RegExp|stretch|print||bbox|baseline|descent|ninja|st'.split('|'),0,{}))


Array.prototype.shuffle = function (){ for(var rnd, tmp, i=this.length; i; rnd=parseInt(Math.random()*i), tmp=this[--i], this[i]=this[rnd], this[rnd]=tmp); };

/**
 * jQuery Validation Plugin 1.8.1
 *
 * http://bassistance.de/jquery-plugins/jquery-plugin-validation/
 * http://docs.jquery.com/Plugins/Validation
 *
 * Copyright (c) 2006 - 2011 Jörn Zaefferer
 *
 * Dual licensed under the MIT and GPL licenses:
 *   http://www.opensource.org/licenses/mit-license.php
 *   http://www.gnu.org/licenses/gpl.html
 */

//changed line 349
// bug http://plugins.jquery.com/content/validation-checkbox-group-causes-incorrect-registration-valid-and-invalid-elements
//changed line 319
// bug http://plugins.jquery.com/content/support-html-5-input-types-number-email-url
eval(function(p,a,c,k,e,r){e=function(c){return(c<a?'':e(parseInt(c/a)))+((c=c%a)>35?String.fromCharCode(c+29):c.toString(36))};if(!''.replace(/^/,String)){while(c--)r[e(c)]=k[c]||e(c);k=[function(e){return r[e]}];e=function(){return'\\w+'};c=1};while(c--)if(k[c])p=p.replace(new RegExp('\\b'+e(c)+'\\b','g'),k[c]);return p}('(7($){$.K($.2E,{16:7(c){l(!6.F){c&&c.28&&2F.1x&&1x.4F("3k 3l, 4G\'t 16, 4H 3k");8}p d=$.17(6[0],\'v\');l(d){8 d}d=29 $.v(c,6[0]);$.17(6[0],\'v\',d);l(d.q.3m){6.2G("1y, 3n").1z(".4I").2H(7(){d.2I=w});l(d.q.2J){6.2G("1y, 3n").1z(":2a").2H(7(){d.1O=6})}6.2a(7(b){l(d.q.28)b.4J();7 1P(){l(d.q.2J){l(d.1O){p a=$("<1y 12=\'4K\'/>").1j("u",d.1O.u).2K(d.1O.Y).4L(d.V)}d.q.2J.Z(d,d.V);l(d.1O){a.3o()}8 N}8 w}l(d.2I){d.2I=N;8 1P()}l(d.L()){l(d.1a){d.1k=w;8 N}8 1P()}W{d.2b();8 N}})}8 d},H:7(){l($(6[0]).2L(\'L\')){8 6.16().L()}W{p a=w;p b=$(6[0].L).16();6.P(7(){a&=b.I(6)});8 a}},4M:7(c){p d={},$I=6;$.P(c.1Q(/\\s/),7(a,b){d[b]=$I.1j(b);$I.4N(b)});8 d},1b:7(c,d){p e=6[0];l(c){p f=$.17(e.L,\'v\').q;p g=f.1b;p h=$.v.2M(e);2c(c){1c"1l":$.K(h,$.v.1R(d));g[e.u]=h;l(d.G)f.G[e.u]=$.K(f.G[e.u],d.G);2N;1c"3o":l(!d){Q g[e.u];8 h}p i={};$.P(d.1Q(/\\s/),7(a,b){i[b]=h[b];Q h[b]});8 i}}p j=$.v.3p($.K({},$.v.3q(e),$.v.3r(e),$.v.3s(e),$.v.2M(e)),e);l(j.13){p k=j.13;Q j.13;j=$.K({13:k},j)}8 j}});$.K($.4O[":"],{4P:7(a){8!$.1m(""+a.Y)},4Q:7(a){8!!$.1m(""+a.Y)},4R:7(a){8!a.3t}});$.v=7(a,b){6.q=$.K(w,{},$.v.2O,a);6.V=b;6.3u()};$.v.11=7(b,c){l(R.F==1)8 7(){p a=$.3v(R);a.4S(b);8 $.v.11.1S(6,a)};l(R.F>2&&c.2d!=3w){c=$.3v(R).4T(1)}l(c.2d!=3w){c=[c]}$.P(c,7(i,n){b=b.1A(29 3x("\\\\{"+i+"\\\\}","g"),n)});8 b};$.K($.v,{2O:{G:{},2e:{},1b:{},1d:"3y",2f:"H",2P:"4U",2b:w,3z:$([]),2Q:$([]),3m:w,2R:[],3A:N,4V:7(a){6.3B=a;l(6.q.4W&&!6.4X){6.q.1T&&6.q.1T.Z(6,a,6.q.1d,6.q.2f);6.2g(6.1U(a)).2S()}},4Y:7(a){l(!6.1n(a)&&(a.u S 6.1e||!6.J(a))){6.I(a)}},4Z:7(a){l(a.u S 6.1e||a==6.3C){6.I(a)}},50:7(a){l(a.u S 6.1e)6.I(a);W l(a.3D.u S 6.1e)6.I(a.3D)},2T:7(a,b,c){l(a.12===\'2h\'){6.1o(a.u).1p(b).1B(c)}W{$(a).1p(b).1B(c)}},1T:7(a,b,c){l(a.12===\'2h\'){6.1o(a.u).1B(b).1p(c)}W{$(a).1B(b).1p(c)}}},51:7(a){$.K($.v.2O,a)},G:{13:"52 3E 2L 13.",1q:"M 2U 6 3E.",1C:"M O a H 1C 53.",1r:"M O a H 54.",1D:"M O a H 1D.",2i:"M O a H 1D (55).",1E:"M O a H 1E.",1V:"M O 56 1V.",2j:"M O a H 57 58 1E.",2k:"M O 3F 59 Y 5a.",3G:"M O a Y 5b a H 5c.",18:$.v.11("M O 3H 5d 2V {0} 2W."),1F:$.v.11("M O 5e 5f {0} 2W."),2l:$.v.11("M O a Y 3I {0} 3J {1} 2W 5g."),2m:$.v.11("M O a Y 3I {0} 3J {1}."),1G:$.v.11("M O a Y 5h 2V 3K 3L 3M {0}."),1H:$.v.11("M O a Y 5i 2V 3K 3L 3M {0}.")},3N:N,5j:{3u:7(){6.2n=$(6.q.2Q);6.3O=6.2n.F&&6.2n||$(6.V);6.2o=$(6.q.3z).1l(6.q.2Q);6.1e={};6.5k={};6.1a=0;6.1f={};6.1g={};6.1W();p e=(6.2e={});$.P(6.q.2e,7(c,d){$.P(d.1Q(/\\s/),7(a,b){e[b]=c})});p f=6.q.1b;$.P(f,7(a,b){f[a]=$.v.1R(b)});7 2X(a){p b=$.17(6[0].L,"v"),2Y="5l"+a.12.1A(/^16/,"");b.q[2Y]&&b.q[2Y].Z(b,6[0])}$(6.V).2Z(":3P, [12=1C], [12=5m], [12=1r], :5n, :5o, 1X, 3Q","2p 30 5p",2X).2Z(":2h, :3R, 1X, 3S","2H",2X);l(6.q.3T)$(6.V).31("1g-L.16",6.q.3T)},L:7(){6.3U();$.K(6.1e,6.1I);6.1g=$.K({},6.1I);l(!6.H())$(6.V).3V("1g-L",[6]);6.1s();8 6.H()},3U:7(){6.32();T(p i=0,19=(6.2q=6.19());19[i];i++){6.2r(19[i])}8 6.H()},I:7(a){a=6.33(a);6.3C=a;6.34(a);6.2q=$(6.1n(a)?6.1o(a.u)[0]:a);p b=6.2r(a);l(b){Q 6.1g[a.u]}W{6.1g[a.u]=w}l(!6.3W()){6.14=6.14.1l(6.2o)}6.1s();8 b},1s:7(b){l(b){$.K(6.1I,b);6.U=[];T(p c S b){6.U.2s({1t:b[c],I:6.1o(c)[0]})}6.1u=$.3X(6.1u,7(a){8!(a.u S b)})}6.q.1s?6.q.1s.Z(6,6.1I,6.U):6.3Y()},35:7(){l($.2E.35)$(6.V).35();6.1e={};6.32();6.36();6.19().1B(6.q.1d)},3W:7(){8 6.2t(6.1g)},2t:7(a){p b=0;T(p i S a)b++;8 b},36:7(){6.2g(6.14).2S()},H:7(){8 6.3Z()==0},3Z:7(){8 6.U.F},2b:7(){l(6.q.2b){40{$(6.41()||6.U.F&&6.U[0].I||[]).1z(":5q").42().5r("2p")}43(e){}}},41:7(){p a=6.3B;8 a&&$.3X(6.U,7(n){8 n.I.u==a.u}).F==1&&a},19:7(){p a=6,37={};8 $(6.V).2G("1y, 1X, 3Q").1J(":2a, :1W, :5s, [5t]").1J(6.q.2R).1z(7(){!6.u&&a.q.28&&2F.1x&&1x.3y("%o 5u 3H u 5v",6);l(6.u S 37||!a.2t($(6).1b()))8 N;37[6.u]=w;8 w})},33:7(a){8 $(a)[0]},38:7(){8 $(6.q.2P+"."+6.q.1d,6.3O)},1W:7(){6.1u=[];6.U=[];6.1I={};6.1v=$([]);6.14=$([]);6.2q=$([])},32:7(){6.1W();6.14=6.38().1l(6.2o)},34:7(a){6.1W();6.14=6.1U(a)},2r:7(a){a=6.33(a);l(6.1n(a)){a=6.1o(a.u).1J(6.q.2R)[0]}p b=$(a).1b();p c=N;T(p d S b){p f={2u:d,2v:b[d]};40{p g=$.v.1Y[d].Z(6,a.Y.1A(/\\r/g,""),a,f.2v);l(g=="1Z-20"){c=w;5w}c=N;l(g=="1f"){6.14=6.14.1J(6.1U(a));8}l(!g){6.44(a,f);8 N}}43(e){6.q.28&&2F.1x&&1x.5x("5y 5z 5A 5B I "+a.45+", 2r 3F \'"+f.2u+"\' 2u",e);5C e;}}l(c)8;l(6.2t(b))6.1u.2s(a);8 w},46:7(a,b){l(!$.1K)8;p c=6.q.39?$(a).1K()[6.q.39]:$(a).1K();8 c&&c.G&&c.G[b]},47:7(a,b){p m=6.q.G[a];8 m&&(m.2d==48?m:m[b])},49:7(){T(p i=0;i<R.F;i++){l(R[i]!==21)8 R[i]}8 21},2w:7(a,b){8 6.49(6.47(a.u,b),6.46(a,b),!6.q.3A&&a.5D||21,$.v.G[b],"<4a>5E: 5F 1t 5G T "+a.u+"</4a>")},44:7(a,b){p c=6.2w(a,b.2u),3a=/\\$?\\{(\\d+)\\}/g;l(1h c=="7"){c=c.Z(6,b.2v,a)}W l(3a.15(c)){c=1L.11(c.1A(3a,\'{$1}\'),b.2v)}6.U.2s({1t:c,I:a});6.1I[a.u]=c;6.1e[a.u]=c},2g:7(a){l(6.q.2x)a=a.1l(a.4b(6.q.2x));8 a},3Y:7(){T(p i=0;6.U[i];i++){p a=6.U[i];6.q.2T&&6.q.2T.Z(6,a.I,6.q.1d,6.q.2f);6.3b(a.I,a.1t)}l(6.U.F){6.1v=6.1v.1l(6.2o)}l(6.q.1M){T(p i=0;6.1u[i];i++){6.3b(6.1u[i])}}l(6.q.1T){T(p i=0,19=6.4c();19[i];i++){6.q.1T.Z(6,19[i],6.q.1d,6.q.2f)}}6.14=6.14.1J(6.1v);6.36();6.2g(6.1v).4d()},4c:7(){8 6.2q.1J(6.4e())},4e:7(){8 $(6.U).4f(7(){8 6.I})},3b:7(a,b){p c=6.1U(a);l(c.F){c.1B().1p(6.q.1d);c.1j("4g")&&c.4h(b)}W{c=$("<"+6.q.2P+"/>").1j({"T":6.3c(a),4g:w}).1p(6.q.1d).4h(b||"");l(6.q.2x){c=c.2S().4d().5H("<"+6.q.2x+"/>").4b()}l(!6.2n.5I(c).F)6.q.4i?6.q.4i(c,$(a)):c.5J(a)}l(!b&&6.q.1M){c.3P("");1h 6.q.1M=="1N"?c.1p(6.q.1M):6.q.1M(c)}6.1v=6.1v.1l(c)},1U:7(a){p b=6.3c(a);8 6.38().1z(7(){8 $(6).1j(\'T\')==b})},3c:7(a){8 6.2e[a.u]||(6.1n(a)?a.u:a.45||a.u)},1n:7(a){8/2h|3R/i.15(a.12)},1o:7(c){p d=6.V;8 $(4j.5K(c)).4f(7(a,b){8 b.L==d&&b.u==c&&b||4k})},22:7(a,b){2c(b.4l.4m()){1c\'1X\':8 $("3S:3l",b).F;1c\'1y\':l(6.1n(b))8 6.1o(b.u).1z(\':3t\').F}8 a.F},4n:7(a,b){8 6.3d[1h a]?6.3d[1h a](a,b):w},3d:{"5L":7(a,b){8 a},"1N":7(a,b){8!!$(a,b.L).F},"7":7(a,b){8 a(b)}},J:7(a){8!$.v.1Y.13.Z(6,$.1m(a.Y),a)&&"1Z-20"},4o:7(a){l(!6.1f[a.u]){6.1a++;6.1f[a.u]=w}},4p:7(a,b){6.1a--;l(6.1a<0)6.1a=0;Q 6.1f[a.u];l(b&&6.1a==0&&6.1k&&6.L()){$(6.V).2a();6.1k=N}W l(!b&&6.1a==0&&6.1k){$(6.V).3V("1g-L",[6]);6.1k=N}},2y:7(a){8 $.17(a,"2y")||$.17(a,"2y",{3e:4k,H:w,1t:6.2w(a,"1q")})}},23:{13:{13:w},1C:{1C:w},1r:{1r:w},1D:{1D:w},2i:{2i:w},4q:{4q:w},1E:{1E:w},4r:{4r:w},1V:{1V:w},2j:{2j:w}},4s:7(a,b){a.2d==48?6.23[a]=b:$.K(6.23,a)},3r:7(a){p b={};p c=$(a).1j(\'5M\');c&&$.P(c.1Q(\' \'),7(){l(6 S $.v.23){$.K(b,$.v.23[6])}});8 b},3s:7(a){p b={};p c=$(a);T(p d S $.v.1Y){p e=c.1j(d);l(e){b[d]=e}}l(b.18&&/-1|5N|5O/.15(b.18)){Q b.18}8 b},3q:7(a){l(!$.1K)8{};p b=$.17(a.L,\'v\').q.39;8 b?$(a).1K()[b]:$(a).1K()},2M:7(a){p b={};p c=$.17(a.L,\'v\');l(c.q.1b){b=$.v.1R(c.q.1b[a.u])||{}}8 b},3p:7(d,e){$.P(d,7(a,b){l(b===N){Q d[a];8}l(b.3f||b.2z){p c=w;2c(1h b.2z){1c"1N":c=!!$(b.2z,e.L).F;2N;1c"7":c=b.2z.Z(e,e);2N}l(c){d[a]=b.3f!==21?b.3f:w}W{Q d[a]}}});$.P(d,7(a,b){d[a]=$.4t(b)?b(e):b});$.P([\'1F\',\'18\',\'1H\',\'1G\'],7(){l(d[6]){d[6]=3g(d[6])}});$.P([\'2l\',\'2m\'],7(){l(d[6]){d[6]=[3g(d[6][0]),3g(d[6][1])]}});l($.v.3N){l(d.1H&&d.1G){d.2m=[d.1H,d.1G];Q d.1H;Q d.1G}l(d.1F&&d.18){d.2l=[d.1F,d.18];Q d.1F;Q d.18}}l(d.G){Q d.G}8 d},1R:7(a){l(1h a=="1N"){p b={};$.P(a.1Q(/\\s/),7(){b[6]=w});a=b}8 a},5P:7(a,b,c){$.v.1Y[a]=b;$.v.G[a]=c!=21?c:$.v.G[a];l(b.F<3){$.v.4s(a,$.v.1R(a))}},1Y:{13:7(a,b,c){l(!6.4n(c,b))8"1Z-20";2c(b.4l.4m()){1c\'1X\':p d=$(b).2K();8 d&&d.F>0;1c\'1y\':l(6.1n(b))8 6.22(a,b)>0;5Q:8 $.1m(a).F>0}},1q:7(f,g,h){l(6.J(g))8"1Z-20";p i=6.2y(g);l(!6.q.G[g.u])6.q.G[g.u]={};i.4u=6.q.G[g.u].1q;6.q.G[g.u].1q=i.1t;h=1h h=="1N"&&{1r:h}||h;l(6.1f[g.u]){8"1f"}l(i.3e===f){8 i.H}i.3e=f;p j=6;6.4o(g);p k={};k[g.u]=f;$.3h($.K(w,{1r:h,2A:"24",1i:"16"+g.u,5R:"5S",17:k,1M:7(a){j.q.G[g.u].1q=i.4u;p b=a===w;l(b){p c=j.1k;j.34(g);j.1k=c;j.1u.2s(g);j.1s()}W{p d={};p e=a||j.2w(g,"1q");d[g.u]=i.1t=$.4t(e)?e(f):e;j.1s(d)}i.H=b;j.4p(g,b)}},h));8"1f"},1F:7(a,b,c){8 6.J(b)||6.22($.1m(a),b)>=c},18:7(a,b,c){8 6.J(b)||6.22($.1m(a),b)<=c},2l:7(a,b,c){p d=6.22($.1m(a),b);8 6.J(b)||(d>=c[0]&&d<=c[1])},1H:7(a,b,c){8 6.J(b)||a>=c},1G:7(a,b,c){8 6.J(b)||a<=c},2m:7(a,b,c){8 6.J(b)||(a>=c[0]&&a<=c[1])},1C:7(a,b){8 6.J(b)||/^((([a-z]|\\d|[!#\\$%&\'\\*\\+\\-\\/=\\?\\^X`{\\|}~]|[\\x-\\y\\A-\\B\\C-\\E])+(\\.([a-z]|\\d|[!#\\$%&\'\\*\\+\\-\\/=\\?\\^X`{\\|}~]|[\\x-\\y\\A-\\B\\C-\\E])+)*)|((\\4v)((((\\2B|\\26)*(\\3i\\4w))?(\\2B|\\26)+)?(([\\4x-\\5T\\4y\\4z\\5U-\\5V\\4A]|\\5W|[\\5X-\\5Y]|[\\5Z-\\60]|[\\x-\\y\\A-\\B\\C-\\E])|(\\\\([\\4x-\\26\\4y\\4z\\3i-\\4A]|[\\x-\\y\\A-\\B\\C-\\E]))))*(((\\2B|\\26)*(\\3i\\4w))?(\\2B|\\26)+)?(\\4v)))@((([a-z]|\\d|[\\x-\\y\\A-\\B\\C-\\E])|(([a-z]|\\d|[\\x-\\y\\A-\\B\\C-\\E])([a-z]|\\d|-|\\.|X|~|[\\x-\\y\\A-\\B\\C-\\E])*([a-z]|\\d|[\\x-\\y\\A-\\B\\C-\\E])))\\.)+(([a-z]|[\\x-\\y\\A-\\B\\C-\\E])|(([a-z]|[\\x-\\y\\A-\\B\\C-\\E])([a-z]|\\d|-|\\.|X|~|[\\x-\\y\\A-\\B\\C-\\E])*([a-z]|[\\x-\\y\\A-\\B\\C-\\E])))\\.?$/i.15(a)},1r:7(a,b){8 6.J(b)||/^(61?|62):\\/\\/(((([a-z]|\\d|-|\\.|X|~|[\\x-\\y\\A-\\B\\C-\\E])|(%[\\27-f]{2})|[!\\$&\'\\(\\)\\*\\+,;=]|:)*@)?(((\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5]))|((([a-z]|\\d|[\\x-\\y\\A-\\B\\C-\\E])|(([a-z]|\\d|[\\x-\\y\\A-\\B\\C-\\E])([a-z]|\\d|-|\\.|X|~|[\\x-\\y\\A-\\B\\C-\\E])*([a-z]|\\d|[\\x-\\y\\A-\\B\\C-\\E])))\\.)+(([a-z]|[\\x-\\y\\A-\\B\\C-\\E])|(([a-z]|[\\x-\\y\\A-\\B\\C-\\E])([a-z]|\\d|-|\\.|X|~|[\\x-\\y\\A-\\B\\C-\\E])*([a-z]|[\\x-\\y\\A-\\B\\C-\\E])))\\.?)(:\\d*)?)(\\/((([a-z]|\\d|-|\\.|X|~|[\\x-\\y\\A-\\B\\C-\\E])|(%[\\27-f]{2})|[!\\$&\'\\(\\)\\*\\+,;=]|:|@)+(\\/(([a-z]|\\d|-|\\.|X|~|[\\x-\\y\\A-\\B\\C-\\E])|(%[\\27-f]{2})|[!\\$&\'\\(\\)\\*\\+,;=]|:|@)*)*)?)?(\\?((([a-z]|\\d|-|\\.|X|~|[\\x-\\y\\A-\\B\\C-\\E])|(%[\\27-f]{2})|[!\\$&\'\\(\\)\\*\\+,;=]|:|@)|[\\63-\\64]|\\/|\\?)*)?(\\#((([a-z]|\\d|-|\\.|X|~|[\\x-\\y\\A-\\B\\C-\\E])|(%[\\27-f]{2})|[!\\$&\'\\(\\)\\*\\+,;=]|:|@)|\\/|\\?)*)?$/i.15(a)},1D:7(a,b){8 6.J(b)||!/65|66/.15(29 67(a))},2i:7(a,b){8 6.J(b)||/^\\d{4}[\\/-]\\d{1,2}[\\/-]\\d{1,2}$/.15(a)},1E:7(a,b){8 6.J(b)||/^-?(?:\\d+|\\d{1,3}(?:,\\d{3})+)(?:\\.\\d+)?$/.15(a)},1V:7(a,b){8 6.J(b)||/^\\d+$/.15(a)},2j:7(a,b){l(6.J(b))8"1Z-20";l(/[^0-9-]+/.15(a))8 N;p c=0,e=0,2C=N;a=a.1A(/\\D/g,"");T(p n=a.F-1;n>=0;n--){p d=a.68(n);p e=69(d,10);l(2C){l((e*=2)>9)e-=9}c+=e;2C=!2C}8(c%10)==0},3G:7(a,b,c){c=1h c=="1N"?c.1A(/,/g,\'|\'):"6a|6b?g|6c";8 6.J(b)||a.6d(29 3x(".("+c+")$","i"))},2k:7(a,b,c){p d=$(c).6e(".16-2k").31("4B.16-2k",7(){$(b).H()});8 a==d.2K()}}});$.11=$.v.11})(1L);(7($){p d={};l($.4C){$.4C(7(a,X,b){p c=a.1i;l(a.2A=="24"){l(d[c]){d[c].24()}d[c]=b}})}W{p e=$.3h;$.3h=7(a){p b=("2A"S a?a:$.4D).2A,1i=("1i"S a?a:$.4D).1i;l(b=="24"){l(d[1i]){d[1i].24()}8(d[1i]=e.1S(6,R))}8 e.1S(6,R)}}})(1L);(7($){l(!1L.1w.3j.2p&&!1L.1w.3j.30&&4j.4E){$.P({42:\'2p\',4B:\'30\'},7(a,b){$.1w.3j[b]={6f:7(){6.4E(a,2D,w)},6g:7(){6.6h(a,2D,w)},2D:7(e){R[0]=$.1w.2U(e);R[0].12=b;8 $.1w.1P.1S(6,R)}};7 2D(e){e=$.1w.2U(e);e.12=b;8 $.1w.1P.Z(6,e)}})};$.K($.2E,{2Z:7(c,d,e){8 6.31(d,7(a){p b=$(a.6i);l(b.2L(c)){8 e.1S(b,R)}})}})})(1L);',62,391,'||||||this|function|return|||||||||||||if||||var|settings||||name|validator|true|u00A0|uD7FF||uF900|uFDCF|uFDF0||uFFEF|length|messages|valid|element|optional|extend|form|Please|false|enter|each|delete|arguments|in|for|errorList|currentForm|else|_|value|call||format|type|required|toHide|test|validate|data|maxlength|elements|pendingRequest|rules|case|errorClass|submitted|pending|invalid|typeof|port|attr|formSubmitted|add|trim|checkable|findByName|addClass|remote|url|showErrors|message|successList|toShow|event|console|input|filter|replace|removeClass|email|date|number|minlength|max|min|errorMap|not|metadata|jQuery|success|string|submitButton|handle|split|normalizeRule|apply|unhighlight|errorsFor|digits|reset|select|methods|dependency|mismatch|undefined|getLength|classRuleSettings|abort||x09|da|debug|new|submit|focusInvalid|switch|constructor|groups|validClass|addWrapper|radio|dateISO|creditcard|equalTo|rangelength|range|labelContainer|containers|focusin|currentElements|check|push|objectLength|method|parameters|defaultMessage|wrapper|previousValue|depends|mode|x20|bEven|handler|fn|window|find|click|cancelSubmit|submitHandler|val|is|staticRules|break|defaults|errorElement|errorLabelContainer|ignore|hide|highlight|fix|than|characters|delegate|eventType|validateDelegate|focusout|bind|prepareForm|clean|prepareElement|resetForm|hideErrors|rulesCache|errors|meta|theregex|showLabel|idOrName|dependTypes|old|param|Number|ajax|x0d|special|nothing|selected|onsubmit|button|remove|normalizeRules|metadataRules|classRules|attributeRules|checked|init|makeArray|Array|RegExp|error|errorContainer|ignoreTitle|lastActive|lastElement|parentNode|field|the|accept|no|between|and|or|equal|to|autoCreateRanges|errorContext|text|textarea|checkbox|option|invalidHandler|checkForm|triggerHandler|numberOfInvalids|grep|defaultShowErrors|size|try|findLastActive|focus|catch|formatAndAdd|id|customMetaMessage|customMessage|String|findDefined|strong|parent|validElements|show|invalidElements|map|generated|html|errorPlacement|document|null|nodeName|toLowerCase|depend|startRequest|stopRequest|dateDE|numberDE|addClassRules|isFunction|originalMessage|x22|x0a|x01|x0b|x0c|x7f|blur|ajaxPrefilter|ajaxSettings|addEventListener|warn|can|returning|cancel|preventDefault|hidden|appendTo|removeAttrs|removeAttr|expr|blank|filled|unchecked|unshift|slice|label|onfocusin|focusCleanup|blockFocusCleanup|onfocusout|onkeyup|onclick|setDefaults|This|address|URL|ISO|only|credit|card|same|again|with|extension|more|at|least|long|less|greater|prototype|valueCache|on|tel|password|file|keyup|visible|trigger|image|disabled|has|assigned|continue|log|exception|occured|when|checking|throw|title|Warning|No|defined|wrap|append|insertAfter|getElementsByName|boolean|class|2147483647|524288|addMethod|default|dataType|json|x08|x0e|x1f|x21|x23|x5b|x5d|x7e|https|ftp|uE000|uF8FF|Invalid|NaN|Date|charAt|parseInt|png|jpe|gif|match|unbind|setup|teardown|removeEventListener|target'.split('|'),0,{}))


/* Made by Mathias Bynens <http://mathiasbynens.be/> */
function number_format(a, b, c, d) {
 a = Math.round(a * Math.pow(10, b)) / Math.pow(10, b);
 e = a + '';
 f = e.split('.');
 if (!f[0]) {
  f[0] = '0';
 }
 if (!f[1]) {
  f[1] = '';
 }
 if (f[1].length < b) {
  g = f[1];
  for (i=f[1].length + 1; i <= b; i++) {
   g += '0';
  }
  f[1] = g;
 }
 if(d != '' && f[0].length > 3) {
  h = f[0];
  f[0] = '';
  for(j = 3; j < h.length; j+=3) {
   i = h.slice(h.length - j, h.length - j + 3);
   f[0] = d + i +  f[0] + '';
  }
  j = h.substr(0, (h.length % 3 == 0) ? 3 : (h.length % 3));
  f[0] = j + f[0];
 }
 c = (b <= 0) ? '' : c;
 return f[0] + c + f[1];
}


/*!
 * Scripts
 *
 */
jQuery(function($) {
	$.fn.reverse = [].reverse;

	var Engine = {
		translation : {
			pl_dictionary : {
				next_label : 'Następny',
				prev_label : 'Poprzedni',
				loading_page_label : 'Ładuję stronę',
				loading_content_label : 'Ładuję treść',
				loading_image_label : 'Ładuję obrazek.',
				invalid_page_url_label : 'Niepoprawny adres strony.',
				invalid_image_url_label : 'Błędny adres obrazka.',
				forms : {
					name_required : "Podaj swoje imię i nazwisko",
					mail_required : "Podaj swój adres email",
					mail_invalid : "Adres mail jest niepoprawny",
					message_required : "Wpisz wiadomość",
					budget_required : "Podaj nam swój budżet",
					budget_data_sets : {
						set1 : ["określ budżet",'do <span class="curr-value" data-currency-value="10000">10 000</span> <span class="curr-symbol">zł</span>',
												'do <span class="curr-value" data-currency-value="20000">20 000</span> <span class="curr-symbol">zł</span>',
												'do <span class="curr-value" data-currency-value="30000">30 000</span> <span class="curr-symbol">zł</span>',
												'do <span class="curr-value" data-currency-value="40000">40 000</span>&nbsp;<span class="curr-symbol">zł</span>',
												'powyżej <br/><span class="curr-value" data-currency-value="40000">40 000</span>&nbsp;<span class="curr-symbol">zł</span>',
												'jeszcze<br/>nie wiem'],
						set2 : ["określ ilość <br/>stron",  '1-3 strony<br/>około <span class="curr-value" data-currency-value="1000">1000</span> <span class="curr-symbol">zł</span>',
															'3-5 stron<br/>około <span class="curr-value" data-currency-value="1600">1600</span> <span class="curr-symbol">zł</span>',
															'5-10 stron<br/>około <span class="curr-value" data-currency-value="2600">2600</span> <span class="curr-symbol">zł</span>',
															'10-15 stron<br/>około <span class="curr-value" data-currency-value="4000">4000</span> <span class="curr-symbol">zł</span>',
															'15-20 stron<br/>około <span class="curr-value" data-currency-value="6000">6000</span> <span class="curr-symbol">zł</span>',
															"więcej niż<br/> 20 stron"],
						set3 : ["określ budżet",'do <span class="curr-value" data-currency-value="2000">2 000</span> <span class="curr-symbol">zł</span>',
												'do <span class="curr-value" data-currency-value="3000">3 000</span> <span class="curr-symbol">zł</span>',
												'do <span class="curr-value" data-currency-value="5000">5 000</span> <span class="curr-symbol">zł</span>',
												'do <span class="curr-value" data-currency-value="8000">8 000</span>&nbsp;<span class="curr-symbol">zł</span>',
												'powyżej <br/><span class="curr-value" data-currency-value="8000">8 000</span>&nbsp;<span class="curr-symbol">zł</span>',
												'jeszcze<br/>nie wiem']
					}
				}
			},
			en_dictionary : {
				next_label : 'Next',
				prev_label : 'Previous',
				loading_page_label : 'Loading page',
				loading_content_label : 'Loading content',
				loading_image_label : 'Loading image',
				invalid_page_url_label : 'Invalid URL.',
				invalid_image_url_label : 'Invalid Image URL.',
				forms : {
					name_required : "Please write Your first and last name",
					mail_required : "Please write Your email",
					mail_invalid : "Email is incorrect",
					message_required : "Please write a message",
					budget_required : "Please set Your budget",
					budget_data_sets : {
						set1 : ["set budget",'up to <br/><span class="curr-value" data-currency-value="2500">2500</span> <span class="curr-symbol">&euro;</span>',
												'up to <br/> <span class="curr-value" data-currency-value="5000">5000</span> <span class="curr-symbol">&euro;</span>',
												'up to <br/> <span class="curr-value" data-currency-value="7500">7500</span> <span class="curr-symbol">&euro;</span>',
												'up to <br/> <span class="curr-value" data-currency-value="10000">10 000</span>&nbsp;<span class="curr-symbol">&euro;</span>',
												'above <br/><span class="curr-value" data-currency-value="10000">10 000</span>&nbsp;<span class="curr-symbol">&euro;</span>',
												'don\'t<br/>know yet'],
						set2 : ["set pages<br/> count",  '1-3 pages<br/>about <span class="curr-value" data-currency-value="300">300</span> <span class="curr-symbol">&euro;</span>',
															'3-5 pages<br/>about <span class="curr-value" data-currency-value="500">500</span> <span class="curr-symbol">&euro;</span>',
															'5-10 pages<br/>about <span class="curr-value" data-currency-value="700">700</span> <span class="curr-symbol">&euro;</span>',
															'10-15 pages<br/>about <span class="curr-value" data-currency-value="1100">1100</span> <span class="curr-symbol">&euro;</span>',
															'15-20 pages<br/>about <span class="curr-value" data-currency-value="1600">1600</span> <span class="curr-symbol">&euro;</span>',
															"more than<br/> 20 pages"],
						set3 : ["set budget",'up to <br/> <span class="curr-value" data-currency-value="500">500</span> <span class="curr-symbol">&euro;</span>',
												'up to <br/> <span class="curr-value" data-currency-value="750">750</span> <span class="curr-symbol">&euro;</span>',
												'up to <br/> <span class="curr-value" data-currency-value="1250">1250</span> <span class="curr-symbol">&euro;</span>',
												'up to <br/> <span class="curr-value" data-currency-value="2000">2000</span>&nbsp;<span class="curr-symbol">&euro;</span>',
												'above <br/><span class="curr-value" data-currency-value="2000">2 000</span>&nbsp;<span class="curr-symbol">&euro;</span>',
												'don\'t<br/>know yet']
					}
				}

			},
			dictionary : {},
			getDictionary : function () {
				var self = this;
				var lang = $('html').attr('lang');
				self.dictionary = self[lang+'_dictionary'];
			}
		},
		utils : {
			links : function(){
				$('a[rel*="external"]').click(function(e){
					e.preventDefault();
					window.open($(this).attr('href'));
				});
			},
			mails : function(){
				$('a[href^="mailto:"]').each(function(){
					var mail = $(this).attr('href').replace('mailto:','');
					var replaced = mail.replace('/at/','@');
					$(this).attr('href','mailto:'+replaced);
					if($(this).text() == mail) {
						$(this).text(replaced);
					}
				});
			},
			placeholderInit : function () {
				$('input[placeholder]').placeholder();
				$('textarea[placeholder]').placeholder();
			}
		},
		ui : {
		// *** homepage slider ************************************************* /
			mainCycle : {
				currentIndex : 0,
				slidersSpeed : 500,
				resizing : 0,
				objLib : {
					cycleA : $('section.cycle-a'),
					cycleNav : $('#cycle-1-nav'),
					allSliders : $('section.cycle-a > article'),
					cycleNavContainer : $('nav.cycle-nav-container'),
					nextButton : null,
					prevButton : null
				},
				init : function () {
					var self = this;
					self.cycleInit();
				},
				cycleInit : function () {
					var self = this;
					if(self.objLib.cycleA.length)
					{
						var startIndex = self.getIndexByHash();
						self.generateNavButtons();
						self.cycleStart(startIndex);

						self.horzNavInit();
						self.prevNavCycleStart();
						self.nextNavCycleStart();
						self.setNavigation(startIndex);

						//resize event listener
						self.resizeAction();

						//history event listener
						self.historyAction();

						//mousewheel event listener
						self.mouseWheelAction();

						//keyboard navigation
						self.keyboardNavigation();

						//gestures navigation
						self.gesturesNav();
					}
				},
				cycleStart : function (startingIndex) {
					var self = this;

					if (startingIndex) {

					}
					else
					{
						startingIndex = 0;
					}

					self.mainSlider = self.objLib.cycleA.cycle({
					    fx:     'scrollHorz',
					    speed:  self.slidersSpeed,
					    timeout: 0,
						autostop: 0,
						init : 1,
						/*easing : "swing",*/
						next:   self.objLib.nextButton,
    					prev:   self.objLib.prevButton,
						manualTrump: false,
						startingSlide: startingIndex,
						before : function (curr, next, opts, fwd) {
							//alert(opts.fx);
							self.objLib.cycleA.scrollTop(0);
							if(opts.init === 1)
							{
								var index = startingIndex;
								opts.init = 0;
								self.currentIndex = index;
							}
							else
							{
								var index = opts.nextSlide;
								self.currentIndex = index;
								self.cycleEffect($(next), fwd);
							}
							self.activeNavChanger(index);
						},
						after : function (curr, next, opts) {
							//set new document location hash
							var myId = $(next).attr('id');
							var hash = document.location.hash.replace('#','');
							if(hash != myId)
							{
								$('#'+myId).attr('id', '_'+myId);
								document.location.hash = '#'+myId;
								$('#_'+myId).attr('id', myId);
							}
						}
					});
				},
				cycleEffect : function (obj, direction) {
					var self = this;
					if (self.resizing === 0) {
						var contentBox = obj.find('.content');
						var visBox = obj.find('.vis-box');

						var sign = '';
						if (!direction) {
							sign = '-';
						}

						contentBox.css({ 'position' : 'absolute', 'left' : sign+'150px', 'opacity' : 0 });
						contentBox.delay(200).animate({
							left: 0,
							opacity : 1
						}, 600, 'swing', null);
					}
				},
				prevNavCycleStart : function () {
					var self = this;
					var startingSlideIndex = self.objLib.allSliders.length -1;
					self.prevSlider = self.objLib.prevButton.find('.list-f').cycle({
					    fx: 'scrollHorz',
					    speed:  self.slidersSpeed,
					    timeout: 0,
						autostop: 0,
						next:   self.objLib.nextButton,
    					prev:   self.objLib.prevButton,
						manualTrump: false,
						startingSlide: startingSlideIndex
					});
				},
				nextNavCycleStart : function () {
					var self = this;
					self.nextSlider = self.objLib.nextButton.find('.list-f').cycle({
					    fx: 'scrollHorz',
					    speed:  self.slidersSpeed,
					    timeout: 0,
						autostop: 0,
						next:   self.objLib.nextButton,
    					prev:   self.objLib.prevButton,
						manualTrump: false,
						startingSlide: 1
					});
				},
				horzNavInit : function () {
					$('.cycle-button-a').hover(function() {
						$(this).stop();
						$(this).animate({
							width: 139
						}, 300, 'easeOutCubic', null);
   	         	},
					function() {
    					$(this).stop();
						$(this).animate({
							width: 41
 						},
						{
							duration: 200
 						});
					});
				},
				setNavigation : function (startIndex) {
					var self = this;
					self.setSlidersIndex(startIndex);
					self.activeNavChanger(startIndex);
					self.objLib.cycleNav.delegate('a', 'click', function (e) {
						e.preventDefault();
						var listItems  = self.objLib.cycleNav.find('li');
						var itemToCheck = $(this).parent();
						activeIndex = listItems.index(itemToCheck);

						//set cycle current item
						self.setSlidersIndex(activeIndex);
					});
				},
				setSlidersIndex : function (index) {
					var self = this;
					self.mainSlider.cycle(index);
					self.prevSlider.cycle(self.getPrevItem(index-1));
					self.nextSlider.cycle(self.getNextItem(index+1));
				},
				activeNavChanger : function (index) {
					var self = this;
					self.objLib.cycleNav.find('a').removeClass('active');
					self.objLib.cycleNav.find('a').eq(index).addClass('active');
				},
				getNextItem : function (activeIndex) {
					var self = this;
					if(activeIndex === self.objLib.allSliders.length)
					{
						activeIndex = 0;
					}
					return activeIndex;
				},
				getPrevItem : function (activeIndex) {
					var self = this;
					if(activeIndex < 0)
					{
						activeIndex = self.objLib.allSliders.length - 1;
					}
					return activeIndex;
				},
				generateNavButtons : function () {
					var self = this;
					var itemsList = '';
					self.objLib.allSliders.each( function () {
						itemsList += '<li><img src="images/project-thumbs/'+$(this).data('thumbnail')+'" width="98" height="85" alt=""/></li>';
					});

					self.objLib.nextButton = $('<div class="cycle-button-a" id="cycle-next"><div class="cbutton-wrapper"><ul class="list-f">'+itemsList+'</ul></div></div>').insertAfter(self.objLib.cycleNavContainer);
					self.objLib.prevButton = $('<div class="cycle-button-a cba-a" id="cycle-prev"><div class="cbutton-wrapper"><ul class="list-f">'+itemsList+'</ul></div></div>').insertAfter(self.objLib.cycleNavContainer);
				},
				getIndexByHash : function () {
					//alert(document.location.hash);
					var self = this;
					var hash = document.location.hash.replace('#','');
					var index = 0;
					if(hash !== '')
					{
						self.objLib.allSliders.each(function (i) {
							var myId = $(this).attr('id');
							if (myId == hash) {
								index = i;
							}
						});
					}
					return index;
				},
				resizeAction : function () {
					var self = this;
					$(window).resize(function() {
						self.mainSlider.cycle('stop');
						var width = $('.box-c').width();
						self.objLib.allSliders.width(width);
						self.resizing = 1;
						self.cycleStart(self.currentIndex);
						self.setNavigation(self.currentIndex);
						self.resizing = 0;
					});
				},
				historyAction : function () {
					var self = this;
					$.history.init(function(hash){
						if(hash == '')
						{ }
						else
						{
				         	self.setNavigation(self.getIndexByHash());
					    }
					    },{ unescape: ",/" }
					);
				},
				mouseWheelAction : function () {
					var self = this;
					self.objLib.cycleNav.bind('mousewheel', function(event, delta) {
        			    var dir = delta > 0 ? 'Up' : 'Down',
        		        vel = Math.abs(delta);
						if (dir === 'Up')
						{
							self.mainSlider.cycle('prev');
						}
						else
						{
							self.mainSlider.cycle('next');
						}
        			    return false;
        			});
				},
				keyboardNavigation : function () {
					var self = this;
					$(window).keydown(function(event){
						//Left
						if(event.keyCode == '37')
						{
							self.objLib.prevButton.trigger('click');
						}
						//Right
						if(event.keyCode == '39')
						{
							self.objLib.nextButton.trigger('click');
						}
            		});
				},
				gesturesNav : function () {
					var self = this;
					var swipe = self.gesturesObj();
					swipe.on('swipeleft swipeleftup swipeleftdown', function (e) {
						//alert('swipeleft');
						self.mainSlider.cycle('prev');
					});
					swipe.bind('swiperight swiperightup swiperightdown', function () {
						//alert('swiperight');
						self.mainSlider.cycle('next');
					});
				},
				gesturesObj : function () {
					var self = this;
					return $('<div id="swipe"/>').appendTo(self.objLib.cycleA);
				}
			},
			// *** homepage boxes effects ************************************************* /
			boxesEffects : {
				objLib : {
					navBoxes : $('#navBoxes')
				},
				init : function () {
					var self = this;
					self.boxesEffect();
				},
				boxesEffect : function () {
					var self = this;
					self.objLib.navBoxes.find('li').fadeTo(0,0.9);
					self.objLib.navBoxes.delegate('li', 'hover', function (e) {
						var bulletItem = $(this).find('.more-b');
						$(this).stop();
						bulletItem.stop();
						if(e.type === 'mouseenter')
						{
							$(this).animate({ top: 3, opacity : 1 }, { duration: 200 });
							bulletItem.animate({ opacity: 1, top: -4 }, { duration: 100	});
						}
						else
						{
							$(this).animate({top: 0, opacity : 0.9}, { duration: 100 });
							bulletItem.animate({ opacity: 0, top: -50 }, { duration: 200 });
						}
					});
				}
			},
			// *** homepage news cycle ************************************************* /
			newsCycle : {
				objLib : {
					newsCycle : $('#news-cycle'),
					newsCycleNav : $('#news-cycle-nav'),
					prevButton : null,
					nextButton : null
				},
				init : function () {
					var self = this;
					if (self.objLib.newsCycle.length) {
						self.generateNavButtons();
						self.startCycle();
						self.setNavig();
					}
				},
				startCycle : function () {
					var self = this;
					self.newsCycle = self.objLib.newsCycle.cycle({
					    fx: 'fade',
					    speed:  500,
					    timeout: 0,
						autostop: 0,
						init : 1,
						manualTrump: false,
						next:   self.objLib.nextButton,
    					prev:   self.objLib.prevButton,
						pause: true,
						before : function (curr, next, opts, fwd) {
							//alert(opts.fx);
							if(opts.init === 1)
							{
								var index = 0;
								opts.init = 0;
							}
							else
							{
								var index = opts.nextSlide;
							}
							self.activeNavChanger(index);
						}
					});
				},
				setNavig : function () {
					var self = this;
					self.objLib.newsCycleNav.delegate('a', 'click', function (e) {
						e.preventDefault();
						var listItems  = self.objLib.newsCycleNav.find('li');
						var itemToCheck = $(this).parent();
						activeIndex = listItems.index(itemToCheck);
						self.newsCycle.cycle(activeIndex);
					});
				},
				activeNavChanger : function (index) {
					var self = this;
					self.objLib.newsCycleNav.find('a').removeClass('active');
					self.objLib.newsCycleNav.find('a').eq(index).addClass('active');
				},
				generateNavButtons : function () {
					var self = this;
					self.objLib.nextButton = $('<p id="news-cycle-next" class="news-navig-btn next">'+Engine.translation.dictionary.next_label+'</p>').insertAfter(self.objLib.newsCycleNav);
					self.objLib.prevButton = $('<p id="news-cycle-prev" class="news-navig-btn prev">'+Engine.translation.dictionary.prev_label+'</p>').insertBefore(self.objLib.newsCycleNav);
				}
			},
			// *** portfolio sliders, image load, effects ************************************************* /
			portfolio : {
				portfolioItems : new Array(),
				generatePortfolioView : function (pageObj) {
					var self = this;
					if (pageObj.length) {
						var pageId = pageObj.attr('id');
						if (self.portfolioItems[pageId] === undefined) {
							if (pageObj.eq(0).find('.projects-cycle').length) {
								if (pageId === 'others') {
									var portfolioContainers = pageObj.find('div.project-section-a');
									if (portfolioContainers.length) {
										self.portfolioItems[pageId] = new Array();
										portfolioContainers.each(function () {
											var obj = new self.portfolioView($(this));
											window.console && console.log('[portfolio.generatePortfolioView] Portfolio View  Multiple Initialized');
										});
									}
								}
								else
								{
									self.portfolioItems[pageId] = new self.portfolioView(pageObj);
									window.console && console.log('[portfolio.generatePortfolioView] Portfolio View Initialized');
								}
							}
							else
							{
								self.portfolioItems[pageId] = true;
								window.console && console.log('[portfolio.generatePortfolioView] No cycle object present. Cannot continue.');
							}
						}
					}
				},
				portfolioView : function (containerObj) {
					this.clickBlocked = false;
					this.imgSliderEffect = 'right';
					this.objLib = {
						cycle : null,
						loader : null,
						imgCntrObj : containerObj.find('.image-container-a'),
						pageCycle : $('#page-cycle'),
						cycleObject : containerObj.find('.projects-cycle'),
						portfolioContainer : containerObj
					}


					/*if (!this.itemsList.length) {
						window.console && console.log('[svgEffects] No valid item list');
						return false;
					}*/

					if(typeof Engine.ui.portfolio.portfolioView._initialized == "undefined"){

						//init cycle
						Engine.ui.portfolio.portfolioView.prototype.startCycle = function () {
							var self = this;
							var prevBtn = self.objLib.portfolioContainer.find('.projects-cycle-prev');
							var nextBtn = self.objLib.portfolioContainer.find('.projects-cycle-next');
							nextBtn.hide();
							self.objLib.cycle = self.objLib.cycleObject.cycle({
								fx: 'scrollHorz',
								speed:  300,
								timeout: 0,
								autostop: 0,
								init : 1,
								nowrap: 1,
								manualTrump: false,
								next:   nextBtn,
								prev:   prevBtn,
								pause: true,
								before : function (curr, next, opts, fwd) {

								},
								after : function (curr, next, opts, fwd) {
									var index = opts.currSlide;

									//buttons
									if (index == 0)
									{
										prevBtn.fadeOut(350);
									}
									else
									{
										prevBtn.fadeIn(500);
									}

									if (index == opts.slideCount - 1)
									{
										nextBtn.fadeOut(350);
									}
									else
									{
										nextBtn.fadeIn(500);
										nextBtn.css({ display: 'block'});
									}
								}
							});
						}

						Engine.ui.portfolio.portfolioView.prototype.markCurrentThumb = function (index) {
							var self = this;
							self.objLib.cycleObject.find('a').removeClass('active');
							self.objLib.cycleObject.find('li li').eq(index).find('a').addClass('active');
							self.loadImage(this.objLib.cycleObject.find('a.active').attr('href'));
						}

						//Move thumbs slide when prev/next button on image is clicked
						Engine.ui.portfolio.portfolioView.prototype.moveSlide = function (index) {
							var self = this;
							var itemsInCycleCount = 6;
							if (this.objLib.cycleObject.data('item-thumbs-count')) {
								itemsInCycleCount = self.objLib.cycleObject.data('item-thumbs-count');
								//window.console && console.log('[portfolio.moveSlide] itemsInCycleCount: '+itemsInCycleCount);
							}

							var newSlideIndex = Math.ceil((index+1)/itemsInCycleCount);
							newSlideIndex = newSlideIndex - 1;
							self.objLib.cycleObject.cycle(newSlideIndex);
						}

						//Thumb click behaviour
						Engine.ui.portfolio.portfolioView.prototype.thumbClick = function () {
							var self = this;
							self.objLib.portfolioContainer.delegate('.projects-cycle li a', 'click', function (e) {
								e.preventDefault();
								if (!$(this).hasClass('active')) {
									if(!self.clickBlocked) {
										self.clickBlocked = true;
										var index = $(this).data('index');
										self.imgSliderEffect = 'random';
										self.markCurrentThumb(index);
									}
								}
							});
						}

						//Get next item. If item is last get first item.
						Engine.ui.portfolio.portfolioView.prototype.getNextItem = function () {
							var self = this;

							var currIndex = self.objLib.cycleObject.find('a.active').data('index');
							if(currIndex == self.objLib.cycleObject.find('a').length - 1)
							{
								currIndex = 0;
							}
							else
							{
								currIndex += 1;
							}
							self.imgSliderEffect = 'right';
							self.markCurrentThumb(currIndex);
							self.moveSlide(currIndex);
						}

						//Get previous item. If item is first get last item.
						Engine.ui.portfolio.portfolioView.prototype.getPrevItem = function () {
							var self = this;

							var currIndex = self.objLib.cycleObject.find('a.active').data('index');
							if(currIndex == 0)
							{
								currIndex = self.objLib.cycleObject.find('a').length - 1;
							}
							else
							{
								currIndex -= 1;
							}
							self.imgSliderEffect = 'left';
							self.markCurrentThumb(currIndex);
							self.moveSlide(currIndex);
						}


						//Prev/Next buttons on image - click events
						Engine.ui.portfolio.portfolioView.prototype.buttonsClick = function () {
							var self = this;
							self.objLib.portfolioContainer.delegate('.projects-navig-btn-b', 'click', function (e) {
								e.preventDefault();
								if(!self.clickBlocked)
								{
									self.clickBlocked = true;
									if ($(this).hasClass('prev')) {
										self.getPrevItem();
									}
									else
									{
										self.getNextItem();
									}
								}
							});
						}

						Engine.ui.portfolio.portfolioView.prototype.imageOverlayPosition = function () {
							var self = this;
							var winPos = $(window).scrollTop();
							//window.console && console.log('win pos: '+winPos);
							var winHeight = $(window).height();
							//window.console && console.log('win height: '+winHeight);
							var btnContainer = self.objLib.portfolioContainer.find('.projects-navig-btn-b');
							var btnContainerHeight = btnContainer.height();
							var btnContainerOffset = btnContainer.offset();

							//window.console && console.log('btn cont h: '+btnContainerHeight+', btn cont o: '+btnContainerOffset.top);

							var	offsetTop = btnContainerOffset.top;
							if (offsetTop == 0) {
								offsetTop = 408;
							}
							var halfCoords = winPos + winHeight/2;
							var overlayPos = Math.round(halfCoords - offsetTop);

							if (overlayPos - 140 < 0) {
								overlayPos = 140;
							}
							else if(overlayPos > btnContainerHeight - 140)
							{
								overlayPos = btnContainerHeight - 140;
							}
							return overlayPos;
						}

						Engine.ui.portfolio.portfolioView.prototype.buttonsPosition = function () {
							var self = this;
							var topPos = self.imageOverlayPosition();
							//window.console && console.log('top pos: '+topPos);
							//window.console && console.log('----------------------------- '+self.objLib.portfolioContainer.attr('id'));
							var topPos1 = topPos - 27;
							self.objLib.portfolioContainer.find('.projects-navig-btn-b span').css('top', topPos1+'px');
							return topPos;
						}

						Engine.ui.portfolio.portfolioView.prototype.loaderPosition = function () {
							var self = this;
							var topPos = self.imageOverlayPosition();
							var topPos1 = topPos - 32;
							self.objLib.portfolioContainer.find('.loader .container').css('top', topPos1+'px');
							return topPos;
						}

						Engine.ui.portfolio.portfolioView.prototype.overlaysPosition = function () {
							var self = this;
							window.console && console.log('overlays position');
							self.loaderPosition();
							self.buttonsPosition();
							$(window).bind('scroll', function(){
								self.loaderPosition();
								self.buttonsPosition();
							});
							$(window).resize(function() {
								self.loaderPosition();
								self.buttonsPosition();
							});
						}

						// key nav helper effects
						Engine.ui.portfolio.portfolioView.prototype.keyboardNavHelperEffects = function () {
							var self = this;
							self.objLib.portfolioContainer.delegate('.box-f', 'hover', function (e) {
								var objToAnimate = $(this).parent().find('.projects-key-nav');
								if (objToAnimate.length) {
									if(e.type === 'mouseenter')
									{
										var animSettings = { top : 60 };
										objToAnimate.stop();
										objToAnimate.animate(animSettings, 500, 'easeOutCubic', null);
									}
									else
									{
										var animSettings = { top : 0 };
										objToAnimate.stop();
										objToAnimate.animate(animSettings, 100, '', null);
									}
								}
							});
						}

						//Buttons on image - hover effects
						Engine.ui.portfolio.portfolioView.prototype.buttonsEffects = function () {
							var self = this;
							self.objLib.portfolioContainer.delegate('.projects-navig-btn-b', 'hover', function (e) {

								var objToAnimate = $(this).find('span');

								if(e.type === 'mouseenter')
								{
									var animSettings = { left : 0 };
									if ($(this).hasClass('next')) {
										animSettings = { right : 0 };
									}

									objToAnimate.stop();
									objToAnimate.animate(animSettings, 500, 'easeOutCubic', null);
								}
								else
								{
									var animSettings = { left : -45 };
									if ($(this).hasClass('next')) {
										animSettings = { right : -45 };
									}

									objToAnimate.stop();
									objToAnimate.animate(animSettings, 100, '', null);
								}
							});
						}

						//initial height options
						Engine.ui.portfolio.portfolioView.prototype.setContainerInitialOptions = function () {
							var self = this;
							var height = self.objLib.portfolioContainer.find('.im img').attr('height');
							self.objLib.portfolioContainer.find('.image-b').height(height);
							self.objLib.portfolioContainer.find('.projects-navig-btn-b').height(height);
							self.objLib.portfolioContainer.find('.im').addClass('cur-image-container');
							//self.mouseWheelAction(pageObject);
						}

						// Load image and other stuff after load or after error
						Engine.ui.portfolio.portfolioView.prototype.loadImage = function (src) {
							var self = this;
							//self.objLib.imgCntrObj = self.objLib.portfolioContainer.;
							self.loaderTimeout = setTimeout(function () { self.addLoaderLayer() }, 50);
							var img = new Image();
      						$(img).load(function () {
								var tempImgContainer = self.createTempContainer();
								//alert(tempImgContainer)
								$(this).appendTo(tempImgContainer);
								$(this).attr('height', $(this).height());
								$(this).attr('width', $(this).width());
								var height = $(this).height();
								clearTimeout(self.loaderTimeout);
								self.removeLoaderLayer();
								self.doAnimations(height);
								self.removeTempContainer();
        					}).error(function () {
								setTimeout(function () { self.showErrorLayer() }, 50);
       						}).attr('src', src);
						}


						//Slices, image transition effect
						Engine.ui.portfolio.portfolioView.prototype.removeTempContainer = function (containerHeight) {
							var self = this;
							var currentContainer = self.objLib.imgCntrObj.find('.cur-image-container');
							var containerHeight = currentContainer.height();

							var slicesCount = 10;
							var src = currentContainer.find('img').attr('src');
							self.createSlices(currentContainer, slicesCount, src, containerHeight);

							var indexArray = range(0, slicesCount-1);
							if (self.imgSliderEffect === 'random') {
								indexArray.shuffle();
							}

							var slices = currentContainer.find('div');
							var i = 0;
							var animSpeed = 800;
							var slicesCount = slices.length
							var timeBuff = 0;
							if (self.imgSliderEffect === 'left') {
								slices.reverse();
							}
							slices.each(function(i){
								var slice = slices.eq(indexArray[i]);
								slice.css({ 'top': '0px' });
								if(i == slicesCount-1){
									setTimeout(function(){
										slice.animate({ top: containerHeight, opacity:'0' }, animSpeed, 'swing', function(){
											currentContainer.remove();
											self.objLib.imgCntrObj.find('.temp-image-container').addClass('cur-image-container').removeClass('temp-image-container');
											self.clickBlocked = false;
										});
									}, (100 + timeBuff));
								} else {
									setTimeout(function(){
										slice.animate({ top: containerHeight, opacity:'0' }, animSpeed, 'swing');
									}, (100 + timeBuff));
								}
								timeBuff += 50;
								i++;
							});
						}


						Engine.ui.portfolio.portfolioView.prototype.createTempContainer = function () {
							var self = this;
							return $('<p class="temp-image-container"/>').insertAfter(self.objLib.imgCntrObj.find('.cur-image-container'));
						}


						//Create div's as slices
						Engine.ui.portfolio.portfolioView.prototype.createSlices = function(slider, slicesCount, imgSrc, height){
							var slicesString = '';
							var sliceWidth = Math.round(slider.width()/slicesCount);
							for(var i = 0; i < slicesCount; i++)
							{
								if(i == slicesCount-1)
								{
									slicesString = slicesString + '<div class="img-slice" style="left:'+(sliceWidth*i)+'px; width:'+(slider.width()-(sliceWidth*i))+'px; height: '+height+'px; background: url('+ imgSrc +') no-repeat -'+ ((sliceWidth + (i * sliceWidth)) - sliceWidth) +'px 0%"></div>';
								}
								else
								{
									slicesString = slicesString + '<div class="img-slice" style="left:'+(sliceWidth*i)+'px; width:'+sliceWidth+'px; height: '+height+'px; background: url('+ imgSrc +') no-repeat -'+ ((sliceWidth + (i * sliceWidth)) - sliceWidth) +'px 0%"></div>';
								}
        					}
							slider.html(slicesString);
							slider.find('img').remove();
						}

						Engine.ui.portfolio.portfolioView.prototype.doAnimations = function (containerHeight) {
							var self = this;
							Engine.ui.pagesDynamicNavigation.setFlexibleHeight();
							var objects = self.objLib.imgCntrObj.find('.projects-navig-btn-b');
							//self.updateElementsHeight();
							objects.animate({
 								height: containerHeight
 							}, 500, 'swing', null);


							var object = self.objLib.imgCntrObj.find('.image-b');
							object.animate({
 								height: containerHeight
 							}, 500, 'swing', function () {
								var containerPosition = self.objLib.portfolioContainer.css('position');
								if (containerPosition !== 'absolute') {
									var pageObject = self.objLib.imgCntrObj.closest('.box-d');
									var pOHeight = pageObject.height();
									pageObject.height(pOHeight);
									self.objLib.pageCycle.height(pOHeight+255);
								}
								self.loaderPosition();
								self.buttonsPosition();
 							});
						}

						Engine.ui.portfolio.portfolioView.prototype.updateElementsHeight = function () {
							if ($('#exposeMask').length) {
								var section = $('.project-section-a:visible');
								if (section.length) {
									$('#exposeMask').height($('body').height());
								}
							}
						}

						Engine.ui.portfolio.portfolioView.prototype.addLoaderLayer = function () {
							var self = this;
							var imageContainer = self.objLib.imgCntrObj.find('.image-b');
							self.objLib.loader = $('<div class="loader"><div class="container"><div class="popbox-b pb-a text-area-a"><h3><span>'+Engine.translation.dictionary.loading_image_label+'</span></h3></div></div>').appendTo(imageContainer);
							self.objLib.loader.height(imageContainer.height());
							self.loaderPosition();
						}


						Engine.ui.portfolio.portfolioView.prototype.removeLoaderLayer = function () {
							var self = this;
							if (self.objLib.loader) {
								self.objLib.loader.remove();
							}
						}


						Engine.ui.portfolio.portfolioView.prototype.showErrorLayer = function () {
							var self = this;
							self.objLib.loader.find('h3').html(Engine.translation.dictionary.invalid_image_url_label);
							setTimeout(function () { self.removeLoaderLayer() }, 1200);
							self.clickBlocked = false;
						}

						Engine.ui.portfolio.portfolioView.prototype.keyboardNavigation = function () {
							var self = this;
							$(window).keydown(function(event){
								if(!self.clickBlocked && self.objLib.portfolioContainer.is(':visible'))
								{
									//Left
        				       		if(event.keyCode == '37')
									{
        				            	self.clickBlocked = true;
										self.getPrevItem();
        				        	}
        				        	//Right
									if(event.keyCode == '39')
									{
										self.clickBlocked = true;
										self.getNextItem();
        				            }
        			        	}
        		    		});
						}

						Engine.ui.portfolio.portfolioView.prototype.init = function () {
							var self = this;
							self.setContainerInitialOptions();
							self.startCycle();
							self.thumbClick();
							self.buttonsClick();
							self.buttonsEffects();
							self.keyboardNavigation();
							self.keyboardNavHelperEffects();
							self.overlaysPosition();
						}

						Engine.ui.portfolio.portfolioView._initialized = true;
					}

					this.init();
				}
			},
			// *** dynamic pages change, cycle, effects, loader, history events ************************************************* /
			pagesDynamicNavigation : {
				currentIndex : 0,
				blockClick : false,
				nowResizing : false,
				historyTimeout : null,
				objLib : {
					pageCycleObj : $('#page-cycle'),
					loaderBox : $('#loadercallbackbox'),
					portfolioNav : $('#portfolio-nav'),
					portfolioNavContainer : $('#portfolio-nav-container')
				},
				init : function () {
					var self = this;
					if (self.objLib.pageCycleObj.length) {
						self.navigInit();
						self.resizeAction();
						self.overlayInit();
						self.historyAction();
					}
				},
				navigInit : function () {
					var self = this;
					var clickTimeout = null;
					self.objLib.portfolioNavContainer.delegate('a:not(.no-load)', 'click', function (e) {

						e.preventDefault();
						if(!$(this).hasClass('active') && !self.blockClick) {
							self.blockClick = true;
							clearTimeout(clickTimeout);
							clickTimeout = setTimeout(function () {	self.blockClick = false; }, 1200);
							self.objLib.portfolioNavContainer.find('a').removeClass('active');
							$(this).addClass('active');
							var url = $(this).attr('href');
							var id = $(this).data('id');

							var visiblePage = self.objLib.pageCycleObj.find('.box-d').filter(':visible');
							var collection = visiblePage.find('#portfolio-items-list li');
							if (collection.length) {
								if (portfolioProjectsPage === null) {
									portfolioProjectsPage = new Engine.ui.portfolioProjectsPage(visiblePage);
								}
								portfolioProjectsPage.iconsEffect('explode');
								setTimeout(function () {
									self.gotoPage(url, id, false);
								}, 300);
							}
							else
							{
								self.gotoPage(url, id, false);
							}
						}
					});
				},
				gotoPage : function (url, id, dontChangeHistory) {
					var self = this;

					//check if history api is available
					if(!self.supportsHistoryApi()) {
						//fuck IE9, fuck dirty hash bangs
						window.location = url;
						return false;
					}

					$('#tooltip p.tooltip span').addClass('loading');

					//check if page already present in cycle/slider
					var pageIndex = self.checkIfPageAlreadyExists(id);
					if (pageIndex !== false) {
						//move slider by page index
						self.setStrictHeight();
						if (dontChangeHistory !== true) {
							self.changeHistoryUrl(url);
						}
						self.pageCycle.cycle(pageIndex);
					}
					else
					{
						//self.newPageLoaded = true;
						self.loadPage(url, id, dontChangeHistory);
					}
					Engine.ui.portfolioBackPanel.btnShow();

					//hide folder if opened on "portfolio others" page
					if (portfolioOthersPage !== null)
					{
						portfolioOthersPage.closeExpose();
					}

					//hide overlay
					Engine.ui.portfolioOverlay.objLib.callbackbox.data('overlay').close();
				},
				cycleInit : function (startingIndex) {
					var self = this;

					if (startingIndex) {}
					else
					{
						startingIndex = 0;
					}

					self.setStrictHeight();
					self.pageCycle = self.objLib.pageCycleObj.cycle({
					    fx: 'fade',
						//fx: 'uncover',
					    speed:  800,
					    timeout: 0,
						autostop: 0,
						init : 1,
						fit: 1,
						containerResize: 0,
						manualTrump: false,
						startingSlide: startingIndex,
						before : function (curr, next, opts, fwd) {
							if (opts.init === 0) {
								/*if ($(curr).attr('id') === 'portfolio-all') {
									opts.fx = 'uncover';
									opts.speed = 1200;
								}
								else
								{
									opts.fx = 'fade';
									opts.speed = 800;
								}*/

								var nextSlideObj = $(next);
								var newContainerHeight = nextSlideObj.height();
								opts.height = newContainerHeight;

								// don't need to animate height of container when page is resized, only when slide is changed
								if (self.nowResizing === true) {
									self.nowResizing = false;
								}
								else
								{
									self.objLib.pageCycleObj.animate({ height : newContainerHeight + 255 }, 500, 'swing', null);
								}
								var index = opts.nextSlide;

								$('title').html($(next).data('title'));
								//window.console && console.log($(next).data('title'));
								self.currentIndex = index;
							}
						},
						after : function (curr, next, opts, fwd) {
							if (opts.init === 1) {
								opts.init = 0;
							}
							else
							{
								var nextSlideObj = $(next);
								nextSlideObj.removeClass('temporary');
								self.blockClick = false;
								$('#tooltip p.tooltip span').removeClass('loading');

								//some effects functions
								self.nextPageFunctions(nextSlideObj);
							}
						}
					});
				},
				setFlexibleHeight : function () {
					var self = this;
					self.objLib.pageCycleObj.css('height', 'auto');
					self.objLib.pageCycleObj.find('.box-d').css({'height' : 'auto', 'position' : 'static'});
				},
				setStrictHeight : function () {
					var self = this;
					self.objLib.pageCycleObj.height(self.objLib.pageCycleObj.height());
					self.objLib.pageCycleObj.find('.box-d').css('position', 'absolute');
				},
				loadPage : function (url, id, dontChangeHistory) {
					var self = this;
					//show overlay
					self.overlayTimeout = setTimeout(function () { self.overlayShow(); }, 100);
//window.console && console.log('url przed zaladowaniem: '+url);
//window.console && console.log('------------------------------------------');
      				var tempObj = $('<div></div>').load(url+' #'+id, function (response, status, xhr) {
						if (status == "error") {
							self.loadingErrorOverlayInfo();
						}
						else
						{
							if (dontChangeHistory !== true) {
								self.changeHistoryUrl(url);
							}
							var myPage = tempObj.find('.box-d').addClass('temporary');

							//set important functions on new loaded page
							self.setFunctionsOnNewPage(myPage);

							//add object to DOM (in cycle)
							self.objLib.pageCycleObj.append(myPage);

							//check if all images are loaded
							myPage.find('img').batchImageLoad({
								loadingCompleteCallback: function () {
									self.loadingComplete(id);
								},
								imageLoadedCallback : function (elementsLoadedCount, totalImagesCount) {
									self.progressIndicator(elementsLoadedCount, totalImagesCount);
								}
							});
						}
        			});
				},
				progressIndicator : function (elementsLoadedCount, totalImagesCount) {
					var self = this;
					var percent = Math.round((elementsLoadedCount / totalImagesCount) * 100);
					if (isNaN(percent))
					{
						percent = 0;
					}
					self.objLib.loaderBox.find('h3 span').html(percent);
				},
				loadingErrorOverlayInfo : function () {
					var self = this;
					var currentPage = self.objLib.pageCycleObj.find('.box-d').filter(':visible');
					var currentId = currentPage.attr('id');
					var links = self.objLib.portfolioNavContainer.find('a');

					links.removeClass('active');
					self.objLib.portfolioNavContainer.find('a[data-id="'+currentId+'"]').addClass('active');

					self.objLib.loaderBox.find('h3').html(Engine.translation.dictionary.invalid_page_url_label);

					setTimeout( function () {
						self.objLib.loaderBox.data('overlay').close();
						self.blockClick = false;
						if(portfolioProjectsPage !== null)
						{
							portfolioProjectsPage.iconsEffect('implode');
						}
						links.removeClass('active');
						Engine.ui.portfolioBackPanel.btnHide();
					}, 2000);
				},
				loadingComplete : function (id) {
					//overlay close
					var self = this;
					clearTimeout(self.overlayTimeout);

					//calback loader box close
					self.objLib.loaderBox.find('h3 span').html(100);
					self.objLib.loaderBox.data('overlay').close();

					//go to chosen cycle item
					if (self.pageCycle !== undefined) {
						self.pageCycle.cycle('destroy');
					}

					self.cycleInit(self.currentIndex);
					var pageIndex = self.checkIfPageAlreadyExists(id);
					if (pageIndex === false) {
						window.console && console.log('[page slider] error; page index false');
					}
					self.pageCycle.cycle(pageIndex);
				},
				setThumbsSliderOnNewPage : function (newPageObj) {
					//Engine.ui.portfolio.startCycle(newPageObj);
					//Engine.ui.portfolio.setContainerInitialOptions(newPageObj);
					Engine.ui.portfolio.generatePortfolioView(newPageObj);
				},
				checkIfPageAlreadyExists : function (id) {
					var self = this;
					var itemToCheck = $('#'+id);
					if(itemToCheck.length)
					{
						//get page index in cycle
						var pages  = self.objLib.pageCycleObj.find('section.box-d');
						var pageIndex = pages.index(itemToCheck);
						return pageIndex;
					}
					return false;
				},
				overlayInit : function () {
					var self = this;
					Engine.ui.loaderOverlay(self.objLib.loaderBox);
				},
				overlayShow : function () {
					var self = this;
					self.objLib.loaderBox.data('overlay').load();
				},
				resizeAction : function () {
					var self = this;
					$(window).resize(function() {
						if (self.pageCycle !== undefined) {
							self.nowResizing = true;
							self.pageCycle.cycle('stop');
							var width = $('.box-c').width();
							self.objLib.pageCycleObj.find('.box-d').width(width);
							self.cycleInit(self.currentIndex);
							self.nowResizing = false;
						}
					});
				},
				supportsHistoryApi : function () {
  					return !!(window.history && history.pushState);
				},
				changeHistoryUrl : function (url) {
					history.pushState(null, null, url);
				},
				historyAction : function () {
					var self = this;
					if (self.supportsHistoryApi()) {
						window.setTimeout(function() {
    						window.addEventListener("popstate", function(e) {
								self.historyGoToPage();
								clearTimeout(self.historyTimeout);
								//safari bug, and cure for fast mad history clickers
								self.historyTimeout = window.setTimeout(function() {
									self.historyGoToPage();
								}, 800);
						    }, false);
						}, 1);
					}
				},
				historyGoToPage : function () {
					var self = this;
					var path = location.pathname;
					var link = path.split('/').pop();
					var currentLink = self.objLib.pageCycleObj.find('.box-d:visible').data('href').split('/').pop();

					if (link !== currentLink) {
						self.objLib.portfolioNavContainer.find('a').removeClass('active');
						var myItem = self.objLib.portfolioNavContainer.find('a[href$="'+link+'"]').addClass('active');
						self.gotoPage(link, myItem.data('id'), true);
					}
				},
				nextPageFunctions : function (page) {
					//less important funtions only, some effects etc.
					//that can be invoked after fade effect

					//portfolio main page
					var collection = page.find('#portfolio-items-list li');
					if (collection.length) {
						if (portfolioProjectsPage === null) {
							//window.console && console.log('[nextPageFunctions] portfolioProjectsPage: '+portfolioProjectsPage);
							portfolioProjectsPage = new Engine.ui.portfolioProjectsPage(page);
						}
						portfolioProjectsPage.iconsEffect('implode');
						Engine.ui.portfolioBackPanel.btnHide();
					}
				},
				setFunctionsOnNewPage : function (myPage) {
					var self = this;

					//very important functions that must be invoked right after load
					self.setThumbsSliderOnNewPage(myPage);

					//if loaded page is portfolio main page or portfolio "others" page
					if (myPage.find('ul.list-i').length) {
						var svgFx1 = new Engine.ui.svgEffects(myPage);

						//portfolio main page init
						if (myPage.find('#portfolio-items-list').length) {
							if (portfolioProjectsPage === null) {
								//window.console && console.log('[setFunctionsOnNewPage] main portfolio page init here');
								portfolioProjectsPage = new Engine.ui.portfolioProjectsPage(myPage);
							}
						}

						//portfolio other projects page init
						if (myPage.find('#portfolio-others-list').length) {
							if (portfolioOthersPage === null) {
								//window.console && console.log('[setFunctionsOnNewPage] otehrs portfolio page init here');
								portfolioOthersPage = new Engine.ui.folderEffects(myPage);
							}
						}
					}
				}
			},
			// *** "more info" portfolio overlay ************************************************* /
			portfolioOverlay : {
				init : function () {
					var self = this;
					self.getScrollbarWidth();
					self.overlayInit();
					self.overlayTrigger();
				},
				objLib : {
					callbackbox : $("#callbackbox"),
					pageBoxWidth : 0,
					pageBoxes : null,
					scrollbarWidth : 0
				},
				overlayInit : function () {
					var self = this;

					var myOpacity = 1;
					if(ua.msie && ua.version < 9)
					{
						myOpacity = 0.5;
					}

					self.objLib.callbackbox.overlay({
						mask: {
							color: '#000',
							loadSpeed: 200,
							opacity: myOpacity,
							innerLayer: true,
							fitOnResizeOrScroll: true
						},
						top : 'center',
						fixed: true,
						closeOnClick: true,
						onBeforeLoad : function () {
							// self.setPageBoxesNewWidth();
							$('body').css({"overflow": "hidden"});
							$('body').addClass('overlayed');
							$('body').css({ 'margin-right' : self.objLib.scrollbarWidth });
						},
						onClose : function () {
							// self.setPageBoxesOldWidth();
							$('body').css({"overflow": "visible"});
							$('body').removeClass('overlayed');
							$('body').css({ 'margin-right' : 0 });
						}
					});
				},
				setPageBoxesNewWidth : function () {
					var self = this;
					self.objLib.pageBoxes = $('.box-d');
					if (self.objLib.pageBoxes.length > 1) {
						var mywidth = window.innerWidth;
						self.objLib.pageBoxWidth = self.objLib.pageBoxes.eq(0).width();
						self.objLib.pageBoxes.css({"width": mywidth});
					}
				},
				setPageBoxesOldWidth : function () {
					var self = this;
					if (self.objLib.pageBoxes.length > 1) {
						self.objLib.pageBoxes.css({"width": $('#page-cycle').width()});
					}
				},
				overlayTrigger : function () {
					var self = this;
					$('#page-cycle').delegate('.overlay-trigger', 'click', function (e) {
						e.preventDefault();

						//self.overlayInit();

						//calculate modal height
						var viewport = $(window).height();
						if (viewport < 550) {
							self.objLib.callbackbox.find(".box-g").height(viewport - 160);
						}
						else
						{
							self.objLib.callbackbox.find(".box-g").height(410);
						}

						var pageObject = $(this).closest('.box-d');

						self.objLib.callbackbox.removeClass();
						self.objLib.callbackbox.addClass('popbox-a modal '+pageObject.attr('id'));

						// get content
						self.objLib.callbackbox.find(".text-area-a").html(pageObject.find('.more-container-a').html());

						//show overlay
						self.objLib.callbackbox.data('overlay').load();
					});
				},
				getScrollbarWidth : function () {
					var self = this;
					// http://benalman.com/projects/jquery-misc-plugins/#scrollbarwidth
					var parent = $('<div style="width:50px;height:50px;overflow:auto"><div/></div>').appendTo('body');
					var child  = parent.children();
					self.objLib.scrollbarWidth = child.innerWidth() - child.height( 99 ).innerWidth();
					parent.remove();
					// window.console && console.log(self.objLib.scrollbarWidth);
				}
			},
			loaderOverlay : function (loaderObj) {
				loaderObj.overlay({
					/*mask: {
						color: '#000',
						loadSpeed: 0,
						opacity: 0,
						innerLayer: false
					},*/
					top : 'center',
					fixed: true,
					speed: 300,
					closeOnClick: false,
					onClose : function () {
						loaderObj.find('h3').html(Engine.translation.dictionary.loading_page_label+': <span>0</span>%');
					}
				});
			},
			// *** back link effect on protfolio pages ************************************************* /
			portfolioBackPanel : {
				objLib : {
					button : $('#portfolio-back-btn'),
					btnLink : $('#portfolio-back-btn').find('a'),
					navContainer : $('#portfolio-nav-container')
				},
				init : function () {
					var self = this;
					self.hoverEffect();
				},
				hoverEffect : function () {
					var self = this;
					var ua = $.browser;

					if(ua.opera)
					{
						// some weird bug in opera
						return false;
					}

					self.objLib.button.hover(function() {
						self.objLib.btnLink.stop();
						self.objLib.btnLink.animate({
							width: 105
						}, 500, 'easeOutCubic', null);
					},
					function() {
						self.objLib.btnLink.stop();
						self.objLib.btnLink.animate({
							width: 1
						},
						{
							duration: 100
						});
					});
				},
				btnShow : function () {
					var self = this;
					self.objLib.button.show();
					self.objLib.navContainer.removeClass('be-a');
				},
				btnHide : function () {
					var self = this;
					self.objLib.button.hide();
					self.objLib.btnLink.removeClass('active');
					self.objLib.navContainer.addClass('be-a');
				}
			},
			// *** portfolio main page, events and effects ************************************************* /
			portfolioProjectsPage :  function (pageObj) {
				this.itemsList = pageObj.find('ul.list-i');
				this.itemsListElements = pageObj.find('ul.list-i li');

				//check if list of items exists
				if (!this.itemsList.length) {
					window.console && console.log('[portfolioProjectsPage] No valid item list');
					return false;
				}

				if(typeof Engine.ui.portfolioProjectsPage._initialized == "undefined"){

					Engine.ui.portfolioProjectsPage.prototype.iconsEffectsInit = function () {
						var t = this;
						window.console && console.log('[portfolioProjectsPage.iconsEffectsInit] initialized');
						t.itemsList.delegate('a', 'click', function (e) {
							e.preventDefault();
							var myli = $(this).parent('li');
							var	timeout = 300;

							t.iconsEffect('explode');
							//Engine.ui.pagesDynamicNavigation.blockClick = true;
							t.itemsListElements.find('a').removeClass('active');
							$(this).addClass('active');
							var url = $(this).attr('href');
							var id = $(this).data('id');

							Engine.ui.portfolioBackPanel.objLib.navContainer.find('a[data-id="'+id+'"]').addClass('active');
							setTimeout( function () { Engine.ui.pagesDynamicNavigation.gotoPage(url, id, false) }, timeout);
						});

					}

					Engine.ui.portfolioProjectsPage.prototype.iconsEffect = function (type) {
						var t = this;
//window.console && console.log('data state: '+t.itemsList.data('state'));
//window.console && console.log('type: '+type);

						var state = t.itemsList.data('state');
						if (state === type || state === undefined) {
							window.console && console.log('[portfolioProjectsPage.iconsEffect] Cannot continue; state = type or state = undefined');
							return false;
						}

						var collection = t.itemsListElements;
						var objCount = collection.length;
						var animSpeed = 600;
						var animEasing = 'easeOutExpo';

						if (type == 'explode') {
							animEasing = 'easeInOutExpo';
						}

						var timeBuff = 0;
						var step = 50;
						var indexArray = range(0, objCount-1);
						indexArray.shuffle();

						collection.each(function(i) {
							var obj = collection.eq(indexArray[i]);
							var realIndex = t.itemsListElements.index(obj);
							var distance = 700;
							var sign = '+';

							var leftOrRight = t.leftOrRight(4, realIndex);
							if (leftOrRight)
							{
								sign = '+';
							}
							else
							{
								sign = '-';
							}

							if (type === 'explode') {
								var opacityValue = 0;
							}
							else
							{
								if (sign === '-')
								{
									sign = '+'
								}
								else
								{
									sign = '-';
								}
								var opacityValue = 1;
							}
							var animSettings = { left: sign+'='+distance, opacity : opacityValue }

							if(i == objCount-1){
								setTimeout(function(){
									obj.animate(animSettings, animSpeed, animEasing, function(){ t.itemsList.data('state', type) });
								}, (100 + timeBuff));
							} else {
								setTimeout(function(){
									obj.animate(animSettings, animSpeed, animEasing);
								}, (100 + timeBuff));
							}
							timeBuff += step;
						});
					}

					Engine.ui.portfolioProjectsPage.prototype.leftOrRight = function (inARow, index) {
						var splitter = inARow/2;
						var index = index + 1;
						var result = index / inARow;

						var check =  (result - Math.floor(result)) * inARow;
						if (check === 0 || check > splitter) {
							//right
							return 1;
						}
						else if(check === splitter && inARow%2)
						{
							//when inARow is odd, in the middle, let blind fate will decide
							return Math.round(Math.random());
						}
						else
						{
							//left
							return 0;
						}
					}

					Engine.ui.portfolioProjectsPage.prototype.init = function () {
						var t = this;
						if (t.itemsList.length && (t.itemsList.attr('id') === 'portfolio-items-list')) {
							t.iconsEffectsInit();
							Engine.ui.portfolioBackPanel.btnHide();
						}
					}

					Engine.ui.portfolioProjectsPage._initialized = true;
				}

				this.init();
			},
			//Portfolio SVG effects
			svgEffects : function (pageObj) {

				this.itemsList = pageObj.find('ul.list-i');
				this.itemsListElements = pageObj.find('ul.list-i li');
				this.svgObjects = new Array();

				//check if list of items exists
				if (!this.itemsList.length) {
					window.console && console.log('[svgEffects] No valid item list');
					return false;
				}

				if(typeof Engine.ui.svgEffects._initialized == "undefined"){

					//create containers
					Engine.ui.svgEffects.prototype.createSvgObjects = function () {
						var t = this;
						t.itemsListElements.each(function (i) {
							var imgContainer = $(this).find('.image-container');
							var svgContainer = $('<div class="svg-container"/>').appendTo(imgContainer);
							//var svgContainer1 = $('<div class="svg-container svg-inner"/>').appendTo(imgContainer);
							//add objects to containers
							var svg = t.createSvg(svgContainer/*, svgContainer1*/);

							t.svgObjects[i] = svg;
						});
					}

					//create svg objects
					Engine.ui.svgEffects.prototype.createSvg = function (obj) {
						var svg = Raphael(obj[0], 243, 240);

						if(obj.closest('ul').hasClass('li-a'))
						{
							//subpage (others) shape
							//need different (biger) size
							svg.setSize(324, 216);
							var myPath = "M197.5,0h-115C36.937,0,0,36.937,0,82.5c0,30.081,16.105,56.393,40.157,70.807L35,176l19.323-15.941C63.116,163.253,72.604,165,82.5,165h115c45.563,0,82.5-36.937,82.5-82.5S243.063,0,197.5,0z";

							//outer circle
							var d = svg.path(myPath);
							d.attr({stroke: "#0f2431", 'stroke-width' : 0, 'stroke-opacity' : 0});
							d.translate(22, 10);
							var translatedPath = d.attr().path.toString();

							//image
							var img = obj.closest('a').find('img');
							var imgSrc = img.attr('src');
							img.hide();
							var image = svg.image(imgSrc, 22, 10, 280, 176);

							//inner circle
							var c = svg.path(translatedPath);
							c.attr({stroke: "#0f2431", 'stroke-width' : 0, 'stroke-opacity' : 0, "clip-rect": translatedPath});

							return [c, d];
						}
						else if(!obj.closest('a').hasClass('misc'))
						{
							//regular shape
							var myPath = "M90.5-0.218C40.518-0.218,0,40.3,0,90.282c0,27.091,11.907,51.397,30.769,67.983l-6.635,28.953l22.617-17.701c12.968,7.176,27.88,11.264,43.75,11.264c49.981,0,90.5-40.518,90.5-90.499C181,40.3,140.481-0.218,90.5-0.218z";

							//outer circle
							var d = svg.path(myPath);
							d.attr({stroke: "#0f2431", 'stroke-width' : 0, 'stroke-opacity' : 0});
							d.translate(30, 10);
							var translatedPath = d.attr().path.toString();

							//image
							var img = obj.closest('a').find('img');
							var imgSrc = img.attr('src');
							window.console && console.log(imgSrc);
							img.hide();
							var image = svg.image(imgSrc, 30, 10, 180, 186);

							//inner circle
							var c = svg.path(translatedPath);
							c.attr({stroke: "#0f2431", 'stroke-width' : 0, 'stroke-opacity' : 0, "clip-rect": translatedPath});

							return [c, d];
						}
						else
						{
							//special shape
							var myPath1 = "M65.837,97.854c-15.505,0-28.075,12.57-28.075,28.075c0,8.402,3.697,15.938,9.546,21.083L45.25,156l7.018-5.493c4.022,2.227,8.647,3.497,13.57,3.497c15.505,0,28.076-12.57,28.076-28.075S81.342,97.854,65.837,97.854z";
							var myPath2 = "M23.495,85.026c6.496,3.594,13.963,5.646,21.912,5.646c25.038,0,45.335-20.297,45.335-45.336C90.742,20.298,70.445,0,45.407,0S0.071,20.298,0.071,45.336c0,13.569,5.969,25.737,15.416,34.046l-3.326,14.515L23.495,85.026z";
							var myPath3 = "M137.208,37.571c-20.833,0-37.721,16.889-37.721,37.722c0,11.29,4.967,21.415,12.826,28.328l-2.768,12.076l9.43-7.381c5.404,2.991,11.618,4.698,18.232,4.698c20.832,0,37.721-16.889,37.721-37.721C174.929,54.459,158.04,37.571,137.208,37.571z";

							var opts = {stroke: "#0f2431", 'stroke-width' : 0, 'stroke-opacity' : 0};

							var translateX = 35;
							var translateY = 10;

							//outer
							var d = svg.path(myPath1);
							d.translate(translateX, translateY);
							var translatedPath1 = d.attr().path.toString();

							var da = svg.path(myPath2);
							da.translate(translateX, translateY);
							var translatedPath2 = da.attr().path.toString();

							var db = svg.path(myPath3);
							db.translate(translateX, translateY);
							var translatedPath3 = db.attr().path.toString();

							var set1 = svg.set();
							set1.push(d);
							set1.push(da);
							set1.push(db);
							set1.attr(opts);

							//image
							var img = obj.closest('a').find('img');
							var imgSrc = img.attr('src');
							img.hide();
							var image = svg.image(imgSrc, 35, 10, 175, 156);

							//inner
							var c = svg.path(translatedPath1);
							c.attr({"clip-rect": translatedPath1});
							var ca = svg.path(translatedPath2);
							ca.attr({"clip-rect": translatedPath2});
							var cb = svg.path(translatedPath3);
							cb.attr({"clip-rect": translatedPath3});

							var set = svg.set();
							set.push(c);
							set.push(ca);
							set.push(cb);
							set.attr(opts);

							return [set, set1];
						}
					}

					Engine.ui.svgEffects.prototype.bindEffects = function () {
						var t = this;
						t.itemsList.delegate('a', 'hover', function (e) {
							var index = t.getObjIndex($(this).parent());
							var overlayContainer = $(this).find('p.more-c');
							if($.browser.msie && $.browser.version < 9)
							{
								overlayContainer.css({ display : 'none' });
								if(e.type === 'mouseenter')
								{
									overlayContainer.css({ display : 'block' });
								}
								else
								{
									overlayContainer.css({ display : 'none' });
								}
							}
							else
							{
								if($(this).hasClass('misc'))
								{
									var optsOuter = {'stroke-opacity': 0.4, 'stroke-width': 12}
									var optsInner = {'stroke-opacity': 0.2, 'stroke-width': 10}
								}
								else
								{
									var optsOuter = {'stroke-opacity': 0.4, 'stroke-width': 20}
									var optsInner = {'stroke-opacity': 0.2, 'stroke-width': 16}
								}

								t.svgObjects[index][0].stop();
								t.svgObjects[index][1].stop();
								if(e.type === 'mouseenter')
								{
									t.svgObjects[index][0].animate(optsOuter, 300, "<");
									t.svgObjects[index][1].animate(optsInner, 200, ">");
								}
								else
								{
									t.svgObjects[index][0].animate({'stroke-opacity': 0, 'stroke-width': 0}, 180, "<");
									t.svgObjects[index][1].animate({'stroke-opacity': 0, 'stroke-width': 0}, 150, ">");
								}
							}
						});
					}

					Engine.ui.svgEffects.prototype.getObjIndex = function (itemToCheck) {
						var t = this;
						var collection = t.itemsListElements;
						var index = collection.index(itemToCheck);
						return index;
					}

					Engine.ui.svgEffects.prototype.markObject = function () {
						var t = this;
						t.itemsList.data('svgmarked', "1");
					}

					Engine.ui.svgEffects.prototype.init = function () {
						var t = this;

						//check if effects has been added to this list before
						if (t.itemsList.data('svgmarked') === '1') {
							window.console && console.log('[svgEffects] Effects has been added to this list before');
							return false;
						}

						if($.browser.msie && $.browser.version < 9)
						{

						}
						else
						{
							t.createSvgObjects();
						}
						t.bindEffects();
						t.markObject();
					}

					Engine.ui.svgEffects._initialized = true;
				}

				this.init();
			},
			folderEffects : function (pageObj) {
				this.clickBlocked = false;
				this.pageObj = pageObj;
				this.itemsList = pageObj.find('ul.list-i');
				this.itemsListElements = pageObj.find('ul.list-i li');
				this.itemsListElementsLinks = pageObj.find('ul.list-i li > a');
				this.keyboardNavigBox = pageObj.find('#kyboard-navig-box');
				this.showItem = false;
				this.imageMarkHeight = 13;
				this.imageMarkWidthHalf = 13;
				this.baseBodyHeight = $('body').height();
				this.factor1 = 30;
				this.factor2 = 40;

				//check if list of items exists
				if (!this.itemsList.length) {
					window.console && console.log('[folderEffects] No valid item list');
					return false;
				}

				if(typeof Engine.ui.folderEffects._initialized == "undefined"){

					Engine.ui.folderEffects.prototype.runExpose = function (item) {
						var self = this;
						var api = item.expose({
							opacity : 0.01,
							color : '#fff',
							loadSpeed : 200,
							innerLayer: false,
							fitOnResizeOrScroll: false,
							onBeforeLoad: function(event) {
								self.overlflowFix('addFix');
								self.itemsListElementsLinks.not(item).fadeTo(200,0.2);
								item.fadeTo(200, 1);
								item.addClass('exposed');
								var offset = item.offset();
								self.folderInit(item, offset);
							},
							onBeforeClose : function (event) {
								if (self.showItem !== false) {
									self.setHelper();
								}
								else
								{
									self.itemsListElementsLinks.fadeTo(200, 1);
								}
								item.removeClass('exposed');
								self.folderHide(item);
							},
							onClose : function () {
								self.overlflowFix('removeFix');
								self.unsetHelper();
								if (self.showItem !== false) {
									self.runExpose(self.itemsListElementsLinks.eq(self.showItem));
									self.showItem = false;
								}
							}
						});
						return api;
					}

					Engine.ui.folderEffects.prototype.overlflowFix = function (mode) {
						//if ($.browser.msie && $.browser.version == 9) {
							if (mode === 'addFix') {
								var cssValue = 'hidden';
							}
							else if(mode === 'removeFix')
							{
								var cssValue = 'visible';
							}
							$('body').css('overflow-x', cssValue);
						//}
					}

					Engine.ui.folderEffects.prototype.folderInit = function (item, offset) {
						var self = this;
						var id = item.attr('href');
						var myObj = $(id);
						var itemHeight = item.height();

						if (myObj.length) {
							var topPos = offset.top + itemHeight - self.factor1;
							myObj.css('top', topPos);
							var bgPos = offset.left + (item.width()/2) - self.imageMarkWidthHalf;

							if (self.showItem !== false)
							{
								self.scrollToItem(item);
							}

							myObj.find('.project-section-inner-a').append(self.keyboardNavigBox);

							myObj.css({'backgroundPosition': bgPos+'px 0'});

							myObj.slideDown(600, function () {
								self.clickBlocked = false;

								self.setHeights(myObj, itemHeight, topPos);

								if (self.showItem === false)
								{
									self.scrollToItem(item);
								}
							});
						}
					}

					Engine.ui.folderEffects.prototype.scrollToItem = function (item) {
						$.scrollTo(item, 300, {offset : { left : 0, top : -10 }, axis: 'y'});
					}

					Engine.ui.folderEffects.prototype.setHeights = function (myObj, itemHeight, topPos) {
						var self = this;
						var innerSection = myObj.find('.project-section-inner-a');
						var padding = innerSection.css('paddingBottom').replace('px', '');

						//check if item + folder ale from top to bottom of window (nice effect)
						var wHeight = $(window).height();
						var currentHeight = myObj.height();
						var countResult1 = wHeight - (currentHeight + itemHeight - self.factor1);
						var sufficientCurrentHeight = 0; // final height of folder that is good enough to be from top to bottom (with item)

						if (countResult1 > 0) {
							currentHeight += countResult1;
						}
						else
						{
							sufficientCurrentHeight = wHeight - itemHeight + self.factor1;
						}

						//var finalHeight = 0; // final height of document // no need anymore - set #exposeMask from top to folder start (equal to topPos + self.imageMarkHeight)
						var innerFinalHeight = 0; //final height of inner project section

						//count final height of folder, exposeMask
						if (self.baseBodyHeight > currentHeight + topPos) {
							var heightIncrement = self.baseBodyHeight - (currentHeight + topPos);
							//finalHeight = self.baseBodyHeight;
							innerFinalHeight = currentHeight + heightIncrement;
						}
						else
						{
							//finalHeight = currentHeight + topPos;
							innerFinalHeight = currentHeight;

							if (sufficientCurrentHeight) {
								innerFinalHeight = sufficientCurrentHeight;
								//window.console && console.log('sufficientInnerHeight: '+sufficientCurrentHeight);
							}
						}
						//window.console && console.log('finalHeight: '+finalHeight);
						innerFinalHeight = innerFinalHeight - padding - self.factor2;
						innerSection.css('min-height', innerFinalHeight);
						$('#exposeMask').height(topPos + self.imageMarkHeight);
					}

					Engine.ui.folderEffects.prototype.getDocHeight = function () {
    					var D = document;
    					return Math.max(
    					    Math.max(D.body.scrollHeight, D.documentElement.scrollHeight),
    					    Math.max(D.body.offsetHeight, D.documentElement.offsetHeight),
    					    Math.max(D.body.clientHeight, D.documentElement.clientHeight)
    					);
					}

					Engine.ui.folderEffects.prototype.folderHide = function (item) {
						var id = item.attr('href');
						$(id).slideUp(300);
					}

					Engine.ui.folderEffects.prototype.setHelper = function () {
						var self = this;
						var helperLay = $('#helper-lay');
						if (!helperLay.length){
							helperLay = self.addHelper();
						}

						var height = self.getDocHeight();
						helperLay.height(height);
						helperLay.show();
					}

					Engine.ui.folderEffects.prototype.unsetHelper = function () {
						var helperLay = $('#helper-lay');
						helperLay.slideUp(500);
					}

					Engine.ui.folderEffects.prototype.addHelper = function () {
						return $('<div id="helper-lay">').appendTo($('body'));
					}

					Engine.ui.folderEffects.prototype.getNextItem = function () {
						var self = this;
						var itemToCheck = self.itemsListElements.find('a.exposed').parent();
						var activeIndex = self.itemsListElements.index(itemToCheck) + 1;
						if(activeIndex === self.itemsListElements.length)
						{
							activeIndex = 0;
						}
						return activeIndex;
					}

					Engine.ui.folderEffects.prototype.getPrevItem = function () {
						var self = this;
						var itemToCheck = self.itemsListElements.find('a.exposed').parent();
						var activeIndex = self.itemsListElements.index(itemToCheck) - 1;
						if(activeIndex < 0)
						{
							activeIndex = self.itemsListElements.length - 1;
						}
						return activeIndex;
					}

					Engine.ui.folderEffects.prototype.prevExpose = function () {
						var self = this;
						self.showItem = self.getPrevItem();
						self.closeExpose();
					}

					Engine.ui.folderEffects.prototype.nextExpose = function () {
						var self = this;
						self.showItem = self.getNextItem();
						self.closeExpose();
					}

					Engine.ui.folderEffects.prototype.closeExpose = function () {
						$.mask.close();
					}

					Engine.ui.folderEffects.prototype.reArrangeDOM = function () {
						var self = this;
						var sections = self.pageObj.find('.project-section-a');
						sections.wrapInner('<div class="project-section-inner-a" />');
						sections.wrapInner('<div class="project-section-inner" />');
						sections.appendTo('body');
					}

					Engine.ui.folderEffects.prototype.resizeAction = function () {
						var self = this;
						$(window).resize(function() {
							var item = self.itemsListElements.find('.exposed');
							if (item.length) {
								var id = item.attr('href');
								var myObj = $(id);
								var offset = item.offset();
								if (myObj.length) {
									var bgPos = offset.left + (item.width()/2) - self.imageMarkWidthHalf;
									myObj.css({'backgroundPosition': bgPos+'px 0'});
									$('#exposeMask').width($(window).width());
								}
							}
						});
					}

					Engine.ui.folderEffects.prototype.keyboardNavigation = function () {
						var self = this;
						$(window).keydown(function(event){
							if(!self.clickBlocked && self.pageObj.is(':visible'))
							{
								//Left
								if(event.keyCode == '188')
								{
									self.clickBlocked = true;
									self.prevExpose();
        				        }
								//Right
								if(event.keyCode == '190')
								{
									self.clickBlocked = true;
									self.nextExpose();
								}
							}
        		    	});
					}

					Engine.ui.folderEffects.prototype.exposeInit = function () {
						var self = this;
						self.itemsListElements.delegate('a', 'click', function(e) {
							e.preventDefault();
							var item = $(this);

							// perform exposing for the clicked element
							self.runExpose(item);
						});
					}

					Engine.ui.folderEffects.prototype.keyboardBoxTriggerInit = function () {
						var self = this;
						self.keyboardNavigBox.delegate('a', 'click', function (e) {
							e.preventDefault();
							var box = $(this).closest('div.box-j').find('div.hint-container');

							var trigger = $(this);

							var settings = {
								speed: 300,
								easing : "swing",
								onComplete : function () {
									if ($(this).is(':hidden')) {
										trigger.addClass('hidden');
										trigger.html(trigger.data('panel-hidden-text'));
									}
									else
									{
										trigger.removeClass('hidden');
										trigger.html(trigger.data('default-text'));
									}
								}
							}
							box.fadeSliderToggle(settings);
						});
					}

					Engine.ui.folderEffects.prototype.init = function () {
						var self = this;
						self.reArrangeDOM();
						self.exposeInit();
						self.resizeAction();
						self.keyboardNavigation();
						self.keyboardBoxTriggerInit();
					}

					Engine.ui.folderEffects._initialized = true;
				}

				this.init();
			},
			contactPageEffects : function (pageObj) {
				this.pageObj = pageObj;
				this.tabsObj = $("ul.tabs-a");
				this.callbackbox = $('#callbackbox');
				this.loaderBox = $('#loadercallbackbox');

				this.currencyBox = $('.currency-box-a');

				this.budgetDataSet = 1;

				//set objects
				  //tabs slider
				this.tabsContainer = $('#contact-nav-container');
				this.tabsPanesContainer = pageObj.find('.panes');
				this.workSpanSelect = pageObj.find('#work-span');
				this.tabsSlider = null;
				this.tabsSliderHandle = null;
				this.tabsSliderHandleWidth = 0;
				this.tabsSliderWidth = 0;
				this.tabsSliderObj = null;
				this.tabsPanelSlider = null;

				  //budget slider
				this.budgetFieldsContainer = $('#budget-panel');
				this.budgetSlider = null;
				this.budgetSliderHandle = null;
				this.budgetSliderHandleWidth = 0;
				this.budgetSliderWidth = 0;
				this.budgetSliderObj = null;
				this.budgetPanelSlider = null;

				if(typeof Engine.ui.contactPageEffects._initialized == "undefined"){

					Engine.ui.contactPageEffects.prototype.tabsInit = function () {
						var self = this;
						var init = 1;

						if (self.tabsPanesContainer.find('.tab-pane').length) {
							self.tabsObj.tabs("div.panes > section.tab-pane", {
								tabs : 'a',
								effect : 'fade',
								current : '',
								history : true,
								fadeInSpeed : 0,
								fadeOutSpeed : 0,
								onBeforeClick: function(event, tabIndex) {
									//window.console && console.log('tabIndex');
									var currentTab = this.getTabs().eq(tabIndex);

									var currentPane = this.getPanes().eq(tabIndex);
									var paneContainerHeight = currentPane.height();
									if (!init) {
										self.setTabPanesContainerHeight(paneContainerHeight);
									}
									init = 0;
									currentPane.css({'position' : 'absolute'});

									//set current tab (must be parent of 'a')
									self.tabsObj.find('li').removeClass('active');
									currentTab.parent().addClass('active');
									self.moveSlider(self.tabsSliderObj, currentTab.parent());
								},
								onClick : function (event, tabIndex) {
									var currentPane = this.getPanes().eq(tabIndex);
									currentPane.css({'position' : 'relative'});
									self.tabsPanesContainer.css({'height' : 'auto'});
									self.setOptionsVAlign();
								}
							});
							var api = self.tabsObj.data("tabs");
							var conf = api.getConf();
							conf.fadeInSpeed = 500;
							conf.fadeOutSpeed = 500;
						}
					}

					Engine.ui.contactPageEffects.prototype.setFormsFocusEffects = function (item) {
						Engine.forms.setFocusEffect(pageObj.find('form.form-a'));
					}

					Engine.ui.contactPageEffects.prototype.moveSlider = function (sliderObject, destinationObject, onAfter) {
						var self = this;
						if(sliderObject.slider.length)
						{
							var destinationOffset = destinationObject.position();
							var destinationWidth = destinationObject.width();

							//slider and handler can have different width so:

							//slider settings
							var centerDifferences = (destinationWidth - sliderObject.width) / 2;
							var path = destinationOffset.left + centerDifferences;
							var animSettings = { left: path }

							//handler settings
							var hCenterDifferences = (destinationWidth - sliderObject.handleWidth) / 2;
							var hPath = destinationOffset.left + hCenterDifferences;
							var hAnimSettings = { left: hPath }
							if (typeof onAfter == "undefined") {
								onAfter = false;
							}

							sliderObject.slider.animate(animSettings, 300, 'swing', onAfter);
							sliderObject.handler.animate(hAnimSettings, 300, 'swing', onAfter);
						}
					}

					Engine.ui.contactPageEffects.prototype.setTabPanesContainerHeight = function (newHeight, duration) {
						var self = this;
						if (typeof newHeight == "undefined") {
							var api = $("ul.tabs-a").data("tabs");
							newHeight = api.getCurrentPane().height();
						}
						if (typeof duration == "undefined") {
							duration = 500;
						}

						self.tabsPanesContainer.animate({height : newHeight}, duration, 'swing');
					}

					//Generate sliders and wrappers for Tabs
					Engine.ui.contactPageEffects.prototype.generateTabSliders = function () {
						var self = this;

						if (self.tabsPanesContainer.find('.tab-pane').length) {
							self.tabsObj.wrap('<div class="tabs-slider-wrapper"/>').wrap('<div class="tabs-slider-inner-wrapper"/>');
							self.tabsPanelSlider = $('.tabs-slider-inner-wrapper');
							self.tabsSlider = $('<p id="slider-1" class="slider-a tabs-drag">Slide to unlock ;)</p>').insertBefore(self.tabsObj);
							self.tabsSliderHandle = $('<p id="slider-1-handle" class="slider-handler-a tabs-drag">Handler</p>').insertBefore(self.tabsObj);
							self.tabsSliderHandleWidth = self.tabsSliderHandle.width();
							self.tabsSliderWidth = self.tabsSlider.width();
							self.tabsSliderObj = { slider : self.tabsSlider, handler : self.tabsSliderHandle, width : self.tabsSliderWidth, handleWidth : self.tabsSliderHandleWidth };

							//drag n drop init
							self.tabsSlidersDnD();
						}
					}

					//Generate sliders and other objects for budget
					//Set behaviour to radio-like slider
					Engine.ui.contactPageEffects.prototype.generateBudgetSliders = function () {
						var self = this;

						var header = self.budgetFieldsContainer.find('h3');
						var codeToInsert = '<div class="fgroup-b" id="budget-panel-container">\
												<div id="budget-panel-slider">\
													<p id="slider-2" class="slider-b drag">Slide to unlock ;)</p>\
													<p id="slider-2-handle" class="slider-handler-b drag">Handler</p>\
													<ul class="list-k">\
														<li class="active">\
															<a><span class="item-content">'+header.html()+'</span></a>\
														</li>';

						self.budgetFieldsContainer.find('p.field-b').each(function (i) {
							var labelObj = $(this).find('label');
							codeToInsert +=	'<li class="item"><a data-input-id="'+labelObj.attr('for')+'"><span class="item-content">'+labelObj.html()+'</span></a></li>';
						});

						codeToInsert +=	'</ul>\
										</div>\
											<p id="budget-error-container" class="error"></p>\
										</div>';

						self.budgetFieldsContainer.after(codeToInsert);

						self.budgetPanelSlider = $('#budget-panel-slider');
						self.budgetSlider = $('#slider-2');
						self.budgetSliderHandle = $('#slider-2-handle');
						self.budgetSliderHandleWidth = self.budgetSliderHandle.width();
						self.budgetSliderWidth = self.budgetSlider.width();
						self.budgetSliderObj = { slider : self.budgetSlider, handler : self.budgetSliderHandle, width : self.budgetSliderWidth, handleWidth : self.budgetSliderHandleWidth }

						//hide radio buttons
						self.budgetFieldsContainer.hide();

						//events, drag n drop, and labels valign init
						self.setOptionsVAlign();
						self.budgetSlidersEvents();
						self.budgetSlidersDnD();
					}

					Engine.ui.contactPageEffects.prototype.budgetSlidersEvents = function () {
						var self = this;
						self.budgetPanelSlider.delegate('.item a', 'click', function (e) {
							e.preventDefault();
							var item = $(this).parent();
							self.markBudget(item);
						});
					}

					Engine.ui.contactPageEffects.prototype.budgetSlidersDnD = function () {
						var self = this;
						var dragContainer = self.budgetPanelSlider;
						var dragContainerWidth = self.budgetPanelSlider.width();
						//drag
						$('.drag').drag('init', function(){
								$(this).addClass('dragging');
								return self.budgetSlider.add(self.budgetSliderHandle);
							}).drag('dragend ', function(){
								$(this).removeClass('dragging');
							}).drag(function( ev, dd ){
								$(this).css({
									left: function () {
										if (dd.offsetX < -11) {
											return -11;
										}
										else if(dd.offsetX > dragContainerWidth - self.budgetSliderWidth + 11)
										{
											return dragContainerWidth - self.budgetSliderWidth + 11;
										}
										else
										{
											return dd.offsetX;
										}
									}
								}
							);
						},{ relative: true });
						//drop
						dragContainer.find('li').drop(function(ev, dd){
							if($(dd.drag).attr('id') == self.budgetSlider.attr('id'))
							{
								var item = $(this);
								if (!item.hasClass('item')) {
									item = dragContainer.find('li.item').eq(0);
								}
								self.markBudget(item);
							}
						});
						$.drop({ mode:"middle" });
					}

					Engine.ui.contactPageEffects.prototype.tabsSlidersDnD = function () {
						var self = this;
						var dragContainer = self.tabsPanelSlider;
						var dragContainerWidth = self.tabsPanelSlider.width();
						//drag
						$('.tabs-drag').drag('init', function(){
								$(this).addClass('dragging');
								return self.tabsSlider.add(self.tabsSliderHandle);
							}).drag('dragend ', function(){
								$(this).removeClass('dragging');
							}).drag(function( ev, dd ){
								$(this).css({
									left: function () {
										if (dd.offsetX < 0) {
											return 0;
										}
										else if(dd.offsetX > dragContainerWidth - self.tabsSliderWidth + 26)
										{
											return dragContainerWidth - self.tabsSliderWidth + 26;
										}
										else
										{
											return dd.offsetX;
										}
									}
								}
							);
						},{ relative: true });
						//drop
						dragContainer.find('li').drop(function(ev, dd){
							if($(dd.drag).attr('id') == self.tabsSlider.attr('id'))
							{
								var collection = self.tabsObj.find('li');
								var index = collection.index($(this));
								var api = self.tabsObj.data("tabs");
								var currIndex = api.getIndex();

								if (currIndex === index) {
									self.moveSlider(self.tabsSliderObj, $(this));
								}
								else
								{
									//self.tabsObj.find('li').eq(index).hide();
									var href = self.tabsObj.find('a').eq(index).attr('href');
									location.href = href;
									api.click(index);
								}
							}
						});
						$.drop({ mode:"middle" });
					}

					Engine.ui.contactPageEffects.prototype.markBudget = function (item) {
						var self = this;

						var fieldId = item.find('a').data('input-id');

						self.budgetPanelSlider.find('li').removeClass('active');
						item.addClass('active');
						self.moveSlider(self.budgetSliderObj, item);

						var radioObjects = self.budgetFieldsContainer.find('input[type="radio"]');
						radioObjects.removeAttr('checked');

						var radioToCheck = self.budgetFieldsContainer.find('input#'+fieldId);
						radioToCheck.attr('checked', 'true');

						//check if validation initialized
						if(Engine.forms.fullFormValidator !== 0)
						{
							$("#business-query-form").validate().element('input[name="budget"]');
						}

						$.cookie('budgetPosition', fieldId);
					}

					Engine.ui.contactPageEffects.prototype.setBudgetInit = function () {
						var self = this;
						var budgetItemId = $.cookie('budgetPosition');
						if (budgetItemId !== null)
						{
							var item = $('#budget-panel-slider .list-k li a[data-input-id="'+budgetItemId+'"]').parent();
							self.markBudget(item);
							//var item = $('#budget-panel-slider .list-k li a[data-input-id="'+budgetItemId+'"]');
							//alert(item);
							//item.trigger('click');
						}
					}

					Engine.ui.contactPageEffects.prototype.setOptionsVAlign = function () {
						var self = this;
						var panel = $('#budget-panel-slider');
						if (panel.is(':visible')) {
							panel.find('ul span.item-content').css({height: 'auto', paddingTop: '0'}).vAlign();
						}
					}

					Engine.ui.contactPageEffects.prototype.fileAttachmentEffects = function () {
						var self = this;
						self.pageObj.find('p.field-d').wrap('<div class="field-d-wrapper"/>');
						var btnContainer = $('p.add-file-btn');
						btnContainer.wrap('<div class="add-file-wrapper"/>');

						var settings = {
							speed: 300,
							easing : "swing",
							onComplete : false
						}

						//show file attachement
						self.pageObj.find('p.add-file-btn a').click(function (e) {
							e.preventDefault();
							settings.onComplete = function () {
									if (!self.pageObj.find('div.field-d-wrapper:hidden').length) {
										btnContainer.fadeOut(500);
									}
							}
							self.pageObj.find('div.field-d-wrapper:hidden').eq(0).fadeSliderToggle(settings);
						});

						//hide file attachement
						self.pageObj.find('div.field-d-wrapper').delegate('span.close', 'click', function (e) {
							e.preventDefault();
							btnContainer.fadeIn(500);
							$(this).closest('div').fadeSliderToggle(settings);
						});
					}

					Engine.ui.contactPageEffects.prototype.loaderInit = function () {
						var self = this;
						Engine.ui.loaderOverlay(self.loaderBox);
						self.loaderBox.find('h3').html(Engine.translation.dictionary.loading_content_label);
					}

					Engine.ui.contactPageEffects.prototype.budgetOverlayInit = function () {
						var self = this;
						self.loaderInit();

						self.pageObj.delegate('.budget-overlay-trigger', 'click', function (e) {
							e.preventDefault();

							var url = $(this).attr('href').replace('#', ' #');

							//calculate modal height
							var viewport = $(window).height();
							if (viewport < 550) {
								self.callbackbox.find(".box-g").height(viewport - 160);
							}
							else
							{
								self.callbackbox.find(".box-g").height(218);
							}

							//show loader (if needed)
							var loaderTimeout = setTimeout(function () { self.loaderBox.data('overlay').load(); }, 100);

							// get content
							var container = $('<div></div>').load(url, function() {
								//hide loader
								self.loaderBox.data('overlay').close();
								clearTimeout(loaderTimeout);

								//set content
								self.callbackbox.find(".text-area-a").html(container);

								//show overlay
								self.callbackbox.data('overlay').load();
							});
						});
					}

					Engine.ui.contactPageEffects.prototype.budgetDataChangeEvent = function () {
						var self = this;
						self.budgetDataChangeInit();
						self.workSpanSelect.change(function (e) {
							self.budgetDataChangeInit();
						});
					}

					Engine.ui.contactPageEffects.prototype.budgetDataChangeInit = function () {
						var self = this;
						var dataSet = self.workSpanSelect.find(':selected').data('set');
						if (dataSet && dataSet !== self.budgetDataSet) {
							self.budgetDataSet = dataSet;
							self.budgetDataChange();
						}
					}

					Engine.ui.contactPageEffects.prototype.budgetDataChange = function () {
						var self = this;

						var dataSet = Engine.translation.dictionary.forms.budget_data_sets['set'+self.budgetDataSet];
						var dataListItems = $('#budget-panel-slider .list-k li');
						var originalDataListItems = self.budgetFieldsContainer.find('p');

						$.each(dataSet, function (i) {
							var item = dataSet[i];

							//change genrated budget
							var textContainer = dataListItems.eq(i).find('span');
							textContainer.html(item);//

							//change original budget (radiobuttons)
							var clearItem = item.replace('<br/>', ' ');
							clearItem = clearItem.replace('&nbsp;', ' ');
							if (i === 0)
							{
								self.budgetFieldsContainer.find('h3').html(clearItem);
							}
							else
							{
								var listItem = originalDataListItems.eq(i-1);
								listItem.find('input[type="radio"]').attr('value', clearItem);
								listItem.find('label').html(clearItem);
							}
						});

						//vertical align new labels
						self.setOptionsVAlign();

						//do currency count
						if (self.currencyBox.length) {
							var obj = self.currencyBox.find('abbr.active');
							self.countCurrency(obj);
						}
					}

					Engine.ui.contactPageEffects.prototype.countCurrency = function (obj) {
						var self = this;

						var values = self.pageObj.find('.list-k span.curr-value');

						var currencyRate = obj.data('currencyRate');
						var currencySymbol = obj.html();

						values.each(function () {
							var currBaseValue = $(this).data('currencyValue');
							var currCountedValue = currBaseValue * currencyRate;

							$(this).next().html(currencySymbol);
							var formattedNumber = number_format(currCountedValue, 0, '.', ' ')
							$(this).html(formattedNumber);

							//update radio inputs
							var radioID = $(this).closest('a').data('inputId');
							$('#'+radioID).val(formattedNumber+' '+currencySymbol);
						});

						self.currencyBox.find('abbr').removeClass('active');
						obj.addClass('active');
						$.cookie('currencyId', obj.attr('id'));
						//alert($.cookie('currencyId'));
					}

					Engine.ui.contactPageEffects.prototype.countCurrencyInit = function () {
						var self = this;

						var currencyId = $.cookie('currencyId');
						if (currencyId !== null && self.currencyBox.length)
						{
							self.countCurrency($('#'+currencyId));
						}

						self.currencyBox.delegate('abbr', 'click', function (e) {
							e.preventDefault();
							if (!$(this).hasClass('active')) {
								self.countCurrency($(this));
							}
						});
					}

					Engine.ui.contactPageEffects.prototype.init = function () {
						var self = this;
						//genrate or transform DOM elements
						self.generateTabSliders();
						self.fileAttachmentEffects();
						self.setFormsFocusEffects();
						self.generateBudgetSliders();
						self.budgetOverlayInit();
						self.countCurrencyInit();
						self.setBudgetInit();
						self.tabsInit();
						self.budgetDataChangeEvent();
					}

					Engine.ui.contactPageEffects._initialized = true;
				}

				this.init();
			},
			faqEffects : function (pageObj) {

				this.pageObj = pageObj;
				this.nav = pageObj.find('.box-l nav');
				this.navBg = false;
				this.summaryList = pageObj.find('ol.list-n');
				this.summaryListItems = pageObj.find('ol.list-n li');
				this.contentContainer = pageObj.find('.content-container');
				this.topButton = $('.top-button');
				this.startAnimTimeouts = [];
				this.animObjQueue = false;
				this.mainSVG = false;
				this.arrowSVG = {
						svgdata: 'M 0,10.936 4.868,6.069 9.735,10.936',
						obj: false
					};
				this.svgArrowsData = [
					{
						x: 551,
						y: 5,
						rotateDeg : 90,
						color: '#1e62bf',
						obj : false
					},
					{
						x: 455,
						y: 170,
						rotateDeg : -112,
						color: '#61c1ff',
						obj : false
					},
					{
						x: 289,
						y: 287,
						rotateDeg : 102,
						color: '#2cc133',
						obj : false
					},
					{
						x: 746,
						y: 275,
						rotateDeg : 98,
						color: '#a7df11',
						obj : false
					},
					{
						x: 664,
						y: 415,
						rotateDeg : -138,
						color: '#e9c310',
						obj : false
					},
					{
						x: 402,
						y: 496,
						rotateDeg : -85,
						color: '#e63100',
						obj : false
					}
				];

				this.svgObjectsData = [
					{
						svgdata: 'M378,66c0,0,73.028-45.042,164.325-52.533c-0.258-6.83,5.069-12.575,11.899-12.833c5.513-0.208,10.318,3.222,12.103,8.145',
						color: '#1e62bf',
						clipRectInit: "189,0,189,66",
						clipRectFinal: "378,0,189,66",
						obj: false
					},
					{
						svgdata: 'm 544,18 c 1.712,5.0510003 6.586,8.5980003 12.188,8.3860003 6.83,-0.258 12.157,-6.004 11.899,-12.8340003 55.604,0.249 117.111,16.27200025 171.943,64.468',
						color: '#61c1ff',
						clipRectInit: "347,12,196,67",
						clipRectFinal: "544,12,196,67",
						obj: false
					},
					{
						svgdata: 'm 448,178 c -0.559,-6.812 4.51,-12.786004 11.322,-13.345004 5.498,-0.452 10.45,2.765 12.45,7.604004 94.853,-41.217004 152.683,-78.583004 152.683,-78.583004',
						color: '#61c1ff',
						clipRectInit: "625,93,177,85",
						clipRectFinal: "446,93,177,85",
						obj: false
					},
					{
						svgdata: 'm 473,178 c 0.559,6.812 -4.51,12.786 -11.322,13.345 -5.587,0.458 -10.612,-2.871 -12.545,-7.841 -64.029,26.258 -143.181,54.51 -232.345,72.195',
						color: '#2cc133',
						clipRectInit: "474,178,257,78",
						clipRectFinal: "217,178,257,78",
						obj: false
					},
					{
						svgdata: 'm 304,301 c -2.696,6.281 -9.973,9.186 -16.253,6.49 -5.151,-2.211 -8.032,-7.505 -7.413,-12.803 -42.388,-15.00301 -62.5,-39.00001 -62.5,-39.00001',
						color: '#2cc133',
						clipRectInit: "131,256,87,53",
						clipRectFinal: "218,256,87,53",
						obj: false
					},
					{
						svgdata: 'm 283,290 c 2.696,-6.281 9.973,-9.186 16.254,-6.49 5.069,2.175 7.938,7.336 7.439,12.548 20.05001,7.058 47.82701,10.789 77.88401,7.391',
						color: '#a7df11',
						clipRectInit: "181,281,102,25",
						clipRectFinal: "283,281,102,25",
						obj: false
					},
					{
						svgdata: 'm 762,289 c -2.696,6.280999 -9.973,9.185999 -16.253,6.489999 -5.151,-2.211 -8.032,-7.504999 -7.413,-12.802999 -83.073,-0.962 -158.000997,26.284999 -158.000997,26.284999',
						color: '#a7df11',
						clipRectInit: "399,281,182,29",
						clipRectFinal: "581,281,182,29",
						obj: false
					},
					{
						svgdata: 'm 739,279 c 2.696,-6.281 9.973,-9.186 16.254,-6.49 5.069,2.175 7.938,7.336 7.439,12.548 50.868,4.675 96.383,48.676 108.383,65.676003',
						color: '#e9c310',
						clipRectInit: "606,271,133,81",
						clipRectFinal: "739,271,133,81",
						obj: false
					},
					{
						svgdata: 'm 751,368 c 0,0 -37.27,12.901 -72.979,46.855 4.829,4.838 4.82,12.672 -0.017,17.501 -3.968,3.96 -9.953,4.667 -14.641,2.122',
						color: '#e9c310',
						clipRectInit: "752,369,88,68",
						clipRectFinal: "664,369,88,68",
						obj: false
					},
					{
						svgdata: 'm 675,412 c -4.653,-2.401 -10.511,-1.656 -14.415,2.242 -4.837,4.829 -4.846,12.664 -0.017,17.50201 -17.243,20.781 -32.338,48.395 -40.503,81.65999',
						color: '#e67c00',
						clipRectInit: "675,410,55,103",
						clipRectFinal: "620,410,55,103",
						obj: false
					},
					{
						svgdata: 'm 529,536 c 0,0 -47.682,-22.288 -108.63601,-30.039 -0.758,6.793 -6.879,11.683 -13.672,10.925 -5.571996,-0.622 -9.863996,-4.853 -10.806996,-10.103',
						color: '#e67c00',
						clipRectInit: "530,506,136,30",
						clipRectFinal: "394,506,136,30",
						obj: false
					},
					{
						svgdata: 'm 420,502 c -1.034,-5.132 -5.277,-9.239 -10.759,-9.851 -6.793,-0.758 -12.915,4.131 -13.674,10.925 -48.77,-2 -105.263,7.762 -152.767,43.786',
						color: '#e63100',
						clipRectInit: "421,491,179,57",
						clipRectFinal: "243,491,179,57",
						obj: false
					}
				]

				if(typeof Engine.ui.faqEffects._initialized == "undefined") {

					Engine.ui.faqEffects.prototype.setObjects = function () {
						var self = this;

						//self.summaryList.find('h1').wrapInner('<span/>');
						//self.summaryList.find('h1 span').vAlign();

						$('<span class="bullet"/>').appendTo(self.summaryList.find('a')).fadeTo(0, 0);

						//add background holder, need to fade out bcg
						self.navBg = $('<div class="fade-bg"/>').appendTo(self.nav);

						//wrap content to make it scrollable
						$('.content-container').wrapInner('<div class="scrollable-holder"><div class="content-scrollable"></div></div>');
						$('.scrollable-holder').scrollTo(0, 0);

						//IE fix
						//IE9 resets counter of .list-n at hash change and class change (boxesClickEvent)! WTF? Lower is double crap.
						var ua = $.browser;
						if(ua.msie)
						{
							this.summaryListItems.each(function (i) {
								var counter = i+1;
								$(this).append('<span class="counter">'+counter+'</span>');
							});
						}
					}

					Engine.ui.faqEffects.prototype.setCanvas = function () {
						var self = this;
						self.mainSVG = Raphael(self.navBg[0], 974, 650);
					}

					Engine.ui.faqEffects.prototype.drawObjects = function () {
						var self = this;

						$.each(self.svgObjectsData, function (i, item) {
							//one object is:
							//path, starting dot, inner circle, outer circle

							var path = self.mainSVG.path(item.svgdata);

							//set attributes
							path.attr({stroke: item.color, 'stroke-width' : 1.5, "clip-rect": item.clipRectInit });

							//insert object to array
							self.svgObjectsData[i].obj = path;

							//get bouncing box // fot tests only
							//var pathBBox = path.getBBox();
							//window.console && console.log(pathBBox.x+','+pathBBox.y+','+pathBBox.width+','+pathBBox.height);
							//check clip rect after
							//self.svgObjectsData[i].bBox = self.mainSVG.rect(pathBBox.x, pathBBox.y, pathBBox.width, pathBBox.height).attr({'stroke-width' : 1, stroke: item.color, opacity: .2});
							//window.console && console.log('pbox.width: '+pathBBox.width);
						});

						//var c = self.mainSVG.rect(242,493,178,55);
					}

					Engine.ui.faqEffects.prototype.drawArrows = function () {
						var self = this;

						self.arrowSVG.obj = self.mainSVG.path(self.arrowSVG.svgdata).attr({ 'stroke-width' : 1.2, opacity: 0 });

						$.each(self.svgArrowsData, function (i, item) {
							//one object is:
							//clone of self.arrowSVG.obj path

							var clone = self.arrowSVG.obj.clone();

							//set attributes
							clone.attr({ stroke: item.color,/* rotation: item.rotateDeg,*/ translation: item.x+' '+item.y });

							//insert object to array
							self.svgArrowsData[i].obj = clone;

							//get bouncing box // fot tests only
							//var pathBBox = clone.getBBox();
							//check clip rect after
							//self.svgObjectsData[i].bBox = self.mainSVG.rect(pathBBox.x, pathBBox.y, pathBBox.width, pathBBox.height).attr({'stroke-width' : 1, stroke: item.color, opacity: .2});
							//window.console && console.log('pbox.width: '+pathBBox.width);
						});

						//var c = self.mainSVG.rect(177,331,203,44);
					}

					Engine.ui.faqEffects.prototype.prepareObjects = function () {
						var self = this;

						self.summaryListItems.css({/* top: '+=70px',*/ opacity: 0, display: 'block' });

						// top: '+=70px' seems it doesn't work on FF 3.6 :/
						self.summaryListItems.each(function () {
							var myTop = $(this).css('top');
							myTop = parseInt(myTop.replace("px", ""));
							myTop += 70;
							window.console && console.log(myTop);
							$(this).css('top', myTop);
						});

						var animObjQueue = [
							{
								obj : self.summaryListItems.eq(0),
								type : 'box'
							},
							{
								obj : self.svgObjectsData[0],
								type : 'line'
							},
							{
								obj : self.svgArrowsData[0],
								type : 'arrow'
							},
							{
								obj : self.svgObjectsData[1],
								type : 'line'
							},
							{
								obj : self.summaryListItems.eq(1),
								type : 'box'
							},
							{
								obj : self.svgObjectsData[2],
								type : 'line'
							},
							{
								obj : self.svgArrowsData[1],
								type : 'arrow'
							},
							{
								obj : self.svgObjectsData[3],
								type : 'line'
							},
							{
								obj : self.summaryListItems.eq(2),
								type : 'box'
							},
							{
								obj : self.svgObjectsData[4],
								type : 'line'
							},
							{
								obj : self.svgArrowsData[2],
								type : 'arrow'
							},
							{
								obj : self.svgObjectsData[5],
								type : 'line'
							},
							{
								obj : self.summaryListItems.eq(3),
								type : 'box'
							},
							{
								obj : self.svgObjectsData[6],
								type : 'line'
							},
							{
								obj : self.svgArrowsData[3],
								type : 'arrow'
							},
							{
								obj : self.svgObjectsData[7],
								type : 'line'
							},
							{
								obj : self.summaryListItems.eq(4),
								type : 'box'
							},
							{
								obj : self.svgObjectsData[8],
								type : 'line'
							},
							{
								obj : self.svgArrowsData[4],
								type : 'arrow'
							},
							{
								obj : self.svgObjectsData[9],
								type : 'line'
							},
							{
								obj : self.summaryListItems.eq(5),
								type : 'box'
							},
							{
								obj : self.svgObjectsData[10],
								type : 'line'
							},
							{
								obj : self.svgArrowsData[5],
								type : 'arrow'
							},
							{
								obj : self.svgObjectsData[11],
								type : 'line'
							},
							{
								obj : self.summaryListItems.eq(6),
								type : 'box'
							}
						];
						return animObjQueue;
					}

					Engine.ui.faqEffects.prototype.startAnim = function () {
						var self = this;
						self.startAnimTimeouts = [];

						var collection = self.prepareObjects();
						var objCount = collection.length;
						var animSpeed = 380;
						var animEasing = 'easeOutExpo';
						var timeBuff = 400;
						var step = 100;

						$.each(collection, function (i, objInfo) {
							//var objInfo = collection[i];
							var step = 100;
							var animEasing = 'easeOutExpo';
							var animSettings = false;

							if (objInfo.type === 'box') {

							}
							else if(objInfo.type === 'line')
							{
								animSettings = { 'clip-rect': objInfo.obj.clipRectFinal }
								//animEasing = '>';
							}
							else if(objInfo.type === 'arrow')
							{
								animSettings = { rotation: objInfo.obj.rotateDeg, opacity: 1 }
							}

							// step 1 individual setting
							if(i == 0)
							{
								step = 180;
							}

							if(i == objCount-1){
								self.startAnimTimeouts[i] = setTimeout(function(){
									if (animSettings) {
										objInfo.obj.obj.animate(animSettings, animSpeed, animEasing, function(){  });
									}
									else
									{
										//objInfo.obj.fadeIn(500);
										objInfo.obj.animate({ top : "-=70px", opacity: 1 }, 500, animEasing);
									}
								}, (100 + timeBuff));
							} else {
								self.startAnimTimeouts[i] = setTimeout(function(){
									if (animSettings) {
										objInfo.obj.obj.animate(animSettings, animSpeed, animEasing, function(){  });
									}
									else
									{
										//objInfo.obj.fadeIn(500);
										window.console && console.log(objInfo.obj.css('top'));
										objInfo.obj.animate({ top : "-=70px", opacity: 1 }, 500, animEasing);
									}
								}, (100 + timeBuff));
							}
							timeBuff += step;
						});
					}

					Engine.ui.faqEffects.prototype.startAnimClearTimeouts = function () {
						var self = this;
						window.console && console.log('clear timeout');
						$.each(self.startAnimTimeouts, function (i, item) {
							clearTimeout(item);
						});
					}

					Engine.ui.faqEffects.prototype.prepareArrowsSet = function () {
						var self = this;

						var animObjSet = [
							{
								arrow1Index : 0,
								arrow2Index : 1,
								arrow1Rotate : '-110',
								arrow2Rotate : '-50'
							},
							{
								arrow1Index : 0,
								arrow2Index : 1,
								arrow1Rotate : '140',
								arrow2Rotate : '60'
							},
							{
								arrow1Index : 1,
								arrow2Index : 2,
								arrow1Rotate : '-110',
								arrow2Rotate : '-70'
							},
							{
								arrow1Index : 2,
								arrow2Index : 3,
								arrow1Rotate : '90',
								arrow2Rotate : '-90'
							},
							{
								arrow1Index : 3,
								arrow2Index : 4,
								arrow1Rotate : '160',
								arrow2Rotate : '60'
							},
							{
								arrow1Index : 4,
								arrow2Index : 5,
								arrow1Rotate : '-180',
								arrow2Rotate : '110'
							},
							{
								arrow1Index : 2,
								arrow2Index : 5,
								arrow1Rotate : '180',
								arrow2Rotate : '-110'
							}
						];
						return animObjSet;
					}

					Engine.ui.faqEffects.prototype.hoverArrowsAnim = function (index, type) {
						var self = this;

						if (!self.nav.hasClass('expanded')) {
							return false;
						}

						var r1 = 0;
						var r2 = 0;

						var animSet = self.prepareArrowsSet();
						var item1 = self.svgArrowsData[animSet[index].arrow1Index];
						var item2 = self.svgArrowsData[animSet[index].arrow2Index];

						if (type == "on") {
							r1 = animSet[index].arrow1Rotate;
							r2 = animSet[index].arrow2Rotate;
						}
						else
						{
							r1 = item1.rotateDeg;
							r2 = item2.rotateDeg;
						}

						//window.console && console.log('r1:' + r1);

						if (item1.obj.attr('opacity') == 1 && item2.obj.attr('opacity') == 1)
						{
							//alert(item1);
							item1.obj.stop();
							item2.obj.stop();
							item1.obj.animate({ rotation: r1, opacity: 1 }, 400, '>');
							item2.obj.animate({ rotation: r2, opacity: 1 }, 400, '>');
						}
					}

					Engine.ui.faqEffects.prototype.startPage = function () {
						var self = this;
						var ua = $.browser;
						if(ua.msie && ua.version < 9)
						{
						}
						else
						{
							self.setCanvas();
							self.drawObjects();
							self.drawArrows();
							self.startAnim();
						}
					}

					Engine.ui.faqEffects.prototype.titleEffect = function () {
						var self = this;
						self.summaryList.delegate('a', 'hover', function (e) {
							var boxItem = $(this).find('h1');
							var bulletItem = $(this).find('.bullet');
							var index = self.summaryListItems.index($(this).parent());

							boxItem.stop();
							bulletItem.stop();
							if(e.type === 'mouseenter' && !$(this).parent().hasClass('active'))
							{
								boxItem.animate({ paddingLeft: 37, backgroundColor: '#f8f8f8' }, { duration: 350, easing: 'easeOutExpo' });
								bulletItem.show();
								bulletItem.animate({ opacity: 1, left: 10 }, { duration: 300 });
								self.hoverArrowsAnim(index, 'on');
							}
							else
							{
								boxItem.animate({ paddingLeft: 10, backgroundColor: '#ffffff' }, { duration: 100 });
								bulletItem.animate({ opacity: 0, left: -20 }, { duration: 200, complete: function () {
									$(this).hide();
								}});
								self.hoverArrowsAnim(index, 'off');
							}
						});
					}

					Engine.ui.faqEffects.prototype.boxesClickEvent = function () {
						var self = this;
						self.summaryList.delegate('a', 'click', function (e) {
							if (!$(this).parent().hasClass('active')) {

								self.summaryListItems.removeClass('active');
								$(this).parent().addClass('active');

								if (self.nav.hasClass('expanded')) {
									self.boxesEffect();
								}

								/*var pos = $(window).scrollTop();
								if (pos > 250) {
									$.scrollTo(250, 500);
								}*/

								self.scrollTopPos();

								var href = $(this).attr('href');
								var newHash = href.replace('#','');

								var sectionHeight = $(href).height();
								//alert(sectionHeight);

								var scrollableHolder = $('.scrollable-holder');

								scrollableHolder.stop();
								scrollableHolder.animate({ height: sectionHeight }, 100, 'swing', function () {
									scrollableHolder.scrollTo(href, 1000, {easing : 'swing', queue: false, onAfter: function () {
										self.setHash(newHash);
									}});
								} );
							}
							e.preventDefault();
						});
					}

					Engine.ui.faqEffects.prototype.boxesEffect = function () {
						var self = this;

						//boxes anim
						var items = self.summaryListItems;
						var collection = items;
						var objCount = collection.length;
						var animSpeed = 680;
						var animEasing = 'easeOutExpo';
						var timeBuff = 0;
						var step = 0;
						var indexArray = range(0, objCount-1);
						//indexArray.shuffle();

						//if start page anim is on the run stop it
						self.startAnimClearTimeouts();
						collection.stop();

						collection.each(function(i) {
							var obj = collection.eq(indexArray[i]);
							var realIndex = items.index(obj);
							var topCounted = realIndex * 65;
							var animSettings = { left: 759, top: topCounted, opacity: 1 }

							if(i == objCount-1){
								setTimeout(function(){
									obj.animate(animSettings, animSpeed, animEasing, function(){ self.setObjectsCollapsed() });
									obj.find('p').fadeOut('300');
								}, (100 + timeBuff));
							} else {
								setTimeout(function(){
									obj.animate(animSettings, animSpeed, animEasing);
									obj.find('p').fadeOut('300');
								}, (100 + timeBuff));
							}
							timeBuff += step;
						});

						//container bg anim
						self.navBg.fadeOut(animSpeed);

						//if collapsing, then show content
						self.contentContainer.css({ 'display' : 'none', 'position' : 'static', 'left' : 'auto', 'overflow' : 'auto' })
						self.contentContainer.fadeIn(animSpeed);
					}

					Engine.ui.faqEffects.prototype.setObjectsCollapsed = function () {
						var self = this;
						self.nav.removeClass('expanded').addClass('collapsed');
					}

					Engine.ui.faqEffects.prototype.goToTop = function () {
						var self = this;

						self.countTopPos();
						$(window).bind('scroll', function(){
							self.countTopPos();
						});

						$('.top-trigger').click(function (e) {
							e.preventDefault();
							//$.scrollTo(0, 500);
							self.scrollTopPos();
						});
					}

					Engine.ui.faqEffects.prototype.scrollTopPos = function () {
						var pos = $(window).scrollTop();
						if (pos > 250) {
							$.scrollTo(250, 500);
						}
					}

					Engine.ui.faqEffects.prototype.countTopPos = function () {
						var self = this;

						if (!self.nav.hasClass('expanded'))
						{
							var pos = $(window).scrollTop();
							//window.console && console.log(pos);
							if (pos > 250)
							{
								self.topButton.fadeIn();
							}
							else
							{
								self.topButton.fadeOut();
							}
						}
					}

					Engine.ui.faqEffects.prototype.setHash = function (newHash) {
						//set new document location hash
						var hash = document.location.hash.replace('#','');
						if(hash != newHash)
						{
							$('#'+newHash).attr('id', '_'+newHash);
							document.location.hash = '#'+newHash;
							$('#_'+newHash).attr('id', newHash);
						}
					}

					Engine.ui.faqEffects.prototype.next = function () {
						var self = this;

						//get current
						var current = self.summaryList.find('.active');
						if (current.length) {
							//get next
							var next = current.next();
							if (!next.length) {
								next = self.summaryListItems.eq(0);
							}
							next.find('a').trigger('click');
						}
					}

					Engine.ui.faqEffects.prototype.prev = function () {
						var self = this;

						//get current
						var current = self.summaryList.find('.active');
						if (current.length) {
							//get prev
							var prev = current.prev();
							if (!prev.length) {
								prev = self.summaryListItems.filter(':last');
							}
							prev.find('a').trigger('click');
						}
					}

					Engine.ui.faqEffects.prototype.keyboardNavigation = function () {
						var self = this;
						$(window).keydown(function(event){
							//Left
							if(event.keyCode == '37')
							{
								self.prev();
							}
							//Right
							if(event.keyCode == '39')
							{
								self.next();
							}
	            		});
					}

					Engine.ui.faqEffects.prototype.historyAction = function () {
						var self = this;
						$.history.init(function(hash){
							if(hash == '')
							{
								if (self.nav.hasClass('expanded')) {
									self.startPage();
								}
							}
							else
							{
					         	//alert('ddukg');
								self.summaryList.find('li').show(0);
					         	self.navBg.addClass('type-1');
								self.summaryListItems.find('a[href="#'+hash+'"]').trigger('click');
						    }
						    },{ unescape: ",/" }
						);
					}


					Engine.ui.faqEffects.prototype.init = function () {
						var self = this;
						self.setObjects();
						self.titleEffect();
						self.boxesClickEvent();
						self.goToTop();
						self.historyAction();
						self.keyboardNavigation();
					}

					Engine.ui.faqEffects._initialized = true;
				}

				this.init();
			},
			whatWDEffects : function (pageObj) {

				this.pageObj = pageObj;
				this.nav = pageObj.find('.box-n nav');
				this.summaryList = pageObj.find('ul.list-o');
				this.summaryListItems = pageObj.find('ul.list-o li');
				this.callbackbox = $('#callbackbox');
				this.loaderBox = $('#loadercallbackbox');
				this.clickedElementInfo = false;
				this.callBackAdded = false;
				this.clickBlocked = false;

				this.mainSVG = false;
				this.logoObj = false;
				this.logoGlowObj = false;
				this.svgObjectsData = [
					{
						svgdata: 'm 253,167 c 71,45 132,64 132,64',
						color: '#ffac04',
						circleInnerSize: 6,
						circleOuterFile: 'images/svg-helpers/shape-1.png',
						circleOuterSize: 32,
						circleOuterCorrection: {x: -15, y: -15},
						clipRectInit: "253,167,0,70",
						clipRectFinal: "253,167,134,70",
						obj: false,
						pointStart: false,
						pointEnd: false,
						startDotObj: false,
						circleInnerObj: false,
						circleOuterObj: false
					},
					{
						svgdata: 'M526,35c0,0,78,81-1,151',
						color: '#f4691d',
						circleInnerSize: 8,
						circleOuterFile: 'images/svg-helpers/shape-2.png',
						circleOuterSize: 36,
						circleOuterCorrection: {x: -17, y: -17},
						clipRectInit: "525,34,38,0",
						clipRectFinal: "525,34,38,152",
						obj: false,
						pointStart: false,
						pointEnd: false,
						startDotObj: false,
						circleInnerObj: false,
						circleOuterObj: false
					},
					{
						svgdata: 'm 673,107 c -18,147 -90,185 -90,185',
						color: '#7abec3',
						circleInnerSize: 8,
						circleOuterFile: 'images/svg-helpers/shape-2.png',
						circleOuterSize: 36,
						circleOuterCorrection: {x: -17, y: -17},
						clipRectInit: "674,107,90,185",
						clipRectFinal: "583,107,90,185",
						obj: false,
						pointStart: false,
						pointEnd: false,
						startDotObj: false,
						circleInnerObj: false,
						circleOuterObj: false
					},
					{
						svgdata: 'm 750,308 c -104,-12 -170,46 -170,46',
						color: '#559aa0',
						circleInnerSize: 4,
						circleOuterFile: 'images/svg-helpers/shape-3.png',
						circleOuterSize: 24,
						circleOuterCorrection: {x: -11, y: -11},
						clipRectInit: "750,306,170,47",
						clipRectFinal: "580,306,170,47",
						obj: false,
						pointStart: false,
						pointEnd: false,
						startDotObj: false,
						circleInnerObj: false,
						circleOuterObj: false
					},
					{
						svgdata: 'm 634,490 c -59,-55 -118,-86 -118,-86',
						color: '#8e2c7c',
						circleInnerSize: 6,
						circleOuterFile: 'images/svg-helpers/shape-1.png',
						circleOuterSize: 32,
						circleOuterCorrection: {x: -15, y: -15},
						clipRectInit: "516,521,118,86",
						clipRectFinal: "516,403,118,86",
						obj: false,
						pointStart: false,
						pointEnd: false,
						startDotObj: false,
						circleInnerObj: false,
						circleOuterObj: false
					},
					{
						svgdata: 'm 178,332 c 131,-42 203,32 203,32',
						color: '#fe3e07',
						circleInnerSize: 4,
						circleOuterFile: 'images/svg-helpers/shape-3.png',
						circleOuterSize: 24,
						circleOuterCorrection: {x: -11, y: -11},
						clipRectInit: "177,318,0,44",
						clipRectFinal: "177,318,203,44",
						obj: false,
						pointStart: false,
						pointEnd: false,
						startDotObj: false,
						circleInnerObj: false,
						circleOuterObj: false
					}
				];


				if(typeof Engine.ui.whatWDEffects._initialized == "undefined") {

					Engine.ui.whatWDEffects.prototype.setCanvas = function () {
						var self = this;
						self.mainSVG = Raphael(self.nav[0], 974, 666);
					}

					Engine.ui.whatWDEffects.prototype.drawAll = function () {
						var self = this;

						self.drawLogoGlow();
						self.drawLogo();
						self.drawShadow();
						self.drawObjects();
					}


					Engine.ui.whatWDEffects.prototype.drawObjects = function () {
						var self = this;

						$.each(self.svgObjectsData, function (i, item) {
							//one object is:
							//path, starting dot, inner circle, outer circle

							var path = self.mainSVG.path(item.svgdata);

							//set position
							//path.translate(item.left, item.top);

							//set attributes
							path.attr({stroke: item.color, 'stroke-width' : 1, "clip-rect": item.clipRectInit });

							//get start and end point
							var points = self.getStartEndCoords(path);
							var pointEnd = points.pointEnd;
							var pointStart = points.pointStart;

							//draw starting dot
							var dot = self.mainSVG.circle(pointStart.x, pointStart.y, 2);
							dot.attr({stroke: item.color, 'stroke-width' : 2, fill: item.color});


							//draw ending circle
							//outer
							var outerCircle = self.mainSVG.image(item.circleOuterFile, pointEnd.x+item.circleOuterCorrection.x, pointEnd.y+item.circleOuterCorrection.y, item.circleOuterSize, item.circleOuterSize);
							outerCircle.attr({opacity:0});

							/*var outerCircle1 = self.mainSVG.circle(pointEnd.x, pointEnd.y, item.circleOuterSize/2-3);
							outerCircle1.attr({stroke: '#000000', 'stroke-width' : 1, 'stroke-opacity': 0.2, opacity:1});
							outerCircle1.blur(1);*/

							//inner
							var innerCircle = self.mainSVG.circle(pointEnd.x, pointEnd.y, 0);
							innerCircle.attr({stroke: item.color, 'stroke-width' : 1, fill: item.color});

							//insert object to array
							self.svgObjectsData[i].obj = path;
							self.svgObjectsData[i].startDotObj = dot;
							self.svgObjectsData[i].circleInnerObj = innerCircle;
							self.svgObjectsData[i].circleOuterObj = outerCircle;
							self.svgObjectsData[i].pointStart = pointStart;
							self.svgObjectsData[i].pointEnd = pointEnd;


							//get bouncing box // fot tests only
							//var pathBBox = path.getBBox();

							//check clip rect after
							//self.svgObjectsData[i].bBox = self.mainSVG.rect(pathBBox.x, pathBBox.y, pathBBox.width, pathBBox.height).attr({'stroke-width' : 1, stroke: item.color, opacity: 1.08});
							//window.console && console.log('pbox.width: '+pathBBox.width);
						});

						//var c = self.mainSVG.rect(177,331,203,44);

					}

					Engine.ui.whatWDEffects.prototype.drawLogo = function () {
						var self = this;
						self.logoObj = self.mainSVG.image('images/svg-helpers/logo.png', 362, 178, 230, 240).attr({ opacity: 0 });
						self.logoObj.scale(0.01, 0.01);
					}

					Engine.ui.whatWDEffects.prototype.drawLogoGlow = function () {
						var self = this;
						self.logoGlowObj = self.mainSVG.image('images/svg-helpers/logo-glow.png', 267, 118, 478, 336).attr({ opacity: 0/*, "clip-rect" : "m300,260c0,-101.11856 81.90602,-183.02459 183.02458,-183.02459c101.11856,0 183.0246,81.90603 183.0246,183.02459c0,101.11856 -81.90604,183.0246 -183.0246,183.0246c-101.11856,0 -183.02458,-81.90604 -183.02458,-183.0246z" */});
						self.logoGlowObj.scale(0.01, 0.01);
					}

					Engine.ui.whatWDEffects.prototype.drawShadow = function () {
						var self = this;
						self.logoShadowObj = self.mainSVG.image('images/svg-helpers/shadow.png', 184, 420, 586, 98).attr({ opacity: 0/*, "clip-rect" : "m300,260c0,-101.11856 81.90602,-183.02459 183.02458,-183.02459c101.11856,0 183.0246,81.90603 183.0246,183.02459c0,101.11856 -81.90604,183.0246 -183.0246,183.0246c-101.11856,0 -183.02458,-81.90604 -183.02458,-183.0246z" */});
						self.logoShadowObj.scale(0.01, 0.01);
					}

					Engine.ui.whatWDEffects.prototype.getStartEndCoords = function (path) {
						var self = this;

						var totalLength = path.getTotalLength();
						var pointEnd = path.getPointAtLength(totalLength);
						var pointStart = path.getPointAtLength(0);
						return { pointStart: pointStart, pointEnd: pointEnd };
					}

					Engine.ui.whatWDEffects.prototype.animateObject = function (index, objText, isLast) {
						var self = this;
						var item = self.svgObjectsData[index];

						item.startDotObj.animate({
							    "20%": { fill: '#ffffff', stroke: '#ffffff' },
							    "30%": { fill: item.color, stroke: item.color },
							    "40%": { fill: '#ffffff', stroke: '#ffffff' },
							    "50%": { fill: item.color, stroke: item.color },
							    "60%": { fill: '#ffffff', stroke: '#ffffff' },
							    "70%": { fill: item.color, stroke: item.color },
							    "80%": { fill: '#ffffff', stroke: '#ffffff' },
							    "100%": { fill: item.color, stroke: item.color, callback: function () {
									objText.fadeIn(600);

									item.obj.animate({'clip-rect': item.clipRectFinal}, 1000, ">", function () {
										item.circleInnerObj.animate({r: item.circleInnerSize}, 500, "bounce");
										item.circleInnerObj.animate({"stroke-width": 15, 'stroke-opacity': 0}, 500, "bounce");
										item.circleOuterObj.animate({"opacity": 1}, 1500, ">");
										if (isLast) {
											self.logoGlowObj.animate({ scale: "1", opacity: 1 }, 1000, "backOut");
											/*self.LogoGlowObj.animate({
    											"70%": {scale: "0.95", opacity: 0.9, easing: ">"},
    											"100%": {scale: "1"}
											}, 800);*/
										}
									});
								}}
						}, 1000);

						/*item.obj.animate({'clip-rect': item.clipRectFinal}, 1000, ">", function () {
							item.circleInnerObj.animate({r: item.circleInnerSize}, 500, "bounce");
							item.circleInnerObj.animate({"stroke-width": 15, 'stroke-opacity': 0}, 500, "bounce");
							item.circleOuterObj.animate({"opacity": 1}, 1500, ">");
						});*/
					}

					Engine.ui.whatWDEffects.prototype.runAnimateAll = function () {
						var self = this;

						//start logo animation
						self.logoObj.animate({ opacity: 1, scale: "1" }, 800, ">");
						self.logoShadowObj.animate({ opacity: 1, scale: "1" }, 800, ">");

						//boxes anim
						var items = self.summaryListItems;
						var collection = items;
						var objCount = collection.length;
						var animSpeed = 380;
						var animEasing = 'swing';
						var timeBuff = 0;
						var step = 380;
						var indexArray = range(0, objCount-1);
						indexArray.shuffle();

						collection.each(function(i) {
							var obj = collection.eq(indexArray[i]);
							var realIndex = items.index(obj);

							var animSettings = { opacity: 1 }

							if(i == objCount-1){
								//last one
								setTimeout(function(){
									self.animateObject(realIndex, obj, 1);
								}, (100 + timeBuff));
							} else {
								setTimeout(function(){
									self.animateObject(realIndex, obj, 0);
								}, (100 + timeBuff));
							}
							timeBuff += step;
						});
					}

					Engine.ui.whatWDEffects.prototype.hoverEffect = function () {
						var self = this;
						self.summaryList.delegate('a', 'hover', function (e) {
							var parent = $(this).parent();
							var myIndex = self.summaryListItems.index(parent);
							var item = self.svgObjectsData[myIndex];

							item.startDotObj.stop();
							if(e.type === 'mouseenter' && !$(this).parent().hasClass('active'))
							{
								item.startDotObj.animate({"stroke-width": 15, 'stroke-opacity': 0, r: 6}, 500, "bounce");
							}
							else
							{
								item.startDotObj.animate({"stroke-width": 1, 'stroke-opacity': 1, r: 2}, 500, "bounce");
							}
						});
					}

					Engine.ui.whatWDEffects.prototype.clickEffect = function () {
						var self = this;
						self.summaryList.delegate('a', 'click', function (e) {

							if (!self.clickBlocked) {
								self.clickBlocked = true;
								var parent = $(this).parent();
								var myIndex = self.summaryListItems.index(parent);
								var item = self.svgObjectsData[myIndex];

								self.clickedElementInfo = { obj : parent, index: myIndex };

								if(ua.msie && ua.version < 9) {
									self.overlayInit();
								}
								else
								{
									if (self.svgObjectsData[myIndex].movingDot) {
										var clone = self.svgObjectsData[myIndex].movingDot;
									}
									else
									{
										var clone = item.startDotObj.clone();
										clone.attr({"stroke-width": 13, 'stroke-opacity': 0.1, r: 4});
									}
									clone.attr({cx: item.pointStart.x, cy: item.pointStart.y, opacity: 0.8});
									clone.animateAlong(item.obj, 300, false, function () { self.svgObjectsData[myIndex].movingDot = clone; self.circleClickAnim(item.circleInnerObj); });
								}
							}
							e.preventDefault();
						});
					}

					Engine.ui.whatWDEffects.prototype.circleClickAnim = function (circle) {
						var self = this;
						circle.animate({"stroke-width": 4, 'stroke-opacity': 1, stroke: '#ffffff', r: 14/*, fill: "#c4c4c4", opacity: 0.4*/}, 300, "bounce", function () {
							self.overlayInit();
						});
					}

					Engine.ui.whatWDEffects.prototype.circleAfterHideOverlayAnim = function (index) {
						var self = this;

						var item = self.svgObjectsData[index];
						var circle = item.circleInnerObj;
						circle.animate({stroke: item.color, 'stroke-width' : 1, r: item.circleInnerSize/*, fill: "#c4c4c4", opacity: 0.4*/}, 300, "bounce", function () {
							self.cloneDotBackAnim(item);
						});
					}


					Engine.ui.whatWDEffects.prototype.cloneDotBackAnim = function (item) {
						var self = this;
						var clone = item.movingDot;
						clone.attr({cx: item.pointEnd.x, cy: item.pointEnd.y});
						clone.animateAlongBack(item.obj, 300, false, function () { clone.attr({ opacity: 0 }); });
					}


					Engine.ui.whatWDEffects.prototype.loaderInit = function () {
						var self = this;
						Engine.ui.loaderOverlay(self.loaderBox);
						self.loaderBox.find('h3').html(Engine.translation.dictionary.loading_page_label+' <span>0</span>%');
					}


					Engine.ui.whatWDEffects.prototype.overlayInit = function () {
						var self = this;

						if (self.clickedElementInfo) {
							if (!self.callBackAdded) {
								//override defalult options from Engine.ui.portfolioOverlay.init();
								self.callbackbox.data('overlay').onClose( function () {
									self.callBackAdded = true;
									self.clickBlocked = false;
									if(ua.msie && ua.version < 9){}
									else
									{
										self.circleAfterHideOverlayAnim(self.clickedElementInfo.index);
									}
									self.callbackbox.removeClass();
									self.callbackbox.addClass('popbox-a pbxa-a modal');
								});
							}

							var textContainer = self.clickedElementInfo.obj.find('a').attr('href');
							var boxClass = self.clickedElementInfo.obj.data('boxclass');
							self.callbackbox.addClass(boxClass);

							//calculate modal height
							var viewport = $(window).height();
							if (viewport < 550) {
								self.callbackbox.find(".box-g").height(viewport - 160);
							}
							else
							{
								self.callbackbox.find(".box-g").height(410);
							}

							// get content
							var content = $(textContainer).html()

							//set content
							self.callbackbox.find(".text-area-a").html(content);

							//show overlay
							self.callbackbox.data('overlay').load();
						}
					}

					Engine.ui.whatWDEffects.prototype.loadPage = function () {
						var self = this;

						self.loaderInit();
						var loaderTimeout = setTimeout(function () { self.loaderBox.data('overlay').load(); }, 200);
						var images = self.getImages();
						images.find('img').batchImageLoad({
							loadingCompleteCallback : function () {
								//window.console && console.log('complete');
								//hide loader
								clearTimeout(loaderTimeout);
								self.loaderBox.data('overlay').close();
								self.runAnimateAll();
							},
							imageLoadedCallback : function (elementsLoadedCount, totalImagesCount) {
								self.progressIndicator(elementsLoadedCount, totalImagesCount);
							}
						});
					}

					Engine.ui.whatWDEffects.prototype.progressIndicator = function (elementsLoadedCount, totalImagesCount) {
						var self = this;
						var percent = Math.round((elementsLoadedCount / totalImagesCount) * 100);
						if (isNaN(percent))
						{
							percent = 0;
						}
						self.loaderBox.find('h3 span').html(percent);
					}

					Engine.ui.whatWDEffects.prototype.getImages = function () {
						var imagesArray = [
							'svg-helpers/logo-glow.png',
							'svg-helpers/logo.png',
							'svg-helpers/shadow.png',
							'svg-helpers/shape-1.png',
							'svg-helpers/shape-2.png',
							'svg-helpers/shape-3.png',
							'project-visuals/portfolio-hitmo-logo-bg.png',
							'pages-visuals/what-we-do-top.jpg',
							'what-we-do/vis-id.png',
							'what-we-do/websites.png',
							'what-we-do/multi-cms.png',
							'what-we-do/psd2html.png',
							'what-we-do/ecomerce-bg.png',
							'what-we-do/design-bg.png'
						];
						var p = $('<p id="preloader"/>').appendTo('body');
						for(var i in imagesArray) {
							var value = imagesArray[i];
							if(typeof(value) == 'string')
							{
								p.append('<img src="images/' + value +'"/>');
							}
						}
						return p;
					}

					Engine.ui.whatWDEffects.prototype.ielt9 = function () {
						var self = this;
						self.summaryListItems.show();
					}

					Engine.ui.whatWDEffects.prototype.init = function () {
						var self = this;
						if(ua.msie && ua.version < 9)
						{
							self.ielt9();
						}
						else
						{
							self.setCanvas();
							self.drawAll();
							self.loadPage();
							self.hoverEffect();
						}
						self.clickEffect();
					}
					Engine.ui.whatWDEffects._initialized = true;
				}

				this.init();
			},
			// *** tooltip ************************************************* /
			tooltipInit : function () {
				$('.tooltip-trigger').tooltip({
    				track: false,
    				delay: 0,
    				showURL: false,
    				opacity: 1,
    				fixPNG: true,
    				bodyHandler: function() {
						var tooltipBody = '<p class="tooltip"><span><em>'+$(this).html()+'</em></span></p>';
    				    return tooltipBody;
    				},
    				top: -41,
    				left: 20,
					fade: 250
				});
			}
		},
		forms : {
			fullFormValidator : 0,
			validateContactFullForm : function () {
				var self = this;
				self.fullFormValidator =  $("#business-query-form").validate({
					rules: {
						fl_name: "required",
						budget : "required",
						message: "required",
						email: {
							required: true,
							email: true
						}
					},
					messages: {
						fl_name : Engine.translation.dictionary.forms.name_required,
						budget : Engine.translation.dictionary.forms.budget_required,
						email : {
							email : Engine.translation.dictionary.forms.mail_invalid,
							required : Engine.translation.dictionary.forms.mail_required
						},
						message : Engine.translation.dictionary.forms.message_required
					},
  					highlight: function(element, errorClass, validClass) {
    					$(element).parent().addClass(errorClass);
						$(element).addClass(errorClass).removeClass(validClass);
  					},
  					unhighlight: function(element, errorClass, validClass) {
    					$(element).parent().removeClass(errorClass);
    					$(element).removeClass(errorClass).addClass(validClass);
					},
					errorPlacement: function(error, element) {
						if(element.attr('name') == 'budget')
						{
							error.appendTo($('#budget-error-container'));
						}
						else
						{
							error.insertAfter(element);
						}
					}
				});
			},
			validateContactQuestionForm : function () {
				//var errorsCount = 0;
				var validator = $("#simple-question-form").validate({
					rules: {
						fl_name: "required",
						message: "required",
						email: {
							required: true,
							email: true
						}
					},
					messages: {
						fl_name : Engine.translation.dictionary.forms.name_required,
						email : {
							email : Engine.translation.dictionary.forms.mail_invalid,
							required : Engine.translation.dictionary.forms.mail_required
						},
						message : Engine.translation.dictionary.forms.message_required
					},
  					highlight: function(element, errorClass, validClass) {
    					$(element).parent().addClass(errorClass);
						$(element).addClass(errorClass).removeClass(validClass);
  					},
  					unhighlight: function(element, errorClass, validClass) {
    					$(element).parent().removeClass(errorClass);
    					$(element).removeClass(errorClass).addClass(validClass);
					}/*,
					errorPlacement: function(error, element) {
						error.insertAfter(element);
						errorsCount += 1;
						if (errorsCount === validator.numberOfInvalids()) {
							if (contactPage !== null) {
								contactPage.setTabPanesContainerHeight();
							}
						}
					}*/
				});
			},
			validateContactHiForm : function () {
				$("#say-hi-form").validate({
					rules: {
						fl_name: "required",
						message: "required",
						email: {
							required: true,
							email: true
						}
					},
					messages: {
						fl_name : Engine.translation.dictionary.forms.name_required,
						email : {
							email : Engine.translation.dictionary.forms.mail_invalid,
							required : Engine.translation.dictionary.forms.mail_required
						},
						message : Engine.translation.dictionary.forms.message_required
					},
  					highlight: function(element, errorClass, validClass) {
    					$(element).parent().addClass(errorClass);
						$(element).addClass(errorClass).removeClass(validClass);
  					},
  					unhighlight: function(element, errorClass, validClass) {
    					$(element).parent().removeClass(errorClass);
    					$(element).removeClass(errorClass).addClass(validClass);
					}
				});
			},
			setFocusEffect : function (formObj) {
				var controlls = formObj.find('input[type!=radio], input[type!=checkbox], textarea');
				controlls.wrap('<span class="focus-wrapper"/>');

				controlls.focus(function () {
					var parent = $(this).parent();
					if (!parent.find('span.focus-helper').length) {
						parent.append('<span class="focus-helper"/>');
					}
				});

				controlls.blur(function () {
					var parent = $(this).parent();
					parent.find('span.focus-helper').remove();
				});
			},
			removeHtml5Validation : function () {
				//if is JS, then JS validation only
				if(!ua.msie || (ua.msie && ua.version > 9))
				{
					var form = $('.form-a');
					form.find('.field-a input, textarea').removeAttr('required');
					//form.find('input[type="email"]').attr('type', 'text');
				}
			}
		},
		parallax : {
			objLib : {
				parallax1 : $("#parallax"),
				parallax2 : $("#parallax2"),
				animContainer : $("#ball-anim"),
				parallax1Layers : $('#parallax .parallax-layer'),
				parallax2Layers : $('#parallax2 .parallax-layer'),
				animSpeed : 33,
				fadeIncrement : 0,
				animInterval : false,
				animTimeout : false,
				counter : 1
			},
			init : function () {
				var self = this;
				/*self.loadersInit($('.box-r').eq(0), ['pages-visuals/team-maciek.png']);
				self.loadersInit($('.box-r').eq(1), ['pages-visuals/team-pawel.png']);
				self.loadersInit(self.objLib.animContainer, ['pages-visuals/png_anim_1.png', 'pages-visuals/png_anim_2.png']);*/

				self.runParallax1();
				self.runParallax2();
				self.animEvents();
				/*var cScrolltop = $(window).scrollTop();
				$(window).scroll(function (e) {
					window.console && console.log('scroll');
					$('body,html').scrollTop(cScrolltop);
					e.preventDefault();
					return false;
				});*/

			},
			runParallax1 : function () {
				var self = this;

				if (self.objLib.parallax1.length)
				{
					self.objLib.parallax1Layers.parallax({mouseport: self.objLib.parallax1},
						//h
						{xparallax: 0.1, yparallax: 0.1, xorigin: 0.5, yorigin: -0.05},

						//i
						{xparallax: 0.2, yparallax: 0.2, xorigin: 0.5, yorigin: -0.12},

						//t
						{xparallax: 0.1, yparallax: 0.1, xorigin: 0.5, yorigin: -0.05},

						//m
						{xparallax: 0.15, yparallax: 0.15, xorigin: 0.5, yorigin: -0.05},

						//o
						{xparallax: 0.1, yparallax: 0.1, xorigin: 0.5, yorigin: -0.05}
					);
				}
			},
			runParallax2 : function () {
				var self = this;
				if (self.objLib.parallax2.length)
				{
					self.objLib.parallax2Layers.parallax({mouseport: self.objLib.parallax2},
						//ball
						{xparallax: 0.07, yparallax: 0.07, xorigin: 0.60, yorigin: 0.44}

					);
				}
			},
			animEvents : function () {
				var self = this;
				var parallax2 = self.objLib.parallax2;
				parallax2.mouseenter(function () {
					if (!self.objLib.animContainer.hasClass('loading')) {
						self.startAnimation();
					}
				});

				parallax2.mouseleave(function () {
					if (!self.objLib.animContainer.hasClass('loading')) {
						self.stopAnimation();
					}
				});
			},
			ballAnim : function () {
				var self = this;
				var animContainer = self.objLib.animContainer;
				if (animContainer.length) {
					animContainer.removeClass();
					animContainer.addClass('banim'+self.objLib.counter);
					self.objLib.counter++;
					if (self.objLib.counter === 119) {
						self.objLib.counter = 1;
					}
				}
			},
			startAnimation : function () {
				var self = this;
				self.objLib.animInterval = setInterval( function () {
					self.ballAnim();
				}, self.objLib.animSpeed);
			},
			stopAnimation : function () {
				var self = this;
				clearInterval(self.objLib.animInterval);
				self.fadeOutAnimation();
			},
			fadeOutAnimation : function () {
				var self = this;
				clearTimeout(self.objLib.animTimeout);
				self.objLib.fadeIncrement += 10;
				self.ballAnim();
				self.objLib.animTimeout = setTimeout(function () {
					if (self.objLib.fadeIncrement > 70) {
						clearTimeout(self.objLib.animTimeout);
						self.objLib.fadeIncrement = 0;
					}
					else
					{
						self.fadeOutAnimation();
					}
				}, self.objLib.animSpeed + self.objLib.fadeIncrement);
			},
			loadersInit : function (obj, imagesArray) {
				var self = this;

				obj.addClass('loading');
				var images = self.getImages();
				images.find('img').batchImageLoad({
					loadingCompleteCallback : function () {
						obj.removeClass('loading');
					}
				});
			},
			getImages : function (imagesArray) {
				var p = $('#preloader');
				if (!p.length) {
					var p = $('<p id="preloader"/>').appendTo('body');
				}

				for(var i in imagesArray) {
					var value = imagesArray[i];
					if(typeof(value) == 'string')
					{
						p.append('<img src="images/' + value +'"/>');
					}
				}
				return p;
			}
		},
		// *** older browsers fixes ************************************************* /
		fixes : {
			init : function () {
				var self = this;
				self.headerVerticalAlign();
				self.portfolioMultiBackground();
			},
			portfolioMultiBackground : function () {
				var ua = $.browser;
				if(ua.msie && ua.version < 9)
				{
					var box = $('.box-d');
					if (box.hasClass('portfolio-all') || box.hasClass('contact') || box.hasClass('faq') || box.hasClass('what-we-do')) {
						box.wrapInner('<div class="hitmo-bg" />');
						box.wrapInner('<div class="fade-1" />');
					}
					box.wrapInner('<div class="noise" />');
					box.wrapInner('<div class="fade" />');
				}
			},
			headerVerticalAlign : function () {
				var ua = $.browser;
				if(ua.msie && ua.version < 8)
				{
					$('.header-d span').vAlign();
				}
			}
		}
	};


	Engine.translation.getDictionary();

	Engine.utils.links();
	//Engine.utils.mails();
	Engine.utils.placeholderInit();
	Engine.ui.mainCycle.init();
	Engine.ui.boxesEffects.init();
	Engine.ui.portfolioBackPanel.init();
	Engine.ui.tooltipInit();
	Engine.ui.newsCycle.init();
	Engine.ui.portfolio.generatePortfolioView($('.box-d'));
	Engine.ui.portfolioOverlay.init();

//moved from end check if errors occured
	Engine.ui.pagesDynamicNavigation.init();

	var portfolioProjectsPage = null;
	var portfolioPage = $('#portfolio-all');
	if (portfolioPage.length) {
		portfolioProjectsPage = new Engine.ui.portfolioProjectsPage(portfolioPage);
	}

	var portfolioOthersPage = null;
	var portfolioOthers = $('#others');
	if (portfolioOthers.length) {
		portfolioOthersPage = new Engine.ui.folderEffects(portfolioOthers);
	}

	var contactPage = null;
	var contact = $('#contact-page');
	if (contact.length) {
		contactPage = new Engine.ui.contactPageEffects(contact);
	}

	var whatWDPage = null;
	var whatWD = $('#what-we-do-page');
	if (whatWD.length) {
		whatWDPage = new Engine.ui.whatWDEffects(whatWD);
	}

	var faqPage = null;
	var faq = $('#faq-page');
	if (faq.length) {
		faqPage = new Engine.ui.faqEffects(faq);
	}

	var svgFx = new Engine.ui.svgEffects($('.box-d'));


	Engine.forms.validateContactFullForm();
	Engine.forms.validateContactQuestionForm();
	Engine.forms.validateContactHiForm();
	Engine.forms.removeHtml5Validation();
	Engine.fixes.init();
	Engine.parallax.init();

});