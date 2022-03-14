<#import "parts/common.ftlh" as e>
<#include "parts/security.ftlh">

<@e.page>
<head>
    <meta charset="UTF-8">
    <title>Check Out</title>
    <style type="text/css">
        table { border: 0; }
        table td { padding: 10px; }
    </style>
</head>
<body>
<#--
<div align="center">
    <h1>Check Out</h1>
    <br/>
    <form action="authorize_payment" method="post">
        <table>
            <tr>
                <td>Product/Service:</td>
                <td><input type="text" name="productName" value="Одиндва" /></td>
            </tr>
            <tr>
                <td>Sub Total:</td>
                <td><input type="text" name="subtotal" value="100" /></td>
            </tr>
            <tr>
                <td>Shipping:</td>
                <td><input type="text" name="shipping" value="10" /></td>
            </tr>
            <tr>
                <td>Tax:</td>
                <td><input type="text" name="tax" value="10" /></td>
            </tr>
            <tr>
                <td>Total Amount:</td>
                <td><input type="text" name="total" value="120" /></td>
            </tr>
            <tr>
                <td colspan="2" align="center">
                    <input type="submit" value="Checkout" />
                </td>
            </tr>
        </table>
    </form>
</div>

<div id="smart-button-container">
    <div style="text-align: center;">
        <div id="paypal-button-container"></div>
    </div>
</div>
<script src="https://www.paypal.com/sdk/js?client-id=AZhcxVtP4Zd8xT9YsigI82RHCoT3TgR-15KbWBKgODth8tEBAKirS40yYRN_RnZD-2I7q9I-UGT-23nS&currency=USD" data-sdk-integration-source="button-factory"></script>
<script>
    function initPayPalButton() {
        paypal.Buttons({
            style: {
                shape: 'rect',
                color: 'gold',
                layout: 'vertical',
                label: 'paypal',

            },

            createOrder: function(data, actions) {
                return actions.order.create({
                    purchase_units: [{"amount":{"currency_code":"USD","value":1}}]
                });
            },

            onApprove: function(data, actions) {
                return actions.order.capture().then(function(details) {
                    alert('Transaction completed by ' + details.payer.name.given_name + '!');
                });
            },

            onError: function(err) {
                console.log(err);
            }
        }).render('#paypal-button-container');
    }
    initPayPalButton();
</script>
-->


<#--<iframe src="https://yoomoney.ru/quickpay/shop-widget?writer=seller&targets=%D0%A2%D0%B5%D1%81%D1%82%D0%BE%D0%B2%D0%B0%D1%8F%20%D0%BE%D0%BF%D0%BB%D0%B0%D1%82%D0%B0&targets-hint=%D0%A3%D0%BA%D0%B0&default-sum=2&button-text=11&payment-type-choice=on&mobile-payment-type-choice=on&fio=on&phone=on&mail=on&address=on&hint=&successURL=https%3A%2F%2Fwebquizzes.me%2F&quickpay=shop&account=410012943784354" width="100%" height="223" frameborder="0" allowtransparency="true" scrolling="no"></iframe>-->

<form class="mb-5" method="POST" action="https://yoomoney.ru/quickpay/confirm.xml">
    <div class="form-group">
        <label class="form-label">
            <input class="form-control" type="hidden" name="receiver" value="410012943784354">
        </label>
    </div>

    <#--<div class="form-group">
        <label for="exampleInputEmail1">Описание</label>
        <input class="form-control" type="text" name="formcomment" id="exampleInputEmail1" value="Проект «Железный человек»: реактор холодного ядерного синтеза">
    </div>-->

    <div class="form-group">
        <label class="form-label">Назначение</label>
        <input class="form-control" type="text" name="short-dest" value="Покупка Coins" disabled>

    </div>


    <input class="form-control" type="hidden" name="label" value="${order.order_id?c}">
    <input class="form-control" type="hidden" name="quickpay-form" value="donate">
    <input class="form-control" type="hidden" name="targets" value="Покупка coins">

    <div class="form-group">
        <label class="form-label">Сумма в рублях (1 руб. = 100 coins)</label>
        <input class="form-control" type="text" name="sum" value="2" data-type="number">
    </div>

    <#--<div class="form-group">
        <label class="form-label">Комментарий</label>
        <input class="form-control" type="text" name="comment" value="Хотелось бы получить дистанционное управление.">
    </div>-->

    <input class="form-control" type="hidden" name="need-fio" value="false">
    <input class="form-control" type="hidden" name="need-email" value="false">
    <input class="form-control" type="hidden" name="need-phone" value="false">
    <input class="form-control" type="hidden" name="need-address" value="false">
    <input class="form-control" type="hidden" name="successURL" value="https://webquizzes.me/checkout">
    <div>
    <input type="radio" name="paymentType" checked value="PC"> ЮMoney
    </div>

    <div>
    <input type="radio" name="paymentType" value="AC">
    <label class="form-check-label"> Банковской картой</label>
    </div>

    <div class="mt-3">
    <button type="submit" class="btn btn-primary" value="Перевести">Купить</button>
    </div>
</form>




</body>
</@e.page>