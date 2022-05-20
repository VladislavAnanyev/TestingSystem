<#import "parts/signinCommon.ftlh" as c>
<@c.page>

    <form action="/signup" method="post">
        <div class="container">
            <div class="row align-items-center justify-content-center min-vh-100 gx-0">

                <div class="col-12 col-md-5 col-lg-4">
                    <div class="card card-shadow border-0">
                        <div class="card-body">
                            <div class="row g-6">
                                <div class="col-12">
                                    <div class="text-center">
                                        <h3 class="fw-bold mb-2">Регистрация</h3>
                                        <p>Заполните поля ниже</p>
                                    </div>
                                </div>

                                  <div class="col-12">
                                      <label for="email">Почта</label>
                                      <input type="text" class="form-control" id="email" name="email" required>
                                  </div>


                                <div class="col-12">
                                    <label for="validationDefault01">Имя</label>
                                    <input type="text" class="form-control" id="validationDefault01" name="firstName" required>
                                </div>
                                <div class="col-12">
                                    <label for="validationDefault02">Фамилия</label>
                                    <input type="text" class="form-control" id="validationDefault02" name="lastName" required>
                                </div>

                                <div class="col-12">
                                    <div class="form-floating">
                                        <input type="password" name="password" class="form-control" id="signup-password" placeholder="Пароль">
                                        <label for="signup-password">Пароль</label>

                                        <div id="passwordHelpBlock" class="form-text">
                                            Ваш пароль должен состоять из хотя бы 5 символов и не должен содержать пробелов, специальных символов или эмодзи.
                                        </div>
                                    </div>
                                </div>

                                <div class="col-12">
                                    <button class="btn btn-block btn-lg btn-primary w-100" type="submit">Зарегистрироваться</button>
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
    </form>

</@c.page>