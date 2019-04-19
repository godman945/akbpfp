package com.pchome.enumerate.ad;

public enum EnumAdVideoDownloadStatus {
	
	WAIT(0, "尚未下載"),
	FINISH(1, "下載完成"),
	DOWNLOAD(2, "下載中"),
	MP4_FAIL(3, "mp4下載失敗"),
	WEBM_FAIL(4, "webm下載失敗"),
	FAIL(5, "下載失敗"),
	SCP_FAIL(6, "搬移失敗");
	
	private final int status;
	private final String downloadValue;
	
	private EnumAdVideoDownloadStatus(int status, String downloadValue){
		this.status = status;
		this.downloadValue = downloadValue;
	}

	public int getStatus() {
		return status;
	}

	public String getDownloadValue() {
		return downloadValue;
	}

}

