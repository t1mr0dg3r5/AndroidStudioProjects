package net.uk.rodgers.myrecipeapp

import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    data class RecipeState(
        val loading: Boolean = true,
        val list: List<Category> = emptyList(),
        val error: String? = null)

}