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
public class SearchNoteApp
{
    public static void main( String[] args ) throws Exception
    {
        FileInputStream in = null;
        String encoding = "UTF-8";
        String filePath = "/Users/cola/Downloads/xiaohongshu/减肥食谱/www.xiaohongshu.com/api/sns/v10/search/";
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

                Object data = JSONObject.parseObject(content).get("data");

                Object items = JSONObject.parseObject(data.toString()).get("items");

                JSONArray jsonArray = JSONArray.parseArray(items.toString());
                for (int i1 = 0; i1 < jsonArray.size(); i1++) {
                    try {
                        Object item = jsonArray.get(i1);

                        Object note = JSONObject.parseObject(item.toString()).get("note");
                        Object id = JSONObject.parseObject(note.toString()).get("id");
                        Object title = JSONObject.parseObject(note.toString()).get("title");
                        Object type = JSONObject.parseObject(note.toString()).get("type");
                        Object liked_count = JSONObject.parseObject(note.toString()).get("liked_count");
                        Object time = JSONObject.parseObject(note.toString()).get("time");

                        //发布用户
                        Object user = JSONObject.parseObject(note.toString()).get("user");
                        Object nickname = JSONObject.parseObject(user.toString()).get("nickname");
                        Object userid = JSONObject.parseObject(user.toString()).get("userid");
                        Object images = JSONObject.parseObject(user.toString()).get("images");

                        String line = "减肥食谱" + '\t' + id + "\t"  + title  + "\t"  + type + "\t" +  time + "\t"+
                                liked_count + "\t"
                                + userid + "\t" + nickname + "\t" + images ;


                        System.out.println(line);
                        pw.write(line + "\n");
                    }catch (Exception e){
                        e.printStackTrace();
                        continue;
                    }
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
