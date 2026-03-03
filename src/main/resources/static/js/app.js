const API_BASE = '/api/v1';

// Format currency
const formatCurrency = (amount) => {
    return new Intl.NumberFormat('en-IN', {
        style: 'currency',
        currency: 'INR'
    }).format(amount);
};

// UI Helpers
const setLoading = (btnId, isLoading, originalText) => {
    const btn = document.getElementById(btnId);
    if (isLoading) {
        btn.classList.add('loading');
        btn.innerHTML = `<i data-feather="loader" class="spin"></i> Processing...`;
        feather.replace();
    } else {
        btn.classList.remove('loading');
        btn.innerHTML = originalText;
        feather.replace();
    }
};

const showResult = (boxId, htmlContent, isError = false) => {
    const box = document.getElementById(boxId);
    box.classList.remove('hidden', 'error');
    if (isError) {
        box.classList.add('error');
    }
    box.innerHTML = htmlContent;
};

// 1. Combined Calculator
document.getElementById('combinedForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    const btnText = document.getElementById('btn-combined').innerHTML;
    setLoading('btn-combined', true, btnText);

    const payload = {
        sellerId: parseInt(document.getElementById('c_sellerId').value),
        customerId: parseInt(document.getElementById('c_customerId').value),
        deliverySpeed: document.getElementById('c_speed').value,
        weight: parseFloat(document.getElementById('c_weight').value)
    };

    try {
        const response = await fetch(`${API_BASE}/shipping-charge/calculate`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(payload)
        });

        const data = await response.json();

        if (!response.ok) throw new Error(data.message || 'API Error');

        const html = `
            <div class="result-content">
                <div class="result-row">
                    <span class="result-label">Nearest Hub</span>
                    <span class="result-val">Warehouse ID: ${data.nearestWarehouse.warehouseId}</span>
                </div>
                <div class="result-row">
                    <span class="result-label">Hub Coordinates</span>
                    <span class="coord">${data.nearestWarehouse.warehouseLocation.lat.toFixed(4)}, ${data.nearestWarehouse.warehouseLocation.lng.toFixed(4)}</span>
                </div>
                <div class="result-row" style="margin-top: 10px;">
                    <span class="result-label">Total Shipping Charge</span>
                    <span class="result-val highlight">${formatCurrency(data.shippingCharge)}</span>
                </div>
            </div>
        `;
        showResult('combinedResult', html);
    } catch (err) {
        showResult('combinedResult', `<i data-feather="alert-circle"></i> Error: ${err.message}`, true);
        feather.replace();
    } finally {
        setLoading('btn-combined', false, btnText);
    }
});

// 2. Nearest Warehouse
document.getElementById('nearestForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    const btnText = document.getElementById('btn-nearest').innerHTML;
    setLoading('btn-nearest', true, btnText);

    const sellerId = document.getElementById('n_sellerId').value;
    const productId = document.getElementById('n_productId').value;

    try {
        const response = await fetch(`${API_BASE}/warehouse/nearest?sellerId=${sellerId}&productId=${productId}`);
        const data = await response.json();
        if (!response.ok) throw new Error(data.message || 'API Error');

        const html = `
            <div class="result-content">
                <div class="result-row">
                    <span class="result-label">Warehouse ID</span>
                    <span class="result-val">${data.warehouseId}</span>
                </div>
                <div class="result-row">
                    <span class="result-label">GPS Location</span>
                    <span class="coord">${data.warehouseLocation.lat.toFixed(4)}, ${data.warehouseLocation.lng.toFixed(4)}</span>
                </div>
            </div>
        `;
        showResult('nearestResult', html);
    } catch (err) {
        showResult('nearestResult', `<i data-feather="alert-circle"></i> Error: ${err.message}`, true);
        feather.replace();
    } finally {
        setLoading('btn-nearest', false, btnText);
    }
});

// 3. Simple Charge Calculator
document.getElementById('chargeForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    const btnText = document.getElementById('btn-charge').innerHTML;
    setLoading('btn-charge', true, btnText);

    const warehouseId = document.getElementById('s_warehouseId').value;
    const customerId = document.getElementById('s_customerId').value;
    const speed = document.getElementById('s_speed').value;
    const weight = document.getElementById('s_weight').value;

    try {
        const url = `${API_BASE}/shipping-charge?warehouseId=${warehouseId}&customerId=${customerId}&deliverySpeed=${speed}&weight=${weight}`;
        const response = await fetch(url);
        const data = await response.json();

        if (!response.ok) throw new Error(data.message || 'API Error');

        const html = `
            <div class="result-content">
                <div class="result-row">
                    <span class="result-label">Direct Charge</span>
                    <span class="result-val highlight" style="font-size: 20px;">${formatCurrency(data.shippingCharge)}</span>
                </div>
            </div>
        `;
        showResult('chargeResult', html);
    } catch (err) {
        showResult('chargeResult', `<i data-feather="alert-circle"></i> Error: ${err.message}`, true);
        feather.replace();
    } finally {
        setLoading('btn-charge', false, btnText);
    }
});
