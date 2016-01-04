package com.example.villip.photoagent;

        import android.app.DatePickerDialog;
        import android.app.Dialog;
        import android.app.DialogFragment;
        import android.app.TimePickerDialog;
        import android.content.Intent;
        import android.graphics.Bitmap;
        import android.os.Bundle;
        import android.provider.MediaStore;
        import android.support.v7.app.AppCompatActivity;
        import android.text.format.DateFormat;
        import android.view.View;
        import android.widget.Button;
        import android.widget.DatePicker;
        import android.widget.EditText;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.widget.TimePicker;

        import java.util.Calendar;

public class FormActivity extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final String TAG = "FormActivity";

    private ImageView image;
    private Button setDate, setTime, ok;
    private static TextView time, date;
    private EditText place;
    private Bitmap icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        image = (ImageView) findViewById(R.id.image_iV);
        setDate = (Button) findViewById(R.id.set_date);
        setTime = (Button) findViewById(R.id.set_time);
        time = (TextView) findViewById(R.id.time_tV);
        date = (TextView) findViewById(R.id.date_tV);
        place = (EditText) findViewById(R.id.place_eT);
        ok = (Button) findViewById(R.id.btn_ok);

        image.setOnClickListener(onImageClick());
        setDate.setOnClickListener(onSetDateClick());
        setTime.setOnClickListener(onSetTimeClick());
        ok.setOnClickListener(onOkClick());
    }

    private View.OnClickListener onSetTimeClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getFragmentManager(), "timePicker");
            }
        };
    }

    private View.OnClickListener onSetDateClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getFragmentManager(), "datePicker");
            }
        };
    }

    private View.OnClickListener onImageClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        };
    }


    private View.OnClickListener onOkClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();

                if (icon != null) {
                    intent.putExtra("image", icon);
                }

                if (date.getText().toString().length() > 0) {
                    intent.putExtra("date", date.getText().toString());
                }

                if (time.getText().toString().length() > 0) {
                    intent.putExtra("time", time.getText().toString());
                }

                if (place.getText().toString().length() > 0) {
                    intent.putExtra("place", place.getText().toString());
                }

                setResult(RESULT_OK, intent);
                finish();
            }
        };
    }

    public void takePicture() {
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            icon = data.getParcelableExtra("data");
            image.setImageBitmap(icon);
        }
    }


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_adds_new, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_apply:
                Intent intent = new Intent();

                if (icon != null) {
                    intent.putExtra("image", icon);
                }

                if (date.getText().toString().length() > 0) {
                    intent.putExtra("date", date.getText().toString());
                }

                if (time.getText().toString().length() > 0) {
                    intent.putExtra("time", time.getText().toString());
                }

                if (place.getText().toString().length() > 0) {
                    intent.putExtra("place", place.getText().toString());
                }

                setResult(RESULT_OK, intent);
                finish();
                break;
        }
        return true;
    }
    */

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            time.setText(hourOfDay + ":" + minute);
        }
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            date.setText(year + "/" + (month + 1) + "/" + day);
        }
    }
}