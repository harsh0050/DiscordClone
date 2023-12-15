package com.harsh.discordclone.presentation.authentication.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.harsh.discordclone.adapter.CountryCodeRecyclerViewAdapter
import com.harsh.discordclone.databinding.FragmentCountryCodeBinding
import com.harsh.discordclone.model.CountryCode
import com.harsh.discordclone.presentation.authentication.AuthViewModel

class CountryCodeFragment : Fragment() {
    private lateinit var binding: FragmentCountryCodeBinding
    private lateinit var adapter: CountryCodeRecyclerViewAdapter
    private val viewModel by activityViewModels<AuthViewModel>()
    private var countryCodes: List<CountryCode> = ArrayList()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentCountryCodeBinding.inflate(inflater)
        adapter = CountryCodeRecyclerViewAdapter().setOnClickListener{
            viewModel.updateCountryCode(it)
            findNavController().navigateUp()
        }

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        viewModel.getCountryCodes().observe(viewLifecycleOwner){
            countryCodes = it
            adapter.setData(it)
        }


        binding.searchBar.addTextChangedListener {
            if(it.isNullOrBlank()){
                adapter.setData(countryCodes)
            }else{
                adapter.setData(filterList(it.toString().lowercase()))
            }
        }

        binding.upBtn.setOnClickListener {
            findNavController().navigateUp()
        }
        return binding.root
    }
    private fun filterList(query: String): List<CountryCode>{
        val filteredList : MutableList<CountryCode> = mutableListOf()
        countryCodes.forEach {
            if(it.name.lowercase().startsWith(query))
                filteredList.add(it)
        }
        return filteredList
    }
    companion object{
        const val TAG = "CountryCodeFragment.kt"
    }
}