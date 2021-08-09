package com.maxpay.testappmaxpay.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.maxpay.sdk.model.request.TransactionType
import com.maxpay.testappmaxpay.R
import kotlinx.android.synthetic.main.fragment_blank.*
import kotlinx.android.synthetic.main.fragment_blank.toolbar

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val tt = TransactionType.AUTH3D

        return inflater.inflate(R.layout.fragment_blank, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()

        btnFormExample.setOnClickListener {
            findNavController().navigate(R.id.action_blankFragment_to_simpleFormFragment)
        }
        btnShopExample.setOnClickListener {
            findNavController().navigate(R.id.action_blankFragment_to_shopFragment)
        }
        btnSettings.setOnClickListener {
            findNavController().navigate(R.id.action_blankFragment_to_settingsFragment)
        }

    }
    private fun initToolbar() {
        val toolbar = toolbar as Toolbar
        toolbar.navigationIcon = null
        toolbar.title = resources.getString(R.string.main_screen)
        toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
    }

}