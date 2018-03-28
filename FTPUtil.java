package cn.yy.test;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @package: cn.yy.test
 * @Description: ftp下载文件到本地
 * @Date: Created in  2018-03-28 11:59
 * @Author: yy
 */
public class FTPUtil{
    //ftp服务器地址
    public String hostname = "reportftp.yeepay.com";
    //ftp服务器端口号默认为21
    public Integer port = 21 ;
    //ftp登录账号
    public String username = "trx10017737464";
    //ftp登录密码
    public String password = "Xakd781e90c";

    public FTPClient ftpClient = null;

    /**
     * 初始化ftp服务器
     */
    public void initFtpClient() {
        ftpClient = new FTPClient();
        ftpClient.setControlEncoding("utf-8");
        try {
            System.out.println("connecting...ftp服务器:" + this.hostname + ":" + this.port);
            ftpClient.connect(hostname, port); //连接ftp服务器
            ftpClient.login(username, password); //登录ftp服务器
            int replyCode = ftpClient.getReplyCode(); //是否成功登录服务器
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                System.out.println("connect failed...ftp服务器:" + this.hostname + ":" + this.port);
            }
            System.out.println("connect successfu...ftp服务器:" + this.hostname + ":" + this.port);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** * 下载文件 *
     * @param pathname FTP服务器文件目录 *
     * @param filename 文件名称 *
     * @param localpath 下载后的文件路径 *
     * @return */
    public  boolean downloadFile(String pathname, String filename, String localpath){
        boolean flag = false;
        OutputStream os=null;
        try {
            initFtpClient();
            System.out.println("开始下载文件");
            //切换FTP目录
            ftpClient.changeWorkingDirectory(pathname);
            FTPFile[] ftpFiles = ftpClient.listFiles();
            for(FTPFile file : ftpFiles){
                if(filename.equalsIgnoreCase(file.getName())){
                    System.out.println(file.getName());
                    File localFile = new File(localpath + "/" + file.getName());
                    os = new FileOutputStream(localFile);
//                    ftpClient.setControlEncoding("GBK");
                    ftpClient.retrieveFile(file.getName(), os);
                    os.close();
                }
            }
            ftpClient.logout();
            flag = true;
            System.out.println("下载文件成功");
        } catch (Exception e) {
            System.out.println("下载文件失败");
            e.printStackTrace();
        } finally{
            if(ftpClient.isConnected()){
                try{
                    ftpClient.disconnect();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
            if(null != os){
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }

    /**
     *
     * @param pathname
     * @param localpath
     * @return
     */
    public  boolean showFile(String pathname, String localpath){
        boolean flag = false;
        OutputStream os=null;
        try {
            System.out.println("开始获取文件名称");
            initFtpClient();
            //切换FTP目录
            ftpClient.changeWorkingDirectory(pathname);
            FTPFile[] ftpFiles = ftpClient.listFiles();
            for(FTPFile file : ftpFiles){
                    System.out.println(file.getName());
            }
            ftpClient.logout();
            flag = true;
            System.out.println("获取文件名称成功");
        } catch (Exception e) {
            System.out.println("获取文件名称失败");
            e.printStackTrace();
        } finally{
            if(ftpClient.isConnected()){
                try{
                    ftpClient.disconnect();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
            if(null != os){
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }


    public static void main(String[] args) throws UnsupportedEncodingException {
        FTPUtil ftpUtil = new FTPUtil();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.DATE,calendar.get(Calendar.DATE)-1);
        String date = simpleDateFormat.format(calendar.getTime());
        System.out.println(date);
        ftpUtil.downloadFile("pocheckaccount/" + date ,"BINDCARD-CHECK-FILE-2018-03-27.csv","F:/pocheckaccount");
//        ftpUtil.downloadFile("201803" ,"2018032710017737464_finalStatus.csv","F:/20180327");
//        ftpUtil.showFile("201803","F:/");
    }
}
