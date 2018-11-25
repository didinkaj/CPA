package com.example.johnson.priceadvvisorbeta;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */

public class AccountSection extends Fragment implements View.OnClickListener {
    private UserLoginTask mAuthTask = null;
    // UI references.
    private AutoCompleteTextView mEmailView;
    private TextView mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    //private static final int REQUEST_READ_CONTACTS = 0;
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String emailSet = "emailKey";
    // public static final String passwordSet = "passwordKey";
    private SharedPreference sharedPreference;
    AccountSection context = this;

    View view;

    public AccountSection() {

        // Required empty public constructor


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_account_section, container, false);

        mLoginFormView = view.findViewById(R.id.loginform);
        mProgressView = view.findViewById(R.id.login_progress);

        DatabaseHelper dh = new DatabaseHelper(getContext());
        Integer km = dh.getDataUsersm();

        //Toast loginErr = Toast.makeText(getContext(), "mmmmmm"+km, Toast.LENGTH_SHORT);
       // loginErr.show();
        Log.d("VIVZ", "offsetVVVVVVVVVV" + km);


        mEmailView = (AutoCompleteTextView) view.findViewById(R.id.email);
        mPasswordView = (EditText) view.findViewById(R.id.password);
        // sharedpreferences = this.getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        sharedPreference = new SharedPreference();

        Button log = (Button) view.findViewById(R.id.login);
        log.setText("Login");
        log.setOnClickListener(this);
        Button log1 = (Button) view.findViewById(R.id.reg);
        //log1.setText("Register");
        log1.setOnClickListener(this);
        Button log2 = (Button) view.findViewById(R.id.forgot_password);
        //log2.setText("Register");
        log2.setOnClickListener(this);
        showProgress(false);
        return view;


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                //
                //replaceFragment(fragment);
                //showProgress(true);
                attemptLogin();
                break;
            case R.id.reg:
                //showProgress(true);
                startActivity(new Intent(getActivity(), Register.class));
                break;
            case R.id.forgot_password:
                //showProgress(true);
                startActivity(new Intent(getActivity(), ForgotPassword.class));
                break;
        }
    }
public void nullCheck(){


    }
    public void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.view_pager, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void attemptLogin() {
        DatabaseHelper helper = new DatabaseHelper(this.getContext());
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);
        //mLoginFormView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        EditText a = (EditText) view.findViewById(R.id.email);
        String str = a.getText().toString();
        EditText b = (EditText) view.findViewById(R.id.password);
        String pass = b.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        } else {
            if (TextUtils.isEmpty(password)) {
                mPasswordView.setError(getString(R.string.error_field_required));
                focusView = mPasswordView;
                cancel = true;
            }
        }


        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else {
            if (!isEmailValid(email)) {
                mEmailView.setError(getString(R.string.error_invalid_email));
                focusView = mEmailView;
                cancel = true;
            }
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            String passwordDb = helper.searchPass(str);
            if (!pass.equals(passwordDb)) {
              /*  AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.setTitle("Login");
                alertDialog.setMessage("Failled! incorrect password or email");
                alertDialog.setButton("OK", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which){
                        //no function
                    }
                });
                alertDialog.show();*/
                showProgress(false);

                Toast loginErr = Toast.makeText(getContext(), "username and password incorrect", Toast.LENGTH_SHORT);
                loginErr.show();

            } else {
                //SharedPreferences.Editor editor = sharedpreferences.edit();
                Bundle tab = new Bundle();
                tab.putInt("tab", 0);

                //editor.putString(emailSet, email);
                //editor.putString(passwordSet, password);
                sharedPreference.save(getContext(), email);
                //Toast.makeText(getContext(),getResources().getString(R.string.saved),Toast.LENGTH_LONG).show();
                //editor.commit();
                Toast loginErr = Toast.makeText(getContext(), "Login sucessful", Toast.LENGTH_SHORT);
                loginErr.show();
                // Button log1 = (Button) view.findViewById(R.id.reg);
                //log1.setText("Logout"); 
                //startActivity(new Intent(getActivity(), Profile.class));
                showProgress(true);
                Intent i = new Intent(getActivity(),
                        MainActivity.class);
                i.putExtras(tab);
                startActivity(i);


                //tabHost.setCurrentTab(2);

            }
        }


    }

    public boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    public boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mEmail)) {
                    // Account exists, return true if the password matches.
                    return pieces[1].equals(mPassword);
                }
            }

            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {

            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }

    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };

}
