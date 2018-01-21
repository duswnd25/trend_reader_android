package app.kimyeonjung.trendreader.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import app.kimyeonjung.trendreader.R;

public class AboutApp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);

        Toolbar toolbar = findViewById(R.id.about_app_toolbar);
        toolbar.setTitle(getString(R.string.title_about_dev));
        toolbar.setNavigationOnClickListener(v -> finish());
    }
}
