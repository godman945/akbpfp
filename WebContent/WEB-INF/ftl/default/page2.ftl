<#--
    @deprecated
    @param int pageNo       目前頁數
    @param int pageSize     每頁幾筆
    @param int pageCount    共幾頁
    @param int totalCount    共幾筆
    @javascript wantSearch(pageNo)  連結導向(到第幾頁)
-->
<#assign s=JspTaglibs["/struts-tags"]>
<#assign t=JspTaglibs["http://tiles.apache.org/tags-tiles"]>
        <#if pageNo?exists && pageSize?exists && pageCount?exists && totalCount?exists>
            <span class="pages">
                <input type="hidden" id="pageNo" name="pageNo" />
                <#assign limit = 5 />
                <#assign half = (limit/2)?floor />
                <#assign startPage = 1 />
                <#assign endPage = pageCount />
                <#if pageNo <= half><#-- 前幾頁 -->
                    <#if (pageCount > limit)><#assign endPage = limit /></#if>
                <#elseif (pageCount - pageNo < half)><#-- 後幾頁 -->
                    <#if (pageCount > limit)><#assign startPage = pageCount - limit + 1></#if>
                <#else><#-- 中間幾頁 -->
                    <#if limit % 2 == 0><#assign startPage = pageNo - half + 1>
                    <#else><#assign startPage = pageNo - half>
                    </#if>
                    <#assign endPage = pageNo + half>
                </#if>
                <#if pageCount == 0><#assign endPage = startPage></#if><#-- 沒有資料 -->
                <#if (pageNo > 1)><a href="javascript:wantSearch(1);">第一頁</a>
                <#else>第一頁
                </#if> 
                <#list startPage..endPage as i>
                    <#if i == pageNo><b>${i}</b> 
                    <#else><a href="javascript:wantSearch(${i});">${i}</a> 
                    </#if>
                </#list>
                <#if (pageNo < pageCount)><a href="javascript:wantSearch(${pageCount!});">最終頁</a>
                <#else>最終頁
                </#if>顯示 
                <select id="pageSize" name="pageSize" onchange="wantSearch()">
                <#if pageSize == 50>
                    <option>20</option>
                    <option selected="selected">50</option>
                    <option>100</option>
                <#elseif pageSize == 100>
                    <option>20</option>
                    <option>50</option>
                    <option selected="selected">100</option>
                <#else>
                    <option selected="selected">20</option>
                    <option>50</option>
                    <option>100</option>
                </#if>
                </select> 行 共 ${pageCount!} 頁 / 共 ${totalCount!} 筆
            </span>
        </#if>