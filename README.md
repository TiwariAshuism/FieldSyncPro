# 🏭 Field Asset Inspection & Compliance Manager

**Offline-First SaaS for Field Technicians**
Built with **Kotlin, Jetpack Compose, Room, and Supabase** to enable field teams to manage work orders, inspections, and asset compliance — even in areas with poor or no internet connectivity.

---

## 🚀 Features

### Mobile App (Kotlin + Room)

* Download job orders, checklists, and asset data for offline access.
* Single source of truth in the field.
* Dynamic forms with nested data structures.
* Capture photos and videos locally, with placeholders before sync.

### Offline-First Sync

* **Last Write Wins (LWW)** strategy at the **field level**, avoiding data loss when multiple users update the same record offline.
* Syncs automatically when internet is available.
* Media uploads restricted to **Wi-Fi only** for efficiency.

### Real-Time Updates

* Updates are pushed instantly to the dispatch office dashboard via **Supabase Realtime**.
* Track job status in real-time (Pending, In Progress, Completed).

### Dynamic Forms & Checklists

* Nested and flexible checklists supporting multiple input types: dropdowns, checkboxes, text fields.
* Save progress locally with “Saved Locally — Not Synced” indicators.
* Complete checklist to mark tasks finished.

### Asset Management

* View detailed asset information including photos, QR code scanning, inspection history, and metadata.
* Add observations and upload images to assets directly from the field.

### Photo Capture & Upload

* Large camera preview with **Retake / Save** options.
* Local filename placeholders until synced.
* Wi-Fi-only toggle for media synchronization.

### Sync Manager

* Centralized sync management for forms, checklists, images, and notes.
* Retry failed uploads and resolve conflicts manually if needed.
* Monitor local changes and upload queue with a “Sync Now” button.

---

## 📦 Tech Stack

| Layer            | Technology                                     |
| ---------------- | ---------------------------------------------- |
| Mobile App       | Kotlin, Jetpack Compose                        |
| Local DB         | Room                                           |
| Networking / API | Supabase (Postgres, Realtime)                  |
| Storage          | Supabase Storage for media                     |
| Architecture     | Clean Architecture, Multimodule, Offline-First |
| DI               | Hilt (optional)                                |
| Navigation       | Jetpack Compose Navigation                     |

---

## 🗂 Folder Structure (High-Level)

```
app/
core/
  core-common/
  core-ui/
  core-database/
  core-network/
  core-storage/
  core-sync/
feature-dailyjobs/
feature-jobdetails/
feature-checklist/
feature-assetdetails/
feature-photoupload/
feature-syncmanager/
```

* **core**: shared modules for UI components, themes, database, network, storage, and sync logic.
* **feature-* modules**: self-contained units for each feature (UI, navigation, domain, data).

---

## ⚙ Architecture & Patterns

* **Offline-First**: All updates are cached locally and synced asynchronously.
* **Last Write Wins (LWW)**: Merge offline updates at the field level to prevent data loss.
* **Clean Architecture**: Separation of `data`, `domain`, and `ui`.
* **Feature Navigation**: Each feature exposes its own `NavGraph` using a `FeatureNavigation` contract to avoid circular dependencies.
* **Multimodule**: Isolated modules for scalability and maintainability.

---

## 🏗 Setup Instructions

1. **Clone the repository**

   ```bash
   git clone https://github.com/yourusername/field-asset-inspection.git
   cd field-asset-inspection
   ```

2. **Configure Supabase**

    * Create a project in [Supabase](https://supabase.com/).
    * Add your **API URL** and **anon/public key** in `local.properties` or environment variables.

3. **Run the App**

    * Open the project in **Android Studio Flamingo or higher**.
    * Build and run on an emulator or physical device (min SDK 24).

---

## 📌 Notes

* Media files are stored locally first and uploaded **only when Wi-Fi is available**.
* Offline edits to jobs, checklists, or assets are fully preserved until synced.
* Realtime updates to dispatch dashboard rely on Supabase Realtime subscriptions.

---

## 🌟 Contribution

Contributions are welcome! Please open issues or submit pull requests for improvements, bug fixes, or new features.

---

## 📄 License

This project is licensed under the **MIT License** — see the [LICENSE](LICENSE) file for details.

---

If you want, I can also generate a **visual diagram in the README** showing:

* Feature modules
* Offline-first data flow
* Supabase sync pipeline



----
