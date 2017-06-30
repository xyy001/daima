package me.chunyu.spike.wcl_permission_demo;

import android.media.MediaRecorder;
import android.media.MediaPlayer;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
/**
 * Created by lenovo on 2017/5/17.
 */

@SuppressWarnings("UnnecessarySemicolon")
public class PermissionUse {
    private MediaRecorder  myAudioRecorder = new MediaRecorder();       //Create  a  MediaRecorder
    private String outputFile = null;
    public void Action()
    {
        outputFile = Environment.getExternalStorageDirectory().
                getAbsolutePath() + "/myrecording.3gp";;

        myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        myAudioRecorder.setOutputFile(outputFile);
    }
    public void startAction(){
        try {
            myAudioRecorder.prepare();
            myAudioRecorder.start();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void stopAction(){

        myAudioRecorder.stop();
        myAudioRecorder.release();
        myAudioRecorder  = null;
    }
    public void playAction()throws IllegalArgumentException, SecurityException, IllegalStateException, IOException
    {
        MediaPlayer m = new MediaPlayer();
        m.setDataSource(outputFile);
        m.prepare();
        m.start();
    }

}
