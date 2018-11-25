package com.example.johnson.priceadvvisorbeta;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {
    DatabaseHelper helper = new DatabaseHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);



        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Set the register form
        Button UserRegisterS = (Button) findViewById(R.id.register_in_button);
        Button UserLogin = (Button) findViewById(R.id.lg_button);

        UserLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (v.getId() == R.id.lg_button) {
                    //startActivity(new Intent(Register.this, MainActivity.class));

                    finish();

                }
            }

    });

        UserRegisterS.setOnClickListener(new View.OnClickListener() {
            //@Override
            public void onClick(View v) {
                if (v.getId() == R.id.register_in_button) {

                    EditText name = (EditText) findViewById(R.id.nameReg);
                    EditText phone = (EditText) findViewById(R.id.phoneReg);
                    EditText email = (EditText) findViewById(R.id.emailReg);
                    EditText pass = (EditText) findViewById(R.id.passwordReg);
                    EditText pass1 = (EditText) findViewById(R.id.passwordConfirmReg);

                    String namestr = name.getText().toString();
                    String phonstr = phone.getText().toString();
                    String emailstr = email.getText().toString();
                    String passstr = pass.getText().toString();
                    String pass1str = pass1.getText().toString();

                    //email check if exist
                    String emailDb = helper.dbCheck(emailstr);
                    //phone number check
                    String phoneDb = helper.dbCheckPhone(phonstr);
                    boolean error = false;
                    View focusView = null;
                    name.setError(null);
                    phone.setError(null);
                    email.setError(null);
                    pass.setError(null);
                    pass1.setError(null);
                    // password validation
                    if (TextUtils.isEmpty(passstr)) {
                        pass.setError(getString(R.string.error_field_required));
                        focusView = pass;
                        error = true;
                    } else {
                        if (!passstr.equals(pass1str)) {
                            //pop up message
                            pass1.setError("password doesnt match");
                            focusView = pass1;
                            error = true;
                        } else {
                            AccountSection len = new AccountSection();
                            if (!len.isPasswordValid(pass1str)) {
                                pass1.setError("Password too short (5)");
                                focusView = pass1;
                                error = true;
                            }
                        }
                    }
                    //password check if empty
                    if (TextUtils.isEmpty(pass1str)) {
                        pass1.setError(getString(R.string.error_field_required));
                        focusView = pass1;
                        error = true;
                    } else {


                    }


                    //email validation
                    if (TextUtils.isEmpty(emailstr)) {
                        email.setError(getString(R.string.error_field_required));
                        focusView = email;
                        error = true;
                    } else {
                        AccountSection e = new AccountSection();
                        if (!e.isEmailValid(emailstr)) {
                            email.setError("Invalid email");
                            focusView = email;
                            error = true;

                        } else {
                            if (emailstr.equals(emailDb)) {
                                email.setError("Email already taken try another one");
                                focusView = email;
                                error = true;
                            }
                        }
                    }
                    //phone number
                    if (TextUtils.isEmpty(phonstr)) {
                        phone.setError(getString(R.string.error_field_required));
                        focusView = phone;
                        error = true;
                    } else {
                        if (!isPhoneValid(phonstr)) {
                            phone.setError("Phone number not valid");
                            focusView = phone;
                            error = true;
                        } else {
                            if (phonstr.equals(phoneDb)) {
                                phone.setError("Phone number aleady taken");
                                focusView = phone;
                                error = true;
                            }
                        }
                    }

                    //name validation
                    if (TextUtils.isEmpty(namestr)) {
                        name.setError(getString(R.string.error_field_required));
                        focusView = name;
                        error = true;
                    } else {
                        if (!isNameValid(namestr)) {
                            name.setError("Name too short");
                            focusView = name;
                            error = true;
                        }

                    }
                    //zero errors in the form

                    if (error) {
                        focusView.requestFocus();
                    } else {
                        //insert details to db
                        Contact c = new Contact();
                        c.setName(namestr);
                        c.setPhone(phonstr);
                        c.setEmail(emailstr);
                        c.setPassword(pass1str);
                        helper.insertContact(c);
                        Toast regSuc = Toast.makeText(Register.this, "Registration successful.. login here", Toast.LENGTH_SHORT);
                        regSuc.show();
                        // LoginActivity sp =new LoginActivity();
                        //sp.showProgress(true);*/
                        Bundle tab = new Bundle();

                        Intent i =  new Intent(Register.this, MainActivity.class);
                        tab.putInt("tab", 0);
                        i.putExtras(tab);
                        startActivity(i);
                    }
                }


            }


        });

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }


        return super.onOptionsItemSelected(item);
    }
    public boolean isPhoneValid(String phone) {
        //TODO: Replace this with your own logic
        return phone.length() > 9;
    }
    public boolean isNameValid(String name) {
        //TODO: Replace this with your own logic
        return name.length() > 2;
    }


}
