<#import "parts/signinCommon.ftlh" as e>
<@e.page>


    <div class="container">
        <div class="row align-items-center justify-content-center min-vh-100 gx-0">

            <div class="col-12 col-md-5 col-lg-4">
                <div class="card card-shadow border-0">
                    <div class="card-body">
                        <div class="row g-6">
                            <div class="col-12">
                                <div class="text-center">
                                    <h3 class="fw-bold mb-2">Восстановление пароля</h3>
                                    <p>Введите ваш адрес электронной почты для восстановления</p>
                                </div>
                            </div>

                            <div class="col-12">
                                <div class="form-floating">
                                    <input type="email" name="email" class="form-control" id="resetpassword-email" placeholder="Email">
                                    <label for="resetpassword-email">Почта</label>
                                </div>
                            </div>

                            <div class="col-12">
                                <button onclick="sendEmailMsgWithoutAuth()" class="btn btn-block btn-lg btn-primary w-100" type="submit">Отправить ссылку на почту</button>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Text -->
                <div class="text-center mt-8">
                    <p>Уже есть аккаунт? <a href="/signin">Войти</a></p>
                </div>
            </div>
        </div> <!-- / .row -->
    </div>

    <script src="/static/forgotPassword.js"></script>

</@e.page>