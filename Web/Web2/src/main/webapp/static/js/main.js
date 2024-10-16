const consoleErr = document.querySelector('.console');
const errMessage = Object.freeze({
    EMPTY_FIELDS: "Not all fields are entered",
    SERVER_ERROR: "Internal Server Error",
    REDIRECTION_ERROR: "Bad response / invalid redirection",
    INVALID_JSON: "Invalid json received",
    GRAPH_BUILD_ERROR: "Radius not entered"
});

window.addEventListener('click', () => consoleErr.classList.remove('error'));
window.addEventListener('unhandledrejection', (event) => {
    consoleErr.textContent = event.reason.message;
    consoleErr.classList.add('error');
});

// -------------------------------------------------------------------------------------------------------- //

function setX(sameClick, btn) {
    btn.classList.toggle('selected', !sameClick);
    if (!sameClick && XButton !== null) XButton.classList.remove('selected');
    XButton = sameClick ? null : btn;
}

let XButton = null;
document.querySelectorAll('.form-btn').forEach(btn => btn.addEventListener('click', function () {
    setX(btn === XButton, btn);
    accessSubmit();
}));

// -------------------------------------------------------------------------------------------------------- //

function setY(value) {
    if (!/^-?\d*[.,]?\d*$/.test(value) || value !== '-' && !(value >= -5 && value <= 5))
        YInput.value = value.slice(0, -1);
}

const YInput = document.querySelector('.form-input');
YInput.addEventListener('paste', event => event.preventDefault());
YInput.addEventListener('input', function () {
    setY(this.value);
    accessSubmit();
});

// -------------------------------------------------------------------------------------------------------- //

function setR(isDefined, value = "R") {
    RSelect.classList.toggle('unselected', !isDefined);
    handleRSubs(RSubs, value);
    handleRSubs(RDiv2Subs, isDefined ? value / 2 : value + '/2');
}

function handleRChange() {
    RSelect.selectedIndex ? setR(true, RSelect.value) : setR(false);
    changePointPosition(RSelect.value);
    accessSubmit();
}

const RSelect = document.querySelector('.form-select');
document.addEventListener("DOMContentLoaded", handleRChange);
RSelect.addEventListener('change', handleRChange);

// -------------------------------------------------------------------------------------------------------- //

const XListWidth = document.querySelector('.form-list').clientWidth;
YInput.style.width = XListWidth + 'px';
RSelect.style.width = XListWidth + 'px';

// -------------------------------------------------------------------------------------------------------- //

anyFieldNotEntered = () => XButton === null || ['-', ''].includes(YInput.value) || !RSelect.selectedIndex;
accessSubmit = () => submitBtn.disabled = anyFieldNotEntered();

const submitBtn = document.querySelector('.form-submit');
document.addEventListener('DOMContentLoaded', () => submitBtn.disabled = true);

// -------------------------------------------------------------------------------------------------------- //

function handleRSubs(subs, value) {
    subs.forEach(sub => {
        const oldWidth = sub.getBoundingClientRect().width;
        sub.textContent = sub.textContent[0] !== '-' ? value : '-' + value;
        const bias = sub.getBoundingClientRect().width - oldWidth;
        sub.setAttribute("x", sub.getAttribute("x") - bias);
    });
}

const RSubs = document.querySelectorAll('.sub-R');
const RDiv2Subs = document.querySelectorAll('.sub-R_div_2');

// -------------------------------------------------------------------------------------------------------- //

function addNewRecord(data) {
    const newRow = archive.insertRow();
    Object.values(data).forEach(value => newRow.insertCell().textContent = String(value));
}

const archive = document.querySelector('.table-archive');

// -------------------------------------------------------------------------------------------------------- //

isValidResponseJson = (json) => json.hasOwnProperty('x') && json.hasOwnProperty('y')
    && json.hasOwnProperty('r') && json.hasOwnProperty('hit')
    && json.hasOwnProperty('sent') && json.hasOwnProperty('exec');

async function pointCheck(x, y, r, redirect = false) {
    const resp = await fetch(window.root + '/controller' + `?redirect=${redirect}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            x: x,
            y: y,
            r: r,
            sent: new Date().toLocaleTimeString()
        })
    });

    if (!resp.ok) throw new Error(errMessage.SERVER_ERROR);

    if (redirect ^ resp.redirected) throw new Error(errMessage.REDIRECTION_ERROR);
    if (redirect) return window.location.href = resp.url;

    const data = await resp.json();
    if (!isValidResponseJson(data)) throw new Error(errMessage.INVALID_JSON);

    addNewRecord(data);
    addNewPoint(x, y, r, data['hit'] === 'Y');
}

document.querySelector('.form').addEventListener('submit', async function (event) {
    event.preventDefault();
    if (anyFieldNotEntered()) throw new Error(errMessage.EMPTY_FIELDS);
    await pointCheck(XButton.value, YInput.value, RSelect.value, true);
});

// -------------------------------------------------------------------------------------------------------- //

const graph = document.querySelector(".graph__body");
graph.addEventListener('click', async function (event) {
    if (!RSelect.selectedIndex) throw new Error(errMessage.GRAPH_BUILD_ERROR);

    const point = this.createSVGPoint();

    point.x = event.clientX;
    point.y = event.clientY;

    const svgPoint = point.matrixTransform(this.getScreenCTM().inverse());

    const r = RSelect.value;
    const scale = r / 200;

    const x = svgPoint.x * scale;
    const y = -svgPoint.y * scale;

    await pointCheck(x, y, r);
});

// -------------------------------------------------------------------------------------------------------- //

graph.addEventListener('wheel', function (event) {
    event.preventDefault();

    const index = RSelect.selectedIndex;
    if (index === 0) return;

    const newIndex = event.deltaY > 0 ? Math.max(1, index - 1) : Math.min(RSelect.options.length - 1, index + 1);
    if (!(newIndex - index)) return;

    RSelect.selectedIndex = newIndex;
    handleRChange();
});

// -------------------------------------------------------------------------------------------------------- //

function addNewPoint(x, y, r, hit) {
    const point = document.createElementNS('http://www.w3.org/2000/svg', "circle");

    const scale = 200 / r;
    x = x * scale;
    y = -y * scale;

    point.setAttribute('cx', x);
    point.setAttribute('cy', y);
    point.setAttribute('r', '5');

    point.classList.add('graph__point', hit ? 'in' : 'out');

    graph.appendChild(point);
    points.push(point);
}

// -------------------------------------------------------------------------------------------------------- //

let lastR = RSelect.value;

function changePointPosition(newR) {
    if (newR === lastR || Number(newR) === -1) return;

    const scale = Number(lastR) !== -1 ? lastR / newR : 1;

    points.forEach(point => {
        const x = point.getAttribute('cx') * scale;
        const y = point.getAttribute('cy') * scale;

        point.classList.toggle('resonance', point.classList.contains('out') && checkPointInPath(x, y) ||
            point.classList.contains('in') && !checkPointInPath(x, y));

        point.setAttribute('cx', x);
        point.setAttribute('cy', y);
    });

    lastR = newR;
}

const points = Array.from(document.querySelectorAll('.graph__point'));

// -------------------------------------------------------------------------------------------------------- //

function checkPointInPath(x, y) {
    const point = graph.createSVGPoint();

    point.x = x;
    point.y = y;

    return figure.isPointInFill(point) || figure.isPointInStroke(point);
}

const figure = document.querySelector('.graph__figure');