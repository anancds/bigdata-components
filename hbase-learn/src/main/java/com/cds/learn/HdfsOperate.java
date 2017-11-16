package com.cds.learn;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.util.Bytes;

import org.apache.hadoop.fs.*;
import org.apache.hadoop.fs.permission.FsPermission;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.*;
import java.net.URI;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author guofeng3 Date: 2014/11/27 Time: 14:59
 */
public class HdfsOperate {

  static final Configuration conf = new Configuration();

  static {
    conf.setBoolean("fs.hdfs.impl.disable.cache", true);
  }
//    public static String basePath = "hdfs://hdh140:8020";


  public static void writeFile(String root, String currentPath, byte[] data, String basePath)
      throws Exception {
    FileSystem fileSystem = null;
    FSDataOutputStream fs = null;
    try {
      FileSystem.setDefaultUri(conf, new URI(basePath));
      fileSystem = FileSystem.get(conf);
      Path fsPath = new Path(root);

      if (!fileSystem.exists(fsPath)) {
        fileSystem.mkdirs(fsPath);
      }

      Path filePath = new Path(root + "/" + currentPath);
      fs = fileSystem.create(filePath, true);
      fs.write(data);
      fs.hsync();
      // 十进制511 == 八进制777
      fileSystem.setPermission(filePath, new FsPermission((short) 511));
    } finally {
      try {
        if (null != fs) {
          fs.close();
        }
        if (null != fileSystem) {
          fileSystem.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }

    }
  }

  public static byte[] readFile(String path, String basePath) throws Exception {
    FileSystem fileSystem = null;
    FSDataInputStream fs = null;
    try {
      FileSystem.setDefaultUri(conf, new URI(basePath));
      fileSystem = FileSystem.get(conf);
      Path fsPath = new Path(path);

      if (!fileSystem.exists(fsPath)) {
        throw new Exception("Path:" + path + " not exist");
      }
      fs = fileSystem.open(fsPath);
      int fileLen = (int) fileSystem.getFileStatus(fsPath).getLen();
      byte[] data = new byte[fileLen];
      int dataLength = fs.read(data);
      byte[] schemaData = new byte[dataLength];
      System.arraycopy(data, 0, schemaData, 0, dataLength);
      return schemaData;

    } finally {
      try {
        if (null != fs) {
          fs.close();
        }
        if (null != fileSystem) {
          fileSystem.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }

    }
  }

  //读取文件全部内容
  public static byte[] readFullFile(String path, String basePath) throws Exception {
    FileSystem fileSystem = null;
    FSDataInputStream fs = null;
    try {
      FileSystem.setDefaultUri(conf, new URI(basePath));
      fileSystem = FileSystem.get(conf);
      Path fsPath = new Path(path);

      if (!fileSystem.exists(fsPath)) {
        throw new Exception("Path:" + path + " not exist");
      }
      fs = fileSystem.open(fsPath);
      int fileLen = (int) fileSystem.getFileStatus(fsPath).getLen();
      byte[] data = new byte[fileLen];
      fs.readFully(0, data);
      return data;
    } finally {
      try {
        if (null != fs) {
          fs.close();
        }
        if (null != fileSystem) {
          fileSystem.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }

    }
  }

  public static void deleteFile(String path, String basePath) throws Exception {
    FileSystem fileSystem = null;
    try {
      FileSystem.setDefaultUri(conf, new URI(basePath));
      fileSystem = FileSystem.get(conf);
      Path fsPath = new Path(path);
      if (fileSystem.exists(fsPath)) {
        fileSystem.delete(fsPath, true);
      }

    } finally {
      try {
        if (null != fileSystem) {
          fileSystem.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }

    }
  }

  public static void downloadFile(String hdfsFileWithPath, String basePath, String localPath)
      throws Exception {

    FileSystem localFS = null;
    FileSystem hadoopFS = null;
    try {
      FileSystem.setDefaultUri(conf, new URI(basePath));
      localFS = FileSystem.getLocal(conf);
      hadoopFS = FileSystem.get(conf);
      Path hdfsPath = new Path(hdfsFileWithPath);
      Path local = new Path(localPath);

      hadoopFS.copyToLocalFile(false, hdfsPath, local, true);
    } finally {
      try {
        if (null != hadoopFS) {
          hadoopFS.close();
        }
        if (null != localFS) {
          localFS.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }

    }
  }

  public static void uploadFile(String hdfsPathName, String basePath, String localFileWithPath)
      throws Exception {

    FileSystem localFS = null;
    FileSystem hadoopFS = null;
    try {
      FileSystem.setDefaultUri(conf, new URI(basePath));
      localFS = FileSystem.getLocal(conf);
      hadoopFS = FileSystem.get(conf);
      Path hdfsPath = new Path(hdfsPathName);
      Path localPath = new Path(localFileWithPath);

      if (!hadoopFS.exists(hdfsPath)) {
        hadoopFS.mkdirs(hdfsPath);
      }

      hadoopFS.copyFromLocalFile(false, true, localPath, hdfsPath);
    } finally {
      try {
        if (null != hadoopFS) {
          hadoopFS.close();
        }
        if (null != localFS) {
          localFS.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }

    }
  }

  /**
   * 仅将本地目录下的文件或子目录上传到指定目录
   *
   * @param hdfsPathName 上传到hdfs的路径
   * @param basePath hdfs地址
   * @param localFileWithPath 本地指定目录
   */
  public static void uploadFiles(String hdfsPathName, String basePath, String localFileWithPath)
      throws Exception {

    FileSystem hadoopFS = null;
    try {
      FileSystem.setDefaultUri(conf, new URI(basePath));
      hadoopFS = FileSystem.get(conf);
      Path hdfsPath = new Path(hdfsPathName);
      Path localPath;

      if (!hadoopFS.exists(hdfsPath)) {
        hadoopFS.mkdirs(hdfsPath);
      }

      File localDir = new File(localFileWithPath);
      String[] subRes = localDir.list();
      if (null == subRes) {
        return;
      }
      for (String file : subRes) {
        localPath = new Path(localFileWithPath + "/" + file);
        hadoopFS.copyFromLocalFile(false, true, localPath, hdfsPath);
      }
    } finally {
      try {
        if (null != hadoopFS) {
          hadoopFS.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }

    }
  }

  public static Set<String> traverseTable(String path, String basePath) throws Exception {
    FileSystem fileSystem = null;
    try {
      FileSystem.setDefaultUri(conf, new URI(basePath));
      fileSystem = FileSystem.get(conf);
      Path fsPath = new Path(path);

      FileStatus[] fileStatuses = fileSystem.listStatus(fsPath);

      Set<String> tableNames = new HashSet<>();

      for (FileStatus fs : fileStatuses) {
        tableNames.add(fs.getPath().getName());
      }

      return tableNames;
    } finally {
      try {
        if (null != fileSystem) {
          fileSystem.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public static void isExistOrCreate(String path, String basePath) throws Exception {
    FileSystem fileSystem = null;
    try {
      FileSystem.setDefaultUri(conf, new URI(basePath));
      fileSystem = FileSystem.get(conf);
      Path fsPath = new Path(path);

      if (!fileSystem.exists(fsPath)) {
        fileSystem.mkdirs(fsPath);
      }

      fileSystem.setPermission(fsPath, new FsPermission((short) 511));
    } finally {
      try {
        if (null != fileSystem) {
          fileSystem.close();
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  public static boolean isFileExists(String root, String currentPath, String basePath)
      throws Exception {
    FileSystem fileSystem = null;
    try {
      FileSystem.setDefaultUri(conf, new URI(basePath));
      fileSystem = FileSystem.get(conf);

      Path filePath = new Path(root + "/" + currentPath);

      return fileSystem.exists(filePath);
    } finally {
      try {
        if (null != fileSystem) {
          fileSystem.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }

    }
  }

  /**
   * @param path 文件夹路径
   * @param basePath hdfs原始路径
   * @return 返回二级文件下下的所有文件，形式为<一级文件夹名字， 二级文件名1，二级文件名2，···， 二级文件名n>
   */
  public static Map<String, String> listFiles(String path, String basePath) throws Exception {
    Set<String> folderNames = traverseTable(path, basePath);
    Map<String, String> folder2Files = new HashMap<>();
    for (String folderName : folderNames) {
      StringBuilder sFile = new StringBuilder();
      Set<String> files = traverseTable(path + "/" + folderName, basePath);
      for (String file : files) {
        sFile.append(file).append(",");
        //sFile += file + ",";
      }
      //如果文件夹中没有文件，则不加入map
      if (sFile.length() != 0) {
        folder2Files.put(folderName, sFile.substring(0, sFile.length() - 1));
      }
    }

    return folder2Files;

  }

  public static boolean appendToFile(String filePath, String basePath, String value)
      throws Exception {
    Configuration conf = new Configuration();
    conf.setBoolean("dfs.support.append", true);
    conf.set("dfs.client.block.write.replace-datanode-on-failure.policy", "NEVER");
    conf.set("dfs.client.block.write.replace-datanode-on-failure.enable", "true");
    FileSystem fileSystem = null;
    FSDataOutputStream outPutStream = null;
    try {
      FileSystem.setDefaultUri(conf, new URI(basePath));
      fileSystem = FileSystem.get(conf);
      outPutStream = fileSystem.append(new Path(filePath));
      outPutStream.write(Bytes.toBytes(value));
      outPutStream.flush();

    } finally {
      try {
        if (null != outPutStream) {
          outPutStream.close();
        }
        if (null != fileSystem) {
          fileSystem.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return true;
  }

  public static List<String> getFiles(String path, String basePath)
      throws IOException, URISyntaxException {
    FileSystem fileSystem = null;
    List<String> files = new ArrayList<>();
    try {
      FileSystem.setDefaultUri(conf, new URI(basePath));
      fileSystem = FileSystem.get(conf);
      Path fsPath = new Path(path);

      FileStatus[] fileStatuses = fileSystem.globStatus(fsPath);

      for (FileStatus fs : fileStatuses) {
        if (fileSystem.exists(fs.getPath())) {
          files.add(fs.getPath().getName());
        }
      }

      return files;
    } finally {
      try {
        if (null != fileSystem) {
          fileSystem.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * 仅将本地目录下的文件或子目录上传到指定目录
   *
   * @param hdfsPathName 上传到hdfs的路径
   * @param basePath hdfs地址
   * @param localFileWithPath 本地指定目录
   */
  public static void uploadFilesWithWildcard(String hdfsPathName, String basePath,
      String localFileWithPath, String wildcard) throws Exception {

    FileSystem hadoopFS = null;
    try {
      FileSystem.setDefaultUri(conf, new URI(basePath));
      hadoopFS = FileSystem.get(conf);
      Path hdfsPath = new Path(hdfsPathName);
      Path localPath;

      if (!hadoopFS.exists(hdfsPath)) {
        hadoopFS.mkdirs(hdfsPath);
      }

      File localDir = new File(localFileWithPath);
      String[] subRes = localDir.list();
      if (null == subRes) {
        return;
      }
      for (String file : subRes) {
        if (file.startsWith(wildcard)) {
          localPath = new Path(localFileWithPath + "/" + file);
          hadoopFS.copyFromLocalFile(false, true, localPath, hdfsPath);
        }
      }
    } finally {
      try {
        if (null != hadoopFS) {
          hadoopFS.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }

    }
  }

  public static void main(String[] args) throws Exception {
//    listFiles("/ude/resource/jars","hdfs://hbp212:8020");
    System.out.println(
        isFileExists("/ude", "/resource/jars/ude-base-2.1-RELEASE.jar", "hdfs://hbp212:8020"));
    List<String> files = getFiles("/ude/resource/jars/ude-*.bak", "hdfs://hbp212:8020");
    for (String file : files) {
      deleteFile("/ude/resource/jars/" + file, "hdfs://hbp212:8020");
    }
    uploadFilesWithWildcard("/ude/resource/jars", "hdfs://hbp212:8020", "E:\\svn\\ude\\ude\\package\\ude-2.1-RELEASE", "README");
  }
}

