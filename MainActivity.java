package com.example.android.training;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Toast;

import com.android.debug.hv.ViewServer;

public class MainActivity extends AppCompatActivity {
    SeekBar seekbar; //@param declare seekbar object
    View redBox;  //@param declare redbox object
    View blueBox;  //@param declare bluebox object

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewServer.get(this).addWindow(this); // WK to get the view on pixel perfect tool
        seekbar = (SeekBar) findViewById(R.id.seek_bar); // refer it to xml layout
        redBox = findViewById(R.id.redBox);  // refer it to xml main activity red box
        blueBox = findViewById(R.id.blueBox); // refer it to xml main activity blue box
        seekbar.setMax(255);  // set max value for seek bar

        // lisenting method for seek bar
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override   // to set onProgressChanged parameters
            //as seekbar progress, colors of boxs will be changed
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress >= 20) {       //to set minimum of seekbar as setMin is supported from API26
                    Double progressRed = progress / 3.3;
                    int progressredint = progressRed.intValue();  // to convert double value to int value
                    // color to change incremently from red rgb(244,67,54) to rgb(167,255,235)
                    redBox.setBackgroundColor(Color.rgb(255 - progressredint, progress, progress - 20));
                    // color to change incremently from blue rgb(13,71,161) to rgb(255,249,196)
                    blueBox.setBackgroundColor(Color.rgb(progress, progress, 196));
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });   // closing method

    }
    // WK to get the view on pixel perfect tool
    public void onDestroy() {
        super.onDestroy();
        ViewServer.get(this).removeWindow(this);
    }
    // WK to get the view on pixel perfect tool
    public void onResume() {
        super.onResume();
        ViewServer.get(this).setFocusedWindow(this);
    }
// create the menu and refer it to xml menu layout
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater optionMenu = getMenuInflater();
        optionMenu.inflate(R.menu.option_menu, menu); //to show the menu when click on it
        return true;    // to close the class
    }

    public boolean onOptionsItemSelected(MenuItem item) {    // select Menu method and there no need for conditional switch as Menu has one item

        final AlertDialog.Builder daillog = new AlertDialog.Builder(this);
        View dailogView = getLayoutInflater().inflate(R.layout.dailog,null);
        daillog.setView(dailogView);  // to show the dailog when select from menu
        final AlertDialog dailogShow = daillog.create();
        dailogShow.show(); // to show the dailog when select from menu
        Button not = (Button) dailogView.findViewById(R.id.Not_Now);    // decalre and refet the Not button
        // and notice that button is inside the dailog so we have to find the view inside the dailog not in the main layout
        Button visit = (Button) dailogView.findViewById(R.id.visit_forums); // decalre and refet the Visit Forums button

        not.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dailogShow.dismiss();  // to close the dailog
            }
        });
        visit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {  //start intent action and visit the forum webpage
                String URL = "https://macdiscussions.udacity.com/t/training-task-3-for-students-who-completed-the-user-input-part/102430";
                Uri webpage = Uri.parse(URL);
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                if (intent.resolveActivity(getPackageManager()) != null) {  // to prevent the app from crush if intent doesn't find app to open the webpage
                    startActivity(intent);
                }
            }
        });

        return super.onOptionsItemSelected(item); // return to close the method

    }
}