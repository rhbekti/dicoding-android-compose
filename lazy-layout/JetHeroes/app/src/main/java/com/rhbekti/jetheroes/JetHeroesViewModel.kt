package com.rhbekti.jetheroes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rhbekti.jetheroes.data.HeroRepository
import com.rhbekti.jetheroes.model.Hero
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class JetHeroesViewModel(repository: HeroRepository) : ViewModel() {

    private val _groupedHeroes =
        MutableStateFlow(repository.getHeroes().sortedBy { it.name }.groupBy { it.name[0] })
    val groupedHeroes: StateFlow<Map<Char, List<Hero>>> = _groupedHeroes

}

class ViewModelFactory(private val repository: HeroRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(JetHeroesViewModel::class.java)) {
            return JetHeroesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}