package com.wayxtech.xiaohongshu;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {
        FileInputStream in = null;
        String encoding = "UTF-8";
        String filePath = "/Users/cola/Downloads/xiaohongshu/在家健身/";
        PrintWriter pw = new PrintWriter(new FileWriter("/Users/cola/Downloads/xiaohongshu/zaijiajianshen.txt"));
        File file = new File(filePath);
        // get the folder list
        File[] array = file.listFiles();

        for (int i = 0; i < array.length; i++) {
            System.out.println(filePath + array[i].getName());
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

                if(data.get("items") == null) {
                    continue;
                }

                JSONArray jsonArray = JSONArray.parseArray(data.get("items").toString());
                for (int i1 = 0; i1 < jsonArray.size(); i1++) {
                    Object item = jsonArray.get(i1);
                    Object note = JSONObject.parseObject(item.toString()).get("note");
                    Object liked_count = JSONObject.parseObject(note.toString()).get("liked_count");
                    Object id = JSONObject.parseObject(note.toString()).get("id");
                    Object title = JSONObject.parseObject(note.toString()).get("title");
                    Object type = JSONObject.parseObject(note.toString()).get("type");

                    Object user = JSONObject.parseObject(note.toString()).get("user");
                    Object nickname = JSONObject.parseObject(user.toString()).get("nickname");
                    Object userid = JSONObject.parseObject(user.toString()).get("userid");
                    Object images = JSONObject.parseObject(user.toString()).get("images");

                    String line = "在家健身" + '\t' + id + "\t"  + title  + "\t"  + type + "\t" + liked_count + "\t" + userid + "\t" + nickname + "\t" + images ;


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
