package com.ybase.common;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipOutputStream;

/**
 * Created by yhr on 2017/2/9.
 *
 */

public class YFile {

    private final static String TAG = "YFile";

    private final static String MSG = "com.ybase.common.YFile";

    /**
     * 新建文件，若目录不存在则创建
     * @param pathFile
     * @return
     */
    public static File createNewFile( String pathFile ){
        File file = new File(pathFile);
        try {

            file.getParentFile().mkdirs();

            if( file.createNewFile() ){
                return file;
            }

        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, MSG, e);
        }
        return null;
    }

    public static void copy(InputStream input, OutputStream out){
        try {
            byte[] data = new byte[4096];
            int count;
            while ((count = input.read(data)) != -1) {
                out.write(data, 0, count);
            }
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, MSG, e);
        }finally {
            try {
                if( out!=null ){
                    out.close();
                }
                if( input!=null ){
                    input.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, MSG, e);
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
     * 压缩文件,文件夹
     * @param srcFileString	要压缩的文件/文件夹名字
     * @param zipFileString	指定压缩的目的和名字
     * @throws Exception
     */
    public static void zipFolder(String srcFileString, String zipFileString)throws Exception {

        //创建Zip包
        java.util.zip.ZipOutputStream outZip = new java.util.zip.ZipOutputStream(new java.io.FileOutputStream(zipFileString));

        //打开要输出的文件
        File file = new File(srcFileString);

        //压缩
        zipFiles(file.getParent()+ File.separator, file.getName(), outZip);

        //完成,关闭
        outZip.finish();
        outZip.close();

    }//end of func

    /**
     * 压缩多个文件
     * @param zipPath
     * @param arrayFilePath
     * @throws Exception
     */
    public static File getZipFiles(String zipPath, String... arrayFilePath){

        File zipFile = new File(zipPath);
        try {
            zipFile.getParentFile().mkdirs();
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
            Log.e(TAG, TAG, e);
        }
        return new File(zipPath);
    }

    public static File getZipFiles( String pathZip, File...files ){
        List<String> listPath = new ArrayList<>();
        for (File file : files) {
            listPath.add(file.getAbsolutePath());
        }
        return getZipFiles( pathZip, listPath.toArray(new String[]{}) );
    }

    /**
     * 压缩文件
     * @param folderString
     * @param fileString
     * @param zipOutputSteam
     * @throws Exception
     */
    private static void zipFiles(String folderString, String fileString, ZipOutputStream zipOutputSteam)throws Exception{

        if(zipOutputSteam == null)
            return;

        File file = new File(folderString+fileString);

        //判断是不是文件
        if (file.isFile()) {

            java.util.zip.ZipEntry zipEntry =  new java.util.zip.ZipEntry(fileString);
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
            String fileList[] = file.list();

            //如果没有子文件, 则添加进去即可
            if (fileList.length <= 0) {
                java.util.zip.ZipEntry zipEntry =  new java.util.zip.ZipEntry(fileString+ File.separator);
                zipOutputSteam.putNextEntry(zipEntry);
                zipOutputSteam.closeEntry();
            }

            //如果有子文件, 遍历子文件
            for (int i = 0; i < fileList.length; i++) {
                zipFiles(folderString, fileString+ File.separator+fileList[i], zipOutputSteam);
            }//end of for

        }//end of if

    }//end of func

}
