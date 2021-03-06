package com.example.bar_buddy.TabFragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bar_buddy.Adapters.BarCardAdapter;
import com.example.bar_buddy.AdapterItems.BarItem;
import com.example.bar_buddy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class FavoritesTab extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference barsRef = db.collection("bars");

    private BarCardAdapter adapter;
    private List<BarItem> bars;
    private List<String> bar_ids;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private OnFragmentInteractionListener mListener;

    public FavoritesTab() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bar_ids = new ArrayList<String>();
        bars = new ArrayList<BarItem>();

        getFavorites(new FirestoreCallbackOne() {
            @Override
            public void onCallback() {
                readData(new FirestoreCallbackTwo() {
                    @Override
                    public void onCallback(List<BarItem> list) {
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });

    }

    private void getFavorites(final FirestoreCallbackOne firestoreCallback) {
        db.collection("users").document(user.getUid()).collection("bars_favorites").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                BarItem b = document.toObject(BarItem.class);
                                Log.e("id", b.getBar_id());
                                if(bar_ids != null) {
                                    Log.e("inner", "part");
                                    bar_ids.add((String) b.getBar_id());
                                }
                            }
                            firestoreCallback.onCallback();
                        }
                    }
                });

    }

    private void readData(final FirestoreCallbackTwo firestoreCallback) {
        barsRef
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if(bar_ids.size() == 0) Log.e("i seem to make all" , "delay");
                                if(bar_ids != null && bar_ids.contains((String) document.getId())) {
                                    Log.e("inner", "part2");
                                    BarItem b = document.toObject(BarItem.class);
                                    b.setBar_id(document.getId());

                                    if(bars != null) {
                                        bars.add(b);
                                    }
                                }
                            }
                            firestoreCallback.onCallback(bars);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    private interface FirestoreCallbackOne {
        void onCallback();
    }

    private interface FirestoreCallbackTwo {
        void onCallback(List<BarItem> list);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_favorites_tab, container, false);
        RecyclerView rvCards = (RecyclerView) rootView.findViewById(R.id.favorites_bars_recyclerview);
        rvCards.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new BarCardAdapter(getActivity(), bars);
        rvCards.setAdapter(adapter);
        rvCards.setItemAnimator(new DefaultItemAnimator());
        rvCards.setNestedScrollingEnabled(false);

        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayoutFavs);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(
                R.color.Primary,
                R.color.colorBackground);

        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);

        bars.clear();
        bar_ids.clear();

        getFavorites(new FirestoreCallbackOne() {
            @Override
            public void onCallback() {
                readData(new FirestoreCallbackTwo() {
                    @Override
                    public void onCallback(List<BarItem> list) {
                        adapter.notifyDataSetChanged();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        });
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
