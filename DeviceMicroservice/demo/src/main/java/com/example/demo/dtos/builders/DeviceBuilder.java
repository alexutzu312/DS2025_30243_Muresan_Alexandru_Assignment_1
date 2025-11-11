package com.example.demo.dtos.builders;

import com.example.demo.dtos.DeviceDTO;
import com.example.demo.dtos.DeviceDetailsDTO;
import com.example.demo.entities.Device;

public class DeviceBuilder {

    private DeviceBuilder() {
    }

    public static DeviceDTO toDeviceDTO(Device device) {
<<<<<<< HEAD
        return new DeviceDTO(device.getId(), device.getName(), device.getMaxConsumption(),
                (device.getUserReference() != null ? device.getUserReference().getId() : null ));
=======
        return new DeviceDTO(device.getId(), device.getName(), device.getMaxConsumption());
>>>>>>> e17a6d4c3188685feadbbfdabd3ad1ad4ace9122
    }

    public static DeviceDetailsDTO toDeviceDetailsDTO(Device device) {
        return new DeviceDetailsDTO(device.getId(), device.getName(), device.getMaxConsumption());
    }

    public static Device toEntity(DeviceDetailsDTO deviceDetailsDTO) {
        return new Device(deviceDetailsDTO.getName(),
<<<<<<< HEAD
                deviceDetailsDTO.getMaxConsumption(),
                deviceDetailsDTO.getUserReference() );
=======
                deviceDetailsDTO.getMaxConsumption());
>>>>>>> e17a6d4c3188685feadbbfdabd3ad1ad4ace9122
    }
}
