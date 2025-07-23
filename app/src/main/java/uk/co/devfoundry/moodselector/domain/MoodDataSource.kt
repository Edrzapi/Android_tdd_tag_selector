package uk.co.devfoundry.moodselector.domain
interface MoodDataSource {
    suspend fun getMoods(): List<String>
}