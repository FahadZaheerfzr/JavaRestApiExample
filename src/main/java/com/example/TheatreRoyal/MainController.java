package com.example.TheatreRoyal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller // This means that this class is a Controller
@RequestMapping(path="/demo") // This means URL's start with /demo (after Application path)
public class MainController {
    DBConnector db;
    MainController(){
        db = new DBConnector();
        db.connect();
    }

    @PostMapping(path="/add") // Map ONLY POST Requests
    public @ResponseBody String addNewUser (@RequestParam String forename
            , @RequestParam String surname, @RequestParam String email, @RequestParam String address,
                                            @RequestParam String password) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        db.runQuery("Insert into Users(forename, surname, email, address, password) Values ('" + forename + "', '" + surname + "', '" + email
                + "', '" + address + "', '" + password + "')");

        return "Saved";
    }

    @GetMapping(path="/all")
    public @ResponseBody String getAllUsers() {
        // This returns a JSON or XML with the users
        return "Hello";
    }
}
