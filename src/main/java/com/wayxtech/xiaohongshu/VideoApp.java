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
public class VideoApp
{
    public static void main( String[] args ) throws Exception
    {
        FileInputStream in = null;
        String encoding = "UTF-8";
        String filePath = "/Users/yushuai/IdeaProjects/xiaohongshu/note-运动健身/www.xiaohongshu.com/api/sns/v6/";
        PrintWriter pw = new PrintWriter(new FileWriter(filePath+"result.txt"));
        File file = new File(filePath);
        // get the folder list
        File[] array = file.listFiles();

        for (int i = 0; i < array.length; i++) {
            System.out.println(filePath + array[i].getName());
            if(array[i].getName().startsWith(".")|| array[i].getName().contains(".txt")){
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

                JSONArray jsonArray = JSONArray.parseArray(result.get("data").toString());
                for (int i1 = 0; i1 < jsonArray.size(); i1++) {
                    Object item = jsonArray.get(i1);
                    Object id = JSONObject.parseObject(item.toString()).get("id");
                    Object title = JSONObject.parseObject(item.toString()).get("title");
                    Object type = JSONObject.parseObject(item.toString()).get("type");
                    Object desc = JSONObject.parseObject(item.toString()).get("desc");
                    Object time = JSONObject.parseObject(item.toString()).get("time");
                    Object liked_count = JSONObject.parseObject(item.toString()).get("liked_count");
                    Object collected_count = JSONObject.parseObject(item.toString()).get("collected_count");
                    Object comments_count = JSONObject.parseObject(item.toString()).get("comments_count");
                    Object shared_count = JSONObject.parseObject(item.toString()).get("shared_count");


                    //发布用户
                    Object user = JSONObject.parseObject(item.toString()).get("user");
                    Object nickname = JSONObject.parseObject(user.toString()).get("nickname");
                    Object userid = JSONObject.parseObject(user.toString()).get("id");
                    Object images = JSONObject.parseObject(user.toString()).get("image");

                    String line = "在家健身" + '\t' + id + "\t"  + title  + "\t"  + type + "\t" +  time + "\t"+
                            liked_count + "\t"+ collected_count + "\t"+ comments_count + "\t" + shared_count + "\t"
                            + userid + "\t"+ userid + "\t" + nickname + "\t" + images ;


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
