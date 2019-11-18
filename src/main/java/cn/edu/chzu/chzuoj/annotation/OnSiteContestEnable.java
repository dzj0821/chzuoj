package cn.edu.chzu.chzuoj.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 被此标注标记的方法在现场赛模式中可用
 * @author dzj0821
 *
 */
@Retention(RUNTIME)
@Target(METHOD)
public @interface OnSiteContestEnable {

}
