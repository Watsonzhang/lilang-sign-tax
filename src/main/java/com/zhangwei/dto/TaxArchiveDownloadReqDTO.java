package com.zhangwei.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author: zhangwei
 * @Description:
 * @Date:Create：2021/4/26 上午10:02
 */
@Data
@ApiModel(description = "完税(凭证 报表)下载请求参数")
public class TaxArchiveDownloadReqDTO extends SignBaseDto {

    @ApiModelProperty("纳税人识别号/税号")
    @NotBlank(message = "税号必填")
    private String taxNo;

    @NotNull(message = "会计年度必填")
    @Range(min = 2000, max = 2038)
    @ApiModelProperty("会计年度")
    private Integer kjnd;

    @NotNull(message = "会计区间必填")
    @Range(min = 1, max = 12)
    @ApiModelProperty("会计区间")
    private Integer kjqj;
}
