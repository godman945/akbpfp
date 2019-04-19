<#assign s=JspTaglibs["/struts-tags"]>
<#assign t=JspTaglibs["http://tiles.apache.org/tags-tiles"]>
<input type="hidden" id="messageId" value="${message!}">
<input type="hidden" id ="bgImg" value="${adPreviewVideoBgImg!}">
${previewHtml!}
<script language="JavaScript" src="html/js/ad/pcvideo_action_preview.js?t=${.now?long/1000}"></script>