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


