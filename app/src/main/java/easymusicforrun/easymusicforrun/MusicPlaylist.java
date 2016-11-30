package easymusicforrun.easymusicforrun;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MusicPlaylist extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musicplaylist);

        Button button10 = (Button) findViewById(R.id.button10);
        button10.setOnClickListener(new OnClickListener() {

            UserProfileObj userProfileObj = new UserProfileObj();

            final EditText videoIDforRunning = (EditText)findViewById(R.id.editText2);
            final EditText localClipName   = (EditText)findViewById(R.id.editText);
            final EditText videoIDforWalking   = (EditText)findViewById(R.id.editText4);

            public void onClick(View v)
            {
                final String _videoIDforRunning = videoIDforRunning.getText().toString();
                userProfileObj.setYoutubeVideoIdForRunning(_videoIDforRunning);
                Constants.youtubeVideoIdForRunning = _videoIDforRunning;

                final String _localClipName = localClipName.getText().toString();
                userProfileObj.setLocalPlaylistClipName(_localClipName);
                Constants.localPlaylistClipName = _localClipName;

                final String _videoIDforWalking = videoIDforWalking.getText().toString();
                userProfileObj.setGetYoutubeVideoIdForWalking(_videoIDforWalking);
                Constants.youtubeVideoIdForWalking = _videoIDforWalking;
            }
        });


    }
}
