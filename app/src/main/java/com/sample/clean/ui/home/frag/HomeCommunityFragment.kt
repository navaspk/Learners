package com.sample.clean.ui.home.frag

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.sample.clean.base.BaseFragment
import com.sample.clean.base.ItemClickListener
import com.sample.clean.databinding.FragmentCommunityHomeBinding
import com.sample.clean.ui.event.ClickEvent
import com.sample.clean.ui.home.adapter.CommunityHomeAdapter
import com.sample.clean.ui.home.viewmodel.HomeCommunityViewModel
import com.sample.core.extensions.empty
import com.sample.core.util.PreferenceManager
import com.sample.core.util.PreferenceManager.Companion.LIKED_ITEM
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * The fragment is used for display the home screen content
 */
@AndroidEntryPoint
class HomeCommunityFragment :
    BaseFragment<FragmentCommunityHomeBinding>(FragmentCommunityHomeBinding::inflate),
    ItemClickListener {

    // region VARIABLE

    private var homeAdapter: CommunityHomeAdapter? = null
    private val viewModel: HomeCommunityViewModel by viewModels()

    @Inject
    lateinit var preferenceManager: PreferenceManager

    private var event: (ClickEvent) -> Unit = { event ->
        when (event) {
            is ClickEvent.LikeClicked -> {
                lifecycleScope.launch(Dispatchers.IO) {
                    // todo: jetpack datastore
                    android.util.Log.d(
                        "mine",
                        "event like clicked=${event.checked} & pos is ${event.pos}"
                    )
                    if (event.checked) {
                        preferenceManager.setStringPolicy(
                            LIKED_ITEM,
                            event.pos.toString()
                        )
                    } else {
                        val currentValue = preferenceManager.getStringPolicy(
                            LIKED_ITEM,
                        ).split("#")

                        //for (i in 0..currentValue.size - 1) {
                            android.util.Log.d(
                                "mine",
                                "event else, pos to remove=${event.pos}, list of item available=${currentValue}"
                            )
                        //}

                        if (currentValue.contains(event.pos.toString())) {
                            //var indexToRemove = -1
                            val itr = currentValue.iterator()
                            var finalValue = String.empty
                            while (itr.hasNext()) {
                                val index = itr.next()
                                if (event.pos.toString() != index) {
                                    finalValue = if (finalValue.isEmpty())
                                        index
                                    else
                                        "$finalValue#$index"
                                    android.util.Log.d(
                                        "mine",
                                        "updating final list appending #, index=$index, finalValue=$finalValue"
                                    )
                                }
                            }

                            preferenceManager.freshUpdateOfLikeEventString(LIKED_ITEM, finalValue)

                            /*for (i in 0..currentValue.size - 1) {
                                android.util.Log.d("mine","current value is success. index is ${currentValue[i]}, pos=${event.pos}")
                                if (event.pos.toString() == currentValue[i]) {
                                    indexToRemove = i
                                    break
                                }
                            }*/
                        }
                    }

                    withContext(Dispatchers.Main) {
                        homeAdapter?.getItems()?.get(event.pos)?.like = event.checked
                        homeAdapter?.notifyItemChanged(event.pos)
                    }
                }
            }
        }
    }

    // endregion

    // region LIFECYCLE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getLearnersList()
    }

    override fun initUserInterface(view: View?) {
        //android.util.Log.d("mine", "interface for ...")
        initRecyclerView()
        initToolBar()

        viewModel.liveData.observe(this) {
            lifecycleScope.launch(Dispatchers.Main) {
                homeAdapter?.setItems(it)
            }
        }
    }

    // endregion


    // region UTILS
    private fun initToolBar() {

    }

    private fun initRecyclerView() {
        homeAdapter = CommunityHomeAdapter(this, event)
        viewDataBinding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = homeAdapter
        }
    }

    // endregion


    // region OVERRIDDEN

    override fun onItemClick(position: Int, view: View) {
        // Todo: redirect to detail screen - Future use
    }

    // endregion

}
