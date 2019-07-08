package com.frasker.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.frasker.recyclerview.HeaderRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Integer> data = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            data.add(i);
        }

        HeaderRecyclerView recyclerview = findViewById(R.id.recyclerview);

        recyclerview.setLayoutManager(new LinearLayoutManager(this));

        recyclerview.setAdapter(new MyAdapter(data));

        TextView header = new TextView(this);
        header.setText("header");

        recyclerview.addHeaderView(header);

        TextView footer = new TextView(this);
        footer.setText("footer");

        recyclerview.addFooterView(footer);


    }


    private class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        private List<Integer> data;

        public MyAdapter(List<Integer> data) {
            this.data = data;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(new TextView(parent.getContext()));
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.textView.setText(""+position);
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }


    private class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView;
        }
    }

}
