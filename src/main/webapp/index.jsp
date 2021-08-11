<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>실시간 채팅방</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script>
var lastNo = 0;
var hosting = true;
function submitFunction(){
	$.ajax({
		type: "POST",
		url: "./chatting",
		data: {
			chatName : $('#chatName').val(),
			chatContent : $('#chatContent').val()
		},
		success:function(result){
			if(result == 1){
				autoClosingAlert("#chatSuccess", 2000);
			}else if(result == 0){
				autoClosingAlert("#chatDanger", 2000);
			}else{
				autoClosingAlert("#chatWarning", 2000);
			}
		}
	});
	$("#chatContent").val("");
}
function autoClosingAlert(selector, delay){
	var alert = $(selector);
	alert.show();
	window.setTimeout(function(){alert.hide()}, delay);
}
function chatListFunction(type){
	$.ajax({
		type: "POST",
		url: "./chattingList",
		data: {
			listType: type,
		},
		success:function(data){
			if(data == "") return;
			var parsed = JSON.parse(data);
			var result = parsed.result;
			for(var i = 0; i < result.length;i++){
				addChat(result[i][0].value, result[i][1].value, result[i][2].value);
			}
			lastNo = Number(parsed.last);
		}
	});
}
function addChat(chatName, chatContent, chatTime){
	$("#chatList").append(
		'<div class="row">' +
		'<div class="media">' +
		'<a class="mediaUser" href="#">' +
		'<img class="chatImg" src="images/icon.png" alt="사용자 이미지">' +
		chatName +
		'</a>' +
		'<div class="mediaBody">' +
		'<span class="chatTime">' + 
		chatTime +
		'</span>' +
		'<p class="chatMain">'+ 
		chatContent +
		'</p>' +
		'</div>' +
		'</div>' +
		'</div>' +
		'<hr>'
	);
	$("#chattingBody").scrollTop($("#chattingBody")[0].scrollHeight);
}
function checkChat(){
	setInterval(function(){
		chatListFunction(lastNo);
	}, 1000);
}
</script>
<style type="text/css">
*{padding:0;margin:0;list-style:none;font-family:'빙그레 메로나체';}
body{background-color:#eee;}
#wrap{width:100%;text-align:center;}
	#container{width:700px;height:700px;margin:50px auto 0;position:relative;}
		#chattingHeader{width:100%;height:50px;line-height:50px;background-color:#AED6F1;}
			#title{font-style:italic;text-decoration:underline;}
		
		#chattingBody{width:100%;height:500px;overflow:auto;}
		#chattingBody {-ms-overflow-style: none; /* IE and Edge */
		    			scrollbar-width: none; /* Firefox */}
		#chattingBody::-webkit-scrollbar {display: none; /* Chrome, Safari, Opera*/}
			#chatList{width:100%;}
				.row{width:95%;padding:10px;margin:0 auto;}
					.media{width:100%;clear:both;overflow:hidden;text-align:left;}
						.mediaUser{display:block;width:30%;float:left;text-decoration:none;color:#333;}
						.mediaBody{width:70%;float:right;}
							.chatTime{display:block;width:100%;text-align:right;font-size:14px;}
						.mediaBody:after{ content:""; display:block; clear:both;}
		#chattingWrite{width:100%;padding-top:20px;text-align:left;height:180px;}
			#formGroup{width:690px;padding-left:10px;}
				#chatName{width:200px;height:20px;padding:10px;}
				#chatContent{width:95%;height:80px;overflow:auto;resize:none;margin-top:10px;padding:10px;}
				#cwBtn{width:50px;height:40px;line-height:40px;font-size:14px;vertical-align:middle;}
			
		.message{width:100%;position:absolute;bottom:160px;text-align:center;background-color:rgba(213, 245, 227, 0.5);transition:0.3s;display:none;}
		

</style>
</head>
<body>
<div id="wrap">
	<div id="container">
		<div id="chattingHeader">
			<div id="title"><h4>실시간 채팅창</h4></div>
		</div>
		<div id="chattingBody">
			<div id="chatList">
			</div>
		</div>
		<div id="chattingWrite">
			<form>
				<div class="formGroup">
					<input type="text" id="chatName" placeholder="이름" maxlength="8"/>
					<button type="button" id="cwBtn" onclick="submitFunction();">보내기</button>
				    <br>
				    <textarea id="chatContent" placeholder="Enter message..." maxlength="200"></textarea>
				</div>
			</form>
		</div>
		<div id="chatSuccess" class="message">
			<strong>메시지 전송에 성공하였습니다.</strong>
		</div>
		<div id="chatDanger" class="message">
			<strong>이름과 내용을 모두 입력해주세요.</strong>
		</div>
		<div id="chatWarning" class="message">
			<strong>데이터베이스 오류가 발생했습니다.</strong>
		</div>
	</div>
</div>
<script>
	$(document).ready(function(){
		chatListFunction("ten");
		checkChat();
	});
</script>
</body>
</html>