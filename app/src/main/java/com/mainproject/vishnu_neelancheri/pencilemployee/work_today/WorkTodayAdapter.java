package com.mainproject.vishnu_neelancheri.pencilemployee.work_today;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mainproject.vishnu_neelancheri.pencilemployee.R;

import java.util.ArrayList;

/**
 * Created by Vishnu Neelancheri, email: vishnuvishnuneelan@gmail.com on 3/7/2018
 */

public class WorkTodayAdapter extends RecyclerView.Adapter<WorkTodayAdapter.WorkTodayHolder>{
    ArrayList<WorkTodayModel  > workModelList;
    private SelectTodayWork selectTodayWork;
    private Context mContext;
    public static class WorkTodayHolder extends RecyclerView.ViewHolder{
        TextView txtBill, txtPhone, txtName, txtFrame, TxtPaper, txtCourier, txtTotal, txtBalnce;
        LinearLayout parent;
        public WorkTodayHolder(View view){
            super(view);
            txtBill = view.findViewById( R.id.txt_bill_no );
            txtName = view.findViewById( R.id.txt_bill_name);
            txtPhone = view.findViewById( R.id.txt_phone );
            txtFrame = view.findViewById( R.id.txt_frame_name);
            TxtPaper = view.findViewById( R.id.txt_paper_details );
            txtCourier = view.findViewById( R.id.txt_courier_charge );
            txtTotal = view.findViewById( R.id.total_price );
            txtBalnce = view.findViewById( R.id.txt_balance );
            parent = view.findViewById(R.id.parent);
        }
    }
    public WorkTodayAdapter (Context context,ArrayList<WorkTodayModel  > workModelList , SelectTodayWork selectTodayWork ){
        this.workModelList = workModelList;
        this.selectTodayWork = selectTodayWork;
        mContext = context;
    }
    @Override
    public int getItemCount(){
        return  workModelList.size();
    }
    public interface SelectTodayWork{
        void select(WorkTodayModel workTodayModel );
    }
    @Override
    public WorkTodayHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_todays_work, parent, false);
        return  new WorkTodayHolder( view );
    }
    @Override
    public void onBindViewHolder(WorkTodayHolder workTodayHolder, int pos){
        int pstion = workTodayHolder.getAdapterPosition();
        final WorkTodayModel workTodayModel = workModelList.get( pstion );
        workTodayHolder.txtBill.setText( workTodayModel.getBillId() );
        workTodayHolder.txtName.setText( workTodayModel.getCustName() );
        workTodayHolder.txtPhone.setText( workTodayModel.getCustPhone() );
        workTodayHolder.txtFrame.setText(workTodayModel.getFrameName() );
        workTodayHolder.TxtPaper.setText( workTodayModel.getPaperCart() );
        workTodayHolder.txtCourier.setText( workTodayModel.getCourierCharge() );
        workTodayHolder.txtTotal.setText( workTodayModel.getTotalPrice() );
        workTodayHolder.txtBalnce.setText( workTodayModel.getBalance() );
        workTodayHolder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectTodayWork.select( workTodayModel );
            }
        });
        if ( workTodayModel.getBillPaymentStatus() == 1 ){
            workTodayHolder.parent.setBackgroundColor( mContext.getResources().getColor( R.color.non_completion ));
        } else if ( workTodayModel.getBillPaymentStatus() == 2 ){
            workTodayHolder.parent.setBackgroundColor( mContext.getResources().getColor( R.color.draw_complete ));
        }else if ( workTodayModel.getBillPaymentStatus() == 3 ){
            workTodayHolder.parent.setBackgroundColor( mContext.getResources().getColor( R.color.completed ));
        }
    }
}
