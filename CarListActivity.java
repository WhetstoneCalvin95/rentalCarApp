package edu.txstate.e_e106.rentalcarappajax;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.message.BasicHeader;

public class CarListActivity extends ListActivity {

    List<RentalCar> rentalCars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getCars();
    }
    protected void getCars(){
        List<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader("Accept", "application/json"));
        CarRestClient.get(CarListActivity.this,"rental-cars.json", headers.toArray(new Header[headers.size()]), null,new JsonHttpResponseHandler(){
            public void onSuccess(int statusCode, Header[] headers, JSONArray response){
                rentalCars = new ArrayList<RentalCar>();
                for(int i = 0; i < response.length(); i++){
                    try{
                        rentalCars.add(new RentalCar(response.getJSONObject(i)));
                    }catch(Exception ex){ex.printStackTrace();}
                }
                setListAdapter(new ArrayAdapter<RentalCar>(CarListActivity.this,R.layout.activity_car_list,R.id.carListEntity, rentalCars));
            }
        });
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        RentalCar selectedCar = rentalCars.get(position);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(CarListActivity.this);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("id", selectedCar.getId());
        editor.putString("brand", selectedCar.getBrand());
        editor.putString("color", selectedCar.getColor());
        editor.putString("name", selectedCar.getName());
        editor.putFloat("rent", selectedCar.getRentCost());
        editor.putInt("pos", position);
        editor.commit();

        startActivity(new Intent(CarListActivity.this, CarDetailsActivity.class));
    }
}
