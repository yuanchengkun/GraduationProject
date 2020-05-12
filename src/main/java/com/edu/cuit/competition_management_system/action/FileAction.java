package com.edu.cuit.competition_management_system.action;

import com.edu.cuit.competition_management_system.dao.userdao.FileDao;
import com.edu.cuit.competition_management_system.dao.userdao.FindUser;
import com.edu.cuit.competition_management_system.entity.FileUpload;
import com.edu.cuit.competition_management_system.entity.Users;
import com.edu.cuit.competition_management_system.json.LayuiTable;
import com.edu.cuit.competition_management_system.util.FileUploadUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.time.LocalDate;
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
    @Autowired
    FindUser findUser;
    /**
     * 文件上传
     * @param request
     * @throws IllegalStateException
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping("/uploadFile")
    public LayuiTable saveFile(@RequestParam("file")MultipartFile file,String teamid, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IllegalStateException, IOException{
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
                fileUpload.setUploaddate(LocalDate.now().toString());
                fileUpload.setSavename(uploadName);
                fileUpload.setFilename(fileName);
                fileUpload.setUserid(users.getId());
                if(teamid==null||teamid=="")
                    fileUpload.setTeamid(users.getTeamid());
                else
                    fileUpload.setTeamid(Integer.parseInt(teamid));
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
        String fileName=fileupload.getFilename();
        String saveName=fileupload.getSavename();
        String path = ResourceUtils.getURL("classpath:").getPath();
        path+="/"+saveName;
        downloadFile(null,response,path,fileName);

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
        System.out.println(path);
        path=path+"/"+"static/"+classPic;
        System.out.println(path);
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

    /**
     * 个人简介文件上传
     * @param file
     * @param classPic
     * @param session
     * @return
     * @throws IllegalStateException
     * @throws IOException
     */
    @RequestMapping("uploadPersonFile")
    @ResponseBody
    public LayuiTable uploadPersonFile(MultipartFile file,String classPic,HttpSession session)throws IllegalStateException, IOException{
        LayuiTable layuiTable;
        Users users = (Users) session.getAttribute("loginUser");
        classPic = classPic+"/"+users.getUsername();
        //保存文件
        layuiTable = uploadCarsPicture(file,classPic);
        //保存到用户数据库
        if(!"error".equals(layuiTable.getMsg())){
            //删除之前的文件
            String fileName = users.getUserfile();
            if(fileName!=null){
                String path = ResourceUtils.getURL("classpath:").getPath();
                path+="/"+"static/"+classPic+"/"+fileName;
                File deleteFile=new File(path);
                if(deleteFile.exists()){
                    deleteFile.delete();
                }
            }
            //保存用户数据库
            users.setUserfile(layuiTable.getMsg());
            findUser.save(users);
        }
        return layuiTable;
    }
    @RequestMapping("PersonFileDownload")
    @ResponseBody
    public void PersonFileDownload(HttpServletResponse response,int userid,String openStyle)throws UnsupportedEncodingException,IOException{
        Users users = findUser.findById(userid).get();
        if(users.getUserfile()==null){
            response.setContentType("text/html;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.print("该成员没有上传个人简历");
            return;
        }
        String path = ResourceUtils.getURL("classpath:").getPath();
        path+="static/"+"personPic"+"/"+users.getUsername()+"/"+users.getUserfile();
        String fileName = users.getUserfile();
       /* if("".equals(openStyle)||openStyle==null)
            downloadFile(openStyle,response,path,users.getUserfile());
        else*/
            download(openStyle,path,response,fileName);
    }
    private void downloadFile(String openStyle,HttpServletResponse response,String path,String fileName)throws UnsupportedEncodingException,IOException{
        //获取打开方式
        openStyle = openStyle == null ? "attachment" : openStyle;
        File file = new File(path);
        String name= new String(fileName.getBytes("GBK"), "ISO-8859-1");
        if (file.exists()) {
            response.setContentType("application/force-download");// 设置强制下载不打开
            response.addHeader("Content-Disposition",
                    openStyle + ";fileName="+name);// 设置文件名
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
    private void download(String openStyle, String path, HttpServletResponse response,String fileName) throws IOException {
        //获取打开方式
        if("".equals(openStyle)||openStyle==null){
            openStyle = "attachment";
        }
        //openStyle = openStyle == null ? "attachment" : openStyle;
        //获取文件信息

        //根据文件信息中文件名字 和 文件存储路径获取文件输入流
        String realpath = path;
        //获取文件输入流
        FileInputStream is = new FileInputStream(new File(realpath));
        //附件下载
        response.setHeader("content-disposition", openStyle + ";fileName=" + fileName);
        //获取响应输出流
        ServletOutputStream os = response.getOutputStream();
        //文件拷贝
        IOUtils.copy(is, os);
        IOUtils.closeQuietly(is);
        IOUtils.closeQuietly(os);
    }
}
