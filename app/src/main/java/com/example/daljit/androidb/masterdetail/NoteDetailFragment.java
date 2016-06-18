package com.example.daljit.androidb.masterdetail;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidb.util.MyNotesUtil;
import com.example.daljit.androidb.model.MyNotes;
import com.example.daljit.androidb.db.MyNotesDBHandler;
import com.example.daljit.androidb.R;
import com.example.daljit.androidb.model.ValueObject;

public class NoteDetailFragment extends Fragment {
    public static final String ARG_ITEM_ID = "_id";

    MyNotesDBHandler dbHandler ;

    private MyNotes notes;

    public NoteDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ValueObject.setContext(getContext());
        if (getArguments().containsKey(ARG_ITEM_ID)) {
            notes = ValueObject.NOTES_MAP.get(getArguments().getString(ARG_ITEM_ID));
            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(MyNotesUtil.getTaeaser(notes.getNotes()));
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.note_detail, container, false);

        if (notes != null) {
            ((TextView) rootView.findViewById(R.id.note_detail)).setText(notes.getNotes());
        }

        return rootView;
    }

}
