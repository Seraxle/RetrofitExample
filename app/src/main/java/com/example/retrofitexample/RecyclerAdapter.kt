package com.example.retrofitexample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitexample.model.CharacterItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.character_item.view.*
import java.util.*
import kotlin.collections.ArrayList

class RecyclerAdapter(private val recyclerList: ArrayList<CharacterItem>, private val listener: onCharacterClickListener):
    RecyclerView.Adapter<RecyclerAdapter.recyclerViewHolder>(), Filterable {

    private var filterList: ArrayList<CharacterItem> = ArrayList(recyclerList)
    private var anotherList: ArrayList<CharacterItem> = ArrayList()

    interface onCharacterClickListener {
        fun onCharacterClickListener(position: Int) {

        }
    }
    inner class recyclerViewHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener {
        val characterName = view.charName
        val characterImage = view.imageView
        val characterVision = view.vision
        val objType = view.objectType
        init {
            view.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position: Int = bindingAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onCharacterClickListener(position)
            }
        }

    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerAdapter.recyclerViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.character_item, p0, false)
        return recyclerViewHolder(view)
    }

    override fun onBindViewHolder(p0: RecyclerAdapter.recyclerViewHolder, p1: Int) {
        val charItem = recyclerList[p1]
        p0.characterName.text = charItem.characterName.capitalize().replace("-", " ", true)

        if (charItem.isCharacter) {
            p0.objType.text = "Character"
            if (charItem.characterName.contains("traveler", true)) {
                Picasso.get().load("https://api.genshin.dev/characters/traveler-anemo/portrait.png")
                    .into(p0.characterImage)
            } else {
                Picasso.get().load(
                    "https://api.genshin.dev/characters/"
                            + charItem.characterName + "/gacha-splash.png"
                ).into(p0.characterImage)
            }

        }

        else {
            p0.objType.text = "Weapon"
            Picasso.get().load("https://api.genshin.dev/weapons/"
                    + charItem.characterName + "/icon.png").into(p0.characterImage)
        }
    }

    override fun getItemCount(): Int {
        return recyclerList.size
    }

    override fun getFilter(): Filter {
        return characterFilter
    }

    private var characterFilter = object: Filter() {
        override fun performFiltering(p0: CharSequence?): FilterResults? {
            var filteredList: ArrayList<CharacterItem> = ArrayList()
            if (p0 == null || p0.length == 0) filteredList.addAll(filterList)
            else {
                var filterPattern: String = p0.toString().lowercase().trim()
                for (i in 0 until recyclerList.size) {
                    if (recyclerList[i].characterName.lowercase().contains(filterPattern)) {
                        filteredList.add(recyclerList[i])
                    }
                }
            }

            var results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
            recyclerList.clear()
            if (p1 != null) recyclerList.addAll(p1.values as ArrayList<CharacterItem>)
            notifyDataSetChanged()
        }
    }

}