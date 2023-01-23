package com.example.retrofitexample

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofitexample.databinding.FragmentFirstBinding
import com.example.retrofitexample.model.CharacterItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment(), RecyclerAdapter.onCharacterClickListener {

    private var _binding: FragmentFirstBinding? = null
    private val baseUrl: String = "https://api.genshin.dev/"
    private val genshinViewModel: GenshinViewModel by activityViewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val emptyList: ArrayList<CharacterItem> = ArrayList<CharacterItem>()
        binding.button.setOnClickListener {
            getData()
        }


    }

    private fun getData() {
        val retrofit: Retrofit = Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(
            GsonConverterFactory.create()).build()

        val genshinAPI = retrofit.create(GenshinAPI::class.java)
        val call: Call<ArrayList<String>> = genshinAPI.getCharacterNames()
        call.enqueue(object: Callback<ArrayList<String>> {
            override fun onResponse(
                call: Call<ArrayList<String>>,
                response: Response<ArrayList<String>>
            ) {
                if (!response.isSuccessful) {
                    Log.e("FAIL", "Retrofit Failure")
                    return
                }

                val characterList: ArrayList<String>? = response.body()
                val characterItemList: ArrayList<CharacterItem> = ArrayList()
                if (characterList != null) {
                    for (i in 0 until characterList.size) {
                        characterItemList.add(CharacterItem(characterList[i], true))
                    }
                }

                genshinViewModel.setCharacterItemList(characterItemList)
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            }

            override fun onFailure(call: Call<ArrayList<String>>, t: Throwable) {
                t.message?.let{ Log.e("FAILURE",it)}
            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}