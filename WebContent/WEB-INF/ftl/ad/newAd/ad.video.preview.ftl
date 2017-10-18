<#assign s=JspTaglibs["/struts-tags"]>
<#assign t=JspTaglibs["http://tiles.apache.org/tags-tiles"]>
${previewHtml!}
<input type="hidden" id="messageId" value="${message!}">
<input type="hidden" id="bgImg" value="${adPreviewVideoBgImg!}">
<script language="JavaScript" src="html/js/ad/pcvideo_action_preview.js"></script>