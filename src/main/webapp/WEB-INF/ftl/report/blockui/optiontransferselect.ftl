 
<#assign s=JspTaglibs["/struts-tags"]>
<#assign t=JspTaglibs["http://tiles.apache.org/tags-tiles"]>

 <div class="lbpop" id="optionSelectDialog"  style="display:none; cursor: default"> 
您可以在下表中選擇報表所須的顯示欄位,並加以排序
 
<table border="0" cellspacing="0" cellpadding="0"> 
  <tr> 
    
    <td width="230"><table width="100%" border="0" cellpadding="5" cellspacing="1" class="tb01"> 
    
    	<table border="0" cellspacing="0" cellpadding="0">
      		
      		<tr> 
        		<th>所有可選擇欄位</th> 
      		</tr> 
      
      		<tr> 
        		<td>
        			
        			
        			<select multiple="multiple" id="leftselect" name="leftselect" style="width:210px;height:150px;">
      					<#list tableHeadNotShowList as ths>
							<option value="${ths}">${ths}</option>
    					</#list>
      					
     				</select>
        		
        		</td> 
      		</tr> 
      		<tr>
      			<td>
      				<input id="left_up" type="button" value="^"  style="width:25px;margin:3px 0"/>
					<input id="left_down" type="button" value="v"  style="width:25px;margin:3px 0"/>
      			</td>
      		</tr>
    
    	</table>
    
    </td> 
    
    <td width="40" align="center">
    
        <input id="add" type="button" value="->" style="width:25px;margin:3px 0" /><br /> 
		<input id="remove" type="button" value="<-"  style="width:25px;margin:3px 0"/><br /> 
		<input id="alladd" type="button" value="-->>"  style="width:25px;margin:3px 0"/><br /> 
		<input id="allremove" type="button" value="<<--"  style="width:25px;margin:3px 0"/>
		
	</td> 
    
    <td width="230"><table width="100%" border="0" cellpadding="5" cellspacing="1" class="tb01"> 
      
      	<table border="0" cellspacing="0" cellpadding="0">
    
      		<tr> 
        		<th>報表顯示欄位</th> 
      		</tr> 
      		<tr> 
       	 		<td>
       	 			<select multiple="multiple" id="rightselect" name="rightselect" style="width: 210px;height:150px;">
       	 				<#list tableHeadShowList as ths>
							 <option value="${ths}">${ths}</option>
    					</#list>
    				</select>	
       	 		</td> 
      		</tr> 
      		
      		<tr>
      			<td>
      				<input  type="button" value="^"  style="width:25px;margin:3px 0" id="right_up"/>
					<input  type="button" value="v"  style="width:25px;margin:3px 0" id="right_down"/>
      			</td>
      		</tr>
    	</table>
    </td> 

  </tr> 
</table> 
 
 
<center style="margin:10px"><input id="optionOk" type="button" value="確 定" /> 
 &nbsp; <input type="button" id="optionCancel" value="取 消" /> 
</center> 

</div> 
