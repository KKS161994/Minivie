package com.test.rajat.minivie;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import db.LoginOpenHelper;

/**
 * Created by Rajat on 9/9/2015.
 */
public class SignupActivity extends ActionBarActivity {

    private Button btn_signup;
    private EditText et_email,et_password,et_cnfpassword;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        btn_signup=(Button) findViewById(R.id.btn_signup);
        et_email=(EditText) findViewById(R.id.signupemail);
        et_password=(EditText) findViewById(R.id.signuppassword);
        et_cnfpassword=(EditText) findViewById(R.id.cnfpassword);
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_email.getText().equals("")){
                    Toast.makeText(SignupActivity.this,"Email cannot be blank",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(et_password.getText().equals("")){
                    Toast.makeText(SignupActivity.this,"Password cannot be blank",Toast.LENGTH_SHORT).show();
                    return;
                }
//                if(et_cnfpassword.getText().equals("")){
//                    Toast.makeText(SignupActivity.this,"Confirm Password cannot be blank",Toast.LENGTH_SHORT).show();
//                    return;
//                }
                if(!isValidEmail(et_email.getText())){
                    Toast.makeText(SignupActivity.this,"Email address not valid",Toast.LENGTH_SHORT).show();
                    return;
                }
//                if(!et_cnfpassword.getText().equals(et_password.getText())){
//                   Toast.makeText(SignupActivity.this,"Passwords do not match",Toast.LENGTH_SHORT).show();
//                }
                SQLiteDatabase users= new LoginOpenHelper(SignupActivity.this).getWritableDatabase();
                ContentValues new_user=new ContentValues();
                new_user.put("email",et_email.getText().toString());
                new_user.put("password",et_password.getText().toString());
                users.insert("users",null,new_user);
                Toast.makeText(SignupActivity.this,"Successfully signed up",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SignupActivity.this,LoginActivity.class));
            }
        });


    }
    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }
}
