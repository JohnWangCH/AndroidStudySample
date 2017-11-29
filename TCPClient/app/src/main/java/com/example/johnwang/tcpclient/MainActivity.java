package com.example.johnwang.tcpclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    private Thread thread;                //執行緒
    private Socket clientSocket;        //客戶端的socket
    private BufferedWriter bw;            //取得網路輸出串流
    private BufferedReader br;            //取得網路輸入串流
    private String tmp;                    //做為接收時的緩存
    private JSONObject json_write,json_read;        //從java伺服器傳遞與接收資料的json

    private EditText editTxtMsgFromS, editTxtMsgToS;
    private Button btnSend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSend = (Button) findViewById(R.id.btnSend);
        editTxtMsgFromS = (EditText) findViewById(R.id.editTxtMsgFromS);
        editTxtMsgToS = (EditText) findViewById(R.id.editTxtMsgToS);
        //Connect to Server
        thread=new Thread(Connection);                //賦予執行緒工作
        thread.start();
        //Keep recieve data from Server
    }
    //連結socket伺服器做傳送與接收
    private Runnable Connection = new Runnable(){
        @Override
        public void run() {
            // TODO Auto-generated method stub
            try{
                // IP為Server端
                InetAddress serverIp = InetAddress.getByName("172.16.8.74");
                int serverPort = 4001;
                clientSocket = new Socket(serverIp, serverPort);
                //取得網路輸出串流
                bw = new BufferedWriter( new OutputStreamWriter(clientSocket.getOutputStream()));
                // 取得網路輸入串流
                br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                // 當連線後
                while (clientSocket.isConnected()) {
                    // 取得網路訊息
                    tmp = br.readLine();    //宣告一個緩衝,從br串流讀取值
                    // 如果不是空訊息
                    if(tmp!=null){
                        //將取到的String抓取{}範圍資料
                        //tmp=tmp.substring(tmp.indexOf("{"), tmp.lastIndexOf("}") + 1);
                        //json_read=new JSONObject(tmp);
                        //從java伺服器取得值後做拆解,可使用switch做不同動作的處理
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                editTxtMsgFromS.append(tmp + "\n");
                            }
                        });

                    }
                }
            }catch(Exception e){
                //當斷線時會跳到catch,可以在這裡寫上斷開連線後的處理
                e.printStackTrace();
                Log.e("text","Socket連線="+e.toString());
                finish();    //當斷線時自動關閉房間
            }
        }
    };
    @Override
    protected void onDestroy() {            //當銷毀該app時
        super.onDestroy();
        try {
            //json_write=new JSONObject();
            //json_write.put("action","離線");    //傳送離線動作給伺服器
            Log.i("text","onDestroy()="+"OK"+"\n");
            //寫入後送出
            bw.write("Stop"+"\n");
            bw.flush();
            //關閉輸出入串流後,關閉Socket
            //最近在小作品有發現close()這3個時,導致while (clientSocket.isConnected())這個迴圈內的區域錯誤
            //會跳出java.net.SocketException:Socket is closed錯誤,讓catch內的處理再重複執行,如有同樣問題的可以將下面這3行註解掉
            bw.close();
            br.close();
            clientSocket.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
           // e.printStackTrace();
            Log.e("text","onDestroy()="+e.toString());
        }
    }
    public void btnSend_onClick(View v)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    if(bw != null) {
                        bw.write(editTxtMsgToS.getText().toString() + "\n");
                        bw.flush();
                    }
                }
                catch (Exception e)
                {
                    Log.e("TcpClient", "" + e.toString());
                }

            }
        }).start();

    }

}
