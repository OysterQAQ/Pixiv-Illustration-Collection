package dev.cheerfun.pixivic.biz.web.collection.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import dev.cheerfun.pixivic.basic.sensitive.annotation.SensitiveCheck;
import dev.cheerfun.pixivic.common.po.Illustration;
import dev.cheerfun.pixivic.common.po.illust.ImageUrl;
import lombok.Data;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author OysterQAQ
 * @version 1.0
 * @date 2020/4/29 5:22 下午
 * @description Collection
 */
@Data
public class Collection {
    private Integer id;
    private Integer userId;
    private String username;
    private List<ImageUrl> cover;
    @SensitiveCheck
    @Size(min = 2, max = 40)
    private String title;
    @SensitiveCheck
    private String caption;
    private List<CollectionTag> tagList;
    private Integer illustCount;
    private List<Illustration> illustrationList;
    private Integer isPublic;
    private Integer useFlag;
    private Integer forbidComment;
    private Integer pornWarning;
    private Integer totalBookmarked;
    private Integer totalView;
    private Integer totalPeopleSeen;
    private Integer totalLiked;
    private Integer totalReward;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime createTime;

}
