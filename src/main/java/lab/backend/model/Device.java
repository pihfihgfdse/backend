package lab.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "devices")
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;      // 设备名称（如：示波器）
    private String model;     // 型号（如：DS1054Z）
    private Integer stock;    // 库存数量（借出一台，库存减一）
    private String status;    // 状态（NORMAL正常 / BROKEN损坏）

    // 省略 getter 和 setter（你可以用 Alt+Insert 自动生成，或者直接自己写上）
    public Device() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}