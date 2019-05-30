<#assign s=JspTaglibs["/struts-tags"]>
<#assign t=JspTaglibs["http://tiles.apache.org/tags-tiles"]>

<div class="container-prodmanage">

	<!-- 次目錄導覽列 開始 -->
	<div class="nav-wrap pos-relative no-pseudo h-auto">
		<div class="nav-title"><p>關鍵字成效</p></div>
	</div>
	<!-- 次目錄導覽列 結束 -->

	<div class="content-wrap p-lr60">
		<div class="content-box p-none">
            
            <form id="excerptFrom" name="excerptFrom" action="" method="post">
            	<#-- 隱藏欄位 -->
	            <input type="hidden" id="isDownload" name="isDownload" value="true"> <#-- 下載報表用 -->
	            <input type="hidden" id="whereMap" name="whereMap" value=""> <#-- 下載報表用 -->
	            <input type="hidden" id="sortBy" name="sortBy" value=""> <#-- 下載報表用 -->
	            <input type="hidden" id="showHideColumn" name="showHideColumn" value=""> <#-- 下載報表用 -->
	            <#-- 隱藏欄位 結束 -->
	            
				<!--功能列 開始-->
				<div class="nav-wrap prodtable" data-filter="all" data-menu="default" data-list="row" >
					<div class="nav-box pos-relative" >
	                        
						<!-- 下載報表按鈕 + 帳戶ID 開始 -->
						<div class="btn-create download-form pos-absolute pos-right transition-all">
							<a href="javascript:processDownloadReport();"><em></em></a>
						</div>
	
						<div class="altername-box pos-absolute pos-right pos-top">
							<span>帳戶：</span>${customer_info_title!}
						</div>
	
						<style>.altername-box{line-height:60px}</style>
						<!-- 下載報表按鈕 + 帳戶ID 結束 -->
	
						<!--左 表格欄位 篩選功能 -->
						<ul class="txt-table txt-regular">
							<li class="txt-cell select-menu p-r10" style="display:inline-block;">
								<div class="select-box pos-relative w-130">
									<p class="pos-absolute pointer-events-none ">顯示其他欄位</p>
									<div class="menu-box" onclick="$('.columnType').toggle()"></div>
									<ul class="menu-items columnType pos-absolute pos-left" style="display:none">
	                                    <li data-column-name="rankAvg" data-select="true"><em></em>平均廣告排名</li>
									</ul>
								</div>
							</li>
	
							<li class="txt-cell p-r10">
								<div class="select-box datepickr-box p-l10 pos-relative">
									<div class="txt-cell  p-r10">
										<input class="input-date" id="startDate" name="startDate" value="${startDate}" readonly="true">
										~
										<input class="input-date" id="endDate" name="endDate" value="${endDate}" readonly="true">
									</div>
									<div class="date-range-btn" onclick="$('.date-range').toggle()"></div>
									<ul class="date-dropdown date-range txt-left pos-absolute pos-right"style="display:none">
										<#list dateSelectMap?keys as itemKey>
							            	<#assign item = dateSelectMap[itemKey]>
											<li class="selectDateRange" date="${item}">${itemKey}</li>
									    </#list>
	                                </ul>
	                            </div>
	                        </li>
	
							<li class="txt-cell group-default p-r10">
								<div class="select-box pos-relative" id="example_length">
									<span class="pos-absolute">顯示</span>
	                                <select id="pageSize" name="pageSize" aria-controls="example">
	                                    <option value="10">10則</option>
	                                    <option value="20">20則</option>
	                                    <option value="30">30則</option>
	                                    <option value="40">40則</option>
	                                    <option value="50">50則</option>
	                                </select>
	                            </div>
	                        </li>
	                        
	                        <li class="txt-cell group-default">
	                            <div class="input-text">
	                                <input type="text" id="searchText" name="searchText" maxlength="20" value="" required placeholder="搜尋關鍵字">
	                            </div>
	                        </li>
	                        
	                    </ul>
	            	</div>                    
	        	</div>
	            <!-- 功能列 結束 -->
	            
	            <!-- 表格內容 開始 -->
	            <div class="prodtable-wrap m-b30" data-filter="all">
	            	<@t.insertAttribute name="keywordDetailList" />
	            </div>
	            <!-- 表格內容 結束 -->
	        
	            <#-- 頁碼 pagination 開始 -->
				<#-- data-order: 目前頁碼 -->
				<#-- data-quantity: 頁數 -->
				<div class="pagination-wrap txt-noselect m-b30" data-order="79" data-quantity="150">
				    <#-- data-num: 頁碼 -->
				    <ul class="pagination-box txt-table">
				        
				    </ul>
				</div>
				<#-- 頁碼 pagination 結束 -->
				
				<!-- 圖表開始 -->
				<@t.insertAttribute name="chartTable" />
				<!-- 圖表結束 -->
			</form>
			
		</div>
	</div>
</div>