
package myjava.test;

import com.sshtools.j2ssh.SshClient;
import com.sshtools.j2ssh.authentication.AuthenticationProtocolState;
import com.sshtools.j2ssh.authentication.PasswordAuthenticationClient;
import com.sshtools.j2ssh.connection.ChannelOutputStream;
import com.sshtools.j2ssh.session.SessionChannelClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RemoteExecution {

	SessionChannelClient sessionSsh = null;
	
	/**
	 * 连接方式分发
	 * 字符串数组参数顺序：0连接方式、1脚本路径、2脚本库名称、3脚本库服务器IP、4端口、5用户名、6密码
	 */
	public String init(String [] strArr) {
		String connect = strArr[0];

		//根据数组第一个参数判断是登录还是执行
		if(connect.equals("SSH")){
			return SSHSendCommand(strArr);
		}else if (connect.equals("TELNET")){
//			return TELNETSendCommand(StrArr);
		}
		return null;
	}

	/**
	 * SSH执行命令
	 */
	public String SSHSendCommand(String [] strArr) {
		String line = "";
		String lines = "";
		try {
			System.out.println("SSHSendCommand");
			String scriptLib = strArr[1]+"\n";
			String hostIp = strArr[3];
			Integer port = Integer.parseInt(strArr[4]);
			String userName = strArr[5];
			String password = strArr[6];
			SSHLogin(hostIp,userName,password,port);
			SessionChannelClient sessionSsh = this.sessionSsh ;
			if (sessionSsh.startShell()){
				
				//向远程机执行指令
				ChannelOutputStream sshWriter = sessionSsh.getOutputStream();
				String command = "sh " + scriptLib;
				sshWriter.write(command.getBytes());
				sshWriter.flush();
				
				//返回执行结果
				BufferedReader in = new BufferedReader(new InputStreamReader(sessionSsh.getInputStream()));    
				BufferedReader err= new BufferedReader(new InputStreamReader(sessionSsh.getStderrInputStream()));    
				while ((line = in.readLine()) != null){   
					lines += line + "\n";
				}
				while ((line = err.readLine()) != null){    
					lines += line + "\n";     
				}
				if (sessionSsh != null) {
					sessionSsh.close();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lines;
	}
	
	/**
	 *	SSH远程登陆 
	 */
	public void SSHLogin(String hostIp,String username,String password,int port){
		SshClient client = new SshClient();
		try {    
			ConsoleKnownHostsKeyVerification console = new ConsoleKnownHostsKeyVerification();  
			client.connect(hostIp, port,console);
			//设置用户名和密码    
			PasswordAuthenticationClient pwd = new PasswordAuthenticationClient();    
			pwd.setUsername(username);    
			pwd.setPassword(password);    
			int result = client.authenticate(pwd);    
			
			//如果连接完成
			if (result == AuthenticationProtocolState.COMPLETE) {    
				SessionChannelClient sessionSsh = client.openSessionChannel();
				this.sessionSsh = sessionSsh;
				
			}
			
		} catch (IOException e) {    
			e.printStackTrace();    
		}
	}
	
	/**
	 * 连接方式分发
	 * 字符串数组参数顺序：0连接方式、1脚本路径、2脚本库名称、3脚本库服务器IP、4端口、5用户名、6密码
	 */
	public static void main(String[] args) {
		RemoteExecution remoteExecution = new RemoteExecution();
		String [] strArr = new String[]{"SSH"," /home/yingtaobook/start.sh","","101.200.175.148","22","root","gaige967326"};
		remoteExecution.init(strArr);
	}
	
	
}
