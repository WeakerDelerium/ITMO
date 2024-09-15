const XCheckboxes = document.querySelectorAll('.X-input__checkbox');
const RCheckboxes = document.querySelectorAll('.R-input__checkbox');
const YInput = document.querySelector('.form-input');

let activeXCheckbox = null;
let activeRCheckbox = null;

const RSubs = document.querySelectorAll('.sub-R');
const RDiv2Subs = document.querySelectorAll('.sub-R_div_2');

const userPoint = document.querySelector('.graph__point');

const submitBtn = document.querySelector('.form-submit');

const archive = document.querySelector('.table-archive');

function handlePoint() {
    if ((activeRCheckbox && activeXCheckbox) === null || ['-', ''].includes(YInput.value)) {
        userPoint.setAttribute('r', '0');
        submitBtn.disabled = true;
    } else {
        submitBtn.disabled = false;

        if (!(isFinite(activeXCheckbox.value) && isFinite(YInput.value) && isFinite(activeRCheckbox.value))) return;

        const scale = 200 / activeRCheckbox.value;
        const X = activeXCheckbox.value * scale;
        const Y = -YInput.value * scale;

        userPoint.setAttribute('cx', X.toString());
        userPoint.setAttribute('cy', Y.toString());
        userPoint.setAttribute('r', '5');
    }
}

function handleRSubs(subs, value) {
    subs.forEach(sub => sub.textContent = sub.textContent[0] !== '-' ? value : '-' + value);
}

XCheckboxes.forEach(checkbox => checkbox.addEventListener('change', () => {
    if (checkbox === activeXCheckbox) activeXCheckbox = null; else {
        if (activeXCheckbox !== null) activeXCheckbox.checked = false;
        activeXCheckbox = checkbox;
    }
    handlePoint();
}));

RCheckboxes.forEach(checkbox => checkbox.addEventListener('change', () => {
    if (checkbox === activeRCheckbox) {
        activeRCheckbox = null;
        handleRSubs(RSubs, 'R');
        handleRSubs(RDiv2Subs, 'R/2');
    } else {
        if (activeRCheckbox !== null) activeRCheckbox.checked = false;
        activeRCheckbox = checkbox;
        if (!isFinite(activeRCheckbox.value)) return;
        handleRSubs(RSubs, activeRCheckbox.value);
        handleRSubs(RDiv2Subs, activeRCheckbox.value / 2);
    }
    handlePoint();
}));

YInput.addEventListener('paste', event => event.preventDefault());
YInput.addEventListener('input', function () {
    if (!/^-?\d*\.?\d*$/.test(this.value)) this.value = this.value.slice(0, -1);
    handlePoint();
});

async function exchange(event) {
    event.preventDefault();

    const X = activeXCheckbox.value;
    const Y = YInput.value;
    const R = activeRCheckbox.value;

    const queryParams = new URLSearchParams();

    queryParams.append('x', X);
    queryParams.append('y', Y);
    queryParams.append('r', R);

    const startExec = Date.now();

    const sendTime = new Date().toLocaleTimeString();

    const response = await fetch(`/fcgi-bin/FCGIServer.jar?${queryParams.toString()}`);
    if (!response.ok) return;

    const responseData = await response.json();
    if (!('hit' in responseData)) throw new Error('Invalid data received from server');

    const endExec = Date.now();

    addNewRow(X, Y, R, responseData.hit, sendTime, `${endExec - startExec} мс`);
}

function addNewRow(X, Y, R, hit, sent, exec) {
    const newRow = archive.insertRow();
    [X, Y, R, hit, sent, exec].forEach(value => newRow.insertCell().textContent = value);
}