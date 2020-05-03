package com.silverstar.sampleapp.ui.detail

import android.os.Bundle
import android.view.MenuItem
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.silverstar.sampleapp.R
import com.silverstar.sampleapp.data.entity.Item
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class DetailActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        viewModel = ViewModelProvider(this, viewModelFactory).get(DetailViewModel::class.java)

        val strItem = intent.getStringExtra(EXTRA_ITEM)

        if (strItem == null) {
            Toast.makeText(this, R.string.unknown_access, Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val item = Gson().fromJson(strItem, Item::class.java)

        initViews(item)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initViews(item: Item) {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        findViewById<TextView>(R.id.tv_name).text = item.name
        findViewById<TextView>(R.id.tv_rate).text = getString(R.string.rate_fmt, item.rate)
        findViewById<TextView>(R.id.tv_price).text = getString(R.string.price_fmt, item.price)
        findViewById<TextView>(R.id.tv_subject).text = item.subject
        val cbLiked = findViewById<CheckBox>(R.id.cb_liked)
        cbLiked.isChecked = item.liked
        Glide.with(this).load(item.imageUrl).into(findViewById(R.id.iv_place))

        cbLiked.setOnClickListener {
            viewModel.updateLikedState(
                item.copy(liked = !cbLiked.isChecked)
            )
        }
    }

    companion object {
        const val EXTRA_ITEM = "com.silverstar.sampleapp.EXTRA_ITEM"
    }
}