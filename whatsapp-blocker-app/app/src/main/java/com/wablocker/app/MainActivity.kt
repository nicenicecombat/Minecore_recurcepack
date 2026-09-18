package com.wablocker.app

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import com.wablocker.app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonToggle.setOnClickListener {
            val newState = !PrefsManager.isBlockingEnabled(this)
            PrefsManager.setBlockingEnabled(this, newState)
            refreshUi()
        }

        binding.buttonEnableAccessibility.setOnClickListener {
            startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
        }

        binding.buttonEnableUsageAccess.setOnClickListener {
            startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
        }
    }

    override fun onResume() {
        super.onResume()
        refreshUi()
    }

    private fun refreshUi() {
        val blockingEnabled = PrefsManager.isBlockingEnabled(this)
        val accessibilityEnabled = isAccessibilityServiceEnabled()
        val usageAccessGranted = UsageStatsHelper.hasUsageAccess(this)

        // Toggle button: red "AKTIVIEREN" when off, green "DEAKTIVIEREN" when on.
        if (blockingEnabled) {
            binding.buttonToggle.text = getString(R.string.button_deactivate)
            binding.buttonToggle.setBackgroundColor(getColor(R.color.blocker_green))
            binding.textStatus.text = getString(R.string.status_active)
        } else {
            binding.buttonToggle.text = getString(R.string.button_activate)
            binding.buttonToggle.setBackgroundColor(getColor(R.color.blocker_red))
            binding.textStatus.text = getString(R.string.status_inactive)
        }

        // The toggle only works if the accessibility service is actually on.
        binding.buttonToggle.isEnabled = accessibilityEnabled

        binding.cardAccessibilityWarning.visibility =
            if (accessibilityEnabled) android.view.View.GONE else android.view.View.VISIBLE

        binding.cardUsageWarning.visibility =
            if (usageAccessGranted) android.view.View.GONE else android.view.View.VISIBLE

        if (usageAccessGranted) {
            val millis = UsageStatsHelper.getTodayWhatsAppUsageMillis(this)
            binding.textUsageTime.text = UsageStatsHelper.formatDuration(millis)
        } else {
            binding.textUsageTime.text = getString(R.string.usage_unknown)
        }
    }

    private fun isAccessibilityServiceEnabled(): Boolean {
        val expectedComponent = "$packageName/${BlockerAccessibilityService::class.java.name}"
        val enabledServices = Settings.Secure.getString(
            contentResolver, Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
        ) ?: return false

        val splitter = TextUtils.SimpleStringSplitter(':')
        splitter.setString(enabledServices)
        for (component in splitter) {
            if (component.equals(expectedComponent, ignoreCase = true)) return true
        }
        return false
    }
}
