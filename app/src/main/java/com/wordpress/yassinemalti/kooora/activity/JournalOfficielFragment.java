package com.wordpress.yassinemalti.kooora.activity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.NativeExpressAdView;
import com.wordpress.yassinemalti.kooora.R;

public class JournalOfficielFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public JournalOfficielFragment() {

    }

    public static JournalOfficielFragment newInstance(String param1, String param2) {
        JournalOfficielFragment fragment = new JournalOfficielFragment();
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

        View rootView = inflater.inflate(R.layout.fragment_journal_officiel, container, false);
        NativeExpressAdView adBanner_4 = (NativeExpressAdView) rootView.findViewById(R.id.adBanner_4);
        AdRequest request_4 = new AdRequest.Builder().build();
        adBanner_4.loadAd(request_4);

        TextView t1 = (TextView) rootView.findViewById(R.id.textViewLienJournalOfficiel_1);
        t1.setMovementMethod(LinkMovementMethod.getInstance());

        TextView t2 = (TextView) rootView.findViewById(R.id.textViewLienJournalOfficiel_2);
        t2.setMovementMethod(LinkMovementMethod.getInstance());

        TextView t3 = (TextView) rootView.findViewById(R.id.textViewLienJournalOfficiel_3);
        t3.setMovementMethod(LinkMovementMethod.getInstance());

        TextView t4 = (TextView) rootView.findViewById(R.id.textViewLienJournalOfficiel_4);
        t4.setMovementMethod(LinkMovementMethod.getInstance());

        TextView t5 = (TextView) rootView.findViewById(R.id.textViewLienJournalOfficiel_5);
        t5.setMovementMethod(LinkMovementMethod.getInstance());

        TextView t6 = (TextView) rootView.findViewById(R.id.textViewLienJournalOfficiel_6);
        t6.setMovementMethod(LinkMovementMethod.getInstance());

        TextView t7 = (TextView) rootView.findViewById(R.id.textViewLienJournalOfficiel_7);
        t7.setMovementMethod(LinkMovementMethod.getInstance());

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
}
