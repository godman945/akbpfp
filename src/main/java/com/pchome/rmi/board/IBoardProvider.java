package com.pchome.rmi.board;

import com.pchome.rmi.mailbox.EnumCategory;


public interface IBoardProvider {
	
	public Integer add(String customerInfoId, EnumBoardType boardType, EnumCategory category);
	
	public Integer add(String customerInfoId, String content, EnumBoardType boardType, EnumCategory category) throws Exception;

    public Integer add(String customerInfoId, String content, EnumBoardType boardType, EnumCategory category, String deleteId) throws Exception;

    public Integer add(String customerInfoId, String content, String urlAddress, EnumBoardType boardType, EnumCategory category, String deleteId) throws Exception;

	public Integer hide(String customerInfoId, EnumCategory category) throws Exception;

	public Integer delete(String customerInfoId, EnumCategory category) throws Exception;

    public Integer delete(String customerInfoId, EnumCategory category, String deleteId) throws Exception;
	
}
