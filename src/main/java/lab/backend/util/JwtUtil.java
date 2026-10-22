package lab.backend.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class JwtUtil {

    // 这是系统签发通行证的“防伪印章”，越长越好，绝不能泄露
    private static final SecretKey KEY = Keys.hmacShaKeyFor(
            "实验室后端的秘密钥匙请改成至少32个字符的乱码".getBytes(StandardCharsets.UTF_8));

    // 通行证有效期：24小时
    private static final long EXPIRE_MS = 24 * 60 * 60 * 1000;

    // 签发通行证
    public static String generate(Long userId, String username) {
        return Jwts.builder()
                .subject(username) // 通行证上写着“你是谁”
                .claim("uid", userId) // 附加信息：用户ID
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRE_MS))
                .signWith(KEY) // 盖章防伪
                .compact();
    }

    // 验证通行证（如果过期或伪造，会直接报错）
    public static String verify(String token) {
        return Jwts.parser().verifyWith(KEY).build()
                .parseSignedClaims(token).getPayload().getSubject();
    }
}