package com.a.dev.MyApplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import io.github.yavski.fabspeeddial.FabSpeedDial;
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter;

public class ListDataFragment extends Fragment {

    private FabSpeedDial fabSpeedDial;

    private OnFragmentInteractionListener mListener;

    public ListDataFragment() {

    }

    public static ListDataFragment newInstance(String param1, String param2) {
        ListDataFragment fragment = new ListDataFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list_data, container, false);

        fabSpeedDial = view.findViewById(R.id.fabCollapse);
        fabSpeedDial.setMenuListener(new SimpleMenuListenerAdapter() {
            @Override
            public boolean onMenuItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_add:
                        Intent toAddLocation = new Intent(getActivity(), AddMapsActivity.class);

                        startActivity(toAddLocation);
                        Toast.makeText(getActivity(), "Add Clicked", Toast.LENGTH_LONG).show();
                        return true;
                    case R.id.action_current:
                        Toast.makeText(getActivity(), "Current Clicked", Toast.LENGTH_LONG).show();
                        return true;
                }
                return false;
            }
        });

        return view;
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }
}
