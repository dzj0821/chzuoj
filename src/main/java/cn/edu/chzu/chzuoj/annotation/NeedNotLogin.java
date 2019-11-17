package cn.edu.chzu.chzuoj.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 当need-login（需要登录才能访问OJ内容）开启时，被此注解标注的方法可以被未登录用户访问
 * @author dzj0821
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface NeedNotLogin {

}
