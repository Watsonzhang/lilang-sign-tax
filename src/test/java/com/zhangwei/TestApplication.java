package com.zhangwei;

import com.zhangwei.beans.Goods;
import com.zhangwei.beans.Stock;
import com.zhangwei.service.OkHttpService;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.crypto.IllegalBlockSizeException;
import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@SpringBootTest(classes = {CalculateApplication.class})
@RunWith(SpringRunner.class)
public class TestApplication {

    @Autowired
    OkHttpService okHttpService;

    @Test
    public void test(){
        System.out.println(1);
    }

    @Test
    public void test2(){
        String target="A";
        Goods a = Goods.builder().name("A").price(BigDecimal.ONE).number(BigDecimal.ONE).stable(false).build();
        Goods b = Goods.builder().name("B").price(BigDecimal.ONE).number(BigDecimal.ONE).stable(false).build();
        Goods s = Goods.builder().name("S").price(BigDecimal.TEN).number(BigDecimal.ONE).stable(false).build();
        List<Goods> goodsList = new ArrayList<Goods>();
        goodsList.add(a);
        goodsList.add(b);
        goodsList.add(s);
        Stock stock = Stock.builder().goodsList(goodsList).build();
        BigDecimal value = stock.calcValue();
        Goods investGoods = findTarget(target, goodsList);


    }

    private Goods findTarget(String target, List<Goods> goodsList) {
        Goods goods = null;
        for (int i = 0; i < goodsList.size(); i++) {
            Goods item = goodsList.get(i);
            if(item.getName().equals(target)){
                goods= item;
            }
        }
        return goods;
    }

    @Test
    public void test3(){
        try {
            Path workDir = Files.createTempDirectory("zip-tmp-");
            String fileName="wwww.zip";
            // 解压文档，到临时的工作目录
            String s= workDir.toAbsolutePath().toString() + File.separator+fileName;
            System.out.println(s);
            File file = new File(s);
            InputStream execute = okHttpService.execute();
            FileUtils.copyInputStreamToFile(execute, file);
        }catch (Exception e){

        }

    }

}

