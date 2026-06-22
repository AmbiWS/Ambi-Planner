package com.ambiws.ambiplanner.features.home.ui

import androidx.recyclerview.widget.LinearLayoutManager
import com.ambiws.ambiplanner.base.BaseFragment
import com.ambiws.ambiplanner.base.UiState
import com.ambiws.ambiplanner.base.list.DefaultListDiffer
import com.ambiws.ambiplanner.databinding.FragmentHomeBinding
import com.ambiws.ambiplanner.features.home.ui.list.RoutineAdapterDelegate
import com.ambiws.ambiplanner.features.home.ui.list.RoutineBaseItemModel
import com.ambiws.ambiplanner.utils.extensions.subscribe
import com.google.android.material.snackbar.Snackbar
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>(
    FragmentHomeBinding::inflate
) {

    // TODO Handle empty list message
    private val adapter by lazy {
        AsyncListDifferDelegationAdapter(
            DefaultListDiffer<RoutineBaseItemModel>(),
            RoutineAdapterDelegate.routineAdapterDelegate { itemModel ->
                viewModel.navigateToEditRoutine(itemModel)
            },
        )
    }

    override fun setupUi() {
        super.setupUi()
        with(binding) {
            rvRoutines.layoutManager = LinearLayoutManager(requireContext())
            rvRoutines.adapter = adapter
        }
    }

    override fun setupListeners() {
        super.setupListeners()
        binding.ivAdd.setOnClickListener {
            viewModel.navigateToEditRoutine(null)
        }
    }

    override fun setupObservers() {
        super.setupObservers()
        subscribe(viewModel.routineLiveData) {
            adapter.items = it
        }
        subscribe(viewModel.stateLiveEvent) {
            when (it) {
                is UiState.Error -> {
                    Snackbar
                        .make(
                            requireContext(),
                            binding.root,
                            "${it.error} is occurred",
                            Snackbar.LENGTH_SHORT
                        )
                        .show()
                }
                UiState.Loading -> {
                    // Show loading
                }
                UiState.Success -> {
                    // Show success
                }
            }
        }
    }
}
