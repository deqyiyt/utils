package com.ias.common.utils.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.apache.commons.io.FileUtils;

import com.ias.common.utils.date.TimeUtil;
import com.ias.common.utils.random.RandomUtils;

/**
 *********************************************** 
 * @Copyright (c) by ysc All right reserved.
 * @Create Date: 2014-2-24 下午2:15:05
 * @Create Author: hujz
 * @File Name: FileUtil
 * @Function: 对java中的File进行2次封装
 * @Last version: 1.0
 * @Last Update Date:
 * @Change Log:
 ************************************************* 
 */
public class FileUtil {
	private static final int BUFFER_SIZE = 16 * 1024;
	/**
	 * 根据路径创建指定的文件夹
	 * 
	 * @param path
	 *            文件夹路径
	 */
	public static void touchPath(String path) {
		try {
			File filePath = new File(path);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	* 判断文件及目录是否存在，若不存在则创建文件及目录
	* @param filepath
    * @return
    * @throws Exception
    */
	public static File checkExist(String filepath){
		File file=new File(filepath);
		try{
			if (file.exists()) {							//判断文件目录的存在
				if(!file.isDirectory()){					//判断文件的存在性 
					file.createNewFile();					//创建文件
				}
			}else {
				File file2=new File(file.getParent());
				file2.mkdirs();							//创建文件夹
				if(!file.isDirectory()){
					file.createNewFile();//创建文件 
				}
			}
			return file;
		}catch(Exception e){
			e.printStackTrace();
		}
		return file;
	}
	
	/**
	 * 创建多级目录
	 * 
	 * @param path
	 */
	public static void makeMoreDir(String path) {
		if(null==path)return;
		path=path.trim();
		StringTokenizer st = new StringTokenizer(path, System.getProperties().getProperty("file.separator"));
		String path1 = st.nextToken() + System.getProperties().getProperty("file.separator");
		if(System.getProperties().getProperty("file.separator").equals(path.subSequence(0, 1))){
			path1=System.getProperties().getProperty("file.separator")+path1;
		}
		String path2 = path1;
		while (st.hasMoreTokens()) {
			path1 = st.nextToken() + System.getProperties().getProperty("file.separator");
			path2 += path1;
			File inbox = new File(path2);
			if (!inbox.exists()) {
				inbox.mkdir();
			}
		}
	}

	/**
	 * 输入流拷贝文件
	 * @param src 文件输入流
	 * @param dstPath 文件输出路径
	 * @return =true 拷贝成功 =false 拷贝失败
	 */
	public static boolean copy(InputStream src, final String dstPath) {
		try {
			File dstFile = new File(dstPath);
			byte[] buffer = new byte[64 * 1024];
			int length = 0;
			OutputStream os = new FileOutputStream(dstFile);

			try {
				while ((length = src.read(buffer, 0, buffer.length)) != -1) {
					os.write(buffer, 0, length);
				}
			} finally {
				close(os);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 输入流拷贝文件
	 * @param src 文件输入流
	 * @param dstPath 文件输出路径
	 * @return =true 拷贝成功 =false 拷贝失败
	 */
	public static boolean copy(byte[] buffer, final String dstPath) {
		File dstFile = new File(dstPath);
		try (OutputStream os = new FileOutputStream(dstFile)){
			os.write(buffer);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
    /**
     * 拷贝文件
     * @author 胡久洲
     * @date 2014-3-16 下午7:25:30
     * @param sourceFile	源文件
     * @param targetFile	备份文件地址(包含文件名，如：d:\\test.txt)
     * @throws IOException
     */
    public static void copy(File sourceFile, String targetFilePath) throws IOException {
    	copy(sourceFile, new File(targetFilePath));
    }
    
	/**
	 * 拷贝文件
	 * @param src 源文件
	 * @param dst 目标文件
	 */
	public static void copy(File src, File dst) {
	    InputStream in = null;
	    OutputStream out = null;
	    try {
	        in = new BufferedInputStream(new FileInputStream(src), BUFFER_SIZE);
	        out = new BufferedOutputStream(new FileOutputStream(dst),
	                BUFFER_SIZE);
	        byte[] buffer = new byte[BUFFER_SIZE];
	        int len = 0;
	        while ((len = in.read(buffer)) > 0) {
	            out.write(buffer, 0, len);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        if (null != in) {
	            try {
	                in.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	        if (null != out) {
	            try {
	                out.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	}
	
	public static boolean copyFolder(String oldPath, String newPath) {
		try {
			(new File(newPath)).mkdirs();
			File a = new File(oldPath);
			String[] file = a.list();
			File temp = null;
			for (int i = 0; i < file.length; i++) {
				if (oldPath.endsWith(File.separator)) {
					temp = new File(oldPath + file[i]);
				} else {
					temp = new File(oldPath + File.separator + file[i]);
				}

				if (temp.isFile()) {
					FileInputStream input = new FileInputStream(temp);
					FileOutputStream output = new FileOutputStream(newPath + "/" + (temp.getName()).toString());
					byte[] buffer = new byte[(int) temp.length()];
					input.read(buffer);
					output.write(buffer);
					output.flush();
					output.close();
					input.close();
				}
				if (temp.isDirectory()) {
					copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
				}
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public static boolean copyFileAsStream(String file1, String file2) {
		File temp = new File(file1);
		if (temp.isFile()) {
			try {
				checkExist(file2);
				FileInputStream input = new FileInputStream(temp);
				FileOutputStream output = new FileOutputStream(file2);
				byte[] buffer = new byte[(int) temp.length()];
				input.read(buffer);
				output.write(buffer);
				output.flush();
				output.close();
				input.close();
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		} else
			return false;
	}

	/**
	 * 文件添加后缀
	 * @param path 文件(带路径)
	 * @param suffix 后缀名称
	 */
	public static void rename(String path, String suffix) {
		try {
			File file = new File(path);
			File dstFile = new File(path + "." + suffix);
			file.renameTo(dstFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 指定路径下的文件列表
	 * @param fileInfo 文件信息对象
	 * @param path 文件所在路径
	 * @return 指定路径下的文件列表
	 */
	public static String[] toFileList(String path) {
		String[] listfilenames;
		File file = new File(path);
		listfilenames = file.list();
		if (null == listfilenames) {
			return new String[] {};
		}

		return listfilenames;
	}
	
	public static List<File> getFiles(File folder){
		List<File>files=new ArrayList<File>();
		iterateFolder(folder, files);
		return files;
	}
	
	private static void iterateFolder(File folder,List<File>files)  {
		  File flist[] = folder.listFiles();
		  files.add(folder);
		  if (flist == null || flist.length == 0) {
			  files.add(folder) ;
		  }else{
			  for (File f : flist) {
			      if (f.isDirectory()) {
			           iterateFolder(f,files);
			      } else {
			          files.add(f) ;
			      }
			 }
		 }
	}
	
    /**
     * 查询文件夹下的文件
     * @author 胡久洲
     * @date 2014-3-16 下午7:14:48
     * @param baseDirName	文件夹路径
     * @param targetFileName	后缀名集合
     * @return
     */
    public static List<File> findFiles(String baseDirName,String... targetFileName) {
    	List<File> fileList = new ArrayList<File>();
    	try{
    		String tempName = null;
	        File baseDir = new File(baseDirName);   
	        if (!baseDir.exists() || !baseDir.isDirectory()){
	        	
	        } else {
	            String[] filelist = baseDir.list();
	            for (int i = 0; i < filelist.length; i++) {
	                File readfile = new File(baseDirName + "/" + filelist[i]); 
	                if(!readfile.isDirectory()) {
	                	tempName =  readfile.getName();
	                	for(String target : targetFileName) {
	            			if (wildcardMatch(target, tempName)) {
		                        File src = new File(readfile.getAbsoluteFile().toString());
		                        fileList.add(src);
		                    }
	                	}
	                }
	            }
	        }  
    	}catch (Exception e) {
		}
    	return fileList;
    }
    
    /**
     * 递归查询文件夹下的文件
     * @author 胡久洲
     * @date 2014-3-16 下午7:14:48
     * @param baseDirName	文件夹路径
     * @param fileList	返回的文件集合
     * @param targetFileName	后缀名集合
     * @return
     */
    public static void findRecuFiles(String baseDirName,List<File> fileList, String... targetFileName) {
    	try{
    		String tempName = null;
	        File baseDir = new File(baseDirName);   
	        if (!baseDir.exists() || !baseDir.isDirectory()){
	        	System.out.println("未找到文件");
	        } else {
	            String[] filelist = baseDir.list();
	            for (int i = 0; i < filelist.length; i++) {
	                File readfile = new File(baseDirName + "/" + filelist[i]); 
	                if(!readfile.isDirectory()) {
	                	tempName =  readfile.getName();
	                	for(String target : targetFileName) {
	            			if (wildcardMatch(target, tempName)) {
		                        File src = new File(readfile.getAbsoluteFile().toString());
		                        fileList.add(src);
		                    }
	                	}
	                } else if(readfile.isDirectory()){   
	                	findRecuFiles(baseDirName + File.separator + filelist[i],fileList,targetFileName);   
	                }
	            }
	        }  
    	}catch (Exception e) {
		}
    }
    
    public static boolean wildcardMatch(String pattern, String str) {   
        int patternLength = pattern.length();   
        int strLength = str.length();   
        int strIndex = 0;   
        char ch;   
        for (int patternIndex = 0; patternIndex < patternLength; patternIndex++) {   
            ch = pattern.charAt(patternIndex);   
            if (ch == '*') {   
                while (strIndex < strLength) {   
                    if (wildcardMatch(pattern.substring(patternIndex + 1),   
                            str.substring(strIndex))) {   
                        return true;   
                    }   
                    strIndex++;   
                }   
            } else if (ch == '?') {   
                strIndex++;   
                if (strIndex > strLength) {   
                    return false;   
                }   
            } else {   
                if ((strIndex >= strLength) || (ch != str.charAt(strIndex))) {   
                    return false;   
                }   
                strIndex++;   
            }   
        }   
        return (strIndex == strLength);   
    }  

	/**
	 * 
	 *获取指定文件夹下的所有文件，包括子文件夹的文件名称.<br>
	 *@author:hujiuzhou@hotoa.com
	 *@param strPath 指定文件夹路径
	 *@param filelist 获取的文件集合
	 */
	public static void toFileList(String strPath, List<String> filelist) {
		File dir = new File(strPath);
		File[] files = dir.listFiles();
		if (files == null) {
			return;
		}
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				toFileList(files[i].getAbsolutePath(), filelist);
			} else {
				// filelist.add(files[i].getAbsolutePath());
				filelist.add(files[i].getName());
			}
		}

	}
	
	/**
     * 文件备份操作
     * @param srcPath 文件元路径
     * @param dstPath 备份目标路径
     */
    public static void backup(String srcPath, String dstPath) {
        try {
            File srcFile = new File(srcPath);
            File dstFile = new File(dstPath);
            FileUtils.copyFile(srcFile, dstFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
	/**
	 * 关闭输入流
	 * @param stream 输入流
	 */
	public static void close(InputStream stream) {
		try {
			stream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 关闭输出流
	 * @param stream 输出流
	 */
	public static void close(OutputStream stream) {
		try {
			stream.flush();
			stream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 关闭读入文件流
	 * @param reader 读入文件流
	 */
	public static void close(Reader reader) {
		try {
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 关闭寫入文件流
	 * @param writer 寫入文件流
	 */
	public static void close(Writer writer) {
		try {
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除指定路径下的文件
	 * @param dirPath 指定路径
	 * @throws java.lang.Exception 删除失败错误
	 */
	public static void deleteAllSubFiles(String dirPath) throws Exception {
		try {
			File dir = new File(dirPath);
			if (dir.isDirectory()) {
				File[] subFiles = dir.listFiles();
				for (int i = 0; i < subFiles.length; i++) {
					File subFile = subFiles[i];
					try {
						subFile.delete();
					} catch (Exception e) {
						throw e;
					}
				}
			}
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * 文件是否存在错误
	 * @param filePathname 指定的文件
	 * @return =true 文件存在 false 文件不存在
	 */
	public static boolean isFileExisted(String filePathname) {
		boolean existed;
		try {
			File f = new File(filePathname);
			existed = f.isFile();
		} catch (Exception e) {
			existed = false;
		}
		return existed;
	}

	/**
	 * 去掉文件的路径，只留下文件名
	 * 
	 * @param pathname
	 *            带路径的文件名
	 * @return 文件名称
	 */
	public static String removePath(String pathname) {
		String fname = pathname;
		int index = pathname.lastIndexOf(File.separator);
		if (index >= 0) {
			fname = pathname.substring(index + 1);
		}
		return fname;
	}

	/**
	 * 去掉文件名而只保留文件路径
	 * 
	 * @param pathname
	 *            带路径的文件名
	 * @return 文件路径
	 */
	public static String removeFileName(String pathname) {
		String fname = pathname;
		int index = pathname.lastIndexOf(File.separator);
		if (index >= 0) {
			fname = pathname.substring(0, index);
		}
		return fname;
	}

	/**
	 *根据路径删除指定的目录或文件，无论存在与否<br>
	 *工程名:cctcpsp<br>
	 *包名:com.common.psp.action.knowledgebase.knowledgetype<br>
	 *方法名:DeleteFolder方法.<br>
	 * 
	 *@author:jiangwenqi
	 *@since :1.0:2009-11-5
	 *@param sPath
	 *            要删除的目录或文件
	 *@return 删除成功返回 true，否则返回 false。
	 */
	public static boolean DeleteFolder(String sPath) {
		boolean flag = false;
		File file = new File(sPath);
		// 判断目录或文件是否存在
		if (!file.exists()) { // 不存在返回 false
			return flag;
		} else {
			// 判断是否为文件
			if (file.isFile()) { // 为文件时调用删除文件方法
				return deleteFile(sPath);
			} else { // 为目录时调用删除目录方法
				return deleteDirectory(sPath);
			}
		}
	}

	/**
	 *删除单个文件.<br>
	 *工程名:cctcpsp<br>
	 *包名:com.common.psp.action.knowledgebase.knowledgetype<br>
	 *方法名:deleteFile方法.<br>
	 * 
	 *@author:jiangwenqi
	 *@since :1.0:2009-11-5
	 *@param sPath
	 *            被删除文件的文件名
	 *@return 单个文件删除成功返回true，否则返回false
	 */
	public static boolean deleteFile(String sPath) {
		boolean flag = false;
		File file = new File(sPath);
		// 路径为文件且不为空则进行删除
		if (file.isFile() && file.exists()) {
			file.delete();
			flag = true;
		}
		return flag;
	}

	/**
	 * 删除目录（文件夹）以及目录下的文件 工程名:cctcpsp<br>
	 *包名:com.common.psp.action.knowledgebase.knowledgetype<br>
	 *方法名:deleteDirectory方法.<br>
	 * 
	 *@author:jiangwenqi
	 *@since :1.0:2009-11-5
	 *@param sPath
	 *            被删除目录的文件路径
	 *@return 目录删除成功返回true，否则返回false
	 */
	public static boolean deleteDirectory(String sPath) {
		// 如果sPath不以文件分隔符结尾，自动添加文件分隔符
		if (!sPath.endsWith(File.separator)) {
			sPath = sPath + File.separator;
		}
		File dirFile = new File(sPath);
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			return false;
		}
		boolean flag = true;
		// 删除文件夹下的所有文件(包括子目录)
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 删除子文件
			if (files[i].isFile()) {
				flag = deleteFile(files[i].getAbsolutePath());
				if (!flag)
					break;
			} // 删除子目录
			else {
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag)
					break;
			}
		}
		if (!flag)
			return false;
		// 删除当前目录
		if (dirFile.delete()) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 删除文件或路径
	 * 
	 * @param fileName
	 * @return
	 */
	public static boolean deleteFileOrDirectory(String fileName) {
		File file = new File(fileName);
		if (!file.exists())
			return false;
		else {
			if (file.isFile())
				return deleteFile(fileName);
			else
				return deleteDirectory(fileName);
		}
	}
	
	
	/**
	 * 
	 * @Title: write
	 * @Description: 写入文件
	 * @param cont	文件内容
	 * @param dist	需要写入的文件
	 * @param encode	编码格式
	 * @return 设定文件
	 * @author hujiuzhou
	 * @return boolean 返回类型
	 * @throws
	 */
	public static boolean write(String cont, File dist,String encode) {
		OutputStreamWriter writer = null;
		try {
            writer = new OutputStreamWriter(new FileOutputStream(dist),encode); 
            writer.write(cont);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}finally{
			close(writer);
		}
	}
	
    /**
     * @Project SC
     * @Package com.hujz.soasoft.util.type
     * @Method appendWrite方法.<br>
     * @Description 文件末尾追加写入
     * @author hjz
     * @date 2015-7-21 下午12:51:13
     * @param fileName
     * @param content
     */
    public static void appendWrite(String fileName, String content) {
    	FileWriter writer = null;
	    try {
	        //打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
	        writer = new FileWriter(fileName, true);
	        writer.write(content);
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally{
			close(writer);
		}
    }
	public static boolean writeBuffered(String cont, File dist,String encode) {
		OutputStreamWriter writer = null;
		BufferedWriter bfw = null;
		try {
            writer = new OutputStreamWriter(new FileOutputStream(dist,true),encode);
            bfw = new BufferedWriter(writer);
            bfw.write(cont);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}finally{
			close(writer);
		}
	}
	
	/**
	 * @Description 下载网络资源到本地
	 * @author 胡久洲
	 * @date 2013-7-3 上午11:07:54
	 * @param urlPath	网络路径
	 * @param localPath		本地路径
	 * @throws Exception
	 */
	public static void download(String urlPath,String localPath) throws Exception{
        URL url = new URL(urlPath);
        // 打开网络输入流
        DataInputStream dis = new DataInputStream(url.openStream());
        FileOutputStream fos = new FileOutputStream(checkExist(localPath));
        byte[] buffer = new byte[1024];
        int length;
        // 开始填充数据
        while ((length = dis.read(buffer)) > 0) {
            fos.write(buffer, 0, length);
        }
        dis.close();
        fos.close();
	}
	
	/**
	 * @Method read方法.<br>
	 * @Description 读取文件的内容
	 * @author 胡久洲
	 * @date 2013-8-8 下午6:01:12
	 * @param src		需要读取的文件
	 * @param detector	文件字符格式
	 * @return
	 */
	public static String read(File src,String detector) {
		StringBuffer res = new StringBuffer();
		String line = null;
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(src),detector));
			while ((line = reader.readLine()) != null) {
				res.append(line + "\r\n");
			}
			
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return res.toString();
	}
	
	/**
	 * 生成当前年月格式的文件路径
	 * 
	 * yyyyMM 200806
	 * 
	 * @return
	 */
	public static String genPathName() {
		return TimeUtil.toString(Calendar.getInstance(),TimeUtil.pathDf);
	}

	/**
	 * 生产以当前日、时间开头加4位随机数的文件名
	 * ddHHmmss 03102230
	 * @return 10位长度文件名
	 */
	public static String genFileName() {
		return TimeUtil.toString(Calendar.getInstance(),TimeUtil.nameDf) + RandomUtils.getRandomValue(4);
	}

	/**
	 * 生产以当前时间开头加4位随机数的文件名
	 * 
	 * @param ext
	 *            文件名后缀，不带'.'
	 * @return 10位长度文件名+文件后缀
	 */
	public static String genFileName(String ext) {
		return genFileName() + "." + ext;
	}
	
	public static String getFileSufix(String fileName) {
		int splitIndex = fileName.lastIndexOf(".");
		return fileName.substring(splitIndex + 1);
	}
	
	/**
	 * 将本地资源转换为byte[]
	 * @author jiuzhou.hu
	 * @date 2015年12月28日 上午10:40:01
	 * @param filePath	本地资源地址
	 * @return
	 */
	public static byte[] readToByte(String filePath) {
		File file = new File(filePath);
		return readToByte(file);
	}
	
	/**
	 * 将文件转换为byte[]
	 * @author jiuzhou.hu
	 * @date 2015年12月28日 上午10:40:01
	 * @param filePath	本地资源地址
	 * @return
	 */
	public static byte[] readToByte(File file) {
		Long filelength = file.length();
		byte[] filecontent = new byte[filelength.intValue()];
		try {
			FileInputStream in = new FileInputStream(file);
			in.read(filecontent);
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return filecontent;
	}
	
	/**
	 * 将InputStream转换为byte[]
	 * @author jiuzhou.hu
	 * @date 2016年1月11日 下午6:28:48
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static byte[] readToByte(InputStream is) throws IOException {
		ByteArrayOutputStream swapStream = null;
		byte[] in2b = null;
		try {
			swapStream = new ByteArrayOutputStream();
	        byte[] buff = new byte[100];
	        int rc = 0;
	        while ((rc = is.read(buff, 0, 100)) > 0) {  
	            swapStream.write(buff, 0, rc);
	        }
	        in2b = swapStream.toByteArray();
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			if(is != null) {
				is.close();
			}
			if(swapStream != null) {
				swapStream.close();
			}
		}
		return in2b;
	}
	
	/**
	 * @Title 获取音频的时长
	 * @Method getAudioSecondLength方法.<br>
	 * @author jiuzhou.hu
	 * @date 2016年8月2日 下午4:15:31
	 * @param is	输入流
	 * @return		返回单位：秒
	 */
	public static long getAudioSecondLength(InputStream is) {
		long length = 0l;
		try (Clip clip = AudioSystem.getClip()){
			AudioInputStream ais = AudioSystem.getAudioInputStream(new BufferedInputStream(is));
	        clip.open(ais);
	        length = (long)Math.floor(clip.getMicrosecondLength()/1000000D);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return length;
	}
}
