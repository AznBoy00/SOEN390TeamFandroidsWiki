package org.wikipedia.notifications;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.AlarmManager;
import android.view.View;
import android.widget.TimePicker;
import android.app.TimePickerDialog;
import android.widget.TextView;
import org.wikipedia.R;
import org.wikipedia.activity.BaseActivity;
import java.util.Calendar;

public class NotificationSchedulerActivity extends BaseActivity {
    TextView TextView_Time;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_scheduler);
        TextView_Time= findViewById(R.id.TextView_Time);

    }

    // Method that shows the TimePickerDialog when the button is clicked
    public void btn_set_alarm(View view) {

        showDialog(1);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        Calendar c = Calendar.getInstance();
        TimePickerDialog tpd = new TimePickerDialog(NotificationSchedulerActivity.this,AlertDialog.THEME_DEVICE_DEFAULT_DARK, TimeMap, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), false);

        if (id == 1) {
            return tpd;
        }
        return null;

    }

    protected TimePickerDialog.OnTimeSetListener TimeMap =
            new TimePickerDialog.OnTimeSetListener() {

                // Sets the time of the notification via the TimePickerDialog and sets the alarmManager to repeat the notifications everyday
                @Override
                public void onTimeSet(TimePicker TimeP, int hourOfDay, int minute) {

                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    calendar.set(Calendar.MINUTE, minute);
                    calendar.set(Calendar.SECOND, 0);

                    Intent alertIntent = new Intent(getApplicationContext(), NotificationReceiver.class);
                    AlarmManager alarmManager = (AlarmManager) getSystemService( ALARM_SERVICE );

                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY , PendingIntent.getBroadcast(getApplicationContext(), 0, alertIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT ));

                    //alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), PendingIntent.getBroadcast(getApplicationContext(), 0, alertIntent,
                    //PendingIntent.FLAG_UPDATE_CURRENT ));
                    TextView_Time.setText(hourOfDay + ":" + minute);

                }
            };
}
