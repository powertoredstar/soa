package u3123004163.spring_rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.BeanNameViewResolver;

/**
 * Spring 上下文配置类
 * 学号: 3123004163
 * 姓名: 张逸壕
 */
@Configuration
@ComponentScan(basePackages = {"u3123004163.spring_rest"})
public class AppConfig {

    /**
     * 配置 BeanNameViewResolver，用于处理 JSON 格式数据返回。
     * 当控制器方法返回的视图名与 Bean 名称匹配时，使用该 Bean 进行渲染。
     */
    @Bean
    public ViewResolver beanNameViewResolver() {
        BeanNameViewResolver resolver = new BeanNameViewResolver();
        resolver.setOrder(1);
        return resolver;
    }
}
