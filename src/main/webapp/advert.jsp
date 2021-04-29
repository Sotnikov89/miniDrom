<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
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
        $(document).ready( function() {
            $.ajax({
                type: 'GET',
                url: 'make',
                dataType: 'json',
            }).done(function(data) {
                for(let i=0; i<data.length; i++) {
                    $("#make").append(new Option(data[i].name, data[i].id));
                    for(let y=0; y<data[i].models.length; y++) {
                        $("#model").append(new Option(data[i].name + " - " + data[i].models[y].name, data[i].models[y].id));
                    }
                }
            }).fail(function(err){
                alert(err);
            });
            $.ajax({
                type: 'GET',
                url: 'type',
                dataType: 'json',
            }).done(function(data) {
                for(let i=0; i<data.length; i++) {
                    $("#type").append(new Option(data[i].name, data[i].id));
                }
            }).fail(function(err){
                alert(err);
            });
            $.ajax({
                type: 'GET',
                url: 'advert',
                dataType: 'json',
                data: {getByUser: true},
            }).done(function(data) {
                $('#exit').append("<div class='col-md-auto'> <h4>" + "Здравствуйте " + data[0].user.name + "!</h4></div>")
                for (let i=0; i<data.length; i++) {
                    var status;
                    var done = "Активна"
                    if (data[i].sold) {
                        status=" checked";
                        done = "Продана";
                    }
                    $('#first').append(
                        "<div class='container border-bottom pt-3'>" +
                            "<div class='row gx-1 justify-content-center'>" +
                                "<div class='col-sm-1'>" +
                                    "<img src='download?photoId=" + data[i].photoId + "' width='100px' height='100px'/>" +
                                "</div>" +
                                "<div class='col-sm-5'>" +
                                    "<div class='row row-cols-auto'>" +
                                         "<div class='col-12 col-sm-4'>" +
                                            "Марка - " + data[i].make +
                                        "</div>" +
                                        "<div class='col-12 col-sm-4'>" +
                                            "Модель - " + data[i].model.name +
                                        "</div>" +
                                        "<div class='col-12 col-sm-4'>" +
                                            "Кузов - " + data[i].typeBody.name +
                                        "</div>" +
                                    "</div>" +
                                    "<div class='row row-cols-auto'>" +
                                        "<div class='col-12 col-sm-4'>" +
                                            "Пробег - " + data[i].mileage +
                                        "</div>" +
                                        "<div class='col-12 col-sm-4'>" +
                                            "Цена - " + data[i].price +
                                        "</div>" +
                                        "<div class='col-12 col-sm-4'>" +
                                            "Год выпуска - " + data[i].yearOfIssue +
                                        "</div>" +
                                    "</div>" +
                                    "<div class='row row-cols-auto'>" +
                                        "<div class='col-12 col-sm-1'>" +
                                        "</div>" +
                                        "<div class='col-12 col-sm-11 form-check form-switch'>" +
                                            "<input class='form-check-input' type='checkbox' id='switch'" + status + ">" +
                                            "<label class='form-check-label' for='switch'>" + done + "</label>" +
                                        "</div>" +
                                    "</div>" +
                                "</div>" +
                            "</div>" +
                            "<div class='row justify-content-center'>" +
                                "Описание: " + data[i].description +
                            "</div>" +
                        "</div>"
                    )
                }
            }).fail(function(err){
                alert(err);
            });
        })
    </script>
</head>
<body>
<div class="container pt-1" id="first">
    <div class="row pt-2 row-cols-auto justify-content-center">
        <div class="col-md-auto">
            <button type="button" class="btn btn-secondary" onclick="window.location.href='index'">Главная</button>
        </div>
        <div class="col-md-auto" id="exit">
            <button type="button" class="btn btn-secondary" onclick="window.location.href='auth'">Вход и регистрация</button>
        </div>
    </div>
    <div class="row pt-2 row-cols-auto justify-content-center">
        <div class="col-6">
            <h4>Новое объявление</h4>
        </div>
    </div>
    <form action="advert" method="post" enctype="multipart/form-data">
    <div class="row pt-2 row-cols-auto justify-content-center">
        <div class="col-6">
            Описание
            <textarea class="form-control" rows="4" id="desc" name="desc"></textarea>
        </div>
    </div>
    <div class="row pt-2 row-cols-auto justify-content-center">
        <div class="col-6 col-md-2">
            Марка
            <select class='form-select' aria-label='multiple select example' id='make' name='make'>
            </select>
        </div>
        <div class="col-6 col-md-2">
            Модель
            <select class='form-select' aria-label='multiple select example' id='model' name='model'>
            </select>
        </div>
        <div class="col-6 col-md-2">
            Кузов
            <select class='form-select' aria-label='multiple select example' id='type' name='type'>
            </select>
        </div>
    </div>
    <div class="row pt-2 row-cols-auto justify-content-center">
        <div class="col-6 col-md-2">
            Пробег
            <input type="text" class="form-control" size="7" id="mileage" name="mileage">
        </div>
        <div class="col-6 col-md-2">
            Цена
            <input type="text" class="form-control" size="10" id="price" name="price">
        </div>
        <div class="col-6 col-md-2">
            Год выпуска
            <input type="text" class="form-control" size="4" id="yearOfIssue" name="yearOfIssue">
        </div>
    </div>
    <div class="row pt-2 row-cols-auto justify-content-center">
        <div class="col-6 col-md-3">
            Фото
            <input class="form-control" type="file" id="photo" name="photo">
        </div>
        <div class="col-6 col-md-1 pt-4">
            <button type="submit" class="btn btn-secondary">Сохранить</button>
        </div>
    </div>
        <div class="row pt-2 row-cols-auto justify-content-center">
            <div class="col-6">
                <h4>Ваши объявления:</h4>
            </div>
        </div>
    </form>
</div>
</body>
</html>
