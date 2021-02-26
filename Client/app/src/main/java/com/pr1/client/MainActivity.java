package com.pr1.client;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    Button myButton;
    View myView;
    boolean isUp;
    boolean connected;
    private ListView fileList;
    private ListView serverList;
    private Context context;
    private TextView dialog_title;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);
        getSupportActionBar().setElevation(0);
        setContentView(R.layout.activity_main);
        context = this;
        RowItem[] rowItems = {
                new RowItem("Cool", "Desc", "t1"),
                new RowItem("Cool2", "Desc2", "t2"),
                new RowItem("Cool3", "Desc3", "t3"),
                new RowItem("Cool4", "Desc4", "t4"),
                new RowItem("Cool5", "Desc5", "t4"),
                new RowItem("Cool6", "Desc6", "t4"),
                new RowItem("Cool7", "Desc7", "t4"),
                new RowItem("Cool8", "Desc8", "t4"),
                new RowItem("Cool9", "Desc9", "t4"),
        };

        MenuAdapter adapter = new MenuAdapter(this, rowItems, R.drawable.ic_microsoft_powerpoint);

        TextView currentPage = (TextView) findViewById(R.id.currentPage);
        Shader textShader = new LinearGradient(0, 0, 0, 50,
                new int[]{Color.parseColor("#415CFF"),
                        Color.parseColor("#C125FF")},
                new float[]{0, 1}, Shader.TileMode.CLAMP);
        currentPage.getPaint().setShader(textShader);

        builder = new AlertDialog.Builder(this);

        dialog_title = (TextView) this.findViewById(R.id.android_title_text);

        fileList = (ListView) findViewById(R.id.file_list);
        fileList.setAdapter(adapter);

        myView = findViewById(R.id.my_view);
        // initialize as invisible (could also do in xml)
        myView.setVisibility(View.INVISIBLE);
        myView.animate().translationY(1400).setDuration(500).start();
        isUp = false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_cart) {
            onSlideViewButtonClick(myView);
        }
        if (id == R.id.action_connect) {
            if (!connected) {
                showDialog();
                item.setIcon(R.drawable.ic_online);
                connected = true;
            } else {
                item.setIcon(R.drawable.ic_offline);
                connected = false;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void showDialog() {
        //Uncomment the below code to Set the message and title from the strings.xml file
        //builder.setMessage(R.string.dialog_message).setTitle(R.string.dialog_title);

        //Setting message manually and performing action on button click
        builder.setMessage("")
                .setCancelable(false)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                }).setView(R.layout.search_window);

        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setCustomTitle(dialog_title);
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#AE150519")));
        alert.show();
        ImageButton search = (ImageButton) alert.findViewById(R.id.serverSearchButton);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressBar bar = (ProgressBar) alert.findViewById(R.id.progress_loader);
                bar.setVisibility(View.VISIBLE);
                RowItem[] rowItems = {
                        new RowItem("Cool", "Desc", "t1")
                };
                MenuAdapter adapter = new MenuAdapter(alert.getContext(), rowItems, R.drawable.ic_server);
                // bar.setVisibility(View.INVISIBLE);
                serverList = (ListView) alert.findViewById(R.id.server_list);
                serverList.setAdapter(adapter);
                serverList.setVisibility(View.VISIBLE);
            }
        });

    }

    // slide the view from below itself to the current position
    public void slideUp(View view) {
        myView.setVisibility(View.VISIBLE);
        view.animate().translationY(0).setDuration(500).start();
    }

    // slide the view from its current position to below itself
    public void slideDown(View view) {
        view.animate().translationY(1400).setDuration(500).start();
    }

    public void onSlideViewButtonClick(View view) {
        if (isUp) {
            slideDown(myView);
        } else {
            slideUp(myView);
        }
        isUp = !isUp;
    }
}