# REALMAX GETKEY — Online Demo

ระบบ GetKey แบบมี Backend จริงด้วย Flask + SQLite

## รันบนคอม/เซิร์ฟเวอร์
```bash
pip install -r requirements.txt
python app.py
```
จากนั้นเปิด:
http://127.0.0.1:5000

## API
POST /api/getkey
- สร้าง KEY อายุ 1 วัน

POST /api/verify
JSON:
{"key":"REAL-XXXX-XXXX-XXXX"}

GET /api/stats
- ดูจำนวน KEY ทั้งหมด/ที่ยัง active

## หมายเหตุ
นี่เป็นระบบตัวอย่างสำหรับโปรเจกต์ของคุณเอง ฐานข้อมูลจะถูกสร้างเป็น keys.db อัตโนมัติ
ก่อนนำขึ้นอินเทอร์เน็ตจริงควรเพิ่ม Admin Login, rate limit, HTTPS และ secret/authentication สำหรับ API
