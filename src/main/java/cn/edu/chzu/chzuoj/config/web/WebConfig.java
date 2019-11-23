package cn.edu.chzu.chzuoj.config.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import cn.edu.chzu.chzuoj.config.Config;
import cn.edu.chzu.chzuoj.interceptor.CsrfCheckInterceptor;
import cn.edu.chzu.chzuoj.interceptor.GlobalVarInterceptor;
import cn.edu.chzu.chzuoj.interceptor.NeedLoginCheckInterceptor;
import cn.edu.chzu.chzuoj.interceptor.ContentCehckInterceptor;

/**
 * SpringMVC的配置类
 * @author dzj0821
 *
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
	@Autowired
	private Config config;
	@Autowired
	private NeedLoginCheckInterceptor needLoginCheckInterceptor;
	@Autowired
	private ContentCehckInterceptor contentCehckInterceptor;
	@Autowired
	private GlobalVarInterceptor globalVarInterceptor;
	@Autowired
	private CsrfCheckInterceptor csrfCheckInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(globalVarInterceptor);
		if (config.getNeedLogin()) {
			registry.addInterceptor(needLoginCheckInterceptor);
		}
		if (config.getOnSiteContestId() != null || config.getExamContestId() != null || config.getOiMode()) {
			registry.addInterceptor(contentCehckInterceptor);
		}
		if (config.getCsrfCheck()) {
			registry.addInterceptor(csrfCheckInterceptor);
		}
	}
}
