<#assign s=JspTaglibs["/struts-tags"]>

<link href="<@s.url value="/html/css/ad/adPlugInStyle.css" />" rel="stylesheet" type="text/css" />
<link type="text/css" rel="stylesheet" href="<@s.url value="/" />html/css/fancybox/jquery.fancybox-1.3.4.css" />
<script language="JavaScript" src="<@s.url value="/" />html/js/jquery/jquery.fancybox-1.3.4.js"></script> 
<script language="JavaScript" src="<@s.url value="/" />html/js/ad/adActionAdd.js" ></script>
<style type="text/css">
.level1 {width: 23px; height: 32px;}
.level2 {width: 38px; height: 32px;}
.level3 {width: 53px; height: 32px;}
.level4 {width: 68px; height: 32px;}
.selectTop:hover{text-decoration:underline}
</style>

<div style="display: none;">
	<img id="calImg" src="<@s.url value="/html/img/"/>icon_cal.gif" alt="Popup" class="trigger">
</div>

<div class="cont">
    <form method="post" id="modifyForm" name="modifyForm" action="doAdActionAdd.html">
        <input type="hidden" name="type" id="type">
        <h2>
			<div class="cal">帳戶名稱：${customer_info_title!}</div>
        	<img vspace="12" hspace="2" align="absmiddle" src="<@s.url value="/" />html/img/iconcr.gif">新增廣告
		</h2>
        <div class="steps"><b>輸入廣告基本設定</b> &gt; 建立分類及出價  &gt; 製作廣告及關鍵字設定  &gt; 廣告完成 </div>
        <div class="grtba">
            <h4>廣告基本設定</h4>
            <table width="100%" cellspacing="1" cellpadding="0" border="0" class="tb02">
                <tbody>
                    <tr>
                        <th height="35"><span class="t_s02">* </span>廣告名稱</th>
                        <td><input type="text" id="adActionName" name="adActionName" maxlength="20" value="${adActionName!}"></td>
                    </tr>
                    <tr>
                        <th height="35"><span class="t_s02">* </span>廣告播放類型</th>
                        <td>
                        	<select id="adType" name="adType">
                        		<#if adTypeMap?exists>
		                    	<#list adTypeMap?keys as skey>
		                    		<#if skey == adType >
		                    		<option value="${skey}" selected >${adTypeMap[skey]}</option>
		                    		<#else>
		                    		<option value="${skey}">${adTypeMap[skey]}</option>
		                    		</#if>
		                        </#list>
		                        </#if>
		                    </select>
                        </td>
                    </tr>
                    <tr>
                        <th height="35"><span class="t_s02">* </span>廣告樣式</th>
                        <td>
                        	<select id="adStyle" name="adStyle">
                        		<#if adStyleTypeMap?exists>
                        			<#list adStyleTypeMap?keys as skey>
                        					<#if adStyleTypeMap[skey] == 2>
                        						<#if (pfd_customer_info_id == "PFDC20150422001" || pfd_customer_info_id == "PFDC20161012001")>
                        							<option value="${adStyleTypeMap[skey]}" >${skey}</option>
                        						</#if>
                        					<#else>
                        						<option value="${adStyleTypeMap[skey]}" >${skey}</option>
                        					</#if>
                        			</#list>
								</#if>
		                    </select>
		                    <em id="adStyleMsg" style="color:red" class="error"></em>
                        </td>
                    </tr>
                    <tr>
                        <th height="35"><span class="t_s02">* </span>廣告播放裝置</th>
                        <td>
                        	<select id="adDevice" name="adDevice">
		                    	
		                       
		                    </select>
                        </td>
                    </tr>
                </tbody>
            </table>
		</div>
        <div style="clear:both;height:20px"></div>
        <div class="grtba">
            <h4>廣告走期及花費設定</h4>
            <table width="100%" cellspacing="1" cellpadding="0" border="0" class="tb02">
                <tbody>
                    <tr>
                        <th height="35"><span class="t_s02">* </span>廣告開始日期</th>
                        <td>
                        	<input id="adActionStartDate" name="adActionStartDate" value="${adActionStartDate!}" readonly="true"/>
                       	</td>
                    </tr>
                    <tr>
                        <th height="35"><span class="t_s02">* </span>廣告結束日期</th>
                        <td>
                            <input type="radio" value="N" id="selAdActionEndDate" name="selAdActionEndDate" onclick="cleanEndDate();" <#if selAdActionEndDate == "N">checked</#if>>無  
                            <input type="radio" value="Y" id="selAdActionEndDate" name="selAdActionEndDate" <#if selAdActionEndDate == "Y">checked</#if>>  <input value="${adActionEndDate!}" id="adActionEndDate" name="adActionEndDate" readonly="true"/><span id="chkEndDate" name="chkEndDate" style="float:righ;color:red"></span>
                        </td>
                    </tr>
                    <tr>
                        <th height="35">
							<span class="t_s02">* </span>每日廣告預算 <a style="cursor:pointer;" onclick="opennots(1)"><img src="<@s.url value="/" />html/img/question.gif" align="absmiddle"></a><br>
							<div id="shownotes1" style="visibility: hidden;" class="adnoticepop">
								<h4>每日預算設定</h4>
								<div class="adpopcont">每日廣告的實際花費，會依搜尋量的變化，與每日設定的廣告預算有小差異。</div>
								<a onclick="closenots(1)" style="cursor:pointer;" class="adpopclose">關閉</a>
							</div>
						</th>
                        <td>
                        	每日花費 NT$ <input type="text" id="adActionMax" name="adActionMax" maxlength="6" value="${adActionMax!}">
                        	<input type="hidden" id="remain" name="remain" value="${tmpRemain!}">
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div style="clear:both;height:20px"></div>
        <div style="padding: 8px 8px 8px 2%;<#if adType?string == '1' >display:none;</#if>" id="detailTitle" >
        	<span class="t_s02">* 
	        	<#if openDetail == 'N' >
	        		<input type="button" id="detailId" style="cursor: pointer;font-size:13px;" onclick="openDetail()" value="進階設定 +" />
	        		<em id="detailMsg" style="padding: 0 15px;font-style:normal;display:none;">提醒您，選擇廣告進階設定會降低廣告觸及對象，使廣告曝光進而大幅減少。</em>
	        	<#else>
	        		<input type="button" id="detailId" style="cursor: pointer;font-size:13px;" onclick="closeDetail()" value="進階設定 -" />
	        		<em id="detailMsg" style="padding: 0 15px;font-style:normal">提醒您，選擇廣告進階設定會降低廣告觸及對象，使廣告曝光進而大幅減少。</em>
	        	</#if>
        	</span>
        </div>
        
        <div id="selectDetail" <#if openDetail == 'N' > style="display:none" </#if> >
        	<div class="grtba">
            	<h4>廣告時段</h4>
            	<table width="100%" cellspacing="1" cellpadding="0" border="0" class="tb02">
                <tbody>
                    <tr>
                        <th height="45"><span class="t_s02">* </span>廣告播放時段</th>
                        <td>
                        	<input type="radio" value="N" id="selTime1" name="selTime" onchange="selAllTime()" <#if timeType == "A">checked</#if> >全天播放廣告
                        	<br/>
                        	<input type="radio" value="Y" id="selTime2" name="selTime" onchange="selAnyTime()" <#if timeType == "S">checked</#if> >
                        	<span id="openTimeDetail" >
                        	<#if timeType == "A">
                        		自訂播放時段
                        	<#else>
                        		<a id="detailId" style="cursor: pointer;" onclick="selectTime()" >自訂播放時段</a>
                        	</#if>
                        	</span>
                        	<div style="display:none;" id="selectTimeDiv">
                        		<div class="noticepop" style="width:836px;"><h4>自訂播放時段</h4><div>
                        		<table class="tmselectpop" cellspacing="0" >
                        			<thead >
                        				<tr>
	                        				<th class="timeall" rowspan="2" align="center" >時段全選</th>
	                        				<th class="timeam" colspan="3" align="center" style="border-right: 1px solid #c4c4c4;">上午&nbsp;&nbsp;<u>(00:00~11:59)</u></th>
	                        				<th class="timeam" colspan="3" align="center" >下午&nbsp;&nbsp;<u>(12:00~23:59)</u></th>
	                        			</tr>
	                        			<tr>
                                            <th align="center" style="border-bottom: 1px solid #c4c4c4;">
                                            <div>
                                                    <input type="checkbox" id="selAllTime00" name="selAllTime00" onchange="selAllTimeCheckbox('00')">
                                                    <input type="checkbox" id="selAllTime01" name="selAllTime01" onchange="selAllTimeCheckbox('01')">
                                                    <input type="checkbox" id="selAllTime02" name="selAllTime02" onchange="selAllTimeCheckbox('02')">
                                                    <input type="checkbox" id="selAllTime03" name="selAllTime03" onchange="selAllTimeCheckbox('03')">
                                                </div>
                                                <span class="tclock01">00</span>
                                                <span class="tclock01">01</span>
                                                <span class="tclock01">02</span>
                                                <span class="tclock01">03</span>
                                                <br>

                                            </th>
                                            <th align="center" style="border-bottom: 1px solid #c4c4c4;">
                                            <div>
                                                    <input type="checkbox" id="selAllTime04" name="selAllTime04" onchange="selAllTimeCheckbox('04')">
                                                    <input type="checkbox" id="selAllTime05" name="selAllTime05" onchange="selAllTimeCheckbox('05')">
                                                    <input type="checkbox" id="selAllTime06" name="selAllTime06" onchange="selAllTimeCheckbox('06')">
                                                    <input type="checkbox" id="selAllTime07" name="selAllTime07" onchange="selAllTimeCheckbox('07')">
                                                </div>
                                                <span class="tclock01">04</span>
                                                <span class="tclock01">05</span>
                                                <span class="tclock01">06</span>
                                                <span class="tclock01">07</span>

                                            </th>
                                            <th align="center" style="border-right: 1px solid #c4c4c4;border-bottom: 1px solid #c4c4c4;">
                                            <div>
                                                    <input type="checkbox" id="selAllTime08" name="selAllTime08" onchange="selAllTimeCheckbox('08')">
                                                    <input type="checkbox" id="selAllTime09" name="selAllTime09" onchange="selAllTimeCheckbox('09')">
                                                    <input type="checkbox" id="selAllTime10" name="selAllTime10" onchange="selAllTimeCheckbox('10')">
                                                    <input type="checkbox" id="selAllTime11" name="selAllTime11" onchange="selAllTimeCheckbox('11')">
                                                </div>
                                                <span class="tclock01">08</span>
                                                <span class="tclock01">09</span>
                                                <span class="tclock01">10</span>
                                                <span class="tclock01">11</span>

                                            </th>
                                            <th align="center" style="border-bottom: 1px solid #c4c4c4;">
                                            <div>
                                                    <input type="checkbox" id="selAllTime12" name="selAllTime12" onchange="selAllTimeCheckbox('12')">
                                                    <input type="checkbox" id="selAllTime13" name="selAllTime13" onchange="selAllTimeCheckbox('13')">
                                                    <input type="checkbox" id="selAllTime14" name="selAllTime14" onchange="selAllTimeCheckbox('14')">
                                                    <input type="checkbox" id="selAllTime15" name="selAllTime15" onchange="selAllTimeCheckbox('15')">
                                                </div>
                                                <span class="tclock02">12</span>
                                                <span class="tclock02">13</span>
                                                <span class="tclock02">14</span>
                                                <span class="tclock02">15</span>

                                            </th>
                                            <th align="center" style="border-bottom: 1px solid #c4c4c4;">
                                            <div>
                                                    <input type="checkbox" id="selAllTime16" name="selAllTime16" onchange="selAllTimeCheckbox('16')">
                                                    <input type="checkbox" id="selAllTime17" name="selAllTime17" onchange="selAllTimeCheckbox('17')">
                                                    <input type="checkbox" id="selAllTime18" name="selAllTime18" onchange="selAllTimeCheckbox('18')">
                                                    <input type="checkbox" id="selAllTime19" name="selAllTime19" onchange="selAllTimeCheckbox('19')">
                                                </div>
                                                <span class="tclock02">16</span>
                                                <span class="tclock02">17</span>
                                                <span class="tclock02">18</span>
                                                <span class="tclock02">19</span>

                                            </th>
                                            <th align="center" style="border-bottom: 1px solid #c4c4c4;">
                                             <div>
                                                    <input type="checkbox" id="selAllTime20" name="selAllTime20" onchange="selAllTimeCheckbox('20')">
                                                    <input type="checkbox" id="selAllTime21" name="selAllTime21" onchange="selAllTimeCheckbox('21')">
                                                    <input type="checkbox" id="selAllTime22" name="selAllTime22" onchange="selAllTimeCheckbox('22')">
                                                    <input type="checkbox" id="selAllTime23" name="selAllTime23" onchange="selAllTimeCheckbox('23')">
                                                </div>
                                                <span class="tclock02">20</span>
                                                <span class="tclock02">21</span>
                                                <span class="tclock02">22</span>
                                                <span class="tclock02">23</span>

                                            </th>
                                        </tr>
                        			</thead>
                        			<tbody>
                        				<#assign day = ['星期一','星期二','星期三','星期四','星期五','星期六','星期日']>
                        				<#assign index = 1>
                        				<#assign index2 = 0>
                        				<#assign seq = ['00','01','02','03','04','05','06','07','08','09','10','11','12','13','14','15','16','17','18','19','20','21','22','23']>
                        				<#list day as selDay>
	                        				<tr>
	                        					<td align="center">
	                        						<input type="checkbox"  id="selAll${index}" name="selAll${index}" onchange="selAllCheckbox(${index})" />
	                        						${selDay}
	                        					</td>
	                        					<#list seq?chunk(4) as row> 
												<td align="center" class="<#if index2/4 <= 2><#if index%2 == 1>time_ambg01<#else>time_ambg02</#if><#else><#if index%2 == 1>time_pmbg01<#else>time_pmbg02</#if></#if>" > 
												<#list row as cell>
													<input type="checkbox"  id="checkbox${index}${cell}" name="checkbox${index}${cell}" onchange="selCheckbox(${index}${cell})" ${timeCodeMap[index + cell]} />
													<#assign index2 = index2 + 1>
												</#list>
												</td> 
												</#list>
	                        				</tr>
	                        				<#assign index = index + 1>
	                        				<#assign index2 = 0>
                        				</#list>
                        			</tbody>
                        		</table>
                        		<center>
	                        		<input onclick="closeBtn();" class="popbtn" type="button" value="取消">
	                        		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	                        		<input onclick="saveBtn();" class="popbtn" type="button" value="確定">
                        		</center>
                        		<input type="hidden" id="timeCode" name="timeCode" />
                        	</div>
                       	</td>
                    </tr>
                </tbody>
            </table>
            </div>
            
            <div style="clear:both;height:20px"></div>
	        <div class="grtba">
	        	<h4>廣告投放地區</h4>
		        <table width="100%" cellspacing="1" cellpadding="0" border="0" class="tb02">
		        	<tbody>
		        		<tr>
			                 <th height="35"><span class="t_s02">* </span>廣告播放地區</th>
			                    <td>
			                   		<#if adCountryMap?exists>
				                    	<#list adCountryMap?keys as skey>
			                       			<input type="radio" value="${skey!}" name="countryRadio" <#if skey == 'NULL' >checked  id="sel_def" <#else>id="sel_tw" </#if> />${adCountryMap[skey!]} <br>
			                       		</#list>
			                       		<ul id="tw_list">
                                        <li>
                                            <input type="checkbox" id="AR01" name="adCity" value="AR01"> 北臺灣
                                        </li>
                                        <li>
                                            <input type="checkbox" id="AR02" name="adCity" value="AR02"> 中臺灣
                                        </li>
                                        <li>
                                            <input type="checkbox" id="AR03" name="adCity" value="AR03"> 南臺灣
                                        </li>
                                        <li>
                                            <input type="checkbox" id="AR04" name="adCity" value="AR04"> 東臺灣
                                        </li>
                                        <li>
                                            <input type="checkbox" id="AR05" name="adCity" value="AR05"> 金馬地區
                                        </li>
                                        <div style="color:graytext;">各家業者所採用的地理位置資訊源有所不同，結果可能無法完全一致。</div>
                                    </ul>
			                       		
			                       		
		                       		</#if>
			                    </td>
			             </tr>
		        	</tbody>
		        </table>
	        </div>
            
            
            
            <div style="clear:both;height:20px"></div>
            <div class="grtba">
            	<h4>廣告指定投放</h4>
	            <table width="100%" cellspacing="1" cellpadding="0" border="0" class="tb02">
	            	<tbody>
	            		<tr>
	                        <th height="70" Rowspan="2">
	                        	<input type="radio" value="0" id="adSpecificPlayType1" name="adSpecificPlayType" <#if adSpecificPlayType == "0">checked</#if> >指定廣告受眾性別/年齡
	                        </th>
	                        <td height="35">
	                           <input type="radio" value="" id="sex1" name="adActionSex" <#if adActionSex == "">checked</#if> <#if adSpecificPlayType != "0">disabled</#if> >不分性別
	                           <input type="radio" value="M" id="sex2" name="adActionSex" <#if adActionSex == "M">checked</#if> <#if adSpecificPlayType != "0">disabled</#if> >男
	                           <input type="radio" value="F" id="sex3" name="adActionSex" <#if adActionSex == "F">checked</#if> <#if adSpecificPlayType != "0">disabled</#if> >女
	                        </td>
	                    </tr>
	                    <tr>
	                        <td height="35">
	                           <input type="radio" value="" id="age1" name="age" onclick="selAllAge()" <#if ageType == "A">checked</#if> <#if adSpecificPlayType != "0">disabled</#if> >不分年齡
	                           <input type="radio" value="" id="age2" name="age" onclick="selAnyAge()" <#if ageType == "S">checked</#if> <#if adSpecificPlayType != "0">disabled</#if> >自訂
	                           <select id="adActionStartAge" name="adActionStartAge" <#if ageType == "A" || adSpecificPlayType != "0" >disabled</#if> > 
							        <#list adActionStartAgeMap?keys as skey>
							  		    <option value="${skey}" <#if skey == adActionStartAge>selected</#if> >${adActionStartAgeMap[skey]}</option>
							  	    </#list>
						      	</select>
						      	&nbsp;&nbsp;~&nbsp;&nbsp;
						      	<select id="adActionEndAge" name="adActionEndAge" <#if ageType == "A"  || adSpecificPlayType != "0" >disabled</#if> > 
							        <#list adActionEndAgeMap?keys as skey>
							  		    <option value="${skey}" <#if skey == adActionEndAge>selected</#if> >${adActionEndAgeMap[skey]}</option>
							  	    </#list>
						      	</select>
	                        </td>
	                    </tr>
	                    <tr>
	                    	<th height="35">
	                        	<input type="radio" value="1" id="adSpecificPlayType2" name="adSpecificPlayType" <#if adSpecificPlayType == "1">checked</#if> >指定投放網站類型
	                        </th>
	                        <td>
	                        	<div style="display:none;" id="websiteAddDiv">
									<div>
										<div class="inxrltbox" >
											<div class="showinxrltLayer" id="websiteAdd" >
												
											</div>
										</div>
									</div>
								</div>
	                        	<div>
		                        	<div id="test" class="wsh_typ_select">
	                            		<div id="websiteOutterBox" class="outter_box">
	                            			<div class="inner_box">
	                            				<span class="lf_slinx" autocomplete="off" autocorrect="off">
	                            					<label class="fill">
	                            					<input type="text" class="rst" aria-autocomplete="list" aria-expanded="false" aria-owns="js_mh" role="combobox" placeholder="選擇投放網站類型" autocomplete="off" autocorrect="off" value="" readonly></label>
	                            				</span>
	                            				<div class="rt_btn">
	                            					<span id="websiteButton" class="tgt_brws_but" onclick="openWebsite('1')" ><em>瀏覽</em></span>
	                            				</div>
	                            			</div>
	                            		</div>
	                            	</div>
	                            	<div id="websiteData" class="uiSctWTLayer" style="width: 449px; max-height: 256px;display:block;display:none">
		                            	<div class="ui_slctOutter sdft clearfix">
			                            	<div class="slstcont">
			                            		<ul id="websiteUi" class="idxBx">
			                            			<li style="background:#fff;border-bottom:1px solid #e8e2e2;">
														<div class="s_mrow" role="presentation">
															<div class="cntxt">
																網站類型
															</div>
															<div class="bt_slctall selectTop" style="visibility:inherit;cursor: pointer;padding-right:8px;" onclick="specificSelectAll('website')" >
																<em class="box">選擇全部</em>
															</div>
															|
															<div class="bt_slctall selectTop" style="visibility:inherit;cursor: pointer;padding-right:8px;" onclick="specificRemoveAll('website')" >
																<em class="box">取消</em>
															</div>
														</div>
													</li>
			                            		</ul>
			                            	</div>
		                            	</div>
	                            	</div>
								</div>
	                        </td>
	                    </tr>
	            	</tbody>
	            </table>
            </div>
	        <div style="clear:both;height:20px"></div>
	        <div class="grtba">
	        	<h4>廣告曝光頻率</h4>
		        <table width="100%" cellspacing="1" cellpadding="0" border="0" class="tb02">
		        	<tbody>
		        		<tr>
	                        <th height="45"><span class="t_s02">* </span>曝光頻率限制</th>
	                        <td>
	                        	<input type="radio" value="N" id="pvLimitSelect1" name="pvLimitSelect" onchange="selNoLimit()" <#if pvLimitSelect == "N">checked</#if> >無曝光頻率限制 
	                        	<br/>
	                        	<input type="radio" value="Y" id="pvLimitSelect2" name="pvLimitSelect" onchange="selAnyLimit()" <#if pvLimitSelect == "Y">checked</#if> >
	                        	<select id="adPvLimitStyle" name="adPvLimitStyle" <#if pvLimitSelect == "N">disabled</#if> > 
							        <#list adPvLimitStyleMap?keys as skey>
							  		    <option value="${skey}" <#if skey == adPvLimitStyle>selected</#if> >${adPvLimitStyleMap[skey]}</option>
							  	    </#list>
						      	</select>
						      	&nbsp;&nbsp;
						      	<select id="adPvLimitPeriod" name="adPvLimitPeriod" <#if pvLimitSelect == "N">disabled</#if> > 
							        <#list adPvLimitPeriodMap?keys as skey>
							  		    <option value="${skey}" <#if skey == adPvLimitPeriod>selected</#if> >${adPvLimitPeriodMap[skey]}</option>
							  	    </#list>
						      	</select>
						      	&nbsp;&nbsp;
						      	曝光給同一廣告受眾
						      	<input type="text" class="pkwdh" id="adPvLimitAmount" name="adPvLimitAmount" maxlength="11" value="${adPvLimitAmount!}" <#if pvLimitSelect == "N">disabled</#if> />
						      	次
	                        </td>
	                    </tr>
		        	</tbody>
		        </table>
	        </div>
	       <div style="clear:both;height:20px"></div>
        	<div class="grtba">
            	<h4>再行銷追蹤</h4>
	            <table width="100%" cellspacing="1" cellpadding="0" border="0" class="tb02">
	            	<tbody>
	            		<tr>
	                        <th height="45">再行銷追蹤</th>
	                        <td height="35">
	                           <input type="radio" value="0" id="notUseTrackingCode" name="adactionTrackingCode" checked >不使用再行銷追蹤
	                           <br>
	                           <input type="radio" value="1" id="useTrackingCode" name="adactionTrackingCode" >使用再行銷追蹤，<label class="error" style="display:none;" id="trackingCodrMsg">至少選擇一組追蹤代碼</label>
	                           <br>
	                           <div>
	                           		<div style="display:none;" id="trackingCodeAddDiv">
										<div>
											<div class="inxrltbox" style="">
												<ul class="clasf_area" id="trackingCodeAdd">
												</ul>
											</div>
										</div>
								    </div>
		                        	<div id="test" class="wsh_typ_select">
	                            		<div id="websiteOutterBox" class="outter_box">
	                            			<div class="inner_box">
	                            				<span class="lf_slinx" autocomplete="off" autocorrect="off">
	                            					<label class="fill">
	                            					<input type="text" class="rst" aria-autocomplete="list" aria-expanded="false" aria-owns="js_mh" role="combobox" placeholder="選擇再行銷代碼" autocomplete="off" autocorrect="off" value="" readonly></label>
	                            				</span>
	                            				<div class="rt_btn">
	                            					<span id="trackingCodeButton" class="tgt_brws_but" onclick="openTrackingCode('1')" ><em>瀏覽</em></span>
	                            				</div>
	                            			</div>
	                            		</div>
	                            		
	                            	</div>
	                            	<div id="trackingCodeData" class="uiSctWTLayer" style="width: 449px; max-height: 256px;display:block;display:none">
		                            	<div class="ui_slctOutter sdft clearfix">
			                            	<div class="slstcont">
			                            		<ul id="trackingCodeUl" class="idxBx">
			                            			<li style="background:#fff;border-bottom:1px solid #e8e2e2;">
														<div class="s_mrow" role="presentation">
															<div class="cntxt">
																再行銷代碼
															</div>
															<div onclick="specificTrackingCodeSelectAll()" class="bt_slctall selectTop" style="visibility:inherit;cursor: pointer;padding-right:8px;" onclick="specificTrackingCodeSelectAll()" >
																<em class="box">選擇全部</em>
															</div>

															<div onclick="specificTrackingCodeRemoveAll()" class="bt_slctall selectTop" style="visibility:inherit;cursor: pointer;padding-right:8px;" onclick="specificTrackingCodeRemoveAll()" >
																<em class="box">取消</em>
															</div>
														</div>
													</li>
			                            		</ul>
			                            	</div>
		                            	</div>
	                           </div>
	                        </td>
	                    </tr>
	            	</tbody>
	            </table>
            </div>
        
        <div style="clear:both;height:20px"></div>
	        <div class="grtba">
	        	<h4>轉換追蹤</h4>
		        <table width="100%" cellspacing="1" cellpadding="0" border="0" class="tb02">
		        	<tbody>
		        		<tr>
	                        <th height="45">轉換追蹤</th>
	                        <td>
	                        	<input type="radio" value="N" name="convertCodeRadio" onclick="changeConvertCodeType('N')" checked>不使用轉換追蹤
	                        	<br/>
	                        	<input type="radio" value="Y" name="convertCodeRadio" onclick="changeConvertCodeType('Y')" >使用轉換追蹤，套用代碼：
	                        	<select id="convertCodeSelect"  disabled> 
							       <option value="0">請選擇</option>
						      	</select>
						      	<label class="error" style="display:none;" id="convertCodrMsg">請選擇一組轉換代碼</label>
	                        </td>
	                    </tr>
		        	</tbody>
		        </table>
	        </div>
        </div>
        </div>
        
        
        
        
        <center style="margin:25px 10px 20px 10px">
            <input type="button" id="cancel" value="取 消">&nbsp; 
            <input type="button" id="save" style="color: #1d5ed6" value="繼 續">
        </center>
        <#if aid??>
        <input type="hidden" id="aid" name="aid" value="${aid!}">
        </#if>
        <#if adActionSeq??>
        <input type="hidden" id="adActionSeq" name="adActionSeq" value="${adActionSeq!}">
        </#if>
        <input type="hidden" id="adDeviceSelect" name="adDeviceSelect" value="${adDevice!}">
        <span id="adAllDevice" name="adAllDevice" style="display:none;" >${adAllDevice!}</span>
        <span id="adSearchDevice" name="adSearchDevice" style="display:none;" >${adSearchDevice!}</span>
        <span id="adChannelDevice" name="adChannelDevice" style="display:none;" >${adChannelDevice!}</span>
        <input type="hidden" id="backPage" name="backPage" value="${backPage!}">
        <input type="hidden" id="adOperatingRule" name="adOperatingRule" value="${adOperatingRule!}">
        <textarea  id="prodCodeInfo" style="display:none;" name="prodCodeInfo" value=""/></textarea>
       	<input type="hidden" id="adCountry" name="adCountry" value="NULL">
    </form>
</div>
<div id="introDialog" style="display:none"></div>
<input type="hidden" id="messageId" value="${message!!}">
<input type="hidden" id="oldwebsiteCategory" value="${oldWebsiteCategory!}" />
<input type="hidden" id="prodAdMsg" value="${prodAdMsg!}" />

​<textarea id="txtArea" rows="10" cols="70" style="display:none">
	${pfpCodeTrackingJson!}
</textarea>
​<textarea id="txtArea2" rows="10" cols="70" style="display:none">
	${pfpCodeConvertJson!}
</textarea>