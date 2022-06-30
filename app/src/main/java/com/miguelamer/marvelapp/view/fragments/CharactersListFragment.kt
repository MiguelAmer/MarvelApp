package com.miguelamer.marvelapp.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.miguelamer.marvelapp.R
import com.miguelamer.marvelapp.databinding.FragmentCharactersListBinding
import com.miguelamer.marvelapp.domain.model.MarvelCharacter
import com.miguelamer.marvelapp.view.adapters.CharactersListAdapter
import com.miguelamer.marvelapp.view.interfaces.RecyclerViewClickListener
import com.miguelamer.marvelapp.view.viewmodel.CharactersListViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CharactersListFragment: Fragment(), RecyclerViewClickListener {

    companion object {
        const val CHARACTER_MODEL_PARAM = "CharacterModel"
    }

    private lateinit var binding: FragmentCharactersListBinding
    private val charactersListViewModel: CharactersListViewModel by viewModels()
    private lateinit var adapter: CharactersListAdapter
    private var dataset: MutableList<MarvelCharacter?> = mutableListOf()
    private lateinit var recyclerView: RecyclerView
    private var isLoading: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharactersListBinding.inflate(inflater)
        recyclerView = binding.charactersListRecyclerview
        adapter = CharactersListAdapter(dataset, this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        charactersListViewModel.loadCharacters(dataset.size)
        charactersListViewModel.characterModelList.observeOnce(viewLifecycleOwner) {
            dataset.addAll(it)
            adapter.notifyDataSetChanged()
        }
        initScrollListener()
        return binding.root
    }

    override fun onItemClicked(position: Int) {
        val bundle = Bundle()
        bundle.putSerializable(CHARACTER_MODEL_PARAM, dataset[position])
        val fragment = CharacterDetailsFragment()
        fragment.arguments = bundle
        parentFragmentManager.beginTransaction().replace(R.id.main_container, fragment).addToBackStack(null).commit()
    }


    private fun initScrollListener() {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == dataset.size - 1) {
                        isLoading = true
                        loadMore()
                    }
                }
            }
        })
    }

    private fun loadMore() {
        if (dataset[dataset.size-1] != null) {
            dataset.add(null)
        }
        adapter.notifyDataSetChanged()
        charactersListViewModel.loadCharacters(dataset.size)
        charactersListViewModel.characterModelList.observe(viewLifecycleOwner) {
            if (!adapter.dataset.contains(it[0])) {
                dataset.removeAt(dataset.size-1)
                dataset.addAll(it)
                adapter.notifyDataSetChanged()
            }
            isLoading = false
        }
    }

    private fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
        observe(lifecycleOwner, object : Observer<T> {
            override fun onChanged(t: T?) {
                observer.onChanged(t)
                removeObserver(this)
            }
        })
    }
}