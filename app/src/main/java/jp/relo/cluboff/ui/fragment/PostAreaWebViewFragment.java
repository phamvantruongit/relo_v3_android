package jp.relo.cluboff.ui.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import jp.relo.cluboff.R;
import jp.relo.cluboff.model.AreaCouponPost;
import jp.relo.cluboff.ui.BaseFragmentBottombar;
import jp.relo.cluboff.ui.webview.MyWebViewClient;
import jp.relo.cluboff.util.Constant;

/**
 * Created by tonkhanh on 5/18/17.
 */

public class PostAreaWebViewFragment extends BaseFragmentBottombar {

    WebView mWebView;
    private String url;
    private String strPost;

    private final static int REQUEST_CHECK_SETTINGS = 0;
    public static final int MULTIPLE_PERMISSIONS = 10;
    String[] permissions = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION};


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mWebView = (WebView) view.findViewById(R.id.wvCoupon);
        setupWebView(mWebView);

        if (!checkPermissions()) {
            requestPermission();
        }else{
            loadUrl();
        }
    }
    @Override
    public void setupBottombar() {
        lnBottom.setVisibility(View.VISIBLE);
        imvBackBottomBar.setVisibility(View.VISIBLE);
        imvForwardBottomBar.setVisibility(View.VISIBLE);
        imvBrowserBottomBar.setVisibility(View.GONE);
        imvReloadBottomBar.setVisibility(View.VISIBLE);
        imvBackBottomBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWebView.goBack();
                imvBackBottomBar.setEnabled(false);
            }
        });
        imvForwardBottomBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWebView.goForward();
                imvForwardBottomBar.setEnabled(false);
            }
        });
        imvBrowserBottomBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(mWebView.getUrl())));
            }
        });

        imvReloadBottomBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWebView.reload();
            }
        });

        imvBackBottomBar.setEnabled(mWebView.canGoBack());
        imvForwardBottomBar.setEnabled(mWebView.canGoForward());
    }

    @Override
    public int getRootLayoutId() {
        return R.layout.fragment_webview;
    }

    @Override
    protected void getMandatoryViews(View root, Bundle savedInstanceState) {

    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    protected void registerEventHandlers() {

    }

    private void setupWebView(final WebView webView) {
        WebSettings webSettings = webView.getSettings();
        webSettings.setDomStorageEnabled(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        //Disable cache Webview
        webSettings.setAppCacheEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        webView.setWebViewClient(new MyWebViewClient(getActivity()) {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                showLoading(getActivity());
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if(isVisible()){
                    hideLoading();
                    imvBackBottomBar.setEnabled(webView.canGoBack());
                    imvForwardBottomBar.setEnabled(webView.canGoForward());
                }

            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                //TODO hide loading
                if(isVisible()){
                    hideLoading();
                }
            }

        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private boolean checkPermissions() {
        int findLoca = ContextCompat.checkSelfPermission(getActivity(), permissions[0]);
        return findLoca == PackageManager.PERMISSION_GRANTED;

    }
    private void requestPermission() {
        requestPermissions(permissions, MULTIPLE_PERMISSIONS);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MULTIPLE_PERMISSIONS:
                if (grantResults.length > 0) {
                    boolean location = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (location) {
                        Toast.makeText(getActivity(), "Permission is Accepted", Toast.LENGTH_SHORT).show();
                        loadUrl();
                    }else{
                        Toast.makeText(getActivity(), "Please accept permission access to get location", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Please accept permission access", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    private void loadUrl() {
        url = Constant.WEBVIEW_URL_AREA_COUPON;
        strPost = new AreaCouponPost().toString();
        mWebView.postUrl( url, strPost.getBytes());
    }

}