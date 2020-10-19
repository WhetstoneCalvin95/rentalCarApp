package edu.txstate.e_e106.rentalcarappajax;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.TextHttpResponseHandler;

import java.text.DecimalFormat;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

public class UpdateRentActivity extends AppCompatActivity {

    private int updateCarID;
    private String updateCarName;
    private int updateCarPosition;
    DecimalFormat twoDecimalPlaces = new DecimalFormat("###,##0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_rent);

        final TextView idDisplay = findViewById(R.id.txtUpdateCarID);
        final TextView nameDisplay = findViewById(R.id.txtUpdateCarName);
        final EditText rentalCostInput = findViewById(R.id.editNewRentCost);
        Button updateRentButton = findViewById(R.id.btnUpdateRentalCost);
        Button goBackHome = findViewById(R.id.homeButton);

        SharedPreferences sharedPref3 = PreferenceManager.getDefaultSharedPreferences(this);
        updateCarID = sharedPref3.getInt("id", 0);
        updateCarName = sharedPref3.getString("name", "No name found.");
        updateCarPosition = sharedPref3.getInt("pos", 0);

        idDisplay.setText("ID: " + updateCarID);
        nameDisplay.setText("Name: " + updateCarName);

        updateRentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "rental-cars/" +updateCarPosition+ "/rent.json";
                StringEntity entity = null;
                try {
                    String rentText = rentalCostInput.getText().toString();
                    float newRent;

                    if(rentText.equals("")) {
                        Toast.makeText(UpdateRentActivity.this, "Please enter a rental cost.", Toast.LENGTH_SHORT).show();
                        return;
                    }else {
                        newRent = Float.parseFloat(rentText);
                        entity = new StringEntity("" + twoDecimalPlaces.format(newRent));
                    }

                }  catch(Exception ex) {
                    ex.printStackTrace();
                }
                entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/text"));
                CarRestClient.put(UpdateRentActivity.this, url, entity,
                        "application/text", new TextHttpResponseHandler(){
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                                Toast.makeText(UpdateRentActivity.this, "success", Toast.LENGTH_LONG).show();

                            }
                            @Override
                            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                Toast.makeText(UpdateRentActivity.this, responseString, Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });

        goBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UpdateRentActivity.this, MainActivity.class));
            }
        });

    }
}
