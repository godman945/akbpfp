<#assign s=JspTaglibs["/struts-tags"]>
<#assign t=JspTaglibs["http://tiles.apache.org/tags-tiles"]>
<script language="JavaScript" src="html/js/ad/pcvideo_action_preview.js"></script>
<input type="hidden" id="messageId" value="${message!}">
<img id ="bgImg" width=100 height=100 src="${adPreviewVideoBgImg!}">
${previewHtml!}

