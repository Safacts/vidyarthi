package com.aadi.learner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.List;
import com.aadi.learner.HistoryItem;


public class HistoryAdapter extends ArrayAdapter<HistoryItem> {
    private Context context;
    private List<HistoryItem> historyList;

    public HistoryAdapter(Context context, List<HistoryItem> historyList) {
        super(context, 0, historyList);
        this.context = context;
        this.historyList = historyList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.history_item, parent, false);
        }

        HistoryItem historyItem = getItem(position);

        ToggleButton pinButton = convertView.findViewById(R.id.pinButton);
        TextView historyUrl = convertView.findViewById(R.id.historyUrl);

        historyUrl.setText(historyItem.getUrl());
        pinButton.setChecked(historyItem.isPinned());

        pinButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            historyItem.setPinned(isChecked);
            if (isChecked) {
                savePinnedUrl(historyItem.getUrl());
            } else {
                removePinnedUrl(historyItem.getUrl());
            }
        });

        return convertView;
    }

    private void savePinnedUrl(String url) {
        // Logic to save pinned URL permanently
    }

    private void removePinnedUrl(String url) {
        // Logic to remove pinned URL
    }
}
