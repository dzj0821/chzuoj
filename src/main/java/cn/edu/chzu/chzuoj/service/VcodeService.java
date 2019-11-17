package cn.edu.chzu.chzuoj.service;

/**
 * 
 * @author dzj0821
 *
 */
public interface VcodeService {
	/**
	 * 生成验证码
	 * @return 验证码
	 */
	public String generateCode();
	
	/**
	 * 根据验证码生成宽度为验证码字符串长度*15，高度24的jpeg图像
	 * @param code 验证码
	 * @return 图像字节
	 */
	public byte[] generateCodeImage(String code);
	
	/**
	 * 验证验证码是否正确
	 * @param sessionCode 服务器上存储的验证码
	 * @param code 用户提交的验证码
	 * @return 是否正确
	 */
	public boolean verify(String sessionCode, String code);
}
