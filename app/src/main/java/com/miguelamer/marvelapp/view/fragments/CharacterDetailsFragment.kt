package com.miguelamer.marvelapp.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.miguelamer.marvelapp.R
import com.miguelamer.marvelapp.databinding.FragmentCharacterDetailsBinding
import com.miguelamer.marvelapp.domain.model.MarvelCharacter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterDetailsFragment : Fragment() {

    private lateinit var binding: FragmentCharacterDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val marvelCharacter = requireArguments().getSerializable(CharactersListFragment.CHARACTER_MODEL_PARAM) as MarvelCharacter
        initViews(marvelCharacter)
    }

    private fun initViews(marvelCharacter: MarvelCharacter) {
        if (marvelCharacter.description == "") {
            binding.characterDescriptionTextview.text = binding.root.context.getString(R.string.no_description_available)
        } else {
            binding.characterDescriptionTextview.text = marvelCharacter.description
        }
        binding.characterNameTextview.text = marvelCharacter.name
        Glide.with(binding.root).load(marvelCharacter.thumbnail.getUrl()).into(binding.characterDetailImageview)
    }
}