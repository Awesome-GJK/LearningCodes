package main.java;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: gaojiankang
 * @Desc:
 * @create: 2024-10-12 11:12
 **/
@Slf4j
public class SftpClientUtils {

    public static final int PERMISSIONS = 0775;

    private SftpClientUtils() {
    }

    public static void uploadFileToSftp(
            InputStream inputStream,
            String fileName,
            String filePath,
            String host, int port, String username, String pwd) {

        ChannelSftp sftp = connectSftpServer(host, port, username, pwd);
        try {
            // 切换到指定目录，不存在就创建
            toPathOrCreateDir(sftp, filePath);

            // 上传文件
            sftp.put(inputStream, fileName);

            // 关闭输入流
            inputStream.close();
        } catch (SftpException | IOException e) {
            throw new RuntimeException("SFTP上传文件过程出现异常", e);
        } finally {
            sftpDisconnect(sftp);
        }
    }

    public static void downloadFileFromSftp(
            String fileName,
            String localFilePath,
            String remoteFilePath,
            String host, int port, String username, String pwd) {

        ChannelSftp sftp = connectSftpServer(host, port, username, pwd);
        try {
            Path path = Paths.get(localFilePath + fileName);

            // 如果父目录不存在，则创建
            if (Files.notExists(path.getParent())) {
                Files.createDirectories(path.getParent());
            }

            // 如果文件不存在，则创建文件
            if (Files.notExists(path)) {
                Files.createFile(path);
            }

            // 下载文件
            OutputStream outputStream = Files.newOutputStream(path);
            sftp.get(remoteFilePath + "/" + fileName, outputStream);

            outputStream.close();
        } catch (SftpException | IOException e) {
            throw new RuntimeException("SFTP下载文件过程出现异常", e);
        } finally {
            sftpDisconnect(sftp);
        }
    }

    public static ChannelSftp connectSftpServer(String host, int port, String username, String pwd) {
        JSch jsch = new JSch();
        Session session;
        ChannelSftp sftpChannel = null;
        try {
            // 创建Session
            session = jsch.getSession(username, host, port);
            session.setPassword(pwd);

            // 跳过主机密钥检查
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);

            // 连接Session
            session.connect();

            // 设置会话超时时间为30秒
            session.setTimeout(60000);

            // 打开SFTP通道
            sftpChannel = (ChannelSftp) session.openChannel("sftp");
            sftpChannel.connect();
        } catch (Exception e) {
            throw new RuntimeException("连接SFTP服务器失败", e);
        }
        return sftpChannel;
    }

    public static void toPathOrCreateDir(ChannelSftp sftp, String filePath) throws SftpException {
        String[] dirs = filePath.split("/");
        for (String dir : dirs) {
            if (dir.isEmpty()) {
                continue;
            }
            try {
                sftp.cd(dir);
            } catch (SftpException e) {
                sftp.mkdir(dir);
                sftp.chmod(PERMISSIONS, dir);
                sftp.cd(dir);
            }
        }
    }

    public static void sftpDisconnect(ChannelSftp sftp) {
        if (sftp != null) {
            try {
                sftp.exit();
                sftp.getSession().disconnect();
            } catch (Exception e) {
                System.err.println("断开SFTP连接失败: " + e.getMessage());
            }
        }
    }
}
