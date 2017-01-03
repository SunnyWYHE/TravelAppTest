package com.hsbc.gbl.eep.robotpa.travelapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.content.Intent;
import android.widget.Toast;
import android.view.View;
import android.text.TextUtils;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class HomeActivity extends AppCompatActivity {

    private TextView mWelcomeUserView; //  User Name TextView Object
    private EditText mAddressToTranslateView;  //address to translate EditText object
    private TextView mAddressTranslatedView;   //translated address TextView object
    private TextView mErrorMsgView; // Error Msg TextView Object
    private View mProgressView;  //show progress
    private View mTranslateBoxView;

    /**
     * Keep track of the translation task to ensure we can cancel it if requested.
     */
//    private TranslationTask mTranslateTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent it = getIntent();
        String username = it.getStringExtra("username");
        if(username != null) {
            mWelcomeUserView = (TextView)findViewById(R.id.welcome_user);
            mWelcomeUserView.setText(username);
        }

        mAddressToTranslateView = (EditText)findViewById(R.id.addr_eng);
        mAddressTranslatedView = (TextView)findViewById(R.id.addr_local);

        mTranslateBoxView = findViewById(R.id.translate_box);
        mProgressView = findViewById(R.id.translate_progress);
        // Find Error Msg Text View control by ID
        mErrorMsgView = (TextView)findViewById(R.id.translate_error);
    }

    /**
     * Doing translation
     * @param view
     */
    public void doTranslation(View view) {
//        if(mTranslateTask != null) {
//            return;
//        }

        boolean cancel = false;
        View focusView = null;

        String addrInput = mAddressToTranslateView.getText().toString();

        if(TextUtils.isEmpty(addrInput)) {
            cancel = true;
            focusView = mAddressToTranslateView;
            return;
        }

        if(cancel) {
            focusView.requestFocus();
        } else {
            //TODO: show progress
//            showProgress(true);

            //kick off a background task to do the translation
//            mTranslateTask = new TranslationTask(addrInput);
//            mTranslateTask.execute((Void) null);

            //TODO: check in local database first
            boolean localFound = false;
//            mAddressTranslatedView.setText("北京市");
//            localFound = true;

            //If cannot find in local database, check from server side
            if (!localFound) {
                invokeWS(addrInput);
            }

//            showProgress(false);
        }
    }

    /**
     * Method that performs RESTful webservice invocations
     *
     * @param params
     */
    public void invokeWS(String strToTranslate){

        // Make RESTful webservice call using AsyncHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        String translateURL = "http://webrole1.azurewebsites.net/api/Translate/";
        translateURL = translateURL + strToTranslate;
        RequestParams reqParams = new RequestParams();
        client.get(translateURL,reqParams ,new AsyncHttpResponseHandler() {
            // When the response returned by REST has Http response code '200'

            @Override
            public void onSuccess(int statusCode, org.apache.http.Header[] headers, byte[] responseBody) {
                try {
                    // JSON Object
                    String response = responseBody == null? null : new String(responseBody, "UTF-8");
//                        JSONObject obj = new JSONObject(response);
                    // When the JSON response has status boolean value assigned with true
//                        if(obj.getBoolean("status")){
                    mAddressTranslatedView.setText(response);
//                        }
                    // Else display error message
//                        else{
//                            mErrorMsgView.setText(obj.getString("error_msg"));
//                            Toast.makeText(getApplicationContext(), obj.getString("error_msg"), Toast.LENGTH_LONG).show();
//                        }
//                    } catch (JSONException e) {
//                        // TODO Auto-generated catch block
//                        mErrorMsgView.setText("Error Occured [Server's JSON response might be invalid]!");
//                        e.printStackTrace();
//
                } catch (UnsupportedEncodingException var5) {
                    var5.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, org.apache.http.Header[] headers, byte[] responseBody, Throwable error) {

                // When Http response code is '404'
                if (statusCode == 404) {
                    Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                }
                // When Http response code is '500'
                else if (statusCode == 500) {
                    Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                }
                // When Http response code other than 404, 500
                else {
                    Toast.makeText(getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]", Toast.LENGTH_LONG).show();
                }
            }

        });
    }

    /**
     * Shows the progress UI and hides the translate form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mAddressToTranslateView.setVisibility(show ? View.GONE : View.VISIBLE);
            mTranslateBoxView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mTranslateBoxView.setVisibility(show ? View.GONE : View.VISIBLE);
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
            mTranslateBoxView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}
