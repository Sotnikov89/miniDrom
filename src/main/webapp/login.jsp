<%@ page contentType="text/html; charset=UTF-8" %>
<!doctype html>
<html lang="ru">
<head>
    <!-- Required meta tags -->
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"
            integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.1/dist/umd/popper.min.js"
            integrity="sha384-SR1sx49pcuLnqZUnnPwx6FCym0wLsk5JZuNx2bPPENzswTNFaQU1RDvt3wT4gWFG" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.min.js"
            integrity="sha384-j0CNLUeiqtyaRmlzUHCPZ+Gy5fQu0dQ6eZ/xAww941Ai1SxSY+0EQqNXNE6DZiVc" crossorigin="anonymous"></script>
    <title>МиниДром - продажа и покупка авто</title>
    <script>
        function sendAuth() {
            var data = {email: $('#email').val(), password: $('#password').val()}
            $.ajax({
                type: "POST",
                url: 'auth',
                data: data,
            }).done(function () {
                window.location.href = 'index';
            }).fail(function () {
                alert("Вы ввели не верные почту или пароль. Попробуйте еще раз.");
            })
        }
    </script>
</head>
<body>
<div class="container pt-1">
    <div class="row pt-2 row-cols-auto">
        <div class="col-md-auto">
            <button type="button" class="btn btn-secondary" onclick="window.location.href='index'">Главная</button>
        </div>
        <div class="col-md-auto">
            <button type="button" class="btn btn-secondary" onclick="window.location.href='auth'">Вход и регистрация</button>
        </div>
    </div>
    <div class="row pt-2">
        <div class="card col-8" style="width: 100%">
            <div class="card-header">
                Авторизация
            </div>
            <div class="card-body">
                    <div class="form-group col-8">
                        <label>Почта</label>
                        <input type="text" class="form-control" id="email">
                    </div>
                    <div class="form-group col-8">
                        <label>Пароль</label>
                        <input type="password" class="form-control" id="password">
                    </div>
                    <button type="submit" class="btn btn-primary" onclick="sendAuth()">Войти</button>
            </div>
            <div class="col-md-1">
                <button type="button" class="btn btn-primary" onclick="window.location.href='reg'">Регистрация</button>
            </div>
        </div>
    </div>
</div>
</body>
</html>