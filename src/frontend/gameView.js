const socket = io.connect("http://localhost:8080",{transports: ['websocket']});
socket.on('gameState', parseGameState);

const tileSize = 30;
const playerSize = 0.3;
const projectileSize = 0.15;
const canvas = document.getElementById("canvas");
const context = canvas.getContext("2d");
context.globalCompositeOperation = 'source-over';

function parseGameState(event) {
    const gameState = JSON.parse(event);

    drawGameBoard(gameState['gridSize']);

    placeSquare(gameState['start']['x'], gameState['start']['y'], '#00ff00');

    for (let spawner of gameState['spawners']) {
        placeSquare(spawner['x'], spawner['y'], '#ff0000');
    }

    for (let player of gameState['players']) {
        placeCircle(player['x']+(playerSize/2.0), player['y']+(playerSize/2.0), player['id'] === socket.id ? '#ffff00' : '#56bcff', playerSize);
    }

    for (let wall of gameState['walls']) {
        placeSquare(wall['x'], wall['y'], 'grey');
    }

    for (let projectile of gameState['projectiles']) {
        placeCircle(projectile['x'], projectile['y'], 'red', projectileSize);
    }

}

function cleanInt(input) {
    const value = Math.round(input);
    const asString = value.toString(16);
    return value > 15 ? asString : "0" + asString;
}

function rgb(r, g, b) {
    return "#" + cleanInt(r) + cleanInt(g) + cleanInt(b);
}


function drawGameBoard(gridSize) {

    const gridWidth = gridSize['x'];
    const gridHeight = gridSize['y'];

    context.clearRect(0, 0, gridWidth * tileSize, gridHeight * tileSize);

    canvas.setAttribute("width", (gridWidth * tileSize).toString());
    canvas.setAttribute("height", (gridHeight * tileSize).toString());

    context.strokeStyle = '#bbbbbb';
    for (let j = 0; j <= gridWidth; j++) {
        context.beginPath();
        context.moveTo(j * tileSize, 0);
        context.lineTo(j * tileSize, gridHeight * tileSize);
        context.stroke();
    }
    for (let k = 0; k <= gridHeight; k++) {
        context.beginPath();
        context.moveTo(0, k * tileSize);
        context.lineTo(gridWidth * tileSize, k * tileSize);
        context.stroke();
    }

}


function placeSquare(x, y, color) {
    context.fillStyle = color;
    context.fillRect(x * tileSize, y * tileSize, tileSize, tileSize);
    context.strokeStyle = 'black';
    context.strokeRect(x * tileSize, y * tileSize, tileSize, tileSize);
}


function placeCircle(x, y, color, size) {
    context.fillStyle = color;
    context.beginPath();
    context.arc(x * tileSize,
        y * tileSize,
        size * tileSize,
        0,
        2 * Math.PI);
    context.fill();
    context.strokeStyle = 'black';
    context.stroke();
}

function xComp(degrees){
    return Math.cos(Math.PI*degrees/180.0)
}

function yComp(degrees){
    return Math.sin(Math.PI*degrees/180.0)
}

function drawTower(x, y) {
    const size = 3.0;

    const scaledSize = size / 10.0 * tileSize;
    const centerX = (x + 0.5) * tileSize;
    const centerY = (y + 0.5) * tileSize;

    context.fillStyle = '#760672';
    context.strokeStyle = 'black';

    context.beginPath();
    context.moveTo(centerX + scaledSize , centerY);
    for(let i = 0; i<=7; i++){
        const degrees = i*60.0;
        context.lineTo(centerX + xComp(degrees) * scaledSize, centerY + yComp(degrees) * scaledSize);
    }

    context.lineWidth = 5;
    context.stroke();
    context.lineWidth = 1;
    context.fill()
}
