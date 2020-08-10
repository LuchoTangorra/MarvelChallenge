package com.example.androidchallenge.Characters

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.androidchallenge.Characters.DataSource.CharactersViewModel
import com.example.androidchallenge.R
import org.koin.android.viewmodel.ext.android.viewModel

class CharactersFragment : Fragment() {

    private val viewModel: CharactersViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_characters, container, false)
    }
}