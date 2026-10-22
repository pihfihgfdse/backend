package lab.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users") // 因为 user 在 SQL 中是保留字，所以表名改成 users
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true) // 账号必须唯一
    private String username;

    private String password; // 存加密后的乱码

    private String role; // 角色（admin/member）

    public User() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}