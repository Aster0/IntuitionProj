package com.example.intuitionproject.screens.homedashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.intuitionproject.R
import com.example.intuitionproject.databinding.FragmentLoadingBinding


/**
 * A simple [Fragment] subclass.
 * Use the [LoadingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoadingFragment : Fragment() {
    private lateinit var binding: FragmentLoadingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoadingBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}