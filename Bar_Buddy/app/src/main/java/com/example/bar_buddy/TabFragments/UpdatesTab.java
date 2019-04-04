package com.example.bar_buddy.TabFragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class UpdatesTab extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private List<UpdateItem> updatesList;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference updatesRef = db.collection("updates");
    private UpdatesCardAdapter adapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

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
            public void onCallback() {

                /*for (int i = 0; i < updatesList.size(); i++) {
                    getBar(new FirestoreCallback() {
                        @Override
                        public void onCallback() {
                            adapter.notifyDataSetChanged();
                        }
                    }, i);
                }*/

                adapter.notifyDataSetChanged();
            }
        });

    }

    /*private void getBar(final FirestoreCallback firestoreCallback, final int position) {
        db.collection("bars")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Log.e("in", "big daddy");
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.e(document.getId(), updatesList.get(position).getBar_id());
                                if (document.getId().equals(updatesList.get(position).getBar_id())) {
                                    Log.e("finding", "success");
                                    //updatesList.get(position).setBar(document.toObject(BarItem.class));
                                    BarItem b = document.toObject(BarItem.class);
                                    updatesList.get(position).setBar(b);
                                }
                            }
                            firestoreCallback.onCallback();
                        }
                    }
                });
    }*/

    private void readData(final FirestoreCallback firestoreCallback) {
        updatesRef
                .orderBy("time", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                UpdateItem u = document.toObject(UpdateItem.class);
                                u.setUpdate_id(document.getId());

                                updatesList.add(u);
                            }
                            firestoreCallback.onCallback();
                        }
                    }
                });
    }

    private void reReadData(final FirestoreCallback firestoreCallback) {
        updatesRef
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for(QueryDocumentSnapshot document : task.getResult()) {
                                UpdateItem u = document.toObject(UpdateItem.class);
                                u.setUpdate_id(document.getId());

                                for(int i = 0; i < updatesList.size(); i++) {
                                    if(u.getUpdate_id().equals(updatesList.get(i).getUpdate_id())) {
                                        updatesList.set(i, u);
                                        break;
                                    }
                                }
                            }
                            firestoreCallback.onCallback();
                        }
                    }
                });
    }

    private interface FirestoreCallback {
        void onCallback();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_updates_tab, container, false);
        RecyclerView rvCards = (RecyclerView) rootView.findViewById(R.id.updates_recyclerview);

        adapter = new UpdatesCardAdapter(getActivity(), updatesList);
        rvCards.setAdapter(adapter);
        rvCards.setItemAnimator(new DefaultItemAnimator());
        rvCards.setNestedScrollingEnabled(false);

        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(
                R.color.Primary,
                R.color.colorBackground);

        return rootView;
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);

        reReadData(new FirestoreCallback() {
            @Override
            public void onCallback() {
                adapter.notifyDataSetChanged();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
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
