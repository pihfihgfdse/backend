package lab.backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "borrow_records")
public class BorrowRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 谁借的？（存用户ID）
    private Long userId;

    // 借了什么设备？（存设备ID）
    private Long deviceId;

    // 借出时间
    private LocalDateTime borrowTime;

    // 归还时间（如果还没还，就是 null）
    private LocalDateTime returnTime;

    // 状态：BORROWED（借出中）/ RETURNED（已归还）
    private String status;

    public BorrowRecord() {}

    // 省略 getter 和 setter（请在 IDEA 里按 Alt+Insert 自动生成，或者手敲）
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public Long getDeviceId() { return deviceId; }
    public void setDeviceId(Long deviceId) { this.deviceId = deviceId; }
    public LocalDateTime getBorrowTime() { return borrowTime; }
    public void setBorrowTime(LocalDateTime borrowTime) { this.borrowTime = borrowTime; }
    public LocalDateTime getReturnTime() { return returnTime; }
    public void setReturnTime(LocalDateTime returnTime) { this.returnTime = returnTime; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}