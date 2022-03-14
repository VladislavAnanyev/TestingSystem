package com.example.mywebquizengine.Controller;

import com.example.mywebquizengine.Model.Order;
import com.example.mywebquizengine.Model.OrderDetail;
//import com.example.mywebquizengine.Service.PaymentServices;
import com.example.mywebquizengine.Service.PaymentServices;
import com.example.mywebquizengine.Service.UserService;
/*import com.paypal.api.payments.PayerInfo;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.ShippingAddress;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.PayPalRESTException;*/
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class PayPalController {

    @Autowired
    private PaymentServices paymentServices;

    @Autowired
    private UserService userService;

    @GetMapping(path = "/checkout")
    public String getCheckOut(Model model, @AuthenticationPrincipal Principal principal) {

        Order order = new Order();

        order.setUser(userService.loadUserByUsernameProxy(principal.getName()));

        paymentServices.saveStartOrder(order);

        model.addAttribute("order", order);

        return "checkout";
    }

    /*@PostMapping(path = "/authorize_payment")
    public String authorizePayment(Model model, OrderDetail request) {
        String product = request.getProductName();
        String subtotal = request.getSubtotal();
        String shipping = request.getShipping();
        String tax = request.getTax();
        String total = request.getTotal();

        OrderDetail orderDetail = new OrderDetail(product, subtotal, shipping, tax, total);

        try {
            PaymentServices paymentServices = new PaymentServices();
            String approvalLink = paymentServices.authorizePayment(orderDetail);

            //response.sendRedirect(approvalLink);
            return "redirect:" + approvalLink;

        } catch (PayPalRESTException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            //request.setAttribute("errorMessage", ex.getMessage());
            ex.printStackTrace();
            return "error";
            //request.getRequestDispatcher("error.ftlh").forward(request, response);
        }
    }*/



   /* @GetMapping(path = "/review_payment")
    public String reviewPayment(Model model, *//*Payment request,*//* @RequestParam String paymentId, @RequestParam String PayerID) {

        try {
            PaymentServices paymentServices = new PaymentServices();
*//*            Payment payment = paymentServices.getPaymentDetails(paymentId);

            PayerInfo payerInfo = payment.getPayer().getPayerInfo();
            Transaction transaction = payment.getTransactions().get(0);

            ShippingAddress shippingAddress = transaction.getItemList().getShippingAddress();*//*

            //transaction.setDescription("IPhone");

            model.addAttribute("payer", payerInfo);
            model.addAttribute("transaction", transaction);
            model.addAttribute("shippingAddress", shippingAddress);

            model.addAttribute("paymentId", paymentId);
            model.addAttribute("PayerID", PayerID);

            //request.setAttribute("payer", payerInfo);
            //request.setAttribute("transaction", transaction);
            //request.setAttribute("shippingAddress", shippingAddress);


            String url = "review_payment?paymentId=" + paymentId + "&PayerID=" + PayerID;

            //return "redirect:/" + url;
            return "review";

            //request.getRequestDispatcher(url).forward(request, response);

        } catch (PayPalRESTException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            //request.setAttribute("errorMessage", ex.getMessage());
            ex.printStackTrace();
            return "error";
            //request.getRequestDispatcher("error.ftlh").forward(request, response);
        }
    }*/

    /*@PostMapping(path = "/execute_payment")
    public String executePayment(Model model, @RequestParam String paymentId, @RequestParam String PayerID) {


        try {
            PaymentServices paymentServices = new PaymentServices();
            Payment payment = paymentServices.executePayment(paymentId, PayerID);


            PayerInfo payerInfo = payment.getPayer().getPayerInfo();
            Transaction transaction = payment.getTransactions().get(0);

            //transaction.setDescription("IPhone");
            model.addAttribute("payer", payerInfo);
            model.addAttribute("transaction", transaction);

            *//*request.setAttribute("payer", payerInfo);
            request.setAttribute("transaction", transaction);*//*


            //request.getRequestDispatcher("receip").forward(request, response);
            return "receiptPage";

        } catch (PayPalRESTException ex) {
            //request.setAttribute("errorMessage", ex.getMessage());
            ex.printStackTrace();
            //request.getRequestDispatcher("error.ftlh").forward(request, response);
            return "error";
        }
    }*/
}
