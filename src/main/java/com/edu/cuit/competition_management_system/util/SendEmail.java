package com.edu.cuit.competition_management_system.util;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;

public class SendEmail {

    //163邮箱的SMTP配置信息
    /*
    public static final String HOST = "smtp.163.com"; //SMTP服务器地址
    public static final String PROTOCOL = "smtp";  //SMTP协议
    public static final int PORT = 465;//阿里云和腾讯等ECS服务器禁用25端口发邮件，必须使用465端口，并且使用SSL加密
    public static final String EMAIL_ACCOUNT = "niumu77@163.com";//发件人的email
    public static final String SMTP_ACCOUNT = "niumu77";  //发件人的邮箱账号
    public static final String SMTP_AUTH = "helloworld2019"; //163邮箱开启smpt功能后的第三方登录的授权码，不等同于163密码。
    */


    //QQ邮箱的SMTP配置信息

    public static final String HOST = "smtp.qq.com"; //SMTP服务器地址
    public static final String PROTOCOL = "smtp";  //SMTP协议
    public static final int PORT = 465;//阿里云和腾讯等ECS服务器禁用25端口发邮件，必须使用465端口，并且使用SSL加密
    public static final String EMAIL_ACCOUNT = "627261542@qq.com";//发件人的email
    public static final String SMTP_ACCOUNT = "627261542";  //发件人的邮箱账号
    public static final String SMTP_AUTH = "zlumjmqzwuthbfbb"; //QQ邮箱开启smpt功能后的第三方登录的授权码，不等同于QQ密码。

    public static final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";//SSL协议工厂
    /** 
     * 获取Session 
     * @return 
     */  
    private static Session getSession() {  

    	Properties props = new Properties();
        props.setProperty("mail.smtp.host", HOST);//设置服务器地址
        props.setProperty("mail.transport.protocol" , PROTOCOL);//设置协议
        props.setProperty("mail.smtp.socketFactory.class" , SSL_FACTORY);//设置协议
        props.setProperty("mail.smtp.socketFactory.port" , String.valueOf(PORT));
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", String.valueOf(PORT));//设置端口
        props.setProperty("mail.smtp.auth" , "true");
        props.setProperty("mail.smtp.ssl.enable", "true");

        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                // 密码验证
                return new PasswordAuthentication(SMTP_ACCOUNT, SMTP_AUTH);

            }
        };


        Session session = Session.getInstance(props,auth);
        return session;  
    }  
      
    public static void send(String toEmail , String content) {  
        Session session = getSession();  
        try {
            // 设置为debug模式, 可以查看详细的发送 log
            session.setDebug(true);

            // 3. 创建一封邮件
            MimeMessage message = createMimeMessage(session, EMAIL_ACCOUNT, toEmail,content);

            // 也可以保持到本地查看
            // message.writeTo(file_out_put_stream);

            // 4. 根据 Session 获取邮件传输对象
            Transport transport = session.getTransport();

            // 5. 使用 邮箱账号 和 密码 连接邮件服务器
            //    这里认证的邮箱必须与 message 中的发件人邮箱一致，否则报错
            transport.connect(EMAIL_ACCOUNT, SMTP_AUTH);

            // 6. 发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
            transport.sendMessage(message, message.getAllRecipients());

            // 7. 关闭连接
            transport.close();
        }  
        catch (Exception mex) {
            mex.printStackTrace();  
        }  
    }


    /*
    //以下示例如何发送文本+图片+附件的邮件内容。自行参考。
    //5. 创建图片“节点”
    MimeBodyPart image = new MimeBodyPart();
    DataHandler dh = new DataHandler(new FileDataSource("D:\\参考资料\\广告图片\\54af95cfN67aa0c97.jpg")); // 读取本地文件
    image.setDataHandler(dh);                   // 将图片数据添加到“节点”
    image.setContentID("image_fairy_tail");     // 为“节点”设置一个唯一编号（在文本“节点”将引用该ID）

    //6. 创建文本“节点”
    MimeBodyPart text = new MimeBodyPart();
       这里添加图片的方式是将整个图片包含到邮件内容中, 实际上也可以以 http 链接的形式添加网络图片
     text.setContent("这是一张图片<br/><img src='cid:image_fairy_tail'/>", "text/html;charset=UTF-8");

    //7. （文本+图片）设置 文本 和 图片 “节点”的关系（将 文本 和 图片 “节点”合成一个混合“节点”）
    MimeMultipart mm_text_image = new MimeMultipart();
        mm_text_image.addBodyPart(text);
        mm_text_image.addBodyPart(image);
        mm_text_image.setSubType("related");

     //8. 将 文本+图片 的混合“节点”封装成一个普通“节点”
        最终添加到邮件的 Content 是由多个 BodyPart 组成的 Multipart, 所以我们需要的是 BodyPart,
        上面的 mm_text_image 并非 BodyPart, 所有要把 mm_text_image 封装成一个 BodyPart
    MimeBodyPart text_image = new MimeBodyPart();
        text_image.setContent(mm_text_image);

     //9. 创建附件“节点”
        MimeBodyPart attachment = new MimeBodyPart();
        DataHandler dh2 = new DataHandler(new FileDataSource("C:\\Users\\ZHanG\\Desktop\\111.docx"));  // 读取本地文件
        attachment.setDataHandler(dh2);                                             // 将附件数据添加到“节点”
        attachment.setFileName(MimeUtility.encodeText(dh2.getName()));              // 设置附件的文件名（需要编码）

     //10. 设置（文本+图片）和 附件 的关系（合成一个大的混合“节点” / Multipart ）
    MimeMultipart mm = new MimeMultipart();
        mm.addBodyPart(text_image);
        mm.addBodyPart(attachment);     // 如果有多个附件，可以创建多个多次添加
        mm.setSubType("mixed");         // 混合关系

     11. 设置整个邮件的关系（将最终的混合“节点”作为邮件的内容添加到邮件对象）
        message.setContent(mm);

     12. 设置发件时间
        message.setSentDate(new Date());

     13. 保存上面的所有设置
        message.saveChanges();

    */

    public static MimeMessage createMimeMessage(Session session, String sendMail, String receiveMail,String content) throws Exception {
        // 1. 创建邮件对象
        MimeMessage message = new MimeMessage(session);

        // 2. From: 发件人
        message.setFrom(new InternetAddress(sendMail, "学科竞赛", "UTF-8"));

        // 3. To: 收件人（可以增加多个收件人、抄送、密送）
        message.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, "亲爱的注册用户", "UTF-8"));

        // 4. Subject: 邮件主题
        message.setSubject("注册验证码", "UTF-8");

        // 5.邮件内容
        message.setContent("您的验证码是："+content,"text/html;charset=utf-8");

        // 6. 设置发件时间
        message.setSentDate(new Date());

        // 7. 保存上面的所有设置
        message.saveChanges();

        return message;
    }


    public static String createRandomEmailActiveCode(){
        //根据你的邮箱地址，随机生成一个激活码。保证激活码一定是唯一的。怎么办？
        String code = UUID.randomUUID().toString();
        return code.replaceAll("-","");
    }


}
