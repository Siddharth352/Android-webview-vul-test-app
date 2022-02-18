package com.tmh.vulnwebview;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.webkit.ConsoleMessage;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class RegistrationWebView extends AppCompatActivity  {
    private Context context;
    public RegistrationWebView(){

    }
    public RegistrationWebView(Context context){
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_web_view);
        setTitle("Registration page");
        loadWebView();
    }


    private void loadWebView() {

        WebView webView = findViewById(R.id.webview);
        webView.setWebViewClient(new SSLhandlerWebViewClient());
//        webView.setWebChromeClient(new WebChromeClient() {
//            @Override
//            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
//                Log.d("MyApplication", consoleMessage.message() + " -- From line " +
//                        consoleMessage.lineNumber() + " of " + consoleMessage.sourceId());
//                return true;
//            }
//        });

//        webView.setWebViewClient(new WebViewClient());

        //Allows cross-origin requests from file:// scheme to access content from any origin
        webView.getSettings().setAllowUniversalAccessFromFileURLs(true);

        //Enabling javascript
        webView.getSettings().setJavaScriptEnabled(true);
        try{
            if (getIntent().getExtras().getBoolean("is_reg", false)) {
                webView.loadUrl("file:///android_asset/registration.html");
            } else {
                webView.loadUrl(getIntent().getStringExtra("reg_url"));
                Log.e("tag","here you are");
            }
        }catch (Exception e){
            Log.e("tag","here i am");
        }




    }
}
class CustomWebviewclient extends WebViewClient {
    private  Context context;
    public CustomWebviewclient(Context context){
        this.context = context;
    }

    @Override
    public void onReceivedSslError(WebView view, final SslErrorHandler handler, SslError error) {
        // for SSLErrorHandler
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(R.string.notification_error_ssl_cert_invalid);
        builder.setPositiveButton("continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                handler.proceed();
            }
        });
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                handler.cancel();
            }
        });
        final AlertDialog dialog = builder.create();
        dialog.show();
    }
}