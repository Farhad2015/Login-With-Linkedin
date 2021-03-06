package com.ezzetech.linkedintest;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.linkedin.platform.LISessionManager;
import com.linkedin.platform.errors.LIAuthError;
import com.linkedin.platform.listeners.AuthListener;
import com.linkedin.platform.utils.Scope;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

//    public static final String PACKAGE = "com.ezzetech.linkedintest";

    Button buttonLogin;
    long startTime,endTime;
    Button buttonTestEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        generateHashkey();
        buttonLogin = (Button)findViewById(R.id.login_button);
        if(Build.VERSION.SDK_INT >= 19){
            buttonLogin.setVisibility(View.VISIBLE);
        }else {
            buttonLogin.setVisibility(View.GONE);
        }
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        buttonTestEvent = (Button)findViewById(R.id.test_event);
        buttonTestEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginWithG.class);
                startActivity(intent);
            }
        });
       // addaneventCalender();
    }


    public void login(){
        LISessionManager.getInstance(getApplicationContext()).init(this, buildScope(), new AuthListener() {
            @Override
            public void onAuthSuccess() {

                // Toast.makeText(getApplicationContext(), "success" + LISessionManager.getInstance(getApplicationContext()).getSession().getAccessToken().toString(), Toast.LENGTH_LONG).show();

            }

            @Override
            public void onAuthError(LIAuthError error) {

                Toast.makeText(getApplicationContext(), "failed " + error.toString(),
                        Toast.LENGTH_LONG).show();
            }
        }, true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        LISessionManager.getInstance(getApplicationContext()).onActivityResult(this, requestCode, resultCode, data);
        Intent intent = new Intent(MainActivity.this, HomePage.class);
        startActivity(intent);
    }

    // This method is used to make permissions to retrieve data from linkedin

    private static Scope buildScope() {
        return Scope.build(Scope.R_BASICPROFILE, Scope.R_EMAILADDRESS);
    }

//    public void generateHashkey() {
//        try {
//            PackageInfo info = getPackageManager().getPackageInfo(
//                    PACKAGE,
//                    PackageManager.GET_SIGNATURES);
//            for (Signature signature : info.signatures) {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//
//                textView = (TextView) findViewById(R.id.hashKey);
//                textView.setText(Base64.encodeToString(md.digest(), Base64.NO_WRAP));
//
//                Log.d("KESSTRING", textView.getText().toString());
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//            Log.d("Name not found", e.getMessage(), e);
//
//        } catch (NoSuchAlgorithmException e) {
//            Log.d("Error", e.getMessage(), e);
//        }
//    }

    private void addaneventCalender(){

        String startDate = "2016-12-22";
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
            startTime=date.getTime();
        }
        catch(Exception e){ }

        Calendar cal = Calendar.getInstance();
        Intent intent = new Intent(Intent.ACTION_EDIT);
        intent.setType("vnd.android.cursor.item/event");
        intent.putExtra("beginTime",startTime);
        intent.putExtra("allDay", true);
        //intent.putExtra("rrule", "FREQ=YEARLY");
        intent.putExtra("endTime", endTime);
        intent.putExtra("title", "A Test Event from android app in demo linkedin");
        startActivity(intent);
    }
}
