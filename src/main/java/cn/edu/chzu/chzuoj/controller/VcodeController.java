package cn.edu.chzu.chzuoj.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.chzu.chzuoj.annotation.NeedNotLogin;
import cn.edu.chzu.chzuoj.service.VcodeService;
import cn.edu.chzu.chzuoj.util.SessionAttrNameUtil;

/**
 * 
 * @author dzj0821
 *
 */
@Controller
public class VcodeController extends BaseController {
	
	@Autowired
	private VcodeService vcodeService;
	
	@NeedNotLogin
	@RequestMapping(value = "/vcode", produces = MediaType.IMAGE_JPEG_VALUE)
	@ResponseBody
	public byte[] vcode() {
		//阻止浏览器使用缓存
		response.setHeader("Expires", "Mon, 26 Jul 1997 05:00:00 GMT");
		response.addHeader("Cache-Control", "no-cache");
		response.addHeader("Pragma", "no-cache");
		
		String code = vcodeService.generateCode();
		//放入session
		request.getSession().setAttribute(SessionAttrNameUtil.getVcode(), code);
		return vcodeService.generateCodeImage(code);
	}
}
