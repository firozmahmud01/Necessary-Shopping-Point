package com.firoz.mahmud.necessaryshoppingpoint;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class UserDetails extends AppCompatActivity {
    //variables
    String payment;
    SharedPreferences sp;
    SharedPreferences.Editor spe;





    //interface
    EditText name,email,phone,address;
    RadioGroup group;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);



        //interface
        name=findViewById(R.id.user_details_user_name_edittext);
        email=findViewById(R.id.user_details_email_edittext);
        phone=findViewById(R.id.user_details_phone_number_edittext);
        address=findViewById(R.id.user_details_address_edittext);
        group=findViewById(R.id.user_details_payment_radio_group);






        //variables
        payment=BuyerItem.paymentTypeCashOndelevary;
        sp=getSharedPreferences(KeyF.sharedprefarence.databasename,MODE_PRIVATE);
        spe=sp.edit();





        //setting data
        name.setText(sp.getString(KeyF.sharedprefarence.usernamekey,""));
        email.setText(sp.getString(KeyF.sharedprefarence.emailkey,""));
        phone.setText(sp.getString(KeyF.sharedprefarence.numberkey,""));
        address.setText(sp.getString(KeyF.sharedprefarence.addresskey,""));



        spe.putBoolean(KeyF.sharedprefarence.isbuyingkey,true);
        spe.commit();







        //saving new data
        Intent intent=getIntent();
        if (intent.getStringExtra(KeyF.sharedprefarence.namekey)!=null){
            spe.putString(KeyF.sharedprefarence.detailskey,intent.getStringExtra(KeyF.sharedprefarence.detailskey));
            Set<String>s=new HashSet<>();
            s.addAll(Arrays.asList(intent.getStringArrayExtra(KeyF.sharedprefarence.picurlkey)));
            spe.putStringSet(KeyF.sharedprefarence.picurlkey,s);
            spe.putString(KeyF.sharedprefarence.namekey,intent.getStringExtra(KeyF.sharedprefarence.namekey));
            spe.putString(KeyF.sharedprefarence.prizekey,intent.getStringExtra(KeyF.sharedprefarence.prizekey));
            spe.putString(KeyF.sharedprefarence.productcatagorykey,intent.getStringExtra(KeyF.sharedprefarence.productcatagorykey));
            spe.putString(KeyF.sharedprefarence.productidkey,intent.getStringExtra(KeyF.sharedprefarence.productidkey));
            spe.commit();
        }



        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId==R.id.user_details_bkash_radiobutton){
                    payment=BuyerItem.paymentTypebkash;
                }else{
                    payment=BuyerItem.paymentTypeCashOndelevary;
                }
            }
        });







        findViewById(R.id.user_details_done_flotingaction_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkingValidity()){
                    return;
                }





                spe.putString(KeyF.sharedprefarence.usernamekey,name.getText().toString());
                spe.putString(KeyF.sharedprefarence.emailkey,email.getText().toString());
                spe.putString(KeyF.sharedprefarence.numberkey,phone.getText().toString());
                spe.putString(KeyF.sharedprefarence.addresskey,address.getText().toString());
                spe.putString(KeyF.sharedprefarence.paymentkey,payment);
                spe.commit();



                startActivity(new Intent(UserDetails.this,BkashDetails.class));
            }
        });


    }

    private boolean checkingValidity(){
        if (name.getText().toString().isEmpty()){
            name.setError("Submit your name");
            return true;
        }
        if (email.getText().toString().isEmpty()){
            email.setError("Submit your email");
            return true;
        }
        if (phone.getText().toString().isEmpty()){
            phone.setError("Submit your phone number");
            return true;
        }
        if (address.getText().toString().isEmpty()){
            address.setError("Submit your address");
            return true;
        }

        return false;
    }







    @Override
    public void onBackPressed() {
        spe.putBoolean(KeyF.sharedprefarence.isbuyingkey,false);
        spe.commit();
        if (getIntent().getStringExtra(KeyF.sharedprefarence.namekey)==null){
            startActivity(new Intent(UserDetails.this,Home.class));
            finish();
        }else {
            super.onBackPressed();
        }
    }
}