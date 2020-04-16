package com.wayxtech.xiaohongshu;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.PrintWriter;

/**
 * Hello world!
 *
 */
public class UserApp
{
    public static void main( String[] args ) throws Exception
    {
        FileInputStream in = null;
        String encoding = "UTF-8";
        String filePath = "/Users/cola/Downloads/xiaohongshu/user-减肥/";
        PrintWriter pw = new PrintWriter(new FileWriter("/Users/cola/Downloads/xiaohongshu/user-减肥.txt"));
        File file = new File(filePath);
        // get the folder list
        File[] array = file.listFiles();

        for (int i = 0; i < array.length; i++) {
            System.out.println(filePath + array[i].getName());
            if(array[i].getName().startsWith(".")){
                continue;
            }
            File file1 = new File(filePath + array[i].getName());
            Long filelength = file1.length();
            byte[] filecontent = new byte[filelength.intValue()];
            try {
                in = new FileInputStream(file1);
                in.read(filecontent);
                String content = new String(filecontent, encoding);
                //System.out.println(content);

                JSONObject result = JSONObject.parseObject(content);


                JSONObject data = JSONObject.parseObject(result.get("data").toString());


                JSONArray jsonArray = JSONArray.parseArray(data.get("users").toString());
                for (int i1 = 0; i1 < jsonArray.size(); i1++) {
                    Object item = jsonArray.get(i1);
                    Object id = JSONObject.parseObject(item.toString()).get("id");
                    Object name = JSONObject.parseObject(item.toString()).get("name");
                    Object desc = JSONObject.parseObject(item.toString()).get("desc");
                    Object image = JSONObject.parseObject(item.toString()).get("image");

                    if(!desc.toString().contains("笔记")) {
                        continue;
                    }
                    if(!desc.toString().contains("粉丝")) {
                        continue;
                    }

                    String []tag = desc.toString().split(" | ");
                    String qiye = tag.length == 5 ? "企业":"个人";

                    String biji = tag.length == 5 ? tag[2]:tag[0];
                    biji = biji.split("笔记·")[1];

                    String fensi = tag.length == 5 ? tag[4]:tag[2];
                    fensi = fensi.split("粉丝·")[1];

                    if(fensi.contains("万")) {
                        fensi = String.valueOf(Double.parseDouble(fensi.split("万")[0]) * 10000);
                    }

                    String line = "健身" + "\t" + id + "\t"  + name  + "\t"  + desc + "\t" +  image + "\t"+
                            qiye + "\t"+ biji + "\t"+ fensi ;


                    System.out.println(line);
                    pw.write(line + "\n");


                }


            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                in.close();

            }
        }
        pw.close();

    }
}
