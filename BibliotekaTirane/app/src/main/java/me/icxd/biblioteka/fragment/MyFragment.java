package me.icxd.biblioteka.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MyFragment extends Fragment {
    
    private static final String BUNDLE_TITLE = "title";
    private String title;

    public static MyFragment newInstance(String title) {
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_TITLE, title);
        MyFragment fragment = new MyFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("MyFragment", "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("MyFragment", "onResume");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i("MyFragment", "onViewCreated");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i("MyFragment", "onAttach");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("MyFragment", "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("MyFragment", "onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("MyFragment", "onDestroy");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i("MyFragment", "onDestroyView");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i("MyFragment", "onDetach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            title = getArguments().getString(BUNDLE_TITLE);
        }
        Log.i("MyFragment", "onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        textView.setText(title);
        textView.setGravity(Gravity.CENTER);

        Log.i("MyFragment", "onCreateView");
        return textView;
    }

}
