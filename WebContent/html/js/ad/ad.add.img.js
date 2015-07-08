$(document).ready(function(){
	
	$(function () {
		uploadButton = $(".alex99");
		
		
//	    var url = 'http://alex.pchome.com.tw:8089/akbpfb/testJQPlot2.html',
//	    uploadButton = $('<button/>').addClass('').prop('disabled', true).text('Processing...').on('click', function () {
//	                var $this = $(this),
//	                data = $this.data();
//	                $this.off('click').text('Abort').on('click', function () {
//	                        $this.remove();
//	                        data.abort();
//	                    });
//	                data.submit().always(function () {
//	                    $this.remove();
//	                });
//	            });
	    $('#fileupload').fileupload({
	        url: 'http://alex.pchome.com.tw:8089/akbpfb/testJQPlot2.html',
	        dataType: 'json',
	        autoUpload: false,
	        acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i,
	        maxFileSize: 5000000, // 5 MB
	        previewMaxWidth: 210,
	        previewMaxHeight: 180,
	        previewCrop: true
	    }).on('fileuploadadd', function (e, data) {
	        data.context = $('<p/>').appendTo('#files');
	        $.each(data.files, function (index, file) {
//	            var node = $('<g/>').append($('<span/>').text(file.name));
	            if (!index) {
//					node.append('').append(uploadButton.clone(true).data(data));
//	            	$("#c1").append(uploadButton.clone(true).data(data));
	            }
//	            node.appendTo(data.context);
//	            var $img = $("#firstDiv").children("img").clone();
//	            $("#secondDiv").append($img);
//	            $("#c1").append('544545656');
	        });
	    }).on('fileuploadprocessalways', function (e, data) {
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
//	        var index = data.index,
//	        file = data.files[index],
//	        node = $(data.context.children()[index]);
////	        console.log(file);
//	        fileArray.push(file);
////	        console.log(file.name);
//	        
//	        
//	        if(!$.browser.msie) {
//	        	var anyWindow = window.URL || window.webkitURL;
//		        var objectUrl = anyWindow.createObjectURL(file);
//		        console.log(detect(objectUrl));
//	        }
	        
//	        createPreViewArea(this);
//	        PreviewImage
	        
//	        console.log(document.getElementById("fileUpload").value);
//	        file.select();
//	        var realpath = document.selection.createRange().text;
	        
	        
//	        document.getElementById('preview').src = src;
	        
	        
	        
//			var img = new Image();
	        
	        
	        
//	        $("#GAG").append(file.img);

//	        var reader = new FileReader();
	        
//	        if($.browser.msie){ 
//	        	$("#GAG").attr("src",file.value); 
//	        }
	        
	        
	        
	        
	        
//	        console.log(file);
//	        console.log(file.preview);
//	        console.log(file.value);
//	    	$("#GAG").append(file.preview); 
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
//	        var filepath = $(file).value();
	        
//	        var path = document.getElementById("fileUpload").value;
//	        var demo = new ImageUploadPreview(file);  
//	        console.log(demo);
	        
//	        loadImage(objectUrl);
//	        var imgwidth = objectUrl.width;
//	        var imgheight = objectUrl.height;
//	        console.log(file.width);
//	        console.log(imgwidth);
//	        console.log(imgheight);
	        	
//	        
//	        var A = '';
//	        var B = '';
//	        var fileName =file.name;
////	    	    var img = new Image();
////	    	    img.src = objectUrl;
////	    	  	var alex00 =  img.onload = function () {
////	    	    	img.onload = null;
////	    	        var imageWidth = img.width,
////	    	            imageHeight = img.height;
////	    	        console.log(imageWidth);
////	    	        console.log(imageHeight);
////	    	        var result = [{'width':imageWidth,'height':imageHeight}]
////	    	        return result;
////	    	    }
//	        
////	        
////	            var image = new Image();
////	            image.src = objectUrl;
////	            var resultData = image.onload = function() {
////	            	image.onload = null;
////	            	var aG = image.width,
////	            		imageHeight = image.height;
////	            	 	console.log(aG);
////	                var result = [{ x: 50, y: 100 }];
////	                return result;
////	            };
//	       
////	      var gX =  loadImage(objectUrl);
////	      console.log(gX);

//	        
////	       
//	        
////	            console.log(alex00);
//	        
////	    	    console.log(resultData[0].x);
////	    	    console.log(img.src);
////    	        console.log(B);
//	        
//	    	    var a =
//		        	'<li class="okbox">'+    
//	                '<div class="adboxdv">'+
//	                '<img src="'+objectUrl+'">'+
//	                '<p class="fancy adinf" onclick="createPreViewArea(\''+file.name+'\');" alt="預覽">預覽</p></div>'+
//	                '<ul>'+
//	                '<li><i>尺寸</i><b>'+A+' x '+B+'</b></li>'+
//	                '<li><i>大小</i><b>100KB</b></li>'+
//	                '<li><i>格式</i><b>PNG</b></li>'+
//	                '</ul>'+
//	                '<a class="addel" href="#">丟</a>'+ 
//	                '</li>';
//		        	$(".aduplodul").append(a);
//	        if (file.error) {
//	            node.append('<br>').append($('<span class="text-danger"/>').text(file.error));
//	        }
//	        if (index + 1 === data.files.length) {
//	            data.context.find('button').text('Upload').prop('disabled', !!data.files.error);
//	        }
//	        console.log("SSSSSSSSS");
	    }).on('fileuploadprogressall', function (e, data) {
//	        var progress = parseInt(data.loaded / data.total * 100, 10);
//	        $('#progress .progress-bar').css(
//	            'width',
//	            progress + '%'
//	        );
	    }).on('fileuploaddone', function (e, data) {
	        $.each(data.result.files, function (index, file) {
//	            if (file.url) {
//	                var link = $('<a>')
//	                    .attr('target', '_blank')
//	                    .prop('href', file.url);
//	                $(data.context.children()[index])
//	                    .wrap(link);
//	            } else if (file.error) {
//	                var error = $('<span class="text-danger"/>').text(file.error);
//	                $(data.context.children()[index]).append('<br>').append(error);
//	            }
	        });
	    }).on('fileuploadfail', function (e, data) {
//	        $.each(data.files, function (index) {
//	            var error = $('<span class="text-danger"/>').text('File upload failed.');
//	            $(data.context.children()[index]).append('<br>').append(error);
	        });
//	    }).prop('disabled', !$.support.fileInput).parent().addClass($.support.fileInput ? undefined : 'disabled');
	});
	
	
	
	//顯示圖片尺寸
	function detect(URL) {
	    var image = new Image();
	    image.src = URL;
	    var x = image.onload = function() {
	    	console.log(image.width);
	    	console.log(image.height);
	        var result1 = [{ x1: 9999, y1: 9999 }]; 
	        return result1;
	    };
//	    console.log("=alex=====:"+result1);
//	    var result = [{ x: 45, y: 56 }]; // An example result
//        return result;
	}
		
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	var alex8888 = '';
//	function loadImage(url) {
//	    var img = new Image();
//	    img.onload = function () {
//	        img.onload = null;
//	        var imageWidth = img.width,
//	            imageHeight = img.height;
//	        alex8888 = imageWidth;
//	    }
//	   console.log(alex8888);
//	   return {level: '4545456'};
//	}
	

	

	

	 function OnClientFileSelected(sender, args){
	    var row = args.get_row();
	    var fakeFileNameInput = sender._getChildFileNameInputField(row);
	    var fakePathString = "fakepath";
	    if (fakeFileNameInput.value.indexOf(fakePathString) != 0){
	        var fakePathLength = fakePathString.length;
	        fakeFileNameInput.value = fakeFileNameInput.value.replace(fakePathString,"[SelectionPath]");
	    }
	} 	
	
	
	
	function ImageUploadPreview(input, opt_onSuccess, opt_onFail) {  
		  this.construct(input, opt_onSuccess, opt_onFail);  
	}  

	
	

	
	
//	
//	function createPreViewArea(src) {
//		document.getElementById('preview').src = src;
//		var img = new Image();
//		img.onload = function(){
//		alert(this.fileSize);
//		};
//		img.onerror = function(){
//		alert('onerror')
//		};
//		img.src = src;
//		}

	
	
	
//	 $(".fancy").live('click', function(event) {
//	    	event.preventDefault();
//	    	$.fancybox({
//				'width'             : 300,
//				'height'            : 250,
//	    		'autoSize'			: true,
//	    		'autoHeight'		: true,
//	    		'autoScale'			: true,
//	    		'transitionIn'		: 'none',
//	    		'transitionOut'		: 'none',
////	    		'type'				: 'iframe',
//	    		'padding'			: 0,
//				'overlayOpacity'    : .75,
//				'overlayColor'      : '#fff',
//	    		'scrolling'			: 'no'
//	    	});
//	    });

	
	
//	$.fancybox.close = function() {
//		alert("FFFFF");
//	};
//	$($.fancybox.close).ready(function() {
//	    console.log( "ready!" );
//	});
//	
//	$.fancybox.close = function() {
//		 console.log( "ready2222!" );
//		 $.fancybox.close();
//		 return false;
//	};
});










function createPreViewArea(obj){
	 var files = document.getElementById("fileupload");
	 if(!$.browser.msie){
		 $.each($(files).context.files, function( index, file ) {
		 console.log($(file));
		 var anyWindow = window.URL || window.webkitURL;
		 var objectUrl = anyWindow.createObjectURL(file);
		 var a =
			 '<li class="okbox">'+    
			 '<div class="adboxdv">'+
			 '<img src="'+objectUrl+'">'+
			 '<p class="fancy adinf" onclick="createPreViewArea(\''+file.name+'\');" alt="預覽">預覽</p></div>'+
			 '<ul>'+
			 '<li><i>尺寸</i><b>'+"100"+' x '+"500"+'</b></li>'+
			 '<li><i>大小</i><b>100KB</b></li>'+
			 '<li><i>格式</i><b>PNG</b></li>'+
			 '</ul>'+
			 '<a class="addel" href="#">丟</a>'+ 
			 '</li>';
		     $(".aduplodul").append(a);
		})
	 }else{
		 
		 var newPreview = document.getElementById("newPreview");
		 var imgDiv=document.createElement("div");
		 document.body.appendChild(imgDiv);
		 imgDiv.style.width="118px";imgDiv.style.height="127px";
		 imgDiv.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod = scale)";
		 imgDiv.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = obj.value;
		 newPreview.appendChild(imgDiv);
		 var showPicUrl = document.getElementById("showPicUrl");
		 showPicUrl.innerText = obj.value;
		 newPreview.style.width="500px";
		 newPreview.style.height="500px";
		 
		 var a =
			 '<li class="okbox">'+    
			 '<div class="adboxdv" id="5599">'+
			 '<p class="fancy adinf" onclick="createPreViewArea(\''+file.name+'\');" alt="預覽">預覽</p></div>'+
			 '<ul>'+
			 '<li><i>尺寸</i><b>'+"100"+' x '+"500"+'</b></li>'+
			 '<li><i>大小</i><b>100KB</b></li>'+
			 '<li><i>格式</i><b>PNG</b></li>'+
			 '</ul>'+
			 '<a class="addel" href="#">丟</a>'+ 
			 '</li>';
		     $(".aduplodul").append(a);
		     $("#5599").prepend(imgDiv);
	 }
	 
	 
	 	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	
	 
	 
	 
	 
	
	 
	 
	 
	 
	 
	 
	 
	 
	}




var ie8Preview = "";
function PreviewImage(fileObj,imgPreviewId,divPreviewId){
//	console.log("AAAAAAAAAAAAAAAAAAAAa");
//	
//    var allowExtention=".jpg,.bmp,.gif,.png";//允许上传文件的后缀名document.getElementById("hfAllowPicSuffix").value;
//    var extention= fileObj.value.substring(fileObj.value.lastIndexOf(".")+1).toLowerCase();            
//    var browserVersion= window.navigator.userAgent.toUpperCase();
//    if(allowExtention.indexOf(extention) > -1){
//    	fileObj.select();
//                if(browserVersion.indexOf("MSIE 9")>-1)
//                	fileObj.blur();//不加上document.selection.createRange().text在ie9会拒绝访问
//                var newPreview =document.getElementById(divPreviewId+"New");
//                if(newPreview==null){
//                    newPreview =document.createElement("div");
//                    newPreview.setAttribute("id",divPreviewId+"New");
//                    newPreview.style.width = document.getElementById(imgPreviewId).width+"px";
//                    newPreview.style.height = document.getElementById(imgPreviewId).height+"px";
//                    newPreview.style.border="solid 1px #d2e2e2";
//                newPreview.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='scale',src='" + document.selection.createRange().text + "')";                            
//                var tempDivPreview=document.getElementById(divPreviewId);
//                tempDivPreview.parentNode.insertBefore(newPreview,tempDivPreview);
//                ie8Preview = tempDivPreview;
//                
//                var tempDivPreview2 = document.getElementById("AAAA");
//                tempDivPreview2.parentNode.insertBefore(newPreview,tempDivPreview2);
//                
//                
//                var tempDivPreview3 = document.getElementById("BBBB");
//                tempDivPreview3.parentNode.insertBefore(newPreview,tempDivPreview3);
//                
//                
//                
////                console.log(tempDivPreview);
////                $("#AAAA").append(ie8Preview);
//                tempDivPreview.style.display="none";                    
//            }
//    }
}









function test(){
	alert("GGG");
}


//點選預覽支援尺寸畫面
function approveSize(){
	$.fancybox(
			$('#approveSizePreView').html(),
			{
				'autoDimensions'	: false,
				'width'         	: 302,
				'height'        	: 398,
				'autoSize'			: true,
				'autoHeight'		: true,
				'autoScale'			: true,
				'transitionIn'		: 'none',
				'transitionOut'		: 'none',
				'padding'			: 0,
				'overlayOpacity'    : .75,
				'overlayColor'      : '#fff',
				'scrolling'			: 'no'
			}
		);
	
}

//關閉light box畫面
function closeBtn(){
	$.fancybox.close();
}


















//存入上傳後的圖片陣列
var fileArray =[];
function alex(){
//	 $.each(fileArray, function (index, file) {
////		 console.log(file.name);
//	 });
	
	
}

//移除預覽圖片
function removeImgPrew(){
	$("#createPreViewArea").empty(); 
}



////移除選圖片
//function removeImg(obj){
//	$(obj).parent().remove();
//}



//點擊預覽
//function createPreViewArea(fileName){
//	$('#a88').empty();
//	var h  = fileName;
//	var anyWindow = window.URL || window.webkitURL;
//	$(fileArray).each(function(index, file) {
//		if(fileName == file.name){
////			console.log(index);
////			console.log(file.name);
//			var objectUrl = anyWindow.createObjectURL(fileArray[index]);
//			console.log(objectUrl);
//			$('#a88').append('<img src="' + objectUrl + '" />');
//		}
//	});
//	
//	$.fancybox(
//	$('#XC').html(),
//	{
//		'autoDimensions'	: false,
//		'width'         	: 300,
//		'height'        	: 2510,
//		'autoSize'			: true,
//		'autoHeight'		: true,
//		'autoScale'			: true,
//		'transitionIn'		: 'none',
//		'transitionOut'		: 'none',
//		'padding'			: 0,
//		'overlayOpacity'    : .75,
//		'overlayColor'      : '#fff',
//		'scrolling'			: 'no'
//	}
//);
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
////	console.log(obj);
////	console.log(fileArray.length);
//	
//	
//	
//	
//	
////	console.log(fileArray[0].name);
////	console.log($(obj).index()); 
////	console.log($(this).index());
//	
//	
////	 var anyWindow = window.URL || window.webkitURL;
////	 var objectUrl = anyWindow.createObjectURL(fileArray[0]);
////	 $('#a88').append('<img src="' + objectUrl + '" />');
//////	var anyWindow = window.URL || window.webkitURL;
////	var objectUrl = anyWindow.createObjectURL(fileArray[0]);
////	$('#createPreViewArea').append('<img src="' + objectUrl + '" />');
//	 
////	var index = $($(obj).parent().parent()).index() - 1;
////	var anyWindow = window.URL || window.webkitURL;
////	var objectUrl = anyWindow.createObjectURL(fileArray[index]);
////	$('#a88').append('<img src="' + objectUrl + '" />');
////	 
//	 
//	 
//	 
////	 var anyWindow = window.URL || window.webkitURL;
////	 var objectUrl = anyWindow.createObjectURL(fileArray[0]);
////	 $('#createPreViewArea').append('<img src="' + objectUrl + '" />');
//	
//	
//
//	
//	
////	console.log($(obj).parent());
////	$("#alex99").append($(obj).parent());
////	$("#alex99").clone().prependTo("p");
////	$("b").clone().prependTo("p");
////	var file = $(obj).file;
////	console.log($(obj).parent().children()[0]);
////	var c = $(obj).parent().children()[0];
//
////	var g = $(obj).parent().children()[0];
////	console.log(file);
////	$("#alex99").prepend(g);
////	console.log(c);
////	$("#alex99").prepend('756456456456456');
////	$("alex99").clone().append("AAAAAAAAAAA");
//	
//	
//	
//	
//	
////	console.log(c);
////	var c = "d4645645";
////	$(c).clone().prependTo($("#alex99"));
////	
////	
////	var g ="44545456564";
//////	$(c).clone().prependTo($("#alex99"));
////	
////	$("#alex99").clone().prependTo($(g));
//	
////	console.log($(obj+ " "));
//	
////	console.log($(file).name);
//	
////	$("#alex99").append($(obj).parent().children()[0]);
//	
////	var preView2 = document.getElementsByName("alex99")[1];
////	$(".fancy").live('click', function(event) {
////		$(g).parent().children()[0],
////    	'autoDimensions'	: false,
////		'width'             : 300,
////		'height'            : 250,
////		'autoSize'			: true,
////		'autoHeight'		: true,
////		'autoScale'			: true,
////		'transitionIn'		: 'none',
////		'transitionOut'		: 'none',
////		'padding'			: 0,
////		'overlayOpacity'    : .75,
////		'overlayColor'      : '#fff',
////		'scrolling'			: 'no'
////	 });
////	console.log($('#createPreViewArea').html());
//	
//	
////$.fancybox(
////		$('#XC').html(),
////		{
////			'autoDimensions'	: false,
////			'width'         	: 300,
////			'height'        	: 2510,
////			'autoSize'			: true,
////			'autoHeight'		: true,
////			'autoScale'			: true,
////			'transitionIn'		: 'none',
////			'transitionOut'		: 'none',
////			'padding'			: 0,
////			'overlayOpacity'    : .75,
////			'overlayColor'      : '#fff',
////			'scrolling'			: 'no'
////		}
////	);
//}