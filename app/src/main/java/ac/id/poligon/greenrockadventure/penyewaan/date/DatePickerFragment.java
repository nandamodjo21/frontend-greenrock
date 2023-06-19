package ac.id.poligon.greenrockadventure.penyewaan.date;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment {
    private onDateClickListener onDateClickListener;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        final Calendar calendar = Calendar.getInstance();
        int tahun = calendar.get(Calendar.YEAR);
        int bulan = calendar.get(Calendar.MONTH);
        int hari = calendar.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                onDateClickListener.onDateSet(datePicker, i, i1, i2);
            }
        }, tahun, bulan, hari);
    }
    public void setOnDateClickListener(onDateClickListener onDateClickListener){
        if (onDateClickListener != null){
            this.onDateClickListener= onDateClickListener;
        }
    }
    public interface onDateClickListener{
        void onDateSet(DatePicker datePicker, int i, int i1, int i2);
    }
}
