package com.warmer.web;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class tryDJL {

    @Test
    public void test() throws IOException {
        try {
            StringBuilder result = new StringBuilder();
            String cmd = new String("python " +
                    "D://gaofenerqi//soft//HHGC//河湖水域典型地物自动识别模块//python//test.py -fileName=" + "uuid" + " -Image=" + " -usedModel=" + " -imgWidth=" + " -usedHeight=" + "imgheight");
            String cmd1 = new String("python " +
                    "F://知识图谱//预测模型//test2.py");
            System.out.println(cmd1);
            Process pr = Runtime.getRuntime().exec(cmd1);

            //获取子进程的输入流
            BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            String line = null;
            List<Float> list = new ArrayList<>();
            for (int j=0;j<20;j++){
                list.add((float) 5.12);
            }
            while ((line = in.readLine()) != null) {
                list.add(Float.parseFloat(line));
                System.out.println("line数据流");
                System.out.println(line);
            }
            System.out.println(list);
            int re = pr.waitFor();
            in.close();
            if (re == 1) {
                System.out.println("调用脚本失败");
            } else {
                System.out.println("调用脚本成功");
            }
        }catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
