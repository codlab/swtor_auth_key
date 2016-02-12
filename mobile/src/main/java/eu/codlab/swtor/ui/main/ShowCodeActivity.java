package eu.codlab.swtor.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.alexandrepiveteau.library.tutorial.ui.fragments.AbstractTutorialValidationFragment;
import com.alexandrepiveteau.library.tutorial.ui.interfaces.ITutorialActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import eu.codlab.swtor.R;
import eu.codlab.swtor.ui.abstracts.AbstractKeysActivity;
import eu.codlab.swtor.ui.service.NotificationKeyService;
import eu.codlab.swtor.ui.tutorial.TutorialActivity;

public class ShowCodeActivity extends AbstractKeysActivity implements ITutorialActivity {

    @NonNull
    public static Intent createIntent(@NonNull AppCompatActivity parent) {
        return new Intent(parent, ShowCodeActivity.class);
    }

    public static void startAndFinish(@NonNull AppCompatActivity parent) {
        parent.startActivity(createIntent(parent));

        parent.finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_code);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.action_add)
    public void onClickActionAdd() {
        TutorialActivity.start(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        NotificationKeyService.start(this);
    }

    @Override
    public void onValidate(@NonNull AbstractTutorialValidationFragment abstractTutorialValidationFragment, boolean b) {
        //NOTHING TO DO SINCE NOT TUTORIAL BUT USED FOR TUTORIAL
        throw new NoSuchMethodError("This method is not to be called");
    }
}
