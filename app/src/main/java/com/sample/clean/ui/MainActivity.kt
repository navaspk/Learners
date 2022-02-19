package com.sample.clean.ui

import com.sample.clean.databinding.ActivityMainBinding
import com.sample.clean.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate)
