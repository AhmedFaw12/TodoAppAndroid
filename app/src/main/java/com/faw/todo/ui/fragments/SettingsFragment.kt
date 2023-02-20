package com.faw.todo.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.faw.todo.R
import com.faw.todo.databinding.FragmentSettingsBinding

class SettingsFragment:Fragment() {

    lateinit var fragmentSettingsBinding: FragmentSettingsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentSettingsBinding = FragmentSettingsBinding.inflate(inflater, container , false)
        return fragmentSettingsBinding.root
    }
}