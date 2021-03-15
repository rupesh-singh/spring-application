
function Callme(){
	console.log("in caller method");
	var request = new XMLHttpRequest();
	request.open('GET','http://localhost:8080/getlogs',true)
	request.onload = function() {
		console.log("Started Log");
	}
	request.send();
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#log").show();
    }
    else {
        $("#log").hide();
    }
    $("#logdetails").html("");
}

function showLogs(message) {
    $("#logdetails").append("<tr><td>" + message + "</td></tr>");
}

function connect() {
    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/log', function (logdetails) {
            showLogs(logdetails.body);
        });
    });
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
	$( "#startlogs" ).click(function() { Callme(); });
});