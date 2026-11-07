package com.example.excessbaggage;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    EditText etWeight, etRate;
    RadioButton rbEconomy;
    Button btnCalculate;
    TextView tvExcess, tvCharge, tvTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etWeight = findViewById(R.id.etWeight);
        etRate = findViewById(R.id.etRate);
        rbEconomy = findViewById(R.id.rbEconomy);
        btnCalculate = findViewById(R.id.btnCalculate);
        tvExcess = findViewById(R.id.tvExcess);
        tvCharge = findViewById(R.id.tvCharge);
        tvTotal = findViewById(R.id.tvTotal);

        btnCalculate.setOnClickListener(v -> {
            String weightStr = etWeight.getText().toString();
            String rateStr = etRate.getText().toString();

            if (weightStr.isEmpty() || rateStr.isEmpty()) {
                return;
            }

            double weight = Double.parseDouble(weightStr);
            double rate = Double.parseDouble(rateStr);

            int x = rbEconomy.isChecked() ? 20 : 40;

            double excess = Math.max(0, weight - x);
            double charge = excess * rate;
            double total = charge + (0.08 * charge);

            tvExcess.setText(getString(R.string.excess_weight_format, excess));
            tvCharge.setText(getString(R.string.excess_charge_format, String.format(Locale.getDefault(), "%.2f", charge)));
            tvTotal.setText(getString(R.string.total_payable_format, String.format(Locale.getDefault(), "%.2f", total)));
        });
    }

    // ----- Menu di title bar (Share + About) -----
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_share) {
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("text/plain");
            share.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_message));
            startActivity(Intent.createChooser(share, getString(R.string.share_title)));
            return true;
        } else if (item.getItemId() == R.id.action_about) {
            startActivity(new Intent(this, AboutActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
