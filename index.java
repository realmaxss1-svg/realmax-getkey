<!DOCTYPE html>
<html lang="th">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>REAL MAX — ระบบสร้างคีย์</title>
    <style>
        * {
            margin: 0; padding: 0; box-sizing: border-box;
            font-family: 'Segoe UI', 'Noto Sans Thai', sans-serif;
        }
        :root {
            --gold: #ffd700;
            --gold-dark: #e6b800;
            --neon-gold: #ffef00;
            --blue: #00ccff;
            --red: #ff4466;
            --green: #00ff88;
            --dark: #0f1729;
            --darker: #0a0f1f;
            --card: rgba(25, 35, 60, 0.85);
        }
        body {
            min-height: 100vh;
            background: linear-gradient(135deg, var(--darker) 0%, var(--dark) 50%, var(--darker) 100%);
            color: white;
            padding: 2rem 1rem;
        }
        .container {
            max-width: 900px;
            margin: 0 auto;
        }

        /* === สติ๊กเกอร์/โลโก้ === */
        .logo-wrapper {
            position: relative;
            display: inline-block;
            margin-bottom: 0.5rem;
        }
        .sticker-main {
            display: flex; align-items: center; justify-content: center;
            gap: 0.8rem; padding: 1rem 2rem;
            background: linear-gradient(135deg, rgba(255, 215, 0, 0.15), rgba(255, 180, 0, 0.05));
            border: 3px solid var(--gold);
            border-radius: 20px;
            box-shadow: 
                0 0 15px rgba(255, 215, 0, 0.4),
                inset 0 0 20px rgba(255, 215, 0, 0.1);
            animation: stickerPulse 2s ease-in-out infinite;
        }
        @keyframes stickerPulse {
            0%, 100% { box-shadow: 0 0 15px rgba(255, 215, 0, 0.4), inset 0 0 20px rgba(255, 215, 0, 0.1); }
            50% { box-shadow: 0 0 25px rgba(255, 215, 0, 0.6), inset 0 0 30px rgba(255, 215, 0, 0.2); }
        }
        .sticker-icon {
            font-size: 2.5rem;
            filter: drop-shadow(0 0 8px var(--neon-gold));
        }
        .logo-text {
            font-size: 2.4rem; font-weight: 900; letter-spacing: 2px;
            background: linear-gradient(90deg, var(--gold) 0%, #fff200 50%, var(--gold) 100%);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
            background-clip: text;
            text-shadow: 0 0 30px rgba(255, 215, 0, 0.5);
        }
        .logo-badge {
            position: absolute; top: -8px; right: -8px;
            background: linear-gradient(90deg, var(--red), #ff2244);
            color: white; font-size: 0.7rem; font-weight: bold;
            padding: 0.25rem 0.6rem; border-radius: 20px;
            border: 2px solid white;
            box-shadow: 0 2px 8px rgba(0,0,0,0.3);
        }
        .subtitle { color: #aaa; font-size: 1rem; margin-top: 0.8rem; }

        /* === การ์ดและแท็บ === */
        .card {
            background: var(--card);
            border-radius: 16px;
            padding: 2rem;
            margin-bottom: 1.5rem;
            border: 1px solid rgba(255, 255, 255, 0.1);
            backdrop-filter: blur(10px);
            box-shadow: 0 8px 32px rgba(0, 0, 0, 0.3);
            transition: transform 0.3s, border-color 0.3s;
        }
        .card:hover {
            border-color: rgba(255, 215, 0, 0.3);
            transform: translateY(-2px);
        }
        .card-title {
            font-size: 1.3rem;
            margin-bottom: 1.2rem;
            display: flex;
            align-items: center;
            gap: 0.5rem;
        }
        .card-title span { font-size: 1.5rem; }

        .form-group { margin-bottom: 1rem; }
        label {
            display: block; margin-bottom: 0.5rem; color: #ccc; font-weight: 500;
        }
        input, button {
            width: 100%; padding: 0.9rem 1rem; border-radius: 8px;
            border: 2px solid transparent; font-size: 1rem;
            transition: 0.3s;
        }
        input {
            background: rgba(255, 255, 255, 0.08);
            color: white;
            border-color: rgba(255, 255, 255, 0.15);
        }
        input:focus {
            outline: none; border-color: var(--gold);
            box-shadow: 0 0 0 3px rgba(255, 215, 0, 0.2);
        }

        .btn {
            font-weight: bold; cursor: pointer; border: none;
        }
        .btn-primary {
            background: linear-gradient(90deg, var(--gold), var(--gold-dark));
            color: #000;
        }
        .btn-primary:hover { transform: translateY(-2px); box-shadow: 0 5px 20px rgba(255, 215, 0, 0.4); }
        .btn-blue {
            background: linear-gradient(90deg, var(--blue), #0066ff);
            color: white;
        }
        .btn-blue:hover { box-shadow: 0 5px 20px rgba(0, 204, 255, 0.4); }
        .btn-red {
            background: linear-gradient(90deg, var(--red), #ff2244);
            color: white;
        }
        .btn-red:hover { box-shadow: 0 5px 20px rgba(255, 68, 102, 0.4); }
        .btn-group { display: flex; gap: 0.8rem; flex-wrap: wrap; }
        .btn-group .btn { flex: 1; min-width: 140px; }

        .result {
            margin-top: 1rem; padding: 1rem; border-radius: 8px;
            display: none; animation: fadeIn 0.3s;
        }
        .result.show { display: block; }
        .result.success { background: rgba(0, 255, 136, 0.1); border: 1px solid var(--green); }
        .result.error { background: rgba(255, 68, 102, 0.1); border: 1px solid var(--red); }
        .result.info { background: rgba(0, 204, 255, 0.1); border: 1px solid var(--blue); }
        @keyframes fadeIn { from { opacity: 0; transform: translateY(10px); } to { opacity: 1; transform: translateY(0); } }

        .key-display {
            font-family: monospace; font-size: 1.3rem; font-weight: bold;
            background: rgba(0, 0, 0, 0.4); padding: 1rem; border-radius: 8px;
            text-align: center; margin: 0.8rem 0;
            color: var(--gold); letter-spacing: 2px;
            word-break: break-all;
            border: 1px solid rgba(255, 215, 0, 0.3);
        }
        .copy-btn {
            background: rgba(255, 255, 255, 0.1); color: white; padding: 0.5rem 1rem;
            border: none; border-radius: 6px; cursor: pointer; font-size: 0.9rem;
            margin-top: 0.5rem;
        }
        .copy-btn:hover { background: rgba(255, 255, 255, 0.2); }

        table {
            width: 100%; border-collapse: collapse; margin-top: 1rem;
        }
        th, td {
            padding: 0.8rem; text-align: left; border-bottom: 1px solid rgba(255, 255, 255, 0.1);
            font-size: 0.9rem;
        }
        th { color: var(--gold); font-weight: 600; }
        .status-active { color: var(--green); }
        .status-revoked { color: var(--red); }

        .loading { display: none; text-align: center; padding: 1rem; }
        .loading.show { display: block; }
        .spinner {
            display: inline-block; width: 30px; height: 30px;
            border: 3px solid rgba(255, 255, 255, 0.2);
            border-top-color: var(--gold); border-radius: 50%;
            animation: spin 1s linear infinite;
        }
        @keyframes spin { to { transform: rotate(360deg); } }

        .tabs { display: flex; margin-bottom: 1.5rem; border-bottom: 1px solid rgba(255, 255, 255, 0.1); flex-wrap: wrap; }
        .tab {
            padding: 0.8rem 1.2rem; cursor: pointer; border-bottom: 3px solid transparent;
            opacity: 0.7; transition: 0.2s;
        }
        .tab:hover, .tab.active { opacity: 1; border-bottom-color: var(--gold); }
        .tab-content { display: none; }
        .tab-content.active { display: block; animation: fadeIn 0.3s; }

        /* === สติ๊กเกอร์ตกแต่งมุม === */
        .corner-badge {
            position: fixed; bottom: 20px; right: 20px;
            background: linear-gradient(135deg, rgba(255, 215, 0, 0.2), rgba(0, 204, 255, 0.2));
            border: 2px solid var(--gold);
            border-radius: 12px;
            padding: 0.7rem 1rem;
            font-size: 0.85rem;
            box-shadow: 0 4px 15px rgba(0,0,0,0.3);
            z-index: 10;
        }
        .corner-badge span { color: var(--gold); font-weight: bold; }
    </style>
</head>
<body>
    <div class="container">
        <header style="text-align: center; margin-bottom: 2.5rem;">
            <!-- 🎯 สติ๊กเกอร์โลโก้หลัก -->
            <div class="logo-wrapper">
                <div class="sticker-main">
                    <span class="sticker-icon">🔥</span>
                    <h1 class="logo-text">REAL MAX</h1>
                    <span class="sticker-icon">🎮</span>
                </div>
                <span class="logo-badge">v2.0</span>
            </div>
            <p class="subtitle">ระบบสร้างและจัดการคีย์เข้าใช้งาน — แพลตฟอร์มเกมครบวงจร</p>
        </header>

        <!-- แถบเมนูแท็บ -->
        <div class="tabs">
            <div class="tab active" data-tab="generate">🔑 สร้างคีย์</div>
            <div class="tab" data-tab="validate">✅ ตรวจสอบ</div>
            <div class="tab" data-tab="list">📜 รายการทั้งหมด</div>
            <div class="tab" data-tab="revoke">❌ ยกเลิกคีย์</div>
        </div>

        <!-- สร้างคีย์ -->
        <div class="tab-content active" id="tab-generate">
            <div class="card">
                <h2 class="card-title"><span>🔑</span> สร้างคีย์ใหม่</h2>
                <div class="form-group">
                    <label for="gen-user">ชื่อผู้ใช้ *</label>
                    <input type="text" id="gen-user" placeholder="กรอกชื่อหรือรหัสผู้ใช้" required>
                </div>
                <div class="form-group">
                    <label for="gen-email">อีเมล (ไม่จำเป็น)</label>
                    <input type="email" id="gen-email" placeholder="example@email.com">
                </div>
                <button class="btn btn-primary" onclick="generateKey()">🚀 สร้างคีย์</button>
                
                <div class="loading" id="gen-loading"><div class="spinner"></div><p style="margin-top:0.5rem;">กำลังสร้าง...</p></div>
                <div class="result" id="gen-result"></div>
            </div>
        </div>

        <!-- ตรวจสอบคีย์ -->
        <div class="tab-content" id="tab-validate">
            <div class="card">
                <h2 class="card-title"><span>✅</span> ตรวจสอบความถูกต้อง</h2>
                <div class="form-group">
                    <label for="val-key">กรอกรหัสคีย์</label>
                    <input type="text" id="val-key" placeholder="RMAX-XXXX-XXXX-XXXX">
                </div>
                <button class="btn btn-blue" onclick="validateKey()">🔍 ตรวจสอบ</button>
                
                <div class="loading" id="val-loading"><div class="spinner"></div><p style="margin-top:0.5rem;">กำลังตรวจสอบ...</p></div>
                <div class="result" id="val-result"></div>
            </div>
        </div>

        <!-- รายการทั้งหมด -->
        <div class="tab-content" id="tab-list">
            <div class="card">
                <h2 class="card-title"><span>📜</span> รายการคีย์ทั้งหมด</h2>
                <div class="btn-group" style="margin-bottom: 1rem;">
                    <button class="btn btn-blue" onclick="loadAllKeys()">โหลดรายการ</button>
                    <input type="text" id="search-user" placeholder="ค้นหาตามชื่อ..." style="flex: 2;">
                    <button class="btn btn-primary" onclick="searchByUser()">ค้นหา</button>
                </div>
                
                <div class="loading" id="list-loading"><div class="spinner"></div><p style="margin-top:0.5rem;">กำลังโหลด...</p></div>
                <div id="list-container"></div>
            </div>
        </div>

        <!-- ยกเลิกคีย์ -->
        <div class="tab-content" id="tab-revoke">
            <div class="card">
                <h2 class="card-title"><span>❌</span> ยกเลิก/ระงับคีย์</h2>
                <div class="form-group">
                    <label for="rev-key">คีย์ที่ต้องการยกเลิก</label>
                    <input type="text" id="rev-key" placeholder="RMAX-XXXX-XXXX-XXXX">
                </div>
                <button class="btn btn-red" onclick="revokeKey()">⚠️ ยกเลิกคีย์</button>
                
                <div class="loading" id="rev-loading"><div class="spinner"></div><p style="margin-top:0.5rem;">กำลังดำเนินการ...</p></div>
                <div class="result" id="rev-result"></div>
            </div>
        </div>
    </div>

    <!-- สติ๊กเกอร์มุมล่างขวา -->
    <div class="corner-badge">
        <span>REAL MAX</span> SYSTEM<br>
        <small>© 2026 — All Rights Reserved</small>
    </div>

<script>
// === การตั้งค่า API ===
const API_BASE = "http://localhost:8080/api/key";

// === สลับแท็บ ===
document.querySelectorAll('.tab').forEach(tab => {
    tab.addEventListener('click', () => {
        document.querySelectorAll('.tab').forEach(t => t.classList.remove('active'));
        document.querySelectorAll('.tab-content').forEach(c => c.classList.remove('active'));
        tab.classList.add('active');
        document.getElementById('tab-' + tab.dataset.tab).classList.add('active');
    });
});

// === ฟังก์ชันช่วย ===
function showLoading(id) { document.getElementById(id).classList.add('show'); }
function hideLoading(id) { document.getElementById(id).classList.remove('show'); }
function showResult(elId, type, html) {
    const el = document.getElementById(elId);
    el.className = 'result ' + type + ' show';
    el.innerHTML = html;
}
function hideResult(elId) { document.getElementById(elId).classList.remove('show'); }

// === 1. สร้างคีย์ ===
async function generateKey() {
    const username = document.getElementById('gen-user').value.trim();
    const email = document.getElementById('gen-email').value.trim();
    
    if (!username) {
        showResult('gen-result', 'error', '⚠️ กรุณากรอกชื่อผู้ใช้');
        return;
    }
    
    hideResult('gen-result');
    showLoading('gen-loading');
    
    try {
        const res = await fetch(`${API_BASE}/generate`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ username, email })
        });
        const data = await res.json();
        
        hideLoading('gen-loading');
        if (data.success) {
            showResult('gen-result', 'success', `
                <strong>✅ สร้างคีย์สำเร็จ!</strong>
                <div class="key-display" id="new-key">${data.data}</div>
                <button class="copy-btn" onclick="copyKey('new-key')">📋 คัดลอกคีย์</button>
                <p style="margin-top:0.5rem;font-size:0.9rem;color:#888;">เก็บรักษาไว้อย่างดี อย่าเปิดเผยให้ผู้อื่น</p>
            `);
        } else {
            showResult('gen-result', 'error', '❌ ' + data.message);
        }
    } catch (err) {
        hideLoading('gen-loading');
        showResult('gen-result', 'error', '❌ ไม่สามารถเชื่อมต่อเซิร์ฟเวอร์');
        console.error(err);
    }
}

// === 2. ตรวจสอบคีย์ ===
async function validateKey() {
    const key = document.getElementById('val-key').value.trim();
    if (!key) return;
    
    hideResult('val-result');
    showLoading('val-loading');
    
    try {
        const res = await fetch(`${API_BASE}/validate`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ key })
        });
        const data = await res.json();
        
        hideLoading('val-loading');
        if (data.valid) {
            showResult('val-result', 'success', '✅ คีย์นี้ <strong>ถูกต้องและใช้งานได้</strong>');
        } else {
            showResult('val-result', 'error', '❌ คีย์ไม่ถูกต้อง หรือถูกยกเลิกแล้ว');
        }
    } catch (err) {
        hideLoading('val-loading');
        showResult('val-result', 'error', '❌ ไม่สามารถเชื่อมต่อเซิร์ฟเวอร์');
    }
}

// === 3. โหลดรายการทั้งหมด ===
async function loadAllKeys() {
    showLoading('list-loading');
    document.getElementById('list-container').innerHTML = '';
    
    try {
        const res = await fetch(`${API_BASE}/list`, { method: 'POST' });
        const data = await res.json();
        hideLoading('list-loading');
        renderKeyTable(data);
    } catch (err) {
        hideLoading('list-loading');
        document.getElementById('list-container').innerHTML = '<p style="color:#ff4466">❌ ไม่สามารถโหลดข้อมูล</p>';
    }
}

// === 4. ค้นหาตามชื่อ ===
async function searchByUser() {
    const name = document.getElementById('search-user').value.trim();
    if (!name) return loadAllKeys();
    
    showLoading('list-loading');
    document.getElementById('list-container').innerHTML = '';
    
    try {
        const res = await fetch(`${API_BASE}/list`, { method: 'POST' });
        let data = await res.json();
        data = data.filter(k => k.username.includes(name));
        hideLoading('list-loading');
        renderKeyTable(data);
    } catch (err) {
        hideLoading('list-loading');
    }
}

// === แสดงตาราง ===
function renderKeyTable(keys) {
    if (!keys.length) {
        document.getElementById('list-container').innerHTML = '<p style="color:#888;text-align:center;padding:1rem;">ไม่พบรายการ</p>';
        return;
    }
    let html = '<table><thead><tr><th>คีย์</th><th>ผู้ใช้</th><th>วันที่สร้าง</th><th>สถานะ</th></tr></thead><tbody>';
    keys.forEach(k => {
        const statusClass = k.revoked ? 'status-revoked' : 'status-active';
        const statusText = k.revoked ? '❌ ยกเลิก' : '✅ ใช้งานได้';
        html += `
            <tr>
                <td style="font-family:monospace;font-size:0.85rem;">${k.key}</td>
                <td>${k.username}</td>
                <td style="font-size:0.85rem;color:#888;">${formatDate(k.createdAt)}</td>
                <td class="${statusClass}">${statusText}</td>
            </tr>
        `;
    });
    html += '</tbody></table>';
    document.getElementById('list-container').innerHTML = html;
}

// === 5. ยกเลิกคีย์ ===
async function revokeKey() {
    const key = document.getElementById('rev-key').value.trim();
    if (!key) return;
    
    if (!confirm('ต้องการยกเลิกคีย์นี้ใช่หรือไม่? การกระทำนี้ไม่สามารถยกเลิกได้')) return;
    
    hideResult('rev-result');
    showLoading('rev-loading');
    
    try {
        const res = await fetch(`${API_BASE}/revoke`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ key })
        });
        const data = await res.json();
        
        hideLoading('rev-loading');
        if (data.success) {
            showResult('rev-result', 'success', '✅ ยกเลิกคีย์สำเร็จ');
        } else {
            showResult('rev-result', 'error', '❌ ' + (data.message || 'ไม่พบคีย์นี้'));
        }
    } catch (err) {
        hideLoading('rev-loading');
        showResult('rev-result', 'error', '❌ ไม่สามารถเชื่อมต่อเซิร์ฟเวอร์');
    }
}

// === คัดลอกคีย์ ===
function copyKey(elId) {
    const text = document.getElementById(elId).textContent;
    navigator.clipboard.writeText(text).then(() => {
        alert('คัดลอกแล้ว!');
    });
}

// === จัดรูปแบบวันที่ ===
function formatDate(d) {
    if (!d) return '-';
    return d.replace('T', ' ').substring(0, 19);
}
</script>
</body>
</html>
          
