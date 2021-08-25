package com.firoz.mahmud.necessaryshoppingpoint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.security.Key;

public class BkashDetails extends AppCompatActivity {
    //static
    public static String userbkashkey="UserBkashNumberKey";
    public static String userbkashidkey="UserBkashIdKye";





    //firebase
    DatabaseReference dr;






    //variables
    SharedPreferences sp;
    String bkashnumber;





    //interface
    ProgressDialog pd;
    TextView number,payment;
    EditText bnumber,bid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bkash_details);
        //firebase
        dr= FirebaseDatabase.getInstance().getReference(KeyF.firebase.clientSettings);
        dr.keepSynced(true);



        //interface
        bnumber=findViewById(R.id.bkash_details_user_bkash_number_edittext);
        bid=findViewById(R.id.bkash_details_bkash_id_edittext);
        number=findViewById(R.id.bkash_details_agentbkash_number_textview);
        pd=new ProgressDialog(this);
        pd.setCancelable(false);
        pd.setMessage("Please wait...");
        pd.show();
        payment=findViewById(R.id.bkashdetails_money_details_textview);
        sp=getSharedPreferences(KeyF.sharedprefarence.databasename,MODE_PRIVATE);




        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String amount=dataSnapshot.child(KeyF.firebase.delevaryamout).getValue(String.class);
                    int pamount=getOnlyNumber(sp.getString(KeyF.sharedprefarence.prizekey,"0"));
                if (sp.getString(KeyF.sharedprefarence.paymentkey,"").equals(BuyerItem.paymentTypeCashOndelevary)){
                    payment.setText("Send "+amount+" taka as advance\nusing this bkash number");
                }else{
                    payment.setText("Send "+(
                            Integer.valueOf(amount)+
                                    pamount
                    )+" taka\nProduct prize "+pamount+" taka and\ndelevary charge "+amount+" taka");
                }
                bkashnumber=dataSnapshot.child(KeyF.firebase.bkashnumber).getValue(String.class);
                number.setText(bkashnumber);
                pd.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





        findViewById(R.id.bkash_details_copy_textview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("Bkash number", bkashnumber);
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(BkashDetails.this, "Copied", Toast.LENGTH_SHORT).show();
                }catch (Exception e){}
            }
        });



        findViewById(R.id.bkash_details_done_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bnumber.getText().toString().isEmpty()){
                    bnumber.setError("Bkash number needed");
                    return;
                }
                if (bid.getText().toString().isEmpty()){
                    bid.setError("Fill it");
                    return;
                }
                final ProgressDialog pd=new ProgressDialog(BkashDetails.this);
                pd.setCancelable(false);
                pd.setMessage("Please wait...");
                pd.show();
                DatabaseReference d=FirebaseDatabase.getInstance().getReference(KeyF.firebase.buyerpath);
                String key=d.push().getKey();
                BuyerItem bi=new BuyerItem(key,sp.getString(KeyF.sharedprefarence.productcatagorykey,"")
                ,sp.getString(KeyF.sharedprefarence.productidkey,""),
                        bnumber.getText().toString(),bid.getText().toString(),
                        sp.getString(KeyF.sharedprefarence.usernamekey,"")
                ,sp.getString(KeyF.sharedprefarence.emailkey,""),
                        sp.getString(KeyF.sharedprefarence.numberkey,""),
                        sp.getString(KeyF.sharedprefarence.addresskey,"")
                ,sp.getString(KeyF.sharedprefarence.paymentkey,""));
                d.child(key).setValue(bi).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            pd.dismiss();
                            startActivity(new Intent(BkashDetails.this,PaymentConfirm.class));
                            finish();
                        }else{
                            pd.dismiss();
                            Toast.makeText(BkashDetails.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
    private int getOnlyNumber(String s){
        String result="";
        for (int x=0;x<s.length();x++){
            char c=s.charAt(x);
            if (c>='0'&&c<='9'){
                result+=c;
            }
        }
        return Integer.valueOf(result);
    }
}