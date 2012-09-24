package com.zzs.rcpptclient;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

import com.zzs.comm.CommHandler;
import com.zzs.comm.InetHandler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	public static final String TAG = "debug";
	TextView status;
	EditText ipText;
	
	Button connect,disconnect,pagedown,pageup;
	Socket s = null;
	OutputStream os = null;
	String ipString = null;
	
	
	//传输通信的handler
    public static CommHandler commHandler = null;
    public static InetHandler inetHandler = null;
    
    // Handler message types:
    public static final int MESSAGE_CONNECTED = 600;
    public static final int MESSAGE_DISCONNECTED = MESSAGE_CONNECTED + 1;
    public static final int MESSAGE_ENCODE_FINISHED = MESSAGE_CONNECTED + 2;
    public static final int MESSAGE_ENCODE_ERROR = MESSAGE_CONNECTED + 3;
    public static final int MESSAGE_SHOW_COULD_NOT_CONECT_MSG = MESSAGE_CONNECTED + 4;
    
    // The Handler that gets information back from the other threads
    private final Handler msgHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            switch(msg.what)
            {
            case MESSAGE_CONNECTED:
            	status.setText("connected");
                Log.i(TAG, "CONNECTED");
                break;

            case MESSAGE_DISCONNECTED:
                Log.i(TAG, "DISCONNECTED");
                status.setText("disconnected");
                break;

            case MESSAGE_ENCODE_FINISHED:
                break;

            case MESSAGE_ENCODE_ERROR:
                Log.e(TAG, "Encode error");
                break;

            case MESSAGE_SHOW_COULD_NOT_CONECT_MSG:
                break;

            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        ipText = (EditText)findViewById(R.id.ip);
        
        status = (TextView)findViewById(R.id.showstatus);
        
        connect = (Button)findViewById(R.id.connect);
        disconnect = (Button)findViewById(R.id.disconnect);
        pagedown = (Button)findViewById(R.id.pagedown);
        pageup = (Button)findViewById(R.id.pageup);
        
        
        connect.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					status.setText("connecting..");
					
					ipString = ipText.getText().toString(); 
					
					if(ipString.equals(""))
						status.setText("please input ip..");
					else{
						ipText.setText("10.0.0.90");
						String[] inetServerAddrAndPort = new String[] {ipString, new String("30000")};
						
						inetHandler = new InetHandler(getApplicationContext(), msgHandler);
						commHandler = inetHandler;   //这么写是为了方便蓝牙扩展
						
						commHandler.connect(inetServerAddrAndPort);
//						s = new Socket(ipString, 30000);//���ӷ�����
//						s.setTcpNoDelay(true);
//						os = s.getOutputStream();
						
					}
//					os = new BufferedOutputStream(s.getOutputStream(),128);//�õ������
//					os = new DataOutputStream(s.getOutputStream());
				} catch (Exception e) {
						e.printStackTrace();//��ӡ�쳣��Ϣ
//						try {
//							if(os != null){
//								os.close();//�ر������
//							}
//							if(s != null){
//								s.close();//�ر�Socket����
//								status.setText("disconnected");
//							}
//						} catch (IOException e1) {
//							// TODO Auto-generated catch block
//							e1.printStackTrace();
//						}					
					}
				} 
		});
        
        disconnect.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try{
//					if(os != null){
//						os.close();//�ر�������
//						os = null;
//					}
//					if(s != null){
//						s.close();//�ر�Socket����
//						s = null;
//					}	
					commHandler.disconnect();
				}
				catch(Exception e){
					e.printStackTrace();//��ӡ�쳣��Ϣ
				}
			}
		});
        
        pagedown.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
//					os.write("pd\n".getBytes("utf-8"));
//					os.flush();
					commHandler.sendQ("pd\n".getBytes("utf-8"));
					status.setText("pagedown");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
        
        pageup.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
//					os.write("pu\n".getBytes("utf-8"));
//					os.flush();
					commHandler.sendQ("pu\n".getBytes("utf-8"));
					status.setText("pageup");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
