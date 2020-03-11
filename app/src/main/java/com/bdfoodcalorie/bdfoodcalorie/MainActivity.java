package com.bdfoodcalorie.bdfoodcalorie;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private Spinner foodSpinner,weightSpinner;
    private ListView ItemlistView;
    String[] foodName;
    private Button addButton;
    private Button resetButton;

    //ListView Array
    ArrayList<String> addArrayList;
    ArrayList<String> CalArrayList;
    ArrayList<String> weightArrayList;
    ArrayList<String> fatArrayList;
    ArrayList<String> proeinArrayList;

    /// Item Array
    public static String[] name_array = new String[19];
    public static Double[] cal_array = new Double[19];
    public static Double[] fat_array = new Double[19];
    public static Double[] protein_array = new Double[19];

    // Total counter
    public static Double totalCounter= 0.0;
    public static Double totalFat = 0.0;
    public static Double totalProtein = 0.0;


    public static TextView resultCal ;
    public static TextView resultFat ;
    public static TextView resultProtein ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /// Result Board id
        resultCal = findViewById(R.id.totalCalorieResult);
        resultFat = findViewById(R.id.fatResult);
        resultProtein = findViewById(R.id.proteinsResult);


        // Initial array
        InititalItem();

        /// Navigation

        drawerLayout = findViewById(R.id.drawerLayout);
        toggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = findViewById(R.id.navigationBarId);
        navigationView.setNavigationItemSelectedListener(this);
        /// Navigation Click Listener line no 207


        ///food Spinner
        foodSpinner = findViewById(R.id.foodListSpinner);
       // ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.food_name,R.layout.support_simple_spinner_dropdown_item);
        ArrayAdapter<String> adapter =new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,name_array);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        foodSpinner.setAdapter(adapter);

        //

        /// WeightSpinner
        weightSpinner = findViewById(R.id.weightListSpinner);0
       // ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,R.array.number,R.layout.support_simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,R.array.number,R.layout.support_simple_spinner_dropdown_item);
        adapter1.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        weightSpinner.setAdapter(adapter1);


        /// Added list
        addArrayList = new ArrayList<String>();
        ItemlistView = findViewById(R.id.list_itemId);
      //  foodName = getResources().getStringArray(R.array.planets_array);

        final CustomAdapter customAdapter = new CustomAdapter(this,addArrayList,weightArrayList,CalArrayList);
        ItemlistView.setAdapter(customAdapter);


        /// Add button
        addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = foodSpinner.getSelectedItem().toString();
                String weight = weightSpinner.getSelectedItem().toString();
                Toast.makeText(MainActivity.this,name+" "+weight,Toast.LENGTH_SHORT).show();


                ///

                /// find Item index
                int index = FindItemIndex(name);

                totalCounter+=cal_array[index];
                totalFat+=fat_array[index];
                totalProtein+=protein_array[index];

                resultCal.setText(CutPrecision(totalCounter.toString()));
                resultFat.setText(CutPrecision(totalFat.toString()));
                resultProtein.setText(CutPrecision(totalProtein.toString()));

                // added list update
                String space1 = "";
                for(int i = 0 ; i < 25-name.length() ;i++ ){
                    space1+=" ";
                }
                addArrayList.add(name+space1+weight+"g"+space1+cal_array[index]);
                Log.d("weight",weight);
               //weightArrayList.add(weight);
              //  Double tmp = cal_array[index];
             //   CalArrayList.add(" "+cal_array[index]);
                customAdapter.notifyDataSetChanged();


            }
        });

        // reset Button
        resetButton = findViewById(R.id.resetButton);

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                totalCounter=0.0;
                totalFat=0.0;
                totalProtein=0.0;

                resultCal.setText(CutPrecision(totalCounter.toString()));
                resultFat.setText(CutPrecision(totalFat.toString()));
                resultProtein.setText(CutPrecision(totalProtein.toString()));

                addArrayList.clear();
                customAdapter.notifyDataSetChanged();
            }
        });



    }

    public void totalResultDataChange(){
        TextView resultCal = findViewById(R.id.totalCalorieResult);
        TextView resultFat = findViewById(R.id.fatResult);
        TextView resultProtein = findViewById(R.id.proteinsResult);

        Double totalCal = Double.valueOf(resultCal.getText().toString());
        Double totalFat = Double.valueOf(resultFat.getText().toString());
        Double totalProtein = Double.valueOf(resultProtein.getText().toString());


    }


    // set precision point 2
    public static String CutPrecision(String str){
        String tmp="";
        int c=0;
        boolean flag = false;
        for(int i = 0 ; i < str.length() ; i++){
            if(str.charAt(i)=='.'){
                flag =true;
            }
            if(flag){
                c++;
            }
            if(c> 2){
                return tmp;
            }
            tmp+=str.charAt(i);
        }
        return  tmp;

    }


    public static int FindItemIndex(String name){
        for(int i = 0 ; i < 19 ; i++){
            if(name_array[i]==name){
                return i;
            }
        }
        return 0;
    }



    public void InititalItem(){

        name_array[0] = "Walnut"; cal_array[0] = Double.valueOf(2938); fat_array[0] = Double.valueOf(69.2); protein_array[0] = Double.valueOf(15.9);
        name_array[1] = "Rice"; cal_array[1] = Double.valueOf(1620); fat_array[1] = Double.valueOf(3.2); protein_array[1] = Double.valueOf(7.9);
        name_array[2] = "Spinach"; cal_array[2] = Double.valueOf(97); fat_array[2] = Double.valueOf(0.4); protein_array[2] = Double.valueOf(2.9);
        name_array[3] = "Apple"; cal_array[3] = Double.valueOf(218); fat_array[3] = Double.valueOf(0.17); protein_array[3] = Double.valueOf(0.26);
        name_array[4] = "Banana"; cal_array[4] = Double.valueOf(371); fat_array[4] = Double.valueOf(0.33); protein_array[4] = Double.valueOf(1.09);
        name_array[5] = "Peanut_Butter"; cal_array[5] = Double.valueOf(2580); fat_array[5] = Double.valueOf(50); protein_array[5] = Double.valueOf(28);
        name_array[6] = "Avocado"; cal_array[6] = Double.valueOf(670); fat_array[6] = Double.valueOf(14.66); protein_array[6] = Double.valueOf(2);
        name_array[7] = "Lentils"; cal_array[7] = Double.valueOf(290); fat_array[7] = Double.valueOf(0.9); protein_array[7] = Double.valueOf(4.2);
        name_array[8] = "Beans"; cal_array[8] = Double.valueOf(438); fat_array[8] = Double.valueOf(0.8); protein_array[8] = Double.valueOf(7.4);
        name_array[9] = "Orange"; cal_array[9] = Double.valueOf(192); fat_array[9] = Double.valueOf(0.12); protein_array[9] = Double.valueOf(0.94);
        name_array[10] = "Broccoli"; cal_array[10] = Double.valueOf(141); fat_array[10] = Double.valueOf(0.37); protein_array[10] = Double.valueOf(2.82);
        name_array[11] = "Sweet_Potato"; cal_array[11] = Double.valueOf(359); fat_array[11] = Double.valueOf(0.1); protein_array[11] = Double.valueOf(1.6);
        name_array[12] = "Cucumber"; cal_array[12] = Double.valueOf(65); fat_array[12] = Double.valueOf(0.11); protein_array[12] = Double.valueOf(0.65);
        name_array[13] = "Mushrooms"; cal_array[13] = Double.valueOf(113); fat_array[13] = Double.valueOf(0.1); protein_array[13] = Double.valueOf(2.5);
        name_array[14] = "Almonds"; cal_array[14] = Double.valueOf(2575); fat_array[14] = Double.valueOf(53.5); protein_array[14] = Double.valueOf(24.1);
        name_array[15] = "Biryani"; cal_array[15] = Double.valueOf(250); fat_array[15] = Double.valueOf(43); protein_array[15] = Double.valueOf(55.3);
        name_array[16] = "Meat"; cal_array[16] = Double.valueOf(143); fat_array[16] = Double.valueOf(3.5); protein_array[16] = Double.valueOf(26);
        name_array[17] = "Chicken"; cal_array[17] = Double.valueOf(165); fat_array[17] = Double.valueOf(3.6); protein_array[17] = Double.valueOf(31);
        name_array[18] = "Jack_Fruit"; cal_array[18] = Double.valueOf(95); fat_array[18] = Double.valueOf(0.6); protein_array[18] = Double.valueOf(1.7);

    }


    //Navigation
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(toggle.onOptionsItemSelected(item)){
            return  true;
        }

        return super.onOptionsItemSelected(item);
    }

    /// Navigation Click Listener
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id  = item.getItemId();

        if(id == R.id.footstep) {
            Toast.makeText(this, "Foot Step click", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, FootStepMeasure.class);
           // Log.d("Main", "line 219");
            startActivity(intent);

        }else if(id == R.id.homeOptionId){

        }

        return false;
    }
}
