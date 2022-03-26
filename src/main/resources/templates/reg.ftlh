<#import "parts/signinCommon.ftlh" as c>
<@c.page>

    <#--<form action="/signup" method="post">
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
    </form>-->

    <form action="/signup" method="post">
    <div class="container">
        <div class="row align-items-center justify-content-center min-vh-100 gx-0">

            <div class="col-12 col-md-5 col-lg-4">
                <div class="card card-shadow border-0">
                    <div class="card-body">
                        <div class="row g-6">
                            <div class="col-12">
                                <div class="text-center">
                                    <h3 class="fw-bold mb-2">Sign Up</h3>
                                    <p>Follow the easy steps</p>
                                </div>
                            </div>

                      <#--      <div class="col-12">
                                <div class="form-floating">
                                    <input type="text" class="form-control" id="signup-name" placeholder="Name">
                                    <label for="signup-name">Name</label>
                                </div>
                            </div>
-->


                            <div class="col-12">
                                <label for="validationDefault01">Имя</label>
                                <input type="text" class="form-control" id="validationDefault01" name="firstName" required>
                            </div>
                            <div class="col-12">
                                <label for="validationDefault02">Фамилия</label>
                                <input type="text" class="form-control" id="validationDefault02" name="lastName" required>
                            </div>
                            <div hidden class="col-12">
                                <label for="validationDefault02">Код</label>
                                <input type="text" class="form-control" id="validationDefault02" value="${code}" name="activationCode" required>
                            </div>

                            <div class="col-12">
                                <div class="form-floating">
                                    <input type="password" name="password" class="form-control" id="signup-password" placeholder="Password">
                                    <label for="signup-password">Password</label>

                                    <div id="passwordHelpBlock" class="form-text">
                                        Ваш пароль должен состоять из хотя бы 5 символов и не должен содержать пробелов, специальных символов или эмодзи.
                                    </div>
                                </div>
                            </div>

                            <div class="col-12">
                                <button class="btn btn-block btn-lg btn-primary w-100" type="submit">Create Account</button>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Text -->
                <div class="text-center mt-8">
                    <p>Already have an account? <a href="/signin">Sign in</a></p>
                </div>

            </div>
        </div> <!-- / .row -->
    </div>
    </form>

</@c.page>