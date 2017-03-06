package com.example.adrianpc.s236308_mappe_2.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.adrianpc.s236308_mappe_2.R;
import com.example.adrianpc.s236308_mappe_2.ContactAdapter;
import com.example.adrianpc.s236308_mappe_2.database.Contact;
import com.example.adrianpc.s236308_mappe_2.database.Database;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ContactFragment extends Fragment {

    private OnListFragmentInteractionListener interactionListener;
    private RecyclerView recyclerView;
    private ContactAdapter adapter;
    private Database db;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ContactFragment(OnListFragmentInteractionListener listener) {
        interactionListener = listener;
    }

    public boolean isInDeleteMode() {
        return adapter.isInDeleteMode();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_list, container, false);
        db = new Database(getContext());
        recyclerView = (RecyclerView) view.findViewById(R.id.view_contactlist);
        adapter = new ContactAdapter(db.getContacts(), interactionListener);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        return view;
    }

    public void delete(Contact contact, int index) {
        db.deleteContact(contact.get_ID());
        adapter.delete(index);
    }

    public void changeDeletable() {
        adapter.changeDeletable();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            interactionListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        interactionListener = null;
    }

    public interface OnListFragmentInteractionListener {
        void onClick(Contact contact);
        void onLongInteraction();
        void onDelete(Contact contact, int index);

    }
}
