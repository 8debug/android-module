package com.lling.photopicker.utils;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.lling.photopicker.beans.Photo;
import com.lling.photopicker.beans.PhotoFloder;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Class: PhotoUtils
 * @Description:
 * @author: lling(www.liuling123.com)
 * @Date: 2015/11/4
 */
public class PhotoUtils {

    public static String addPhoto( Context context, File file ){

        String path = file.getAbsolutePath();
        String strResult;
        ContentValues values = new ContentValues();
        values.put( MediaStore.Images.Media.DATA, path);
        //values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        //values.put(MediaStore.Images.Media.DESCRIPTION, file.getName());
        //values.put( MediaStore.Images.ImageColumns.DATA, path );
        //保存在SD卡中
        Uri uri = MediaStore.Audio.Media.getContentUriForPath( path );
        context.getContentResolver().delete( uri, MediaStore.MediaColumns.DATA + "=\"" + path + "\"", null);
        //context.getContentResolver().delete( uri, null, null);
        uri = context.getContentResolver().insert( MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values );
        strResult = uri!=null?uri.toString():null;

        //保存图片到系统媒体库
        //strResult = MediaStore.Images.Media.insertImage(context.getContentResolver(), path, file.getName(), file.getName());

        return strResult;

    }


    public static Map<String, PhotoFloder> getPhotos(Context context) {
        Map<String, PhotoFloder> floderMap = new HashMap<String, PhotoFloder>();

        String allPhotosKey = "所有图片";
        PhotoFloder allFloder = new PhotoFloder();
        allFloder.setName(allPhotosKey);
        allFloder.setDirPath(allPhotosKey);
        allFloder.setPhotoList(new ArrayList<Photo>());
        floderMap.put(allPhotosKey, allFloder);

        Uri imageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        ContentResolver mContentResolver = context.getContentResolver();

        // 只查询jpeg和png的图片
        Cursor mCursor = mContentResolver.query(imageUri,
                new String[]{MediaStore.Images.Media.DATA},
                MediaStore.Images.Media.MIME_TYPE + " in(?, ?)",
                new String[] { "image/jpeg", "image/png" },
                MediaStore.Images.Media.DATE_MODIFIED + " desc");

        if( mCursor!=null && mCursor.getCount()>0 ){
            /*//by yhr 以下方法会使拍照后的图片少一张在相册中显示，so 屏蔽掉
            mCursor.moveToFirst();*/
            int pathIndex = mCursor.getColumnIndex(MediaStore.Images.Media.DATA);
            while (mCursor.moveToNext()) {
                // 获取图片的路径
                String path = mCursor.getString(pathIndex);

                // 获取该图片的父路径名
                File parentFile = new File(path).getParentFile();
                if (parentFile == null) {
                    continue;
                }
                String dirPath = parentFile.getAbsolutePath();

                if (floderMap.containsKey(dirPath)) {
                    Photo photo = new Photo(path);
                    PhotoFloder photoFloder = floderMap.get(dirPath);
                    photoFloder.getPhotoList().add(photo);
                    floderMap.get(allPhotosKey).getPhotoList().add(photo);
                    continue;
                } else {
                    // 初始化imageFloder
                    PhotoFloder photoFloder = new PhotoFloder();
                    List<Photo> photoList = new ArrayList<Photo>();
                    Photo photo = new Photo(path);
                    photoList.add(photo);
                    photoFloder.setPhotoList(photoList);
                    photoFloder.setDirPath(dirPath);
                    photoFloder.setName(dirPath.substring(dirPath.lastIndexOf(File.separator) + 1, dirPath.length()));
                    floderMap.put(dirPath, photoFloder);
                    floderMap.get(allPhotosKey).getPhotoList().add(photo);
                }
            }
            mCursor.close();
        }
        return floderMap;
    }

}
