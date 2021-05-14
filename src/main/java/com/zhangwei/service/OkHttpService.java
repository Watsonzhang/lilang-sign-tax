package com.zhangwei.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.zhangwei.beans.FileKey;
import com.zhangwei.dto.FetchZipFileParamDTO;
import okhttp3.*;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author: zhangwei
 * @Description:
 * @Date:Create：2021/4/25 下午2:49
 */
@Service
public class OkHttpService {
    public InputStream execute() throws IOException {
        JSONObject requestBody = new JSONObject();
        //String fileKeys="[{\"fileName\":\"上海仁善联人才服务有限公司_202103_税务报表_上海增值税一般纳税人申报表（电子税局）_月报_增值税纳税申报表附列资料（二）（本期进项税额明细）\",\"fileType\":\"pdf\",\"key\":\"233864a294564405817d1b6c702f5815\"},{\"fileName\":\"上海仁善联人才服务有限公司_202103_税务报表_上海增值税一般纳税人申报表（电子税局）_月报_增值税纳税申报表附列资料（三）（服务、不动产和无形资产扣除项目明细）\",\"fileType\":\"pdf\",\"key\":\"fca1d5e2bd5249efb6b1c8028fdd517c\"},{\"fileName\":\"上海仁善联人才服务有限公司_202103_税务报表_上海增值税一般纳税人申报表（电子税局）_月报_增值税纳税申报表附列资料（四）（税额抵减情况表）\",\"fileType\":\"pdf\",\"key\":\"a0e9d1476afb4f9b977d0f6aed879480\"},{\"fileName\":\"上海仁善联人才服务有限公司_202103_税务报表_上海增值税一般纳税人申报表（电子税局）_月报_增值税纳税申报表附列资料（一）（本期销售情况明细）\",\"fileType\":\"pdf\",\"key\":\"7eddd108a694413eb6f58dbff2bf4704\"},{\"fileName\":\"上海仁善联人才服务有限公司_202103_税务报表_上海增值税一般纳税人申报表（电子税局）_月报_附加税申报表\",\"fileType\":\"pdf\",\"key\":\"470c6eb7d6ce4175ae66afa2de29e937\"},{\"fileName\":\"上海仁善联人才服务有限公司_202103_税务报表_上海增值税一般纳税人申报表（电子税局）_月报_增值税一般纳税人主表\",\"fileType\":\"pdf\",\"key\":\"5ed4ede031cf4a949498b94fd5b39ea1\"}]";
        String httpUrl="https://fileserver.yunzhangfang.com/file/server/download/zip";
        String resString="{\"fileKeys\":[{\"fileName\":\"上海仁善联人才服务有限公司_202103_税务报表_上海增值税一般纳税人申报表（电子税局）_月报_增值税纳税申报表附列资料（二）（本期进项税额明细）\",\"fileType\":\"pdf\",\"key\":\"233864a294564405817d1b6c702f5815\"},{\"fileName\":\"上海仁善联人才服务有限公司_202103_税务报表_上海增值税一般纳税人申报表（电子税局）_月报_增值税纳税申报表附列资料（三）（服务、不动产和无形资产扣除项目明细）\",\"fileType\":\"pdf\",\"key\":\"fca1d5e2bd5249efb6b1c8028fdd517c\"},{\"fileName\":\"上海仁善联人才服务有限公司_202103_税务报表_上海增值税一般纳税人申报表（电子税局）_月报_增值税纳税申报表附列资料（四）（税额抵减情况表）\",\"fileType\":\"pdf\",\"key\":\"a0e9d1476afb4f9b977d0f6aed879480\"},{\"fileName\":\"上海仁善联人才服务有限公司_202103_税务报表_上海增值税一般纳税人申报表（电子税局）_月报_增值税纳税申报表附列资料（一）（本期销售情况明细）\",\"fileType\":\"pdf\",\"key\":\"7eddd108a694413eb6f58dbff2bf4704\"},{\"fileName\":\"上海仁善联人才服务有限公司_202103_税务报表_上海增值税一般纳税人申报表（电子税局）_月报_附加税申报表\",\"fileType\":\"pdf\",\"key\":\"470c6eb7d6ce4175ae66afa2de29e937\"},{\"fileName\":\"上海仁善联人才服务有限公司_202103_税务报表_上海增值税一般纳税人申报表（电子税局）_月报_增值税一般纳税人主表\",\"fileType\":\"pdf\",\"key\":\"5ed4ede031cf4a949498b94fd5b39ea1\"}],\"zipName\":\"2021年3期完税报表0425114950\"}";
        String replace = resString.replace("\\", "");
        System.out.println(replace);
        RequestBody body = null;
        body = RequestBody.create(MediaType.parse("application/json"),replace);
        Request request = new Request.Builder()
                .removeHeader("User-Agent")
                .addHeader("User-Agent","Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.108 Safari/537.36")
                .url(httpUrl).post(body).build();
        OkHttpClient client = new OkHttpClient().newBuilder().connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS).build();
        Response response = client.newCall(request).execute();
        return response.body().byteStream();
    }

    /**
     * 抓取zip文件转化成cos资源url
     *
     * @param paramDTO
     * @param tenantId
     * @return
     */
    public String fetchZip2COSFileURL(FetchZipFileParamDTO paramDTO, Long tenantId){
        String serverUrl="https://fileserver.yunzhangfang.com/file/server/download/zip";
        String reqString = JSON.toJSONString(paramDTO);
        System.out.println(reqString);
        RequestBody body;
        body = RequestBody.create(MediaType.parse("application/json"), reqString);
        Request request = new Request.Builder()
                .url(serverUrl).post(body).build();
        OkHttpClient client = new OkHttpClient().newBuilder().connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS).build();
        String downloadUrl = null;
        try {
            Response response = client.newCall(request).execute();
            Path workDir = Files.createTempDirectory("zip-tmp-");
            File file = new File(workDir.toAbsolutePath().toString() + File.separator + paramDTO.getZipName()+".zip");
            FileUtils.copyInputStreamToFile(response.body().byteStream(), file);
            System.out.println(file);

            /* Path workDir = Files.createTempDirectory("zip-tmp-");
            File file = new File(workDir.toAbsolutePath().toString() + File.separator + paramDTO.getZipName() + suffix);
            FileUtils.copyInputStreamToFile(response.body().byteStream(), file);*/
            /* ElectronicImageUploadParam param = new ElectronicImageUploadParam();
            param.setTenantId(tenantId);
            param.setQyId(tenantId);
            param.setUserId(tenantId);
            param.setImageName(UUID.randomUUID().toString());
            param.setImageType(ImageTypeEnum.BILL);
            param.setAppName("open-platform-bff");
            downloadUrl = ElectronicImageTemplate.uploadImageReturnUrl(file, param);*/
        } catch (Exception e) {
          e.printStackTrace();
        }
        return downloadUrl;
    }

    private static List<FileKey> generateFillKey() {
        ArrayList<FileKey> keys = Lists.newArrayList();
        keys.add(FileKey.builder().fileName("上海仁善联人才服务有限公司_202103_税务报表_上海增值税一般纳税人申报表（电子税局）_月报_增值税纳税申报表附列资料（二）（本期进项税额明细）").fileType("pdf").key("233864a294564405817d1b6c702f5815").build());
        keys.add(FileKey.builder().fileName("上海仁善联人才服务有限公司_202103_税务报表_上海增值税一般纳税人申报表（电子税局）_月报_增值税纳税申报表附列资料（三）（服务、不动产和无形资产扣除项目明细）").fileType("pdf").key("fca1d5e2bd5249efb6b1c8028fdd517c").build());
        keys.add(FileKey.builder().fileName("上海仁善联人才服务有限公司_202103_税务报表_上海增值税一般纳税人申报表（电子税局）_月报_增值税纳税申报表附列资料（四）（税额抵减情况表）").fileType("pdf").key("a0e9d1476afb4f9b977d0f6aed879480").build());
        keys.add(FileKey.builder().fileName("上海仁善联人才服务有限公司_202103_税务报表_上海增值税一般纳税人申报表（电子税局）_月报_增值税纳税申报表附列资料（一）（本期销售情况明细）").fileType("pdf").key("7eddd108a694413eb6f58dbff2bf4704").build());
        keys.add(FileKey.builder().fileName("上海仁善联人才服务有限公司_202103_税务报表_上海增值税一般纳税人申报表（电子税局）_月报_附加税申报表").fileType("pdf").key("470c6eb7d6ce4175ae66afa2de29e937").build());
        keys.add(FileKey.builder().fileName("上海仁善联人才服务有限公司_202103_税务报表_上海增值税一般纳税人申报表（电子税局）_月报_增值税一般纳税人主表").fileType("pdf").key("5ed4ede031cf4a949498b94fd5b39ea1").build());
        return keys;
    }

    public static void main(String[] args) {
        String s = JSONObject.toJSONString(generateFillKey());
        System.out.println(s);
    }
}
