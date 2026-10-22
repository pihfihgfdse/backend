package lab.backend.config;

import lab.backend.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 1. 如果是浏览器的预检请求（OPTIONS），直接放行
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        // 2. 拿请求头里的 Authorization
        String authHeader = request.getHeader("Authorization");

        // 3. 如果没带，或者格式不对，直接打回（401）
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(401);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"error\":\"请先登录，没有通行证不能进！\"}");
            return false;
        }

        try {
            // 4. 验证 Token
            String token = authHeader.substring(7);
            String username = JwtUtil.verify(token);

            request.setAttribute("username", username);
            return true; // 放行
        } catch (Exception e) {
            response.setStatus(401);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"error\":\"通行证无效或已过期，请重新登录\"}");
            return false;
        }
    }
}