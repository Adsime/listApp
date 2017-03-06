package com.example.adrianpc.s236308_mappe_2;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.adrianpc.s236308_mappe_2.fragments.ContactFragment;
import com.example.adrianpc.s236308_mappe_2.database.Contact;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactHolder> {

    private List<Contact> contacts;
    private ContactFragment.OnListFragmentInteractionListener mListener;
    private boolean inDeleteMode;

    public ContactAdapter(List<Contact> contacts, ContactFragment.OnListFragmentInteractionListener listener) {
        this.contacts = contacts;
        mListener = listener;
        inDeleteMode = false;
    }

    @Override
    public ContactHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_contact, parent, false);
        return new ContactHolder(view);
    }

    @Override
    public void onBindViewHolder(ContactHolder holder, final int position) {
        final int id = position;
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClick(contacts.get(position));
            }
        });
        if(inDeleteMode) {
            holder.delete.setClickable(true);
            holder.delete.setImageResource(android.R.drawable.ic_delete);
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onDelete(contacts.get(position), position);
                }
            });
        } else {
            holder.delete.setClickable(false);
            holder.delete.setImageResource(0);
        }
        Contact current = contacts.get(position);
        holder.name.setText(current.getName());
        holder.card.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mListener.onLongInteraction();
                return false;
            }
        });
    }

    public void delete(int index) {
        contacts.remove(index);
        notifyItemRemoved(index);
        notifyItemRangeChanged(index, getItemCount());
    }

    public void changeDeletable() {
        inDeleteMode = !inDeleteMode;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public boolean isInDeleteMode() {
        return inDeleteMode;
    }

    public class ContactHolder extends RecyclerView.ViewHolder {
        CardView card;
        ImageView contactImage;
        TextView name;
        ImageButton delete;
        public ContactHolder(View view) {
            super(view);
            card = (CardView) view.findViewById(R.id.card);
            contactImage = (ImageView) view.findViewById(R.id.card_image);
            name = (TextView) view.findViewById(R.id.card_name);
            delete = (ImageButton) view.findViewById(R.id.card_delete);
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }
}
