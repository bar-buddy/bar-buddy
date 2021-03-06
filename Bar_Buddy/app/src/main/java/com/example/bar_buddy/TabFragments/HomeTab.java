package com.example.bar_buddy.TabFragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.bar_buddy.Activities.MainActivity;
import com.example.bar_buddy.Adapters.BarCardAdapter;
import com.example.bar_buddy.AdapterItems.BarItem;
import com.example.bar_buddy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import static android.support.constraint.Constraints.TAG;

public class HomeTab extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private List<BarItem> bars;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Query barsRef = db.collection("bars");
    private BarCardAdapter adapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private SearchView searchView;

    private OnFragmentInteractionListener mListener;

    public HomeTab() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);

        bars = new ArrayList<BarItem>();

        readData(new FirestoreCallback() {
            @Override
            public void onCallback(List<BarItem> list) {
                adapter.notifyDataSetChanged();
            }
        }, "");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);

        menuInflater.inflate(R.menu.search_menu, menu);
        menuInflater.inflate(R.menu.sort_menu, menu);


        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                bars.clear();
                readData(new FirestoreCallback() {
                    @Override
                    public void onCallback(List<BarItem> list) {
                        adapter.notifyDataSetChanged();
                    }
                }, s);
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.sortmenu_default:
                item.setChecked(true);
                barsRef = db.collection("bars");
                loadBars();
                break;
            case R.id.sortmenu_AZ:
                item.setChecked(true);
                barsRef = db.collection("bars").orderBy("bar_name", Query.Direction.ASCENDING);
                loadBars();
                break;
            case R.id.sortmenu_ZA:
                item.setChecked(true);
                barsRef = db.collection("bars").orderBy("bar_name", Query.Direction.DESCENDING);
                loadBars();
                break;
            case R.id.sortmenu_WaitHL:
                item.setChecked(true);
                barsRef = db.collection("bars").orderBy("bar_wait", Query.Direction.DESCENDING);
                loadBars();
                break;
            case R.id.sortmenu_WaitLH:
                item.setChecked(true);
                barsRef = db.collection("bars").orderBy("bar_wait", Query.Direction.ASCENDING);
                loadBars();
                break;
            case R.id.sortmenu_CovHL:
                item.setChecked(true);
                barsRef = db.collection("bars").orderBy("bar_cover", Query.Direction.DESCENDING);
                loadBars();
                break;
            case R.id.sortmenu_covLH:
                item.setChecked(true);
                barsRef = db.collection("bars").orderBy("bar_cover", Query.Direction.ASCENDING);
                loadBars();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void readData(final FirestoreCallback firestoreCallback, final String s) {
        barsRef
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                BarItem b = document.toObject(BarItem.class);
                                b.setBar_id(document.getId());

                                if(bars != null &&
                                        b.getBar_name().toLowerCase().contains(s.toLowerCase())) {

                                    /*for(int i = 0; i < bars.size(); i++) {
                                        bars.get(i).getBar_name()
                                    }*/
                                    bars.add(b);
                                }
                            }
                            firestoreCallback.onCallback(bars);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    private interface FirestoreCallback {
        void onCallback(List<BarItem> list);
    }

    private void loadBars() {
        bars.clear();

        mSwipeRefreshLayout.setRefreshing(true);

        readData(new FirestoreCallback() {
            @Override
            public void onCallback(List<BarItem> list) {
                adapter.notifyDataSetChanged();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, "");

        /*reReadData(new FirestoreCallback() {
            @Override
            public void onCallback(List<BarItem> list) {
                adapter.notifyDataSetChanged();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home_tab, container, false);

        RecyclerView rvCards = (RecyclerView) rootView.findViewById(R.id.home_bars_recyclerview);
        rvCards.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new BarCardAdapter(getActivity(), bars);
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
        loadBars();
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
