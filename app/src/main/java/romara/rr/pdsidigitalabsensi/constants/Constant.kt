package romara.rr.pdsidigitalabsensi.constants

import romara.rr.pdsidigitalabsensi.model.Location.MLogUser

object Constant {
    fun dataLogAbsen(): MutableList<MLogUser>{
        return mutableListOf(
            MLogUser("205", "1238123", "yoga.hermawan", "2020-01-20 07:45:08.000", -6.3891, 106.8583),
            MLogUser("205", "1238123", "yoga.hermawan", "2020-01-20 07:45:08.000", -6.3891, 106.8583)
        )
    }
}