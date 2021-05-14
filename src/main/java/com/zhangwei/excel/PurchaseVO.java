package com.zhangwei.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: zhangwei
 * @Description:
 * @Date:Create：2021/4/25 上午9:51
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
//内容高度
@ContentRowHeight(25)
//表头行高度
@HeadRowHeight(25)
//列的宽度
@ColumnWidth(25)
public class PurchaseVO {
    @ExcelProperty("商品名称")
    private String goodsName;

    @ExcelProperty("价格")
    private String price;

    @ExcelProperty("总量")
    private String total;
}
