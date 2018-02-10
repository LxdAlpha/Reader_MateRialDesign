package com.example.alpha.reader_materialdesign;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alpha.reader_materialdesign.Domain.LoginMessage;
import com.example.alpha.reader_materialdesign.Domain.RegisterMessage;
import com.example.alpha.reader_materialdesign.Domain.User;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    Context context;
    EditText passWord;
    EditText againPassWord;
    EditText userName;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        button = findViewById(R.id.MyButton);
        context = this;
        passWord = findViewById(R.id.passWord);
        againPassWord = findViewById(R.id.againPassWord);
        userName = findViewById(R.id.userName);
        againPassWord.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String a = passWord.getText().toString();
                String b = againPassWord.getText().toString();
                if(!a.equals(b)){
                    againPassWord.setError("密码不一致");
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = new User();
                user.setName(userName.getText().toString());
                user.setPassword(passWord.getText().toString());
                new register().execute(user);
            }
        });
    }


    class register extends AsyncTask<User, Void, String> {

        @Override
        protected String doInBackground(User... users) {
            Map<String, String> parms = new HashMap<>();
            parms.put("user.username", users[0].getName());
            parms.put("user.password", users[0].getPassword());
            String result = null;
            try {
                result = sendPOSTRequest("http://192.168.0.104:8088/kevin/user_add.action", parms, "utf-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            RegisterMessage message = new Gson().fromJson(result, RegisterMessage.class);
            //Toast.makeText(context, message.getStatus() + " " + message.getLogin(), Toast.LENGTH_SHORT).show();
            if(message.getAdd().equals("0")){
                Toast.makeText(context, "注册失败", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(context, "注册成功", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }


    private  String sendPOSTRequest(String path, Map<String, String> params, String encoding) throws Exception{
        //  title=liming&timelength=90
        StringBuilder data = new StringBuilder();
        if(params!=null && !params.isEmpty()){
            for(Map.Entry<String, String> entry : params.entrySet()){
                data.append(entry.getKey()).append("=");
                data.append(URLEncoder.encode(entry.getValue(), encoding));
                data.append("&");
            }
            data.deleteCharAt(data.length() - 1);
        }
        byte[] entity = data.toString().getBytes();//生成实体数据
        HttpURLConnection conn = (HttpURLConnection) new URL(path).openConnection();
        conn.setConnectTimeout(5000);
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);//允许对外输出数据
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length", String.valueOf(entity.length));
        OutputStream outStream = conn.getOutputStream();
        outStream.write(entity);

        /*
        if(conn.getResponseCode() == 200){
            return true;
        }
        return false;
        */

        BufferedReader reader = null;
        String response="";
        reader = new BufferedReader(new InputStreamReader(
                conn.getInputStream()));
        String lines;
        while ((lines = reader.readLine()) != null) {
            lines = new String(lines.getBytes(), "utf-8");
            response+=lines;
        }


        conn.disconnect();
        if(reader!=null){
            reader.close();
        }if(outStream!=null){
            outStream.close();
        }

        return  response;
    }
}
