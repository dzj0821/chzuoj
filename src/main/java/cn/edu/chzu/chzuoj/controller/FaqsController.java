package cn.edu.chzu.chzuoj.controller;

import java.util.Arrays;
import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * @author dzj0821
 *
 */
@Controller
public class FaqsController extends BaseController {
	
	private final static Locale[] SUPPORT_LOCALES = {
		Locale.SIMPLIFIED_CHINESE,
		Locale.US
	};
	
	@GetMapping("/faqs")
	public ModelAndView faqs() {
		Locale requestLocale = request.getLocale();
		if (Arrays.stream(SUPPORT_LOCALES).anyMatch((a) -> a.equals(requestLocale))) {
			//如果请求的语言在支持的列表中
			return new ModelAndView("faqs/" + requestLocale.toString());
		}
		//如果不在支持列表中，优先找相同语言的
		for (Locale locale : SUPPORT_LOCALES) {
			if (locale.getLanguage().equals(requestLocale.getLanguage())) {
				return new ModelAndView("faqs/" + locale.toString());
			}
		}
		//都不在，返回默认
		return new ModelAndView("faqs/zh_CN");
	}
}
