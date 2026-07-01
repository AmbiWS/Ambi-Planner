package com.ambiws.ambiplanner.features.home.ui

import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.ambiws.ambiplanner.R
import com.ambiws.ambiplanner.base.BaseFragment
import com.ambiws.ambiplanner.base.UiState
import com.ambiws.ambiplanner.base.list.DefaultListDiffer
import com.ambiws.ambiplanner.databinding.FragmentHomeBinding
import com.ambiws.ambiplanner.features.home.ui.list.RoutineAdapterDelegate
import com.ambiws.ambiplanner.features.home.ui.list.RoutineBaseItemModel
import com.ambiws.ambiplanner.features.home.ui.list.RoutineItemModel
import com.ambiws.ambiplanner.utils.extensions.subscribe
import com.google.android.material.snackbar.Snackbar
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>(
    FragmentHomeBinding::inflate
) {

    private val adapter by lazy {
        AsyncListDifferDelegationAdapter(
            DefaultListDiffer<RoutineBaseItemModel>(),
            RoutineAdapterDelegate.routineAdapterDelegate(
                onClickListener = { itemModel ->
                    viewModel.navigateToEditRoutine(itemModel)
                },
                onDoneChanged = { itemModel, isDone ->
                    viewModel.onRoutineDoneChanged(itemModel, isDone)
                }
            ),
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
            binding.tvEmpty.isVisible = it.isEmpty()
            binding.tvTotal.isVisible = it.isNotEmpty()
            adapter.items = it

            var totalMinutes = 0
            it.filterIsInstance<RoutineItemModel>().forEach { item ->
                item.timeToComplete?.let { timeStr ->
                    val regex = "(\\d+)h (\\d+)m".toRegex()
                    val match = regex.find(timeStr)
                    if (match != null) {
                        val (hours, minutes) = match.destructured
                        totalMinutes += hours.toInt() * 60 + minutes.toInt()
                    }
                }
            }
            val h = totalMinutes / 60
            val m = totalMinutes % 60
            binding.tvTotal.text = getString(R.string.total_time_today, h, m)
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
