package com.limox.jesus.teambeta.Fragments.SignUp;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.limox.jesus.teambeta.R;
import com.limox.jesus.teambeta.Validators.Validate;


public class SignUpEmail_Fragment extends Fragment  {

    private EditText edtEmail;
    private Button btnValidate;
    private TextView txvSignIn;
    private SignUpEmailListener mCallBack;

    public interface SignUpEmailListener{
        void backToLogin();
        void startSignUpUser(Bundle args);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof SignUpEmailListener)
            mCallBack = (SignUpEmailListener) activity;
        else
            throw new ClassCastException(activity.toString()+ "must implement SignUpEmailListener");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallBack = null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.activity_sign_up_email,container,false);

        edtEmail = (EditText) rootView.findViewById(R.id.sue_edtEmail);
        btnValidate = (Button) rootView.findViewById(R.id.sue_btnNext);
        txvSignIn = (TextView) rootView.findViewById(R.id.sue_txvSignIn);

        btnValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int message = Validate.validateEmail(edtEmail.getText().toString());
                if ( message == Validate.MESSAGE_OK){
                    Bundle bundle = new Bundle();
                    bundle.putString("email",edtEmail.getText().toString());
                    mCallBack.startSignUpUser(bundle);
                    //Cerrar fragment
                }else
                    edtEmail.setError(getResources().getString(Validate.validateEmail(edtEmail.getText().toString())));
            }
        });
        txvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallBack.backToLogin();
            }
        });

        return rootView;
    }

}
