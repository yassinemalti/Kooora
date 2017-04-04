package com.wordpress.yassinemalti.kooora.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.NativeExpressAdView;
import com.wordpress.yassinemalti.kooora.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class MaintenantFragment extends Fragment {

    private static final String TAG = "MaintenantFragment";
    public WebView myWebView;
    String url = "http://m.kooora.com/";
    ProgressDialog progressDialog;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MaintenantFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static MaintenantFragment newInstance(String param1, String param2) {
        MaintenantFragment fragment = new MaintenantFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_maintenant, container, false);
        NativeExpressAdView adBanner_0 = (NativeExpressAdView) rootView.findViewById(R.id.adBanner_0);
        AdRequest request_0 = new AdRequest.Builder().build();
        adBanner_0.loadAd(request_0);
        myWebView = (WebView) rootView.findViewById(R.id.activity_maintenant_webview);
        new Title().execute();
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private class Title extends AsyncTask<Void, Void, Void> {

        String html;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle(R.string.app_name);
            progressDialog.setMessage("جاري التحديث...");
            progressDialog.setIndeterminate(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            //myWebView.loadUrl("http://m.kooora.com/?region=-1&area=6");
            //Document doc = Jsoup.connect("http://en.wikipedia.org/").get();
            //Elements newsHeadlines = doc.select("#mp-itn b a");
            url = "http://en.wikipedia.org/";
            try {
                Document document = Jsoup.connect(url).timeout(10000).get();
                Elements newsHeadlines = document.select("#mp-itn b a");
                html = newsHeadlines.toString();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Log.d(TAG, html);
            String mime = "text/html";
            String encoding = "utf-8";
            myWebView.loadData(html, mime, encoding);
            WebSettings webSettings = myWebView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            myWebView.setWebViewClient(new WebViewClient());
            progressDialog.dismiss();
        }
    }

}
