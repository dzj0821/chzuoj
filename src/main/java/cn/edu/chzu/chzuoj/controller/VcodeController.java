package cn.edu.chzu.chzuoj.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.chzu.chzuoj.component.BaseComponent;
import cn.edu.chzu.chzuoj.component.NoCacheComponent;
import cn.edu.chzu.chzuoj.util.SessionAttrNameUtil;

/**
 * 
 * @author dzj0821
 *
 */
@Controller
public class VcodeController extends BaseController {
	@Autowired
	private NoCacheComponent noCacheComponent;
	/**
	 * 验证码长度
	 */
	private final static int CODE_LENGTH = 4;
	/**
	 * 验证码随机列表
	 */
	private final static String CODE_LIST = "0123456789";
	
	@RequestMapping(value = "/Vcode", produces = MediaType.IMAGE_JPEG_VALUE)
	@ResponseBody
	public byte[] vcode(@RequestParam Map<String, String> map) {
		control(new BaseComponent[] {
				noCacheComponent
		}, map, null);
		Random random = new Random();
		String code = "";
		for(int i = 0; i < CODE_LENGTH; i++) {
			code += CODE_LIST.charAt(random.nextInt(CODE_LIST.length()));
		}
		//放入session
		request.getSession().setAttribute(SessionAttrNameUtil.getVcode(), code);
		//绘制验证码
		int width = 15 * CODE_LENGTH;
		int height = 24;
		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics graphics = bufferedImage.getGraphics();
		//绘制背景
		int backgroundRed = random.nextInt(256);
		int backgroundGreen = random.nextInt(256);
		int backgroundBlue = random.nextInt(256);
		graphics.setColor(new Color(backgroundRed, backgroundGreen, backgroundBlue));
		graphics.fillRect(0, 0, width - 1, height - 1);
		//绘制边框
		graphics.setColor(Color.BLACK);
		graphics.drawRect(0, 0, width - 1, height - 1);
		//绘制干扰点
		int pointNum = random.nextInt(CODE_LENGTH * 25) + CODE_LENGTH * 25;
		graphics.setColor(new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
		for (int i = 0; i < pointNum; i++) {
			//随机范围2到width-2
			graphics.fillRect(random.nextInt(width - 4) + 2, random.nextInt(height - 4) + 2, 1, 1);
		}
		//绘制验证码
		graphics.setFont(new Font(Font.DIALOG, Font.PLAIN, 20));
		//验证码颜色是背景反色，亮一点防止看不清
		graphics.setColor(new Color(255 - backgroundRed, 255 - backgroundGreen, 255 - backgroundBlue).brighter());
		graphics.drawString(code, 7, 20);
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		try {
			ImageIO.write(bufferedImage, "jpeg", stream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return stream.toByteArray(); 
	}
}
