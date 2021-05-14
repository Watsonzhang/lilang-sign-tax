package com.zhangwei.beans;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: zhangwei
 * @Description:
 * @Date:Create：2021/4/22 下午3:37
 */
@Data
@Builder
public class Goods {
    private String name;
    private BigDecimal price;
    private BigDecimal number;
    private Boolean stable;
}
