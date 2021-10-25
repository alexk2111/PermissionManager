package com.sigmasoftware.akucherenko.permissionmanager

import android.Manifest
import android.content.pm.PackageManager.PERMISSION_GRANTED
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.sigmasoftware.akucherenko.permissionmanager.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var allPermission: Array<String> = arrayOf()
    private val requestPermissionContract = ActivityResultContracts.RequestPermission()

    private val requestAnswerPhoneCalls =
        registerForActivityResult(requestPermissionContract) { isGranted: Boolean ->
            if (isGranted) {
                binding.switchAnswerPhoneCalls.isChecked = true
            } else {
                if (shouldShowRequestPermissionRationale(Manifest.permission.ANSWER_PHONE_CALLS)) {
                    showSnackbar(getString(R.string.rationaleAnswerPhoneCalls))
                }
                binding.switchAnswerPhoneCalls.isChecked = false
            }
        }

    private val requestReadCallLog =
        registerForActivityResult(requestPermissionContract) { isGranted: Boolean ->
            if (isGranted) {
                binding.switchReadCallLog.isChecked = true
            } else {
                if (shouldShowRequestPermissionRationale(Manifest.permission.READ_CALL_LOG)) {
                    showSnackbar(getString(R.string.rationaleReadCallLog))
                }
                binding.switchReadCallLog.isChecked = false
            }
        }

    private val requestReadContacts =
        registerForActivityResult(requestPermissionContract) { isGranted: Boolean ->
            if (isGranted) {
                binding.switchReadContacts.isChecked = true
            } else {
                if (shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS)) {
                    showSnackbar(getString(R.string.rationaleReadContacts))
                }
                binding.switchReadContacts.isChecked = false
            }
        }

    private val requestReadSms =
        registerForActivityResult(requestPermissionContract) { isGranted: Boolean ->
            if (isGranted) {
                binding.switchReadSms.isChecked = true
            } else {
                if (shouldShowRequestPermissionRationale(Manifest.permission.READ_SMS)) {
                    showSnackbar(getString(R.string.rationaleReadSms))
                }
                binding.switchReadSms.isChecked = false
            }
        }

    private val requestMultiplePermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { resultsMap ->
            resultsMap.forEach {
                if (it.value) return@forEach
                if (shouldShowRequestPermissionRationale(it.key)) {
                    var text = getString(R.string.rationaleMultiPermission)
//                    when (it.key) {
//                        Manifest.permission.ANSWER_PHONE_CALLS -> text =
//                            getString(R.string.rationaleAnswerPhoneCalls)
//                        Manifest.permission.READ_CALL_LOG -> text =
//                            getString(R.string.rationaleReadCallLog)
//                        Manifest.permission.READ_CONTACTS -> text =
//                            getString(R.string.rationaleReadContacts)
//                        Manifest.permission.READ_SMS
//                        -> text = getString(R.string.rationaleReadSms)
//                    }
                    showSnackbar(text)
                }
            }
            checkPermissions()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(this.binding.root)

        checkPermissions()

        binding.switchAnswerPhoneCalls.setOnClickListener() {
            requestAnswerPhoneCalls.launch(Manifest.permission.ANSWER_PHONE_CALLS)
        }

        binding.switchReadCallLog.setOnClickListener() {
            requestReadCallLog.launch(Manifest.permission.READ_CALL_LOG)
        }

        binding.switchReadContacts.setOnClickListener() {
            requestReadContacts.launch(Manifest.permission.READ_CONTACTS)
        }
        binding.switchReadSms.setOnClickListener() {
            requestReadSms.launch(Manifest.permission.READ_SMS)
        }

        binding.grantAll.setOnClickListener() {
            0
            allPermission =
                arrayOf(
                    Manifest.permission.ANSWER_PHONE_CALLS,
                    Manifest.permission.READ_CALL_LOG,
                    Manifest.permission.READ_CONTACTS,
                    Manifest.permission.READ_SMS
                )
            requestMultiplePermissionLauncher.launch(allPermission)

        }
    }

    private fun checkPermission(permission: String) = ContextCompat.checkSelfPermission(
        this,
        permission
    ) == PERMISSION_GRANTED

    private fun showSnackbar(text: String) {
        Snackbar.make(binding.root, text, Snackbar.LENGTH_INDEFINITE)
            .setAction("Ok") {
            }
            .show()

    }

    private fun checkPermissions(){
        binding.switchInternet.isChecked = checkPermission(Manifest.permission.INTERNET)
        binding.switchWifiState.isChecked = checkPermission(Manifest.permission.ACCESS_WIFI_STATE)
        binding.switchAnswerPhoneCalls.isChecked =
            checkPermission(Manifest.permission.ANSWER_PHONE_CALLS)
        binding.switchReadCallLog.isChecked = checkPermission(Manifest.permission.READ_CALL_LOG)
        binding.switchReadContacts.isChecked = checkPermission(Manifest.permission.READ_CONTACTS)
        binding.switchReadSms.isChecked = checkPermission(Manifest.permission.READ_SMS)
    }

//    private fun requestPermission(permission: String, callback: (Boolean) -> Unit) {
//        val requestPermissionContract = ActivityResultContracts.RequestPermission()
//        val request = registerForActivityResult(requestPermissionContract, callback)
//
//        request.launch(permission)
//    }
}