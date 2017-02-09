package com.ybase.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by yhr on 2017/2/9.
 *
 */

public class YFile {

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

}
