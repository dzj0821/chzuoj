package cn.edu.chzu.chzuoj.dao;

import java.util.List;

import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import cn.edu.chzu.chzuoj.entity.New;

/**
 * 
 * @author dzj0821
 *
 */
public interface NewDao {
	/**
	 * 获取有效的新闻列表
	 * @return
	 */
	@Select("SELECT * FROM `news` WHERE `defunct` != 'Y' ORDER BY `importance` ASC, `time` DESC LIMIT 50")
	@Results(id = "newMap", value = {
			@Result(column = "news_id", property = "id", id = true),
			@Result(column = "user_id", property = "user", one = @One(select = "cn.edu.chzu.chzuoj.dao.UserDao.selectUserById"))
	})
	public List<New> selectNews();
}
