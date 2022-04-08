package com.zhangwei.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import java.util.concurrent.Executor;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: zhangwei
 * @Description:
 * @Date:Create：2021/4/22 下午3:31
 */
@Configuration
public class ApplicationConfig {

    @Bean("taxCollectSyncExecutor")
    public ThreadPoolExecutor taxCollectSyncExecutor() {
        return new ThreadPoolExecutor(8, 20, 0L, TimeUnit.MILLISECONDS,
                new SynchronousQueue<>(), new CustomizableThreadFactory("TaxCollectSyncThread-pool-"), new ThreadPoolExecutor.CallerRunsPolicy());
    }




}
