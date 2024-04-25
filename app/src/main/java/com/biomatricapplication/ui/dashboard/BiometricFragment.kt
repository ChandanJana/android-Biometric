package com.biomatricapplication.ui.dashboard

import android.app.Activity
import android.content.Intent
import android.hardware.biometrics.BiometricManager.Authenticators.BIOMETRIC_STRONG
import android.hardware.biometrics.BiometricManager.Authenticators.BIOMETRIC_WEAK
import android.hardware.biometrics.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.biomatricapplication.R
import com.biomatricapplication.databinding.FragmentBiometricBinding


class BiometricFragment : Fragment() {

    private val TAG: String = BiometricManager::class.java.simpleName

    private var _binding: FragmentBiometricBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var biometricViewModel: BiometricViewModel
    private var isBiometricEnabled = false
    private var isBiometricPresent = false
    private val authenticators = BIOMETRIC_WEAK
    private lateinit var enrollBiometricRequestLauncher: ActivityResultLauncher<Intent>
    private lateinit var biometricPrompt: BiometricPrompt

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        biometricViewModel =
            ViewModelProvider(this).get(BiometricViewModel::class.java)

        // Initialize a launcher for requesting user to enroll in biometric
        enrollBiometricRequestLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == Activity.RESULT_OK) {
                    showBiometricPrompt()
                } else {
                    Log.d(TAG, "Failed to enroll in biometric")
                }
            }

        _binding = FragmentBiometricBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textDashboard
        biometricViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        checkIfBiometricIsSupported()
        if (isBiometricEnabled) {
            showBiometricPrompt()
        } else if (isBiometricPresent) {
            biometricViewModel.setText(getString(R.string.biometric_not_enabled));
        } else {
            biometricViewModel.setText(getString(R.string.biometric_not_present));
        }
        return root
    }

    private fun checkIfBiometricIsSupported() {
        val id: Int = BiometricManager.from(requireContext()).canAuthenticate()
        when (id) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                isBiometricEnabled = true
                isBiometricPresent = true
            }
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                Log.d(TAG, "No biometric features available on this device.")
                isBiometricEnabled = false
                isBiometricPresent = false
            }
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                Log.d(TAG, "Biometric features are currently unavailable.")
                isBiometricEnabled = false
                isBiometricPresent = false
            }
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                Log.d(TAG, "He user hasn't associated any biometric credentials with their account.")
                isBiometricEnabled = false
                isBiometricPresent = true
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    Log.d(TAG, "He user TRUE")
                    enrollBiometricRequestLauncher.launch(
                        Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
                            putExtra(
                                Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                                authenticators
                            )
                        }
                    )
                } else {
                    Log.d(TAG, "He user FALSE")
                    Log.d(TAG, "Could not request biometric enrollment in API level < 30")
                }
            }
        }
    }

    private fun showBiometricPrompt() {
        val executor = ContextCompat.getMainExecutor(requireContext())
        biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    Log.e(TAG, "onAuthenticationError")
                    biometricViewModel.setText(getString(R.string.failed_biometric))
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    Log.d(TAG, "onAuthenticationSucceeded")
                    biometricViewModel.setText(getString(R.string.successfully_validated_biometric))
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Log.e(TAG, "onAuthenticationFailed")
                    biometricViewModel.setText(getString(R.string.failed_biometric))
                }
            })
        val promptInfo: BiometricPrompt.PromptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Unlock App")
            .setSubtitle("This is is to validate using your biometric to check whether you are valid user to see the screen content")
            .setNegativeButtonText("Cancel")
            .build()
        biometricPrompt.authenticate(promptInfo)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}