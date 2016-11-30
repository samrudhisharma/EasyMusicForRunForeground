# EasyMusicForRun

#### Name: Samrudhi Sharma <br/>
#### Unity ID: ssharm17 <br/>

##### Introduction: <br/>
The application being suggested is called “Easy Music For Run”. This application helps manage the nature of a music playlist such that a user has a consistent experience while being active or inactive. The application takes into account features such as internet connectivity and headphones being plugged in. To improve the application experience further we could incorporate step counters and/or ability to handle incoming phone calls. <br/>

##### Context Aware Features: <br/>

1. Ability to detect if headphones connected or not.
2. Ability to detect if internet connected or not.
3. Ability to detect speed of runner.
4. Ability to switch music or alter music as per internet connectivity.

Relevant Files:
[MusicPlaying.java](https://github.com/samrudhisharma/EasyMusicForRun/blob/master/app/src/main/java/easymusicforrun/easymusicforrun/MusicPlaying.java)

##### Runtime Adaptation Features: <br/>

1. Runner can specify the type of music he wants.

Relevant Files:<br/>
[Taking inputs to populate values](https://github.com/samrudhisharma/EasyMusicForRun/blob/master/app/src/main/java/easymusicforrun/easymusicforrun/MusicPlaylist.java) <br/>
[Global Constants File. Must be populated by user before trying to play app.](https://github.com/samrudhisharma/EasyMusicForRun/blob/master/app/src/main/java/easymusicforrun/easymusicforrun/Constants.java)

##### Features Implemented: <br/>

1. Receiver to detect if headphones connected or not.
2. Receiver to detect if internet connectivity is there or not.
3. Receiver to detect location, and speed of user. 
4. Views and flow for user.
5. POJO to change values.
6. Play a local file.
7. Entire Workflow.

##### Features Left: <br/>

1. Handle playlists, rather than playing just one song. 

##### Installation Requirements: <br/>

1. Clone this repository.
2. Open in Android Studio and Click Run.

##### Screenshots: <br/>

[Screenshots](Screenshots.md)

##### Application Flow: <br/>

1. The Runner must provide the details of the local file name, video id for running, and id for walking. This must be done through clicking the application settings. 

2. Next the runner will navigate back to the home page and click on Play. 
3. Depending on the context the appropriate action will happen. For example, as shown in the Screenshots, if the Wifi or LTE is not present, and headphones are connected and the user is active, a local clip will be played and a toast will be shown. 
4. Now if the the headphones are connected, Wifi is on, and the user is active a Youtube video will be displayed once the user clicks on Play again.


##### References: <br/>
https://stackoverflow.com/questions/574195/android-youtube-app-play-video-intent <br/>
https://stackoverflow.com/questions/2091465/how-do-i-pass-data-between-activities-on-android <br/>
https://stackoverflow.com/questions/25824554/how-to-make-another-activity-as-main-activitystart-up <br/>
https://stackoverflow.com/questions/13194081/how-to-open-a-second-activity-on-click-of-button-in-android-app <br/>
https://stackoverflow.com/questions/26312733/neither-user-nor-current-process-has-android-permission-access-coarse-location <br/>
https://stackoverflow.com/questions/1560788/how-to-check-internet-access-on-android-inetaddress-never-times-out?page=1&tab=votes#tab-top <br/>
https://stackoverflow.com/questions/6179906/how-can-i-receive-a-notification-when-the-device-loses-network-connectivity <br/>
https://stackoverflow.com/questions/13610258/how-to-detect-when-a-user-plugs-headset-on-android-device-opposite-of-action-a <br/>
https://stackoverflow.com/questions/15698790/broadcast-receiver-for-checking-internet-connection-in-android-app <br/>
https://inthecheesefactory.com/blog/google-awareness-api-in-action/en<br/>
https://code.tutsplus.com/tutorials/android-essentials-creating-simple-user-forms--mobile-1758 <br/>
http://www.compiletimeerror.com/2013/10/playing-audio-in-android-application.html#.WDHSaKIrK34 <br/>
