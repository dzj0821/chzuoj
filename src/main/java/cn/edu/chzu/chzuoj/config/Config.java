package cn.edu.chzu.chzuoj.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "chzuoj")
public class Config {
	private DatabaseConfig database;
	private String name;
	private String home;
	private String adminMail;
	private Integer onSiteContestId;
	private String cdnUrl;
	private String css;

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
}
