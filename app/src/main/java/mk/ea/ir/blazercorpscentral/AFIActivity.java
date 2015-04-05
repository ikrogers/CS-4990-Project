package mk.ea.ir.blazercorpscentral;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

/**
 * Created by u786 on 4/3/2015.
 */
public class AFIActivity extends Activity {
private WebView mWebView;
    private final String VIEW_DOC = "http://docs.google.com/gview?embedded=true&url=";
    private final String URL = "http://static.e-publishing.af.mil/production/1/af_a1/publication/afi36-2903/afi36-2903.pdf";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        // Let's display the progress in the activity title bar, like the
        // browser app does.

        mWebView = new WebView(this);
        mWebView.getSettings().setJavaScriptEnabled(true);

        mWebView.getSettings().setAllowFileAccess(true);
        mWebView.getSettings().setAllowContentAccess(true);
        mWebView.getSettings().setAppCacheEnabled(true);


        getWindow().requestFeature(Window.FEATURE_PROGRESS);
        setContentView(mWebView);


        final Activity activity = this;
        mWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                // Activities and WebViews measure progress with different scales.
                // The progress meter will automatically disappear when we reach 100%
                activity.setProgress(progress * 1000);
            }
        });
        mWebView.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(activity, "Oh no! " + description, Toast.LENGTH_SHORT).show();
            }
        });


        Toast.makeText(activity, "USAF Dress and Appearance Standards are loading... Please wait.", Toast.LENGTH_SHORT).show();

        mWebView.loadUrl(URL);
            mWebView.setDownloadListener(new DownloadListener() {
                public void onDownloadStart(String url, String userAgent, String something, String mimetype, long contentLength) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    Log.w("AFI", "File Location " + (VIEW_DOC + URL));

                    intent.setData(Uri.parse(VIEW_DOC + URL));
                    startActivity(intent);
                }
            });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(event.getAction() == KeyEvent.ACTION_DOWN){
            switch(keyCode)
            {
                case KeyEvent.KEYCODE_BACK:
                    if(mWebView.canGoBack()){
                        mWebView.goBack();
                    }else{
                        finish();
                    }
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }
}
