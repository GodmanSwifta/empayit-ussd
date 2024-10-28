package com.swifta.ussd.entity.cache;

import com.swifta.ussd.model.MenuPageStore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@RedisHash("ussdSession")
@AllArgsConstructor
@NoArgsConstructor
public class UssdSession implements Serializable {

    @Id
    private String sessionId;
    @Indexed
    private String msisdn;
    private String currentStage;
    private String ussdInput;
    private boolean closed;
    private Map<String, String> dataStore = new HashMap<>();
    private MenuPageStore menuPageStore;
    private Date createdDate = new Date();
    private Date updatedDate;

    public void setData(String key, String value) {
        dataStore.put(key, value);
    }

    public String getData(String key) {
        return dataStore.get(key);
    }

    public String getData(String key, String defaultMessage) {
        return dataStore.getOrDefault(key, defaultMessage);
    }
}
