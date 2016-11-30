package easymusicforrun.easymusicforrun;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.List;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listactivity);
    }

    public void callUserPreferences(View view)
    {
        Intent intent = new Intent(ListActivity.this, UserPreferences.class);
        startActivity(intent);
    }

}
