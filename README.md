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

## 📦 تکنولوژی‌های استفاده‌شده

- Java 21
- Spring Boot
- Spring Data JPA (Hibernate)
- H2 Database (In-memory)
- Modelmapper
- Lombok
- REST API
- IntelliJ IDEA

---

## 📁 ساختار کلی پروژه

---

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

---

## 🔌 اجرا و تست

### 1. اجرای برنامه در IntelliJ

- پروژه را باز کن.
- مطمئن شو فایل `application.properties` به درستی تنظیم شده:
  ```properties
  spring.datasource.url=jdbc:h2:mem:librarydb
  spring.jpa.hibernate.ddl-auto=create-drop
  spring.h2.console.enabled=true
  server.port=8384


