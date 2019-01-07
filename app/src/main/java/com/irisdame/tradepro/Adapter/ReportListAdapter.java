package com.irisdame.tradepro.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.irisdame.tradepro.Activities.FinalReportActivity;
import com.irisdame.tradepro.R;
import com.irisdame.tradepro.Utility.TypeCode;
import com.irisdame.tradepro.Utility.Utils;

import java.util.List;

public class ReportListAdapter extends RecyclerView.Adapter<ReportListAdapter.ReportListHolder> {

    private Context context;
    private List<String> productName;
    private int typePos;
    private int noOfActivity;

    public ReportListAdapter(Context context, List<String> productName, int typePos, int noOfActivity) {
        this.context = context;
        this.productName = productName;
        this.typePos = typePos;
        this.noOfActivity = noOfActivity;
    }

    @NonNull
    @Override
    public ReportListAdapter.ReportListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.report_list_item, null, false);
        return new ReportListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportListAdapter.ReportListHolder reportListHolder, final int i) {
        reportListHolder.productNameTV.setText(productName.get(i));

        reportListHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchFinalReport = new Intent(context, FinalReportActivity.class);

                if(noOfActivity == 2 && Utils.noIntentFlag == 0) {
                    launchFinalReport.putExtra("position", typePos);

                    //Log.v("TESTM", "Pos " + i + "  " + TypeCode.copyData[2][5]);

                    String[] fields = TypeCode.getFieldsList(typePos);

                    launchFinalReport.putExtra("noOfActivity", noOfActivity);

                    for(int k=0; k<fields.length; k++) {
                        launchFinalReport.putExtra(fields[k], TypeCode.copyData[i][k]);
                    }

                    context.startActivity(launchFinalReport);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if(productName != null)
        {
            return productName.size();
        } else {
            return 0;
        }

    }


    public class ReportListHolder extends RecyclerView.ViewHolder{

        TextView productNameTV;
        LinearLayout linearLayout;

        public ReportListHolder(@NonNull View itemView) {
            super(itemView);

            productNameTV = itemView.findViewById(R.id.productNameTV);

            linearLayout = itemView.findViewById(R.id.linearLayout);
        }
    }
}
