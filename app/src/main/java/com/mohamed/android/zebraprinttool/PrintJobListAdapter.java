package com.mohamed.android.zebraprinttool;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.HashMap;

/**
 * Created by ctsims on 7/22/2016.
 */
public class PrintJobListAdapter extends ArrayAdapter<ZebraPrintTask.PrintJob> {

    boolean isCancelled = false;

    HashMap<Integer, WeakReference<View>> trackingTable = new HashMap<>();

    public PrintJobListAdapter(Context context, ZebraPrintTask.PrintJob[] jobs) {
        super(context, -1, jobs);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ZebraPrintTask.PrintJob job = this.getItem(position);
        if(convertView == null) {
            convertView = View.inflate(this.getContext(), R.layout.component_print_job, null);
            Integer tag = (Integer)convertView.getTag();
            if(trackingTable.containsKey(tag)){
                trackingTable.remove(tag);
            }
        }
        updateView(job, convertView);
        return convertView;
    }

    private void updateView(ZebraPrintTask.PrintJob job, View convertView) {
        ((TextView)convertView.findViewById(R.id.print_job_name)).setText(job.getDisplayName());

        String statusString = job.getStatus().toString();
        if(!job.isFinished() && isCancelled) {
            statusString = "CANCELLED";
        }

        TextView errorView = ((TextView)convertView.findViewById(R.id.print_job_error));

        if(job.getStatus() == ZebraPrintTask.PrintJob.Status.ERROR) {
            errorView.setVisibility(View.VISIBLE);
            errorView.setText(job.getErrorMessage());
        } else {
            errorView.setVisibility(View.GONE);
        }

        ((TextView)convertView.findViewById(R.id.print_job_status)).setText(statusString);
        convertView.setTag(job.getJobId());
        trackingTable.put(job.getJobId(), new WeakReference<>(convertView));
    }

    public void setModeCancelled() {
        this.isCancelled = true;
    }
}
