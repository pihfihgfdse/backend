package lab.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final AuthInterceptor authInterceptor;

    // 把刚才的保安注入进来
    public WebConfig(AuthInterceptor authInterceptor) {
        this.authInterceptor = authInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 排班规则如下：
        registry.addInterceptor(authInterceptor)
                // 1. 拦截所有以 /api/ 开头的请求
                .addPathPatterns("/api/**")
                // 2. 但是，/api/auth/ 开头的（比如登录、注册）必须放行，不然没人能拿到证
                .excludePathPatterns("/api/auth/**");
    }
}