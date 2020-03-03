package cn.edu.chzu.chzuoj.entity;

/**
 * 颜色主题种类
 * @author dzj0821
 *
 */
public enum ColorTheme {
	/**
	 * 白色
	 */
	DEFAULT("default"),
	/**
	 * 蓝色
	 */
	PRIMARY("primary"),
	/**
	 * 绿色
	 */
	SUCCESS("success"),
	/**
	 * 藏青色
	 */
	INFO("info"),
	/**
	 * 橙色
	 */
	WARNING("warning"),
	/**
	 * 红色
	 */
	DANGER("danger");
	
	private String value;
	
	public static ColorTheme get(int id) {
		for (ColorTheme theme : ColorTheme.values()) {
			if (theme.getId() == id) {
				
				return theme;
			}
		}
		return null;
	}
	
	private ColorTheme(String value) {
		this.value = value;
	}
	
	public int getId() {
		return ordinal();
	}
	
	public String getValue() {
		return value;
	}
}
