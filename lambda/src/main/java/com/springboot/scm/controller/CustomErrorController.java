package com.springboot.scm.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(
            HttpServletRequest request,
            Model model) {

        Object statusAttribute = request.getAttribute(
                RequestDispatcher.ERROR_STATUS_CODE);

        int statusCode = statusAttribute instanceof Integer
                ? (Integer) statusAttribute
                : HttpStatus.INTERNAL_SERVER_ERROR.value();

        String message;

        switch (statusCode) {
            case 400:
                message = "Bad Request";
                break;
            case 401:
                message = "You must log in to access this resource.";
                break;
            case 403:
                message = "You do not have permission to access this resource.";
                break;
            case 404:
                message = "The page you are looking for was not found.";
                break;
            case 405:
                message = "This request method is not allowed.";
                break;
            case 408:
                message = "The request timed out.";
                break;
            case 429:
                message = "Too many requests. Please try again later.";
                break;
            case 500:
                message = "An internal server error occurred.";
                break;
            case 502:
                message = "Bad Gateway.";
                break;
            case 503:
                message = "The service is temporarily unavailable.";
                break;
            default:
                message = "Something went wrong. Please try again.";
                break;
        }

        model.addAttribute("statusCode", statusCode);
        model.addAttribute("errorMessage", message);

        return "error/error";
    }
}