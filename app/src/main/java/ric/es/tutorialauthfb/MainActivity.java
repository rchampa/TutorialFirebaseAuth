package ric.es.tutorialauthfb;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    @BindView(R.id.tv_name) TextView tv_name;
    boolean isGoogle=false;


    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            GoogleSignInAccount acct = (GoogleSignInAccount)extras.get("user_data");
            if(acct!=null){
                String personName = acct.getDisplayName();
                String personPhotoUrl = acct.getPhotoUrl().toString();//load with UIL
                String email = acct.getEmail();
                tv_name.setText(personName + " " + email);
                isGoogle = true;
            }
            //The key argument here must match that used in the other activity
        }
    }

    @OnClick(R.id.btn_logout) public void logout(){

        if(isGoogle){
            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                    new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status status) {
                            FirebaseAuth.getInstance().signOut();
                            finish();
                            startActivity(new Intent(MainActivity.this,LoginActivity.class));
                        }
                    });
        }
        else{
            FirebaseAuth.getInstance().signOut();
            finish();
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
