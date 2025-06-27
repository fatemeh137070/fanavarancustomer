# 🧩 Fanavaran Customer Management System

یک سامانه‌ی مدیریت مشتریان و خدمات پشتیبانی است که توسط شرکت‌ها برای مدیریت مشتریان، سرویس‌های اجاره‌ای، صدور فاکتور، پشتیبانی فنی و ثبت لاگ‌های سیستمی استفاده می‌شود.

---

## 📌 ویژگی‌های اصلی پروژه

- مدیریت **مشتریان** (حقیقی یا حقوقی)
- ثبت و مدیریت **سرویس‌های اجاره‌شده**
- صدور و پیگیری **فاکتورها** (خرید یا تمدید)
- مدیریت **تیکت‌ها** و پاسخگویی پشتیبان
- سیستم **نقش و کاربر** (Admin, Support, Customer)
- ثبت **لاگ فعالیت‌ها** برای نظارت

---

## 🧱 معماری پروژه (معماری لایه‌ای)

پروژه با معماری چند لایه‌ای (Multi-layered Architecture) طراحی شده و شامل لایه‌های زیر است:
📦 controller ← مدیریت API و endpointها
📦 facade ← واسط بین controller و service
📦 service ← منطق تجاری و پردازش اصلی
📦 dal (repository) ← ارتباط با دیتابیس (JPA)
📦 entity ← مدل‌های پایگاه داده (JPA Entity)
📦 dto ← انتقال داده بین لایه‌ها
📦 mapper ← تبدیل Entity ↔ DTO
📦 exception ← مدیریت خطاها
📦 config ← پیکربندی پروژه





---

## 🚀 پیشنهادات برای بهبود آینده پروژه

| حوزه              | پیشنهاد                                          |
|-------------------|--------------------------------------------------|
| 🔐 امنیت           | افزودن احراز هویت و سطح دسترسی با Spring Security و JWT |
| 📊 گزارش‌گیری      | اضافه کردن داشبورد آماری برای مدیران با داده‌های فیلتر شده |
| 🧩 معماری          | تبدیل به معماری میکروسرویس در نسخه آینده |
| 🧰 مانیتورینگ      | اضافه کردن Spring Actuator و ELK برای لاگ و سلامت سیستم |
| 🧾 مستندسازی       | اضافه کردن Swagger UI برای مشاهده و تست APIها |
| ⬆️ CI/CD           | پیاده‌سازی GitHub Actions یا Jenkins برای استقرار خودکار |
| 🔄 بهینه‌سازی Mapper | استفاده از MapStruct به‌جای ModelMapper برای افزایش سرعت |
| 💻 رابط کاربری     | ساخت پنل مدیریت با React/Vue برای Admin و Customer |

---

## ✅ پیش‌نیازهای اجرای پروژه

- Java 21
- Maven یا Gradle
- PostgreSQL / H2 / MySQL (قابل تنظیم)
- Spring Boot 3+
- IntelliJ IDEA یا Eclipse

---

## 🛠️ نحوه اجرا در IntelliJ

1. پروژه را Clone کن:
   ```bash
   git clone https://github.com/your-username/fanavaran-customer.git





🔗 روابط بین موجودیت‌ها (Entity Relationships)



Customer (1) ────────────▶ (*) CustomerService
Customer (1) ────────────▶ (*) Invoice
CustomerService (1) ─────▶ (*) Invoice
Customer (1) ────────────▶ (*) Ticket
Ticket (1) ──────────────▶ (*) TicketResponse
User (1) ────────────────▶ (*) TicketResponse
User (1) ────────────────▶ (*) ActivityLog
User (*) ────────────────◀▶ (*) Role
# Fanavaran Customer Management System

سیستم مدیریت مشتریان، سرویس‌ها، فاکتورها، تیکت‌ها، پاسخ‌ها و لاگ‌های فعالیت کاربران



## 🔗 روابط بین موجودیت‌ها (Entity Relationships)

| موجودیت | موجودیت مرتبط | نوع رابطه | توضیح |
|---------|----------------|-----------|--------|
| `Customer` → `CustomerService` | OneToMany | یک مشتری چند سرویس دارد |
| `Customer` → `Invoice` | OneToMany | یک مشتری چند فاکتور دارد |
| `CustomerService` → `Invoice` | OneToMany | یک سرویس چند فاکتور دارد |
| `Customer` → `Ticket` | OneToMany | یک مشتری چند تیکت دارد |
| `Ticket` → `TicketResponse` | OneToMany | یک تیکت چند پاسخ دارد |
| `User` → `TicketResponse` | OneToMany | یک کاربر چند پاسخ ثبت کرده |
| `User` → `ActivityLog` | OneToMany | یک کاربر چند لاگ دارد |
| `User` ↔ `Role` | ManyToMany | چند نقش برای هر کاربر و برعکس |


