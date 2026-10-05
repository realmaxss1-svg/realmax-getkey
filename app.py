from flask import Flask, render_template, request, jsonify
import sqlite3, secrets, string
from datetime import datetime, timedelta, timezone

app = Flask(__name__)
DB = "keys.db"

def db():
    con = sqlite3.connect(DB)
    con.row_factory = sqlite3.Row
    return con

def init_db():
    con = db()
    con.execute("""CREATE TABLE IF NOT EXISTS keys (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        key TEXT UNIQUE NOT NULL,
        days INTEGER NOT NULL,
        created_at TEXT NOT NULL,
        expires_at TEXT NOT NULL,
        status TEXT NOT NULL DEFAULT 'active',
        used INTEGER NOT NULL DEFAULT 0
    )""")
    con.commit()
    con.close()

def new_key():
    chars = string.ascii_uppercase + string.digits
    parts = ["".join(secrets.choice(chars) for _ in range(4)) for _ in range(3)]
    return "REAL-" + "-".join(parts)

@app.get("/")
def home():
    return render_template("index.html")

@app.post("/api/getkey")
def getkey():
    days = 1
    key = new_key()
    now = datetime.now(timezone.utc)
    expires = now + timedelta(days=days)
    con = db()
    con.execute(
        "INSERT INTO keys(key,days,created_at,expires_at) VALUES(?,?,?,?,?)",
        (key, days, now.isoformat(), expires.isoformat())
    )
    con.commit()
    con.close()
    return jsonify(ok=True, key=key, expires_at=expires.isoformat())

@app.post("/api/verify")
def verify():
    data = request.get_json(silent=True) or {}
    key = str(data.get("key","")).strip().upper()
    con = db()
    row = con.execute("SELECT * FROM keys WHERE key=?", (key,)).fetchone()
    con.close()
    if not row:
        return jsonify(ok=False, message="ไม่พบ KEY"), 404

    expires = datetime.fromisoformat(row["expires_at"])
    if row["status"] != "active" or expires <= datetime.now(timezone.utc):
        return jsonify(ok=False, message="KEY หมดอายุหรือถูกปิดใช้งาน"), 403

    return jsonify(ok=True, message="KEY ใช้งานได้", expires_at=row["expires_at"])

@app.get("/api/stats")
def stats():
    con = db()
    total = con.execute("SELECT COUNT(*) c FROM keys").fetchone()["c"]
    active = con.execute("SELECT COUNT(*) c FROM keys WHERE status='active'").fetchone()["c"]
    con.close()
    return jsonify(total=total, active=active)

if __name__ == "__main__":
    init_db()
    app.run(host="0.0.0.0", port=5000, debug=False)
