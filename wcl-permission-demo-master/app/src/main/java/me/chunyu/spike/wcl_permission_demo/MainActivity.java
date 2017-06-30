package me.chunyu.spike.wcl_permission_demo;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.io.IOException;
import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 0; // 请求码
    private Button start,stop,play;
    private PermissionUse myPermissionuse =new PermissionUse();
    // 所需的全部权限
    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.RECORD_AUDIO,            //录音权限
            Manifest.permission.MODIFY_AUDIO_SETTINGS,  //允许程序修改全局音频设置
            Manifest.permission.WRITE_EXTERNAL_STORAGE  //
    };

    @Bind(R.id.main_t_toolbar) Toolbar mTToolbar;

    private PermissionsChecker mPermissionsChecker; // 权限检测器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(mTToolbar);
        mPermissionsChecker = new PermissionsChecker(this);

        start = (Button)findViewById(R.id.button1);
        stop = (Button)findViewById(R.id.button2);
        play = (Button)findViewById(R.id.button3);

        stop.setEnabled(false);
        play.setEnabled(false);
    }
    @Override
    protected void onResume() {
        super.onResume();

        // 缺少权限时, 进入权限配置页面
        if (mPermissionsChecker.lacksPermissions(PERMISSIONS)) {
            startPermissionsActivity();
        }
    }
    private void startPermissionsActivity() {
        PermissionsActivity.startActivityForResult(this, REQUEST_CODE, PERMISSIONS);
    }
    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 拒绝时, 关闭页面, 缺少主要权限, 无法运行
        if (requestCode == REQUEST_CODE && resultCode == PermissionsActivity.PERMISSIONS_DENIED) {
            finish();
        }
    }
        public void action(){
         myPermissionuse.Action();
        }
        public void start(View view){
            action();
            myPermissionuse.startAction();
            start.setEnabled(false);
            stop.setEnabled(true);
            Toast.makeText(getApplicationContext(), "Recording started", Toast.LENGTH_LONG).show();
        }
        public void stop(View view){
            myPermissionuse.stopAction();
            stop.setEnabled(false);
            play.setEnabled(true);
            Toast.makeText(getApplicationContext(), "Audio recorded successfully",
                    Toast.LENGTH_LONG).show();
        }
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
    public void play(View view) throws IllegalArgumentException,
            SecurityException, IllegalStateException, IOException{
        myPermissionuse.playAction();
        Toast.makeText(getApplicationContext(), "Playing audio", Toast.LENGTH_LONG).show();
    }
}
