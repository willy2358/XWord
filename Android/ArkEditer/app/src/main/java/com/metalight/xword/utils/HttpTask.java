package com.metalight.xword.utils;

/**
 * Created by willy on 2016/5/13.
 */

import android.os.AsyncTask;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class HttpTask extends AsyncTask<String, Integer, String> {
    private static final String TAG = "HTTP_TASK";

    @Override
    protected String doInBackground(String... params) {
        // Performed on Background Thread
        String url = params[0];
        try {
            String json = new NetworkTool().getContentFromUrl(url);
            return json;
            //TestWebService1();
            //return  "";
        } catch (Exception e) {
            // TODO handle different exception cases
            Log.e(TAG, e.toString());
            e.printStackTrace();
            return null;
        }
    }

    private void TestWebService1() {
        String SERVICE_NS = "http://192.168.0.203:8733/";
        String SERVICE_URL = "http://192.168.0.203:8733/XWordService/DocPageFetchService/";
        String methodName = "GetData";
//创建httpTransportSE传输对象
        HttpTransportSE ht = new HttpTransportSE(SERVICE_URL);
        ht.debug = true;
//使用soap1.1协议创建Envelop对象
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
//实例化SoapObject对象
        SoapObject request = new SoapObject(SERVICE_NS, methodName);
/**
 * 设置参数，参数名不一定需要跟调用的服务器端的参数名相同，只需要对应的顺序相同即可
 * */
        request.addProperty("value", "111");
//将SoapObject对象设置为SoapSerializationEnvelope对象的传出SOAP消息
        envelope.bodyOut = request;
        try{
//调用webService
            ht.call(null, envelope);
//txt1.setText("看看"+envelope.getResponse());
            if(envelope.getResponse() != null){
                //txt2.setText("有返回");
                SoapObject result = (SoapObject) envelope.bodyIn;
                String name = result.getProperty(0).toString();
                //txt1.setText("返回值 = "+name);
            }else{
                //txt2.setText("无返回");
            }
        }catch (Exception e) {
            e.printStackTrace();
            Log.d(e.toString(), "OnClickTest: ");
        }
    }
    @Override
    protected void onPostExecute(String json) {
        // Done on UI Thread
        if (json != null && json != "") {
  /*          Log.d(TAG, "taskSuccessful");
            int i1 = json.indexOf("["), i2 = json.indexOf("{"), i = i1 > -1
                    && i1 < i2 ? i1 : i2;
            if (i > -1) {
                json = json.substring(i);*/
                taskHandler.taskSuccessful(json);
/*            } else {
                Log.d(TAG, "taskFailed");
                taskHandler.taskFailed();
            }*/
        } else {
            Log.d(TAG, "taskFailed");
            taskHandler.taskFailed();
        }
    }

    public static interface HttpTaskHandler {
        void taskSuccessful(String json);
        void taskFailed();
    }

    HttpTaskHandler taskHandler;

    public void setTaskHandler(HttpTaskHandler taskHandler) {
        this.taskHandler = taskHandler;
    }

}
