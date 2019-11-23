package cn.edu.chzu.chzuoj.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 
 * @author dzj0821
 *
 */
@Component
@ConfigurationProperties(prefix = "chzuoj")
public class Config {
	private DatabaseConfig database;
	private String name;
	private String home;
	private String adminMail;
	private Integer onSiteContestId;
	private Integer examContestId;
	private String cdnUrl;
	private String css;
	private Boolean needLogin;
	private Boolean openRegister;
	private Boolean shareCode;
	private Boolean vcode;
	private Boolean csrfCheck;
	private Boolean registerNeedReview;
	private Boolean oiMode;

	public DatabaseConfig getDatabase() {
		return database;
	}

	public void setDatabase(DatabaseConfig database) {
		this.database = database;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHome() {
		return home;
	}

	public void setHome(String home) {
		this.home = home;
	}

	public String getAdminMail() {
		return adminMail;
	}

	public void setAdminMail(String adminMail) {
		this.adminMail = adminMail;
	}

	public Integer getOnSiteContestId() {
		return onSiteContestId;
	}

	public void setOnSiteContestId(Integer onSiteContestId) {
		this.onSiteContestId = onSiteContestId;
	}

	public Integer getExamContestId() {
		return examContestId;
	}

	public void setExamContestId(Integer examContestId) {
		this.examContestId = examContestId;
	}

	public String getCdnUrl() {
		return cdnUrl;
	}

	public void setCdnUrl(String cdnUrl) {
		this.cdnUrl = cdnUrl;
	}

	public String getCss() {
		return css;
	}

	public void setCss(String css) {
		this.css = css;
	}

	public Boolean getNeedLogin() {
		return needLogin;
	}

	public void setNeedLogin(Boolean needLogin) {
		this.needLogin = needLogin;
	}

	public Boolean getOpenRegister() {
		return openRegister;
	}

	public void setOpenRegister(Boolean openRegister) {
		this.openRegister = openRegister;
	}

	public Boolean getShareCode() {
		return shareCode;
	}

	public void setShareCode(Boolean shareCode) {
		this.shareCode = shareCode;
	}

	public Boolean getVcode() {
		return vcode;
	}

	public void setVcode(Boolean vcode) {
		this.vcode = vcode;
	}

	public Boolean getCsrfCheck() {
		return csrfCheck;
	}

	public void setCsrfCheck(Boolean csrfCheck) {
		this.csrfCheck = csrfCheck;
	}
	
	public Boolean getRegisterNeedReview() {
		return registerNeedReview;
	}
	
	public void setRegisterNeedReview(Boolean registerNeedReview) {
		this.registerNeedReview = registerNeedReview;
	}
	
	public Boolean getOiMode() {
		return oiMode;
	}
	
	public void setOiMode(Boolean oiMode) {
		this.oiMode = oiMode;
	}
}
