package com.example.baitap1;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
public class MainActivity extends AppCompatActivity {
    private SeekBar seekBar;
    private View colorView1, colorView2, colorView3, colorView4, colorView5;
    private ColorViewModel colorViewModel;
    private int currentSeekBarProgress = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.relative_layout);
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setupToolbar();

        colorViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(ColorViewModel.class);

        setSupportActionBar(myToolbar);
        seekBar = findViewById(R.id.seekBar);
        colorView1 = findViewById(R.id.leftView1);
        colorView2 = findViewById(R.id.leftView2);
        colorView3 = findViewById(R.id.rightView1);
        colorView4 = findViewById(R.id.rightView2);
        colorView5 = findViewById(R.id.rightView3);

        // Set giá trị của SeekBar từ ViewModel hoặc biến toàn cục
        seekBar.setProgress(colorViewModel.getSeekBarProgress());
        currentSeekBarProgress = seekBar.getProgress();

        // Set up the SeekBar listener
        setupSeekBarListener();
    }

    private void setupSeekBarListener() {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateColors(progress);
                // Lưu giá trị của SeekBar vào ViewModel và biến toàn cục
                colorViewModel.setSeekBarProgress(progress);
                currentSeekBarProgress = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Not needed for this example
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Not needed for this example
            }
        });
    }

    private void updateColors(int progress) {
        // Update the colors of the views based on the SeekBar progress
        int color1 = Color.rgb(247, 255, progress);
        int color2 = Color.rgb(0, 0, progress);
        int color3 = Color.rgb(progress + 166, 0, 0);
        int color4 = Color.rgb(progress, 178, progress+6);
        int color5 = Color.rgb(0, 192+ progress, 192+ progress);

        // Find views by their IDs in the current layout
        View colorView1 = findViewById(R.id.leftView1);
        View colorView2 = findViewById(R.id.leftView2);
        View colorView3 = findViewById(R.id.rightView1);
        View colorView4 = findViewById(R.id.rightView2);
        View colorView5 = findViewById(R.id.rightView3);

        colorView1.setBackgroundColor(color1);
        colorView2.setBackgroundColor(color2);
        colorView3.setBackgroundColor(color3);
        colorView4.setBackgroundColor(color4);
        colorView5.setBackgroundColor(color5);

        // Lưu giá trị của SeekBar vào ViewModel
        colorViewModel.setSeekBarProgress(progress);
    }

    private void setupToolbar() {
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_more_information) {
            showInformationDialog();
            return true;
        } else if (id == R.id.action_choose_layout) {
            showChooseLayoutDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showInformationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thông tin nhóm");
        builder.setMessage("Nhóm 9, Bài tập 1");

        builder.setPositiveButton("Đóng", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showChooseLayoutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Layout");
        final CharSequence[] items = {"Linear Layout", "Relative Layout", "Table Layout"};

        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        setContentView(R.layout.linear_layout);
                        setupViews();
                        break;
                    case 1:
                        setContentView(R.layout.relative_layout);
                        setupViews();
                        break;
                    case 2:
                        setContentView(R.layout.table_layout);
                        setupViews();
                        break;
                }
                setupToolbar();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void setupViews() {
        seekBar = findViewById(R.id.seekBar);
        seekBar.setProgress(currentSeekBarProgress);  // Set lại giá trị của SeekBar
        setupSeekBarListener();
        // Update colors based on current SeekBar progress
        updateColors(currentSeekBarProgress);
    }

    public static class ColorViewModel extends ViewModel {
        private int seekBarProgress;

        public int getSeekBarProgress() {
            return seekBarProgress;
        }

        public void setSeekBarProgress(int progress) {
            this.seekBarProgress = progress;
        }
    }
}