package com.edu.cuit.competition_management_system.action;

import com.edu.cuit.competition_management_system.dao.userdao.FileDao;
import com.edu.cuit.competition_management_system.entity.FileUpload;
import com.edu.cuit.competition_management_system.entity.Users;
import com.edu.cuit.competition_management_system.json.LayuiTable;
import com.edu.cuit.competition_management_system.util.FileUploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

/**
 * @author yuanck 2016051230
 * 描述：和文档相关控制器
 */
@Controller
@RequestMapping("File")
public class FileAction {
    @Autowired
    FileDao fileDao;
    /**
     * 文件上传
     * @param request
     * @throws IllegalStateException
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping("/uploadFile")
    public LayuiTable saveFile(@RequestParam("file")MultipartFile file, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IllegalStateException, IOException{
        LayuiTable jsonData = new LayuiTable();
        Users users = (Users)session.getAttribute("loginUser");
        //String statueCode="0";
        //获取解析器
        CommonsMultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        //判断是否是文件
        if(resolver.isMultipart(request)){
            //进行转换
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)(request);
            //获取所有文件名称
            Iterator<String> it = multiRequest.getFileNames();
            while(it.hasNext()){
                //根据文件名称取文件
                file = multiRequest.getFile(it.next());
                String fileName = file.getOriginalFilename();  //文件名
                String uploadName = String.valueOf( System.currentTimeMillis());//时间戳字符串
                String fileType=fileName.substring(fileName.indexOf("."), fileName.length());
                uploadName=uploadName+fileType;
                String path =ResourceUtils.getURL("classpath:").getPath();
                //String path = request.getSession().getServletContext().getRealPath("/uploadFile");
                path=path+"/"+uploadName;
                File newFile = new File(path);
                //上传的文件写入到指定的文件中
                file.transferTo(newFile);
                //保存进数据库
                FileUpload fileUpload=new FileUpload();
                fileUpload.setUploaddate(new Date().toString());
                fileUpload.setSavename(uploadName);
                fileUpload.setFilename(fileName);
                fileUpload.setUserid(users.getId());
                System.out.println(fileUpload);
                fileDao.save(fileUpload);
               /* if(insertStatue>0){
                    //statueCode="1";
                }*/
            }
        }
        return jsonData;
        //return statueCode;
    }
    /**
     *删除文件
     * @param request
     * @return
     * @throws IllegalStateException
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping("/deleteFile")
    public String deleteFile(String fileId,HttpServletRequest request) throws IOException {
        String statueCode="0";
        //找到文件名  删除本地文件
        int fileID=Integer.parseInt(fileId);

        FileUpload file=fileDao.findById(fileID).get();
        if(file!=null){
            String fileName=file.getSavename();
            String path = ResourceUtils.getURL("classpath:").getPath();
            path+="/"+fileName;
            File deleteFile=new File(path);
            if(deleteFile.exists()){
                deleteFile.delete();
            }
            //删除数据库
            fileDao.deleteById(fileID);
            statueCode="1";
        }
        return statueCode;
    }
    /**
     * 文件下载
     * @return
     * @throws UnsupportedEncodingException
     */
    @ResponseBody
    @RequestMapping("/fileDownload")
    public  void fileDownload(int fileId,HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException,IOException {
        FileUpload fileupload=fileDao.findById(fileId).get();
        System.out.println(fileupload);
        String fileName=fileupload.getFilename();
        String saveName=fileupload.getSavename();
        System.out.println(fileName);
        String path = ResourceUtils.getURL("classpath:").getPath();
        path+="/"+saveName;
        File file = new File(path);
        String name= new String(fileName.getBytes("GBK"), "ISO-8859-1");
        if (file.exists()) {

            response.setContentType("application/force-download");// 设置强制下载不打开
            response.addHeader("Content-Disposition",
                    "attachment;fileName="+name);// 设置文件名
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream os = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    /**
     * 上传图片
     * @param file
     * @param classPic 图片分类文件夹名
     * @return
     * @throws IllegalStateException
     * @throws IOException
     */
    @RequestMapping("uploadPic")
    @ResponseBody
    public LayuiTable uploadCarsPicture(MultipartFile file,String classPic)throws IllegalStateException, IOException{
        LayuiTable layuiTable = new LayuiTable();
        String msg="";
        String path =ResourceUtils.getURL("classpath:").getPath();
        path=path+"/"+"static/"+classPic;

        try{
            String uploadSuccessFileName = FileUploadUtils.uploadFile(file,path);
            msg=uploadSuccessFileName;
            layuiTable.setCode(1);
            layuiTable.setMsg(msg);
            return layuiTable;
        }
        catch(Exception ex){
            layuiTable.setCode(0);
            ex.printStackTrace();
            msg="error";
            layuiTable.setMsg(msg);
            return layuiTable;
        }
    }

}
