package com.rokid.news;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class MainActivity extends Activity {

    private static final String TITLE_KEY = "KEY";
    private TextView tvTitle;
    private CommandController commandController;
    private BaseCommandProcessor netCommandProcessor;

    private Handler refreshHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String title = msg.getData().getString(TITLE_KEY);
            tvTitle.setAlpha(0);
            tvTitle.setText(title);
            tvTitle.animate().setDuration(300).alpha(1).start();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvTitle = (TextView) this.findViewById(R.id.tv_title);
        tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                netCommandProcessor.next();
            }
        });

        netCommandProcessor = new NetCommandProcessor();
        netCommandProcessor.setNewsShow(new NetCommandProcessor.NewsShow() {
            @Override
            public void setTitle(String title) {
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString(TITLE_KEY,title);
                message.setData(bundle);
                refreshHandler.sendMessage(message);
            }

            @Override
            public void setContent(String content) {
            }

            @Override
            public void finish() {
                Logger.d("finish activity!");
                Intent home = new Intent(Intent.ACTION_MAIN);
                home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                home.addCategory(Intent.CATEGORY_HOME);
                startActivity(home);
            }
        });
        commandController = new CommandController(netCommandProcessor);
        commandController.parseIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        commandController.parseIntent(intent);
    }

    @Override
    protected void onPause() {
        if (netCommandProcessor != null){
            netCommandProcessor.pauseTTS();
        }
        super.onPause();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
    }

}
