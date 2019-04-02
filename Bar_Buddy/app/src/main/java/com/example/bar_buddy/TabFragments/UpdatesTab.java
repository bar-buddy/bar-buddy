package com.example.bar_buddy.TabFragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bar_buddy.AdapterItems.BarItem;
import com.example.bar_buddy.Adapters.BarCardAdapter;
import com.example.bar_buddy.Adapters.UpdatesCardAdapter;
import com.example.bar_buddy.R;
import com.example.bar_buddy.AdapterItems.UpdateItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class UpdatesTab extends Fragment {

    private List<UpdateItem> updatesList;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference updatesRef = db.collection("updates");
    private UpdatesCardAdapter adapter;

    private OnFragmentInteractionListener mListener;

    public UpdatesTab() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        updatesList = new ArrayList<UpdateItem>();

        readData(new FirestoreCallback() {
            @Override
            public void onCallback(List<UpdateItem> list) {
                adapter.notifyDataSetChanged();
            }
        });

    }

    private void readData(final FirestoreCallback firestoreCallback) {
        updatesRef
                .orderBy("time")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                UpdateItem u = document.toObject(UpdateItem.class);

                                updatesList.add(u);
                            }
                            firestoreCallback.onCallback(updatesList);
                        }
                    }
                });
    }

    private interface FirestoreCallback {
        void onCallback(List<UpdateItem> list);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_updates_tab, container, false);
        RecyclerView rvCards = (RecyclerView) rootView.findViewById(R.id.updates_recyclerview);

        adapter = new UpdatesCardAdapter(getActivity(), updatesList);
        rvCards.setAdapter(adapter);
        rvCards.setItemAnimator(new DefaultItemAnimator());
        rvCards.setNestedScrollingEnabled(false);

        return rootView;
    }


    /* !!!DISREGARD EVERYTHING PAST HERE!!! */

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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
