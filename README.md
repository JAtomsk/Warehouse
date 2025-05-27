# 📦 JJWarehouse – Android Inventory Management App

> ⚠️ **Work In Progress**  
> This is a previously developed project that I’ve revisited to enhance across three core areas:
> **Software Design & Engineering**, **Algorithms & Data Structures**, and **Databases**.

---

## 🔧 Project Overview

JJWarehouse is an Android inventory management application built in **Java** using Android Studio. Initially created as a lightweight inventory tracker, it has evolved to include:

- Secure user authentication
- Real-time search functionality
- Scalable backend with MySQL and RESTful API

---

## ✨ Areas of Enhancement

---

## 🧱 1. Software Design & Engineering

The app uses fragments, a single-activity architecture, and native Android UI components. Emphasis was placed on intuitive navigation and clean design for mobile usability.

### ✅ Key Enhancements:
- **UI Theme Update** – Applied a modern color scheme for visual clarity and appeal
- **Custom Buttons from Figma** – Touch-optimized for CRUD operations
- **User Account Creation** – UI + logic to create usernames and passwords
- **Login with Feedback** – Added success/failure messages
- **Responsive UI** – Layouts adjust for different device sizes
- **Quick Quantity Adjustments** – Increment/decrement buttons
- **Improved Documentation** – Inline comments and organized structure
- **Secure Local Auth** – SHA-256 hashing, prepared SQL statements
- **Error Logging** – Tracked login issues for debugging

### 🎨 UI Details:
- Custom logo and color theme aligned with app branding
- Touch-friendly scaling of buttons and text views
- Consistent UI behavior across devices

> *“This process reshaped the entire application, transforming it into a secure, intuitive, and modern mobile experience.”*

---

## 🧮 2. Algorithms & Data Structures

Data structure use was critical to managing inventory state, user actions, and search functionality.

### 📂 Data Structures Used:
- **`ArrayList`** – To manage dynamic inventory data
- **`Bundles`** – For fragment-to-fragment communication
- **`Item` Objects** – Custom class with `id`, `name`, and `quantity`
- **`RecyclerView`** – Efficient list rendering and UI responsiveness

### 🔍 Algorithms & Features:
- **Search Functionality**  
  - SQL `LIKE` queries for partial string matching  
  - `getItemsFilteredBy()` in `DatabaseHelper`  
  - Real-time filtering via `updateData()` in `InventoryAdapter`

- **Performance Optimizations**  
  - Indexed queries  
  - Reduced time complexity in data retrieval  
  - Immediate UI feedback during user input

> _"This project challenged me to dive into real-time search logic, dynamic UI updates, and adapter lifecycle management—all in a language I was still mastering."_  

---

## 🗄️ 3. Databases & API Integration

Originally built on **SQLite**, the project has now been migrated to **MySQL** hosted on **AWS**, with a complete backend built in **Node.js**.

### 🔁 Transition Highlights:
- **SQLite ➝ MySQL (AWS RDS)**
- **Backend API** with Node.js + Express
- **JWT Authentication** – Secure session handling
- **`bcrypt` Password Hashing**
- **Redesigned Schema:** `Users`, `Items`, etc.
- **Role-Based Access Control (in progress)**

### 📡 API & Networking:
- **Retrofit Integration**  
  - `ApiInterface`, `RetrofitClient` for structured API access  
  - `LoginResponse`, `ItemResponse` for handling server responses  
- **SharedPreferences**  
  - JWT and user role storage for session management

### 📉 Optimization & Security:
- HTTPS API requests  
- Token-based auth with JWT  
- Indexed queries for fast search  
- Modular REST endpoints

> *“Shifting to a server-based architecture gave me a hands-on understanding of full-stack development, real-world authentication, and secure data handling.”*

---

## 🧠 Reflections

> _“Much of this implementation was built not from prior expertise but through consistent learning, applying references like Daniele Liang’s *Java Data Structures and Algorithms* and ZyBooks Mobile Architecture materials.”_

### 🧩 Challenges Faced:
- Learning Java while actively building the system
- Connecting new search features to the existing `RecyclerView`
- Dynamic data binding and adapter refresh handling
- UI consistency while integrating new backend features

---

## 🔜 What's Next

- Expand test inventory to stress-test search and UI performance
- Finalize **Role-Based Access Control**
- Integrate SMS or Firebase notifications
- Polish admin feature set and permissions

---

## 🏁 Conclusion

This enhancement journey reflects growth not just in technical skill, but in thinking like a full-stack developer:

- **Frontend:** Secure, scalable, intuitive UI
- **Backend:** RESTful APIs, secure auth, structured DB
- **Middle Layer:** Efficient algorithms & real-time feedback

> *"JJWarehouse is now more than a school project—it's a robust mobile solution built with modern, secure, and scalable practices."*

---
