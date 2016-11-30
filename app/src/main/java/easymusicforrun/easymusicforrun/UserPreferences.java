package easymusicforrun.easymusicforrun;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class UserPreferences extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userpreferences);
    }

    public void callMusicPlaylist(View view)
    {
        Intent intent = new Intent(UserPreferences.this, MusicPlaylist.class);
        startActivity(intent);
    }
}
