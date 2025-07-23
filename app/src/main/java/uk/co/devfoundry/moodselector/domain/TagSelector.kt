package uk.co.devfoundry.moodselector.domain


interface TagSelector {
    fun selectMood(mood: String)
    fun getSelectedMoods(): List<String>
}
