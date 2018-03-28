package cn.yy.test;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @package: cn.yy.test
 * @Description:
 * @Date: Created in  2018-03-28 15:32
 * @Author: yy
 */
public class ExcellUtil {

    public static void writeXls() {//把读取的文件写入到excel文件中
        int i = 0;
        try {
            File file = new File("F:/pocheckaccount/PAYMENT-CHECK-FILE-2018-03-27.csv");
            if (file.isFile() && file.exists()) {
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file));
                BufferedReader bufferReader = new BufferedReader(read);
                String lineTxt = null;
                String[] arg = null;
                List<String[]> list = new ArrayList<String[]>() ;
                lineTxt = bufferReader.readLine();
                try {
                    String tempDate;
                    while (lineTxt != null && !lineTxt.trim().equals("")) {
                        i++;
                        arg = lineTxt.split(",");
                        list.add(arg) ;
                        lineTxt = bufferReader.readLine();
                    }
                    for(String[] args:list){
//                        System.out.println(args[0]+"   "+args[1]+"   "+args[2]+"   "+args[3]+"   "+args[4]+"   "+args[5]+"   "+args[6]+"   "+args[7]+"   "+args[8]);
                    }

                    operateExl(list) ;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("文件路径不正确!");
            }
        } catch (Exception e) {
            System.out.println("文件错误!");
        } finally {
            System.out.println(i);
        }
    }
    public static void operateExl(List<String[]> list) {
        try {
            WritableWorkbook wb = Workbook.createWorkbook(new File("F:/pocheckaccount/PAYMENT-CHECK-FILE-2018-03-27.xls")) ;
            WritableSheet sheet = wb.createSheet("第一页", 0) ;
            for(int i=0;i<list.size();i++){
                String arg[] = list.get(i) ;
                for(int j=0;j<arg.length;j++){
                    sheet.addCell(new Label(j,i,arg[j])) ;
                }
            }
            wb.write() ;
            wb.close() ;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace() ;
        }
    }

    public static void main(String[] args) {
        writeXls();
    }
}
