
import java.awt.AWTException;
import java.awt.Canvas;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.DataInputStream;//���������
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.awt.Graphics;

public class RCServer{
	
	public static void main(String[] args){//������
		ServerSocket ss = null;//ServerSocket������
		Socket s = null;//Socket������
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
			ss = new ServerSocket(30000);//������8888�˿�
			System.out.println("�Ѽ�����30000�˿ڣ�");
			mouseX %= mouseRangeW;
            mouseY %= mouseRangeH;
			robot.mouseMove(mouseX, mouseY);
		}
		catch(Exception e){
			e.printStackTrace();//��ӡ�쳣��Ϣ
		}
		while(true){
			try{
				s = ss.accept();//�ȴ��ͻ�������
				System.out.println("ip: " + s.getInetAddress());//��ӡ�ͻ���IP
				System.out.println("�����ǿͻ��˴�����������Ϣ:\n");
//				din = new DataInputStream(s.getInputStream());
//				dout = new DataOutputStream(s.getOutputStream());//�õ����������
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
					
					System.out.println("msg: " + msg);//��ӡ�ͻ��˷�������Ϣ
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
				e.printStackTrace();//��ӡ�쳣��Ϣ
			}	
			finally{
				try{
					if(dout != null){
						dout.close();//�ر������
					}
					if(din != null){
						din.close();//�ر�������
					}
					if(s != null){
						s.close();//�ر�Socket����
					}
				}
				catch(Exception e){
					e.printStackTrace();//��ӡ�쳣��Ϣ
				}
			}
		}
	}
}