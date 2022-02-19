package com.sample.clean.ui.home.adapter

import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.sample.clean.R
import com.sample.clean.base.BaseViewHolder
import com.sample.clean.databinding.ItemCommunityHomeBinding
import com.sample.clean.servermodel.ResponseItem
import android.text.Html
import com.sample.clean.ui.event.ClickEvent

/**
 * View holder class bind the date to the view
 */
class CommunityHomeViewHolder(
    private val recyclerBinding: ItemCommunityHomeBinding,
    private var event: (ClickEvent) -> Unit
) : BaseViewHolder<ResponseItem>(recyclerBinding.root) {

    override fun bindView(item: ResponseItem) {

        // User Image
        recyclerBinding.apply {
            Glide.with(userImage.context)
                .load(item.pictureUrl)
                .error(R.drawable.user_image_place_holder)
                .placeholder(R.drawable.user_image_place_holder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(userImage)

            // User name
            userName.text = item.firstName

            // New User
            userNew.visibility =
                if (item.referenceCnt == 1) View.VISIBLE else View.GONE

            // User topic
            userTopic.text = item.topic

            // User lang
            val allLang = getLanguages(item.natives as ArrayList<String>)
            val nativeLangs =
                "<b>" + userNative.context.getString(R.string.natives) + "</b> " + allLang
            userNative.text = Html.fromHtml(nativeLangs)

            // User Learn
            val learn = getLanguages(item.learns as ArrayList<String>)
            val learnLangs =
                "<b>" + userLearn.context.getString(R.string.natives) + "</b> " + learn
            userLearn.text = Html.fromHtml(learnLangs)

            android.util.Log.d("mine","binding view, like=${item.like} & pos=$adapterPosition")
            if (item.like)
                userLike.setImageResource(R.drawable.like_press)
            else
                userLike.setImageResource(R.drawable.like)
            userLike.setOnClickListener {
                event.invoke(ClickEvent.LikeClicked(item.like.not(), adapterPosition))
            }
        }

    }

    private fun getLanguages(list: ArrayList<String>): String {
        var allLang = ""
        for (lan in list) {
            allLang = "$allLang $lan"
        }
        return allLang
    }
}