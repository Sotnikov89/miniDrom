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
            }).done(function(data) {
                for(let i=0; i<data.length; i++) {
                    $("#FindMake").append(new Option(data[i].name, data[i].id));
                    for(let y=0; y<data[i].models.length; y++) {
                        let option = new Option(data[i].models[y].name, data[i].models[y].id);
                        option.setAttribute("id", data[i].id);
                        $("#FindModel").append(option);
                    }
                }
            }).fail(function(err){
                alert(err);
            });
            $.ajax({
                type: 'GET',
                url: 'type',
            }).done(function(data) {
                for(let i=0; i<data.length; i++) {
                    $("#FindType").append(new Option(data[i].name, data[i].id));
                }
            }).fail(function(err){
                alert(err);
            });
            getAll();
        })
        function getAll(){
            $('#adverts').empty();
            $.ajax({
                type: 'GET',
                url: 'advert',
                data: {getAll: true},
            }).done(function(data) {
                renderData(data);
            }).fail(function(err){
                alert(err);
            });
        }
        function getByToday(){
            $('#adverts').empty();
            $.ajax({
                type: 'GET',
                url: 'advert',
                data: {getByToday: true},
            }).done(function(data) {
                renderData(data);
            }).fail(function(err){
                alert(err);
            });
        }
        function getByFilter(){
            $('#adverts').empty();
            let object = {
                make: $('#FindMake').val(), model: $('#FindModel').val(), type: $('#FindType').val(),
                mileage: $('#FindMileage').val(), price: $('#FindPrice').val(), photo: $('#FindPhoto').is(":checked")};
            $.ajax({
                type: 'GET',
                url: 'advert',
                data: object,
            }).done(function(data) {
                renderData(data);
            }).fail(function(err){
                alert(err);
            });
        }
        function renderData(data) {
            for(let i=0; i<data.length; i++) {
                $('#adverts').append(
                    "<div class='container border-bottom pt-3' id='adverts'>" +
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
                                    "<div class='col-12 col-sm-12'>" +
                                        "Описание: " + data[i].description +
                                    "</div>" +
                                "</div>" +
                            "</div>" +
                        "</div>" +
                    "</div>"
                )
            }
        }
        function showModelByMake() {
            var makeId = $("#FindMake").val();
            $("#FindModel option").each(function () {
                let model = $(this);
                if (model.attr("id") === makeId) {
                    model.show();
                } else {
                    model.hide();
                }
            })
        }
    </script>
</head>
<body>
<div class="container pt-1">
    <div class="row pt-2 row-cols-auto justify-content-center">
        <div class="col-md-auto">
            <button type="button" class="btn btn-secondary" onclick="window.location.href='index'">Главная</button>
        </div>
        <div class="col-md-auto">
            <button type="button" class="btn btn-secondary" onclick="window.location.href='auth'">Вход и регистрация</button>
        </div>
        <div class="col-md-auto">
            <button type="button" class="btn btn-secondary" onclick="window.location.href='advert'">Подать/Закрыть</button>
        </div>
    </div>
    <div class="row pt-2 row-cols-auto justify-content-center">
        <div class="col-6 col-md-2">
            Марка
            <select class='form-select' aria-label='multiple select example' id='FindMake' onchange="showModelByMake()">
            </select>
        </div>
        <div class="col-6 col-md-2">
            Модель
            <select class='form-select' aria-label='multiple select example' id='FindModel'>
            </select>
        </div>
        <div class="col-6 col-md-2">
            Кузов
            <select class='form-select' aria-label='multiple select example' id='FindType'>
            </select>
        </div>
    </div>
    <div class="row pt-2 row-cols-auto justify-content-center">
        <div class="col-6 col-md-2">
            Цена, не больше
            <input type="text" class="form-control" size="10" id="FindPrice">
        </div>
        <div class="col-6 col-md-2">
            Пробег, не больше
            <input type="text" class="form-control" size="7" id="FindMileage">
        </div>
        <div class="col-8 col-md-2 pt-4">
            <label class="form-check-label" for="FindPhoto">С фото </label>
            <input type="checkbox" class="form-check-input" id="FindPhoto">
        </div>
    </div>
    <div class="row pt-2 row-cols-auto justify-content-center">
        <div class="col-6 col-md-2">
            <button type="button" class="btn btn-secondary" onclick="getByFilter()">Поиск</button>
        </div>
        <div class="col-6 col-md-2">
            <button type="button" class="btn btn-secondary" onclick="getAll()">Показать все</button>
        </div>
        <div class="col-6 col-md-2">
            <button type="button" class="btn btn-secondary" onclick="getByToday()">За сегодня</button>
        </div>
    </div>
</div>
<div class='container border-bottom pt-3' id='adverts'>
</div>
</body>
</html>
