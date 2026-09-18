package com.wablocker.app

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import android.widget.Toast

/**
 * Watches for the foreground app switching to WhatsApp. When blocking is
 * enabled, it immediately sends the user back to the home screen, so
 * WhatsApp never actually stays open. Toggling blocking off in the app
 * lets WhatsApp open normally again.
 */
class BlockerAccessibilityService : AccessibilityService() {

    private val whatsappPackages = setOf("com.whatsapp", "com.whatsapp.w4b")
    private var lastBlockToastAt = 0L

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event?.eventType != AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) return
        val packageName = event.packageName?.toString() ?: return

        if (packageName !in whatsappPackages) return
        if (!PrefsManager.isBlockingEnabled(applicationContext)) return

        performGlobalAction(GLOBAL_ACTION_HOME)

        val now = System.currentTimeMillis()
        if (now - lastBlockToastAt > 3000) {
            lastBlockToastAt = now
            Toast.makeText(
                applicationContext,
                getString(R.string.toast_whatsapp_blocked),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onInterrupt() {
        // No-op: nothing to clean up.
    }
}
