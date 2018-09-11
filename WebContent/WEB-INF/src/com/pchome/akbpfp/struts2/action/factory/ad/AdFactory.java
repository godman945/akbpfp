package com.pchome.akbpfp.struts2.action.factory.ad;

import com.pchome.akbpfp.struts2.action.intfc.ad.IAd;
import com.pchome.enumerate.ad.EnumAdStyleType;

public class AdFactory {

	private MultimediaAd multimediaAd;
	private ProdAd prodAd;
	private VideoAd videoAd;
	
	public IAd getaAdObject(EnumAdStyleType enumAdStyleType) throws Exception {
		switch (enumAdStyleType) {
			case AD_STYLE_MULTIMEDIA:
				return multimediaAd;
			case AD_STYLE_PRODUCT:
				return prodAd;
			case AD_STYLE_VIDEO:
				return videoAd;
		}
		return null;
	}

	public MultimediaAd getMultimediaAd() {
		return multimediaAd;
	}

	public void setMultimediaAd(MultimediaAd multimediaAd) {
		this.multimediaAd = multimediaAd;
	}

	public ProdAd getProdAd() {
		return prodAd;
	}

	public void setProdAd(ProdAd prodAd) {
		this.prodAd = prodAd;
	}

	public VideoAd getVideoAd() {
		return videoAd;
	}

	public void setVideoAd(VideoAd videoAd) {
		this.videoAd = videoAd;
	}
	
	
}
