package com.ambiws.ambiplanner.features.home.ui.list

import androidx.core.view.isVisible
import com.ambiws.ambiplanner.databinding.ItemRoutineBinding
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

object RoutineAdapterDelegate {

    fun routineAdapterDelegate(
        onClickListener: (RoutineItemModel) -> Unit
    ): AdapterDelegate<List<RoutineBaseItemModel>> {
        return adapterDelegateViewBinding<RoutineItemModel, RoutineBaseItemModel, ItemRoutineBinding>(
            { layoutInflater, parent ->
                ItemRoutineBinding.inflate(layoutInflater, parent, false)
            }
        ) {
            bind {
                with (binding) {
                    tvTitle.text = item.title
                    tvDescription.apply { isVisible = item.description?.also { text = it } != null }
                    tvStart.apply { isVisible = item.startTime?.also { text = it } != null }
                    tvCompletionTime.apply { isVisible = item.timeToComplete?.also { text = it } != null }
                    ivEdit.setOnClickListener { onClickListener.invoke(item) }
                }
            }
        }
    }
}
