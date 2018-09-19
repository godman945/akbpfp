package com.pchome.akbpfp.struts2.action.api;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.pchome.akbpfp.struts2.BaseCookieAction;
import com.pchome.rmi.api.IAPIProvider;

public class AdModelAPIAction extends BaseCookieAction{
	
	private IAPIProvider admAPI;
	
	private String tproNo;
	private String adNo;
	private InputStream returnAdHtml;			// 回傳廣告
	
	private String adPreviewVideoURL;
	private String adPreviewVideoBgImg="";
	private String realUrl = "";
	
	/* 商品廣告用參數 START*/
	//行銷結尾圖
	private String uploadLog;
	//logo圖
	private String uploadLogoLog;
	//廣告名稱
	private String adName;
	//商品目錄ID
	private String catalogId;
	//商品群組ID
	private String catalogGroupId;
	//logo類型
	private String logoType;
	//logo標題文字
	private String logoText;
	//logo背景顏色
	private String logoBgColor;
	//logo文字顏色
	private String logoFontColor;
	//按鈕文字
	private String btnTxt;
	//按鈕文字顏色
	private String btnFontColor;
	//按鈕背景顏色
	private String btnBgColor;
	//標籤文字
	private String disTxtType;
	//標籤背景顏色
	private String disBgColor;
	//標籤文字顏色
	private String disFontColor;
	
	private String logoImg;
	/* 商品廣告用參數 END*/
	
	/**
	 * 吐Html廣告
	 */
	public String adModelAction() throws Exception{
		
	    //log.info(" tproNo = "+tproNo+"  , adNo = "+adNo);
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	    Date date = new Date();
	    String adHtml = admAPI.getAdContent(tproNo, adNo);
	    adHtml = adHtml.replaceAll("jpg", "jpg?time="+sdf.format(date));
	    log.info(adHtml);
	    returnAdHtml = new ByteArrayInputStream(adHtml.getBytes("UTF-8"));
	    return SUCCESS;
	}
	
	
	/**
	 * 吐影音廣告
	 */
	public String adModelVideoAction() throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	    Date date = new Date();
	    String adHtml = admAPI.getAdVideoContent(adPreviewVideoURL,adPreviewVideoBgImg,realUrl);
//	    log.info(adHtml);
	    returnAdHtml = new ByteArrayInputStream(adHtml.getBytes("UTF-8"));
	    return SUCCESS;
	}
	
	/**
	 * 吐商品廣告
	 */
	public String adModelProdAction() throws Exception{
		
		InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(new File("/home/webuser/akb/adm/data/tad/c_x05_pad_tpro_0099.def")), "UTF-8"); 
		BufferedReader fileReader = new BufferedReader(inputStreamReader);
//		fileReader = new FileReader(new File("/home/webuser/akb/adm/data/tad/c_x05_pad_tpro_0099.def"));
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String sCurrentLine;
		StringBuffer adHtml = new StringBuffer();
		boolean start = false;
		while ((sCurrentLine = bufferedReader.readLine()) != null) {
			if(!start){
				if("html:".equals(sCurrentLine)){
					start = true;
				}
				continue;
			}
			if(start){
				if(sCurrentLine.indexOf("<#dad_logo_type>") >= 0){
					sCurrentLine = sCurrentLine.replace("<#dad_logo_type>", "type1");
				}
//				
//				if(sCurrentLine.indexOf("<#dad_logo_img_url>") >= 0){
//					sCurrentLine = sCurrentLine.replace("<#dad_logo_img_url>", "data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wBDAAMCAgICAgMCAgIDAwMDBAYEBAQEBAgGBgUGCQgKCgkICQkKDA8MCgsOCwkJDRENDg8QEBEQCgwSExIQEw8QEBD/2wBDAQMDAwQDBAgEBAgQCwkLEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBD/wAARCAA3ASwDASIAAhEBAxEB/8QAHgAAAQQDAQEBAAAAAAAAAAAABwAFBggDBAkCAQr/xAA/EAABAwMDAgQDBgQEBAcAAAABAgMEBQYRAAcSITEIEyJBFFFhFSMyQnGBCTORoRZSU3IXJIKSQ2KxwcLR4f/EABsBAAIDAQEBAAAAAAAAAAAAAAMEAQIFAAYH/8QAOREAAQMCAwQIBQIFBQAAAAAAAQACAwQRBRIhMVFhcRMiQYGRobHRBhQjwfAy8QdiotLhFTNCU3L/2gAMAwEAAhEDEQA/ADm3ZKAf5OD+mszdjN/6QGieinMD8nbWURGh+XT4CzdqGabFa/0h/TWUWGz/AKYGOmMaJXwrQ7AdtIsDvgY1K6yHQsRkd0D+mvSbHjA/yx00QSx8kdNY1obT+MhIzkkkDGouosoIbJiA/wAoH/p14VZsJAKlNDA+nbWtcu+e2tCq6bWauaPLrTquCY8VpUny1dR94UdB1GCOWfpqp1bt/wAUHicvGqW9F3EVRLIZlqYQ9EaLKJKUq7JQghToBA6qUACPc6zKjF6eA5Qbny8Vr0uC1NQM5Fh5+CNd+brbM7ftq+17ngvyEuJaMeI824sKJIwolQSnGDnkoHQXufxE3ZWEA7Q7XxK2hKgS6XX5XJOOoDbTaOJz78z+nvqT0Dw90/w/TVrqGwt3bgTEMlaay81EeacKBk8Vrdywnp0SG8nsORxo4bU7u2ncMeOmq7KXnbzieXmrVSlKYYAJ7rIbUroM+lB76xqjF6p2sdgPzmfRb1NgtJGLvBcfzl91Vqi7i+JarPuGr+HqpMwlAHzafSnVOtggnoHXPWemMcRjp36ZZ7j3A3wjPuxY+3yWUFoqbbkQS3UEnPctKWMgDOSltQHzPv1MozlluxW5DTzflOJCk821I6fM5AxpVmj7ZXBBdpNXYo1RacSQuLJKHUqz80LyD/TQBXVb+sJAOF1f5WjYcpiJ7lxyql97/OEToNOacioSQpDbEd5SykZUvgEIc4fUJIHur302ne3dZmKhFU25p4Ws+iSqO+ltYxk/hUQT2PQ9B7a6hXlsxbsVx2q2w8ll5KlPNx5SfNbS4TnKF9Vt/oDj6DVf6lc1At6oPW9cdEiUaqTSlx2POjNLp9RdOEec37oeAyCUkpIKiU8lHIW4tUMOW9+8pk4XSyjMGDwVc7OuWtXdC8x20Ich5oKVLMSQQWEgjkos4W4AMjqrGScdNSKRb1NkxhKMZxllxwtIcKQ4hSgcH1IKgB9SR31JL33BolqVpqozrCYZdjMqQ1KpUjLSVg5bA4jkCMfiQACPT1zob3r4pKTc0Bulz6HIYrCF+W666ry0y2OuCsjBDoKR+p+Y6BmHGKsm7esOXslpsEpXdmU8D7rdqW344lSWAUnsQOmohU7A45IYx+2stlX3V6oUItisJdqIy6qmzDhL2V+sHIGQM5yjJ6HsVY0UaDclBuSIn7bpy6LOwfMbeIU0SMZKXU5SR1Hc9M4PXW7S4rFN1ZOq7isCswWem60fWbw294QAqNjLQThs/wBNRqdaL6M8WjjPXVr5tlxJDXxEYocaWMpWghSVD5gjUUqliJyQGv7a1RqsbUGyrBKoLzfds6a5FMW2cFOrCVOxwCoeV0/TUTqVkkFXFv8ApqcoKjOQgy7DIyCk9dajkTvorw7DYqVbh02fVI9KiyX0NOzpKFqajIJALiggKUQM56AnU8qHhx22gTZEGXvzSSsylxobkdiI80/heA4tYmAMoKfUFK7djjrijmAIrJiqxuRlZ7a1lxgfy6OlP2Qtuou3JHlbtW5TXKHVU0+OZT7Plz2sOZkNrbeUFJyhP4OafWPUOmfd7bEWBbNoT7ipO+tv12fBiIf+zYqWQ468p+K2WUHzypRCZD6+SUKGIq+wUFAZajtlugAthSeo14KVA6OsDYu051vU+tr3TpxeqMFEpMVmZR0KjuFRCmX0yqmw42oAE/yznI+fRsh7Q7fq3Vf2/ru8MCFSI8bzVXDGjR5UVT3lBzy0FMpLawOXAqS4fUkgA9CaFGDroO6WrP1nwv8Ah8pdMTUEeLmmyVKiuyPIZo8JawW0g8OKKmo8ieSQMZJQcAhTSnK4UqlIqdah0r4xEdqXKbj/ABD622ktpWsJ5qU4tKEgZySpaUj3UB11QgHYrgq5O1v8Nr/iPYG2l+Obq1yGzuJGdkLEGw5NRjUngpSf+YktPhCEnj+Nzyx/TUMtbwW2tVNr9w91ro3zbolIsC8E2mtUegipImFSmUpkJWxKIAJfHpTzHT8fXpELc8PO2NyTWYrHiPtqE38M45JfqCYsREd9IJSz97LT5hVgetHJGFDryykR5Gz+3be4des6r+IW06TTKUtDcKtPxJUxmplXu18AiQhASB6itwYynGfVxopVj7Y/h7WXE3+uXaC89wq3VKVTLCcvCnVenxmacX18EqQFtr+JIbHLr2WfYD3G7fhc24e8KFL3wav6TJrk6+W7YekR1kUpiMoN5cLb8dp7mnzCSefDAH1xEr+2J2Ysu2psyB4trMuK4mWUSYlIplFqbrMtopJ4fFpZLbb2QAG1gAZ9Ska2rU8OW2ly2xSZz/iYtCBcdQpqak/Rniwy3ESpXpZXMkSWmvO4YKmzhSVZT1xy1yhE+6v4b1etGs7n/bG4E+NadgWwzcNNuqTbC2adX3VtIX8Ky6p/ykqCl8OSXHDkfh9tNdneA+g3G1sjHqm8k+BUN62JD0NqPayJLNOLSVqIccVNbUvIR3CB37ar9UrGtGPfsGzYu5cAwJE4RJVekR8QoyC4E+d9wt1a0BPqOAD8sjrojHw97MxpFTizvEtTpBhSpDUeRTIMB2PKYbU7h4GRUmHBySyohHlkkqQlPPmgqs1QhHuVZytutxrq2+XUBPVbFbnUYyw15QkGM+trzOGTx5cM8cnGcZPfUb04XBShQa/U6EmfFnJp0x6IJUR5DzD4bWU+Y2tClIWhWMhSVFJBBBI66b9EXL9EYSgdznpr390rp89M7tTSjpzHXW81JSkJbW8z5iyMpDgJCSnIIx37HOO3vjRHuyJBjcy3lNIQlKylQSrtkdD+mM6ZbmvuwbK5f4ru2l05aOBU0/JSh0BZwnKPxYOD1xoK+IbxJ3ftq5Jo22lvWtOnRIy3X3K1OU0WwEEqT5KlIJUoJPFXmYUcBOT01QDfvcndC+mjuFci6bTpqvKjvMRVkcVJGPQgrV6QrPU5x069hrDqMV6wjpyC69tfy636TBi8dJNo21+K6pMbqWFc4+FtG42HZD6FLiKfivIaeAJT6FrQlCyFY6cu2T7aC/iCuaHTbfqC597Jp7vqSUOqElR9lAIZ4tN4AIzhZ659s65ZW1et1rryFP3DVwXT6/hZamScjB6p6DoMduurY7TT7ZbpDYrTtGeU4UBC3HhLqSSlXq4rZbLoHz9Xv066wsTFVIR0z9NwvZb+H0tND1om95tdQqVT6y6x9twavJabd4yVyJ7iSH0IICkrdPpSpR5DikH2SVKPU3r2DkruTbuLd1rPzwFskoQhttQCx0CQFJz0IIx07e2qWVX7WqNzvGBZsgvAhptx1l58pacJKcKUVOpHqGMHqOpGSdHfajwy1WnUD4u3b0ve2KvOe+KlJpk56C0tefxuAOJS57dFJUQMAj20iSwNAfzWtZwOZp0VqKXfl0sUZxq47yjUuQwgHzqgyEuOKH4kojhtHQ9cfenGe5xrSh7nTKkp5LlPpFQcQ590tVXU6CjtyMdllZBzn0lZH1OoRSNpN81FCKh4jrp+HScFHk09Ssf71Ryo6ad5NoqxSbNlXBBr9w3BVo6Oa3ahdM2KwAO6lNxnWU/XAH7av0jTa/54odos1mt1O79vujEzdFGfaTKvJpt5DhwhLiVoSkD5NuIQR+w+WhTvYrwo1WkrN81WVSIzJU6tyI+YhJUMep0I5EfIElIPXGdc66xdW599TXLTui9vg6etS23IaEhSw2Ao5Q67ydOeIAyvkSUgAkjWiz4U7huWE7WbTakvwG18HJMpBQB1wFkq6EZ7hPJSfcaaiiZE76kluQv5/ugyiVri1jDcbzby/ZGms3z4bLSuIja6Td1yupUAtEqfGmQ+PsfMfQe3T+WrI+mh/vhd1QuWisVWipvWGhuUA0ZFJQ3A5qT1QZHxbuAU5ISGkgjpjqTokbR/w1anVnX2N065c1tSh0hmJAaejvDA9QeS4tQ9/StCPbBJ6CO7geAfffZy5Vv2hL/xPbtQQITstlXwx8p1aB5clpasBOTy5pUpKeHNRRgaaHQh2YPvbeNv5yS93uAY4Wvu/PugAa1eSIjgluTG4q0JSpCeLzThB9srSpI/2k/pplelCuOunlIQ62AWwkLcQkAdQoKHQn3OQNWJ3t2d3o2gtOA/X9vItQpjbTcv7Tp7fxUdlXEZDpAHE9QCSChR7FXU6BlKbvrdaQqi0WSp+YW0lqmslMdhxKBjCWkBLWf1HvosNnDPYDiFSS8Ztcnn9l5hbp3dSKY3R5LMCdTmVgoTUKa0+Gx1IShxQLiAeuAhacdSMdTqV0Le+86yg0qLCQ/nrGjHzXktgEkJCllS1j6Oqc/Q9RoYzYdWpdWXQLppn2ZUIyi2tmWx5KkKP5V5Hp/t37jvo22tslurYKqVuXZtXiRAFpcw1KZcWU4zyaSpQS/kZwlBKjjp111U2GNtnAAnYexRA5zzmBJA7O1Rv/iTu5b9aaogjVCFUg+pao3wykvyFqIICsAeYkHsOPTJ6nvog2x4ia7Fqn2FuXQCwpDgYeeDJZdZcyfxpPQgdBjCSO5zpqvCtbiXvUzdlSuKqSXFnkKvTZj4HPkQkuNE8mFZCgOaUdiMnGiBZ9v7gVikwmblusVOFEUHGVVOkGrBIKcqcadb83kkg9eH+UZxjoKPEn0lracBf01911RhsNWDm149vjt+yIE2itSUJeZAU24kKSodcg9QRqMVO3QeX3ef21Jrgv6iW7EEqqVmHIZJLbfCnyIfqAOQoO5KOowAoA/p7j2f4grHZU58YhothQCfhJQdcWD2PApTj98a9LBjNHNo12vI+1l4+bA66PrFmnMe915i0dMCrxJ3CMPh3kuj4htS2spORySn1EfQak90XLTJVt1WC0q0FOzPOJSihPpdkLcyS6TgJaUjOEY5dznHvEzu/tZUmWXTXVx3HzgNOxXSpB+SlJSpI/rjW2BRK0hblHqkOclIHIx30ucc/PBOP30CtwmjxaaOaR2rLWtl7DfW4J/L7QCFA6oo2lpYQDvBWhbyaXbtBlIRutEZkzqa0hLEuiSJhhOheS02VAto9IALiQehIA/NqIUen2NHXW3q/fq1vfEoENx+12Jy5SfLWVOEvhZaHJKEhOepWCQACRJKlQU5PoH9NRWpUMDl6NVPw/GTK7pXXkIJOWK+lrD/AG9mltb7T26ofzbhls0acXe6wVOq2bbVctW6bar1JrU2nSxIlRJlqNxI7KglJClCOG1PAKzhPIYKQexzqRbibpWVUbQqlDtyuUF1+Wn4Z3NsS4nnt58wKbX8S55eCeIBSTyB7JwdDyo0RPX0ajkylFJOG+mlpfhOlnkilkkcTHs/QL9bMLgMA0JNrW43NiisxKRgLbCx58t6fbfsbYapW9DlXHu7UKNWHEkyYooj0lttXXAC0pHzT2z0Cv8Aygt8Gx9lFO1ZqqbwzECI6pMB1q33uM1Hl8griTlslXowr369uuo1Igcc5Top+HLZCgbq1ms1G9XK83bduxQ9MFDhuSJbrzgX5LYDbbqkIJbVyUG1kYA49SpJ5MJmaXv+blsdbfT6ut9Pp34ak6cdUaKrD7NDB5+6Fe4Nube0achrb6+JtyxvMdacelUhUAjhxCXEguLyhzKiAeKkgAKGdRSPFjuS2Wpkgx2FuJS46Ecy2knqriMcsDrjPXXSi2/Dvs1FXAqVK2xoTUp1tZbS+5JqSVnlj76FOIewkdMttnKupwMAy8eHtlmHPdnbZWAVUmRARJLdkQXVKbW2rKgy1FLhS4SgrGEKaAHEkFRSWG8MQic9zyP+Ry3PPKA3yTBBLr2A4arml/hTbZUtKk7q8ILTziJBdoUgSltpUeK47SSptwqTghLjrOCcEgerWvJoG3Kaf5sPcSa7NRS0y1sO0JTbaphQgmI24HlE8VKcSXVJSnCAQDywL513wveH9aJMW4dvg245JxLmUWdJVUo6FKTksRonnRUFOSQhxBUUg5SOqk0d322rkbL7pVrb16S/JagKaeivvsKYdcjvNJdbK21AFCwlYSpJHRSVDQG00hNxUP8ACP8AsVy4AatHn7qMx6TbUh95p67RGQmA0+065AcUlcpRb8yOQkkpCQp0hzBCvLHQcsjyuiW+pCPKvSGFhaku+dEkJTj8qmylCirI78ggg/MddMy+mvCgMDTTYH/9h/p/t7/8IeYbvX3X04ycZx7Z180tLTSqu4V+tXzMpq49l1+DRpSgU+fKpypeMjoUAOICSD1yoLH01Xmu+IS8dlaoaXuTui9U5yWinEWHTm1ElOW1KZQFLT1I74BHUA4wbfqjJWSVtgntoD7u+EG1NyarKuejV+bQa1LUFPK4CRFdIzklslKgo9BlKwkY/CTnS2IU0k7AYnEHgbLQ+Hn0AnMeJOyMI0dkz2Om3UG3K6rdfe/ETfRLguGlVWI0InAlhpSfjHkghnzEJZUjCVKKscMZ7AciQKX9l7nrlRLMeDV6rCaDRWxFprTCcLwSlLqSUBI5JKSEEnIyhPYSrdjZzc/ZJ9DtzQUvUx5fBipwVlyMtXUhJJAUhXTsoDODjOM6hVNvSbFWHRLWhQBGckEf+mvKOp5aZxDDY/za+e1fZ6T4UwmuhbJTVOZh3WA8vQrar+wDNK80T9pb5hPMuFGHatGAR2xzJjDI7kY78h1+e3ZNsrtt5ufTrakUmVHWkJf+NUpxX1wnpnPt00QbX8SV60hj4RusNKQGwn7xDalgA55BQTyBz75+fXUqX4iYdSZdcqtuQDIkBAW/Hdcb5kHJURyPqPzHTOPToZlrLZXtuOfutfDvhGLD5c4jEgP/AIN+4suO4pupe8e4NvvvO0WfDhSXzl95qhwg46fmtzyeSz9SdbcvxC71TGvJcvmW0jBOWI7EckZ/zNoB/v8A/enNrc/biQiMZduFqQG2lOLH34U5j7whCVNJSn39zkn6HWMXxs9NTIdmwWoas5bQw084kqB6c8ukBJA64KjlaQB1JAulynWH0Xom0tHEbuoRcfyNP2UTm7n7nz2lMv7hXIttwELR9rPlKv25/wBu2oytl8rLnP1qJJwckknPUn9dFCPuDsNEQHptKqk1/wBRU0htDCCe4HIrJySPkQPr7t7m5WyLyER028srSgZddBVyyBk4C08SOuCCR7kDrqwrXtNhCbdyeirY4NIqcgcAAo9Ym5bO2FcVVKladLuGK6kNyYU6MhZWjrng6QVIPXsDxOfUDgEdBNh9yNptyLSS9tQiFBixDiTSmIyYyojiupCmkgAZJPqGQe4J1QCqb07I06ow3Krt81Uo6WfWlmSlpSltpIT5iStwEKV+IBIOCCnJBBsh4CLuoFwUG7LlotlN0WDIqCENNNy3CjklHqUnPXucYOfbr0zqXSOkGcxlvgvnHxm6kq3dK1pbKAN2vdfsVymoTjjAdQtKsdRhXInTXOkOw0LLiUOtBPJaSO3zzrVFfRx5NxQoj8WVKIz+hOhzurubItq3qlMapbbykR1AJUO/T5A9T29/noL6gAaBfPY6dznIQ+ILxn7S7cUqdbUKmiu1GQ040uiIP3fHryLwIUhpB656FZHXjjrrmVcV1Tq5cky5qJadPozEiSZESHTnVrTHBUSPLKllwEHrnoM9kpGEieu7u1AXI9fVX2ksSfHmTHUSS+w84tQ5YVzSXVFGR0zj+2NS6T4i37hq7FYoVJosZpjATSHGS/FQ0AAUpaeQttKSEg5HHPI5HY60g3oB1WX3m+zhoobnkdqSButqfFBur3Ze1UbgsXdB+2UpaKWTVY6HHfLByUl0JDwT8sODuP11u27KuKEFldgXK5ClAIix6bLWwyFE9MCQy+F5I9sE/PVoKrfW9W79rU+s7dWXJtqmMuurkSYTsOWX3EEA8IjxabQn8SssoUVEkZ6qzAKxdm6oqDlOrm5FwPzHnklbcmkR4TjJKRlbiWwt1s9E9Ug5GOntokxdE0BzG2PF1j/SB5oEL+lccriHDbsuPMqFUrcHcWguNKpuxUeVUIz5U1Nq0d96WyFJSUthbJZLfpKeISEgAjiAO+lU7+3ID7rcexZltSy55kh6G7JhvBSTknm84peBkZ9XTIAxkDR+tuzK4uK3Kpd7RqwzHCuMduvee6y256lBKlIQ2CVE4AQgeo5yRqE3Ns/aFCabq1ap92QCtDnmeXKgyFeYQcIbbbcJVkk+o56AgpyQRUxZTmdF1d4uR6rmVAdoJetuNr+CClUpt9XrWOE+pkvPNeYyqpVmIjlkj/xH1pBPXtyydN7+zt2ol5qMyCprPrfamNvIT8/U2pSTjPsTjRssPw/25ehW1Mg3tTExVIWHKhHMMygrOEtJKFDI6HOV5zjA76IMrw4W1QJMVmn3AuOmCniWw249I59QeUhT3EH5pQ2E9fY9NaMVLVOjDoG2G4jXntSM+I0rJOjndfiCSOWxAujeH4OREolbh29HmcvvYEp9xh3jntlaAnlj1YzjGOuSBqb27szZ9qS2blo27spMxpQCoX2G4tlwHu2t1LnHj7ZA6dx1xrzfPhMuVipOS4AkyIshpDzbMcpU+ObaXOHBYSkekqVyygKSlRwcdcNqeGm+3JLLlBo9wvlDKXyHHILaOBVxzhUoJKcnqewGSrA66UdQYiCHtkIO39I9UX/UMPkGR4BB4n0RHpb1mXAtEVyvTIUpachp2muKGf8AcnIwfb3PyGmO4UbfU5xcaVc8kSkfiZVECMfvyJ+XTjp0VRN249smQqjUaZDjhxhldYMeputoSCpahhx5KEIGAcqSrJTgH1EMdxbF2WafGr1er8dM18uMoYIDEZaUJCg+G2xzSCErSEguHlwB6qGNellxOU2lAHFYlVDhMWsbieA/yVDjVdvahNMCLU5Ul7qVJaABSP8ANkpwcft+utKoUq0grAlTiCnJUWkpIPuMZOQD75GfkNSKrUagU9nhSrrpj7TaE4bbjSWvVjqkAtYwPnkZ1DZriOqgok5/bW0yN9uu7XgsOSWO/wBNunHX2TdKpdo+WfMfnpWPMIKUpI7ejPy69z/bUq2i3Zpm0MmqxV0SLcFCrrSUzqbLISoOtpWGXm3OKkhaFOKUOSFpPunIBEGncSSdMMtIyo6h0QIsSoZPY3AC6Y7Pbh7f740mowtvHHJDtJS7Oq1PqcViDJTEUQEpSWR8MGOawFFGFrIyQ3nOiJcEy4aXSanVK7Z85+35TUSN8Ct1pSIakLIZXy8pWQCohBWjjnClcyBquv8ACit2LVKvuTMnRUPxXKZGgPIUSOSVucsZHX8nz10FvS0rbqdCRbtQppfpz4Y5MLfcJ+6WlaDz5cj6kIPU/lH1zg1lHM6Q9DKWi2wBp143aT5o0jaqYF0UmXTTQWv4HRUP8QPiL212xrEe07kpDtWuelJKatRVJYLb+VJKGpUxaVpQrhnKmG1AggHh31z43lve5d278nX3XmaJGflhuOzCpSkIjRWW0BDbTSORISEp7knJzo3/AMR2lmmeKa4XOGBOiRJeQe/Jv/8ANVTf/F9dPMjyDQp5pIAa43/OGiwuAgkHuNYldtZF6xntojdi5fdLS0tWXLv956T++vPnjPbOlpaYSSaLqoNFvS3aha1ww0yafUmVMPNnvg9lA+ygQCD7EA65Xbh2Q9t5e1YsydLblLpclTAfQjAdT3SrBzxJSQcdcdsnvpaWsfF2NyNf23svqn8LamT5qelJuzLmtxBAv4HXfpuTAiHFWkEtLyMYCF8TrKGENDn8bKbHsC4Ccf8Ab/76WlrDA0X3H5eNjOkaNfD0XnnKWs/DT1qycgKR3/fI17QzVllI+KOCcYCQP/kdLS1cC66nb056xPifdZk0yQ6sJcXIKs+nDqRg/wDbr05TmG0c3lSRn8xcyM/oMaWlqwaCnXwMYwu281jtazFbh3XCs6hKhvSag+GkuOlxryehKlHPIKwkKPTvjXUfaCzrT2gsGn2VQWVlqKjk68U4Lzp/G4QPcn+2NLS0hWE5gzsXxv4gmdLM3S3LmVL1XCy2ghsDJz0KTqCX7U4tVpz8V8DC0FP4SdLS1nPWFGLG6oLuBtnbUK5ZRTTh5D7pCw0so9Sj9MZydWO212ps2wbbhwV29Ak1FpSn3JL7CHnEOqxkIcUCoAYA6HrjOlpa9fgUTZMznC9rff2Xl/iWokjDI2mwN/K3upw9VCO3b20z1lVNrERyBWabGnxXOq2JLKXW1Y+aVAg6Wlr01gdCvG3INwo2u3bJZQGmbOojaEAhKUU5oAA/L09NYGolBpbvn0yhU+I6M+tiKhCuv1AzpaWqiGMG4aPBS6eVwsXHxXz/ABDIhSmpkc4dYcS4gkZAUk5HT9da6q1CXGVJTMjwXVuABtCnQpCcdwfLWQP+v9tLS0YhButeq3bIly2lKuzyeDayFsvvtoQrCAeiWM8lBKQSAc8RkgAais1y31tuSZ1ZTKkOKWs+XJeSrJ7cuUUgqBySeXXI7ddLS1UBTdMlTqbkuK1QDeEVqnxWeaQoSPJcWVFXHCWAoqBPdQwPZWNRCqRo7KlparkKSEtFwFpD4yrlx4Dm2n1Y9XX0498+nS0tTsUJkmxoyWvN+3IK1eUF8Al/kFYPo6t4z0x3x1HXviNS3Sc5GlpakrkyTHsAnGmWW6evT66WloTirsFyjR4VPFldXhrrVTZpdPoc2i14tKqLVR89tWWuXAtuspWpB9RHVtY69vfVnZv8VamTHjGXttQmiGypMlVyTSgkHASQKXyJPfsBgdxpaWlZWAm6fiJGiodv/vhdW/24Em/LuYpjEpTSYrTVOaWhlLSMhOOZKz091H9h20K3TlWRpaWhDYmAsK9Yz20tLUt2KV90tLS1K5f/2Q==");
//				}
				adHtml.append(sCurrentLine).append("\n");
			}
			
		}
		
//		System.out.println(adHtml.toString());
		
		returnAdHtml = new ByteArrayInputStream(adHtml.toString().getBytes("UTF-8"));
		return SUCCESS;
	}
	
	
	public void setAdmAPI(IAPIProvider admAPI) {
		this.admAPI = admAPI;
	}

	public InputStream getReturnAdHtml() {
		return returnAdHtml;
	}

	public void setTproNo(String tproNo) {
		this.tproNo = tproNo;
	}

	public void setAdNo(String adNo) {
		this.adNo = adNo;
	}

	public void setAdPreviewVideoURL(String adPreviewVideoURL) {
		this.adPreviewVideoURL = adPreviewVideoURL;
	}


	public void setAdPreviewVideoBgImg(String adPreviewVideoBgImg) {
		this.adPreviewVideoBgImg = adPreviewVideoBgImg;
	}


	public void setRealUrl(String realUrl) {
		this.realUrl = realUrl;
	}


	public void setAdName(String adName) {
		this.adName = adName;
	}


	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}


	public void setCatalogGroupId(String catalogGroupId) {
		this.catalogGroupId = catalogGroupId;
	}


	public void setLogoType(String logoType) {
		this.logoType = logoType;
	}


	public void setLogoText(String logoText) {
		this.logoText = logoText;
	}


	public void setLogoBgColor(String logoBgColor) {
		this.logoBgColor = logoBgColor;
	}


	public void setLogoFontColor(String logoFontColor) {
		this.logoFontColor = logoFontColor;
	}


	public void setBtnTxt(String btnTxt) {
		this.btnTxt = btnTxt;
	}


	public void setBtnFontColor(String btnFontColor) {
		this.btnFontColor = btnFontColor;
	}


	public void setBtnBgColor(String btnBgColor) {
		this.btnBgColor = btnBgColor;
	}


	public void setDisTxtType(String disTxtType) {
		this.disTxtType = disTxtType;
	}


	public void setDisBgColor(String disBgColor) {
		this.disBgColor = disBgColor;
	}


	public void setDisFontColor(String disFontColor) {
		this.disFontColor = disFontColor;
	}


	public String getLogoImg() {
		return logoImg;
	}


	public void setLogoImg(String logoImg) {
		this.logoImg = logoImg;
	}

}
