package com.yaic.servicelayer.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yaic.commons.io.output.StringBuilderWriter;
import com.yaic.servicelayer.codec.binary.Base64Decoder;
import com.yaic.servicelayer.codec.binary.Base64Encoder;


/**
 * 文件操作工具类
 * <ul>
 * <li>读写文件</li>
 * <li>文件字符串相关操作</li>
 * </ul>
 */
public class FileUtil
{
	private final static Logger logger = LoggerFactory.getLogger(FileUtil.class);

	private final static String FILE_EXTENSION_SEPARATOR = ".";
	/**
	 * Represents the end-of-file (or stream).
	 */
	private final static int EOF = 0xffffffff;

	/**
	 * Represents the default buffer size ({@value}).
	 */
	private final static int DEFAULT_BUFFER_SIZE = 0x1000;

	/**
	 * 读文件
	 *
	 * @param filePath
	 *           文件路径
	 * @return 若文件路径所在文件不存在返回null，否则返回文件内容
	 */
	public static String readFile(final String filePath)
	{
		if (StringUtil.isEmpty(filePath))
		{
			return null;
		}

		final File file = new File(filePath);
		if (file == null || file.isFile() == false)
		{
			return null;
		}

		final char[] buffer = new char[DEFAULT_BUFFER_SIZE];
		InputStream input = null;
		InputStreamReader in = null;
		StringBuilderWriter out = null;
		int n;

		try
		{
			input = new FileInputStream(file);
			in = new InputStreamReader(input, StandardCharsets.UTF_8);
			out = new StringBuilderWriter();
			while (EOF != (n = in.read(buffer)))
			{
				out.write(buffer, 0, n);
			}
			return out.toString();
		}
		catch (final IOException e)
		{
			throw new RuntimeException(e.getMessage(), e);
		}
		finally
		{
			IOUtil.closeQuietly(out);
			IOUtil.closeQuietly(in);
			IOUtil.closeQuietly(input);
		}
	}

	/**
	 * 写文件
	 *
	 * @param filePath
	 *           文件路径
	 * @param content
	 *           内容
	 * @param append
	 *           是否追加, true写在文件尾部，false写在文件头部（覆盖源文件内容）
	 * @return 写文件成功, 返回true, 否则返回false
	 */
	public static boolean writeFile(final String filePath, final String content, final boolean append)
	{
		FileWriter fileWriter = null;
		try
		{
			fileWriter = new FileWriter(filePath, append);
			fileWriter.write(content);
			return true;
		}
		catch (final IOException e)
		{
			throw new RuntimeException(e.getMessage(), e);
		}
		finally
		{
			IOUtil.closeQuietly(fileWriter);
		}
	}

	/**
	 * 写文件 设置字符集
	 *
	 * @param filePath
	 *           文件路径
	 * @param content
	 *           内容
	 * @param chatset
	 * @param append
	 *           是否追加, true写在文件尾部，false写在文件头部（覆盖源文件内容）
	 * @return 写文件成功, 返回true, 否则返回false
	 */
	public static boolean writeFile(final String filePath, final String content, final String chatset, final boolean append)
	{
		OutputStreamWriter osw = null;
		try
		{
			osw = new OutputStreamWriter(new FileOutputStream(filePath, true), chatset);
			osw.write(content);
			osw.flush();
			return true;
		}
		catch (final IOException e)
		{
			throw new RuntimeException("IOException occurred. ", e);
		}
		finally
		{
			IOUtil.closeQuietly(osw);
		}
	}

	/**
	 * 读文件，每行作为list的一个元素 设置编码格式
	 *
	 * @param filePath
	 *           文件路径
	 * @param charset
	 * @return 若文件路径所在文件不存在返回null，否则返回文件内容
	 */
	public static List<String> readFileToList(final String filePath, final String charset)
	{
		final File file = new File(filePath);
		final List<String> fileContent = new ArrayList<String>();
		if (file != null && file.isFile())
		{
			BufferedReader reader = null;
			InputStreamReader isr = null;
			try
			{
				isr = new InputStreamReader(new FileInputStream(file), charset);
				reader = new BufferedReader(isr);
				String line = null;
				while ((line = reader.readLine()) != null)
				{
					fileContent.add(line);
				}
				return fileContent;
			}
			catch (final IOException e)
			{
				throw new RuntimeException("IOException occurred. ", e);
			}
			finally
			{
				IOUtil.closeQuietly(reader);
			}
		}
		return null;
	}

	/**
	 * 写文件
	 *
	 * @param filePath
	 *           文件路径
	 * @param stream
	 *           内容流
	 * @return 写文件成功, 返回true, 否则返回false
	 */
	public static boolean writeFile(final String filePath, final InputStream stream)
	{
		OutputStream o = null;
		try
		{
			o = new FileOutputStream(filePath);
			final byte data[] = new byte[1024];
			int length = -1;
			while ((length = stream.read(data)) != -1)
			{
				o.write(data, 0, length);
			}
			o.flush();
			return true;
		}
		catch (final FileNotFoundException e)
		{
			throw new RuntimeException("FileNotFoundException occurred. ", e);
		}
		catch (final IOException e)
		{
			throw new RuntimeException("IOException occurred. ", e);
		}
		finally
		{
			IOUtil.closeQuietly(o);
			IOUtil.closeQuietly(stream);
		}
	}

	/**
	 * 读文件，每行作为list的一个元素
	 *
	 * @param filePath
	 *           文件路径
	 * @return 若文件路径所在文件不存在返回null，否则返回文件内容
	 */
	public static List<String> readFileToList(final String filePath)
	{
		final File file = new File(filePath);
		final List<String> fileContent = new ArrayList<String>();
		if (file != null && file.isFile())
		{
			BufferedReader reader = null;
			try
			{
				reader = new BufferedReader(new FileReader(file));
				String line = null;
				while ((line = reader.readLine()) != null)
				{
					fileContent.add(line);
				}
				reader.close();
				return fileContent;
			}
			catch (final IOException e)
			{
				throw new RuntimeException("IOException occurred. ", e);
			}
			finally
			{
				IOUtil.closeQuietly(reader);
			}
		}
		return null;
	}

	/**
	 * 从路径中获得文件名（不包含后缀名）
	 *
	 * @param filePath
	 *           文件路径
	 * @return 文件名（不包含后缀名）
	 *
	 *         <pre>
	 *      getFileNameWithoutExtension(null)               =   null
	 *      getFileNameWithoutExtension("")                 =   ""
	 *      getFileNameWithoutExtension("   ")              =   "   "
	 *      getFileNameWithoutExtension("abc")              =   "abc"
	 *      getFileNameWithoutExtension("a.mp3")            =   "a"
	 *      getFileNameWithoutExtension("a.b.rmvb")         =   "a.b"
	 *      getFileNameWithoutExtension("c:\\")              =   ""
	 *      getFileNameWithoutExtension("c:\\a")             =   "a"
	 *      getFileNameWithoutExtension("c:\\a.b")           =   "a"
	 *      getFileNameWithoutExtension("c:a.txt\\a")        =   "a"
	 *      getFileNameWithoutExtension("/home/admin")      =   "admin"
	 *      getFileNameWithoutExtension("/home/admin/a.txt/b.mp3")  =   "b"
	 *         </pre>
	 */
	public static String getFileNameWithoutExtension(final String filePath)
	{
		if (StringUtil.isEmpty(filePath))
		{
			return filePath;
		}

		final int extenPosi = filePath.lastIndexOf(FILE_EXTENSION_SEPARATOR);
		final int filePosi = filePath.lastIndexOf(File.separator);
		if (filePosi == -1)
		{
			return (extenPosi == -1 ? filePath : filePath.substring(0, extenPosi));
		}
		else
		{
			if (extenPosi == -1)
			{
				return filePath.substring(filePosi + 1);
			}
			else
			{
				return (filePosi < extenPosi ? filePath.substring(filePosi + 1, extenPosi) : filePath.substring(filePosi + 1));
			}
		}
	}

	/**
	 * Extract the filename from the given path, e.g. {@code "mypath/myfile.txt" -> "myfile.txt"}.
	 *
	 * @param path
	 *           the file path (may be {@code null})
	 * @return the extracted filename, or {@code null} if none
	 */
	public static String getFilename(final String path)
	{
		if (path == null)
		{
			return null;
		}
		final int separatorIndex = path.lastIndexOf(File.separator);
		return (separatorIndex != -1 ? path.substring(separatorIndex + 1) : path);
	}

	/**
	 * 从路径中获得文件夹路径
	 *
	 * @param filePath
	 *           文件名
	 * @return 文件夹路径
	 *
	 *         <pre>
	 *      getFolderName(null)               =   null
	 *      getFolderName("")                 =   ""
	 *      getFolderName("   ")              =   ""
	 *      getFolderName("a.mp3")            =   ""
	 *      getFolderName("a.b.rmvb")         =   ""
	 *      getFolderName("abc")              =   ""
	 *      getFolderName("c:\\")              =   "c:"
	 *      getFolderName("c:\\a")             =   "c:"
	 *      getFolderName("c:\\a.b")           =   "c:"
	 *      getFolderName("c:a.txt\\a")        =   "c:a.txt"
	 *      getFolderName("c:a\\b\\c\\d.txt")    =   "c:a\\b\\c"
	 *      getFolderName("/home/admin")      =   "/home"
	 *      getFolderName("/home/admin/a.txt/b.mp3")  =   "/home/admin/a.txt"
	 *         </pre>
	 */
	public static String getFolderName(final String filePath)
	{

		if (StringUtil.isEmpty(filePath))
		{
			return filePath;
		}

		final int filePosi = filePath.lastIndexOf(File.separator);
		if (filePosi == -1)
		{
			return "";
		}
		return filePath.substring(0, filePosi);
	}

	/**
	 * Extract the filename extension from the given path, e.g. "mypath/myfile.txt" -> "txt".
	 *
	 * @param path
	 *           the file path (may be {@code null})
	 * @return the extracted filename extension, or {@code null} if none
	 */
	public static String getFilenameExtension(final String path)
	{
		if (path == null)
		{
			return null;
		}

		final int extIndex = path.lastIndexOf(FILE_EXTENSION_SEPARATOR);
		if (extIndex == -1)
		{
			return null;
		}

		final int folderIndex = path.lastIndexOf(File.separator);
		if (folderIndex > extIndex)
		{
			return null;
		}

		return path.substring(extIndex + 1);
	}

	/**
	 * 根据文件路径循环创建文件的文件夹<br/>
	 * <br/>
	 * <strong>注意：</strong><br/>
	 * makeFolder("C:\\Users\\Trinea")仅能创建Users文件夹, makeFolder("C:\\Users\\Trinea\\")才能创建到Trinea文件夹
	 *
	 * @param filePath
	 *           文件路径
	 * @return 是否成功创建文件夹，若文件夹已存在，返回true
	 *         <ul>
	 *         <li>若{@link FileUtil#getFolderName(String)}返回为空，返回false;</li>
	 *         <li>若文件夹存在，返回true</li>
	 *         <li>否则返回是否成功创建文件夹{/li>
	 *         </ul>
	 */
	public static boolean makeFolder(final String filePath)
	{
		final String folderName = getFolderName(filePath);
		if (StringUtil.isEmpty(folderName))
		{
			return false;
		}

		final File folder = new File(folderName);
		return (folder.exists() && folder.isDirectory()) ? true : folder.mkdirs();
	}

	/**
	 * 判断文件是否存在
	 *
	 * @param filePath
	 *           文件路径
	 * @return 存在返回true，否则返回false
	 */
	public static boolean isFileExist(final String filePath)
	{
		if (StringUtil.isEmpty(filePath))
		{
			return false;
		}

		final File file = new File(filePath);
		return (file.exists() && file.isFile());
	}

	/**
	 * 判断文件夹是否存在
	 *
	 * @param directoryPath
	 *           文件夹路径
	 * @return 存在返回true，否则返回false
	 */
	public static boolean isFolderExist(final String directoryPath)
	{
		if (StringUtil.isEmpty(directoryPath))
		{
			return false;
		}

		final File dire = new File(directoryPath);
		return (dire.exists() && dire.isDirectory());
	}

	/**
	 * 删除文件或文件夹
	 * <ul>
	 * <li>路径为null或空字符串，返回true</li>
	 * <li>路径不存在，返回true</li>
	 * <li>路径存在并且为文件或文件夹，返回{@link File#delete()}，否则返回false</li>
	 * <ul>
	 *
	 * @param path
	 *           路径
	 * @return 是否删除成功
	 */
	public static boolean deleteFile(final String path)
	{
		if (StringUtil.isEmpty(path))
		{
			return true;
		}

		final File file = new File(path);
		if (file.exists())
		{
			if (file.isFile())
			{
				return file.delete();
			}
			else if (file.isDirectory())
			{
				final File[] listFiles = file.listFiles();
				if (listFiles != null && listFiles.length > 0)
				{
					for (final File f : listFiles)
					{
						if (f.isFile())
						{
							f.delete();
						}
						else if (f.isDirectory())
						{
							deleteFile(f.getAbsolutePath());
						}
					}
				}
				return file.delete();
			}
			return false;
		}
		return true;
	}

	/**
	 * 得到文件大小
	 * <ul>
	 * <li>路径为null或空字符串，返回-1</li>
	 * <li>路径存在并且为文件，返回文件大小，否则返回-1</li>
	 * <ul>
	 *
	 * @param path
	 *           路径
	 * @return size of file
	 */
	public static long getFileSize(final String path)
	{
		if (StringUtil.isEmpty(path))
		{
			return -1;
		}
		final File file = new File(path);
		return (file.exists() && file.isFile() ? file.length() : -1);
	}

	/**
	 * 读文件
	 *
	 * @param filePath
	 *           文件路径
	 * @return 若文件路径所在文件不存在返回null，否则返回文件内容
	 */
	public static StringBuilder readFileToStrBuilder(final String filePath)
	{
		final File file = new File(filePath);
		final StringBuilder fileContent = new StringBuilder("");
		if (file != null && file.isFile())
		{
			BufferedReader reader = null;
			try
			{
				reader = new BufferedReader(new FileReader(file));
				String line = null;
				while ((line = reader.readLine()) != null)
				{
					if (!fileContent.toString().equals(""))
					{
						fileContent.append("\r\n");
					}
					fileContent.append(line);
				}
				reader.close();
				return fileContent;
			}
			catch (final IOException e)
			{
				throw new RuntimeException("IOException occurred. ", e);
			}
			finally
			{
				if (reader != null)
				{
					try
					{
						reader.close();
					}
					catch (final IOException e)
					{
						throw new RuntimeException("IOException occurred. ", e);
					}
				}
			}
		}
		return null;
	}

	/**
	 * 将文件转成base64 字符串
	 * @param path
	 * @return base64 字符串
	 * @throws FileNotFoundException 
	 */
	public static String encodeBase64File(final String path) throws FileNotFoundException
	{
		if (StringUtil.isEmpty(path))
		{
			throw new FileNotFoundException(path);
		}

		final StringBuilder builder = new StringBuilder();
		InputStreamReader in = null;
		BufferedReader br = null;

		byte[] data = null;
		String line = null;

		try
		{
			in = new InputStreamReader(new FileInputStream(path),StandardCharsets.UTF_8);
			br = new BufferedReader(in);
			while ((line = br.readLine()) != null) 
			{
				builder.append(line);
			}
			data = builder.toString().getBytes(StandardCharsets.UTF_8);
		}
		catch (final IOException e)
		{
			logger.error("文件转base64出现异常:{}", e.getMessage(), e);
		}
		finally
		{
			IOUtil.closeQuietly(in);
			IOUtil.closeQuietly(br);
		}

		return Base64Encoder.encode(data);
	}

	/**
	 * 将base64字符解码保存文件
	 *
	 * @param base64Code
	 * @param targetPath
	 * @return 保存成功返回true，失败false
	 */
	public static boolean decoderBase64File(final String base64Code, final String targetPath)
	{
		if (base64Code == null)
		{
			return false;
		}
		
		final byte[] data = Base64Decoder.decode(base64Code);
		for (int i = 0, len = data.length; i < len; ++i)
			{
				{//调整异常数据
					if (data[i] < 0)
					{
						data[i] += 256;
					}
				}
			}
		try(final FileOutputStream out = new FileOutputStream(targetPath))
		{
			out.write(data);
			out.flush();
			return true;
		}
		catch (final Exception e)
		{
			logger.error("base64字符解码保存文件出现异常:{}", e.getMessage(), e);
			return false;
		}
	}
}
