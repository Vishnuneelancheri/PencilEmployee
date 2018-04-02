package com.mainproject.vishnu_neelancheri.pencilemployee.new_order;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mainproject.vishnu_neelancheri.pencilemployee.R;
import com.mainproject.vishnu_neelancheri.pencilemployee.utils.PencilUtil;

public class EntereAdvanceFragment extends DialogFragment implements View.OnClickListener{

    private static final String TOTAL_AMOUNT = "total";
    EditText edtAdvance;
    private String totalAmount;
    public EntereAdvanceFragment() {
        // Required empty public constructor
    }
    public static EntereAdvanceFragment newInstance(String totalAmount){
        EntereAdvanceFragment entereAdvanceFragment = new EntereAdvanceFragment();
        Bundle bundle = new Bundle();
        bundle.putString( TOTAL_AMOUNT, totalAmount );
        entereAdvanceFragment.setArguments( bundle );
        return entereAdvanceFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Bundle bundle = getArguments();
        totalAmount = bundle.getString(TOTAL_AMOUNT);
        View view =  inflater.inflate(R.layout.fragment_entere_advance, container, false);
        edtAdvance = view.findViewById( R.id.edt_txt_advance );
        Button btnCancel = view.findViewById( R.id.btn_proceed );
        Button btnProceed = view.findViewById( R.id.btn_cancel );
        btnCancel.setOnClickListener(this);
        btnProceed.setOnClickListener( this );
        TextView txtAdvance = view.findViewById( R.id.txt_advance );
        txtAdvance.setText("Total Amount = "+ totalAmount );
        return view;
    }





    @Override
    public void onClick(View view) {
        int id = view.getId();
        if ( id == R.id.btn_proceed)
            submitValue();
        else
            cancel();
    }
    private void submitValue(){
        String advanceTemp = edtAdvance.getText().toString();
        if ( advanceTemp.isEmpty() ){
            advanceTemp = "0";
            PencilUtil.toaster( getContext(), "Please enter valid advance amount" );
            return;
        }

        if ( Integer.parseInt(totalAmount) <  Integer.parseInt( advanceTemp )){
            PencilUtil.toaster( getContext(), "Please enter advance amount less than or equal to" + totalAmount );
        }else {
            ((NewOrderActivity)getActivity()).advancePayment( advanceTemp );
        }
        getDialog().dismiss();
    }
    private void cancel(){
        getDialog().dismiss();
    }

}
