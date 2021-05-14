package com.zhangwei.beans;

import lombok.Builder;
import lombok.Data;

/**
 * @Author: zhangwei
 * @Description:
 * @Date:Create：2021/4/25 下午3:02
 */
@Data
@Builder
public class FileKey {
    private String fileName;
    private String fileType;
    private String key;
}
