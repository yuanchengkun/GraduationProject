package com.edu.cuit.competition_management_system.action.admin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.edu.cuit.competition_management_system.dao.userdao.FileDao;
import com.edu.cuit.competition_management_system.entity.FileUpload;
import com.edu.cuit.competition_management_system.json.LayuiTable;
import com.edu.cuit.competition_management_system.util.UpdateTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("AdminFile")
public class AdminFileAction {
    @Autowired
    FileDao fileDao;

    @RequestMapping("fileList")
    public String fileList(){
        return "admin/file/file_list";
    }

    @RequestMapping("pageFile")
    @ResponseBody
    public LayuiTable pageFile(String page,String limit){
        LayuiTable layuiTable = new LayuiTable();
        Pageable pager = PageRequest.of(Integer.parseInt(page)-1,Integer.parseInt(limit));
        Page<FileUpload> pagerlist = fileDao.findAll(pager);
        List<FileUpload> usersList = pagerlist.getContent();
        layuiTable.setCode(0);
        layuiTable.setMsg("ok");
        layuiTable.setCount(pagerlist.getTotalElements());
        layuiTable.setData(usersList);
        return layuiTable;
    }
    @RequestMapping("updateFile")
    @ResponseBody
    public String updateFile(String param){
        String msg = "";
        FileUpload fileUpload = JSON.parseObject(param, new TypeReference<FileUpload>() {});
        try{
            if(fileUpload.getFileid()!=null){
                FileUpload source = fileDao.findById(fileUpload.getFileid()).get();
                UpdateTool.copyNullProperties(source,fileUpload);
            }
            fileDao.save(fileUpload);
            msg="ok";
        }catch (Exception e){
            msg="error";
            System.out.println(e.getMessage());
        }
        return msg;
    }
}
