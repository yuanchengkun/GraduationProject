package com.edu.cuit.competition_management_system.json;

import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 * @author yuanck 2016051230
 * 满足layui表格框架的json格式
 */
public class Tablejson<T>{
    private  Integer code = 0;
    private  Integer count = 0;
    private  String msg = "";
    private StringBuffer data;   // 响应内容

    public void setData(List<T> list) {
        data = new StringBuffer("\"data\":[");
        for (T ml:list){

            String s = JSON.toJSONString(ml);
            data.append(s+",");
            count++;
        }
        if(count==0) {
            data.append("]");
        }
        data.setCharAt(data.length()-1,']');
        data.insert(0,"{\"code\":"+code+",\"msg\":\""+msg+"\",\"count\":"+count+",");
        data.append("}");
    }

    public String getDate(){

        return data.toString();
    }
}
