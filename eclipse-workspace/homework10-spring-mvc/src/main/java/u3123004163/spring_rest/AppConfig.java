package u3123004163.spring_rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.BeanNameViewResolver;

import java.util.List;

/**
 * Spring 上下文配置类
 * 学号: 3123004163
 * 姓名: 张逸壕
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"u3123004163.spring_rest"})
public class AppConfig implements WebMvcConfigurer {

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

    /**
     * 配置消息转换器，确保 JSON 格式正确处理
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new MappingJackson2HttpMessageConverter());
    }
}
