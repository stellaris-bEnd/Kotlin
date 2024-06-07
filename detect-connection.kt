class NetworkStatusReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (isOnline(context)) {
            val workManager = WorkManager.getInstance(context)
            workManager.enqueue(OneTimeWorkRequest.from(SyncWorker::class.java))
        }
    }

    private fun isOnline(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}
