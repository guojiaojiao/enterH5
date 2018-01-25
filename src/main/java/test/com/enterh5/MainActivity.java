package test.com.enterh5;

import android.annotation.SuppressLint;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    WebView webView;
    Button btn1;
    Button btn2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView= (WebView) findViewById(R.id.web);
        btn1= (Button) findViewById(R.id.btn1);
        btn2= (Button) findViewById(R.id.btn2);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.loadUrl("javascript:setRed()");
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.loadUrl("javascript:setColor('#00f','变了')");
            }
        });
        initWebView();
        webView.loadUrl("file:///android_asset/enterAndroid1.html"); //加载assets文件中的H5页面
    }

    @SuppressLint("JavascriptInterface")  //添加该字段
    private void initWebView(){
        WebSettings settings =  webView.getSettings();
        settings.setJavaScriptEnabled(true);  //设置运行使用JS
        ButtonClick click = new ButtonClick();
        //这里添加JS的交互事件，这样H5就可以调用原生的代码
        webView.addJavascriptInterface(click,click.toString());
    }

    class ButtonClick{

        //这是 button.click0() 的触发事件
        //H5调用方法：javascript:button.click0()
        @JavascriptInterface
        public void click0(){
            show("title","");
        }

        //这是 button.click0() 的触发事件，可以传递待参数
        //H5调用方法：javascript:button.click0('参数1','参数2')
        @JavascriptInterface
        public void click0(String data1,String data2){
            show(data1,data2);
        }


        @JavascriptInterface  //必须添加，这样才可以标志这个类的名称是 button
        public String toString(){
            return "button";
        }

        private void show(String title,String data){
            new AlertDialog.Builder(getWindow().getContext())
                    .setTitle(title)
                    .setMessage(data)
                    .setPositiveButton("确定",null)
                    .create().show();
        }
    }
}
