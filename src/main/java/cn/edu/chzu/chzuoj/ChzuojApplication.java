package cn.edu.chzu.chzuoj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 
 * @author dzj0821
 *
 */
@SpringBootApplication
@MapperScan("cn.edu.chzu.chzuoj.dao")
public class ChzuojApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChzuojApplication.class, args);
	}

}
