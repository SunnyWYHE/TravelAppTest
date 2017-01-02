package com.hsbc.gbl.eep.robotpa.travelapp;

import android.os.AsyncTask;
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

    /**
     * Keep track of the translation task to ensure we can cancel it if requested.
     */
    private TranslationTask mTranslateTask = null;

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
    }

    /**
     * Doing translation
     * @param view
     */
    public void doTranslation(View view) {
        if(mTranslateTask != null) {
            return;
        }

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

            //kick off a background task to do the translation
            mTranslateTask = new TranslationTask(addrInput);
            mTranslateTask.execute((Void) null);
        }
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class TranslationTask extends AsyncTask<Void, Void, Boolean> {

        private final String addrToTranslate;

        TranslationTask(String addr) {
            addrToTranslate = addr;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // Instantiate Http Request Param Object
            RequestParams reqParams = new RequestParams();

//            // Put Http parameter username with value of Email Edit View control
//            reqParams.put("username", mEmail);
//            // Put Http parameter password with value of Password Edit Value control
//            reqParams.put("password", mPassword);
            // Invoke RESTful Web Service with Http parameters
            invokeWS(reqParams);

            return true;
        }

        /**
         * Method that performs RESTful webservice invocations
         *
         * @param params
         */
        public void invokeWS(RequestParams params){
//            // Show Progress Dialog
//            showProgress(true);
//
//            // Make RESTful webservice call using AsyncHttpClient object
//            AsyncHttpClient client = new AsyncHttpClient();
//            client.get("http://robotpawithmysql.azurewebsites.net/robotpa-useraccount-webapp/login/dologin",params ,new AsyncHttpResponseHandler() {
//                // When the response returned by REST has Http response code '200'
//
//                @Override
//                public void onSuccess(int statusCode, org.apache.http.Header[] headers, byte[] responseBody) {
//                    // Hide Progress Dialog
//                    showProgress(false);
//                    try {
//                        // JSON Object
//                        String response = responseBody == null? null : new String(responseBody, "UTF-8");
//                        JSONObject obj = new JSONObject(response);
//                        // When the JSON response has status boolean value assigned with true
//                        if(obj.getBoolean("status")){
//                            //Toast.makeText(getApplicationContext(), "You are successfully logged in!", Toast.LENGTH_LONG).show();
//                            // Navigate to Home screen
//                            navigatetoHomeActivity(mEmail);
//                        }
//                        // Else display error message
//                        else{
//                            mErrorMsgView.setText(obj.getString("error_msg"));
//                            Toast.makeText(getApplicationContext(), obj.getString("error_msg"), Toast.LENGTH_LONG).show();
//                        }
//                    } catch (JSONException e) {
//                        // TODO Auto-generated catch block
//                        Toast.makeText(getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
//                        e.printStackTrace();
//
//                    } catch (UnsupportedEncodingException var5) {
//                        var5.printStackTrace();
//                    }
//                }
//
//                @Override
//                public void onFailure(int statusCode, org.apache.http.Header[] headers, byte[] responseBody, Throwable error) {
//                    // Hide Progress Dialog
//                    showProgress(false);
//
//                    // When Http response code is '404'
//                    if (statusCode == 404) {
//                        Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
//                    }
//                    // When Http response code is '500'
//                    else if (statusCode == 500) {
//                        Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
//                    }
//                    // When Http response code other than 404, 500
//                    else {
//                        Toast.makeText(getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]", Toast.LENGTH_LONG).show();
//                    }
//                }
//
//            });
        }

        @Override
        protected void onPostExecute(final Boolean success) {
//            mAuthTask = null;
//            showProgress(false);
//
//            if (success) {
//                finish();
//            } else {
//                mPasswordView.setError(getString(R.string.error_incorrect_password));
//                mPasswordView.requestFocus();
//            }
        }

        @Override
        protected void onCancelled() {
//            mAuthTask = null;
//            showProgress(false);
        }
    }
}
