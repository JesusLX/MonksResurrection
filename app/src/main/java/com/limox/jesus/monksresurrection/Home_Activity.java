package com.limox.jesus.monksresurrection;



import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Home_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

      

    }
    private void OpenAboutMe(){
        AboutMe_Fragment fr = AboutMe_Fragment.newInstance();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.ah_container,fr);
        ft.commit();
    }

}
