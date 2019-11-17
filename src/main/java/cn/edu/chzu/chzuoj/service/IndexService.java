package cn.edu.chzu.chzuoj.service;

/**
 * 
 * @author dzj0821
 *
 */
public interface IndexService {
	/**
	 * 获得主页的图标数据中的总提交部分，通过JSON编码
	 * @return
	 */
	public String[][] getAllChartData();
	
	/**
	 * 获得主页的图标数据中的正确提交部分，通过JSON编码
	 * @return
	 */
	public String[][] getAcceptedChartData();
	
	/**
	 * 获取最近的提交速度，速度单位使用getSubmitSpeedUnit()获取
	 * @see #getSubmitSpeedUnit()
	 * @param allChartData 总提交图标数据
	 * @param administrator 是否是管理员
	 * @return 字符串类型的速度
	 */
	public String getSubmitSpeed(String[][] allChartData, boolean administrator);
	
	/**
	 * 获取最近的提交速度的单位
	 * @param administrator 是否是管理员
	 * @return 单位字符串在i18n中的key
	 */
	public String getSubmitSpeedUnit(boolean administrator);
}
