package com.zhangwei.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.google.common.collect.Lists;
import com.zhangwei.dto.FetchZipFileParamDTO;
import com.zhangwei.excel.PurchaseDetailVO;
import com.zhangwei.excel.PurchaseVO;
import com.zhangwei.excel.UserBo;
import com.zhangwei.service.OkHttpService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: zhangwei
 * @Description:
 * @Date:Create：2021/4/23 下午2:42
 */
@RestController
@RequestMapping("/test")
@Api(value = "TestController", tags = {"测试控制器"})
public class TestController {

    @Autowired
    OkHttpService okHttpService;

    @GetMapping
    @ApiOperation(value = "测试导出普通文件", notes = "测试导出普通文件")
    public void testDownLoad() throws IOException {
        HttpServletResponse res = ((ServletRequestAttributes) (RequestContextHolder
                .currentRequestAttributes())).getResponse();
        String fileName = "/home/yzf/下载/all.xlsx";
        res.setHeader("content-type", "application/octet-stream");
        res.setContentType("application/octet-stream");
        res.setHeader("Content-Disposition", "attachment; filename=" + "new.xlsx");
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            os = res.getOutputStream();
            bis = new BufferedInputStream(new FileInputStream(
                    new File(fileName)));
            int i = bis.read(buff);

            while (i != -1) {
                os.write(buff, 0, buff.length);
                os.flush();
                i = bis.read(buff);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @GetMapping("/export")
    @ApiOperation(value = "测试导出excel", notes = "测试导出excel")
    public void testExport() {
        try {
            HttpServletResponse response = ((ServletRequestAttributes) (RequestContextHolder
                    .currentRequestAttributes())).getResponse();
            //  设置内容格式 以及 编码方式
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            //   调用service去获取数据
            //  使用java8新特性的stream流去处理数据，把空的数据过滤掉
            List<UserBo> resultBo = selectAll();
            //  设置文件名称
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("测试导出.xlsx", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName);
            HorizontalCellStyleStrategy horizontalCellStyleStrategy = getHorizontalCellStyleStrategy();
            //  sheet名称
            EasyExcel.write(response.getOutputStream(), UserBo.class).sheet("测试导出").registerWriteHandler(horizontalCellStyleStrategy).doWrite(resultBo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private HorizontalCellStyleStrategy getHorizontalCellStyleStrategy() {
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        //设置表头居中对齐
        headWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.RIGHT);
        //内容样式
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        contentWriteCellStyle.setDataFormat((short) 49);
        //设置内容靠左对齐
        contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.LEFT);
        return new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
    }

    @GetMapping("/url")
    @ApiOperation(value = "url下载导出", notes = "url下载导出")
    public void testExportUrl() throws UnsupportedEncodingException {
        HttpServletResponse res = ((ServletRequestAttributes) (RequestContextHolder
                .currentRequestAttributes())).getResponse();
        res.setHeader("content-type", "application/octet-stream");
        res.setContentType("application/octet-stream");
        String fileName = URLEncoder.encode("我的.jpg", "UTF-8");
        res.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            os = res.getOutputStream();
            bis = new BufferedInputStream(new FileInputStream(
                    getNewFile()));
            int i = bis.read(buff);

            while (i != -1) {
                os.write(buff, 0, buff.length);
                os.flush();
                i = bis.read(buff);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @GetMapping("/file")
    @ApiOperation(value = "file下载导出", notes = "file下载导出")
    public void testExportFile() throws UnsupportedEncodingException {
        HttpServletResponse res = ((ServletRequestAttributes) (RequestContextHolder
                .currentRequestAttributes())).getResponse();
        res.setHeader("content-type", "application/octet-stream");
        res.setContentType("application/octet-stream");
        String fileName = URLEncoder.encode("hello.zip", "UTF-8");
        res.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            os = res.getOutputStream();
            bis = new BufferedInputStream(okHttpService.execute());
            int i = bis.read(buff);

            while (i != -1) {
                os.write(buff, 0, buff.length);
                os.flush();
                i = bis.read(buff);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("export file finish");

    }

    @GetMapping("/more")
    @ApiOperation(value = "测试导出多个sheet", notes = "测试导出多个sheet")
    public void testExportMore() {
        HttpServletResponse response = ((ServletRequestAttributes) (RequestContextHolder
                .currentRequestAttributes())).getResponse();
        // 这里URLEncoder.encode可以防止中文乱码
        try {
            String fileName = URLEncoder.encode("template", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            //新建ExcelWriter
            ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream()).build();
            //获取sheet0对象
            WriteSheet mainSheet = EasyExcel.writerSheet(0, "采购单").head(PurchaseVO.class).build();
            //向sheet0写入数据 传入空list这样只导出表头
            excelWriter.write(getMainList(), mainSheet);
            //获取sheet1对象
            WriteSheet detailSheet = EasyExcel.writerSheet(1, "采购单明细").head(PurchaseDetailVO.class).build();
            //向sheet1写入数据 传入空list这样只导出表头
            excelWriter.write(getDetailList(), detailSheet);
            //关闭流
            excelWriter.finish();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<PurchaseDetailVO> getDetailList() {
        List<PurchaseDetailVO> detailVOS = Lists.newArrayList();
        PurchaseDetailVO vo = PurchaseDetailVO.builder().goodsId("987").number("10").spec("红富士").build();
        detailVOS.add(vo);
        return detailVOS;
    }

    private List<PurchaseVO> getMainList() {
        List<PurchaseVO> main = Lists.newArrayList();
        PurchaseVO purchaseVO = new PurchaseVO();
        purchaseVO.setGoodsName("苹果");
        purchaseVO.setPrice("100.00");
        purchaseVO.setTotal("1000");
        main.add(purchaseVO);
        return main;
    }

    private List<UserBo> selectAll() {
        ArrayList<UserBo> userBos = new ArrayList<>();
        UserBo userBo = new UserBo();
        userBo.setAge("12");
        userBo.setName("hello");
        userBo.setSex("男");
        userBos.add(userBo);
        return userBos;

    }


    public static File getNewFile(){
        URI u = URI.create("https://static.dingtalk.com/media/lADPD3W5OJo0SC7NC7jND6A_4000_3000.jpg?auth_bizType=IM&auth_bizEntity=%7B%22cid%22%3A%2239313512692%22%2C%22msgId%22%3A%226865552244970%22%7D&bizType=im&open_id=206645299");
        final String FILE_TO = "/home/yzf/下载/hello.jpg";
        File file = null;
        InputStream inputStream = null;
        try {
             inputStream = u.toURL().openStream();
            file = new File(FILE_TO);
            // commons-io
            FileUtils.copyInputStreamToFile(inputStream, file);
            return file;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;

    }

    public static File downLoadFromUrl(String urlStr, String fileName, String savePath) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        //设置超时间为3秒
        conn.setConnectTimeout(3 * 1000);
        //防止屏蔽程序抓取而返回403错误
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

        //得到输入流
        InputStream inputStream = conn.getInputStream();
        //获取自己数组
        byte[] getData = readInputStream(inputStream);

        //文件保存位置
        File saveDir = new File(savePath);
        if (!saveDir.exists()) {
            saveDir.mkdir();
        }
        File file = new File(saveDir + File.separator + fileName);
        return file;
        /* FileOutputStream fos = new FileOutputStream(file);
        fos.write(getData);
        if(fos!=null){
            fos.close();
        }
        if(inputStream!=null){
            inputStream.close();
        }

        System.out.println("info:"+url+" download success");
*/
    }

    /**
     * 从输入流中获取字节数组
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }

    /**
     * 将数据写入文本文件
     * @param list
     * @param path
     */
    private void writeToTxt(String text,String path) {
        String dir = path.substring(0,path.lastIndexOf("/"));
        File parent = new File(dir);
        if (!parent.exists()) {
            boolean mkdirs = parent.mkdirs();
        }
        FileOutputStream outSTr = null;
        BufferedOutputStream Buff = null;
        try {
            outSTr = new FileOutputStream(new File(path));
            Buff = new BufferedOutputStream(outSTr);
                Buff.write(text.getBytes("UTF-8"));
            Buff.flush();
            Buff.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/generate")
    @ApiOperation(value = "测试生成文件", notes = "测试生成文件")
    public void testGenerateFile() throws IOException {
        String fileName = "/home/yzf/下载/world.txt";
        String content ="hellworld";
       // writeToTxt(content,fileName);
        FileUtils.copyInputStreamToFile(new ByteArrayInputStream(content.getBytes()),new File(fileName) );
    }

    @GetMapping("/unzip")
    @ApiOperation(value = "测试解压zip文件", notes = "测试解压zip文件")
    public void testUnZipFile() throws IOException, ZipException {
        String exZipFile = "/home/yzf/下载/2021年3期完税报表0425115050.zip";
        ZipFile zipFile = new ZipFile(exZipFile);
        zipFile.setFileNameCharset("UTF-8");
        String extractDir="/home/yzf/下载/zip";
        if (!zipFile.isValidZipFile()) {   // 验证.zip文件是否合法，包括文件是否存在、是否为zip文件、是否被损坏等
            throw new ZipException("压缩文件不合法,可能被损坏.");
        }
        File destDir = new File(extractDir);     // 解压目录
        if (destDir.isDirectory() && !destDir.exists()) {
            destDir.mkdir();
        }
        zipFile.extractAll(extractDir);

    }

    @GetMapping("/zip")
    @ApiOperation(value = "测试压缩zip文件", notes = "测试压缩zip文件")
    public void testZipFile() throws IOException, ZipException {
        // 生成的压缩文件
        ZipFile zipFile = new ZipFile("/home/yzf/下载/zip/target.zip");
        zipFile.setFileNameCharset("GBK");
        ZipParameters parameters = new ZipParameters();
        // 压缩方式
        parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
        // 压缩级别
        parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
        // 要打包的文件夹
        File currentFile = new File("/home/yzf/下载/zip");
        File[] list = currentFile.listFiles();
        // 遍历test文件夹下所有的文件、文件夹
        for (File f : list) {
            if (f.isDirectory()) {
                zipFile.addFolder(f.getPath(), parameters);
            } else {
                zipFile.addFile(f, parameters);
            }
        }

    }

    @GetMapping("/test")
    public void taxArchiveTest() {
        FetchZipFileParamDTO param = new FetchZipFileParamDTO();
        FetchZipFileParamDTO.KeyItem keyItem1 = new FetchZipFileParamDTO.KeyItem();
        keyItem1.setFileName("上海仁善联人才服务有限公司_202103_税务报表_上海增值税一般纳税人申报表（电子税局）_月报_增值税纳税申报表附列资料（二）（本期进项税额明细）");
        keyItem1.setFileType("pdf");
        keyItem1.setKey("233864a294564405817d1b6c702f5815");
        param.setFileKeys(Lists.newArrayList(keyItem1));
        param.setZipName("2021年3期完税报表0425114950");
        okHttpService.fetchZip2COSFileURL(param,12L);
        System.out.println(111);
    }

}
