package easymusicforrun.easymusicforrun;

import java.io.IOException;
import android.content.Context;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;

public class UserProfileObj {

    String localPlaylistClipName = "";
    String youtubeVideoIdForRunning = "";
    String youtubeVideoIdForWalking = "";

    public String getLocalPlaylistClipName() {
        return localPlaylistClipName;
    }

    public void setLocalPlaylistClipName(String localPlaylistClipName) {
        this.localPlaylistClipName = localPlaylistClipName;
    }

    public String getYoutubeVideoIdForRunning(){
        return youtubeVideoIdForRunning;
    }

    public void setYoutubeVideoIdForRunning(String youtubeVideoIdForRunning) {
        this.youtubeVideoIdForRunning = youtubeVideoIdForRunning;
    }

    public String getGetYoutubeVideoIdForWalking() {
        return youtubeVideoIdForWalking;
    }

    public void setGetYoutubeVideoIdForWalking(String youtubeVideoIdForWalking) {
        this.youtubeVideoIdForWalking = youtubeVideoIdForWalking;
    }


}
