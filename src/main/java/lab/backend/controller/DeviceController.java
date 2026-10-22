package lab.backend.controller;

import lab.backend.model.Device;
import lab.backend.repository.DeviceRepository;
import lab.backend.service.DeviceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/devices")
public class DeviceController {

    private final DeviceRepository deviceRepo;
    private final DeviceService deviceService;

    public DeviceController(DeviceRepository deviceRepo, DeviceService deviceService) {
        this.deviceRepo = deviceRepo;
        this.deviceService = deviceService;
    }

    // 查所有设备
    @GetMapping
    public List<Device> list() {
        return deviceRepo.findAll();
    }

    // 添加设备
    @PostMapping
    public Device add(@RequestBody Device device) {
        return deviceRepo.save(device);
    }

    // 借设备
    @PostMapping("/{deviceId}/borrow")
    public Map<String, String> borrow(@PathVariable Long deviceId, @RequestParam Long userId) {
        String result = deviceService.borrowDevice(userId, deviceId);
        return Map.of("message", result);
    }

    // 归还设备
    @PostMapping("/{deviceId}/return")
    public Map<String, String> returnDevice(@PathVariable Long deviceId, @RequestParam Long userId) {
        String result = deviceService.returnDevice(userId, deviceId);
        return Map.of("message", result);
    }
}