class SyncWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {

    private val database = Room.databaseBuilder(
        applicationContext,
        AppDatabase::class.java, "database-name"
    ).build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://your.api.url/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(ApiService::class.java)

    override suspend fun doWork(): Result {
        val unsyncedData = database.dataDao().getUnsyncedData()
        for (data in unsyncedData) {
            try {
                val response = apiService.syncData(data).await()
                if (response.isSuccessful) {
                    data.isSynced = true
                    database.dataDao().update(data)
                }
            } catch (e: Exception) {
                return Result.retry()
            }
        }
        return Result.success()
    }
}

interface ApiService {
    @POST("sync")
    fun syncData(@Body data: DataEntity): Call<ResponseBody>
}
