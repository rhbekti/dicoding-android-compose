package com.rhbekti.jetheroes.data

import com.rhbekti.jetheroes.model.Hero
import com.rhbekti.jetheroes.model.HeroesData

class HeroRepository {
    fun getHeroes(): List<Hero> = HeroesData.heroes

}