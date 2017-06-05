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
import android.support.annotation.IdRes;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.text.DecimalFormat;

public class PizzaActivity extends AppCompatActivity {

    DecimalFormat df = new DecimalFormat("#0.00");

    private float mTip = 0.0f;
    private float mPizzaPrice;
    private float mServiceTip;
    private float mIsBadWeather;
    private float mIsReallyBadWeather;
    private float mIsMoreThanThreeMiles;
    private float mIsMoreThanFiveMiles;
    private float mPoor;
    private float mCommon;
    private float mExcellent;
    private int mCurrentRadioButton;

    private EditText mGetPizzaPrice;
    private RadioGroup mServiceRadioGroup;
    private RadioButton mPoorRadioButton;
    private RadioButton mCommonRadioButton;
    private RadioButton mExcellentRadioButton;
    private CheckBox mBadWeather;
    private CheckBox mReallyBadWeather;
    private CheckBox mThreeMiles;
    private CheckBox mFiveMiles;
    private CheckBox mMinTip;
    private CheckBox mLargeOrder;
    private EditText mTotalTip;
    private EditText mGrandTotal;

    private static final String TAG = "PizzaActivity";

    public static Intent newIntent(Context packageContext) {
        Intent i = new Intent(packageContext, PizzaActivity.class);
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pizza);

        mGetPizzaPrice = (EditText) findViewById(R.id.pizzaEditText);

        mGetPizzaPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String checkNull = mGetPizzaPrice.getText().toString();
                if(checkNull != null && !checkNull.isEmpty()){
                    mPizzaPrice = Float.valueOf(checkNull);
                } else {
                    mPizzaPrice = 0;
                }
                if(mPizzaPrice >= 100){
                    mLargeOrder.setChecked(true);
                    mPoorRadioButton.setText("Poor 5%");
                    mCommonRadioButton.setText("Common 10%");
                    mExcellentRadioButton.setText("Excellent 15%");
                    mPoor = 0.05f;
                    mCommon = 0.10f;
                    mExcellent = 0.15f;
                }
                else {
                    mLargeOrder.setChecked(false);
                    mPoorRadioButton.setText("Poor 10%");
                    mCommonRadioButton.setText("Common 15%");
                    mExcellentRadioButton.setText("Excellent 20%");
                    mPoor = 0.10f;
                    mCommon = 0.15f;
                    mExcellent = 0.20f;
                }
                calculateServiceTip(mCurrentRadioButton);
                checkMinTip();
                displayTipAndTotal();
            }
        });

        mServiceRadioGroup = (RadioGroup) findViewById(R.id.serviceRadioGroup);

        mServiceRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                calculateServiceTip(checkedId);

                checkMinTip();
                displayTipAndTotal();
            }
        });
        // find the radio button by returned id
        mPoorRadioButton = (RadioButton) findViewById(R.id.poorRadioButton);
        mCommonRadioButton = (RadioButton) findViewById(R.id.commonRadioButton);
        mExcellentRadioButton = (RadioButton) findViewById(R.id.excellentRadioButton);

        mBadWeather = (CheckBox) findViewById(R.id.badWeatherCheckbox);
        mBadWeather.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mIsBadWeather = 1;
                }
                else {
                    mIsBadWeather = 0;
                }
                calculateServiceTip(mCurrentRadioButton);
                checkMinTip();
                displayTipAndTotal();
            }
        });

        mReallyBadWeather = (CheckBox) findViewById(R.id.reallyBadWeatherCheckbox);
        mReallyBadWeather.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mIsReallyBadWeather = 2;
                }
                else {
                    mIsReallyBadWeather = 0;
                }
                calculateServiceTip(mCurrentRadioButton);
                checkMinTip();
                displayTipAndTotal();
            }
        });

        mThreeMiles = (CheckBox) findViewById(R.id.threeMileCheckbox);
        mThreeMiles.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mIsMoreThanThreeMiles = 1;
                }
                else {
                    mIsMoreThanThreeMiles = 0;
                }
                calculateServiceTip(mCurrentRadioButton);
                checkMinTip();
                displayTipAndTotal();
            }
        });

        mFiveMiles = (CheckBox) findViewById(R.id.fiveMileCheckbox);
        mFiveMiles.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mIsMoreThanFiveMiles = 2;
                }
                else {
                    mIsMoreThanFiveMiles = 0;
                }
                calculateServiceTip(mCurrentRadioButton);
                checkMinTip();
                displayTipAndTotal();
            }
        });


        mMinTip = (CheckBox) findViewById(R.id.minimumCheckbox);
        mMinTip.setClickable(false);

        mLargeOrder = (CheckBox) findViewById(R.id.largeOrderCheckbox);
        mLargeOrder.setClickable(false);

        mTotalTip = (EditText) findViewById(R.id.pizzaTipEditText);

        mGrandTotal = (EditText) findViewById(R.id.pizzaTotalEditText);
        checkLargeOrder();

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

    private void checkLargeOrder() {
        if (mPizzaPrice >= 100) {

        }
    }
    private void calculateServiceTip(int tipId){
        mCurrentRadioButton = tipId;
        if(tipId == mPoorRadioButton.getId()){
            mServiceTip = mPizzaPrice * mPoor;
        }
        else if(tipId == mCommonRadioButton.getId()){
            mServiceTip = mPizzaPrice * mCommon;
        }
        else if(tipId == mExcellentRadioButton.getId()){
            mServiceTip = mPizzaPrice * mExcellent;
        }
    }

    private void displayTipAndTotal(){
        mTip = calculateTip(mServiceTip, mIsBadWeather, mIsReallyBadWeather, mIsMoreThanThreeMiles, mIsMoreThanFiveMiles);
        /*mTotalTip.setText(Float.toString(mTip));
        mGrandTotal.setText(Float.toString(mPizzaPrice + mTip));*/
        mTotalTip.setText("$"+df.format(mTip));
        mGrandTotal.setText("$"+df.format(mPizzaPrice + mTip));
    }


    private void checkMinTip() {
        if (mServiceTip < 3) {
            if(mPizzaPrice != 0){
                mMinTip.setChecked(true);
                mServiceTip = 3;
            }
            else if (mPizzaPrice == 0){
                mMinTip.setChecked(false);
                mServiceTip = 0;
            }
        }
        else {
            mMinTip.setChecked(false);
        }
    }

    private float calculateTip(float service, float badWeather, float reallyBadWeather, float threeMiles, float fiveMiles) {
        float mTipAmount = 0.0f;
        mTipAmount = mTipAmount + service + badWeather + reallyBadWeather + threeMiles + fiveMiles;
        return mTipAmount;
    }
}