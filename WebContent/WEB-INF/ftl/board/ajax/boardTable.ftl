<#assign t=JspTaglibs["http://tiles.apache.org/tags-tiles"]>

<span class="pages"><@t.insertAttribute name="page" /></span>
	   
<div style="clear:both;height:10px"></div>
         
<table id="listTable" width="100%" border="0" cellpadding="0" cellspacing="1" class="tablesorter tb01">
<thead>
	<tr>
	    <th width="20%" height="35">公告類型</th>
	    <th width="60%">公告內容</th>
	    <th width="20%">公告日期</th>
	</tr>
</thead>
<tbody>	
	<#list boardList as boards>
		<tr>
			<td width="20%" height="35">
				<#list enumBoardType as types>
					<#if boards.boardType == types.type>
						${types.chName!}
					</#if>
				</#list>  
			</td>
			<td width="60%" align="left">  
				<#if boards.hasUrl == "N" >
					${boards.content!}
				<#else>
					<a href="http://${boards.urlAddress!}" target="_blank">${boards.content!}</a> 
				</#if>
			</td>
			<td width="20%">  
				${boards.startDate!}			
			</td>
		</tr>
	</#list>
</tbody>
</table>