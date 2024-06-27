package com.huiiro.ncn.component.splash

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Process
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import com.huiiro.ncn.R
import com.huiiro.ncn.base.consts.Constant
import com.huiiro.ncn.base.fragment.dialog.BaseCommonDialogFragment
import com.huiiro.ncn.core.helper.UpdateHelper
import com.huiiro.ncn.domain.CrowUpdateEntity
import com.huiiro.ncn.http.repository.CrowRepository
import kotlinx.coroutines.launch

class SplashUpdateDialogFragment : BaseCommonDialogFragment() {

    private lateinit var onClickUpdate: View.OnClickListener
    private lateinit var agreeView: TextView
    private lateinit var disagreeView: TextView
    private val updateHelper by lazy { UpdateHelper(requireContext().applicationContext) }
    private lateinit var updateEntity: CrowUpdateEntity

    companion object {
        const val TAG = "SplashUpdateDialogFragment"
        fun show(fragmentManager: FragmentManager, onClickAgreeEvent: View.OnClickListener) {
            val dialogFragment = SplashUpdateDialogFragment()
            dialogFragment.onClickUpdate = onClickAgreeEvent
            dialogFragment.show(fragmentManager, "SplashTermsServiceDialogFragment")
        }
    }

    override fun getLayoutView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.splash_fragment_update, container, false)
    }

    override fun initViewData() {
        super.initViewData()

        isCancelable = false

        agreeView = findViewById(R.id.splash_update_agree)
        disagreeView = findViewById(R.id.splash_update_disagree)

    }

    @SuppressLint("SetTextI18n")
    override fun initListener() {
        super.initListener()

        lifecycleScope.launch {
            val crowUpdateEntity = CrowRepository.checkUpdate()
            updateEntity = crowUpdateEntity.getData()!!

            //init data put here
            val updateDesc = findViewById<TextView>(R.id.splash_update_desc)
            updateDesc.text = updateEntity.desc

            val updateVersion = findViewById<TextView>(R.id.splash_update_version)
            updateVersion.text = "最新版本： ${updateEntity.name}"

        }

        agreeView.setOnClickListener {
            dismiss()
            Log.d(TAG, "initListener: Button Clicked!")
            if (Constant.VERSION_CODE < updateEntity.code!!) {
                Log.d(TAG, "initListener: prepare for update")
                updateHelper.downloadAndInstallApk(updateEntity.url!!)
            }
            onClickUpdate.onClick(it)
        }

        disagreeView.setOnClickListener {
            dismiss()
            if (updateEntity.force == true) {
                Log.d(TAG, "initListener: force update, user reject!")
                Process.killProcess(Process.myPid())
            } else {
                Log.d(TAG, "initListener: user ignore update")
                onClickUpdate.onClick(it)
            }
        }
    }
}