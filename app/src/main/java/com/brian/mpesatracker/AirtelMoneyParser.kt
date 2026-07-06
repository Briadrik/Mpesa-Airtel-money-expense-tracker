package com.brian.mpesatracker

import java.util.regex.Pattern

/**
 * Parser for Airtel Money SMS. Airtel messages differ from M-PESA in format:
 *   - "TID:V3KGJ3ESVJ1. Received Ksh 200 from BRIAN KIBET 254719342725 on
 *      26/03/26 04:22 PM. Bal:Ksh 201.59 Sender TID:UCQ5YAU197."
 *   - "Q3KGJHCN9BU Confirmed. You have successfully purchased a bundle of
 *      Ksh 20 via Airtel Networks Kenya Ltd on 26/03/26 at 04:34 PM.
 *      Fee: Ksh 0. Bal: Ksh 181.59."
 *   - "G3KM5ZJGMKQ. Ksh 130 sent to Esther Kamara 113074184 on 30/03/26 at
 *      06:34 PM. Fee: Ksh 6. Bal: Ksh 5.59. Receiving TID: ..."
 *
 * Notably, Airtel amounts often have NO decimals ("Ksh 200" not "Ksh200.00"),
 * unlike M-PESA which always includes ".00".
 */
object AirtelMoneyParser {

    fun isAirtelMessage(sender: String, body: String): Boolean {
        if (sender.contains("airtel", ignoreCase = true)) return true
        if (body.contains("Airtel", ignoreCase = true)) return true
        if (body.trim().startsWith("TID:", ignoreCase = true)) return true
        if (Regex("Bal:\\s?[Kk][Ss][Hh]").containsMatchIn(body)) return true
        return false
    }

    // Airtel amounts may or may not have decimals: "Ksh 200" or "Ksh 181.59"
    private fun firstAmount(text: String): Double {
        val m = Pattern.compile("[Kk][Ss][Hh]\\s?([\\d,]+(?:\\.\\d{1,2})?)").matcher(text)
        return if (m.find()) m.group(1)!!.replace(",", "").toDouble() else 0.0
    }

    private fun extractLabeled(text: String, label: String): Double? {
        val m = Pattern.compile(
            "$label:?\\s?[Kk][Ss][Hh]\\s?([\\d,]+(?:\\.\\d{1,2})?)",
            Pattern.CASE_INSENSITIVE
        ).matcher(text)
        return if (m.find()) m.group(1)?.replace(",", "")?.toDoubleOrNull() else null
    }

    private fun extractTxnCode(msg: String): String? {
        val trimmed = msg.trim()
        // "TID:V3KGJ3ESVJ1. Received..." -> leading transaction id after TID:
        Regex("^TID:([A-Z0-9]+)", RegexOption.IGNORE_CASE).find(trimmed)?.let {
            return it.groupValues[1]
        }
        // "Q3KGJHCN9BU Confirmed..." or "G3KM5ZJGMKQ. Ksh130 sent to..."
        Regex("^([A-Z0-9]{8,12})[.\\s]").find(trimmed)?.let {
            return it.groupValues[1]
        }
        return null
    }

    fun parse(body: String): ParsedMpesa {
        val msg = body.trim()
        val txnCode = extractTxnCode(msg)
        val balance = extractLabeled(msg, "Bal")
        val cost = extractLabeled(msg, "Fee") ?: 0.0

        return when {
            // ── Bundle purchase ──────────────────────────────────────────────
            msg.contains("purchased a bundle", ignoreCase = true) -> ParsedMpesa(
                txnCode, firstAmount(msg), "Airtel Bundle",
                TxnDirection.EXPENSE, "DATA_BUNDLE", balance, cost, msg, provider = "AIRTEL"
            )

            // ── Send money ───────────────────────────────────────────────────
            msg.contains("sent to", ignoreCase = true) -> ParsedMpesa(
                txnCode, firstAmount(msg),
                MoneyParserUtils.extractName(msg, listOf("sent to")) ?: "Unknown",
                TxnDirection.EXPENSE, "SEND", balance, cost, msg, provider = "AIRTEL"
            )

            // ── Paid to till / merchant ──────────────────────────────────────
            msg.contains("paid to", ignoreCase = true) -> ParsedMpesa(
                txnCode, firstAmount(msg),
                MoneyParserUtils.extractName(msg, listOf("paid to")) ?: "Unknown",
                TxnDirection.EXPENSE, "BUY_GOODS", balance, cost, msg, provider = "AIRTEL"
            )

            // ── Withdraw ─────────────────────────────────────────────────────
            msg.contains("withdraw", ignoreCase = true) -> ParsedMpesa(
                txnCode, firstAmount(msg),
                MoneyParserUtils.extractName(msg, listOf("from")) ?: "Agent",
                TxnDirection.EXPENSE, "WITHDRAW", balance, cost, msg, provider = "AIRTEL"
            )

            // ── Receive money ────────────────────────────────────────────────
            msg.contains("received", ignoreCase = true) -> ParsedMpesa(
                txnCode, firstAmount(msg),
                MoneyParserUtils.extractName(msg, listOf("from")) ?: "Unknown",
                TxnDirection.INCOME, "RECEIVE", balance, cost, msg, provider = "AIRTEL"
            )

            // ── Fallback ─────────────────────────────────────────────────────
            else -> ParsedMpesa(
                txnCode, firstAmount(msg), "Unknown",
                TxnDirection.UNKNOWN, "OTHER", balance, cost, msg, provider = "AIRTEL"
            )
        }
    }
}
