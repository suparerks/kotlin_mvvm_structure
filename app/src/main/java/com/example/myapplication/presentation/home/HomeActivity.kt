package com.example.myapplication.presentation.home

import android.content.DialogInterface
import android.os.Bundle
import androidx.lifecycle.Observer
import com.example.myapplication.R
import com.example.myapplication.presentation.common.MasterActivity
import kotlinx.android.synthetic.main.activity_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : MasterActivity() {

    private val viewModel: HomeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setBackButtonVisible(false)
        initMaster()
        setTitle("Test")
        initDialog(this)

        initEvent()
        initViewModel()

        viewModel.loadUser()
    }

    private fun initEvent() {
        btnIncrease.setOnClickListener {
            viewModel.onBtnIncreaseClick()
        }
    }

    private fun initViewModel() {
        viewModel.shouldShowUser.observe(this, Observer { user ->

        })

        viewModel.shouldShowError.observe(this, Observer { messageError ->

        })

        viewModel.showCurrentNumber.observe(this, Observer { currentNumber ->
            txtNumber.text = currentNumber.toString()
            showDialogMsgSuccess(
                title = null,
                msg = currentNumber.toString(),
                btnText = "ok",
                listenerOk = DialogInterface.OnClickListener { dialog, _ ->
                    dialog.cancel()
                }
            )
        })
    }
}