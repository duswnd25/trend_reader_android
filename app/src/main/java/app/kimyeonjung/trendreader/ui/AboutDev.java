package app.kimyeonjung.trendreader.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.vansuita.materialabout.builder.AboutBuilder;
import com.vansuita.materialabout.views.AboutView;

import app.kimyeonjung.trendreader.R;

public class AboutDev extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_about_dev);

        Toolbar toolbar = findViewById(R.id.about_dev_toolbar);
        toolbar.setTitle(getString(R.string.title_about_dev));
        toolbar.setNavigationOnClickListener(v -> finish());

        AboutBuilder builder = AboutBuilder.with(this)
                .setAppIcon(R.mipmap.ic_launcher)
                .setAppName(R.string.app_name)
                .setPhoto(R.drawable.dev_profile)
                .setCover(R.mipmap.profile_cover)
                .setLinksAnimated(true)
                .setDividerDashGap(13)
                .setName("YeonJung Kim")
                .setSubTitle("Student")
                .setLinksColumnsCount(4)
                .setBrief("컴퓨터공학 전공중인 학생입니다.")
                .addGitHubLink("duswnd25")
                .addLinkedInLink("연중-김-172989119")
                .addEmailLink("duswnd25@gmail.com")
                .addWebsiteLink("https://duswnd25.github.io/portfolio")
                .addFiveStarsAction()
                .addMoreFromMeAction("김연중")
                .setVersionNameAsAppSubTitle()
                .addShareAction(R.string.app_name)
                .addUpdateAction()
                .setActionsColumnsCount(2)
                .addFeedbackAction("duswnd25@gmail.com")
                .addChangeLogAction((Intent) null)
                .addDonateAction((Intent) null)
                .setWrapScrollView(true)
                .setShowAsCard(true);

        AboutView aboutView = builder.build();

        ((FrameLayout) findViewById(R.id.about_container)).addView(aboutView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
