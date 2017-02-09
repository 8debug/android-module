package com.ybase.common;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by yhr on 2017/2/9.
 *
 */

public class YFile {

    private final static String TAG = "YFile";

    public static void copy(InputStream input, OutputStream out){
        try {
            byte[] buffer = new byte[1024];
            while ( input.read(buffer)!=-1 ){
                out.write(buffer);
                buffer = new byte[1024];
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                out.close();
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * copy文件
     * @param fileSrc
     * @param fileDes
     */
    public static void copy(File fileSrc, File fileDes){
        try {
            if( fileDes.getParentFile().isDirectory() && !fileDes.getParentFile().exists() ){
                fileDes.getParentFile().mkdirs();
            }
            copy(new FileInputStream(fileSrc), new FileOutputStream(fileDes));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 压缩多个文件
     * @param zipPath
     * @param arrayFilePath
     * @throws Exception
     */
    public static File getZipFiles(String zipPath, String... arrayFilePath){

        File zipFile = new File(zipPath);
        try {
            if( zipFile.exists() ){
                zipFile.delete();
            }
            zipFile.createNewFile();

            //创建Zip包
            java.util.zip.ZipOutputStream outZip = new java.util.zip.ZipOutputStream(new FileOutputStream(zipFile.getPath()));
            for (String filePath : arrayFilePath) {
                //打开要输出的文件
                File file = new File(filePath);
                //压缩
                zipFiles(file.getParent()+ File.separator, file.getName(), outZip);
            }
            //完成,关闭
            outZip.finish();
            outZip.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new File(zipPath);
    }

    /**
     * 压缩文件,文件夹
     * @param pathSrc	要压缩的文件/文件夹名字
     * @param pathDesZip	指定压缩的目的和名字
     * @throws Exception
     */
    public static void zipFolder(String pathSrc, String pathDesZip)throws Exception {

        //创建Zip包
        ZipOutputStream outZip = new ZipOutputStream(new FileOutputStream(pathDesZip));

        //打开要输出的文件
        File file = new File(pathSrc);
        file = file.isFile() ? file.getParentFile(): file;

        //压缩
        zipFiles( file.getParent(), file.getName(), outZip );

        //完成,关闭
        outZip.finish();
        outZip.close();

    }

    /**
     * 压缩文件
     * @param folderString
     * @param filename
     * @param zipOutputSteam
     * @throws Exception
     */
    private static void zipFiles(String folderString, String filename, ZipOutputStream zipOutputSteam)throws Exception{

        if(zipOutputSteam == null)
            return;

        File file = new File(folderString, filename);

        //判断是不是文件
        if (file.isFile()) {

            java.util.zip.ZipEntry zipEntry =  new java.util.zip.ZipEntry(filename);
            java.io.FileInputStream inputStream = new java.io.FileInputStream(file);
            zipOutputSteam.putNextEntry(zipEntry);

            int len;
            byte[] buffer = new byte[4096];

            while((len=inputStream.read(buffer)) != -1)
            {
                zipOutputSteam.write(buffer, 0, len);
            }

            zipOutputSteam.closeEntry();
        } else {

            //文件夹的方式,获取文件夹下的子文件
            String[] filenames = file.list();

            //如果没有子文件, 则添加进去即可
            if (filenames.length <= 0) {
                java.util.zip.ZipEntry zipEntry =  new java.util.zip.ZipEntry(filename+ File.separator);
                zipOutputSteam.putNextEntry(zipEntry);
                zipOutputSteam.closeEntry();
            }

            //如果有子文件, 遍历子文件
            for (int i = 0; i < filenames.length; i++) {
                zipFiles(folderString, filenames[i], zipOutputSteam);
            }//end of for

        }//end of if

    }//end of func

}
