package com.ambiws.ambiplanner.features.home.ui

import com.ambiws.ambiplanner.base.BaseFragment
import com.ambiws.ambiplanner.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>(
    FragmentHomeBinding::inflate
) {

    override fun setupListeners() {
        super.setupListeners()
        binding.ivAdd.setOnClickListener {
            viewModel.navigateToEditRoutine()
        }
    }
}
