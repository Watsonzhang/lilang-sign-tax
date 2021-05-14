package com.zhangwei.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by yezhiqiang on 2021/4/27 11:22
 */
@Data
public class TaxMoneyDownloadReqDTO extends SignBaseDto {
    @ApiModelProperty("会计年度")
    @NotNull(message = "会计年度不能为空")
    private Integer kjnd;

    @ApiModelProperty("会计区间")
    @NotNull(message = "会计区间不能为空")
    private Integer kjqj;
}
