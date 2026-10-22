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

        // 1. 保安伸手：把你的请求头(Header)里的 Authorization 给我看看
        String authHeader = request.getHeader("Authorization");

        // 2. 查证：如果没带，或者不是以 "Bearer " 开头的，直接拒绝
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(401); // 401 状态码代表“未授权”
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"error\":\"请先登录，没有通行证不能进！\"}");
            return false; // false 代表：不准进！
        }

        try {
            // 3. 验卡：把 "Bearer " 后面的那串长字符切下来，放进验卡机验证
            String token = authHeader.substring(7);
            String username = JwtUtil.verify(token); // 会去校验有没有过期、是不是伪造的

            // 验证通过：把用户信息存起来，后面可能用得到
            request.setAttribute("username", username);
            return true; // true 代表：放行！
        } catch (Exception e) {
            // 4. 如果 Token 过期了，或者被人瞎改过
            response.setStatus(401);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"error\":\"通行证无效或已过期，请重新登录\"}");
            return false;
        }
    }
}