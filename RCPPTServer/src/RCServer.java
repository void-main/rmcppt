
import java.awt.AWTException;
import java.awt.Canvas;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.DataInputStream;//引入相关类
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.awt.Graphics;

public class RCServer{
	
	public static void main(String[] args){//主方法
		ServerSocket ss = null;//ServerSocket的引用
		Socket s = null;//Socket的引用
		DataInputStream din = null;
		DataOutputStream dout = null;
		Robot robot = null;
		
		int mouseX = 640;
	    int mouseY = 400;
	    final int mouseRangeW = 1280;
	    final int mouseRangeH = 800;
	    
		try {
			robot = new Robot();
		} catch (AWTException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try{
			ss = new ServerSocket(30000);//监听到8888端口
			System.out.println("已监听到30000端口！");
			mouseX %= mouseRangeW;
            mouseY %= mouseRangeH;
			robot.mouseMove(mouseX, mouseY);
		}
		catch(Exception e){
			e.printStackTrace();//打印异常信息
		}
		while(true){
			try{
				s = ss.accept();//等待客户端连接
				System.out.println("ip: " + s.getInetAddress());//打印客户端IP
				System.out.println("以下是客户端传来的数据信息:\n");
//				din = new DataInputStream(s.getInputStream());
//				dout = new DataOutputStream(s.getOutputStream());//得到输入输出流
				BufferedReader br = new BufferedReader(
						new InputStreamReader(
								s.getInputStream()));
				InputStream in = s.getInputStream();
				
//				int n = 0; 
//	            byte[] buf = new byte[1024]; 
//	            String message = ""; 
//	            while((n = in.read(buf)) > 0) { 
//	                message += new String(buf, 0, n); 
//	                System.out.println("receive:" + message); 
//	            } 
				
				String msg;
				String[] tmp;
				int xv, yv;
				while ((msg = br.readLine()) != null) {
					
					System.out.println("msg: " + msg);//打印客户端发来的消息
					System.out.println("====================");
					if (msg.equals(Messages.PageUp)) {
						robot.keyPress(KeyEvent.VK_P);
						robot.keyRelease(KeyEvent.VK_P);
					} else if (msg.equals(Messages.PageDown)) {
						robot.keyPress(KeyEvent.VK_N);
						robot.keyRelease(KeyEvent.VK_N);
					} else {
						tmp = msg.split("/");
						xv = Integer.parseInt(tmp[0]);
						yv = Integer.parseInt(tmp[1]);
						if (xv > 3) {
							mouseX -= 3;
						} else if (xv < -3) {
							mouseX += 3;
						}
						
						if (yv > 4) {
							mouseY -= 3;
						} else if (yv < 0) {
							mouseY += 3;
						}
						
//						mouseX -= xv;
//						mouseY -= yv;
						if(mouseX >= mouseRangeW)
							mouseX = mouseRangeW;
						if(mouseY >= mouseRangeH)
							mouseY = mouseRangeH;
//						mouseX %= mouseRangeW;
//		                mouseY %= mouseRangeH;
						robot.mouseMove(mouseX, mouseY);
						
					}
				};
			}
			catch(Exception e){
				e.printStackTrace();//打印异常信息
			}	
			finally{
				try{
					if(dout != null){
						dout.close();//关闭输出流
					}
					if(din != null){
						din.close();//关闭输入流
					}
					if(s != null){
						s.close();//关闭Socket连接
					}
				}
				catch(Exception e){
					e.printStackTrace();//打印异常信息
				}
			}
		}
	}
}