<!doctype html>
<html lang="en">
<head>
    <title>Websocket ChatRoom</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <link rel="stylesheet" href="/webjars/bootstrap/4.3.1/dist/css/bootstrap.min.css">
    <style>
        [v-cloak] {
            display: none;
        }
        body {
            display: flex;
            flex-direction: column;
            justify-content: space-between;
            min-height: 100vh;
            background-color: #f8f9fa;
        }
        #app {
            display: flex;
            flex-direction: column;
            justify-content: space-between;
            height: 100%;
        }
        .chat-header {
            margin-bottom: 20px;
        }
        .chat-messages {
            flex-grow: 1;
            overflow-y: auto;
            margin-bottom: 20px;
        }
        .chat-input {
            margin-top: 20px;
        }
    </style>
</head>
<body>
<div class="container" id="app" v-cloak>
    <div class="chat-header text-center">
        <h2>{{ roomName }}</h2>
    </div>
    <div class="chat-messages">
        <ul class="list-group">
            <li class="list-group-item" v-for="message in messages">
                {{ message.sender }} - {{ message.message }}
            </li>
        </ul>
        <div v-if="searchResults.length > 0">
            <h4>검색 결과</h4>
            <ul class="list-group">
                <li class="list-group-item" v-for="message in searchResults">
                    {{ message.sender }} - {{ message.message }}
                </li>
            </ul>
        </div>
    </div>
    <div class="chat-input">
        <div class="input-group mb-3">
            <div class="input-group-prepend">
                <label class="input-group-text">검색</label>
            </div>
            <input type="text" class="form-control" v-model="searchKeyword" v-on:keypress.enter="searchMessages">
            <div class="input-group-append">
                <button class="btn btn-secondary" type="button" @click="searchMessages">검색</button>
            </div>
        </div>
        <div class="input-group">
            <div class="input-group-prepend">
                <label class="input-group-text">내용</label>
            </div>
            <input type="text" class="form-control" v-model="message" v-on:keypress.enter="sendMessage">
            <div class="input-group-append">
                <button class="btn btn-primary" type="button" @click="sendMessage">보내기</button>
            </div>
        </div>
    </div>
</div>
<script src="/webjars/vue/2.5.16/dist/vue.min.js"></script>
<script src="/webjars/axios/0.17.1/dist/axios.min.js"></script>
<script src="/webjars/sockjs-client/1.1.2/sockjs.min.js"></script>
<script src="/webjars/stomp-websocket/2.3.3-1/stomp.min.js"></script>
<script>
    var sock = new SockJS("/ws-stomp");
    var ws = Stomp.over(sock);
    var reconnect = 0;
    var vm = new Vue({
        el: '#app',
        data: {
            roomId: 'main-room',
            roomName: 'CodeEasy Chatting Room',
            sender: '',
            message: '',
            messages: [],
            searchKeyword: '',
            searchResults: []
        },
        created() {
            var sender = prompt('대화명을 입력해 주세요.');
            if(sender != "") {
                this.sender = sender;
                this.loadMessages();
                this.connectWebSocket();
            } else {
                alert('대화명을 입력해 주세요.');
                location.reload();
            }
        },
        methods: {
            sendMessage: function() {
                ws.send("/pub/chats/send", {}, JSON.stringify({type:'TALK', roomId:this.roomId, sender:this.sender, message:this.message}));
                this.message = '';
            },
            recvMessage: function(recv) {
                this.messages.push({"type":recv.type,"sender":recv.type == 'ENTER' ? '[알림]' : recv.sender,"message":recv.message})
            },
            connectWebSocket: function() {
                var _this = this;
                ws.connect({}, function(frame) {
                    ws.subscribe("/sub/chats" + _this.roomId, function(message) {
                        var recv = JSON.parse(message.body);
                        _this.recvMessage(recv);
                    });
                    ws.send("/pub/chats/send", {}, JSON.stringify({type:'ENTER', roomId:_this.roomId, sender:_this.sender}));
                }, function(error) {
                    if(reconnect++ < 5) {
                        setTimeout(function() {
                            sock = new SockJS("/ws-stomp");
                            ws = Stomp.over(sock);
                            _this.connectWebSocket();
                        }, 10 * 1000);
                    }
                });
            },
            loadMessages: function() {
                var _this = this;
                axios.get('/chats/messages/' + this.roomId).then(function(response) {
                    _this.messages = response.data;
                });
            },
            searchMessages: function() {
                var _this = this;
                axios.get('/chats/search', {
                    params: { keyword: this.searchKeyword }
                }).then(function(response) {
                    _this.searchResults = response.data;
                    // 검색 결과가 없을 때 searchResults 배열이 빈 배열인지 확인하여 처리
                    if (_this.searchResults.length === 0) {
                        alert('검색 결과가 없습니다.');
                    }
                });
            }
        }
    });
</script>
</body>
</html>