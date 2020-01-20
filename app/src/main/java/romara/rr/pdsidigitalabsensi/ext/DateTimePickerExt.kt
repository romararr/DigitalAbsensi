package romara.rr.pdsidigitalabsensi.ext

import android.app.DatePickerDialog
import android.content.Context
import romara.rr.pdsidigitalabsensi.view.LogsActivity
import java.text.SimpleDateFormat
import java.util.*

//fun Context.datePick(context: Context) {
//    val cal = Calendar.getInstance()
//    val dateSetListener = DatePickerDialog.OnDateSetListener { datePicker, day, month, year ->
//        cal.set(Calendar.DAY_OF_MONTH, day)
//        cal.set(Calendar.MONTH, month)
//        cal.set(Calendar.YEAR, year)
//        LogsActivity.selectedDate = SimpleDateFormat("DDDD, DD MM YYYY").format(cal)
//    }
//    DatePickerDialog(
//        context,
//        dateSetListener,
//        cal.get(Calendar.DAY_OF_MONTH),
//        cal.get(Calendar.MONTH),
//        cal.get(Calendar.YEAR)
//    ).show()
//}