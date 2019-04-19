package com.pchome.akbpfp.db.vo.faq;

import java.util.ArrayList;

public class FaqListVO {

	private int no;
	private String name;
	private String fid;
	private ArrayList<FaqQuestionVO> faqQuestionVOs;

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public ArrayList<FaqQuestionVO> getFaqQuestionVOs() {
		return faqQuestionVOs;
	}

	public void setFaqQuestionVOs(ArrayList<FaqQuestionVO> faqQuestionVOs) {
		this.faqQuestionVOs = faqQuestionVOs;
	}
}
