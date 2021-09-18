<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>API TEST</title>
	
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet"
		integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x" crossorigin="anonymous">
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.min.js"></script>
	<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
	
	<script>
		$(document).ready(function () {
			let loginIdHtml = `
				<div class="alert alert-danger d-flex align-items-center alert-dismissible fade show" role="alert" id="inputIdAlert">
					<svg class="bi flex-shrink-0 me-2" width="24" height="24" role="img" aria-label="Danger:"><use xlink:href="#exclamation-triangle-fill"/></svg>
					<div>아이디를 입력하세요</div>
					<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
				</div>
			`;

			let loginPwHtml = `
				<div class="alert alert-danger d-flex align-items-center alert-dismissible fade show" role="alert" id="inputPwAlert">
					<svg class="bi flex-shrink-0 me-2" width="24" height="24" role="img" aria-label="Danger:"><use xlink:href="#exclamation-triangle-fill"/></svg>
					<div>비밀번호를 입력하세요</div>
					<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
				</div>
			`;
			
			$("#loginModal").on("show.bs.modal", function () {
				$('#loginIdDiv').html('');
				$('#loginPwDiv').html('');
			});

			$("#loginModal").on("shown.bs.modal", function () {
				$('#login-id').focus();
			});

			$("#login-id").on("keyup", function (key) {
				if (key.keyCode == 13) {
					if ($("#login-id").val().trim() == "") {
						$('#login-id').focus();
						if ($("#loginIdDiv").children().length == 0) {
							$("#loginIdDiv").html(loginIdHtml).hide().fadeIn(500);
						}
					} else {
						$('#login-pw').focus();
						$("#inputIdAlert").alert('close');
					}
				}
			});

			$("#login-pw").on("keyup", function (key) {
				if (key.keyCode == 13) {
					if ($("#login-pw").val().trim() == "") {
						$('#login-pw').focus();
						if ($("#loginPwDiv").children().length == 0) {
							$("#loginPwDiv").html(loginPwHtml).hide().fadeIn(500);
						}
					} else {
						$("#inputPwAlert").alert('close');
					}
				}
			});
		});
	</script>
</head>
<body>
	<nav class="navbar navbar-expand-sm bg-dark navbar-dark">
		<div class="container">
			<a class="navbar-brand" href="${pageContext.request.contextPath}/">
				API TEST
			</a>
			<ul class="navbar-nav">
				<li class="nav-item">
					<a class="nav-link" href="${pageContext.request.contextPath}/">홈</a>
				</li>
				<li class="nav-item">
					<a class="nav-link" href="${pageContext.request.contextPath}/blog">블로그 API</a>
				</li>
				<li class="nav-item">
					<a class="nav-link" href="${pageContext.request.contextPath}/youtube">유튜브 API</a>
				</li>
			</ul>
			<div class="ml-auto">
				<button type="button" class="btn btn-sm btn-success" data-bs-toggle="modal" data-bs-target="#loginModal">
					로그인
				</button>
				<button type="button" class="btn btn-sm btn-danger" data-bs-toggle="modal" data-bs-target="#logoutModal">
					로그아웃
				</button>
			</div>
		</div>
		<div class="modal fade" id="loginModal" tabindex="-1" aria-labelledby="loginModalLabel" aria-hidden="true">
			<div class="modal-dialog modal-dialog-centered">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title" id="loginModalLabel">로그인</h5>
						<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="닫기"></button>
					</div>
					<div class="modal-body">
						<svg xmlns="http://www.w3.org/2000/svg" style="display: none;">
							<symbol id="check-circle-fill" fill="currentColor" viewBox="0 0 16 16">
								<path d="M16 8A8 8 0 1 1 0 8a8 8 0 0 1 16 0zm-3.97-3.03a.75.75 0 0 0-1.08.022L7.477 9.417 5.384 7.323a.75.75 0 0 0-1.06 1.06L6.97 11.03a.75.75 0 0 0 1.079-.02l3.992-4.99a.75.75 0 0 0-.01-1.05z" />
							</symbol>
							<symbol id="info-fill" fill="currentColor" viewBox="0 0 16 16">
								<path d="M8 16A8 8 0 1 0 8 0a8 8 0 0 0 0 16zm.93-9.412-1 4.705c-.07.34.029.533.304.533.194 0 .487-.07.686-.246l-.088.416c-.287.346-.92.598-1.465.598-.703 0-1.002-.422-.808-1.319l.738-3.468c.064-.293.006-.399-.287-.47l-.451-.081.082-.381 2.29-.287zM8 5.5a1 1 0 1 1 0-2 1 1 0 0 1 0 2z" />
							</symbol>
							<symbol id="exclamation-triangle-fill" fill="currentColor" viewBox="0 0 16 16">
								<path d="M8.982 1.566a1.13 1.13 0 0 0-1.96 0L.165 13.233c-.457.778.091 1.767.98 1.767h13.713c.889 0 1.438-.99.98-1.767L8.982 1.566zM8 5c.535 0 .954.462.9.995l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 5.995A.905.905 0 0 1 8 5zm.002 6a1 1 0 1 1 0 2 1 1 0 0 1 0-2z" />
							</symbol>
						</svg>
						<div id="loginIdDiv"></div>
						<div id="loginPwDiv"></div>
						<form>
							<div class="mb-3">
								<span class="badge bg-secondary mb-2">아이디</span>
								<input type="text" class="form-control" id="login-id">
							</div>
							<div class="mb-3">
								<span class="badge bg-secondary mb-2">비밀번호</span>
								<input type="password" class="form-control" id="login-pw">
							</div>
						</form>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-sm btn-primary me-auto" tabindex="3">회원가입</button>
						<button type="submit" class="btn btn-sm btn-success" tabindex="1">로그인</button>
						<button type="button" class="btn btn-sm btn-danger" data-bs-dismiss="modal" tabindex="2">닫기</button>
					</div>
				</div>
			</div>
		</div>
	</nav>

	<div class="container mt-4">
		<form method="GET" action="${pageContext.request.contextPath}/youtube">
			<div class="col-3 ps-0">
                <div class="input-group">
                    <input type="text" class="form-control" name="word" value="${word}">
                    <button type="submit" class="btn btn-primary">검색</button>
                </div>
            </div>
		</form>
		<br>
		<c:if test="${empty data}">
			<div>(검색어를 입력해주세요)</div>
		</c:if>
		<c:if test="${!empty data}">
			<div class="row row-cols-1 row-cols-md-4 g-4">
				<c:forEach var="item" items="${data.items}">
					<div class="col">
						<div class="card text-center" style="width: 18rem;">
							<div class="card-header">
								<img src="${item.snippet.thumbnails.high.url}" class="card-img-top" alt="...">
							</div>
							<div class="card-body">
								<h5 class="card-title">${item.snippet.title}</h5>
							</div>
							<ul class="list-group list-group-flush">
								<li class="list-group-item">
									${item.snippet.description}
								</li>
							</ul>
							<div class="card-body">
								<c:if test="${item.id.kind eq 'youtube#video'}">
							    	<a href="https://www.youtube.com/watch?v=${item.id.videoId}" class="btn btn-sm btn-primary">바로가기</a>
								</c:if>
								<c:if test="${item.id.kind eq 'youtube#channel'}">
							    	<a href="https://www.youtube.com/channel/${item.id.channelId}" class="btn btn-sm btn-primary">바로가기</a>
								</c:if>
							</div>
						</div>
					</div>
				</c:forEach>
			</div>
		</c:if>
	</div>
</body>
</html>