<#assign s=JspTaglibs["/struts-tags"]>

<link href="<@s.url value="/html/css/ad/adPlugInStyle.css" />" rel="stylesheet" type="text/css" />
<link type="text/css" rel="stylesheet" href="<@s.url value="/" />html/css/fancybox/jquery.fancybox-1.3.4.css" />
<script language="JavaScript" src="<@s.url value="/" />html/js/jquery/jquery.fancybox-1.3.4.js"></script> 
<script language="JavaScript" src="<@s.url value="/" />html/js/ad/adActionAdd.js" ></script>

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
                        <th height="35"><span class="t_s02">* </span>廣告播放裝置</th>
                        <td>
                        	<select id="adDevice" name="adDevice">
		                    	
		                       
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
            <div style="clear:both;height:10px"></div>
            <div style="padding: 8px 8px 8px 2%;<#if adType == '1' >display:none;</#if>" id="detailTitle" ><span class="t_s02">* </span><a id="detailId" style="cursor: pointer;" onclick="openDetail()" >進階設定+</a></div>
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
	                        				<th>&nbsp;時間&nbsp;</th>
	                        				<th>&nbsp;00:00~03:59&nbsp;</th>
	                        				<th>&nbsp;04:00~07:59&nbsp;</th>
	                        				<th>&nbsp;08:00~11:59&nbsp;</th>
	                        				<th>&nbsp;12:00~15:59&nbsp;</th>
	                        				<th>&nbsp;16:00~19:59&nbsp;</th>
	                        				<th>&nbsp;20:00~23:59&nbsp;</th>
                        				</tr>
                        			</thead>
                        			<tbody>
                        				<#assign day = ['星期一','星期二','星期三','星期四','星期五','星期六','星期日']>
                        				<#assign index = 1>
                        				<#assign seq = ['00','01','02','03','04','05','06','07','08','09','10','11','12','13','14','15','16','17','18','19','20','21','22','23']>
                        				<#list day as selDay>
	                        				<tr>
	                        					<td>${selDay}</td>
	                        					<#list seq?chunk(4) as row> 
												<td> 
												<#list row as cell><input type="checkbox" style="zoom:1.5" id="checkbox${index}${cell}" name="checkbox${index}${cell}" onchange="selCheckbox(${index}${cell})" ${timeCodeMap[index + cell]} /></#list>
												</td> 
												</#list>
	                        				</tr>
	                        				<#assign index = index + 1>
                        				</#list>
                        			</tbody>
                        		</table>
                        		<center><input onclick="closeBtn();" class="popbtn" type="button" value="關閉"></center>
                        		<input type="hidden" id="timeCode" name="timeCode" />
                        	</div>
                       	</td>
                    </tr>
                    <tr>
                        <th height="45"><span class="t_s02">* </span>廣告群組設定</th>
                        <td>
                           <input type="radio" value="" id="sex1" name="adActionSex" <#if adActionSex == "">checked</#if> >ALL
                           <input type="radio" value="M" id="sex2" name="adActionSex" <#if adActionSex == "M">checked</#if> >男
                           <input type="radio" value="F" id="sex3" name="adActionSex" <#if adActionSex == "F">checked</#if> >女
                           <br/>
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
                <input type="button" id="cancel" value="取 消">&nbsp; 
                <input type="button" id="save" value="下一步! 新增分類">
            </center>
        </div>
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
    </form>
</div>
<div id="introDialog" style="display:none"></div>
<input type="hidden" id="messageId" value="${message!!}">
