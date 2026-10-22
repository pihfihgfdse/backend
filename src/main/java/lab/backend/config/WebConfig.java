package lab.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final AuthInterceptor authInterceptor;

    public WebConfig(AuthInterceptor authInterceptor) {
        this.authInterceptor = authInterceptor;
    }

    // 1. 给保安排班（原来的代码）
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/auth/**");
    }

    // 2. 🔥 新增：配置跨域放行规则（给前端开的特权通道）
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 所有的接口都允许跨域
                .allowedOriginPatterns("*") // 允许任何前端网址访问
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 允许的请求动作
                .allowedHeaders("*") // 允许携带任何请求头（包括 Token）
                .allowCredentials(true); // 允许携带凭证
    }
}