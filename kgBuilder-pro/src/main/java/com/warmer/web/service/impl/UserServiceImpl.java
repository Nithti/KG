package com.warmer.web.service.impl;

import com.warmer.web.dao.impl.KGGraphRepository;
import com.warmer.web.entity.Waterlevel;
import com.warmer.web.service.UserService;
//import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.warmer.web.dao.locationDao;
import com.warmer.web.entity.location;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private locationDao locationDao;
    @Autowired
    private KGGraphRepository kgGraphRepository;

    @Override
    public List<Long> getYjSize(){
        return kgGraphRepository.getYJSize();
    }

    @Override
    public List<List<String>> getLocation(String id) {

        List<List<String>> list = new ArrayList<List<String>>();
        List<String> city = new ArrayList<>();
//        List<List<String>> list = new ArrayList<List<String>>();
        List<String> town = new ArrayList<>();
        List<String> count = new ArrayList<>();
        List<String> number = new ArrayList<>();
        int[] n = {12,10,8,6,5,11,12,11,6,4,4,0,12,11,8,7,9};
//        n = [12,10,8,6,5,11,12,11,6,4,4,12,11,8,7,9];
        //System.out.println(n[0]);

        if (id==null)
            return null;
        if (id.equals("370000"))
        {

            String p = new String();
            for (int i=1;i<=17;i++)
            {
                if (i<10)
                {
                    p = "0" + i;
                }
                else
                {
                    p = Integer.toString(i);
                }

                List<location> locations = locationDao.selectById("37" + p);
//                System.out.println("location为"+locations);
                if (locations.size()!=0)
                {
                    number.add(String.valueOf(n[i-1]));
                    //返回第一个值为city
                    String c = locations.get(0).getAd_nm();
                    city.add(c);
//                    list.add(city);
                    for (int j=1;j<locations.size();j++)
                    {
                        String t = locations.get(j).getAd_nm();
                        town.add(t);
                        count.add(Long.toString(kgGraphRepository.getDomainSize(c+t)));
                    }
//                    list.add(town);
                }
            }
            list.add(city);
//            System.out.println("list为"+list);
            list.add(town);
            System.out.println("city为"+city);
            System.out.println("town为"+town);
//            System.out.println("number为"+number);

        }
        else if (id.substring(4, 6).equals("00"))
        {
            List<location> locations = locationDao.selectById(id.substring(0,4));
            city.add(locations.get(0).getAd_nm());
            list.add(city);
            String numid = id.substring(2,4);
            int nid = Integer.parseInt(numid);
            number.add(String.valueOf(n[nid-1]));
            for (int i=1;i<locations.size();i++)
            {
                town.add(locations.get(i).getAd_nm());

            }
            list.add(town);
            for (String t: town) {
                String c = city.get(0);
                count.add(Long.toString(kgGraphRepository.getDomainSize(c+t)));
            }
            System.out.println(list);
        }
        else
        {
            List<location> l = locationDao.selectById(id);


            city.add(l.get(0).getAd_pre_nm());
            list.add(city);

            town.add(l.get(0).getAd_nm());
            list.add(town);
            for (String t: town) {
                String c = city.get(0);

                count.add(Long.toString(kgGraphRepository.getDomainSize(c+t)));

            }
            System.out.println(list);
        }
        list.add(count);
//        System.out.println(list)                                                                                                                                                                                                   ;
        System.out.println("count为"+count);
        list.add(number);
        System.out.println("number为"+number);
//        System.out.println("number为"+number);
        System.out.println("list为"+list);
        return list;
    }

    @Test
    @Override
    public List<Float> getWaterLevel(String rescd){
        try {
            String st_cd = locationDao.selectStcd(rescd);
            System.out.println("st_cd"+st_cd);
            List<Waterlevel> l1 = locationDao.selectLevel(st_cd);
            System.out.println("l1.size:"+l1.size());
            List<Waterlevel> l2 = new ArrayList<>();
            if(l1.size()<30){
                l2.addAll(l1);
                l2.addAll(l1);
                System.out.println("l2.size:"+l2.size());
            }
            else {
                l2.addAll(l1);
            }
            if(l2.size()>30){
                try {
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(new FileOutputStream("/home/software/water_level_data2.csv"), StandardCharsets.UTF_8));
                CSVFormat csvFormat = CSVFormat.EXCEL.withHeader("st_cd","rz","tm");
                CSVPrinter printer = csvFormat.print(writer);
                for (int i = 0; i < l2.size(); i++) {
                    Waterlevel user1 = l2.get(i);
                    printer.printRecord(user1.getSt_cd(),user1.getRz(),user1.getTm());
                }
                printer.flush();
                printer.close();
                //本地
//                    String cmd = new String("python " +
//                            "F://知识图谱//预测模型//test2.py --data_path=F://知识图谱//预测模型//water_level_data2.csv");
              //远程
                  String cmd = new String("python " +
                    "/home/software/test2.py --data_path=/home/software/water_level_data2.csv");
                    System.out.println(cmd);
                    Process pr = Runtime.getRuntime().exec(cmd);
                    //获取子进程的输入流
                    BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
//            String rescd= "37060250009-A5";
                    String line = null;
                    List<Float> list = new ArrayList<>();
                    for (int j=0;j<20;j++){
                        list.add(l2.get(j).getRz());
                    }
                    while ((line = in.readLine()) != null) {
                        list.add(Float.parseFloat(line));
//                        System.out.println("line数据流");
//                        System.out.println(line);
                    }
                    System.out.println(list);
                    int re = pr.waitFor();
                    in.close();
                    if (re == 1) {
                        System.out.println("调用脚本1失败");
                    } else {
                        System.out.println("调用脚本1成功");
                    }
                    return list;
            } catch (Exception e) {
                e.printStackTrace();
            }
            }
            else{
//                String cmd = new String("python " +
//                        "F://知识图谱//预测模型//test2.py");
            String cmd = new String("python " +
                    "/home/software/test2.py");
                System.out.println(cmd);
                Process pr = Runtime.getRuntime().exec(cmd);
                //获取子进程的输入流
                BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
//            String rescd= "37060250009-A5";
                String line = null;
                List<Float> list = new ArrayList<>();
                for (int j=0;j<4;j++){
                    list.add((float) 58.3);
                    list.add((float) 58.4);
                    list.add((float) 58.7);
                    list.add((float) 58.1);
                    list.add((float) 58.0);
                }
                while ((line = in.readLine()) != null) {
                    list.add(Float.parseFloat(line));
//                    System.out.println("line数据流");
//                    System.out.println(line);
                }
                System.out.println(list);
                int re = pr.waitFor();
                in.close();
                if (re == 1) {
                    System.out.println("调用脚本2失败");
                } else {
                    System.out.println("调用脚本2成功");
                }
                return list;
            }

        }catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;

    }
}
