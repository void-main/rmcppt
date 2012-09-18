package com.zzs.rcpptclient;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

import android.os.Bundle;
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
						s = new Socket(ipString, 30000);//连接服务器
						s.setTcpNoDelay(true);
						os = s.getOutputStream();
						status.setText("connected");
					}
//					os = new BufferedOutputStream(s.getOutputStream(),128);//得到输出流
//					os = new DataOutputStream(s.getOutputStream());
				} catch (Exception e) {
						e.printStackTrace();//打印异常信息
						try {
							if(os != null){
								os.close();//关闭输出流
							}
							if(s != null){
								s.close();//关闭Socket连接
								status.setText("disconnected");
							}
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}					
					}
				} 
		});
        
        disconnect.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try{
					if(os != null){
						os.close();//关闭输入流
						os = null;
					}
					if(s != null){
						s.close();//关闭Socket连接
						s = null;
					}	
					status.setText("disconnected");
				}
				catch(Exception e){
					e.printStackTrace();//打印异常信息
				}
			}
		});
        
        pagedown.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					os.write("pd\n".getBytes("utf-8"));
					os.flush();
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
					os.write("pu\n".getBytes("utf-8"));
					os.flush();
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
