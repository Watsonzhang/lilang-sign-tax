package com.zhangwei.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by yezhiqiang on 2021/5/20 13:52
 */
@Data
@ApiModel("税金明细入参")
public class TaxMoneyReqDTO extends SignBaseDto {
    @ApiModelProperty("企业税号集合")
    private List<String> taxNoList;

    @ApiModelProperty("会计年度")
    @NotNull
    private Integer kjnd;

    @ApiModelProperty("会计区间")
    @NotNull
    private Integer kjqj;
}
