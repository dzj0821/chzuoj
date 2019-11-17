package cn.edu.chzu.chzuoj.config.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import cn.edu.chzu.chzuoj.interceptor.NeedLoginCheckInterceptor;
import cn.edu.chzu.chzuoj.interceptor.OnSiteContentCehckInterceptor;

/**
 * SpringMVC的配置类
 * @author dzj0821
 *
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
	@Autowired
	private NeedLoginCheckInterceptor needLoginCheckInterceptor;
	@Autowired
	private OnSiteContentCehckInterceptor onSiteContentCehckInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(needLoginCheckInterceptor);
		registry.addInterceptor(onSiteContentCehckInterceptor);
	}
}
