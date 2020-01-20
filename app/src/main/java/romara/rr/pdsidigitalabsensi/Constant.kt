package romara.rr.pdsidigitalabsensi

import romara.rr.pdsidigitalabsensi.model.Location.MLogUser

object Constant {
    fun dataLogAbsen(): MutableList<MLogUser>{
        return mutableListOf(
            MLogUser("1", "1238123", "yoga", "07:00", -12.3891, 121.1241029),
            MLogUser("2", "2193200", "romararr", "08:00", -12.3891, 121.1241029)
        )
    }
}