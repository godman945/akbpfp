<#--
    @param int pageNo       目前頁數
    @param int pageSize     每頁幾筆
    @param int pageCount    共幾頁
    @param int totalCount   共幾筆
    @javascript wantSearch(pageNo)  連結導向(到第幾頁)
-->
<#assign s=JspTaglibs["/struts-tags"]>
<#assign t=JspTaglibs["http://tiles.apache.org/tags-tiles"]>
<span class="pages">
    <input type="hidden" id="pageNo" name="pageNo" value="${pageNo!}" />
    <input type="hidden" id="selectPageSize" name="selectPageSize" value="${pageSize!}" />
    <input type="hidden" id="pageCount" name="pageCount" value="${pageCount!}" />
    <input type="hidden" id="contentPath" name="contentPath" value="<@s.url value="/html/img/"/>" />
    <img id="fpage" src="<@s.url value="/html/img/"/>page_first_disable.gif" style="vertical-align:middle" />&nbsp
    <img id="ppage" src="<@s.url value="/html/img/"/>page_pre_disable.gif" style="vertical-align:middle" />&nbsp
    ${pageNo!}/${pageCount!}&nbsp
    <img id="npage" src="<@s.url value="/html/img/"/>page_next_disable.gif" style="vertical-align:middle" />&nbsp
    <img id="epage" src="<@s.url value="/html/img/"/>page_end_disable.gif" style="vertical-align:middle" />
    <#if pageSize?exists>
    &nbsp&nbsp顯示&nbsp
    <select id="pageSize" name="pageSize" style="vertical-align:middle">
        <option>20</option>
        <option>50</option>
        <option>100</option>
    </select>&nbsp行
    </#if>
</span>