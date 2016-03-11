var stompClient = null;

function setConnected(connected) {
    document.getElementById('connect').disabled = connected;
    document.getElementById('disconnect').disabled = !connected;
    document.getElementById('conversationDiv').style.visibility = connected ? 'visible' : 'hidden';
}

function connect() {
    var socket = new SockJS('/hello');

    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greetings', function (greeting) {
            showGreeting(JSON.parse(greeting.body));
        });
    });
}

function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    var name = document.getElementById('name').value;
    stompClient.send("/app/hello", {}, JSON.stringify({'uri': name}));
}

function addHost() {
    var hs = {};
    hs.uri = $('#name').val();
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        type: "POST",
        url: "/add/host",
        data: JSON.stringify(hs),
        dataType: 'json'
    });
    $('#filters').append('<button value="' + hs.uri + '" onclick="removeHost(this.value)"  class="btn btn-default btn-sm ' + hs.uri + '">' + hs.uri + '<span class="glyphicon glyphicon-remove" aria-hidden="true"></span></button>');
}

function removeHost(host) {
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        type: "DELETE",
        url: "/add/host",
        data: JSON.stringify({uri: host}),
        dataType: "json"
    });
    $('#filters .' + host).remove();
}

function showGreeting(message) {
    var params = '';
    $.each(message.params, function (key, value) {
        params += key + ': ' + value + '<br />\n'
    });
    $('#table > tbody:last').append('' +
        '<tr>' +
        '   <td>' + message.timestamp + '</td>' +
        '   <td>' + message.method + '</td>' +
        '   <td>' + message.uri + '</td>' +
        '   <td>' + params + '</td>' +
        '</tr>')
}