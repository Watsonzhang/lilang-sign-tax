package com.zhangwei.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author: zhangwei
 * @Description: 报税状态
 * @Date:Create：2021/4/26 上午10:02
 */
@Data
@ApiModel(description = "报税状态请求参数")
public class TaxReportStatusReqDTO extends SignBaseDto {

    @ApiModelProperty("企业税号集合")
    private List<String> taxNoList;

    @NotNull(message = "会计年度必填")
    @Range(min = 2000, max = 2038)
    @ApiModelProperty("会计年度")
    private Integer kjnd;

    @NotNull(message = "会计区间必填")
    @Range(min = 1, max = 12)
    @ApiModelProperty("会计区间")
    private Integer kjqj;
}
