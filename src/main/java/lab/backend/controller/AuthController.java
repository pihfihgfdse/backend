package lab.backend.controller;

import lab.backend.model.User;
import lab.backend.repository.UserRepository;
import lab.backend.util.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository users;
    // BCrypt 加密工具，专门给密码“加盐加乱”
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public AuthController(UserRepository users) {
        this.users = users;
    }

    // 1. 注册接口
    @PostMapping("/register")
    public Map<String, String> register(@RequestBody User u) {
        // 检查账号是否已存在
        if (users.findByUsername(u.getUsername()).isPresent()) {
            throw new RuntimeException("用户名已被占用");
        }
        // 🔥 核心：把明文密码加密成一串乱码再存进去
        u.setPassword(encoder.encode(u.getPassword()));
        u.setRole("member");
        users.save(u);
        return Map.of("message", "注册成功");
    }

    // 2. 登录接口
    @PostMapping("/login")
    public Map<String, String> login(@RequestBody User u) {
        // 去数据库里按用户名找人，找不到就报错
        User dbUser = users.findByUsername(u.getUsername())
                .orElseThrow(() -> new RuntimeException("用户名或密码错误"));

        // 🔥 核心：对比用户输入的密码 和 数据库里的乱码是否匹配
        if (!encoder.matches(u.getPassword(), dbUser.getPassword())) {
            throw new RuntimeException("用户名或密码错误");
        }

        // 登录成功，生成 Token 通行证
        String token = JwtUtil.generate(dbUser.getId(), dbUser.getUsername());

        // 把通行证发给前端
        return Map.of("token", token, "role", dbUser.getRole());
    }
}