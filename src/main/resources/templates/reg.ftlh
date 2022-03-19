<#import "parts/common.ftlh" as c>
<@c.page>

    <form action="/signup" method="post">
        <title>Регистрация</title>
        <div class="form-row">
            <div class="col-md-6 mb-3">
                <label for="validationDefault01">Имя</label>
                <input type="text" class="form-control" id="validationDefault01" name="firstName" required>
            </div>
            <div class="col-md-6 mb-3">
                <label for="validationDefault02">Фамилия</label>
                <input type="text" class="form-control" id="validationDefault02" name="lastName" required>
            </div>
            <div hidden class="col-md-6 mb-3">
                <label for="validationDefault02">Код</label>
                <input type="text" class="form-control" id="validationDefault02" value="${code}" name="activationCode" required>
            </div>

        </div>

        <div class="form-group">
            <label for="exampleInputPassword1">Пароль</label>
            <input required minlength="5" type="password" class="form-control" id="exampleInputPassword1" name="password" aria-describedby="passwordHelpBlock">
            <div id="passwordHelpBlock" class="form-text">
                Ваш пароль должен состоять из хотя бы 5 символов и не должен содержать пробелов, специальных символов или эмодзи.
            </div>
        </div>
        <button class="btn btn-primary" type="submit">Зарегистироваться</button>
    </form>

</@c.page>