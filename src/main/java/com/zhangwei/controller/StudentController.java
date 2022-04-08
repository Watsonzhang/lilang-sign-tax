package com.zhangwei.controller;

import com.zhangwei.dto.SignBaseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.concurrent.ThreadPoolExecutor;

@RestController
@RequestMapping("/stu")
@Api(value = "StudentController", tags = {"学生控制器"})
public class StudentController {


   @Autowired
   @Qualifier("taxCollectSyncExecutor")
   ThreadPoolExecutor taskExecutor;

    @GetMapping
    @ApiOperation(value = "测试导出普通文件", notes = "测试导出普通文件")
    public String testStudent() throws InterruptedException {
        System.out.println(taskExecutor);
        taskExecutor.execute(()->{
           Thread thread = Thread.currentThread();
           System.out.println(thread.getName());
       });
        return "hello world";
    }
}
