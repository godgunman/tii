package tw.tii.hackjunction.passengerapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import tw.tii.hackjunction.passengerapp.R;

/**
 * Created by yi on 2015/11/8.
 */
public class SummaryFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_summary, container, false);
        view.findViewById(R.id.tv_news).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView iv = (ImageView)view.findViewById(R.id.imageSummary);
                iv.setImageResource(R.drawable.summary_news);
            }
        });
        view.findViewById(R.id.tv_fees).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView iv = (ImageView)view.findViewById(R.id.imageSummary);
                iv.setImageResource(R.drawable.summary_fee);
            }
        });
        view.findViewById(R.id.tv_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView iv = (ImageView)view.findViewById(R.id.imageSummary);
                iv.setImageResource(R.drawable.summary_info);
            }
        });
        return view;
    }

    @Override
    public void putAllData() {
        // Do nothing.
    }
}
