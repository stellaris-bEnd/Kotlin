@Entity
data class DataEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val data: String,
    val isSynced: Boolean = false
)

@Dao
interface DataDao {
    @Insert
    suspend fun insert(data: DataEntity): Long

    @Query("SELECT * FROM DataEntity WHERE isSynced = 0")
    suspend fun getUnsyncedData(): List<DataEntity>

    @Update
    suspend fun update(data: DataEntity)
}

@Database(entities = [DataEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dataDao(): DataDao
}
