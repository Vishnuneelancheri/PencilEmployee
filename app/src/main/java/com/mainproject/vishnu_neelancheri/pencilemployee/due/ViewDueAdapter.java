package com.mainproject.vishnu_neelancheri.pencilemployee.due;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.mainproject.vishnu_neelancheri.pencilemployee.R;

import java.util.ArrayList;

/**
 * Created by Vishnu Neelancheri, email: vishnuvishnuneelan@gmail.com on 3/28/2018
 */

public class ViewDueAdapter extends RecyclerView.Adapter<ViewDueAdapter.DueHolder>{
    private ArrayList<DueModel> dueModelList;
    private Context context;
    private Clicker clicker;
    public  ViewDueAdapter(Context context, ArrayList<DueModel> dueModelList, Clicker clicker ){
        this.context = context;
        this.dueModelList = dueModelList;
        this.clicker = clicker;
    }
    public static class DueHolder extends RecyclerView.ViewHolder{
        public DueHolder(View view){
            super(view);
            txtDate = view.findViewById( R.id.txt_date );
            txtAmount = view.findViewById( R.id.txt_amount );
            btnPay = view.findViewById( R.id.btn_pay );
        }
        private TextView txtDate, txtAmount;
        private Button btnPay;
    }
    @Override
    public int getItemCount(){
        return dueModelList.size();
    }
    @Override
    public DueHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from( parent.getContext() )
                .inflate( R.layout.recycler_due , parent, false );
        return new DueHolder( view );
    }
    @Override
    public void onBindViewHolder( DueHolder dueHolder, int posi ){
        int pos = dueHolder.getAdapterPosition();
        final DueModel dueModel = dueModelList.get(pos);
        dueHolder.txtAmount.setText( dueModel.getCommission());
        dueHolder.txtDate.setText( dueModel.getDueDate() );
        if ( dueModel.getPaymentStatus().equals("0")){
            dueHolder.btnPay.setText("Pay amount");
            dueHolder.btnPay.setBackgroundColor( context.getResources().getColor( R.color.colorAccent));
            dueHolder.btnPay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clicker.click(dueModel.getPaymentId());
                }
            });
        }else{
            dueHolder.btnPay.setText("Paid");
            dueHolder.btnPay.setBackgroundColor( context.getResources().getColor( R.color.completed));
        }
    }
    public interface Clicker{
        void click(String id);
    }
}
