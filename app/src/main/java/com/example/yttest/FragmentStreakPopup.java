package com.example.yttest;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.airbnb.lottie.LottieAnimationView;

public class FragmentStreakPopup extends DialogFragment {
    static final String ARG_STREAK_COUNT = "streak_count";

    Button btnCloseFragment;
    int streakCount;
    TextView streakCounter;

    LottieAnimationView fireAnimationView;


    public static FragmentStreakPopup newInstance(int streakCount) {
        FragmentStreakPopup fragment = new FragmentStreakPopup();
        Bundle args = new Bundle();
        args.putInt(ARG_STREAK_COUNT, streakCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            streakCount = getArguments().getInt(ARG_STREAK_COUNT, 0);
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_streak_popup, container, false);

        streakCounter = view.findViewById(R.id.streakCounter);
        fireAnimationView = view.findViewById(R.id.fireAnimationView);
        btnCloseFragment = view.findViewById(R.id.btnCloseFragment);

        streakCounter.setText("" + streakCount);
        streakCounter.setShadowLayer(1, 0, -25, Color.BLACK);
        streakCounter.setShadowLayer(4, 0, -15, Color.BLACK);
        streakCounter.setShadowLayer(10, 0, -20, Color.BLACK);
//        streakCounter.setShadowLayer(20, 0, -25, Color.BLACK);

        startPopAnimationForLottie();

        btnCloseFragment.setOnClickListener(v -> dismiss());





        return view;
    }

    private void startPopAnimationForLottie() {
        // Scale animation to make the Lottie view pop
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(fireAnimationView, "scaleX", 0f, 1.2f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(fireAnimationView, "scaleY", 0f, 1.2f, 1f);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(fireAnimationView, "alpha", 0f, 1f);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(scaleX, scaleY, alpha);
        animatorSet.setDuration(900); // Duration for the pop effect

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                // Start the TextView animation after a delay
                new Handler().postDelayed(() -> startPopAnimationForTextView(), 500); // 1000 ms delay
            }
        });

        animatorSet.start();
    }

    private void startPopAnimationForTextView() {
        // Set TextView to visible before starting the animation
        streakCounter.setVisibility(View.VISIBLE);

        // Scale animation to make the TextView pop
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(streakCounter, "scaleX", 0f, 1.2f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(streakCounter, "scaleY", 0f, 1.2f, 1f);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(streakCounter, "alpha", 0f, 1f);
        ObjectAnimator rotation = ObjectAnimator.ofFloat(streakCounter, "rotation", -15f, 0f);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(scaleX, scaleY, alpha, rotation);
        animatorSet.setDuration(400); // Duration for the pop effect

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                // Delay before showing the close button
            new Handler().postDelayed(() -> btnCloseFragment.setVisibility(View.VISIBLE), 1000); // 1000ms delay before showing button
            }
        });

        animatorSet.start();
    }


}