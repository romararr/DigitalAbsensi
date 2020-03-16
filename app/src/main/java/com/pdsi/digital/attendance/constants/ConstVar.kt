package com.pdsi.digital.attendance.constants

class ConstVar {
    companion object {
        val BASE_URL = "https://apps.pertamina.com/PDSIDAS/api/"
        val DEV_URL = "https://apps.pertamina.com/PDSIDAS/apidev/index.php/"
        val LOCAL_URL = "http://10.13.1.55/PDSIDAS/apidev/"

        val USR = "user"
        val ADM = "admin"

        val INOFFICE = "DALAMKANTOR"
        val OUTOFFICE = "LUARKANTOR"
        val COME = "come"
        val RETURN = "return"
        val BREAK = "break"
        val ENDBREAK = "end_of_break"
        val SUDAHABSEN = "SUDAHABSEN"
        val ATTENDYET = "BELUMSENDDATANG"
        val MANUAL = "MANUAL"
        val FAR_FROMOFFICE = "Due to far from office"

        val EXPIRED = "Token is Expired"
        val MSG_FAROFFICE = "Anda absen di luar kantor, silahkan tunggu persetujuan atasan."
    }
}