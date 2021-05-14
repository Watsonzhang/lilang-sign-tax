package com.zhangwei.dto;

import com.zhangwei.anon.SignIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 带签名、外部应用accessKey、时间戳的对象基础类
 */
@Data
@ApiModel(description = "带签名、外部应用accessKey、时间戳的对象基础类")
public class SignBaseDto implements Serializable {

    /**
     * 外部应用accessKey
     */
    @NotBlank
    @ApiModelProperty(required = true, notes = "外部应用accessKey")
    private String accessKey;

    /**
     * 签名
     */
    @NotBlank
    @SignIgnore
    @ApiModelProperty(required = true, notes = "签名")
    private String sign;

    /**
     * 时间戳
     */
    @NotNull
    @ApiModelProperty(required = true, notes = "时间戳")
    private Long timestamp;
}
