package demo01;

import com.jcraft.jsch.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ConnectionSSH {
    public static void main(String[] args) throws JSchException, IOException {
        JSch jsch = new JSch();
        String privateKey = "serverKey";
        jsch.addIdentity(privateKey);

        String username = "root";
        String host = "119.23.252.45";
        Session session=jsch.getSession(username, host, 22);
        session.setConfig("StrictHostKeyChecking", "no"); //跳过检测，否则会报错，说找不到主机。
        session.connect();

        String command = "/root/test.sh"; //脚本路径
        ChannelExec channel=(ChannelExec)session.openChannel("exec"); //运行脚本方式
        channel.setCommand(command);

//        Channel channel=session.openChannel("shell");  //键盘输入命令方式
//        channel.setInputStream(System.in);
//        channel.setOutputStream(System.out);

        BufferedReader in = new BufferedReader(new InputStreamReader(channel.getInputStream()));

        channel.connect();

        String msg;

        while((msg = in.readLine()) != null){
            System.out.println(msg);
        }
        channel.disconnect();
        session.disconnect();


    }
}
