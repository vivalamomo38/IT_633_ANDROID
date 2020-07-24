package com.example.acmedepartmentstore;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.acmedepartmentstore.databinding.ActivityMainBinding;
import com.example.acmedepartmentstore.ui.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mBinding;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        // Test to see if currentUser is null else go to home

        if(currentUser !=null){
            // Call private method to animateGuestButtonWidth and send user to Home Activity
            startActivity(new Intent(MainActivity.this, Home.class));


        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBinding= DataBindingUtil.setContentView(this,R.layout.activity_main);
    }
    public void guestLogin(View view){

        // Call private method to animateGuestButtonWidth and send user to Home Activity
        animateButtonWidth(0);
        fadeOutTextAndSetProgressDialog();
        nextAction(0);


    }

    public void loadLogin(View view){

        // Call private method to animateGuestButtonWidth and send user to Home Activity
            animateButtonWidth(1);
            fadeOutTextAndSetProgressDialog();
            nextAction(1);


        }


    private void animateButtonWidth(int button){
        if(button==1){
            //Code to execute login button animation
        ValueAnimator anim = ValueAnimator.ofInt(mBinding.signInBtn.getMeasuredWidth());
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                int value = (Integer) animation.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = mBinding.signInBtn.getLayoutParams();
                layoutParams.width=value;
                mBinding.signInBtn.requestLayout();
                mBinding.guestBtn.setVisibility(View.INVISIBLE);
            }
        });


        anim.setDuration(250);
        anim.start();}else if (button==0) {
            // Code to execute guest button login
            ValueAnimator anim = ValueAnimator.ofInt(mBinding.guestBtn.getMeasuredWidth());
            anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {

                    int value = (Integer) animation.getAnimatedValue();
                    ViewGroup.LayoutParams layoutParams = mBinding.guestBtn.getLayoutParams();
                    layoutParams.width=value;
                    mBinding.guestBtn.requestLayout();
                    mBinding.guestBtn.setVisibility(View.INVISIBLE);

                }
            });


            anim.setDuration(250);
            anim.start();



        };
    }

    private void fadeOutTextAndSetProgressDialog(){
        mBinding.signInTxt.animate().alpha(0f).setDuration(250).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                // When Text is GONE call the progress Dialog
                showProgressDialog();
            }
        }).start();
        mBinding.guestTxt.animate().alpha(0f).setDuration(250).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                // When Text is GONE call the progress Dialog
                showProgressDialog();
            }
        }).start();
    }


    //SHOW PROGRESS DIALOG METHOD
    private void showProgressDialog(){

        mBinding.progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_IN);
        mBinding.progressBar.setVisibility(View.VISIBLE);

    }

    private void nextAction(int button){
if(button==1) {
    new Handler().postDelayed(new Runnable() {
        @Override
        public void run() {
            revealButton();
            fadeOutProgressDialog();
            delayedStartNextActivity(1);

        }
    }, 2000);
}else if (button==0){ new Handler().postDelayed(new Runnable() {
    @Override
    public void run() {
        revealButton();
        fadeOutProgressDialog();
        delayedStartNextActivity(0);

    }
}, 2000);

}    }

    private void revealButton(){
        mBinding.signInBtn.setElevation(0f);
        mBinding.revealView.setVisibility(View.VISIBLE);

        int x = mBinding.revealView.getWidth();
        int y = mBinding.revealView.getHeight();

        int startX = (int) (getFinalWidth()/ 2 + mBinding.signInBtn.getX());
        int startY = (int) (getFinalWidth()/ 2 + mBinding.signInBtn.getY());

        float radius = Math.max(x,y) * 1.2f;

        Animator reveal = ViewAnimationUtils.createCircularReveal(mBinding.revealView,startX,startY,getFinalWidth(),radius);
        reveal.setDuration(750);
        reveal.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                finish();
            }
        });

        reveal.start();
    }

    // Fade Out Progress Dialog

    private void fadeOutProgressDialog(){
        mBinding.progressBar.animate().alpha(0f).setDuration(200).start();
        mBinding.imageView.animate().alpha(0f).setDuration(100).start();
    }

    private void delayedStartNextActivity(int button){
if(button==1){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                // Fix me -- This is a workaround for the MainActivity not being on the stack when using up navigation
                // -- This code adds main activity back to the stack before calling the next Intent

                startActivity(new Intent(MainActivity.this, MainActivity.class));

                // Intent to run the new activity

                startActivity(new Intent(MainActivity.this, LoginActivity.class));

            }
        },100);}
else if(button==0){
    new Handler().postDelayed(new Runnable() {
        @Override
        public void run() {

            // Fix me -- This is a workaround for the MainActivity not being on the stack when using up navigation
            // -- This code adds main activity back to the stack before calling the next Intent

            startActivity(new Intent(MainActivity.this, MainActivity.class));

            // Intent to run the new activity

            startActivity(new Intent(MainActivity.this, Home.class));

        }
    },100);
}

    }

    // GET RESOURCE WIDTH FOR BUTTON
    private int getFinalWidth (){

        return (int) getResources().getDimension(R.dimen.get_width);
    }
}