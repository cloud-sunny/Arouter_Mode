package sun.com.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author sunxiaoyun
 * @description $
 * @time 19/7/19
 */
@Target(ElementType.TYPE)//声明放在类上面
@Retention(RetentionPolicy.CLASS)//java-class-run声明这个注解的声明周期
public @interface BindPath {
    String value();
}
