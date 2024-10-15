package main.java;


import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;

import lombok.extern.slf4j.Slf4j;


/**
 * Test
 *
 * @author: GJK
 * @date: 2022/6/23 16:25
 * @description:
 */
@Slf4j
public class Test {


    public static void main(String[] args) throws SftpException {
        String host = "180.169.95.129";
        String user = "LZNYTZ_fan";
        String password = "j3i6!E9X";
        int port = 22;
        ChannelSftp sftp = SftpClientUtils.connectSftpServer(host, port, user, password);

        // 切换到指定目录，不存在就创建
        SftpClientUtils.toPathOrCreateDir(sftp, "/upload/20231015");

        SftpClientUtils.sftpDisconnect(sftp);

    }


}



