package org.nickas21.smart.tuya.tuyaEntity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(value = {"valuePrefer", "name"}, ignoreUnknown = true)
public class DeviceStatus implements Serializable {
    private Object value;
    private Object valueOld;
    private Long eventTime;
    private String name;
}
