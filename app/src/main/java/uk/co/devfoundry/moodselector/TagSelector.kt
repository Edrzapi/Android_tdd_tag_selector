package uk.co.devfoundry.moodselector


interface TagSelector {
    fun selectMood(mood: String)
    fun getSelectedMoods(): List<String>
}
