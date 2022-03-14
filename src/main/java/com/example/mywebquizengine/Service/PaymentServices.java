package com.example.mywebquizengine.Service;

import com.example.mywebquizengine.Model.Order;
import com.example.mywebquizengine.Model.OrderDetail;
import com.example.mywebquizengine.Repos.OrderRepository;
/*import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;*/
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentServices {
    private static final String CLIENT_ID = "AZhcxVtP4Zd8xT9YsigI82RHCoT3TgR-15KbWBKgODth8tEBAKirS40yYRN_RnZD-2I7q9I-UGT-23nS";
    private static final String CLIENT_SECRET = "EIIuBSiMIrEcN8B9YWGzm6Okyn34-MDhP1_AM-DNQ-NM2U-g6v8rN4PQLQtMEPT7WhnmACDqrifFrw33";
    private static final String MODE = "live";

    @Autowired
    private OrderRepository orderRepository;

    public Order saveFinalOrder(Order order) {



        Order finalOrder = orderRepository.findById(order.getOrder_id()).get();
        finalOrder.setCoins(order.getCoins());
        finalOrder.setAmount(order.getAmount());
        finalOrder.setOperation_id(order.getOperation_id());

        orderRepository.save(finalOrder);

        return finalOrder;
    }

    public void saveStartOrder(Order order) {

        orderRepository.save(order);
    }


    /*public String authorizePayment(OrderDetail orderDetail)
            throws PayPalRESTException {

        Payer payer = getPayerInformation();
        RedirectUrls redirectUrls = getRedirectURLs();
        List<Transaction> listTransaction = getTransactionInformation(orderDetail);

        Payment requestPayment = new Payment();
        requestPayment.setTransactions(listTransaction);
        requestPayment.setRedirectUrls(redirectUrls);
        requestPayment.setPayer(payer);
        requestPayment.setIntent("sale");

        APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);

        Payment approvedPayment = requestPayment.create(apiContext);

        return getApprovalLink(approvedPayment);

    }

    private Payer getPayerInformation() {
        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        PayerInfo payerInfo = new PayerInfo();
        payerInfo.setFirstName("Владислав")
                .setLastName("Ананьев")
                .setEmail("a.vlad.v@yandex.ru");

        payer.setPayerInfo(payerInfo);

        return payer;
    }

    private RedirectUrls getRedirectURLs() {
        RedirectUrls redirectUrls = new RedirectUrls();
        String url = "";
        try {
           url = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }


        redirectUrls.setCancelUrl("http://" + url + ":8080" + "/error");
        redirectUrls.setReturnUrl("http://" + url + ":8080" + "/review_payment");

        return redirectUrls;
    }

    private List<Transaction> getTransactionInformation(OrderDetail orderDetail) {
        Details details = new Details();

        details.setShipping(orderDetail.getShipping());
        details.setSubtotal(orderDetail.getSubtotal());
        details.setTax(orderDetail.getTax());

        Amount amount = new Amount();
        amount.setCurrency("RUB");
        amount.setTotal(orderDetail.getTotal());
        amount.setDetails(details);

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setDescription(orderDetail.getProductName());


        ItemList itemList = new ItemList();
        List<Item> items = new ArrayList<>();

        Item item = new Item();
        item.setCurrency("RUB");
        item.setName(orderDetail.getProductName());
        item.setPrice(orderDetail.getSubtotal());
        item.setTax(orderDetail.getTax());
        item.setQuantity("1");
        item.setSku("1");


        items.add(item);
        itemList.setItems(items);

        *//*ShippingAddress shippingAddress = new ShippingAddress();
        shippingAddress.setCountryCode("US");
        shippingAddress.setRecipientName("Александр");
        shippingAddress.setLine1("Первая линия");
        shippingAddress.setCity("Москва");
        shippingAddress.setState("Москва");
        shippingAddress.setPostalCode("345654");
        itemList.setShippingAddress(shippingAddress);*//*

        transaction.setItemList(itemList);

        //

        //shippingAddress.setCountryCode("RU");

        //transaction.getItemList().setShippingAddress(shippingAddress);

        List<Transaction> listTransaction = new ArrayList<>();
        listTransaction.add(transaction);

        return listTransaction;
    }

    private String getApprovalLink(Payment approvedPayment) {
        List<Links> links = approvedPayment.getLinks();
        String approvalLink = null;

        for (Links link : links) {
            if (link.getRel().equalsIgnoreCase("approval_url")) {
                approvalLink = link.getHref();
                break;
            }
        }

        return approvalLink;
    }

    public Payment getPaymentDetails(String paymentId) throws PayPalRESTException {
        APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);
        return Payment.get(apiContext, paymentId);
    }

    public Payment executePayment(String paymentId, String payerId)
            throws PayPalRESTException {
        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);

        Payment payment = new Payment().setId(paymentId);

        APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);

        return payment.execute(apiContext, paymentExecution);
    }
*/
    public Order findById(String label) {
        return orderRepository.findById(Integer.valueOf(label)).get();
    }
}
