package u3123004163.spring_rest;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * Spring Web 初始化类
 * 学号: 3123004163
 * 姓名: 张逸壕
 *
 * 实现 WebApplicationInitializer 接口，替代传统 web.xml 配置。
 * 在 onStartup() 中注册 Spring 配置类和 DispatcherServlet。
 */
public class WebInit implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        // 检查是否已经通过web.xml注册了dispatcher
        if (servletContext.getServletRegistration("dispatcher") != null) {
            return;
        }
        
        // 创建 Spring 应用上下文，注册配置类
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        ctx.register(AppConfig.class);
        ctx.setServletContext(servletContext);

        // 创建并注册 DispatcherServlet，拦截 /api/* 路径下的所有请求
        DispatcherServlet dispatcherServlet = new DispatcherServlet(ctx);
        ServletRegistration.Dynamic registration =
                servletContext.addServlet("dispatcher", dispatcherServlet);
        if (registration != null) {
            registration.setLoadOnStartup(1);
            registration.addMapping("/api/*");
        }
    }
}
