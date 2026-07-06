# M-Pesa Tracker v2

Android app that auto-reads incoming M-Pesa SMS messages, parses them, and lets you categorize every shilling you spend.

## What's new in v2

### Parser upgrades
- **Fuliza** — detects `FULIZA_USE` vs `FULIZA_REPAY` separately; shows a "Fuliza used this period" line on the dashboard
- **Reversals** — tagged as `REVERSAL`, never counted as an expense
- **Data bundles** — detected via "data bundle"/"data pack" keywords, tagged as `DATA_BUNDLE` with its own emoji in History
- **Paybills** — distinguished from send-money via "for account" keyword, tagged `PAYBILL`
- **All transaction types**: `SEND`, `PAYBILL`, `BUY_GOODS`, `WITHDRAW`, `AIRTIME`, `DATA_BUNDLE`, `RECEIVE`, `FULIZA_USE`, `FULIZA_REPAY`, `REVERSAL`, `OTHER`

### Dashboard
- **Week / Month / Year toggle** — all figures (spend, income, fees, Fuliza) recalculate instantly
- **Total fees paid** — always surfaced; the `cost` field was in the DB since day one, now it's visible
- **Total income** — shown in green next to spend
- **Fuliza row** — only appears when Fuliza was used in the selected period
- **Uncategorized badge** — warns you how many expenses still need tagging

### History tab
- Each transaction shows: counterparty + type tag, amount (green/red), category or "Tag +" button, fee if non-zero, timestamp
- Tap "Tag +" on any uncategorized transaction to categorize inline without a notification

### Categories (new DB table, Manage tab)
- 13 seeded defaults: Fare, Groceries, Food, Toiletries, **Weed**, **Alcohol**, Bills, Transport, Savings, Business, Family/Personal, Airtime/Data, Other
- Add, rename, or delete categories from the Manage tab
- **Auto-suggest**: when JOHN DOE shows up again, the last category you used for JOHN DOE is pre-highlighted in blue on the categorize sheet

### Bottom navigation
Three tabs: **Dashboard** · **History** · **Categories**

## Database
Room v2 — migration from v1 adds `txnType` column and `categories` table. Fresh installs seed categories automatically.

## Build
```
./gradlew assembleDebug
```
Output: `app/build/outputs/apk/debug/app-debug.apk`

## Permissions required
- `RECEIVE_SMS` — to intercept M-Pesa messages live
- `READ_SMS` — for backfilling historical messages (future)
- `POST_NOTIFICATIONS` — for the categorize notification (Android 13+)
