package platform

actual fun currentTimeMillis(): Long = kotlinx.datetime.Clock.System.now().toEpochMilliseconds()
