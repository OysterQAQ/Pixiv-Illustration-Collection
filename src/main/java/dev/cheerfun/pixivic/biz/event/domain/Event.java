package dev.cheerfun.pixivic.biz.event.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author OysterQAQ
 * @version 1.0
 * @date 19-12-14 下午8:51
 * @description NotifyEvent
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Event implements Serializable {
    private String eventId;
    private Integer userId;
    private String userName;
    private String action;
    private String objectType;
    private Integer objectId;
    private Integer actionObjectId;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime createDate;
}
