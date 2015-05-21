$(document).ready(function(){
	$(function () {
		uploadButton = $("#alex");
		
		
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
	        data.context = $('<div/>').appendTo('#files');
	        $.each(data.files, function (index, file) {
	            var node = $('<p/>')
	                    .append($('<span/>').text(file.name));
	            if (!index) {
					node.append('').append(uploadButton.clone(true).data(data));
	            }
	            node.appendTo(data.context);
	        });
	    }).on('fileuploadprocessalways', function (e, data) {
	        var index = data.index,
	            file = data.files[index],
	            node = $(data.context.children()[index]);
	        if (file.preview) {
	        	//設定預覽位置
//	        	var alex = ;
	        	//1.目前class下新增
//	        	$(".aduplodul").append(file.preview);
	        	var a =
	        	'<li class="okbox">'+    
                '<div class="adboxdv">'+
                '<div class="'+'alex'+'" ></div>'+
                '<a class="fancy adinf" href="#" alt="預覽">預覽</a></div>'+
                '<ul>'+
                '<li><i>尺寸</i><b>300 x 100</b></li>'+
                '<li><i>大小</i><b>100KB</b></li>'+
                '<li><i>格式</i><b>PNG</b></li>'+
               '</ul>'+
                '<a class="addel" href="#">丟</a>'+ 
                '</li>';
	        	console.log(file);
	        	
	        	
	        	$("#alex5").attr("src",file.preview);
	        	
	        	
	        	$(".aduplodul").append(a);
	        	$("."+'alex').append(file.preview);
	            node.prepend('').prepend(file.preview);
	        }
	        if (file.error) {
	            node.append('<br>').append($('<span class="text-danger"/>').text(file.error));
	        }
	        if (index + 1 === data.files.length) {
	            data.context.find('button').text('Upload').prop('disabled', !!data.files.error);
	        }
	    }).on('fileuploadprogressall', function (e, data) {
	        var progress = parseInt(data.loaded / data.total * 100, 10);
	        $('#progress .progress-bar').css(
	            'width',
	            progress + '%'
	        );
	    }).on('fileuploaddone', function (e, data) {
	        $.each(data.result.files, function (index, file) {
	            if (file.url) {
	                var link = $('<a>')
	                    .attr('target', '_blank')
	                    .prop('href', file.url);
	                $(data.context.children()[index])
	                    .wrap(link);
	            } else if (file.error) {
	                var error = $('<span class="text-danger"/>').text(file.error);
	                $(data.context.children()[index]).append('<br>').append(error);
	            }
	        });
	    }).on('fileuploadfail', function (e, data) {
	        $.each(data.files, function (index) {
	            var error = $('<span class="text-danger"/>').text('File upload failed.');
	            $(data.context.children()[index]).append('<br>').append(error);
	        });
	    }).prop('disabled', !$.support.fileInput)
	        .parent().addClass($.support.fileInput ? undefined : 'disabled');
	});
	
	
	
	
	
	
	
	
	$(".fancy").live('click', function(event) {
    	event.preventDefault();
    	$.fancybox({
			'width'             : 300,
			'height'            : 250,
    		'href'				:'showimg.html',
    		'autoSize'			: true,
    		'autoHeight'		: true,
    		'autoScale'			: true,
    		'transitionIn'		: 'none',
    		'transitionOut'		: 'none',
    		'type'				: 'iframe',
    		'padding'			: 0,
			'overlayOpacity'    : .75,
			'overlayColor'      : '#fff',
    		'scrolling'			: 'no'
    	});
    });
});

