/****************************************************************************
Copyright (c) 2008-2010 Ricardo Quesada
Copyright (c) 2010-2012 cocos2d-x.org
Copyright (c) 2011      Zynga Inc.
Copyright (c) 2013-2014 Chukong Technologies Inc.
 
http://www.cocos2d-x.org

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
****************************************************************************/
package org.cocos2dx.cpp;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.example.cocos2dx.iapppay.R;
import com.iapppay.interfaces.callback.IPayResultCallback;
import com.iapppay.sdk.main.IAppPay;
import com.iapppay.sdk.main.IAppPayOrderUtils;

import org.cocos2dx.lib.Cocos2dxActivity;

public class AppActivity extends Cocos2dxActivity {
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//妯睆锛氭牴鎹紶鎰熷櫒妯悜鍒囨崲


        /**
         * SDK鍒濆鍖? 锛岃鏀惧湪娓告垙鍚姩鐣岄潰
         */
        IAppPay.init(AppActivity.this, IAppPay.PORTRAIT, PayConfig.appid);//鎺ュ叆鏃讹紒涓嶈浣跨敤Demo涓殑appid
    }
    
    private static AppActivity instance = new AppActivity();
    public static Object getObj()
    {
        return instance;
    }

    public void onPay() {
        android.util.Log.d("JAVA", "初始化");
        new Handler(Looper.getMainLooper()).post(new Runnable() {

        	@Override
        	public void run() {
        		
		        String cporderid = System.currentTimeMillis()   + "";
		        String param = getTransdata("userid001", "cpprivateinfo123456" , 6 , 0.01f , cporderid);
		        IAppPay.startPay((Activity) AppActivity.getContext(), param, iPayResultCallback);


        	}
        });

    }

    /**
     * 鏀粯缁撴灉鍥炶皟
     */
    IPayResultCallback iPayResultCallback = new IPayResultCallback() {

        @Override
        public void onPayResult(int resultCode, String signvalue, String resultInfo) {
            // TODO Auto-generated method stub
            switch (resultCode) {
                case IAppPay.PAY_SUCCESS:
                    //璋冪敤 IAppPayOrderUtils 鐨勯獙绛炬柟娉曡繘琛屾敮浠樼粨鏋滈獙璇?
                    boolean payState = IAppPayOrderUtils.checkPayResult(signvalue, PayConfig.publicKey);
                    if(payState){
                        Toast.makeText(AppActivity.getContext(), "鏀粯鎴愬姛", Toast.LENGTH_LONG).show();
                    }
                    break;
                default:
                    Toast.makeText(AppActivity.getContext(), resultInfo, Toast.LENGTH_LONG).show();
                    break;
            }
            Log.d("MainDemoActivity", "requestCode:" + resultCode + ",signvalue:" + signvalue + ",resultInfo:" + resultInfo);
        }
    };


    /** 鑾峰彇鏀堕摱鍙板弬鏁? */
    private String getTransdata( String appuserid, String cpprivateinfo, int waresid, float price, String cporderid) {
        //璋冪敤 IAppPayOrderUtils getTransdata() 鑾峰彇鏀粯鍙傛暟
        IAppPayOrderUtils orderUtils = new IAppPayOrderUtils();
        orderUtils.setAppid(PayConfig.appid);
        orderUtils.setWaresid(waresid);//浼犲叆鎮ㄥ晢鎴峰悗鍙板垱寤虹殑鍟嗗搧缂栧彿
        orderUtils.setCporderid(cporderid);
        orderUtils.setAppuserid(appuserid);
        orderUtils.setPrice(price);//鍗曚綅 鍏?
        orderUtils.setWaresname("鑷畾涔夊悕绉?");//寮?鏀句环鏍煎悕绉?(鐢ㄦ埛鍙嚜瀹氫箟锛屽鏋滀笉浼犱互鍚庡彴閰嶇疆涓哄噯)
        orderUtils.setCpprivateinfo(cpprivateinfo);
        return orderUtils.getTransdata(PayConfig.privateKey);
    }


}
