<#import "parts/signinCommon.ftlh" as e>
<#include "parts/security.ftlh">

<@e.page>


    <body class="bg-light">

    <div class="container">
        <form action="/signin" method="post">
            <div class="row align-items-center justify-content-center min-vh-100 gx-0">
                <div class="col-12 col-md-5 col-lg-4">
                    <div class="card card-shadow border-0">
                        <div class="card-body">
                            <div class="row g-6">
                                <div class="col-12">
                                    <div class="text-center">
                                        <h3 class="fw-bold mb-2">Вход</h3>
                                        <p>Войдите в аккаунт</p>
                                    </div>
                                </div>

                                <div class="col-12">
                                    <div class="form-floating">
                                        <input type="text" class="form-control" id="signin-email" name="username"
                                               placeholder="Email">
                                        <label for="signin-email">Почта</label>
                                    </div>
                                </div>

                                <div class="col-12">
                                    <div class="form-floating">
                                        <input type="password" class="form-control" name="password" id="signin-password"
                                               placeholder="Password">
                                        <label for="signin-password">Пароль</label>
                                    </div>
                                </div>

                                <div class="col-12">
                                    <button class="btn btn-block btn-lg btn-primary w-100" type="submit">Войти
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>


                    <!-- Text -->
                    <div class="text-center mt-8">
                        <p>Ещё нет аккаунта? <a
                                    href="/signup">Зарегестрироваться</a></p>
                        <p><a href="/password/forget">Забыли пароль?</a>
                        </p>
                    </div>
                </div>

            </div> <!-- / .row -->
        </form>
    </div>


</@e.page>