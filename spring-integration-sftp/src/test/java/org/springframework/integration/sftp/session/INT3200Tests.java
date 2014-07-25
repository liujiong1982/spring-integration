/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.integration.sftp.session;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.sshd.SshServer;
import org.apache.sshd.server.PasswordAuthenticator;
import org.apache.sshd.server.keyprovider.SimpleGeneratorHostKeyProvider;
import org.apache.sshd.server.session.ServerSession;
import org.junit.Test;

import org.springframework.core.io.UrlResource;
import org.springframework.integration.file.remote.session.Session;

import com.jcraft.jsch.ChannelSftp.LsEntry;

/**
 * @author Gary Russell
 * @since 3.0.2
 *
 */
public class INT3200Tests {

	/*
	 * Verify the socket is closed if the channel.connect() fails.
	 */
	@Test
	public void testConnectFailSocketOpen() throws Exception {
		final int port = 2222;
		SshServer server = SshServer.setUpDefaultServer();
		try {
			server.setPasswordAuthenticator(new PasswordAuthenticator() {

				@Override
				public boolean authenticate(String arg0, String arg1, ServerSession arg2) {
					return true;
				}
			});
			server.setPort(port);
			server.setKeyPairProvider(new SimpleGeneratorHostKeyProvider("hostkey.ser"));
//			List<DefaultSftpSessionFactory> list=new ArrayList<DefaultSftpSessionFactory>();
			
			DefaultSftpSessionFactory f = new DefaultSftpSessionFactory();
//			list.add(f);
//			server.setServiceFactories(list);
			server.start();
			
			
			
			

//			 JSch jsch = new JSch();
//
//		        Session session = jsch.getSession( "root", "localhost", 2222 );
//		        java.util.Properties config = new java.util.Properties();
//		        config.put("StrictHostKeyChecking", "no");
//		        config.put("PreferredAuthentications","password");
//		        session.setConfig(config);
//
//		        session.setPassword( "1234" );
//		        session.connect( 60000 );
//		        com.jcraft.jsch.Channel channel = session.openChannel("sftp");
//		        ChannelSftp sftp = ( ChannelSftp ) channel;
//		        sftp.connect( 60000 );
//		        System.out.println("bağlantı sağlandı");
//		        sftp.disconnect();
//		        System.out.println("bağlantı sonlandı");
			

			DefaultSftpSessionFactory f = new DefaultSftpSessionFactory();
			f.setHost("localhost");
			f.setPort(2222);
			f.setUser("user");
			f.setPrivateKeyPassphrase("pass");
			f.setPassword("hello");
			URL uri=INT3200Tests.class.getResource("sftp_rsa");
			f.setPrivateKey(new UrlResource(this.getClass().getResource("sftp_rsa")));
			Session<LsEntry> session = f.getSession();
			System.out.println("--------------------------------------------------------");
			System.out.println(session);
//			int n = 0;
//			while (true) {
//				try {
//					f.getSession();
//					fail("Expected Exception");
//				}
//				catch (Exception e) {
//					if (e instanceof IllegalStateException && "failed to create SFTP Session".equals(e.getMessage())) {
//						if (e.getCause() instanceof IllegalStateException) {
//							if (e.getCause().getCause() instanceof JSchException) {
//								if (e.getCause().getCause().getCause() instanceof ConnectException) {
//									assertTrue("Server failed to start in 10 seconds", n++ < 100);
//									Thread.sleep(100);
//									continue;
//								}
//							}
//						}
//					}
//					assertThat(e, instanceOf(IllegalStateException.class));
//					assertThat(e.getCause(), instanceOf(IllegalStateException.class));
//					assertThat(e.getCause().getMessage(), equalTo("failed to connect"));
//					break;
//				}
//			}
//
//			n = 0;
//			while (n++ < 100 && server.getActiveSessions().size() > 0) {
//				Thread.sleep(100);
//			}
//
//			assertEquals(0, server.getActiveSessions().size());
		}
		finally {
			server.stop(true);
		}
	}

}
