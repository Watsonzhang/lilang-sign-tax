package com.zhangwei.dto;

import lombok.Data;

import java.util.List;

/**
 * @Author: zhangwei
 * @Description:
 * @Date:Create：2021/4/27 上午11:31
 */
@Data
public class FetchZipFileParamDTO {

    /**
     * zip文件名
     */
    private String zipName;


    private List<KeyItem> fileKeys;

    @Data
    public static  class  KeyItem{
        /**
         * 文件名
         */
        private String fileName;
        /**
         * 文件类型
         */
        private String fileType;
        /**
         * 文件key
         */
        private String key;
    }

}
