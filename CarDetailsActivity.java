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

import java.text.DecimalFormat;

public class CarDetailsActivity extends AppCompatActivity {

    private String carBrand, carColor, carName;
    private int carId;
    private float carRent;
    private int carPosition;
    public DecimalFormat dollars = new DecimalFormat("$###,##0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_details);

        final TextView idText = findViewById(R.id.txtID);
        final TextView brandText = findViewById(R.id.txtBrand);
        final TextView nameText = findViewById(R.id.txtName);
        final TextView colorText = findViewById(R.id.txtColor);
        final TextView rentText = findViewById(R.id.txtCost);

        SharedPreferences sharedPref2 = PreferenceManager.getDefaultSharedPreferences(this);
        carBrand = sharedPref2.getString("brand", "No brand found.");
        carColor = sharedPref2.getString("color", "No color found.");
        carName = sharedPref2.getString("name", "No name found.");
        carId = sharedPref2.getInt("id", 0);
        carRent = sharedPref2.getFloat("rent", 0);
        carPosition = sharedPref2.getInt("pos", 0);

        idText.setText("Id: " + carId);
        brandText.setText("Brand: " + carBrand);
        nameText.setText("Name: " + carName);
        colorText.setText("Color: " + carColor);
        rentText.setText("Rent per day: " + dollars.format(carRent));

        final TextView totalDisplay = findViewById(R.id.totalCostDisplay);
        final EditText rentDaysText = findViewById(R.id.editRentalDays);

        Button calcCost = findViewById(R.id.calcButton);
        calcCost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int numRentDays = Integer.parseInt(rentDaysText.getText().toString());
                    if (numRentDays > 30) {
                        totalDisplay.setText("Please call our representatives at: (512)777-2222");
                        Toast.makeText(CarDetailsActivity.this, "Number of rental days too large, please call our representatives.", Toast.LENGTH_LONG).show();
                    } else {
                        float totalCost = numRentDays * carRent;
                        totalDisplay.setText("Total Cost: " + dollars.format(totalCost));
                    }
                }catch(Exception ex){
                    totalDisplay.setText("No days entered, please try again.");
                    Toast.makeText(CarDetailsActivity.this, "Error occurred.", Toast.LENGTH_SHORT).show();
                }
                totalDisplay.setVisibility(View.VISIBLE);
            }
        });

        Button updateButton = findViewById(R.id.updateRentButton);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CarDetailsActivity.this, UpdateRentActivity.class));
            }
        });
    }
}
