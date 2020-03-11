package com.bdfoodcalorie.bdfoodcalorie;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by ibrahim on 4/8/18.
 */

public class CustomAdapter extends BaseAdapter {

    Context context;
    ArrayList<String> foodName;
    ArrayList<String> CalArrayList;
    ArrayList<String> weightArrayList;


    private LayoutInflater inflater;

    CustomAdapter(Context context , ArrayList<String> foodName,ArrayList<String> weightArrayList, ArrayList<String> CalArrayListList){
        this.context = context;
        this.foodName = foodName;
        this.weightArrayList = weightArrayList;
        this.CalArrayList = CalArrayList;

    }

    @Override
    public int getCount() {
        return foodName.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        if(view == null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            view = inflater.inflate(R.layout.add_item_layout,viewGroup,false);

        }
        TextView nameTextView = view.findViewById(R.id.itemNameId);
     //   TextView weightTextView = view.findViewById(R.id.itemWeightId);
        //TextView calTextView = view.findViewById(R.id.itemCalId);
        Button cancelButton = view.findViewById(R.id.cancelButton);

        nameTextView.setText(foodName.get(i));
      // weightTextView.setText(weightArrayList.get(i));
        //calTextView.setText(CalArrayList.get(i));

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = getName_From_foodName(foodName.get(i));

                Toast.makeText(context,"Cancel "+name,Toast.LENGTH_SHORT).show();

                /// find Item index
                int index = MainActivity.FindItemIndex(name);


                MainActivity.totalCounter-=MainActivity.cal_array[index];
                MainActivity.totalFat-=MainActivity.fat_array[index];
                MainActivity.totalProtein-=MainActivity.protein_array[index];
               // Log.d("Costomadapter","00 ");

                MainActivity.resultCal.setText(MainActivity.CutPrecision(MainActivity.totalCounter.toString()));
                MainActivity.resultFat.setText(MainActivity.CutPrecision(MainActivity.totalFat.toString()));
                MainActivity.resultProtein.setText(MainActivity.CutPrecision(MainActivity.totalProtein.toString()));



                foodName.remove(i);
                notifyDataSetChanged();

            }
        });

        return view;
    }

    protected String getName_From_foodName(String str){
        String name="";

        for(int i = 0 ; i < str.length() ; i++){
            if(str.charAt(i)==' '){
                break;
            }else{
                name+=str.charAt(i);
            }
        }
        return name;
    }

}
