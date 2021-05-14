package com.zhangwei.beans;

import lombok.Builder;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

/**
 * @Author: zhangwei
 * @Description:
 * @Date:Create：2021/4/22 下午3:40
 */
@Data
@Builder
public class Stock {
    private BigDecimal value;
    private List<Goods> goodsList;

    public BigDecimal calcValue(){
        if(CollectionUtils.isEmpty(this.getGoodsList())){
            return BigDecimal.ZERO;
        }
        BigDecimal sumValue = BigDecimal.ZERO;
        for (int i = 0; i < this.getGoodsList().size(); i++) {
            Goods item = this.getGoodsList().get(i);
            sumValue=sumValue.add(item.getPrice().multiply(item.getNumber()));
        }
        return sumValue;
    }
}
