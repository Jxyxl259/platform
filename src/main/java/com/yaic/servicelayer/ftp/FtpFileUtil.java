package com.yaic.servicelayer.ftp;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yaic.servicelayer.charset.StandardCharset;


/**
 * @author yuanyaqi
 *
 */
public class FtpFileUtil
{

	private static final Logger logger = LoggerFactory.getLogger(FtpFileUtil.class);
	/**
	 * 服务器地址
	 */
	private String hostName;
	/**
	 * 服务器端口
	 */
	private int port;
	/**
	 * 服务器用户名
	 */
	private String userName;
	/**
	 * 服务器密码
	 */
	private String password;
	/**
	 * 服务器文件路径
	 */
	private String filePath;

	/**
	 * 设置PassiveMode传输  
	 */
	private Integer type = null;

	private FTPClient ftpClient = null;
	/**
	 * 设置默认超时时间
	 */
	private int defaultTimeoutSecond = 5 * 60 * 1000;
	/**
	 * 设置读取数据时阻塞的超时时间
	 */
	private int soTimeoutSecond = 10 * 60 * 1000;
	/**
	 * 设置数据超时时间
	 */
	private int dataTimeoutSecond = 2 * 60 * 1000;
	/**
	 * 设置传输模式
	 */
	private int FILE_TYPE = FTP.BINARY_FILE_TYPE;
	/**
	 * 默认字符集
	 */
	private String LOCAL_CHARSET  = StandardCharset.UTF_8.name();
	
	/**
	 * 连接到服务器
	 *
	 * @return true 连接服务器成功，false 连接服务器失败
	 */
	private boolean connectServer()
	{
		final boolean flag = true;
		if (ftpClient == null)
		{
			return login();
		}
		return flag;
	}

	/**
	 * 登录
	 *
	 * @return 登录成功true，失败false
	 */
	public boolean login()
	{
		boolean flag = true;
		int reply;
		try
		{
			ftpClient = new FTPClient();
			ftpClient.setDefaultPort(port);
			ftpClient.connect(hostName);
			ftpClient.login(userName, password);
			ftpClient.setDefaultTimeout(defaultTimeoutSecond);
			ftpClient.setSoTimeout(soTimeoutSecond);
			ftpClient.setDataTimeout(dataTimeoutSecond);
			reply = ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply))
			{
				ftpClient.disconnect();
				logger.error("FTP server refused connection");
				flag = false;
			}
		}
		catch (final SocketException e)
		{
			flag = false;
			logger.error("登录失败,连接超时！hostName:{},原因:{}", hostName, e.getMessage(), e);
		}
		catch (final IOException e)
		{
			flag = false;
			logger.error("登录失败,FTP服务器无法打开！hostName:{},原因:{}", hostName, e.getMessage(), e);
		}
		return flag;
	}

	/**
	 * 关闭连接
	 */
	public void closeConnect()
	{
		if (ftpClient != null) {
		try
		{
			ftpClient.logout();
		}
		catch (final IOException e) {
			logger.error("退出出现异常:{}", e.getMessage(), e);
		}
		try
		{
			ftpClient.disconnect();
		}
		catch (final IOException e) {
			logger.error("关闭连接出现异常:{}", e.getMessage(), e);
		}
		}
	}
	/**
	 *
	 * 上传文件至FTP服务器
	 * 如果目录不存在创建目录
	 *
	 * @param filePath 服务器文件路径
	 * @param fileName 文件名字
	 * @param input 输入流
	 * @return 上传成功true，失败false
	 */
	public synchronized boolean uploadFile(final String filePath, final String fileName, final InputStream input)
	{
		return uploadFile(filePath, fileName, input, LOCAL_CHARSET);
	}
	/**
	 *
	 * 上传文件至FTP服务器
	 * 如果目录不存在创建目录
	 *
	 * @param filePath 服务器文件路径
	 * @param fileName 文件名字
	 * @param input 输入流
	 * @param charset 自定义字符集
	 * @return 上传成功true，失败false
	 */
	public synchronized boolean uploadFile(final String filePath, final String fileName, final InputStream input, final String charset)
	{
		boolean flag = true;
		String newchar = charset.toUpperCase();
		try
		{
			connectServer();
			if(newchar.equals(LOCAL_CHARSET)) {
				if (!FTPReply.isPositiveCompletion(ftpClient.sendCommand("OPTS UTF8", "ON")))
				{// 开启服务器对UTF-8的支持，如果服务器支持就用UTF-8编码，否则就使用本地编码（GBK）.
					throw new Exception("FTP服务器不支持UTF-8编码");
				}
			}
			ftpClient.setControlEncoding(newchar);
			
			ftpClient.setFileType(FILE_TYPE);
			if(type == null || type == 1) {
				ftpClient.enterLocalPassiveMode(); //本地被动模式
	        }else if(2 == type){
	        	ftpClient.enterRemotePassiveMode();//远程被动模式
	        }else if(3 == type){
	        	ftpClient.enterLocalActiveMode();//本地主动模式
	        }
			ftpClient.setFileTransferMode(FTP.STREAM_TRANSFER_MODE); // 设置传输方式为流方式
			boolean isChangeWork = ftpClient.changeWorkingDirectory(filePath);
			//如果目录不存在，则创建目录	
			if (!isChangeWork) {
				String[] dirAry = filePath.split("/");
				if (dirAry.length > 0) {
					String dirPath = "";
					for (int i = 1; i < dirAry.length; i++) {
						if ("".equals(dirPath)) {
							dirPath = "/" + dirAry[i];
						} else {
							dirPath = dirPath + "/" + dirAry[i];
						}
						if (!ftpClient.changeWorkingDirectory(dirPath)) {
							boolean isMade = ftpClient.makeDirectory(dirPath);
							if (!isMade) {
								throw new IOException("ftp 上传文件创建目录失败");
							}
						}
					}
				}
				isChangeWork = ftpClient.changeWorkingDirectory(filePath);
				if (!isChangeWork) {
					logger.info("切换路径失败！");
				}
			}
			if (input == null)
			{
				logger.info("文件不存在！");
			}
			flag = ftpClient.storeFile(fileName, input);
		}
		catch (final IOException e)
		{
			flag = false;
			logger.error("文件上传失败！原因:{}", e.getMessage(), e);
		}
		catch (final Exception e)
		{
			flag = false;
			logger.error("文件上传失败！原因:{}", e.getMessage(), e);
		}
		finally
		{
			try
			{
				if (input != null)
				{
					input.close();
				}
			}
			catch (final Exception e)
			{
			}
		}
		return flag;
	}

	/**
	 * 下载文件
	 *
	 * @param remoteFileName 远程文件的名称
	 * @param localFileName 要写入的本地名称
	 * @return 下载成功true，失败false
	 */
	public boolean loadFile(final String remoteFileName, final String localFileName)
	{
		boolean flag = true;
		BufferedOutputStream buffOut = null;
		try
		{
			connectServer();
			buffOut = new BufferedOutputStream(new FileOutputStream(localFileName));
			flag = ftpClient.retrieveFile(remoteFileName, buffOut);
		}
		catch (final Exception e)
		{
			flag = false;
			logger.error("文件下载失败！", e.getMessage(), e);
		}
		finally
		{
			try
			{
				if (buffOut != null)
				{
					buffOut.close();
				}
			}
			catch (final Exception e)
			{
			}
		}
		return flag;
	}

	/**
	 * 在服务器上创建一个文件夹
	 *
	 * @param dir 文件夹名称
	 * @return 创建成功true，失败false
	 */
	public boolean makeDirectory(final String dir)
	{
		boolean flag = true;
		try
		{
			connectServer();
			flag = ftpClient.makeDirectory(dir);
		}
		catch (final Exception e)
		{
			flag = false;
			logger.error("创建文件夹异常！", e.getMessage(), e);
		}
		return flag;
	}

	/**
	 * 列出Ftp服务器上的所有文件和目录
	 * 
	 * @param pathname 路径名称
	 * @return fileLists 文件列表
	 */
	public List<String> listRemoteAllFiles(final String pathname) {
		List<String> fileLists = new ArrayList<String>();
		String[] names = null;
		try {
			connectServer();
			names = ftpClient.listNames(pathname);
			for (int i = 0, len = names.length; i < len; i++) {
				String file = names[i];
				fileLists.add(file);
			}
		}
		catch (final Exception e)
		{
			logger.error("出现异常！", e.getMessage(), e);
		}
		return fileLists;
	}

	/**
	 * 删除一个文件
	 * @param filename 文件名称
	 * @return 删除成功true，失败false
	 */
	public boolean deleteFile(final String filename)
	{
		boolean flag = true;
		try
		{
			connectServer();
			flag = ftpClient.deleteFile(filename);
		}
		catch (final IOException ioe)
		{
			flag = false;
			logger.error("删除异常:{}", ioe.getMessage(), ioe);
		}
		return flag;
	}

	/**
	 * 删除目录
	 *
	 * @param pathname 目录名称
	 */
	public void deleteDirectory(final String pathname)
	{
		try
		{
			connectServer();
			final File file = new File(pathname);
			if (file.isDirectory())
			{
				@SuppressWarnings("unused")
				final File file2[] = file.listFiles();
			}
			else
			{
				deleteFile(pathname);
			}
			ftpClient.removeDirectory(pathname);
		}
		catch (final IOException ioe)
		{
			logger.error("删除异常:{}", ioe.getMessage(), ioe);
		}
	}

	/**
	 * 获取指定目录下的文件列表
	 *
	 * @param filePath 服务器文件路径
	 * @return 文件列表
	 */
	public FTPFile[] getFileList(final String filePath)
	{
		FTPFile[] fs = null;
		try
		{
			connectServer();
			ftpClient.enterLocalPassiveMode();
			ftpClient.changeWorkingDirectory(filePath);
			fs = ftpClient.listFiles();
		}
		catch (final IOException e)
		{
			logger.error("获取指定目录下的文件列表失败！原因:{}", e.getMessage(), e);
		}
		return fs;
	}

	/**
	 *无参构造
	 */
	public FtpFileUtil()
	{
		super();
	}

	/**
	 * 有参构造
	 * @param hostName 服务器名称
	 * @param port 服务器端口
	 * @param userName 服务器用户名
	 * @param password 服务器密码
	 */
	public FtpFileUtil(final String hostName, final int port, final String userName, final String password)
	{
		super();
		this.hostName = hostName;
		this.port = port;
		this.userName = userName;
		this.password = password;
	}

	/**
	 * 有参构造
	 * @param hostName 服务器名称
	 * @param port 服务器端口
	 * @param userName 服务器用户名
	 * @param password 服务器密码
	 * @param filePath 服务器文件路径
	 */
	public FtpFileUtil(final String hostName, final int port, final String userName, final String password, final String filePath)
	{
		super();
		this.hostName = hostName;
		this.port = port;
		this.userName = userName;
		this.password = password;
		this.filePath = filePath;
	}

	
	/**
	 * 有参构造
	 * @param hostName 服务器名称
	 * @param port 服务器端口
	 * @param userName 服务器用户名
	 * @param password 服务器密码
	 * @param filePath 服务器文件路径
	 * @param type PassiveMode传输类型
	 */
	public FtpFileUtil(String hostName, int port, String userName, String password, String filePath, Integer type) {
		super();
		this.hostName = hostName;
		this.port = port;
		this.userName = userName;
		this.password = password;
		this.filePath = filePath;
		this.type = type;
	}
	
	/**
	 * @return 获取服务器地址
	 */
	public String getHostName()
	{
		return hostName;
	}

	/**
	 * @param hostName 服务器地址
	 */
	public void setHostName(final String hostName)
	{
		this.hostName = hostName;
	}

	/**
	 * @return 获取服务器端口
	 */
	public int getPort()
	{
		return port;
	}

	/**
	 * @param port 服务器端口
	 */
	public void setPort(final int port)
	{
		this.port = port;
	}

	/**
	 * @return 获取服务器用户名
	 */
	public String getUserName()
	{
		return userName;
	}

	/**
	 * @param userName 服务器用户名
	 */
	public void setUserName(final String userName)
	{
		this.userName = userName;
	}

	/**
	 * @return 获取服务器密码
	 */
	public String getPassword()
	{
		return password;
	}

	/**
	 * @param password 服务器密码
	 */
	public void setPassword(final String password)
	{
		this.password = password;
	}

	/**
	 * @return 获取服务器文件路径
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * @param filePath 服务器文件路径
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * @return 获取传输模式
	 */
	public int getFILE_TYPE()
	{
		return FILE_TYPE;
	}

	/**
	 * @param fILE_TYPE 传输模式
	 */
	public void setFILE_TYPE(final int fILE_TYPE)
	{
		FILE_TYPE = fILE_TYPE;
	}

	/**
	 * @param defaultTimeoutSecond 默认超时时间
	 */
	public void setDefaultTimeoutSecond(final int defaultTimeoutSecond)
	{
		this.defaultTimeoutSecond = defaultTimeoutSecond;
	}

	/**
	 * @param soTimeoutSecond 读取数据时阻塞的超时时间
	 */
	public void setSoTimeoutSecond(final int soTimeoutSecond)
	{
		this.soTimeoutSecond = soTimeoutSecond;
	}

	/**
	 * @param dataTimeoutSecond 数据超时时间
	 */
	public void setDataTimeoutSecond(final int dataTimeoutSecond)
	{
		this.dataTimeoutSecond = dataTimeoutSecond;
	}
}
