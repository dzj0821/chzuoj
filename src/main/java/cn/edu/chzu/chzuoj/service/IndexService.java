package cn.edu.chzu.chzuoj.service;

import java.util.Map;

/**
 * 
 * @author dzj0821
 *
 */
public interface IndexService {
	
	/**
	 * 获取最近的提交速度和单位
	 * @see #getSubmitSpeedUnit()
	 * @param allChartData 总提交图标数据
	 * @param administrator 是否是管理员
	 * @return map中参数speed为String类型的速度，参数speedUnit为速度单位字符串在i18n中的key
	 */
	public Map<String, String> getSubmitSpeedAndUnit(String[][] allChartData, boolean administrator);
}
