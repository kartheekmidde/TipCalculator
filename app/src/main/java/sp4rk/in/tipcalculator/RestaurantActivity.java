package sp4rk.in.tipcalculator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DecimalFormat;

public class RestaurantActivity extends AppCompatActivity {

    DecimalFormat df = new DecimalFormat("#0.00");

    private EditText mBillEditText;
    private EditText mTotalTipEditText;
    private EditText mTaxEditText;
    private EditText mTotalEditText;
    private EditText mSplitAmountEditText;
    private SeekBar mTipSeekBar;
    private TextView mPercentTextView;
    private Spinner mSplitSpinner;

    private double totBillPreTax = 0.0;
    private int seekValue = 16;
    private double total_tip_amount = 0.0;
    private double total_tax = 0;
    private double grand_total = 0;
    private int split_spinner_value = 1;

    public static Intent newIntent(Context packageContext) {
        Intent i = new Intent(packageContext, RestaurantActivity.class);
        return i;
    }

    private double updateTipAmount(double totBillPreTax, double seekValue){
        double total_tip_amount = totBillPreTax*(seekValue/100);
        return total_tip_amount;
    }

    private double updateGrandTotal(double totBillPreTax, double total_tip_amount, double total_tax){
        double grand_total = totBillPreTax + total_tip_amount + total_tax;
        return grand_total;
    }

    private double updateSplitAmount(double grand_total, double split_spinner_value){
        double split_amount = grand_total/split_spinner_value;
        return split_amount;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        mBillEditText = (EditText) findViewById(R.id.billEditText);
        mPercentTextView = (TextView) findViewById(R.id.percentTextView);

        mTipSeekBar = (SeekBar)findViewById(R.id.tipSeekBar);
        mTipSeekBar.setMax(10);
        mTipSeekBar.incrementProgressBy(1);
        seekValue = mTipSeekBar.getProgress() + 15;

        mTotalTipEditText = (EditText) findViewById(R.id.totalTipEditText);
        mTaxEditText = (EditText) findViewById(R.id.taxEditText);
        mTotalEditText = (EditText) findViewById(R.id.totalEditText);

        mSplitSpinner = (Spinner) findViewById(R.id.splitSpinner);
        mSplitAmountEditText = (EditText) findViewById(R.id.splitAmountEditText);

        totBillPreTax = Double.parseDouble(mBillEditText.getText().toString());
        total_tip_amount = Double.parseDouble(mTotalTipEditText.getText().toString().replace("$",""));
        total_tax = Double.parseDouble(mTaxEditText.getText().toString());
        grand_total = totBillPreTax + total_tip_amount + total_tax;
        split_spinner_value = Integer.parseInt(mSplitSpinner.getSelectedItem().toString());

        mBillEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String mBET = mBillEditText.getText().toString();
                if(mBET != null && !mBET.isEmpty()){
                    totBillPreTax = Double.parseDouble(mBET);
                } else {
                    totBillPreTax = 0;
                }
                double updatedTipAmount = updateTipAmount(totBillPreTax,seekValue);
                mTotalTipEditText.setText("$"+df.format(updatedTipAmount));
                total_tip_amount = Double.parseDouble(mTotalTipEditText.getText().toString().replace("$",""));

                double updatedGrandTotal = updateGrandTotal(totBillPreTax, total_tip_amount, total_tax);
                mTotalEditText.setText("$"+df.format(updatedGrandTotal));
                grand_total = Double.parseDouble(mTotalEditText.getText().toString().replace("$",""));

                double updatedSplitAmount = updateSplitAmount(grand_total,split_spinner_value);
                mSplitAmountEditText.setText("$"+df.format(updatedSplitAmount));
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //mTotalEditText.setText(mBillEditText.getText());
            }

            @Override
            public void afterTextChanged(Editable s){
                //mTotalEditText.setText(mBillEditText.getText());
            }
        });

        mTipSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChanged = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                seekValue = progress + 15;
                mPercentTextView.setText(seekValue+"%");
                double updatedTipAmount = updateTipAmount(totBillPreTax,seekValue);
                mTotalTipEditText.setText("$"+df.format(updatedTipAmount));

                String mTTET = mTotalTipEditText.getText().toString().replace("$","");
                if(mTTET != null && !mTTET.isEmpty()){
                    total_tip_amount = Double.parseDouble(mTTET);
                } else {
                    total_tip_amount = 0;
                }
                double updatedGrandTotal = updateGrandTotal(totBillPreTax, total_tip_amount, total_tax);
                mTotalEditText.setText("$"+df.format(updatedGrandTotal));
                grand_total = Double.parseDouble(mTotalEditText.getText().toString().replace("$",""));

                double updatedSplitAmount = updateSplitAmount(grand_total,split_spinner_value);
                mSplitAmountEditText.setText("$"+df.format(updatedSplitAmount));
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        mTaxEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String mTET = mTaxEditText.getText().toString();
                if(mTET != null && !mTET.isEmpty()){
                    total_tax = Double.parseDouble(mTET);
                } else {
                    total_tax = 0;
                }
                double updatedGrandTotal = updateGrandTotal(totBillPreTax, total_tip_amount, total_tax);
                mTotalEditText.setText("$"+df.format(updatedGrandTotal));
                grand_total = Double.parseDouble(mTotalEditText.getText().toString().replace("$",""));

                double updatedSplitAmount = updateSplitAmount(grand_total,split_spinner_value);
                mSplitAmountEditText.setText("$"+df.format(updatedSplitAmount));
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s){
            }
        });

        mSplitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                split_spinner_value = Integer.parseInt(mSplitSpinner.getSelectedItem().toString());
                double updatedSplitAmount = updateSplitAmount(grand_total,split_spinner_value);
                mSplitAmountEditText.setText("$"+df.format(updatedSplitAmount));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}
