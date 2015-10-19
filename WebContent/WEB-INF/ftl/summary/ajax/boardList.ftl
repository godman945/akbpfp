<#if (boardList?size > 0)>

<#list boardList as boards> 
	<#-- 顯示最新 6 則 -->
	<#if boards_index <= 6>
		<div class="ancli">
			<b>
				<#list enumBoardType as types>
					<#if boards.boardType == types.type>
						${types.chName!}：
					</#if>
				</#list>  
			</b>  
				<#if boards.hasUrl == "N" >
					${boards.content!}
				<#else>
					<a href="http://${boards.urlAddress!}" target="_blank">${boards.content!}</a> 
				</#if>
			<span>  
				${boards.startDate!}			
			</span>
		</div>
	</#if>
</#list>
<div style="text-align:right;margin-top:5px;margin-right:25px;"><a href="board.html" style="text-decoration:none;color:#FF0000">more</a></div>

<#else>
	<div class="ancli">無公告訊息</div>
</#if>