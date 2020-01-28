package romara.rr.pdsidigitalabsensi.local

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import org.threeten.bp.LocalDate

class SharedPrefManager private constructor(private val context: Context) {

    companion object {
        private val SP_TOKEN = "token_shared_pref"
        private val SP_ROLE = "role_shared_pref"
        private val SP_USER = "user_shared_pref"
        private val SP_NIP = "nip_shared_pref"
        private val SP_DATENOW = "now_shared_pref"
        private val SP_TIME_IN = "come_shared_pref"
        private val SP_TIME_OUT = "return_shared_pref"
        private val SP_BREAK = "break_shared_pref"
        private val SP_END_BREAK = "endbreak_shared_pref"
        private val SP_LOC = "location_shared_pref"

        private var mInstance: SharedPrefManager? = null

        @Synchronized
        fun getInstance(context: Context): SharedPrefManager {
            if (mInstance == null) {
                mInstance = SharedPrefManager(context)
            }
            return mInstance as SharedPrefManager
        }
    }

    private fun pref(key: String): SharedPreferences {
        return context.getSharedPreferences(key, Context.MODE_PRIVATE)
    }

    fun isLoggedIn(): Boolean {
        return pref(SP_TOKEN).getString("token", null) != null
    }

    fun saveToken(token: String) {
        val editor = pref(SP_TOKEN).edit()
        editor.putString("token", token)
        editor.apply()
    }

    fun saveRole(role: String){
        val editor = pref(SP_ROLE).edit()
        editor.putString("role", role)
        editor.apply()
    }

    fun saveLocation(key: String, coor: Double){
        val editor = pref(SP_LOC).edit()
        editor.putString(key, coor.toString())
        editor.apply()
    }

    fun saveUser(user: String) {
        val editor = pref(SP_USER).edit()
        editor.putString("username", user)
        editor.apply()
    }

    fun saveNip(nip: String){
        val editor = pref(SP_NIP).edit()
        editor.putString("nip", nip)
        editor.apply()
    }

    fun saveTimeIn(time: String) {
        val editor = pref(SP_TIME_IN).edit()
        editor.putString("timein", time)
        editor.apply()

        Log.d("COME", time)
    }

    fun saveTimeOut(time: String) {
        val editor = pref(SP_TIME_OUT).edit()
        editor.putString("timeout", time)
        editor.apply()

        Log.d("OUT", time)
    }

    fun saveBreak(breakTime: String) {
        val editor = pref(SP_BREAK).edit()
        editor.putString("break", breakTime)
        editor.apply()
    }

    fun saveEndBreak(breakTime: String) {
        val editor = pref(SP_END_BREAK).edit()
        editor.putString("endbreak", breakTime)
        editor.apply()
    }

    fun saveDateNow(now: Int) {
        val editor = pref(SP_DATENOW).edit()
        editor.putInt("datenow", now)
        editor.apply()
    }

    fun getToken(): String? {
        return pref(SP_TOKEN).getString("token", "")
    }

    fun getRole(): String? {
        return pref(SP_ROLE).getString("role", "user")
    }

    fun getUser(): String? {
        return pref(SP_USER).getString("username", "username")
    }

    fun getNip(): String? {
        return pref(SP_NIP).getString("nip", "")
    }

    fun getTimeIn(): String? {
        return pref(SP_TIME_IN).getString("timein", "")
    }

    fun getTimeOut(): String? {
        return pref(SP_TIME_OUT).getString("timeout", "")
    }

    fun getBreak(): String? {
        return pref(SP_BREAK).getString("break", "")
    }

    fun getEndBreak () : String? {
        return pref(SP_END_BREAK).getString("endbreak", "")
    }

    fun getLocation(key: String) : String? {
        return pref(SP_LOC).getString(key, "0")
    }

    fun getToday(): Boolean {
        return pref(SP_DATENOW).getInt("datenow", 0).equals(LocalDate.now().dayOfMonth)
    }

    fun clearRecentTime() {
        saveTimeIn("")
        saveTimeOut("")
        saveBreak("")
        saveEndBreak("")
        saveDateNow(LocalDate.now().dayOfMonth)
    }

    fun clear(): Boolean {
        return try {
            val sharedPreferences = context.getSharedPreferences(SP_TOKEN, Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.clear()
            editor.apply()
            true
        } catch (e: Exception) {
            false
        }
    }

}