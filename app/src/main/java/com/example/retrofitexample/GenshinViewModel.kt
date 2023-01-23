package com.example.retrofitexample

import androidx.lifecycle.ViewModel
import com.example.retrofitexample.model.CharacterItem

class GenshinViewModel: ViewModel() {

    private var characterItemList: ArrayList<CharacterItem> = ArrayList()

    fun getCharacterItemList(): ArrayList<CharacterItem> {
        return this.characterItemList
    }

    fun setCharacterItemList(characterItemList: ArrayList<CharacterItem>) {
        this.characterItemList = characterItemList
    }
}