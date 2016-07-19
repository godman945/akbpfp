<#assign s=JspTaglibs["/struts-tags"]>

<link href="<@s.url value="/html/css/ad/adPlugInStyle.css" />" rel="stylesheet" type="text/css" />
<link type="text/css" rel="stylesheet" href="<@s.url value="/" />html/css/fancybox/jquery.fancybox-1.3.4.css" />
<script language="JavaScript" src="<@s.url value="/" />html/js/jquery/jquery.fancybox-1.3.4.js"></script>  
<script language="JavaScript" src="<@s.url value="/" />html/js/ad/adActionEdit.js" ></script>

<div style="display: none;">
	<img id="calImg" src="<@s.url value="/html/img/"/>icon_cal.gif" alt="Popup" class="trigger">
</div>


<div class="cont">
    <form method="post" id="modifyForm" name="modifyForm" action="doAdActionEdit.html">
        <input type="hidden" name="type" id="type">
        <h2>
			<div class="cal">帳戶名稱：${customer_info_title!}</div>
        	<img vspace="12" hspace="2" align="absmiddle" src="<@s.url value="/" />html/img/iconcr.gif">修改廣告
        </h2>
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
                        	<select id="adType" name="adType" disabled>
		                    	<#list adTypeList as data>
		                    		<#if '${data.type?c!}' == adType >
		                    		<option value="${data.type!}" selected >${data.typeName!}(${data.explanation!})</option>
		                    		<#else>
		                    		<option value="${data.type!}">${data.typeName!}(${data.explanation!})</option>
		                    		</#if>
		                        </#list>
		                    </select>
                        </td>
                    </tr>
                    <tr>
                        <th height="35"><span class="t_s02">* </span>廣告播放裝置</th>
                        <td>
                        	<select id="adDevice" name="adDevice" disabled>
		                    	<#list adDeviceList as data2>
		                    		<#if '${data2.devType?c!}' == adDevice >
		                    		<option value="${data2.devType!}" selected >${data2.devTypeName!}</option>
		                    		<#else>
		                    		<option value="${data2.devType!}">${data2.devTypeName!}</option>
		                    		</#if>
		                        </#list>
		                    </select>
                        </td>
                    </tr>
                </tbody>
            </table>
            <div style="clear:both;height:10px"></div>
            <h4>廣告走期及花費設定</h4>
            <table width="100%" cellspacing="1" cellpadding="0" border="0" class="tb02">
                <tbody>
                    <tr>
                        <th height="35"><span class="t_s02">* </span>廣告開始日期</th>
                        <td>
                        <#if !addFlag?exists || addFlag!="y">
                            ${adActionStartDate!}
                            <input type="hidden" name="adActionStartDate" value="${adActionStartDate!}" />
                        <#else>
                        	<input type="text" id="adActionStartDate" name="adActionStartDate" value="${adActionStartDate!}" readonly="true" />
                        </#if>
                       	</td>
                    </tr>
                    <tr>
                        <th height="35"><span class="t_s02">* </span>廣告結束日期</th>
                        <td>
                            <input type="radio" id="selAdActionEndDate" name="selAdActionEndDate" value="N" onclick="cleanEndDate();" <#if selAdActionEndDate == "N">checked</#if> <#if !addFlag?exists || addFlag!="y">disabled</#if> />無  
                            <input type="radio" id="selAdActionEndDate" name="selAdActionEndDate" value="Y" <#if selAdActionEndDate == "Y">checked</#if> <#if !addFlag?exists || addFlag!="y">disabled</#if> />
                            <#if !addFlag?exists || addFlag!="y">
                                ${adActionEndDate!}
                                <input type="hidden" name="adActionEndDate" value="${adActionEndDate!}" />
                            <#else>
                                <input type="text" id="adActionEndDate" name="adActionEndDate" value="${adActionEndDate!}" readonly="true" />
                            </#if>
                        </td>
                    </tr>
                    <tr>
                        <th height="35">
							<span class="t_s02">* </span>每日預算 <a style="cursor:pointer;" onclick="opennots(1)"><img src="<@s.url value="/" />html/img/question.gif" align="absmiddle"></a><br>
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
            <div style="clear:both;height:10px"></div>
            <div style="padding: 8px 8px 8px 2%;<#if adType?string == '1' >display:none;</#if>" id="detailTitle" ><span class="t_s02">* </span><a id="detailId" style="cursor: pointer;font-size:13px;" onclick="openDetail()" >進階設定+</a></div>
            <div id="selectDetail" style="display:none" >
            	<h4>廣告進階設定</h4>
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
                        	<div style="display:none;"  id="selectTimeDiv">
                        		<div class="noticepop" style="width:auto;"><h4>自訂播放時段</h4><div>
                        		<table style="font-size:16px;" cellspacing="5 20 5 20" >
                        			<thead bgcolor="#F8E0E0" >
                        				<tr>
	                        				<th rowspan="2" align="center" >時間</th>
	                        				<th rowspan="2" align="center" >全<br/>時<br/>段</th>
	                        				<th colspan="3" align="center" >上午</th>
	                        				<th colspan="3" align="center" >下午</th>
	                        			</tr>
	                        			<tr>
	                        				<th align="center">
	                        					<span style="width:22%; display:inline-block;">12</span>
												<span style="width:22%; display:inline-block;">1</span>
												<span style="width:22%; display:inline-block;">2</span>
												<span style="width:22%; display:inline-block;">3</span>
		                        				<br/>
		                        				<div style="word-spacing: -0.25em;">
			                        				<input type="checkbox" style="zoom:1.5" id="selAllTime00" name="selAllTime00" onchange="selAllTimeCheckbox('00')" />
			                        				<input type="checkbox" style="zoom:1.5" id="selAllTime01" name="selAllTime01" onchange="selAllTimeCheckbox('01')" />
			                        				<input type="checkbox" style="zoom:1.5" id="selAllTime02" name="selAllTime02" onchange="selAllTimeCheckbox('02')" />
			                        				<input type="checkbox" style="zoom:1.5" id="selAllTime03" name="selAllTime03" onchange="selAllTimeCheckbox('03')" />
		                        				</div>
	                        				</th>
	                        				<th align="center">
	                        					<span style="width:22%; display:inline-block;">4</span>
												<span style="width:22%; display:inline-block;">5</span>
												<span style="width:22%; display:inline-block;">6</span>
												<span style="width:22%; display:inline-block;">7</span>
	                        					<div style="word-spacing: -0.25em;">
			                        				<input type="checkbox" style="zoom:1.5" id="selAllTime04" name="selAllTime04" onchange="selAllTimeCheckbox('04')" />
			                        				<input type="checkbox" style="zoom:1.5" id="selAllTime05" name="selAllTime05" onchange="selAllTimeCheckbox('05')" />
			                        				<input type="checkbox" style="zoom:1.5" id="selAllTime06" name="selAllTime06" onchange="selAllTimeCheckbox('06')" />
			                        				<input type="checkbox" style="zoom:1.5" id="selAllTime07" name="selAllTime07" onchange="selAllTimeCheckbox('07')" />
		                        				</div>
	                        				</th>
	                        				<th align="center">
	                        					<span style="width:22%; display:inline-block;">8</span>
												<span style="width:22%; display:inline-block;">9</span>
												<span style="width:22%; display:inline-block;">10</span>
												<span style="width:22%; display:inline-block;">11</span>
	                        					<div style="word-spacing: -0.25em;">
			                        				<input type="checkbox" style="zoom:1.5" id="selAllTime08" name="selAllTime08" onchange="selAllTimeCheckbox('08')" />
			                        				<input type="checkbox" style="zoom:1.5" id="selAllTime09" name="selAllTime09" onchange="selAllTimeCheckbox('09')" />
			                        				<input type="checkbox" style="zoom:1.5" id="selAllTime10" name="selAllTime10" onchange="selAllTimeCheckbox('10')" />
			                        				<input type="checkbox" style="zoom:1.5" id="selAllTime11" name="selAllTime11" onchange="selAllTimeCheckbox('11')" />
		                        				</div>
	                        				</th>
	                        				<th align="center">
	                        					<span style="width:22%; display:inline-block;">12</span>
												<span style="width:22%; display:inline-block;">1</span>
												<span style="width:22%; display:inline-block;">2</span>
												<span style="width:22%; display:inline-block;">3</span>
	                        					<div style="word-spacing: -0.25em;">
			                        				<input type="checkbox" style="zoom:1.5" id="selAllTime12" name="selAllTime12" onchange="selAllTimeCheckbox('12')" />
			                        				<input type="checkbox" style="zoom:1.5" id="selAllTime13" name="selAllTime13" onchange="selAllTimeCheckbox('13')" />
			                        				<input type="checkbox" style="zoom:1.5" id="selAllTime14" name="selAllTime14" onchange="selAllTimeCheckbox('14')" />
			                        				<input type="checkbox" style="zoom:1.5" id="selAllTime15" name="selAllTime15" onchange="selAllTimeCheckbox('15')" />
		                        				</div>
	                        				</th>
	                        				<th align="center">
	                        					<span style="width:22%; display:inline-block;">4</span>
												<span style="width:22%; display:inline-block;">5</span>
												<span style="width:22%; display:inline-block;">6</span>
												<span style="width:22%; display:inline-block;">7</span>
	                        					<div style="word-spacing: -0.25em;">
			                        				<input type="checkbox" style="zoom:1.5" id="selAllTime16" name="selAllTime16" onchange="selAllTimeCheckbox('16')" />
			                        				<input type="checkbox" style="zoom:1.5" id="selAllTime17" name="selAllTime17" onchange="selAllTimeCheckbox('17')" />
			                        				<input type="checkbox" style="zoom:1.5" id="selAllTime18" name="selAllTime18" onchange="selAllTimeCheckbox('18')" />
			                        				<input type="checkbox" style="zoom:1.5" id="selAllTime19" name="selAllTime19" onchange="selAllTimeCheckbox('19')" />
		                        				</div>
	                        				</th>
	                        				<th align="center">
	                        					<span style="width:22%; display:inline-block;">8</span>
												<span style="width:22%; display:inline-block;">9</span>
												<span style="width:22%; display:inline-block;">10</span>
												<span style="width:22%; display:inline-block;">11</span>
	                        					<div style="word-spacing: -0.25em;">
			                        				<input type="checkbox" style="zoom:1.5" id="selAllTime20" name="selAllTime20" onchange="selAllTimeCheckbox('20')" />
			                        				<input type="checkbox" style="zoom:1.5" id="selAllTime21" name="selAllTime21" onchange="selAllTimeCheckbox('21')" />
			                        				<input type="checkbox" style="zoom:1.5" id="selAllTime22" name="selAllTime22" onchange="selAllTimeCheckbox('22')" />
			                        				<input type="checkbox" style="zoom:1.5" id="selAllTime23" name="selAllTime23" onchange="selAllTimeCheckbox('23')" />
		                        				</div>
	                        				</th>
                        				</tr>
                        			</thead>
                        			<tbody>
                        				<#assign day = ['星期一','星期二','星期三','星期四','星期五','星期六','星期日']>
                        				<#assign index = 1>
                        				<#assign seq = ['00','01','02','03','04','05','06','07','08','09','10','11','12','13','14','15','16','17','18','19','20','21','22','23']>
                        				<#list day as selDay>
	                        				<tr>
	                        					<td align="center">${selDay}</td>
	                        					<td align="center"><input type="checkbox" style="zoom:1.5" id="selAll${index}" name="selAll${index}" onchange="selAllCheckbox(${index})" /></td>
	                        					<#list seq?chunk(4) as row> 
												<td align="center"> 
												<#list row as cell><input type="checkbox" style="zoom:1.5" id="checkbox${index}${cell}" name="checkbox${index}${cell}" onchange="selCheckbox(${index}${cell})" ${timeCodeMap[index + cell]} /></#list>
												</td> 
												</#list>
	                        				</tr>
	                        				<#assign index = index + 1>
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
                    <tr>
                        <th height="35">
                        	<span class="t_s02">* </span>性別取向設定 <a style="cursor:pointer;" onclick="opennots(2)"><img src="<@s.url value="/" />html/img/question.gif" align="absmiddle"></a><br>
							<div id="shownotes2" style="visibility: hidden;" class="adnoticepop">
								<h4>性別取向設定</h4>
								<div class="adpopcont">廣告實際投放對象會依系統數據分析媒合推播廣告給您指定的性別取向對象，包含目標對象的真實性別與產品性別定位。</div>
								<a onclick="closenots(2)" style="cursor:pointer;" class="adpopclose">關閉</a>
							</div>
                        </th>
                        <td>
                           <input type="radio" value="" id="sex1" name="adActionSex" <#if adActionSex == "">checked</#if> >不分性別
                           <input type="radio" value="M" id="sex2" name="adActionSex" <#if adActionSex == "M">checked</#if> >男
                           <input type="radio" value="F" id="sex3" name="adActionSex" <#if adActionSex == "F">checked</#if> >女
                        </td>
                    </tr>
                    <tr>
                        <th height="45"><span class="t_s02">* </span>年齡區間設定</th>
                        <td>
                           <input type="radio" value="" id="age1" name="age" onclick="selAllAge()" <#if ageType == "A">checked</#if> >全部年齡
                           <input type="radio" value="" id="age2" name="age" onclick="selAnyAge()" <#if ageType == "S">checked</#if> >自訂
                           <select id="adActionStartAge" name="adActionStartAge" <#if ageType == "A">disabled</#if> > 
						        <#list adActionStartAgeMap?keys as skey>
						  		    <option value="${skey}" <#if skey == adActionStartAge>selected</#if> >${adActionStartAgeMap[skey]}</option>
						  	    </#list>
					      	</select>
					      	&nbsp;&nbsp;~&nbsp;&nbsp;
					      	<select id="adActionEndAge" name="adActionEndAge" <#if ageType == "A">disabled</#if> > 
						        <#list adActionEndAgeMap?keys as skey>
						  		    <option value="${skey}" <#if skey == adActionEndAge>selected</#if> >${adActionEndAgeMap[skey]}</option>
						  	    </#list>
					      	</select>
                        </td>
                    </tr>
                </tbody>
            </table>
            </div>
            <center style="margin:10px">
                <input type="button" value="取 消" onclick="history.go(-1);">&nbsp; 
                <input type="button" id="save" value="儲存變更">
            </center>
        </div>
		<input type="hidden" id="adActionSeq" name="adActionSeq" value="${adActionSeq!}">
		<input type="hidden" id="backPage" name="backPage" value="${backPage!}">
    </form>
</div>
<div id="introDialog" style="display:none"></div>
<input type="hidden" id="messageId" name="messageId" value="${message!!}">
