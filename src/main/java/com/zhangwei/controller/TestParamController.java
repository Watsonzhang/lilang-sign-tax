package com.zhangwei.controller;

import com.alibaba.fastjson.JSON;
import com.zhangwei.dto.TaxArchiveDownloadReqDTO;
import com.zhangwei.dto.TaxMoneyDownloadReqDTO;
import com.zhangwei.until.SignUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

/**
 * @Author: zhangwei
 * @Description:
 * @Date:Create：2021/5/6 下午2:21
 */
@RestController
@RequestMapping("/param")
@Api(value = "TestController", tags = {"开放api参数接口 临时"})
public class TestParamController {

    @GetMapping("/archive")
    @ApiOperation(value = "完税报表和完税证明参数", notes = "完税报表和完税证明参数")
    public String taxArchiveParam(
            @RequestParam(value = "accessKey", required = true) @NotBlank String accessKey,
            @RequestParam(value = "accessSecret", required = true) @NotBlank String accessSecret,
            @RequestParam(value = "taxNo", required = true) @NotBlank String taxNo,
            @RequestParam(value = "kjnd", required = true) @NotBlank Integer kjnd,
            @RequestParam(value = "kjqj", required = true) @NotBlank Integer kjqj) {
        TaxArchiveDownloadReqDTO reqDTO = new TaxArchiveDownloadReqDTO();
        reqDTO.setAccessKey(accessKey);
        reqDTO.setTaxNo(taxNo);
        reqDTO.setKjnd(kjnd);
        reqDTO.setKjqj(kjqj);
        reqDTO.setTimestamp(System.currentTimeMillis());
        reqDTO.setSign(SignUtil.getSignByObj(reqDTO, accessSecret));
        return JSON.toJSONString(reqDTO);
    }

    @GetMapping("/money")
    @ApiOperation(value = "税金明细参数", notes = "税金明细参数")
    public String taxMoneyParam(
            @RequestParam(value = "accessKey", required = true) @NotBlank String accessKey,
            @RequestParam(value = "accessSecret", required = true) @NotBlank String accessSecret,
            @RequestParam(value = "kjnd", required = true) @NotBlank Integer kjnd,
            @RequestParam(value = "kjqj", required = true) @NotBlank Integer kjqj) {
        TaxMoneyDownloadReqDTO reqDTO = new TaxMoneyDownloadReqDTO();
        reqDTO.setAccessKey(accessKey);
        reqDTO.setKjnd(kjnd);
        reqDTO.setKjqj(kjqj);
        reqDTO.setTimestamp(System.currentTimeMillis());
        reqDTO.setSign(SignUtil.getSignByObj(reqDTO, accessSecret));
        return JSON.toJSONString(reqDTO);
    }
}
