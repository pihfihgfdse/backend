
package lab.backend.service;

import lab.backend.model.BorrowRecord;
import lab.backend.model.Device;
import lab.backend.repository.BorrowRecordRepository;
import lab.backend.repository.DeviceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DeviceService {

    private final DeviceRepository deviceRepo;
    private final BorrowRecordRepository recordRepo;

    public DeviceService(DeviceRepository deviceRepo, BorrowRecordRepository recordRepo) {
        this.deviceRepo = deviceRepo;
        this.recordRepo = recordRepo;
    }

    // 借设备
    public String borrowDevice(Long userId, Long deviceId) {
        Device device = deviceRepo.findById(deviceId)
                .orElseThrow(() -> new RuntimeException("设备不存在"));

        if (device.getStock() <= 0) {
            throw new RuntimeException("库存不足，借不了啦！");
        }

        device.setStock(device.getStock() - 1);
        deviceRepo.save(device);

        BorrowRecord record = new BorrowRecord();
        record.setUserId(userId);
        record.setDeviceId(deviceId);
        record.setBorrowTime(LocalDateTime.now());
        record.setStatus("BORROWED");
        recordRepo.save(record);

        return "借出成功！";
    }

    // 归还设备
    public String returnDevice(Long userId, Long deviceId) {
        BorrowRecord record = recordRepo.findByUserIdAndDeviceIdAndStatus(userId, deviceId, "BORROWED")
                .orElseThrow(() -> new RuntimeException("找不到借出记录，可能已经还了哦！"));

        record.setStatus("RETURNED");
        record.setReturnTime(LocalDateTime.now());
        recordRepo.save(record);

        Device device = deviceRepo.findById(deviceId)
                .orElseThrow(() -> new RuntimeException("设备不存在"));
        device.setStock(device.getStock() + 1);
        deviceRepo.save(device);

        return "归还成功！";
    }
}