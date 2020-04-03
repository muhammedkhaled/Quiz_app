package com.muhammadkhaled.quizapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StartFragment extends Fragment {


    private ProgressBar startProgress;
    private TextView startFeedBackText;

    private FirebaseAuth firebaseAuth;
    private static final String START_TAG = "StartFragment";

    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_start, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();
        navController = Navigation.findNavController(view);

        startProgress = view.findViewById(R.id.start_progress);
        startFeedBackText = view.findViewById(R.id.start_feedback);

        startFeedBackText.setText("Checking User Account");
    }

    @Override
    public void onStart() {
        super.onStart();
        startFeedBackText.setText("Creating Account...");

        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser == null){
            startFeedBackText.setText("Account Created...");
            firebaseAuth.signInAnonymously().addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        navController.navigate(R.id.action_splashScrean_to_listFragment);
                    }else {
                        Log.d(START_TAG, "start log : " + task.getException().toString());
                    }
                }
            });

        }else {
            startFeedBackText.setText("Logged in...");
            navController.navigate(R.id.action_splashScrean_to_listFragment);
        }
    }
}
