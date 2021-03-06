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
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    Client client;
    private MenuAdapter serverAdapter;
    private MenuAdapter pptAdapter;
    TextView currentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);
        getSupportActionBar().setElevation(0);
        setContentView(R.layout.activity_main);
        context = this;

        currentPage = (TextView) findViewById(R.id.currentPage);
        Shader textShader = new LinearGradient(0, 0, 0, 50,
                new int[]{Color.parseColor("#415CFF"),
                        Color.parseColor("#C125FF")},
                new float[]{0, 1}, Shader.TileMode.CLAMP);
        currentPage.getPaint().setShader(textShader);

        builder = new AlertDialog.Builder(this);
        client = new Client();

        dialog_title = (TextView) this.findViewById(R.id.android_title_text);


        myView = findViewById(R.id.my_view);
        // initialize as invisible (could also do in xml)
        myView.setVisibility(View.INVISIBLE);
        myView.animate().translationY(1400).setDuration(500).start();
        isUp = false;
        pptAdapter = new MenuAdapter(context, null, R.drawable.ic_microsoft_powerpoint);
        ImageButton prevButton = findViewById(R.id.prevButton);
        prevButton.setOnClickListener(v -> {
            if (client.isConnected()) {
                Integer currentP = Integer.parseInt((String) currentPage.getText());
                if (currentP > 1)
                    currentPage.setText(String.valueOf(currentP - 1));
                new handleCommand().execute("prev_click", client);
            }
        });
        ImageButton nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(v -> {
            if (client.isConnected()) {
                Integer currentP = Integer.parseInt((String) currentPage.getText());
                currentPage.setText(String.valueOf(currentP + 1));
                new handleCommand().execute("nxt_click", client);
            }
        });
        fileList = (ListView) findViewById(R.id.file_list);
        fileList.setOnItemClickListener((parent, view, position, id) -> {
            RowItem entry = (RowItem) pptAdapter.getItem(position);
            new handleCommand().execute("open_file|" + entry.value, client);
        });

        LinearLayout mouse_pad = findViewById(R.id.mouse_pad);
        mouse_pad.setOnTouchListener(mouse_Listener);
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
        String currentIP = Utils.getIPAddress(true);
        TextView ipText = (TextView) alert.findViewById(R.id.ipTextView);
        ipText.setText("Current IP:       " + currentIP);
        ImageButton search = (ImageButton) alert.findViewById(R.id.serverSearchButton);
        ImageButton connect = (ImageButton) alert.findViewById(R.id.serverConnectButton);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressBar bar = alert.findViewById(R.id.progress_loader);
                EditText port = alert.findViewById(R.id.portText);
                bar.setVisibility(View.VISIBLE);
                TextView err = alert.findViewById(R.id.noServerError);
                err.setVisibility(View.INVISIBLE);
                serverAdapter = new MenuAdapter(context, null, R.drawable.ic_server);
                new getServerList().execute(currentIP, port.getText().toString(), alert, serverAdapter, client);

            }
        });
        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText text = alert.findViewById(R.id.editTextStaticIP);
                String[] ip_port = text.getText().toString().split(":");
                new connectToServer().execute(ip_port[0], Integer.parseInt(ip_port[1]), client, alert);
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

    private void SwitchPPTListState() {
        if (isUp) {
            slideDown(myView);
        } else {
            slideUp(myView);
        }
        isUp = !isUp;
    }

    public void onSlideViewButtonClick(View view) {
        if (pptAdapter.isEmpty()) {
            new handleCommand().execute("ppt_list", client);
        } else {
            SwitchPPTListState();
        }

    }

    private View.OnTouchListener mouse_Listener = new View.OnTouchListener() {
        float dX, dY;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    return true;
                case MotionEvent.ACTION_MOVE:
                    dX = event.getX();
                    dY = event.getY();
                    String msg = String.format("move_mouse|%s|%s", dX, dY);
                    new handleCommand().execute(msg, client);
                    break;
                default:
                    return false;
            }
            return false;
        }
    };

    private final class connectToServer extends AsyncTask<Object, Void, Boolean> {
        private AlertDialog alert;
        private Client client;

        @Override
        protected Boolean doInBackground(Object... objects) {
            String ip = (String) objects[0];
            Integer port = (Integer) objects[1];
            client = (Client) objects[2];
            alert = (AlertDialog) objects[3];
            try {
                if (!client.isConnected())
                    client.startConnection(ip, port);
                currentPage.setText("1");
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean socket) {
            if (socket) {
                Toast.makeText(getApplicationContext(),
                        "Connected!",
                        Toast.LENGTH_LONG)
                        .show();
                alert.cancel();
            } else {
                Toast.makeText(getApplicationContext(),
                        "Server Not Found!",
                        Toast.LENGTH_LONG)
                        .show();
            }
        }
    }

    private final class getServerList extends AsyncTask<Object, Integer, Boolean> {
        private AlertDialog alert;
        private TextView progress;
        private MenuAdapter adapter;
        private Client client;

        @Override
        protected Boolean doInBackground(Object... params) {
            alert = (AlertDialog) params[2];
            adapter = (MenuAdapter) params[3];
            client = (Client) params[4];
            progress = alert.findViewById(R.id.serverSearchIp);
            progress.setVisibility(View.VISIBLE);
            String startingIP = ((String) params[0]).replaceAll("(.*\\.)\\d+$", "$1");
            String startingPort = (String) params[1];
            List<RowItem> rowItems = new ArrayList<>();
            ExecutorService es = Executors.newFixedThreadPool(20);
            for (int ip = 1; ip < 256; ip++) {
                publishProgress(ip);
                try {
                    client.startConnection(startingIP + ip, Integer.parseInt(startingPort));
                    RowItem pc = new RowItem(startingIP + ip, startingPort.toString(), startingIP + ip + ":" + startingPort.toString());
                    rowItems.add(pc);
                    currentPage.setText("1");
                    break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (rowItems.isEmpty())
                return false;

            es.shutdown();
            adapter.setItems(rowItems);
            return true;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progress.setText(values[0] + "/255");
        }

        @Override
        protected void onPostExecute(Boolean result) {
            ProgressBar bar = alert.findViewById(R.id.progress_loader);
            bar.setVisibility(View.INVISIBLE);
            progress.setVisibility(View.INVISIBLE);
            if (result) {
                serverList = alert.findViewById(R.id.server_list);
                serverList.setAdapter(adapter);
                serverList.setVisibility(View.VISIBLE);
                serverList.setOnItemClickListener((parent, view, position, id) -> {
                    RowItem entry = (RowItem) adapter.getItem(position);
                    String[] ip_port = entry.value.split(":");
                    new connectToServer().execute(ip_port[0], Integer.parseInt(ip_port[1]), client, alert);
                });
            } else {
                TextView err = alert.findViewById(R.id.noServerError);
                err.setVisibility(View.VISIBLE);
            }
        }
    }

    private final class handleCommand extends AsyncTask<Object, Integer, String> {
        private Client client;
        private String msg;

        @Override
        protected String doInBackground(Object... objects) {
            client = (Client) objects[1];
            msg = (String) objects[0];
            try {
                if (client.isConnected())
                    return client.sendMessage(msg);
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                switch (msg) {
                    case "ppt_list":
                        String[] file_list = result.split("\\|");
                        List<RowItem> items = new ArrayList<>();
                        for (int i = 0; i < file_list.length; i++) {
                            String[] file = file_list[i].split("\\|");
                            String name = file[0];
                            String value = String.valueOf(i);
                            items.add(new RowItem(name, "PPT", value));
                        }
                        pptAdapter = new MenuAdapter(context, items, R.drawable.ic_microsoft_powerpoint);
                        fileList.setAdapter(pptAdapter);

                        SwitchPPTListState();
                        break;
                }
            }
        }
    }
}