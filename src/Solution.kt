/**
 * Lamport's monotonic clock
 *
 * @author Vasilkov Dmitry
 */
class Solution : MonotonicClock {
    private var logicalTimeHigh by RegularInt(0)
    private var logicalTimeLow by RegularInt(0)

    private var memoryHigh by RegularInt(0)
    private var memoryLow by RegularInt(0)

    private var reserve by RegularInt(0)

    override fun write(time: Time) {
        logicalTimeLow = time.d1
        memoryLow = time.d2

        reserve = time.d3

        memoryHigh = memoryLow
        logicalTimeHigh = logicalTimeLow
    }

    override fun read(): Time {
        val highTime = logicalTimeHigh
        val lowTime = memoryHigh

        val reserveTime = reserve

        val memoryLowTime = memoryLow
        val logicalLowTime = logicalTimeLow

        return Time(
            logicalLowTime,
            if (highTime == logicalLowTime) memoryLowTime else 0,
            if (highTime == logicalLowTime
                && lowTime == memoryLowTime) reserveTime else 0
        )
    }
}
