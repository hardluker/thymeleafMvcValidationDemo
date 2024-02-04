package com.dba9514.mvc.controllers;

import com.dba9514.mvc.model.Customer;
import jakarta.validation.Valid;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CustomerController {

    // Init binder to convert trim input strings
    // remove leading and trailing whitespace
    // This is necessary for fields that cannot be null and prevents pure whitespace submissions.
    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        //Instantiating a string trimmer editor.
        //The "true" indicates pure whitespace will be trimmed to null.
        var stringTrimmerEditor = new StringTrimmerEditor(true);

        //Custom editor is registered on the binder.
        // This pre-processes all incoming string form data.
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/")
    public String showForm(Model model) {
        //Adding the customer to the model
        model.addAttribute("customer", new Customer());

        //.html file name
        return "customer-form";
    }

    //@Valid tells MVC to perform validation based on rules in preceding class
    @PostMapping("/processForm")
    public String processForm(
            @Valid @ModelAttribute("customer") Customer customer,
            BindingResult bindingResult) {

        System.out.println("Binding results: " + bindingResult.toString());
        System.out.println("\n\n\n\n");
        //Routing based on errors
        if (bindingResult.hasErrors())
            return "customer-form";
        //else
        return "customer-confirmation";
    }
}
