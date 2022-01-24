'use strict';

var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#text');
var messageArea = document.querySelector('#messageArea');
var connectingElement = document.querySelector('.connecting');
var roomNumber = document.location.pathname.substr(document.location.pathname.lastIndexOf('#message') + 8);


var stompClient = null;
var userName = null;

function onConnected() {
    stompClient.subscribe('/topic/public/', onMessageReceived);
    connectingElement.classList.add('hidden');
}

function onError() {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red'
}

function send(event) {
    if (!stompClient) {
        userName = document.querySelector('#name').textContent.trim();

        if (userName) {
            var socket = new SockJS('/chat-websocket');
            stompClient = Stomp.over(socket);

            stompClient.connect({}, onConnected, onError);
        }
        event.preventDefault();
    } else {
        var messageContent = messageInput.value.trim();
        var current_dataTime = new Date();

        if (messageContent && stompClient) {
            var chatMessage = {
                author: username,
                text: messageInput.value,
                room: roomNumber,
                creationDate: current_dataTime.getFullYear() + "-" + ("0" + (current_dataTime.getMonth() + 1)).slice(-2) +
                    "-" + ("0" + current_dataTime.getDay()).slice(-2) + " " + ("0" + current_dataTime.getHours()).slice(-2) +
                    ":" + ("0" + current_dataTime.getMinutes()).slice(-2) + ":" + ("0" + current_dataTime.getSeconds()).slice(-2)
            };
            stompClient.send('/app/chat.send', JSON.stringify(chatMessage));
            messageInput.value = '';
        }
        event.preventDefault();
    }
}

function onMessageReceived(payload) {

    var message = JSON.parse(payload.body);

    if ((message.room === roomNumber && message.room !== 'Bot') || (message.author === username && message.room === roomNumber && message.room === 'bot')) {
        var messageElement = document.createElement('li');

        messageElement.classList.add('chat-message');
        var usernameElement = document.createElement('span');
        var usernameText = document.createTextNode(message.author + ' (' + message.creationDate + ') ');
        usernameElement.appendChild(usernameText)
        messageElement.appendChild(usernameElement)

        var textElement = document.createElement('p');
        textElement.innerHTML = message.text;

        messageElement.appendChild(textElement);

        messageArea.appendChild(messageElement);
    }
}

messageForm.addEventListener('submit', send, true);
document.querySelector('#send').click();


