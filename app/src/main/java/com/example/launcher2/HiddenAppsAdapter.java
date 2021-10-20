package com.example.launcher2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HiddenAppsAdapter extends RecyclerView.Adapter<HiddenAppsAdapter.ViewHolder> {

    private Context context;
    private ArrayList<AppObject> arrayList;

    public HiddenAppsAdapter(Context context, ArrayList<AppObject> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_layout_add,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(arrayList.get(position).getName());
        holder.image.setImageDrawable(arrayList.get(position).getImage());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.checkBox.setVisibility(View.INVISIBLE);
            }
        });

        holder.layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                holder.checkBox.setVisibility(View.VISIBLE);
                return true;
            }
        });



        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String name = arrayList.get(position).getName();
                if (isChecked){
                    ArrayList<String> List = PrefConfig.readList(context);
                    List.remove(name);
                    PrefConfig.writelist(context,List);
                    holder.layout.setAlpha(0.5f);

               }else{
                    ArrayList<String> List = PrefConfig.readList(context);
                    List.add(name);
                    PrefConfig.writelist(context,List);
                    holder.layout.setAlpha(1.0f);
                    holder.checkBox.setVisibility(View.INVISIBLE);
                }

            }
        });




    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private CheckBox checkBox;
        private TextView name;
        private LinearLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.allAppsImage);
            name = itemView.findViewById(R.id.allAppsName);
            layout= itemView.findViewById(R.id.allAppslayout);
            checkBox = itemView.findViewById(R.id.chechItem);
        }
    }

    public void removeAt(int position){
        String name = arrayList.get(position).getName();
        ArrayList<String> List = PrefConfig.readList(context);
        List.remove(name);
        PrefConfig.writelist(context,List);
        notifyDataSetChanged();
        notifyItemChanged(position);

    }
}
