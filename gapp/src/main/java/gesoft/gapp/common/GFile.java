package gesoft.gapp.common;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

/**
 * Android Zip压缩解压缩
 * @author Ren.xia
 * @version 1.0
 * @updated 26-七月-2010 13:04:27
 */
public class GFile {

	public GFile(){

	}

	/**
	 * 删除文件
	 * @param path	文件路径
     */
	public static void deleteFile( String path ){
		File file = new File(path);
		if( file.exists() ){
			file.delete();
		}
	}

	/**
	 * 删除文件
	 * @param file	文件
     */
	public static void deleteFile( File file ){
		if( file.exists() ){
			file.delete();
		}
	}

	/**
	 * 删除多个文件
	 * @param arrayPath	多个文件路径
     */
	public static void deleteFiles( String ... arrayPath ){
		for (String path : arrayPath) {
			deleteFile( path );
		}
	}

	/**
	 * 删除文件
	 * @param arrayFile	多个文件
     */
	public static void deleteFiles( File ... arrayFile ){
		for ( File file : arrayFile) {
			deleteFile( file );
		}
	}

	/**
	 * 取得压缩包中的 文件列表(文件夹,文件自选)
	 * @param zipFileString		压缩包名字
	 * @param bContainFolder	是否包括 文件夹
	 * @param bContainFile		是否包括 文件
	 * @return
	 * @throws Exception
	 */
	public static java.util.List<File> getFileList(String zipFileString, boolean bContainFolder, boolean bContainFile)throws Exception {


		java.util.List<File> fileList = new java.util.ArrayList<File>();
		java.util.zip.ZipInputStream inZip = new java.util.zip.ZipInputStream(new java.io.FileInputStream(zipFileString));
		java.util.zip.ZipEntry zipEntry;
		String szName = "";

		while ((zipEntry = inZip.getNextEntry()) != null) {
			szName = zipEntry.getName();

			if (zipEntry.isDirectory()) {

				// get the folder name of the widget
				szName = szName.substring(0, szName.length() - 1);
				File folder = new File(szName);
				if (bContainFolder) {
					fileList.add(folder);
				}

			} else {
				File file = new File(szName);
				if (bContainFile) {
					fileList.add(file);
				}
			}
		}//end of while

		inZip.close();

		return fileList;
	}

	/**
	 * 返回压缩包中的文件InputStream
	 * @param zipFileString		压缩文件的名字
	 * @param fileString	解压文件的名字
	 * @return InputStream
	 * @throws Exception
	 */
	public static java.io.InputStream upZip(String zipFileString, String fileString)throws Exception {
		java.util.zip.ZipFile zipFile = new java.util.zip.ZipFile(zipFileString);
		java.util.zip.ZipEntry zipEntry = zipFile.getEntry(fileString);

		return zipFile.getInputStream(zipEntry);

	}


	/**
	 * 解压一个压缩文档 到指定位置
	 * @param zipFileString	压缩包的名字
	 * @param outPathString	指定的路径
	 * @throws Exception
	 */
	public static void unZipFolder(String zipFileString, String outPathString)throws Exception {
		java.util.zip.ZipInputStream inZip = new java.util.zip.ZipInputStream(new java.io.FileInputStream(zipFileString));
		java.util.zip.ZipEntry zipEntry;
		String szName = "";

		while ((zipEntry = inZip.getNextEntry()) != null) {
			szName = zipEntry.getName();

			if (zipEntry.isDirectory()) {

				// get the folder name of the widget
				szName = szName.substring(0, szName.length() - 1);
				File folder = new File(outPathString + File.separator + szName);
				folder.mkdirs();

			} else {

				File file = new File(outPathString + File.separator + szName);
				file.createNewFile();
				// get the output stream of the file
				java.io.FileOutputStream out = new java.io.FileOutputStream(file);
				int len;
				byte[] buffer = new byte[1024];
				// read (len) bytes into buffer
				while ((len = inZip.read(buffer)) != -1) {
					// write (len) byte from buffer at the position 0
					out.write(buffer, 0, len);
					out.flush();
				}
				out.close();
			}
		}//end of while

		inZip.close();

	}//end of func


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
			L.e(e);
		}
		return new File(zipPath);
	}

	/**
	 * 压缩文件
	 * @param folderString
	 * @param fileString
	 * @param zipOutputSteam
	 * @throws Exception
	 */
	private static void zipFiles(String folderString, String fileString, java.util.zip.ZipOutputStream zipOutputSteam)throws Exception{

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
		}
		else {

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

	/**
	 * 在SD卡根目录中创建Image文件，文件名为时间戳
	 * @param context
	 * @return
     */
	public static File createFileImage(Context context){
		return createFile(context, new Date().getTime()+".jpg");
	}

	/**
	 * 创建文件
	 * @param context
	 * @param fileName	完整文件名 例如 xxx.jpg
     * @return file		创建后的文件
     */
	public static File createFile(Context context, String fileName){
		File file;
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			//String timeStamp = String.valueOf(new Date().getTime());
			file = new File( Environment.getExternalStorageDirectory() + File.separator + fileName );
		}else{
			File cacheDir = context.getCacheDir();
			//String timeStamp = String.valueOf(new Date().getTime());
			file = new File(cacheDir, fileName);
		}
		return file;
	}

	/**
	 * 删除文件或目录
	 * @param pathDir
	 * @return
     */
	public static boolean delFileOrDir(String pathDir){
		try {
			File f = new File(pathDir);
			if( f.isDirectory() && f.exists() ){
                File[] array = f.listFiles();
                for (File file : array) {
                    delFileOrDir(file.getAbsolutePath());
                }
            }else if( f.isFile() && f.exists() ){
                f.delete();
            }
		} catch (Exception e) {
			L.e(e);
			return false;
		}
		return true;
	}

}