<#import "parts/signinCommon.ftlh" as e>
<@e.page>
    <title>Изменить пароль</title>
    <script src="/static/changePassword.js"></script>
    <!-- Button trigger modal -->

    <div class="container">
        <div class="row align-items-center justify-content-center min-vh-100 gx-0">

            <div class="col-12 col-md-5 col-lg-4">

                <div class="card card-shadow border-0">
                    <div class="card-body position-relative pt-0 mb-6">
                        <div class="position-absolute top-0 start-50 translate-middle">
                            <a href="#" class="avatar avatar-xl mx-auto border border-light border-5">
                                <img src="${user.avatar}" alt="" class="avatar-img">
                            </a>
                        </div>
                    </div>

                    <div class="card-body">
                        <div class="row g-6">
                            <div class="col-12">
                                <div class="text-center">
                                    <h4 class="fw-bold">${user.firstName} ${user.lastName}</h4>
                                    <p>Введите новый пароль</p>
                                </div>
                            </div>

                            <div class="col-12">
                                <div class="form-floating">
                                    <input type="password" class="form-control" id="lockscreen-password" placeholder="Password">
                                    <label for="lockscreen-password">Password</label>
                                </div>
                            </div>

                            <div class="col-12">
                                <button onclick="changePass()" class="btn btn-block btn-lg btn-primary w-100" type="submit">Подтвердить</button>
                            </div>
                        </div>

                    </div>
                </div>

            </div>
        </div> <!-- / .row -->
    </div>


    <#--<button type="button" class="btn btn-primary ml-2 my-1" data-toggle="modal" data-target="#staticBackdrop">
        Изменить пароль
    </button>

    <div class="modal fade" id="staticBackdrop" data-backdrop="static" data-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="staticBackdropLabel">Изменение пароля</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <div class="form-group">

                        &lt;#&ndash;                        <input type="text" class="form-control" id="oldpassword" placeholder="Придумайте новый пароль"  aria-describedby="emailHelp" name="password">&ndash;&gt;
                        <input type="password" class="form-control" id="password1" placeholder="Придумайте новый пароль"  aria-describedby="emailHelp" name="password1">
                        <input type="password" class="form-control mt-3" id="password2" placeholder="Введите новый пароль ещё раз" aria-describedby="emailHelp" name="password2">
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Назад</button>
                    <button data-dismiss="modal" type="button" onclick="changePass()" class="btn btn-primary">Сменить</button>
                </div>

            </div>
        </div>
    </div>-->
</@e.page>