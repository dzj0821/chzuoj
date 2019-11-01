package cn.edu.chzu.chzuoj.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

/**
 * 
 * @author dzj0821
 *
 */
@Component
public class LocaleUtil {
	@Autowired
	private static MessageSource messageSource;
	
	public static String getMessage(String key) {
		return getMessage(key, null, null);
	}
	
	public static String getMessage(String key, Object[] args) {
		return getMessage(key, args, null);
	}
	
	public static String getMessage(String key, String defaultMessage) {
		return getMessage(key, null, defaultMessage);
	}
	
	public static String getMessage(String key, Object[] args, String defaultMessage) {
		return messageSource.getMessage(key, args, defaultMessage, LocaleContextHolder.getLocale());
	}
	
	@Autowired
	public void setMessageSource(MessageSource messageSource) {
		LocaleUtil.messageSource = messageSource;
	}
	
	private LocaleUtil() {}
}
