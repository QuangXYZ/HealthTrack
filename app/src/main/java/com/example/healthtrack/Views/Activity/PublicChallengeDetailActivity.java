package com.example.healthtrack.Views.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.healthtrack.Controller.ChallengeController;
import com.example.healthtrack.Models.Challenge;
import com.example.healthtrack.Models.Record;
import com.example.healthtrack.Utils.stepComparator;
import com.example.healthtrack.R;
import com.example.healthtrack.Utils.DataLocalManager;
import com.example.healthtrack.Views.Adapters.RankingAdapter;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;

public class PublicChallengeDetailActivity extends AppCompatActivity {

    MaterialToolbar toolbar;
    TextView name, description, member, dateEnd, target, step, stepAchived, ranking;
    ProgressBar progressBar;
    RecyclerView recyclerView;
    Challenge challenge;
    RankingAdapter adapter;
    ChallengeController challengeController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_challenge);
        init();
        settingUpListeners();
    }

    void init() {
        toolbar = findViewById(R.id.public_challenge_toolbar);
        name = findViewById(R.id.public_challenge_name);
        description = findViewById(R.id.public_challenge_description);
        member = findViewById(R.id.public_challenge_member);
        dateEnd = findViewById(R.id.public_challenge_date_end);
        target = findViewById(R.id.public_challenge_target);
        step = findViewById(R.id.public_challenge_step);
        stepAchived = findViewById(R.id.public_challenge_step_achive);
        ranking = findViewById(R.id.public_challenge_rank);
        progressBar = findViewById(R.id.public_challenge_progress);
        recyclerView = findViewById(R.id.public_challenge_recyclerview);
        challengeController = new ChallengeController();


        Intent intent = getIntent();
        if (intent != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                challenge = intent.getSerializableExtra("Challenge", Challenge.class);
            } else {
                challenge = (Challenge) intent.getSerializableExtra("Challenge");
            }
        }

        String userId = DataLocalManager.getUser().get_id();
        List<Record> records = challenge.getUserRecords();
        records.sort(new stepComparator());
        for (Record record : records) {
            if (record.getUserId().equals(userId)) {
                step.setText("Tiến độ thử thách: " + (int) (record.getStepTotal() / challenge.getTarget() * 1.0) * 100 + "%");
                progressBar.setProgress((int) (record.getStepTotal() / challenge.getTarget() * 1.0) * 100);
                stepAchived.setText("" + record.getStepTotal());
                ranking.setText((records.indexOf(record) + 1) + "");
            }
        }

        name.setText(challenge.getName());
        description.setText(challenge.getDescription());
        member.setText("Số người tham gia: " + challenge.getListMember().size());
        dateEnd.setText("Ngày kết thúc: " + challenge.getDateEnd().split("T")[0]);
        target.setText("Mục tiêu: " + challenge.getTarget() + " bước");

        adapter = new RankingAdapter(this, records);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(true);


    }

    void settingUpListeners() {
        toolbar.setNavigationOnClickListener(v -> {
            finish();
        });

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.exit_challenge) {
                    new MaterialAlertDialogBuilder(PublicChallengeDetailActivity.this)
                            .setTitle("Confirm")
                            .setMessage("Bạn có chắc chắn")
                            .setNeutralButton("Có", (dialog, which) -> {

                                challengeController.leaveChallenge(challenge.get_id(), new ChallengeController.ChallengeControllerCallback() {
                                    @Override
                                    public void onSuccess(String message) {
                                        finish();
                                    }

                                    @Override
                                    public void onError(String error) {

                                    }
                                });
                            })
                            .setPositiveButton("Không", (dialog, which) -> {
                            }).show();
                }
                return false;
            }
        });

    }
}